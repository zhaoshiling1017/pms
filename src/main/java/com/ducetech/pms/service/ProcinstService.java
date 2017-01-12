package com.ducetech.pms.service;

import java.util.List;
import java.util.Map;

import com.ducetech.framework.model.PagerRS;
import com.ducetech.framework.model.Query;
import com.ducetech.pms.model.Procinst;

/** 
* @ClassName: ProcinstService  
* @author gaoy
* @date 2016年9月6日 下午2:58:31 
* @Description: 流程实例Service接口
*/
public interface ProcinstService {
	
	/** 
	* @Title:    getAllProcinst 
	* @return List<Procinst>
	* @Description: 获取所有流程实力
	*/ 
	List<Procinst> getAllProcinst();
	
	/** 
	* @Title:    getProcinstByQuery 
	* @return List<Procinst>
	* @Description: 按条件筛选流程实例，不带分页
	*/ 
	List<Procinst> getProcinstByQuery(Procinst procinst);
	
	/** 
	* @Title:    getProcinstByPager 
	* @return PagerRS<Procinst>
	* @Description: 按条件筛选流程实例，带分页
	*/ 
	PagerRS<Procinst> getProcinstByPager(Query<Procinst> query);
	
	/** 
	* @Title:    getProcinstByProcinstId 
	* @return Procinst
	* @Description: 按ID获取流程实例
	*/ 
	Procinst getProcinstByProcinstId(String procinstId);

	/** 
	* @Title:    addProcinst 
	* @param  procinst
	* @return void
	* @Description: 增加流程实例
	*/ 
	void addProcinst(Procinst procinst);

	/** 
	* @Title: getMyRelatedByUserId  
	* @param  query
	* @return PagerRS<Procinst>
	* @Description: 按userId获取与自己相关的流程实例
	*/
	PagerRS<Procinst> getMyRelatedByUserId(Query<Procinst> query, String userId);
	
	/** 
	* @Title: getProcinstByRoleIds  
	* @param query
	* @param roleids
	* @return PagerRS<Procinst>
	* @Description: 通过角色IDS获取流程实例
	*/
	PagerRS<Procinst> getProcinstByRoleIds(Query<Procinst> query, List<String> roleIds);

	/** 
	* @Title: startProcess  
	* @param proci
	* @return void
	* @Description: 发起普通流程
	*/
	void startProcess(Procinst procinst);
	
	/**
	 * 
	* @Title: updateProcinst 
	* @Description: 更新临时流程
	* @param @param procinst    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	void updateProcinst(Procinst procinst);
	
	/**
	 * 
	* @Title: getProcinstByDept 
	* @Description: 按部门条件筛选流程实例，带分页
	* @param @param query
	* @param @return    设定文件 
	* @return PagerRS<Procinst>    返回类型 
	* @throws
	 */
	PagerRS<Procinst> getProcinstByDept(Query<Procinst> query);

	/*
	 * 获得普通任务条数
	 */
	List<Map<String, Integer>> getProcinctCountByName(Procinst procinst);
	/*
	 * 获得临时任务条数
	 */
	Map<String, Integer> getProcinctCountByNameTemp(Procinst procinst);

}
