package com.dandinglong.task;

import com.alibaba.fastjson.JSON;
import com.dandinglong.entity.ScanImageByDayDetailEntity;
import com.dandinglong.entity.scanres.InvoiceEntity;
import com.dandinglong.mapper.ScanImageByDayDetailMapper;
import com.dandinglong.model.excel.GenerateExceInvoice;
import com.dandinglong.model.qiniu.FileSaveSys;
import com.dandinglong.service.InvoiceProcessService;
import com.dandinglong.util.ApplicationContextProvider;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

public class ExcelGenerateRunnable implements Runnable {
    private ScanImageByDayDetailEntity scanImageByDayDetailEntity;

    public ExcelGenerateRunnable(ScanImageByDayDetailEntity scanImageByDayDetailEntity) {
        this.scanImageByDayDetailEntity = scanImageByDayDetailEntity;
    }

    @Override
    public void run() {
        System.out.println("开始生成excel");
        String filePath=ApplicationContextProvider.getApplicationContext().getEnvironment().getProperty("excleFileLocation");
        InvoiceProcessService invoiceProcessService = ApplicationContextProvider.getBean(InvoiceProcessService.class);
        GenerateExceInvoice generateExceInvoice = ApplicationContextProvider.getBean(GenerateExceInvoice.class);
        ScanImageByDayDetailMapper scanImageByDayDetailMapper = ApplicationContextProvider.getBean(ScanImageByDayDetailMapper.class);
        List<InvoiceEntity> invoiceEntityList = null;
        try {
            invoiceEntityList = invoiceProcessService.getInvoiceEntityByDate(scanImageByDayDetailEntity.getDealDate(), scanImageByDayDetailEntity.getUserId());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            String excelFileName=generateExceInvoice.generate(invoiceEntityList,filePath);
            excelFileName= FileSaveSys.uploadFile(filePath,excelFileName);
            scanImageByDayDetailEntity.setExcelFile(excelFileName);
            scanImageByDayDetailEntity.setExcelStep(2);
            scanImageByDayDetailMapper.updateByPrimaryKey(scanImageByDayDetailEntity);
            System.out.println("生成完成");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
