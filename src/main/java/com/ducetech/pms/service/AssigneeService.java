package com.ducetech.pms.service;

import java.util.List;

import com.ducetech.framework.model.User;
import com.ducetech.pms.model.Assignee;
import com.ducetech.pms.model.Procinst;

/**
 * 
* @ClassName: AssigneeService 
* @Description: 临时工单部室处理结果Service
* @author yett 
* @date 2016年9月23日 下午2:10:12 
*
 */
public interface AssigneeService {

	//新增
	void addAssignee(User user,Procinst procinst,String xmbIds,String bsIds);
	
	//修改
	void updateAssignee(Assignee assignee);
	
	//根据建议状态查询
	List<Assignee> getAssigneeByResult(Assignee assignee);
	
	List<Assignee> selectAssigneeByRoleIds(Assignee assignee,User user);
	
	List<Assignee> getAssigneeByTaskId(String taskId);
	
	Assignee getAssigneeById(String id);
}
