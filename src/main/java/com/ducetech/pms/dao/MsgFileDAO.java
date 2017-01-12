package com.ducetech.pms.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ducetech.pms.model.MsgFile;

public interface MsgFileDAO {

	MsgFile selectMsgFileById(@Param("msgFileId")String msgFileId);

	void insertMsgFile(MsgFile msgFile);

	List<MsgFile> selectMsgFile(MsgFile msgFile);

	void deleteMsgFile(String fileId);
	
}
