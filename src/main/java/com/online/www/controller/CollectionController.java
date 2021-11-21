package com.online.www.controller;

import com.online.www.annotation.TokenRequired;
import com.online.www.pojo.bo.UserStarBo;
import com.online.www.result.CommonResult;
import com.online.www.service.UserStarService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;

/**
 * @author Lenovo
 */
@Validated
@Api(tags = "试题收藏")
@RestController
@RequestMapping("/api/collection")
public class CollectionController {
    @Resource
    private UserStarService userStarService;

    @ApiOperation(value = "题目收藏", notes = "根据用户需要收藏一道题目")
    @TokenRequired
    @PostMapping("/star")
    public CommonResult<Boolean> collectionQuestion(@RequestAttribute("userId") @ApiIgnore Integer userId,
                                                    @RequestBody @Validated UserStarBo userStarBo) {
        userStarBo.setUserId(userId);
        return CommonResult.autoResult(userStarService.collectionQuestion(userStarBo));
    }
}
