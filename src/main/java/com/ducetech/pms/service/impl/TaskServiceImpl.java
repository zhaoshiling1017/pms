package com.ducetech.pms.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ducetech.constant.Constant;
import com.ducetech.framework.dao.RoleDAO;
import com.ducetech.framework.dao.UserDAO;
import com.ducetech.framework.model.PagerRS;
import com.ducetech.framework.model.Query;
import com.ducetech.framework.model.Role;
import com.ducetech.framework.model.User;
import com.ducetech.pms.dao.AssigneeDAO;
import com.ducetech.pms.dao.NoticeDAO;
import com.ducetech.pms.dao.NoticeRoleDAO;
import com.ducetech.pms.dao.ProcFileDAO;
import com.ducetech.pms.dao.ProcNodeDAO;
import com.ducetech.pms.dao.ProcinstDAO;
import com.ducetech.pms.dao.TaskDAO;
import com.ducetech.pms.model.Assignee;
import com.ducetech.pms.model.Notice;
import com.ducetech.pms.model.NoticeRole;
import com.ducetech.pms.model.ProcNode;
import com.ducetech.pms.model.Procinst;
import com.ducetech.pms.model.ProcinstFile;
import com.ducetech.pms.model.Task;
import com.ducetech.pms.service.TaskService;
import com.ducetech.redis.MyRedisTemplate;
import com.ducetech.util.CachePool;
import com.ducetech.util.DateUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/** 
* @ClassName: TaskServiceImpl  
* @author gaoy
* @date 2016年9月6日 下午4:28:32 
* @Description: 工单Service实现类
*/
@Service
public class TaskServiceImpl implements TaskService {
	
	@Autowired
	private TaskDAO taskDAO;
	
	@Autowired
	private RoleDAO roleDAO;
	
	@Autowired
	private UserDAO userDAO;
	
	@Autowired
	private ProcinstDAO procinstDAO;
	
	@Autowired
	private ProcNodeDAO procNodeDAO;
	
	@Autowired
	private AssigneeDAO assigneeDAO;
	
	@Autowired
	private ProcFileDAO procFileDAO;
	
	@Autowired
	private MyRedisTemplate myRedisTemplate;
	
	@Autowired
	private NoticeDAO noticeDAO;
	
	@Autowired
	private NoticeRoleDAO noticeRoleDAO;
	
	@Override
	public List<Task> getAllTask() {
		return this.getTaskByPager(new Query<Task>(new Task())).getResults();
	}

	@Override
	public List<Task> getTaskByQuery(Task task) {
	    List<Task> tasks = taskDAO.selectTask(task);
		for (Task tk : tasks) {
			if(StringUtils.isNotEmpty(tk.getDisposeTime())){
				tk.setDisposeTime(DateUtil.formatDate(tk.getDisposeTime()));
			}
			if(StringUtils.isNotEmpty(tk.getDisposePersionId())){
				User user = userDAO.selectUserByUserId(tk.getDisposePersionId());
				if(user!=null){
					tk.setDisposePersion(user);
				}
			}
			if(StringUtils.isNotEmpty(tk.getUpTaskId())){
				Task upTask = this.getTaskByTaskId(tk.getUpTaskId());
				if(upTask!=null){
					tk.setUpTask(upTask);
				}
			}
			if(StringUtils.isNotEmpty(tk.getRoleId())){
				Role role = roleDAO.selectRoleById(tk.getRoleId());
				if(role!=null){
					tk.setRole(role);
				}
			}
		}
		return tasks;
	}
	
