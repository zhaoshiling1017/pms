package com.ducetech.pms.controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSON;
import com.ducetech.constant.Constant;
import com.ducetech.framework.controller.BaseController;
import com.ducetech.framework.model.User;
import com.ducetech.framework.service.RoleService;
import com.ducetech.framework.service.UserService;
import com.ducetech.pms.model.Assignee;
import com.ducetech.pms.model.Notice;
import com.ducetech.pms.model.NoticeRole;
import com.ducetech.pms.model.Procinst;
import com.ducetech.pms.model.ProcinstFile;
import com.ducetech.pms.model.Task;
import com.ducetech.pms.model.TaskLog;
import com.ducetech.pms.service.AssigneeService;
import com.ducetech.pms.service.NoticeRoleService;
import com.ducetech.pms.service.NoticeService;
import com.ducetech.pms.service.ProcFileService;
import com.ducetech.pms.service.ProcinstService;
import com.ducetech.pms.service.TaskLogService;
import com.ducetech.pms.service.TaskService;
import com.ducetech.redis.MyRedisTemplate;
import com.ducetech.util.CachePool;
import com.ducetech.util.DateUtil;
import com.ducetech.util.DownloadUtil;

/**
 * 
* @ClassName: TempTaskController 
* @Description: 临时流程Controller 
* @author yett 
* @date 2016年9月23日 上午10:14:05 
*
 */

@Controller
@RequestMapping("tempTasks")
public class TempTaskController extends BaseController{

	@Autowired
	private ProcinstService procinstService;
	@Autowired
	private TaskService taskService;
	@Autowired
	private AssigneeService assigneeService;
	@Autowired
	private UserService userService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private TaskLogService taskLogService;
	@Autowired
	private ProcFileService procFileService;
	@Autowired
	private NoticeService noticeService;
	@Autowired
	private NoticeRoleService noticeRoleService;
	@Autowired
	private MyRedisTemplate myRedisTemplate;
	
	/**
	 * @throws IOException  
	 * 
	* @Title: publishTempTask 
	* @Description: 发送临时流程 
	* @param @param taskId
	* @param @param model
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	@RequestMapping(value = "/publishTempTask", method = RequestMethod.POST)
 	public void publishTempTask(Procinst procinst,HttpServletRequest request,
 			HttpServletResponse response) throws IOException {
		User user = getLoginUser(request);
		if(procinst!=null){
			String roleNameIds = request.getParameter("roleNameIds"); 		//发起角色ID
			List<String> roleNameList = new ArrayList<String>();
			String xmb[] = roleNameIds.split(",");  
			for(int i=0;i<xmb.length;i++){
				roleNameList.add(xmb[i]);
			}
			procinst.setStartPersonRoleId(roleNameList.get(0));
			String xmbIds = request.getParameter("xmbIds");   				//执行项目部
			String bsIds = request.getParameter("bsIds");					//相关部室
			procinst.setProcType(Constant.PROCINST_TYPE_TEMP);  			//临时流程
			procinst.setProcinstCode("LSRW0001"); 							//任务编号
			procinst.setStartTime(DateUtil.dateTimeToString(new Date()));	//发起时间
			procinst.setStatus(Constant.PROCINST_UNCOMPLETE);   			//未完成
			procinst.setStartPersonId(user.getUserId());        			//发起人
			procinst.setStartPerson(user);									//对象	
			procinstService.addProcinst(procinst);							//新增保存
			assigneeService.addAssignee(user,procinst,xmbIds, bsIds);		//创建对应的工单以及部室关系
			response.getWriter().write("{\"1\":\"" + "发送成功" + "\""
					+",\"id\":\"" + procinst.getProcinstId() + "\""
					+ "}"); 
		}else {
			response.getWriter().write("{\"0\":\"" + "发送失败" + "\"}"); 
		}
	}
	
	/** 
	 * 
	* @Title: tempTaskHandle 
	* @Description:  临时工单处理页面
	* @param @param taskId
	* @param @param model
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	@RequestMapping(value = "/tempTaskHandle/{pageType}/{taskId}", method = RequestMethod.GET)
	public String tempTaskHandle(@PathVariable String pageType, @PathVariable String taskId, Model model) {
		int type=0;
		//工单信息
		Task task = taskService.getTaskByTaskId(taskId);
		User user = userService.getUserByUserId(task.getStartPersonId());
		task.setStartPerson(user);
		task.setStartTime(DateUtil.formatDate(task.getStartTime()));
		User disUser= null;
		if(StringUtils.isNotEmpty(task.getDisposePersionId()))
			disUser = userService.getUserByUserId(task.getDisposePersionId());
		if(disUser!=null)
			task.setDisposePersion(disUser);
		//流程信息
		Procinst procinst = procinstService.getProcinstByProcinstId(task.getProcinstId());
		User procinstUser = userService.getUserByUserId(procinst.getStartPersonId());
		if(procinstUser!=null)
			procinst.setStartPerson(procinstUser);
		//判断是建议还是确认或退回  0：部室建议  1：非部室建议
		Assignee ass = new Assignee();
		ass.setTaskId(task.getTaskId());
		List<Assignee> assignees = assigneeService.getAssigneeByResult(ass);
		if(assignees.size()>0){
			for (Assignee assignee : assignees) {
				if (assignee.getResult().equals(Constant.TASK_COMPLETE)) {
					type=1;
				}else {
					type=0;
					break;
				}
			}
		}
		//流转记录
		TaskLog taskLog =null;
		List<TaskLog> taskLogs = new ArrayList<TaskLog>();
		taskLog = new TaskLog();
		taskLog.setProcinstId(task.getProcinstId());
		taskLog.setTaskId("-1");
		List<TaskLog> taskLogs1 = taskLogService.getTaskLog(taskLog);
		for (TaskLog taskLog2 : taskLogs1) {
			taskLogs.add(taskLog2);
		}
		String upTaskId = task.getUpTaskId();
		List<Task> recordTask = new ArrayList<Task>();
		while (StringUtils.isNotEmpty(upTaskId)) {
			taskLog.setTaskId(upTaskId);
			List<TaskLog> taskLogs2 = taskLogService.getTaskLog(taskLog);
			for (TaskLog taskLog2 : taskLogs2) {
				taskLogs.add(taskLog2);
			}
			Task upTask = taskService.getTaskByTaskId(upTaskId);
			if(upTask!=null){
				recordTask.add(upTask);
				upTaskId = upTask.getUpTaskId();
			}
		}
		/*if(StringUtils.isNotEmpty(task.getUpTaskId())){
			taskLog.setTaskId(task.getUpTaskId());
			List<TaskLog> taskLogs2 = taskLogService.getTaskLog(taskLog);
			for (TaskLog taskLog2 : taskLogs2) {
				taskLogs.add(taskLog2);
			}
		}*/
		taskLog.setTaskId(taskId);
		List<TaskLog> taskLogs2 = taskLogService.getTaskLog(taskLog);
		for (TaskLog taskLog2 : taskLogs2) {
			taskLogs.add(taskLog2);
		}
		
