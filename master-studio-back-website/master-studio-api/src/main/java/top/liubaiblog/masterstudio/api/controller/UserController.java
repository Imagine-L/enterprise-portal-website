package top.liubaiblog.masterstudio.api.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import top.liubaiblog.masterstudio.common.annotation.SetLog;
import top.liubaiblog.masterstudio.domain.enums.LogModuleEnums;
import top.liubaiblog.masterstudio.domain.query.user.*;
import top.liubaiblog.masterstudio.domain.vo.*;
import top.liubaiblog.masterstudio.domain.enums.ResponseDataEnum;
import top.liubaiblog.masterstudio.domain.vo.user.UserDetailVO;
import top.liubaiblog.masterstudio.domain.vo.user.UserPartVO;
import top.liubaiblog.masterstudio.service.UserService;
import top.liubaiblog.masterstudio.util.http.ResponseDataBuilder;

import java.util.List;

/**
 * @author 留白
 * @description
 */
@Api(tags = "用户管理控制器")
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/name/{username}")
    @ApiOperation("判断用户名是否存在")
    public ResponseData<CheckRepeatVO> usernameExist(@PathVariable("username") String username) {
        boolean exist = userService.exist(username);
        return ResponseDataBuilder.builder().success(new CheckRepeatVO(exist));
    }

    @PostMapping("/email/{email}")
    @ApiOperation("判断邮箱是否已经绑定了用户")
    public ResponseData<String> emailIsBinding(@PathVariable("email") String email) {
        String uid = userService.emailIsBinding(email);
        return ResponseDataBuilder.builder().success(uid);
    }

    @GetMapping("/names")
    @ApiOperation("获取用户名列表")
    public ResponseData<List<UserPartVO>> names() {
        List<UserPartVO> names = userService.names();
        return ResponseDataBuilder.builder().success(names);
    }

    @GetMapping("/{uid}")
    @ApiOperation("获取用户详细信息")
    public ResponseData<UserDetailVO> getById(@PathVariable("uid") Long uid) {
        UserDetailVO userDetail = userService.getById(uid);
        return ResponseDataBuilder.builder().success(userDetail);
    }

    @PostMapping("/list")
    @ApiOperation("获取用户列表")
    public ResponseData<PageVO<UserPartVO>> list(@RequestBody @Validated GetUserQuery userQuery) {
        PageVO<UserPartVO> users = userService.listByUserQuery(userQuery);
        return ResponseDataBuilder.builder().success(users);
    }

    @PostMapping
    @ApiOperation("保存用户")
    @SetLog(module = LogModuleEnums.USER, operType = "保存用户")
    public ResponseData<Void> save(@RequestBody @Validated SaveUserQuery saveUserQuery) {
        if (userService.saveByUserQuery(saveUserQuery)) {
            return ResponseDataBuilder.builder().success(null);
        } else {
            return ResponseDataBuilder.builder().build(ResponseDataEnum.SERVER_EXECUTE_EXCEPTION);
        }
    }

    @PutMapping
    @ApiOperation("修改用户")
    @SetLog(module = LogModuleEnums.USER, operType = "修改用户")
    public ResponseData<Void> update(@RequestBody @Validated UpdateUserQuery updateUserQuery) {
        if (userService.updateByUserQuery(updateUserQuery)) {
            return ResponseDataBuilder.builder().success(null);
        } else {
            return ResponseDataBuilder.builder().build(ResponseDataEnum.SERVER_EXECUTE_EXCEPTION);
        }
    }

    @PutMapping("/pwd")
    @ApiOperation("修改用户密码")
    @SetLog(module = LogModuleEnums.USER, operType = "修改密码")
    public ResponseData<Void> updatePwd(@RequestBody @Validated UpdateUserPwdQuery updateUserPwdQuery) {
        if (userService.updatePwd(updateUserPwdQuery)) {
            return ResponseDataBuilder.builder().success(null);
        } else {
            return ResponseDataBuilder.builder().build(ResponseDataEnum.SERVER_EXECUTE_EXCEPTION);
        }
    }

    @DeleteMapping("/{uid}")
    @ApiOperation("删除用户")
    @SetLog(module = LogModuleEnums.USER, operType = "删除用户")
    public ResponseData<Void> delete(@PathVariable("uid") Long uid) {
        if (userService.removeById(uid)) {
            return ResponseDataBuilder.builder().success(null);
        } else {
            return ResponseDataBuilder.builder().build(ResponseDataEnum.SERVER_EXECUTE_EXCEPTION);
        }
    }

}
