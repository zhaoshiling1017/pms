package com.ducetech.pms.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ducetech.constant.Constant;
import com.ducetech.framework.dao.DepartmentDAO;
import com.ducetech.framework.dao.RoleDAO;
import com.ducetech.framework.model.Department;
import com.ducetech.framework.model.PagerRS;
import com.ducetech.framework.model.Query;
import com.ducetech.framework.model.Role;
import com.ducetech.framework.model.User;
import com.ducetech.framework.service.UserService;
import com.ducetech.pms.dao.AssigneeDAO;
import com.ducetech.pms.dao.ProcinstDAO;
import com.ducetech.pms.dao.TaskDAO;
import com.ducetech.pms.model.Assignee;
import com.ducetech.pms.model.ProcNode;
import com.ducetech.pms.model.Procinst;
import com.ducetech.pms.model.Task;
import com.ducetech.pms.service.ProcNodeService;
import com.ducetech.pms.service.ProcinstService;
import com.ducetech.pms.service.TaskService;
import com.ducetech.util.DateUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/** 
* @ClassName: ProcinstServiceImpl  
* @author gaoy
* @date 2016年9月6日 下午3:00:25 
* @Description: 流程实例Service实现类
*/
@Service
public class ProcinstServiceImpl implements ProcinstService {
	
	@Autowired
	private ProcinstDAO procinstDAO;
	
	@Autowired
	private TaskDAO taskDAO;
	
	@Autowired
	private RoleDAO roleDAO;
	
	@Autowired
	private ProcNodeService procNodeService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private TaskService taskService;
	@Autowired
	private AssigneeDAO assigneeDAO;
	@Autowired
	private DepartmentDAO departmentDAO;
	
	
	@Override
	public List<Procinst> getAllProcinst() {
		return this.getProcinstByPager(new Query<Procinst>(new Procinst())).getResults();
	}

	@Override
	public List<Procinst> getProcinstByQuery(Procinst procinst) {
		return procinstDAO.selectProcinst(procinst);
	}

	@Override
	@Transactional
	public PagerRS<Procinst> getProcinstByPager(Query<Procinst> query) {
		if(query != null && query.getPage() > 0){				//如果传入offset大于0,则启用分页查询，否则不启用
			PageHelper.startPage(query.getPage(), query.getRows(), true);
		}
		List<Procinst> procinsts = procinstDAO.selectProcinst(query.getT());
		for (Procinst procinst : procinsts) {
			if(procinst.getStatus().equals(Constant.PROCINST_UNCOMPLETE)){
				if(StringUtils.isNotEmpty(procinst.getProcinstId())){
					Task t = new Task();
					t.setProcinstId(procinst.getProcinstId());
					List<Task> tasks = taskDAO.selectTask(t);
					String nodeNames="";
					Set<String> nodeIds = new HashSet<String>();
					for (Task task : tasks) {
						if(task.getProcType().equals(Constant.TASK_TYPE_ORDI)){ //普通流程
							if(task.getStatus().equals(Constant.TASK_UNCOMPLETE)){
								nodeIds.add(task.getNodeId());
							}
						}else if(task.getProcType().equals(Constant.TASK_TYPE_TEMP)){//临时流程
							if(task.getStatus().equals(Constant.TASK_UNCOMPLETE)){
								Role role = roleDAO.selectRoleById(task.getRoleId());
								if(role!=null)
									nodeNames +=role.getRoleName()+",";
							}else if(task.getStatus().equals(Constant.TASK_COMPLETE)){
								Assignee a = new Assignee();
								a.setTaskId(task.getTaskId());
								List<Assignee> assignees = assigneeDAO.selectAssigneeByResult(a);
								if (assignees.size()>0 ) {
									for (Assignee assignee : assignees) {
										if(assignee.getResult().equals(Constant.TASK_UNCOMPLETE)){
											Role role = roleDAO.selectRoleById(assignee.getUserId());
											if(role!=null)
											nodeNames +=role.getRoleName()+",";
										}
									}
								}
							}
							if(task.getStatus().equals(Constant.TASK_COMPLETE) && task.getIsBack().equals("3")){
								Role role = roleDAO.selectRoleById(procinst.getStartPersonRoleId());
								if(role!=null){
									nodeNames +=role.getRoleName()+",";
								}
							}					
						}
					}
					if(procinst.getProcType().equals(Constant.PROCINST_TYPE_TEMP)){ //临时流程
						if(nodeNames.length() > 0){
							String nodeName[] = nodeNames.split(",");
							Set<String> node = new HashSet<String>();
							for(int i=0;i<nodeName.length;i++){
								node.add(nodeName[i]);
							}
							nodeNames =StringUtils.join(node.toArray(), ",");
						}
					}else if(procinst.getProcType().equals(Constant.PROCINST_TYPE_ORDI)){ //普通流程
						for(String nodeId : nodeIds){
							Task ts = new Task();
							ts.setProcinstId(procinst.getProcinstId());
							ts.setNodeId(nodeId);
							ts.setStatus(Constant.TASK_UNCOMPLETE);
							List<Task> tks = taskService.getTaskByQuery(ts);
							String roleNames="";
							for(Task tk : tks){
								roleNames += tk.getRole().getRoleName() + "，";
							}
							if(roleNames.length() > 0){
								roleNames = roleNames.substring(0,roleNames.length()-1);
							}
							ProcNode pn = procNodeService.getProcNodeByNodeId(nodeId);
							nodeNames += pn.getNodeName() + "(" + roleNames + ")" + "，";
						}
						if(nodeNames.length() > 0){
							nodeNames = nodeNames.substring(0,nodeNames.length()-1);
						}
					}
					procinst.setNodeNames(nodeNames);
				}
			}
			if(StringUtils.isNotEmpty(procinst.getStartTime()))
				procinst.setStartTime(DateUtil.formatDate(procinst.getStartTime()));
			if(StringUtils.isNotEmpty(procinst.getEndTime()))
				procinst.setEndTime(DateUtil.formatDate(procinst.getEndTime()));
			User user = userService.getUserByUserId(procinst.getStartPersonId());
			if(user!=null){
				procinst.setStartPerson(user);
			}
		}
		@SuppressWarnings({ "rawtypes", "unchecked" })
		PageInfo page = new PageInfo(procinsts);
		PagerRS<Procinst> pagerRS = new PagerRS<Procinst>(procinsts, page.getTotal(), page.getPages());
		return pagerRS;
	}

