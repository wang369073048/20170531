package com.asdc.lrm.dao;

import java.util.List;

import com.asdc.lrm.dao.common.Dao;
import com.asdc.lrm.entity.SysMenuRoleEntity;

public interface SysMenuRoleDao extends Dao<SysMenuRoleEntity, Long>{
	/**
	 * 根据菜单ID获取
	 * @param menuId
	 * @return
	 */
	List<SysMenuRoleEntity> findByMenuId(long menuId);
	
	/**
	 * 根据角色ID获取
	 * @param roleId
	 * @return
	 */
	List<SysMenuRoleEntity> findByRoleId(long roleId);
}
