package com.ducetech.pms.dao;

import java.util.List;

import com.ducetech.pms.model.NoticeRole;

/** 
* @ClassName: NoticeRoleDAO  
* @author gaoy
* @date 2016年10月27日 下午2:40:05 
* @Description: 系统通知角色DAO
*/
public interface NoticeRoleDAO {
	
	/** 
	* @Title: selectNoticeRole  
	* @param noticeRole
	* @return List<NoticeRole>
	* @Description: 通用查询
	*/
	List<NoticeRole> selectNoticeRole(NoticeRole noticeRole); 
	
	/** 
	* @Title: selectNoticeRoleById  
	* @param Id
	* @return NoticeRole
	* @Description: 按ID查询
	*/
	NoticeRole selectNoticeRoleById(String Id);
	
	/** 
	* @Title: updateNoticeRole  
	* @param noticeRole
	* @return void
	* @Description: 更新通知角色
	*/
	void updateNoticeRole(NoticeRole noticeRole);
	
	/** 
	* @Title: insertNoticeRole  
	* @param noticeRole
	* @return void
	* @Description: 新增通知角色
	*/
	void insertNoticeRole(NoticeRole noticeRole);
}
