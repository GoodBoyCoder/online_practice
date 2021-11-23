package com.online.www.controller;


import javax.annotation.Resource;

import com.online.www.annotation.TokenRequired;
import com.online.www.pojo.bo.CreateExamBo;
import com.online.www.pojo.bo.LoginBo;
import com.online.www.pojo.vo.ExamWithQuestionVo;
import com.online.www.pojo.vo.LoginVo;
import com.online.www.result.CommonResult;
import com.online.www.service.ExamService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author online
 * @since 2021-11-19
 */
@Api(tags = "考试管理")
@RestController
@RequestMapping("/api/exam")
public class ExamController extends BaseController {
    @Resource
    private ExamService examService;

    @ApiOperation(value = "自动组卷")
    @TokenRequired
    @PostMapping("/autoCreateExam")
    public CommonResult<ExamWithQuestionVo> autoCreateExam(@Validated @RequestBody CreateExamBo createExamBo) {
        return CommonResult.operateSuccess(examService.autoCreateExam(createExamBo, getUserId()));
    }
}

