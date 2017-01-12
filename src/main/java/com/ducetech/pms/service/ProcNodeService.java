package com.ducetech.pms.service;

import java.util.List;

import com.ducetech.framework.model.PagerRS;
import com.ducetech.framework.model.Query;
import com.ducetech.pms.model.ProcNode;

/** 
* @ClassName: ProcNodeService  
* @author gaoy
* @date 2016年9月6日 下午4:05:12 
* @Description: 流程节点Service接口
*/
public interface ProcNodeService {
	
	/** 
	* @Title:    getAllProcNode 
	* @return List<ProcNode>
	* @Description: 获取所有流程节点
	*/ 
	List<ProcNode> getAllProcNode();
	
	/** 
	* @Title:    getProcNodeByQuery 
	* @return List<ProcNode>
	* @Description: 按条件筛选流程节点，不带分页
	*/ 
	List<ProcNode> getProcNodeByQuery(ProcNode procNode);
	
	/** 
	* @Title:    getProcNodeByPager 
	* @return PagerRS<ProcNode>
	* @Description: 按条件筛选流程节点，带分页
	*/ 
	PagerRS<ProcNode> getProcNodeByPager(Query<ProcNode> query);
	
	/** 
	* @Title:    getProcNodeByNodeId 
	* @return ProcNode
	* @Description: 按节点ID获取流程节点
	*/ 
	ProcNode getProcNodeByNodeId(String nodeId);
}
