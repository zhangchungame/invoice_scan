package com.dandinglong.controller;

import com.dandinglong.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
public class BaseController {
    @Autowired
    private HttpServletRequest httpServletRequest;
    @Autowired
    private HttpSession httpSession;
    public UserEntity getUserEntity(){
        UserEntity userEntity=(UserEntity)httpSession.getAttribute("userEntity");
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity){
        httpSession.setAttribute("userEntity",userEntity);
    }
}