	@Override
	public List<Task> getTaskByQueryASC(Task task) {
	    List<Task> tasks = taskDAO.selectTaskASC(task);
		for (Task tk : tasks) {
			if(StringUtils.isNotEmpty(tk.getDisposeTime())){
				tk.setDisposeTime(DateUtil.formatDate(tk.getDisposeTime()));
			}
			if(StringUtils.isNotEmpty(tk.getDisposePersionId())){
				User user = userDAO.selectUserByUserId(tk.getDisposePersionId());
				if(user!=null){
					tk.setDisposePersion(user);
				}
			}
			if(StringUtils.isNotEmpty(tk.getUpTaskId())){
				Task upTask = this.getTaskByTaskId(tk.getUpTaskId());
				if(upTask!=null){
					tk.setUpTask(upTask);
				}
			}
			if(StringUtils.isNotEmpty(tk.getRoleId())){
				Role role = roleDAO.selectRoleById(tk.getRoleId());
				if(role!=null){
					tk.setRole(role);
				}
			}
		}
		return tasks;
	}

	@Override
	public PagerRS<Task> getTaskByPager(Query<Task> query) {
		if(query != null && query.getPage() > 0){				//如果传入offset大于0,则启用分页查询，否则不启用
			PageHelper.startPage(query.getPage(), query.getRows(), true);
		}
		List<Task> tasks = taskDAO.selectTask(query.getT());
		@SuppressWarnings({ "rawtypes", "unchecked" })
		PageInfo page = new PageInfo(tasks);
		PagerRS<Task> pagerRS = new PagerRS<Task>(tasks, page.getTotal(), page.getPages());
		return pagerRS;
	}

	@Override
	public Task getTaskByTaskId(String taskId) {
		Task task = taskDAO.selectTaskByTaskId(taskId);
		if(task!=null){
			if(StringUtils.isNotEmpty(task.getDisposePersionId())){
				User user = userDAO.selectUserByUserId(task.getDisposePersionId());
				if(user!=null){
					task.setDisposePersion(user);
				}
			}
			if(StringUtils.isNotEmpty(task.getDisposeTime())){
				task.setDisposeTime(DateUtil.formatDate(task.getDisposeTime()));
			}
			if(StringUtils.isNotEmpty(task.getRoleId())){
				Role role = roleDAO.selectRoleById(task.getRoleId());
				if(role!=null){
					task.setRole(role);
				}
			}
			if(StringUtils.isNotEmpty(task.getBackTime())){
				task.setBackTime(DateUtil.formatDate(task.getBackTime()));
			}
			if(StringUtils.isNotEmpty(task.getBackPersionId())){
				User backUser = userDAO.selectUserByUserId(task.getBackPersionId());
				task.setBackPersion(backUser);
			}
			if(StringUtils.isNotEmpty(task.getApproveTaskId())){
				Task approve = this.getTaskByTaskId(task.getApproveTaskId());
				if(approve!=null){
					task.setApproveTask(approve);
				}
			}
		}
		return task;
	}

	@Override
	public void addTask(Task task) {
		taskDAO.insertTask(task);
	}
	
