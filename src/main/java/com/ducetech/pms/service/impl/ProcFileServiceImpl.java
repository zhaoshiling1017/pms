package com.ducetech.pms.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ducetech.framework.dao.RoleDAO;
import com.ducetech.framework.dao.UserDAO;
import com.ducetech.framework.model.Role;
import com.ducetech.framework.model.User;
import com.ducetech.pms.dao.ProcFileDAO;
import com.ducetech.pms.dao.ProcNodeDAO;
import com.ducetech.pms.model.ProcNode;
import com.ducetech.pms.model.ProcinstFile;
import com.ducetech.pms.service.ProcFileService;
import com.ducetech.util.DateUtil;
/** 
* @ClassName: ProcFileServiceImpl  
* @author gaoy
* @date 2016年10月21日 上午11:39:28 
* @Description: 流程文件Service实现类
*/
@Service
public class ProcFileServiceImpl implements ProcFileService{
	
	@Autowired
	private ProcFileDAO procFileDAO;
	
	@Autowired
	private UserDAO userDAO;
	
	@Autowired
	private RoleDAO roleDAO;
	
	@Autowired
	private ProcNodeDAO procNodeDAO;
	
	@Override
	public List<ProcinstFile> getProcFile(ProcinstFile procinstFile) {
		List<ProcinstFile> files = procFileDAO.selectProcFile(procinstFile);
		for(ProcinstFile file : files){
			User user = userDAO.selectUserByUserId(file.getCreateById());
			if(user!=null){
				file.setUploadPerson(user);
			}
			file.setUploadTime(DateUtil.formatDate(file.getUploadTime()));
		}
		return files;
	}

	@Override
	public void addProcFile(ProcinstFile procinstFile) {
		procFileDAO.insertProcFile(procinstFile);
		
	}

	@Override
	public void deleteProcFileById(String fileId) {
		procFileDAO.deleteProcFile(fileId);
		
	}

	@Override
	public ProcinstFile getProcFileById(String fileId) {
		return procFileDAO.selectProcFileById(fileId);
	}

	@Override
	public void updateIsBackProcFile(String fileId) {
		procFileDAO.updateIsBackProcFile(fileId);
	}

	@Override
	public List<ProcinstFile> getProcFileByTaskIds(List<String> taskIds) {
		List<ProcinstFile> files = new ArrayList<ProcinstFile>();
		if(taskIds.size()>0){
			files = procFileDAO.selectProcFileByTaskIds(taskIds);
			for(ProcinstFile file : files){
				if(StringUtils.isNotEmpty(file.getCreateById())){
					User user = userDAO.selectUserByUserId(file.getCreateById());
					if(user!=null){
						file.setUploadPerson(user);
					}
				}
				if(StringUtils.isNotEmpty(file.getUploadTime())){
					file.setUploadTime(DateUtil.formatDate(file.getUploadTime()));
				}
				if(StringUtils.isNotEmpty(file.getUploadRoleId())){
					Role role = roleDAO.selectRoleById(file.getUploadRoleId());
					if(role!=null){
						file.setUploadRole(role);
					}
				}
				if(StringUtils.isNotEmpty(file.getUploadNodeId())){
					ProcNode node = procNodeDAO.selectProcNodeByNodeId(file.getUploadNodeId());
					if(node!=null){
						file.setUploadNode(node);
					}
				}
			}
		}
		return files;
	}
}