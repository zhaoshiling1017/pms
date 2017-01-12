package com.ducetech.pms.model;

import java.io.Serializable;

import com.ducetech.framework.model.BaseModel;

/** 
* @ClassName: msgRole  
* @author gaoy
* @date 2016年9月5日 下午3:24:21 
* @Description: 集团消息与角色
*/
public class MsgRole extends BaseModel implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String id;				//ID
	
	private String msgId;			//集团消息ID
	
	private String roleId;			//角色ID
	
	private String userId;			//人员ID
	
	private String userName;		//人员姓名
	
	private String status;			//已读未读状态
	
	private String readTime;		//阅读时间

	private String delTime;		//删除时间
	
	private Message message;
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMsgId() {
		return msgId;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getReadTime() {
		return readTime;
	}

	public void setReadTime(String readTime) {
		this.readTime = readTime;
	}

	public String getDelTime() {
		return delTime;
	}

	public void setDelTime(String delTime) {
		this.delTime = delTime;
	}

	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}
}
