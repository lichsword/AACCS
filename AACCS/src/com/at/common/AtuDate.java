package com.at.common;

import java.util.Calendar;
import java.util.Date;

/**
 * @author lichs_000
 */
public class AtuDate {

    public static final long ONE_SECOND = 1000;
    /**
     * 1分钟的毫秒时间
     */
    public static final long ONE_MUNITE = 60 * ONE_SECOND;

    /**
     * 1小时的毫秒时间
     */
    public static final long ONE_HOUR = 60 * ONE_MUNITE;

    /**
     * 1天的毫秒时间
     */
    public static final long ONE_DAY = 24 * ONE_HOUR;

    private static final String LINK_CHAR = "-";
    private static final String LINK_CHAR_TIME = ":";

    /**
     * 北京东8区距离英国UTC时间为8个小时，即28800000l毫秒
     */
    private static final long BEIJIN_DIFF_UTC = 28800000l;

    /**
     * format:"year-month-day hours:minutes:seconds"
     * 
     * @return formated date string
     */
    public static String getCurrentDateAsString() {
        long time = System.currentTimeMillis();
        return getDateAsString(time);
    }

    /**
     * format:"year-month-day"
     * 
     * @return formated date string
     */
    public static String getCurrentDateAsString2() {
        long time = System.currentTimeMillis();
        return getDateAsString2(time);
    }

    /**
     * Year from 1900
     * 
     * @return
     */
    public static Date getCurrentDate() {
        long time = System.currentTimeMillis();
        return getDate(time);
    }

    /**
     * 必位8位int数 e.g.: 20111223
     * 
     * @return
     */
    public static int getCurrentDateAsInt() {
        Date date = getCurrentDate();
        int day = date.getDay();
        int month = date.getMonth();
        int year = date.getYear();
        return year * 10000 + month * 100 + day;
    }

    /**
     * the local system time in milliseconds.
     * 
     * @return Returns the current system time in milliseconds since January 1,
     *         1970 00:00:00 UTC
     */
    public static long getCurrentDateAsLong() {
        return System.currentTimeMillis();
    }

    /**
     * Year from 1900
     * 
     * @param millisecond
     * @return
     */
    public static Date getDate(long millisecond) {
        Date date = new Date(millisecond);
        return date;
    }

    /**
     * format:"year-month-day hours:minutes:seconds"
     * 
     * @param millisecond
     *            (since Jan. 1, 1970, midnight GMT.)
     * @return 将指定毫秒时间(since Jan. 1, 1970, midnight GMT.)转为String输出
     */
    public static String getDateAsString(long millisecond) {
        Date date = new Date(millisecond);
        int year = date.getYear() + 1900;
        int month = date.getMonth() + 1;
        int day = date.getDate();
        int hours = date.getHours();
        int minutes = date.getMinutes();
        int seconds = date.getSeconds();
        return year + LINK_CHAR + month + LINK_CHAR + day + " " + hours + LINK_CHAR_TIME + minutes + LINK_CHAR_TIME
                + seconds;
    }

    public static String getDateAsString_YYYY_MM_DD_hh_mm(long millisecond) {
        Date date = new Date(millisecond);
        int year = date.getYear() + 1900;
        int month = date.getMonth() + 1;
        int day = date.getDate();
        int hours = date.getHours();
        int minutes = date.getMinutes();
        return String.format("%d-%02d-%02d %02d:%02d", year, month, day, hours, minutes);
    }

    public static String getDateAsString_YYYY_MM_DD_hh_mm_ss(final String template, long millisecond) {
        Date date = new Date(millisecond);
        int year = date.getYear() + 1900;
        int month = date.getMonth() + 1;
        int day = date.getDate();
        int hours = date.getHours();
        int minutes = date.getMinutes();
        int seconds = date.getSeconds();
        return String.format(template, year, month, day, hours, minutes, seconds);
    }

