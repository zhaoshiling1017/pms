package com.ducetech.pms.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import com.ducetech.framework.controller.BaseController;
import com.ducetech.framework.model.Department;
import com.ducetech.framework.model.PagerRS;
import com.ducetech.framework.model.Permission;
import com.ducetech.framework.model.Query;
import com.ducetech.framework.model.Role;
import com.ducetech.framework.model.User;
import com.ducetech.framework.service.DepartmentService;
import com.ducetech.framework.service.PermissionService;
import com.ducetech.framework.service.RoleService;
import com.ducetech.framework.service.UserService;
import com.ducetech.util.CachePool;
import com.ducetech.util.DateUtil;
import com.ducetech.util.Encrypt;
import com.ducetech.util.JsonUtils;
import com.ducetech.util.MyCache;

/**
 * 
* @ClassName: PermissionController 
* @Description: 人员角色管理Controller 
* @author yett 
* @date 2016年9月19日 上午9:23:04 
*
 */
@Controller
@RequestMapping("permissions")
public class PermissionController extends BaseController{

	@Autowired
	private UserService userService;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private DepartmentService departmentService;
	
	@Autowired
	private PermissionService permissionService;
	
	/** 
	* @Title: person  
	* @param model
	* @return String
	* @Description: 跳转人员首页
	*/
	@RequestMapping(value = "/person", method = RequestMethod.GET)
	public String person(Model model) {
		Role role = new Role();
		role.setIsDeleted("0");
		List<Role> roles = roleService.getRoleByQuery(role);
		model.addAttribute("roles", roles);
		List<Department> depts = departmentService.getAllDepartments();
		model.addAttribute("depts", depts);
		return "/pms/permission/person";
	}
	
	/** 
	* @Title: personData  
	* @param model
	* @return void
	 * @throws Exception 
	 * @Description: 人员首页数据
	*/
	@RequestMapping(value = "/personData", method = RequestMethod.POST)
	public void personData(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Query<User> query = JsonUtils.getSearchCondition(User.class, request);
		//去空字符串
		query.getT().setEmployeeCode(query.getT().getEmployeeCode().trim());
		query.getT().setName(query.getT().getName().trim());
		PagerRS<User> pagerRS = userService.getUserByPager(query);
		response.getWriter().write(JSON.toJSONString(pagerRS));
	}
	
	/** 
	* @Title: addUser  
	* @return void
	 * @throws IOException 
	* @Description: 新增人员
	*/
	@RequestMapping(value = "/addUser", method = RequestMethod.POST)
	public void addUser(User user, HttpServletResponse response, HttpServletRequest request) throws IOException {
		User userInfo = getLoginUser(request);
		User us = new User();
		us.setLoginName(user.getLoginName());
		List<User> uName = userService.getUserByQuery(us);
		User ur = new User();
		ur.setEmployeeCode(user.getEmployeeCode());
		List<User> uCode = userService.getUserByQuery(ur);
		if(uName!=null && uName.size()>0){
			response.getWriter().write("{\"0\":\"" + "登录名已存在" + "\"}");
		}else if(uCode!=null && uCode.size()>0){
			response.getWriter().write("{\"0\":\"" + "工号已存在" + "\"}");
		}else{
			user.setCreateById(userInfo.getUserId());
			user.setCreateAt(DateUtil.dateTimeToString(new Date()));
			userService.addUser(user);
			initRoleIdsCache();
			response.getWriter().write("{\"1\":\"" + "成功" + "\"}");
		}
	}
	
	/** 
	* @Title: editUser  
	* @param userId
	* @param response
	* @param request
	* @throws IOException
	* @Description: 跳转人员编辑页面
	*/
	@RequestMapping(value = "/editUser", method = RequestMethod.POST)
	public void editUser(String userId, HttpServletResponse response, HttpServletRequest request) throws IOException {
		User user = new User();
		if(StringUtils.isNotEmpty(userId)){
			 user = userService.getUserByUserId(userId);
		}
		response.getWriter().write(JSON.toJSONString(user));
	}
	
