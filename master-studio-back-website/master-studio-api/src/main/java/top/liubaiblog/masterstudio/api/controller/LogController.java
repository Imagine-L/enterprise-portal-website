package top.liubaiblog.masterstudio.api.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import top.liubaiblog.masterstudio.common.annotation.SetLog;
import top.liubaiblog.masterstudio.domain.enums.LogModuleEnums;
import top.liubaiblog.masterstudio.domain.enums.ResponseDataEnum;
import top.liubaiblog.masterstudio.domain.query.log.GetLogQuery;
import top.liubaiblog.masterstudio.domain.vo.PageVO;
import top.liubaiblog.masterstudio.domain.vo.ResponseData;
import top.liubaiblog.masterstudio.domain.vo.log.LogDetailVO;
import top.liubaiblog.masterstudio.domain.vo.log.LogPartVO;
import top.liubaiblog.masterstudio.service.LogService;
import top.liubaiblog.masterstudio.util.http.ResponseDataBuilder;

import java.util.List;

/**
 * @author 留白
 * @description
 */
@RestController
@RequestMapping("/log")
@Api(tags = "日志监控控制器")
public class LogController {

    @Autowired
    private LogService logService;

    @PostMapping("/list")
    @ApiOperation("查看日志列表")
    public ResponseData<PageVO<LogPartVO>> list(@RequestBody @Validated GetLogQuery logQuery) {
        PageVO<LogPartVO> pageVO = logService.listByLogQuery(logQuery);
        return ResponseDataBuilder.builder().success(pageVO);
    }

    @GetMapping("/{lid}")
    @ApiOperation("查看日志详情")
    public ResponseData<LogDetailVO> getById(@PathVariable("lid") Long lid) {
        LogDetailVO logDetailVO = logService.getById(lid);
        return ResponseDataBuilder.builder().success(logDetailVO);
    }

    @ApiOperation("删除日志")
    @DeleteMapping
    @SetLog(module = LogModuleEnums.LOG, operType = "删除日志")
    public ResponseData<Void> deleteByIds(@RequestBody List<Long> ids) {
        if (logService.removeByIds(ids)) {
            return ResponseDataBuilder.builder().success(null);
        } else {
            return ResponseDataBuilder.builder().build(ResponseDataEnum.SERVER_EXECUTE_EXCEPTION);
        }
    }

    @ApiOperation("删除全部日志")
    @DeleteMapping("all")
    @SetLog(module = LogModuleEnums.LOG, operType = "删除全部日志")
    public ResponseData<Void> deleteAll() {
        if (logService.removeAll()) {
            return ResponseDataBuilder.builder().success(null);
        } else {
            return ResponseDataBuilder.builder().build(ResponseDataEnum.SERVER_EXECUTE_EXCEPTION);
        }
    }

}
