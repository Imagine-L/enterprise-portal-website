package top.liubaiblog.masterstudio.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import top.liubaiblog.masterstudio.dao.mapper.HomeChartMapper;
import top.liubaiblog.masterstudio.domain.po.HomeChart;
import top.liubaiblog.masterstudio.domain.po.User;
import top.liubaiblog.masterstudio.domain.query.chart.SaveHomeChartQuery;
import top.liubaiblog.masterstudio.domain.query.chart.UpdateHomeChartQuery;
import top.liubaiblog.masterstudio.domain.vo.chart.HomeChartDetailVO;
import top.liubaiblog.masterstudio.domain.vo.file.SaveFileVO;
import top.liubaiblog.masterstudio.service.HomeChartService;
import top.liubaiblog.masterstudio.service.UploadFileService;
import top.liubaiblog.masterstudio.service.UserService;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
* @author 13326
* @description 针对表【ms_home_chart(栏目表)】的数据库操作Service实现
* @createDate 2022-10-03 15:23:40
*/
@Transactional
@Service
public class HomeChartServiceImpl extends ServiceImpl<HomeChartMapper, HomeChart>
    implements HomeChartService {

    @Autowired
    private UserService userService;

    @Autowired
    private UploadFileService uploadFileService;

    @Override
    public List<HomeChartDetailVO> listDetail() {
        List<HomeChart> charts = list();
        return charts.stream().map(chart -> {
            HomeChartDetailVO chartVO = new HomeChartDetailVO();
            BeanUtils.copyProperties(chart, chartVO);
            if (!Objects.isNull(chart.getCreateBy())) {
                String createBy = userService.getSingleNameById(chart.getCreateBy());
                chartVO.setCreateBy(createBy);
            }
            if (!Objects.isNull(chart.getUpdateBy())) {
                String updateBy = userService.getSingleNameById(chart.getUpdateBy());
                chartVO.setUpdateBy(updateBy);
            }
            return chartVO;
        }).collect(Collectors.toList());
    }

    @Override
    public boolean saveByChartQuery(SaveHomeChartQuery homeChartQuery) {
        // 获取当前登录用户
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();
        // 保存图片
        SaveFileVO file = uploadFileService.save(homeChartQuery.getImage());
        // 存储入数据库
        HomeChart homeChart = new HomeChart();
        homeChart.setImage(file.getNetworkPath());
        homeChart.setLink(homeChartQuery.getLink());
        homeChart.setCreateBy(currentUser.getUid());
        homeChart.setCreateTime(new Date());
        homeChart.setUpdateBy(currentUser.getUid());
        homeChart.setCreateTime(new Date());
        return save(homeChart);
    }

    @Override
    public boolean updateByChartQuery(UpdateHomeChartQuery homeChartQuery) {
        // 获取当前登录用户
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();
        // 封装修改参数
        HomeChart homeChart = new HomeChart();
        BeanUtils.copyProperties(homeChartQuery, homeChart);
        // 保存图片
        MultipartFile imageFile = homeChartQuery.getImage();
        if (!Objects.isNull(imageFile)) {
            SaveFileVO file = uploadFileService.save(imageFile);
            homeChart.setImage(file.getNetworkPath());
        }
        homeChart.setUpdateBy(currentUser.getUid());
        homeChart.setCreateTime(new Date());
        return updateById(homeChart);
    }
}