	/** 
	* @Title: updateUser  
	* @param user
	* @param response
	* @param request
	* @throws IOException
	* @return void
	* @Description: 更新人员信息
	*/
	@RequestMapping(value = "/updateUser", method = RequestMethod.POST)
	public void updateUser(User user, HttpServletResponse response, HttpServletRequest request) throws IOException {
		  List<User> uList = userService.getAllUsers();
		   for (int i = 0; i < uList.size(); i++) {
			   if(uList.get(i).getUserId().equals(user.getUserId())){
				   uList.remove(i);
			   }
		   }
		   String result = "";
		   for(User us : uList){
				if(us.getLoginName().equals(user.getLoginName())){
					 response.getWriter().write("{\"0\":\"用户名已存在\"}");
					 result="err";
					 return;
				}else if(us.getEmployeeCode().equals(user.getEmployeeCode())){
					 response.getWriter().write("{\"0\":\"工号已存在\"}");
					 result="err";
					 return;
				}
		   }
		   if(!result.equals("err")){
			   userService.updateUser(user);
			   initRoleIdsCache();
			   response.getWriter().write("{\"1\":\"" + "成功" + "\"}");
		   }
	}
	
	
	/** 
	* @Title: userStatus  
	* @param user
	* @param response
	* @param request
	* @throws IOException
	* @Description: 人员禁用启用
	*/
	@RequestMapping(value = "userStatus", method = RequestMethod.POST)
	public void userStatus(String userId, String isDeleted, HttpServletResponse response, HttpServletRequest request) throws IOException {
		User user = new User();
		user.setUserId(userId);
		user.setIsDeleted(isDeleted);
		userService.updateUserStatus(user);
		response.getWriter().write("{\"1\":\"" + "成功" + "\"}");
	}
	
	/** 
	* @Title: resetPass  
	* @param userId
	* @param isDeleted
	* @param response
	* @param request
	* @throws IOException
	* @Description: 重置密码为123456
	*/
	@RequestMapping(value = "resetPass", method = RequestMethod.POST)
	public void resetPass(String userId, String isDeleted, HttpServletResponse response, HttpServletRequest request) throws IOException {
		User user = userService.getUserByUserId(userId);
		user.setPassword(Encrypt.md5("123456",user.getSecretKey()));
		userService.resetPass(user);
		response.getWriter().write("{\"1\":\"" + "重置成功，密码为：123456" + "\"}");
	}
	
	/**
	 * 
	* @Title: personLoginLog 
	* @Description: 人员登录日志
	* @param @param model
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	@RequestMapping(value = "/personLoginLog", method = RequestMethod.GET)
	public String personLoginLog(Model model) {
		
		return "/pms/permission/login-log";
	}
	
	
	/** 
	* @Title: role
	* @param model
	* @return String
	* @Description: 角色管理首页 
	*/
	@RequestMapping(value = "/role", method = RequestMethod.GET)
	public String role(Model model) {
		return "/pms/permission/role";
	}
	
	/** 
	* @Title: roleData  
	* @param request
	* @param response
	* @throws Exception
	* @Description: 角色管理数据
	*/
	@RequestMapping(value = "/roleData", method = RequestMethod.POST)
	public void roleData(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Query<Role> query = JsonUtils.getSearchCondition(Role.class, request);
		PagerRS<Role> roles = roleService.getRoleByPager(query);
		response.getWriter().write(JSON.toJSONString(roles));
	}
	
	/** 
	* @Title: roleStatus  
	* @param roleId
	* @param isDeleted
	* @param response
	* @param request
	* @throws IOException
	* @Description: 角色禁用启用
	*/
	@RequestMapping(value = "roleStatus", method = RequestMethod.POST)
	public void roleStatus(String roleId, String isDeleted, HttpServletResponse response, HttpServletRequest request) throws IOException {
		Role role = new Role();
		role.setRoleId(roleId);
		role.setIsDeleted(isDeleted);
		roleService.updateRole(role);
		response.getWriter().write("{\"1\":\"" + "成功" + "\"}");
	}
	
	/**
	 * 
	* @Title: addRole 
	* @Description: 新增角色页面
	* @param @param model
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	@RequestMapping(value = "/addRole", method = RequestMethod.GET)
	public String addRole(Model model) {
		
		return "/pms/permission/add-role";
	}
	
	/** 
	* @Title: saveRole  
	* @param model
	* @param request
	* @param response
	* @throws IOException
	* @Description: 新增角色和菜单权限
	*/
	@RequestMapping(value = "/saveRole", method = RequestMethod.POST)
	public void saveRole(Role role, HttpServletRequest request, HttpServletResponse response) throws IOException {
		User user = getLoginUser(request);
		role.setCreateById(user.getUserId());
		role.setCreateAt(DateUtil.dateTimeToString(new Date()));
		roleService.addRoleAndPermission(role);
		response.getWriter().write("{\"1\":\"" + "成功" + "\"}");
	}
	
