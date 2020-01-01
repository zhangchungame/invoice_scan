package com.dandinglong.model;

import net.coobird.thumbnailator.Thumbnails;

import java.io.File;
import java.io.IOException;

public class ImageCompressThumb implements ImageCompress{
    @Override
    public String compress(String fileName) {
        File file=new File(fileName);
        if (file.length() < 3 * 1000 * 1000) {
            return fileName;
        }
        double quality=(double)5000000/file.length();
        try {
            Thumbnails.of(fileName).scale(1f).outputQuality(quality).toFile(fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileName;
    }
}
