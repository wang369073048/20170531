package com.asdc.lrm.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import com.asdc.lrm.dao.GroupDao;
import com.asdc.lrm.entity.GroupEntity;
import com.asdc.lrm.entity.common.Constants;
import com.asdc.lrm.service.GroupService;
import com.asdc.lrm.util.UtilException;
import com.asdc.lrm.util.UtilProperties;
import com.asdc.lrm.util.UtilSession;

@SuppressWarnings("unchecked")
public class GroupServiceImpl extends BaseServiceImpl implements GroupService {

	private GroupDao groupDao;
	public void setGroupDao(GroupDao groupDao) {
		this.groupDao = groupDao;
	}
	
	public GroupEntity getById(long id){
		return groupDao.get(id);
	}

	public String fingGroupGrid(long parentId, String keyword, int page, int rows){
		try{
			List<GroupEntity> groupList = groupDao.findGroups(parentId, keyword, page, rows);
			int total = groupDao.findGroupsTotal(parentId, keyword);
	        Map pageMap = new HashMap();
	        pageMap.put("total", total);
	        
	        List dataList = new ArrayList();
	        for(int i=0; i<groupList.size(); i++){
	            Map dataMap = new HashMap();
	            dataMap.put("id", groupList.get(i).getId());
	            dataMap.put("groupName", groupList.get(i).getGroupName());
	            dataMap.put("sortNumber", groupList.get(i).getSortNumber());
	            dataMap.put("createDate", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(groupList.get(i).getCreateTime()));
	            dataList.add(dataMap);
	        }   
	        pageMap.put("rows", dataList);
	        JSONObject object = JSONObject.fromObject(pageMap);
			return object.toString();
		}catch (RuntimeException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public void saveGroup(GroupEntity entity){
		try{
			if(entity == null)
				throw new RuntimeException("entity is null");
			
			entity.setCreator(UtilSession.getUserId());
			entity.setCreateTime(new Date());
			entity.setDeleteMark(0);
			groupDao.save(entity);
			
			super.log(UtilProperties.getPropertyLog(Constants.LOG_MODULE_GROUP), 
					UtilProperties.getPropertyLog(Constants.LOG_ACTION_CREATE), 
					entity.getGroupName());
			
		}catch(RuntimeException e){
			System.err.println(e.getMessage());
			throw new UtilException("保存部门信息时出现异常，请联系管理员");
		}
	}
	
	public void updateGroup(GroupEntity entity){
		try{
			if(entity == null)
				throw new RuntimeException("entity is null");
			
			entity.setUpdateUser(UtilSession.getUserId());
			entity.setUpdateTime(new Date());
			groupDao.update(entity);
			
			super.log(UtilProperties.getPropertyLog(Constants.LOG_MODULE_GROUP), 
					UtilProperties.getPropertyLog(Constants.LOG_ACTION_UPDATE), 
					entity.getGroupName());
			
		}catch(RuntimeException e){
			System.err.println(e.getMessage());
			throw new UtilException("保存部门信息时出现异常，请联系管理员");
		}
	}
	
	public void removeGroup(String ids){
		try{
			String[] idArray = ids.split(",");
			for (String id : idArray) {
				GroupEntity entity = groupDao.get(Long.valueOf(id));
				
				if(entity.getUserSet().size() > 0)
					throw new UtilException("部门下存在用户，不能删除!");
				
				int childCount = groupDao.findGroupsTotal(entity.getId(), null);
				if(childCount > 0)
					throw new UtilException("存在子级部门，不能删除!");
				
				entity.setDeleteMark(1);
				groupDao.update(entity);
				
				super.log(UtilProperties.getPropertyLog(Constants.LOG_MODULE_GROUP), 
						UtilProperties.getPropertyLog(Constants.LOG_ACTION_DELETE), 
						entity.getGroupName());
			}
		}catch(UtilException e){
			if(e.getErrorKey() != null)
				throw new UtilException(e.getErrorKey());
			else
				throw new UtilException("删除系统菜单时出现异常，请联系管理员");
		}
	}
}
