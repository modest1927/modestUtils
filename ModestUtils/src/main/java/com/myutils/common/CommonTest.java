package com.myutils.common;

import cn.hutool.core.lang.PatternPool;
import cn.hutool.core.lang.Validator;
import cn.hutool.core.util.ReUtil;
import com.myutils.utils.UUIDGenerator;
import sun.misc.Regexp;

import java.util.regex.Pattern;

/**
 * @Description
 * @Author OYQJ
 * @Date 2020-08-28
 */
public class CommonTest {

    public static void main(String[] args) {
//        String secret = "2a95242cd1df45ab922c224bc9f01271";//锦江掌上永荣 给的
//        String tokenStr = MD5Util.MD5Encode("@_@" + "202010151044" + secret, "UTF-8");
//        System.out.println(tokenStr);
//        System.out.println("\"2020-10-01 00:00:00\".substring(11,19) = " + "2020-10-01 00:00:00".substring(11, 16));
//        testStringValueOf();


        /*Student student = new Student();
        student = null;
        System.gc();
        SoftReference<Student> studentSoftReference = new SoftReference<>(new Student());
        Student student1 = studentSoftReference.get();*/


//        String substring = "2020-11-25 10:45:34".substring(0, 10);
//        System.out.println("substring = " + substring);
//        System.out.println(PinyinUtil.getPinyin("欧阳桥建"));
//        System.out.println(PinyinUtil.getFirstLetter('欧'));
        System.out.println("10002".substring(1));
        /**
         * 线路sp_line
         * 线路单元sp_line_unit
         * 地线sp_ground_wire
         * 是否启用
         *位置
         */
        System.out.println(UUIDGenerator.getNewID());

        String s = "2222*111";
        Regexp regexp = new Regexp("(*)");
        Pattern pattern = PatternPool.get("");
        Validator.isMatchRegex("","");
        ReUtil.isMatch(",","");
    }

    public static class Student {
        @Override
        protected void finalize() throws Throwable {
            System.out.println("Student 被回收了");
        }
    }

    /**
     * 测试字符串转变的性能
     */
    public static void testStringValueOf() {
        long t = System.currentTimeMillis();
        for (int i = 0; i < 1000000; i++) {
            String s0 = String.valueOf(11);
        }
        System.out.println("耗时" + (System.currentTimeMillis() - t));

        t = System.currentTimeMillis();
        for (int i = 0; i < 1000000; i++) {
            String s = "" + 11;
        }
        System.out.println("耗时" + (System.currentTimeMillis() - t));

        String str = "";
        t = System.currentTimeMillis();
        for (int i = 0; i < 1000000; i++) {
            String s = str + 11;
        }
        System.out.println("耗时" + (System.currentTimeMillis() - t));
    }
}
