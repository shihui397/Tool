package com.jsd.library.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static android.R.attr.format;


public class DateUtils {
    private static SimpleDateFormat todayFormate = new SimpleDateFormat("HH:mm");
    private static SimpleDateFormat yesterdayFormate = new SimpleDateFormat("昨天HH:mm");
    private static SimpleDateFormat dateFormate = new SimpleDateFormat("MM月dd日");
    private static SimpleDateFormat simpleDateFormate = new SimpleDateFormat("MM/dd");
    public static SimpleDateFormat yearMonth = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static SimpleDateFormat simpleYearMonth = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    private static final long ONE_DAY = 1 * 24 * 60 * 60 * 1000;

    public static String getNowTime() {
        return yearMonth.format(new Date(System.currentTimeMillis()));
    }

    public static String getDateTime(Date date) {
        return yearMonth.format(date);
    }

    public static String getDateTime(String time) {
        Date date = new Date(time);
        return yearMonth.format(date);
    }

    /**
     * 获取当前年份
     *
     * @return
     */
    public static int getYear() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);

        return year;
    }

    public static int getYear(String time, String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        Date date = null;
        try {
            date = simpleDateFormat.parse(time);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            return calendar.get(Calendar.YEAR);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 获取当前月份
     *
     * @return
     */
    public static int getMonth() {
        Calendar cal = Calendar.getInstance();
        int month = cal.get(Calendar.MONTH);
        return month;
    }

    public static int getMonth(String time, String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        Date date = null;
        try {
            date = simpleDateFormat.parse(time);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            return calendar.get(Calendar.MONTH);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 获取当前月份
     *
     * @return
     */
    public static int getDay() {
        Calendar cal = Calendar.getInstance();
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return day;
    }

    public static int getDay(String time, String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        Date date = null;
        try {
            date = simpleDateFormat.parse(time);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            return calendar.get(Calendar.DAY_OF_MONTH);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 获取当前小时
     *
     * @return
     */
    public static int getHour() {
        Calendar cal = Calendar.getInstance();
        int day = cal.get(Calendar.HOUR_OF_DAY);
        return day;
    }

    /**
     * 根据时间获取值班医生的状态
     *
     * @return
     */
    public static boolean getDoctorTime() {
        int hour = getHour();
        return hour >= 8 && hour < 18;
    }

    /**
     * 获取当前分钟
     *
     * @return
     */
    public static int getMinute() {
        Calendar cal = Calendar.getInstance();
        int day = cal.get(Calendar.MINUTE);
        return day;
    }

    public static long getMillisecond(String currentTime, String fomatType) {
        SimpleDateFormat format = new SimpleDateFormat(fomatType);
        Date currentDate = null;
        try {
            currentDate = format.parse(currentTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (currentDate == null) {
            return 0;
        } else {
            return currentDate.getTime();
        }
    }

    public static long getMillisecond(String currentTime) {
        Date currentDate = new Date(currentTime);

        if (currentDate == null) {
            return 0;
        } else {
            return currentDate.getTime();
        }
    }

    /**
     * 获取时间和当前时间的关系
     *
     * @return
     */
    public static boolean getRelation(String currentTime, String fomatType) {
        boolean isOutDay = false;

        SimpleDateFormat format = new SimpleDateFormat(fomatType);

        Date currentDate = null;

        Date now = new Date();
        String nowTime = format.format(now);

        try {
            currentDate = format.parse(currentTime);
            now = format.parse(nowTime);
            if (currentDate.getTime() >= now.getTime()) {
                isOutDay = true;
            } else {
                isOutDay = false;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return isOutDay;
    }


    /**
     * 转换为时间
     *
     * @param s
     * @return
     */
    public static String getDateTime(String s, String formatType) {
        SimpleDateFormat sd = new SimpleDateFormat(formatType);
        String time = null;
        try {
            time = sd.format(yearMonth.parse(s));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return time;
    }

    /**
     * 转换为时间
     *
     * @param s
     * @return
     */
    public static String getDateTime(String s, String formTpye, String toTpye) {
        SimpleDateFormat formsd = new SimpleDateFormat(formTpye);
        SimpleDateFormat sd = new SimpleDateFormat(toTpye);
        String time = null;
        try {
            time = sd.format(formsd.parse(s));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return time;
    }

    public static int getDayOffset(long time) {
        if (time > 0) {
            time *= 1000;
            long timeOffset = System.currentTimeMillis() - time;
            if (timeOffset > 0) {
                return (int) ((timeOffset / ONE_DAY) + 1);
            }
        }
        return -1;
    }

    public static String getFullYearMoth(long time) {
        if (time > 0) {
            return simpleYearMonth.format(time);
        }
        return "";
    }

    public static String getSimpleTimeString(long pubTime) {
        pubTime *= 1000;
        return getSimpleFakeTimeString(pubTime);
    }

    public static String getSimpleFakeTimeString(long pubTime) {
        if (pubTime <= 0) {
            return "error time";
        }
        Calendar cal = getCalendar();
        long todayStart = cal.getTimeInMillis();
        long tomorrowStart = todayStart + ONE_DAY;
        //今天
        return formatTowCase(pubTime);
    }

    /**
     * @param pubTime
     * @return
     */
    public static String getComplexTimeString(long pubTime) {
        if (pubTime <= 0) {
            return "error time";
        }
        pubTime *= 1000;
        long hourMill = 3600 * 1000; //一小时的毫秒数
        long dayMill = 24 * hourMill; //一天的毫秒值
        long currentTime = System.currentTimeMillis();

        Calendar cal = getCalendar();
        long todayStart = cal.getTimeInMillis();//今天的开始时间
        long lastDayStart = todayStart - dayMill;//昨天的开始时间

        long intervalMill = currentTime - pubTime; //当前时间和发布时间差值毫秒数
        if (intervalMill < hourMill) {//如果差值时间小于一小时，显示多少分钟
            long minute = (intervalMill % hourMill) / 1000 / 60;
            minute = minute > 0 ? minute : 1; //一分钟内显示一分钟
            return minute + "分钟前";
        } else if (pubTime > todayStart && pubTime < todayStart + dayMill) {//今天且超过一小时
            long hour = (intervalMill % dayMill) / hourMill;
            hour = hour > 0 ? hour : 1;
            return hour + "小时前";
        } else if (pubTime >= lastDayStart && pubTime < todayStart) {
            return "昨天";
        } else {//直接显示日期
            return dateFormate.format(new Date(pubTime));
        }
    }

    /**
     * @param time
     * @return 如果是今天返回格式：HH:mm（时:分）  否则返回格式：MM/dd（月/日）
     */
    public static String getPluginItemTime(long time) {
        if (time < 0) {
            return "";
        }
        time *= 1000;
//        return formatTowCase(time);
        return todayFormate.format(time);
    }

    /**
     * @param time
     * @return 格式：MM/dd HH:mm
     */
    public static String getPluginInnerTime(long time) {
        if (time < 0) {
            return "";
        }
        time *= 1000;
        return getAllTime(time);
    }

    public static String getAllTime(long time) {
        return dateFormate.format(time) + " " + todayFormate.format(time);
    }

    private static String formatTowCase(long time) {
        if (isToday(time)) {
            return todayFormate.format(time);
        } else {
            return simpleDateFormate.format(time);
        }
    }

    /**
     * 获取当前时间是某年的第几个周
     *
     * @param currentTime
     * @return
     */
    public static String weekNumOfYear(String currentTime) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        Date date = null;
        try {
            date = format.parse(currentTime);
            Calendar calendar = Calendar.getInstance();
            calendar.setFirstDayOfWeek(Calendar.MONDAY);
            calendar.setMinimalDaysInFirstWeek(7);
            calendar.setTime(date);
            return String.valueOf(calendar.get(Calendar.WEEK_OF_YEAR));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static int weekNumOfYear(String currentTime, String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        Date date = null;
        try {
            date = simpleDateFormat.parse(currentTime);
            Calendar calendar = Calendar.getInstance();
            calendar.setFirstDayOfWeek(Calendar.MONDAY);
            calendar.setMinimalDaysInFirstWeek(7);
            calendar.setTime(date);
            return calendar.get(Calendar.WEEK_OF_YEAR);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static int weekOfYear() {
        return Calendar.getInstance().get(Calendar.WEEK_OF_YEAR);
    }

    /**
     * 获取当前时间是某年的第几个月
     *
     * @param currentTime
     * @return
     */
    public static String monthOfYear(String currentTime) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM");
        Date date = null;
        try {
            date = format.parse(currentTime);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            return String.valueOf(calendar.get(Calendar.MONTH) + 1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String timeOfYear(String currentTime, String formatType) {
        SimpleDateFormat format = new SimpleDateFormat(formatType);
        Date date = null;
        try {
            date = format.parse(currentTime);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            return String.valueOf(calendar.get(Calendar.YEAR));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * @param time
     * @return 格式:mm/dd
     */
    public static String getDailyInfo(long time) {
        if (time < 0) {
            return "";
        }
        time *= 1000;
        return simpleDateFormate.format(time);
    }

    private static boolean isToday(long time) {
        long dayMill = 24 * 3600 * 1000; //一天的毫秒值
        Calendar cal = getCalendar();
        long todayStart = cal.getTimeInMillis();
        long todayEnd = todayStart + dayMill;
        return time > todayStart && time < todayEnd;
    }

    private static Calendar getCalendar() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal;
    }

    public static String getDate() {
        StringBuilder builder = new StringBuilder();
        builder.append(dateFormate.format(new Date(System.currentTimeMillis())));
        builder.append("（" + getWeekDay() + "）");
        builder.append("作业：");
        return builder.toString();
    }

    public static String getWeekDay() {
        Calendar c = Calendar.getInstance();
        switch (c.get(Calendar.DAY_OF_WEEK)) {
            case Calendar.MONDAY:
                return "星期一";
            case Calendar.TUESDAY:
                return "星期二";
            case Calendar.WEDNESDAY:
                return "星期三";
            case Calendar.THURSDAY:
                return "星期四";
            case Calendar.FRIDAY:
                return "星期五";
            case Calendar.SATURDAY:
                return "星期六";
            case Calendar.SUNDAY:
                return "星期日";
            default:
                return "未知星期";
        }
    }

    public static String getCurrentWeekDay(String currentTime) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        Date currentDate = null;
        try {
            currentDate = format.parse(currentTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar c = Calendar.getInstance();
        c.setTime(currentDate);

        switch (c.get(Calendar.DAY_OF_WEEK)) {
            case Calendar.MONDAY:
                return "星期一";
            case Calendar.TUESDAY:
                return "星期二";
            case Calendar.WEDNESDAY:
                return "星期三";
            case Calendar.THURSDAY:
                return "星期四";
            case Calendar.FRIDAY:
                return "星期五";
            case Calendar.SATURDAY:
                return "星期六";
            case Calendar.SUNDAY:
                return "星期日";
            default:
                return "未知星期";
        }
    }

    public static String getCurrentWeekDay(String currentTime, String formatType) {
        SimpleDateFormat format = new SimpleDateFormat(formatType);
        Date currentDate = null;
        try {
            currentDate = format.parse(currentTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar c = Calendar.getInstance();
        c.setTime(currentDate);

        switch (c.get(Calendar.DAY_OF_WEEK)) {
            case Calendar.MONDAY:
                return "星期一";
            case Calendar.TUESDAY:
                return "星期二";
            case Calendar.WEDNESDAY:
                return "星期三";
            case Calendar.THURSDAY:
                return "星期四";
            case Calendar.FRIDAY:
                return "星期五";
            case Calendar.SATURDAY:
                return "星期六";
            case Calendar.SUNDAY:
                return "星期日";
            default:
                return "未知星期";
        }
    }

    public static String getCardWeekDay() {
        Calendar c = Calendar.getInstance();
        switch (c.get(Calendar.DAY_OF_WEEK)) {
            case Calendar.MONDAY:
                return "周一";
            case Calendar.TUESDAY:
                return "周二";
            case Calendar.WEDNESDAY:
                return "周三";
            case Calendar.THURSDAY:
                return "周四";
            case Calendar.FRIDAY:
                return "周五";
            case Calendar.SATURDAY:
                return "周六";
            case Calendar.SUNDAY:
                return "周日";
            default:
                return "未知星期";
        }
    }

    public static int getTimeWeekDay(String currentTime, String formatType) {
        SimpleDateFormat format = new SimpleDateFormat(formatType);
        Date currentDate = null;
        try {
            currentDate = format.parse(currentTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar c = Calendar.getInstance();
        c.setTime(currentDate);

        switch (c.get(Calendar.DAY_OF_WEEK)) {
            case Calendar.MONDAY:
                return 0;
            case Calendar.TUESDAY:
                return 1;
            case Calendar.WEDNESDAY:
                return 2;
            case Calendar.THURSDAY:
                return 3;
            case Calendar.FRIDAY:
                return 4;
            case Calendar.SATURDAY:
                return 5;
            case Calendar.SUNDAY:
                return 6;
            default:
                return -1;
        }
    }

    public static String getTimeString(long time) {
        if (time <= 0) {
            return "error time";
        }
        time *= 1000;
        Calendar cal = getCalendar();
        long todayStart = cal.getTimeInMillis();
        long lastDayStart = todayStart - ONE_DAY;
        long tomorrowStart = todayStart + ONE_DAY;
        long weekStart = todayStart - 7 * ONE_DAY;

        //今天
        if (time >= todayStart && time < tomorrowStart) {
            return todayFormate.format(new Date(time));
        }
        //昨天
        else if (time >= lastDayStart && time < todayStart) {
            return yesterdayFormate.format(new Date(time));
        }
        //前7天
        else if (time >= weekStart) {
            return getWeekDay();
        }
        //7天后
        else {
            return dateFormate.format(new Date(time));
        }
    }

    public static String[] timeFormat(long ms) {// 将毫秒数换算成x分x秒x毫秒
        String[] times = new String[2];
        int ss = 1000;
        int mi = ss * 60;
        long minute = (ms) / mi;
        long second = (ms - (minute * mi)) / ss;
        String strMinute = minute < 10 ? "0" + minute : "" + minute;
        String strSecond = second < 10 ? "0" + second : "" + second;
        times[0] = strMinute;
        times[1] = strSecond;
        return times;
    }

    /**
     * Java 毫秒转换为（天：时：分：秒）方法
     *
     * @param ms 毫秒
     * @return 一个装了天 时 分 秒的数组
     */
    public static String[] timeFormatMore(long ms) {// 将毫秒数换算成x天x时x分x秒x毫秒
        String[] times = new String[4];
        int ss = 1000;
        int mi = ss * 60;
        int hh = mi * 60;
        int dd = hh * 24;

        long day = ms / dd;
        long hour = (ms - day * dd) / hh;
        long minute = (ms - day * dd - hour * hh) / mi;
        long second = (ms - day * dd - hour * hh - minute * mi) / ss;
        String strDay = day < 10 ? "0" + day : "" + day;
        String strHour = hour < 10 ? "0" + hour : "" + hour;
        String strMinute = minute < 10 ? "0" + minute : "" + minute;
        String strSecond = second < 10 ? "0" + second : "" + second;
        times[0] = strDay;
        times[1] = strHour;
        times[2] = strMinute;
        times[3] = strSecond;
        return times;
    }

    /**
     * 日期时间转换器
     *
     * @param date
     * @return
     */
    public static String dateChangeTwo(String date) {
        Date datetime = new Date(date);
        SimpleDateFormat format = new SimpleDateFormat("MM/dd");
        String time = format.format(datetime);
        return time;
    }

    /**
     * 日期时间转换器
     *
     * @param date
     * @return
     */
    public static String dateChangeTwo(String date, String formatType) {
        Date datetime = new Date(date);
        SimpleDateFormat format = new SimpleDateFormat(formatType);
        String time = format.format(datetime);
        return time;
    }

    /**
     * 获取当前时间
     *
     * @param formatType yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String getCurTime(String formatType) {
        Calendar calendar = new GregorianCalendar();
        Date date = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat(formatType);
        // SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        return format.format(date);
    }

    /**
     * @param time
     * @param formatType yyyy/MM/dd
     * @return
     */
    public static String formatTime(Long time, String formatType) {
        String format;
        try {
            SimpleDateFormat formater = new SimpleDateFormat(formatType);
            format = formater.format(new Date(time));
            return format;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取当前时间+/-addDate天
     *
     * @param addDate
     * @param formatType
     * @return
     */
    public static String getCurTimeAddND(int addDate, String formatType) {
        Calendar cal = new GregorianCalendar();
        cal.add(Calendar.DATE, addDate);
        Date date = cal.getTime();
        SimpleDateFormat format = new SimpleDateFormat(formatType);
        return format.format(date);
    }

    /**
     * 获取时间+/-addDate天
     *
     * @param addDate
     * @param formatType
     * @return
     */
    public static String getTimeAddND(String currentTime, int addDate, String formatType) {

        SimpleDateFormat format = new SimpleDateFormat(formatType);

        Date currentDate = null;

        try {
            currentDate = format.parse(currentTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar cal = new GregorianCalendar();
        cal.setTime(currentDate);
        cal.add(Calendar.DATE, addDate);
        Date date = cal.getTime();
        return format.format(date);
    }

    /**
     * 获取时间上一天
     *
     * @return
     */
    public static String getPreDay(String currentTime) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");

        Date date = null;

        try {
            date = format.parse(currentTime);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.DATE, -1);
            date = calendar.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return format.format(date);
    }

    /**
     * 获取时间下一天
     *
     * @return
     */
    public static String getNextDay(String currentTime) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");

        Date date = null;

        try {
            date = format.parse(currentTime);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.DATE, +1);
            date = calendar.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return format.format(date);
    }

    /**
     * 获取时间上一月
     *
     * @return
     */
    public static String getPreMonth(String currentTime) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM");

        Date date = null;

        try {
            date = format.parse(currentTime);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.MONTH, -1);
            date = calendar.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return format.format(date);
    }

    /**
     * 获取时间上一月
     *
     * @return
     */
    public static String getNextMonth(String currentTime) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM");

        Date date = null;

        try {
            date = format.parse(currentTime);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.MONTH, +1);
            date = calendar.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return format.format(date);
    }


    /**
     * 获取当前时间+/-hour
     *
     * @param hour
     * @param formatType
     * @return
     */
    public static String getCurTimeAddH(int hour, String formatType) {
        Calendar cal = new GregorianCalendar();
        cal.add(Calendar.HOUR_OF_DAY, hour);
        Date date = cal.getTime();
        SimpleDateFormat format = new SimpleDateFormat(formatType);
        return format.format(date);
    }

    /**
     * @param datestr 2009-01-12 09:12:22
     * @return 星期几
     */
    public static String getweekdayBystr(String datestr) {
        if ("".equals(datestr)) {
            return "";
        }
        int y = Integer.valueOf(datestr.substring(0, 4));
        int m = Integer.valueOf(datestr.substring(5, 7)) - 1;
        int d = Integer.valueOf(datestr.substring(8, 10));

        Calendar cal = Calendar.getInstance();
        cal.set(y, m, d);
        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK) - 1;// 今天是星期几
        String wstr = null;
        switch (dayOfWeek) {
            case 1:
                wstr = "一";
                break;
            case 2:
                wstr = "二";
                break;
            case 3:
                wstr = "三";
                break;
            case 4:
                wstr = "四";
                break;
            case 5:
                wstr = "五";
                break;
            case 6:
                wstr = "六";
                break;
            case 0:
                wstr = "日";
                break;
            default:
                wstr = "";
                break;
        }
        return "星期" + wstr;
    }

    public static String getweekdayBystrNew(String datestr) {
        if ("".equals(datestr)) {
            return "";
        }
        int y = Integer.valueOf(datestr.substring(0, 4));
        int m = Integer.valueOf(datestr.substring(5, 7)) - 1;
        int d = Integer.valueOf(datestr.substring(8, 10));

        Calendar cal = Calendar.getInstance();
        cal.set(y, m, d);
        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK) - 1;// 今天是星期几
        String wstr = null;
        switch (dayOfWeek) {
            case 1:
                wstr = "一";
                break;
            case 2:
                wstr = "二";
                break;
            case 3:
                wstr = "三";
                break;
            case 4:
                wstr = "四";
                break;
            case 5:
                wstr = "五";
                break;
            case 6:
                wstr = "六";
                break;
            case 0:
                wstr = "日";
                break;
            default:
                wstr = "";
                break;
        }
        return "周" + wstr;
    }

    /**
     * 今天的序号
     *
     * @return
     */
    public static int getDayofWeekIndex() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        return cal.get(Calendar.DAY_OF_WEEK) - 1;
    }

    /**
     * 获取时间串
     *
     * @param longstr 秒
     * @return 1月前 1周前 1天前 1小时前 1分钟前
     */
    public static String getTimeStrByLong(long longstr) {

        Calendar calendar = new GregorianCalendar();
        Date date = calendar.getTime();
        Long clv = date.getTime();
        Long olv = Long.valueOf(longstr);

        calendar.setTimeInMillis(olv * 1000); // 转毫秒
        Date date2 = calendar.getTime();

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String curtime = format.format(date2);

        Long belv = clv - olv * 1000;
        String retStr = "";
        // 24 * 60 * 60 * 1000;
        Long daylong = Long.valueOf("86400000");
        Long hourlong = Long.valueOf("3600000");
        Long minlong = Long.valueOf("60000");

        if (belv >= daylong * 30) {// 月

            Long mul = belv / (daylong * 30);
            retStr = retStr + mul + "月";
            belv = belv % (daylong * 30);
            return retStr + "前";
        }
        if (belv >= daylong * 7) {// 周

            Long mul = belv / (daylong * 7);
            retStr = retStr + mul + "周";
            belv = belv % (daylong * 7);
            return retStr + "前";
        }
        if (belv >= daylong) {// 天

            Long mul = belv / daylong;
            retStr = retStr + mul + "天";
            belv = belv % daylong;
            return retStr + "前";
        }
        if (belv >= hourlong) {// 时
            Long mul = belv / hourlong;
            retStr = retStr + mul + "小时";
            belv = belv % hourlong;
            return retStr + "前";
        }
        if (belv >= minlong) {// 分
            Long mul = belv / minlong;
            retStr = retStr + mul + "分钟";
            return retStr + "前";
        }
        return "";
    }

    /**
     * 今天明天后天
     *
     * @param todaystr
     * @return
     */
    public static String getTodayZh(String todaystr) {
        Calendar calendar = new GregorianCalendar();
        Date date = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String today = format.format(date);
        if (today.equals(todaystr))
            return "今天";

        calendar = new GregorianCalendar();
        calendar.add(Calendar.DATE, 1);
        date = calendar.getTime();
        format = new SimpleDateFormat("yyyy-MM-dd");
        String tomorrow = format.format(date);
        if (tomorrow.equals(todaystr))
            return "明天";

        calendar = new GregorianCalendar();
        calendar.add(Calendar.DATE, 2);
        date = calendar.getTime();
        format = new SimpleDateFormat("yyyy-MM-dd");
        String aftertomo = format.format(date);
        if (aftertomo.equals(todaystr))
            return "后天";
        return "";
    }

    /**
     * 判断时间是否过期
     *
     * @param deadLine
     * @return
     */
    public static boolean isDeadLine(String deadLine) {
        try {
            long currentTimeMillis = System.currentTimeMillis();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = sdf.parse(deadLine);
            long anotherTimeMillis = date.getTime();
            return (currentTimeMillis - anotherTimeMillis) > 0;
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 是否是今天
     *
     * @param timeStr
     * @param pattern
     * @return
     */
    public static boolean isToday(String timeStr, String pattern) {
        try {
            SimpleDateFormat formater = new SimpleDateFormat(pattern);
            Date date1 = formater.parse(timeStr);
            Date date = new Date();
            String otherStr = formater.format(date1);
            String curtimeStr = formater.format(date);
            return otherStr.equals(curtimeStr);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 日期格式化转换
     *
     * @param oldDateStr
     * @param oldPattern
     * @param newPattern
     * @return
     */
    public static String changeFormater(String oldDateStr, String oldPattern, String newPattern) {
        if ("".equals(oldDateStr)) {
            return "";
        }
        try {
            SimpleDateFormat oldFormater = new SimpleDateFormat(oldPattern);
            SimpleDateFormat newFormater = new SimpleDateFormat(newPattern);
            Date date = oldFormater.parse(oldDateStr);
            return newFormater.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 一个星期后的7天
     *
     * @return
     */
    public static String getNextWeekDayStrNew() {
        StringBuffer sb = new StringBuffer();
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.DAY_OF_MONTH, 7);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        sb.append(day + "日");
        sb.append("-");
        cal.add(Calendar.DAY_OF_MONTH, 6);
        day = cal.get(Calendar.DAY_OF_MONTH);
        sb.append(day + "日");
        return sb.toString();
    }

    public static String getCurWeekDayStrNew() {
        StringBuffer sb = new StringBuffer();
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        int day = cal.get(Calendar.DAY_OF_MONTH);
        sb.append(day + "日");
        sb.append("-");
        cal.add(Calendar.DAY_OF_MONTH, 6);
        day = cal.get(Calendar.DAY_OF_MONTH);
        sb.append(day + "日");
        return sb.toString();
    }

    /**
     * 多少时间之前
     *
     * @param time "operatorTime":"2014-02-18 16:39:37"
     * @return
     */
    public static String diffCurTime(String time, String curTime) {
        try {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date2 = df.parse(curTime);
            Date date1 = df.parse(time);
            long l = date2.getTime() - date1.getTime();
            long day = l / (24 * 60 * 60 * 1000);
            long hour = (l / (60 * 60 * 1000) - day * 24);
            long min = ((l / (60 * 1000)) - day * 24 * 60 - hour * 60);
            long s = (l / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
            StringBuffer sBuffer = new StringBuffer();
            if (day > 0) {
                sBuffer.append(day + "天");
            }
            if (hour > 0) {
                sBuffer.append(hour + "小时");
            }
            if (min > 0) {
                sBuffer.append(min + "分");
            }
            if (s > 0) {
                sBuffer.append(s + "秒");
            }
            return sBuffer.toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取当前时间前30分钟
     *
     * @return
     */
    public static String getCurTimeAdd30M() {
        Calendar cal = new GregorianCalendar();
        cal.add(Calendar.MINUTE, 30);
        Date date = cal.getTime();
        SimpleDateFormat format = new SimpleDateFormat("HHmm");
        return format.format(date);
    }

    /**
     * 获取当前时间前+20分钟
     *
     * @return
     */
    public static String getCurTimeAdd20M() {
        Calendar cal = new GregorianCalendar();
        cal.add(Calendar.MINUTE, 20);
        Date date = cal.getTime();
        SimpleDateFormat format = new SimpleDateFormat("HHmm");
        return format.format(date);
    }

    /**
     * 获取当前时间前+40分钟
     *
     * @return
     */
    public static String getCurTimeAdd40M() {
        Calendar cal = new GregorianCalendar();
        cal.add(Calendar.MINUTE, 40);
        Date date = cal.getTime();
        SimpleDateFormat format = new SimpleDateFormat("HHmm");
        return format.format(date);
    }

    /**
     * 两时间相距是否在 一个小时之内
     *
     * @param startPoint
     * @param aimPoint
     * @return
     */
    public static boolean isOutOfOneHour(long startPoint, long aimPoint) {
        return Math.abs(aimPoint - startPoint) > 900000;
    }

    /**
     * 分钟转化为小时
     */
    public static float MinuteToHour(int minute) {
        float hour;
        hour = minute / 60 + 1.0f * (minute % 60) / 60;
        return new BigDecimal(hour).setScale(2, RoundingMode.HALF_UP).floatValue();
    }

    // 获取当前时间所在年的周数
    public static int getWeekOfYear(Date date) {
        Calendar c = new GregorianCalendar();
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.setMinimalDaysInFirstWeek(7);
        c.setTime(date);
        return c.get(Calendar.WEEK_OF_YEAR);
    }

    // 获取当前时间所在年的周数
    public static int getWeekOfYear() {
        Calendar c = new GregorianCalendar();
        return c.get(Calendar.WEEK_OF_YEAR);
    }

    // 获取当前时间所在年的最大周数
    public static int getMaxWeekNumOfYear(int year) {
        Calendar c = new GregorianCalendar();
        c.set(year, Calendar.DECEMBER, 31, 23, 59, 59);

        return getWeekOfYear(c.getTime());
    }


    // 获取某年的第几周的开始日期
    public static Date getFirstDayOfWeek(int year, int week) {
        Calendar c = new GregorianCalendar();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, Calendar.JANUARY);
        c.set(Calendar.DATE, 1);

        Calendar cal = (GregorianCalendar) c.clone();
        cal.add(Calendar.DATE, week * 7);

        return getFirstDayOfWeek(cal.getTime());
    }

    // 获取某年的第几周的开始日期
    public static String getFirstDayOfWeek(String time, String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        Calendar c = new GregorianCalendar();
        c.set(Calendar.YEAR, getYear(time, format));
        c.set(Calendar.MONTH, Calendar.JANUARY);
        c.set(Calendar.DATE, 1);

        Calendar cal = (GregorianCalendar) c.clone();
        cal.add(Calendar.DATE, weekNumOfYear(time, format) * 7);
        return simpleDateFormat.format(getFirstDayOfWeek(cal.getTime()));
    }


    // 获取某年的第几周的结束日期
    public static Date getLastDayOfWeek(int year, int week) {
        Calendar c = new GregorianCalendar();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, Calendar.JANUARY);
        c.set(Calendar.DATE, 1);

        Calendar cal = (GregorianCalendar) c.clone();
        cal.add(Calendar.DATE, week * 7);

        return getLastDayOfWeek(cal.getTime());
    }

    // 获取某年的第几周的结束日期
    public static String getLastDayOfWeek(String time, String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        Calendar c = new GregorianCalendar();
        c.set(Calendar.YEAR, getYear(time, format));
        c.set(Calendar.MONTH, Calendar.JANUARY);
        c.set(Calendar.DATE, 1);

        Calendar cal = (GregorianCalendar) c.clone();
        cal.add(Calendar.DATE, weekNumOfYear(time, format) * 7);

        return simpleDateFormat.format(getLastDayOfWeek(cal.getTime()));
    }

    // 获取当前时间所在周的开始日期
    public static Date getFirstDayOfWeek(Date date) {
        Calendar c = new GregorianCalendar();
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.setTime(date);
        c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek()); // Monday
        return c.getTime();
    }

    // 获取当前时间所在周的结束日期
    public static Date getLastDayOfWeek(Date date) {
        Calendar c = new GregorianCalendar();
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.setTime(date);
        c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek() + 6); // Sunday
        return c.getTime();
    }
}
