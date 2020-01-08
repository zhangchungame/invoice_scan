package com.dandinglong.task;

import com.alibaba.fastjson.JSON;
import com.dandinglong.entity.ScanImageByDayDetailEntity;
import com.dandinglong.entity.scanres.InvoiceEntity;
import com.dandinglong.mapper.ScanImageByDayDetailMapper;
import com.dandinglong.model.excel.GenerateExceInvoice;
import com.dandinglong.model.qiniu.FileSaveSys;
import com.dandinglong.service.InvoiceProcessService;
import com.dandinglong.util.ApplicationContextProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

public class ExcelGenerateRunnable implements Runnable {
    private Logger logger= LoggerFactory.getLogger(ExcelGenerateRunnable.class);
    private ScanImageByDayDetailEntity scanImageByDayDetailEntity;

    public ExcelGenerateRunnable(ScanImageByDayDetailEntity scanImageByDayDetailEntity) {
        this.scanImageByDayDetailEntity = scanImageByDayDetailEntity;
    }

    @Override
    public void run() {
        logger.info("开始生成excel");
        logger.info("scanImageByDayDetailEntity={}",JSON.toJSONString(scanImageByDayDetailEntity));
        String filePath=ApplicationContextProvider.getApplicationContext().getEnvironment().getProperty("excleFileLocation");
        InvoiceProcessService invoiceProcessService = ApplicationContextProvider.getBean(InvoiceProcessService.class);
        GenerateExceInvoice generateExceInvoice = ApplicationContextProvider.getBean(GenerateExceInvoice.class);
        ScanImageByDayDetailMapper scanImageByDayDetailMapper = ApplicationContextProvider.getBean(ScanImageByDayDetailMapper.class);
        List<InvoiceEntity> invoiceEntityList = null;
        try {
            invoiceEntityList = invoiceProcessService.getInvoiceEntityByDate(scanImageByDayDetailEntity.getDealDate(), scanImageByDayDetailEntity.getUserId());
        } catch (ParseException e) {
            logger.error(e.getMessage(),e);
        }
        try {
            String excelFileName=generateExceInvoice.generate(invoiceEntityList,filePath);
            excelFileName= FileSaveSys.uploadFile(filePath,excelFileName);
            scanImageByDayDetailEntity.setExcelFile(excelFileName);
            scanImageByDayDetailEntity.setExcelStep(2);
            scanImageByDayDetailMapper.updateByPrimaryKey(scanImageByDayDetailEntity);
            logger.info("生成完成");
            logger.info("scanImageByDayDetailEntity={}",JSON.toJSONString(scanImageByDayDetailEntity));
        } catch (NoSuchFieldException e) {
            logger.error(e.getMessage(),e);
        } catch (IllegalAccessException e) {
            logger.error(e.getMessage(),e);
        } catch (IOException e) {
            logger.error(e.getMessage(),e);
        }
    }
}
