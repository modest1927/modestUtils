package com.myutils.example;

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
        String substring = "2020-11-25 10:45:34".substring(0, 10);
        System.out.println("substring = " + substring);


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
