package com.asdc.lrm.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.asdc.lrm.dao.SysMenuDao;
import com.asdc.lrm.dao.UserDao;
import com.asdc.lrm.entity.RoleEntity;
import com.asdc.lrm.entity.SysMenuEntity;
import com.asdc.lrm.entity.UserEntity;
import com.asdc.lrm.service.LoginService;
import com.asdc.lrm.util.UtilException;
import com.asdc.lrm.util.UtilSession;
import com.opensymphony.xwork2.ActionContext;

@SuppressWarnings("unchecked")
public class LoginServiceImpl implements LoginService {

	private UserDao userDao;
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
	
	private SysMenuDao sysMenuDao;
	public void setSysMenuDao(SysMenuDao sysMenuDao) {
		this.sysMenuDao = sysMenuDao;
	}
	
	public void lonig(String loginName, String password){
		try{
			ActionContext actionContext = ActionContext.getContext();
			Map session = actionContext.getSession();
			
			UserEntity userEntity = userDao.findUsersByLoginNameAndPassword(loginName, password);
			if(userEntity != null){
				if(userEntity.getEnabled() == 1)
					throw new UtilException("该用户为未启用状态，不能使用，请联系管理员!");
				
				Set<RoleEntity> roleSet = userEntity.getRoleSet();
			    if(roleSet.size() == 0)
			    	throw new UtilException("您无权登录系统，请联系管理员!");
			    
			    session.put("userEntity", userEntity);
			}else{
				throw new UtilException("用户名密码不匹配!");
			}
		}catch(UtilException e){
			if(e.getErrorKey() != null)
				throw new UtilException(e.getErrorKey());
			else
				throw new UtilException("登录系统时出现异常，请联系管理员!");
		}
	}
	
	public List<SysMenuEntity> firstMenu(){
		try{
			UserEntity user = userDao.get(UtilSession.getUserId());
			Set<RoleEntity> roleSet = user.getRoleSet();
			List<Long> roleIds = new ArrayList<Long>();
			for (RoleEntity role : roleSet) {
				roleIds.add(role.getId());
			}
			return sysMenuDao.findSysMenusByRole(roleIds, 0, 1);
		}catch(RuntimeException e){
			e.printStackTrace();
			return null;
		}
	}
	
	public List<SysMenuEntity> secondMenu(long parentId){
		try{
			UserEntity user = userDao.get(UtilSession.getUserId());
			Set<RoleEntity> roleSet = user.getRoleSet();
			List<Long> roleIds = new ArrayList<Long>();
			for (RoleEntity role : roleSet) {
				roleIds.add(role.getId());
			}
			return sysMenuDao.findSysMenusByRole(roleIds, parentId, 1);
		}catch(RuntimeException e){
			e.printStackTrace();
			return null;
		}
	}
}
