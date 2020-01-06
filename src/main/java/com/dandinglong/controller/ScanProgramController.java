package com.dandinglong.controller;

import com.dandinglong.entity.Code2Session;
import com.dandinglong.entity.UserEntity;
import com.dandinglong.service.ScanProgramService;
import com.dandinglong.util.FileNameUtil;
import com.dandinglong.util.JsonResult;
import com.dandinglong.util.ResultUtil;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
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

@RestController
public class ScanProgramController {
    @Autowired
    private HttpServletRequest httpServletRequest;
    @Value("${uploadFileLocation}")
    private String uploadFileLocation;
    @Autowired
    private ScanProgramService scanProgramService;

    @RequestMapping("/scan/onLogin")
    public JsonResult onLogin(@RequestParam("code")String code) throws IOException {
        HttpSession session=httpServletRequest.getSession();
        System.out.println(session.getId());
        Code2Session login = scanProgramService.getOpenId(code);
        UserEntity userEntity=scanProgramService.login(login.getOpenId());
        session.setAttribute("code2session",login);
        session.setAttribute("userEntity",userEntity);
        return ResultUtil.success(session.getId());
    }
    @RequestMapping("/scan/upload")
    public JsonResult upload(@RequestParam("file")MultipartFile file) throws IOException {
        HttpSession session=httpServletRequest.getSession();
        System.out.println(session.getId());
        String filename=FileNameUtil.generateFileName(file.getOriginalFilename());
        file.transferTo(new File(uploadFileLocation+ filename));
        UserEntity userEntity = (UserEntity)session.getAttribute("userEntity");
        scanProgramService.dealUploadImage(uploadFileLocation,filename,userEntity);
        return ResultUtil.success("OK");
    }
    @RequestMapping("/batch/batchList")
    public JsonResult batchList(@RequestParam(value = "pageNum",defaultValue = "1")int pageNum){
        HttpSession session=httpServletRequest.getSession();
        UserEntity userEntity = (UserEntity)session.getAttribute("userEntity");
        return ResultUtil.success(scanProgramService.getScanEntityList(userEntity.getId(),pageNum));

    }
    @RequestMapping("/batch/batchDetail")
    public JsonResult batchDetail(@RequestParam(value = "batchId")int batchId) throws ParseException {
        return ResultUtil.success(scanProgramService.selectBatchTypeAndProcess(batchId));
    }
    @RequestMapping("/batch/reProcess")
    public JsonResult reProcess(@RequestParam(value = "batchId")int batchId) throws ParseException {
        HttpSession session=httpServletRequest.getSession();
        UserEntity userEntity = (UserEntity)session.getAttribute("userEntity");
        return ResultUtil.success(scanProgramService.reGenerateExcel(batchId,userEntity.getId()));
    }
}
