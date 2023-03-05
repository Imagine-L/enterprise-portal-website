package top.liubaiblog.masterstudio.util.aspect;

import com.baomidou.mybatisplus.core.toolkit.ArrayUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import top.liubaiblog.masterstudio.common.annotation.SetLog;
import top.liubaiblog.masterstudio.common.constant.RabbitConstants;
import top.liubaiblog.masterstudio.domain.po.Log;
import top.liubaiblog.masterstudio.domain.po.User;
import top.liubaiblog.masterstudio.util.http.HttpRequestUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Objects;

/**
 * @author 留白
 * @description 日志增强类，向异步写入日志信息
 */
@Aspect
@Component
@Slf4j
public class LogAspect {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    @SuppressWarnings("all")
    private RabbitTemplate rabbitTemplate;

    @Around("@annotation(setLog)")
    public Object around(ProceedingJoinPoint joinPoint, SetLog setLog) {
        Object result = null;
        // 记录程序开始运行时间
        long startTime = System.currentTimeMillis();
        try {
            result = joinPoint.proceed();
        } catch (Throwable e) {
            saveLog(joinPoint, setLog, startTime, e);
            throw new RuntimeException(e);
        }
        saveLog(joinPoint, setLog, startTime, null);
        return result;
    }

    /**
     * 保存日志
     */
    private void saveLog(ProceedingJoinPoint joinPoint, SetLog setLog, long startTime, Throwable e) {
        Log sysLog = new Log();
        try {
            // 取得请求对象
            HttpServletRequest req = HttpRequestUtils.getLocalRequest();
            // 获取登录对象
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            Object principal = authentication.getPrincipal();
            User currentUser = null;
            if (principal instanceof User) {
                currentUser = (User) principal;
            } else {
                currentUser = new User();
                currentUser.setUid(-1L);
                currentUser.setUsername("anonymous");
            }
            // 方法名
            String className = joinPoint.getSignature().getDeclaringType().getName();
            String methodName = className + "." + joinPoint.getSignature().getName() + "()";
            // 方法参数
            String requestJson = null;
            Object[] requestParams = joinPoint.getArgs();
            if (!ArrayUtils.isEmpty(requestParams)) {
                requestJson = objectMapper.writeValueAsString(requestParams[0]);
            }
            // 封装日志对象
            sysLog.setModule(setLog.module().getValue());
            sysLog.setOperType(setLog.operType());
            sysLog.setRequestMode(req.getMethod());
            sysLog.setRequestAddr(req.getServletPath());
            sysLog.setRequestJson(requestJson);
            sysLog.setRequestUser(currentUser.getUid());
            sysLog.setHandlerMethod(methodName);
            sysLog.setCreateTime(new Date());
            sysLog.setRuntime((int)(System.currentTimeMillis() - startTime));
            if (Objects.isNull(e)) {
                sysLog.setSuccess(true);
            } else {
                sysLog.setSuccess(false);
                sysLog.setErrorClass(e.getClass().getName());
                sysLog.setErrorMessage(e.getMessage());
            }
            rabbitTemplate.convertAndSend(RabbitConstants.LOG_EXCHANGE,
                    RabbitConstants.LOG_SAVE_EXCHANGE_BINDING_QUEUE,
                    sysLog);
            printLog(sysLog);
        } catch (Exception ex) {
            log.error("日志封装出错 => ", ex);
        }
    }

    /**
     * 打印日志
     */
    private void printLog(Log sysLog) {
        log.info("============= 日志信息[start] =============");
        log.info("请求模块 => {}", sysLog.getModule());
        log.info("操作类型 => {}", sysLog.getOperType());
        log.info("请求地址 => {}", sysLog.getRequestAddr());
        log.info("请求方式 => {}", sysLog.getRequestMode());
        log.info("请求参数 => {}", sysLog.getRequestJson());
        log.info("请求用户编号 => {}", sysLog.getRequestUser());
        log.info("处理方法 => {}", sysLog.getHandlerMethod());
        log.info("处理时长 => {} ms", sysLog.getRuntime());
        if (!sysLog.getSuccess()) {
            log.error("是否成功 => {}", sysLog.getSuccess());
            log.error("异常类名 => {}", sysLog.getErrorClass());
            log.error("异常信息 => {}", sysLog.getErrorMessage());
        } else {
            log.info("是否成功 => {}", sysLog.getSuccess());
        }
        log.info("============= 日志信息[end] =============");
    }
}
