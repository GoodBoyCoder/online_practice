package com.online.www.controller;


import javax.annotation.Resource;

import com.online.www.pojo.bo.LoginBo;
import com.online.www.pojo.vo.LoginVo;
import com.online.www.result.CommonResult;
import com.online.www.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
@RequestMapping("/api/user")
public class UserController {
    @Resource
    private UserService userService;

    @ApiOperation(value = "登录",notes = "用户登录")
    @PostMapping("/login")
    public CommonResult<LoginVo> login(@Validated @RequestBody LoginBo loginBo) {
        return CommonResult.operateSuccess(userService.login(loginBo));
    }
}

