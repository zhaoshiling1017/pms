package com.ducetech.pms.model;

import java.io.Serializable;

import com.ducetech.framework.model.BaseModel;
import com.ducetech.framework.model.Role;
import com.ducetech.framework.model.User;

/**
 * 
* @ClassName: ProcinstFile 
* @Description: 实例附件实体类
* @author yett 
* @date 2016年9月5日 下午3:43:23 
*
 */
public class ProcinstFile extends BaseModel implements Serializable{

	private static final long serialVersionUID = 1L;
	
	//流程附件ID	
	private String procinstFileId;
	//流程实例ID	
	private String procinstId;
	//类型
	private String procType;
	//工单ID	
	private String taskId;
	//节点ID
	private String nodeId;
	//节点
	private ProcNode procNode;
	//是否退回标记
	private String isBack;
	//上传时间	
	private String uploadTime;
	//附件地址	
	private String path;
	//附件名称
	private String fileName;
	//上传附件的工单ID
	private String uploadTaskId;
	//上传附件的节点ID
	private String uploadNodeId;
	//上传节点
	private ProcNode uploadNode;
	//上传附件角色ID
	private String uploadRoleId;
	//上传角色
	private	Role uploadRole;
	//上传人
	private User uploadPerson;
	public String getProcinstFileId() {
		return procinstFileId;
	}
	public void setProcinstFileId(String procinstFileId) {
		this.procinstFileId = procinstFileId;
	}
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
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public String getNodeId() {
		return nodeId;
	}
	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}
	public ProcNode getProcNode() {
		return procNode;
	}
	public void setProcNode(ProcNode procNode) {
		this.procNode = procNode;
	}
	public String getIsBack() {
		return isBack;
	}
	public void setIsBack(String isBack) {
		this.isBack = isBack;
	}
	public String getUploadTime() {
		return uploadTime;
	}
	public void setUploadTime(String uploadTime) {
		this.uploadTime = uploadTime;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getUploadTaskId() {
		return uploadTaskId;
	}
	public void setUploadTaskId(String uploadTaskId) {
		this.uploadTaskId = uploadTaskId;
	}
	public String getUploadNodeId() {
		return uploadNodeId;
	}
	public void setUploadNodeId(String uploadNodeId) {
		this.uploadNodeId = uploadNodeId;
	}
	public ProcNode getUploadNode() {
		return uploadNode;
	}
	public void setUploadNode(ProcNode uploadNode) {
		this.uploadNode = uploadNode;
	}
	public String getUploadRoleId() {
		return uploadRoleId;
	}
	public void setUploadRoleId(String uploadRoleId) {
		this.uploadRoleId = uploadRoleId;
	}
	public Role getUploadRole() {
		return uploadRole;
	}
	public void setUploadRole(Role uploadRole) {
		this.uploadRole = uploadRole;
	}
	public User getUploadPerson() {
		return uploadPerson;
	}
	public void setUploadPerson(User uploadPerson) {
		this.uploadPerson = uploadPerson;
	}
}
