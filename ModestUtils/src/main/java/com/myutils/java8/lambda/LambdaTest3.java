package com.myutils;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LambdaTest3 {

	public static void main(String[] args) throws ParseException {
        Map map = new HashMap<>();
        List<Map<String, Object>> list = new ArrayList<>();
        list.add((new HashMap<String, Object>() {{
            put("status", "1");
            put("name", "aa");
        }}));
        list.add((new HashMap<String, Object>() {{
            put("status", "2");
            put("name", "bb");
        }}));
        list.add((new HashMap<String, Object>() {{
            put("status", "3");
            put("name", "cc");
        }}));
        map.put("num",8);
        map.put("size",8);
        map.put("orderPymtSrlListListArry",list);
        String json = map.toString();


    }
}
