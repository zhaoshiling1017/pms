package com.ducetech.pms.controller;

import java.io.File;
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
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.ducetech.constant.Constant;
import com.ducetech.framework.controller.BaseController;
import com.ducetech.framework.model.PagerRS;
import com.ducetech.framework.model.Query;
import com.ducetech.framework.model.Role;
import com.ducetech.framework.model.User;
import com.ducetech.framework.service.RoleService;
import com.ducetech.pms.model.ProcNode;
import com.ducetech.pms.model.Procinst;
import com.ducetech.pms.model.ProcinstFile;
import com.ducetech.pms.model.Task;
import com.ducetech.pms.service.ProcFileService;
import com.ducetech.pms.service.ProcNodeService;
import com.ducetech.pms.service.ProcinstService;
import com.ducetech.pms.service.TaskService;
import com.ducetech.redis.MyRedisTemplate;
import com.ducetech.util.DateUtil;
import com.ducetech.util.DownloadUtil;
import com.ducetech.util.JsonUtils;

/** 
* @ClassName: TaskController  
* @author gaoy
* @date 2016年9月6日 下午4:25:17 
* @Description: 工单Controller
*/
@Controller
@RequestMapping("/tasks")
public class TaskController extends BaseController{
	
	@Autowired
	private TaskService taskService;
	
	@Autowired
	private ProcinstService procinstService;
	
	@Autowired
	private ProcNodeService procNodeService;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private ProcFileService procFileService;
	
	@Autowired
	private MyRedisTemplate myRedisTemplate;
	
	/** 
	* @Title: task  
	* @param model
	* @return String
	* @Description: 进入工单页面
	*/
	@RequestMapping(value = "/task", method = RequestMethod.GET)
	public String task(Model model, HttpServletRequest request) {
		return "/pms/task/task";
	}
	
	/** 
	* @Title: taskData  
	* @param model
	* @param request
	* @return String
	 * @throws Exception 
	* @Description: 工单列表数据
	*/
	@RequestMapping(value = "/taskData", method = RequestMethod.POST)
	public void taskData(HttpServletRequest request, HttpServletResponse response) throws Exception {
		User user = getLoginUser(request);
		Query<Task> query = JsonUtils.getSearchCondition(Task.class, request);
		//去空字符串
		query.getT().setTaskCode(query.getT().getTaskCode().trim());
//		query.getT().setStatus(Constant.TASK_UNCOMPLETE);
		PagerRS<Task> pageRS = taskService.getTasksByUserId(query, user.getUserId());	//按人员ID获取工单 
		response.getWriter().write(JSON.toJSONString(pageRS));
	}
	
	/** 
	* @Title: taskDetail  
	* @param model
	* @return String
	* @Description: 普通工单详情
	*/
	@RequestMapping(value = "/taskDetail/{pageType}/{procinstId}", method = RequestMethod.GET)
	public String taskDetail(@PathVariable String pageType, @PathVariable String procinstId, Model model) {
		//获取流程及工单信息
		Procinst procinst = procinstService.getProcinstByProcinstId(procinstId);	//流程实例信息
		model.addAttribute("procinst", procinst);
		Task t = new Task();
		List<String> statusMulti = new ArrayList<String>();
		statusMulti.add(Constant.TASK_COMPLETE);
		statusMulti.add(Constant.TASK_NULLIFY);
		t.setStatusMulti(statusMulti);
		t.setProcinstId(procinstId);
		List<Task> tasks = taskService.getTaskByQueryASC(t);	//获取流程实例下的工单已完成的节点信息
		//一个流程实例下的所有附件
		ProcinstFile procinstFile = new ProcinstFile();
		procinstFile.setProcinstId(procinst.getProcinstId());
		List<ProcinstFile> procinstFiles = procFileService.getProcFile(procinstFile);
		model.addAttribute("procinstFiles",procinstFiles);
		model.addAttribute("tasks", tasks);
		model.addAttribute("pageType", pageType);
		UUID uuid = UUID.randomUUID();
		model.addAttribute("uuid", uuid);
		return "/pms/task/task-detail";
	}
	
