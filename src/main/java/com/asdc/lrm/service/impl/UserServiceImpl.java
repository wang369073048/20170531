package com.asdc.lrm.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONObject;

import com.asdc.lrm.dao.GroupDao;
import com.asdc.lrm.dao.UserDao;
import com.asdc.lrm.entity.GroupEntity;
import com.asdc.lrm.entity.UserEntity;
import com.asdc.lrm.entity.common.Constants;
import com.asdc.lrm.service.UserService;
import com.asdc.lrm.util.UtilException;
import com.asdc.lrm.util.UtilProperties;
import com.asdc.lrm.util.UtilSession;

@SuppressWarnings("unchecked")
public class UserServiceImpl extends BaseServiceImpl implements UserService {

	private UserDao userDao;
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
	
	private GroupDao groupDao;
	public void setGroupDao(GroupDao groupDao) {
		this.groupDao = groupDao;
	}

	public UserEntity getById(long id){
		return userDao.get(id);
	}
	
	public UserEntity findUsersByLoginNameAndPassword(String loginName, String password){
		return userDao.findUsersByLoginNameAndPassword(loginName, password);
	}

	public String findUserGrid(long groupId, String keyword, int page, int rows){
		try {
			UserEntity userEntity = new UserEntity();
			userEntity.setUserType(1);
			if(keyword != null && !keyword.equals("")){
				userEntity.setLoginName(keyword);
			}
			List<UserEntity> userList = userDao.findUsers(groupId, userEntity, page, rows);
			int total = userDao.findUsersTotal(groupId, userEntity);
			
	        Map pageMap = new HashMap();
	        pageMap.put("total", total);
	        
	        List dataList = new ArrayList();
	        for(int i=0; i<userList.size(); i++){
	        	Map dataMap = new HashMap();
	            dataMap.put("id", userList.get(i).getId());
	            dataMap.put("userId", userList.get(i).getId());
	            dataMap.put("userName", userList.get(i).getUserName());
	            dataMap.put("loginName", userList.get(i).getLoginName());
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
	
	public void saveUser(UserEntity entity, long groupId, File file, String fileName){
		try{
			if(groupId != 0){
				/** 设置用户所属部门 **/
				Set<GroupEntity> groupSet = new HashSet<GroupEntity>();
				groupSet.add(groupDao.get(groupId));
				entity.setGroupSet(groupSet);
				
				entity.setUserType(1);
				entity.setCreateTime(new Date());
				entity.setCreator(UtilSession.getUserId());
				entity.setDeleteMark(0);
				userDao.save(entity);
				
				super.log(UtilProperties.getPropertyLog(Constants.LOG_MODULE_USER), 
						UtilProperties.getPropertyLog(Constants.LOG_ACTION_CREATE), 
						entity.getUserName());
				
			}else{
				throw new RuntimeException();
			}
		}catch(RuntimeException e){
			e.printStackTrace();
			throw new UtilException("保存用户信息时出现异常，请联系管理员");
		}
	}
	
	public void updateUser(UserEntity entity, long groupId, File file, String fileName){
		try{
			if(groupId != 0){
				/** 设置用户所属部门 **/
				Set<GroupEntity> groupSet = new HashSet<GroupEntity>();
				groupSet.add(groupDao.get(groupId));
				entity.setGroupSet(groupSet);
				
				UserEntity userEntity = userDao.get(entity.getId());
				entity.setRoleSet(userEntity.getRoleSet());
				
				entity.setUpdateUser(UtilSession.getUserId());
				entity.setUpdateTime(new Date());
				userDao.merge(entity);
				
				super.log(UtilProperties.getPropertyLog(Constants.LOG_MODULE_USER), 
						UtilProperties.getPropertyLog(Constants.LOG_ACTION_UPDATE), 
						entity.getUserName());
				
			}else{
				throw new RuntimeException();
			}
		}catch(RuntimeException e){
			e.printStackTrace();
			throw new UtilException("保存用户信息时出现异常，请联系管理员");
		}
	}
	
	public void removeUser(String ids){
		try{
			String[] idArray = ids.split(",");
			for (String id : idArray) {
				UserEntity entity = userDao.get(Long.valueOf(id));
				entity.setRoleSet(null);
				entity.setDeleteMark(1);
				userDao.update(entity);
				
				super.log(UtilProperties.getPropertyLog(Constants.LOG_MODULE_USER), 
						UtilProperties.getPropertyLog(Constants.LOG_ACTION_DELETE), 
						entity.getUserName());
			}
		}catch(UtilException e){
			if(e.getErrorKey() != null)
				throw new UtilException(e.getErrorKey());
			else
				throw new UtilException("删除用户信息时出现异常，请联系管理员");
		}
	}
	
	public boolean checkLoginName(String loginName, long userId){
		boolean unique = false;
		try{
			UserEntity entity = userDao.findUsersByLoginNameAndPassword(loginName, null);
			if(entity == null)
				unique = true;
			else{
				if(entity.getId() == userId)
					unique = true;
			}
		}catch (RuntimeException e) {
			e.printStackTrace();
		}
		return unique;
	}
	
	public void updateUserPassword(String formerpassword, String newpassword){
		try{
			UserEntity entity = userDao.get(UtilSession.getUserId());
			if(!entity.getPassword().equals(formerpassword))
				throw new UtilException("原始密码输入不正确!");
			
			entity.setPassword(newpassword);
			userDao.update(entity);
		}catch(UtilException e){
			if(e.getErrorKey() != null)
				throw new UtilException(e.getErrorKey());
			else
				throw new UtilException("修改用户密码时出现异常，请联系管理员");
		}
	}
}