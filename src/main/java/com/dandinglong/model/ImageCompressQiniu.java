package com.dandinglong.model;

import com.alibaba.fastjson.JSONObject;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

public class ImageCompressQiniu implements ImageCompress {
    @Override
    public String compress(String fileName) {
        Configuration cfg = new Configuration(Region.region0());
//...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
//...生成上传凭证，然后准备上传
        String accessKey = "fpByCXWaCQe5e72WUNB644EVRZyaOVCINaUa8WA8";
        String secretKey = "3UlIdigVe08V6DoHBEqtLSOs8aKZd1vEkPig8SF0";
        String bucket = "daixiazai";
//        String key = null;
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);
        BucketManager bucketManager = new BucketManager(auth, cfg);
        File f = new File(fileName);
        if (f.length() < 3 * 1000 * 1000) {
            return fileName;
        }
        try {
            String key = null;
            Response response = uploadManager.put(fileName, key, upToken);
            //解析上传成功的结果
            JSONObject jsonObject = JSONObject.parseObject(response.bodyString());
            key = jsonObject.getString("key");
            System.out.println(key);
            String fileName2 = key;
            String domainOfBucket = "http://qiniu.dandinglong.site";
            String finalUrl = String.format("%s/%s?imageMogr2/thumbnail/!75p", domainOfBucket, fileName2);
            System.out.println(finalUrl);
            response.close();
            OkHttpClient httpClient = new OkHttpClient();
            Request request = new Request.Builder().url(finalUrl).build();
            Call call = httpClient.newCall(request);
            okhttp3.Response execute = call.execute();
            InputStream stream = execute.body().byteStream();

            FileOutputStream fileOutputStream = new FileOutputStream(fileName);
            byte[] bytes = new byte[1024];
            int len;
            while ((len = stream.read(bytes)) != -1) {
                fileOutputStream.write(bytes, 0, len);
            }
            fileOutputStream.close();
            stream.close();
            System.out.println(finalUrl + " download  finish");
            bucketManager.delete(bucket, key);
            System.out.println(finalUrl + "  finish");
        } catch (QiniuException ex) {
            Response r = ex.response;
            System.err.println(r.toString());
            try {
                System.err.println(r.bodyString());
            } catch (QiniuException ex2) {
                //ignore
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("finalUrl");
        return fileName;
    }
}
