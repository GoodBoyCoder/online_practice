package com.online.www.util;

import java.util.LinkedList;
import java.util.List;

import com.online.www.pojo.po.Question;

/**
 * @author GoodBoyCoder
 * @date 2021-11-23
 */
public class CollectionUtils {
    /**
     * 忽略原集合抽取给定数目的随机元素
     *
     * @param originList 带提取集合
     * @param number     提取数目
     * @return 提取集合
     */
    public static <T> List<T> getRandomIgnoreOrigin(List<T> originList, int number) {
        List<T> result = new LinkedList<>();
        for (int i = 0; i < number; i++) {
            if (originList.isEmpty()) {
                break;
            }
            T t = originList.remove((int) (Math.random() * originList.size()));
            result.add(t);
        }
        return result;
    }
}
