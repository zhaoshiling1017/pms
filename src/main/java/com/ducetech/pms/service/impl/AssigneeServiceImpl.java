package com.ducetech.pms.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ducetech.constant.Constant;
import com.ducetech.framework.dao.RoleDAO;
import com.ducetech.framework.dao.UserDAO;
import com.ducetech.framework.model.Role;
import com.ducetech.framework.model.User;
import com.ducetech.pms.dao.AssigneeDAO;
import com.ducetech.pms.dao.NoticeDAO;
import com.ducetech.pms.dao.NoticeRoleDAO;
import com.ducetech.pms.dao.ProcinstDAO;
import com.ducetech.pms.dao.TaskDAO;
import com.ducetech.pms.dao.TaskLogDAO;
import com.ducetech.pms.model.Assignee;
import com.ducetech.pms.model.Notice;
import com.ducetech.pms.model.NoticeRole;
import com.ducetech.pms.model.Procinst;
import com.ducetech.pms.model.Task;
import com.ducetech.pms.model.TaskLog;
import com.ducetech.pms.service.AssigneeService;
import com.ducetech.redis.MyRedisTemplate;
import com.ducetech.util.CachePool;
import com.ducetech.util.DateUtil;
@Service
public class AssigneeServiceImpl implements AssigneeService{

	@Autowired
	private AssigneeDAO assigneeDAO;
	@Autowired
	private TaskDAO taskDAO;
	@Autowired
	private RoleDAO roleDAO;
	@Autowired
	private UserDAO userDAO;
	@Autowired
	private TaskLogDAO taskLogDAO;
	@Autowired
	private ProcinstDAO procinstDAO;
	@Autowired
	private MyRedisTemplate myRedisTemplate;
	@Autowired
	private NoticeDAO noticeDAO;
	@Autowired
	private NoticeRoleDAO noticeRoleDAO;
	
	@Transactional
	@Override
	public void addAssignee(User user,Procinst procinst, String xmbIds, String bsIds) {
		Set<String> roleIds = new HashSet<String>();
		List<String> xmbList = new ArrayList<String>();
		String xmb[] = xmbIds.split(",");  
		for(int i=0;i<xmb.length;i++){
			xmbList.add(xmb[i]);
		}
		List<String> bsList = new ArrayList<String>();
		String bs[] = bsIds.split(",");  
		for(int i=0;i<bs.length;i++){
			bsList.add(bs[i]);
		}
		Task task = null;
		Assignee assignee=null;
		//流转记录
		TaskLog taskLog = null;
		taskLog = new TaskLog();
//		taskLog.setTaskId(task.getTaskId());  								//工单ID
		taskLog.setNodeName(userDAO.selectUserByUserId(procinst.getStartPersonId()).getName()+"下发");  //节点名称
		taskLog.setPerson(userDAO.selectUserByUserId(procinst.getStartPersonId()).getName()); //处理人
		taskLog.setNodeTime(DateUtil.dateTimeToString(new Date()));  		//时间
		taskLog.setProcinstId(procinst.getProcinstId());					//实例Id
		taskLog.setTaskId("-1");
		taskLog.setNodeAction("下发");   										//节点动作
		taskLogDAO.insertTaskLog(taskLog);
		//
		for(int i=0;i<xmbList.size();i++){
			roleIds.add(xmbList.get(i));
			//根据角色创建工单
			task = new Task();
			task.setTaskCode(procinst.getProcinstCode()+"000"+(i+1));
			task.setStartTime(DateUtil.dateTimeToString(new Date()));   	//开始时间
			task.setProcinstId(procinst.getProcinstId());   				//流程实例ID
			task.setProcinstCode(procinst.getProcinstCode());  				//流程实例编号
			task.setProcType(Constant.TASK_TYPE_TEMP);   					//临时流程
			task.setStatus(Constant.TASK_UNCOMPLETE);     					//未完成
			task.setRoleId(xmbList.get(i));    								//执行部门角色
			task.setStartPersonId(user.getUserId());   						//发起人
			task.setStartPersonRoleId(procinst.getStartPersonRoleId());     //发起人角色ID		
			task.setStartDeptId(user.getDepartmentId()); 					//发起人部门
			task.setIsBack(Constant.TASK_IS_UNBACK); 						//是否退回
			taskDAO.insertTask(task);
			//消息通知
			Notice notice = new Notice();
			notice.setProcinstId(procinst.getProcinstId());
			notice.setNoticeType(Constant.NOTICE_NOTICETYPE_DISPOSE);
			notice.setCreateById(user.getUserId());
			notice.setSendTime(DateUtil.dateTimeToString(new Date()));
			notice.setTaskId(task.getTaskId());
			notice.setRoleId(task.getRoleId());
			noticeDAO.insertNotice(notice);
			List<User> users = userDAO.selectUsersByRoleId(notice.getRoleId());
			for (User u : users) {
				NoticeRole noticeRole = new NoticeRole();
				noticeRole.setUserId(u.getUserId());
				noticeRole.setNoticeId(notice.getNoticeId());
				noticeRole.setRoleId(notice.getRoleId());
				noticeRole.setReceiveTime(notice.getSendTime());
				noticeRoleDAO.insertNoticeRole(noticeRole);
			}
			for(int j=0;j<bsList.size();j++){
				//创建部室受理单子
				assignee = new Assignee();
				assignee.setTaskId(task.getTaskId());  						//工单ID
				assignee.setUserId(bsList.get(j));  					    //建议部室
				assignee.setResult(Constant.TASK_UNCOMPLETE);   		    //0未处理  1 已处理 2作废
				assigneeDAO.insertAssignee(assignee);
			}
		}
		sendMessage(roleIds);
		myRedisTemplate.sendMessage(task.getStartPersonId(), Constant.NOTICE_TYPE_PROC_SUCCESS);
	}

	/**
	 * 
	* @Title: sendMessage 
	* @Description: 推送消息
	* @param @param roleIds    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	
	@Async
	public void sendMessage(Set<String> roleIds) {
		CachePool cachePool = CachePool.getInstance();
		for (String role : roleIds) {
			List<User> users = cachePool.getCacheItem(role);
			for (User user : users) {
				myRedisTemplate.sendMessage(user.getUserId(), Constant.NOTICE_TYPE_PROC);
			}
		}
	}
	
	@Override
	public void updateAssignee(Assignee assignee) {
		assigneeDAO.updateAssignee(assignee);
	}

	@Override
	public List<Assignee> getAssigneeByResult(Assignee assignee) {
		return assigneeDAO.selectAssigneeByResult(assignee);
	}

	@Override
	public List<Assignee> selectAssigneeByRoleIds(Assignee assignee,User user) {
		List<Role> roles = roleDAO.selectRolesByUserId(user.getUserId());
		List<String> roleIds = new ArrayList<String>();
		if(roles!=null && roles.size()>0){
			for (Role role : roles) {
				roleIds.add(role.getRoleId());
			} 
		}
		return assigneeDAO.selectAssigneeByRoleIds(assignee, roleIds);
	}

	@Override
	public List<Assignee> getAssigneeByTaskId(String taskId) {
		return assigneeDAO.selectAssigneeByTaskId(taskId);
	}

	@Override
	public Assignee getAssigneeById(String id) {
		return assigneeDAO.selectAssigneeById(id);
	}	


	
}
