package com.dandinglong.service;

import com.daidu.aip.ocr.MyAipOcr;
import com.dandinglong.dto.UploadFileProducerMessageDto;
import com.dandinglong.entity.UploadFileEntity;
import com.dandinglong.entity.UserEntity;
import com.dandinglong.enums.ImgTypeEnum;
import com.dandinglong.exception.WxException;
import com.dandinglong.mapper.UploadFileMapper;
import com.dandinglong.model.MessageBrokerComponent;
import com.dandinglong.util.DateFormaterUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class UploadService {
    private Logger logger= LoggerFactory.getLogger(UploadService.class);
    @Autowired
    private UploadFileMapper uploadFileMapper;
    @Autowired
    private  UserDetailProcessorService userDetailProcessorService;
    @Autowired
    private BatchProcessService batchProcessService;

    @Autowired
    private MessageBrokerComponent messageBrokerComponent;
    @Autowired
    private AipOcrClientSelector aipOcrClientSelector;


    /**
     * 失败文件重新上传
     */
    @Transactional
    public boolean reUpload(String imageUrl,int uploadId,int userId){
        UploadFileEntity uploadFileEntity = uploadFileMapper.selectByPrimaryKey(uploadId);
        if(uploadFileEntity==null || userId!=uploadFileEntity.getUserId()){
            throw new WxException("批次错误");
        }
        if(!DateFormaterUtil.YMDformater.get().format(uploadFileEntity.getInsertTime()).equals(DateFormaterUtil.YMDformater.get().format(new Date()))){
            throw new WxException("只能更改当天的图片");
        }
        uploadFileEntity.setFileName(imageUrl);
        uploadFileEntity.setStep(0);
        uploadFileEntity.setUpdateTime(new Date());
        MyAipOcr client = aipOcrClientSelector.getAvailableInvoiceClient(aipOcrClientSelector.getProcessTimes(1));

        UploadFileProducerMessageDto uploadFileProducerMessageDto =new UploadFileProducerMessageDto(uploadFileEntity,client.getToken());
        messageBrokerComponent.queueOffer(uploadFileProducerMessageDto);
        uploadFileMapper.updateByPrimaryKey(uploadFileEntity);
        batchProcessService.divFailNum(DateFormaterUtil.YMDformater.get().format(uploadFileEntity.getInsertTime()),uploadFileEntity.getUserId(),uploadFileEntity.getType());

        return true;
    }
    /**
     * 七牛新上传文件   记录数据库
     * @param imageUrl
     * @param type
     * @param userId
     * @return
     */
    public UserEntity newQiNiuUploaded(String imageUrl, String type, int userId){
        logger.info("收到上传到七牛的文件    userId={}     imageUrl={}   type={}",userId,imageUrl,type);
        ImgTypeEnum.checkType(type);
        UploadFileEntity uploadFileEntity=new UploadFileEntity();
        uploadFileEntity.setFileName(imageUrl);
        uploadFileEntity.setType(type);
        uploadFileEntity.setUserId(userId);
        uploadFileEntity.setStep(0);
        uploadFileEntity.setInsertTime(new Date());
        uploadFileEntity.setUpdateTime(new Date());
        uploadFileMapper.insert(uploadFileEntity);
        userDetailProcessorService.divAndCheckScore(userId,type);
        batchProcessService.addUploadNum(DateFormaterUtil.YMDformater.get().format(new Date()),userId,type);
        MyAipOcr client = aipOcrClientSelector.getAvailableInvoiceClient(aipOcrClientSelector.getProcessTimes(1));

        UploadFileProducerMessageDto uploadFileProducerMessageDto =new UploadFileProducerMessageDto(uploadFileEntity,client.getToken());
        messageBrokerComponent.queueOffer(uploadFileProducerMessageDto);
        UserEntity userEntity = userDetailProcessorService.userDetail(userId);
        logger.info("上传的文件已放入队列   userId={}     imageUrl={}   type={}",userId,imageUrl,type);
        return userEntity;
    }

}
