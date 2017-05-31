package com.asdc.lrm.dao;

import java.util.List;

import com.asdc.lrm.dao.common.Dao;
import com.asdc.lrm.entity.UserEntity;

public interface UserDao extends Dao<UserEntity, Long>{
	
	/**
	 * 根据用户名和密码查询
	 * @param loginName
	 * @param password
	 * @return
	 */
	UserEntity findUsersByLoginNameAndPassword(String loginName, String password);
	
	/**
	 * 根据条件查询，如果条件全部为空则查询全部
	 * @param groupId	
	 * @param entity
	 * @param page
	 * @param rows
	 * @return
	 */
	List<UserEntity> findUsers(long groupId, UserEntity entity, int page, int rows);
	
	/**
	 * 根据条件获取总数
	 * @param groupId
	 * @param entity
	 * @return
	 */
	int findUsersTotal(long groupId, UserEntity entity);
}