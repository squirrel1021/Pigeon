package com.july.pigeon.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @ClassName: DateUtils
 * @Description: 时间操作工具类
 * @author Kevinliu 
 * @date 2015年1月20日 下午3:40:10
 * 
*/

public class DateUtils {

	/**
	 * 时间范围：年
	 */
	public static int YEAR = 1;

	/**
	 * 时间范围：季度
	 */
	public static int QUARTER = 2;

	/**
	 * 时间范围：月
	 */
	public static int MONTH = 3;

	/**
	 * 时间范围：旬
	 */
	public static int TENDAYS = 4;

	/**
	 * 时间范围：周
	 */
	public static int WEEK = 5;

	/**
	 * 时间范围：日
	 */
	public static int DAY = 6;

	static final String dayNames[] = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };

	static final String weekDayNames[] = { "日", "一", "二", "三", "四", "五", "六" };

	/* 基准时间 */
	private static Date fiducialDate = null;

	private Calendar cal = null;

	private DateUtils(Date fiducialDate) {
		if (fiducialDate != null) {
			this.fiducialDate = fiducialDate;
		} else {
			this.fiducialDate = new Date(System.currentTimeMillis());
		}

		this.cal = Calendar.getInstance();
		this.cal.setTime(this.fiducialDate);
		this.cal.set(Calendar.HOUR_OF_DAY, 0);
		this.cal.set(Calendar.MINUTE, 0);
		this.cal.set(Calendar.SECOND, 0);
		this.cal.set(Calendar.MILLISECOND, 0);

		this.fiducialDate = this.cal.getTime();
	}

	/**
	 * 获取DateHelper实例
	* @Title: getInstance 
	* @param fiducialDate
	* @return DateUtils 返回类型  
	 */
	public static DateUtils getInstance(Date fiducialDate) {
		return new DateUtils(fiducialDate);
	}

	/**
	 * 获取DateHelper实例, 使用当前时间作为基准时间
	* @Title: getInstance 
	* @return DateUtils 返回类型  
	*/
	public static DateUtils getInstance() {
		return new DateUtils(null);
	}

	/**
	 * 获取年的第一天
	* @Title: getFirstDayOfYear 
	* @Description: 偏移量指的是获取目标数与当前数的间隔，如想拿当前数，传入0
	* @param offset 偏移量
	* @return Date 返回类型  
	 */
	public Date getFirstDayOfYear(int offset) {
		cal.setTime(this.fiducialDate);
		cal.set(Calendar.YEAR, cal.get(Calendar.YEAR) + offset);
		cal.set(Calendar.MONTH, Calendar.JANUARY);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		return cal.getTime();
	}

	/**
	 * 获取年的最后一天
	* @Title: getLastDayOfYear 
	* @Description: 偏移量指的是获取目标数与当前数的间隔，如想拿当前数，传入0
	* @param offset 偏移量
	* @return Date 返回类型  
	 */
	public Date getLastDayOfYear(int offset) {
		cal.setTime(this.fiducialDate);
		cal.set(Calendar.YEAR, cal.get(Calendar.YEAR) + offset);
		cal.set(Calendar.MONTH, Calendar.DECEMBER);
		cal.set(Calendar.DAY_OF_MONTH, 31);
		return cal.getTime();
	}

	/**
	 * 获取季度的第一天
	* @Title: getFirstDayOfQuarter 
	* @Description: 偏移量指的是获取目标数与当前数的间隔，如想拿当前数，传入0
	* @param offset 偏移量
	* @return Date 返回类型  
	 */
	public Date getFirstDayOfQuarter(int offset) {
		cal.setTime(this.fiducialDate);
		cal.add(Calendar.MONTH, offset * 3);
		int mon = cal.get(Calendar.MONTH);
		if (mon >= Calendar.JANUARY && mon <= Calendar.MARCH) {
			cal.set(Calendar.MONTH, Calendar.JANUARY);
			cal.set(Calendar.DAY_OF_MONTH, 1);
		}
		if (mon >= Calendar.APRIL && mon <= Calendar.JUNE) {
			cal.set(Calendar.MONTH, Calendar.APRIL);
			cal.set(Calendar.DAY_OF_MONTH, 1);
		}
		if (mon >= Calendar.JULY && mon <= Calendar.SEPTEMBER) {
			cal.set(Calendar.MONTH, Calendar.JULY);
			cal.set(Calendar.DAY_OF_MONTH, 1);
		}
		if (mon >= Calendar.OCTOBER && mon <= Calendar.DECEMBER) {
			cal.set(Calendar.MONTH, Calendar.OCTOBER);
			cal.set(Calendar.DAY_OF_MONTH, 1);
		}
		return cal.getTime();
	}

	public Date getYesterday() {
		long time = fiducialDate.getTime() - 60 * 60 * 24 * 1000;
		return new Date(time);
	}

	public Date getTomorrow() {
		long time = this.fiducialDate.getTime() + 60 * 60 * 24 * 1000;
		return new Date(time);
	}

	/**
	 * 获取季度的最后一天
	* @Title: getLastDayOfQuarter 
	* @Description: 偏移量指的是获取目标数与当前数的间隔，如想拿当前数，传入0
	* @param offset 偏移量
	* @return Date 返回类型  
	 */
	public Date getLastDayOfQuarter(int offset) {
		cal.setTime(this.fiducialDate);
		cal.add(Calendar.MONTH, offset * 3);

		int mon = cal.get(Calendar.MONTH);
		if (mon >= Calendar.JANUARY && mon <= Calendar.MARCH) {
			cal.set(Calendar.MONTH, Calendar.MARCH);
			cal.set(Calendar.DAY_OF_MONTH, 31);
		}
		if (mon >= Calendar.APRIL && mon <= Calendar.JUNE) {
			cal.set(Calendar.MONTH, Calendar.JUNE);
			cal.set(Calendar.DAY_OF_MONTH, 30);
		}
		if (mon >= Calendar.JULY && mon <= Calendar.SEPTEMBER) {
			cal.set(Calendar.MONTH, Calendar.SEPTEMBER);
			cal.set(Calendar.DAY_OF_MONTH, 30);
		}
		if (mon >= Calendar.OCTOBER && mon <= Calendar.DECEMBER) {
			cal.set(Calendar.MONTH, Calendar.DECEMBER);
			cal.set(Calendar.DAY_OF_MONTH, 31);
		}
		return cal.getTime();
	}

	/**
	 * 获取月的最后一天
	* @Title: getFirstDayOfMonth 
	* @Description: 偏移量指的是获取目标数与当前数的间隔，如想拿当前数，传入0
	* @param offset 偏移量
	* @return Date 返回类型  
	 */
	public Date getFirstDayOfMonth(int offset) {
		cal.setTime(this.fiducialDate);
		cal.add(Calendar.MONTH, offset);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		return cal.getTime();
	}

	/**
	 * 获取月的最后一天
	* @Title: getLastDayOfMonth 
	* @Description: 偏移量指的是获取目标数与当前数的间隔，如想拿当前数，传入0
	* @param offset 偏移量
	* @return Date 返回类型  
	 */
	public Date getLastDayOfMonth(int offset) {
		cal.setTime(this.fiducialDate);
		cal.add(Calendar.MONTH, offset + 1);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.add(Calendar.DAY_OF_MONTH, -1);
		return cal.getTime();
	}

	/**
	 * 获取旬的第一天
	* @Title: getFirstDayOfTendays 
	* @Description: 偏移量指的是获取目标数与当前数的间隔，如想拿当前数，传入0
	* @param offset 偏移量
	* @return Date 返回类型  
	 */
	public Date getFirstDayOfTendays(int offset) {
		cal.setTime(this.fiducialDate);
		int day = cal.get(Calendar.DAY_OF_MONTH);
		if (day >= 21) {
			day = 21;
		} else if (day >= 11) {
			day = 11;
		} else {
			day = 1;
		}

		if (offset > 0) {
			day = day + 10 * offset;
			int monOffset = day / 30;
			day = day % 30;
			cal.add(Calendar.MONTH, monOffset);
			cal.set(Calendar.DAY_OF_MONTH, day);
		} else {
			int monOffset = 10 * offset / 30;
			int dayOffset = 10 * offset % 30;
			if ((day + dayOffset) > 0) {
				day = day + dayOffset;
			} else {
				monOffset = monOffset - 1;
				day = day - dayOffset - 10;
			}
			cal.add(Calendar.MONTH, monOffset);
			cal.set(Calendar.DAY_OF_MONTH, day);
		}
		return cal.getTime();
	}

	/**
	 * 获取旬的最后一天
	* @Title: getLastDayOfTendays 
	* @Description: 偏移量指的是获取目标数与当前数的间隔，如想拿当前数，传入0
	* @param offset 偏移量
	* @return Date 返回类型  
	 */
	public Date getLastDayOfTendays(int offset) {
		Date date = getFirstDayOfTendays(offset + 1);
		cal.setTime(date);
		cal.add(Calendar.DAY_OF_MONTH, -1);
		return cal.getTime();
	}

	/**
	 * 获取周的第一天(MONDAY)
	* @Title: getFirstDayOfWeek 
	* @Description: 偏移量指的是获取目标数与当前数的间隔，如想拿当前数，传入0
	* @param offset 偏移量
	* @return Date 返回类型  
	 */
	public Date getFirstDayOfWeek(int offset) {
		cal.setTime(this.fiducialDate);
		cal.add(Calendar.DAY_OF_MONTH, offset * 7);
		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		return cal.getTime();
	}

	/**
	 * 获取周的最后一天(SUNDAY)
	* @Title: getLastDayOfWeek 
	* @Description: 偏移量指的是获取目标数与当前数的间隔，如想拿当前数，传入0
	* @param offset 偏移量
	* @return Date 返回类型  
	 */
	public Date getLastDayOfWeek(int offset) {
		cal.setTime(this.fiducialDate);
		cal.add(Calendar.DAY_OF_MONTH, offset * 7);
		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		cal.add(Calendar.DAY_OF_MONTH, 6);
		return cal.getTime();
	}

	/**
	 * 获取指定时间范围的第一天
	* @Title: getFirstDate  
	* @Description: 偏移量指的是获取目标数与当前数的间隔，如想拿当前数，传入0
	* @param dateRangeType 时间范围类型
	* @param offset 偏移量
	* @return Date 返回类型  
	* @throws:throws
	 */
	public Date getFirstDate(int dateRangeType, int offset) {
		return null;
	}

	/**
	 * 获取指定时间范围的最后一天
	* @Title: getLastDate 
	* @Description: 偏移量指的是获取目标数与当前数的间隔，如想拿当前数，传入0
	* @param dateRangeType 时间范围类型
	* @param offset 偏移量
	* @return Date 返回类型  
	* @throws:throws
	 */
	public Date getLastDate(int dateRangeType, int offset) {
		return null;
	}

	/**
	 * 根据日历的规则，为基准时间添加指定日历字段的时间量
	* @Title: add 
	* @Description: 偏移量指的是获取目标数与当前数的间隔，如想拿当前数，传入0
	* @param field 日历字段, 使用Calendar类定义的日历字段常量
	* @param offset 偏移量
	* @return Date 返回类型  
	* @throws:throws
	 */
	public Date add(int field, int offset) {
		cal.setTime(this.fiducialDate);
		cal.add(field, offset);
		return cal.getTime();
	}

	/**
	 * 根据日历的规则，为基准时间添加指定日历字段的单个时间单元
	* @Title: roll 
	* @param field 日历字段, 使用Calendar类定义的日历字段常量
	* @param up 指定日历字段的值的滚动方向。true:向上滚动 / false:向下滚动
	* @return Date 返回类型  
	 */
	public Date roll(int field, boolean up) {
		cal.setTime(this.fiducialDate);
		cal.roll(field, up);
		return cal.getTime();
	}

	/**
	 * 获得某月的天数 
	* @Title: getDaysOfMonth 
	* @param year 年份
	* @param month 月份
	* @return int 返回类型  
	 */
	public static int getDaysOfMonth(String year, String month) {
		int days = 0;
		if (month.equals("1") || month.equals("3") || month.equals("5") || month.equals("7") || month.equals("8")
				|| month.equals("10") || month.equals("12")) {
			days = 31;
		} else if (month.equals("4") || month.equals("6") || month.equals("9") || month.equals("11")) {
			days = 30;
		} else {
			if ((Integer.parseInt(year) % 4 == 0 && Integer.parseInt(year) % 100 != 0)
					|| Integer.parseInt(year) % 400 == 0) {
				days = 29;
			} else {
				days = 28;
			}
		}

		return days;
	}

	/**
	 * 获得某月的天数 
	* @Title: getDaysOfMonth 
	* @param year 年份
	* @param month 月份
	* @return int 返回类型  
	 */
	public int getDaysOfMonth(int year, int month) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month - 1, 1);
		return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 获得当前日期
	* @Title: getCurrentDay 
	* @return int 返回类型  
	 */
	public static int getCurrentDay() {
		Calendar calendar = Calendar.getInstance();
		return calendar.get(Calendar.DATE);
	}

	/**
	 * 获得当前月份
	* @Title: getCurrentMonth 
	* @return int 返回类型  
	 */
	public static int getCurrentMonth() {
		Calendar calendar = Calendar.getInstance();
		return calendar.get(Calendar.MONTH) + 1;
	}

	/**
	 * 获得当前年份
	* @Title: getCurrentYear 
	* @return int 返回类型  
	 */
	public static int getCurrentYear() {
		Calendar calendar = Calendar.getInstance();
		return calendar.get(Calendar.YEAR);
	}

	/**
	 * 返回传入日期实例的天
	* @Title: getDay 
	* @param date 日期实例
	* @return int 返回类型  
	 */
	public static int getDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.DATE);
	}

	/**
	 * 返回传入日期实例的年份
	* @Title: getYear 
	* @param date 日期实例
	* @return int 返回类型  
	 */
	public static int getYear(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.YEAR);
	}

	/**
	 * 返回传入日期实例的月份，[1-12] 
	* @Title: getMonth 
	* @param date 日期实例
	* @return int 返回类型  
	 */
	public static int getMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.MONTH) + 1;
	}

	/**
	 * 两个日期相减 得到秒数 
	* @Title: timeSub 
	* @Description: 用一句话描述该文件做什么
	* @param firstDate 开始时间 
	* @param secondDate 结束时间 
	* @param format 时间格式 
	* @return long 相减得到的秒数  
	 */
	public static long timeSub(String firstDate, String secondDate, String format) {
		long first = strToDate(firstDate, format).getTime();
		long second = strToDate(secondDate, format).getTime();
		return (second - first) / 1000;
	}

	/**
	 * 比较两个日期的年差 
	* @Title: yearDiff 
	* @Description: 用一句话描述该文件做什么
	* @param before 开始年份
	* @param after	结束年份
	* @param format
	* @return int 返回类型  
	* @throws:throws
	 */
	public int yearDiff(String before, String after, String format) {
		Date beforeDay = strToDate(before, format);
		Date afterDay = strToDate(after, format);
		return getYear(afterDay) - getYear(beforeDay);
	}

	/**
	 * 比较指定日期与当前年份的差 
	* @Title: yearDiffCurr 
	* @Description: 用一句话描述该文件做什么
	* @param after 目标日期
	* @param format 日期格式
	* @return int 返回类型  
	 */
	public int yearDiffCurr(String after, String format) {
		Date beforeDay = new Date();
		Date afterDay = strToDate(after, format);
		return getYear(beforeDay) - getYear(afterDay);
	}

	/**
	 *比较指定日期与当前日期的差  
	* @Title: yearDiffCurr 
	* @Description: 用一句话描述该文件做什么
	* @param after 目标日期
	* @param format 日期格式
	* @return int 返回类型  
	 */
	public long dayDiffCurr(String before, String format) {
		Date currDate = strToDate(getCurrentDate(), format);
		Date beforeDate = strToDate(before, format);
		return (currDate.getTime() - beforeDate.getTime()) / 86400000;

	}

	/**
	 *比较指定日期与当前日期的差   分钟
	* @Title: yearDiffCurr 
	* @Description: 用一句话描述该文件做什么
	* @param after 目标日期
	* @param format 日期格式
	* @return int 返回类型  
	 */
	public long minDiffCurr(String before, String format) {
		Date currDate = strToDate(getCurrentDateHour(), format);
		Date beforeDate = strToDate(before, format);
		return (currDate.getTime() - beforeDate.getTime()) / 60000;
	}

	/**
	 * 获取每月的第一周 
	* @Title: getFirstWeekdayOfMonth 
	* @Description: 用一句话描述该文件做什么
	* @param year 目标年份
	* @param month 目标月份
	* @return int 返回类型  
	 */
	public int getFirstWeekdayOfMonth(int year, int month) {
		Calendar c = Calendar.getInstance();
		c.setFirstDayOfWeek(Calendar.SATURDAY); // 星期天为第一天
		c.set(year, month - 1, 1);
		return c.get(Calendar.DAY_OF_WEEK);
	}

	/**
	 * 获取每月的最后一周  
	* @Title: getFirstWeekdayOfMonth 
	* @Description: 用一句话描述该文件做什么
	* @param year 目标年份
	* @param month 目标月份
	* @return int 返回类型  
	 */
	public int getLastWeekdayOfMonth(int year, int month) {
		Calendar c = Calendar.getInstance();
		c.setFirstDayOfWeek(Calendar.SATURDAY); // 星期天为第一天
		c.set(year, month - 1, getDaysOfMonth(year, month));
		return c.get(Calendar.DAY_OF_WEEK);
	}

	/**
	 * 把字符串转换为日期
	* @Title: strToDate 
	* @param dateStr 日期字符串
	* @param format 日期格式
	* @return Date 返回类型  
	 */
	public static Date strToDate(String dateStr, String format) {
		Date date = null;

		if (dateStr != null && (!dateStr.equals(""))) {
			DateFormat df = new SimpleDateFormat(format);
			try {
				date = df.parse(dateStr);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return date;
	}

	/**
	 * 把字符串转换为日期，日期的格式为yyyy-MM-dd HH:mm:ss
	* @Title: strToDate 
	* @param dateStr 日期字符串
	* @return (Date) yyyy-MM-dd HH:mm:ss  
	 */
	public static Date strToDate(String dateStr) {
		Date date = null;

		if (dateStr != null && (!dateStr.equals(""))) {
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			try {
				date = df.parse(dateStr);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return date;
	}

	/**
	 * 把日期转换为想要的字符串格式
	* @Title: dateToStr 
	* @param date 日期实例
	* @param format 日期格式
	* @return String 返回类型  
	 */
	public static String dateToStr(Date date, String format) {
		return (date == null) ? "" : new SimpleDateFormat(format).format(date);
	}

	/**
	 * 把日期转换为字符串  输出格式:yyyy-MM-dd hh:mm:ss
	* @Title: dateToStr 
	* @param date 日期实例
	* @param format 日期格式
	* @return (String) yyyy-MM-dd hh:mm:ss
	 */
	public static String dateToStr(Date date) {
		return (date == null) ? "" : new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(date);
	}
	
	public static String dateToString(Date date) {
		return (date == null) ? "" : new SimpleDateFormat("yyyy-MM-dd").format(date);
	}

	/**
	 * 取得当前日期 年-月-日
	* @Title: getCurrentDate 
	* @return (String) yyyy-MM-dd  
	 */
	public static String getCurrentDate() {
		DateFormat f = new SimpleDateFormat("yyyy-MM-dd");
		return f.format(Calendar.getInstance().getTime());
	}

	/**
	 * 取得当前日期 年-月-日  时：分：秒
	* @Title: getCurrentDate 
	* @return (String) yyyy-MM-dd HH:mm:ss
	 */
	public static String getCurrentDateHour() {
		DateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return f.format(Calendar.getInstance().getTime());
	}

	//yyyy-MM-dd'T'HH:mm:ss.SSSZ

	public static String getCurrentDate(String format) {

		DateFormat f = new SimpleDateFormat(format);

		return f.format(new Date());

	}

	/**
	 * 取当前日期的字符串形式,"yyyy年MM月dd日"
	* @Title: getPrintDate 
	* @return (String) yyyy年MM月dd日  
	 */
	public static String getPrintDate() {
		String printDate = "";
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		printDate += calendar.get(Calendar.YEAR) + "年";
		printDate += (calendar.get(Calendar.MONTH) + 1) + "月";
		printDate += calendar.get(Calendar.DATE) + "日";
		return printDate;
	}

	/**
	 * 将指定的日期字符串转化为日期对象
	* @Title: getDate 
	* @param dateStr 日期实例
	* @param format 日期格式
	* @return Date 返回类型 日期格式为null时 默认为  yyyy-MM-dd
	 */
	public static Date getDate(String dateStr, String format) {
		if (dateStr == null) {
			return new Date();
		}
		if (format == null) {
			format = "yyyy-MM-dd";
		}
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		try {
			Date date = sdf.parse(dateStr);
			return date;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 从指定Timestamp中得到相应的日期的字符串形式 日期"XXXX-XX-XX"
	* @Title: getDateFromDateTime 
	* @param dateTime
	* @return (String) yyyy-MM-dd 
	 */
	public static String getDateFromDateTime(Timestamp dateTime) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(dateTime).toString();
	}

	/**
	 * 得到当前时间 return java.sql.Timestamp
	* @Title: getNowTimestamp 
	* @return Timestamp 返回类型  
	 */
	public static Timestamp getNowTimestamp() {
		long curTime = System.currentTimeMillis();
		return new Timestamp(curTime);
	}

	public static void main(String[] args) {
		DateUtils dateHelper = DateUtils.getInstance();

		/* Year */
		for (int i = -5; i <= 5; i++) {
		}
	}

	/** 
	 * @Description: 设置时间格式为"yyyy-MM-dd"
	 * @param time
	 * @return String 返回类型  
	 * @throws:throws
	*/

	public static String setTimeDate(long time) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String timeDate = format.format(new Date(time));
		return timeDate;
	}

	/** 
	 * @Description: 设置时间格式为"yyyy-MM-dd HH:mm:ss"
	 * @param time
	 * @return String 返回类型  
	 * @throws:throws
	*/

	public static String setTimeDateClock(long time) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");
		String timeDate = format.format(new Date(time));
		return timeDate;
	}
	public static String setTimeDateClock2(long time) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String timeDate = format.format(new Date(time));
		return timeDate;
	}
	public static String setTimeDateClock3(long time) {
		SimpleDateFormat format = new SimpleDateFormat("MM-dd HH:mm");
		String timeDate = format.format(new Date(time));
		return timeDate;
	}
	public static String setTimeDateClock1(long time) {
		SimpleDateFormat format = new SimpleDateFormat("MM月dd日  HH:mm");
		String timeDate = format.format(new Date(time));
		return timeDate;
	}
	public static String setTimeDateClock4(long time) {
		SimpleDateFormat format = new SimpleDateFormat("MM月dd日 HH时mm分");
		String timeDate = format.format(new Date(time));
		return timeDate;
	}
	public static String setTimeClock(long time) {
		SimpleDateFormat format = new SimpleDateFormat("HH:mm");
		String timeDate = format.format(new Date(time));
		return timeDate;
	}

	/** 
	 * @Description: 设置时间格式为"yyyy-MM-dd HH:mm:ss"
	 * @param time
	 * @return String 返回类型  
	 * @throws:throws
	*/

	public static String setTimeDateClockShort(long time) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd  HH:mm");
		String timeDate = format.format(new Date(time));
		return timeDate;
	}

	/** 
	 * @Description: 设置时间格式为"yyyy-MM-dd HH:mm"
	 * @param time
	 * @return String 返回类型  
	 * @throws:throws
	*/

	public static String setTimeDateMinute(long time) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String timeDate = format.format(new Date(time));
		return timeDate;
	}

	/** 
	 * @Description: 设置时间格式为"dd"
	 * @param time
	 * @return String 返回类型  
	 * @throws:throws
	*/

	public static String setTimeDateDd(long time) {
		SimpleDateFormat format = new SimpleDateFormat("MM-dd");
		String timeDate = format.format(new Date(time));
		return timeDate;
	}
	
	/** 
	 * @Description: 设置时间格式为"yyyy"
	 * @param time
	 * @return String 返回类型  
	 * @throws:throws
	*/

	public static String getYearL(long time) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy");
		String timeDate = format.format(new Date(time));
		return timeDate;
	}

	/**
	  * 根据一个日期，返回是星期几的字符串
	  * 
	  * @param time
	  * @return
	  */
	public static String getWeek(long time) {
		// 再转换为时间
		Date date = new Date(time);
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int hour = c.get(Calendar.DAY_OF_WEEK);
		String week = getWeekStr(hour);
		return week;
	}

	public static String getWeekStr(int hour) {
		String str = "";
		switch (hour) {
		case 1:
			str = "星期天";
			break;
		case 2:
			str = "星期一";
			break;
		case 3:
			str = "星期二";
			break;
		case 4:
			str = "星期三";
			break;
		case 5:
			str = "星期四";
			break;
		case 6:
			str = "星期五";
			break;
		case 7:
			str = "星期六";
			break;

		default:
			break;
		}
		return str;
	}
	
	/**获取明天日期
	 * @param date
	 * @return
	 */
	public static Date getNextDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, +1);//+1今天的时间加一天
		date = calendar.getTime();
		return date;
	}
	
}
