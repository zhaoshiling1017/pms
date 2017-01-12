package com.ducetech.websocket;

import java.util.Date;

import org.springframework.web.socket.WebSocketSession;

/** 
 * @ClassName: UserSocketVo  
 * @author chensf
 * @date 2016年5月12日 下午4:33:45 
 * @Description: 用户Socket连接实体
 */
public class UserSocketVo {

	private String userId; //用户ID
	
	private Date connectionTime; //成功连接时间
	
	private Date preRequestTime; //上次请求时间
	
	private Date newRequestTime; //新请求时间
	
	private Date lastSendTime = new Date(); //下架消息最近一次发送时间
	
	private Date lastTaskSendTime = new Date(); //待处理任务最近一次发送时间
	
	private WebSocketSession webSocketSession; //用户对应的wsSession 默认仅缓存一个

	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * @return the connectionTime
	 */
	public Date getConnectionTime() {
		return connectionTime;
	}

	/**
	 * @param connectionTime the connectionTime to set
	 */
	public void setConnectionTime(Date connectionTime) {
		this.connectionTime = connectionTime;
	}

	/**
	 * @return the preRequestTime
	 */
	public Date getPreRequestTime() {
		return preRequestTime;
	}

	/**
	 * @param preRequestTime the preRequestTime to set
	 */
	public void setPreRequestTime(Date preRequestTime) {
		this.preRequestTime = preRequestTime;
	}

	/**
	 * @return the newRequestTime
	 */
	public Date getNewRequestTime() {
		return newRequestTime;
	}

	/**
	 * @param newRequestTime the newRequestTime to set
	 */
	public void setNewRequestTime(Date newRequestTime) {
		this.newRequestTime = newRequestTime;
	}

	/**
	 * @return the lastSendTime
	 */
	public Date getLastSendTime() {
		return lastSendTime;
	}

	/**
	 * @param lastSendTime the lastSendTime to set
	 */
	public void setLastSendTime(Date lastSendTime) {
		this.lastSendTime = lastSendTime;
	}

	/**
	 * @return the lastTaskSendTime
	 */
	public Date getLastTaskSendTime() {
		return lastTaskSendTime;
	}

	/**
	 * @param lastTaskSendTime the lastTaskSendTime to set
	 */
	public void setLastTaskSendTime(Date lastTaskSendTime) {
		this.lastTaskSendTime = lastTaskSendTime;
	}

	/**
	 * @return the webSocketSession
	 */
	public WebSocketSession getWebSocketSession() {
		return webSocketSession;
	}

	/**
	 * @param webSocketSession the webSocketSession to set
	 */
	public void setWebSocketSession(WebSocketSession webSocketSession) {
		this.webSocketSession = webSocketSession;
	}	
}
