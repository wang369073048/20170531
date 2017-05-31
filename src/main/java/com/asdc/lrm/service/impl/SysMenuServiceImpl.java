package com.asdc.lrm.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import com.asdc.lrm.dao.SysMenuDao;
import com.asdc.lrm.dao.SysMenuRoleDao;
import com.asdc.lrm.entity.SysMenuEntity;
import com.asdc.lrm.entity.SysMenuRoleEntity;
import com.asdc.lrm.entity.common.Constants;
import com.asdc.lrm.service.SysMenuService;
import com.asdc.lrm.util.UtilException;
import com.asdc.lrm.util.UtilFile;
import com.asdc.lrm.util.UtilProperties;
import com.asdc.lrm.util.UtilSession;

@SuppressWarnings("unchecked")
public class SysMenuServiceImpl extends BaseServiceImpl implements SysMenuService{

	private SysMenuDao sysMenuDao;
	public void setSysMenuDao(SysMenuDao sysMenuDao) {
		this.sysMenuDao = sysMenuDao;
	}
	
	private SysMenuRoleDao sysMenuRoleDao;
	public void setSysMenuRoleDao(SysMenuRoleDao sysMenuRoleDao) {
		this.sysMenuRoleDao = sysMenuRoleDao;
	}
	
	public SysMenuEntity getById(long id){
		return sysMenuDao.get(id);
	}
	
