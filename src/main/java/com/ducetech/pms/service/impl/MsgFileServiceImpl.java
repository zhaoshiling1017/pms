package com.ducetech.pms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ducetech.pms.dao.MsgFileDAO;
import com.ducetech.pms.model.MsgFile;
import com.ducetech.pms.service.MsgFileService;
@Service
public class MsgFileServiceImpl implements MsgFileService{

	@Autowired
	MsgFileDAO msgFileDAO;
	
	@Override
	public List<MsgFile> getMsgFile(MsgFile msgFile) {
		return msgFileDAO.selectMsgFile(msgFile);
	}

	@Override
	public void addMsgFile(MsgFile msgFile) {
		msgFileDAO.insertMsgFile(msgFile);
		
	}

	@Override
	public void deleteMsgFileById(String fileId) {
		msgFileDAO.deleteMsgFile(fileId);
		
	}

	@Override
	public MsgFile getMsgFileById(String fileId) {
		return msgFileDAO.selectMsgFileById(fileId);
	}

	
}
