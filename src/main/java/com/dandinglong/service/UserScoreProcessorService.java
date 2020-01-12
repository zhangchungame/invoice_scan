package com.dandinglong.service;

import com.dandinglong.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class UserScoreProcessorService {
    @Autowired
    private UserMapper userMapper;
    @Value("${score.invoice.percommitscore}")
    private int invoicepercommitscore;
    public boolean divAndCheckScore(int userId,String type) {
        int percommitScore=0;
        switch (type){
            case "invoice":
            default:
                percommitScore=invoicepercommitscore;
        }
        if(userMapper.consumScore(percommitScore,userId)==1){
            return true;
        }else{
            return false;
        }
    }

    /**
     * 每日恢复用户积分
     */
    public int recoverUserFreeScore(){
        return userMapper.recoverUserFreeScore();
    }
}
