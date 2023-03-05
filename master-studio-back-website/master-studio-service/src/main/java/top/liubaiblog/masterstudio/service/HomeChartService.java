package top.liubaiblog.masterstudio.service;

import com.baomidou.mybatisplus.extension.service.IService;
import top.liubaiblog.masterstudio.domain.po.HomeChart;
import top.liubaiblog.masterstudio.domain.query.chart.SaveHomeChartQuery;
import top.liubaiblog.masterstudio.domain.query.chart.UpdateHomeChartQuery;
import top.liubaiblog.masterstudio.domain.vo.chart.HomeChartDetailVO;

import java.util.List;

/**
* @author 13326
* @description 针对表【ms_home_chart(栏目表)】的数据库操作Service
* @createDate 2022-10-03 15:23:40
*/
public interface HomeChartService extends IService<HomeChart> {

    /**
     * 获取轮播图详情列表
     */
    List<HomeChartDetailVO> listDetail();

    /**
     * 根据条件保存轮播图
     */
    boolean saveByChartQuery(SaveHomeChartQuery homeChartQuery);

    /**
     * 根据条件更新轮播图
     */
    boolean updateByChartQuery(UpdateHomeChartQuery homeChartQuery);
}
