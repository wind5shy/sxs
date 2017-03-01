package com.shixiseng.recommend.common.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 日期工具类
 * 
 * @author Alfred Xu(wind5shy@qq.com)
 */
public class DateUtils {

	private final static Log LOG = LogFactory.getLog(DateUtils.class);

	public static long getServerTime() {
		return System.currentTimeMillis();
	}

	/**
	 * 格式化日期,默认返回yyyy-MM-dd HH:mm:ss
	 * 
	 * @param date
	 * @return
	 */
	public static String format(Date date) {
		return format(date, "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 格式化显示当前日期
	 * 
	 * @param format
	 * @return
	 */
	public static String format(String format) {
		return format(new Date(), format);
	}

	/**
	 * 日期格式化
	 * 
	 * @param date
	 * @param format
	 * @return
	 */
	public static String format(Date date, String format) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			return sdf.format(date);
		} catch (Exception e) {
			LOG.warn("fail to format date.", e);
		}
		return null;
	}

	/**
	 * 时间格式化， 传入毫秒
	 * 
	 * @param time
	 * @return
	 */
	public static String dateFormat(long time) {
		return format(new Date(time), "yyyy-MM-dd HH:mm:ss");
	}

	public static String getQuarter(Calendar c) {
		int y = c.get(Calendar.YEAR);
		int m = c.get(Calendar.MONTH) + 1;
		if (m <= 3) {
			return y + "一季度";
		} else if (m > 3 && m <= 6) {
			return y + "二季度";
		} else if (m > 6 && m <= 9) {
			return y + "三季度";
		} else {
			return y + "四季度";
		}
	}

	public static String getMonth(Calendar c) {
		int y = c.get(Calendar.YEAR);
		int m = c.get(Calendar.MONTH) + 1;
		StringBuilder sb = new StringBuilder();
		sb.append(y);
		sb.append("-");
		if (m < 10) {
			sb.append(0);
		}
		sb.append(m);
		return sb.toString();
	}

	public static String getDay(Calendar c) {
		int y = c.get(Calendar.YEAR);
		int m = c.get(Calendar.MONTH) + 1;
		int d = c.get(Calendar.DAY_OF_MONTH);
		StringBuilder sb = new StringBuilder();
		sb.append(y);
		sb.append("-");
		if (m < 10) {
			sb.append(0);
		}
		sb.append(m);
		sb.append("-");
		if (d < 10) {
			sb.append(0);
		}
		sb.append(d);
		return sb.toString();
	}
}
