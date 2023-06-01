package com.lx.net.common.utils;

import android.annotation.SuppressLint;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;


/**
 * 时间相关工具类
 */
public class TimeUtils {

    private TimeUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    @SuppressLint("ConstantLocale")
    public static final SimpleDateFormat DEFAULT_SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
    @SuppressLint("ConstantLocale")
    public static final SimpleDateFormat DEFAULT_YMD = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    @SuppressLint("ConstantLocale")
    public static final SimpleDateFormat DEFAULT_SDF_ZIGUO = new SimpleDateFormat("MM-dd HH:mm", Locale.getDefault());

    public static long timeInMillisPerDay = 24 * 60 * 60 * 1000;

    /**
     * @param date
     * @return 当日的初始时间戳
     */
    public static long getStartTimeStampOfDay(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        long timeStamp = date.getTime();
        long hourDiff = c.get(Calendar.HOUR_OF_DAY) * 60 * 60 * 1000;
        long minuteDiff = c.get(Calendar.MINUTE) * 60 * 1000;
        long secondDiff = c.get(Calendar.SECOND) * 1000;
        long milliSecondDiff = c.get(Calendar.MILLISECOND);
        return timeStamp - hourDiff - minuteDiff - secondDiff - milliSecondDiff;
    }

    /**
     * @param year
     * @return 当日的初始时间戳
     */
    public static long getStartTimeStampOfYear(int year) {
        Calendar c = Calendar.getInstance();
        c.set(year, 0, 1, 0, 0);
        long milliSecondDiff = c.get(Calendar.MILLISECOND);
        return c.getTimeInMillis() - milliSecondDiff;
    }

    /**
     * 获取日期 2018 1.18
     * @param day 0今天  1前一天  2前两天
     * @return
     */
    public static String getTimeYMD(int day) {
        //获取明天的最早时间戳
        long startTimeStampOfDay = getStartTimeStampOfDay(new Date(System.currentTimeMillis()));
        return getYYYYMMdd(startTimeStampOfDay - day * timeInMillisPerDay);
    }
    /**
     * 获取日期 2018 1.18
     * @param day 0今天  1前一天  2前两天
     * @return
     */
    public static String getTimeYMD2(int day) {
        //获取明天的最早时间戳
        long startTimeStampOfDay = getStartTimeStampOfDay(new Date(System.currentTimeMillis()));
        return getYYYY_MM_dd(startTimeStampOfDay - day * timeInMillisPerDay);
    }
    /**
     * 获取日期 2018 1.18
     * @param day 0明天  1.后天  2.大后天
     * @return
     */
    public static String getTimeYMD3(int day) {
        //获取明天的最早时间戳
        long startTimeStampOfDay = getStartTimeStampOfDay(new Date(System.currentTimeMillis()));
        return getYYYY_MM_dd(startTimeStampOfDay + day * timeInMillisPerDay);
    }

    /**
     * 获取当天的最后一个时间字符串 2018 1.18 23:45
     * @param day 0今天  1前一天  2前两天
     * @return
     */
    public static String getDataFinishedTimeYMDHM(int day) {
        //获取明天的最早时间戳
        long startTimeStampOfDay = getStartTimeStampOfDay(new Date(System.currentTimeMillis() + timeInMillisPerDay));
        return getYYYYMMddHHmm(startTimeStampOfDay - day * timeInMillisPerDay - 15 * 60 * 1000);
    }

    /**
     * 获取当天的最后一个时间字符串 2018 1.18 23:45
     * @param milliseconds
     * @return
     */
    public static String getDataFinishedTimeYMDHM(long milliseconds) {
        //获取明天的最早时间戳
        long startTimeStampOfDay = getStartTimeStampOfDay(new Date(milliseconds + timeInMillisPerDay));
        return getYYYYMMddHHmm(startTimeStampOfDay - 15 * 60 * 1000);
    }

    /**
     * 将时间戳转为时间字符串
     * <p>格式为yyyy-MM-dd HH:mm:ss</p>
     *
     * @param milliseconds 毫秒时间戳
     * @return 时间字符串
     */
    public static String milliseconds2String(long milliseconds) {
        return milliseconds2String(milliseconds, DEFAULT_SDF);
    }

