package com.logho.interceptor;

import com.logho.constants.UserContants;
import com.logho.pojo.User;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BlogAdminInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        User user = (User) request.getSession().getAttribute(UserContants.LOGINED_USER);
        if (user == null) {
            request.setAttribute("message","请先登录！");
            request.getRequestDispatcher("/admin").forward(request,response);
            return false;
        }
        return true;
    }
}
