package com.ssm.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MyInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestUrl = request.getRequestURI();
        if(requestUrl.contains(".css") || requestUrl.contains(".jsp")||requestUrl.contains(".js")||requestUrl.contains(".ico")||requestUrl.contains(".map")||requestUrl.contains(".ttf")||requestUrl.contains(".woff")||requestUrl.contains(".org")){
            return true;
        }
        if (request.getSession().getAttribute("user") == null) {
            response.sendRedirect("/Management/user/user-login");
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
