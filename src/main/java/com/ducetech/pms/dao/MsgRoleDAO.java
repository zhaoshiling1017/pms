package com.ducetech.pms.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ducetech.pms.model.MsgRole;

/**
 * 
* @ClassName: MsgRoleDAO 
* @Description: 集团消息与角色 
* @author yett 
* @date 2016年9月8日 上午10:54:40 
*
 */
public interface MsgRoleDAO {

	List<MsgRole> selectMsgRoles(@Param("msgRole")MsgRole msgRole,@Param("userId") String userId);
	
	List<MsgRole> selectMsgRoleByMsgId(@Param("msgId") String msgId);
	
	MsgRole selectMsgRoleById(@Param("id") String id);
	
	void addMsgRole(MsgRole msgRole);
	
	void updateMsgRole(MsgRole msgRole);
	
	void deleteMsgRole(MsgRole msgRole);
}
