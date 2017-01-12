package com.ducetech.pms.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ducetech.constant.Constant;
import com.ducetech.framework.controller.BaseController;
import com.ducetech.framework.model.Department;
import com.ducetech.framework.model.PagerRS;
import com.ducetech.framework.model.Query;
import com.ducetech.framework.model.User;
import com.ducetech.framework.service.DepartmentService;
import com.ducetech.framework.service.UserService;
import com.ducetech.pms.model.Message;
import com.ducetech.pms.model.MsgRole;
import com.ducetech.pms.model.Procinst;
import com.ducetech.pms.model.Task;
import com.ducetech.pms.service.MessageService;
import com.ducetech.pms.service.MsgRoleService;
import com.ducetech.pms.service.ProcinstService;
import com.ducetech.pms.service.TaskService;
import com.ducetech.util.DateUtil;


/**
 * 
* @ClassName: MainPageController 
* @Description: 系统主页Controller 
* @author chensf
* @date 2016年9月18日 下午5:38:26 
*
 */
@Controller
public class MainPageController extends BaseController{
	
	@Autowired
	private TaskService taskService;
	@Autowired
	private ProcinstService procinstService;
	@Autowired
	private MessageService messageService;
	@Autowired
	private MsgRoleService msgRoleService;
	@Autowired
	private UserService userService;
	@Autowired
	private DepartmentService departmentService;
	
	@RequestMapping(value = "/main", method = RequestMethod.GET)
	public String testPager(Model model, HttpServletRequest request) {
		User user = getLoginUser(request);
		//集团消息数据
		MsgRole msgRole = new MsgRole();
		Query<MsgRole> query = new Query<MsgRole>();
		msgRole.setIsDeleted(Constant.NOT_DELETE);
		msgRole.setStatus(Constant.MSG_ROLE_UNREAD);
		query.setT(msgRole);
		query.setPage(1);
		query.setRows(5);
		PagerRS<MsgRole> pageRS = msgRoleService.getMsgRoleByPager(query,user.getUserId());
		for (MsgRole msgRole1 : pageRS.getResults()) {
			Message message = messageService.getMessageById(msgRole1.getMsgId());
			message.setPublishTime(DateUtil.formatDate(message.getPublishTime()));
			User user1 = userService.getUserByUserId(message.getPublisherId());
			Department dept = departmentService.getDepartmentById(user1.getDepartmentId());
			user1.setDepartment(dept);
			message.setUser(user1);
			msgRole1.setMessage(message);
		}
		model.addAttribute("msgRoles", pageRS.getResults());
		model.addAttribute("msgRolesCount", pageRS.getCount());
		
		//工单列表数据
		Task task = new Task();						//设置工单查询条件
		task.setStatus(Constant.TASK_UNCOMPLETE);
		Query<Task> taskQuery = new Query<Task>();		//将task放入query并设置分页参数
		taskQuery.setT(task);
		taskQuery.setPage(1);
		taskQuery.setRows(5);
		PagerRS<Task> taskRS = taskService.getTasksByUserId(taskQuery, user.getUserId());	//按人员ID获取工单 
		Task tk = null;
		for(Task t : taskRS.getResults()){
			tk = new Task();
			tk.setProcinstId(t.getProcinstId());
			tk.setNodeId(t.getUpNodeId());
			List<Task> upTaskList = taskService.getTaskByQuery(tk);
			for(Task ta : upTaskList){
				t.setDisposePersion(ta.getDisposePersion());
				t.setDisposeTime(ta.getDisposeTime());
			}
		}
		model.addAttribute("tasks", taskRS.getResults());
		model.addAttribute("taskCount", taskRS.getCount());
		
		//获取与我相关数据
		Procinst procinst = new Procinst();
		procinst.setStatus(Constant.PROCINST_UNCOMPLETE);
		Query<Procinst> procinstQuery = new Query<Procinst>();
		procinstQuery.setT(procinst);
		procinstQuery.setPage(1);
		procinstQuery.setRows(5);
		PagerRS<Procinst> procRS = procinstService.getMyRelatedByUserId(procinstQuery, user.getUserId());	//按userId获取与自己相关的流程实例
		model.addAttribute("procinsts", procRS.getResults());
		model.addAttribute("procinstCount", procRS.getCount());
		return "/pms/main-page";
	}
}
