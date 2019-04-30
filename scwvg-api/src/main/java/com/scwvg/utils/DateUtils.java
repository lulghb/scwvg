package com.scwvg.utils;

import ch.qos.logback.classic.Logger;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.time.Period;
import java.util.Calendar;
import java.util.Date;


/**
 * @aothor: lul
 * @unit: 智谷园网络科技有限公司
 * @iphone:18482297774
 * @date：20192019/4/29
 * @Explain：日期处理工具类
 **/
public class DateUtils {
    /**
     * 使用 lombok日志
     */
    private static Logger logger;

    public static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    public static final String MINUTE_PATTERN = "yyyy-MM-dd HH:mm";
    public static final String HOUR_PATTERN = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_PATTERN = "yyyy-MM-dd";
    public static final String MONTH_PATTERN = "yyyy-MM";
    public static final String YEAR_PATTERN = "yyyy";
    public static final String MINUTE_ONLY_PATTERN = "mm";
    public static final String HOUR_ONLY_PATTERN = "HH";

    /**
     * 日期相加减天数
     *  date 如果为Null，则为当前时间
     *  days 加减天数
     *  includeTime 是否包括时分秒,true表示包含
     * @return
     */
    public static Date dateAdd(Date date, int days, boolean includeTime) throws Exception{
        if(date == null){
            date = new Date();
        }
        if(!includeTime){
            SimpleDateFormat sdf = new SimpleDateFormat(DateUtils.DATE_PATTERN);
            date = sdf.parse(sdf.format(date));
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, days);
        return cal.getTime();
    }

    /**
     * 时间格式化成字符串
     *  date Date
     *  pattern StrUtils.DATE_TIME_PATTERN || StrUtils.DATE_PATTERN， 如果为空，则为yyyy-MM-dd
     * @return
     * @throws Exception
     */


    /**
     * 将日期时间格式成日期对象，和dateParse互用
     *  dateTime Date
     * @return Date
     */
    public static Date dateTimeToDate(Date dateTime) throws Exception{
        Calendar cal = Calendar.getInstance();
        cal.setTime(dateTime);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    /**
     * 时间加减小时
     *  startDate 要处理的时间，Null则为当前时间
     *  hours 加减的小时
     * @return Date
     */
    public static Date dateAddHours(Date startDate, int hours) {
        if (startDate == null) {
            startDate = new Date();
        }
        Calendar c = Calendar.getInstance();
        c.setTime(startDate);
        c.set(Calendar.HOUR, c.get(Calendar.HOUR) + hours);
        return c.getTime();
    }

    /**
     * 时间加减分钟
     *  startDate 要处理的时间，Null则为当前时间
     *  minutes 加减的分钟
     * @return
     */
    public static Date dateAddMinutes(Date startDate, int minutes) {
        if (startDate == null) {
            startDate = new Date();
        }
        Calendar c = Calendar.getInstance();
        c.setTime(startDate);
        c.set(Calendar.MINUTE, c.get(Calendar.MINUTE) + minutes);
        return c.getTime();
    }

    /**
     * 时间加减秒数
     *  startDate 要处理的时间，Null则为当前时间
     * @return
     */
    public static Date dateAddSeconds(Date startDate, int seconds) {
        if (startDate == null) {
            startDate = new Date();
        }
        Calendar c = Calendar.getInstance();
        c.setTime(startDate);
        c.set(Calendar.SECOND, c.get(Calendar.SECOND) + seconds);
        return c.getTime();
    }

    /**
     * 时间加减天数
     *  startDate 要处理的时间，Null则为当前时间
     *  days 加减的天数
     * @return Date
     */
    public static Date dateAddDays(Date startDate, int days) {
        if (startDate == null) {
            startDate = new Date();
        }
        Calendar c = Calendar.getInstance();
        c.setTime(startDate);
        c.set(Calendar.DATE, c.get(Calendar.DATE) + days);
        return c.getTime();
    }

    /**
     * 时间加减月数
     *  startDate 要处理的时间，Null则为当前时间
     *  months 加减的月数
     * @return Date
     */
    public static Date dateAddMonths(Date startDate, int months) {
        if (startDate == null) {
            startDate = new Date();
        }
        Calendar c = Calendar.getInstance();
        c.setTime(startDate);
        c.set(Calendar.MONTH, c.get(Calendar.MONTH) + months);
        return c.getTime();
    }

    /**
     * 时间加减年数
     *  startDate 要处理的时间，Null则为当前时间
     *  years 加减的年数
     * @return Date
     */
    public static Date dateAddYears(Date startDate, int years) {
        if (startDate == null) {
            startDate = new Date();
        }
        Calendar c = Calendar.getInstance();
        c.setTime(startDate);
        c.set(Calendar.YEAR, c.get(Calendar.YEAR) + years);
        return c.getTime();
    }

    /**
     * 时间比较（如果myDate>compareDate返回1，<返回-1，相等返回0）
     *  myDate 时间
     *  compareDate 要比较的时间
     * @return int
     */
    public static int dateCompare(Date myDate, Date compareDate) {
        Calendar myCal = Calendar.getInstance();
        Calendar compareCal = Calendar.getInstance();
        myCal.setTime(myDate);
        compareCal.setTime(compareDate);
        return myCal.compareTo(compareCal);
    }

    /**
     * 获取两个时间中最小的一个时间
     *  date
     *  compareDate
     * @return
     */
    public static Date dateMin(Date date, Date compareDate) {
        if(date == null){
            return compareDate;
        }
        if(compareDate == null){
            return date;
        }
        if(1 == dateCompare(date, compareDate)){
            return compareDate;
        }else if(-1 == dateCompare(date, compareDate)){
            return date;
        }
        return date;
    }

    /**
     * 获取两个时间中最大的一个时间
     *  date
     *  compareDate
     * @return
     */
    public static Date dateMax(Date date, Date compareDate) {
        if(date == null){
            return compareDate;
        }
        if(compareDate == null){
            return date;
        }
        if(1 == dateCompare(date, compareDate)){
            return date;
        }else if(-1 == dateCompare(date, compareDate)){
            return compareDate;
        }
        return date;
    }


    /**
     * 获取两个日期（不含时分秒）相差的天数，包含今天
     *  startDate
     *  endDate
     * @return
     * @throws Exception
     */
    public static int dateBetweenIncludeToday(Date startDate, Date endDate) throws Exception {
        return dateBetweenIncludeToday(startDate,endDate)+1;
    }

    /**
     * 获取日期时间的年份，如2017-02-13，返回2017
     *  date
     * @return
     */
    public static int getYear(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.YEAR);
    }

