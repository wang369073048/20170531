package com.asdc.lrm.dao;

import java.util.List;
import java.util.Map;

import com.asdc.lrm.dao.common.Dao;
import com.asdc.lrm.entity.ZykLogEntity;

public interface ZykLogDao extends Dao<ZykLogEntity, String>{

	/***
	 * 根据资源Id获取实体类
	 * @param zykId
	 * @return
	 */
	List<ZykLogEntity> getZykLogByZykId(String zykId);

	/***
	 * 根据objectType 和action来查询相应的总数
	 * @param zykId
	 * @param beginDate
	 * @param endDate
	 * @param objectType
	 * @param action
	 * @return
	 */
	int getLogCount(String zykId, String beginDate, String endDate,String objectType,String action);

	/***
	 * 统计各个objectType下的数量
	 * @param endDate 
	 * @param beginDate 
	 * @param zykId 
	 * @return
	 */
	Map<String, Object> getLogObjectTypeCountMap(String zykId, String beginDate, String endDate);
	
	/***
	 * 统计objectType下用户的行为的数量
	 * @param endDate 
	 * @param beginDate 
	 * @param zykId 
	 * @return
	 */
	Map<String, Object> getObjectTypeUserCountMap(String zykId, String beginDate, String endDate);
	
	/***
	 * 统计各个课程的数量
	 * @param zykId
	 * @param beginDate
	 * @param endDate
	 * @param objectType
	 * @param limitNum  前几个数目
	 * @return
	 */
	Map<String, Object> getLogCourseCountMap(String zykId,String beginDate,String endDate,String objectType,String limitNum);
	
	/***
	 * 统计素材库各个mediaType下action的数量  
	 * 			如果actions为空 统计各个mediaType的总数量
	 * @param zykId
	 * @param beginDate
	 * @param endDate
	 * @param objectType
	 * @param action
	 * @return {mediaType,count}
	 */
	Map<String, Object> getLogResourceCountMap(String zykId,String beginDate,String endDate,String objectType,String limitNum,String... actions);

	/**
	 * 获取已经使用素材个数
	 * @param zykId
	 * @param beginDate
	 * @param endDate
	 * @param objectType
	 * @return
	 */
	int getUsedResourceCount(String zykId,String beginDate,String endDate,String objectType);
	
	/***
	 * 获取各个模块下面action的数量   
	 * 		如果actions参数为空，获取objectType下各个action的数量
	 * @param zykId
	 * @param beginDate
 	 * @param objectType
	 * @param action
	 * @return
	 */
	Map<String, Object> getActionCountMap(String zykId,String beginDate,String endDate,String objectType,String... action);
	
	/***
	 * 有活动记录的注册用户数
	 * 
	 * type   1 ： 有资源浏览和下载活动记录的注册用户数量
	 * 	       2 ： 有课程浏览及学习活动记录的注册用户数量
	 * @param zykId
	 * @param beginDate
	 * @param endDate
	 * @param objectType
	 * @param type 
	 * @return
	 */
	int getActivedUserCount(String zykId,String beginDate,String endDate,String objectType,String type);
	
	/***
	 * 获取活动用户天数
	 * @param zykId
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	int getActivedUserDateCount(String zykId,String beginDate,String endDate);
	
	
	/**
	 * 根据角色获取活动总次数
	 * @param zykId
	 * @param beginDate
	 * @param endDate
	 * @param role 不能为空
	 * @return
	 */
	int getActivedUserCountByRole(String zykId,String beginDate,String endDate,String role);
	
	/***
	 * 获取用户累计学习天数
	 * @param zykId
	 * @param beginDate
	 * @param endDate
	 * @param role  不能为空
	 * @return
	 */
	int getActivedUserDateCountByRole(String zykId,String beginDate,String endDate,String role);
	
	
	/****
	 * 统计用户角色在各个objectType中含有的各个action的数量
	 * 	 如果actions为空 统计各个objectType的数量
	 * @param zykId
	 * @param beginDate
	 * @param endDate
	 * @param role
	 * @param objectType
	 * @param limitNum
	 * @param actions
	 * @return
	 */
	Map<String, Object> getActivedUserCountMap(String zykId,String beginDate,String endDate,String role,String objectType,String limitNum,String...actions);
	
	
	/***
	 * 获取每个人的名称和活动天数
	 * @param zykId
	 * @param beginDate
	 * @param endDate
	 * @param role
	 * @param objectType
	 * @param limitNum
	 * @param actions
	 */
	Map<String, Object> getActivedUserDateCountMap(String zykId,String beginDate,String endDate,String role,String objectType,String limitNum,String...actions);
	
	/***
	 * 根据zykid获取表中的起始时间
	 * @param zykId
	 */
	Object getFirstLogData(String zykId);
	
	/**
	 * 不同文件类型资源素材浏览和下载频次
	 * @param zykId
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	List<Object[]> getLogCourseCountList(String zykId,String beginDate,String endDate);
	
	/**
	 * 查询资源素材应用情况
	 * 浏览资源;评价资源;分享资源;下载资源
	 */
	List<Object[]> findResourceActionCount(String zykId, String beginDate, String endDate);
	
	/**
	 * 注册用户活动情况
	 */
	List<Object[]> findUserActiveCount(String zykId);
	
	/**
	 * 不同文件类型资源素材浏览和下载频次
	 */
	List<Object[]> resourceUsingAndDownloadCountList(String zykId);
	
	/***
	 * 根据zykid获取表中的起止时间
	 * @param zykId
	 */
	List<Object[]> getFirstDate(String zykId);
	/***
	 * 根据zykid获取表中的起止时间
	 * @param zykId
	 */
	List<Object[]> getLastDate(String zykId);
	
	/**
	 * 根据logId, zykId 查询zyk_log中全部数据
	 * @param logId, zykId
	 * @return
	 */
	ZykLogEntity findLogByLogIdAndZykId(String logId, String zykId);
}
