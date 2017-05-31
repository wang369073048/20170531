package com.asdc.lrm.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.asdc.lrm.dao.GroupDao;
import com.asdc.lrm.dao.RoleDao;
import com.asdc.lrm.dao.SysMenuRoleDao;
import com.asdc.lrm.dao.UserDao;
import com.asdc.lrm.entity.GroupEntity;
import com.asdc.lrm.entity.RoleEntity;
import com.asdc.lrm.entity.SysMenuRoleEntity;
import com.asdc.lrm.entity.UserEntity;
import com.asdc.lrm.entity.common.Constants;
import com.asdc.lrm.service.RoleService;
import com.asdc.lrm.util.UtilException;
import com.asdc.lrm.util.UtilProperties;
import com.asdc.lrm.util.UtilSession;

@SuppressWarnings("unchecked")
public class RoleServiceImpl extends BaseServiceImpl implements RoleService {

	private RoleDao roleDao;
	public void setRoleDao(RoleDao roleDao) {
		this.roleDao = roleDao;
	}
	
	private SysMenuRoleDao sysMenuRoleDao;
	public void setSysMenuRoleDao(SysMenuRoleDao sysMenuRoleDao) {
		this.sysMenuRoleDao = sysMenuRoleDao;
	}
	
	private GroupDao groupDao;
	public void setGroupDao(GroupDao groupDao) {
		this.groupDao = groupDao;
	}
	
	private UserDao userDao;
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
	
	public RoleEntity getById(long id){
		return roleDao.get(id);
	}
	
	public String findRoleFroTree(){
		try{
			List mapList = new ArrayList();
			List<RoleEntity> roleList = roleDao.findRoles(null, -1, -1);
			for(RoleEntity entity : roleList){
				Map map = new HashMap();
				map.put("id", entity.getId());
				map.put("name", entity.getRoleName());
				mapList.add(map);
			}
			JSONArray jsonArray = JSONArray.fromObject(mapList);
			return jsonArray.toString();
		}catch (RuntimeException e) {
			e.printStackTrace();
			return null;
		}
	}