	/** 
	* @Title: relate  
	* @param model
	* @return String
	* @Description: 与我相关页面
	*/
	@RequestMapping(value = "/relate", method = RequestMethod.GET)
	public String relate(Model model, HttpServletRequest request) {
		return "/pms/task/relate-process";
	}
	
	/** 
	* @Title: relateData  
	* @param model
	* @return String
	 * @throws Exception 
	* @Description: 与我相关数据
	*/
	@RequestMapping(value = "/relateData", method = RequestMethod.POST)
	public void relateData(HttpServletRequest request,HttpServletResponse response) throws Exception {
		User user = getLoginUser(request);
		Query<Procinst> query = JsonUtils.getSearchCondition(Procinst.class, request);
		query.getT().setStatus(Constant.PROCINST_UNCOMPLETE);
		//去空字符串
		query.getT().setProcinstCode(query.getT().getProcinstCode().trim());
		query.getT().setProcName(query.getT().getProcName().trim());
		PagerRS<Procinst> pageRS = procinstService.getMyRelatedByUserId(query, user.getUserId());	//按userId获取与自己相关的流程实例
		response.getWriter().write(JSON.toJSONString(pageRS));
	}
	
	/** 
	* @Title: relateDetail  
	* @param model
	* @return String
	* @Description: 与我相关详情
	*/
	@RequestMapping(value = "/relateDetail", method = RequestMethod.GET)
	public String relateDetail(Model model) {
		
		return "/pms/task/relate-process-detail";
	}
	
	/** 
	* @Title: taskHandle  
	* @param model
	* @return String
	* @Description: 工单处理页面
	*/
	@RequestMapping(value = "/taskHandle/{pageType}/{taskId}", method = RequestMethod.GET)
	public String taskHandle(@PathVariable String pageType, @PathVariable String taskId, Model model) {
		//获取流程及工单信息
		Task task = taskService.getTaskByTaskId(taskId);	//工单信息
		model.addAttribute("task", task);
		if(StringUtils.isNoneEmpty(task.getUpTaskId())){
			Task upTask = taskService.getTaskByTaskId(task.getUpTaskId());
			model.addAttribute("uptask", upTask);
		}
		Procinst procinst = procinstService.getProcinstByProcinstId(task.getProcinstId());	//流程实例信息
		model.addAttribute("procinst", procinst);
		Task t = new Task();
		List<String> statusMulti = new ArrayList<String>();
		statusMulti.add(Constant.TASK_COMPLETE);
		statusMulti.add(Constant.TASK_NULLIFY);
		t.setStatusMulti(statusMulti);
		t.setProcinstId(task.getProcinstId());
		List<Task> tasks = taskService.getTaskByQuery(t);	//获取流程实例下的工单已完成的节点信息
		model.addAttribute("tasks", tasks);
		ProcNode upNode = procNodeService.getProcNodeByNodeId(task.getUpNodeId());	//获取上一节点信息
		model.addAttribute("upNode", upNode);
		List<ProcNode> nextNodes = new ArrayList<ProcNode>();	//多个下个节点
		if(StringUtils.isNotEmpty(task.getNextNodeId())){
			String [] nodeIds = task.getNextNodeId().split(",");
			for(String nodeId : nodeIds){
				if(!Constant.NODE_END.equals(nodeId)){
					ProcNode nextNode = procNodeService.getProcNodeByNodeId(nodeId);	//获取下一节点
					List<Role> roles = new ArrayList<Role>();
					roles = roleService.getRolesByNodeId(nextNode.getNodeId());
					nextNode.setRoles(roles);
					nextNodes.add(nextNode);
				}
			}
		}
		model.addAttribute("nextNodes", nextNodes);
		//获取此工单流程的记录
		String upTaskId = task.getUpTaskId();
		List<String> taskIds = new ArrayList<String>();
		while (StringUtils.isNotEmpty(upTaskId)) {
			Task upTask = taskService.getTaskByTaskId(upTaskId);
			if(upTask!=null){
				taskIds.add(upTask.getTaskId());
				upTaskId = upTask.getUpTaskId();
			}
		}
		List<Task> recordTask = taskService.getTaskByIds(taskIds);
		model.addAttribute("recordTask", recordTask);
		taskIds.add(taskId);
		List<ProcinstFile> procinstFiles = procFileService.getProcFileByTaskIds(taskIds);	//获取此工单下的附件
		model.addAttribute("procinstFiles", procinstFiles);
		ProcinstFile file = new ProcinstFile();
		file.setTaskId("0");
		List<ProcinstFile> fillingFiles = procFileService.getProcFile(file);	//流程后补附件
		model.addAttribute("fillingFiles", fillingFiles);
		model.addAttribute("pageType", pageType);
		UUID uuid = UUID.randomUUID();
		model.addAttribute("uuid", uuid);
		return "/pms/task/task-handle";
	}
	
