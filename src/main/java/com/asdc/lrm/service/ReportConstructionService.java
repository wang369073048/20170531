package com.asdc.lrm.service;

import java.util.List;

public interface ReportConstructionService {
	
	/**
	 * 统计用户数量
	 * @param zykId
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public List<String> reportUserCount(String zykId, String beginDate, String endDate);
	
	/**
	 * 用户院校分布
	 * @param zykId
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public List<String> reportInstituteCount(String zykId, String beginDate, String endDate);
	
	/**
	 * 用户院校分布
	 * @param zykId
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public List<String> reportProvinceCount(String zykId, String beginDate, String endDate);
	
	/**
	 * 资源素材总数
	 * @param zykId
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public List<String> reportResourceCount(String zykId, String beginDate, String endDate);
	
	/**
	 * 不同教学应用的资源素材分布
	 * @param zykId
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public List<String> reportInstructionCount(String zykId, String beginDate, String endDate);
	
	/**
	 * 专业知识点数量
	 * @param zykId
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public List<String> reportKnowledgeCount(String zykId, String beginDate, String endDate);
	
	/**
	 * 课程数
	 * @param zykId
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public List<String> reportCourseCount(String zykId, String beginDate, String endDate);
	
	/**
	 * 不同层级课程分布
	 * @param zykId
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public List<String> reportCourseTypeCount(String zykId, String beginDate, String endDate);
	
	/**
	 * 不同层级课程分布 
	 * @param zykId
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public List<String> reportCourseLevelCount(String zykId, String beginDate, String endDate);
	
	/**
	 * 各门课程的模块数
	 * @param zykId
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public List<String> reportCourseModuleCount(String zykId, String beginDate, String endDate);
	
	/**
	 * 课程引用的资源素材占所有资源素材比
	 * @param zykId
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public List<String> reportCourseQuoteResource(String zykId, String beginDate, String endDate);
	
	/**
	 * 题库题目总数
	 * @param zykId
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public List<String> reportQuestionCount(String zykId);
	
}
