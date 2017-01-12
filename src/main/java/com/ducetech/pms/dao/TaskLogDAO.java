package com.ducetech.pms.dao;

import java.util.List;

import com.ducetech.pms.model.TaskLog;

/**
 * 
* @ClassName: TaskLogDAO 
* @Description: 流转记录DAO
* @author yett 
* @date 2016年10月8日 下午3:30:38 
*
 */
public interface TaskLogDAO {

	/**
	 * 
	* @Title: insertTaskLog 
	* @Description: 增加日志 
	* @param @param taskLog    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	void insertTaskLog(TaskLog taskLog);
	
	/**
	 * 
	* @Title: selectTaskLog 
	* @Description: 获取日志 
	* @param @param taskLog
	* @param @return    设定文件 
	* @return List<TaskLog>    返回类型 
	* @throws
	 */
	List<TaskLog> selectTaskLog(TaskLog taskLog);
	
}
