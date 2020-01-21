package com.dandinglong.controller;

import com.dandinglong.entity.UserEntity;
import com.dandinglong.mapper.UserMapper;
import com.dandinglong.model.excel.GenerateExceInvoice;
import com.dandinglong.service.AipOcrClientSelector;
import com.dandinglong.service.InvoiceProcessService;
import com.dandinglong.service.UserDetailProcessorService;
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

@RestController
public class TestController {
    private Logger logger= LoggerFactory.getLogger(TestController.class);
    @Autowired
    private UserDetailProcessorService userDetailProcessorService;
    @Autowired
    private AipOcrClientSelector aipOcrClientSelector;
    @Autowired
    private InvoiceProcessService invoiceProcessService;
    @Autowired
    private GenerateExceInvoice generateExceInvoice;
    @Value("${excleFileLocation}")
    private String excleFileLocation;
    @Autowired
    private UserMapper userMapper;

    @RequestMapping("/test")
    public JsonResult test() throws IOException {
        UserEntity userEntity = userMapper.selectByPrimaryKey(1);

        return ResultUtil.success(userDetailProcessorService.generateUserShareImage(userEntity));
    }
}
