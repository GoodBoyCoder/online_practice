package com.online.www.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.online.www.exception.AuthException;

/**
 * @author GoodBoyCoder
 * @date 2021-11-22
 */
public class BaseController {
    @Resource
    protected HttpServletRequest request;

    protected Integer getUserId() {
        String userId = (String) request.getAttribute("userId");
        if (null == userId) {
            throw new AuthException("用户可能未登录，需要登录后才能执行操作");
        }
        return Integer.parseInt(userId);
    }
}
