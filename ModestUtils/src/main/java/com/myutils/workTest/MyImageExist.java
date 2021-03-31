package com.myutils.workTest;

import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MyImageExist {

    public static void main(String[] args){
        long s = System.currentTimeMillis();
        try {
            //创建数据源对象
            DriverManagerDataSource ds = new DriverManagerDataSource();
            ds.setDriverClassName("com.mysql.cj.jdbc.Driver");
            ds.setUrl("jdbc:mysql://192.168.99.42:3306/ycbp_zs?serverTimezone=Asia/Shanghai");
            ds.setUsername("root");
            ds.setPassword("123456");
            //创建jdbcTemplate对象
            JdbcTemplate jt = new JdbcTemplate();
            //执行操作（插入操作）
            jt.setDataSource(ds);
            String sql = "SELECT b.user_name,a.FILE_NAME,a.FILE_PATH,c.unit_name FROM s_attach_list a,s_user b,s_unit c " +
                    "WHERE a.transaction_id = b.user_id  AND transaction_type = 'sp_user_data'  AND c.UNIT_ID=b.UNIT_ID ";
            List<Map<String, Object>> dataList = jt.queryForList(sql);
            List<Map<String,Object>> noFileDataList = new ArrayList<>();
            if (CollectionUtils.isNotEmpty(dataList)){
                for (Map<String, Object> map : dataList) {
                    String fileName = MapUtils.getString(map, "FILE_NAME", "");
                    String filePath = MapUtils.getString(map, "FILE_PATH", "");
                    if (StringUtils.isNotBlank(fileName)){
                        String path = filePath + "\\" + fileName;
                        File file = new File(path);
                        /*if (!file.exists()){
                            map.put("path",path);
                            noFileDataList.add(map);
                        }*/
                        long length = file.length()/1000;
                        if (length < 10){
                            map.put("path",path);
                            map.put("size",length);
                            noFileDataList.add(map);
                        }
                    }
                }
            }
            if(noFileDataList.size()>0) {
                //输出为txt
                /*try {
                    String line = System.getProperty("line.separator");
                    StringBuffer str = new StringBuffer();
                    FileWriter fw = new FileWriter("D:\\imageIsExist.txt", true);
                    for (Map<String, Object> map : noFileDataList) {
                        String userName = MapUtils.getString(map, "user_name", "");
                        String unitName = MapUtils.getString(map, "unit_name", "");
                        String path = MapUtils.getString(map, "path", "");
                        str.append(userName+" : "+unitName+"【"+path+"】").append(line);
                    }
                    fw.write(str.toString());
                    System.out.println ("写入成功!");
                    fw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }*/
                //输出为excel
                ExcelWriter writer = ExcelUtil.getWriter("D:/isSmall.xlsx");
                writer.passCurrentRow();
                writer.write(noFileDataList, true);
                writer.close();
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        long l = System.currentTimeMillis() - s;
        System.out.println("耗时: " + l/1000 +"s");
    }
}