    /**
     * 获取日期时间的月份，如2017年2月13日，返回2
     *  date
     * @return
     */
    public static int getMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.MONTH) + 1;
    }

    /**
     * 获取日期时间的第几天（即返回日期的dd），如2017-02-13，返回13
     *  date
     * @return
     */
    public static int getDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.DATE);
    }

    /**
     * 获取日期时间当月的总天数，如2017-02-13，返回28
     *  date
     * @return
     */
    public static int getDaysOfMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.getActualMaximum(Calendar.DATE);
    }

    /**
     * 获取日期时间当年的总天数，如2017-02-13，返回2017年的总天数
     *  date
     * @return
     */
    public static int getDaysOfYear(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.getActualMaximum(Calendar.DAY_OF_YEAR);
    }

    /**
     * 根据时间获取当月最大的日期
     * <li>2017-02-13，返回2017-02-28</li>
     * <li>2016-02-13，返回2016-02-29</li>
     * <li>2016-01-11，返回2016-01-31</li>
     *  date Date
     * @return
     * @throws Exception
     */


    /**
     * 根据时间获取当月最小的日期，也就是返回当月的1号日期对象
     *  date Date
     * @return
     * @throws Exception
     */


    public static void main(String[] args) throws Exception {

    //System.out.println(dateTimeToDate(new Date()));
    /*System.out.println(dateParse("2017-02-04 14:58:20", null));
    System.out.println(dateTimeToDateStringIfTimeEndZero(new Date()));
    */
        //System.out.println(dateBetween(dateParse("2017-01-30", null), dateParse("2017-02-01", null)));
        //System.out.println(dateBetweenIncludeToday(dateParse("2017-01-30", null), dateParse("2017-02-01", null)));
    /*
    System.out.println(getDaysOfMonth(dateParse("2017-02-01", null)));
    System.out.println(getDaysOfYear(dateParse("2017-01-30", null)));*/
    /*    Date d= dateAddMonths(new Date(),12);
        String s = DateFormat.getDateInstance().format(d);
    System.out.println(s+"DDDD");*/

    /*System.out.println(dateFormat(maxDateOfMonth(dateParse("2016-02", "yyyy-MM")), null));
    System.out.println(dateFormat(minDateOfMonth(dateParse("2016-03-31", null)), null));*/


        LocalDate today = LocalDate.now();
        System.out.println("Today : " + today);
        LocalDate birthDate = LocalDate.of(1993, Month.OCTOBER, 19);
        System.out.println("BirthDate : " + birthDate);

        Period p = Period.between(birthDate, today);
        System.out.printf("年龄 : %d 年 %d 月 %d 日", p.getYears(), p.getMonths(), p.getDays());

    }
}
