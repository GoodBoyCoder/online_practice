package com.online.www.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.online.www.mapper.ExamMapper;
import com.online.www.mapper.ExamUserMapper;
import com.online.www.mapper.SubjectMapper;
import com.online.www.pojo.po.Exam;
import com.online.www.pojo.po.ExamUser;
import com.online.www.pojo.vo.AnalysisVo;
import com.online.www.service.ExamUserService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author online
 * @since 2021-11-19
 */
@Service
public class ExamUserServiceImpl extends ServiceImpl<ExamUserMapper, ExamUser> implements ExamUserService {

    @Resource
    private ExamUserMapper examUserMapper;

    @Resource
    private ExamMapper examMapper;

    @Resource
    private SubjectMapper subjectMapper;

    @Override
    public List<AnalysisVo> analysisExam(Integer userId) {
        List<ExamUser> examUserList = examUserMapper.selectByUserId(userId);
        if (CollectionUtils.isEmpty(examUserList)) {
            return Collections.emptyList();
        }

        // 考试及格情况
        Map<Integer, Boolean> examResultMap = examUserList.stream()
                .collect(Collectors.toMap(ExamUser::getExamId, ExamUser::isPassing));

        // 按照科目将考试分组
        Map<Integer, List<Exam>> examMap = examMapper
                .selectInExamIdList(examUserList.stream().map(ExamUser::getExamId).collect(Collectors.toList()))
                .stream()
                .collect(Collectors.groupingBy(Exam::getSubjectId));

        List<AnalysisVo> analysisVoList = new LinkedList<>();
        for (Map.Entry<Integer, List<Exam>> entry : examMap.entrySet()) {
            AnalysisVo analysisVo = new AnalysisVo();
            analysisVo.setSubjectId(entry.getKey());
            analysisVo.setSubjectName(subjectMapper.selectById(entry.getKey()).getSubjectName());

            List<Exam> examList = entry.getValue();
            int passingCount = 0;
            for (Exam exam : examList) {
                if (examResultMap.get(exam.getId())) {
                    passingCount++;
                }
            }
            analysisVo.setRate((double) passingCount / examList.size());

            analysisVoList.add(analysisVo);
        }

        return analysisVoList;
    }
}
