package com.dandinglong.model.excel;

import com.dandinglong.entity.ScanImageByDayDetailEntity;
import com.dandinglong.entity.scanres.InvoiceEntity;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface GenerateExcel {
    public String generate(List<InvoiceEntity> invoiceEntityList,String filePath) throws NoSuchFieldException, IllegalAccessException, IOException;
}