	@Transactional
	@Override
	public void updateTask(Task task,int type) {
		boolean flg=false;
		CachePool cachePool = CachePool.getInstance();
		switch (type) {
			case 0:   //处理
				taskDAO.updateTask(task);
				List<Assignee> assignees = assigneeDAO.selectAssigneeByTaskId(task.getTaskId());
				for (Assignee assignee : assignees) {
					List<User> users = cachePool.getCacheItem(assignee.getUserId());
					for (User user : users) {
						myRedisTemplate.sendMessage(user.getUserId(), Constant.NOTICE_TYPE_PROC);
					}
				}
				break; 
			case 1:    //确认通过
				if(task.getProcType().equals(Constant.TASK_TYPE_TEMP)){
					task.setIsBack(Constant.TASK_IS_BACK_TWO);
					task.setEndTime(DateUtil.dateTimeToString(new Date()));
				}
				taskDAO.updateTask(task);
				Task t = new Task();
				t.setProcinstId(task.getProcinstId());
				List<Task> tasks = taskDAO.selectTask(t);
				for (Task task2 : tasks) {
					if (task2.getIsBack().equals(Constant.TASK_IS_BACK_TWO)) {
						flg=true;
					}else if(task2.getIsBack().equals(Constant.TASK_IS_BACK)){
						flg=true;
					}else{
						flg=false;
						break;
					}
				}
				if(flg){
					Procinst procinst = procinstDAO.selectProcinstByprocinstId(task.getProcinstId());
					procinst.setStatus(Constant.PROCINST_COMPLETE);
					procinst.setEndTime(DateUtil.dateTimeToString(new Date()));
					procinstDAO.updateProcinst(procinst);
				}
				break;
			case 2:
				taskDAO.updateTask(task);
				//推送消息
				Procinst procinst = procinstDAO.selectProcinstByprocinstId(task.getProcinstId());
				List<User> users = cachePool.getCacheItem(procinst.getStartPersonRoleId());
				for (User user : users) {
					myRedisTemplate.sendMessage(user.getUserId(), "NOTICE_TYPE_PROC");
				}
				break;
		}
		/*if(type==1){    //确认通过
			if(task.getProcType().equals(Constant.TASK_TYPE_TEMP)){
				task.setIsBack(Constant.TASK_IS_BACK_TWO);
				task.setEndTime(DateUtil.dateTimeToString(new Date()));
			}
			taskDAO.updateTask(task);
			Task t = new Task();
			System.out.println(task.getProcinstId());
			t.setProcinstId(task.getProcinstId());
			List<Task> tasks = taskDAO.selectTask(t);
			System.out.println(tasks.size());
			for (Task task2 : tasks) {
				if (task2.getIsBack().equals(Constant.TASK_IS_BACK_TWO)) {
					flg=true;
				}else if(task2.getIsBack().equals(Constant.TASK_IS_BACK)){
					flg=true;
				}else{
					flg=false;
					break;
				}
			}
			if(flg){
				Procinst procinst = procinstDAO.selectProcinstByprocinstId(task.getProcinstId());
				procinst.setStatus(Constant.PROCINST_COMPLETE);
				procinst.setEndTime(DateUtil.dateTimeToString(new Date()));
				procinstDAO.updateProcinst(procinst);
				//推送消息
				CachePool cachePool = CachePool.getInstance();
				List<User> users = cachePool.getCacheItem(procinst.getStartPersonRoleId());
				for (User user : users) {
					myRedisTemplate.sendMessage(user.getUserId(), "NOTICE_TYPE_PROC");
				}
			}
		}else if(type==0){  //处理
			taskDAO.updateTask(task);
			List<Assignee> assignees = assigneeDAO.selectAssigneeByTaskId(task.getTaskId());
			CachePool cachePool = CachePool.getInstance();
			for (Assignee assignee : assignees) {
				List<User> users = cachePool.getCacheItem(assignee.getUserId());
				for (User user : users) {
					myRedisTemplate.sendMessage(user.getUserId(), Constant.NOTICE_TYPE_PROC);
				}
			}
		}else if(type==2){
			taskDAO.updateTask(task);
			//推送消息
			Procinst procinst = procinstDAO.selectProcinstByprocinstId(task.getProcinstId());
			CachePool cachePool = CachePool.getInstance();
			List<User> users = cachePool.getCacheItem(procinst.getStartPersonRoleId());
			for (User user : users) {
				myRedisTemplate.sendMessage(user.getUserId(), "NOTICE_TYPE_PROC");
			}
		}*/
	}

	@Override
	public PagerRS<Task> getTasksByUserId(Query<Task> query, String userId) {
		List<Role> roles = roleDAO.selectRolesByUserId(userId);
		List<String> roleIds = new ArrayList<String>();
		if(roles!=null && roles.size()>0){
			for (Role role : roles) {
				roleIds.add(role.getRoleId());
			} 
		}
		PagerRS<Task> pagerRS = this.getTaskByRoleIds(query, roleIds);
		return pagerRS;
	}

