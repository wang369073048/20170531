package com.asdc.lrm.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import com.asdc.lrm.dao.GroupDao;
import com.asdc.lrm.dao.UserDao;
import com.asdc.lrm.entity.GroupEntity;
import com.asdc.lrm.entity.UserEntity;
import com.asdc.lrm.service.OrgTreeService;

@SuppressWarnings("unchecked")
public class OrgTreeServiceImpl implements OrgTreeService {
	private GroupDao groupDao;
	public void setGroupDao(GroupDao groupDao) {
		this.groupDao = groupDao;
	}
	
	private UserDao userDao;
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
	
	public String findGroupFroTree(long parentId){
		try{
			List mapList = new ArrayList();
			List<GroupEntity> groupList = groupDao.findGroups(parentId, null, -1, -1);
			for(GroupEntity entity : groupList){
				Map map = new HashMap();
				map.put("id", entity.getId());
				map.put("pId", parentId);
				map.put("name", entity.getGroupName());
				map.put("isParent",true);
				mapList.add(map);
			}
			JSONArray jsonArray = JSONArray.fromObject(mapList);
			return jsonArray.toString();
		}catch (RuntimeException e) {
			e.printStackTrace();
			return null;
		}
	}

	public String findGroupUserFroTree(long parentId) {
		try{
			List mapList = new ArrayList();
			GroupEntity pGroup = groupDao.get(parentId);
			List<GroupEntity> cGroupList = groupDao.findGroups(parentId, null, -1, -1);
			List<UserEntity> cUserList = new ArrayList<UserEntity>();
			cUserList.addAll(pGroup.getUserSet());
			
			if(cGroupList != null){
				for(GroupEntity cGroup : cGroupList){
					Map map = new HashMap();
					map.put("id", cGroup.getId());
					map.put("pId", parentId);
					map.put("name", cGroup.getGroupName());
					map.put("isParent",true);
					map.put("nocheck",true);
					mapList.add(map);
				}
			}
			
			if(cUserList != null){
				for(UserEntity userInfo : cUserList){
					Map map = new HashMap();
					map.put("id", userInfo.getId());
					map.put("pId", parentId);
					map.put("name", userInfo.getUserName());
					map.put("isParent",false);
					mapList.add(map);
				}
			}
			
			JSONArray jsonArray = JSONArray.fromObject(mapList);
			return jsonArray.toString();
		}catch (RuntimeException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public String findUserFroTree(int userType){
		try{
			List mapList = new ArrayList();
			UserEntity user = new UserEntity();
			user.setUserType(userType);
			List<UserEntity> userList = userDao.findUsers(-1, user, -1, -1);
			
			for(UserEntity entity : userList){
				Map map = new HashMap();
				map.put("id", entity.getId());
				map.put("pId", 1);
				map.put("name", entity.getUserName());
				mapList.add(map);
			}
			
			JSONArray jsonArray = JSONArray.fromObject(mapList);
			return jsonArray.toString();
		}catch (RuntimeException e) {
			e.printStackTrace();
			return null;
		}
	}
}
