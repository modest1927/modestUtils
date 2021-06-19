package com.myutils.workTest;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Description
 * @Author OYQJ
 * @Date 2021-06-16
 */
@Slf4j
public class ExcelFileParse {
    public static void main(String[] args) throws IOException {
        //获取resources下指定路径下的所有文件
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        long s = System.currentTimeMillis();
        Resource[] resources = resolver.getResources("classpath:realDataExcel/*.*");
        List<String> allSheetList = CollUtil.newArrayList("变电总算表（表一甲）","建筑部分汇总表（表二乙）","安装部分汇总表（表二甲）","其他费用表（表四）"/*,"技术指标表（表五乙）"*/,"建设场地征用及清理费用表（表七）","送电总算表（表一丁）","安装部分汇总表（表二丁）","建筑部分汇总表（表二丁）"
                ,"其他费用表（表四）"/*,"技术指标表（表五丁）"*/,"建设场地征用及清理费用表（表七）","送电总算表（表一丙）","架空输电线路工程部分汇总表（表二丙）","其他费用表（表四）"/*,"技术指标表（表五丁）"*/,"建设场地征用及清理费用表（表七）","送电总算表（表一）","通信线路工程部分汇总表（表二）","其他费用表（表四）",
                "建设场地征用及清理费用表（表七）","变电站工程总(估、概、预、结)算表","变电站建筑工程专业汇总(估、概、预、结)算表","变电站安装工程汇总(估、概、预、结)算表","变电站工程其他费用(估、概、预、结)算表"/*,"变电站工程概况及主要技术经济指标表"*/,"变电站工程建设场地征用及清理费用(估、概、预、结)算表",
                "电缆输电线路工程总(估、概、预、结)算表","电缆输电线路安装工程费用汇总(估、概、预、结)算表","电缆输电线路建筑工程费用汇总(估、概、预、结)算表","电缆输电线路其他费用(估、概、预、结)算表"/*,"电缆输电线路工程概况及主要技术经济指标表"*/,"电缆输电线路建设场地征用及清理费用(估、概、预、结)算表"
                ,"架空输电线路工程总(估、概、预、结)算表","架空输电线路安装工程汇总(估、概、预、结)算表","架空输电线路其他费用(估、概、预、结)算表"/*,"架空输电线路工程概况及主要技术经济指标表"*/,"架空输电线路建设场地征用及清理费用(估、概、预、结)算表","通信线路工程总(估、概、预、结)算表","通信线路安装工程汇总(估、概、预、结)算表",
                "通信线路其他费用(估、概、预、结)算表"/*,"通信线路工程概况及主要技术经济指标表"*/,"通信线路建设场地征用及清理费用(估、概、预、结)算表");
        List<Map> allSheetDataList = new ArrayList<>();
        for (Resource resource : resources) {
            String filename = resource.getFilename();
            File file = resource.getFile();
            ExcelReader reader = ExcelUtil.getReader(file);
            List<Sheet> sheets = reader.getSheets();
            for (Sheet sheet : sheets) {
                String sheetName = sheet.getSheetName();
                if (allSheetList.contains(sheetName)){
                    log.info("开始解析:sheetName-{}", sheetName);
                    //寻找标记位置
                    int flagRowIndex = -1;
                    int flagColIndex = -1;
                    for(int r=0;r<sheet.getLastRowNum()+1;r++) {
                        Row row = sheet.getRow(r);
                        if (row != null) {
                            for (int c = 0; c < row.getLastCellNum() + 1; c++) {
                                Cell cell = row.getCell(c);
                                if (cell == null) continue;
                                String objvalue = com.sgcctd.model.models.excel.util.ExcelUtil.getCellValue(cell) ;
                                if(objvalue!=null&&objvalue.startsWith("工程或费用名称")) {
                                    flagRowIndex = r ;
                                    flagColIndex = c ;
                                    log.info("找到标记位，标记位值为:{}",objvalue);
                                    break;
                                }
                            }
                        }
                    }
                    //获取sheet页的数据
                    ExcelReader sheetReader = ExcelUtil.getReader(file, sheetName);
                    List<List<Object>> readList = sheetReader.read(2);
                    List<Object> rowList_0 = readList.get(0);
                    //判断是否为多行表头
                    if (readList.size() > 1) {
                        List<Object> rowList_1 = readList.get(1);
                        if (Objects.equals(rowList_0.get(0), rowList_1.get(0))) {
                            for (int i = 0; i < rowList_0.size(); i++) {
                                rowList_0.set(i,!Objects.equals(rowList_0.get(i), rowList_1.get(i)) ? rowList_0.get(i) + "_" + rowList_1.get(i) : rowList_0.get(i));
                            }
                            readList.remove(1);
                        }
                    }
                    //找到所有的指标值对应的 所有表头值
                    Map<String, List<Object>> sheetDataMap = new HashMap<>();
                    Map sheetMap = new HashMap<>();
                    sheetMap.put("sheetName", sheetName);
                    for (int i = flagColIndex ; i < readList.size(); i++) {
                        List<Object> rowDataList = readList.get(i);
                        List colDataList = new ArrayList<>();
                        Object indexName = rowDataList.get(1);
                        for (int j = flagRowIndex; j < rowDataList.size(); j++) {
                            Map dataMap = new HashMap<>();
                            dataMap.put(rowList_0.get(j), rowDataList.get(j));
                            colDataList.add(dataMap);
                        }
                        if (indexName != null) {
                            sheetDataMap.put(indexName + "", colDataList);
                        }
                    }
                    sheetMap.put("data", sheetDataMap);
                    allSheetDataList.add(sheetMap);
                }
            }
        }
        long l = System.currentTimeMillis() - s;
        List<String> allFieldList = new  ArrayList<>();
        for (Map sheetDataMap : allSheetDataList) {
            String sheetName = MapUtils.getString(sheetDataMap, "sheetName", "");
            Map<String,List<Map<String,Object>>> dataMap = (Map) MapUtils.getObject(sheetDataMap, "data");
            for (Map.Entry<String, List<Map<String, Object>>> dataEntry : dataMap.entrySet()) {
                List<Map<String, Object>> indexList = dataEntry.getValue();
                for (Map<String, Object> map : indexList) {
                    allFieldList.addAll(map.keySet());
                }
            }
        }
        System.out.println(allFieldList.stream().distinct().collect(Collectors.joining(",")));
        log.info("解析耗时:{}ms", l);

    }


