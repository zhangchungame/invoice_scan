package com.dandinglong.service;

import com.dandinglong.entity.scanres.InvoiceDataEntity;
import com.dandinglong.entity.scanres.InvoiceDetailEntity;
import com.dandinglong.entity.scanres.InvoiceEntity;
import com.dandinglong.mapper.InvoiceDataMapper;
import com.dandinglong.mapper.InvoiceDetailMapper;
import com.dandinglong.util.DateFormaterUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Service
public class InvoiceProcessService {
    @Autowired
    private InvoiceDataMapper invoiceDataMapper;
    @Autowired
    private InvoiceDetailMapper invoiceDetailMapper;
    public List<InvoiceEntity> getInvoiceEntityByDate(String dealDate,int userId) throws ParseException {
        List<InvoiceEntity> result=new ArrayList<>();
        Example example=new Example(InvoiceDataEntity.class);
        Date startDate= DateFormaterUtil.formater.get().parse(dealDate+"000000");
        Date endDate=DateFormaterUtil.formater.get().parse(dealDate+"235959");
        example.createCriteria().andBetween("insertTime",startDate,endDate).andEqualTo("userId",userId);
        List<InvoiceDataEntity> dataEntityList = invoiceDataMapper.selectByExample(example);
        Iterator<InvoiceDataEntity> dataEntityIterator = dataEntityList.iterator();
        while (dataEntityIterator.hasNext()){
            InvoiceDataEntity dataEntity=dataEntityIterator.next();
            InvoiceEntity invoiceEntity=new InvoiceEntity();
            invoiceEntity.setInvoiceDataEntity(dataEntity);
            example=new Example(InvoiceDetailEntity.class);
            example.createCriteria().andEqualTo("invoiceId",dataEntity.getId());
            List<InvoiceDetailEntity> invoiceDetailEntities = invoiceDetailMapper.selectByExample(example);
            invoiceEntity.setInvoiceDetailEntityList(invoiceDetailEntities);
            result.add(invoiceEntity);
        }
        return result;
    }
}
