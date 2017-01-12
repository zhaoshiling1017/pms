package com.ducetech.pms.service;

import java.util.List;


import com.ducetech.framework.model.PagerRS;
import com.ducetech.framework.model.Query;
import com.ducetech.pms.model.ProcNode;
import com.ducetech.pms.model.Task;

/** 
* @ClassName: TaskService  
* @author gaoy
* @date 2016年9月6日 下午4:26:04 
* @Description: 工单Service接口
*/
public interface TaskService {
	
	/** 
	* @Title:    getAllTask 
	* @return List<Task>
	* @Description: 获取所有工单
	*/ 
	List<Task> getAllTask();
	
	/** 
	* @Title:    getTaskByQuery 
	* @param  task
	* @return List<Task>
	* @Description: 按条件筛选工单，不带分页，降序
	*/ 
	List<Task> getTaskByQuery(Task task);
	
	/** 
	* @Title:    getTaskByQueryASC 
	* @param  task
	* @return List<Task>
	* @Description: 按条件筛选工单，不带分页，升序
	*/ 
	List<Task> getTaskByQueryASC(Task task);
	
	/** 
	* @Title:    getTaskByPager 
	* @param  query
	* @return PagerRS<Task>
	* @Description: 按条件筛选工单，带分页
	*/ 
	PagerRS<Task> getTaskByPager(Query<Task> query);
	
	/** 
	* @Title:    getTaskByTaskId 
	* @param  taskId
	* @return Task
	* @Description: 按ID获取工单
	*/ 
	Task getTaskByTaskId(String taskId);

	/** 
	* @Title:    addTask 
	* @param  task
	* @return void
	* @Description: 新增工单
	*/ 
	void addTask(Task task);
	
	/** 
	* @Title: updateTask  
	* @return void
	* @Description: 更新工单
	*/
	void updateTask(Task task,int type);

	/** 
	* @Title: getTasksByUserId  
	* @param query
	* @param userId
	* @return PagerRS<Task>
	* @Description: 按人员ID获取工单 
	*/
	PagerRS<Task> getTasksByUserId(Query<Task> query, String userId);
	
	/** 
	* @Title: getTaskByRoleIds  
	* @param query
	* @param roleIds
	* @return PagerRS<Task>
	* @Description: 通过角色IDS获取工单
	*/
	PagerRS<Task> getTaskByRoleIds(Query<Task> query, List<String> roleIds);
	
	/** 
	* @Title: createTask  
	* @param procinst
	* @param procNode
	* @return void
	* @Description: 按节点类型生成工单
	*/
	void createTask(Task task, List<ProcNode> procNodes);

	/** 
	* @Title: assignTask  
	* @param task
	* @return void
	* @Description: 工单处理派工单
	*/
	void assignTask(Task task);

	/** 
	* @Title: returnTask  
	* @param task
	 * @param ot 
	* @return void
	* @Description: 退回工单
	*/
	void returnTask(Task task);
	
	/**
	 * 
	* @Title: tempTaskBack 
	* @Description: 临时工单退回
	* @param @param task    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	void tempTaskBack(Task task);
	
	/** 
	* @Title: selectTaskByIds  
	* @param taskIds
	* @return List<Task>
	* @Description: 按工单ids获取正序
	*/
	List<Task> getTaskByIds(List<String> taskIds);
	
}
