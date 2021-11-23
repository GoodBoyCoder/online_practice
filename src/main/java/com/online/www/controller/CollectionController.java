package com.online.www.controller;

import javax.annotation.Resource;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.online.www.annotation.TokenRequired;
import com.online.www.pojo.bo.UserStarBo;
import com.online.www.pojo.vo.QuestionVo;
import com.online.www.result.CommonResult;
import com.online.www.service.UserStarService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author Lenovo
 */

@Api(tags = "试题收藏")
@RestController
@RequestMapping("/api/collection")
public class CollectionController extends BaseController {
    @Resource
    private UserStarService userStarService;

    @ApiOperation(value = "题目收藏", notes = "根据用户需要收藏一道题目")
    @TokenRequired
    @PostMapping("/star")
    public CommonResult<Boolean> collectionQuestion(@RequestBody @Validated UserStarBo userStarBo) {
        userStarBo.setUserId(getUserId());
        return CommonResult.autoResult(userStarService.collectionQuestion(userStarBo));
    }

    @ApiOperation(value = "收藏题目分页展示", notes = "分页展示用户已经收藏的题目")
    @TokenRequired
    @GetMapping("/starQuestions")
    public CommonResult<Page<QuestionVo>> selectStarQuestions(
            @RequestParam(name = "currentPage", required = false, defaultValue = "1") Integer currentPage,
            @RequestParam(name = "size", required = false, defaultValue = "10") Integer size) {
        return CommonResult.operateSuccess(userStarService.getStarQuestion(getUserId(), currentPage, size));
    }

    @ApiOperation(value = "移出收藏", notes ="将当前题目移出收藏表")
    @TokenRequired
    @PostMapping("/deleteStar")
    public CommonResult<Boolean> deleteStar(@RequestBody @Validated UserStarBo userStarBo){
        userStarBo.setUserId(getUserId());
        return CommonResult.autoResult(userStarService.deleteStar(userStarBo));
    }
}
