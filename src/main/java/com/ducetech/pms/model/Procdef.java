package com.ducetech.pms.model;

import java.io.Serializable;

import com.ducetech.framework.model.BaseModel;

/** 
* @ClassName: Procdef  
* @author gaoy
* @date 2016年9月5日 下午3:00:13 
* @Description: 流程定义
*/
public class Procdef extends BaseModel implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String procdefId;		//流程定义ID
	
	private String procName;		//流程名称
	
	private String procCode;		//流程编号
	
	private String procType;		//流程类型	0普通，1临时

	public String getProcdefId() {
		return procdefId;
	}

	public void setProcdefId(String procdefId) {
		this.procdefId = procdefId;
	}

	public String getProcName() {
		return procName;
	}

	public void setProcName(String procName) {
		this.procName = procName;
	}

	public String getProcCode() {
		return procCode;
	}

	public void setProcCode(String procCode) {
		this.procCode = procCode;
	}

	public String getProcType() {
		return procType;
	}

	public void setProcType(String procType) {
		this.procType = procType;
	}
}
