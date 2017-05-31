package com.asdc.lrm.service;

import java.io.File;

import com.asdc.lrm.entity.SysMenuEntity;

public interface SysMenuService {
	
	/**
	 * 根据ID查询
	 * @param id
	 * @return
	 */
	SysMenuEntity getById(long id);

	/**
	 * 根据parentId查询，返回json数据，共页面tree使用
	 * @param parentId
	 * @return
	 */
	String findSysMenuFroTree(long parentId);
	
	/**
	 * 根据parentId查询，返回json数据，共页面gird使用
	 * @param keyword
	 * @param parentId
	 * @param page
	 * @param rows
	 * @return
	 */
	String findSysMenuGrid(long parentId, int page, int rows);
	
	/**
	 * 保存菜单信息，上传菜单图片
	 * @param entity
	 * @param file
	 * @param fileName
	 */
	void saveSysMenu(SysMenuEntity entity, File file, String fileName);
	
	/**
	 * 修改菜单信息，上传菜单图片
	 * @param entity
	 * @param file
	 * @param fileName
	 */
	void updateSysMenu(SysMenuEntity entity, File file, String fileName);
	
	/**
	 * 批量删除菜单信息，同时删除菜单图片
	 * @param entity
	 */
	void removeSysMenu(String ids);
	
	/**
	 * 删除菜单图片
	 * @param id
	 */
	void removeSysMenuImg(long id);
	
	/**
	 * 获取菜单信息转换为json数据，并将已和指定角色绑定的数据选中
	 * @param roleId
	 * @return
	 */
	String findSysMenuFroTreeAssignRole(long roleId);
}
