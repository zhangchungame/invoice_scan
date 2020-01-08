package com.dandinglong.model;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baidu.aip.ocr.AipOcr;
import com.dandinglong.entity.baidu.JsonRootBean;
import com.dandinglong.exception.RecognitionException;
import com.dandinglong.task.ExcelGenerateRunnable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class ImageRecognitionBaidu implements ImageRecognition {
    private Logger logger= LoggerFactory.getLogger(ImageRecognitionBaidu.class);

    private AipOcr client;

    public ImageRecognitionBaidu(AipOcr client) {
        this.client = client;
    }

    @Override
    public JsonRootBean recognition(String image) {
        HashMap<String, String> options = new HashMap<String, String>();
        options.put("accuracy", "high");
        org.json.JSONObject res = client.vatInvoice(image, options);
        String result = res.toString(2);
        if (res.has("error_msg")) {
            logger.error("百度识别失败，识别结果 {}",result);
            throw new RecognitionException(res.getString("error_msg"));
        }
        JsonRootBean jsonRootBean = JSON.parseObject(result, JsonRootBean.class);
        return jsonRootBean;
    }
}