	/** 
	* @Title: taskDispose  
	* @param taskId
	* @param model
	* @return void
	 * @throws IOException 
	* @Description: 工单处理
	*/
	@RequestMapping(value = "/taskDispose", method = RequestMethod.POST)
	public void taskDispose(Task task,HttpServletRequest request, HttpServletResponse response) throws IOException {
		User user = getLoginUser(request);
		if(StringUtils.isNotEmpty(task.getTaskId())){
			Task t = taskService.getTaskByTaskId(task.getTaskId());
			if(t.getStatus().equals(Constant.TASK_UNCOMPLETE)){
				task.setStatus(Constant.TASK_COMPLETE);
				task.setDisposePersionId(user.getUserId());
				task.setDisposeTime(DateUtil.dateTimeToString(new Date()));
				task.setEndTime(DateUtil.dateTimeToString(new Date()));
				taskService.assignTask(task);
				response.getWriter().write("{\"1\":\"" + "提交成功" + "\"}");
				//response.getWriter().write("{\"result\":\"" + "提交成功" + "\"" + ",\"id\":\"" + task.getTaskId() + "\"" + "}");
			}else if(t.getStatus().equals(Constant.TASK_COMPLETE)){
				response.getWriter().write("{\"0\":\"" + "工单已经提交过" + "\"}");
			}else if(t.getStatus().equals(Constant.TASK_NULLIFY)){
				response.getWriter().write("{\"0\":\"" + "工单已经退回过" + "\"}");
			}
		}else{
			response.getWriter().write("{\"0\":\"" + "稍后重试" + "\"}");
		}
	}
	
	/** 
	* @Title: returnTask  
	* @param task
	* @param request
	* @param response
	* @throws IOException
	* @Description: 工单退回
	*/
	@RequestMapping(value = "/returnTask", method = RequestMethod.POST)
	public void returnTask(Task task,HttpServletRequest request, HttpServletResponse response) throws IOException {
		User user = getLoginUser(request);
		if(StringUtils.isNotEmpty(task.getTaskId())){
			Task tk = taskService.getTaskByTaskId(task.getTaskId());
			if(tk.getStatus().equals(Constant.TASK_UNCOMPLETE)){
				tk.setBackPersionId(user.getUserId());
				tk.setBackComment(task.getBackComment());
				tk.setDisposePersionId(user.getUserId());
				taskService.returnTask(tk);
				response.getWriter().write("{\"1\":\"" + "提交成功" + "\"}");
			}else if(tk.getStatus().equals(Constant.TASK_COMPLETE)){
				response.getWriter().write("{\"0\":\"" + "工单已经提交过" + "\"}");
			}else if(tk.getStatus().equals(Constant.TASK_NULLIFY)){
				response.getWriter().write("{\"0\":\"" + "工单已经退回过" + "\"}");
			}
		}else{
			response.getWriter().write("{\"0\":\"" + "稍后重试" + "\"}");
		}
	}
	
