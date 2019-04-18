package com.example.tool;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeTool {
    public static String etaTime(long endTime) {
        try {
            long nowTime = System.currentTimeMillis() ;
            long diff = endTime - nowTime; // 得到的差值是微秒级别，可以忽略
            String res ="";
            if(diff<0)
            {
                diff = (0L-diff);
                res+= "已过去";
            }
            else
            {
                res+= "还剩余";
            }
            long days = diff / (60L * 60L * 24L * 1000L);
            long hours = (diff - days * (60 * 60 * 24 * 1000)) / (60 * 60 * 1000);
            long minutes = (diff - days * (60 * 60 * 24 * 1000) - hours * (60 * 60 * 1000)) / (60* 1000);
            long seconds = (diff - days * (60 * 60 * 24 * 1000) - hours * ( 60 * 60 * 1000) - minutes * ( 60 * 1000 )) / 1000 ;
            return res
                    + (days    <= 0 ? "" : days     + "天")
                    + (hours   <= 0 ? "" : hours    + "小时")
                    + (minutes <= 0 ? "" : minutes  + "分")
                    + (seconds <= 0 ? "" : seconds  + "秒")
                    ;

        } catch (Exception e) {
            e.printStackTrace();
            return "时间计算失败";
        }
    }


    public static long dateToStamp(String s) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        Date date = simpleDateFormat.parse(s);
        long ts = date.getTime() + 8*60*60*1000;
        return ts;
    }

    public static String stmpEtaTime(String s)
    {
        try {
            return etaTime(dateToStamp(s));
        } catch (ParseException e) {
            e.printStackTrace();
            return "时间计算失败";
        }
    }

    public static long getTomorrowZeroSeconds() {
        long current = System.currentTimeMillis();// 当前时间毫秒数
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        long tomorrowzero = calendar.getTimeInMillis();
        long tomorrowzeroSeconds = (tomorrowzero- current) ;
        return tomorrowzeroSeconds;
    }
}
