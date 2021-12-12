package com.online.www.controller;

import javax.annotation.Resource;
import java.util.List;

import com.online.www.annotation.TokenRequired;
import com.online.www.pojo.vo.AnalysisVo;
import com.online.www.result.CommonResult;
import com.online.www.service.UserQuestionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p >
 *
 * @author yellow
 * @since 2021-12-11
 */
@Api(tags = "用户做题")
@RestController
@RequestMapping("/api/userQuestion")
public class UserQuestionController extends BaseController{

    @Resource
    private UserQuestionService userQuestionService;

    @ApiOperation(value = "分析用户做题情况")
    @TokenRequired
    @GetMapping("/analysis")
    public CommonResult<List<AnalysisVo>> userQuestionAnalysis(){
        return CommonResult.operateSuccess(userQuestionService.userQuestionAnalysis(getUserId()));
    }
}