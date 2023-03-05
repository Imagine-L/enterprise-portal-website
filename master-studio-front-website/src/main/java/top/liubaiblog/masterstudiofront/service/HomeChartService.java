package top.liubaiblog.masterstudiofront.service;

import top.liubaiblog.masterstudiofront.domain.po.HomeChart;
import com.baomidou.mybatisplus.extension.service.IService;
import top.liubaiblog.masterstudiofront.domain.vo.chart.HomeChartVO;

import java.util.List;

/**
* @author 13326
* @description 针对表【ms_home_chart(栏目表)】的数据库操作Service
* @createDate 2022-11-06 19:54:44
*/
public interface HomeChartService extends IService<HomeChart> {

    /**
     * 获取轮播图列表
     */
    List<HomeChartVO> listVO();

}
