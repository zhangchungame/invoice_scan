package com.dandinglong.util;

import java.text.SimpleDateFormat;

/**
 * 时间格式化工具
 */
public class DateFormaterUtil {
    public static ThreadLocal<SimpleDateFormat> formater = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyyMMddHHmmss");
        }
    };
    public static ThreadLocal<SimpleDateFormat> YMDformater = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyyMMdd");
        }
    };

}
