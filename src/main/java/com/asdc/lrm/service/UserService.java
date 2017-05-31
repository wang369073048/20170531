package com.asdc.lrm.service;

import java.io.File;

import com.asdc.lrm.entity.UserEntity;

public interface UserService {
	
	/**
	 * 根据ID查询
	 * @param id
	 * @return
	 */
	UserEntity getById(long id);
	
	/**
	 * 根据用户名和密码查询
	 * @param loginName
	 * @param password
	 * @return
	 */
	UserEntity findUsersByLoginNameAndPassword(String loginName, String password);
	
	/**
	 * 根据关键字查询指定部门中的用户
	 * @param groupId
	 * @param keyword
	 * @param page
	 * @param rows
	 * @return
	 */
	String findUserGrid(long groupId, String keyword, int page, int rows);
	
	/**
	 * 保存用户信息，并上传用户照片
	 * @param entity
	 * @param groupId
	 * @param file
	 * @param fileName
	 */
	void saveUser(UserEntity entity, long groupId, File file, String fileName);
	
	/**
	 * 修改用户信息，并上传用户照片
	 * @param entity
	 * @param groupId
	 * @param file
	 * @param fileName
	 */
	void updateUser(UserEntity entity, long groupId, File file, String fileName);
	
	/**
	 * 批量删除用户信息
	 * @param ids
	 */
	void removeUser(String ids);
	
	/**
	 * 判断用户名是否已存在
	 * @param loginName
	 * @param userId
	 * @return
	 */
	boolean checkLoginName(String loginName, long userId);
	
	/**
	 * 修改用户密码
	 * @param formerpassword
	 * @param newpassword
	 */
	void updateUserPassword(String formerpassword, String newpassword);
}
