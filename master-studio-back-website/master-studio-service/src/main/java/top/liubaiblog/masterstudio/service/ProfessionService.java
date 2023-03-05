package top.liubaiblog.masterstudio.service;

import com.baomidou.mybatisplus.extension.service.IService;
import top.liubaiblog.masterstudio.domain.po.Profession;
import top.liubaiblog.masterstudio.domain.query.profession.GetProfessionQuery;
import top.liubaiblog.masterstudio.domain.query.profession.SaveProfessionQuery;
import top.liubaiblog.masterstudio.domain.query.profession.UpdateProfessionQuery;
import top.liubaiblog.masterstudio.domain.vo.PageVO;
import top.liubaiblog.masterstudio.domain.vo.profession.ProfessionDetailVO;
import top.liubaiblog.masterstudio.domain.vo.profession.ProfessionPartVO;

import java.util.List;

/**
* @author 13326
* @description 针对表【ms_profession(岗位表)】的数据库操作Service
* @createDate 2022-10-03 15:23:40
*/
public interface ProfessionService extends IService<Profession> {

    /**
     * 判断指定pid的岗位是否存在
     */
    boolean exist(Long pid);

    /**
     * 判断岗位名是否存在
     */
    String exist(String name);

    /**
     * 根据pid获取岗位名
     */
    String getSingleNameById(Long pid);

    /**
     * 获取岗位名称列表
     */
    List<ProfessionPartVO> names();

    /**
     * 获取岗位列表
     */
    PageVO<ProfessionPartVO> listByProfessionQuery(GetProfessionQuery professionQuery);

    /**
     * 根据编号获取岗位详细信息
     */
    ProfessionDetailVO getById(Long pid);

    /**
     * 根据参数保存岗位
     */
    boolean saveByProfessionQuery(SaveProfessionQuery saveProfessionQuery);

    /**
     * 根据参数修改岗位
     */
    boolean updateByProfessionQuery(UpdateProfessionQuery updateProfessionQuery);

    /**
     * 根据编号删除岗位
     */
    boolean removeById(Long pid);
}
