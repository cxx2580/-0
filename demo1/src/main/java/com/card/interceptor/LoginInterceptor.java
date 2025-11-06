package com.card.interceptor;

import org.springframework.stereotype.Component; // 新增注解
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

// 新增@Component注解，让Spring识别为Bean
@Component
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        // 检查session中是否有登录用户信息
        if (session.getAttribute("loginUser") == null) {
            // 未登录，重定向到登录页
            response.sendRedirect(request.getContextPath() + "/user/login");
            return false;
        }
        return true;
    }
}