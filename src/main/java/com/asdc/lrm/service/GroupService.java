package com.asdc.lrm.service;

import com.asdc.lrm.entity.GroupEntity;

public interface GroupService {

	/**
	 * 根据ID查询
	 * @param id
	 * @return
	 */
	GroupEntity getById(long id);
	
	/**
	 * 根据关键字查询指定部门的下级部门，如果parentId=-1，则查询全部部门
	 * @param parentId
	 * @param keyword
	 * @param page
	 * @param rows
	 * @return
	 */
	String fingGroupGrid(long parentId, String keyword, int page, int rows);
	
	/**
	 * 保存部门信息
	 * @param entity
	 */
	void saveGroup(GroupEntity entity);
	
	/**
	 * 修改部门信息
	 * @param entity
	 */
	void updateGroup(GroupEntity entity);
	
	/**
	 * 批量删除部门信息
	 * @param ids
	 */
	void removeGroup(String ids);
}
