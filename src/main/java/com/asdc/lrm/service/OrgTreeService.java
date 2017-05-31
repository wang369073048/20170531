package com.asdc.lrm.service;

public interface OrgTreeService {

	/**
	 * 获取指定部门的子级部门信息，转换为json数据，供页面中Tree使用
	 * @param parentId
	 * @return
	 */
	String findGroupFroTree(long parentId);
	
	/**
	 * 获取指定部门的子级部门信息和用户信息，转换为json数据，供页面中Tree使用
	 * @param parentId
	 * @return
	 */
	String findGroupUserFroTree(long parentId);
	
	/**
	 * 获取用户信息，转换为json数据，供页面中Tree使用
	 * @param userType 0：全部 1：系统用户 2：教练 3：会员
	 * @return
	 */
	String findUserFroTree(int userType);
}