	public String findSysMenuFroTree(long parentId){
		List mapList = new ArrayList();
		try{
			List<SysMenuEntity> systemMenuList = sysMenuDao.findSysMenus(parentId, -1, -1);
			for(SysMenuEntity entity : systemMenuList){
				Map map = new HashMap();
				map.put("id", entity.getId());
				map.put("pId", entity.getParentId());
				map.put("name", entity.getMenuName());
				
				int systemMenuTotal = sysMenuDao.findSysMenusTotal(entity.getId());
				if(systemMenuTotal > 0)
					map.put("isParent",true);
				else
					map.put("isParent",false);
				
				mapList.add(map);
			}
			JSONArray jsonArray = JSONArray.fromObject(mapList);
			return jsonArray.toString();
		}catch (RuntimeException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public String findSysMenuGrid(long parentId, int page, int rows){
		try {
			List<SysMenuEntity> systemMenuList = sysMenuDao.findSysMenus(parentId, page, rows);
			int total = sysMenuDao.findSysMenusTotal(parentId);
			
	        Map pageMap = new HashMap();
	        pageMap.put("total", total);
	        
	        List dataList = new ArrayList();
	        for(int i=0; i<systemMenuList.size(); i++){
	            Map dataMap = new HashMap();
	            dataMap.put("id", systemMenuList.get(i).getId());
	            dataMap.put("menuId", systemMenuList.get(i).getId());
	            dataMap.put("menuName", systemMenuList.get(i).getMenuName());
	            dataMap.put("menuEnName", systemMenuList.get(i).getMenuEnName());
	            dataMap.put("menuUrl", systemMenuList.get(i).getMenuUrl());
	            dataMap.put("menuImg", systemMenuList.get(i).getMenuImg());
	            dataMap.put("sortNumber", systemMenuList.get(i).getSortNumber());
	            dataList.add(dataMap);
	        }
	        pageMap.put("rows", dataList);
	        JSONObject object = JSONObject.fromObject(pageMap);
	        return object.toString();
		} catch (RuntimeException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public void saveSysMenu(SysMenuEntity entity, File file, String fileName){
		try{
			/** 如果文件不为空，先上传文件 **/
			if(file !=  null)
				entity.setMenuImg(super.fileUploadImg(file, fileName, UtilFile.createSysMenuImgDir()));
			
			entity.setCreateTime(new Date());
			entity.setCreator(UtilSession.getUserId());
			entity.setDeleteMark(0);
			sysMenuDao.save(entity);
			
			super.log(UtilProperties.getPropertyLog(Constants.LOG_MODULE_SYSMENU), 
					UtilProperties.getPropertyLog(Constants.LOG_ACTION_CREATE), 
					entity.getMenuName());
			
		}catch(RuntimeException e){
			e.printStackTrace();
			throw new UtilException("保存系统菜单时出现异常，请联系管理员");
		}
	}
	
	public void updateSysMenu(SysMenuEntity entity, File file, String fileName){
		try{
			if(file !=  null)
				entity.setMenuImg(super.fileUploadImg(file, fileName, UtilFile.createSysMenuImgDir()));
			else{
				if(entity.getMenuImg() == null || entity.getMenuImg().equals("")){
					entity.setMenuImg(null);
				}
			}
			
			entity.setUpdateUser(UtilSession.getUserId());
			entity.setUpdateTime(new Date());
			sysMenuDao.update(entity);
			
			super.log(UtilProperties.getPropertyLog(Constants.LOG_MODULE_SYSMENU), 
					UtilProperties.getPropertyLog(Constants.LOG_ACTION_UPDATE), 
					entity.getMenuName());
			
		}catch(RuntimeException e){
			e.printStackTrace();
			throw new UtilException("保存系统菜单时出现异常，请联系管理员");
		}
	}
	
	public void removeSysMenu(String ids){
		try{
			String[] idArray = ids.split(",");
			for (String id : idArray) {
				List list = sysMenuRoleDao.findByMenuId(Long.valueOf(id));
				if(list.size() > 0)
					throw new UtilException("菜单已经关联角色，不能删除!");
				
				List childList = sysMenuDao.findSysMenus(Long.valueOf(id), -1, -1);
				if(childList.size() > 0)
					throw new UtilException("存在下级菜单，不能删除!");
				
				SysMenuEntity entity = sysMenuDao.get(Long.valueOf(id));
				UtilFile.deleteFile(ServletActionContext.getServletContext().getRealPath("")+UtilFile.separator+entity.getMenuImg());
				
				super.log(UtilProperties.getPropertyLog(Constants.LOG_MODULE_SYSMENU), 
						UtilProperties.getPropertyLog(Constants.LOG_ACTION_DELETE), 
						entity.getMenuName());
				
				sysMenuDao.delete(entity);
			}
		}catch(UtilException e){
			if(e.getErrorKey() != null)
				throw new UtilException(e.getErrorKey());
			else
				throw new UtilException("删除系统菜单时出现异常，请联系管理员");
		}
	}
	
	public void removeSysMenuImg(long id){
		try{
			SysMenuEntity entity = sysMenuDao.get(id);
			UtilFile.deleteFile(ServletActionContext.getServletContext().getRealPath("")+UtilFile.separator+entity.getMenuImg());
			entity.setMenuImg(null);
			sysMenuDao.update(entity);
			
			super.log(UtilProperties.getPropertyLog(Constants.LOG_MODULE_SYSMENU), 
					UtilProperties.getPropertyLog(Constants.LOG_ACTION_DELETE_IMG), 
					entity.getMenuName());
			
		}catch(RuntimeException e){
			e.printStackTrace();
			throw new UtilException("删除菜单图片时出现异常，请联系管理员");
		}
	}
	
	public String findSysMenuFroTreeAssignRole(long roleId){
		List mapList = new ArrayList();
		try{
			List<SysMenuEntity> sysMenuList = sysMenuDao.findSysMenus(-1, -1, -1);
			for(SysMenuEntity menu : sysMenuList){
				Map map = new HashMap();
				map.put("id", menu.getId());
				map.put("pId", menu.getParentId());
				map.put("name", menu.getMenuName());
				boolean ischecked = false;
				List<SysMenuRoleEntity> menuRoleList = sysMenuRoleDao.findByMenuId(menu.getId());
				for (SysMenuRoleEntity menuRole : menuRoleList) {
					if(menuRole.getRoleId() == Long.valueOf(roleId).longValue()){
						ischecked = true;
						break;
					}
				}
				map.put("checked",ischecked);
				map.put("halfCheck",false);
				mapList.add(map);
			}
			
			JSONArray jsonArray = JSONArray.fromObject(mapList);
			return jsonArray.toString();
		}catch(RuntimeException e){
			e.printStackTrace();
			return null;
		}
	}
}