	@Override
	public Procinst getProcinstByProcinstId(String procinstId) {
		Procinst procinst = procinstDAO.selectProcinstByprocinstId(procinstId);
		if(procinst!=null){
			if(StringUtils.isNotEmpty(procinst.getStartTime())){
				procinst.setStartTime(DateUtil.formatDate(procinst.getStartTime()));
			}
			if(StringUtils.isNotEmpty(procinst.getEndTime())){
				procinst.setEndTime(DateUtil.formatDate(procinst.getEndTime()));
			}
			if(StringUtils.isNotEmpty(procinst.getStartPersonId())){
				User user = userService.getUserByUserId(procinst.getStartPersonId());
				if(user!=null){
					Department department = departmentDAO.selectDepartmentById(user.getDepartmentId());
					if(department!=null){
						user.setDepartment(department);
					}
					procinst.setStartPerson(user);
				}
			}
			if(procinst.getStatus().equals(Constant.PROCINST_UNCOMPLETE)){
				if(StringUtils.isNotEmpty(procinst.getProcinstId())){
					Task t = new Task();
					t.setProcinstId(procinst.getProcinstId());
					List<Task> tasks = taskDAO.selectTask(t);
					String nodeNames="";
					Set<String> nodeIds = new HashSet<String>();
					for (Task task : tasks) {
						if(task.getProcType().equals(Constant.TASK_TYPE_ORDI)){ //普通流程
							if(task.getStatus().equals(Constant.TASK_UNCOMPLETE)){
								nodeIds.add(task.getNodeId());
							}
						}else if(task.getProcType().equals(Constant.TASK_TYPE_TEMP)){//临时流程
							if(task.getStatus().equals(Constant.TASK_UNCOMPLETE)){
								Role role = roleDAO.selectRoleById(task.getRoleId());
								if(role!=null)
									nodeNames +=role.getRoleName()+",";
							}else if(task.getStatus().equals(Constant.TASK_COMPLETE)){
								Assignee a = new Assignee();
								a.setTaskId(task.getTaskId());
								List<Assignee> assignees = assigneeDAO.selectAssigneeByResult(a);
								if (assignees.size()>0 ) {
									for (Assignee assignee : assignees) {
										if(assignee.getResult().equals(Constant.TASK_UNCOMPLETE)){
											Role role = roleDAO.selectRoleById(assignee.getUserId());
											if(role!=null)
												nodeNames +=role.getRoleName()+",";
										}
									}
								}
							}
							if(task.getStatus().equals(Constant.TASK_COMPLETE) && task.getIsBack().equals("3")){
								Role role = roleDAO.selectRoleById(procinst.getStartPersonRoleId());
								if(role!=null)
									nodeNames +=role.getRoleName()+",";
							}					
						}
					}
					if(procinst.getProcType().equals(Constant.PROCINST_TYPE_TEMP)){ //临时流程
						if(nodeNames.length() > 0){
							String nodeName[] = nodeNames.split(",");
							Set<String> node = new HashSet<String>();
							for(int i=0;i<nodeName.length;i++){
								node.add(nodeName[i]);
							}
							nodeNames =StringUtils.join(node.toArray(), ",");
						}
					}else if(procinst.getProcType().equals(Constant.PROCINST_TYPE_ORDI)){ //普通流程
						for(String nodeId : nodeIds){
							Task ts = new Task();
							ts.setProcinstId(procinst.getProcinstId());
							ts.setNodeId(nodeId);
							ts.setStatus(Constant.TASK_UNCOMPLETE);
							List<Task> tks = taskService.getTaskByQuery(ts);
							String roleNames="";
							for(Task tk : tks){
								roleNames += tk.getRole().getRoleName() + "，";
							}
							if(roleNames.length() > 0){
								roleNames = roleNames.substring(0,roleNames.length()-1);
							}
							ProcNode pn = procNodeService.getProcNodeByNodeId(nodeId);
							nodeNames += pn.getNodeName() + "(" + roleNames + ")" + "，";
						}
						if(nodeNames.length() > 0){
							nodeNames = nodeNames.substring(0,nodeNames.length()-1);
						}
					}
					procinst.setNodeNames(nodeNames);
				}
			}
		}
		return procinst;
	}

