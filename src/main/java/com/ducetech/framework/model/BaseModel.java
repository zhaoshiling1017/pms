package com.ducetech.framework.model;

/** 
 * @ClassName: BaseModel  
 * @author chensf
 * @date 2015年9月25日 上午11:45:58 
 * @Description: 基类
 */
public class BaseModel{

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
