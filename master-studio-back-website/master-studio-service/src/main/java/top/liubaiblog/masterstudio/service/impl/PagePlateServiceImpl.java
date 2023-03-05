package top.liubaiblog.masterstudio.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import top.liubaiblog.masterstudio.common.exception.param.RequestParamsException;
import top.liubaiblog.masterstudio.dao.mapper.PagePlateMapper;
import top.liubaiblog.masterstudio.domain.enums.NavTypeEnums;
import top.liubaiblog.masterstudio.domain.enums.PlateTypeEnums;
import top.liubaiblog.masterstudio.domain.po.Navigation;
import top.liubaiblog.masterstudio.domain.po.PagePlate;
import top.liubaiblog.masterstudio.domain.po.User;
import top.liubaiblog.masterstudio.domain.query.plate.GetPlateQuery;
import top.liubaiblog.masterstudio.domain.query.plate.SavePlateQuery;
import top.liubaiblog.masterstudio.domain.query.plate.UpdatePlateQuery;
import top.liubaiblog.masterstudio.domain.vo.PageVO;
import top.liubaiblog.masterstudio.domain.vo.file.SaveFileVO;
import top.liubaiblog.masterstudio.domain.vo.plate.PlateDetailVO;
import top.liubaiblog.masterstudio.domain.vo.plate.PlatePartVO;
import top.liubaiblog.masterstudio.service.NavigationService;
import top.liubaiblog.masterstudio.service.PagePlateService;
import top.liubaiblog.masterstudio.service.UploadFileService;
import top.liubaiblog.masterstudio.service.UserService;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author 13326
 * @description 针对表【ms_page_plate(页面板块表)】的数据库操作Service实现
 * @createDate 2022-10-03 15:23:40
 */
