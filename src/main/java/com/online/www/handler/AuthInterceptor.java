package com.online.www.handler;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

import com.alibaba.druid.util.StringUtils;
import com.online.www.annotation.TokenRequired;
import com.online.www.config.SecurityProperties;
import com.online.www.exception.AuthException;
import com.online.www.util.JwtUtil;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * @author GoodBoyCoder
 * @date 2021-11-19
 */
public class AuthInterceptor implements HandlerInterceptor {
    @Resource
    private SecurityProperties securityProperties;
    @Resource
    private JwtUtil jwtUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //非方法映射
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();

        if (method.isAnnotationPresent(TokenRequired.class)) {
            //请求提获取token
            String token = request.getHeader(securityProperties.getTokenHeader());
            if (StringUtils.isEmpty(token)) {
                throw new AuthException("无有效token，请重新登录");
            }

            //token验证
            String userId = (String) jwtUtil.verifierToken(token);
            if (StringUtils.isEmpty(userId)) {
                throw new AuthException("无效的token令牌，请重新登录");
            }

            //将userID绑定到请求
            request.setAttribute("userId", userId);
            return true;
        }
        return true;
    }
}
