package com.dandinglong.controller;

import com.dandinglong.model.qiniu.FileSaveSys;
import com.dandinglong.util.JsonResult;
import com.dandinglong.util.ResultUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

//@RestController
public class TokenController {
    @RequestMapping("/token/qiniu")
    public JsonResult qiniuToken(){
        return ResultUtil.success(FileSaveSys.getToken());
    }
}
