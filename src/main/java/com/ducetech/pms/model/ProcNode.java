package com.ducetech.pms.model;

import java.io.Serializable;
import java.util.List;

import com.ducetech.framework.model.BaseModel;
import com.ducetech.framework.model.Role;

/** 
* @ClassName: ProcNode  
* @author gaoy
* @date 2016年9月5日 下午3:06:08 
* @Description: 流程节点
*/
public class ProcNode extends BaseModel implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String nodeId;		//流程节点ID
	
	private String nodeName;	//节点名称
	
	private String nodeCode;	//节点编号
	
	private String nodeType;	//节点类型	0普通节点，1审批节点
	
	private String isTime;		//是否限时
	
	private String upNodeId;	//上一节点ID
	
	private String nextNodeId;	//下一节点ID
	
	private String procdefId;	//流程定义ID
	
	private List<Role> roles;	//角色

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

	public String getNodeCode() {
		return nodeCode;
	}

	public void setNodeCode(String nodeCode) {
		this.nodeCode = nodeCode;
	}

	public String getNodeType() {
		return nodeType;
	}

	public void setNodeType(String nodeType) {
		this.nodeType = nodeType;
	}

	public String getIsTime() {
		return isTime;
	}

	public void setIsTime(String isTime) {
		this.isTime = isTime;
	}

	public String getUpNodeId() {
		return upNodeId;
	}

	public void setUpNodeId(String upNodeId) {
		this.upNodeId = upNodeId;
	}

	public String getNextNodeId() {
		return nextNodeId;
	}

	public void setNextNodeId(String nextNodeId) {
		this.nextNodeId = nextNodeId;
	}

	public String getProcdefId() {
		return procdefId;
	}

	public void setProcdefId(String procdefId) {
		this.procdefId = procdefId;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
}