	/**
	 * 
	* @Title: editRole 
	* @Description: 编辑角色 
	* @param @param model
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	@RequestMapping(value = "/editRole/{roleId}", method = RequestMethod.GET)
	public String editRole(@PathVariable String roleId, Model model) {
		Role role = roleService.getRoleById(roleId);
		model.addAttribute("role", role);
		return "/pms/permission/edit-role";
	}
	
	/** 
	* @Title: updateRole  
	* @param role
	* @param model
	* @param request
	* @param response
	* @throws IOException
	* @Description: 更新角色和菜单权限
	*/
	@RequestMapping(value = "/updateRole", method = RequestMethod.POST)
	public void updateRole(Role role, HttpServletRequest request, HttpServletResponse response) throws IOException {
		roleService.updateRoleAndPermission(role);
		initRoleIdsCache();
		response.getWriter().write("{\"1\":\"" + "成功" + "\"}");
	}
	
	/**
	 * 
	* @Title: rolePerson 
	* @Description: 角色拥有人员
	* @param @param model
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	@RequestMapping(value = "/rolePerson/{roleId}", method = RequestMethod.GET)
	public String rolePerson(@PathVariable String roleId, Model model) {
		Role role = roleService.getRoleById(roleId);
		model.addAttribute("role", role);
		return "/pms/permission/role-person";
	}
	
	/** 
	* @Title: rolePersonData  
	* @param role
	* @param response
	 * @throws Exception 
	* @Description: 角色已有人员数据
	*/
	@RequestMapping(value = "/rolePersonData", method = RequestMethod.POST)
	public void rolePersonData(HttpServletResponse response, HttpServletRequest request) throws Exception {
		Query<Role> query = JsonUtils.getSearchCondition(Role.class, request); 
		PagerRS<User> pageRS = userService.getUsersByRoleIdPage(query);
		response.getWriter().write(JSON.toJSONString(pageRS));
	}
	
	/**
	 * 
	* @Title: passwordUpdate 
	* @Description: 修改用户密码
	* @param @param user
	* @param @param model
	* @param @param response
	* @param @param request
	* @param @throws IOException    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	@RequestMapping(value = "/passwordUpdate", method = RequestMethod.POST)
	public void passwordUpdate(User user, Model model, HttpServletResponse response, HttpServletRequest request) throws IOException {
		user = userService.getUserByUserId(user.getUserId());
		String password = request.getParameter("password");
		String newPassword = request.getParameter("newPassword");
		String comfirmPassword  = request.getParameter("comfirmPassword");
		String pw = Encrypt.md5(password,user.getSecretKey());
		if(pw.equals(user.getPassword())){
			if(newPassword.equals(comfirmPassword)){
				user.setPassword(Encrypt.md5(newPassword,user.getSecretKey()));
				userService.updateUserPassword(user);
				response.getWriter().write("{\"1\":\"" + "成功" + "\"}");
			}else{ 
				response.getWriter().write("{\"0\":\"" + "两次密码不同" + "\"}");
			}
		}else {
			response.getWriter().write("{\"0\":\"" + "旧密码错误" + "\"}");
		}
	}
	
	/** 
	* @Title: getAllPermission  
	* @param response
	* @param request
	* @throws IOException
	* @Description: 获取所有菜单权限
	*/
	@RequestMapping(value = "/getAllPermission", method = RequestMethod.GET)
	public void getAllPermission(HttpServletResponse response, HttpServletRequest request) throws IOException {
		List<Permission> permissions = permissionService.getAllPermissions();
		response.getWriter().write(JSON.toJSONString(permissions));
	}
	
	/** 
	* @Title: getCheckedPermission  
	* @param response
	* @param request
	* @throws IOException
	* @Description: 获取角色已选的菜单权限
	*/
	@RequestMapping(value = "/getCheckedPermission", method = RequestMethod.POST)
	public void getCheckedPermission(String roleId, HttpServletResponse response, HttpServletRequest request) throws IOException {
		List<Permission> permissions = permissionService.getPermissionsByRoleId(roleId);
		response.getWriter().write(JSON.toJSONString(permissions));
	}
	
	
	public void initRoleIdsCache(){
		CachePool cachePool = CachePool.getInstance();
		cachePool.clearAllItems();
		MyCache myCache = new MyCache();
		List<Role> roles = roleService.getRoleByQuery(new Role());
		for (Role role : roles) {
			role.getRoleId();
			List<User> users = userService.getUsersByRoleId(role.getRoleId());
			List<String> ids = new ArrayList<>();
			for (int i = 0; i < users.size(); i++) {
				ids.add(users.get(i).getUserId());
			}
			cachePool.putCacheItem(role.getRoleId(), users);
			myCache.getRoleIdsCache().put(role.getRoleId(),ids);
		}
		logger.info("-------------------------------------------");
		logger.info("--------------缓存跟新完成----------------");
		logger.info("-------------------------------------------");
	}
}
