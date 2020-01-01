package com.dandinglong.controller;

import com.dandinglong.entity.UserEntity;
import com.dandinglong.mapper.UserMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
public class TestController {
    @Autowired
    private UserMapper userMapper;
    @RequestMapping("/test")
    public PageInfo test(){
        UserEntity userEntity=new UserEntity();
        userEntity.setOpenId(UUID.randomUUID().toString());
        userEntity.setRegisterTime(new Date());
        userEntity.setLastLoginTime(new Date());
        userMapper.insert(userEntity);
        List<UserEntity> userEntities = userMapper.selectAll();
        userMapper.updateLastLoginTime(userEntities.get(0));
        PageHelper.startPage(0, 10);
        // 紧跟着的第一个select方法会被分页
        List<UserEntity> list = userMapper.selectAll();
        PageInfo pageInfo = new PageInfo<>(list);
        //获取总记录数
        int total = (int) pageInfo.getTotal();
        return pageInfo;
    }
}