	/** 
	* @Title: getProcdefData  
	* @param procdefId
	* @param request
	* @param response
	* @throws IOException
	* @Description: 根据流程定义ID获取节点
	*/
	@RequestMapping(value = "/getProcdefData", method = RequestMethod.POST)
	public void getProcdefData(String procdefId,HttpServletRequest request, HttpServletResponse response) throws IOException {
		ProcNode procNode = new ProcNode();
		procNode.setProcdefId(procdefId);
		List<ProcNode> procNodes = procNodeService.getProcNodeByQuery(procNode);
		response.getWriter().write(JSON.toJSONString(procNodes));
	}
	
	/** 
	* @Title: getUnNodeData  
	* @param procinstId
	* @param request
	* @param response
	* @throws IOException
	* @Description: 获取未完成节点数据
	*/
	@RequestMapping(value = "/getUnNodeData", method = RequestMethod.POST)
	public void getUnNodeData(String procinstId, String procdefId, HttpServletRequest request, HttpServletResponse response) throws IOException {
		Map<String, String> nodeMap = new HashMap<String, String>(); //默认0未有工单(灰色)、1已完成(绿色)、2未完成(红色)、3前面有未完成工单节点(黄色)
		ProcNode procNode = new ProcNode();
		procNode.setProcdefId(procdefId);
		List<ProcNode> data = procNodeService.getProcNodeByQuery(procNode);//流程实例下的所有节点
		for(ProcNode pn : data){
			nodeMap.put("node" + pn.getNodeId(), "0");
		}
		ProcNode procNodeOne = new ProcNode();
		String nextNode = "0";
		for(int j=0;j<data.size();j++){
			if(data.get(j).getUpNodeId().equals(nextNode)){
				nextNode = data.get(j).getNextNodeId();
				procNodeOne=data.get(j);
				data.remove(data.get(j));
				break;
			}
		}
		Task task1=new Task();
		task1.setProcinstId(procinstId);
		task1.setNodeId(procNodeOne.getNodeId());
		List<Task> tasks1=taskService.getTaskByQuery(task1);
		for (Task task2 : tasks1) {
			if ("".equals(task2.getStatus())) {
				nodeMap.put("node" + procNodeOne.getNodeId(), "0");
			}else if ("0".equals(task2.getStatus())) {
				nodeMap.put("node" + procNodeOne.getNodeId(), "2");
			}else if ("1".equals(task2.getStatus())) {
				nodeMap.put("node" + procNodeOne.getNodeId(), "1");
			}
		}
		String[] arr = nextNode.split(",");
		for (ProcNode pro : data) {
			for (String string : arr) {
				if (pro.getNodeId().equals(string)) {
					Task task = new Task();
					task.setProcinstId(procinstId);
					task.setNodeId(string);
					List<Task> tasks = taskService.getTaskByQuery(task);
					for (Task task2 : tasks) {
						String up = nodeMap.get("node" + pro.getUpNodeId());
						if (task2.getStatus().equals("0")) {
							if (up.equals("0")) {
								nodeMap.put("node" + pro.getNodeId(), "0");
							} else if (up.equals("1")) {
								nodeMap.put("node" + pro.getNodeId(), "2");
							} else if (up.equals("2")) {
								nodeMap.put("node" + pro.getNodeId(), "3");
							} else if (up.equals("3")) {
								nodeMap.put("node" + pro.getNodeId(), "3");
							} else {
								nodeMap.put("node" + pro.getNodeId(), "2");
							}
							break;
							
						}else{
							// 默认0未有工单(灰色)、1已完成(绿色)、2未完成(红色)、3前面有未完成工单节点(黄色)
							if (up == null) {
								nodeMap.put("node" +pro.getNodeId(), "0");
							} else if (up.equals("0")) {
								nodeMap.put("node" + pro.getNodeId(), "0");
							} else if (up.equals("1") && "0".equals(task2.getStatus())) {
								nodeMap.put("node" + pro.getNodeId(), "2");
							} else if (up.equals("1") && "1".equals(task2.getStatus())) {
								nodeMap.put("node" + pro.getNodeId(), "1");
							} else if (up.equals("2") && "1".equals(task2.getStatus())) {
								nodeMap.put("node" + pro.getNodeId(), "3");
							} else if (up.equals("2") && "0".equals(task2.getStatus())) {
								nodeMap.put("node" + pro.getNodeId(), "3");
							} else if (up.equals("3") && "1".equals(task2.getStatus())) {
								nodeMap.put("node" + pro.getNodeId(), "3");
							} else if (up.equals("3") && "0".equals(task2.getStatus())) {
								nodeMap.put("node" + pro.getNodeId(), "3");
							}
						}
					}
				}
			}
			if (arr.length>1) {
				for(int i=0;i<arr.length-1;i++){
					arr[i] = arr[i+1];
				}
				arr[arr.length-1] = "";
			}else{
				arr = pro.getNextNodeId().split(",");
			}
		}
		response.getWriter().write(JSON.toJSONString(nodeMap));
	}
	
