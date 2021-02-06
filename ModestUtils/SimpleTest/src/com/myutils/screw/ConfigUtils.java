package com.myutils.screw;
import org.springframework.util.CollectionUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public final class ConfigUtils {
    //查4年
    public  static final  Integer YEAR_FOUR = 3;
    //行业占比截取排行前9
    public  static final  Integer NINE = 7;

    public static final Integer TAB_ORDER = 5;

    public static final  Map<String,String> DATA_MAP = new HashMap<>();
    //指标图片配置
    public static final  Map<String,String> DATA_IMG_MAP = new HashMap<>();

    public static final  Map<String,String> COM_ID_MAP = new HashMap<>();
    //时间戳(1天)
    public static final Long TIME_DAY = 86400000L;

    public static final  List<String> AIR_LIST = new ArrayList<>();

    static {
        DATA_MAP.put("co2", "c");
        DATA_MAP.put("temp", "envtpr");
        DATA_MAP.put("SDJ", "h");
        DATA_IMG_MAP.put("cso2", "/oss/filemag/getFileById?fileId=5dc50a905648900008565d04");
        DATA_IMG_MAP.put("envtpr", "/oss/filemag/getFileById?fileId=5dc50a965648900008565d06");
        DATA_IMG_MAP.put("h", "/oss/filemag/getFileById?fileId=5dc50a9d5648900008565d08");
        DATA_IMG_MAP.put("c", "/oss/filemag/getFileById?fileId=5dc50aa35648900008565d0a");
        DATA_IMG_MAP.put("dust", "/oss/filemag/getFileById?fileId=5dc50aaa5648900008565d0c");
        DATA_IMG_MAP.put("cco", "/oss/filemag/getFileById?fileId=5dc50a845648900008565d00");
        DATA_IMG_MAP.put("cnox", "/oss/filemag/getFileById?fileId=5dc50a8a5648900008565d02");

        AIR_LIST.add("cso2");
        AIR_LIST.add("cnox");
        AIR_LIST.add("cco");
        AIR_LIST.add("c");
    }


    /**
     * 获取当前区间的上一个区间
     */
    public static Map<String,String> getLastInterval(String startTime,String endTime){
        Map map = new HashMap();
        try{
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date sDate = format.parse(startTime);
            Date eDate = format.parse(endTime);
            long start = 0,end = 0;
            if(sDate.getTime() == eDate.getTime()) {
                 start = sDate.getTime() - (60*60*24);
                 end = sDate.getTime() - (60*60*24);
            }else{
                start = sDate.getTime() - (eDate.getTime() - sDate.getTime());
                end = sDate.getTime();
            }
            map.put("startTime",format.format(start));
            map.put("endTime",format.format(end));

        }catch (Exception e){

        }
        return map;
    }

    /**
     * 按照区间拼接坐标
     */
    public static List<Map<String,Object>> getDataList(String startTime,String endTime){
        List<Map<String,Object>> dataList = new ArrayList<>();
        try {
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date sDate = format.parse(startTime);
            Date eDate = format.parse(endTime);
            long start = sDate.getTime();
            long end = eDate.getTime();
            if (start > end) {
                return null;
            }
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(eDate);
            while (end >= start) {
                Map map = new HashMap<>();
                String date = format.format(calendar.getTime());
                map.put("time",date);
                map.put("month",date.substring(5,10));
                map.put("day",date.substring(8,10));
                dataList.add(map);
                calendar.add(Calendar.DAY_OF_MONTH, -1);
                end = calendar.getTimeInMillis();
            }

        }catch (Exception e){
            //e.printStackTrace();
        }
        if(!CollectionUtils.isEmpty(dataList)) {
            Collections.sort(dataList, (p1, p2) -> {
                return String.valueOf(p1.get("time")).compareTo(p2.get("time") + "");
            });
        }
        return dataList;
    }

    /**
     * 按照区间拼接坐标自己选择
     */
    public static List<Map<String,Object>> getDataList(String startTime,String endTime,String pattern){
        List<Map<String,Object>> dataList = new ArrayList<>();
        try {
            DateFormat format = new SimpleDateFormat(pattern);
            Date sDate = format.parse(startTime);
            Date eDate = format.parse(endTime);
            long start = sDate.getTime();
            long end = eDate.getTime();
            if (start > end) {
                return null;
            }
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(eDate);
            while (end >= start) {
                Map map = new HashMap<>();
                String date = format.format(calendar.getTime());
                map.put("time",date);
                dataList.add(map);
                calendar.add(Calendar.MONTH, -1);
                end = calendar.getTimeInMillis();
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        if(!CollectionUtils.isEmpty(dataList)) {
            Collections.sort(dataList, (p1, p2) -> {
                return String.valueOf(p1.get("time")).compareTo(p2.get("time") + "");
            });
        }
        return dataList;
    }

    /**
     * 获取一个月的每十五分钟的值
     */
    public static List<Map<String,Object>> getMonthFm( String year, String month, String cols){
        List<Map<String,Object>> list = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        int y = calendar.get(Calendar.YEAR);
        int m = calendar.get(Calendar.MONTH) + 1;
        int d = calendar.get(Calendar.DATE);
        int h = calendar.get(Calendar.HOUR_OF_DAY);
        int mi = calendar.get(Calendar.MINUTE);
        //月维度
        int f = d;
        if (Integer.parseInt(year) != y || Integer.parseInt(month) != m) {
            Calendar c = Calendar.getInstance();
            c.set(Calendar.YEAR, Integer.parseInt(year));
            c.set(Calendar.MONTH, Integer.parseInt(month) - 1);
            f = c.getActualMaximum(Calendar.DAY_OF_MONTH);
        }
        String day = "-1";
        for (int k = 1; k <= f; k++) {
            if(Integer.parseInt(year) == y && Integer.parseInt(month) == m && k == d){
                day = d+"";
            }
            String arr[] = new String[]{"00","15","30","45"};
            loop:
            for (int i = 0; i < 24; i++) {
                for (int j = 0; j < arr.length; j++) {
                    if (Integer.parseInt(year) == y && Integer.parseInt(month) == m && Integer.parseInt(day) == d) {
                        if (i >= h && mi < Integer.parseInt(arr[j])) {
                            break loop;
                        }
                    }
                    Map tempMap = new HashMap<>();
                    String hm = (i < 10 ? ("0" + i) : i) + ":" + arr[j];
                    for(String col:cols.split(",")){
                        tempMap.put(col, "");
                    }
                    tempMap.put("time", year + "-" + month + "-" + (k < 10 ? ("0" + k) : k) + " " + hm);
                    if(!hm.equals("00:00")) {
                        tempMap.put("read_time", year + "-" + month + "-" + (k < 10 ? ("0" + k) : k) + " " + hm);
                    }else{
                        tempMap.put("read_time", (k < 10 ? ("0" + k) : k));
                    }
                   list.add(tempMap);
                    if (Integer.parseInt(year) == y && Integer.parseInt(month) == m && Integer.parseInt(day) == d) {
                        if (i >= h && mi >= Integer.parseInt(arr[arr.length - 1]) && j == (arr.length - 1)) {
                            break loop;
                        }
                    }
                }
            }
        }
        return list;
    }

    /**
     *
     * @param reportType 周期 1_日,2_月,3_年
     * @param year
     * @param month
     * @param day
     * @return
     */
    public static List<Map<String,Object>> getDataList(String reportType, String year, String month, String day){
        List<Map<String,Object>> list = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        int y = calendar.get(Calendar.YEAR);
        int m = calendar.get(Calendar.MONTH) + 1;
        int d = calendar.get(Calendar.DATE);
        int h = calendar.get(Calendar.HOUR_OF_DAY);
        int mi = calendar.get(Calendar.MINUTE);
        if ("2".equals(reportType)) {
            //年维度
            int f = m;
            if (Integer.parseInt(year) != y) {
                f = 12;
            }
            for (int i = 1; i <= f; i++) {
                Map tempMap = new HashMap<>();
                tempMap.put("time",(i < 10 ? ("0" + i) : i));
                list.add(tempMap);
            }
        } else if ("1".equals(reportType)) {
            //月维度
            int f = d ;
            if (Integer.parseInt(year) != y || Integer.parseInt(month) != m) {
                Calendar c = Calendar.getInstance();
                c.set(Calendar.YEAR, Integer.parseInt(year));
                c.set(Calendar.MONTH, Integer.parseInt(month) - 1);
                f = c.getActualMaximum(Calendar.DAY_OF_MONTH);
            }
            for (int i = 1; i <= f; i++) {
                Map tempMap = new HashMap<>();
                tempMap.put("time", (i < 10 ? ("0" + i) : i));
                list.add(tempMap);
            }
        } else {
            String arr[] = new String[60];
            for(int i=0;i<60;i++){
                if(i<10){
                    arr[i]="0"+i;
                }else{
                    arr[i] = i+"";
                }
            }
            //日维度
            loop:
            for (int i = 0; i < 24; i++) {
                for (int j = 0; j < arr.length; j++) {
                    if (Integer.parseInt(year) == y && Integer.parseInt(month) == m && Integer.parseInt(day) == d) {
                        if (i >= h && mi < Integer.parseInt(arr[j])) {
                            break loop;
                        }
                    }
                    Map tempMap = new HashMap<>();
                    tempMap.put("time", (i < 10 ? ("0" + i) : i) + ":" + arr[j] );
                    list.add(tempMap);
                    if (Integer.parseInt(year) == y && Integer.parseInt(month) == m && Integer.parseInt(day) == d) {
                        if (i >= h && mi >= Integer.parseInt(arr[arr.length - 1]) && j == (arr.length - 1)) {
                            break loop;
                        }
                    }
                }
            }
        }

        return list;
    }

    /**
     *
     * @param reportType 实时监测坐标轴
     * @param year
     * @param month
     * @param day
     * @return
     */
    public static List<Map<String,Object>> getRealTimeDataList(String reportType, String year, String month, String day){
        List<Map<String,Object>> list = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        int y = calendar.get(Calendar.YEAR);
        int m = calendar.get(Calendar.MONTH) + 1;
        int d = calendar.get(Calendar.DATE);
        int h = calendar.get(Calendar.HOUR_OF_DAY);
        int mi = calendar.get(Calendar.MINUTE);
        if ("2".equals(reportType)) {
            //年维度
            int f = m;
            if (Integer.parseInt(year) != y) {
                f = 12;
            }
            for (int i = 1; i <= f; i++) {
                Map tempMap = new HashMap<>();
                tempMap.put("time",(i < 10 ? ("0" + i) : i));
                list.add(tempMap);
            }
        } else if ("1".equals(reportType)) {
            //月维度
            int f = d ;
            if (Integer.parseInt(year) != y || Integer.parseInt(month) != m) {
                Calendar c = Calendar.getInstance();
                c.set(Calendar.YEAR, Integer.parseInt(year));
                c.set(Calendar.MONTH, Integer.parseInt(month) - 1);
                f = c.getActualMaximum(Calendar.DAY_OF_MONTH);
            }
            for (int i = 1; i <= f; i++) {
                Map tempMap = new HashMap<>();
                tempMap.put("time", (i < 10 ? ("0" + i) : i));
                list.add(tempMap);
            }
        } else {
            String arr[] = new String[60];
            for(int i=0;i<60;i++){
                if(i<10){
                    arr[i]="0"+i;
                }else{
                    arr[i] = i+"";
                }
            }
            //日维度
            loop:
            for (int i = 0; i < 24; i++) {
                for (int j = 0; j < arr.length; j++) {
//                    if (Integer.parseInt(year) == y && Integer.parseInt(month) == m && Integer.parseInt(day) == d) {
//                        if (i >= h && mi < Integer.parseInt(arr[j])) {
//                            break loop;
//                        }
//                    }
                    Map tempMap = new HashMap<>();
                    tempMap.put("time", (i < 10 ? ("0" + i) : i) + ":" + arr[j] );
                    list.add(tempMap);
//                    if (Integer.parseInt(year) == y && Integer.parseInt(month) == m && Integer.parseInt(day) == d) {
//                        if (i >= h && mi >= Integer.parseInt(arr[arr.length - 1]) && j == (arr.length - 1)) {
//                            break loop;
//                        }
//                    }
                }
            }
        }

        return list;
    }

    public static List<Map<String,Object>> getFiveMDataList(String reportType, String year, String month, String day){
        List<Map<String,Object>> list = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        int y = calendar.get(Calendar.YEAR);
        int m = calendar.get(Calendar.MONTH) + 1;
        int d = calendar.get(Calendar.DATE);
        int h = calendar.get(Calendar.HOUR_OF_DAY);
        int mi = calendar.get(Calendar.MINUTE);
        if ("2".equals(reportType)) {
            //年维度
            int f = m;
            if (Integer.parseInt(year) != y) {
                f = 12;
            }
            for (int i = 1; i <= f; i++) {
                Map tempMap = new HashMap<>();
                tempMap.put("time",(i < 10 ? ("0" + i) : i));
                list.add(tempMap);
            }
        } else if ("1".equals(reportType)) {
            //月维度
            int f = d ;
            if (Integer.parseInt(year) != y || Integer.parseInt(month) != m) {
                Calendar c = Calendar.getInstance();
                c.set(Calendar.YEAR, Integer.parseInt(year));
                c.set(Calendar.MONTH, Integer.parseInt(month) - 1);
                f = c.getActualMaximum(Calendar.DAY_OF_MONTH);
            }
            for (int i = 1; i <= f; i++) {
                Map tempMap = new HashMap<>();
                tempMap.put("time", (i < 10 ? ("0" + i) : i));
                list.add(tempMap);
            }
        } else {
            List<String> arrayList = new ArrayList<>();
            //String arr[] = new String[];
            for(int i=0;i<60;i++){
                if(i%4 == 0) {
                    if (i < 10) {
                        arrayList.add("0" + i);
                    } else {
                        arrayList.add(i + "");
                    }
                }
            }
            String arr[] =  arrayList.toArray(new String[]{});
            //日维度
            loop:
            for (int i = 0; i < 24; i++) {
                for (int j = 0; j < arr.length; j++) {
                    if (Integer.parseInt(year) == y && Integer.parseInt(month) == m && Integer.parseInt(day) == d) {
                        if (i >= h && mi < Integer.parseInt(arr[j])) {
                            break loop;
                        }
                    }
                    Map tempMap = new HashMap<>();
                    tempMap.put("time", (i < 10 ? ("0" + i) : i) + ":" + arr[j] );
                    list.add(tempMap);
                    if (Integer.parseInt(year) == y && Integer.parseInt(month) == m && Integer.parseInt(day) == d) {
                        if (i >= h && mi >= Integer.parseInt(arr[arr.length - 1]) && j == (arr.length - 1)) {
                            break loop;
                        }
                    }
                }
            }
        }

        return list;
    }


    /**
     *
     * @param reportType 周期 1_日,2_月,3_年
     * @param year
     * @param month
     * @param day
     * @return
     */
    public static List<Map<String,Object>> getXaxisList(String reportType, String year, String month, String day){
        List<Map<String,Object>> list = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        int y = calendar.get(Calendar.YEAR);
        int m = calendar.get(Calendar.MONTH) + 1;
        int d = calendar.get(Calendar.DATE);
        int h = calendar.get(Calendar.HOUR_OF_DAY);
        int mi = calendar.get(Calendar.MINUTE);
        if ("2".equals(reportType)) {
            //年维度
            int f = m;
            if (Integer.parseInt(year) != y) {
                f = 12;
            }
            for (int i = 1; i <= f; i++) {
                Map tempMap = new HashMap<>();
                tempMap.put("time",(i < 10 ? ("0" + i) : i));
                list.add(tempMap);
            }
        } else if ("1".equals(reportType)) {
            //月维度
            int f = d;
            if (Integer.parseInt(year) != y || Integer.parseInt(month) != m) {
                Calendar c = Calendar.getInstance();
                c.set(Calendar.YEAR, Integer.parseInt(year));
                c.set(Calendar.MONTH, Integer.parseInt(month) - 1);
                f = c.getActualMaximum(Calendar.DAY_OF_MONTH);
            }
            for (int i = 1; i <= f; i++) {
                Map tempMap = new HashMap<>();
                tempMap.put("time", (i < 10 ? ("0" + i) : i));
                list.add(tempMap);
            }
        } else {
            String arr[] = new String[]{"00", "30"};
            //日维度
            loop:
            for (int i = 0; i < 24; i++) {
                for (int j = 0; j < arr.length; j++) {
                    if (Integer.parseInt(year) == y && Integer.parseInt(month) == m && Integer.parseInt(day) == d) {
                        if (i >= h && mi < Integer.parseInt(arr[j])) {
                            break loop;
                        }
                    }
                    Map tempMap = new HashMap<>();
                    tempMap.put("time", (i < 10 ? ("0" + i) : i) + ":" + arr[j] );
                    list.add(tempMap);
                    if (Integer.parseInt(year) == y && Integer.parseInt(month) == m && Integer.parseInt(day) == d) {
                        if (i >= h && mi >= Integer.parseInt(arr[arr.length - 1]) && j == (arr.length - 1)) {
                            break loop;
                        }
                    }
                }
            }
        }

        return list;
    }

    /**
     *
     * @param reportType 周期 1_日,2_月,3_年
     * @param year
     * @param month
     * @param day
     * @return
     */
    public static List<Map<String,Object>> getAppXaxisList(String reportType, String year, String month, String day){
        List<Map<String,Object>> list = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        int y = calendar.get(Calendar.YEAR);
        int m = calendar.get(Calendar.MONTH) + 1;
        int d = calendar.get(Calendar.DATE);
        int h = calendar.get(Calendar.HOUR_OF_DAY);
        int mi = calendar.get(Calendar.MINUTE);
        if ("2".equals(reportType)) {
            //年维度
            int f = m;
            if (Integer.parseInt(year) != y) {
                f = 12;
            }
            for (int i = 1; i <= f; i++) {
                Map tempMap = new HashMap<>();
                tempMap.put("time",i);
                list.add(tempMap);
            }
        } else if ("1".equals(reportType)) {
            //月维度
            int f = d;
            if (Integer.parseInt(year) != y || Integer.parseInt(month) != m) {
                Calendar c = Calendar.getInstance();
                c.set(Calendar.YEAR, Integer.parseInt(year));
                c.set(Calendar.MONTH, Integer.parseInt(month) - 1);
                f = c.getActualMaximum(Calendar.DAY_OF_MONTH);
            }
            for (int i = 1; i <= f; i++) {
                Map tempMap = new HashMap<>();
                tempMap.put("time", i);
                list.add(tempMap);
            }
        } else {
            String arr[] = new String[]{"00", "15", "30", "45"};
            //日维度
            loop:
            for (int i = 0; i < 24; i++) {
                for (int j = 0; j < arr.length; j++) {
                    if (Integer.parseInt(year) == y && Integer.parseInt(month) == m && Integer.parseInt(day) == d) {
                        if (i >= h && mi < Integer.parseInt(arr[j])) {
                            break loop;
                        }
                    }
                    Map tempMap = new HashMap<>();
                    tempMap.put("time", (i < 10 ? ("0" + i) : i) + ":" + arr[j] );
                    list.add(tempMap);
                    if (Integer.parseInt(year) == y && Integer.parseInt(month) == m && Integer.parseInt(day) == d) {
                        if (i >= h && mi >= Integer.parseInt(arr[arr.length - 1]) && j == (arr.length - 1)) {
                            break loop;
                        }
                    }
                }
            }
        }

        return list;
    }

    /**
     *筛选拼接坐标
     * @return
     */
    public static List<Map<String,Object>>  getXaxisList(List<Map<String,Object>> xAxisList){
        if(!CollectionUtils.isEmpty(xAxisList))
            xAxisList = xAxisList.stream()
                    .filter((Map m)->String.valueOf(m.get("time")).substring(3,5).equals("00"))
                    .collect(Collectors.toList());
        for(Map map:xAxisList){
            map.put("time",String.valueOf(map.get("time")).substring(0,2));
        }
        return xAxisList;
    }

    /**
     * 拼接
     */
    public static String getInSql(List<Map<String,Object>> mapList,String cols){
        String sql = "";
        if(!CollectionUtils.isEmpty(mapList)){
            for(int i=0;i<mapList.size();i++){
                if(i!=0){
                    sql+=",";
                }
                if(i == 0){
                    sql+="(";
                }
                sql+="'"+mapList.get(i).get(cols)+"'";
                if(i == mapList.size() - 1){
                    sql+=")";
                }
            }
        }
        return sql;
    }

    public static List<Map<String,Object>> filterList(List<Map<String,Object>> listFast,List<Map<String,Object>> listLast,String createTime,int fast,int last){
        if(CollectionUtils.isEmpty(listFast)){
            return listLast;
        }
        List<String> bIDs = listFast.stream().map(o->String.valueOf(o.get(createTime)).substring(fast,last)).collect(Collectors.toList());
        if(!CollectionUtils.isEmpty(listLast)) {
            listLast = listLast.stream().filter(o -> !bIDs.contains(String.valueOf(o.get("time")))).collect(Collectors.toList());
        }
        return listLast;
    }
    public static List<Map<String,Object>> realTimeList(List<Map<String,Object>> listFast,List<Map<String,Object>> listLast,String createTime,int fast,int last){
        if(CollectionUtils.isEmpty(listFast)){
            return listLast;
        }
        List<String> bIDs = listFast.stream().map(o->String.valueOf(o.get(createTime)).substring(fast,last)).collect(Collectors.toList());
        List<Map<String,Object>> lastList = listLast.stream().filter(o->!bIDs.contains(String.valueOf(o.get("time")))).collect(Collectors.toList());
        if(CollectionUtils.isEmpty(lastList)){
            List<String> timeList = listLast.stream().map(o->String.valueOf(o.get("time"))).collect(Collectors.toList());
            listFast = listFast.stream().filter(o->timeList.contains(String.valueOf(o.get(createTime)))).collect(Collectors.toList());
            return listFast;
        }else{
            listFast.addAll(lastList);
        }
        return listFast;
    }

    public static List<Map<String,Object>> realTimeList(List<Map<String,Object>> listFast,List<Map<String,Object>> listLast,String createTime){
        if(CollectionUtils.isEmpty(listFast)){
            return listLast;
        }
        List<String> bIDs = listFast.stream().map(o->String.valueOf(o.get(createTime))).collect(Collectors.toList());
        List<Map<String,Object>> lastList = listLast.stream().filter(o->!bIDs.contains(String.valueOf(o.get("time")))).collect(Collectors.toList());
        if(CollectionUtils.isEmpty(lastList)){
            List<String> timeList = listLast.stream().map(o->String.valueOf(o.get("time"))).collect(Collectors.toList());
            listFast = listFast.stream().filter(o->timeList.contains(String.valueOf(o.get(createTime)))).collect(Collectors.toList());
            return listFast;
        }else{
            listFast.addAll(lastList);
        }
        Collections.sort(listFast, (p1, p2) -> {
            return String.valueOf(p1.get("time")).compareTo(p2.get("time") + "");
        });
        return listFast;
    }

    /**
     *
     * @param listFast
     * @param listLast
     * @param createTime
     * @return
     */
    public static List<Map<String,Object>> realTimeAppList(List<Map<String,Object>> listFast,List<Map<String,Object>> listLast,String createTime,String cols){
        if(CollectionUtils.isEmpty(listFast)){
            return listLast;
        }
        List<String> bIDs = listFast.stream().map(o->String.valueOf(o.get(createTime))).collect(Collectors.toList());
        List<Map<String,Object>> lastList = listLast.stream().filter(o->!bIDs.contains(String.valueOf(o.get("time")))).collect(Collectors.toList());
        if(CollectionUtils.isEmpty(lastList)){
            List<String> timeList = listLast.stream().map(o->String.valueOf(o.get("time"))).collect(Collectors.toList());
            listFast = listFast.stream().filter(o->timeList.contains(String.valueOf(o.get(createTime)))).collect(Collectors.toList());
            return listFast;
        }else{
            if(!CollectionUtils.isEmpty(lastList)){
                String[] colsArr = cols.split("[,]");
                for(Map map:lastList){
                    Map saveMap = new HashMap<>();
                    String time = map.get("time")+"";
                    for(String col:colsArr){
                        saveMap.put(col,"");
                    }
                    saveMap.put("time",time);
                    listFast.add(saveMap);
                }
            }
        }
        Collections.sort(listFast, (p1, p2) -> {
            return String.valueOf(p1.get("time")).compareTo(p2.get("time") + "");
        });
        return listFast;
    }

    public static List<Map<String,Object>> realTimeList(List<Map<String,Object>> listFast,List<Map<String,Object>> listLast,String createTime,String key){
        if(CollectionUtils.isEmpty(listFast)){
            return listLast;
        }
        List<String> bIDs = listFast.stream().map(o->String.valueOf(o.get(createTime))).collect(Collectors.toList());
        List<Map<String,Object>> lastList = listLast.stream().filter(o->!bIDs.contains(String.valueOf(o.get("time")))).collect(Collectors.toList());
        if(CollectionUtils.isEmpty(lastList)){
            List<String> timeList = listLast.stream().map(o->String.valueOf(o.get("time"))).collect(Collectors.toList());
            listFast = listFast.stream().filter(o->timeList.contains(String.valueOf(o.get(createTime)))).collect(Collectors.toList());
            return listFast;
        }else{
            lastList.stream()
                    .forEach((Map m)->m.put("UNIT_NAME",key));
            listFast.addAll(lastList);
        }
        Collections.sort(listFast, (p1, p2) -> {
            return String.valueOf(p1.get("time")).compareTo(p2.get("time") + "");
        });
        return listFast;
    }


    public static List<String> filterList(List<Map<String,Object>> listFast,List<String> listLast){
        if(CollectionUtils.isEmpty(listFast)){
            return listLast;
        }
        List<String> bIDs = listFast.stream().map(o->String.valueOf(o.get("date")).substring(0,String.valueOf(o.get("date")).indexOf("."))).collect(Collectors.toList());
        listLast = listLast.stream().filter(o->!bIDs.contains(o)).collect(Collectors.toList());
        return listLast;
    }
    public static List<String> filterPowerList(List<Map<String,Object>> listFast,List<String> listLast){
        if(CollectionUtils.isEmpty(listFast)){
            return listLast;
        }
        List<String> bIDs = listFast.stream().map(o->String.valueOf(o.get("time")).substring(0,String.valueOf(o.get("time")).indexOf("."))).collect(Collectors.toList());
        listLast = listLast.stream().filter(o->!bIDs.contains(o)).collect(Collectors.toList());
        return listLast;
    }


    public static List<Map<String,Object>> getMeaInsCmobo(List<Map<String,Object>> mapList,String uids){
        List<Map<String,Object>> treeList = new ArrayList<>();
        Iterator<Map<String, Object>> mapListIterator = mapList.listIterator();
        while (mapListIterator.hasNext()){
            Map<String, Object> next = mapListIterator.next();
            if(next.get("PARENT_ID").equals(uids)){
                treeList.add(next);
                mapListIterator.remove();
            }
        }
        for(Map<String, Object> map:treeList){
            List<Map<String, Object>> valueList = getMeaInsCmobo(mapList, map.get("value") + "");
            if(!CollectionUtils.isEmpty(valueList)) {
                map.put("children",valueList );
            }
        }
        return treeList;
    }

    public static String getTime(String pattern){
        return new SimpleDateFormat(pattern).format(new Date());
    }

    /**
     * 获取昨天日期
     * @return
     */
    public static String getYesterday(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH,calendar.get(Calendar.DAY_OF_MONTH)-1);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(calendar.getTime());
    }
    /**
     * 获取某月的最后一天
     */
    public static String getEachMonth(String month){
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.MONTH,Integer.parseInt(month)-1);
        int dy = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        String day = dy<10?"0"+dy:dy+"";
        return day;
    }

    /**
     * 获取某月的最后一天
     */
    public static String getEm(String time){
        String[] timeArr = time.split("-");
        String month = timeArr.length > 1?timeArr[1]:"";
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR,Integer.parseInt(timeArr[0]));
        calendar.set(Calendar.MONTH,Integer.parseInt(month)-1);
        int dy = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        return dy<10?"0"+dy:dy+"";
    }



    public static List<Map<String,Object>>  getXaxisList(String startTime,String endTime,String type){
        List<Map<String,Object>> xAxisList = new ArrayList<>();
        String[] timeArr = startTime.split("-");
        timeArr[1] = timeArr.length > 1 ? timeArr[1] : "";
        timeArr[2] = timeArr.length > 2 ? timeArr[2] : "";
        String[] eTimeArr = endTime.split("-");
        eTimeArr[2] = eTimeArr.length > 2 ? eTimeArr[2] : "";
        Calendar instance = Calendar.getInstance();
        int sDay = Integer.parseInt(timeArr[2]);
        int eDay = Integer.parseInt(eTimeArr[2]);
        if(type.equals("1")) {
            instance.set(Calendar.MONTH, Integer.parseInt(timeArr[1]) - 1);
            int day = instance.getActualMaximum(Calendar.DAY_OF_MONTH);
            for (int i = sDay; i < day; i++) {
                Map saveMap = new HashMap<>();
                saveMap.put("time", (i < 10) ? "0" + i : i + "");
                xAxisList.add(saveMap);
            }
            for (int i = 1; i < eDay; i++) {
                Map saveMap = new HashMap<>();
                saveMap.put("time", (i < 10) ? "0" + i : i + "");
                xAxisList.add(saveMap);
            }
        }else{
            for (int i = sDay; i < eDay; i++) {
                Map saveMap = new HashMap<>();
                saveMap.put("time", (i < 10) ? "0" + i : i + "");
                xAxisList.add(saveMap);
            }
        }
        return xAxisList;
    }






    /**
     * 判断字符串中是否包含中文
     * @param str
·     * @return 是否为中文
     * @warn 不能校验是否为中文标点符号
     */
    public static  boolean isContainChinese(String str) {
        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(str);
        if (m.find()) {
            return true;
        }
        return false;
    }


}
