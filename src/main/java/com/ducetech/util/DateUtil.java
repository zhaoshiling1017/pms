/**
* Copyright 2011 NetEase. All Right Reserved.
* 
* @author Youhua Wang
* @version Jun 28, 2011 11:48:14 AM
*/
package com.ducetech.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

/**
 * DateUtil.java
 */
public class DateUtil {
	public static SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	public static SimpleDateFormat dfCode = new SimpleDateFormat("yyyyMMdd");
	public static SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static SimpleDateFormat dtCode = new SimpleDateFormat("yyyyMMddHHmmss");

	public static String dateToString(Date date){
		 
		return df.format(date);
	}

	public static String dateToStringCode(Date date) {

		return dfCode.format(date);
	}
	
	public static String dateTimeToStringCode(Date date) {

		return dtCode.format(date);
	}

	public static String dateTimeToString(Date date) {

		return dt.format(date);
	}
	
	public static String yesterday(){
		Calendar day=Calendar.getInstance(); 
        day.add(Calendar.DATE,-1); 
        return df.format(day.getTime()); 
	}
	
	public static Date parse(String dateStr) throws ParseException{
        return df.parse(dateStr);
	}

	public static Date parseTime(String dateStr) throws ParseException {
		return dt.parse(dateStr);
	}
	
	public static List<String> lastDays(int count){
		List<String> strings =new ArrayList<String>();
		Calendar day=Calendar.getInstance(); 
		for(int i=0;i<count;i++){
			day.add(Calendar.DATE,-1); 
			strings.add(df.format(day.getTime()));
		}
        return strings; 
	}
	
	public static String trimDate(String date){
		if(StringUtils.isNotBlank(date)){
			String[] arr=date.split("-");
			return arr[1]+"-"+arr[2];
		}
		else {
			return "";
		}
	}
    public static long getMinitesBetween(Date oldDate, Date newDate) {
        long minites = 0;
        if (oldDate != null && newDate != null) {
            minites = newDate.getTime() - oldDate.getTime();
        }
        return minites;
    }
    
    /**
     * 
    * @Title: formatDate 
    * @Description: 格式化日期后面的.0
    * @param @param date
    * @param @return    设定文件 
    * @return String    返回类型 
    * @throws
     */
    public static String formatDate(String date) {
    	date = date.substring(0, 19);
		return date;
    }
    
}
