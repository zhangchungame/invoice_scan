package com.dandinglong.task;

import com.dandinglong.service.AipOcrClientSelector;
import com.dandinglong.service.UserDetailProcessorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class MidnightTask {
    private Logger logger= LoggerFactory.getLogger(MidnightTask.class);
    @Autowired
    private AipOcrClientSelector aipOcrClientSelector;
    @Autowired
    private UserDetailProcessorService userDetailProcessorService;
    @Scheduled(cron = "59 59 23 * * ?")
    public void initBaiduAppInfo(){
        logger.info("initBaiduAppInfo start");
        aipOcrClientSelector.usedTimesZeroing();
        logger.info("initBaiduAppInfo end");
    }
//    @Scheduled(cron = "0 0 0 * * ?")
    public void initUserScore(){
        userDetailProcessorService.recoverUserFreeScore();
    }
}
