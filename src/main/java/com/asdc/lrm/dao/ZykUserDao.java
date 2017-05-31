package com.asdc.lrm.dao;

import java.util.List;

import com.asdc.lrm.dao.common.Dao;
import com.asdc.lrm.entity.ZykUserEntity;

public interface ZykUserDao extends Dao<ZykUserEntity, String>{

	/**
	 * 根据zykId查询
	 * @param zykId
	 * @return
	 */
	ZykUserEntity getUserByZykId(String zykId);

	/**
	 * 根据zykId 及createdTime 查询用户数量
	 * @param zykId
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	List<Object[]> findUsers(String zykId, String beginDate, String endDate);
	
	/**
	 * 根据院校查询用户分布
	 * @param zykId
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	List<Object[]> findUserByInstitute(String zykId, String beginDate, String endDate);
	
	/**
	 * 根据院校查询用户分布
	 * @param zykId
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	List<Object[]> findUserByProvince(String zykId, String beginDate, String endDate);
	
	/***
	 * 根据zykId 与 注册时间
	 * 获取用户数量
	 * @param zykId
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	int getUsersCount(String zykId, String beginDate, String endDate);
	
	/**
	 * 按年份分组，查询用户数量
	 * @param zykId
	 * @param yearDate
	 * @return
	 */
	List<Object[]> findUserCountGroupByYear(String zykId, int lastYear);
	
	/**
	 * 根据zykId 查询zyk_user中全部数据
	 * @param zykId
	 * @return
	 */
	List<ZykUserEntity> findAllUserByZykId(String zykId);
	
	/**
	 * 查询全部用户数量
	 * @return
	 */
	int findUserTotalCount();
	
	/**
	 * 根据userId 查询zyk_user中全部数据
	 * @param userId
	 * @return
	 */
	ZykUserEntity findUserByUserIdAndZykId(String userId, String zykId);
}