	@Override
	public PagerRS<Task> getTaskByRoleIds(Query<Task> query, List<String> roleIds) {
		if(query != null && query.getPage() > 0){				//如果传入offset大于0,则启用分页查询，否则不启用
			PageHelper.startPage(query.getPage(), query.getRows(), true);
		}
		List<Task> tasks = taskDAO.selectTasksByRoleIds(query.getT(), roleIds);
		for (Task rs : tasks) {
			if (StringUtils.isNotEmpty(rs.getUpTaskId())) {
				Task task = taskDAO.selectTaskByTaskId(rs.getUpTaskId());
				rs.setIsBack(task.getIsBack());
			}
			User startUser = userDAO.selectUserByUserId(rs.getStartPersonId());
			if(startUser!=null){
				rs.setStartPerson(startUser);
			}
			Procinst procinst = procinstDAO.selectProcinstByprocinstId(rs.getProcinstId());
			if(procinst!=null){
				rs.setProcinst(procinst);
			}
			User disposeUser = userDAO.selectUserByUserId(rs.getDisposePersionId());
			if(StringUtils.isNotEmpty(rs.getDisposeTime())){
				rs.setDisposeTime(DateUtil.formatDate(rs.getDisposeTime()));
			}
			if(disposeUser!=null){
				rs.setDisposePersion(disposeUser);
			}
			ProcNode upNode = procNodeDAO.selectProcNodeByNodeId(rs.getUpNodeId());
			if(upNode!=null){
				rs.setUpNode(upNode);
			}
			//获取上一工单信息
			if(StringUtils.isNotEmpty(rs.getUpTaskId())){
				Task upTask = taskDAO.selectTaskByTaskId(rs.getUpTaskId());
				if(upTask!=null){
					Role upRole = roleDAO.selectRoleById(upTask.getRoleId());
					upTask.setRole(upRole);
					rs.setUpTask(upTask);
				}
			}
			if(StringUtils.isNotEmpty(rs.getRoleId())){
				Role role = roleDAO.selectRoleById(rs.getRoleId());
				rs.setRole(role);
			}
		}
		@SuppressWarnings({ "rawtypes", "unchecked" })
		PageInfo page = new PageInfo(tasks);
		PagerRS<Task> pagerRS = new PagerRS<Task>(tasks, page.getTotal(), page.getPages());
		return pagerRS;
	}

	@Override
	@Transactional
	public void createTask(Task task, List<ProcNode> procNodes) {
		//通过节点ID，获取对应角色
		for (ProcNode procNode : procNodes) {
			task.setTaskCode("TASK20160914001");
			task.setNextNodeId(procNode.getNextNodeId());
			task.setNodeId(procNode.getNodeId());
			task.setNodeName(procNode.getNodeName());
			task.setNodeType(procNode.getNodeType());
			task.setIsBack(Constant.TASK_IS_UNBACK);
			List<Role> roles = roleDAO.selectRolesByNodeId(procNode.getNodeId());	//每个角色生成一条
			Set<String> roleIds = new HashSet<String>();
			Notice notice = new Notice();
			notice.setProcinstId(task.getProcinstId());
			notice.setNoticeType(Constant.NOTICE_NOTICETYPE_DISPOSE);
			notice.setSendTime(DateUtil.dateTimeToString(new Date()));
			notice.setCreateById(task.getStartPersonId());
			notice.setCreateAt(DateUtil.dateTimeToString(new Date()));
			for(Role role : roles){
				task.setRoleId(role.getRoleId()); 
				taskDAO.insertTask(task);
				roleIds.add(role.getRoleId());
				notice.setTaskId(task.getTaskId());
				notice.setRoleId(role.getRoleId());
				noticeDAO.insertNotice(notice);
				List<User> users = userDAO.selectUsersByRoleId(role.getRoleId());
				NoticeRole noticeRole = new NoticeRole();	//存入通知角色中
				noticeRole.setRoleId(role.getRoleId());
				noticeRole.setNoticeId(notice.getNoticeId());
				for(User u : users){
					noticeRole.setUserId(u.getUserId());
					noticeRoleDAO.insertNoticeRole(noticeRole);
				}
			}
			sendMessage(roleIds);
			myRedisTemplate.sendMessage(task.getStartPersonId(), Constant.NOTICE_TYPE_PROC_SUCCESS);
		} 
	}
	
