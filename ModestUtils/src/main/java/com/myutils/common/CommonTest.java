package com.myutils.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.AsyncResult;

import java.io.UnsupportedEncodingException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @Description
 * @Author OYQJ
 * @Date 2020-08-28
 */
@Slf4j
public class CommonTest {

    public static void main(String[] args) throws ExecutionException, InterruptedException, UnsupportedEncodingException {
        /*String secret = "2a95242cd1df45ab922c224bc9f01271";//锦江掌上永荣 给的
        String tokenStr = MD5Util.MD5Encode("@_@" + "202010151044" + secret, "UTF-8");
        System.out.println(tokenStr);
        System.out.println("\"2020-10-01 00:00:00\".substring(11,19) = " + "2020-10-01 00:00:00".substring(11, 16));
        testStringValueOf();*/

        /*Student student = new Student();
        student = null;
        System.gc();
        SoftReference<Student> studentSoftReference = new SoftReference<>(new Student());
        Student student1 = studentSoftReference.get();*/

        /*String substring = "2020-11-25 10:45:34".substring(0, 10);
        System.out.println("substring = " + substring);
        System.out.println(PinyinUtil.getPinyin("欧阳桥建"));
        System.out.println(PinyinUtil.getFirstLetter('欧'));
        System.out.println("10002".substring(1));*/

        /*Future<Boolean> saveLog = saveLog();
        if (saveLog.get()){
            System.out.println("继续");
        }*/

        /*String url = "aHR0cDovL2p4emx0ZXN0LnNnY2N0ZC5jbi9qeHpsL2ZpbGVtYWcvZ2V0RmlsZUJ5SWQ%2FZmlsZUlkPTdjMDQ4M2ZhLTE0ZWUtNGQxNC1iYTU2LWM2MGFkNzQzNzU0NCZmdWxsZmlsZW5hbWU9N2MwNDgzZmEtMTRlZS00ZDE0LWJhNTYtYzYwYWQ3NDM3NTQ0LnBkZg%3D%3D";
        String urlDecode = URLDecoder.decode(url, "utf-8");
        String s = Base64.decodeStr(urlDecode);
        System.out.println(s);*/
        String s = "1、当班期间运行方式变动情况\n 无\n 2、交班终了运行方式\n （1）35kV站用电Ⅰ、Ⅲ回运行正常，110kV站用电Ⅱ回热备用状态；\n （2）双极水冷系统正常方式运行；\n （3）控制楼、阀厅空调系统正常方式运行。\n";

    }


    public static Future<Boolean> saveLog(){
        Boolean result = true;
        return new AsyncResult<Boolean>(result);
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
