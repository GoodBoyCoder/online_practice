package com.online.www.pojo.po.strategy;

/**
 * 多选题判题策略
 *
 * @author GoodBoyCoder
 * @date 2021-11-22
 */
public class MultiStrategy implements Strategy{

    @Override
    public boolean judge(String answer, String reply) {
        if (answer.length() != reply.length()) {
            return false;
        }
        int[] counter = new int[256];
        for (char c : answer.toCharArray()) {
            counter[c]++;
        }

        for (char c : reply.toCharArray()) {
            if (--counter[c] < 0) {
                return false;
            }
        }
        return true;
    }
}
