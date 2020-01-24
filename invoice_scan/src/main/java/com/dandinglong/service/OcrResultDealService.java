package com.dandinglong.service;

import com.dandinglong.dto.UploadFileConsumerMessageDto;
import com.dandinglong.entity.UploadFileEntity;
import com.dandinglong.enums.ImgTypeEnum;
import com.dandinglong.service.ocrresult.ResultDealService;
import com.dandinglong.util.ApplicationContextProvider;
import org.springframework.stereotype.Service;

@Service
public class OcrResultDealService {
    public void ocrDeal(UploadFileConsumerMessageDto uploadFileConsumerMessageDto){
        String beanName= ImgTypeEnum.getEnumByType(uploadFileConsumerMessageDto.getUploadFileEntity().getType()).getServiceName();
        ResultDealService resultService = (ResultDealService)ApplicationContextProvider.getBean(beanName);
        resultService.deal(uploadFileConsumerMessageDto);
    }
}
