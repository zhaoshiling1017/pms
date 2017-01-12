package com.ducetech.pms.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ducetech.framework.dao.DepartmentDAO;
import com.ducetech.framework.dao.RoleDAO;
import com.ducetech.framework.dao.UserDAO;
import com.ducetech.framework.model.Department;
import com.ducetech.framework.model.PagerRS;
import com.ducetech.framework.model.Query;
import com.ducetech.framework.model.Role;
import com.ducetech.framework.model.User;
import com.ducetech.pms.dao.MessageDAO;
import com.ducetech.pms.model.Message;
import com.ducetech.pms.service.MessageService;
import com.ducetech.util.DateUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service
public class MessageServiceImpl implements MessageService{

	@Autowired
	private MessageDAO messageDAO;
	@Autowired
	private RoleDAO roleDAO;
	@Autowired
	private UserDAO userDAO;
	@Autowired
	private DepartmentDAO departmentDAO;
	
	@Override
	public void addMessage(Message message) {
		messageDAO.insertMessage(message);
	}

	@Override
	public void updateMessage(Message message) {
		messageDAO.updateMessage(message);
	}

	@Override
	public Message getMessageById(String msgId) {
		return messageDAO.selectMessageById(msgId);
	}

	@Override
	public void deleteMessage(Message message) {
		messageDAO.deleteMessage(message);
	}

	@Override
	public PagerRS<Message> getMessageByUserId(Query<Message> query,String userId) {
		List<Role> roles = roleDAO.selectRolesByUserId(userId);
		List<String> roleIds = new ArrayList<String>();
		if(roles!=null && roles.size()>0){
			for (Role role : roles) {
				roleIds.add(role.getRoleId());
			} 
		}
		PagerRS<Message> pagerRS = this.getMessageByRoleIds(query, roleIds);
		return pagerRS;
	}

	@Override
	public PagerRS<Message> getMessageByRoleIds(Query<Message> query,
			List<String> roleIds) {
		if(query != null && query.getPage() > 0){				//如果传入offset大于0,则启用分页查询，否则不启用
			PageHelper.startPage(query.getPage(), query.getRows(), true);
		}
		List<Message> messages = messageDAO.selectMessageByRoleIds(query.getT(), roleIds);
		for (Message message : messages) {
			message.setPublishTime(DateUtil.formatDate(message.getPublishTime()));
			User user1 = userDAO.selectUserByUserId(message.getPublisherId());
			Department dept = departmentDAO.selectDepartmentById(user1.getDepartmentId());
			user1.setDepartment(dept);
			message.setUser(user1);
		}
		@SuppressWarnings({ "rawtypes", "unchecked" })
		PageInfo page = new PageInfo(messages);
		PagerRS<Message> pagerRS = new PagerRS<Message>(messages, page.getTotal(), page.getPages());
		return pagerRS;
	}
}
