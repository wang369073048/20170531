package com.asdc.lrm.service;

import java.util.List;
import java.util.Map;

public interface ReportApplicationService {

	/***
	 * 统计用户行为概况
	 * @param zykId
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	Map<String,Object> reportUserData(String zykId, String beginDate, String endDate);

	/***
	 * 统计课程排课次数
	 * @param zykId
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	Map<String,Object> reportCourseCount(String zykId, String beginDate, String endDate);
	
	/***
	 * 不同媒体类型的资源素材使用情况
	 * @param zykId
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	
	Map<String,Object> reportResourceCount(String zykId, String beginDate,
			String endDate);

	/***
	 * 活动用户情况
	 * @param zykId
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	Map<String,Object> reportActivedUserCount(String zykId, String beginDate,
			String endDate);

	/***
	 * 获取前3个课程排名
	 * @param zykId
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	Map<String,Object> reportTopThreeCourseCount(String zykId, String beginDate,
			String endDate);

	/***
	 * 获取前3个素材应用情况
	 * @param zykId
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	List<String> reportTopThreeResourceCount(String zykId, String beginDate, String endDate);

	/***
	 * 被使用的资源素材个数在全部资源素材的占比
	 * @param zykId
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	List<Map<String,Object>> reportUsedResourceRatio(String zykId, String beginDate, String endDate);

	/***
	 * 平均每个资源素材的使用次数
	 * @param zykId
	 * @param beginDate
	 * @param endDate
	 */
	Map<String,Object> reportUsedResourceAVG(String zykId, String beginDate, String endDate);
	
	/***
	 * 根据模块类型返回相应的下的action数量
	 * @param zykId
	 * @param beginDate
	 * @param endDate
	 * @param objectType
	 * @return
	 */
	Map<String,Object> reportActionMapByObjectType(String zykId,String beginDate, String endDate,String objectType,String... actions);

	
	/***
	 * 活动记录的注册用户数比例
	 * @param zykId
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	Map<String,Object> reportActivedUserLogCount(String zykId, String beginDate,
			String endDate);

	
	/**
	 * 人均活动天数
	 * @param zykId
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	Map<String,Object> reportActivedDateUserAVG(String zykId, String beginDate, String endDate);

	/***
	 * 题库题目引用数
	 * @param zykId
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	Map<String, Object> reportCitedquesCount(String zykId, String beginDate,
			String endDate);

	/***
	 * 题库题目使用总次数
	 * @param zykId
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	Map<String, Object> reportQuesUsingCount(String zykId, String beginDate,
			String endDate);

	/**
	 * 累计学习天数
	 * @param zykId
	 * @param beginDate
	 * @param endDate
	 */
	Map<String, Object> reportAccumulatorUserDateCount(String zykId, String beginDate,
			String endDate);

	/***
	 * 论坛活动总次数统计
	 * @param zykId
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	Map<String, Object> reportForumActivedCount(String zykId, String beginDate,
			String endDate);

	/***
	 * 作业活动总次数统计
	 * @param zykId
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	Map<String, Object> reportWorkActivedCount(String zykId, String beginDate,
			String endDate);

	/***
	 * 考试活动总次数统计
	 * @param zykId
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	Map<String, Object> reportExamActivedCount(String zykId, String beginDate,
			String endDate);
	
	
	/***
	 * 资源素材应用情况
	 * @param zykId
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	List<String> reportResourceActionCount(String zykId, String beginDate, String endDate);

}
