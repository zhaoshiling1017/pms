package com.ducetech.pms.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ducetech.pms.model.ProcinstFile;

/** 
* @ClassName: ProcFileDAO  
* @author gaoy
* @date 2016年10月21日 上午11:47:18 
* @Description: 流程文件DAO
*/
public interface ProcFileDAO {

	/** 
	* @Title: selectProcFile  
	* @param procinstFile
	* @return List<ProcinstFile>
	* @Description: 通用查询
	*/
	List<ProcinstFile> selectProcFile(ProcinstFile procinstFile);

	/** 
	* @Title: selectProcFileById  
	* @param procFileId
	* @return ProcinstFile
	* @Description: 按ID查询
	*/
	ProcinstFile selectProcFileById(@Param("procFileId")String procFileId);

	/** 
	* @Title: insertProcFile  
	* @param procinstFile
	* @return void
	* @Description: 流程附件新增
	*/
	void insertProcFile(ProcinstFile procinstFile);

	/** 
	* @Title: deleteProcFile  
	* @param fileId
	* @return void
	* @Description: 流程附件删除标记
	*/
	void deleteProcFile(@Param("fileId") String fileId);
	
	/**
	 * 
	* @Title: isBackProcFile 
	* @Description: 流程附件退回标记
	* @param @param fileId    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	void updateIsBackProcFile(@Param("fileId") String fileId);
	
	/** 
	* @Title: selectProcFileByTaskIds  
	* @param taskIds
	* @return List<ProcinstFile>
	* @Description: 按工单ids获取正序流程附件
	*/
	List<ProcinstFile> selectProcFileByTaskIds(@Param("list") List<String> taskIds);
}
