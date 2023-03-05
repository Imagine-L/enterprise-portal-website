package top.liubaiblog.masterstudiofront.service;

import top.liubaiblog.masterstudiofront.domain.po.PagePlate;
import com.baomidou.mybatisplus.extension.service.IService;
import top.liubaiblog.masterstudiofront.domain.vo.plate.PlateDetailVO;

import java.util.List;

/**
* @author 13326
* @description 针对表【ms_page_plate(页面板块表)】的数据库操作Service
* @createDate 2022-11-06 19:54:45
*/
public interface PagePlateService extends IService<PagePlate> {
    /**
     * 获取栏目绑定的页面板块 (这里不会检查栏目类型是否为页面栏目)
     */
    List<PlateDetailVO> getByNavBinding(Long nid);
}
