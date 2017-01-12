package com.ducetech.framework.service;

import java.util.List;

import com.ducetech.framework.model.Department;


public interface DepartmentService {
	
	List<Department> getAllDepartments();
	
	Department getDepartmentById(String id);

	void addDepartment(Department department);
	
	void updateDepartment(Department department);
	
	void deleteDepartmentById(String id);
	
	List<Department> findDepartmentByCond(Department department);
	
	Department findDepartmentCodeById(String id);
	
	/** 
	* @Title: getDepartmentByType  
	* @param type
	* @return List<Department>
	* @Description: 通过类型拿到相应的部门列表,类型有:部室,项目部,维修部等
	*/
	List<Department> getDepartmentByType(String type);
	
	String getMaxDepartmentCode();

	/** 
	* @Title: getChildrenDepartments  
	* @param departmentId
	* @return List<Department>
	* @Description: 通过ID得到一个部门的子部门
	*/
	List<Department> getChildrenDepartments(String departmentId);
}
