package com.dandinglong;

import com.baidu.aip.util.Base64Util;
import com.baidu.aip.util.Util;
import okhttp3.*;

import java.io.IOException;

public class Demo2 {
    public static void main(String[] args) throws IOException {
        byte[] data = Util.readFileByBytes("f:/img.jpg");
        String base64Content = Base64Util.encode(data);
        OkHttpClient client=new OkHttpClient();
        MediaType parse = MediaType.parse("application/x-www-form-urlencoded");
        RequestBody requestBody = RequestBody.create("image:"+base64Content, parse);
        FormBody formBody=new FormBody.Builder().add("image",base64Content).build();
//        Request build = new Request.Builder().header("","").post(formBody).url("https://aip.baidubce.com/rest/2.0/ocr/v1/vat_invoice?access_token=24.40cae09772250e2333374fc38c94d155.2592000.1581521385.282335-17864821").build();
        Request build = new Request.Builder()
//                .header("host","aip.baidubce.com")
//                .header("x-bce-date","2020-01-13T15:49:09Z")
//                .header("content-type","application/x-www-form-urlencoded")
//                .header("authorization","bce-auth-v1/PmQ2gx4KovbDekph57GbscFd/2020-01-13T15:51:47Z/1800/host;x-bce-date/02d0cc98b5749189853e94b3a1a260259d242ef15ca35bd942832d19399f8382")
                .post(formBody).url("https://aip.baidubce.com/rest/2.0/ocr/v1/vat_invoice?authorization=bce-auth-v1/PmQ2gx4KovbDekph57GbscFd/2020-01-13T16:13:43Z/1800/host;x-bce-date/ff22b0ac38724502df5338be41bdb9d2330f07ccb7bea622787aa59de4f8d898").build();
        Call call = client.newCall(build);
        Response execute = call.execute();
        String string = execute.body().string();
        System.out.println(string);
    }
}
