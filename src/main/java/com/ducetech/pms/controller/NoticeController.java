package com.ducetech.pms.controller;

import java.io.IOException;
import java.util.Date;

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
import com.ducetech.framework.model.PagerRS;
import com.ducetech.framework.model.Query;
import com.ducetech.framework.model.Role;
import com.ducetech.framework.model.User;
import com.ducetech.framework.service.RoleService;
import com.ducetech.pms.model.Notice;
import com.ducetech.pms.model.NoticeRole;
import com.ducetech.pms.model.Procdef;
import com.ducetech.pms.model.Procinst;
import com.ducetech.pms.model.Task;
import com.ducetech.pms.service.NoticeRoleService;
import com.ducetech.pms.service.NoticeService;
import com.ducetech.pms.service.ProcdefService;
import com.ducetech.pms.service.ProcinstService;
import com.ducetech.pms.service.TaskService;
import com.ducetech.util.DateUtil;
import com.ducetech.util.JsonUtils;

/** 
* @ClassName: Notice  
* @author gaoy
* @date 2016年9月6日 下午4:42:51 
* @Description: 系统通知
*/
@Controller
@RequestMapping("notices")
public class NoticeController extends BaseController {
	
	@Autowired
	private NoticeService noticeService;
	@Autowired
	private NoticeRoleService noticeRoleService;
	@Autowired
	private ProcinstService procinstService;
	@Autowired
	private ProcdefService procdefService;
	@Autowired
	private TaskService taskService;
	@Autowired
	private RoleService roleService;
	
	/** 
	* @Title: notice  
	* @param model
	* @return String
	* @Description: 系统通知首页
	*/
	@RequestMapping(value = "/notice", method = RequestMethod.GET)
	public String notice(Model model) {
		return "/pms/notice/notice";
	}
	
	/** 
	* @Title: noticeData  
	* @return void
	 * @throws Exception 
	 * @Description: 系统通知首页数据
	*/
	@RequestMapping(value = "/noticeData", method = RequestMethod.POST)
	public void noticeData(HttpServletRequest request, HttpServletResponse response) throws Exception {
		User user = getLoginUser(request);
		Query<NoticeRole> query = JsonUtils.getSearchCondition(NoticeRole.class, request);
		query.getT().setUserId(user.getUserId());
		query.getT().setIsDeleted(Constant.NOT_DELETE);
		PagerRS<NoticeRole> pagerRS = noticeRoleService.getNoticeRoleByPager(query);
		for (NoticeRole noticeRole : pagerRS.getResults()) {
			if(StringUtils.isNotEmpty(noticeRole.getReceiveTime())){
				noticeRole.setReceiveTime(DateUtil.formatDate(noticeRole.getReceiveTime()));
			}
			Notice notice = noticeService.gettNoticeById(noticeRole.getNoticeId());
			if(StringUtils.isNotEmpty(notice.getSendTime())){
				notice.setSendTime(DateUtil.formatDate(notice.getSendTime()));
			}		
			if(StringUtils.isNotEmpty(notice.getProcdefId())){
				Procdef procdef = procdefService.getProcdefByProcdefId(notice.getProcdefId());
				if(procdef!=null){
					notice.setProcdef(procdef);
				}
			}
			if(StringUtils.isNotEmpty(notice.getProcinstId())){
				Procinst procinst = procinstService.getProcinstByProcinstId(notice.getProcinstId());
				if(procinst!=null){
					notice.setProcinst(procinst);
				}
			}
			if(StringUtils.isNotEmpty(notice.getTaskId())){
				Task task = taskService.getTaskByTaskId(notice.getTaskId());
				if(task!=null){
					notice.setTask(task);
				}
			}
			if(StringUtils.isNotEmpty(notice.getRoleId())){
				Role role = roleService.getRoleById(notice.getRoleId());
				if(role!=null){
					notice.setRole(role);
				}
			}
			noticeRole.setNotice(notice);
		}
		response.getWriter().write(JSON.toJSONString(pagerRS));
	}
	
