package com.dandinglong.model;

import com.dandinglong.annotation.FunctionUseTime;
import net.coobird.thumbnailator.Thumbnails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

@Component
public class ImageCompressThumb implements ImageCompress{
    private Logger logger= LoggerFactory.getLogger(ImageCompressThumb.class);
    @Override
    @FunctionUseTime
    public String compress(String fileName) {
        File file=new File(fileName);
        if (file.length() < 3 * 1000 * 1000) {
            return fileName;
        }else{
            float scale=1;
            float quality=1;
            if(file.length()>10*1000*1000){
                scale=0.5f;
                quality=0.8f;
            }else if(file.length()>8*1000*1000){
                scale=0.8f;
                quality=0.8f;
            }else {
                quality=0.8f;
            }
            try {
                Thumbnails.of(fileName).scale(scale).outputQuality(quality).toFile(fileName);
                return compress(fileName);
            } catch (IOException e) {
                logger.error("压缩图片失败 ",e);
                return fileName;
            }
        }
    }
}
