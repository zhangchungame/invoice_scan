package com.dandinglong;

import com.alibaba.fastjson.JSON;
import com.baidu.aip.ocr.AipOcr;
import com.baidu.aip.ocr.MyBceClient;
import com.baidu.aip.util.AipClientConst;
import com.baidu.aip.util.Base64Util;
import com.baidu.aip.util.SignUtil;
import com.baidu.aip.util.Util;
import com.baidubce.BceClientConfiguration;
import com.baidubce.auth.BceCredentials;
import com.baidubce.auth.BceV1Signer;
import com.baidubce.auth.DefaultBceCredentials;
import com.baidubce.http.HttpMethodName;
import com.baidubce.internal.InternalRequest;
import com.baidubce.services.ocr.OcrClient;
import com.dandinglong.entity.baidu.JsonRootBean;
import com.dandinglong.entity.scanres.InvoiceDataEntity;
import com.dandinglong.model.qiniu.FileSaveSys;
import com.qiniu.util.Base64;
import net.coobird.thumbnailator.Thumbnails;
import okhttp3.*;
import org.apache.commons.codec.binary.Hex;
import org.json.JSONObject;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.lang.reflect.Field;
import java.net.URI;
import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Demo {
    public static void main(String[] args) throws Exception {
        byte[] data = Util.readFileByBytes("f:/img.jpg");
        String base64Content = Base64Util.encode(data);
        System.out.println(base64Content);

        AipOcr aipOcr=new AipOcr("17864821","PmQ2gx4KovbDekph57GbscFd","GqwAUKGguPiQFxFA2T8tx8GrjzbyYUw6");
        aipOcr.vatInvoice("f:/img.jpg",null);


//        AipOcr aipOcr=new AipOcr("17864821","PmQ2gx4KovbDekph57GbscFd","GqwAUKGguPiQFxFA2T8tx8GrjzbyYUw6");
//        aipOcr.
//        JSONObject jsonObject = aipOcr.vatInvoice(data, null);
//        System.out.println(jsonObject.toString());
//        AipOcr aipOcr2=new AipOcr("17864821","PmQ2gx4KovbDekph57GbscFd","GqwAUKGguPiQFxFA2T8tx8GrjzbyYUw6");
//        JSONObject jsonObject2 = aipOcr2.vatInvoice(data, null);
//        System.out.println(jsonObject2.toString());
//        JSONObject jsonObject3 = aipOcr.vatInvoice(data, null);
//        System.out.println(jsonObject3.toString());
        // 传入可选参数调用接口
//        AipOcr client=new AipOcr("17864821","PmQ2gx4KovbDekph57GbscFd","GqwAUKGguPiQFxFA2T8tx8GrjzbyYUw6");
//        HashMap<String, String> options = new HashMap<String, String>();
//        options.put("language_type", "CHN_ENG");
//        options.put("detect_direction", "true");
//        options.put("detect_language", "true");
//        options.put("probability", "true");


        // 参数为本地图片路径
//        String image = "test.jpg";
//        JSONObject res = client.basicGeneral(image, options);
//        System.out.println(res.toString(2));
//
//
//
//        // 通用文字识别, 图片参数为远程url图片
//            JSONObject res = client.basicGeneralUrl("http://files.dxz3000.com/微信图片_20200101224055.jpg", options);
////            JSONObject res = client.basicGeneralUrl("http://files.dxz3000.com/20200108225745160.jpg", options);
//            System.out.println(res.toString(2));
//        FileSaveSys.uploadFile("f:/","img.jpg");

//        String canonicalRequest="POST /rest/2.0/ocr/v1/vat_invoice host:aip.baidubce.com x-bce-date:2020-01-13T09%3A41%3A39Z";
//        String authStringPrefix="bce-auth-v1/PmQ2gx4KovbDekph57GbscFd/2020-01-13T09:43:00Z/1800";
//        String SigningKey=sha256Hex("GqwAUKGguPiQFxFA2T8tx8GrjzbyYUw6",authStringPrefix);
//        System.out.println(SigningKey);
//        String Signature=sha256Hex(SigningKey,canonicalRequest);
//        System.out.println(Signature);
//        MyBceClient aipOcr = new MyBceClient("17864821","PmQ2gx4KovbDekph57GbscFd","GqwAUKGguPiQFxFA2T8tx8GrjzbyYUw6");
//        JSONObject jsonObject = aipOcr.vatInvoice("f:/img.jpg", null);
//        System.out.println(jsonObject.toString());


//        String method="POST";
//        String path="/rest/2.0/ocr/v1/vat_invoice";
//        String host="aip.baidubce.com";
//        String ak="PmQ2gx4KovbDekph57GbscFd";
//        String sk="GqwAUKGguPiQFxFA2T8tx8GrjzbyYUw6";
//        String timeStamp="2020-01-13T01:33:38Z";
//        String express="1800";
//        String authStringPrefix = String.format("bce-auth-v1/%s/%s/%s",
//                ak, timeStamp, express);
//        String canonicalRequest=method+"\n"+path+"\n"+"\n"+"host:"+host;
//        String signingKey= SignUtil.hmacSha256(sk,authStringPrefix);
//        String signatur=SignUtil.hmacSha256(signingKey,canonicalRequest);
//        String authorization=String.format("%s/%s/%s/%s");
//        System.out.println(signatur);
//        BceV1Signer bceV1Signer = new BceV1Signer();
//        InternalRequest internalRequest = new InternalRequest(HttpMethodName.POST,new URI("/rest/2.0/ocr/v1/vat_invoice"));
//        internalRequest.addHeader("host","aip.baidubce.com");
//
//        DefaultBceCredentials defaultBceCredentials = new DefaultBceCredentials("PmQ2gx4KovbDekph57GbscFd", "GqwAUKGguPiQFxFA2T8tx8GrjzbyYUw6");
//        bceV1Signer.sign(internalRequest,defaultBceCredentials);
//        System.out.println(1);
    }

    private static final String BCE_AUTH_VERSION = "bce-auth-v1";
    private static final String DEFAULT_ENCODING = "UTF-8";
    private static final Charset UTF8 = Charset.forName(DEFAULT_ENCODING);
    private static String sha256Hex(String signingKey, String stringToSign) throws InvalidKeyException, NoSuchAlgorithmException {
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(new SecretKeySpec(signingKey.getBytes(UTF8), "HmacSHA256"));
            return new String(Hex.encodeHex(mac.doFinal(stringToSign.getBytes(UTF8))));
        } catch (Exception e) {
            throw e;
        }
    }
}
