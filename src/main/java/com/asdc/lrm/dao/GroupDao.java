package com.asdc.lrm.dao;

import java.util.List;

import com.asdc.lrm.dao.common.Dao;
import com.asdc.lrm.entity.GroupEntity;

public interface GroupDao extends Dao<GroupEntity, Long>{

	/**
	 * 根据关键字查询指定部门的下级部门，如果parentId=-1，则查询全部部门
	 * @param parentId
	 * @param keyword
	 * @param page
	 * @param rows
	 * @return
	 */
	List<GroupEntity> findGroups(long parentId, String keyword, int page, int rows);
		
	/**
	 * 根据关键字查询指定部门的下级部门总数
	 * @param parentId
	 * @param keyword
	 * @return
	 */
	int findGroupsTotal(long parentId, String keyword);
}
