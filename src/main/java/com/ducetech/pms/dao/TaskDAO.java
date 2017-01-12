package com.ducetech.pms.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ducetech.pms.model.Task;

/** 
* @ClassName: ServiceDAO  
* @author gaoy
* @date 2016年9月6日 下午4:30:43 
* @Description: 工单DAO接口
*/
public interface TaskDAO {
	
	/** 
	* @Title:    selectTask 
	* @return List<Task>
	* @Description: 通用查询
	*/ 
	List<Task> selectTask(Task task);
	
	/** 
	* @Title:    selectTaskASC
	* @return List<Task>
	* @Description: 通用查询升序
	*/ 
	List<Task> selectTaskASC(Task task);
	
	/** 
	* @Title:    selectTaskByTaskId 
	* @param  taskId
	* @return Task
	* @Description: 按ID查询工单
	*/ 
	Task selectTaskByTaskId(@Param("taskId") String taskId);

	/** 
	* @Title:    insertTask 
	* @param  task
	* @return void
	* @Description: 新增工单
	*/ 
	void insertTask(Task task);
	
	/** 
	* @Title: updateTask  
	* @param task
	* @return void
	* @Description: 更新表单
	*/
	void updateTask(Task task);
	
	/** 
	* @Title: selectTasksByRoleIds  
	* @param task
	* @param roleIds
	* @return List<Task>
	* @Description: 通过角色IDS获取工单
	*/
	List<Task> selectTasksByRoleIds(@Param("task") Task task,@Param("roleIds") List<String> roleIds);

	List<Task> selectTaskByProcinstId(Task task);
	
	/** 
	* @Title: selectTaskByIds  
	* @param list
	* @return List<Task>
	* @Description: 按工单ids获取正序
	*/
	List<Task> selectTaskByIds(@Param("list") List<String> taskIds);
	
}
