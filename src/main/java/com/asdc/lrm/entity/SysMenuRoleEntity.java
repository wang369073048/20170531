package com.asdc.lrm.entity;

public class SysMenuRoleEntity {

	private long id;					// ID
	private long menuId;				// 菜单ID
	private long roleId;				// 角色ID
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getMenuId() {
		return menuId;
	}
	public void setMenuId(long menuId) {
		this.menuId = menuId;
	}
	public long getRoleId() {
		return roleId;
	}
	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}
}