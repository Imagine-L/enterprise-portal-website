package top.liubaiblog.masterstudiofront.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import top.liubaiblog.masterstudiofront.domain.po.PagePlate;
import top.liubaiblog.masterstudiofront.domain.vo.plate.PlateDetailVO;
import top.liubaiblog.masterstudiofront.service.PagePlateService;
import top.liubaiblog.masterstudiofront.mapper.PagePlateMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 13326
 * @description 针对表【ms_page_plate(页面板块表)】的数据库操作Service实现
 * @createDate 2022-11-06 19:54:45
 */
@Service
public class PagePlateServiceImpl extends ServiceImpl<PagePlateMapper, PagePlate>
        implements PagePlateService {

    @Override
    public List<PlateDetailVO> getByNavBinding(Long nid) {
        LambdaQueryWrapper<PagePlate> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(PagePlate::getPid,
                PagePlate::getPlateName,
                PagePlate::getDescription,
                PagePlate::getImage,
                PagePlate::getLink,
                PagePlate::getPlateType);
        // 条件封装
        wrapper.eq(PagePlate::getBindNav, nid);
        wrapper.eq(PagePlate::getReleased, true);
        wrapper.eq(PagePlate::getDisabled, false);
        wrapper.orderByAsc(PagePlate::getOrderSeed);
        // 查询
        List<PagePlate> list = list(wrapper);
        // 返回值封装
        return list.stream().map(plate -> {
            PlateDetailVO plateDetailVO = new PlateDetailVO();
            BeanUtils.copyProperties(plate, plateDetailVO);
            return plateDetailVO;
        }).collect(Collectors.toList());
    }

}




