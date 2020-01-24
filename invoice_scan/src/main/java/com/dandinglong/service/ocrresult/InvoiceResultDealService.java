package com.dandinglong.service.ocrresult;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dandinglong.dto.UploadFileConsumerMessageDto;
import com.dandinglong.entity.ScanImageByDayDetailEntity;
import com.dandinglong.entity.UploadFileEntity;
import com.dandinglong.entity.baidu.Words_result;
import com.dandinglong.entity.scanres.InvoiceEntity;
import com.dandinglong.exception.RecognitionException;
import com.dandinglong.mapper.*;
import com.dandinglong.service.BatchProcessService;
import com.dandinglong.util.BaiduScanResultExchangeUtil;
import com.dandinglong.util.DateFormaterUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class InvoiceResultDealService implements ResultDealService{
    private Logger logger=LoggerFactory.getLogger(InvoiceResultDealService.class);
    @Autowired
    private InvoiceDataMapper invoiceDataMapper;
    @Autowired
    private InvoiceDetailMapper invoiceDetailMapper;
    @Autowired
    private UploadFileMapper uploadFileMapper;
    @Autowired
    private BatchProcessService batchProcessService;
    @Override
    public boolean deal(UploadFileConsumerMessageDto uploadFileConsumerMessageDto) {
        logger.info("开始处理增值税发票{}",uploadFileConsumerMessageDto.getOcrResult());
        UploadFileEntity uploadFileEntity = uploadFileConsumerMessageDto.getUploadFileEntity();
        String ocrResult = uploadFileConsumerMessageDto.getOcrResult();
        String dealDate=DateFormaterUtil.YMDformater.get().format(uploadFileEntity.getInsertTime());

        JSONObject ocrResultJsonObject=JSONObject.parseObject(ocrResult);
        if (ocrResultJsonObject.containsKey("error_msg")) {
            logger.error("百度识别失败，识别结果 {}",ocrResult);
            uploadFileEntity.setStep(3);
            uploadFileEntity.setUpdateTime(new Date());
            uploadFileMapper.updateByPrimaryKey(uploadFileEntity);
            //失败数量加一
            batchProcessService.addFailNum(dealDate,uploadFileEntity.getUserId(),uploadFileEntity.getType());
            return false;
        }
        Words_result words_result = ocrResultJsonObject.getJSONObject("words_result").toJavaObject(Words_result.class);
//        转换
        InvoiceEntity invoiceEntity = BaiduScanResultExchangeUtil.exchangeInvoice(words_result);
        //插入发票信息
        invoiceEntity.getInvoiceDataEntity().setInsertTime(new Date());
        invoiceEntity.getInvoiceDataEntity().setUserId(uploadFileEntity.getUserId());
        invoiceDataMapper.insert(invoiceEntity.getInvoiceDataEntity());
        //插入明细
        for (int i = 0; i < invoiceEntity.getInvoiceDetailEntityList().size(); i++) {
            invoiceEntity.getInvoiceDetailEntityList().get(i).setUserId(uploadFileEntity.getUserId());
            invoiceEntity.getInvoiceDetailEntityList().get(i).setInvoiceId(invoiceEntity.getInvoiceDataEntity().getId());
            invoiceEntity.getInvoiceDetailEntityList().get(i).setInsertTime(new Date());
            invoiceDetailMapper.insert(invoiceEntity.getInvoiceDetailEntityList().get(i));
        }
        //图片识别成功
        uploadFileEntity.setStep(2);
        uploadFileEntity.setUpdateTime(new Date());
        uploadFileMapper.updateByPrimaryKey(uploadFileEntity);
        //今日识别图片数量+1

        batchProcessService.addScanNum(dealDate,uploadFileEntity.getUserId(),uploadFileEntity.getType());
        logger.info("处理完成           {}", JSON.toJSONString(uploadFileEntity));

        return false;
    }
}
