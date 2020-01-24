package com.dandinglong.controller;

import com.alibaba.fastjson.JSON;
import com.dandinglong.dto.UploadFileConsumerMessageDto;
import com.dandinglong.dto.UploadFileProducerMessageDto;
import com.dandinglong.exception.WxException;
import com.dandinglong.model.MessageBrokerComponent;
import com.dandinglong.service.OcrResultDealService;
import com.dandinglong.util.JsonResult;
import com.dandinglong.util.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageBrokerController extends  BaseController {
    private Logger logger= LoggerFactory.getLogger(MessageBrokerController.class);
    @Autowired
    private MessageBrokerComponent messageBrokerComponent;
    @Autowired
    private OcrResultDealService ocrResultDealService;

    @RequestMapping("/ocr/getOcrTask")
    public JsonResult getOcrTask(){
        UploadFileProducerMessageDto uploadFileProducerMessageDto = messageBrokerComponent.queuePoll();
        if(uploadFileProducerMessageDto==null){
            return ResultUtil.fail(new WxException("没有数据"));
        }
        logger.info("文件被请求了，准备处理 {}", JSON.toJSONString(uploadFileProducerMessageDto));
        return ResultUtil.success(uploadFileProducerMessageDto);
    }


    @RequestMapping("/ocr/feedBackOcrTask")
    public JsonResult feedBackOcrTask(@RequestBody UploadFileConsumerMessageDto uploadFileConsumerMessageDto){
        logger.info("接受到处理完的消息 {}", JSON.toJSONString(uploadFileConsumerMessageDto));
        ocrResultDealService.ocrDeal(uploadFileConsumerMessageDto);
        return ResultUtil.success(1);
    }
}
