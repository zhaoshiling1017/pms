package com.ducetech.pms.model;

import java.io.Serializable;
import java.util.List;

import com.ducetech.framework.model.BaseModel;
import com.ducetech.framework.model.Role;
import com.ducetech.framework.model.User;

/**
 * 
* @ClassName: Task 
* @Description: 工单实体类 
* @author yett 
* @date 2016年9月5日 下午3:28:30 
*
 */
public class Task extends BaseModel implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String taskId;	            //工单ID
	
	private String taskCode;			//工单编号
	
	private String procinstId;			//流程实例ID	
	
	private String procinstCode;        //流程实例编号
	
	private Procinst procinst;			//流程实例
	
	private String procType;			//流程类型	0普通流程，1临时流程
	
	private String upNodeId;			//上一节点ID
	
	private ProcNode upNode;			//上一节点
	
	private String nextNodeId;			//下一节点ID
	
	private String nodeId;				//节点ID
	
	private String nodeName;	        //节点名称
	
	private String nodeType;	        //节点类型
	
	private ProcNode procNode;			//节点
	
	private String timeLimit;	        //限时时间
	
	private String status;	      		//完成状态	0未完成，1已完成，2已作废
	
	private List<String> statusMulti;	//多个状态
	
	private String comment;				//备注
	
	private String startTime;			//任务开始时间
	
	private String endTime;		    	//任务完成时间
	
	private String startPersonId; 		//发起人ID
	
	private String startPersonRoleId;   //发起人角色ID
	
	private String startDeptId;			//发起人部门ID
	
	private User startPerson;			//发起人
	
	private String disposePersionId;	//处理人ID
	
	private User disposePersion;		//处理人
	
	private String disposeTime;			//处理时间
	
	private String backPersionId;		//退回人ID
	
	private User backPersion;			//退回人
	
	private String backTime;			//退回时间
	
	private String backComment;			//退回备注
	
	private String isBack;				//退回标记
	
	private String roleId;				//角色ID
	
	private Role role;					//角色
	
	private List<Task> tasks;			//多个工单
	
	private String upTaskId;			//上一工单ID
	
	private Task upTask;				//上一工单
	
	private String approveTaskId;		//审批工单ID
	
	private Task approveTask;			//审批工单
	
	private String beginTime;			//时间起
	
	private String finishTime;			//时间止
	
	private String file;				//附件

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getTaskCode() {
		return taskCode;
	}

	public void setTaskCode(String taskCode) {
		this.taskCode = taskCode;
	}

	public String getProcinstId() {
		return procinstId;
	}

	public void setProcinstId(String procinstId) {
		this.procinstId = procinstId;
	}

	public String getProcinstCode() {
		return procinstCode;
	}

	public void setProcinstCode(String procinstCode) {
		this.procinstCode = procinstCode;
	}

	public Procinst getProcinst() {
		return procinst;
	}

	public void setProcinst(Procinst procinst) {
		this.procinst = procinst;
	}

	public String getProcType() {
		return procType;
	}

	public void setProcType(String procType) {
		this.procType = procType;
	}

	public String getUpNodeId() {
		return upNodeId;
	}

	public void setUpNodeId(String upNodeId) {
		this.upNodeId = upNodeId;
	}

	public ProcNode getUpNode() {
		return upNode;
	}

	public void setUpNode(ProcNode upNode) {
		this.upNode = upNode;
	}

	public String getNextNodeId() {
		return nextNodeId;
	}

	public void setNextNodeId(String nextNodeId) {
		this.nextNodeId = nextNodeId;
	}

	public String getNodeId() {
		return nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	public String getNodeType() {
		return nodeType;
	}

	public void setNodeType(String nodeType) {
		this.nodeType = nodeType;
	}

	public ProcNode getProcNode() {
		return procNode;
	}

	public void setProcNode(ProcNode procNode) {
		this.procNode = procNode;
	}

	public String getTimeLimit() {
		return timeLimit;
	}

	public void setTimeLimit(String timeLimit) {
		this.timeLimit = timeLimit;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<String> getStatusMulti() {
		return statusMulti;
	}

	public void setStatusMulti(List<String> statusMulti) {
		this.statusMulti = statusMulti;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
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

	public String getStartPersonId() {
		return startPersonId;
	}

	public void setStartPersonId(String startPersonId) {
		this.startPersonId = startPersonId;
	}

	public String getStartDeptId() {
		return startDeptId;
	}

	public void setStartDeptId(String startDeptId) {
		this.startDeptId = startDeptId;
	}

	public User getStartPerson() {
		return startPerson;
	}

	public void setStartPerson(User startPerson) {
		this.startPerson = startPerson;
	}

	public String getDisposePersionId() {
		return disposePersionId;
	}

	public void setDisposePersionId(String disposePersionId) {
		this.disposePersionId = disposePersionId;
	}

	public User getDisposePersion() {
		return disposePersion;
	}

	public void setDisposePersion(User disposePersion) {
		this.disposePersion = disposePersion;
	}

	public String getDisposeTime() {
		return disposeTime;
	}

	public void setDisposeTime(String disposeTime) {
		this.disposeTime = disposeTime;
	}

	public String getBackPersionId() {
		return backPersionId;
	}

	public void setBackPersionId(String backPersionId) {
		this.backPersionId = backPersionId;
	}

	public User getBackPersion() {
		return backPersion;
	}

	public void setBackPersion(User backPersion) {
		this.backPersion = backPersion;
	}

	public String getBackTime() {
		return backTime;
	}

	public void setBackTime(String backTime) {
		this.backTime = backTime;
	}

	public String getBackComment() {
		return backComment;
	}

	public void setBackComment(String backComment) {
		this.backComment = backComment;
	}

	public String getIsBack() {
		return isBack;
	}

	public void setIsBack(String isBack) {
		this.isBack = isBack;
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

	public List<Task> getTasks() {
		return tasks;
	}

	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}

	public String getUpTaskId() {
		return upTaskId;
	}

	public void setUpTaskId(String upTaskId) {
		this.upTaskId = upTaskId;
	}

	public Task getUpTask() {
		return upTask;
	}

	public void setUpTask(Task upTask) {
		this.upTask = upTask;
	}

	public String getApproveTaskId() {
		return approveTaskId;
	}

	public void setApproveTaskId(String approveTaskId) {
		this.approveTaskId = approveTaskId;
	}

	public Task getApproveTask() {
		return approveTask;
	}

	public void setApproveTask(Task approveTask) {
		this.approveTask = approveTask;
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

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	public String getStartPersonRoleId() {
		return startPersonRoleId;
	}

	public void setStartPersonRoleId(String startPersonRoleId) {
		this.startPersonRoleId = startPersonRoleId;
	}
}
