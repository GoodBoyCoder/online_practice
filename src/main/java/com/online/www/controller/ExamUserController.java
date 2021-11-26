package com.online.www.controller;

import com.online.www.annotation.TokenRequired;
import com.online.www.pojo.vo.AnalysisVo;
import com.online.www.result.CommonResult;
import com.online.www.service.ExamUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author spice
 * @date 2021/11/26 13:28
 */
@Api(tags = "用户考试管理")
@RestController
@RequestMapping("/api/examUser")
public class ExamUserController extends BaseController {

    @Resource
    private ExamUserService examUserService;

    @ApiOperation(value = "考试分析", notes = "分析用户的考试情况预测出各科的考试通过率")
    @TokenRequired
    @GetMapping("/analysisExam")
    public CommonResult<List<AnalysisVo>> analysisExam() {
        return CommonResult.operateSuccess(examUserService.analysisExam(getUserId()));
    }
}
