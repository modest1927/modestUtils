package com.myutils.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class DateUtils {

    /**
     * 获取两个日期字符串之间的日期集合
     * @param startTime:String
     * @param endTime:String
     * @return list:yyyy-MM-dd
     */
    public static List<String> getBetweenDate(String startTime, String endTime){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        // 声明保存日期集合
        List<String> list = new ArrayList<String>();
        try {
            // 转化成日期类型
            Date startDate = sdf.parse(startTime);
            Date endDate = sdf.parse(endTime);

            //用Calendar 进行日期比较判断
            Calendar calendar = Calendar.getInstance();
            while (startDate.getTime()<=endDate.getTime()){
                // 把日期添加到集合
                list.add(sdf.format(startDate));
                // 设置日期
                calendar.setTime(startDate);
                //把日期增加一天
                calendar.add(Calendar.DATE, 1);
                // 获取增加后的日期
                startDate=calendar.getTime();
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 获取两个日期之间所有的月份集合
     * @param startTime
     * @param endTime
     * @return：YYYY-MM
     */
    public static List<String> getMonthBetweenDate(String startTime, String endTime){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        // 声明保存日期集合
        List<String> list = new ArrayList<String>();
        try {
            // 转化成日期类型
            Date startDate = sdf.parse(startTime);
            Date endDate = sdf.parse(endTime);

            //用Calendar 进行日期比较判断
            Calendar calendar = Calendar.getInstance();
            while (startDate.getTime()<=endDate.getTime()){
                // 把日期添加到集合
                list.add(sdf.format(startDate));
                // 设置日期
                calendar.setTime(startDate);
                //把日期增加一天
                calendar.add(Calendar.MONTH, 1);
                // 获取增加后的日期
                startDate=calendar.getTime();
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 获取两个日期之间所有的年份集合
     * @param startTime
     * @param endTime
     * @return：YYYY
     */
    public static List<String> getYearBetweenDate(String startTime, String endTime){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        // 声明保存日期集合
        List<String> list = new ArrayList<String>();
        try {
            // 转化成日期类型
            Date startDate = sdf.parse(startTime);
            Date endDate = sdf.parse(endTime);

            //用Calendar 进行日期比较判断
            Calendar calendar = Calendar.getInstance();
            while (startDate.getTime()<=endDate.getTime()){
                // 把日期添加到集合
                list.add(sdf.format(startDate));
                // 设置日期
                calendar.setTime(startDate);
                //把日期增加一天
                calendar.add(Calendar.YEAR, 1);
                // 获取增加后的日期
                startDate=calendar.getTime();
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static List getDayBetween(String type, String dateFirst, String dateSecond) {
        String pattern = "";
        if ("year".equals(type)) {
            pattern = "yyyy";
        } else if ("day".equals(type)) {
            pattern = "yyyy-MM-dd";
        } else if ("month".equals(type)) {
            pattern = "yyyy-MM";
        } else if ("minute".equals(type)) {
            dateFirst += " 00:00:00";
            dateSecond = getNextDay(dateFirst);
            pattern = "yyyy-MM-dd HH:mm:ss";
        } else if ("hour".equals(type)) {
            dateFirst += " 00:00:00";
            dateSecond = getNextDay(dateFirst);
            pattern = "yyyy-MM-dd HH:mm:ss";
        }else {
            return null;
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        List list = new ArrayList();
        try {

            Date dateOne = dateFormat.parse(dateFirst);

            Date dateTwo = dateFormat.parse(dateSecond);

            Calendar calendar = Calendar.getInstance();

            calendar.setTime(dateOne);

            while (calendar.getTime().before(dateTwo)) {
                list.add(dateFormat.format(calendar.getTime()));
                ////System.out.println(dateFormat.format(calendar.getTime()));
                if ("year".equals(type)) {
                    calendar.add(Calendar.YEAR, 1);
                } else if ("day".equals(type)) {
                    calendar.add(Calendar.DAY_OF_MONTH, 1);
                } else if ("month".equals(type)) {
                    calendar.add(Calendar.MONTH, 1);
                } else if ("minute".equals(type)) {
                    calendar.add(Calendar.MINUTE, 15);
                }else if ("hour".equals(type)) {
                    calendar.add(Calendar.HOUR_OF_DAY, 1);
                }
            }
            if(!"hour".equals(type)){
                list.add(dateFormat.format(calendar.getTime()));
            }
            return list;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getNextDay(String date) {
        try {
            SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date temp = dft.parse(date);
            Calendar cld = Calendar.getInstance();
            cld.setTime(temp);
            cld.add(Calendar.DATE, 1);
            temp = cld.getTime();
            //获得下一天日期字符串
            String nextDay = dft.format(temp);
            return nextDay;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static List<String> getBetweenDate(String startTime, String endTime,String dateType){
        String format = "yyyy-MM-dd";
        if("day".equals(dateType)){
            startTime = startTime +"-01";
        }else if("month".equals(dateType)){
            format = "yyyy-MM";
        }else if("year".equals(dateType)){
            format = "yyyy";
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        // 声明保存日期集合
        List<String> list = new ArrayList<String>();
        try {
            // 转化成日期类型
            Date startDate = sdf.parse(startTime);
            Date endDate = sdf.parse(endTime);

            //用Calendar 进行日期比较判断
            Calendar calendar = Calendar.getInstance();
            while (startDate.getTime()<=endDate.getTime()){
                // 把日期添加到集合
                list.add(sdf.format(startDate));
                // 设置日期
                calendar.setTime(startDate);
                //把日期增加一天
                calendar.add(Calendar.DATE, 1);
                // 获取增加后的日期
                startDate=calendar.getTime();
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static int getDaysOfMonth(String strDate) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
        Date date = format.parse(strDate);
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        int days = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        return days;
    }

    public static String getLastMonth(String thisMonth) {
        //thisMonth = "201805";
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
            SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM");
            Date date = sdf.parse(thisMonth);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.MONTH, -1);
            return sdf2.format(calendar.getTime());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getLastYear(String thisYear) {
        //thisMonth = "201805";
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
            SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy");
            Date date = sdf.parse(thisYear);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.YEAR, -1);
            return sdf2.format(calendar.getTime());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     *  java 获取 获取某年某月 所有日期（yyyy-mm-dd格式字符串）
     */
    public static List<String> getMonthFullDay(String date) throws ParseException {
        SimpleDateFormat dateFormatYYYYMMDD = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");

        Date date1 =sdf.parse(date);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date1);

        int year = calendar.get(Calendar.YEAR);    //获取年
        int month = calendar.get(Calendar.MONTH) + 1;   //获取月份，0表示1月份

        List<String> fullDayList = new ArrayList<>(32);
        // 获得当前日期对象
        Calendar cal = Calendar.getInstance();
        cal.clear();// 清除信息
        cal.set(Calendar.YEAR, year);
        // 1月从0开始
        cal.set(Calendar.MONTH, month-1 );
        // 当月1号
        cal.set(Calendar.DAY_OF_MONTH,1);
        int count = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        for (int j = 1; j <= count ; j++) {
            fullDayList.add(dateFormatYYYYMMDD.format(cal.getTime()));
            cal.add(Calendar.DAY_OF_MONTH,1);
        }
        return fullDayList;
    }

    /**
     * 获得该月最后一天
     * @param year
     * @param month
     * @return
     */
    public static String getLastDayOfMonth(int year,int month){
        Calendar cal = Calendar.getInstance();
        //设置年份
        cal.set(Calendar.YEAR,year);
        //设置月份
        cal.set(Calendar.MONTH, month-1);
        //获取某月最大天数
        int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        //设置日历中月份的最大天数
        cal.set(Calendar.DAY_OF_MONTH, lastDay);
        //格式化日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String lastDayOfMonth = sdf.format(cal.getTime());
        return lastDayOfMonth;
    }

}
