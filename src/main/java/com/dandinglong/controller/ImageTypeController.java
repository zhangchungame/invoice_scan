package com.dandinglong.controller;

import com.dandinglong.entity.ImageTypeAndTypeName;
import com.dandinglong.util.JsonResult;
import com.dandinglong.util.ResultUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class ImageTypeController {
    @RequestMapping("/imageType/all")
    public JsonResult imageTypeAll(){
        List<ImageTypeAndTypeName> result=new ArrayList<>();
//        Map<String, ImageTypeAndTypeName> result=new HashMap<>();
        result.add(new ImageTypeAndTypeName("invoice","增值税专用发票"));
        result.add(new ImageTypeAndTypeName("other","其他"));
        return ResultUtil.success(result);
    }
}
