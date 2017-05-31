package com.asdc.lrm.entity;

import java.util.Set;

import com.asdc.lrm.entity.common.BaseEntity;

public class UserEntity extends BaseEntity{

	private String loginName;								// 登录名
	private String password="123456";						// 登录密码
	private String userName;								// 人员姓名
	
	private int enabled;									// 是否启用
	private int userType;									// 用户类型	1：系统用户 
	private Set<GroupEntity> groupSet;						// 部门集合
	private Set<RoleEntity> roleSet;						// 角色集合
	
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public int getEnabled() {
		return enabled;
	}
	public void setEnabled(int enabled) {
		this.enabled = enabled;
	}
	public int getUserType() {
		return userType;
	}
	public void setUserType(int userType) {
		this.userType = userType;
	}
	public Set<GroupEntity> getGroupSet() {
		return groupSet;
	}
	public void setGroupSet(Set<GroupEntity> groupSet) {
		this.groupSet = groupSet;
	}
	public Set<RoleEntity> getRoleSet() {
		return roleSet;
	}
	public void setRoleSet(Set<RoleEntity> roleSet) {
		this.roleSet = roleSet;
	}
}