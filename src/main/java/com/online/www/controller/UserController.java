package com.online.www.controller;


import javax.annotation.Resource;
import javax.validation.constraints.NotEmpty;

import com.online.www.annotation.TokenRequired;
import com.online.www.pojo.bo.LoginBo;
import com.online.www.pojo.bo.SignUpBo;
import com.online.www.pojo.vo.LoginVo;
import com.online.www.pojo.vo.UserVo;
import com.online.www.result.CommonResult;
import com.online.www.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author online
 * @since 2021-11-19
 */
@Api(tags = "用户管理")
@RestController
@Validated
@RequestMapping("/api/user")
public class UserController extends BaseController {
    @Resource
    private UserService userService;

    @ApiOperation(value = "登录", notes = "用户登录")
    @PostMapping("/login")
    public CommonResult<LoginVo> login(@Validated @RequestBody LoginBo loginBo) {
        return CommonResult.operateSuccess(userService.login(loginBo));
    }

    @ApiOperation(value = "注册", notes = "用户注册")
    @PostMapping("/register")
    public CommonResult<LoginVo> register(@Validated @RequestBody SignUpBo signUpBo) {
        return CommonResult.operateSuccess(userService.signUp(signUpBo));
    }

    @ApiOperation(value = "用户名验证")
    @GetMapping("/checkUserName")
    public CommonResult<Boolean> checkUserName(@RequestParam(value = "userName")
                                               @NotEmpty(message = "校验用户名需要非空") String userName) {
        return CommonResult.operateSuccess(userService.checkUserName(userName));
    }

    @ApiOperation(value = "获取用户信息")
    @TokenRequired
    @GetMapping("/getUserInfo")
    public CommonResult<UserVo> getUserInfo() {
        return CommonResult.operateSuccess(userService.getUserInfo(getUserId()));
    }

}

