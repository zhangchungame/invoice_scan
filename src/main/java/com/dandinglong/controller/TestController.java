package com.dandinglong.controller;

import com.dandinglong.entity.UserEntity;
import com.dandinglong.entity.scanres.InvoiceEntity;
import com.dandinglong.mapper.UserMapper;
import com.dandinglong.model.excel.GenerateExceInvoice;
import com.dandinglong.service.InvoiceProcessService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
public class TestController {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private InvoiceProcessService invoiceProcessService;
    @Autowired
    private GenerateExceInvoice generateExceInvoice;
    @Value("${excleFileLocation}")
    private String excleFileLocation;
    @RequestMapping("/test")
    public List<InvoiceEntity> test() throws ParseException, IllegalAccessException, NoSuchFieldException, IOException {
        List<InvoiceEntity> invoiceEntityList = invoiceProcessService.getInvoiceEntityByDate("20200102", 1);
        System.out.println(generateExceInvoice.generate(invoiceEntityList,excleFileLocation));
        return invoiceEntityList;
    }
}
