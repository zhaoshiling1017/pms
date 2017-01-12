package com.ducetech.pms.service;

import java.util.List;

import com.ducetech.framework.model.PagerRS;
import com.ducetech.framework.model.Query;
import com.ducetech.pms.model.Procdef;

/** 
* @ClassName: ProcdefService  
* @author gaoy
* @date 2016年9月6日 下午2:41:19 
* @Description: 流程定义Service接口
*/
public interface ProcdefService {
	
	/** 
	* @Title: getAllProcdef 
	* @param 
	* @return List<Procdef>
	* @Description: 获取所有流程定义
	*/ 
	List<Procdef> getAllProcdef();
	
	/** 
	* @Title:    getProcdefByQuery 
	* @param  procdef
	* @return List<Procdef>
	* @Description: 按条件筛选流程定义，不带分页
	*/ 
	List<Procdef> getProcdefByQuery(Procdef procdef);
	
	/** 
	* @Title:    getProcdefByPager 
	* @param  @param query
	* @return PagerRS<Procdef>
	* @Description: 按条件筛选流程定义，带分页
	*/ 
	PagerRS<Procdef> getProcdefByPager(Query<Procdef> query);
	
	/** 
	* @Title:    getProcdefByProcdefId 
	* @param  @param procdefId
	* @return Procdef
	* @Description: 按ID获取流程定义
	*/ 
	Procdef getProcdefByProcdefId(String procdefId);

	/** 
	* @Title:    getProcdefsByUserId 
	* @param  userId
	* @return List<Procdef>
	* @Description: 通过登录人ID获取可以发起的流程
	*/ 
	List<Procdef> getProcdefsByUserId(String userId);
}
