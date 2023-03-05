package top.liubaiblog.masterstudio.api.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import top.liubaiblog.masterstudio.common.annotation.SetLog;
import top.liubaiblog.masterstudio.domain.enums.LogModuleEnums;
import top.liubaiblog.masterstudio.domain.enums.ResponseDataEnum;
import top.liubaiblog.masterstudio.domain.po.HomeChart;
import top.liubaiblog.masterstudio.domain.query.chart.SaveHomeChartQuery;
import top.liubaiblog.masterstudio.domain.query.chart.UpdateHomeChartQuery;
import top.liubaiblog.masterstudio.domain.vo.ResponseData;
import top.liubaiblog.masterstudio.domain.vo.chart.HomeChartDetailVO;
import top.liubaiblog.masterstudio.service.HomeChartService;
import top.liubaiblog.masterstudio.util.http.ResponseDataBuilder;

import java.util.List;

/**
 * @author 留白
 * @description
 */
@RestController
@Api(tags = "首页轮播图控制器")
@RequestMapping("/chart")
public class HomeChartController {

    @Autowired
    private HomeChartService homeChartService;

    @ApiOperation("获取轮播图列表")
    @GetMapping("/list")
    public ResponseData<List<HomeChartDetailVO>> list() {
        List<HomeChartDetailVO> homeCharts = homeChartService.listDetail();
        return ResponseDataBuilder.builder().success(homeCharts);
    }

    @ApiOperation("保存轮播图")
    @PostMapping
    @SetLog(module = LogModuleEnums.HOME_CHART, operType = "保存轮播图")
    public ResponseData<Void> save(@Validated SaveHomeChartQuery homeChartQuery) {
        if (homeChartService.saveByChartQuery(homeChartQuery)) {
            return ResponseDataBuilder.builder().success(null);
        } else {
            return ResponseDataBuilder.builder().build(ResponseDataEnum.SERVER_EXECUTE_EXCEPTION);
        }
    }

    @ApiOperation("修改轮播图")
    @PutMapping
    @SetLog(module = LogModuleEnums.HOME_CHART, operType = "修改轮播图")
    public ResponseData<Void> update(@Validated UpdateHomeChartQuery homeChartQuery) {
        if (homeChartService.updateByChartQuery(homeChartQuery)) {
            return ResponseDataBuilder.builder().success(null);
        } else {
            return ResponseDataBuilder.builder().build(ResponseDataEnum.SERVER_EXECUTE_EXCEPTION);
        }
    }

    @ApiOperation("删除轮播图")
    @DeleteMapping("/{hid}")
    @SetLog(module = LogModuleEnums.HOME_CHART, operType = "删除轮播图")
    public ResponseData<Void> delete(@PathVariable("hid") Long hid) {
        if (homeChartService.removeById(hid)) {
            return ResponseDataBuilder.builder().success(null);
        } else {
            return ResponseDataBuilder.builder().build(ResponseDataEnum.SERVER_EXECUTE_EXCEPTION);
        }
    }

}
