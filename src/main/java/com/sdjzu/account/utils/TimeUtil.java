package com.sdjzu.account.utils;

import org.apache.commons.lang3.tuple.Pair;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author mindgazer
 * @date 2018/08/26
 */
public class TimeUtil {

    public static final String PATTERN_YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    public static final String PATTERN_YYYY_MM_DD = "yyyy-MM-dd";
    public static final String PATTERN_HH_MM_SS = "hh:mm:ss";
    private static final String[] WEEKDAY = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};

    /**
     * 使用指定格式化字符串，对指定的日期时间对象进行格式化
     *
     * @param pattern 日期时间格式字符串，如：yyyy-MM-dd HH:mm:ss
     * @param date    日期时间对象
     * @return 格式化后的日期时间字符串
     */
    public static String format(String pattern, Date date) {
        return new SimpleDateFormat(pattern).format(date);
    }

    /**
     * 使用yyyy-MM-dd HH:mm:ss对传入的日期进行格式化
     *
     * @param date 指定日期对象
     * @return 返回指定日期时间的格式化后的字符串
     */
    public static String formatDateTime(Date date) {
        return format(PATTERN_YYYY_MM_DD_HH_MM_SS, date);
    }

    /**
     * 使用yyyy-MM-dd HH:mm:ss对当前日期进行格式化
     *
     * @return 返回当前日期时间的格式化后的字符串
     */
    public static String formatCurrentDateTime() {
        return formatDateTime(new Date());
    }

    /**
     * 采用yyyy-MM-dd进行格式化
     *
     * @param date 日期对象
     * @return 形如2019-04-06
     */
    public static String formatDate(Date date) {
        return format(PATTERN_YYYY_MM_DD, date);
    }

    /**
     * 使用yyyy-MM-dd的格式对当前日期时间进行格式化
     *
     * @return 形如2019-04-06的字符串
     */
    public static String formatCurrentDate() {
        return formatDate(new Date());
    }

    /**
     * 采用hh:mm:ss的pattern进行格式化
     *
     * @param date 日期对象
     * @return 形如21:00:35
     */
    public static String formatTime(Date date) {
        return format(PATTERN_HH_MM_SS, date);
    }

    /**
     * 使用指定的格式化字符串，对传入的日期时间字符串进行解析
     *
     * @param pattern 日期格式字符串，如：yyyy-MM-dd HH:mm:ss
     * @param str     日期时间字符串，如2019-04-06
     * @return 解析后的Date对象
     */
    public static Date parse(String pattern, String str) {
        try {
            return new SimpleDateFormat(pattern).parse(str);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 测试输入的日期时间字符串是否为指定的格式
     *
     * @param pattern     格式
     * @param dateTimeStr 日期时间字符串
     * @return 如果该日期时间字符串是合法的格式则返回true
     */
    public static boolean testValidFormat(String pattern, String dateTimeStr) {
        try {
            new SimpleDateFormat(pattern).parse(dateTimeStr);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    /**
     * 使用yyyy-MM-dd HH:mm:ss格式解析指定的日期时间字符串
     *
     * @param dateTimeString 合法的日期时间格式，如2019-04-06 10:01:00
     * @return 返回解析后的Date对象
     */
    public static Date parseDateTime(String dateTimeString) {
        return parse(PATTERN_YYYY_MM_DD_HH_MM_SS, dateTimeString);
    }

    /**
     * 使用yyyy-MM-dd日期格式字符串解析指定的日期字符串
     *
     * @param dateString 合法的日期格式，如2019-04-06
     * @return 返回解析后的Date对象
     */
    public static Date parseDate(String dateString) {
        return parse(PATTERN_YYYY_MM_DD, dateString);
    }

    /**
     * 通过指定时间区间，计算出起止日期，结束日期以当前时间为准。开始时间=结束时间-interval
     *
     * @param intervalDay 间隔日s
     * @return 返回起止日期s
     */
    public static Pair<Date, Date> computeStartEndTimeByInterval(int intervalDay) {
        Date endDate = new Date();
        Calendar startCalendar = Calendar.getInstance();
        startCalendar.add(Calendar.DAY_OF_MONTH, intervalDay * -1);
        return Pair.of(startCalendar.getTime(), endDate);
    }

    /**
     * 获取星期中文描述
     *
     * @param date 日期对象
     * @return 周日、周一...
     */
    public static String getWeekdayDesc(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        w = w < 0 ? 0 : w;
        return WEEKDAY[w];
    }

}
