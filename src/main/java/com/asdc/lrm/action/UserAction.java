package com.asdc.lrm.action;

import java.io.IOException;

import com.asdc.lrm.entity.UserEntity;
import com.asdc.lrm.service.UserService;
import com.asdc.lrm.util.UtilException;
import com.asdc.lrm.util.UtilHTTP;

public class UserAction extends BaseAction<UserEntity>{
	private static final long serialVersionUID = 1L;

	public UserAction(){
		entity = new UserEntity();
	}
	
	private UserService userService;
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	private long groupId;
	public long getGroupId() {
		return groupId;
	}
	public void setGroupId(long groupId) {
		this.groupId = groupId;
	}
	
	/**
	 * 人员列表数据
	 */
	public void findUserGrid(){
		if(page == 0)
			page = 1;
		if(rows == 0)
			rows = 20;
		try {
			jsonData = userService.findUserGrid(groupId, keyword, page-1, rows);
		}catch (RuntimeException e) {
			e.printStackTrace();
		}finally{
			outPrint();
		}
	}
	
	/**
	 * 保存用户信息
	 * @return
	 */
	public void userCreate(){
		try {
			userService.saveUser(entity, groupId, imgFile, imgFileFileName);
			jsonData = "{\"status\":\"1\",\"message\":\"保存成功\"}";
		}catch (UtilException e){
			jsonData = "{\"status\":\"2\",\"message\":\""+e.getErrorKey()+"\"}";
		}finally{
			outPrint();
		}
	}

	/**
	 * 进入修改页面
	 * @return
	 */
	public String goUpdate(){
		try{
			entity = userService.getById(id);
			return SUCCESS;
		}catch(RuntimeException e){
			e.printStackTrace();
			return INPUT;
		}
	}
	
	/**
	 * 修改人员信息
	 * @return
	 */
	public void userUpdate(){
		try {
			userService.updateUser(entity, groupId, imgFile, imgFileFileName);
			jsonData = "{\"status\":\"1\",\"message\":\"保存成功\"}";
		}catch (UtilException e){
			jsonData = "{\"status\":\"2\",\"message\":\""+e.getErrorKey()+"\"}";
		}finally{
			outPrint();
		}
	}
	
	/**
	 * 删除人员信息
	 */
	public void userDelete(){
		try {
			userService.removeUser(UtilHTTP.getHttpServletRequest().getParameter("ids"));
			jsonData = "{\"status\":\"1\",\"message\":\"删除成功\"}";
		}catch (UtilException e){
			jsonData = "{\"status\":\"2\",\"message\":\""+e.getErrorKey()+"\"}";
		}finally{
			outPrint();
		}
	}
	
	/**
	 * 检查用户名是否已存在
	 */
    public void checkLoginName(){
		try {
			if(entity.getId() == null)
				entity.setId(0l);
			boolean unique = userService.checkLoginName(entity.getLoginName(), entity.getId());
			if(unique)
				jsonData = "true";
			else
				jsonData = "false";
		}catch (UtilException e){
			e.printStackTrace();
		}finally{
			outPrint();
		}
    }
    
	/**
	 * 修改人员密码
	 * @throws IOException
	 */
	public void userPasswordUpdate(){
		try {
			userService.updateUserPassword(UtilHTTP.getHttpServletRequest().getParameter("formerpassword"), 
					UtilHTTP.getHttpServletRequest().getParameter("newpassword"));
			jsonData = "{\"status\":\"1\",\"message\":\"保存成功\"}";
		}catch (UtilException e){
			jsonData = "{\"status\":\"2\",\"message\":\""+e.getErrorKey()+"\"}";
		}finally{
			outPrint();
		}
	}
}