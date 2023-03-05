package top.liubaiblog.masterstudio.api.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import top.liubaiblog.masterstudio.domain.query.LoginQuery;
import top.liubaiblog.masterstudio.domain.vo.LoginVO;
import top.liubaiblog.masterstudio.domain.vo.ResponseData;
import top.liubaiblog.masterstudio.service.LoginService;
import top.liubaiblog.masterstudio.util.http.ResponseDataBuilder;

/**
 * @author 留白
 * @description
 */
@RestController
@Api(tags = "登录控制器")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping("/login")
    @ApiOperation("登录操作")
    public ResponseData<LoginVO> login(@RequestBody @Validated LoginQuery loginQuery) {
        LoginVO loginVO = loginService.login(loginQuery);
        return ResponseDataBuilder.builder().success(loginVO);
    }

    @GetMapping("/logout")
    @ApiOperation("登出操作")
    public ResponseData<Void> logout() {
        loginService.logout();
        return ResponseDataBuilder.builder().success(null);
    }
}
