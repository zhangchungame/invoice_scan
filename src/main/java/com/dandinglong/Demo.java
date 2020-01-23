package com.dandinglong;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dandinglong.entity.UserEntity;
import com.dandinglong.enums.ImgTypeEnum;
import com.dandinglong.exception.WxException;
import com.dandinglong.util.FileNameUtil;
import okhttp3.*;
import okio.BufferedSink;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Demo {
    public static void main(String[] args) throws Exception {
        Demo demo=new Demo();
        demo.testEnum();
    }
    public void testEnum(){
        System.out.println(JSON.toJSONString(ImgTypeEnum.valueList()));
    }
    public String downLoadAvatar() throws IOException {
        OkHttpClient okHttpClient=new OkHttpClient();
        Request request=new Request.Builder().url("https://wx.qlogo.cn/mmopen/vi_32/YH1uvwabzSSLPeWWskRksX6IeYKhhyv2S3x67aF7IarhbMR7bglQV5FMZPzz2kR4F1dQgR7ubSO7TEWGlWHPHg/132").build();
        Call call=okHttpClient.newCall(request);
        Response response = call.execute();
        String outputPath="F:/"+ FileNameUtil.generateFileName("aaa.jpg");
        FileOutputStream fo=new FileOutputStream(outputPath);
        fo.write(response.body().bytes());
        return outputPath;
    }
    public String accessToken(String appId,String secret) throws IOException {
        OkHttpClient client=new OkHttpClient();
        Request request=new Request.Builder().url(String.format("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s",appId,secret)).build();
        Call call=client.newCall(request);
        Response response = call.execute();
        String resp=response.body().string();
        JSONObject jsonObject = JSON.parseObject(resp);
        return jsonObject.getString("access_token");
    }
    public void makeQrCode(String filePathName,int userId) throws IOException {
        String accessToken=accessToken("wx5cf219648df27a68","3fb427c47bf3d0d1be9488fac8353a2d");
        System.out.println(accessToken);
        OkHttpClient client=new OkHttpClient();
        RequestBody formBody= new FormBody.Builder()
                .add("scene","userId=1").build();
        RequestBody requestBody=new RequestBody() {
            @Nullable
            @Override
            public MediaType contentType() {
                return MediaType.parse("application/json; charset=utf-8");
            }

            @Override
            public void writeTo(@NotNull BufferedSink bufferedSink) throws IOException {
                Map<String,String> map=new HashMap<>();
                map.put("scene","userId=123");
                String s = JSON.toJSONString(map);
                System.out.println(s);
                bufferedSink.writeUtf8(s);
            }
        };

        Map<String,String> map=new HashMap<>();
        map.put("scene","userId=123");
        String s = JSON.toJSONString(map);
        RequestBody requestBody1 = RequestBody.create(s,MediaType.parse("application/json"));
        Request request=new Request.Builder().url("https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token="+accessToken)
                .post(requestBody1).build();
        Call call=client.newCall(request);
        Response response = call.execute();
//        System.out.println(response.body().string());
        FileOutputStream fileOutputStream=new FileOutputStream(filePathName);
        ResponseBody body = response.body();
        fileOutputStream.write(body.bytes());
//        InputStream inputStream = body.byteStream();
//        int len=0;
//        byte[] bytes=new byte[1024];
//        while((len=inputStream.read(bytes))!=-1){
//            fileOutputStream.write(bytes,0,len);
//        }
        fileOutputStream.close();
    }
    public void test(){

//        byte[] data = Util.readFileByBytes("f:/img.jpg");
//        String base64Content = Base64Util.encode(data);
//        System.out.println(base64Content);
//
//        AipOcr aipOcr=new AipOcr("17864821","PmQ2gx4KovbDekph57GbscFd","GqwAUKGguPiQFxFA2T8tx8GrjzbyYUw6");
//        aipOcr.vatInvoice("f:/img.jpg",null);


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
}
