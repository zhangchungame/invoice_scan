package com.dandinglong.controller;

import com.dandinglong.entity.UserEntity;
import com.dandinglong.mapper.UserMapper;
import com.dandinglong.util.JsonResult;
import com.dandinglong.util.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@RestController
public class WelcomeController {
    private Logger logger= LoggerFactory.getLogger(WelcomeController.class);
    @Autowired
    private UserMapper userMapper;
    @RequestMapping("/welcome")
    public JsonResult welcome(){
        List<String> res=new ArrayList<>();
        res.add("https://files.dxz3000.com/welcome/welcome1.png");
        res.add("https://files.dxz3000.com/welcome/welcome2.png");
        res.add("https://files.dxz3000.com/welcome/welcome3.png");
        res.add("https://files.dxz3000.com/welcome/welcome4.png");
        res.add("https://files.dxz3000.com/welcome/welcome5.png");
        res.add("https://files.dxz3000.com/welcome/welcome6.png");
        res.add("https://files.dxz3000.com/welcome/welcome7.png");
        res.add("https://files.dxz3000.com/welcome/welcome8.png");
        res.add("https://files.dxz3000.com/welcome/welcome9.png");
        return ResultUtil.success(res);
    }
    @RequestMapping("/welcome/welcomeNotShow")
    public JsonResult welcomeNotShow(HttpServletRequest request){
        HttpSession session=request.getSession();
        UserEntity userEntity=(UserEntity)session.getAttribute("userEntity");
        userEntity.setShowWelcome(0);
        logger.info("用户取消欢迎页 {}",userEntity);
        return ResultUtil.success(userMapper.updateByPrimaryKey(userEntity));
    }
}
