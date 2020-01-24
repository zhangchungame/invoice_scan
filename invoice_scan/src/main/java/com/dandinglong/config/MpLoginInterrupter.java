package com.dandinglong.config;

import com.dandinglong.entity.UserEntity;
import com.dandinglong.exception.WxException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class MpLoginInterrupter implements HandlerInterceptor {
    private Logger logger= LoggerFactory.getLogger(MpLoginInterrupter.class);
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session=request.getSession();
        UserEntity userEntity=(UserEntity)session.getAttribute("userEntity");
        if(userEntity==null){
            logger.error("未登录");
            throw new WxException("未登录");
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

//        System.out.println("postHandle");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

//        System.out.println("afterCompletion");
    }
}