@Slf4j
@Service
public class PagePlateServiceImpl extends ServiceImpl<PagePlateMapper, PagePlate>
        implements PagePlateService {

    @Autowired
    private NavigationService navigationService;

    @Autowired
    private UserService userService;

    @Autowired
    private UploadFileService fileService;

    @Override
    public List<PagePlate> getByNavBinding(Long nid) {
        LambdaQueryWrapper<PagePlate> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(PagePlate::getBindNav, nid);
        wrapper.orderByDesc(PagePlate::getReleased, PagePlate::getDisabled);
        return list(wrapper);
    }

    @Override
    public PageVO<PlatePartVO> listByPlateQuery(GetPlateQuery plateQuery) {
        // 条件封装
        LambdaQueryWrapper<PagePlate> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(plateQuery.getPlateName())) {
            wrapper.like(PagePlate::getPlateName, plateQuery.getPlateName());
        }
        if (!Objects.isNull(plateQuery.getPlateType())) {
            PlateTypeEnums type = PlateTypeEnums.getInstance(plateQuery.getPlateType());
            wrapper.eq(PagePlate::getPlateType, type.getCode());
        }
        if (!Objects.isNull(plateQuery.getBindNav())) {
            wrapper.eq(PagePlate::getBindNav, plateQuery.getBindNav());
        }
        wrapper.orderByAsc(PagePlate::getOrderSeed);
        wrapper.orderByDesc(PagePlate::getCreateTime);
        // 条件查询
        Page<PagePlate> platePage = new Page<>(plateQuery.getPageNo(), plateQuery.getPageSize());
        page(platePage, wrapper);
        // 封装返回值
        PageVO<PlatePartVO> pageVO = new PageVO<>();
        pageVO.setTotalRecords(platePage.getTotal());
        pageVO.setTotalPages(platePage.getPages());
        List<PagePlate> records = platePage.getRecords();
        List<PlatePartVO> plateParts = records.stream().map(plate -> {
            PlatePartVO platePartVO = new PlatePartVO();
            BeanUtils.copyProperties(plate, platePartVO);
            PlateTypeEnums plateType = PlateTypeEnums.getInstance(plate.getPlateType());
            platePartVO.setPlateType(plateType.getName());
            String bindNav = navigationService.getSingleNameById(plate.getBindNav());
            platePartVO.setBindNav(bindNav);
            return platePartVO;
        }).collect(Collectors.toList());
        pageVO.setRecords(plateParts);
        return pageVO;
    }

    @Override
    public PlateDetailVO getById(Long pid) {
        // 查询板块
        PagePlate pagePlate = getById((Serializable) pid);
        if (Objects.isNull(pagePlate)) {
            throw new RequestParamsException("无法查询到指定编号板块");
        }
        // 封装返回值
        PlateDetailVO plateDetailVO = new PlateDetailVO();
        BeanUtils.copyProperties(pagePlate, plateDetailVO);
        // 板块类型
        PlateTypeEnums plateType = PlateTypeEnums.getInstance(pagePlate.getPlateType());
        plateDetailVO.setPlateTypeName(plateType.getName());
        // 板块绑定的栏目名
        String bindNav = navigationService.getSingleNameById(plateDetailVO.getBindNav());
        plateDetailVO.setBindNavName(bindNav);
        // 创建者和更新者
        if (!Objects.isNull(pagePlate.getCreateBy())) {
            String createBy = userService.getSingleNameById(pagePlate.getCreateBy());
            plateDetailVO.setCreateBy(createBy);
        }
        if (!Objects.isNull(pagePlate.getUpdateBy())) {
            String updateBy = userService.getSingleNameById(pagePlate.getUpdateBy());
            plateDetailVO.setUpdateBy(updateBy);
        }
        return plateDetailVO;
    }

    @Override
    public boolean saveByPlateQuery(SavePlateQuery plateQuery) {
        // 判断绑定栏目是否存在，并且类型为页面类型
        Navigation nav = navigationService.exist(plateQuery.getBindNav());
        if (Objects.isNull(nav) || !Objects.equals(nav.getNavType(), NavTypeEnums.PAGE.getCode())) {
            throw new RequestParamsException("绑定的栏目不存在或绑定栏目非页面栏目");
        }
        // 获取登录用户
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();
        // 封装保存参数
        PagePlate pagePlate = new PagePlate();
        BeanUtils.copyProperties(plateQuery, pagePlate);
        // 保存板块图片
        MultipartFile image = plateQuery.getImage();
        SaveFileVO saveFile = fileService.save(image);
        pagePlate.setImage(saveFile.getNetworkPath());
        pagePlate.setCreateBy(currentUser.getUid());
        pagePlate.setCreateTime(new Date());
        pagePlate.setUpdateBy(currentUser.getUid());
        pagePlate.setUpdateTime(new Date());
        boolean result = save(pagePlate);
        if (!result) {
            return false;
        }
        // 如果这个板块未禁用并且是已发布的，则更新栏目的可用性
        if (!plateQuery.getDisabled() && plateQuery.getReleased()) {
            navigationService.adaptUsedById(pagePlate.getBindNav());
        }
        return true;
    }

    @Override
    public boolean updateByPlateQuery(UpdatePlateQuery plateQuery) {
        // 判断绑定栏目是否存在
        Navigation exist = navigationService.exist(plateQuery.getBindNav());
        if (Objects.isNull(exist)) {
            throw new RequestParamsException("无法查询到绑定的栏目");
        }
        // 获取登录用户
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();
        // 封装修改参数
        PagePlate pagePlate = new PagePlate();
        BeanUtils.copyProperties(plateQuery, pagePlate);
        pagePlate.setUpdateBy(currentUser.getUid());
        pagePlate.setUpdateTime(new Date());
        // 保存图片
        MultipartFile image = plateQuery.getImage();
        if (!Objects.isNull(image)) {
            SaveFileVO imageFile = fileService.save(image);
            pagePlate.setImage(imageFile.getNetworkPath());
        }
        // 更新
        boolean result = updateById(pagePlate);
        if (!result) {
            return false;
        }
        // 更新绑定栏目可用性
        navigationService.adaptUsedById(pagePlate.getBindNav());
        return true;
    }

    @Override
    public boolean removeById(Long pid) {
        // 获取板块信息
        LambdaQueryWrapper<PagePlate> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(PagePlate::getPid, PagePlate::getBindNav);
        wrapper.eq(PagePlate::getPid, pid);
        PagePlate removePlate = getOne(wrapper);
        if (Objects.isNull(removePlate)) {
            throw new RequestParamsException("要删除的栏目不存在");
        }
        // 删除板块
        boolean result = removeById((Serializable) pid);
        if (!result) {
            return false;
        }
        // 更新绑定栏目可用性
        navigationService.adaptUsedById(removePlate.getBindNav());
        return true;
    }
}




