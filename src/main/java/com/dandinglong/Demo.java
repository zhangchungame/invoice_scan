package com.dandinglong;

import com.alibaba.fastjson.JSON;
import com.dandinglong.entity.baidu.JsonRootBean;
import net.coobird.thumbnailator.Thumbnails;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Demo {
    public static void main(String[] args) throws IOException {
        double quality=3000000/6884938;
        System.out.println(quality);
//        Thumbnails.of("f:/微信图片_20200101224055.jpg").scale(1f).outputQuality(0.8f).toFile("f:/IMG_20191204_113238.jpg");
    }
}
