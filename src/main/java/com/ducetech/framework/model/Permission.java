package com.ducetech.framework.model;

import java.io.Serializable;

/** 
 * @ClassName: Permission  
 * @author chensf
 * @date 2015年9月25日 下午1:53:18 
 * @Description: 权限
 */
public class Permission extends BaseModel implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private String permissionId;					//权限ID

	private String name;							//菜单、连接、按钮权限

	private String permissionStr;					//菜单权限辨识字符串

	private String parentPermissionId;				//父级菜单ID
	
	private String parentPermissionName;			//父级菜单名字

	private String comment;							//备注,说明

	public String getPermissionId() {
		return permissionId;
	}

	public void setPermissionId(String permissionId) {
		this.permissionId = permissionId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPermissionStr() {
		return permissionStr;
	}

	public void setPermissionStr(String permissionStr) {
		this.permissionStr = permissionStr;
	}

	public String getParentPermissionId() {
		return parentPermissionId;
	}

	public void setParentPermissionId(String parentPermissionId) {
		this.parentPermissionId = parentPermissionId;
	}

	public String getParentPermissionName() {
		return parentPermissionName;
	}

	public void setParentPermissionName(String parentPermissionName) {
		this.parentPermissionName = parentPermissionName;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
}
