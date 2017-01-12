package com.ducetech.pms.service;

import java.util.List;

import com.ducetech.framework.model.PagerRS;
import com.ducetech.framework.model.Query;
import com.ducetech.pms.model.Message;

/**
 * 
* @ClassName: MessageService 
* @Description: 集团消息Service 
* @author yett 
* @date 2016年9月6日 下午2:38:40 
*
 */
public interface MessageService {
	
	/** 
	 * 
	* @Title: getMessageByUserId 
	* @Description: 按人员ID获取集团消息
	* @param @param query
	* @param @param userId
	* @param @return    设定文件 
	* @return PagerRS<Message>    返回类型 
	* @throws
	 */
	PagerRS<Message> getMessageByUserId(Query<Message> query, String userId);
	
	/** 
	 * 
	* @Title: getMessageByRoleIds 
	* @Description: 通过角色IDS获取集团消息
	* @param @param query
	* @param @param roleIds
	* @param @return    设定文件 
	* @return PagerRS<Message>    返回类型 
	* @throws
	 */
	PagerRS<Message> getMessageByRoleIds(Query<Message> query, List<String> roleIds);
	
	
	Message getMessageById(String msgId);
	
	void addMessage(Message message);
	
	void updateMessage(Message message);
	
	void deleteMessage(Message message);
}
