package com.dandinglong;

import com.alibaba.fastjson.JSON;
import com.baidu.aip.ocr.AipOcr;
import com.baidu.aip.util.Base64Util;
import com.baidu.aip.util.Util;
import com.dandinglong.entity.baidu.JsonRootBean;
import com.dandinglong.entity.scanres.InvoiceDataEntity;
import com.dandinglong.model.qiniu.FileSaveSys;
import net.coobird.thumbnailator.Thumbnails;
import okhttp3.*;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Demo {
    public static void main(String[] args) throws IOException, NoSuchFieldException, IllegalAccessException {
//        byte[] data = Util.readFileByBytes("f:/img.jpg");
//        String base64Content = Base64Util.encode(data);
//        System.out.println(base64Content);
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
//
//
//        // 参数为本地图片路径
////        String image = "test.jpg";
////        JSONObject res = client.basicGeneral(image, options);
////        System.out.println(res.toString(2));
//
//
//
//        // 通用文字识别, 图片参数为远程url图片
//            JSONObject res = client.basicGeneralUrl("http://files.dxz3000.com/微信图片_20200101224055.jpg", options);
////            JSONObject res = client.basicGeneralUrl("http://files.dxz3000.com/20200108225745160.jpg", options);
//            System.out.println(res.toString(2));
        FileSaveSys.uploadFile("f:/","img.jpg");
    }
}