	/** 
	* @Title: noticeUnCount  
	* @param request
	* @param response
	* @throws Exception
	* @Description: 未读消息数量
	*/
	@RequestMapping(value = "/noticeUnCount", method = RequestMethod.POST)
	public void noticeUnCount(HttpServletRequest request, HttpServletResponse response) throws Exception {
		User user = getLoginUser(request);
		Query<NoticeRole> query = new Query<NoticeRole>();
		NoticeRole noticeRole = new NoticeRole();
		query.setT(noticeRole);
		query.getT().setUserId(user.getUserId());
		query.getT().setStatus(Constant.NOTICE_ROLE_UNREAD);
		query.getT().setIsDeleted(Constant.NOT_DELETE);
		PagerRS<Notice> pagerRS = noticeService.getNoticeByNoticeRole(query);
		response.getWriter().write(JSON.toJSONString(pagerRS.getCount()));
	}
	
	/**
	 * @throws IOException 
	 * 
	* @Title: delMessage 
	* @Description: 系统通知删除
	* @param @param msgId
	* @param @param model
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	@RequestMapping(value = "/delNotice/{id}", method = RequestMethod.GET)
	public void delNotice(@PathVariable String id ,HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		NoticeRole noticeRole = noticeRoleService.getNoticeRoleById(id);
		if(noticeRole.getIsDeleted().equals(Constant.NOT_DELETE)){
			noticeRole.setIsDeleted(Constant.DELETE);
			noticeRole.setDelTime(DateUtil.dateTimeToString(new Date()));
			noticeRoleService.updateNoticeRole(noticeRole);
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
	@RequestMapping(value = "/readNotice/{id}", method = RequestMethod.GET)
	public void readNotice(@PathVariable String id ,HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		NoticeRole noticeRole = noticeRoleService.getNoticeRoleById(id);
		if(noticeRole.getStatus().equals(Constant.MSG_ROLE_UNREAD)){
			noticeRole.setStatus(Constant.MSG_ROLE_READ);
			noticeRole.setReadTime(DateUtil.dateTimeToString(new Date()));
			noticeRoleService.updateNoticeRole(noticeRole);
			response.getWriter().write("{\"1\":\"" + "标记已读" + "\"}");
		}else {
			response.getWriter().write("{\"0\":\"" + "消息已标记" + "\"}");
		}
	}
	
	/**
	 * 
	* @Title: getNoticeById 
	* @Description: 获得一条消息的详细内容 
	* @param @param id
	* @param @param request
	* @param @param response
	* @param @throws IOException    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	@RequestMapping(value = "/getNoticeById/{id}", method = RequestMethod.GET)
	public void getNoticeById(@PathVariable String id ,HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		NoticeRole noticeRole = noticeRoleService.getNoticeRoleById(id);
		//跟新为已读状态
		noticeRole.setStatus(Constant.MSG_ROLE_READ);
		noticeRole.setReadTime(DateUtil.dateTimeToString(new Date()));
		noticeRoleService.updateNoticeRole(noticeRole);
//		noticeRole.setReceiveTime(DateUtil.formatDate(noticeRole.getReceiveTime()));
		Notice notice = noticeService.gettNoticeById(noticeRole.getNoticeId());
		if(StringUtils.isNotEmpty(notice.getSendTime())){
			notice.setSendTime(DateUtil.formatDate(notice.getSendTime()));
		}		
		if(StringUtils.isNotEmpty(notice.getProcdefId())){
			Procdef procdef = procdefService.getProcdefByProcdefId(notice.getProcdefId());
			if(procdef!=null){
				notice.setProcdef(procdef);
			}
		}
		if(StringUtils.isNotEmpty(notice.getProcinstId())){
			Procinst procinst = procinstService.getProcinstByProcinstId(notice.getProcinstId());
			if(procinst!=null){
				notice.setProcinst(procinst);
			}
		}
		if(StringUtils.isNotEmpty(notice.getTaskId())){
			Task task = taskService.getTaskByTaskId(notice.getTaskId());
			if(task!=null){
				notice.setTask(task);
			}
		}
		if(StringUtils.isNotEmpty(notice.getRoleId())){
			Role role = roleService.getRoleById(notice.getRoleId());
			if(role!=null){
				notice.setRole(role);
			}
		}
		noticeRole.setNotice(notice);
		response.getWriter().write(JSON.toJSONString(noticeRole));
	}
	
	/** 
	* @Title: timerNotice  
	* @param model
	* @return String
	* @Description: 定时通知
	*/
	@RequestMapping(value = "/timer/notice", method = RequestMethod.GET)
	public String timerNotice(Model model) {
		return "/pms/notice/timer-notice";
	}
}
