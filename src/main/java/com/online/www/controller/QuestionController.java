package com.online.www.controller;


import javax.annotation.Resource;

import com.online.www.annotation.TokenRequired;
import com.online.www.pojo.bo.QuestionSelectBo;
import com.online.www.pojo.vo.QuestionVo;
import com.online.www.result.CommonResult;
import com.online.www.service.QuestionService;
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
@Api(tags = "题目管理")
@RestController
@RequestMapping("/api/question")
public class QuestionController extends BaseController {
    @Resource
    private QuestionService questionService;

    @ApiOperation(value = "随机题目获取", notes = "根据条件从题库随机获取一道题目")
    @TokenRequired
    @PostMapping("/getRandomQuestion")
    public CommonResult<QuestionVo> getRandomQuestion(@Validated @RequestBody QuestionSelectBo selectBo) {
        return CommonResult.operateSuccess(questionService.getRandomQuestion(selectBo, getUserId()));
    }
}

