package com.dandinglong.controller;

import com.alibaba.fastjson.JSON;
import com.dandinglong.entity.Code2Session;
import com.dandinglong.entity.UserEntity;
import com.dandinglong.exception.WxException;
import com.dandinglong.service.ScanProgramService;
import com.dandinglong.service.UserDetailProcessorService;
import com.dandinglong.util.JsonResult;
import com.dandinglong.util.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
public class UserController {
    private Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private HttpServletRequest httpServletRequest;
    @Value("${uploadFileLocation}")
    private String uploadFileLocation;
    @Autowired
    private ScanProgramService scanProgramService;
    @Autowired
    private UserDetailProcessorService userDetailProcessorService;

    @RequestMapping("/user/onLogin")
    public JsonResult onLogin(@RequestParam("code") String code, @RequestParam(value = "orginal", required = false) Integer orginalUserId) throws IOException {
        if(orginalUserId==null){
            orginalUserId=0;
        }
        HttpSession session = httpServletRequest.getSession();
        Code2Session login = userDetailProcessorService.getOpenId(code);
        UserEntity userEntity = userDetailProcessorService.login(login.getOpenId(), orginalUserId);
        session.setAttribute("code2session", login);
        session.setAttribute("userEntity", userEntity);
        logger.info("userEntity=" + JSON.toJSONString(userEntity));
        Map<String, Object> result = new HashMap<>();
        result.put("session", session.getId());
        result.put("showWelcome", userEntity.getShowWelcome());
        result.put("freeScore", userEntity.getTodayUsedScore());
        result.put("userId", userEntity.getId());
        return ResultUtil.success(result);
    }

    @RequestMapping("/user/userInfo")
    public JsonResult userInfo(@RequestBody UserEntity userEntity) throws IOException {
        HttpSession session = httpServletRequest.getSession();
        UserEntity userEntitySession = (UserEntity) session.getAttribute("userEntity");
        if (userEntitySession == null) {
            throw new WxException("请先登录");
        }
        userEntitySession.setAvatarUrl(userEntity.getAvatarUrl());
        userEntitySession.setGender(userEntity.getGender());
        userEntitySession.setNickName(userEntity.getNickName());
        userEntitySession.setCountry(userEntity.getCountry());
        UserEntity userEntity1 = userDetailProcessorService.updateUserInfo(userEntitySession);
        logger.info("用户信息结果={}", userEntity1);
        return ResultUtil.success(userEntity1);
    }

    @RequestMapping("/user/sharePage")
    public JsonResult sharePage() {
        HttpSession session = httpServletRequest.getSession();
        UserEntity userEntitySession = (UserEntity) session.getAttribute("userEntity");
        return ResultUtil.success(userEntitySession);
    }

}
