package top.liubaiblog.masterstudio.service;

import top.liubaiblog.masterstudio.domain.query.LoginQuery;
import top.liubaiblog.masterstudio.domain.query.forget.UpdatePwdByTempTokenQuery;
import top.liubaiblog.masterstudio.domain.query.forget.ValidateCodeQuery;
import top.liubaiblog.masterstudio.domain.vo.LoginVO;

/**
 * @author 留白
 * @description
 */
public interface LoginService {
    /**
     * 登录服务
     */
    LoginVO login(LoginQuery loginQuery);

    /**
     * 登出服务
     */
    void logout();

    /**
     * 向用户发送验证码邮件，之后返回一个临时token
     */
    String getCodeAfterBuildToken(String username);

    /**
     * 校验用户输入的验证码是否和之前发送的验证码一致
     */
    boolean validateCode(ValidateCodeQuery validateCodeQuery);

    /**
     * 根据临时令牌修改密码
     */
    boolean updatePwdByTempToken(UpdatePwdByTempTokenQuery updateQuery);
}
