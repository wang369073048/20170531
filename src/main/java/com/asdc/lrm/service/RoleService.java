package com.asdc.lrm.service;

import com.asdc.lrm.entity.RoleEntity;

public interface RoleService {

	/**
	 * 根据ID查询
	 * @param id
	 * @return
	 */
	RoleEntity getById(long id);
	
	/**
	 * 获取全部角色，返回json数据
	 */
	String findRoleFroTree();
	
	/**
	 * 角色信息列表数据，返回json格式
	 * @param keyword
	 * @param page
	 * @param rows
	 * @return
	 */
	String findRoleGrid(String keyword, int page, int rows);
	
	/**
	 * 保存角色信息
	 * @param entity
	 */
	void saveRole(RoleEntity entity);
	
	/**
	 * 修改角色信息
	 * @param entity
	 */
	void updateRole(RoleEntity entity);
	
	/**
	 * 批量删除角色信息
	 * @param entity
	 */
	void removeRole(String ids);
	
	/**
	 * 获取部门用户信息转换为json数据，并将已和指定角色绑定的数据选中
	 * @param roleId
	 * @return
	 */
	String findUserFroTreeAssignRole(long roleId);
	
	/**
	 * 角色与用户进行关联
	 * @param roleId
	 * @param userIds
	 */
	void roleAssignUser(long roleId, String userIds);
	
	/**
	 * 角色与菜单进行关联
	 * @param roleId
	 * @param menuIds
	 */
	void roleAssignMenu(long roleId, String menuIds);
}
