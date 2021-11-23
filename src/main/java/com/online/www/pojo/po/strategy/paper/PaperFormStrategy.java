package com.online.www.pojo.po.strategy.paper;

import java.util.List;
import java.util.Map;

import com.online.www.pojo.po.Question;

/**
 * @author GoodBoyCoder
 * @date 2021-11-23
 */
public interface PaperFormStrategy {
    /**
     * 自动组卷
     *
     * @return 题目MAP（类型-题目集合）
     */
    Map<Integer, List<Question>> autoCreate();

    /**
     * 结果整合
     *
     * @return 集合
     */
    List<Question> createInOrderList();
}
