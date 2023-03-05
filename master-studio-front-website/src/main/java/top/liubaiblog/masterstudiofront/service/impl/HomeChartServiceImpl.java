package top.liubaiblog.masterstudiofront.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import top.liubaiblog.masterstudiofront.domain.po.HomeChart;
import top.liubaiblog.masterstudiofront.domain.vo.chart.HomeChartVO;
import top.liubaiblog.masterstudiofront.service.HomeChartService;
import top.liubaiblog.masterstudiofront.mapper.HomeChartMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
* @author 13326
* @description 针对表【ms_home_chart(栏目表)】的数据库操作Service实现
* @createDate 2022-11-06 19:54:44
*/
@Service
public class HomeChartServiceImpl extends ServiceImpl<HomeChartMapper, HomeChart>
    implements HomeChartService{

    @Override
    public List<HomeChartVO> listVO() {
        List<HomeChart> list = list();
        return list.stream().map(chart -> {
            HomeChartVO chartVO = new HomeChartVO();
            BeanUtils.copyProperties(chart, chartVO);
            return chartVO;
        }).collect(Collectors.toList());
    }
}




