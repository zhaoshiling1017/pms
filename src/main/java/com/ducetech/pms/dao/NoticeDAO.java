package com.ducetech.pms.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ducetech.pms.model.Notice;
import com.ducetech.pms.model.NoticeRole;

/** 
* @ClassName: NoticeDAO  
* @author gaoy
* @date 2016年9月6日 下午4:48:37 
* @Description: 系统通知DAO接口
*/
public interface NoticeDAO {
	
	/** 
	* @Title: selectNotice  
	* @param notice
	* @return List<Notice>
	* @Description: 通用查询
	*/
	List<Notice> selectNotice(Notice notice);
	
	/** 
	* @Title: selectNoticeById  
	* @param noticeId
	* @return Notice
	* @Description: 按ID查询系统通知
	*/
	Notice selectNoticeById(@Param("noticeId") String noticeId);
	
	/** 
	* @Title: insertNotice  
	* @param notice
	* @Description: 新增系统通知
	*/
	void insertNotice(Notice notice);
	
	/** 
	* @Title: updateNotice  
	* @param notice
	* @return void
	* @Description: 更新系统通知
	*/
	void updateNotice(Notice notice);

	/** 
	* @Title: selectNoticeByRoleIds  
	* @param roleIds
	* @return List<Notice>
	* @Description: 通过角色ID获取通知
	*/
	List<Notice> selectNoticeByRoleIds(@Param("notice") Notice notice, @Param("roleIds") List<String> roleIds);
	
	/** 
	* @Title: selectNoticeByUserId  
	* @param noticeRole
	* @return List<Notice>
	* @Description: 按人员ID获取系统通知
	*/
	List<Notice> selectNoticeByNoticeRole(@Param("nRole") NoticeRole noticeRole);
}
