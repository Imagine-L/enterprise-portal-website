package top.liubaiblog.masterstudio.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import top.liubaiblog.masterstudio.domain.po.Log;
import top.liubaiblog.masterstudio.domain.vo.log.LogDetailVO;
import top.liubaiblog.masterstudio.domain.vo.log.LogPartVO;

import java.util.Date;

/**
 * @author 13326
 * @description 针对表【ms_log(日志表)】的数据库操作Mapper
 * @createDate 2022-10-03 15:23:40
 * @Entity top.liubaiblog.masterstudio.domain.do.Log
 */
public interface LogMapper extends BaseMapper<Log> {

    Page<LogPartVO> pageByCondition(Page<LogPartVO> page,
                                    @Param("module") String module,
                                    @Param("operType") String operType,
                                    @Param("requestMode") String requestMode,
                                    @Param("requestUser") String requestUser,
                                    @Param("createStartTime") String createStartTime,
                                    @Param("createStopTime") String createStopTime);

    /**
     * 根据编号查询日志详情
     */
    LogDetailVO selectDetailById(@Param("lid") Long lid);
}




