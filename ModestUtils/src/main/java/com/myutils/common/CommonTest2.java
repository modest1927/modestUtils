package com.myutils.common;

import com.alibaba.fastjson.JSON;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @Description
 * @Author OYQJ
 * @Date 2021-05-22
 */
public class CommonTest2 {
    public static void main(String[] args) {
        String data = "[{\"startTime\":\"2021/05/08 09:30:48\",\"endTime\":\"2021/05/08 17:30:37\",\"title\":\"2021年05月12日监管记录\",\"content\":\"011B-B/C相换流变绕组直流电组、绕组连同套管的介质损耗正切值及电容量测量、绕组连同套管的绝缘电阻、套管的绝缘电阻、铁芯及夹件绝缘电阻测量、有载调压开关切换试验。\"},{\"startTime\":\"2021/05/07 09:00:03\",\"endTime\":\"2021/05/07 17:33:19\",\"title\":\"2021年05月12日监管记录\",\"content\":\"012B-A/B/C相换流变绕组直流电组。\"},{\"startTime\":\"2021/05/10 09:00:19\",\"endTime\":\"2021/05/10 17:31:34\",\"title\":\"2021年05月12日监管记录\",\"content\":\"012B-A/B/C相换流变绕组连同套管的介质损耗正切值及电容量测量、绕组连同套管的绝缘电阻、套管的绝缘电阻、铁芯及夹件绝缘电阻测量、有载调压开关切换试验。\"},{\"startTime\":\"2021/05/09 09:00:55\",\"endTime\":\"2021/05/09 16:53:10\",\"title\":\"2021年05月12日监管记录\",\"content\":\"011B-A相换流变绕组直流电组、绕组连同套管的介质损耗正切值及电容量测量、绕组连同套管的绝缘电阻、套管的绝缘电阻、铁芯及夹件绝缘电阻测量、有载调压开关切换试验、套管SF6气体含水量测量。011B-A/B/C相、012B-A/B/C相套管SF6气体含水量测量。\"},{\"startTime\":\"2021/05/11 09:19:02\",\"endTime\":\"2021/05/11 16:58:16\",\"title\":\"2021年05月12日监管记录\",\"content\":\"备用Y/Y换流变绕组直流电组、绕组连同套管的介质损耗正切值及电容量测量、绕组连同套管的绝缘电阻、套管的绝缘电阻、铁芯及夹件绝缘电阻测量、有载调压开关切换试验、套管SF6气体含水量测量。\"},{\"startTime\":\"2021/05/12 08:59:09\",\"endTime\":\"2021/05/12 12:06:20\",\"title\":\"2021年05月12日监管记录\",\"content\":\"备用Y/D换流变绕组直流电组、绕组连同套管的介质损耗正切值及电容量测量、绕组连同套管的绝缘电阻、套管的绝缘电阻、铁芯及夹件绝缘电阻测量、有载调压开关切换试验、套管SF6气体含水量测量。\"}]";
        List<Map> list = JSON.parseObject(data, List.class);
        Collections.sort(list, (p1, p2) -> String.valueOf(p1.get("startTime")).compareTo(p2.get("startTime") + ""));
        System.out.println(list);
    }
}