	@Async
	public void sendMessage(Set<String> roleIds) {
		CachePool cachePool = CachePool.getInstance();
		Role r = new Role();
		r.setIsDeleted(Constant.NOT_DELETE);
		List<Role> roles = roleDAO.selectRole(r);
		for (Role role : roles) {
			List<User> users = userDAO.selectUsersByRoleId(role.getRoleId());
			cachePool.putCacheItem(role.getRoleId(), users);
		}
		for (String role : roleIds) {
			List<User> users = cachePool.getCacheItem(role);
			for (User user : users) {
				myRedisTemplate.sendMessage(user.getUserId(), Constant.NOTICE_TYPE_PROC);
			}
		}
	}
	
	@Override
	@Transactional
	public void assignTask(Task task) {
		Task ta = taskDAO.selectTaskByTaskId(task.getTaskId());
		if(StringUtils.isEmpty(ta.getNextNodeId())||Constant.NODE_END.equals(ta.getNextNodeId())){	//下一节点是空，下一节点是-1，则是指向结束
			taskDAO.updateTask(task); 		//更新工单状态为0，已完成
			Task ts = new Task();
			ts.setStatus(Constant.TASK_UNCOMPLETE);
			ts.setProcinstId(task.getProcinstId());
			List<Task> tasks = taskDAO.selectTask(ts);
			if(tasks!=null && tasks.size()<1){				//判断如果取出的工单没有未完成的，那么更新流程实例为已完成
				Procinst procinst = new Procinst();	
				procinst.setProcinstId(ta.getProcinstId());
				procinst.setStatus(Constant.PROCINST_COMPLETE);
				procinst.setEndTime(DateUtil.dateTimeToString(new Date()));
				procinstDAO.updateProcinst(procinst);
			}
		}else{													//正常流程
			taskDAO.updateTask(task); //更新工单状态为0，已完成
			Procinst procinst = procinstDAO.selectProcinstByprocinstId(ta.getProcinstId());
			User user = userDAO.selectUserByUserId(procinst.getStartPersonId());
			task.setProcinstId(procinst.getProcinstId());
			task.setProcinstCode(procinst.getProcinstCode());
			task.setProcType(procinst.getProcType());
			task.setStartTime(DateUtil.dateTimeToString(new Date()));
			task.setStartPersonId(user.getUserId());
			task.setStartDeptId(user.getDepartmentId());
			task.setUpNodeId(ta.getNodeId());
			task.setUpTaskId(task.getTaskId());
			task.setIsBack(Constant.TASK_IS_UNBACK);
			for(Task tk : task.getTasks()){			//多个工单也就是节点
				ProcNode procNode = procNodeDAO.selectProcNodeByNodeId(tk.getNodeId());
				task.setNodeId(procNode.getNodeId());
				task.setComment(tk.getComment());
				task.setTimeLimit(tk.getTimeLimit()); 
				task.setTaskCode("TASK20160914001");
				task.setNextNodeId(procNode.getNextNodeId());
				task.setNodeId(procNode.getNodeId());
				task.setNodeName(procNode.getNodeName());
				task.setNodeType(procNode.getNodeType());
				List<String> roleIds =new ArrayList<>();
				if(StringUtils.isNotEmpty(tk.getRoleId())){
					String[] ids = tk.getRoleId().split(",");
					Collections.addAll(roleIds, ids);
				}
				Set<String> setRoleIds = new HashSet<String>();
				Notice notice = new Notice();					//通知
				notice.setProcinstId(ta.getProcinstId());
				notice.setNoticeType(Constant.NOTICE_NOTICETYPE_DISPOSE);
				notice.setCreateById(task.getDisposePersionId());
				notice.setSendTime(DateUtil.dateTimeToString(new Date()));
				notice.setCreateAt(DateUtil.dateTimeToString(new Date()));
				//每个角色生成一条
				if(roleIds!=null && roleIds.size()>0){
					for(String roleId : roleIds){
						task.setRoleId(roleId); 
						taskDAO.insertTask(task);
						setRoleIds.add(roleId);
						String uuid = task.getFile();
						if(myRedisTemplate.getItem(uuid)!=null){
							@SuppressWarnings("unchecked")
							List<Map<String, String>> lists = (List<Map<String, String>>) myRedisTemplate.getItem(uuid);
							ProcinstFile procFile = new ProcinstFile();
							for (Map<String, String> map : lists) {
								Set<String> keys = map.keySet();
								for (String key : keys) {
									procFile.setPath(map.get(key));
									procFile.setFileName(key);
								}
								procFile.setProcinstId(task.getProcinstId());
								procFile.setTaskId(task.getTaskId());
								procFile.setProcType(task.getProcType());
								procFile.setNodeId(task.getNodeId());
								procFile.setUploadTime(DateUtil.dateTimeToString(new Date()));
								procFile.setCreateById(task.getDisposePersionId());
								procFile.setUploadNodeId(ta.getNodeId());
								procFile.setUploadTaskId(ta.getTaskId());
								procFile.setUploadRoleId(ta.getRoleId());
								procFileDAO.insertProcFile(procFile);
							}
						}
						/*if(StringUtils.isNotEmpty(file)&&file.length()>2){
							file = file.replace("[", "");
							file = file.replace("]", "");
							file = file.replace("{", "");
							file = file.replace("}", "");
							file = file.substring(1, file.length()-1);
							String files[] = file.split("\",\"");
							for (String string : files) {
								String path[] = string.split(",");
								if(path.length>0){
									ProcinstFile procFile = new ProcinstFile();
									procFile.setProcinstId(task.getProcinstId());
									procFile.setTaskId(task.getTaskId());
									procFile.setProcType(task.getProcType());
									procFile.setNodeId(task.getNodeId());
									procFile.setUploadTime(DateUtil.dateTimeToString(new Date()));
									procFile.setPath(path[0]);
									procFile.setFileName(path[1]);
									procFile.setCreateById(task.getDisposePersionId());
									procFile.setUploadNodeId(ta.getNodeId());
									procFile.setUploadTaskId(ta.getTaskId());
									procFile.setUploadRoleId(ta.getRoleId());
									procFileDAO.insertProcFile(procFile);
								}
							}
						}*/
						notice.setTaskId(task.getTaskId());
						notice.setRoleId(roleId);
						noticeDAO.insertNotice(notice);
						NoticeRole noticeRole = new NoticeRole();	//存入通知角色中
						noticeRole.setRoleId(roleId);
						noticeRole.setNoticeId(notice.getNoticeId());
						List<User> users = userDAO.selectUsersByRoleId(roleId);
						for(User u : users){
							noticeRole.setUserId(u.getUserId());
							noticeRoleDAO.insertNoticeRole(noticeRole);
						}
					}
					sendMessage(setRoleIds);
					myRedisTemplate.sendMessage(task.getDisposePersionId(), Constant.NOTICE_TYPE_PROC_SUCCESS);
				}
			}
		}
	}

