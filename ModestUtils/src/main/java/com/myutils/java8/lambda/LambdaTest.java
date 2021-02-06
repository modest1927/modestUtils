package com.myutils;

import com.myutils.utils.DateUtils;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class LambdaTest {

	public static void main(String[] args) throws ParseException {
        List<HashMap<String,Object>> list = new ArrayList<>();
        list.add(new HashMap<String,Object>(){{put("status", "tj");}});
        list.add(new HashMap<String,Object>(){{put("status", "zc");}});
        list.add(new HashMap<String,Object>(){{put("status", "zc");}});
        Map<Boolean, Long> status = list.stream().collect(Collectors.groupingBy(map -> "tj".equals(map.get("status")), Collectors.counting()));
        System.out.println(status.get(true));
        System.out.println(status.get(false));
        System.out.println("status = " + status);
        Map<HashMap<String, Object>, Long> collect = list.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        Set<Map.Entry<HashMap<String, Object>, Long>> entries = collect.entrySet();
        for (Map.Entry<HashMap<String, Object>, Long> entry : entries) {
            System.out.println(entry.getKey());
        }
        BigDecimal SYTQ_power = new BigDecimal("12");
        BigDecimal QNTQ_power = new BigDecimal("14");
        BigDecimal sum = new BigDecimal("10");
        BigDecimal divide = sum.subtract(SYTQ_power).multiply(new BigDecimal("100")).divide(SYTQ_power,2,BigDecimal.ROUND_HALF_UP);
        System.out.println(divide);

        Map<Object, List<HashMap<String, Object>>> status1 = list.stream().collect(Collectors.groupingBy(map -> map.get("status")));
        Set<Object> keySet = status1.keySet();
        for (Object o : keySet) {

        }
        System.out.println("status1 = " + status1);

        System.out.println("-------------------");
        String date = "2019-06";
        List<String> monthFullDay = DateUtils.getMonthFullDay(date);
        System.out.println(monthFullDay);
        System.out.println("-------------------");
        String syDate ="";
        String qnDate ="";
        String dateTime = "2019-06-31";
        syDate = DateUtils.getLastMonth(dateTime.substring(0, 7)) + dateTime.substring(7);
        qnDate = DateUtils.getLastYear(dateTime.substring(0,4)) +dateTime.substring(4,7) + dateTime.substring(7);
        System.out.println(syDate);
        System.out.println(qnDate);
    }
}
