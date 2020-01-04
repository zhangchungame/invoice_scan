package com.dandinglong.util;

import com.dandinglong.exception.MyRunntimeException;

public class ResultUtil {
    public static JsonResult success(Object res){
        JsonResult jsonResult=new JsonResult();
        jsonResult.setCode(0);
        jsonResult.setMsg("ok");
        jsonResult.setData(res);
        return jsonResult;
    }

    public static JsonResult fail(MyRunntimeException e){
        JsonResult jsonResult=new JsonResult();
        jsonResult.setCode(e.getCode());
        jsonResult.setMsg(e.getMessage());
        return jsonResult;
    }
    public static JsonResult fail(Exception e){
        JsonResult jsonResult=new JsonResult();
        jsonResult.setCode(500);
        jsonResult.setMsg("系统错误");
        return jsonResult;
    }
}
