package com.dandinglong.model;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baidu.aip.ocr.AipOcr;
import com.dandinglong.entity.baidu.JsonRootBean;
import com.dandinglong.exception.RecognitionException;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class ImageRecognitionBaidu implements ImageRecognition {
    private AipOcr client;

    public ImageRecognitionBaidu(AipOcr client) {
        this.client = client;
    }

    @Override
    public JsonRootBean recognition(String image) {
        HashMap<String, String> options = new HashMap<String, String>();
        options.put("accuracy", "high");
        org.json.JSONObject res = client.vatInvoice(image, options);
        if (res.has("error_msg")) {
            System.err.println(res.getString("error_msg"));
            throw new RecognitionException(res.getString("error_msg"));
        }
        String result = res.toString(2);
        JsonRootBean jsonRootBean = JSON.parseObject(result, JsonRootBean.class);
        return jsonRootBean;
    }
}
