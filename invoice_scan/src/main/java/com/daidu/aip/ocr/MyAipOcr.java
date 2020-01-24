package com.daidu.aip.ocr;

import com.baidu.aip.ocr.AipOcr;

public class MyAipOcr extends AipOcr {
    public MyAipOcr(String appId, String apiKey, String secretKey) {
        super(appId, apiKey, secretKey);
    }
    public String getToken(){
        if (needAuth()) {
            getAccessToken(config);
        }
        return accessToken;
    }
    public void setToken(String token){
        accessToken=token;
        isAuthorized.set(true);
    }

}
