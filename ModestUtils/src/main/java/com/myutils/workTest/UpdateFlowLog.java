package com.myutils.workTest;


import com.alibaba.fastjson.JSON;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Description
 * @Author OYQJ
 * @Date 2021-05-21
 */
public class UpdateFlowLog {
    public static void main(String[] args) {
        JdbcTemplate jt = JdbcInit.getJdbcTemplate();
        List<String> sqlList=new ArrayList<>();
        String sql="select log_id,params,business_id from s_flow_log ";
        List<Map<String, Object>> list = jt.queryForList(sql);
        if(list!=null&&list.size()>0){
            list.forEach(map -> {
                String logId= MapUtils.getString(map,"log_id","");
                String para=MapUtils.getString(map,"params","");
                Map paraMap= JSON.parseObject(para);
                String bId=MapUtils.getString(paraMap,"b_id","");
                String business_id=MapUtils.getString(map,"business_id","");
                if (StringUtils.isBlank(business_id)){
                    String sqls="update s_flow_log set business_id='"+bId+"' where log_id='"+logId+"'";
                    sqlList.add(sqls);
                }
            });
        }
        if(sqlList!=null&&sqlList.size()>0){
            int[] ints = jt.batchUpdate(sqlList.toArray(new String[sqlList.size()]));
            System.out.println("更新数量:"+ints.length);
//            System.out.println("更新数量:"+sqlList.size());
        }
    }
}
