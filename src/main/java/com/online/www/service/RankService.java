package com.online.www.service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.online.www.mapper.ExamMapper;
import com.online.www.mapper.ExamUserMapper;
import com.online.www.pojo.po.Exam;
import com.online.www.pojo.po.ExamUser;
import org.springframework.stereotype.Service;

/**
 * @author GoodBoyCoder
 * @date 2021-12-07
 */
@Service
public class RankService {
    private static final Integer MAX_RANK = 50;
    private static final Map<Integer, List<ExamUser>> EXAM_RANK_MAP = new HashMap<>();

    @Resource
    private ExamUserMapper examUserMapper;
    @Resource
    private ExamMapper examMapper;

    public List<ExamUser> getExamRankList(Integer subjectId) {
        List<ExamUser> examUsers = EXAM_RANK_MAP.get(subjectId);
        if (Objects.isNull(examUsers)) {
            examUsers = new ArrayList<>();
            EXAM_RANK_MAP.put(subjectId, examUsers);
        }

        if (examUsers.isEmpty() || examUsers.size() < MAX_RANK) {
            synchronized (this) {
                if (examUsers.isEmpty() || examUsers.size() < MAX_RANK) {
                    //清空排名
                    examUsers.clear();
                    //获取前50排名
                    List<Exam> exams = examMapper.selectWithSubjectList(subjectId);
                    List<Integer> examIdList = exams.stream().map(Exam::getId).collect(Collectors.toList());
                    if (!examIdList.isEmpty()) {
                        List<ExamUser> examUserInExamId = examUserMapper.getExamUserInExamId(examIdList);
                        //筛选出每人最优秀的考试成绩记录
                        Map<Integer, ExamUser> examUserMap = examUserInExamId.stream().collect(
                                Collectors.toMap(ExamUser::getUserId, Function.identity(),
                                        (c1, c2) -> c1.getTotalScore() > c2.getTotalScore() ? c1 :
                                                (c1.getPassTime() < c2.getPassTime() ? c1 : c2))
                        );
                        examUsers.addAll(
                                examUserMap.values().stream()
                                        .sorted(Comparator.comparing(ExamUser::getTotalScore, Comparator.reverseOrder()).thenComparing(ExamUser::getPassTime))
                                        .limit(MAX_RANK)
                                        .collect(Collectors.toList()));
                    }
                }
            }
        }
        return examUsers;
    }

    /**
     * 刷新排名缓存
     *
     * @param subjectId 科目ID
     * @param toRefresh 待刷新考试信息
     */
    public void refreshExamRank(Integer subjectId, ExamUser toRefresh) {
        List<ExamUser> examRankList = getExamRankList(subjectId);
        int index = 0;
        synchronized (this) {
            for (ExamUser examUser : examRankList) {
                boolean needAdd = toRefresh.getTotalScore() > examUser.getTotalScore()
                        || ((toRefresh.getTotalScore().equals(examUser.getTotalScore()) && toRefresh.getPassTime() < examUser.getPassTime()));
                if (needAdd) {
                    examRankList.add(index, toRefresh);
                    break;
                }
                index++;
            }

            if (examRankList.size() >= MAX_RANK) {
                examRankList.remove(examRankList.size() - 1);
            }
        }
    }
}
