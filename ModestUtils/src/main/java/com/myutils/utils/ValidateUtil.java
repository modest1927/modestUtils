package com.myutils.utils;

import java.text.SimpleDateFormat;

import static org.springframework.integration.util.MessagingAnnotationUtils.hasValue;

/**
 * @author muhc
 * 参数校验工具
 */
public class ValidateUtil {
    /**
     * 是否是日期格式
     * @param datevalue 日期
     * @param dateFormat 格式,多个格式用","分隔
     * @return
     */
    public static boolean isDateString(String datevalue, String dateFormat) {
        if (!hasValue(datevalue)) {
            return false;
        }
        try {
            String[] arr = dateFormat.split(",");
            boolean flag = false;
            for (String s : arr) {
                SimpleDateFormat fmt = new SimpleDateFormat(s);
                java.util.Date dd = fmt.parse(datevalue);
                if (datevalue.equals(fmt.format(dd))) {
                    flag = true;
                    return flag;
                }
            }
            return flag;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 判断不为空，包括"null"
     * @param string
     * @return
     */
    public static boolean isNotNull(String string) {
        return  string!=null && !("").equals(string.trim()) && !("null").equals(string);
    }

    /**
     * 判断不为空,不包括"null"
     * @param str
     * @return
     */
    public static boolean NotNvl(String str) {
        return (str != null) && (!("").equals(str.trim()));
    }

    /**
     * 判断空
     * @param string
     * @return
     */
    public static boolean isNull(String string) {
        return  string==null || ("").equals(string.trim()) || ("null").equals(string);
    }
}
