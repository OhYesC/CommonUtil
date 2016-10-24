package com.ljzforum.platform.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.time.DateUtils;

public class DateConvertUtils extends DateUtils {

	public static final String FORMAT_DATE_19 = "yyyy-MM-dd HH:mm:ss";

	public static final String FORMAT_DATE_10_SLASH = "yyyy/MM/dd";

	/**
	 * "yyyy-MM-dd HH:mm:ss:SSS";的时间格式字符创
	 */
	private static final String DATE_FORMAT_SSS = "yyyy-MM-dd HH:mm:ss:SSS";

	private static final String DF_TO_DAY = "yyyy-MM-dd";

	public static final String DF_TO_DAY_2 = "yyyyMMdd";

	/**
	 * 日期格式化
	 * 
	 * @param pattern
	 * @param date
	 * @return
	 */
	public static String formatDate(String pattern, Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(date);
	}

	/**
	 * 默认格式化当前日期
	 * 
	 * @param pattern
	 * @return
	 */
	public static String formatDate(String pattern) {
		return formatDate(pattern, new Date());
	}

	public static String formatDate(Date date, String pattern) {
		return formatDate(pattern, date);
	}

	public static String formateDate19(Date date) {
		return formatDate(FORMAT_DATE_19, date);
	}

	/**
	 * 取得一个月的开始时间
	 * 
	 * @param date
	 * @return yyyy-MM-dd hh:mm:ss
	 */
	public static Date getMonthStart(Date date) {
		Date monthStart = null;
		if (date != null) {
			Calendar nowDate = Calendar.getInstance();
			nowDate.setTime(date);
			monthStart = DateUtils.truncate(nowDate.getTime(), Calendar.MONTH);
		}
		return monthStart;
	}

	/**
	 * 取得一个月的结束时间+1天
	 * 
	 * @param date
	 * @return yyyy-MM-dd hh:mm:ss
	 */
	public static Date getMonthEnd(Date date) {
		Date monthEnd = null;
		if (date != null) {
			Calendar nowDate = Calendar.getInstance();
			nowDate.setTime(date);
			Date startMonth = DateUtils.truncate(nowDate.getTime(),
					Calendar.MONTH);
			monthEnd = DateUtils.addMonths(startMonth, 1);
		}
		return monthEnd;
	}

	/**
	 * 取得上一个月的开始时间
	 * 
	 * @param date
	 * @return
	 */
	public static Date getPreMonthStart(Date date) {
		return getMonthStart(DateUtils.addMonths(date, -1));
	}

	/**
	 * 取得前一个月的结束时间+1天
	 * 
	 * @param date
	 * @return
	 */
	public static Date getPreMonthEnd(Date date) {
		return getMonthEnd(DateUtils.addMonths(date, -1));
	}

	/**
	 * date1 日期是否大于date2日期，比较精度到日期
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static boolean after(Date date1, Date date2) {
		if (date1 == null)
			return false;
		if (date2 == null)
			return true;
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(date1);
		cal1.set(Calendar.HOUR_OF_DAY, 0);
		cal1.set(Calendar.MINUTE, 0);
		cal1.set(Calendar.SECOND, 0);

		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(date2);
		cal2.set(Calendar.HOUR_OF_DAY, 0);
		cal2.set(Calendar.MINUTE, 0);
		cal2.set(Calendar.SECOND, 0);
		return cal1.after(cal2);
	}

	public static void main(String[] a) {

	}

	public static String format(Date createDate, String formatDate) {

		return null;
	}

	public static Date parse(String value, String formatDate) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @param time
	 * @return
	 */
	public static String getToDayDate(long time) {
		DateFormat fmt = createDateFmt(DF_TO_DAY);
		return fmt.format(new Date(time));
	}

	private static DateFormat createDateFmt(String datefmt) {
		DateFormat fmt = new SimpleDateFormat(datefmt);
		return fmt;
	}

	/**
	 * 计算两个日期之间相差的天数
	 * 
	 * @param smdate
	 *            较小的时间
	 * @param bdate
	 *            较大的时间
	 * @return 相差天数
	 * @throws ParseException
	 */
	public static int daysBetween(Date smdate, Date bdate)
			throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		smdate = sdf.parse(sdf.format(smdate));
		bdate = sdf.parse(sdf.format(bdate));
		Calendar cal = Calendar.getInstance();
		cal.setTime(smdate);
		long time1 = cal.getTimeInMillis();
		cal.setTime(bdate);
		long time2 = cal.getTimeInMillis();
		long between_days = (time2 - time1) / (1000 * 3600 * 24);

		return Integer.parseInt(String.valueOf(between_days));
	}

	/**
	 * 字符串的日期格式的计算
	 */
	public static int daysBetween(String smdate, String bdate)
			throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.setTime(sdf.parse(smdate));
		long time1 = cal.getTimeInMillis();
		cal.setTime(sdf.parse(bdate));
		long time2 = cal.getTimeInMillis();
		long between_days = (time2 - time1) / (1000 * 3600 * 24);

		return Integer.parseInt(String.valueOf(between_days));
	}

}
