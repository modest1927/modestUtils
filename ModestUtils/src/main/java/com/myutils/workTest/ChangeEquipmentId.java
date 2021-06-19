package com.myutils.workTest;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Description
 * @Author OYQJ
 * @Date 2021-05-26
 */
public class ChangeEquipmentId {
    public static void main(String[] args) {
        JdbcTemplate jt = JdbcInit.getJdbcTemplate();
        List<Object[]> argList = new ArrayList<>();
        String sql="SELECT t.uids,t.equipment_id,t.equipment_name,t.equipment_no,t.equipment_model,a.equipment_code FROM sp_equipment_basic_info t LEFT JOIN sp_equipment_property_info a on a.uids = t.equipment_no where t.operation_date >= '2021-05-26' ";
        List<Map<String, Object>> list = jt.queryForList(sql);
        String updateSql = "UPDATE sp_equipment_basic_info SET equipment_id = ? WHERE uids = ?";
        List<String> uidsList = new ArrayList<>();
        if(CollectionUtils.isNotEmpty(list)){
            Map<String, List<Map<String, Object>>> equipmentListMap = list.stream().collect(Collectors.groupingBy(e -> e.get("equipment_no") + ""));
            for (Map.Entry<String, List<Map<String, Object>>> entry : equipmentListMap.entrySet()) {
                String key = entry.getKey();
                List<Map<String, Object>> valueList = entry.getValue();
                int size = valueList.size();
                Map<String, List<Map<String, Object>>> sameEquipMap = valueList.stream().collect(Collectors.groupingBy(e -> e.get("equipment_id") + ""));
                if (sameEquipMap.size() == 1){
                    String equipmentCode = MapUtils.getString(valueList.get(0), "equipment_code", "");
                    for (Map.Entry<String, List<Map<String, Object>>> listEntry : sameEquipMap.entrySet()) {
                        List<Map<String, Object>> mapList = listEntry.getValue();
                        if (mapList.size() == 1){
                            continue;
                        }
                        for (int i = 1; i <= mapList.size(); i++) {
                            String uids = MapUtils.getString(mapList.get(i - 1), "uids", "");
                            String serialNumber = String.valueOf(10000 + i).substring(1);
                            String newEquipmentId = equipmentCode + serialNumber;
                            Object[] argObj = {newEquipmentId,uids};
                            uidsList.add(uids);
                            argList.add(argObj);
                        }
                    }
                }
            }
        }
        if(CollectionUtils.isNotEmpty(argList)){
            System.out.println(uidsList.stream().collect(Collectors.joining(",")));
//            int[] ints = jt.batchUpdate(updateSql, argList);
//            System.out.println("更新数量:"+ints.length);
        }
    }
}
