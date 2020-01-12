package com.dandinglong.task;

import com.dandinglong.mapper.BaiduAppInfoMapper;
import com.dandinglong.service.AipOcrClientSelector;
import com.dandinglong.service.UserScoreProcessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class MidnightTask {
    @Autowired
    private AipOcrClientSelector aipOcrClientSelector;
    @Autowired
    private UserScoreProcessorService userScoreProcessorService;
    @Scheduled(cron = "0 0 0 * * ?")
    public void initBaiduAppInfo(){
        aipOcrClientSelector.usedTimesZeroing();
    }
    @Scheduled(cron = "0 0 0 * * ?")
    public void initUserScore(){
        userScoreProcessorService.recoverUserFreeScore();
    }
}
