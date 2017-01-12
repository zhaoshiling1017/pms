package com.ducetech.pms.service;

import java.util.List;

import com.ducetech.framework.model.PagerRS;
import com.ducetech.framework.model.Query;
import com.ducetech.pms.model.Message;
import com.ducetech.pms.model.MsgRole;

/**
 * 
* @ClassName: MsgRoleService 
* @Description: 集团消息与角色 
* @author yett 
* @date 2016年9月8日 上午9:55:56 
*
 */
public interface MsgRoleService {

	/**
	 * 
	* @Title: getProcinstByPager 
	* @Description: 分页查询人员角色消息
	* @param @param query
	* @param @return    设定文件 
	* @return PagerRS<Procinst>    返回类型 
	* @throws
	 */
	PagerRS<MsgRole> getMsgRoleByPager(Query<MsgRole> query,String userId);

	/**
	 * 根据id获得对象
	 */
	MsgRole getMsgRoleById(String id);
	
	/**
	 * 根据msgId获得对象
	 */
	List<MsgRole> getMsgRoleByMsgId(String msgId);
	
	
	/**
	 * 获得所有人员角色消息
	 */
	List<MsgRole> getMsgRoles(MsgRole msgRole,String userId);
	
	/**
	 * 保存人员对应消息
	 */
	void addMsgRole(MsgRole msgRole);
	
	/**
	 * 根据角色异步新增人员消息
	 */
	void getMsgRole(List<String> list,Message msg);
	
	/**
	 * 更新以读未读状态
	 */
	void updateMsgRole(MsgRole msgRole);
	
	/**
	 * 删除信息
	 */
	void deleteMsgRole(MsgRole msgRole);
}
