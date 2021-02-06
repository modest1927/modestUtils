package com.myutils.utils;

import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 用于处理日期格式的工具类
 * 
 * 
 */
public class DateUtil {

	public DateUtil() {
	}

	/**
	 * 获取当前系统日期
	 * 
	 * @return 返回当前系统时间,精确到日.
	 */
	public static Date getSystemDate() {
		Date gmt8 = null;
		try {
			Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"),
					Locale.CHINESE);
			Calendar day = Calendar.getInstance();
			day.set(Calendar.YEAR, cal.get(Calendar.YEAR));
			day.set(Calendar.MONTH, cal.get(Calendar.MONTH));
			day.set(Calendar.DATE, cal.get(Calendar.DATE));
			day.set(Calendar.HOUR_OF_DAY, 0);
			day.set(Calendar.MINUTE, 0);
			day.set(Calendar.SECOND, 0);
			day.set(Calendar.MILLISECOND, 0);
			gmt8 = day.getTime();
		} catch (Exception e) {
			// System.out.println("获取GMT8时间 getGMT8Time() error !");
			e.printStackTrace();
			gmt8 = null;
		}
		return gmt8;
	}

	/**
	 * 获取当前系统时间
	 * 
	 * @return 返回当前系统时间,精确到毫秒
	 */
	public static Date getSystemDateTime() {
		Date gmt8 = null;
		try {
			Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"),
					Locale.CHINESE);
			Calendar day = Calendar.getInstance();
			day.set(Calendar.YEAR, cal.get(Calendar.YEAR));
			day.set(Calendar.MONTH, cal.get(Calendar.MONTH));
			day.set(Calendar.DATE, cal.get(Calendar.DATE));
			day.set(Calendar.HOUR_OF_DAY, cal.get(Calendar.HOUR_OF_DAY));
			day.set(Calendar.MINUTE, cal.get(Calendar.MINUTE));
			day.set(Calendar.SECOND, cal.get(Calendar.SECOND));
			day.set(Calendar.MILLISECOND, cal.get(Calendar.MILLISECOND));
			gmt8 = day.getTime();
		} catch (Exception e) {
			// System.out.println("获取GMT8时间 getGMT8Time() error !");
			e.printStackTrace();
			gmt8 = null;
		}
		return gmt8;

	}

	/**
	 * 获取当前系统时间,返回指定格式的时间字符串
	 * 
	 * @param format
	 *            格式参数
	 * @return
	 */
	public static String getSystemDateTimeStr(String format) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeZone(TimeZone.getTimeZone("GMT+8"));
		Date d = new Date(cal.getTimeInMillis());
		String str = "";
		try {
			DateFormat dateFormat = new SimpleDateFormat(format, Locale.ENGLISH);
			dateFormat.setTimeZone(TimeZone.getTimeZone("GMT+8"));
			dateFormat.setLenient(false);
			str = dateFormat.format(d);
		} catch (Exception e) {
			DateFormat dateFormat = new SimpleDateFormat(DateTimeFormat,
					Locale.ENGLISH);
			dateFormat.setTimeZone(TimeZone.getTimeZone("GMT+8"));
			dateFormat.setLenient(false);
			str = dateFormat.format(d);
		}
		return str;
	}

	/**
	 * 将传入的Date类型的参数格式化为指定格式的日期字符串
	 * 
	 * @param d
	 *            传入的日期参数
	 * @param format
	 *            指定的日期格式
	 * @return 返回日期格式字符串
	 */
	public static String getDateTimeStr(Date d, String format) {

		String str = "";
		try {
			DateFormat dateFormat = new SimpleDateFormat(format, Locale.ENGLISH);
			dateFormat.setTimeZone(TimeZone.getTimeZone("GMT+8"));
			dateFormat.setLenient(false);
			str = dateFormat.format(d);
		} catch (Exception e) {
			DateFormat dateFormat = new SimpleDateFormat(DateTimeFormat,
					Locale.ENGLISH);
			dateFormat.setTimeZone(TimeZone.getTimeZone("GMT+8"));
			dateFormat.setLenient(false);
			str = dateFormat.format(d);
		}
		return str;
	}

	/**
	 * 获取当前系统时间的星期数
	 * 
	 * @return
	 */
	public static String getLocaleDayOfWeek() {
		Locale usersLocale = Locale.getDefault();
		DateFormatSymbols dfs = new DateFormatSymbols(usersLocale);
		String weekdays[] = dfs.getWeekdays();
		Calendar cal = Calendar.getInstance();
		cal.setTimeZone(TimeZone.getTimeZone("GMT+8"));
		return weekdays[cal.get(Calendar.DAY_OF_WEEK)];
	}

	/**
	 * 获取SimpleDateFormat
	 * 
	 * @return SimpleDateFormat
	 */
	public static SimpleDateFormat getSimpleDateFormat(String format) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		dateFormat.setTimeZone(TimeZone.getTimeZone("GMT+8"));
		return dateFormat;
	}

	public static String getPreWeekid(String weekId) {
		String preWeekStr = "";

		Integer intX = new Integer(weekId.substring(0, 4));
		Integer intN = new Integer(weekId.substring(4));

		int x = intX.intValue();
		int n = intN.intValue();

		int preWeek = n - 1;
		int preYear = x;

		if (n == 1) {
			preYear = x - 1;
			Calendar cal1 = new GregorianCalendar(preYear, Calendar.DECEMBER,
					25);
			preWeek = cal1.get(cal1.WEEK_OF_YEAR);

		}
		if (preWeek < 10) {
			preWeekStr = preYear + "0" + preWeek;
		} else {
			preWeekStr = preYear + "" + preWeek;
		}
		return preWeekStr;

	}

	public static String getPeriodByWeekId(String weekId) {
		Integer intX = new Integer(weekId.substring(0, 4));
		Integer intN = new Integer(weekId.substring(4));
		int x = intX.intValue();
		int n = intN.intValue();
		Calendar cal = new GregorianCalendar(x, Calendar.JANUARY, 1);

		Calendar cal1 = new GregorianCalendar(2010, Calendar.DECEMBER, 31);

		int rtn = cal1.get(cal1.WEEK_OF_YEAR);
		// System.out.println("******" + rtn );
		int ff = cal.get(Calendar.DAY_OF_WEEK);
		int week = cal.get(Calendar.WEEK_OF_YEAR);
		// System.out.println("week:"+week);
		// System.out.println("ff:"+ff);
		switch (ff) {
		case 1:
			cal.set(Calendar.DATE, 7 * (n - 2) + ff + 7);
			break;
		case 2:
			cal.set(Calendar.DATE, 7 * (n - 2) + ff + 6);
			break;
		case 3:
			cal.set(Calendar.DATE, 7 * (n - 2) + ff + 5);
			break;
		case 4:
			cal.set(Calendar.DATE, 7 * (n - 2) + ff + 4);
			break;
		case 5:
			cal.set(Calendar.DATE, 7 * (n - 2) + ff + 3);
			break;
		case 6:
			cal.set(Calendar.DATE, 7 * (n - 2) + ff + 2);
			break;
		case 7:
			cal.set(Calendar.DATE, 7 * (n - 2) + ff + 1);
			break;

		}

		// //System.out.println("cal 1:"+cal.getTime());
		int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
		// //System.out.println("dayOfWeek :"+dayOfWeek);
		Calendar calFirstDayInThisWeek = (Calendar) cal.clone();
		calFirstDayInThisWeek.add(Calendar.DATE, cal
				.getActualMinimum(Calendar.DAY_OF_WEEK)
				- dayOfWeek);
		Calendar calLastDayInThisWeek = (Calendar) cal.clone();
		calLastDayInThisWeek.add(Calendar.DATE, cal
				.getActualMaximum(Calendar.DAY_OF_WEEK)
				- dayOfWeek);
		Date startDate = calFirstDayInThisWeek.getTime();
		Date endDate = calLastDayInThisWeek.getTime();
		String startStr = DateUtil.getDateTimeStr(startDate, "yyyy年MM月dd日");
		String endStr = DateUtil.getDateTimeStr(endDate, "yyyy年MM月dd日");
		return startStr + "-" + endStr;

	}

	/**
	 * 获得上一月度（格式yyyyMM）
	 * 
	 * @param monthId
	 * @return
	 * @author Shirley
	 * @createTime 2011-2-21 上午10:44:17
	 */
	public static String getPreMonthid(String monthId) {

		return getPreMonthid(monthId, "yyyyMM");

	}

	/**
	 * 获得上一月度（格式yyyyMM）
	 * 
	 * @param monthId
	 * @return
	 * @author Shirley
	 * @createTime 2011-2-21 上午10:44:17
	 */
	public static String getPreMonthid(String monthId, String format) {

		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		Calendar cal = Calendar.getInstance();
		try {
			cal.setTime(dateFormat.parse(monthId));
			cal.set(Calendar.YEAR, cal.get(cal.YEAR));
			cal.set(Calendar.MONTH, cal.get(cal.MONTH) - 1);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dateFormat.format(cal.getTime());

	}

	/**
	 * 获得上一季度（格式yyyy-q）
	 * 
	 * @param monthId
	 * @return
	 * @author Shirley
	 * @createTime 2011-2-21 上午10:44:17
	 */
	public static String getPreQuarter(String quarterId) {

		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, Integer.valueOf(quarterId.substring(0, 4)));
		cal.set(Calendar.MONTH,
				(Integer.valueOf(quarterId.substring(5)) - 1) * 3 - 1);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMM");
		Date preMonth = cal.getTime();
		int year = cal.get(cal.YEAR);
		int quarter = (cal.get(cal.MONTH) - 1) / 3 + 1;
		return year + "-" + quarter;

	}

	/**
	 * 根据月份获得起止时间
	 * 
	 * @param monthId
	 * @param format
	 * @return
	 * @author Shirley
	 * @createTime 2011-2-21 下午02:26:56
	 */
	public static String getPeriodByMonthId(String monthId, String format) {

		Calendar cal = Calendar.getInstance();
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);

		cal.set(Calendar.YEAR, Integer.valueOf(monthId.substring(0, 4)));
		cal.set(Calendar.MONTH, Integer.valueOf(monthId.substring(4)) - 1);
		cal.set(Calendar.DATE, 1);
		String beginStr = dateFormat.format(cal.getTime());
		cal.set(Calendar.MONTH, Integer.valueOf(monthId.substring(4)));
		cal.set(Calendar.DATE, 0);
		String endStr = dateFormat.format(cal.getTime());
		return beginStr + "-" + endStr;
	}

	/**
	 * 两个月份相减
	 * 
	 * @param minuend
	 * @param subtrahend
	 * @param format
	 * @return
	 * @author Shirley's
	 * @createTime 2011-7-19 下午02:49:28
	 */
	public static int minusMonth(String minuend, String subtrahend,
			String format) {
		Calendar cal1 = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		int result = 0;
		try {
			cal1.setTime(dateFormat.parse(minuend));
			cal2.setTime(dateFormat.parse(subtrahend));
			int year = cal1.get(cal1.YEAR) - cal2.get(cal1.YEAR);
			int month = cal1.get(cal1.MONTH) - cal2.get(cal1.MONTH);
			result = year * 12 + month;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 两个季度相减（格式yyyy-q）
	 * 
	 * @param minuend
	 * @param subtrahend
	 * @param format
	 * @return
	 * @author Shirley's
	 * @createTime 2011-7-19 下午02:49:42
	 */
	public static int minusQuarter(String minuend, String subtrahend) {
		int result = 0;
		int year = Integer.valueOf(minuend.substring(0, 4))
				- Integer.valueOf(subtrahend.substring(0, 4));
		int quarter = Integer.valueOf(minuend.substring(5))
				- Integer.valueOf(subtrahend.substring(5));
		result = year * 4 + quarter;
		return result;
	}

	private static String DateTimeFormat = "yyyy-MM-dd";

	/**
	 * 获取系统当前默认时区与指定时区的时间差.(单位:毫秒)
	 * 
	 * @param timeZoneId
	 *            时区Id
	 * @return 系统当前默认时区与指定时区的时间差.(单位:毫秒)
	 */
	private static int getDiffTimeZoneRawOffset(String timeZoneId) {
		return TimeZone.getDefault().getRawOffset()
				- TimeZone.getTimeZone(timeZoneId).getRawOffset();
	}

	public static String string2Timezone(String srcFormater,
			String srcDateTime, String dstFormater, String dstTimeZoneId) {
		if (srcFormater == null || "".equals(srcFormater))
			return null;
		if (srcDateTime == null || "".equals(srcDateTime))
			return null;
		if (dstFormater == null || "".equals(dstFormater))
			return null;
		if (dstTimeZoneId == null || "".equals(dstTimeZoneId))
			return null;
		SimpleDateFormat sdf = new SimpleDateFormat(srcFormater);
		try {
			int diffTime = getDiffTimeZoneRawOffset(dstTimeZoneId);
			Date d = sdf.parse(srcDateTime);
			long nowTime = d.getTime();
			long newNowTime = nowTime - diffTime;
			d = new Date(newNowTime);
			return date2String(dstFormater, d);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		} finally {
			sdf = null;
		}
	}

	/**
	 * 日期(时间)转化为字符串.
	 * 
	 * @param formater
	 *            日期或时间的格式.
	 * @param aDate
	 *            java.util.Date类的实例.
	 * @return 日期转化后的字符串.
	 */
	public static String date2String(String formater, Date aDate) {
		if (formater == null || "".equals(formater))
			return null;
		if (aDate == null)
			return null;
		return (new SimpleDateFormat(formater)).format(aDate);
	}

	/**
	 * 当前日期(时间)转化为字符串.
	 * 
	 * @param formater
	 *            日期或时间的格式.
	 * @return 日期转化后的字符串.
	 */
	public static String date2String(String formater) {
		return date2String(formater, new Date());
	}
	
	/**
	 * 获取当前系统时间
	 * 
	 * @return 返回当前系统时间,精确到毫秒
	 */
	public static Date getSystemDateTime2(String dstTimeZoneId) {
		int diffTime = getDiffTimeZoneRawOffset(dstTimeZoneId);
		Date d = new Date();
		long nowTime = d.getTime();
		long newNowTime = nowTime - diffTime;
		d = new Date(newNowTime);
		return d;
	}	

	/**
	 * 获取当前系统时间
	 * 
	 * @return 返回当前系统时间,精确到毫秒
	 */
	public static Date getSystemDateTime2() {
		return getSystemDateTime2("Asia/Shanghai");
	}		
	
	/**
	 * 获取当前系统时间,返回指定格式的时间字符串
	 * 
	 * @param format
	 *            格式参数
	 * @return
	 */
	public static String getSystemDateTimeStr2(String format) {
		return getSystemDateTimeStr2(format, "Asia/Shanghai");
	}
	/**
	 * 获取当前系统时间,返回指定格式的时间字符串
	 * 
	 * @param format
	 *            格式参数
	 * @return
	 */
	public static String getSystemDateTimeStr2(String format, String timeZoneId) {
		String nowDateTime = date2String(format);
		return string2Timezone(format, nowDateTime,
				format, timeZoneId);
	}

	public static void main(String[] args) {
		System.out.println(getSystemDateTimeStr2("yyyy-MM-dd HH:mm:ss", "Asia/Seoul"));
		System.out.println(getSystemDateTimeStr2("yyyy-MM-dd HH:mm:ss"));
		System.out.println(getSystemDateTime2("Asia/Seoul"));
		System.out.println(getSystemDateTime2());
	}
}
