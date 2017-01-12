package com.ducetech.pms.service;

import java.util.List;

import com.ducetech.framework.model.PagerRS;
import com.ducetech.framework.model.Query;
import com.ducetech.pms.model.Notice;
import com.ducetech.pms.model.NoticeRole;

/** 
* @ClassName: NoticeService  
* @author gaoy
* @date 2016年9月6日 下午4:43:42 
* @Description: 系统通知Service接口
*/
public interface NoticeService {
	
	/** 
	* @Title: getAllNotice  
	* @return List<Notice>
	* @Description: 获取所有系统通知
	*/
	List<Notice> getAllNotice();
	
	/** 
	* @Title: getNoticeByQuery  
	* @param notice
	* @return List<Notice>
	* @Description: 按条件筛选通知，不带分页
	*/
	List<Notice> getNoticeByQuery(Notice notice);
	
	/** 
	* @Title: getNoticeByPager  
	* @param query
	* @return PagerRS<Notice>
	* @Description: 按条件筛选通知，带分页
	*/
	PagerRS<Notice> getNoticeByPager(Query<Notice> query);
	
	/** 
	* @Title: gettNoticeById  
	* @param noticeId
	* @return Notice
	* @Description: 按ID查询系统通知
	*/
	Notice gettNoticeById(String noticeId);
	
	/** 
	* @Title: addNotice  
	* @param notice
	* @Description: 新增系统通知
	*/
	void addNotice(Notice notice);
	
	/** 
	* @Title: updateNotice  
	* @param notice
	* @return void
	* @Description: 更新系统通知
	*/
	void updateNotice(Notice notice);
	
	/** 
	* @Title: getNoticeByUserId  
	* @param query
	* @param userId
	* @return PagerRS<Notice>
	* @Description: 通过人员ID获取通知
	*/
	PagerRS<Notice> getNoticeByUserId(Query<Notice> query, String userId);
	
	/** 
	* @Title: getNoticeByRoleIds  
	* @param query
	* @param roleIds
	* @return PagerRS<Notice>
	* @Description: 通过角色ID获取通知
	*/
	PagerRS<Notice> getNoticeByRoleIds(Query<Notice> query, List<String> roleIds);
	
	/** 
	* @Title: getNoticeByUserId  
	* @param query
	* @return PagerRS<Notice>
	* @Description: 按人员ID获取系统通知
	*/
	PagerRS<Notice> getNoticeByNoticeRole(Query<NoticeRole> query);
}
