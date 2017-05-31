package com.asdc.lrm.action;

import com.asdc.lrm.entity.SysMenuEntity;
import com.asdc.lrm.service.SysMenuService;
import com.asdc.lrm.util.UtilException;
import com.asdc.lrm.util.UtilHTTP;

public class SysMenuAction extends BaseAction<SysMenuEntity>{

	private static final long serialVersionUID = 1L;
	
	public SysMenuAction(){
		entity = new SysMenuEntity();
	}
	
	private SysMenuService sysMenuService;
	public void setSysMenuService(SysMenuService sysMenuService) {
		this.sysMenuService = sysMenuService;
	}
	
	/**
	 * 查询菜单数据，转换为json数据，供页面tree使用
	 */
	public void findSysMenuFroTree(){
		try {
			jsonData = sysMenuService.findSysMenuFroTree(parentId);
		}catch (RuntimeException e) {
			e.printStackTrace();
		}finally{
			outPrint();
		}
	}
	
	/**
	 * 系统菜单列表数据
	 */
	public void findSysMenuGrid(){
		if(page == 0)
			page = 1;
		if(rows == 0)
			rows = 20;
		try {
			jsonData = sysMenuService.findSysMenuGrid(parentId, page-1, rows);
		}catch (RuntimeException e) {
			e.printStackTrace();
		}finally{
			outPrint();
		}
	}
	
	/**
	 * 保存系统菜单
	 * @return
	 */
	public void saveSysMenu() {
		try {
			sysMenuService.saveSysMenu(entity, imgFile, imgFileFileName);
			jsonData = "{\"status\":\"1\",\"message\":\"保存成功\"}";
		}catch (UtilException e){
			jsonData = "{\"status\":\"2\",\"message\":\""+e.getErrorKey()+"\"}";
		}finally{
			outPrint();
		}
	}
	
	/**
	 * 获取系统菜单详细内容，带入修改页面
	 * @return
	 */
	public String goUpdate(){
		try{
			entity = sysMenuService.getById(id);
			return SUCCESS;
		}catch(RuntimeException e){
			e.printStackTrace();
			return INPUT;
		}
	}
	
	/**
	 * 修改系统菜单
	 * @return
	 */
	public void updateSysMenu() {
		try {
			sysMenuService.updateSysMenu(entity, imgFile, imgFileFileName);
			jsonData = "{\"status\":\"1\",\"message\":\"保存成功\"}";
		}catch (UtilException e){
			jsonData = "{\"status\":\"2\",\"message\":\""+e.getErrorKey()+"\"}";
		}finally{
			outPrint();
		}
	}
	
	/**
	 * 删除系统菜单
	 */
	public void removeSysMenu() {
		try {
			sysMenuService.removeSysMenu(UtilHTTP.getHttpServletRequest().getParameter("ids"));
			jsonData = "{\"status\":\"1\",\"message\":\"删除成功\"}";
		}catch (UtilException e){
			jsonData = "{\"status\":\"2\",\"message\":\""+e.getErrorKey()+"\"}";
		}finally{
			outPrint();
		}
	}
	
	/**
	 * 删除图片文件
	 */
	public void removeSysMenuImg(){
		try {
			sysMenuService.removeSysMenuImg(id);
			jsonData = "{\"status\":\"1\",\"message\":\"删除成功\"}";
		}catch (UtilException e){
			jsonData = "{\"status\":\"2\",\"message\":\""+e.getErrorKey()+"\"}";
		}finally{
			outPrint();
		}
	}
	
	/**
	 * 获取菜单信息转换为json数据，并将已和指定角色绑定的数据选中
	 * @return
	 */
	public String findSysMenuFroTreeAssignRole(){
		try {
			jsonData = sysMenuService.findSysMenuFroTreeAssignRole(Long.valueOf(UtilHTTP.getHttpServletRequest().getParameter("roleId")));
			return SUCCESS;
		}catch (RuntimeException e) {
			e.printStackTrace();
			return INPUT;
		}
	}
}