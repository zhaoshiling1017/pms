package com.ducetech.framework.model;

import java.io.Serializable;
import java.util.List;

public class Role extends BaseModel implements Serializable {

	private static final long serialVersionUID = 1L;

	private String roleId;				//角色ID
	
	private String roleName;			//角色名称
	
	private String comment;				//备注
	
	private String deptId;				//部门ID
	
	private String permissionIds;		//菜单权限IDS
	
	private String permissionMajorIds;	//菜单权限专业IDS
	
	private String permissionDeptIds;   //菜单权限部门IDS
	
	private String groupIds; 			//审批组IDS
	
	private List<Permission> permissions;
	
	private List<User> users;			//已配人员
	
	private String userNames;			//已配人员名字

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public String getPermissionMajorIds() {
		return permissionMajorIds;
	}

	public void setPermissionMajorIds(String permissionMajorIds) {
		this.permissionMajorIds = permissionMajorIds;
	}

	public String getPermissionDeptIds() {
		return permissionDeptIds;
	}

	public void setPermissionDeptIds(String permissionDeptIds) {
		this.permissionDeptIds = permissionDeptIds;
	}

	public String getPermissionIds() {
		return permissionIds;
	}

	public void setPermissionIds(String permissionIds) {
		this.permissionIds = permissionIds;
	}

	public List<Permission> getPermissions() {
		return permissions;
	}

	public void setPermissions(List<Permission> permissions) {
		this.permissions = permissions;
	}

	public String getGroupIds() {
		return groupIds;
	}

	public void setGroupIds(String groupIds) {
		this.groupIds = groupIds;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public String getUserNames() {
		return userNames;
	}

	public void setUserNames(String userNames) {
		this.userNames = userNames;
	}
}
