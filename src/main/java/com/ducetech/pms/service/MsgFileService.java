package com.ducetech.pms.service;

import java.util.List;

import com.ducetech.pms.model.MsgFile;

public interface MsgFileService {

	List<MsgFile> getMsgFile(MsgFile msgFile);
	
	void addMsgFile(MsgFile msgFile);
	
	
	void deleteMsgFileById(String fileId);
	
	MsgFile getMsgFileById(String fileId);
}
