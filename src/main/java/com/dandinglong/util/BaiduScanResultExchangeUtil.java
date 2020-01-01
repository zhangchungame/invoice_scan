package com.dandinglong.util;

import com.dandinglong.entity.scanres.InvoiceDataEntity;
import com.dandinglong.entity.scanres.InvoiceDetailEntity;
import com.dandinglong.entity.scanres.InvoiceEntity;
import com.dandinglong.entity.baidu.Words_result;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;

public class BaiduScanResultExchangeUtil {
    public static InvoiceEntity exchangeInvoice(Words_result words_result) {
        InvoiceEntity invoiceEntity = new InvoiceEntity();
        invoiceEntity.setInvoiceDetailEntityList(new ArrayList<>());
        InvoiceDataEntity target = new InvoiceDataEntity();
        BeanUtils.copyProperties(words_result, target);
        invoiceEntity.setInvoiceDataEntity(target);
        int size=Math.max(
        Math.max(
        Math.max(words_result.getCommodityNum().size(),words_result.getCommodityAmount().size()),
        Math.max(words_result.getCommodityTaxRate().size(),words_result.getCommodityType().size())
        ),
        Math.max(
        Math.max(words_result.getCommodityTax().size(),words_result.getCommodityName().size()),
        words_result.getCommodityUnit().size()
        ));
        for (int i = 0; i < size; i++) {
            InvoiceDetailEntity invoiceDetailEntity = new InvoiceDetailEntity();
            try {
                invoiceDetailEntity.setCommodityNum(words_result.getCommodityNum().get(i).getWord());
            } catch (Exception e) {
            }
            try {
                invoiceDetailEntity.setCommodityAmount(words_result.getCommodityAmount().get(i).getWord());
            } catch (Exception e) {
            }
            try {
                invoiceDetailEntity.setCommodityTaxRate(words_result.getCommodityTaxRate().get(i).getWord());
            } catch (Exception e) {
            }
            try {
                invoiceDetailEntity.setCommodityType(words_result.getCommodityType().get(i).getWord());
            } catch (Exception e) {
            }
            try {
                invoiceDetailEntity.setCommodityTax(words_result.getCommodityTax().get(i).getWord());
            } catch (Exception e) {
            }
            try {
                invoiceDetailEntity.setCommodityName(words_result.getCommodityName().get(i).getWord());
            } catch (Exception e) {
            }
            try {
                invoiceDetailEntity.setCommodityUnit(words_result.getCommodityUnit().get(i).getWord());
            } catch (Exception e) {
            }
            invoiceEntity.getInvoiceDetailEntityList().add(invoiceDetailEntity);
        }
        return invoiceEntity;
    }
}
