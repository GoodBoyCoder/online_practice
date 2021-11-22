package com.online.www.mapper;

import java.util.List;
import java.util.Objects;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.online.www.pojo.bo.QuestionSelectBo;
import com.online.www.pojo.po.Question;
import com.online.www.pojo.po.UserStar;
import com.online.www.pojo.vo.QuestionVo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.util.CollectionUtils;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author online
 * @since 2021-11-19
 */
@Mapper
public interface QuestionMapper extends BaseMapper<Question> {
    /**
     * 获取满足条件的题目
     *
     * @param bo               查询条件
     * @param questionDoneList 剔除在外的题目ID
     * @return 随机题目
     */
    default List<Question> selectQuestions(QuestionSelectBo bo, List<Long> questionDoneList) {
        QueryWrapper<Question> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .select(Question::getId, Question::getQuestion, Question::getQuestionOptions, Question::getPic,
                        Question::getQuestionType, Question::getMark, Question::getChapter, Question::getSubjectId,
                        Question::getRemark)
                .eq(Objects.nonNull(bo.getSubjectId()), Question::getSubjectId, bo.getSubjectId())
                .eq(Objects.nonNull(bo.getType()), Question::getQuestionType, bo.getType())
                .isNotNull(Objects.nonNull(bo.getNeedPic()) && bo.getNeedPic(), Question::getPic)
                .like(Objects.nonNull(bo.getChapter()), Question::getChapter, bo.getChapter())
                .notIn(!CollectionUtils.isEmpty(questionDoneList), Question::getId, questionDoneList);
        return selectList(queryWrapper);
    }

    /**
     * 分页获取被收藏题目
     *
     * @param userStarList 被收藏题目
     * @param currentPage 当前页
     * @param size 页大小
     * @return Page<Question>
     */
    default Page<Question> selectQuestions(List<UserStar> userStarList, Integer currentPage, Integer size){
        QueryWrapper<Question> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .select(Question::getId, Question::getQuestion, Question::getQuestionOptions, Question::getPic,
                        Question::getQuestionType, Question::getMark, Question::getChapter, Question::getSubjectId,
                        Question::getRemark)
                .in(Question::getId,userStarList);
        return selectPage(new Page<>(currentPage, size), queryWrapper);
    }
}
