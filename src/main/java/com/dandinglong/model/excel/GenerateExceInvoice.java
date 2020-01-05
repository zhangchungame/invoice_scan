package com.dandinglong.model.excel;

import com.dandinglong.entity.scanres.InvoiceDataEntity;
import com.dandinglong.entity.scanres.InvoiceDetailEntity;
import com.dandinglong.entity.scanres.InvoiceEntity;
import com.dandinglong.model.excel.ExcellCellMeaning;
import com.dandinglong.model.excel.ExcellRowMeaningConfigInvoice;
import com.dandinglong.model.excel.GenerateExcel;
import com.dandinglong.util.FileNameUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.List;

@Component
public class GenerateExceInvoice implements GenerateExcel {
    @Override
    public String generate(List<InvoiceEntity> invoiceEntityList, String filePath) throws NoSuchFieldException, IllegalAccessException, IOException {
        List<ExcellCellMeaning> excellCellMeanings = ExcellRowMeaningConfigInvoice.getExcellCellMeanings();
        List<ExcellCellMeaning> excellCellMeaningsDetails = ExcellRowMeaningConfigInvoice.getExcellCellMeaningsDetail();
        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFSheet sheet = wb.createSheet();
        XSSFRow row = sheet.createRow(0);
        Iterator<ExcellCellMeaning> meaningIterator = excellCellMeanings.iterator();
        ExcellCellMeaning meaning;
        XSSFCell cell;
        while (meaningIterator.hasNext()) {
            meaning = meaningIterator.next();
            cell = row.createCell(meaning.getCellNum());
            cell.setCellValue(meaning.getDescription());
        }
        Iterator<ExcellCellMeaning> excelCellMeaningsDetailIterator = excellCellMeaningsDetails.iterator();
        while (excelCellMeaningsDetailIterator.hasNext()) {
            meaning = excelCellMeaningsDetailIterator.next();
            cell = row.createCell(meaning.getCellNum());
            cell.setCellValue(meaning.getDescription());
        }
        int rowNum = 1;
        InvoiceEntity invoiceEntity;
        InvoiceDataEntity invoiceDataEntity;
        List<InvoiceDetailEntity> invoiceDetailEntityList;
        for (int i = 0; i < invoiceEntityList.size(); i++) {
            invoiceEntity = invoiceEntityList.get(i);
            invoiceDataEntity = invoiceEntity.getInvoiceDataEntity();
            row = sheet.createRow(rowNum);
            meaningIterator = excellCellMeanings.iterator();
            while (meaningIterator.hasNext()) {
                meaning = meaningIterator.next();
                cell = row.createCell(meaning.getCellNum());
                Field field = InvoiceDataEntity.class.getDeclaredField(meaning.getFieldName());
                field.setAccessible(true);
                cell.setCellValue(String.valueOf(field.get(invoiceDataEntity)));
            }

            invoiceDetailEntityList = invoiceEntity.getInvoiceDetailEntityList();
            Iterator<InvoiceDetailEntity> detailiterator = invoiceDetailEntityList.iterator();
            if(invoiceDetailEntityList.size()>0){
                rowNum--;
            }
            while (detailiterator.hasNext()) {
                rowNum++;
                if(sheet.getRow(rowNum)==null){
                    row=sheet.createRow(rowNum);
                }
                InvoiceDetailEntity invoiceDetailEntity=detailiterator.next();
                excelCellMeaningsDetailIterator = excellCellMeaningsDetails.iterator();
                //一行的detail数据填充
                while (excelCellMeaningsDetailIterator.hasNext()) {
                    meaning = excelCellMeaningsDetailIterator.next();
                    cell = row.createCell(meaning.getCellNum());
                    Field field = InvoiceDetailEntity.class.getDeclaredField(meaning.getFieldName());
                    field.setAccessible(true);
                    cell.setCellValue(String.valueOf(field.get(invoiceDetailEntity)));
                }
            }
            rowNum++;
        }
        String fileName = FileNameUtil.generateFileName("aaa.xlsx");
        FileOutputStream fileOutputStream = new FileOutputStream(filePath + fileName);
        wb.write(fileOutputStream);
        wb.close();
        fileOutputStream.close();
        return fileName;
    }
}
