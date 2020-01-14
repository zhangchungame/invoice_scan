package com.baidu.aip.ocr;

import com.baidu.aip.client.BaseClient;
import com.baidu.aip.error.AipError;
import com.baidu.aip.http.AipRequest;
import com.baidu.aip.ocr.AipOcr;
import com.baidu.aip.ocr.OcrConsts;
import com.baidu.aip.util.Base64Util;
import com.baidu.aip.util.Util;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

public class MyBceClient extends AipOcr {
    public MyBceClient(String appId, String apiKey, String secretKey) {
        super(appId, apiKey, secretKey);
        isBceKey.set(true);
    }

    public JSONObject vatInvoice(byte[] image, HashMap<String, String> options) {
        AipRequest request = new AipRequest();
        preOperation(request);

        String base64Content = Base64Util.encode(image);
        request.addBody("image", base64Content);
        if (options != null) {
            request.addBody(options);
        }
        request.setUri(OcrConsts.VAT_INVOICE);
        postOperation(request);
        return requestServer(request);
    }
    public JSONObject vatInvoice(String image, HashMap<String, String> options) {
        try {
            byte[] data = Util.readFileByBytes(image);
            return vatInvoice(data, options);
        } catch (IOException e) {
            e.printStackTrace();
            return AipError.IMAGE_READ_ERROR.toJsonResult();
        }
    }
}
