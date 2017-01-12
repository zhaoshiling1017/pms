package com.ducetech.pms.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ducetech.pms.model.ProcNode;

/** 
* @ClassName: ProcNodeDAO  
* @author gaoy
* @date 2016年9月6日 下午4:09:54 
* @Description: 流程节点DAO接口
*/
public interface ProcNodeDAO {
	
	/** 
	* @Title:    selectProcNode 
	* @return List<ProcNode>
	* @Description: 通用查询
	*/ 
	List<ProcNode> selectProcNode(ProcNode procNode);
	
	/** 
	* @Title:    selectProcNodeByNodeId 
	* @return ProcNode
	* @Description: 按ID查询节点
	*/ 
	ProcNode selectProcNodeByNodeId(@Param("nodeId") String nodeId);
}
