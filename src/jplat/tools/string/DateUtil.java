package jplat.tools.string;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import z.log.tracelog.XLog;

/**
 * 日期格式
 * 
 */
public class DateUtil
{
	public static String FMT_ALL = "yyyyMMddHHmmssSSS";
	public static String FMT_YHS = "yyyyMMddHHmmss";
	public static String FMT_YMD = "yyyyMMdd";
	public static String FMT_HMS = "HHmmss";
	public static String FMT_READ_ALL = "yyyy/MM/dd-HH:mm:ss:SSS";
	
	public static String DATE_FORMATTER = "yyyyMMdd";

	/**
	 * 提前N月的日期
	 * 
	 * @param date
	 * @param days
	 * @return
	 */
	public static Date beforeMonth(Date date, int month) {
		java.util.Calendar c = java.util.Calendar.getInstance();
		c.setTime(date);
		c.add(java.util.Calendar.MONTH, -month);
		return c.getTime();

	}

	public static final SimpleDateFormat sdf = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss.SSS");
	public static final SimpleDateFormat ymd = new SimpleDateFormat(
			"yyyy-MM-dd");

	public static java.util.Date parseDate(String dateStr, String format) {
		if (dateStr==null || dateStr.trim().length()== 0 )
			return null;
		java.util.Date date = null;
		try {
			java.text.DateFormat df = new java.text.SimpleDateFormat(format);
			String dt = dateStr;// .replaceAll("-", "/");
			if ((!dt.equals("")) && (dt.length() < format.length())) {
				dt += format.substring(dt.length()).replaceAll("[YyMmDdHhSs]",
						"0");
			}
			date = (java.util.Date) df.parse(dt);
		} catch (Exception e) {
		}
		return date;
	}

	public static java.util.Date parseDate(String dateStr) {
		return parseDate(dateStr, DATE_FORMATTER);
	}

	public static java.util.Date parseDate(String dateStr,
			SimpleDateFormat format) {
		if ( dateStr == null || dateStr.trim().length() == 0 )
			return null;
		java.util.Date date = null;
		try {
			date = format.parse(dateStr);
		} catch (Exception e) {
			System.out.println(dateStr);
			e.printStackTrace();
		}
		return date;
	}

	public static String todayStr(String format) {
		return formatDateToStr(new Date(), format);
	}

	public static Date today(String format) {
		return parseDate(todayStr(format), format);
	}

	public static String todayStr() {
		return formatDateToStr(new Date(), DATE_FORMATTER);
	}

	public static String todayfulldata() {
		SimpleDateFormat dateformat1 = new SimpleDateFormat("yyyyMMddHHmmss");
		String a1 = dateformat1.format(new Date());
		return a1;
	}

	public static Date today() {
		return parseDate(todayStr(), DATE_FORMATTER);
	}

