package com.ducetech.pms.service;

import java.util.List;

import com.ducetech.framework.model.PagerRS;
import com.ducetech.framework.model.Query;
import com.ducetech.pms.model.NoticeRole;

/** 
* @ClassName: NoticeRoleService  
* @author gaoy
* @date 2016年10月27日 下午3:11:18 
* @Description: 系统通知角色Service
*/
public interface NoticeRoleService {
	
	/** 
	* @Title: getAllNoticeRole  
	* @return List<NoticeRole>
	* @Description: 获取所有通知角色
	*/
	List<NoticeRole> getAllNoticeRole();
	
	/** 
	* @Title: getNoticeRoleByQuery  
	* @param noticeRole
	* @return List<NoticeRole>
	* @Description: 按条件筛选通知角色，不带分页
	*/
	List<NoticeRole> getNoticeRoleByQuery(NoticeRole noticeRole);
	
	/** 
	* @Title: getNoticeRoleByPager  
	* @param query
	* @return PagerRS<NoticeRole>
	* @Description: 按条件筛选通知角色，带分页
	*/
	PagerRS<NoticeRole> getNoticeRoleByPager(Query<NoticeRole> query);
	
	/** 
	* @Title: getNoticeRoleById  
	* @param Id
	* @return NoticeRole
	* @Description: 按ID查询系统通知角色
	*/
	NoticeRole getNoticeRoleById(String Id);
	
	/** 
	* @Title: addNoticeRole  
	* @param noticeRole
	* @return void
	* @Description: 新增通知角色
	*/
	void addNoticeRole(NoticeRole noticeRole);
	
	/** 
	* @Title: updateNoticeRole  
	* @param noticeRole
	* @return void
	* @Description: 更新通知角色
	*/
	void updateNoticeRole(NoticeRole noticeRole);
}
