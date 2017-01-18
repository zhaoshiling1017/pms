package com.ducetech.framework.model;

public class BaseModel {

	private String createById;		//创建人ID
	
	private User createBy;			//创建人
	
	private String createAt;		//创建时间
	
	private String isDeleted;		//删除标记	0启用	1停用	默认0启用

	public String getCreateById() {
		return createById;
	}

	public void setCreateById(String createById) {
		this.createById = createById;
	}

	public User getCreateBy() {
		return createBy;
	}

	public void setCreateBy(User createBy) {
		this.createBy = createBy;
	}

	public String getCreateAt() {
		return createAt;
	}

	public void setCreateAt(String createAt) {
		this.createAt = createAt;
	}

	public String getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(String isDeleted) {
		this.isDeleted = isDeleted;
	}

}