		//退回信息
		Task t =null;
		if(StringUtils.isNotEmpty(task.getUpTaskId())){
			t = taskService.getTaskByTaskId(task.getUpTaskId());
			User u = userService.getUserByUserId(t.getBackPersionId());
			t.setBackPersion(u);
		}
		//建议信息
		List<Assignee> listAssignees = new ArrayList<Assignee>();
		List<Assignee> lists = assigneeService.getAssigneeByTaskId(task.getTaskId());
		for (Assignee assignee : lists) {
			if(assignee.getResult().equals(Constant.TASK_COMPLETE)){
				User u = userService.getUserByUserId(assignee.getSuggestId());
				assignee.setSuggestUser(u);
				assignee.setSuggestTime(DateUtil.formatDate(assignee.getSuggestTime()));
				listAssignees.add(assignee);
			}
		}
		//附件
		List<ProcinstFile> procinstFiles = new ArrayList<ProcinstFile>();
		ProcinstFile procinstFile = new ProcinstFile();
		if(StringUtils.isNotEmpty(task.getUpTaskId())){
			Task task2 = taskService.getTaskByTaskId(task.getUpTaskId());
			procinstFile.setTaskId(task2.getTaskId());
			if(procFileService.getProcFile(procinstFile).size()>0){
				for (ProcinstFile procinstFile2 : procFileService.getProcFile(procinstFile)) {
					procinstFile2.setUploadTime(DateUtil.formatDate(procinstFile2.getUploadTime()));
					User user2 = userService.getUserByUserId(procinstFile2.getCreateById());
					procinstFile2.setCreateBy(user2);
					procinstFiles.add(procinstFile2);
				}
			}
		}
		procinstFile.setTaskId(task.getTaskId());
		if(procFileService.getProcFile(procinstFile).size()>0){
			for (ProcinstFile procinstFile2 : procFileService.getProcFile(procinstFile)) {
				procinstFile2.setUploadTime(DateUtil.formatDate(procinstFile2.getUploadTime()));
				User user2 = userService.getUserByUserId(procinstFile2.getCreateById());
				procinstFile2.setCreateBy(user2);
				procinstFiles.add(procinstFile2);
			}
		}
		UUID uuid = UUID.randomUUID();
		model.addAttribute("uuid", uuid);
		model.addAttribute("procinstFiles", procinstFiles);		//附件
		model.addAttribute("pageType",pageType);   				//返回类型
		model.addAttribute("listAssignees",listAssignees);   	//建议信息
		model.addAttribute("t",t);								//退回信息
		model.addAttribute("taskLogs",taskLogs);				//流转记录
		model.addAttribute("task", task);						//工单信息
		model.addAttribute("procinst", procinst);				//流程信息
		model.addAttribute("type", type);						//判断是建议还是确认或退回
		return "/pms/temptask/temp-task-handle";
	}
	
	/** 
	 * 
	* @Title: tempTaskDispose 
	* @Description: 临时工单处理 
	* @param @param task
	* @param @param model
	* @param @param response
	* @param @throws IOException    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	@RequestMapping(value = "/tempTaskDispose", method = RequestMethod.POST)
	public void tempTaskDispose(Task task,HttpServletResponse response,
			HttpServletRequest request) throws IOException {
		User user = getLoginUser(request);
		if(task!=null){
			Task t =  taskService.getTaskByTaskId(task.getTaskId());
			if(t.getStatus().equals(Constant.TASK_UNCOMPLETE)){
				int type =0; 													//0:处理    1：确认
				task.setDisposePersionId(getLoginUser(request).getUserId()); 	//处理人
				task.setDisposeTime(DateUtil.dateTimeToString(new Date()));	 	//处理时间	
				task.setEndTime(DateUtil.dateTimeToString(new Date()));   		//结束时间
				task.setStatus(Constant.TASK_COMPLETE);  						//已完成
				taskService.updateTask(task,type);
				List<Assignee> assignees = assigneeService.getAssigneeByTaskId(task.getTaskId());
				task = taskService.getTaskByTaskId(task.getTaskId());
				for (Assignee assignee : assignees) {
					//消息通知
					Notice notice = new Notice();
					notice.setProcinstId(task.getProcinstId());
					notice.setNoticeType(Constant.NOTICE_NOTICETYPE_DISPOSE);
					notice.setCreateById(user.getUserId());
					notice.setSendTime(DateUtil.dateTimeToString(new Date()));
					notice.setTaskId(task.getTaskId());
					notice.setRoleId(assignee.getUserId());
					noticeService.addNotice(notice);
					List<User> users = userService.getUsersByRoleId(notice.getRoleId());
					for (User u : users) {
						NoticeRole noticeRole = new NoticeRole();
						noticeRole.setUserId(u.getUserId());
						noticeRole.setNoticeId(notice.getNoticeId());
						noticeRole.setRoleId(notice.getRoleId());
						noticeRole.setReceiveTime(notice.getSendTime());
						noticeRoleService.addNoticeRole(noticeRole);
					}
				}
				
				//流转记录
				TaskLog taskLog = new TaskLog();
				task = taskService.getTaskByTaskId(task.getTaskId());
				taskLog.setTaskId(task.getTaskId());  							//工单ID
				taskLog.setNodeName(user.getName()+"处理");  						//节点名称
				if(StringUtils.isNotEmpty(task.getUpTaskId())){
					taskLog.setNodeHandle(userService.getUserByUserId(task.getStartPersonId()).getName()+"退回");   //上一节点
				}else {
					taskLog.setNodeHandle(userService.getUserByUserId(task.getStartPersonId()).getName()+"下发");   //上一节点
				}
				taskLog.setPerson(user.getName()); 								//处理人
				taskLog.setNodeTime(DateUtil.dateTimeToString(new Date()));  	//时间
				taskLog.setProcinstId(task.getProcinstId()); 					//实例Id
				if(StringUtils.isNotEmpty(task.getUpTaskId()))
					taskLog.setUpTaskId(task.getUpTaskId());
				taskLog.setNodeAction("处理");   									//节点动作
				taskLog.setNodeRole(roleService.getRoleById(task.getRoleId()).getRoleName()); //节点角色
				taskLogService.addTaskLog(taskLog);
				//
				response.getWriter().write("{\"1\":\"" + "处理成功" + "\""
						+",\"id\":\"" + task.getTaskId() + "\""
						+ "}");
			}else {
				response.getWriter().write("{\"0\":\"" + "工单已处理" + "\"}"); 
			}
		}else {
			response.getWriter().write("{\"0\":\"" + "稍后重试" + "\"}"); 
		}
	}
	
	/**
	 * 
	* @Title: tempTaskSuggest 
	* @Description: 相关部室建议 
	* @param @param task
	* @param @param model
	* @param @param response
	* @param @param request
	* @param @throws IOException    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	@RequestMapping(value = "/tempTaskSuggest", method = RequestMethod.POST)
	public void tempTaskSuggest(Assignee assignee,HttpServletResponse response,
			HttpServletRequest request) throws IOException {
		User user = getLoginUser(request);
		if(assignee!=null){
			List<Assignee> assignees = assigneeService.selectAssigneeByRoleIds(assignee, user);
			if(assignees.size()>0){
				for (Assignee assignee2 : assignees) {
					assignee.setAssigneeId(assignee2.getAssigneeId());
				}
			}
			Assignee a = assigneeService.getAssigneeById(assignee.getAssigneeId());
			if(a.getResult().equals(Constant.TASK_UNCOMPLETE)){
				assignee.setSuggestId(user.getUserId());  	 						//建议人
				assignee.setSuggestUser(user);
				assignee.setResult(Constant.TASK_COMPLETE);  						//已建议
				assignee.setSuggestTime(DateUtil.dateTimeToString(new Date()));		//建议时间
				assigneeService.updateAssignee(assignee);
				List<Assignee> assList = assigneeService.getAssigneeByTaskId(assignee.getTaskId()); //根据工单id查询所有的建议单
				if(assList.size()>0){
					boolean flag=true;
					for (Assignee assignee2 : assList) {
						if(assignee2.getResult().equals(Constant.TASK_UNCOMPLETE)){
							flag=false;
							break;
						}
					}
					if(flag){
						Task task = taskService.getTaskByTaskId(assignee.getTaskId());
						task.setIsBack(Constant.TASK_IS_BACK_THREE);						//表示所属工单的建议单都完成
						taskService.updateTask(task, 2);
						//消息通知
						Notice notice = new Notice();
						notice.setProcinstId(task.getProcinstId());
						notice.setNoticeType(Constant.NOTICE_NOTICETYPE_DISPOSE);
						notice.setCreateById(user.getUserId());
						notice.setSendTime(DateUtil.dateTimeToString(new Date()));
						notice.setTaskId(task.getTaskId());
						Procinst procinst = procinstService.getProcinstByProcinstId(task.getProcinstId());
						notice.setRoleId(procinst.getStartPersonRoleId());
						noticeService.addNotice(notice);
						List<User> users = userService.getUsersByRoleId(notice.getRoleId());
						for (User u : users) {
							NoticeRole noticeRole = new NoticeRole();
							noticeRole.setUserId(u.getUserId());
							noticeRole.setNoticeId(notice.getNoticeId());
							noticeRole.setRoleId(notice.getRoleId());
							noticeRole.setReceiveTime(notice.getSendTime());
							noticeRoleService.addNoticeRole(noticeRole);
						}
					}
				}
				//流转记录
				TaskLog taskLog = new TaskLog();
				Task task = taskService.getTaskByTaskId(assignee.getTaskId());
				taskLog.setTaskId(task.getTaskId());  							//工单ID
				taskLog.setNodeName(user.getName()+"建议");  						//节点名称
				taskLog.setNodeHandle(userService.getUserByUserId(task.getDisposePersionId()).getName()+"处理");   //上一节点
				taskLog.setPerson(user.getName()); 								//处理人
				taskLog.setNodeTime(DateUtil.dateTimeToString(new Date()));  	//时间
				taskLog.setProcinstId(task.getProcinstId()); 					//实例Id
				if(StringUtils.isNotEmpty(task.getUpTaskId()))
					taskLog.setUpTaskId(task.getUpTaskId());
				taskLog.setNodeAction("建议");   									//节点动作
				taskLog.setNodeRole(roleService.getRoleById(task.getRoleId()).getRoleName()); //节点角色
				taskLogService.addTaskLog(taskLog);
				response.getWriter().write("{\"1\":\"" + "建议成功" + "\""
						+",\"id\":\"" + task.getTaskId() + "\""
						+ "}");
			}else {
				response.getWriter().write("{\"0\":\"" + "工单已建议" + "\"}"); 
			}
		}else {
			response.getWriter().write("{\"0\":\"" + "稍后重试" + "\"}");
		}
	}
	
	/**
	 * 
	* @Title: tempTaskBack 
	* @Description: 临时工单退回	
	* @param @param task
	* @param @param model
	* @param @param response
	* @param @param request
	* @param @throws IOException    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	@RequestMapping(value = "/tempTaskBack", method = RequestMethod.POST)
	public void tempTaskBack(Task task, Model model, HttpServletResponse response,HttpServletRequest request) throws IOException {
		User user = getLoginUser(request);
		if(task!=null){
			Task t = taskService.getTaskByTaskId(task.getTaskId());
			if(t.getIsBack().equals(Constant.TASK_IS_BACK)){
				response.getWriter().write("{\"0\":\"" + "工单已退回" + "\"}"); 
			}else if(t.getIsBack().equals(Constant.TASK_IS_BACK_TWO)){
				response.getWriter().write("{\"0\":\"" + "工单已通过" + "\"}"); 
			}else {
				task.setBackPersionId(user.getUserId());   						//退回人
				task.setBackTime(DateUtil.dateTimeToString(new Date()));   		//退回时间
				task.setIsBack(Constant.TASK_IS_BACK);
				task.setStatus(Constant.TASK_NULLIFY);  						//作废
				taskService.tempTaskBack(task);
				//修改附件信息
				ProcinstFile procinstFile = new ProcinstFile();
				procinstFile.setTaskId(task.getTaskId());
				List<ProcinstFile> procinstFiles = procFileService.getProcFile(procinstFile);
				for (ProcinstFile pf : procinstFiles) {
					if(!pf.getCreateById().equals(task.getBackPersionId()))
						procFileService.updateIsBackProcFile(pf.getProcinstFileId());
				}
				
				//流转记录
				TaskLog taskLog = new TaskLog();
				task = taskService.getTaskByTaskId(task.getTaskId());
				taskLog.setTaskId(task.getTaskId());  							//工单ID
				taskLog.setNodeName(user.getName()+"退回");  						//节点名称
				taskLog.setNodeHandle(userService.getUserByUserId(task.getDisposePersionId()).getName()+"处理");   //上一节点
				taskLog.setPerson(user.getName());								//处理人
				taskLog.setNodeTime(DateUtil.dateTimeToString(new Date()));  	//时间
				taskLog.setProcinstId(task.getProcinstId());					//实例Id
				if(StringUtils.isNotEmpty(task.getUpTaskId()))
					taskLog.setUpTaskId(task.getUpTaskId());
				taskLog.setNodeAction("退回");   									//节点动作
				taskLog.setNodeRole(roleService.getRoleById(task.getRoleId()).getRoleName()); //节点角色
				taskLogService.addTaskLog(taskLog);
				response.getWriter().write("{\"1\":\"" + "退回成功" + "\""
						+",\"id\":\"" + task.getTaskId() + "\""
						+ "}");
			}
		}else {
			response.getWriter().write("{\"0\":\"" + "稍后重试" + "\"}"); 
		}
	}
	
	/**
	 * 
	* @Title: tempTaskPass 
	* @Description: 工单确认 
	* @param @param id
	* @param @param model
	* @param @param response
	* @param @param request
	* @param @throws IOException    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	@RequestMapping(value = "/tempTaskPass", method = RequestMethod.POST)
	public void tempTaskPass(String id,HttpServletResponse response,
			HttpServletRequest request) throws IOException {
		User user = getLoginUser(request);
		if(StringUtils.isNotEmpty(id)){
			Task task = taskService.getTaskByTaskId(id);
			if(task.getIsBack().equals(Constant.TASK_IS_BACK_THREE)){
				int type=1;
				taskService.updateTask(task,type);
				TaskLog taskLog = new TaskLog();
				task = taskService.getTaskByTaskId(task.getTaskId());
				taskLog.setTaskId(task.getTaskId());  //工单ID
				taskLog.setNodeName(user.getName()+"通过");  //节点名称
				taskLog.setNodeHandle(userService.getUserByUserId(task.getDisposePersionId()).getName()+"处理");   //上一节点
				taskLog.setPerson(user.getName()); //处理人
				taskLog.setNodeTime(DateUtil.dateTimeToString(new Date()));  //时间
				taskLog.setProcinstId(task.getProcinstId()); //实例Id
				if(StringUtils.isNotEmpty(task.getUpTaskId()))
					taskLog.setUpTaskId(task.getUpTaskId());
				taskLog.setNodeAction("通过");   //节点动作
				taskLog.setNodeRole(roleService.getRoleById(task.getRoleId()).getRoleName()); //节点角色
				taskLogService.addTaskLog(taskLog);
				response.getWriter().write("{\"1\":\"" + "通过成功" + "\"}");
			}else {
				response.getWriter().write("{\"0\":\"" + "工单已通过" + "\"}"); 
			}
		}else {
			response.getWriter().write("{\"0\":\"" + "稍后重试" + "\"}"); 
		}
			
	}
	
	/**
	 * 
	* @Title: tempTaskDetail 
	* @Description: 临时任务详情 
	* @param @param model
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	@RequestMapping(value = "/tempTaskDetail/{pageType}/{procinstId}", method = RequestMethod.GET)
	public String tempTaskDetail(@PathVariable String pageType, 
			@PathVariable String procinstId, Model model) {
		/*
		 * 0     ----主页
		 * 1     ----任务管理页面
		 * 2     ----与我相关页面
		 * 3     ----进度查询页面 
		 * 4     ----流程启动统计
		 * 5	 ----部门执行统计
		 * 6     ----系统通知页面
		 */
		//获取流程及工单信息
		Procinst procinst = procinstService.getProcinstByProcinstId(procinstId);	//流程实例信息
		model.addAttribute("procinst", procinst);
		Task t = new Task();
		List<String> statusMulti = new ArrayList<String>();
		statusMulti.add(Constant.TASK_COMPLETE);
		statusMulti.add(Constant.TASK_NULLIFY);
		t.setStatusMulti(statusMulti);
		t.setProcinstId(procinstId);
		List<Task> tasks = taskService.getTaskByQuery(t);	//获取流程实例下的工单已完成的节点信息
		/*
		 * 流转记录
		 */
		TaskLog taskLog =null;
		List<TaskLog> taskLogs = null;
		taskLog = new TaskLog();
		taskLog.setProcinstId(procinstId);
		taskLogs = taskLogService.getTaskLog(taskLog);
		for (TaskLog taskLog2 : taskLogs) {
			taskLog2.setNodeTime(DateUtil.formatDate(taskLog2.getNodeTime()));
		}
		/**
		 * 所有附件
		 */
		ProcinstFile procinstFile = new ProcinstFile();
		procinstFile.setProcinstId(procinst.getProcinstId());
		List<ProcinstFile> procinstFiles = procFileService.getProcFile(procinstFile);
		UUID uuid = UUID.randomUUID();
		model.addAttribute("uuid", uuid);
		model.addAttribute("procinstFiles",procinstFiles);
		model.addAttribute("taskLogs",taskLogs);
		model.addAttribute("tasks", tasks);
		model.addAttribute("pageType", pageType);
		return "/pms/tempTask/temp-task-detail";
	}

	/**
	 * 
	* @Title: uploadMessageFile 
	* @Description: 文件上传
	* @param @param id
	* @param @param file
	* @param @param request
	* @param @param response
	* @param @throws IOException    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	@RequestMapping(value = "/uploadTempTaskFile", method = RequestMethod.POST)
	public void uploadMessageFile(String uuid , String type,String id,String file,HttpServletRequest request,
			HttpServletResponse response) throws IOException{
		User user = getLoginUser(request);
		if(StringUtils.isNotEmpty(id)){
			Task task = null;
			Procinst procinst =null;
			if(type.equals("1")){
				task = taskService.getTaskByTaskId(id);
			}else {
				procinst = procinstService.getProcinstByProcinstId(id);
			}
			if(myRedisTemplate.getItem(uuid)!=null){
			@SuppressWarnings("unchecked")
			List<Map<String, String>> lists = (List<Map<String, String>>) myRedisTemplate.getItem(uuid);
				for (Map<String, String> map : lists) {
					ProcinstFile procinstFile = new ProcinstFile();
					procinstFile.setCreateById(user.getUserId());
					procinstFile.setUploadTime(DateUtil.dateTimeToString(new Date()));
					Set<String> keys = map.keySet();
					for (String key : keys) {
						procinstFile.setPath(map.get(key));
						procinstFile.setFileName(key);
					}
					if(procinst!=null){
						if(type.equals("0")){
							Task t = new Task();
							t.setProcinstId(procinst.getProcinstId());
							List<Task> tasks = taskService.getTaskByQuery(t);
							for (Task t2 : tasks) {
								if(!t2.getIsBack().equals(Constant.TASK_IS_BACK)){
									procinstFile.setProcinstId(procinst.getProcinstId());
									procinstFile.setProcType(procinst.getProcType());
									procinstFile.setTaskId(t2.getTaskId());
									procinstFile.setNodeId("0");
									procFileService.addProcFile(procinstFile);
								}
							}
						}else {
							Task t = new Task();
							t.setProcinstId(procinst.getProcinstId());
							List<Task> tasks = taskService.getTaskByQuery(t);
							for (Task t2 : tasks) {
								if(!t2.getIsBack().equals(Constant.TASK_IS_BACK)){
									procinstFile.setProcinstId(procinst.getProcinstId());
									procinstFile.setProcType(procinst.getProcType());
									procinstFile.setTaskId(t2.getTaskId());
									procinstFile.setNodeId("1");
									procFileService.addProcFile(procinstFile);
								}
							}
						}
					}else if(task!=null){
						procinstFile.setProcinstId(task.getProcinstId());
						procinstFile.setTaskId(task.getTaskId());
						procinstFile.setProcType(task.getProcType());
						procinstFile.setNodeId("0");
						procFileService.addProcFile(procinstFile);
					}
				}
			}
			/*if(file.length()>2){
				file = file.replace("[", "");
				file = file.replace("]", "");
				file = file.replace("{", "");
				file = file.replace("}", "");
				file = file.substring(1, file.length()-1);
				String files[] = file.split("\",\"");
				for (String string : files) {
					String path[] = string.split(",");
					if(path.length>0){
						ProcinstFile procinstFile = new ProcinstFile();
						procinstFile.setCreateById(user.getUserId());
						procinstFile.setUploadTime(DateUtil.dateTimeToString(new Date()));
						procinstFile.setPath(path[0]);
						procinstFile.setFileName(path[1]);
						if(procinst!=null){
							if(type.equals("0")){
								Task t = new Task();
								t.setProcinstId(procinst.getProcinstId());
								List<Task> tasks = taskService.getTaskByQuery(t);
								for (Task t2 : tasks) {
									if(!t2.getIsBack().equals(Constant.TASK_IS_BACK)){
										procinstFile.setProcinstId(procinst.getProcinstId());
										procinstFile.setProcType(procinst.getProcType());
										procinstFile.setTaskId(t2.getTaskId());
										procinstFile.setNodeId("0");
										procFileService.addProcFile(procinstFile);
									}
								}
							}else {
								Task t = new Task();
								t.setProcinstId(procinst.getProcinstId());
								List<Task> tasks = taskService.getTaskByQuery(t);
								for (Task t2 : tasks) {
									if(!t2.getIsBack().equals(Constant.TASK_IS_BACK)){
										procinstFile.setProcinstId(procinst.getProcinstId());
										procinstFile.setProcType(procinst.getProcType());
										procinstFile.setTaskId(t2.getTaskId());
										procinstFile.setNodeId("1");
										procFileService.addProcFile(procinstFile);
									}
								}
							}
						}else if(task!=null){
							procinstFile.setProcinstId(task.getProcinstId());
							procinstFile.setTaskId(task.getTaskId());
							procinstFile.setProcType(task.getProcType());
							procinstFile.setNodeId("0");
							procFileService.addProcFile(procinstFile);
						}
					}
				}
				response.getWriter().write("{\"1\":\"" + "上传成功" + "\"}");
			}else {
				response.getWriter().write("{\"1\":\"" + "无文件上传" + "\"}");
			}*/
			response.getWriter().write("{\"1\":\"" + "上传成功" + "\"}");
		}else {
			response.getWriter().write("{\"0\":\"" + "上传失败" + "\"}");
		}
	}
	
	/**
	 * 
	* @Title: downloadMessageFile 
	* @Description: 下载附件
	* @param @param fileId
	* @param @param response
	* @param @param request
	* @param @throws IOException    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	@RequestMapping(value = "/downloadTempTaskFile/{fileId}", method = RequestMethod.GET)
	public void downloadTempTaskFile(@PathVariable String fileId, HttpServletResponse response, HttpServletRequest request) throws IOException {
		ProcinstFile procinstFile = procFileService.getProcFileById(fileId);
		File file = new File(procinstFile.getPath());
		String fileName = procinstFile.getFileName();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        FileInputStream inputStream = new FileInputStream(file);
        byte[] buf = new byte[1024];
        int length = 1024;
        int readLength = inputStream.read(buf, 0, length);
        while (readLength != -1) {
        	baos.write(buf, 0, readLength);
			readLength = inputStream.read(buf, 0, length);
        }
		DownloadUtil downloadUtil = new DownloadUtil();
		downloadUtil.download(baos, response, fileName);
		inputStream.close();
	}
	
	/**
	 * 
	* @Title: judgeMessageFile 
	* @Description: 附件下载判断文件是否存在
	* @param @param fileId
	* @param @param response
	* @param @param request
	* @param @throws IOException    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	@RequestMapping(value = "/judgeTempTaskFile/{fileId}", method = RequestMethod.GET)
	public void judgeMessageFile(@PathVariable String fileId, HttpServletResponse response, HttpServletRequest request) throws IOException{
		ProcinstFile procinstFile = procFileService.getProcFileById(fileId);
		File file = new File(procinstFile.getPath());
		if(!file.exists()){
			response.getWriter().write("{\"0\":\"" + "文件不存在" + "\"}");
		}else{
			response.getWriter().write("{\"1\":\"" + "文件存在" + "\"}");
		}    
	}
	
	
	/**
	 * 
	* @Title: deleteTempTaskFile 
	* @Description: 附件删除 
	* @param @param fileId
	* @param @param response
	* @param @param request
	* @param @throws IOException    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	@RequestMapping(value = "/deleteTempTaskFile", method = RequestMethod.POST)
	public void deleteTempTaskFile(String fileId, HttpServletResponse response, HttpServletRequest request) throws IOException {
		ProcinstFile procinstFile = procFileService.getProcFileById(fileId);
		if(procinstFile.getIsDeleted().equals(Constant.NOT_DELETE)){
			procFileService.deleteProcFileById(fileId);
			//删除路径存储文件
			UploadController uploadController = new UploadController();
			uploadController.deleteFile(procinstFile.getPath(), response);
			response.getWriter().write("{\"1\":\"" + "删除成功" + "\"}");
		}else {
			response.getWriter().write("{\"1\":\"" + "附件已删除" + "\"}");
		}
	}
	
	/** 
	 * 
	* @Title: getUnNodeData 
	* @Description: 获取未完成节点数据 
	* @param @param procinstId
	* @param @param request
	* @param @param response
	* @param @throws IOException    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	@RequestMapping(value = "/getUnNodeData", method = RequestMethod.POST)
	public void getUnNodeData(String procinstId,HttpServletRequest request, HttpServletResponse response) throws IOException {
		Map<String, String> nodeMap = new HashMap<String, String>(); //默认0未有工单(灰色)、1已完成(绿色)、2未完成(红色)、3前面有未完成工单节点(黄色)
		Procinst procinst = procinstService.getProcinstByProcinstId(procinstId);
		nodeMap.put("node1", Constant.NODE_GREEN);
		if(procinst.getStatus().equals(Constant.TASK_COMPLETE)){
			nodeMap.put("node2", Constant.NODE_GREEN);
			nodeMap.put("node3", Constant.NODE_GREEN);
			nodeMap.put("node4", Constant.NODE_GREEN);
		}else {
			Task t = new Task();
			t.setProcinstId(procinstId);
			List<Task> tasks = taskService.getTaskByQuery(t);
			//判断第二节点颜色
			for (Task task : tasks) {
				if(!task.getIsBack().equals(Constant.TASK_IS_BACK)){
					if(task.getStatus().equals(Constant.TASK_UNCOMPLETE)){
						nodeMap.put("node2",Constant.NODE_RED);
						break;
					}else {
						nodeMap.put("node2", Constant.NODE_GREEN);
					}
				}
			}
			//根据第二节点判断第三节点颜色
			for (Task task : tasks) {
				String node2 = nodeMap.get("node2");
				if(!task.getIsBack().equals(Constant.TASK_IS_BACK)){
					List<Assignee> assignees = assigneeService.getAssigneeByTaskId(task.getTaskId());
					if(node2.equals(Constant.NODE_GREEN)){   //绿色
						for (Assignee assignee : assignees) {
							if(assignee.getResult().equals(Constant.TASK_UNCOMPLETE)){
								nodeMap.put("node3",Constant.NODE_RED);  
								break;
							}else {
								nodeMap.put("node3", Constant.NODE_GREEN);  
							}
						}
						if(nodeMap.get("node3").equals(Constant.NODE_RED)){
							break;
						}
					}else if(node2.equals(Constant.NODE_RED)){  //红色
						//上一步有完成就显示黄色
						if(task.getStatus().equals(Constant.TASK_COMPLETE)){
							nodeMap.put("node3",Constant.NODE_YELLOW);
							break;
						}else{
							nodeMap.put("node3",Constant.NODE_GRAY);
							nodeMap.put("node4",Constant.NODE_GRAY);
						}
						//当前这一步有操作才显示黄色
//						for (Assignee assignee : assignees) {
//							if(assignee.getResult().equals(Constant.NODE_GREEN)){
//								nodeMap.put("node3",Constant.NODE_YELLOW);
//								break;
//							}else {
//								nodeMap.put("node3",Constant.NODE_GRAY);
//								nodeMap.put("node4",Constant.NODE_GRAY);
//							}
//						}
						if(nodeMap.get("node3").equals(Constant.NODE_YELLOW)){
							break;
						}
					}
				}
			}
			//根据第三节点判断第四节点颜色
			for (Task task : tasks) {
				String node3 = nodeMap.get("node3");
				if(!task.getIsBack().equals(Constant.TASK_IS_BACK)){
					if(node3.equals(Constant.NODE_GREEN)){   //绿色
						if(task.getIsBack().equals(Constant.TASK_IS_BACK_THREE)){
							nodeMap.put("node4",Constant.NODE_RED);
							break;
						}else {
							nodeMap.put("node4", Constant.NODE_GREEN);
						}
					}else if(node3.equals(Constant.NODE_RED)){  //红色
						if(task.getIsBack().equals(Constant.TASK_IS_BACK_TWO) || task.getIsBack().equals(Constant.TASK_IS_BACK_THREE)){
							nodeMap.put("node4",Constant.NODE_YELLOW);
							break;
						}else {
							nodeMap.put("node4",Constant.NODE_GRAY);
						}
					}else if(node3.equals(Constant.NODE_YELLOW)){
						if(task.getIsBack().equals(Constant.TASK_IS_BACK_TWO) || task.getIsBack().equals(Constant.TASK_IS_BACK_THREE)){
							nodeMap.put("node4",Constant.NODE_YELLOW);
							break;
						}else {
							nodeMap.put("node4",Constant.NODE_GRAY);
						}
					}
				}
			}
		}
		System.out.println(JSON.toJSONString(nodeMap));
		response.getWriter().write(JSON.toJSONString(nodeMap));
	}
	
	/**
	 * 
	* @Title: handleShow 
	* @Description: 根据登录人判断工单完成状态是否显示处理按钮 
	* @param @param id
	* @param @param response
	* @param @param request
	* @param @throws IOException    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	@RequestMapping(value = "/handleShow", method = RequestMethod.POST)
	public void handleShow(String id, HttpServletResponse response, HttpServletRequest request) throws IOException {
		User user = getLoginUser(request);
		int temp = 0; //未处理
		Task task = taskService.getTaskByTaskId(id);
		CachePool cachePool = CachePool.getInstance();
		List<User> users = cachePool.getCacheItem(task.getRoleId());
		for (User u : users) {
			if (u.getName().equals(user.getName())) {
				if(task.getStatus().equals(Constant.TASK_COMPLETE) && task.getIsBack().equals(Constant.TASK_IS_UNBACK)){
					temp=1;   //已处理
				}
			}
		}
		if(user.getUserId().equals(task.getStartPersonId())){
			if(task.getStatus().equals(Constant.TASK_COMPLETE) && task.getIsBack().equals(Constant.TASK_IS_BACK_TWO)){
				temp=1;  //已处理
			}
		}
		List<Assignee> assignees = assigneeService.getAssigneeByTaskId(task.getTaskId());
		for (Assignee assignee : assignees) {
			users = cachePool.getCacheItem(assignee.getUserId());
			for (User u : users) {
				if (u.getName().equals(user.getName())) {
					if(assignee.getResult().equals(Constant.TASK_COMPLETE)){
						temp=1;   //已处理
					}
				}
			}
		}
		response.getWriter().write(JSON.toJSONString(temp));
	}
	
}
