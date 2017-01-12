package com.ducetech.pms.model;

import java.io.Serializable;

import com.ducetech.framework.model.BaseModel;
import com.ducetech.framework.model.User;

/** 
* @ClassName: Message  
* @author gaoy
* @date 2016年9月5日 下午3:17:42 
* @Description: 集团消息
*/
public class Message extends BaseModel implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String msgId;			//集团消息ID
	
	private String msgTitle;		//消息标题
	
	private String content;			//消息内容
	
	private String publisherId;		//发布人ID
	
	private String publishTime;		//发布时间
	
	private String status;			//发送状态 
	
	private String delTime;			//删除时间
	
	private String roleName;		//角色名称
	
	private User user;

	public String getMsgId() {
		return msgId;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}

	public String getMsgTitle() {
		return msgTitle;
	}

	public void setMsgTitle(String msgTitle) {
		this.msgTitle = msgTitle;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getPublisherId() {
		return publisherId;
	}

	public void setPublisherId(String publisherId) {
		this.publisherId = publisherId;
	}

	public String getPublishTime() {
		return publishTime;
	}

	public void setPublishTime(String publishTime) {
		this.publishTime = publishTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDelTime() {
		return delTime;
	}

	public void setDelTime(String delTime) {
		this.delTime = delTime;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
}
