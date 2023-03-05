package top.liubaiblog.masterstudio.service;

import com.baomidou.mybatisplus.extension.service.IService;
import top.liubaiblog.masterstudio.domain.po.User;
import top.liubaiblog.masterstudio.domain.query.user.*;
import top.liubaiblog.masterstudio.domain.vo.PageVO;
import top.liubaiblog.masterstudio.domain.vo.user.UserDetailVO;
import top.liubaiblog.masterstudio.domain.vo.user.UserPartVO;

import java.util.List;

/**
* @author 13326
* @description 针对表【ms_user(用户表)】的数据库操作Service
* @createDate 2022-10-03 15:23:40
*/
public interface UserService extends IService<User> {

    /**
     * 根据查询条件查询用户列表
     */
    PageVO<UserPartVO> listByUserQuery(GetUserQuery userQuery);

    /**
     * 根据条件保存用户信息
     */
    boolean saveByUserQuery(SaveUserQuery saveUserQuery);

    /**
     * 根据用户名判断用户是否已存在
     */
    boolean exist(String username);

    /**
     * 根据编号用去用户详细信息
     */
    UserDetailVO getById(Long uid);

    /**
     * 根据编号获取用户名
     */
    String getSingleNameById(Long uid);

    /**
     * 根据岗位获取用户
     */
    List<User> getByProfessionId(Long professionId);

    /**
     * 根据条件更新用户数据
     */
    boolean updateByUserQuery(UpdateUserQuery updateUserQuery);

    /**
     * 用户修改密码
     */
    boolean updatePwd(UpdateUserPwdQuery updateUserPwdQuery);

    /**
     * 根据编号删除用户
     */
    boolean removeById(Long uid);

    /**
     * 判断用户是否为admin用户
     */
    boolean isAdmin(Long uid);

    /**
     * 根据邮箱获取用户
     */
    User getByEmail(String email);

    /**
     * 判断该邮箱是否绑定了用户，返回重复的用户编号
     */
    String emailIsBinding(String email);

    /**
     * 获取用户名列表
     */
    List<UserPartVO> names();

    /**
     * 根据用户名获取用户信息
     */
    User getByUsername(String username);
}
