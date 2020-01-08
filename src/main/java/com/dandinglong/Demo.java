package com.dandinglong;

import com.alibaba.fastjson.JSON;
import com.dandinglong.entity.baidu.JsonRootBean;
import com.dandinglong.entity.scanres.InvoiceDataEntity;
import net.coobird.thumbnailator.Thumbnails;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class Demo {
    public static void main(String[] args) throws IOException, NoSuchFieldException, IllegalAccessException {

        Thumbnails.of("C:\\Users\\Administrator\\Desktop\\照片2019.6-12\\4\\IMG_20191006_144731_1.jpg").scale(1).outputQuality(0.5f).toFile("f:/1-0.5.jpg");
        Thumbnails.of("C:\\Users\\Administrator\\Desktop\\照片2019.6-12\\4\\IMG_20191006_144731_1.jpg").scale(1).outputQuality(0.8f).toFile("f:/1-0.8.jpg");
    }
}
