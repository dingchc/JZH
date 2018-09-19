package com.jzh.parents.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * 日期工具类
 *
 * @author ding
 * @date 2018/3/26
 */

public class DateUtils {

    public static final String YYYYMMDD_HHMMSS = "yyyyMMddHHmmss";
    public static final String YYYYMMDDHHMMSS = "yyyy-MM-dd HH:mm:ss";
    public static final String MMDD_HHMM = "MM月dd日 HH:mm";
    public static final String YYYYMMDD = "yyyy-MM-dd";
    public static final String YYYYMMDD2 = "yyyy年MM月dd日";
    public static final String HHmm = "HH:mm";


    /**
     * 格式化日期
     *
     * @param date    日期
     * @param pattern 格式
     * @return 日期格式化后字符串
     */
    public static String formatDateString(Date date, String pattern) {

        String formatDateText = "";

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(pattern, Locale.getDefault());
            formatDateText = dateFormat.format(date);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return formatDateText;
    }


}
