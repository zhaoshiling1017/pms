package com.ducetech.pms.model;

import java.io.Serializable;

import com.ducetech.framework.model.BaseModel;

/** 
* @ClassName: ProcdefRole  
* @author gaoy
* @date 2016年9月5日 下午4:40:25 
* @Description: 流程定义与发起角色
*/
public class ProcdefRole extends BaseModel implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String Id;			//ID
	
	private String procdefId;	//流程定义ID
	
	private String roleId;		//角色ID

	public String getId() {
		return Id;
	}

	public void setId(String id) {
		Id = id;
	}

	public String getProcdefId() {
		return procdefId;
	}

	public void setProcdefId(String procdefId) {
		this.procdefId = procdefId;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
}
