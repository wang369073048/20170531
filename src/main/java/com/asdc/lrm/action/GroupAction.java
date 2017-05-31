package com.asdc.lrm.action;

import com.asdc.lrm.entity.GroupEntity;
import com.asdc.lrm.service.GroupService;
import com.asdc.lrm.util.UtilException;
import com.asdc.lrm.util.UtilHTTP;

public class GroupAction extends BaseAction<GroupEntity>{

	private static final long serialVersionUID = 1L;
	
	public GroupAction(){
		entity = new GroupEntity();
	}
	
	private GroupService groupService;
	public void setGroupService(GroupService groupService) {
		this.groupService = groupService;
	}
	
	/**
	 * 部门信息列表数据
	 */
	public void fingGroupGrid(){
		if (page == 0)
			page = 1;
		if (rows == 0)
			rows = 20;
		try {
			jsonData = groupService.fingGroupGrid(parentId, keyword, page-1, rows);
		}catch (RuntimeException e) {
			e.printStackTrace();
		}finally{
			outPrint();
		}
	}
	
	/**
	 * 保存部门信息
	 * @return
	 */
	public void groupCreate(){
		try {
			groupService.saveGroup(entity);
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
			entity = groupService.getById(id);
			return SUCCESS;
		}catch(RuntimeException e){
			e.printStackTrace();
			return INPUT;
		}
	}
	
	/**
	 * 修改部门信息
	 */
	public void groupUpdate(){
		try {
			groupService.updateGroup(entity);
			jsonData = "{\"status\":\"1\",\"message\":\"保存成功\"}";
		}catch (UtilException e){
			jsonData = "{\"status\":\"2\",\"message\":\""+e.getErrorKey()+"\"}";
		}finally{
			outPrint();
		}
	}
	
	/**
	 * 批量删除部门信息
	 * @return
	 */
	public void groupDelete() {
		try {
			groupService.removeGroup(UtilHTTP.getHttpServletRequest().getParameter("ids"));
			jsonData = "{\"status\":\"1\",\"message\":\"删除成功\"}";
		}catch (UtilException e){
			jsonData = "{\"status\":\"2\",\"message\":\""+e.getErrorKey()+"\"}";
		}finally{
			outPrint();
		}
	}
}