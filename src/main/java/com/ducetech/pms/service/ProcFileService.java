package com.ducetech.pms.service;

import java.util.List;

import com.ducetech.pms.model.ProcinstFile;

/** 
* @ClassName: ProcFileService  
* @author gaoy
* @date 2016年10月21日 上午11:39:16 
* @Description: 流程文件Service接口
*/
public interface ProcFileService {

	/** 
	* @Title: getProcFile  
	* @param procinstFile
	* @return List<ProcinstFile>
	* @Description: 通用查询
	*/
	List<ProcinstFile> getProcFile(ProcinstFile procinstFile);
	
	/** 
	* @Title: addProcFile  
	* @param procinstFile
	* @Description: 流程附件新增
	*/
	void addProcFile(ProcinstFile procinstFile);
	
	/** 
	* @Title: deleteProcFileById  
	* @param fileId
	* @return void
	* @Description: 流程附件删除标记
	*/
	void deleteProcFileById(String fileId);
	
	/** 
	* @Title: getProcFileById  
	* @param fileId
	* @return ProcinstFile
	* @Description: 按ID查询
	*/
	ProcinstFile getProcFileById(String fileId);
	
	/**
	 * 
	* @Title: updateIsBackProcFile 
	* @Description:流程附件退回标记 
	* @param @param fileId    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	void updateIsBackProcFile(String fileId);
	
	/** 
	* @Title: getProcFileByTaskIds  
	* @param taskIds
	* @return List<ProcinstFile>
	* @Description: 按工单ids获取正序流程附件
	*/
	List<ProcinstFile> getProcFileByTaskIds(List<String> taskIds);
}
