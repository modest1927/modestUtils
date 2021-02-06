package com.myutils;

import java.text.ParseException;
import java.util.UUID;

public class LambdaTest2 {
    public static String[] chars = new String[]{"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
    private static int sn = 0;

	public static void main(String[] args) throws ParseException {
        String starttime="24:00";
        String endtime="07:00";
        String time="03:00:00";
        System.out.println("(starttime + \":00\").compareTo(time) = " + (starttime + ":00").compareTo(time));
        System.out.println("(endtime + \":00\").compareTo(time) = " + (endtime + ":00").compareTo(time));
        if ((starttime + ":00").compareTo(time) < 0 && (endtime + ":00").compareTo(time) >= 0) {
            System.out.println("我进来了");
        }

    }
    public static String generateShortUuid() {
        StringBuffer shortBuffer = new StringBuffer();
        String uuid = UUID.randomUUID().toString().replace("-", "");

        for(int i = 0; i < 8; ++i) {
            String str = uuid.substring(i * 4, i * 4 + 4);
            int x = Integer.parseInt(str, 16);
            shortBuffer.append(chars[x % 62]);
        }

        return shortBuffer.toString();
    }
}
