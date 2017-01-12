package com.ducetech.pms.service;

import java.util.List;

import com.ducetech.pms.model.TaskLog;

/**
 * 
* @ClassName: TaskLogService 
* @Description: 流转记录Service
* @author yett 
* @date 2016年10月8日 下午3:31:48 
*
 */
public interface TaskLogService {

	/**
	 * 
	* @Title: addTaskLog 
	* @Description: 增加日志 
	* @param @param taskLog    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	void addTaskLog(TaskLog taskLog);
	
	/**
	 * 
	* @Title: getTaskLog 
	* @Description: 获取日志 
	* @param @param taskLog
	* @param @return    设定文件 
	* @return List<TaskLog>    返回类型 
	* @throws
	 */
	List<TaskLog> getTaskLog(TaskLog taskLog);
	
}
