package com.danidnglong.scheduer;

import com.danidnglong.service.OcrProcessService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class PollByHttpSchedler {
    private Logger logger= LoggerFactory.getLogger(PollByHttpSchedler.class);

    @Autowired
    private OcrProcessService ocrProcessService;
    @Scheduled(fixedRate =500L )
    public void getMessageUploadFile(){
        ocrProcessService.ocrProcess();
    }
}
