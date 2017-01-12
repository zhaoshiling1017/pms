package com.ducetech.pms.model;

import java.io.Serializable;

import com.ducetech.framework.model.BaseModel;

/**
 * 
* @ClassName: TaskLog 
* @Description: 工单流转记录
* @author yett 
* @date 2016年10月8日 下午3:14:34 
*
 */
public class TaskLog extends BaseModel implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String id;            	//ID
	
	private String taskId;			//工单ID
	
	private String upTaskId;		//上一工单ID
	
	private String nodeName;		//节点名称	
	
	private String person;		    //受理人ID
	
	private String nodeTime;		//时间
	
	private String procinstId;		//实例ID
	
	private String nodeRole;		//节点角色
	
	private String nodeHandle;		//处理节点
	
	private String nodeAction;		//节点动作

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	public String getNodeTime() {
		return nodeTime;
	}

	public void setNodeTime(String nodeTime) {
		this.nodeTime = nodeTime;
	}

	public String getProcinstId() {
		return procinstId;
	}

	public void setProcinstId(String procinstId) {
		this.procinstId = procinstId;
	}

	public String getNodeRole() {
		return nodeRole;
	}

	public void setNodeRole(String nodeRole) {
		this.nodeRole = nodeRole;
	}

	public String getNodeHandle() {
		return nodeHandle;
	}

	public void setNodeHandle(String nodeHandle) {
		this.nodeHandle = nodeHandle;
	}

	public String getNodeAction() {
		return nodeAction;
	}

	public void setNodeAction(String nodeAction) {
		this.nodeAction = nodeAction;
	}

	public String getPerson() {
		return person;
	}

	public void setPerson(String person) {
		this.person = person;
	}

	public String getUpTaskId() {
		return upTaskId;
	}

	public void setUpTaskId(String upTaskId) {
		this.upTaskId = upTaskId;
	}
	
	
	
}
