package com.ducetech.pms.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
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
import com.ducetech.framework.model.Department;
import com.ducetech.framework.model.PagerRS;
import com.ducetech.framework.model.Query;
import com.ducetech.framework.model.Role;
import com.ducetech.framework.model.User;
import com.ducetech.framework.service.DepartmentService;
import com.ducetech.framework.service.RoleService;
import com.ducetech.framework.service.UserService;
import com.ducetech.pms.model.Message;
import com.ducetech.pms.model.MsgFile;
import com.ducetech.pms.model.MsgRole;
import com.ducetech.pms.service.MessageService;
import com.ducetech.pms.service.MsgFileService;
import com.ducetech.pms.service.MsgRoleService;
import com.ducetech.redis.MyRedisTemplate;
import com.ducetech.util.CookieUtil;
import com.ducetech.util.DateUtil;
import com.ducetech.util.DownloadUtil;
import com.ducetech.util.JsonUtils;

/**
 * 
* @ClassName: MessageController 
* @Description: 集团消息Controller 
* @author yett 
* @date 2016年9月6日 下午2:38:26 
*
 */
@Controller
@RequestMapping("/messages")
public class MessageController extends BaseController{

	@Autowired
	MessageService messageService;
	@Autowired
	MsgRoleService msgRoleService;
	@Autowired
	UserService userService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private DepartmentService departmentService;
	@Autowired
	private MsgFileService msgFileService;
	@Autowired
	private MyRedisTemplate myRedisTemplate;
	
	
	/**
	 * 
	* @Title: index 
	* @Description: 消息通知首页 
	* @param @param model
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	@RequestMapping(value = "/message", method = RequestMethod.GET)
	public String index(Model model,HttpServletRequest request) {
		return "/pms/message/message";
	}
	
	/**
	 * @throws Exception 
	 * 
	* @Title: messageData 
	* @Description: 消息通知首页数据
	* @param @param model
	* @param @param request
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	@RequestMapping(value = "/messageData", method = RequestMethod.POST)
	public void messageData(Model model,HttpServletRequest request,HttpServletResponse response) throws Exception {
		User user = getLoginUser(request);
		Query<MsgRole> query = JsonUtils.getSearchCondition(MsgRole.class, request);
		query.getT().setIsDeleted(Constant.NOT_DELETE);
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
		response.getWriter().write(JSON.toJSONString(pageRS));
	}
	
	/**
	 * 
	* @Title: messageDetail 
	* @Description: 消息通知详情
	* @param @param model
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	@RequestMapping(value = "/messageDetail/{type}/{id}", method = RequestMethod.GET)
	public String messageDetail(@PathVariable String id,@PathVariable String type,Model model) {
		MsgRole msgRole = msgRoleService.getMsgRoleById(id);
		List<MsgFile> msgFiles =new ArrayList<MsgFile>();
		if(msgRole!=null){
			if(msgRole.getStatus().equals(Constant.MSG_ROLE_UNREAD)){
				msgRole.setStatus(Constant.MSG_ROLE_READ);
				msgRole.setReadTime(DateUtil.dateTimeToString(new Date()));
				msgRoleService.updateMsgRole(msgRole);
			}
			Message message = messageService.getMessageById(msgRole.getMsgId());
			message.setPublishTime(DateUtil.formatDate(message.getPublishTime()));
			message.setIsDeleted(Constant.NOT_DELETE);
			User user = userService.getUserByUserId(message.getPublisherId());
			Department dept = departmentService.getDepartmentById(user.getDepartmentId());
			user.setDepartment(dept);
			message.setUser(user);
			msgRole.setMessage(message);
			//附件信息
			MsgFile msgFile = new MsgFile();
			msgFile.setMsgId(msgRole.getMsgId());
			msgFiles = msgFileService.getMsgFile(msgFile);
			for (MsgFile mf : msgFiles) {
				mf.setCreateBy(userService.getUserByUserId(mf.getCreateById()));
				mf.setUploadTime(DateUtil.formatDate(mf.getUploadTime()));
			}
		}else {
			msgRole = new MsgRole();
		}
		
		model.addAttribute("msgFiles", msgFiles);
		model.addAttribute("type", type);
		model.addAttribute("msgRole", msgRole);
		return "/pms/message/message-detail";
	}
	
	/**
	 * 
	* @Title: messagePublish 
	* @Description: 集团消息发布
	* @param @param model
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	@RequestMapping(value = "/messagePublish", method = RequestMethod.GET)
	public String messagePublish(Model model,HttpServletRequest request) {
		User user = getLoginUser(request);
		List<Role> roles = roleService.getAllRoles();
		UUID uuid = UUID.randomUUID();
		model.addAttribute("uuid", uuid);	
		model.addAttribute("roles", roles);
		model.addAttribute("user", user);
		return "/pms/message/publish-message";
	}
	
	/**
	 * @throws IOException 
	 * 
	* @Title: releaseMessage 
	* @Description: 集团消息发送给对应角色的人员 
	* @param @param message
	* @param @param model
	* @param @param request
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	@RequestMapping(value = "/releaseMessage", method = RequestMethod.POST)
	public void releaseMessage (Message message,HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		if(message!=null){
			List<String> list = new ArrayList<String>();
			String roleIds = request.getParameter("roleIds");
			if(roleIds.length()>0){
				if(roleIds.substring(0, 1).equals(",")){
					roleIds=roleIds.substring(1,roleIds.length());
				}
				String a[] = roleIds.split(",");  
				for(int i=0;i<a.length;i++){
					list.add(a[i]);
				}
				String userId = CookieUtil.getLoginUserId(request);
				message.setPublishTime(DateUtil.dateTimeToString(new Date()));
				message.setPublisherId(userId);
				message.getMsgTitle();
				message.getContent();
				message.setStatus(Constant.MSG_SEND_IN); // 0发送失败  、 1 发送中 、 2 发送成功
				messageService.addMessage(message);
				msgRoleService.getMsgRole(list, message);
				response.getWriter().write("{\"1\":\"" + "发送成功" + "\""
						+",\"id\":\"" + message.getMsgId() + "\""
						+ "}");
			}
		}else {
			response.getWriter().write("{\"0\":\"" + "发送失败" + "\"}");
		}
	}
	
	/**
	 * @throws IOException 
	 * 
	* @Title: delMessage 
	* @Description: 消息通知删除
	* @param @param msgId
	* @param @param model
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	@RequestMapping(value = "/delMessage/{id}", method = RequestMethod.GET)
	public void delMessage(@PathVariable String id ,HttpServletRequest request,
			HttpServletResponse response) throws IOException{
		System.out.println(id);
		MsgRole msgRole = msgRoleService.getMsgRoleById(id);
		if(msgRole.getIsDeleted().equals(Constant.NOT_DELETE)){
			msgRole.setIsDeleted(Constant.DELETE);
			msgRole.setDelTime(DateUtil.dateTimeToString(new Date()));
			msgRoleService.deleteMsgRole(msgRole);
			response.getWriter().write("{\"1\":\"" + "删除成功" + "\"}");
		}else {
			response.getWriter().write("{\"0\":\"" + "消息已删除" + "\"}");
		}	
	}
	
	/**
	 * 
	* @Title: readMessage 
	* @Description: 标记为已读
	* @param @param id
	* @param @param model
	* @param @param request
	* @param @param response
	* @param @throws IOException    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	@RequestMapping(value = "/readMessage/{id}", method = RequestMethod.GET)
	public void readMessage(@PathVariable String id ,HttpServletRequest request,
			HttpServletResponse response) throws IOException{
		MsgRole msgRole = msgRoleService.getMsgRoleById(id);
		if(msgRole.getStatus().equals(Constant.MSG_ROLE_UNREAD)){
			msgRole.setStatus(Constant.MSG_ROLE_READ);
			msgRole.setReadTime(DateUtil.dateTimeToString(new Date()));
			msgRoleService.updateMsgRole(msgRole);
			response.getWriter().write("{\"1\":\"" + "标记已读" + "\"}");
		}else {
			response.getWriter().write("{\"0\":\"" + "消息已标记" + "\"}");
		}
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
	@RequestMapping(value = "/uploadMessageFile", method = RequestMethod.POST)
	public void uploadMessageFile(String uuid,String id,String file,HttpServletRequest request,
			HttpServletResponse response) throws IOException{
		if(StringUtils.isNotEmpty(id)){
			Message message = messageService.getMessageById(id);
			if(myRedisTemplate.getItem(uuid)!=null){    //判断redis里是否存在对应uuid
				@SuppressWarnings("unchecked")
				List<Map<String, String>> lists = (List<Map<String, String>>) myRedisTemplate.getItem(uuid);  
				for (Map<String, String> map : lists) {
					MsgFile msgFile = new MsgFile();
					Set<String> keys = map.keySet();
					for (String key : keys) {
						msgFile.setPath(map.get(key));
						msgFile.setFileName(key);
					}
					msgFile.setMsgId(message.getMsgId());
					msgFile.setUploadTime(DateUtil.dateTimeToString(new Date()));
					msgFile.setCreateById(message.getPublisherId());
					msgFileService.addMsgFile(msgFile);
				}
			}
/*			if(file.length()>2){
				file = file.replace("[", "");
				file = file.replace("]", "");
				file = file.replace("{", "");
				file = file.replace("}", "");
				file = file.substring(1, file.length()-1);
				String files[] = file.split("\",\"");
				for (String string : files) {
					String path[] = string.split(",");
					if(path.length>0){
						MsgFile msgFile = new MsgFile();
						msgFile.setMsgId(message.getMsgId());
						msgFile.setUploadTime(DateUtil.dateTimeToString(new Date()));
						msgFile.setPath(path[0]);
						msgFile.setCreateById(message.getPublisherId());
						msgFile.setFileName(path[1]);
						msgFileService.addMsgFile(msgFile);
					}
				}
			}*/
			response.getWriter().write("{\"1\":\"" + "上传成功" + "\"}");
		}else {
			response.getWriter().write("{\"0\":\"" + "上传失败" + "\"}");
		}
	}
	
	/**
	 * 
	* @Title: deleteMessageFile 
	* @Description: 上传过程中，附件删除同时删除对应的redis缓存内信息 
	* @param @param uuid
	* @param @param fileName
	* @param @param request
	* @param @param response
	* @param @throws IOException    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	@RequestMapping(value = "/deleteMessageFile", method = RequestMethod.POST)
	public void deleteMessageFile(String uuid,String fileName,HttpServletRequest request,HttpServletResponse response) throws IOException{
		if(StringUtils.isNoneEmpty(uuid)){
			@SuppressWarnings("unchecked")
			List<Map<String, String>> lists = (List<Map<String, String>>) myRedisTemplate.getItem(uuid);  
			for (int i = 0; i < lists.size(); i++) {
				if(lists.get(i).get(fileName)!=null){
					lists.remove(i);
				}
			}
			myRedisTemplate.setItem(uuid, lists);
			response.getWriter().write("{\"1\":\"" + "删除成功" + "\"}");
		}else {
			response.getWriter().write("{\"0\":\"" + "删除失败" + "\"}");
		}
			
	}
	
	
	/**
	 * 
	* @Title: getMessageFile 
	* @Description: 获取附件
	* @param @param msgId
	* @param @param response
	* @param @param request
	* @param @return
	* @param @throws IOException    设定文件 
	* @return List<MsgFile>    返回类型 
	* @throws
	 */
	/*@RequestMapping(value = "/getMessageFile", method = RequestMethod.POST)
	@ResponseBody
	public List<MsgFile> getMessageFile(String msgId, HttpServletResponse response, HttpServletRequest request) throws IOException {
		MsgFile msgFile = new MsgFile();
		msgFile.setMsgId(msgId);
		List<MsgFile> msgFiles = msgFileService.getMsgFile(msgFile);
		return msgFiles;
	}*/
	
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
	@RequestMapping(value = "/downloadMessageFile/{fileId}", method = RequestMethod.GET)
	@ResponseBody
	public void downloadMessageFile(@PathVariable String fileId, HttpServletResponse response, HttpServletRequest request) throws IOException {
		MsgFile msgFile = msgFileService.getMsgFileById(fileId);
		File file = new File(msgFile.getPath());
		String fileName = msgFile.getFileName();
		DownloadUtil downloadUtil = new DownloadUtil();
		downloadUtil.prototypeDownload(file, fileName, response, false);
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
	@RequestMapping(value = "/judgeMessageFile/{fileId}", method = RequestMethod.GET)
	public void judgeMessageFile(@PathVariable String fileId, HttpServletResponse response, HttpServletRequest request) throws IOException{
		MsgFile msgFile = msgFileService.getMsgFileById(fileId);
		File file = new File(msgFile.getPath());
		if(!file.exists()){
			response.getWriter().write("{\"0\":\"" + "文件不存在" + "\"}");
		}else{
			response.getWriter().write("{\"1\":\"" + "文件存在" + "\"}");
		}    
	}
	
	
	
	/**
	 * 
	* @Title: groupMessages 
	* @Description: 集团消息管理
	* @param @param model
	* @param @param request
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	@RequestMapping(value = "/groupMessages", method = RequestMethod.GET)
	public String groupMessages(Model model,HttpServletRequest request) {
		return "/pms/message/group-messages";
	}
	
	/**
	 * 
	* @Title: groupMessagesData 
	* @Description: 集团消息管理数据
	* @param @param model
	* @param @param request
	* @param @param response
	* @param @throws Exception    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	@RequestMapping(value = "/groupMessagesData", method = RequestMethod.POST)
	public void groupMessagesData(Model model,HttpServletRequest request,HttpServletResponse response) throws Exception {
		User user = getLoginUser(request);
		Query<Message> query = JsonUtils.getSearchCondition(Message.class, request);
		query.getT().setIsDeleted(Constant.NOT_DELETE);
		query.getT().setPublisherId(user.getUserId());
		PagerRS<Message> pageRS = messageService.getMessageByUserId(query,user.getUserId());
		response.getWriter().write(JSON.toJSONString(pageRS));
	}
	
	/**
	 * 
	* @Title: delGroupMessages 
	* @Description: 集团消息删除
	* @param @param id
	* @param @param request
	* @param @param response
	* @param @throws IOException    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	@RequestMapping(value = "/delGroupMessages/{id}", method = RequestMethod.GET)
	public void delGroupMessages(@PathVariable String id ,HttpServletRequest request,
			HttpServletResponse response) throws IOException{
		Message message = messageService.getMessageById(id);
		if(message.getIsDeleted().equals(Constant.NOT_DELETE)){
			message.setDelTime(DateUtil.dateTimeToString(new Date()));
			message.setIsDeleted(Constant.DELETE);
			messageService.deleteMessage(message);
			response.getWriter().write("{\"result\":\"" + "删除成功" + "\"}");
		}else {
			response.getWriter().write("{\"result\":\"" + "消息已删除" + "\"}");
		}
	}
	
	/**
	 * 
	* @Title: groupMessagesDetail 
	* @Description: 集团通知详情
	* @param @param id
	* @param @param model
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	@RequestMapping(value = "/groupMessagesDetail/{id}", method = RequestMethod.GET)
	public String groupMessagesDetail(@PathVariable String id,Model model) {
		Message message = messageService.getMessageById(id);
		List<MsgFile> msgFiles =new ArrayList<MsgFile>();
		if(message!=null){
			message.setPublishTime(DateUtil.formatDate(message.getPublishTime()));
			User user1 = userService.getUserByUserId(message.getPublisherId());
			Department dept = departmentService.getDepartmentById(user1.getDepartmentId());
			user1.setDepartment(dept);
			message.setUser(user1);
			List<MsgRole> msgRoles = msgRoleService.getMsgRoleByMsgId(message.getMsgId());
			String roleName = "";
			for (MsgRole msgRole : msgRoles) {
				User u = userService.getUserByUserId(msgRole.getUserId());
				roleName += u.getName() +",";
			}
			if(roleName.length()>0){
				roleName = roleName.substring(0,roleName.length()-1);
			}
			message.setRoleName(roleName);
			//附件信息
			MsgFile msgFile = new MsgFile();
			msgFile.setMsgId(message.getMsgId());
			msgFiles = msgFileService.getMsgFile(msgFile);
			for (MsgFile mf : msgFiles) {
				mf.setCreateBy(userService.getUserByUserId(mf.getCreateById()));
				mf.setUploadTime(DateUtil.formatDate(mf.getUploadTime()));
			}
		}else {
			message = new Message();
		}
		model.addAttribute("msgFiles", msgFiles);
		model.addAttribute("message", message);
		return "/pms/message/group-message-detail";
	}
}
