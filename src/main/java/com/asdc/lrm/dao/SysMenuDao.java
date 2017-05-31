package com.asdc.lrm.dao;

import java.util.List;

import com.asdc.lrm.dao.common.Dao;
import com.asdc.lrm.entity.SysMenuEntity;

public interface SysMenuDao extends Dao<SysMenuEntity, Long>{
	
	/**
	 * 获取某菜单的子级菜单，parentId = -1时，查询全部
	 * @param parentId
	 * @param page
	 * @param rows
	 * @return
	 */
	List<SysMenuEntity> findSysMenus(long parentId, int page, int rows);
		
	/**
	 * 获取某菜单的子级菜单总数
	 * @param parentId
	 * @return
	 */
	int findSysMenusTotal(long parentId);

	
	/**
	 * 根据角色获取系统菜单
	 * @param roleIds
	 * @param parentId
	 * @param menuType
	 * @return
	 */
	List<SysMenuEntity> findSysMenusByRole(List<Long>roleIds, long parentId, int menuType);
}
