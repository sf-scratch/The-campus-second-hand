package com.lh.springboot03.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeUtil {

    //返回年月日 时分秒
	public static String getSysTime(){
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		Date date = new Date();

        return sdf.format(date);
		
	}

    //返回年月日
    public static String getDate(){

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Date date = new Date();

        return sdf.format(date);

    }

    //返回年月日
    public static String getDate(Date date) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        return sdf.format(date);

    }

    //    返回数组形式 年 月 日
    public static String[] getDateArray(Date date) {

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        String format = sdf.format(date);
        return format.split(":");
    }




}
