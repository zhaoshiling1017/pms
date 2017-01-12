package com.ducetech.framework.model;

import java.io.Serializable;

/**
 * @ClassName: Department
 * @author chensf
 * @date 2015年9月25日 上午10:40:30
 * @Description: 部门
 */

public class Department extends BaseModel implements Serializable {

	private static final long serialVersionUID = 1L;

	private String departmentId; 			// 部门ID,主键,唯一标识

	private String departmentName; 			// 部门名称

	private String departmentCode; 			// 部门编号
	
	private String parentDepartmentId;		//父部门ID
	
	private String parentDepartment;		//父部门
	
	private String level;					//部门层级
	
	private String type;					//部门类别

	private String principalPersonId; 		// 负责人ID
	
	private User principalPerson; 			//负责人
	
	public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getDepartmentCode() {
		return departmentCode;
	}

	public void setDepartmentCode(String departmentCode) {
		this.departmentCode = departmentCode;
	}

	public String getParentDepartmentId() {
		return parentDepartmentId;
	}

	public void setParentDepartmentId(String parentDepartmentId) {
		this.parentDepartmentId = parentDepartmentId;
	}

	public String getParentDepartment() {
		return parentDepartment;
	}

	public void setParentDepartment(String parentDepartment) {
		this.parentDepartment = parentDepartment;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPrincipalPersonId() {
		return principalPersonId;
	}

	public void setPrincipalPersonId(String principalPersonId) {
		this.principalPersonId = principalPersonId;
	}

	public User getPrincipalPerson() {
		return principalPerson;
	}

	public void setPrincipalPerson(User principalPerson) {
		this.principalPerson = principalPerson;
	}

}