    /**
     * 
     * 
     * @param template
     *            仅限精确到毫秒的模板
     * @param millisecond
     * @return
     */
    public static String getDateAsString_YYYY_MM_DD_hh_mm_ss_ms(final String template, long millisecond) {
        Date date = new Date(millisecond);
        int year = date.getYear() + 1900;
        int month = date.getMonth() + 1;
        int day = date.getDate();
        int hours = date.getHours();
        int minutes = date.getMinutes();
        int seconds = date.getSeconds();
        long milseconds = millisecond % 1000;
        return String.format(template, year, month, day, hours, minutes, seconds, milseconds);
    }

    /**
     * 2015-12-11 12:45:10
     */
    public static final String TEMPLATE_YYYY_MM_DD_HH_MM_SS_1 = "%d-%02d-%02d %02d:%02d:%02d";
    /**
     * 2015-12-11_12_45_10
     */
    public static final String TEMPLATE_YYYY_MM_DD_HH_MM_SS_2 = "%d-%02d-%02d_%02d_%02d_%02d";
    /**
     * 2015-12-11_12_45_10_123(精确到毫秒)
     */
    public static final String TEMPLATE_YYYY_MM_DD_HH_MM_SS_MS_1 = "%d-%02d-%02d_%02d_%02d_%02d_%03d";
    /**
     * 2015-12-11 12:45:10.123(精确到毫秒)
     */
    public static final String TEMPLATE_YYYY_MM_DD_HH_MM_SS_MS_2 = "%d-%02d-%02d %02d:%02d:%02d.%03d";

    /**
     * 1(days) + 3(hours) + 28(munites) + 24(seconds)
     */
    private static final String FORMAT_MILLI_SECOND_AS_STRING = "%d(days) + %d(hours) + %d(munites) + %d(seconds)";

    /**
     * format: "XX(days) + XX(hours) + XX(munites) + XX(seconds)"
     * 
     * @param millisecond
     * @return 将指定毫秒时间，转为String 输出
     */
    public static String getMillisecondAsString(long millisecond) {
        int days = (int) (millisecond / ONE_DAY);
        int hours = (int) ((millisecond % ONE_DAY) / ONE_HOUR);
        int munites = (int) ((millisecond % ONE_HOUR) / ONE_MUNITE);
        int seconds = (int) (millisecond % ONE_MUNITE);
        return String.format(FORMAT_MILLI_SECOND_AS_STRING, days, hours, munites, seconds);
    }

    /**
     * format: "xx天xx小时xx分xx秒xx毫秒"（若大头为0则会自动省略，比如：1500=>1秒500毫秒）
     * 
     * @param millisecond
     * @return 将指定毫秒时间，转为String 输出
     */
    public static String getMillisecondAsString2(long millisecond) {
        int days = (int) (millisecond / ONE_DAY);
        int hours = (int) ((millisecond % ONE_DAY) / ONE_HOUR);
        int munites = (int) ((millisecond % ONE_HOUR) / ONE_MUNITE);
        int seconds = (int) ((millisecond % ONE_MUNITE) / ONE_SECOND);
        int mileseconds = (int) (millisecond % ONE_SECOND);

        StringBuilder sb = new StringBuilder();

        if (days > 0) {
            sb.append(String.format("%d天", days));
        }
        if (hours > 0) {
            sb.append(String.format("%d小时", hours));
        }
        if (munites > 0) {
            sb.append(String.format("%d分", munites));
        }
        if (seconds > 0) {
            sb.append(String.format("%d秒", seconds));
        }
        if (mileseconds > 0) {
            sb.append(String.format("%d毫秒", mileseconds));
        }

        return sb.toString();
    }

    /**
     * Returns this Date as a millisecond value. The value is the number of
     * milliseconds since Jan. 1, 1970, midnight GMT.
     * 
     * @return the number of milliseconds since Jan. 1, 1970, midnight GMT.
     */
    public static long formatDateToLong(int year, int month, int day, int hour, int minute, int second) {
        Date date = new Date(year, month, day, hour, minute, second);
        return date.getTime();
    }

