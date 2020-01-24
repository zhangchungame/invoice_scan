package com.dandinglong.model.ocrmodel;

import com.dandinglong.util.FileNameUtil;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class BaseOcrModel {
    private Logger logger = LoggerFactory.getLogger(BaseOcrModel.class);


    public String downLoadFile(String url,String tmpFilePath) throws IOException {
        logger.info("下载图片url={}", url);
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        Call call = client.newCall(request);
        Response response = call.execute();
        String name = FileNameUtil.generateFileName(url);
        File file = new File(tmpFilePath + name);
        FileOutputStream fo = new FileOutputStream(file);
        byte[] bytes = response.body().bytes();
        fo.write(bytes);
        fo.close();
        logger.info("下载完成，文件名={}", name);
        return name;
    }
}
