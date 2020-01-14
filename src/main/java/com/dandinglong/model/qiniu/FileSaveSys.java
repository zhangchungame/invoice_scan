package com.dandinglong.model.qiniu;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dandinglong.util.FileNameUtil;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class FileSaveSys {

    public static String uploadFile(String filePath,String fileName) throws QiniuException {
        Configuration cfg = new Configuration(Region.region0());
//...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
//...生成上传凭证，然后准备上传
        String accessKey = "fpByCXWaCQe5e72WUNB644EVRZyaOVCINaUa8WA8";
        String secretKey = "3UlIdigVe08V6DoHBEqtLSOs8aKZd1vEkPig8SF0";
        String bucket = "scaninvoice";
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);
        BucketManager bucketManager = new BucketManager(auth, cfg);

        String key = UUID.randomUUID() +"/"+FileNameUtil.generateFileName(fileName);
        Response response = uploadManager.put(filePath+fileName, key, upToken);
        JSONObject jsonObject = JSON.parseObject(response.bodyString());
        return "http://files.dxz3000.com/"+jsonObject.getString("key");
    }
    public static Map<String,String> getToken(){
        String accessKey = "fpByCXWaCQe5e72WUNB644EVRZyaOVCINaUa8WA8";
        String secretKey = "3UlIdigVe08V6DoHBEqtLSOs8aKZd1vEkPig8SF0";
        String bucket = "scaninvoice";
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket,null,1200L,null);
        Map<String ,String> result=new HashMap<>();
        result.put("token",upToken);
        result.put("host","http://files.dxz3000.com/");
        return result;
    }
}
