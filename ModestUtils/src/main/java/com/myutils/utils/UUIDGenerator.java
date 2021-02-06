package com.myutils.utils;

import java.text.SimpleDateFormat;
import java.util.TimeZone;


public class UUIDGenerator {
	private static int sn = 0;

	public synchronized static String getNewID() {
		if(sn>=9999) {
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			sn = 0;
		}
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		 dateFormat.setTimeZone(TimeZone.getTimeZone("GMT+8"));
		return dateFormat.format(new java.util.Date()) + new java.text.DecimalFormat("0000").format( sn++ );
	}
}