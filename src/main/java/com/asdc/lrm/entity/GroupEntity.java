package com.asdc.lrm.entity;

import java.util.Set;

import com.asdc.lrm.entity.common.BaseEntity;

public class GroupEntity extends BaseEntity{

	private long parentId;					// 父级部门ID
	private String groupName;				// 部门名称
	private int sortNumber;					// 排序号
	private Set<UserEntity> userSet;		// 用户集合
	
	public long getParentId() {
		return parentId;
	}
	public void setParentId(long parentId) {
		this.parentId = parentId;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public int getSortNumber() {
		return sortNumber;
	}
	public void setSortNumber(int sortNumber) {
		this.sortNumber = sortNumber;
	}
	public Set<UserEntity> getUserSet() {
		return userSet;
	}
	public void setUserSet(Set<UserEntity> userSet) {
		this.userSet = userSet;
	}
}
