package com.online.www.controller;


import javax.annotation.Resource;

import com.online.www.annotation.TokenRequired;
import com.online.www.pojo.bo.CreateExamBo;
import com.online.www.pojo.bo.ExamCommitBo;
import com.online.www.pojo.po.Exam;
import com.online.www.pojo.vo.ExamWithQuestionVo;
import com.online.www.pojo.vo.QuestionJudgeVo;
import com.online.www.result.CommonResult;
import com.online.www.service.ExamQuestionService;
import com.online.www.service.ExamService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @Resource
    private ExamQuestionService examQuestionService;

    @ApiOperation(value = "自动组卷")
    @TokenRequired
    @PostMapping("/autoCreateExam")
    public CommonResult<ExamWithQuestionVo> autoCreateExam(@Validated @RequestBody CreateExamBo createExamBo) {
        return CommonResult.operateSuccess(examService.autoCreateExam(createExamBo, getUserId()));
    }

    @ApiOperation(value = "考试判题")
    @TokenRequired
    @PostMapping("/examJudge")
    public CommonResult<List<QuestionJudgeVo>> examJudge(@Validated @RequestBody ExamCommitBo examCommitBo) {
        // 保存考试信息
        Exam exam = examService.saveExam(examCommitBo, getUserId());
        // 判题并保存
        return CommonResult.operateSuccess(examQuestionService.examQuestionJudge(examCommitBo.getQuestionJudgeBoList(), getUserId(), exam.getId()));
    }
}