	/** 
	* @Title: downloadTaskFile  
	* @param fileId
	* @param response
	* @param request
	* @throws IOException
	* @Description: 附件下载
	*/
	@RequestMapping(value = "/downloadTaskFile/{fileId}", method = RequestMethod.GET)
	@ResponseBody
	public void downloadTaskFile(@PathVariable String fileId, HttpServletResponse response, HttpServletRequest request) throws IOException {
		ProcinstFile procinstFile = procFileService.getProcFileById(fileId);
		File file = new File(procinstFile.getPath());
		String fileName = procinstFile.getFileName();
		DownloadUtil downloadUtil = new DownloadUtil();
		downloadUtil.prototypeDownload(file, fileName, response, false);
	}
	
	/** 
	* @Title: getDate  
	* @param procdefId
	* @param request
	* @param response
	* @throws IOException
	* @Description: 获取当前时间
	*/
	@RequestMapping(value = "/getDate", method = RequestMethod.GET)
	public void getDate(HttpServletResponse response) throws IOException {
		response.getWriter().write(JSON.toJSONString(DateUtil.dateTimeToString(new Date())));
	}
	
	/** 
	* @Title: uploadTaskFile  
	* @param type
	* @param id
	* @param file
	* @param request
	* @param response
	* @throws IOException
	* @Description: 流程详情上传文件
	*/
	@RequestMapping(value = "/uploadTaskFile", method = RequestMethod.POST)
	public void uploadTaskFile(String procinstId, String file, String uuid,HttpServletRequest request, HttpServletResponse response) throws IOException{
		User user = getLoginUser(request);
		if(StringUtils.isNotEmpty(procinstId)){
			Procinst procinst = procinstService.getProcinstByProcinstId(procinstId);
			if(myRedisTemplate.getItem(uuid)!=null){
				@SuppressWarnings("unchecked")
				List<Map<String, String>> lists = (List<Map<String, String>>) myRedisTemplate.getItem(uuid);
				ProcinstFile procinstFile = new ProcinstFile();
				for (Map<String, String> map : lists) {
					Set<String> keys = map.keySet();
					for (String key : keys) {
						procinstFile.setPath(map.get(key));
						procinstFile.setFileName(key);
					}
					procinstFile.setCreateById(user.getUserId());
					procinstFile.setUploadTime(DateUtil.dateTimeToString(new Date()));
					if(procinst!=null){
						procinstFile.setProcinstId(procinst.getProcinstId());
						procinstFile.setProcType(procinst.getProcType());
						procinstFile.setTaskId("0");
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
							procinstFile.setProcinstId(procinst.getProcinstId());
							procinstFile.setProcType(procinst.getProcType());
							procinstFile.setTaskId("0");
							procFileService.addProcFile(procinstFile);
						}
					}
				}
			}*/
			response.getWriter().write("{\"1\":\"" + "上传成功" + "\"}");
		}else {
			response.getWriter().write("{\"0\":\"" + "上传失败" + "\"}");
		}
	}
}