    public static String getHHmmaa(long timeInMillis) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm aa", Locale.ENGLISH);
        return simpleDateFormat.format(new Date(timeInMillis));
    }

    /**
     * 将时间戳转为时间字符串
     * <p>格式为yyyy-MM-dd HH:mm:ss</p>
     *
     * @param milliseconds 毫秒时间戳
     * @return 时间字符串
     */
    public static String milliseconds2YMDHM(long milliseconds) {
        return milliseconds2String(milliseconds, new SimpleDateFormat("yyyy.MM.dd HH:mm", Locale.getDefault()));
    }
    /**
     * 将时间戳转为时间字符串
     * <p>格式为yyyy-MM-dd HH:mm:ss</p>
     *
     * @param milliseconds 毫秒时间戳
     * @return 时间字符串
     */
    public static String milliseconds2YMDHMS2(long milliseconds) {
        return milliseconds2String(milliseconds, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()));
    }

    /**
     * 将时间戳转为时间字符串
     * <p>格式为用户自定义</p>
     *
     * @param milliseconds 毫秒时间戳
     * @param format       时间格式
     * @return 时间字符串
     */
    public static String milliseconds2String(long milliseconds, SimpleDateFormat format) {
        return format.format(new Date(milliseconds));
    }

    /**
     * 将时间字符串转为时间戳
     * <p>格式为yyyy-MM-dd HH:mm:ss</p>
     *
     * @param time 时间字符串
     * @return 毫秒时间戳
     */
    public static long string2Milliseconds(String time) {
        return string2Milliseconds(time, DEFAULT_SDF);
    }
    /**
     * 将时间字符串转为时间戳
     * <p>格式为yyyy-MM-dd HH:mm:ss</p>
     *
     * @param time 时间字符串
     * @return 毫秒时间戳
     */
    public static long stringYMD(String time) {
        return string2Milliseconds(time, DEFAULT_YMD);
    }

    /**
     * 将时间字符串转为时间戳
     * <p>格式为用户自定义</p>
     *
     * @param time   时间字符串
     * @param format 时间格式
     * @return 毫秒时间戳
     */
    public static long string2Milliseconds(String time, SimpleDateFormat format) {
        try {
            return format.parse(time).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * 将时间字符串转为Date类型
     * <p>格式为yyyy-MM-dd HH:mm:ss</p>
     *
     * @param time 时间字符串
     * @return Date类型
     */
    public static Date string2Date(String time) {
        return string2Date(time, DEFAULT_SDF);
    }
    /**
     * 将时间字符串转为Date类型
     * <p>格式为MM-dd HH:mm</p>
     *
     * @param time 时间字符串
     * @return Date类型
     */
    public static Date stringDateZigo(String time) {
        return string2Date(time, DEFAULT_SDF_ZIGUO);
    }

    /**
     * 将时间字符串转为Date类型
     * <p>格式为用户自定义</p>
     *
     * @param time   时间字符串
     * @param format 时间格式
     * @return Date类型
     */
    public static Date string2Date(String time, SimpleDateFormat format) {
        return new Date(string2Milliseconds(time, format));
    }

    /**
     * 将Date类型转为时间字符串
     * <p>格式为yyyy-MM-dd HH:mm:ss</p>
     *
     * @param time Date类型时间
     * @return 时间字符串
     */
    public static String date2String(Date time) {
        return date2String(time, DEFAULT_SDF);
    }

    /**
     * 将Date类型转为时间字符串
     * <p>格式为用户自定义</p>
     *
     * @param time   Date类型时间
     * @param format 时间格式
     * @return 时间字符串
     */
    public static String date2String(Date time, SimpleDateFormat format) {
        return format.format(time);
    }

    /**
     * 将Date类型转为时间戳
     *
     * @param time Date类型时间
     * @return 毫秒时间戳
     */
    public static long date2Milliseconds(Date time) {
        return time.getTime();
    }

    /**
     * 将时间戳转为Date类型
     *
     * @param milliseconds 毫秒时间戳
     * @return Date类型时间
     */
    public static Date milliseconds2Date(long milliseconds) {
        return new Date(milliseconds);
    }

    /**
     * 获取当前时间字符串
     * <p>格式为yyyy-MM-dd HH:mm:ss</p>
     *
     * @return 时间字符串
     */
    public static String getCurTimeString() {
        return date2String(new Date());
    }

    /**
     * 获取当前时间字符串
     * <p>格式为用户自定义</p>
     *
     * @param format 时间格式
     * @return 时间字符串
     */
    public static String getCurTimeString(SimpleDateFormat format) {
        return date2String(new Date(), format);
    }

    /**
     * 获取当前时间Date
     * <p>Date类型</p>
     *
     * @return Date类型时间
     */
    public static Date getCurTimeDate() {
        return new Date();
    }

    /**
     * @return 获取当前年份
     */
    public static int getCurrentYear() {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        return c.get(Calendar.YEAR);
    }

    /**
     * @return 获取当前月份
     */
    public static int getCurrentMonth() {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        return c.get(Calendar.MONTH);
    }

    /**
     * @return 获取当前日
     */
    public static int getCurrentDay() {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        return c.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * @return 获取当前小时
     */
    public static int getCurrentHour() {
        Date date = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.HOUR_OF_DAY);
    }

    /**
     * @return 获取当前分钟
     */
    public static int getCurrentMinute() {
        Date date = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.MINUTE);
    }

    /**
     * @return 获取当前分钟
     */
    public static int getMinute(long timeInMillis) {
        Date date = new Date(timeInMillis);
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.MINUTE);
    }

    public static int getHour(long timeInMillis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeInMillis);
        return calendar.get(Calendar.HOUR_OF_DAY);
    }

    public static int getMonth(long timeInMillis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeInMillis);
        return calendar.get(Calendar.MONTH);
    }

    public static int getYear(long timeInMillis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeInMillis);
        return calendar.get(Calendar.YEAR);
    }

    /**
     * 获取星期
     * <p>time格式为yyyy-MM-dd HH:mm:ss</p>
     *
     * @param time 时间字符串
     * @return 星期
     */
    public static String getWeek(String time) {
        return new SimpleDateFormat("EEEE", Locale.getDefault()).format(string2Date(time));
    }

    /**
     * 获取星期
     *
     * @param time   时间字符串
     * @param format 时间格式
     * @return 星期
     */
    public static String getWeek(String time, SimpleDateFormat format) {
        return new SimpleDateFormat("EEEE", Locale.getDefault()).format(string2Date(time, format));
    }

    /**
     * 获取星期
     *
     * @param time Date类型时间
     * @return 星期
     */
    public static String getWeek(Date time) {
        return new SimpleDateFormat("EEEE", Locale.getDefault()).format(time);
    }

    /**
     * 获取星期
     * <p>注意：周日的Index才是1，周六为7</p>
     * <p>time格式为yyyy-MM-dd HH:mm:ss</p>
     *
     * @param time 时间字符串
     * @return 1...5
     */
    public static int getWeekIndex(String time) {
        Date date = string2Date(time);
        return getWeekIndex(date);
    }

    /**
     * 获取星期
     * <p>注意：周日的Index才是1，周六为7</p>
     *
     * @param time   时间字符串
     * @param format 时间格式
     * @return 1...7
     */
    public static int getWeekIndex(String time, SimpleDateFormat format) {
        Date date = string2Date(time, format);
        return getWeekIndex(date);
    }

    /**
     * 获取星期
     * <p>注意：周日的Index才是1，周六为7</p>
     *
     * @param time Date类型时间
     * @return 1...7
     */
    public static int getWeekIndex(Date time) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(time);
        return cal.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * 获取月份中的第几周
     * <p>注意：国外周日才是新的一周的开始</p>
     *
     * @param time Date类型时间
     * @return 1...5
     */
    public static int getWeekOfMonth(Date time) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(time);
        return cal.get(Calendar.WEEK_OF_MONTH);
    }

    /**
     * 获取年份中的第几周
     * <p>注意：国外周日才是新的一周的开始</p>
     * <p>time格式为yyyy-MM-dd HH:mm:ss</p>
     *
     * @param time 时间字符串
     * @return 1...54
     */
    public static int getWeekOfYear(String time) {
        Date date = string2Date(time);
        return getWeekOfYear(date);
    }

    /**
     * 获取年份中的第几周
     * <p>注意：国外周日才是新的一周的开始</p>
     *
     * @param time   时间字符串
     * @param format 时间格式
     * @return 1...54
     */
    public static int getWeekOfYear(String time, SimpleDateFormat format) {
        Date date = string2Date(time, format);
        return getWeekOfYear(date);
    }

    /**
     * 获取年份中的第几周
     * <p>注意：国外周日才是新的一周的开始</p>
     *
     * @param time Date类型时间
     * @return 1...54
     */
    public static int getWeekOfYear(Date time) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(time);
        return cal.get(Calendar.WEEK_OF_YEAR);
    }

    /**
     * 获取指定月份的天数
     * @param timeInMillis
     * @return
     */
    public static int getDayCountTheMonthForLong(long timeInMillis) {
        Date date = new Date(timeInMillis);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    /**
     * 毫秒数对应的星期几
     * @param timeInMillis
     */
    public static int getDayCountOfWeekFromLong(long timeInMillis) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date(timeInMillis));
        return cal.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * 毫秒值对应的几号
     *
     * @param timeInMillis
     * @return
     */
    public static int getDayIndexOfMonth(long timeInMillis) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd");
        String format = simpleDateFormat.format(new Date(timeInMillis));
        return Integer.parseInt(format);
    }

    /**
     * 得到当月的天数
     *
     * @param timeInMillis
     * @return
     */
    public static int getDayCountForMonth(long timeInMillis) {
        Date date = new Date(timeInMillis);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    /**
     * 得到当月的天数
     *
     * @return
     */
    public static int getDayCountForMonth(int year, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, 1);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    /**
     * 得到当年的天数
     *
     * @param timeInMillis
     * @return
     */
    public static int getDayCountForYear(long timeInMillis) {
        Date date = new Date(timeInMillis);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.getActualMaximum(Calendar.DAY_OF_YEAR);
    }

    public static int getCurrentDayIndexForYear() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        return calendar.get(Calendar.DAY_OF_YEAR);
    }

    public static String getHH(long timeInMillis) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH");
        return simpleDateFormat.format(new Date(timeInMillis));
    }

    public static String getHHmm(long timeInMillis) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        return simpleDateFormat.format(new Date(timeInMillis));
    }

    public static String getMMdd(long timeInMillis) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM.dd");
        return simpleDateFormat.format(new Date(timeInMillis));
    }

    public static String getMMdd(String pattern, long timeInMillis) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat.format(new Date(timeInMillis));
    }

    public static String getYYYYMM(String pattern, long timeInMillis) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat.format(new Date(timeInMillis));
    }

    public static String getYYYYMMdd(String pattern, long timeInMillis) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat.format(new Date(timeInMillis));
    }

    public static String getYYYYMMdd(long timeInMillis) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd");
        return simpleDateFormat.format(new Date(timeInMillis));
    }

    public static String getYYYY_MM_dd(long timeInMillis) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.format(new Date(timeInMillis));
    }

    public static String getYYYY_MM(long timeInMillis) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM");
        return simpleDateFormat.format(new Date(timeInMillis));
    }

    public static String getYYYYMMddHHmm(long timeInMillis) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        return simpleDateFormat.format(new Date(timeInMillis));
    }

    public static String getYYYYMMddHHmmss(long timeInMillis) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        return simpleDateFormat.format(new Date(timeInMillis));
    }

    public static String getYYYYMMddZiguo(long timeInMillis) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        return simpleDateFormat.format(new Date(timeInMillis));
    }

    /**
     * @param timeInMillis
     * @return 时间戳对应的日期小时和分钟数 格式:3月28日 12:12
     */
    public static String getMMddHHmm( long timeInMillis) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd HH:mm");
        return simpleDateFormat.format(new Date(timeInMillis));
    }
    /**
     * @param timeInMillis
     * @return 时间戳对应的日期小时和分钟数 格式:3月28日 12:12
     */
    public static String getMMddHHmm_s( long timeInMillis) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd HH:mm");
        return simpleDateFormat.format(new Date(timeInMillis*1000));
    }
    /**
     * @param timeInMillis
     * @return 时间戳对应的日期小时和分钟数 格式:3月28日 12:12
     */
    public static String getMMddHHmm_s2( long timeInMillis) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd HH:mm");
        return simpleDateFormat.format(new Date(timeInMillis));
    }
    /**
     * @param timeInMillis
     * @return 时间戳对应的日期小时和分钟数 格式:3月28日 12:12
     */
    public static String getMMddHHmm_s_0( long timeInMillis) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd");
        String upadateTime = simpleDateFormat.format(new Date(timeInMillis*1000));
        Log.d("日期", "getMMddHHmm_s_0: " + upadateTime + " 时间戳 " + timeInMillis);
        if (upadateTime != null && upadateTime.length() > 0){
            if (upadateTime.charAt(0) == '0'){
                upadateTime = upadateTime.substring(1);
            }
        }
        return upadateTime;
    }

    @SuppressLint("DefaultLocale")
    public static String generateTime(long time) {
        int totalSeconds = (int) (time / 1000);
        int seconds = totalSeconds % 60;
        int minutes = (totalSeconds / 60) % 60;
        int hours = totalSeconds / 3600;

        return hours > 0 ? String.format("%02dh%02dm%02ds", hours, minutes, seconds) : String.format("%02dm%02ds", minutes, seconds);
    }

    /**
     * 获取日的时间集合
     * @return
     * @param context
     */
