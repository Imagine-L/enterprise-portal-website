package top.liubaiblog.masterstudio.api.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import top.liubaiblog.masterstudio.common.annotation.SetLog;
import top.liubaiblog.masterstudio.domain.enums.LogModuleEnums;
import top.liubaiblog.masterstudio.domain.enums.ResponseDataEnum;
import top.liubaiblog.masterstudio.domain.query.plate.GetPlateQuery;
import top.liubaiblog.masterstudio.domain.query.plate.SavePlateQuery;
import top.liubaiblog.masterstudio.domain.query.plate.UpdatePlateQuery;
import top.liubaiblog.masterstudio.domain.vo.PageVO;
import top.liubaiblog.masterstudio.domain.vo.ResponseData;
import top.liubaiblog.masterstudio.domain.vo.plate.PlateDetailVO;
import top.liubaiblog.masterstudio.domain.vo.plate.PlatePartVO;
import top.liubaiblog.masterstudio.service.PagePlateService;
import top.liubaiblog.masterstudio.util.http.ResponseDataBuilder;

/**
 * @author 留白
 * @description
 */
@RestController
@Api(tags = "页面板块控制器")
@RequestMapping("/plate")
public class PagePlateController {

    @Autowired
    private PagePlateService pagePlateService;

    @PostMapping("/list")
    @ApiOperation("查询板块列表")
    public ResponseData<PageVO<PlatePartVO>> list(@RequestBody @Validated GetPlateQuery plateQuery) {
        PageVO<PlatePartVO> pages = pagePlateService.listByPlateQuery(plateQuery);
        return ResponseDataBuilder.builder().success(pages);
    }

    @GetMapping("/{pid}")
    @ApiOperation("获取板块详情")
    public ResponseData<PlateDetailVO> getById(@PathVariable("pid") Long pid) {
        PlateDetailVO plateDetailVO = pagePlateService.getById(pid);
        return ResponseDataBuilder.builder().success(plateDetailVO);
    }

    @PostMapping
    @ApiOperation("保存板块")
    @SetLog(module = LogModuleEnums.PAGE_PLATE, operType = "保存板块")
    public ResponseData<Void> save(@Validated SavePlateQuery plateQuery) {
        if (pagePlateService.saveByPlateQuery(plateQuery)) {
            return ResponseDataBuilder.builder().success(null);
        } else {
            return ResponseDataBuilder.builder().build(ResponseDataEnum.SERVER_EXECUTE_EXCEPTION);
        }
    }

    @PutMapping
    @ApiOperation("更新板块")
    @SetLog(module = LogModuleEnums.PAGE_PLATE, operType = "更新板块")
    public ResponseData<Void> update(@Validated UpdatePlateQuery plateQuery) {
        if (pagePlateService.updateByPlateQuery(plateQuery)) {
            return ResponseDataBuilder.builder().success(null);
        } else {
            return ResponseDataBuilder.builder().build(ResponseDataEnum.SERVER_EXECUTE_EXCEPTION);
        }
    }

    @DeleteMapping("/{pid}")
    @ApiOperation("删除板块")
    @SetLog(module = LogModuleEnums.PAGE_PLATE, operType = "删除板块")
    public ResponseData<Void> removeById(@PathVariable("pid") Long pid) {
        if (pagePlateService.removeById(pid)) {
            return ResponseDataBuilder.builder().success(null);
        } else {
            return ResponseDataBuilder.builder().build(ResponseDataEnum.SERVER_EXECUTE_EXCEPTION);
        }
    }

}
