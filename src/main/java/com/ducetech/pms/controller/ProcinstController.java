package com.ducetech.pms.controller;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ducetech.constant.Constant;
import com.ducetech.framework.controller.BaseController;
import com.ducetech.framework.model.Role;
import com.ducetech.framework.model.User;
import com.ducetech.framework.service.RoleService;
import com.ducetech.framework.service.UserService;
import com.ducetech.pms.model.Procdef;
import com.ducetech.pms.model.Procinst;
import com.ducetech.pms.service.ProcdefService;
import com.ducetech.pms.service.ProcinstService;
import com.ducetech.util.DateUtil;

/** 
* @ClassName: ProcinstController  
* @author gaoy
* @date 2016年9月6日 下午2:56:22 
* @Description: 流程实例Controller
*/
@Controller
@RequestMapping("/procinsts")
public class ProcinstController extends BaseController{
	
	@Autowired
	private ProcinstService procinstService;
	
	@Autowired
	private ProcdefService procdefService;
	
	@Autowired
	private RoleService roleService;
	@Autowired
	private UserService userService;
	
	/** 
	* @Title: launchProc  
	* @param model
	* @return String
	* @Description: 发起流程页面
	*/
	@RequestMapping(value = "/launchProc", method = RequestMethod.GET)
	public String launchProc(Model model, HttpServletRequest request) {
		User user = getLoginUser(request);
		List<Procdef> procdefs = procdefService.getProcdefsByUserId(user.getUserId());
		model.addAttribute("procdefs", procdefs);
		return "/pms/task/launch-process";
	}
	
	/** 
	* @Title: startProcess  
	* @param model
	* @param request
	* @return String
	 * @throws IOException 
	* @Description: 发起普通流程
	*/
	@RequestMapping(value = "/startProcess", method = RequestMethod.POST)
	public void startProcess(Procinst procinst, Model model, HttpServletRequest request, HttpServletResponse response) throws IOException {
		User user = getLoginUser(request);
		Procdef pf = procdefService.getProcdefByProcdefId(procinst.getProcdefId());
		//发起普通流程生成流程实例
		if(pf!=null){
			Procinst proci = new Procinst();
			proci.setProcdefId(pf.getProcdefId());
			proci.setProcType(pf.getProcType());
			proci.setProcName(pf.getProcName());
			proci.setProcinstCode("PROC20160914001");
			proci.setStatus(Constant.PROCINST_UNCOMPLETE);	//0未完成，1已完成
			proci.setStartPersonId(user.getUserId());
			proci.setStartTime(DateUtil.dateTimeToString(new Date()));
			proci.setComment(procinst.getComment());
			procinstService.startProcess(proci);	//发起普通流程	
		}
		response.getWriter().write("{\"1\":\"" + "发起成功" + "\"}");
	}
	
	/** 
	* @Title: launchTempProc  
	* @param model
	* @return String
	* @Description: 发起临时流程页面
	*/
	@RequestMapping(value = "/launchTempProc", method = RequestMethod.GET)
	public String launchTempProc(Model model,HttpServletRequest request) {
		User user = getLoginUser(request);
		List<Role> userRoles = roleService.getRolesByUserId(user.getUserId());
		List<Role> roles = roleService.getAllRoles();
		UUID uuid = UUID.randomUUID();
		model.addAttribute("uuid", uuid);
		model.addAttribute("roles", roles);
		model.addAttribute("userRoles", userRoles);
		return "/pms/temptask/launch-temp-process";
	}
}
