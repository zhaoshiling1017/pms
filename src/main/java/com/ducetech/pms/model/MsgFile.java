package com.ducetech.pms.model;

import java.io.Serializable;

import com.ducetech.framework.model.BaseModel;

/** 
* @ClassName: MsgFile  
* @author gaoy
* @date 2016年9月5日 下午3:33:48 
* @Description: 集团消息附件
*/
public class MsgFile extends BaseModel implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String msgFileId;				//ID
	
	private String msgId;			//集团消息ID
	
	private String fileName;       //附件名称
	
	private String uploadTime;		//上传时间
	
	private String path;			//附件地址
	
	public String getMsgId() {
		return msgId;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}

	public String getUploadTime() {
		return uploadTime;
	}

	public void setUploadTime(String uploadTime) {
		this.uploadTime = uploadTime;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getMsgFileId() {
		return msgFileId;
	}

	public void setMsgFileId(String msgFileId) {
		this.msgFileId = msgFileId;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
}