	@Transactional
	@Override
	public void addProcinst(Procinst procinst) {
		procinstDAO.insertProcinst(procinst);
	}

	@Override
	public PagerRS<Procinst> getMyRelatedByUserId(Query<Procinst> query, String userId) {
		List<Role> roles = roleDAO.selectRolesByUserId(userId);
		List<String> roleIds = new ArrayList<String>();
		if(roles!=null && roles.size()>0){
			for (Role role : roles) {
				roleIds.add(role.getRoleId());
			} 
		}
		PagerRS<Procinst> pagerRS = this.getProcinstByRoleIds(query, roleIds);
		return pagerRS;
	}

	@Override
	public PagerRS<Procinst> getProcinstByRoleIds(Query<Procinst> query, List<String> roleIds) {
		if(query != null && query.getPage() > 0){	
			PageHelper.startPage(query.getPage(), query.getRows(), true);
		}
		List<Procinst> procinsts = procinstDAO.selectProcinstByRoleIds(query.getT(),roleIds);
//		Task task = new Task();
		for (Procinst procinst : procinsts) {
			Task t = new Task();
			t.setProcinstId(procinst.getProcinstId());
			List<Task> tasks = taskDAO.selectTask(t);
			String nodeNames="";
			Set<String> nodeIds = new HashSet<String>();
			for (Task task : tasks) {
				if(task.getProcType().equals(Constant.TASK_TYPE_ORDI)){ //普通流程
					if(task.getStatus().equals(Constant.TASK_UNCOMPLETE)){
						nodeIds.add(task.getNodeId());
					}
				}else if(task.getProcType().equals(Constant.TASK_TYPE_TEMP)){//临时流程
					if(task.getStatus().equals(Constant.TASK_UNCOMPLETE)){
						Role role = roleDAO.selectRoleById(task.getRoleId());
						if(role!=null)
						nodeNames +=role.getRoleName()+",";
					}else if(task.getStatus().equals(Constant.TASK_COMPLETE)){
						Assignee a = new Assignee();
						a.setTaskId(task.getTaskId());
						List<Assignee> assignees = assigneeDAO.selectAssigneeByResult(a);
						if (assignees.size()>0 ) {
							for (Assignee assignee : assignees) {
								if(assignee.getResult().equals(Constant.TASK_UNCOMPLETE)){
									Role role = roleDAO.selectRoleById(assignee.getUserId());
									if(role!=null)
									nodeNames +=role.getRoleName()+",";
								}
							}
						}
					}
					if(task.getStatus().equals(Constant.TASK_COMPLETE) && task.getIsBack().equals("3")){
						Role role = roleDAO.selectRoleById(procinst.getStartPersonRoleId());
						if(role!=null)
							nodeNames +=role.getRoleName()+",";
					}					
				}
			}
			if(procinst.getProcType().equals(Constant.PROCINST_TYPE_TEMP)){ //临时流程
				if(nodeNames.length() > 0){
					String nodeName[] = nodeNames.split(",");
					Set<String> node = new HashSet<String>();
					for(int i=0;i<nodeName.length;i++){
						node.add(nodeName[i]);
					}
					nodeNames =StringUtils.join(node.toArray(), ",");
				}
			}else if(procinst.getProcType().equals(Constant.PROCINST_TYPE_ORDI)){ //普通流程
				for(String nodeId : nodeIds){
					Task ts = new Task();
					ts.setProcinstId(procinst.getProcinstId());
					ts.setNodeId(nodeId);
					ts.setStatus(Constant.TASK_UNCOMPLETE);
					List<Task> tks = taskService.getTaskByQuery(ts);
					String roleNames="";
					for(Task tk : tks){
						roleNames += tk.getRole().getRoleName() + "，";
					}
					if(roleNames.length() > 0){
						roleNames = roleNames.substring(0,roleNames.length()-1);
					}
					ProcNode pn = procNodeService.getProcNodeByNodeId(nodeId);
					nodeNames += pn.getNodeName() + "(" + roleNames + ")" + "，";
				}
				if(nodeNames.length() > 0){
					nodeNames = nodeNames.substring(0,nodeNames.length()-1);
				}
			}
			procinst.setNodeNames(nodeNames);
			User user = userService.getUserByUserId(procinst.getStartPersonId());
			if(user!=null){
				procinst.setStartPerson(user);
			}
			if(StringUtils.isNotEmpty(procinst.getStartTime())){
				procinst.setStartTime(DateUtil.formatDate(procinst.getStartTime()));
			}
		}
		@SuppressWarnings({ "rawtypes", "unchecked" })
		PageInfo page = new PageInfo(procinsts);
		PagerRS<Procinst> pagerRS = new PagerRS<Procinst>(procinsts, page.getTotal(), page.getPages());
		return pagerRS;
	}

