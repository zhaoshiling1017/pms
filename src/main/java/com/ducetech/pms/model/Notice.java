package com.ducetech.pms.model;

import java.io.Serializable;

import com.ducetech.framework.model.BaseModel;
import com.ducetech.framework.model.Role;

/** 
* @ClassName: Notice  
* @author gaoy
* @date 2016年9月5日 下午3:44:24 
* @Description: 通知
*/
public class Notice extends BaseModel implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String noticeId;			//系统消息ID
	
	private String procdefId;			//流程定义ID
	
	private	Procdef procdef;			//流程定义
	
	private String procinstId;			//流程实例ID
	
	private Procinst procinst;			//流程实例
	
	private String noticeType;			//通知类型
	
	private String remindTime;			//定时日期
	
	private String remindType;			//定时类型
	
	private String comment;				//备注
	
	private String sendTime;			//发送时间
	
	private String taskId;				//工单ID
	
	private Task task;					//工单
	
	private String roleId;				//角色ID
	
	private Role role;					//角色

	public String getNoticeId() {
		return noticeId;
	}

	public void setNoticeId(String noticeId) {
		this.noticeId = noticeId;
	}

	public String getProcdefId() {
		return procdefId;
	}

	public void setProcdefId(String procdefId) {
		this.procdefId = procdefId;
	}

	public Procdef getProcdef() {
		return procdef;
	}

	public void setProcdef(Procdef procdef) {
		this.procdef = procdef;
	}

	public String getProcinstId() {
		return procinstId;
	}

	public void setProcinstId(String procinstId) {
		this.procinstId = procinstId;
	}

	public Procinst getProcinst() {
		return procinst;
	}

	public void setProcinst(Procinst procinst) {
		this.procinst = procinst;
	}

	public String getNoticeType() {
		return noticeType;
	}

	public void setNoticeType(String noticeType) {
		this.noticeType = noticeType;
	}

	public String getRemindTime() {
		return remindTime;
	}

	public void setRemindTime(String remindTime) {
		this.remindTime = remindTime;
	}

	public String getRemindType() {
		return remindType;
	}

	public void setRemindType(String remindType) {
		this.remindType = remindType;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getSendTime() {
		return sendTime;
	}

	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}
}
