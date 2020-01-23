package com.dandinglong.controller;

import com.dandinglong.entity.UserEntity;
import com.dandinglong.enums.ImgTypeEnum;
import com.dandinglong.model.qiniu.FileSaveSys;
import com.dandinglong.service.UploadService;
import com.dandinglong.service.UserDetailProcessorService;
import com.dandinglong.util.JsonResult;
import com.dandinglong.util.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
public class UploadController extends BaseController{
    private Logger logger=LoggerFactory.getLogger(UploadController.class);
    @Autowired
    private UploadService uploadService;
    @Autowired
    private UserDetailProcessorService userDetailProcessorService;

    @RequestMapping("/upload/qiniuToken")
    public JsonResult qiniuToken(){
        logger.info("请求token");
        return ResultUtil.success(FileSaveSys.getTokenTmp(60L));
    }

    @RequestMapping("/upload/imgType")
    public JsonResult imgType(){
        return ResultUtil.success(ImgTypeEnum.valueList());
    }

    @RequestMapping("/upload/qiniuFileUploaded")
    public JsonResult qiniuFileUploaded(@RequestParam(value = "imageUrl")String imageUrl,@RequestParam(value = "type")String type){
        UserEntity userEntity=getUserEntity();
        userEntity=uploadService.newQiNiuUploaded(imageUrl,type,userEntity.getId());
        setUserEntity(userEntity);
        return ResultUtil.success(userEntity.getTodayUsedScore());
    }
}
