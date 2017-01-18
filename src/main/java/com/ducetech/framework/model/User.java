package com.ducetech.framework.model;

import java.io.Serializable;
import java.util.List;

public class User extends BaseModel implements Serializable {

	private static final long serialVersionUID = 1L;

	private String userId; 			// 用户ID,即用户的唯一标识

	private String loginName; 		// 登录名,用于用户登录,只允许有字母跟数字组成

	private String password; 		// 用户密码,由于采用shiro自带的MD5加密算法

	private String secretKey;		//秘钥,用作生成密码中的盐
	
	private String employeeCode; 	// 工号

	private String name; 			// 真实姓名

	private String email; 			// 邮件地址

	private String gender; 			// 性别

	private String phone; 			// 联系电话

	private String departmentId; 	// 部门ID

	private Department department;	//部门

	private List<Role> roles; 		// 用户拥有的角色列表
	
	private String roleIds;			//角色IDS

	private String roleNames;		//角色名字
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSecretKey() {
		return secretKey;
	}

	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}

	public String getEmployeeCode() {
		return employeeCode;
	}

	public void setEmployeeCode(String employeeCode) {
		this.employeeCode = employeeCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public String getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(String roleIds) {
		this.roleIds = roleIds;
	}

	public String getRoleNames() {
		return roleNames;
	}

	public void setRoleNames(String roleNames) {
		this.roleNames = roleNames;
	}
}
