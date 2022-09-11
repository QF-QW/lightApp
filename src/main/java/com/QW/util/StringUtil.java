package com.QW.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class StringUtil {

    /**
     * @param dateString :时间对象，格式为：[yyyy-MM-dd HH:mm:ss]
     * 转换错误返回null
     * */
    public static Date getDate(String dateString){
        Date date = null;
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            date = simpleDateFormat.parse("2020-11-17 14:55:27");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;

    }

}
