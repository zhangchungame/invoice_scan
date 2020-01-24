package com.dandinglong.controller;

import com.dandinglong.entity.UserEntity;
import com.dandinglong.service.ScanProgramService;
import com.dandinglong.util.JsonResult;
import com.dandinglong.util.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
public class ImageDealContorller {
    private Logger logger= LoggerFactory.getLogger(ImageDealContorller.class);
    @Autowired
    private ScanProgramService scanProgramService;
    @Autowired
    private HttpServletRequest request;
    @RequestMapping("/image/uploaded")
    public JsonResult uploaded(@RequestParam("imageUrl")String imageUrl,@RequestParam("imageType")String imageType){
        logger.info("imageType  {}   imageUrl  {}",imageType,imageUrl);
        HttpSession httpSession=request.getSession();
        UserEntity userEntity =(UserEntity) httpSession.getAttribute("userEntity");
//        scanProgramService.dealUploadImageFromQiniu(imageUrl,imageType,userEntity);
        return ResultUtil.success(1);
    }
}
