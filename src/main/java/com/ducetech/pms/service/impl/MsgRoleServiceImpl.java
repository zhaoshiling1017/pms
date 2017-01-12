package com.ducetech.pms.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ducetech.constant.Constant;
import com.ducetech.framework.model.PagerRS;
import com.ducetech.framework.model.Query;
import com.ducetech.framework.service.UserService;
import com.ducetech.pms.dao.MessageDAO;
import com.ducetech.pms.dao.MsgRoleDAO;
import com.ducetech.pms.model.Message;
import com.ducetech.pms.model.MsgRole;
import com.ducetech.pms.service.MsgRoleService;
import com.ducetech.redis.MyRedisTemplate;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service
public class MsgRoleServiceImpl implements MsgRoleService{

	@Autowired
	private UserService userService;
	@Autowired
	private MsgRoleDAO msgRoleDAO;
	@Autowired
	private MessageDAO messageDAO;
	@Autowired
	private MyRedisTemplate myRedisTemplate;
	/**
	 * 根据角色异步新增人员消息
	 */
	@Async
	public void getMsgRole(List<String> list, Message msg) {
		saveMsgRole(list, msg);
		if(!Constant.MSG_SEND_SUCCESS.equals(messageDAO.selectMessageById(msg.getMsgId()).getStatus())){
			msg.setStatus(Constant.MSG_SEND_LOSE);
			messageDAO.updateMessage(msg);
		}else{
			sendMessage(list);
		}
		myRedisTemplate.sendMessage(msg.getPublisherId(), Constant.NOTICE_TYPE_INFO_SUCCESS);
	}

	private void sendMessage(List<String> list) {
		Set<String> users = new HashSet<String>();
		for(int i=0;i<list.size();i++){
			if(userService.getUsersByRoleId(list.get(i)).size()>0){
				for (int j=0;j<userService.getUsersByRoleId(list.get(i)).size();j++) {
					users.add(userService.getUsersByRoleId(list.get(i)).get(j).getUserId());
				} 
			}
		}
		for (String userId:users) {
			myRedisTemplate.sendMessage(userId, Constant.NOTICE_TYPE_INFO);
		}
	}
	
	
	/**
	 * 保存人员对应消息
	 */
	@Override
	public void addMsgRole(MsgRole msgRole) {
		msgRoleDAO.addMsgRole(msgRole);
	}
	
	/**
	 * 
	* @Title: saveMsgRole 
	* @Description: 遍历添加 
	* @param @param list
	* @param @param msg    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	@Transactional
	public void saveMsgRole(List<String> list, Message msg){
		Set<String> users = new HashSet<String>();
		for(int i=0;i<list.size();i++){
			if(userService.getUsersByRoleId(list.get(i)).size()>0){
				for (int j=0;j<userService.getUsersByRoleId(list.get(i)).size();j++) {
					users.add(userService.getUsersByRoleId(list.get(i)).get(j).getUserId());
				} 
			}
		}
		for (String userId:users) {
			MsgRole msgRole = new MsgRole();
			msgRole.setMsgId(msg.getMsgId());
			msgRole.setUserId(userId);
			msgRole.setStatus(Constant.MSG_ROLE_UNREAD);
			msgRole.setUserName(userService.getUserByUserId(userId).getName());
			addMsgRole(msgRole);
		}
		msg.setStatus(Constant.MSG_SEND_SUCCESS);
		messageDAO.updateMessage(msg);
	}

	/**
	 * 获得所有人员角色消息
	 */
	@Override
	public List<MsgRole> getMsgRoles(MsgRole msgRole,String userId) {
		List<MsgRole> msgRoles = msgRoleDAO.selectMsgRoles(msgRole,userId);
		return msgRoles;
	}

	/**
	 * 
	* @Title: getProcinstByPager 
	* @Description: 分页查询人员角色消息
	* @param @param query
	* @param @return    设定文件 
	* @return PagerRS<Procinst>    返回类型 
	* @throws
	 */
	@Override
	public PagerRS<MsgRole> getMsgRoleByPager(Query<MsgRole> query,String userId) {
		if(query != null && query.getPage() > 0){				//如果传入offset大于0,则启用分页查询，否则不启用
			PageHelper.startPage(query.getPage(), query.getRows(), true);
		}
		List<MsgRole> msgRoles = msgRoleDAO.selectMsgRoles(query.getT(),userId);
		@SuppressWarnings({ "rawtypes", "unchecked" })
		PageInfo page = new PageInfo(msgRoles);
		PagerRS<MsgRole> pagerRS = new PagerRS<MsgRole>(msgRoles, page.getTotal(), page.getPages());
		return pagerRS;
	}

	/**
	 * 更新以读未读状态
	 */
	@Override
	public void updateMsgRole(MsgRole msgRole) {
		msgRoleDAO.updateMsgRole(msgRole);
	}

	/**
	 * 根据id获得对象
	 */
	@Override
	public MsgRole getMsgRoleById(String id) {
		return msgRoleDAO.selectMsgRoleById(id);
	}

	/**
	 * 删除信息
	 */
	@Override
	public void deleteMsgRole(MsgRole msgRole) {
		msgRoleDAO.deleteMsgRole(msgRole);
	}

	/**
	 * 根据msgId获得对象
	 */
	@Override
	public List<MsgRole> getMsgRoleByMsgId(String msgId) {
		return msgRoleDAO.selectMsgRoleByMsgId(msgId);
	}
}