	/**
	 * @param date
	 *            �?��格式化的日期對像
	 * @param formatter
	 *            格式化的字符串形�?
	 * @return 按照formatter指定的格式的日期字符�?
	 * @throws ParseException
	 *             無法解析的字符串格式時拋�?
	 */
	public static String formatDateToStr(Date date, String formatter) {
		if (date == null)
			return "";
		try {
			return new java.text.SimpleDateFormat(formatter).format(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 生成默认格式的日�?
	 * 
	 * @param date
	 * @return
	 */
	public static String formatDateToStr(Date date) {
		return formatDateToStr(date, DATE_FORMATTER);
	}

	/**
	 * 將日期按照指定的模式格式�?
	 * 
	 * @param date
	 *            {@link Date}
	 * @param format
	 *            如：yyyy/MM/dd
	 * @return 返回空表示格式化產生異常
	 */
	public static String format(java.util.Date date, String format) {
		String result = "";
		try {
			if (date != null) {
				java.text.DateFormat df = new java.text.SimpleDateFormat(format);
				result = df.format(date);
			}
		} catch (Exception e) {
		}
		return result;
	}

	/**
	 * 将一种字符日期转化成另外�?��日期格式
	 * 
	 * @param date
	 * @param fmtSrc
	 * @param fmtTag
	 * @return
	 */
	public static String format(String date, String fmtSrc, String fmtTag) {
		if (null == fmtSrc)
			return null;
		if (fmtSrc.equals(fmtTag)) {
			return date;
		}
		String year, month, daty;
		int Y, M, D;
		fmtSrc = fmtSrc.toUpperCase();
		Y = fmtSrc.indexOf("yyyy");
		M = fmtSrc.indexOf("MM");
		D = fmtSrc.indexOf("dd");
		if (Y < 0 || M < 0 || D < 0) {
			return date;
		}
		year = date.substring(Y, Y + 4);
		month = date.substring(M, M + 2);
		daty = date.substring(D, D + 2);
		fmtTag = fmtTag.toUpperCase();
		fmtTag = fmtTag.replaceAll("yyyy", year);
		fmtTag = fmtTag.replaceAll("MM", month);
		fmtTag = fmtTag.replaceAll("dd", daty);
		return fmtTag;
	}

	/**
	 * 計算指定年月的日期數
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	public static int maxDayOfMonth(int year, int month) {
		switch (month) {
		case 1:
		case 3:
		case 5:
		case 7:
		case 8:
		case 10:
		case 12:
			return 31;
		case 4:
		case 6:
		case 9:
		case 11:
			return 30;
		case 2:
			boolean isRunYear = (year % 400 == 0)
					|| (year % 4 == 0 && year % 100 != 0);
			return isRunYear ? 29 : 28;
		default:
			return 31;
		}
	}

	/**
	 * 获取指定年月的日期數
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	public static int maxDayOfMonth(String year, String month) {
		return maxDayOfMonth(Integer.parseInt(year), Integer.parseInt(month));
	}

	/**
	 * 获取指定年月上月月末日期
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	public static String getLastMonthDate(String year, String month) {
		return getLastMonthDate(Integer.parseInt(year), Integer.parseInt(month));
	}

	/**
	 * 获取指定年月上月月末日期
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	public static String getLastMonthDate(int year, int month) {
		if (month <= 1) {
			year -= 1;
			month = 12;
		} else {
			month -= 1;
		}
		StringBuffer bfDate = new StringBuffer();
		bfDate.append(year);
		if (month < 10)
			bfDate.append("0");
		bfDate.append(month);
		bfDate.append(maxDayOfMonth(year, month));
		return bfDate.toString();
	}

	/**
	 * 提前N天的日期
	 * 
	 * @param date
	 * @param days
	 * @return
	 */
	public static Date beforeDate(Date date, int days) {
		java.util.Calendar c = java.util.Calendar.getInstance();
		c.setTime(date);
		c.add(java.util.Calendar.DAY_OF_YEAR, -days);
		return c.getTime();

	}

	/**
	 * @param date
	 * @param days
	 * @return
	 */
	public static Date addHour(Date date, int hour) {
		java.util.Calendar c = java.util.Calendar.getInstance();
		c.setTime(date);
		c.add(java.util.Calendar.HOUR_OF_DAY, hour);
		return c.getTime();
	}

	/**
	 * �?��前的日期
	 * 
	 * @return
	 */
	public static Date getLastWeek() {
		return getNextDay(-7);
	}

	/**
	 * 取相对天数，正数为向后，负数为向�?
	 * 
	 * @param day
	 * @return
	 */
	public static Date getNextDay(int day) {
		java.util.Calendar c = java.util.Calendar.getInstance();
		c.add(java.util.Calendar.DAY_OF_YEAR, day);
		return c.getTime();
	}

	/**
	 * 根据日期以及天数，取相对日期，正数为向后，负数为向前
	 * 
	 * @param day
	 * @return
	 */
	public static String getNextDayByDate(int day,Date date) {
		// 格式化对�?
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		// 日历对象
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(java.util.Calendar.DAY_OF_YEAR, day);
		return format(c.getTime(),"yyyy-MM-dd");
	}
	
	public static String getNextDayStr(int day) {
		return formatDateToStr(getNextDay(day));
	}
	
	public static int curHour(Calendar cal) {
		return cal.get(Calendar.HOUR_OF_DAY);
	}

	public static int curMinute(Calendar cal) {
		return cal.get(Calendar.MINUTE);
	}

	public static int curSecond(Calendar cal) {
		return cal.get(Calendar.SECOND);
	}

	public static String curTimeStr() {
		Calendar cal = Calendar.getInstance();
		// 分别取得当前日期的年、月、日
		StringBuffer bf = new StringBuffer(10);
		int hour = cal.get(Calendar.HOUR_OF_DAY);
		if (hour < 10)
			bf.append("0");
		bf.append(hour);
		bf.append(":");
		int minite = cal.get(Calendar.MINUTE);
		if (minite < 10)
			bf.append("0");
		bf.append(minite);
		bf.append(":");
		int second = cal.get(Calendar.SECOND);
		if (second < 10)
			bf.append("0");
		bf.append(second);
		return bf.toString();
	}

	/***************************************************************************
	 * @功能 计算当前日期某年的第几周
	 * @return interger
	 * @throws ParseException
	 **************************************************************************/
	public static int getWeekNumOfYear() {
		Calendar calendar = Calendar.getInstance();
		int iWeekNum = calendar.get(Calendar.WEEK_OF_YEAR);
		return iWeekNum;
	}

	/***************************************************************************
	 * @功能 计算指定日期某年的第几周
	 * @return interger
	 * @throws ParseException
	 **************************************************************************/
	public static int getWeekNumOfYearDay(String strDate) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(parseDate(strDate));
		int iWeekNum = calendar.get(Calendar.WEEK_OF_YEAR);
		return iWeekNum;
	}

	/***************************************************************************
	 * @功能 计算某年某周的开始日�?
	 * @return interger
	 * @throws ParseException
	 **************************************************************************/
	public static String getWeekFirstDay(int yearNum, int weekNum) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, yearNum);
		cal.set(Calendar.WEEK_OF_YEAR, weekNum);
		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		// 分别取得当前日期的年、月、日
		String tempYear = Integer.toString(yearNum);
		String tempMonth = Integer.toString(cal.get(Calendar.MONTH) + 1);
		String tempDay = Integer.toString(cal.get(Calendar.DATE));
		return tempYear + "-" + tempMonth + "-" + tempDay;
	}
//	0前两个星期，1前一个星期，2当前星期
	public static String getWeekFirstDay(int weekNum) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.WEEK_OF_YEAR, weekNum);
		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		// 分别取得当前日期的年、月、日
		String tempMonth = Integer.toString(cal.get(Calendar.MONTH) + 1);
		String tempDay = Integer.toString(cal.get(Calendar.DATE));
		return cal.get(Calendar.YEAR) + "-" + tempMonth + "-" + tempDay;
	}

	/***************************************************************************
	 * @功能 计算某年某周的结束日�?
	 * @return interger
	 * @throws ParseException
	 **************************************************************************/
	public static String getWeekEndDay(int yearNum, int weekNum) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, yearNum);
		cal.set(Calendar.WEEK_OF_YEAR, weekNum + 1);
		cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		// 分别取得当前日期的年、月、日
		String tempMonth = Integer.toString(cal.get(Calendar.MONTH) + 1);
		String tempDay = Integer.toString(cal.get(Calendar.DATE));
		return cal.get(Calendar.YEAR) + "-" + tempMonth + "-" + tempDay;
	}
