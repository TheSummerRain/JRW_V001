package com.jryz.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

    private static Object lock = new Object();

    private static SimpleDateFormat _sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static SimpleDateFormat _sdf2 = new SimpleDateFormat("yyyy-MM-dd");

    public static String getDateTimeStr(Calendar d) {
        synchronized (lock) {
            return _sdf1.format(d);
        }
    }

    public static String getDateTimeStr(Date d) {
        synchronized (lock) {
            return _sdf1.format(d);
        }
    }

    public static String getDateStr(Calendar d) {
        synchronized (lock) {
            return _sdf2.format(d);
        }
    }

    public static String getDateStr(Date d) {
        synchronized (lock) {
            return _sdf2.format(d);
        }
    }

    public static Date strToDateTime(String str) {
        try {
            synchronized (lock) {
                return _sdf1.parse(str);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Date strToDate(String str) {
        try {
            synchronized (lock) {
                return _sdf2.parse(str);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static String getDateStr(Calendar d, String f) {
        SimpleDateFormat sdf = new SimpleDateFormat(f);
        return sdf.format(d.getTime());
    }

    public static String getDateStr(Date d, String f) {
        SimpleDateFormat sdf = new SimpleDateFormat(f);
        return sdf.format(d);
    }

    public static Calendar getFirstDayOfWeek() {
        Calendar monday = Calendar.getInstance();
        return getADayOfWeek(monday, Calendar.MONDAY);
    }

    public static Calendar getFirstDayOfWeek(Calendar day) {
        Calendar monday = (Calendar) day.clone();
        return getADayOfWeek(monday, Calendar.MONDAY);
    }

    public static Calendar getLastDayOfWeek() {
        Calendar sunday = Calendar.getInstance();
        return getADayOfWeek(sunday, Calendar.SUNDAY);
    }

    public static Calendar getLastDayOfWeek(Calendar day) {
        Calendar sunday = (Calendar) day.clone();
        return getADayOfWeek(sunday, Calendar.SUNDAY);
    }

    /**
     * @param day
     * @param dayOfWeek 一周的起始是周几
     * @return
     */
    private static Calendar getADayOfWeek(Calendar day, int dayOfWeek) {
        int dayIndexOfWeek = day.get(Calendar.DAY_OF_WEEK);
        if (dayIndexOfWeek == dayOfWeek) {
            return day;
        }
        int diffDay = dayOfWeek - dayIndexOfWeek;
        if (dayIndexOfWeek == Calendar.SUNDAY) {
            diffDay -= 7;
        } else if (dayOfWeek == Calendar.SUNDAY) {
            diffDay += 7;
        }
        day.add(Calendar.DATE, diffDay);
        return day;
    }

    /**
     * 获取当前日期前一天
     *
     * @return
     */
    public static Date getNextDay(int nextNum) {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, nextNum);
        Date date = c.getTime();
        return date;
    }


    /**
     * 添加小时
     *
     * @param time
     * @param hour
     * @return
     */
    public static Date addHour(Date time, int hour) {
        Calendar c = Calendar.getInstance();
        c.setTime(time);
        c.add(Calendar.HOUR, hour);
        Date date = c.getTime();
        return date;
    }

    /**
     * 添加分钟
     *
     * @param time
     * @param minute
     * @return
     */
    public static Date addMinute(Date time, int minute) {
        Calendar c = Calendar.getInstance();
        c.setTime(time);
        c.add(Calendar.MINUTE, minute);
        Date date = c.getTime();
        return date;
    }

    /**
     * 添加秒
     *
     * @param time
     * @param second
     * @return
     */
    public static Date addSecond(Date time, int second) {
        Calendar c = Calendar.getInstance();
        c.setTime(time);
        c.add(Calendar.SECOND, second);
        Date date = c.getTime();
        return date;
    }

    /**
     * 添加指定小时、分钟秒
     *
     * @param time
     * @param hour
     * @param minute
     * @param second
     * @return
     */
    public static Date addTime(Date time, int hour, int minute, int second) {
        Calendar c = Calendar.getInstance();
        c.setTime(time);
        c.add(Calendar.HOUR, hour);
        c.add(Calendar.MINUTE, minute);
        c.add(Calendar.SECOND, second);
        Date date = c.getTime();
        return date;
    }

    public static void main(String[] args) {
        System.out.println(addMinute(new Date(), 10));
    }

}
