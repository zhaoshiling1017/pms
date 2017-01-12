package com.ducetech.pms.model;

import java.io.Serializable;
import java.util.List;

import com.ducetech.framework.model.BaseModel;
import com.ducetech.framework.model.User;

/** 
 * @ClassName: Procinst 
 * @Description: 流程实例实体类
 * @author yett 
 * @date 2016年9月5日 下午3:35:22 
 *  
 */
public class Procinst extends BaseModel implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	//流程实例ID	
	private String procinstId;	
	//流程类型  0普通，1临时
	private String procType;
	//流程名称	
	private String procName;	
	//实例编号	
	private String procinstCode;
	//流程启动时间	
	private String startTime;	
	//流程结束时间
	private String endTime;
	//运行状态  0未完成，1已完成，2已作废已
	private String status;	
	//流程定义ID	
	private String procdefId;	
	//发起人ID	
	private String startPersonId;
	//发起人角色ID
	private String startPersonRoleId;
	//发起人
	private User startPerson;			
	//备注	
	private String comment;
	//工单List
	private List<Task> tasks;
	//节点名称
	private String nodeNames;
	
	//查询开始结束时间
	private String beginTime;
	private String finishTime;
	//部门
	private String deptId;
	
	public String getProcinstId() {
		return procinstId;
	}
	public void setProcinstId(String procinstId) {
		this.procinstId = procinstId;
	}
	public String getProcType() {
		return procType;
	}
	public void setProcType(String procType) {
		this.procType = procType;
	}
	public String getProcName() {
		return procName;
	}
	public void setProcName(String procName) {
		this.procName = procName;
	}
	public String getProcinstCode() {
		return procinstCode;
	}
	public void setProcinstCode(String procinstCode) {
		this.procinstCode = procinstCode;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getProcdefId() {
		return procdefId;
	}
	public void setProcdefId(String procdefId) {
		this.procdefId = procdefId;
	}
	public String getStartPersonId() {
		return startPersonId;
	}
	public void setStartPersonId(String startPersonId) {
		this.startPersonId = startPersonId;
	}
	public User getStartPerson() {
		return startPerson;
	}
	public void setStartPerson(User startPerson) {
		this.startPerson = startPerson;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public List<Task> getTasks() {
		return tasks;
	}
	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}
	public String getNodeNames() {
		return nodeNames;
	}
	public void setNodeNames(String nodeNames) {
		this.nodeNames = nodeNames;
	}
	public String getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}
	public String getFinishTime() {
		return finishTime;
	}
	public void setFinishTime(String finishTime) {
		this.finishTime = finishTime;
	}
	public String getDeptId() {
		return deptId;
	}
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
	public String getStartPersonRoleId() {
		return startPersonRoleId;
	}
	public void setStartPersonRoleId(String startPersonRoleId) {
		this.startPersonRoleId = startPersonRoleId;
	}
}