//    public static List<String> getDays(Context context) {
//        List<String> days = new ArrayList<>();
//        long startTime = new Date(117, 0, 1).getTime();
//        Date date = new Date();
//        long currentTime = getStartTimeStampOfDay(date);
//        SimpleDateFormat sdf = new SimpleDateFormat(context.getString(R.string.time_format_mmdd));
//        int dayCount = (int) ((currentTime - startTime) / timeInMillisPerDay);
////        days.add(context.getString(R.string.today));
////        days.add(context.getString(R.string.yesterday));
//        for (int i = 0; i < dayCount; i++) {
//            String data = sdf.format(new Date(currentTime - i * timeInMillisPerDay));
//            days.add(data);
//        }
//        return days;
//    }

    /**
     * 获取周的时间集合
     * @return
     */
//    public static List<String> getWeeks(Context context) {
//        List<String> weekDatas = new ArrayList<>();
//        //获取2017年1月1日的时间戳 1月1日星期天
//        long startTime = new Date(117, 0, 1).getTime();
//        //获取当天最小时间戳
//        Date date = new Date();
//        long endTime = getStartTimeStampOfDay(date) + timeInMillisPerDay;
//        SimpleDateFormat sdf = new SimpleDateFormat(context.getString(R.string.time_format_yyyymd), new Locale("en","US"));
//        SimpleDateFormat sdf2 = new SimpleDateFormat(context.getString(R.string.time_format_d_), new Locale("en","US"));
//        SimpleDateFormat sdf3 = new SimpleDateFormat(context.getString(R.string.time_formar_md), new Locale("en","US"));
//        int weekCount = (int) ((endTime - startTime) / (timeInMillisPerDay * 7));
//        int dayCount = (int) ((endTime - startTime) % (timeInMillisPerDay * 7));
//        if (dayCount != 0) {
//            weekCount += 1;
//        }
//        for (int i = 0; i < weekCount; i++) {
//            long entTime = startTime + 6 * timeInMillisPerDay;
//            String startDate = sdf.format(new Date(startTime));
//            String entDate;
//            if (getMonth(startTime) == getMonth(entTime)) {
//                entDate = sdf2.format(new Date(entTime));
//            } else {
//                // TODO 修改一个星期在两个月中间缺失 - 的bug
////                entDate = sdf3.format(new Date(entTime));
//                entDate = "-" + sdf3.format(new Date(entTime));
//            }
//            weekDatas.add(startDate + entDate);
////            if (dayCount == 0) {
////                if (i == weekCount - 1) {
////                    weekDatas.add(context.getString(R.string.this_week));
////                } else if (i == weekCount - 2) {
////                    weekDatas.add(context.getString(R.string.last_week));
////                } else {
////                    weekDatas.add(startDate + "-" + entDate);
////                }
////            } else {
////                if (i == weekCount - 1) {
////                    weekDatas.add(context.getString(R.string.last_week));
////                    weekDatas.add(context.getString(R.string.this_week));
////                } else {
////                    weekDatas.add(startDate + "-" + entDate);
////                }
////            }
//            startTime = entTime + timeInMillisPerDay;
//        }
//        ArrayList<String> strings = new ArrayList<>();
//        for (int i = 0; i < weekDatas.size(); i++) {
//            strings.add(weekDatas.get(weekDatas.size() - 1 - i));
//        }
//        return strings;
//    }

    /**
     * 获取月的数据集合
     */
