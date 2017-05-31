package com.asdc.lrm.entity;

import java.util.Set;

import com.asdc.lrm.entity.common.BaseEntity;

public class RoleEntity extends BaseEntity {

	private String roleName;				// 角色名称
	private String roleCode;				// 角色编码
	private Set<UserEntity> userSet;		// 用户集合
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getRoleCode() {
		return roleCode;
	}
	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}
	public Set<UserEntity> getUserSet() {
		return userSet;
	}
	public void setUserSet(Set<UserEntity> userSet) {
		this.userSet = userSet;
	}
}
