package com.ducetech.pms.model;

import java.io.Serializable;

import com.ducetech.framework.model.BaseModel;

/** 
* @ClassName: Statistics  
* @author gaoy
* @date 2016年9月29日 上午9:57:50 
* @Description: 统计实体类
*/
public class Statistics extends BaseModel implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String year;			//年
	
	private String month;			//月
	
	private String day;				//日
	
	private String date;			//日期
	
	private String count;			//数量
	
	private String procType;		//流程类型
	
	private String procinstStatus;	//流程实例状态
	
	private String procName;		//流程名称

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public String getProcType() {
		return procType;
	}

	public void setProcType(String procType) {
		this.procType = procType;
	}

	public String getProcinstStatus() {
		return procinstStatus;
	}

	public void setProcinstStatus(String procinstStatus) {
		this.procinstStatus = procinstStatus;
	}

	public String getProcName() {
		return procName;
	}

	public void setProcName(String procName) {
		this.procName = procName;
	}
}
