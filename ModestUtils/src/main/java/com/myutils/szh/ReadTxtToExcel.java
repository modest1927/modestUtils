package com.myutils.szh;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * @Description
 * @Author OYQJ
 * @Date 2021-03-16
 */
public class ReadTxtToExcel {
    public static void main(String[] args) throws IOException {
        //获取resources下指定路径下的所有文件
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        Resource[] resources = resolver.getResources("classpath:txtFile/*.*");
        InputStream in=null;
        InputStreamReader ir = null;
        BufferedReader br = null;
        //创建表头
        List<String> allColList = CollUtil.newArrayList("S/N", "Sale Order Number", "User ID", "Option","Option key");
        ArrayList<Map<String, Object>> rows = new ArrayList<>();
        long s = System.currentTimeMillis();
        for (Resource resource : resources) {
            try {
                //获取文件流
                in = resource.getInputStream();
                ir=new InputStreamReader(in , StandardCharsets.UTF_8);
                br= new BufferedReader(ir);
                String line="";
                Map<String, Object> rowMap = new LinkedHashMap<>();
                String filename = resource.getFilename();
                rowMap.put("文件名",filename);
                while((line=br.readLine())!=null){
                    if (StringUtils.isEmpty(line) || line.contains("���") ||
                            line.contains("Workstation Sale Order Number") || line.contains("Option Sale Order Number")){
                        continue;
                    }
                    String[] split = line.split(":");
                    String[] split2 = line.split("：");
                    String[] split3 = line.split("��");
                    if (split.length > 1 || split2.length > 1){
                        String colName = split.length > split2.length ? split[0] : split2[0];
                        if (allColList.contains(colName)){
                            String colValue = split.length > split2.length ? split[1] : split2[1];
                            rowMap.put(colName,colValue);
                        }
                    }else if (split3.length > 1){
                        String colName = split3[0];
                        if (allColList.contains(colName)){
                            String colValue = split3[1];
                            rowMap.put(colName,colValue);
                        }
                    }
                }
                rows.add(rowMap);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                //一定要关闭流,倒序关闭
                try {
                    if(br!=null){
                        br.close();
                    }
                    if(ir!=null){
                        ir.close();
                    }
                    if(in!=null){
                        in.close();
                    }
                } catch (Exception e2) {

                }
            }
        }
        //将rowMap的size最大的拿出来作为统一表头
        Optional<Map<String, Object>> max = rows.stream().max(Comparator.comparingInt(Map::size));
        if (max.isPresent()) {
            Map<String, Object> maxMap = max.get();
            Iterator<String> it = maxMap.keySet().iterator();
            while (it.hasNext()){
                String col = it.next();
                for (Map<String, Object> row : rows) {
                    if (!row.keySet().contains(col)){
                        row.put(col,"-");
                    }
                }
            }
        }

        //处理size小于表头数的数据,如果没有则为空
        //输出为excel
        ExcelWriter writer = ExcelUtil.getWriter("f:/txtToExcel2.xlsx");
        writer.passCurrentRow();
        writer.write(rows, true);
        writer.close();

        long l = System.currentTimeMillis() - s;
        System.out.println("耗时: " + l/1000 +"s");
    }
}
