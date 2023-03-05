package top.liubaiblog.masterstudio.service;

import com.baomidou.mybatisplus.extension.service.IService;
import top.liubaiblog.masterstudio.domain.po.Log;
import top.liubaiblog.masterstudio.domain.query.log.GetLogQuery;
import top.liubaiblog.masterstudio.domain.vo.PageVO;
import top.liubaiblog.masterstudio.domain.vo.log.LogDetailVO;
import top.liubaiblog.masterstudio.domain.vo.log.LogPartVO;

import java.util.List;

/**
* @author 13326
* @description 针对表【ms_log(日志表)】的数据库操作Service
* @createDate 2022-10-03 15:23:40
*/
public interface LogService extends IService<Log> {

    /**
     * 根据条件获取日志列表
     */
    PageVO<LogPartVO> listByLogQuery(GetLogQuery logQuery);

    /**
     * 根据编号获取日志详情
     */
    LogDetailVO getById(Long lid);

    /**
     * 根据编号删除日志列表
     */
    boolean removeByIds(List<Long> ids);

    /**
     * 删除全部日志
     */
    boolean removeAll();
}
