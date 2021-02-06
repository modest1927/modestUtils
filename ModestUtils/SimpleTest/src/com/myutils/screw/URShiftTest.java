package com.myutils.screw;

import java.util.HashMap;

/**
 * @Description 移位运算符
 * @Author OYQJ
 * @Date 2020-08-29
 */
public class URShiftTest {
    public static void main(String[] args) {
        HashMap<Object, Object> map = new HashMap<>();
        int MAXIMUM_CAPACITY = 1 << 30;
        System.out.println("MAXIMUM_CAPACITY = " + MAXIMUM_CAPACITY);
        int cap = 10;
        int n = cap - 1;
//        n |= n >>> 1;
        n = n | n >>> 1;
        System.out.println("n1 = " + n);
        n |= n >>> 2;
        System.out.println("n2 = " + n);
        n |= n >>> 4;
        System.out.println("n4 = " + n);
        n |= n >>> 8;
        System.out.println("n8 = " + n);
        n |= n >>> 16;
        System.out.println("n16 = " + n);
        System.out.println((n < 0) ? 1 : (n >= MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : n + 1);
        //移位运算符
        URShift();

    }

    public static void URShift() {
        //左移
        int i = 12; //二进制为:0000000000000000000000000001100
        i <<= 2; //i左移2位，把高位的两位数字(左侧开始)抛弃,低位的空位补0,二进制码就为0000000000000000000000000110000
        System.out.println(i); //二进制110000值为48；
        System.out.println("<br>");
        //右移
        i >>= 2; //i右移2为，把低位的两个数字(右侧开始)抛弃,高位整数补0，负数补1，二进制码就为0000000000000000000000000001100
        System.out.println(i); //二进制码为1100值为12
        System.out.println("<br>");
        //右移example
        int j = 11;//二进制码为00000000000000000000000000001011
        j >>= 2; //右移两位，抛弃最后两位,整数补0,二进制码为：00000000000000000000000000000010
        System.out.println(j); //二进制码为10值为2
        System.out.println("<br>");
        byte k = -2; //转为int,二进制码为：0000000000000000000000000000010
        k >>= 2; //右移2位，抛弃最后2位，负数补1,二进制吗为：11000000000000000000000000000
        System.out.println(j); //二进制吗为11值为2
    }

}
