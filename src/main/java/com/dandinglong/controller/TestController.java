package com.dandinglong.controller;

import com.dandinglong.entity.scanres.InvoiceEntity;
import com.dandinglong.mapper.UserMapper;
import com.dandinglong.model.excel.GenerateExceInvoice;
import com.dandinglong.model.qiniu.FileSaveSys;
import com.dandinglong.service.InvoiceProcessService;
import com.dandinglong.util.JsonResult;
import com.dandinglong.util.ResultUtil;
import com.qiniu.common.QiniuException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

@RestController
public class TestController {
    private Logger logger= LoggerFactory.getLogger(TestController.class);
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private InvoiceProcessService invoiceProcessService;
    @Autowired
    private GenerateExceInvoice generateExceInvoice;
    @Value("${excleFileLocation}")
    private String excleFileLocation;
    @RequestMapping("/test")
    public JsonResult test() throws QiniuException {
        logger.trace("***************trace***************");
        logger.debug("***************debug***************");
        logger.info("***************info***************");
        logger.warn("***************warn***************");
        logger.error("***************error***************");
        return ResultUtil.success(7);
    }
}