    public void ex () throws IOException {
       /* //获取resources下指定路径下的所有文件
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        Resource[] resources = resolver.getResources("classpath:excelFile/*.*");
        //创建表头
        List<String> allSheetList = CollUtil.newArrayList("变电总算表（表一甲）","建筑部分汇总表（表二乙）","安装部分汇总表（表二甲）","其他费用表（表四）"*//*,"技术指标表（表五乙）"*//*,"建设场地征用及清理费用表（表七）","送电总算表（表一丁）","安装部分汇总表（表二丁）","建筑部分汇总表（表二丁）"
                ,"其他费用表（表四）"*//*,"技术指标表（表五丁）"*//*,"建设场地征用及清理费用表（表七）","送电总算表（表一丙）","架空输电线路工程部分汇总表（表二丙）","其他费用表（表四）"*//*,"技术指标表（表五丁）"*//*,"建设场地征用及清理费用表（表七）","送电总算表（表一）","通信线路工程部分汇总表（表二）","其他费用表（表四）",
                "建设场地征用及清理费用表（表七）","变电站工程总(估、概、预、结)算表","变电站建筑工程专业汇总(估、概、预、结)算表","变电站安装工程汇总(估、概、预、结)算表","变电站工程其他费用(估、概、预、结)算表"*//*,"变电站工程概况及主要技术经济指标表"*//*,"变电站工程建设场地征用及清理费用(估、概、预、结)算表",
                "电缆输电线路工程总(估、概、预、结)算表","电缆输电线路安装工程费用汇总(估、概、预、结)算表","电缆输电线路建筑工程费用汇总(估、概、预、结)算表","电缆输电线路其他费用(估、概、预、结)算表"*//*,"电缆输电线路工程概况及主要技术经济指标表"*//*,"电缆输电线路建设场地征用及清理费用(估、概、预、结)算表"
                ,"架空输电线路工程总(估、概、预、结)算表","架空输电线路安装工程汇总(估、概、预、结)算表","架空输电线路其他费用(估、概、预、结)算表"*//*,"架空输电线路工程概况及主要技术经济指标表"*//*,"架空输电线路建设场地征用及清理费用(估、概、预、结)算表","通信线路工程总(估、概、预、结)算表","通信线路安装工程汇总(估、概、预、结)算表",
                "通信线路其他费用(估、概、预、结)算表"*//*,"通信线路工程概况及主要技术经济指标表"*//*,"通信线路建设场地征用及清理费用(估、概、预、结)算表");
        List<String> tableColList = CollUtil.newArrayList("sheet名称", "列名");
        Map<Object,Object> allColMap = new LinkedHashMap<>();
        List<Map<String, Object>> rows = new ArrayList<>();
        for (Resource resource : resources) {
            String filename = resource.getFilename();
            File file = resource.getFile();
            ExcelReader reader = ExcelUtil.getReader(file);
            List<Sheet> sheets = reader.getSheets();
            for (Sheet sheet : sheets) {
                String sheetName = sheet.getSheetName();
                if (allSheetList.contains(sheetName)){
                    ExcelReader sheetReader = ExcelUtil.getReader(file, sheetName);
                    List<List<Object>> readList = sheetReader.read(2);
                    //拼接多行表头
                    List<Object> rowList_0 = readList.get(0);
                    if (readList.size() > 1) {
                        List<Object> rowList_1 = readList.get(1);
                        if (Objects.equals(rowList_0.get(0), rowList_1.get(0))) {
                            for (int i = 0; i < rowList_0.size(); i++) {
                                allColMap.put(!Objects.equals(rowList_0.get(i), rowList_1.get(i)) ? rowList_0.get(i) + "_" + rowList_1.get(i) : rowList_0.get(i), sheetName);
                            }
                        } else {
                            for (int i = 0; i < rowList_0.size(); i++) {
                                allColMap.put(rowList_0.get(i), sheetName);
                            }
                        }
                    } else {
                        for (int i = 0; i < rowList_0.size(); i++) {
                            allColMap.put(rowList_0.get(i), sheetName);
                        }
                    }
                }
            }
            //对列名去重
            for (Map.Entry<Object, Object> entry : allColMap.entrySet()) {
                Map rowMap = new HashMap<>();
                rowMap.put("sheet名称",entry.getValue());
                rowMap.put("列名", entry.getKey());
                rows.add(rowMap);
            }
        }
        rows = rows.stream().filter(o -> o != null && o.get("sheet名称") != null)
                .sorted((o1, o2) -> (o2.get("sheet名称") + "").compareTo(((o1.get("sheet名称") + "")))).distinct().collect(Collectors.toList());
        //输出为excel
        ExcelWriter writer = ExcelUtil.getWriter("f:/表头2.xls");
        writer.passCurrentRow();
        writer.write(rows, true);
        writer.close();
        System.out.println("输出成功: ");*/
    }

}
