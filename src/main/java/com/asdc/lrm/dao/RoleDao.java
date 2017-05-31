package com.asdc.lrm.dao;

import java.util.List;

import com.asdc.lrm.dao.common.Dao;
import com.asdc.lrm.entity.RoleEntity;

public interface RoleDao extends Dao<RoleEntity, Long>{

	/**
	 * 根据条件查询，如果条件全部为空则查询全部
	 * @param keyword
	 * @param page
	 * @param rows
	 * @return
	 */
	List<RoleEntity> findRoles(String keyword, int page, int rows);
	
	/**
	 * 根据条件获取总数
	 * @param keyword
	 * @return
	 */
	int findRolesTotal(String keyword);
}
