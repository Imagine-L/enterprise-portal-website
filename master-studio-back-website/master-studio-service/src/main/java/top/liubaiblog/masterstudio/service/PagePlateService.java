package top.liubaiblog.masterstudio.service;

import com.baomidou.mybatisplus.extension.service.IService;
import top.liubaiblog.masterstudio.domain.po.PagePlate;
import top.liubaiblog.masterstudio.domain.query.plate.GetPlateQuery;
import top.liubaiblog.masterstudio.domain.query.plate.SavePlateQuery;
import top.liubaiblog.masterstudio.domain.query.plate.UpdatePlateQuery;
import top.liubaiblog.masterstudio.domain.vo.PageVO;
import top.liubaiblog.masterstudio.domain.vo.plate.PlateDetailVO;
import top.liubaiblog.masterstudio.domain.vo.plate.PlatePartVO;

import java.util.List;

/**
* @author 13326
* @description 针对表【ms_page_plate(页面板块表)】的数据库操作Service
* @createDate 2022-10-03 15:23:40
*/
public interface PagePlateService extends IService<PagePlate> {

    /**
     * 获取栏目绑定的页面板块 (这里不会检查栏目类型是否为页面栏目)
     */
    List<PagePlate> getByNavBinding(Long nid);

    /**
     * 根据条件查询页面板块列表
     */
    PageVO<PlatePartVO> listByPlateQuery(GetPlateQuery plateQuery);

    /**
     * 查询板块详情
     */
    PlateDetailVO getById(Long pid);

    /**
     * 保存板块
     */
    boolean saveByPlateQuery(SavePlateQuery plateQuery);

    /**
     * 更新板块
     */
    boolean updateByPlateQuery(UpdatePlateQuery plateQuery);

    /**
     * 根据编号删除板块
     */
    boolean removeById(Long pid);
}
