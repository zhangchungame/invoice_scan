package com.dandinglong.controller;

import com.alibaba.fastjson.JSON;
import com.dandinglong.entity.UploadFileEntity;
import com.dandinglong.model.MessageBrokerComponent;
import com.dandinglong.util.JsonResult;
import com.dandinglong.util.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageBrokerController extends  BaseController {
    private Logger logger= LoggerFactory.getLogger(MessageBrokerController.class);
    @Autowired
    private MessageBrokerComponent messageBrokerComponent;

    @RequestMapping("/ocr/getOcrTask")
    public JsonResult getOcrTask(){
        UploadFileEntity uploadFileEntity = messageBrokerComponent.queuePoll();
        logger.info("文件被请求了，准备处理 {}", JSON.toJSONString(uploadFileEntity));
        return ResultUtil.success(uploadFileEntity);
    }
}
