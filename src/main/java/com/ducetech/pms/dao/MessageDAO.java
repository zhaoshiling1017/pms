package com.ducetech.pms.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ducetech.pms.model.Message;

/**
 * 
* @ClassName: MessageDAO 
* @Description: 集团消息DAO 
* @author yett 
* @date 2016年9月6日 下午2:39:00 
*
 */
public interface MessageDAO {

	Message selectMessageById(@Param("msgId") String msgId);
	
	void insertMessage(Message message);
	
	void updateMessage(Message message);
	
	void deleteMessage(Message message);
	
	/** 
	 * 
	* @Title: selectTasksByRoleIds 
	* @Description: 通过角色IDS获取集团消息
	* @param @param task
	* @param @param roleIds
	* @param @return    设定文件 
	* @return List<Task>    返回类型 
	* @throws
	 */
	List<Message> selectMessageByRoleIds(@Param("message") Message message,@Param("roleIds") List<String> roleIds);
}
