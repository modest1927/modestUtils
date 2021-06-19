package com.myutils.workTest;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @Description
 * @Author OYQJ
 * @Date 2021-06-16
 */
@Slf4j
public class ComplexSheetParse {
    public static void main(String[] args) throws IOException {
        //获取resources下指定路径下的所有文件
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        long s = System.currentTimeMillis();
        Resource[] resources = resolver.getResources("classpath:realDataExcel/*.*");
        //创建表头
        List<String> allSheetList = CollUtil.newArrayList("技术指标表（表五乙）","技术指标表（表五丁）","技术指标表（表五丁）",
                "变电站工程概况及主要技术经济指标表","电缆输电线路工程概况及主要技术经济指标表","电缆输电线路建设场地征用及清理费用(估、概、预、结)算表",
                "通信线路工程概况及主要技术经济指标表");
        for (Resource resource : resources) {
            String filename = resource.getFilename();
            log.info("开始解析文件:{}", filename);
            File file = resource.getFile();
            ExcelReader reader = ExcelUtil.getReader(file);
            List<Sheet> sheets = reader.getSheets();
            for (Sheet sheet : sheets) {
                String sheetName = sheet.getSheetName();
                if (allSheetList.contains(sheetName)){
                    log.info("开始解析:sheetName-{}", sheetName);
                    //获取sheet
                    ExcelReader sheetReader = ExcelUtil.getReader(file, sheetName);
                    //直接从标记位的行号获取sheet数据
                    List<List<Object>> readList = sheetReader.read(2);
                    for (List<Object> objects : readList) {

                    }
                }
            }
        }
        long l = System.currentTimeMillis() - s;

        log.info("解析耗时:{}ms", l);

    }

}