	@Override
	@Transactional
	public void startProcess(Procinst procinst) {
		procinstDAO.insertProcinst(procinst);
		User user = userService.getUserByUserId(procinst.getStartPersonId());
		//通过流程定义获取起始节点
		ProcNode node = new ProcNode();
		node.setProcdefId(procinst.getProcdefId());
		node.setUpNodeId(Constant.NODE_START); 
		node.setIsDeleted(Constant.NOT_DELETE);
		List<ProcNode> procNodes = procNodeService.getProcNodeByQuery(node);	//按流程定义ID获取起始节点
		Task task = new Task();
		task.setProcinstId(procinst.getProcinstId());
		task.setProcinstCode(procinst.getProcinstCode());
		task.setProcType(procinst.getProcType());
		task.setStartTime(DateUtil.dateTimeToString(new Date()));
		task.setStartPersonId(user.getUserId());
		task.setStartDeptId(user.getDepartmentId());
		task.setUpNodeId(Constant.NODE_START);
		taskService.createTask(task, procNodes);
	}

	
	@Override
	public void updateProcinst(Procinst procinst) {
		procinstDAO.updateProcinst(procinst);
	}
	
	
	@Override
	@Transactional
	public PagerRS<Procinst> getProcinstByDept(Query<Procinst> query) {
		if(query != null && query.getPage() >0){				//如果传入offset大于0,则启用分页查询，否则不启用
			PageHelper.startPage(query.getPage(), query.getRows(), true);
		}
		List<Procinst> procinsts = procinstDAO.selectProcinstByDept(query.getT());
		for (Procinst procinst : procinsts) {
			if(StringUtils.isNotEmpty(procinst.getStartTime()))
				procinst.setStartTime(DateUtil.formatDate(procinst.getStartTime()));
			if(StringUtils.isNotEmpty(procinst.getEndTime()))
				procinst.setEndTime(DateUtil.formatDate(procinst.getEndTime()));
			if(StringUtils.isNotEmpty(procinst.getStartPersonId())){
				User user = userService.getUserByUserId(procinst.getStartPersonId());
				if (user!=null) {
					procinst.setStartPerson(user);
				}
			}
			if(procinst.getStatus().equals(Constant.PROCINST_UNCOMPLETE)){
				if(StringUtils.isNotEmpty(procinst.getProcinstId())){
					Task t = new Task();
					t.setProcinstId(procinst.getProcinstId());
					List<Task> tasks = taskDAO.selectTask(t);
					String nodeNames="";
					Set<String> nodeIds = new HashSet<String>();
					for (Task task : tasks) {
						if(task.getProcType().equals(Constant.TASK_TYPE_ORDI)){ //普通流程
							if(task.getStatus().equals(Constant.TASK_UNCOMPLETE)){
								nodeIds.add(task.getNodeId());
							}
						}else if(task.getProcType().equals(Constant.TASK_TYPE_TEMP)){//临时流程
							if(task.getStatus().equals(Constant.TASK_UNCOMPLETE)){
								Role role = roleDAO.selectRoleById(task.getRoleId());
								if(role!=null)
								nodeNames +=role.getRoleName()+",";
							}else if(task.getStatus().equals(Constant.TASK_COMPLETE)){
								Assignee a = new Assignee();
								a.setTaskId(task.getTaskId());
								List<Assignee> assignees = assigneeDAO.selectAssigneeByResult(a);
								if (assignees.size()>0 ) {
									for (Assignee assignee : assignees) {
										if(assignee.getResult().equals(Constant.TASK_UNCOMPLETE)){
											Role role = roleDAO.selectRoleById(assignee.getUserId());
											if(role!=null)
											nodeNames +=role.getRoleName()+",";
										}
									}
								}
							}
							if(task.getStatus().equals(Constant.TASK_COMPLETE) && task.getIsBack().equals("3")){
								Role role = roleDAO.selectRoleById(procinst.getStartPersonRoleId());
								if(role!=null)
									nodeNames +=role.getRoleName()+",";
							}					
						}
					}
					if(procinst.getProcType().equals(Constant.PROCINST_TYPE_TEMP)){ //临时流程
						if(nodeNames.length() > 0){
							String nodeName[] = nodeNames.split(",");
							Set<String> node = new HashSet<String>();
							for(int i=0;i<nodeName.length;i++){
								node.add(nodeName[i]);
							}
							nodeNames =StringUtils.join(node.toArray(), ",");
						}
					}else if(procinst.getProcType().equals(Constant.PROCINST_TYPE_ORDI)){ //普通流程
						for(String nodeId : nodeIds){
							Task ts = new Task();
							ts.setProcinstId(procinst.getProcinstId());
							ts.setNodeId(nodeId);
							ts.setStatus(Constant.TASK_UNCOMPLETE);
							List<Task> tks = taskService.getTaskByQuery(ts);
							String roleNames="";
							for(Task tk : tks){
								roleNames += tk.getRole().getRoleName() + "，";
							}
							if(roleNames.length() > 0){
								roleNames = roleNames.substring(0,roleNames.length()-1);
							}
							ProcNode pn = procNodeService.getProcNodeByNodeId(nodeId);
							nodeNames += pn.getNodeName() + "(" + roleNames + ")" + "，";
						}
						if(nodeNames.length() > 0){
							nodeNames = nodeNames.substring(0,nodeNames.length()-1);
						}
					}
					procinst.setNodeNames(nodeNames);
				}
			}
		}
		@SuppressWarnings({ "rawtypes", "unchecked" })
		PageInfo page = new PageInfo(procinsts);
		PagerRS<Procinst> pagerRS = new PagerRS<Procinst>(procinsts, page.getTotal(), page.getPages());
		return pagerRS;
	}

	@Override
	public List<Map<String, Integer>> getProcinctCountByName(
			Procinst procinst) {
		List<Map<String, Integer>> maps = procinstDAO.selectProcinctCountByName(procinst);
		return maps;
	}

	@Override
	public Map<String, Integer> getProcinctCountByNameTemp(Procinst procinst) {
		Map<String, Integer> map = procinstDAO.selectProcinctCountByNameTemp(procinst);
		return map;
	}

}