	public String findRoleGrid(String keyword, int page, int rows){
		try {
			List<RoleEntity> roleList = roleDao.findRoles(keyword, page-1, rows);
			int total = roleDao.findRolesTotal(keyword);
			
			Map mapList = new HashMap();
			mapList.put("total", total);
	        
			List dataList = new ArrayList();
			for(int i=0; i<roleList.size(); i++){
				Map dataMap = new HashMap();
				dataMap.put("id", roleList.get(i).getId());
				dataMap.put("roleName", roleList.get(i).getRoleName());
				dataMap.put("roleCode", roleList.get(i).getRoleCode());
				dataMap.put("createTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(roleList.get(i).getCreateTime()));
				dataMap.put("roleId", roleList.get(i).getId());
				dataList.add(dataMap);
			}
			mapList.put("rows", dataList);
			JSONObject object = JSONObject.fromObject(mapList);
			return object.toString();
		} catch (RuntimeException e) {
			e.printStackTrace();
			return null;
		}
	}

	public void saveRole(RoleEntity entity){
		try{
			entity.setCreateTime(new Date());
			entity.setCreator(UtilSession.getUserId());
			entity.setDeleteMark(0);
			roleDao.save(entity);
			
			super.log(UtilProperties.getPropertyLog(Constants.LOG_MODULE_ROLE), 
					UtilProperties.getPropertyLog(Constants.LOG_ACTION_CREATE), 
					entity.getRoleName());
			
		}catch(RuntimeException e){
			e.printStackTrace();
			throw new UtilException("保存系统菜单时出现异常，请联系管理员");
		}
	}
	
	public void updateRole(RoleEntity entity){
		try{
			entity.setUpdateTime(new Date());
			entity.setUpdateUser(UtilSession.getUserId());
			roleDao.update(entity);
			
			super.log(UtilProperties.getPropertyLog(Constants.LOG_MODULE_ROLE), 
					UtilProperties.getPropertyLog(Constants.LOG_ACTION_UPDATE), 
					entity.getRoleName());
			
		}catch(RuntimeException e){
			e.printStackTrace();
			throw new UtilException("保存系统菜单时出现异常，请联系管理员");
		}
	}
	
	public void removeRole(String ids){
		try{
			String[] idArray = ids.split(",");
			for (String id : idArray) {
				RoleEntity entity = roleDao.get(Long.valueOf(id));
				Set<UserEntity> userSet = entity.getUserSet();
				if(userSet.size() > 0)
					throw new UtilException("角色已经关联用户，不能删除!");
				
				List list = sysMenuRoleDao.findByRoleId(Long.valueOf(id));
				if(list.size() > 0)
					throw new UtilException("角色已经关联菜单，不能删除!");
				
				super.log(UtilProperties.getPropertyLog(Constants.LOG_MODULE_ROLE), 
						UtilProperties.getPropertyLog(Constants.LOG_ACTION_DELETE), 
						entity.getRoleName());
				
		        roleDao.delete(entity);
			}
		}catch(UtilException e){
			if(e.getErrorKey() != null){
				throw new UtilException(e.getErrorKey());
			}
			else{
				throw new UtilException("删除角色信息时出现异常，请联系管理员!");
			}
		}
	}
	
	public String findUserFroTreeAssignRole(long roleId){
		List mapList = new ArrayList();
		try{
			List<GroupEntity> groupList = groupDao.findGroups(-1, null, -1, -1);
			for(GroupEntity group : groupList){
				Map groupMap = new HashMap();
				groupMap.put("id", "g_"+group.getId());
				groupMap.put("pId", "g_"+group.getParentId());
				groupMap.put("name", group.getGroupName());
				groupMap.put("isParent",true);
				groupMap.put("nocheck",true);
				mapList.add(groupMap);
				
				Set<UserEntity> userSet = group.getUserSet();
				for(UserEntity user : userSet){
					if(user.getDeleteMark() == 0){
						Map userMap = new HashMap();
						userMap.put("id", user.getId());
						userMap.put("pId", "g_"+group.getId());
						userMap.put("name", user.getUserName());
						boolean ischecked = false;
						Set<RoleEntity> roleSet = user.getRoleSet();
						for (RoleEntity role : roleSet) {
							if(role.getId() == Long.valueOf(roleId).longValue()){
								ischecked = true;
								break;
							}
						}
						userMap.put("checked",ischecked);
						userMap.put("isParent",false);
						mapList.add(userMap);
					}
				}
			}
			
			JSONArray jsonArray = JSONArray.fromObject(mapList);
			return jsonArray.toString();
		}catch(RuntimeException e){
			e.printStackTrace();
			return null;
		}
	}
	
	public void roleAssignUser(long roleId, String userIds){
		try{
			RoleEntity roleEntity = roleDao.get(Long.valueOf(roleId));
			roleEntity.setUserSet(null);
			roleDao.update(roleEntity);
			
			if(userIds == null || userIds.equals("")){
				return;
			}
			
			Set<UserEntity> userSet = new HashSet<UserEntity>();
			String[] userIdArray = userIds.split(";");
			for(String userId : userIdArray){
				UserEntity userEntity = userDao.get(Long.valueOf(userId));
				userSet.add(userEntity);
			}
			roleEntity.setUserSet(userSet);
			roleDao.update(roleEntity);
			
		}catch(RuntimeException e){
			throw new UtilException("角色与用户进行关联时出现异常，请联系管理员");
		}
	}
	
	public void roleAssignMenu(long roleId, String menuIds){
		try{
			List<SysMenuRoleEntity> menuRoleList = sysMenuRoleDao.findByRoleId(Long.valueOf(roleId));
			for(SysMenuRoleEntity entity : menuRoleList){
				sysMenuRoleDao.delete(entity);
			}
			
			if(menuIds == null || menuIds.equals("")){
				return;
			}
			
			String[] menuIdArray = menuIds.split(";");
			for(String menuId : menuIdArray){
				SysMenuRoleEntity entity = new SysMenuRoleEntity();
				entity.setRoleId(roleId);
				entity.setMenuId(Long.valueOf(menuId));
				sysMenuRoleDao.save(entity);
			}
			
		}catch(RuntimeException e){
			throw new UtilException("角色与菜单进行关联时出现异常，请联系管理员");
		}
	}
}