package com.dandinglong.task;

import com.alibaba.fastjson.JSON;
import com.dandinglong.annotation.FunctionUseTime;
import com.dandinglong.entity.ScanImageByDayDetailEntity;
import com.dandinglong.entity.UploadFileEntity;
import com.dandinglong.entity.baidu.JsonRootBean;
import com.dandinglong.entity.baidu.Words_result;
import com.dandinglong.entity.scanres.InvoiceEntity;
import com.dandinglong.mapper.InvoiceDataMapper;
import com.dandinglong.mapper.InvoiceDetailMapper;
import com.dandinglong.mapper.ScanImageByDayDetailMapper;
import com.dandinglong.mapper.UploadFileMapper;
import com.dandinglong.model.ImageCompress;
import com.dandinglong.model.ImageRecognition;
import com.dandinglong.model.qiniu.FileSaveSys;
import com.dandinglong.util.ApplicationContextProvider;
import com.dandinglong.util.BaiduScanResultExchangeUtil;
import com.dandinglong.util.DateFormaterUtil;
import com.qiniu.common.QiniuException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

public class UploadFileDealRunnable implements Runnable {
    private Logger logger= LoggerFactory.getLogger(UploadFileDealRunnable.class);
    private ImageRecognition imageRecognition;
    private UploadFileEntity uploadFileEntity;
    private ImageCompress imageCompress;

    public UploadFileDealRunnable(ImageRecognition imageRecognition, UploadFileEntity uploadFileEntity, ImageCompress imageCompress) {
        this.imageRecognition = imageRecognition;
        this.uploadFileEntity = uploadFileEntity;
        this.imageCompress = imageCompress;
    }

    @FunctionUseTime
    @Override
    public void run() {
        logger.info("开始处理上传的文件  {}", JSON.toJSONString(uploadFileEntity));
        ScanImageByDayDetailEntity scanImageByDayDetailEntity=new ScanImageByDayDetailEntity();
        scanImageByDayDetailEntity.setDealDate(DateFormaterUtil.YMDformater.get().format(new Date()));
        scanImageByDayDetailEntity.setUserId(uploadFileEntity.getUserId());
        scanImageByDayDetailEntity.setUpdateTime(new Date());

        UploadFileMapper uploadFileMapper = ApplicationContextProvider.getBean(UploadFileMapper.class);
        InvoiceDataMapper invoiceDataMapper = ApplicationContextProvider.getBean(InvoiceDataMapper.class);
        InvoiceDetailMapper invoiceDetailMapper = ApplicationContextProvider.getBean(InvoiceDetailMapper.class);
        ScanImageByDayDetailMapper scanImageByDayDetailMapper = ApplicationContextProvider.getBean(ScanImageByDayDetailMapper.class);
        JsonRootBean recognition=null;
        try {
            imageCompress.compress(uploadFileEntity.getFilePath() + uploadFileEntity.getFileName());
            recognition = imageRecognition.recognition(uploadFileEntity.getFilePath() + uploadFileEntity.getFileName());
        } catch (Exception e) {
            logger.error("压缩或处理图片失败",e);
            try {
                uploadFileEntity.setFileName(FileSaveSys.uploadFile(uploadFileEntity.getFilePath(),uploadFileEntity.getFileName()));
            } catch (QiniuException e2) {
                logger.error("上传到七牛错误",e2);
            }
            //标记图片识别失败
            uploadFileEntity.setStep(3);
            uploadFileEntity.setUpdateTime(new Date());
            uploadFileMapper.updateByPrimaryKey(uploadFileEntity);
            //今日识别图片数量+1
            scanImageByDayDetailMapper.scanNumAdd(scanImageByDayDetailEntity);
            logger.info("压缩或处理图片失败        {}",JSON.toJSONString(uploadFileEntity));
            return;
        }
        logger.info("压缩和识别成功           {}",JSON.toJSONString(uploadFileEntity));
        Words_result words_result = recognition.getWords_result();
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
        scanImageByDayDetailMapper.scanNumAdd(scanImageByDayDetailEntity);
        logger.info("处理完成           {}",JSON.toJSONString(uploadFileEntity));
    }
}