	@Override
	@Transactional
	public void returnTask(Task task) {
		//更新所处理工单
		Task oldTask = new Task();
		oldTask.setTaskId(task.getUpTaskId());
		oldTask.setStatus(Constant.TASK_NULLIFY);
		oldTask.setIsBack(Constant.TASK_IS_BACK);
		oldTask.setBackPersionId(task.getBackPersionId());
		oldTask.setBackTime(DateUtil.dateTimeToString(new Date()));
		oldTask.setBackComment(task.getBackComment());
		taskDAO.updateTask(oldTask);
		//更新现在工单
		Task nowTask = new Task();
		nowTask.setTaskId(task.getTaskId());
		nowTask.setStatus(Constant.TASK_COMPLETE);
		nowTask.setDisposePersionId(task.getDisposePersionId());
		nowTask.setDisposeTime(DateUtil.dateTimeToString(new Date()));
		nowTask.setIsBack(Constant.TASK_IS_BACK);
		nowTask.setBackPersionId(task.getBackPersionId());
		nowTask.setBackTime(DateUtil.dateTimeToString(new Date()));
		nowTask.setEndTime(DateUtil.dateTimeToString(new Date()));
		nowTask.setBackComment(task.getBackComment());
		taskDAO.updateTask(nowTask);
		//生成新工单
		Task newTask = new Task();
		Task tk = taskDAO.selectTaskByTaskId(task.getUpTaskId());
		newTask.setTaskCode("重新生成的编码");
		newTask.setProcinstId(tk.getProcinstId());
		newTask.setProcinstCode(tk.getProcinstCode());
		newTask.setProcType(tk.getProcType());
		newTask.setUpNodeId(tk.getUpNodeId());
		newTask.setNextNodeId(tk.getNextNodeId());
		newTask.setNodeId(tk.getNodeId());
		newTask.setNodeName(tk.getNodeName());
		newTask.setNodeType(tk.getNodeType());
		newTask.setTimeLimit(tk.getTimeLimit());
		newTask.setComment(tk.getComment());
		newTask.setStartTime(DateUtil.dateTimeToString(new Date()));
		newTask.setStartPersonId(tk.getStartPersonId());
		newTask.setStartDeptId(tk.getStartDeptId());
		newTask.setRoleId(tk.getRoleId());
		if(task.getNodeType().equals(Constant.NODE_TYPE_APPROVE)){
			newTask.setUpTaskId(task.getTaskId());
		}else{
			newTask.setUpTaskId(tk.getUpTaskId());
		}
		newTask.setApproveTaskId(task.getTaskId());
		newTask.setIsBack(Constant.TASK_IS_UNBACK);
		taskDAO.insertTask(newTask);
		Notice notice = new Notice();					//通知
		notice.setProcinstId(newTask.getProcinstId());
		notice.setNoticeType(Constant.NOTICE_NOTICETYPE_DISPOSE);
		notice.setCreateById(task.getBackPersionId());
		notice.setSendTime(DateUtil.dateTimeToString(new Date()));
		notice.setCreateAt(DateUtil.dateTimeToString(new Date()));
		notice.setTaskId(newTask.getTaskId());
		notice.setRoleId(newTask.getRoleId());
		noticeDAO.insertNotice(notice);
		NoticeRole noticeRole = new NoticeRole();	//存入通知角色中
		noticeRole.setRoleId(newTask.getRoleId());
		noticeRole.setNoticeId(notice.getNoticeId());
		List<User> users = userDAO.selectUsersByRoleId(newTask.getRoleId());
		for(User u : users){
			noticeRole.setUserId(u.getUserId());
			noticeRoleDAO.insertNoticeRole(noticeRole);
		}
		Set<String> setRoleIds = new HashSet<String>();
		setRoleIds.add(newTask.getRoleId());
		sendMessage(setRoleIds);
		myRedisTemplate.sendMessage(task.getDisposePersionId(), Constant.NOTICE_TYPE_PROC_SUCCESS);
	}

