package com.dandinglong.util;

import java.util.Date;

public class FileNameUtil {
    public static String generateFileName(String orangeFileName){
        String newName=DateFormaterUtil.formater.get().format(new Date())+String.valueOf((int)(Math.random()*10000));
        String suffix = orangeFileName.substring(orangeFileName.lastIndexOf(".") + 1);
        return newName+"."+suffix;
    }
}
