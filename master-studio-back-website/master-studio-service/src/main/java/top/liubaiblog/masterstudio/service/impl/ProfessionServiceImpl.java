package top.liubaiblog.masterstudio.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import top.liubaiblog.masterstudio.common.exception.param.RequestParamsException;
import top.liubaiblog.masterstudio.dao.mapper.ProfessionMapper;
import top.liubaiblog.masterstudio.domain.po.Profession;
import top.liubaiblog.masterstudio.domain.po.User;
import top.liubaiblog.masterstudio.domain.query.profession.GetProfessionQuery;
import top.liubaiblog.masterstudio.domain.query.profession.SaveProfessionQuery;
import top.liubaiblog.masterstudio.domain.query.profession.UpdateProfessionQuery;
import top.liubaiblog.masterstudio.domain.vo.PageVO;
import top.liubaiblog.masterstudio.domain.vo.profession.ProfessionDetailVO;
import top.liubaiblog.masterstudio.domain.vo.profession.ProfessionPartVO;
import top.liubaiblog.masterstudio.service.ProfessionService;
import top.liubaiblog.masterstudio.service.UserService;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author 13326
 * @description 针对表【ms_profession(岗位表)】的数据库操作Service实现
 * @createDate 2022-10-03 15:23:40
 */
@Service
@Transactional
public class ProfessionServiceImpl extends ServiceImpl<ProfessionMapper, Profession>
        implements ProfessionService {

    @Autowired
    @Lazy   // 由于userService也依赖的本类，所以本类延迟加载userService以解决循环依赖问题
    private UserService userService;

    @Override
    public boolean exist(Long pid) {
        LambdaQueryWrapper<Profession> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Profession::getPid, pid);
        long count = count(wrapper);
        return count > 0;
    }

    @Override
    public String exist(String name) {
        LambdaQueryWrapper<Profession> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(Profession::getPid);
        wrapper.eq(Profession::getName, name);
        Profession profession = getOne(wrapper);
        return Objects.isNull(profession) || Objects.isNull(profession.getPid()) ? "" : profession.getPid() + "";
    }

    @Override
    public String getSingleNameById(Long pid) {
        LambdaQueryWrapper<Profession> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(Profession::getName);
        wrapper.eq(Profession::getPid, pid);
        Profession profession = getOne(wrapper);
        return profession.getName();
    }

    @Override
    public List<ProfessionPartVO> names() {
        LambdaQueryWrapper<Profession> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(Profession::getPid, Profession::getName);
        List<Profession> list = list(wrapper);
        return list.stream().map(profession -> {
            ProfessionPartVO professionNameVO = new ProfessionPartVO();
            BeanUtils.copyProperties(profession, professionNameVO);
            return professionNameVO;
        }).collect(Collectors.toList());
    }

    @Override
    public PageVO<ProfessionPartVO> listByProfessionQuery(GetProfessionQuery professionQuery) {
        // 条件封装
        LambdaQueryWrapper<Profession> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(Profession::getPid, Profession::getName, Profession::getDescription, Profession::getAllowDel);
        wrapper.orderByDesc(Profession::getCreateTime);
        if (!Objects.isNull(professionQuery.getPid())) {
            wrapper.like(Profession::getPid, professionQuery.getPid());
        }
        if (StringUtils.hasLength(professionQuery.getName())) {
            wrapper.like(Profession::getName, professionQuery.getName());
        }
        if (StringUtils.hasLength(professionQuery.getDescription())) {
            wrapper.like(Profession::getDescription, professionQuery.getDescription());
        }
        // 条件查询
        Page<Profession> proPage = new Page<>(professionQuery.getPageNo(), professionQuery.getPageSize());
        page(proPage, wrapper);
        // 封装返回对象
        PageVO<ProfessionPartVO> pageVO = new PageVO<>();
        pageVO.setTotalRecords(proPage.getTotal());
        pageVO.setTotalPages(proPage.getPages());
        List<Profession> professions = proPage.getRecords();
        List<ProfessionPartVO> professionPartVOs = professions.stream().map(profession -> {
            ProfessionPartVO professionNameVO = new ProfessionPartVO();
            BeanUtils.copyProperties(profession, professionNameVO);
            return professionNameVO;
        }).collect(Collectors.toList());
        pageVO.setRecords(professionPartVOs);
        return pageVO;
    }

    @Override
    public ProfessionDetailVO getById(Long pid) {
        Profession profession = getById((Serializable) pid);
        if (Objects.isNull(profession)) {
            throw new RequestParamsException("岗位编号错误");
        }
        ProfessionDetailVO professionDetailVO = new ProfessionDetailVO();
        BeanUtils.copyProperties(profession, professionDetailVO);
        // 获取创建者
        if (!Objects.isNull(profession.getCreateBy())) {
            String createBy = userService.getSingleNameById(profession.getCreateBy());
            professionDetailVO.setCreateBy(createBy);
        }
        // 获取修改者
        if (!Objects.isNull(profession.getUpdateBy())) {
            String updateBy = userService.getSingleNameById(profession.getUpdateBy());
            professionDetailVO.setUpdateBy(updateBy);
        }
        return professionDetailVO;
    }

    @Override
    public boolean saveByProfessionQuery(SaveProfessionQuery saveProfessionQuery) {
        // 获取当前登录的用户
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();
        // 判断名字没有重复
        if (StringUtils.hasLength(exist(saveProfessionQuery.getName()))) {
            throw new RequestParamsException("岗位名已重复");
        }
        // 封装保存对象
        Profession profession = new Profession();
        BeanUtils.copyProperties(saveProfessionQuery, profession);
        profession.setCreateBy(currentUser.getUid());
        profession.setCreateTime(new Date());
        profession.setUpdateBy(currentUser.getUid());
        profession.setUpdateTime(new Date());
        // 保存返回
        return save(profession);
    }

    @Override
    public boolean updateByProfessionQuery(UpdateProfessionQuery updateProfessionQuery) {
        // 获取当前登录用户
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();
        // 判断名字没有重复
        String pid = exist(updateProfessionQuery.getName());
        if (StringUtils.hasLength(pid) && !Objects.equals(updateProfessionQuery.getPid() + "", pid)) {
            throw new RequestParamsException("岗位名已重复");
        }
        // 封装参数
        Profession profession = new Profession();
        BeanUtils.copyProperties(updateProfessionQuery, profession);
        profession.setUpdateBy(currentUser.getUid());
        profession.setUpdateTime(new Date());
        // 修改返回
        return updateById(profession);
    }

    @Override
    public boolean removeById(Long pid) {
        // 获取该岗位信息
        LambdaQueryWrapper<Profession> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(Profession::getPid, Profession::getAllowDel);
        wrapper.eq(Profession::getPid, pid);
        Profession profession = getOne(wrapper);
        if (Objects.isNull(profession)) {
            throw new RequestParamsException("删除的岗位不存在");
        }
        // 判断岗位是否可删除
        if (!profession.getAllowDel()) {
            throw new RequestParamsException("该岗位不可删除");
        }
        // 判断该岗位下是否绑定了其他用户
        List<User> professionBindingUsers = userService.getByProfessionId(pid);
        if (!professionBindingUsers.isEmpty()) {
            throw new RequestParamsException("请先取消该岗位与其他用户的关联");
        }
        return removeById((Serializable) pid);
    }
}




