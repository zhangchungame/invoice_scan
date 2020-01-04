package com.dandinglong;

import com.alibaba.fastjson.JSON;
import com.dandinglong.entity.baidu.JsonRootBean;
import com.dandinglong.entity.scanres.InvoiceDataEntity;
import net.coobird.thumbnailator.Thumbnails;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class Demo {
    public static void main(String[] args) throws IOException, NoSuchFieldException, IllegalAccessException {
        InvoiceDataEntity invoiceDataEntity=new InvoiceDataEntity();
        invoiceDataEntity.setInvoiceCode("sdfsdf");
        invoiceDataEntity.setPurchaserName("PurchaserNaddd123123me");
        Field field= InvoiceDataEntity.class.getDeclaredField("PurchaserName");
        field.setAccessible(true);
        Object o = field.get(invoiceDataEntity);
        System.out.println(o);
    }
}
