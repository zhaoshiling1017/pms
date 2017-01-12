package com.ducetech.pms.model;

import java.io.Serializable;

import com.ducetech.framework.model.BaseModel;
import com.ducetech.framework.model.User;


/**
 * 
* @ClassName: Assignee 
* @Description: 临时工单部室处理结果 
* @author yett 
* @date 2016年9月23日 上午11:23:05 
*
 */
		
public class Assignee extends BaseModel implements Serializable{

	private static final long serialVersionUID = 1L;

	private String assigneeId;   	//id
	
	private String taskId;			//工单ID		
	
	private String userId;			//受理角色Id
	
	private User user;				//对象
	
	private String result;			//受理结果
	
	private String comment;			//受理意见
	
	private String suggestTime;     //建议时间
	
	private String suggestId;		//建议人
	
	private User suggestUser;		//对象

	public String getAssigneeId() {
		return assigneeId;
	}

	public void setAssigneeId(String assigneeId) {
		this.assigneeId = assigneeId;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getSuggestTime() {
		return suggestTime;
	}

	public void setSuggestTime(String suggestTime) {
		this.suggestTime = suggestTime;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getSuggestId() {
		return suggestId;
	}

	public void setSuggestId(String suggestId) {
		this.suggestId = suggestId;
	}

	public User getSuggestUser() {
		return suggestUser;
	}

	public void setSuggestUser(User suggestUser) {
		this.suggestUser = suggestUser;
	}

	
	
	
}
