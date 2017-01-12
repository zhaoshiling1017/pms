package com.ducetech.pms.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ducetech.pms.model.Procinst;

/** 
* @ClassName: ProcinstDAO  
* @author gaoy
* @date 2016年9月6日 下午3:01:13 
* @Description: 流程实例DAO接口
*/
public interface ProcinstDAO {
	
	/** 
	* @Title:    selectProcinst 
	* @return List<Procinst>
	* @Description: 通用查询
	*/ 
	List<Procinst> selectProcinst(Procinst procinst);
	
	
	/** 
	* @Title:    selectProcinstByprocinstId 
	* @return Procinst
	* @Description: 按ID获取流程实例
	*/ 
	Procinst selectProcinstByprocinstId(@Param("procinstId") String procinstId);

	/** 
	* @Title:    insertProcinst 
	* @param  procinst
	* @return void
	* @Description: 增加流程实例
	*/ 
	void insertProcinst(Procinst procinst);

	/** 
	* @Title: selectProcinstByRoleIds  
	* @param procinst
	* @param roleIds
	* @return List<Procinst>
	* @Description: 通过角色IDS获取流程实例
	*/
	List<Procinst> selectProcinstByRoleIds(@Param("procinst") Procinst procinst,@Param("roleIds") List<String> roleIds);

	/** 
	* @Title: updateProcinst  
	* @param procinst
	* @return void
	* @Description: 更新流程实例
	*/
	void updateProcinst(Procinst procinst);
	
	/**
	 * 
	* @Title: selectProcinstByDept 
	* @Description: 部门执行统计 
	* @param @param procinst
	* @param @return    设定文件 
	* @return List<Procinst>    返回类型 
	* @throws
	 */
	List<Procinst> selectProcinstByDept(Procinst procinst);
	
	List<Map<String, Integer>> selectProcinctCountByName(Procinst procinst);
	
	Map<String, Integer> selectProcinctCountByNameTemp(Procinst procinst);

}
