package top.liubaiblog.masterstudio.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import top.liubaiblog.masterstudio.domain.bo.SecurityUser;
import top.liubaiblog.masterstudio.domain.po.User;
import top.liubaiblog.masterstudio.service.UserService;

import java.util.Objects;

/**
 * @author 留白
 * @description
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername, username);
        User currentUser = userService.getOne(wrapper);
        if (Objects.isNull(currentUser)) {
            throw new UsernameNotFoundException("用户名错误");
        }
        return new SecurityUser(currentUser);
    }
}
