package com.asdc.lrm.service;

import java.util.List;

import com.asdc.lrm.entity.SysMenuEntity;

public interface LoginService {

	/**
	 * 用户登陆
	 * @param loginName
	 * @param password
	 * @return
	 */
	void lonig(String loginName, String password);
	
	/**
	 * 获取当前登陆用户可访问的一级菜单
	 * @return
	 */
	List<SysMenuEntity> firstMenu();
	
	/**
	 * 获取当前登陆用户可访问的二级菜单
	 * @param parentId
	 * @return
	 */
	List<SysMenuEntity> secondMenu(long parentId);
}
