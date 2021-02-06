package com.myutils.utils;

import java.text.SimpleDateFormat;
import java.util.TimeZone;

/**
 * 生成时间流水号加随机数作为唯一id的工具类
 *
 */
public class SnUtil {
	
	private static int sn = 0;
	
	/**
	 * 生成一个新的唯一id，21位：17位时间+四位流水号
	 * @return 返回生成的流水号
	 */
	public synchronized static String getNewID() {
		if(sn>=9999) {
			sn = 0;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		sdf.setTimeZone(TimeZone.getTimeZone("GMT+8"));
		// 年4月2日2时2分2秒2毫秒3序列4共21位
		return sdf.format(new java.util.Date())
				+ new java.text.DecimalFormat("0000").format( sn++ );
	}

	/**
	 * 生成一个新的唯一id，使用自定义前缀，前缀+17位时间+四位流水号
	 * @param prefix 指定前缀
	 * @return 返回生成的流水号
	 */
	public synchronized static String getNewID(String prefix) {
		if(sn>=9999) {
			sn = 0;
		}
		// 年4月2日2时2分2秒2毫秒3序列4共21位
		return prefix.concat(new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new java.util.Date())
				+ new java.text.DecimalFormat("0000").format( sn++ ));
	}
	
}
