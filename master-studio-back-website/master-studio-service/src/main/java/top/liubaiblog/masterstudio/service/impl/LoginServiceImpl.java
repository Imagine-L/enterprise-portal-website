package top.liubaiblog.masterstudio.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static top.liubaiblog.masterstudio.common.constant.RedisConstants.*;

import org.springframework.util.StringUtils;
import top.liubaiblog.masterstudio.common.constant.JwtConstants;
import top.liubaiblog.masterstudio.common.constant.RabbitConstants;
import top.liubaiblog.masterstudio.common.exception.auth.LoginException;
import top.liubaiblog.masterstudio.domain.bo.CodeBO;
import top.liubaiblog.masterstudio.domain.bo.SecurityUser;
import top.liubaiblog.masterstudio.domain.message.EmailMessage;
import top.liubaiblog.masterstudio.domain.po.User;
import top.liubaiblog.masterstudio.domain.prop.ForgetPasswordMessageProperties;
import top.liubaiblog.masterstudio.domain.query.LoginQuery;
import top.liubaiblog.masterstudio.domain.query.forget.UpdatePwdByTempTokenQuery;
import top.liubaiblog.masterstudio.domain.query.forget.ValidateCodeQuery;
import top.liubaiblog.masterstudio.domain.vo.LoginVO;
import top.liubaiblog.masterstudio.service.LoginService;
import top.liubaiblog.masterstudio.service.UserService;
import top.liubaiblog.masterstudio.util.http.HttpRequestUtils;
import top.liubaiblog.masterstudio.util.jwt.JwtUtils;
import top.liubaiblog.masterstudio.util.redis.RedisUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author 留白
 * @description
 */
@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private UserService userService;

    @Autowired
    @SuppressWarnings("all")
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ForgetPasswordMessageProperties forgetPasswordMessageProperties;

    @Override
    public LoginVO login(LoginQuery loginQuery) {
        // 创建身份认证对象
        UsernamePasswordAuthenticationToken beforeAuthentication =
                new UsernamePasswordAuthenticationToken(loginQuery.getUsername(), loginQuery.getPassword());
        // 进行身份认证
        Authentication authenticate = authenticationManager.authenticate(beforeAuthentication);
        if (Objects.isNull(authenticate)) {
            throw new LoginException("用户登录认证异常");
        }
        // 获取认证通过的用户对象
        SecurityUser securityUser = (SecurityUser) authenticate.getPrincipal();
        Long currUid = securityUser.getCurrentUser().getUid();
        String currUsername = securityUser.getUsername();
        // 生成token
        Map<String, Object> claims = new HashMap<>(10);
        claims.put(JwtConstants.UID_KEY, currUid);
        claims.put(JwtConstants.USERNAME_KEY, currUsername);
        String token = JwtUtils.create(claims);
        // 将用户信息存入redis
        redisUtils.valSet(AUTH_PREFIX + SEPARATOR + currUid + SEPARATOR + token,
                securityUser.getCurrentUser(),
                AUTH_EXPIRE_SECONDS,
                TimeUnit.SECONDS);
        // 封装LoginVO返回
        LoginVO loginVO = new LoginVO();
        loginVO.setToken(token);
        BeanUtils.copyProperties(securityUser.getCurrentUser(), loginVO);
        return loginVO;
    }

    @Override
    public void logout() {
        // 获取当前登录的用户对象
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currUser = (User) authentication.getPrincipal();
        // 获取token
        String token = HttpRequestUtils.getToken();
        // 从redis中删除用户信息
        redisUtils.delete(AUTH_PREFIX + SEPARATOR + currUser.getUid() + SEPARATOR + token);
    }

    @Override
    public String getCodeAfterBuildToken(String username) {
        // 获取绑定了该邮箱的账号
        User forgetUser = userService.getByUsername(username);
        if (Objects.isNull(forgetUser)) {
            return null;
        }
        String email = forgetUser.getEmail();
        // 生成验证码，发送给个人用户
        String code = UUID.randomUUID().toString().substring(0, 6);
        EmailMessage emailMessage = new EmailMessage(email,
                forgetPasswordMessageProperties.getSubject(),
                forgetPasswordMessageProperties.getText().replace("${code}", code),
                null,
                forgetPasswordMessageProperties.getType());
        rabbitTemplate.convertAndSend(RabbitConstants.EMAIL_EXCHANGE,
                RabbitConstants.EMAIL_EXCHANGE_BINDING_QUEUE,
                emailMessage);
        // 生成临时token
        String tempToken = UUID.randomUUID().toString().replace("-", "");
        // 在redis中存储token和其验证码
        CodeBO codeBO = new CodeBO(forgetUser.getUid(), code);
        redisUtils.valSet(FORGET_PASSWORD_PREFIX + SEPARATOR + FORGET_STEP1_PREFIX + SEPARATOR + tempToken,
                codeBO,
                FORGET_STEP1_EXPIRE_SECONDS,
                TimeUnit.SECONDS);
        return tempToken;
    }

    @Override
    public boolean validateCode(ValidateCodeQuery validateCodeQuery) {
        String tempToken = validateCodeQuery.getTempToken();
        // 获取redis中的验证码
        CodeBO codeBO = redisUtils.valGetObject(FORGET_PASSWORD_PREFIX + SEPARATOR + FORGET_STEP1_PREFIX + SEPARATOR + tempToken);
        // 比较两个验证码是否一致
        if (Objects.isNull(codeBO) || !Objects.equals(validateCodeQuery.getCode(), codeBO.getCode())) {
            return false;
        }
        // 一致则将用户编号存储到redis中
        redisUtils.valSet(FORGET_PASSWORD_PREFIX + SEPARATOR + FORGET_STEP2_PREFIX + SEPARATOR + tempToken,
                codeBO.getUid(),
                FORGET_STEP2_EXPIRE_SECONDS,
                TimeUnit.SECONDS);
        // 删除第一步生成的验证码缓存
        redisUtils.delete(FORGET_PASSWORD_PREFIX + SEPARATOR + FORGET_STEP1_PREFIX + SEPARATOR + tempToken);
        return true;
    }

    @Override
    public boolean updatePwdByTempToken(UpdatePwdByTempTokenQuery updateQuery) {
        // 获取临时令牌
        String tempToken = updateQuery.getTempToken();
        if (!StringUtils.hasText(tempToken)) {
            return false;
        }
        // 根据临时令牌从redis中获取用户编号
        String uid = redisUtils.valGet(FORGET_PASSWORD_PREFIX + SEPARATOR + FORGET_STEP2_PREFIX + SEPARATOR + tempToken);
        if (!StringUtils.hasText(uid)) {
            return false;
        }
        // 根据用户编号修改密码
        LambdaUpdateWrapper<User> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(User::getUid, uid);
        wrapper.set(User::getPassword, passwordEncoder.encode(updateQuery.getNewPwd()));
        boolean result = userService.update(wrapper);
        // 修改成功则删除之前生成的用户编号缓存
        if (result) {
            redisUtils.delete(FORGET_PASSWORD_PREFIX + SEPARATOR + FORGET_STEP2_PREFIX + SEPARATOR + tempToken);
            return true;
        }
        return false;
    }
}
