package com.myutils.utils;

import org.springframework.util.CollectionUtils;

import java.util.*;

public class TreeUtils {

    public static List<Map<String, Object>> getChildren(List<Map<String, Object>> mapList, Map<String, Object> measureDeviceMap,String parentId, String key, String value) {
        List<Map<String, Object>> treeList = new ArrayList<Map<String, Object>>();
        Iterator it = mapList.iterator();
        while (it.hasNext() && mapList.size() > 0) {
            Map<String, Object> map = (Map<String, Object>) it.next();
            if ((map.get(value)+"").equals(parentId)) {
                if (measureDeviceMap.containsKey(map.get(key)+"")){
                    map.put("isMeasure",true);
                }else {
                    map.put("isMeasure",false);
                }
                treeList.add(map);
                //使用Iterator，以便在迭代时把listData中已经添加到treeList的数据删除，迭代次数
                it.remove();
            }
        }
        for (Map<String, Object> map : treeList) {
            if (!CollectionUtils.isEmpty(map)) {
                List childrenList = getChildren(mapList, measureDeviceMap,map.get(key) + "", key, value);
                if (childrenList.size() > 0) {
                    map.put("child", childrenList);
                }else {
                    String pId = map.get("PARENT_ID")+"";
                    if ("0".equals(pId)){
                        List resList = new ArrayList();
                        Map resMap = new HashMap();
                        resMap.put("value","");
                        resMap.put("label","");
                        resList.add(resMap);
                        map.put("children", resList);
                    }
                }
            }
        }
        return treeList;
    }

}
