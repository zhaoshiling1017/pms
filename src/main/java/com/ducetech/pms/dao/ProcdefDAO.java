package com.ducetech.pms.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ducetech.pms.model.Procdef;

/** 
* @ClassName: ProcdefDAO  
* @author gaoy
* @date 2016年9月6日 下午2:45:49 
* @Description: 流程定义接口
*/
public interface ProcdefDAO {
	
	/** 
	* @Title:    selectProcdef 
	* @return List<Procdef>
	* @Description: 通用查询
	*/ 
	List<Procdef> selectProcdef(Procdef procdef);
	
	/** 
	* @Title:    selectProcdefByProcdefId 
	* @return Procdef
	* @Description: 按ID获取流程定义
	*/ 
	Procdef selectProcdefByProcdefId(@Param("procdefId") String procdefId);

	/** 
	* @Title:    selectProcdefsByUserId 
	* @param   userId
	* @return List<Procdef>
	* @Description: 通过登录人ID获取可以发起的流程
	*/ 
	List<Procdef> selectProcdefsByUserId(@Param("userId") String userId);
}