	/**
	 * 临时工单退回
	 */
	@Transactional
	@Override
	public void tempTaskBack(Task task) {
		taskDAO.updateTask(task);
		task = taskDAO.selectTaskByTaskId(task.getTaskId());
		//重新创建单子
		Task newTask = new Task();
		newTask.setUpTaskId(task.getTaskId());
		newTask.setProcinstId(task.getProcinstId());  //流程ID
		newTask.setTaskCode(task.getTaskCode());
		newTask.setStartTime(task.getStartTime());   //发起时间
		newTask.setStartPersonId(task.getStartPersonId()); //发起人
		newTask.setStartPersonRoleId(task.getStartPersonRoleId()); //发起人角色ID
		newTask.setProcType(task.getProcType());	//流程类型
		newTask.setStatus(Constant.TASK_UNCOMPLETE);	//流程状态
		newTask.setRoleId(task.getRoleId()); 	//角色
		newTask.setProcinstCode(task.getProcinstCode());  //流程编号
		newTask.setStartDeptId(task.getStartDeptId());   //发起人部门
		newTask.setIsBack(Constant.TASK_IS_UNBACK);
		taskDAO.insertTask(newTask);
		
		Notice notice = new Notice();					//通知
		notice.setProcinstId(newTask.getProcinstId());
		notice.setNoticeType(Constant.NOTICE_NOTICETYPE_DISPOSE);
		notice.setCreateById(task.getBackPersionId());
		notice.setSendTime(DateUtil.dateTimeToString(new Date()));
		notice.setCreateAt(DateUtil.dateTimeToString(new Date()));
		notice.setTaskId(newTask.getTaskId());
		notice.setRoleId(newTask.getRoleId());
		noticeDAO.insertNotice(notice);
		List<User> us = userDAO.selectUsersByRoleId(notice.getRoleId());
		for (User u : us) {
			NoticeRole noticeRole = new NoticeRole();
			noticeRole.setUserId(u.getUserId());
			noticeRole.setNoticeId(notice.getNoticeId());
			noticeRole.setRoleId(notice.getRoleId());
			noticeRole.setReceiveTime(notice.getSendTime());
			noticeRoleDAO.insertNoticeRole(noticeRole);
		}
		Assignee ass = new Assignee();
		ass.setTaskId(task.getTaskId());
		List<Assignee> assignees = assigneeDAO.selectAssigneeByResult(ass);
		for (Assignee assignee : assignees) {
			assignee.setResult(Constant.TASK_NULLIFY);  //作废
			assigneeDAO.updateAssignee(assignee);
			ass = new Assignee();
			ass.setTaskId(newTask.getTaskId());
			ass.setUserId(assignee.getUserId());
			ass.setResult(Constant.TASK_UNCOMPLETE);
			assigneeDAO.insertAssignee(ass);
		}
		CachePool cachePool = CachePool.getInstance();
		List<User> users = cachePool.getCacheItem(task.getRoleId());
		for (User user : users) {
			myRedisTemplate.sendMessage(user.getUserId(), "NOTICE_TYPE_PROC");
		}
	}

	@Override
	public List<Task> getTaskByIds(List<String> taskIds) {
		List<Task> tasks = new ArrayList<Task>();
		if(taskIds.size()>0){
			tasks = taskDAO.selectTaskByIds(taskIds);
			for (Task tk : tasks) {
				if(StringUtils.isNotEmpty(tk.getDisposeTime())){
					tk.setDisposeTime(DateUtil.formatDate(tk.getDisposeTime()));
				}
				if(StringUtils.isNotEmpty(tk.getDisposePersionId())){
					User user = userDAO.selectUserByUserId(tk.getDisposePersionId());
					if(user!=null){
						tk.setDisposePersion(user);
					}
				}
				if(StringUtils.isNotEmpty(tk.getUpTaskId())){
					Task upTask = this.getTaskByTaskId(tk.getUpTaskId());
					if(upTask!=null){
						tk.setUpTask(upTask);
					}
				}
				if(StringUtils.isNotEmpty(tk.getRoleId())){
					Role role = roleDAO.selectRoleById(tk.getRoleId());
					if(role!=null){
						tk.setRole(role);
					}
				}
			}
		}
		return tasks;
	}

}
