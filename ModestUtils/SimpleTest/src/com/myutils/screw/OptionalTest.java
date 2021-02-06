package com.myutils.screw;

import com.myutils.util.DateUtil;
import com.myutils.util.MD5Util;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Description
 * @Author OYQJ
 * @Date 2020-08-29
 */
public class OptionalTest {
    public static void main(String[] args) {
        System.out.println("Integer.MAX_VALUE = " + Integer.MAX_VALUE);
        ArrayList list1 = new ArrayList<>();
//        List list1 = Collections.synchronizedCollection(list);
        List list = Collections.synchronizedList(new ArrayList());
        String[] strings = Arrays.copyOf(list1.toArray(), 10, String[].class);
        String timeStr = DateUtil.getSystemDateTimeStr("yyyyMMdd");
        String token = MD5Util.MD5Encode(timeStr + "hrjj@_@", "UTF-8");
        System.out.println("token = " + token);
//        URShift();
//        System.out.println(DateUtils.getLastDay( DateUtil.getSystemDateTimeStr("yyyy-MM-dd 00:00:00")));
//        List<String> timeList = new LinkedList<>();
//        String today = DateUtil.getSystemDateTimeStr("yyyy-MM-dd");
//        String nextDay = DateUtils.getNextDay(today+" 00:00:00");
//        for (int i = 0; i <= 24; i++) {
//            if (i<10){
//                timeList.add(today + " 0" +i+ ":00:00");
//            }else if (i >= 10 && i<=23){
//                timeList.add(today + " " +i+ ":00:00");
//            }else {
//                timeList.add(nextDay+" 00:00:00");
//            }
//        }
//        System.out.println("timeList = " + timeList);
//        String timeArr[] = today.split("[-]");
//        List<Map<String, Object>> xaxisList =
//                ConfigUtils.getXaxisList("3", timeArr[0], timeArr[1], timeArr[2]);
//        System.out.println("xaxisList = " + xaxisList);
//        getInteger();
        /*Date date = new Date();
        Timestamp ts = new Timestamp(date.getTime());
        System.out.println(ts.getTime());
        System.out.println(ts.toString());

        String nowTime = DateUtil.getSystemDateTimeStr("yyyy-MM-dd HH:mm:ss");
        System.out.println(nowTime.substring(11, 16));*/

        /*Calendar calendar = Calendar.getInstance();
        int y = calendar.get(Calendar.YEAR);
        int m = calendar.get(Calendar.MONTH) + 1;
        int d = calendar.get(Calendar.DATE);
        int h = calendar.get(Calendar.HOUR_OF_DAY);
        int mi = calendar.get(Calendar.MINUTE);
        String minute = "";
        String arr[] = new String[]{"00", "15", "30", "45"};
        for (int i = arr.length - 1; i >= 0; i--) {
            if (mi >= Integer.parseInt(arr[i])) {
                minute = arr[i];
                break;
            }
        }
        String month = m < 10 ? ("0" + m) : (m + "");
        String day = d < 10 ? ("0" + d) : (d + "");
        String hour = h < 10 ? ("0" + h) : (h + "");
        String time = y + "-" + month + "-" + day + " " + hour + ":" + minute + ":00";
        Shift();*/
    }

    public static void URShift() {
        String nowTime = DateUtil.getSystemDateTimeStr("yyyy-MM-dd HH:mm:ss");
        int min = Integer.parseInt(nowTime.substring(14, 16));
        String time = "";
        if ( min >= 0  && min < 15){
            time = DateUtil.getSystemDateTimeStr("yyyy-MM-dd HH:00:00");
        } else if (min >= 15  && min < 30){
            time = DateUtil.getSystemDateTimeStr("yyyy-MM-dd HH:15:00");
        } else if (min >= 30  && min < 45){
            time = DateUtil.getSystemDateTimeStr("yyyy-MM-dd HH:30:00");
        } else if (min >= 45  && min < 60){
            time = DateUtil.getSystemDateTimeStr("yyyy-MM-dd HH:45:00");
        }
        System.out.println(time);


    }


    public static void Shift() {
        String nowTime = DateUtil.getSystemDateTimeStr("yyyy-MM-dd HH:mm:ss");
        int min = Integer.parseInt(nowTime.substring(14, 16));
        String time = "";
        Calendar calendar = Calendar.getInstance();
        /* HOUR_OF_DAY 指示一天中的小时 */
        calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) - 1);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:45:00");
        if ( min >= 0  && min < 15){
            time = df.format(calendar.getTime());
        } else if (min >= 15  && min < 30){
            time = DateUtil.getSystemDateTimeStr("yyyy-MM-dd HH:00:00");
        } else if (min >= 30  && min < 45){
            time = DateUtil.getSystemDateTimeStr("yyyy-MM-dd HH:15:00");
        } else if (min >= 45  && min < 60){
            time = DateUtil.getSystemDateTimeStr("yyyy-MM-dd HH:30:00");
        }
        System.out.println("==="+time);
    }

    public static void getInteger(){
        Calendar calendar = Calendar.getInstance();
        /* HOUR_OF_DAY 指示一天中的小时 */
        calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) - 1);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("一个小时前的时间：" + df.format(calendar.getTime()));
        System.out.println("当前的时间：" + df.format(new Date()));
    }


}
