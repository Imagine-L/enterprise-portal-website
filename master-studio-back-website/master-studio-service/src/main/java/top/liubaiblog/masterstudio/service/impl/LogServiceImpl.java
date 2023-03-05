package top.liubaiblog.masterstudio.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import top.liubaiblog.masterstudio.common.exception.param.RequestParamsException;
import top.liubaiblog.masterstudio.dao.mapper.LogMapper;
import top.liubaiblog.masterstudio.domain.po.Log;
import top.liubaiblog.masterstudio.domain.po.User;
import top.liubaiblog.masterstudio.domain.query.log.GetLogQuery;
import top.liubaiblog.masterstudio.domain.vo.PageVO;
import top.liubaiblog.masterstudio.domain.vo.log.LogDetailVO;
import top.liubaiblog.masterstudio.domain.vo.log.LogPartVO;
import top.liubaiblog.masterstudio.service.LogService;

import java.util.Collection;
import java.util.List;

/**
 * @author 13326
 * @description 针对表【ms_log(日志表)】的数据库操作Service实现
 * @createDate 2022-10-03 15:23:40
 */
@Service
public class LogServiceImpl extends ServiceImpl<LogMapper, Log>
        implements LogService {

    @Override
    public PageVO<LogPartVO> listByLogQuery(GetLogQuery logQuery) {
        Page<LogPartVO> page = new Page<>(logQuery.getPageNo(), logQuery.getPageSize());
        getBaseMapper().pageByCondition(page,
                logQuery.getModule(),
                logQuery.getOperType(),
                logQuery.getRequestMode(),
                logQuery.getRequestUser(),
                logQuery.getCreateStartTime(),
                logQuery.getCreateStopTime());
        PageVO<LogPartVO> pageVO = new PageVO<>();
        pageVO.setTotalRecords(page.getTotal());
        pageVO.setTotalPages(page.getPages());
        pageVO.setRecords(page.getRecords());
        return pageVO;
    }

    @Override
    public boolean removeByIds(List<Long> ids) {
        // 获取当前登录用户
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();
        // 判断权限
        if (!currentUser.getAdmin()) {
            throw new RequestParamsException("当前用户无权限删除日志");
        }
        return removeByIds((Collection<?>) ids);
    }

    @Override
    public boolean removeAll() {
        // 获取当前登录用户
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();
        // 判断权限
        if (!currentUser.getAdmin()) {
            throw new RequestParamsException("当前用户无权限删除日志");
        }
        return remove(null);
    }

    @Override
    public LogDetailVO getById(Long lid) {
        return getBaseMapper().selectDetailById(lid);
    }
}




