package com.asdc.lrm.action;

import com.asdc.lrm.entity.UserEntity;
import com.asdc.lrm.service.OrgTreeService;

public class OrgTreeAction extends BaseAction<UserEntity>{
	
	private static final long serialVersionUID = 1L;
	
	private OrgTreeService orgTreeService;
	public void setOrgTreeService(OrgTreeService orgTreeService) {
		this.orgTreeService = orgTreeService;
	}
	
	private int userType;
	public int getUserType() {
		return userType;
	}
	public void setUserType(int userType) {
		this.userType = userType;
	}

	/**
	 * 获取指定部门的子级部门信息，转换为json数据，供页面中Tree使用
	 */
	public void findGroupFroTree(){
		try {
			jsonData = orgTreeService.findGroupFroTree(parentId);
		}catch (RuntimeException e) {
			e.printStackTrace();
		}finally{
			outPrint();
		}
	}
	
	/**
	 * 获取指定部门的子级部门和用户信息，转换为json数据，供页面中Tree使用
	 */
	public void findGroupUserFroTree(){
		try {
			jsonData = orgTreeService.findGroupUserFroTree(parentId);
		}catch (RuntimeException e) {
			e.printStackTrace();
		}finally{
			outPrint();
		}
	}
	
	/**
	 * 获取用户信息，转换为json数据，供页面中Tree使用
	 */
	public void findUserFroTree(){
		try {
			jsonData = orgTreeService.findUserFroTree(userType);
		}catch (RuntimeException e) {
			e.printStackTrace();
		}finally{
			outPrint();
		}
	}
}