//	0前两个星期，1前一个星期，2当前星期
	public static String getWeekEndDay(int weekNum) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.WEEK_OF_YEAR, weekNum + 1);
		cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		// 分别取得当前日期的年、月、日
		String tempMonth = Integer.toString(cal.get(Calendar.MONTH) + 1);
		String tempDay = Integer.toString(cal.get(Calendar.DATE));
		return cal.get(Calendar.YEAR) + "-" + tempMonth + "-" + tempDay;
	}
//	获取当前日期前几个小时或者后几个小时�?为当前日�?
	public static String getDatePreHours(int preHours) {
		// 当前日期
		Date date = new Date();
		// 格式化对�?
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMATTER
				+ " HH:mm:ss");
		// 日历对象
		Calendar calendar = Calendar.getInstance();
		// 设置当前日期
		calendar.setTime(date);
		// 小时增减
		calendar.add(Calendar.HOUR_OF_DAY, preHours);

		return sdf.format(calendar.getTime());
	}

	public static String getDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(new Date());
	}

	public static String getTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		return sdf.format(new Date());
	}
	
	public static String getTime(String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(new Date());
	}
//	dateStr格式为yyyy-MM-dd
	public static boolean isMonthLastDay(String dateStr) {
		Date date=parseDate(dateStr);
		Calendar end=Calendar.getInstance(); 
		end.setTime(date);
		//得到本月�?���?�� 
		end.set(end.DATE, end.getActualMaximum(end.DATE)); 
		String endDate=end.get(end.YEAR)+"-"+(end.get(end.MONTH)+1)+"-"+end.get(end.DATE);
		if(endDate.equals(dateStr)){
			return true;
		}else{
			return false;
		}
	}
	
