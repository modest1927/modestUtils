package com.myutils.workTest;

import cn.hutool.core.util.ReUtil;
import cn.hutool.core.util.StrUtil;
import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Description
 * @Author OYQJ
 * @Date 2021-03-26
 */
public class RegexpTest {

    public static void main(String[] args) {
        /**
         * 主要步骤
         */
        //取出编号查询属于什么类型
        String str = "拉开葛5012断路器";
        Pattern p = Pattern.compile("(\\d+)");
        Matcher m = p.matcher(str);
        if (m.find()) {
            String code = m.group(1);
            System.out.println("编号为:"+code);
        }else {
            //如果没有找到就匹配 地线的编号规则
            String dxRep = ".*(\\#[0-9]*).*";//地线 -
            Pattern dx = Pattern.compile(dxRep);
            Matcher dxM = dx.matcher(str);
            if (dxM.find()){
                String dxCode = dxM.group(1);
                System.out.println("地线编号为:"+dxCode);
            }
            //取出操作: 装设  拆除
            //取出安装位置: 装设  拆除
            String[] opArr = {"装设", "拆除"};
            for (String op : opArr) {
                if (str.contains(op)){
                    //更新状态 和 安装位置
                    String locationRep = ".*(（.*）).*";//地线安装位置
                }
            }
        }
        //根据不同类型找到不同正则
        String locationRep = ".*(（.*）).*";//地线安装位置
        String dxRep = "(\\#[0-9]*)";//地线
        String ddRep = "(\\d+[7])";//地刀
        String dzRep = "(\\d{4}[\\d^7])";//刀闸
        String kgRep = "(\\d+)";//开关
        //找到操作,是否包含: 合上  拉开
        String[] opArr = {"合上", "拉开"};
        for (String op : opArr) {
            if (str.contains(op)){
                //更新状态
            }
        }
    }

    @Test
    public void tets1(){
        String rrr = ".*(\\(.*\\))+.*";
//        String rrr = ".*(\\){1}).*(\\(.*\\))+.*(?!(\\({1})).*";

        String ss = "(sss)";
        String s1 = "(sss)(";
        String s2 = "(ffff(";
        String s3 = ")ffff(";

        Pattern dxPattern = Pattern.compile(rrr);
        Matcher dxMatcher = dxPattern.matcher(ss);
        Matcher dxMatcher1 = dxPattern.matcher(s1);
        Matcher dxMatcher2 = dxPattern.matcher(s2);
        Matcher dxMatcher3 = dxPattern.matcher(s3);
        if (dxMatcher.find()){
            String group = dxMatcher.group(1);
        }
        if (dxMatcher1.find()){
            String group = dxMatcher1.group(1);
        }
        if (dxMatcher2.find()){
            String group = dxMatcher2.group(1);
        }
        if (dxMatcher3.find()){
            String group = dxMatcher3.group(1);
        }

        String group1 = ReUtil.getGroup1("", "");

    }

    @Test
    public void test(){
        /**
         * 地线：(#xx)  “装设”、“拆除”
         *      在500kV葛岗线B相出线电压互感器WA-R51-T11上方引线处（500kV交流场xx号接地桩）装设500kV葛#122单相短路接地线
         * 地刀：(以7结尾+xx+接地刀闸/地刀)  “合上”、“拉开”
         *      合上葛051067接地刀闸；
         *      合上葛050117阀厅接地刀闸；
         *      拉开葛001117接地刀闸
         * 刀闸：(5位数不以7结尾+xx+隔离开关/刀闸)  “合上”、“拉开”
         *      合上葛00302隔离开关；
         * 开关：(编号+xx+“断路器”/“开关”)  “合上”、“拉开”
         *      拉开葛5012断路器；
         *      合上葛5012断路器
         */
        try {
            //地线
            String dx = "在500kV葛岗线B相出线电压互感器WA-R51-T11上方引线处（500kV交流场xx号接地桩）装设500kV葛#1233单相短路接地线";
            String dxRep = "(\\#[0-9]*)";
            Pattern dxPattern = Pattern.compile(dxRep);
            Matcher dxMatcher = dxPattern.matcher(dx);
            if (dxMatcher.find()){
                String dxCode = dxMatcher.group(1);
                System.out.println("【地线】dxCode = " + dxCode);
            }else {
                System.out.println("No match 地线");
            }
            String locationRep = ".*(（.*）).*";//地线安装位置
            boolean match = ReUtil.isMatch(locationRep, dx);
            if (match){
                String location = ReUtil.getGroup1(locationRep, dx);
                location = StrUtil.replaceChars(location,new char[]{'（','）'},"");

                System.out.println("地线安装位置:"+ location);
            }else {
                System.out.println("No match 地线安装位置");
            }

            //地刀
            String dd = "合上葛051067接地刀闸";
            String ddRep = "(\\d+[7])";
            Pattern ddPattern = Pattern.compile(ddRep);
            Matcher ddMatcher = ddPattern.matcher(dd);
            if (ddMatcher.find()){
                String ddCode = ddMatcher.group(1);
                System.out.println("【地刀】ddCode = " + ddCode);
            }else {
                System.out.println("No match 地刀");
            }

            //刀闸
            String dz = "合上葛00302隔离开关";
            String dzRep = "(\\d{4}[\\d^7])";
            Pattern dzPattern = Pattern.compile(dzRep);
            Matcher dzMatcher = dzPattern.matcher(dz);
            if (dzMatcher.find()){
                String dzCode = dzMatcher.group(1);
                System.out.println("【刀闸】dzCode = " + dzCode);
            }else {
                System.out.println("No match 刀闸");
            }

            //开关
            String kg = "拉开葛5012断路器";
            String kg2 = "拉开葛5012开关";
            String kgRep = "(\\d+)";
            Pattern kgPattern = Pattern.compile(kgRep);
            Matcher kgMatcher = kgPattern.matcher(kg);
            if (kgMatcher.find()){
                String kgCode = kgMatcher.group(1);
                System.out.println("【开关】kgCode = " + kgCode);
            }else {
                System.out.println("No match 开关");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
