package top.liubaiblog.masterstudiofront.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.liubaiblog.masterstudiofront.domain.vo.ResponseData;
import top.liubaiblog.masterstudiofront.domain.vo.chart.HomeChartVO;
import top.liubaiblog.masterstudiofront.service.HomeChartService;
import top.liubaiblog.masterstudiofront.util.http.ResponseDataBuilder;

import java.util.List;

/**
 * @author 留白
 * @description
 */
@RestController
@Api(tags = "轮播图控制器")
@RequestMapping("/chart")
public class HomeChartController {

    @Autowired
    private HomeChartService homeChartService;

    @GetMapping("/list")
    @ApiOperation("获取轮播图列表")
    public ResponseData<List<HomeChartVO>> list() {
        List<HomeChartVO> list = homeChartService.listVO();
        return ResponseDataBuilder.builder().success(list);
    }

}
