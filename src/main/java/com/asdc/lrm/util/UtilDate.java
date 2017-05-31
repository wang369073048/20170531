package com.asdc.lrm.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class UtilDate {

	/**
	 * 根据生日计算年龄
	 * @param brithDate
	 * @return
	 */
	public static int getAge(Date brithDate){
		Calendar cal = Calendar.getInstance();
        int yearNow = cal.get(Calendar.YEAR);
        int brithYear = Integer.parseInt(new SimpleDateFormat("yyyy").format(brithDate));
        return yearNow - brithYear;
	}

	/**
	 * 获取当前年份
	 * @return
	 */
	public static int getYear(){
		Calendar cal = Calendar.getInstance();
		return cal.get(Calendar.YEAR);
	}
    
	/**
	 * 获取当前月份
	 * @return
	 */
	public static int getMonth(){
		Calendar cal = Calendar.getInstance();
		return cal.get(Calendar.MONTH)+1;
	}
    
	/** 
	 * 获取当前月的天数
	 * @return
	 */ 
    public static int getDaysOfMonth() {  
    	Calendar rightNow = Calendar.getInstance();
		SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy/MM");
		String date = simpleDate.format(new Date());
		try {
			rightNow.setTime(simpleDate.parse(date));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		int days = rightNow.getActualMaximum(Calendar.DAY_OF_MONTH);
		return days;
    }
    
    /**
     * 获取本年的第一天和最后一天的日期
     * @return
     */
    public static String[] getYearDate(){
    	String[] yearDate = new String[2];
		Calendar cal = Calendar.getInstance();
		yearDate[0] = cal.get(Calendar.YEAR)+"-01-01";
		yearDate[1] = cal.get(Calendar.YEAR)+"-12-31";
		
		return yearDate;
    }
    
    /**
     * 获取本月的第一天和最后一天的日期
     * @return
     */
    public static String[] getMonthDate(){
    	String[] monthDate = new String[2];
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		
		cal.set(Calendar.DAY_OF_MONTH, 1);
		monthDate[0] = format.format(cal.getTime());
		
		cal.add(Calendar.MONTH, 1);
		cal.add(Calendar.DAY_OF_MONTH, -1);
		monthDate[1] = format.format(cal.getTime());
		
		return monthDate;
    }
    
    /**
	 * 获取本周的星期一和星期日的日期
	 * @return
	 */
	public static String[] getWeekDate(){
		String[] weekDate = new String[2];
		Calendar cal =Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY); //获取本周一的日期
        weekDate[0] = df.format(cal.getTime());
		
		cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		cal.add(Calendar.WEEK_OF_YEAR, 1);
		weekDate[1] = df.format(cal.getTime());
		
		return weekDate;
	}
	
	/**
	 * 获取下一年的今天
	 * @return
	 */
	public static String getNextYearToday() {
		String today = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		String[] todayArray = today.split("-");
		int year = Integer.parseInt(todayArray[0]) + 1;
		return year + "-" + todayArray[1] + "-" + todayArray[2];
	}
	
	/**
	 * 字符串转日期格式
	 * @param dateStr
	 * @return yyyy-MM-dd HH:mm:ss
	 */
	public static Date StringToDate(String dateStr){
		DateFormat dd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date=null;
		try {
			date = dd.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	
	/**
	 * 比较两个日期
	 * @param obj
	 * @return
	 */
	public static boolean Date1BeforeDate2(String dateStr1, String dateStr2){
		DateFormat dd = new SimpleDateFormat("yyyy-MM-dd");
		Date date1=null;
		Date date2=null;
		try {
			date1 = dd.parse(dateStr1);
			date2 = dd.parse(dateStr2);
			if(date1.before(date2)){
				return true;
			}else{
				return false;
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * 得到时间字符串中的年份
	 * @param dateStr
	 * @return
	 */
	public static int getYear (String dateStr){
		SimpleDateFormat df = new SimpleDateFormat("yyyy");
		int year = 0;
		year = Integer.parseInt(df.format(UtilDate.StringToDate(dateStr)));
		return year;
	}
	
	/**
	 * 获取 某年的最后一天
	 * @return long
	 */
	public static long getYearTime(int year){
		Calendar Year = Calendar.getInstance();
		Year.set(year, 12, 31, 59, 59);
		Date YearTime = Year.getTime();
		return YearTime.getTime();
	}
}
