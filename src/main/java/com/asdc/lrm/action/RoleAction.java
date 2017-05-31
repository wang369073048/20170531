package com.asdc.lrm.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.asdc.lrm.entity.RoleEntity;
import com.asdc.lrm.service.RoleService;
import com.asdc.lrm.util.UtilException;
import com.asdc.lrm.util.UtilHTTP;

public class RoleAction extends BaseAction<RoleEntity>{

	private static final long serialVersionUID = 1L;

	public RoleAction(){
		entity = new RoleEntity();
	}
	
	private RoleService roleService;
	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}
	
	/**
	 * 获取全部角色，返回json数据，工业面Tree使用
	 */
	public void findRoleFroTree(){
		try {
			jsonData = roleService.findRoleFroTree();
		}catch (RuntimeException e) {
			e.printStackTrace();
		}finally{
			outPrint();
		}
	}
	
	/**
	 * 角色列表数据
	 */
	public void findRoleGrid(){
		if(page == 0)
			page = 1;
		if(rows == 0)
			rows = 20;
		try {
			jsonData = roleService.findRoleGrid(keyword, page-1, rows);
		}catch (RuntimeException e) {
			e.printStackTrace();
		}finally{
			outPrint();
		}
	}
	
	/**
	 * 保存角色信息
	 * @return
	 */
	public void roleCreate(){
		try {
			roleService.saveRole(entity);
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
			entity = roleService.getById(id);
			return SUCCESS;
		}catch(RuntimeException e){
			e.printStackTrace();
			return INPUT;
		}
	}
	
	/**
	 * 修改角色信息
	 * @return
	 */
	public void roleUpdate(){
		try {
			roleService.updateRole(entity);
			jsonData = "{\"status\":\"1\",\"message\":\"保存成功\"}";
		}catch (UtilException e){
			jsonData = "{\"status\":\"2\",\"message\":\""+e.getErrorKey()+"\"}";
		}finally{
			outPrint();
		}
	}
	
	/**
	 * 删除角色信息
	 */
	public void roleDelete(){
		try {
			roleService.removeRole(UtilHTTP.getHttpServletRequest().getParameter("ids"));
			jsonData = "{\"status\":\"1\",\"message\":\"删除成功\"}";
		}catch (UtilException e){
			jsonData = "{\"status\":\"2\",\"message\":\""+e.getErrorKey()+"\"}";
		}finally{
			outPrint();
		}
	}

	/**
	 * 获取用户信息转换为json数据，并将已和指定角色绑定的数据选中
	 * @return
	 */
	public String findUserFroTreeAssignRole(){
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("UTF-8");
		try {
			if(request.getParameter("roleId") == null)
				throw new RuntimeException("roleId is null");
			
			jsonData = roleService.findUserFroTreeAssignRole(Long.valueOf(request.getParameter("roleId")));
			return SUCCESS;
		}catch (RuntimeException e) {
			e.printStackTrace();
			return INPUT;
		}
	}
	
	/**
	 * 用户授权
	 */
	public void roleAssignUser(){
		try {
			roleService.roleAssignUser(Long.valueOf(UtilHTTP.getHttpServletRequest().getParameter("roleId")), 
					UtilHTTP.getHttpServletRequest().getParameter("userIds"));
			jsonData = "{\"status\":\"1\",\"message\":\"保存成功\"}";
		}catch (UtilException e){
			jsonData = "{\"status\":\"2\",\"message\":\""+e.getErrorKey()+"\"}";
		}finally{
			outPrint();
		}
	}
	
	/**
	 * 菜单授权
	 */
	public void roleAssignMenu(){
		try {
			roleService.roleAssignMenu(Long.valueOf(UtilHTTP.getHttpServletRequest().getParameter("roleId")), 
					UtilHTTP.getHttpServletRequest().getParameter("menuIds"));
			jsonData = "{\"status\":\"1\",\"message\":\"保存成功\"}";
		}catch (UtilException e){
			jsonData = "{\"status\":\"2\",\"message\":\""+e.getErrorKey()+"\"}";
		}finally{
			outPrint();
		}
	}
}