//	dateStr格式为yyyy-MM-dd
	public static String getNextMonthLastDay(String dateStr) {
		Date date=parseDate(dateStr);
		Calendar end=Calendar.getInstance(); 
		end.setTime(date);
		//得到上月1�?
		end.add(end.MONTH, 1); 
		//得到本月�?���?�� 
		end.set(end.DATE, end.getActualMaximum(end.DATE)); 
		String endDate=end.get(end.YEAR)+"-"+(end.get(end.MONTH)+1)+"-"+end.get(end.DATE);
		return endDate;
	}
	
	public static String getNextMonthDay(String dateStr)
	{
		Date date=parseDate(dateStr);
		Calendar calendar=Calendar.getInstance(); 
		calendar.setTime(date);
		int thisDate = calendar.get(calendar.DATE);//当前月份当天
		calendar.add(calendar.MONTH, 1); //下个�?
		int nextDate = calendar.getActualMaximum(calendar.DATE);//下个月最后一�?
		if(nextDate < thisDate)//下个月天数比这个月少
			calendar.set(calendar.DATE, nextDate);
		else
			calendar.set(calendar.DATE, thisDate);
		// 格式化对�?
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(calendar.getTime());
	}
	public static void main(String[] args)
	{
		XLog.log(DateUtil.format(DateUtil.beforeDate(new Date(), -1),DateUtil.FMT_YMD));
		
		//计算后一天.
		Date nextDate = JDateUtil.beforeDate(new Date(), -1);
		long nextTime = nextDate.getTime();
		long nowTime = new Date().getTime();
		long seconds = nextTime - nowTime;
		
		XLog.log("seconds=next=%s,now=%s,secnods%s",nextTime+"",nowTime+"", seconds+"");
	}
	
	/**
	 * 获取指定YYYY-MM-DD
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	public static String getNewDate(String dateStr)
	{	
		String year = "";
		String month = "";
		String day = "";
		if(dateStr.length()>=8){
			dateStr = dateStr.substring(0,8);
			year = dateStr.substring(0,4);
			month = dateStr.substring(4,6);
			day = dateStr.substring(6,8);
		}
		return year+"-"+month+"-"+day;
	}
	
	
	/**
	 * Author: chenzhuo: getPreciseTime Description: 获取指定格式的系统时间系统时�?
	 * 
	 * @param   其中yyyy-MM-dd是你要表示的格式 :可以任意组合，不限个数和次序；具体表示为：MM-month,dd-day,yyyy-year;kk-hour,mm-minute,ss-second;
	 * @return String
	 * @see com.ct.unit.Syscommon.getSystemTimeBySomeFormat#()
	 */
	 public static String getSystemTimeBySomeFormat(String format) {
		 Date d = new Date();
		 SimpleDateFormat sdf = new SimpleDateFormat(format);
		 String str = sdf.format(d);
		 return str;
	 }
	 
	public static int daysBetweenSimple( String startdate, String enddate )
	{
		startdate = startdate.replace("-", "");
		startdate = startdate.replace("-", "");
		return daysBetween( startdate, enddate, "yyyyMMdd");
	}
	 	
	 //获取两个日期之间的天�?
	 public static int daysBetween( String startdate, String enddate, String format )
	 {
		 SimpleDateFormat sdf =new SimpleDateFormat(format);
		 Date sdate = null;
		 Date edate = null;
		 try {
			 sdate = sdf.parse(startdate);
			 edate = sdf.parse(enddate);
		 } catch (ParseException e) {
			 // TODO Auto-generated catch block
			 e.printStackTrace();

			 System.out.println("failed:ParseException");
			 return -1;
		 }

		 return daysBetween( sdate, edate );
	 }
	
	public static final int daysBetween(Date early, Date late) { 
	     
        java.util.Calendar calst = java.util.Calendar.getInstance();   
        java.util.Calendar caled = java.util.Calendar.getInstance();   
        calst.setTime(early);   
         caled.setTime(late);   
         //设置时间�?�?  
         calst.set(java.util.Calendar.HOUR_OF_DAY, 0);   
         calst.set(java.util.Calendar.MINUTE, 0);   
         calst.set(java.util.Calendar.SECOND, 0);   
         caled.set(java.util.Calendar.HOUR_OF_DAY, 0);   
         caled.set(java.util.Calendar.MINUTE, 0);   
         caled.set(java.util.Calendar.SECOND, 0);   
        //得到两个日期相差的天�?  
         int days = ((int) (caled.getTime().getTime() / 1000) - (int) (calst   
                .getTime().getTime() / 1000)) / 3600 / 24;   
         
        return days;   
   }
}
