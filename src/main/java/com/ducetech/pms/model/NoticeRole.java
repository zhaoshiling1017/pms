package com.ducetech.pms.model;

import java.io.Serializable;

import com.ducetech.framework.model.BaseModel;

/**
 * 
* @ClassName: NoticeRole 
* @Description: 通知与角色实体类 
* @author yett 
* @date 2016年9月5日 下午3:49:43 
*
 */

public class NoticeRole extends BaseModel implements Serializable{

	private static final long serialVersionUID = 1L;

	//ID	
	private String Id;
	//角色ID	
	private String roleId;
	//通知ID	
	private String noticeId;
	//人员ID	
	private String userId;
	//已读未读状态	
	private String status;
	//阅读时间	
	private String readTime;
	//收到时间	
	private String receiveTime;
	//删除时间
	private String delTime;		
	//对象
	private Notice notice;
	
	public String getId() {
		return Id;
	}
	public void setId(String id) {
		Id = id;
	}
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public String getNoticeId() {
		return noticeId;
	}
	public void setNoticeId(String noticeId) {
		this.noticeId = noticeId;
	}
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
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
	public String getReceiveTime() {
		return receiveTime;
	}
	public void setReceiveTime(String receiveTime) {
		this.receiveTime = receiveTime;
	}
	public String getDelTime() {
		return delTime;
	}
	public void setDelTime(String delTime) {
		this.delTime = delTime;
	}
	public Notice getNotice() {
		return notice;
	}
	public void setNotice(Notice notice) {
		this.notice = notice;
	}
}
