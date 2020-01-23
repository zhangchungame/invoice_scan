package com.dandinglong.controller;

import com.alibaba.fastjson.JSON;
import com.dandinglong.annotation.FunctionUseTime;
import com.dandinglong.entity.Code2Session;
import com.dandinglong.entity.UserEntity;
import com.dandinglong.enums.ImgTypeEnum;
import com.dandinglong.exception.WxException;
import com.dandinglong.service.ScanProgramService;
import com.dandinglong.service.UserDetailProcessorService;
import com.dandinglong.util.FileNameUtil;
import com.dandinglong.util.JsonResult;
import com.dandinglong.util.ResultUtil;
import org.apache.catalina.User;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

@RestController
public class ScanProgramController {
    private Logger logger = LoggerFactory.getLogger(ScanProgramController.class);
    @Autowired
    private HttpServletRequest httpServletRequest;
    @Value("${uploadFileLocation}")
    private String uploadFileLocation;
    @Autowired
    private ScanProgramService scanProgramService;
    @Autowired
    private UserDetailProcessorService userDetailProcessorService;






    @FunctionUseTime
    @RequestMapping("/scan/upload")
    public JsonResult upload(@RequestParam("file") MultipartFile file) throws IOException {
        HttpSession session = httpServletRequest.getSession();
        String filename = FileNameUtil.generateFileName(file.getOriginalFilename());
        file.transferTo(new File(uploadFileLocation + filename));
        UserEntity userEntity = (UserEntity) session.getAttribute("userEntity");
        logger.info("上传图片 fileName={}  userEntity={}  ", filename, JSON.toJSONString(userEntity));
        scanProgramService.dealUploadImage(uploadFileLocation, filename, userEntity);
        userEntity = userDetailProcessorService.userDetail(userEntity.getId());
        session.setAttribute("userEntity", userEntity);
        return ResultUtil.success(userEntity.getTodayUsedScore());
    }

    @RequestMapping("/batch/batchList")
    public JsonResult batchList(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum) {
        HttpSession session = httpServletRequest.getSession();
        UserEntity userEntity = (UserEntity) session.getAttribute("userEntity");
        return ResultUtil.success(scanProgramService.getScanEntityList(userEntity.getId(), pageNum));

    }

    @RequestMapping("/batch/batchDetail")
    public JsonResult batchDetail(@RequestParam(value = "batchId") int batchId) throws ParseException {
        return ResultUtil.success(scanProgramService.selectBatchTypeAndProcess(batchId));
    }

    @RequestMapping("/batch/reProcess")
    public JsonResult reProcess(@RequestParam(value = "batchId") int batchId) throws ParseException {
        HttpSession session = httpServletRequest.getSession();
        UserEntity userEntity = (UserEntity) session.getAttribute("userEntity");
        return ResultUtil.success(scanProgramService.reGenerateExcel(batchId, userEntity.getId()));
    }



    @RequestMapping("/scan/imgType")
    public JsonResult imgType(){
        return null;
    }
}
