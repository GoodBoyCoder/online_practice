package com.online.www.pojo.po.strategy;

/**
 * @author GoodBoy
 * @date 2021-11-22
 */
public interface Strategy {
    /**
     * 判题算法接口抽象
     *
     * @param answer 题目答案
     * @param reply  作答结果
     * @return 判题结果true/false
     */
    boolean judge(String answer, String reply);
}
