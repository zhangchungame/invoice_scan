package com.dandinglong.service;

import com.dandinglong.entity.UploadFileEntity;
import com.dandinglong.entity.UserEntity;
import com.dandinglong.enums.ImgTypeEnum;
import com.dandinglong.mapper.UploadFileMapper;
import com.dandinglong.model.MessageBrokerComponent;
import com.dandinglong.util.DateFormaterUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        messageBrokerComponent.queueOffer(uploadFileEntity);
        UserEntity userEntity = userDetailProcessorService.userDetail(userId);
        logger.info("上传的文件已放入队列   userId={}     imageUrl={}   type={}",userId,imageUrl,type);
        return userEntity;
    }
}
