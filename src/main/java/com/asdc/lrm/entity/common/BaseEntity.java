package com.asdc.lrm.entity.common;

import java.util.Date;

public class BaseEntity {
	private Long id;
	private Long creator;					// 创建人  默认当前用户ID
	private Date createTime;				// 创建时间  默认当前时间
	private Date updateTime;				// 更新时间
	private Long updateUser;				// 更新人
	private Integer deleteMark = 0;			// 删除标记 0：正常 1：删除
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getCreator() {
		return creator;
	}
	public void setCreator(Long creator) {
		this.creator = creator;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public Long getUpdateUser() {
		return updateUser;
	}
	public void setUpdateUser(Long updateUser) {
		this.updateUser = updateUser;
	}
	public Integer getDeleteMark() {
		return deleteMark;
	}
	public void setDeleteMark(Integer deleteMark) {
		this.deleteMark = deleteMark;
	}
}