    /**
     * format:"year-month-day"
     * 
     * @param millisecond
     * @return e.g.: 2012-01-10
     */
    public static String getDateAsString2(long millisecond) {
        Date date = new Date(millisecond);
        int year = date.getYear() + 1900;
        int month = date.getMonth() + 1;
        int day = date.getDate();
        return String.format("%d-%d-%d", year, month, day);
    }

    /**
     * format:"year/month/day"
     * 
     * @param millisecond
     * @return e.g.: 2012/01/10
     */
    public static String getDateAsString3(long millisecond) {
        Date date = new Date(millisecond);
        int year = date.getYear() + 1900;
        int month = date.getMonth() + 1;
        int day = date.getDate();
        return String.format("%d/%d/%d", year, month, day);
    }

    /**
     * format:"month/day/year"
     * 
     * @param millisecond
     * @return e.g.: 01/10/2012
     */
    public static String getDateAsString4(long millisecond) {
        Date date = new Date(millisecond);
        int year = date.getYear() + 1900;
        int month = date.getMonth() + 1;
        int day = date.getDate();
        return String.format("%02d/%02d/%d", month, day, year);
    }

    /**
     * 星期几？first day of week.
     * 
     * @param millisecond
     * @return
     */
    public static int getDayOfWeek() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.DAY_OF_WEEK);
    }

    public static int getDayOfWeek(long millisecond) {
        Date date = new Date(millisecond);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_WEEK);
    }

    public static String getDayOfWeekString() {
        String result = "";
        int dayOfWeek = getDayOfWeek();
        switch (dayOfWeek) {
        case Calendar.MONDAY:
            result = "星期一";
            break;
        case Calendar.TUESDAY:
            result = "星期二";
            break;
        case Calendar.WEDNESDAY:
            result = "星期三";
            break;
        case Calendar.THURSDAY:
            result = "星期四";
            break;
        case Calendar.FRIDAY:
            result = "星期五";
            break;
        case Calendar.SATURDAY:
            result = "星期六";
            break;
        case Calendar.SUNDAY:
            result = "星期天";
            break;
        }
        return result;
    }

    /**
     * C#的时间需要除以10000才是以毫秒为单位
     */
    private static final int CSHARP_TIME_FACT = 10000;

    /**
     * C#的基准时间(除以100000后)要加上62135596800000l，才是java的以1970年为基准的时间
     */
    private static final long CSHARP_TIME_DIFF = 62135596800000l;

    /**
     * 将C#的时间统一成java的时间
     * 
     * @param CSharpTime
     * @return 转换后的java时间
     */
    public static long formatCSharpTimeToJavaTime(long CSharpTime) {
        return CSharpTime / CSHARP_TIME_FACT - CSHARP_TIME_DIFF;
    }

    /**
     * 将java的时间统一成C#的时间
     * 
     * @param javaTime
     * @return 转换后的C#时间
     */
    public static long formatJavaTimeToCSharpTime(long javaTime) {
        return (javaTime + CSHARP_TIME_DIFF) * CSHARP_TIME_FACT;
    }

    /**
     * 将C#的时间统一成java的时间
     * 
     * @param CSharpTime
     * @param modfiyBeijinDiff
     *            是否要计算北京东八区的时差
     * @return
     */
    public static long formatCSharpTimeToJavaTime(long CSharpTime, boolean modfiyBeijinDiff) {
        if (modfiyBeijinDiff) {
            return CSharpTime / CSHARP_TIME_FACT - CSHARP_TIME_DIFF - BEIJIN_DIFF_UTC;
        } else {
            return CSharpTime / CSHARP_TIME_FACT - CSHARP_TIME_DIFF;
        }
    }
}
