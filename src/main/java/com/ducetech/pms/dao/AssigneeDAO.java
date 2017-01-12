package com.ducetech.pms.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ducetech.pms.model.Assignee;

/**
 * 
* @ClassName: AssigneeDAO 
* @Description: 临时工单布施处理结果DAO
* @author yett 
* @date 2016年9月23日 下午2:12:24 
*
 */
	
public interface AssigneeDAO {
	
	/**
	 * 
	* @Title: insertAssignee 
	* @Description: 新增 
	* @param @param assignee    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	void insertAssignee(Assignee assignee);
	
	/**
	 * 
	* @Title: updateAssignee 
	* @Description: 更新 
	* @param @param assignee    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	void updateAssignee(Assignee assignee);

	List<Assignee> selectAssigneeByResult(Assignee assignee);
	
	List<Assignee> selectAssigneeByRoleIds(@Param("assignee") Assignee assignee,@Param("userIds") List<String> userIds);
	
	List<Assignee> selectAssigneeByTaskId(@Param("taskId") String taskId);
	
	Assignee selectAssigneeById(@Param("id") String id);
}
