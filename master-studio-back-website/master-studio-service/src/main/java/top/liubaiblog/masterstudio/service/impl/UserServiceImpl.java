package top.liubaiblog.masterstudio.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import top.liubaiblog.masterstudio.common.exception.auth.AccessPermissionException;
import top.liubaiblog.masterstudio.common.exception.param.RequestParamsException;
import top.liubaiblog.masterstudio.dao.mapper.UserMapper;
import top.liubaiblog.masterstudio.domain.po.User;
import top.liubaiblog.masterstudio.domain.query.user.*;
import top.liubaiblog.masterstudio.domain.vo.PageVO;
import top.liubaiblog.masterstudio.domain.vo.user.UserDetailVO;
import top.liubaiblog.masterstudio.domain.vo.user.UserPartVO;
import top.liubaiblog.masterstudio.domain.enums.UserGenderEnums;
import top.liubaiblog.masterstudio.service.ProfessionService;
import top.liubaiblog.masterstudio.service.UserService;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author 13326
 * @description 针对表【ms_user(用户表)】的数据库操作Service实现
 * @createDate 2022-10-03 15:23:40
 */
@Service
@Transactional
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ProfessionService professionService;

    @Override
    public PageVO<UserPartVO> listByUserQuery(GetUserQuery userQuery) {
        // 条件封装
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasLength(userQuery.getUsername())) {
            wrapper.like(User::getUsername, userQuery.getUsername());
        }
        if (StringUtils.hasLength(userQuery.getNickname())) {
            wrapper.like(User::getNickname, userQuery.getNickname());
        }
        if (StringUtils.hasLength(userQuery.getGender())) {
            userQuery.setGender("0".equals(userQuery.getGender()) ? "0" : "1");
            wrapper.eq(User::getGender, userQuery.getGender());
        }
        wrapper.orderByDesc(User::getCreateTime);
        Page<User> userPage = new Page<>(userQuery.getPageNo(), userQuery.getPageSize());
        // 根据条件查询
        this.page(userPage, wrapper);
        // 封装返回对象
        PageVO<UserPartVO> userPartPages = new PageVO<>();
        userPartPages.setTotalPages(userPage.getPages());
        userPartPages.setTotalRecords(userPage.getTotal());
        List<User> users = userPage.getRecords();
        List<UserPartVO> userParts = users.stream().map(user -> {
            UserPartVO userPartVO = new UserPartVO();
            BeanUtils.copyProperties(user, userPartVO);
            return userPartVO;
        }).collect(Collectors.toList());
        userPartPages.setRecords(userParts);
        return userPartPages;
    }

    @Override
    public boolean saveByUserQuery(SaveUserQuery saveUserQuery) {
        // 获取当前登录用户信息
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();
        // 判断是否有请求的岗位
        Long pid = saveUserQuery.getProfessionId();
        if (!professionService.exist(pid)) {
            throw new RequestParamsException("保存用户对应岗位不存在");
        }
        // 判断用户名是否重复
        if (exist(saveUserQuery.getUsername())) {
            throw new RequestParamsException("用户名已重复");
        }
        // 判断邮箱是否已被绑定
        String emailBindingUid = emailIsBinding(saveUserQuery.getEmail());
        if (StringUtils.hasLength(emailBindingUid)) {
            throw new RequestParamsException("邮箱已被绑定");
        }
        // 封装新用户对象
        User newUser = new User();
        BeanUtils.copyProperties(saveUserQuery, newUser);
        newUser.setPassword(passwordEncoder.encode(saveUserQuery.getPassword()));
        newUser.setAllowDel(true);
        newUser.setCreateBy(currentUser.getUid());
        newUser.setCreateTime(new Date());
        newUser.setUpdateBy(currentUser.getUid());
        newUser.setUpdateTime(new Date());
        return save(newUser);
    }

    @Override
    public boolean exist(String username) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername, username);
        long count = count(wrapper);
        return count > 0;
    }

    @Override
    public UserDetailVO getById(Long uid) {
        User user = getById((Serializable) uid);
        if (Objects.isNull(user)) {
            throw new RequestParamsException("用户编号错误");
        }
        UserDetailVO userDetail = new UserDetailVO();
        BeanUtils.copyProperties(user, userDetail);
        // 获取该用户的性别
        UserGenderEnums genderEnums = UserGenderEnums.getInstance(user.getGender());
        userDetail.setGender(genderEnums.getName());
        // 获取该用户的岗位名
        Long professionId = user.getProfessionId();
        String professionName = professionService.getSingleNameById(professionId);
        userDetail.setProfessionName(professionName);
        // 获取创建者名字
        if (!Objects.isNull(user.getCreateBy())) {
            String createBy = getSingleNameById(user.getCreateBy());
            userDetail.setCreateBy(createBy);
        }
        // 获取修改者的名字
        if (!Objects.isNull(user.getUpdateBy())) {
            String updateBy = getSingleNameById(user.getUpdateBy());
            userDetail.setUpdateBy(updateBy);
        }
        return userDetail;
    }

    @Override
    public String getSingleNameById(Long uid) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(User::getUsername);
        wrapper.eq(User::getUid, uid);
        User user = getOne(wrapper);
        return user.getUsername();
    }

    @Override
    public List<User> getByProfessionId(Long professionId) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(User::getUid);
        wrapper.eq(User::getProfessionId, professionId);
        return list(wrapper);
    }

    @Override
    public boolean updateByUserQuery(UpdateUserQuery updateUserQuery) {
        // 获取当前登录用户
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();
        // 判断该用户岗位是否存在
        Long pid = updateUserQuery.getProfessionId();
        if (!professionService.exist(pid)) {
            throw new RequestParamsException("保存用户对应岗位不存在");
        }
        // 判断邮箱是否已被绑定
        String emailBindingUid = emailIsBinding(updateUserQuery.getEmail());
        if (StringUtils.hasLength(emailBindingUid) &&
                !Objects.equals(String.valueOf(updateUserQuery.getUid()), emailBindingUid)) {
            throw new RequestParamsException("邮箱已被绑定");
        }
        // 判断是否需要锁定用户，如果是管理员用户，则不允许锁定
        if (updateUserQuery.getLocked()) {
            if (isAdmin(updateUserQuery.getUid())) {
                updateUserQuery.setLocked(false);
            }
        }
        // 封装存储对象
        User user = new User();
        BeanUtils.copyProperties(updateUserQuery, user);
        user.setUpdateBy(currentUser.getUid());
        user.setUpdateTime(new Date());
        // 执行更新操作
        return updateById(user);
    }

    @Override
    public boolean updatePwd(UpdateUserPwdQuery updateUserPwdQuery) {
        // 获取当前登录用户
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();
        // 判断改用户是否允许修改密码
        if (!Objects.equals(currentUser.getUid(), updateUserPwdQuery.getUid())
                && !isAdmin(currentUser.getUid())) {
            throw new AccessPermissionException("用户无权限修改密码");
        }
        // 获取该用户
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(User::getUid, User::getPassword);
        queryWrapper.eq(User::getUid, updateUserPwdQuery.getUid());
        User updateUser = getOne(queryWrapper);
        // 校验原密码
        boolean matches = passwordEncoder.matches(updateUserPwdQuery.getOldPwd(), updateUser.getPassword());
        if (!matches) {
            throw new RequestParamsException("用户原密码错误");
        }
        // 更新密码
        String newPwd = passwordEncoder.encode(updateUserPwdQuery.getNewPwd());
        updateUser.setPassword(newPwd);
        updateUser.setUpdateBy(currentUser.getUid());
        updateUser.setUpdateTime(new Date());
        return updateById(updateUser);
    }

    @Override
    public boolean removeById(Long uid) {
        // 判断该用户是否允许删除
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(User::getUid, User::getAllowDel);
        wrapper.eq(User::getUid, uid);
        User removeUser = getOne(wrapper);
        // 判断是否允许删除
        if (removeUser.getAllowDel()) {
            return removeById((Serializable) uid);
        } else {
            throw new RequestParamsException("该用户不允许删除");
        }
    }

    @Override
    public boolean isAdmin(Long uid) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(User::getUid, User::getAdmin);
        wrapper.eq(User::getUid, uid);
        User user = getOne(wrapper);
        return user.getAdmin();
    }

    @Override
    public User getByEmail(String email) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(User::getUid, User::getEmail);
        wrapper.eq(User::getEmail, email);
        return getOne(wrapper);
    }

    @Override
    public String emailIsBinding(String email) {
        User emailBindingUser = getByEmail(email);
        return Objects.isNull(emailBindingUser) ||
                Objects.isNull(emailBindingUser.getUid()) ? "" : emailBindingUser.getUid() + "";
    }

    @Override
    public List<UserPartVO> names() {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(User::getUid, User::getUsername);
        List<User> list = list(wrapper);
        return list.stream().map(user -> {
            UserPartVO userPartVO = new UserPartVO();
            BeanUtils.copyProperties(user, userPartVO);
            return userPartVO;
        }).collect(Collectors.toList());
    }

    @Override
    public User getByUsername(String username) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(User::getUid, User::getUsername, User::getEmail);
        wrapper.eq(User::getUsername, username);
        return getOne(wrapper);
    }
}




