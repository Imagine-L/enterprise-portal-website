package top.liubaiblog.masterstudio.api.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import top.liubaiblog.masterstudio.common.annotation.SetLog;
import top.liubaiblog.masterstudio.domain.enums.LogModuleEnums;
import top.liubaiblog.masterstudio.domain.query.forget.UpdatePwdByTempTokenQuery;
import top.liubaiblog.masterstudio.domain.query.forget.ValidateCodeQuery;
import top.liubaiblog.masterstudio.domain.vo.ResponseData;
import top.liubaiblog.masterstudio.service.LoginService;
import top.liubaiblog.masterstudio.util.http.ResponseDataBuilder;

import java.util.Objects;

/**
 * @author 留白
 * @description
 */
@RestController
@RequestMapping("/forget")
@Api(tags = "忘记密码控制器")
public class ForgetPasswordController {

    @Autowired
    private LoginService loginService;

    @GetMapping("/{username}")
    @ApiOperation("获取验证码")
    public ResponseData<String> sendCode(@PathVariable("username") String username) {
        String tempToken = loginService.getCodeAfterBuildToken(username);
        if (Objects.isNull(tempToken)) {
            return ResponseDataBuilder.builder().fail("A0201", "用户账户不存在");
        }
        return ResponseDataBuilder.builder().success(tempToken);
    }

    @PostMapping
    @ApiOperation("判断验证码是否输入正确")
    public ResponseData<Boolean> validateCode(@RequestBody @Validated ValidateCodeQuery validateCodeQuery) {
        if (loginService.validateCode(validateCodeQuery)) {
            return ResponseDataBuilder.builder().success(true);
        } else {
            return ResponseDataBuilder.builder().fail("A0132", "邮件校验码输入错误");
        }
    }

    @PutMapping
    @ApiOperation("通过临时令牌修改密码")
    @SetLog(module = LogModuleEnums.FORGET_PASSWORD, operType = "修改密码")
    public ResponseData<Void> updatePwdByTempToken(@RequestBody @Validated UpdatePwdByTempTokenQuery updateQuery) {
        if (loginService.updatePwdByTempToken(updateQuery)) {
            return ResponseDataBuilder.builder().success(null);
        } else {
            return ResponseDataBuilder.builder().fail("A0001", "临时令牌非法");
        }
    }
}
