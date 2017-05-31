package com.asdc.lrm.service;

import java.util.List;

public interface ReportUpdateService {
	
	/**
	 * 统计用户注册增长率
	 * @param zykId
	 * @param beginDate
	 * @param endDate
	 * @return 
	 */
	public List<String> reportUserUpdate(String zykId);
	
	/**
	 * 统计历年用户数据变化
	 * @param zykId
	 * @param beginDate
	 * @param endDate
	 * @return 
	 */
	public List<String> reportUserChange(String zykId);
	
	/**
	 * 统计历年课程数据变化
	 * @param zykId
	 * @param beginDate
	 * @param endDate
	 * @return 
	 */
	public List<String> reportCourseChange(String zykId);
	
	/**
	 * 统计历年课程数据变化
	 * @param zykId
	 * @param beginDate
	 * @param endDate
	 * @return 
	 */
	public List<String> reportResourceChange(String zykId);
	
	/**
	 * 统计课程更新增长率
	 * @param zykId
	 * @param beginDate
	 * @param endDate
	 * @return 
	 */
	public List<String> reportCourseUpdate(String zykId);
	
	/**
	 * 同比上一年资源更新率
	 * @param zykId
	 * @param beginDate
	 * @param endDate
	 * @return 
	 */
	public List<String> reportResourceLastYearUpdate(String zykId);
	
	/**
	 * 当年资源素材更新数与资源素材总数的比值
	 * @param zykId
	 * @param beginDate
	 * @param endDate
	 * @return 
	 */
	public List<String> reportResourceTotalCountUpdate(String zykId);
}