//    public static List<String> getMonths(Context context) {
//        List<String> monthDatas = new ArrayList<>();
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTimeInMillis(System.currentTimeMillis());
//        int year = calendar.get(Calendar.YEAR);
//        int month = calendar.get(Calendar.MONTH) + 1;//本月
//        for (int j = year; j >= 2017; j--) {
//            if (j == year){
//                for (int i = month; i >= 1; i--) {
//                    monthDatas.add(String.format(new Locale("en","US"),context.getString(R.string.yyyym), j, i));
//                }
//            }else{
//                for (int i = 12; i >= 1; i--) {
//                    monthDatas.add(String.format(new Locale("en","US"),context.getString(R.string.yyyym), j, i));
//                }
//            }
//        }
//        return monthDatas;
//    }

    /**
     * 获取月的数据集合
     */
//    public static List<String> getYears(Context context) {
//        List<String> yearDatas = new ArrayList<>();
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTimeInMillis(System.currentTimeMillis());
//        int year = calendar.get(Calendar.YEAR);
//        for (int i = year; i >= 2017; i--) {
//            yearDatas.add(String.format(new Locale("en","US"),context.getString(R.string.yyyym_m), i));
//        }
//        return yearDatas;
//    }

    /**
     * 四个字节拼接成一个时间戳  秒
     * @param byte1
     * @param byte2
     * @param byte3
     * @param byte4
     * @return
     */
    public static long getTimeInSecondsByBytes(byte byte1, byte byte2, byte byte3, byte byte4){
        long timeOffset = TimeZone.getDefault().getOffset(Calendar.getInstance().getTimeInMillis()) / 1000;
        return ((byte4 & 0xff) << 24) + ((byte3 & 0xff) << 16) + ((byte2 & 0xff) << 8) + (byte1 & 0xff) - timeOffset;//北京减8小时 时区
    }

    //获得天数
    public static int getTimeDay(long time){
        if (time % timeInMillisPerDay == 0){
            return (int) (time/timeInMillisPerDay);
        } else {
            return ((int) (time/timeInMillisPerDay))+1;
        }

    }

    //获取本月起始时间
    public static long getTimeOfMonthStart(){
        Calendar ca = Calendar.getInstance();
        ca.set(Calendar.HOUR_OF_DAY, 0);
        ca.clear(Calendar.MINUTE);
        ca.clear(Calendar.SECOND);
        ca.clear(Calendar.MILLISECOND);
        ca.set(Calendar.DAY_OF_MONTH, 1);
        return ca.getTimeInMillis();
    }

    //获取时分秒字符串
    public static String transfom(long time) {
        long shi= time / 3600;
        long fen= (time % 3600) / 60;
        long miao= (time % 3600) % 60;
        //shi< 10 ? ("0" + shi) : shi)判断时否大于10时的话就执行shi,否则执行括号中的
        return (shi< 10 ? ("0" + shi) : shi) + ":" + (fen< 10 ? ("0" + fen) : fen) + ":" + (miao< 10 ? ("0" + miao) : miao);
    }

    //获取分秒字符串
    public static String transfomms(long time) {
        long fen= time  / 60;
        long miao= time  % 60;
        //shi< 10 ? ("0" + shi) : shi)判断时否大于10时的话就执行shi,否则执行括号中的
        return (fen< 10 ? ("0" + fen) : fen) + ":" + (miao< 10 ? ("0" + miao) : miao);
    }


    //获取将时间戳转换为小时
    public static long transFomHour(long time) {
        long shi= time / 3600 / 1000;
        return shi;
    }

    /**
     * 将Calendar转成字符串
     *
     * @param calendar calendar
     * @param format   指定格式
     * @return String
     */
    public static String calendarConvertString(Calendar calendar, String format) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
            return simpleDateFormat.format(calendar.getTime());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }


    /**
     * 将字符串转成Calendar
     *
     * @param times  times
     * @param format 指定格式
     * @return Calendar
     */
    public static Calendar stringConvertCalendar(String times, String format) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
            Date date = simpleDateFormat.parse(times);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            return calendar;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

}