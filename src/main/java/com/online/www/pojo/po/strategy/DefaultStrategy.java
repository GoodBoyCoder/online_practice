package com.online.www.pojo.po.strategy;

/**
 * 默认判题策略
 *
 * @author GoodBoyCoder
 * @date 2021-11-22
 */
public class DefaultStrategy implements Strategy {
    @Override
    public boolean judge(String answer, String reply) {
        return answer.equals(reply);
    }
}
