package com.asdc.lrm.action;

import java.util.List;

import com.asdc.lrm.service.ReportApplicationService;
import com.asdc.lrm.util.UtilCharts;
import com.asdc.lrm.util.UtilString;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 应用统计报表Action
 */
public class ReportApplicationAction extends ActionSupport{

	private static final long serialVersionUID = 1L;

	private ReportApplicationService reportApplicationService;

	public void setReportApplicationService(ReportApplicationService reportApplicationService) {
		this.reportApplicationService = reportApplicationService;
	}
	
	private String zykId;
	private String beginDate;
	private String endDate;
	private List<String> resultList;

	public String getZykId() {
		return zykId;
	}
	public void setZykId(String zykId) {
		this.zykId = zykId;
	}
	public String getBeginDate() {
		return beginDate;
	}
	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public List<String> getResultList() {
		return resultList;
	}
	public void setResultList(List<String> resultList) {
		this.resultList = resultList;
	}
	
	/***
	 * 统计用户行为概况
	 * @return
	 */
	public String reportUserData(){
		if(UtilString.isNullAndEmpty(zykId)) 
			return null;
		resultList = UtilCharts.generatePieChartData(reportApplicationService.reportUserData(zykId,beginDate,endDate));
		return SUCCESS;
	}
	
	/***
	 * 课程次数排名
	 * @return
	 */
	public String reportCourseCount(){
		if(UtilString.isNullAndEmpty(zykId)) 
			return null;
		resultList = UtilCharts.generateBarChartData(reportApplicationService.reportCourseCount(zykId,beginDate,endDate),"2");
		return SUCCESS;
	}
	
	/**
	 * 资源应用情况
	 * @return
	 */
	public String reportResourceCount(){
		if(UtilString.isNullAndEmpty(zykId)) 
			return null;
		resultList = UtilCharts.generatePieChartData(reportApplicationService.reportResourceCount(zykId,beginDate,endDate));
		return SUCCESS;
	}
	
	/**
	 * 资源素材应用情况
	 * @return
	 */
	public String reportResourceActionCount(){
		if(UtilString.isNullAndEmpty(zykId)) 
			return null;
		resultList = reportApplicationService.reportResourceActionCount(zykId, beginDate, endDate);
		return SUCCESS;
	}
	
	/***
	 * 不同类型用户浏览频次
	 * @return
	 */
	public String reportActivedUserCount(){
		if(UtilString.isNullAndEmpty(zykId)) 
			return null;
		resultList = UtilCharts.generateBarChartData(reportApplicationService.reportActivedUserCount(zykId,beginDate,endDate),"2");
		return SUCCESS;
	}
	
	/***
	 * 获取课程访问前三
	 * @return
	 */
	public String reportTopThreeCourseCount(){
		if(UtilString.isNullAndEmpty(zykId)) 
			return null;
		resultList = UtilCharts.generateBarChartData(reportApplicationService.reportTopThreeCourseCount(zykId, beginDate, endDate),"2");
		return SUCCESS;
	}
	
	/***
	 * 资源素材浏览和下载频次(前三)
	 * @return
	 */
	public String reportTopThreeResourceCount(){
		if(UtilString.isNullAndEmpty(zykId)) 
			return null;
		resultList = reportApplicationService.reportTopThreeResourceCount(zykId, beginDate, endDate);
		return SUCCESS;
	}
	
	/**
	 * 被使用的资源素材个数在全部资源素材的占比
	 * @return
	 */
	public String reportUsedResourceRatio(){
		if(UtilString.isNullAndEmpty(zykId)) 
			return null;
		resultList = UtilCharts.generatePieChartRatio(reportApplicationService.reportUsedResourceRatio(zykId, beginDate, endDate), "被使用的资源素材个数","未使用的资源素材个数");
		return SUCCESS;
	}
	
	/**
	 * 平均每个资源素材的使用次数
	 * @return
	 */
	public String reportUsedResourceAVG(){
		if(UtilString.isNullAndEmpty(zykId)) 
			return null;
		resultList = UtilCharts.generateChartData(reportApplicationService.reportUsedResourceAVG(zykId, beginDate, endDate));
		return SUCCESS;
	}
	
	/**
	 * 论坛活动模块统计
	 * @return
	 */
	public String reportForumActivedCount(){
		if(UtilString.isNullAndEmpty(zykId)) 
			return null;
		resultList = UtilCharts.generateBarChartDataHasTotal(reportApplicationService.reportForumActivedCount(zykId, beginDate, endDate),"1");
		return SUCCESS;
	}
	
	/**
	 * 作业活动模块统计
	 * @return
	 */
	public String reportWorkActivedCount(){
		if(UtilString.isNullAndEmpty(zykId)) 
			return null;
		resultList = UtilCharts.generateBarChartDataHasTotal(reportApplicationService.reportWorkActivedCount(zykId, beginDate, endDate),"1");
		return SUCCESS;
	}
	
	/**
	 * 考试活动模块统计
	 * @return
	 */
	public String reportExamActivedCount(){
		if(UtilString.isNullAndEmpty(zykId)) 
			return null;
		resultList = UtilCharts.generateBarChartDataHasTotal(reportApplicationService.reportExamActivedCount(zykId, beginDate, endDate),"1");
		return SUCCESS;
	}
	
	/***
	 * 注册用户活动情况占比
	 * @return
	 */
	public String reportActivedUserLogCount(){
		if(UtilString.isNullAndEmpty(zykId)) 
			return null;
		resultList = UtilCharts.generatePieLoopChartDataRatio(reportApplicationService.reportActivedUserLogCount(zykId, beginDate, endDate));
		return SUCCESS;
	}
	
	/**
	 *  人均活动天数
	 * @return
	 */
	public String reportActivedDateUserAVG(){
		if(UtilString.isNullAndEmpty(zykId)) 
			return null;
		resultList = UtilCharts.generateChartData(reportApplicationService.reportActivedDateUserAVG(zykId, beginDate, endDate));
		return SUCCESS;
	}
	
	/***
	 * 题库题目引用数
	 */
	public String reportCitedquesCount(){
		if(UtilString.isNullAndEmpty(zykId)) 
			return null;
		resultList = UtilCharts.generatePieChartData(reportApplicationService.reportCitedquesCount(zykId, beginDate, endDate));
		return SUCCESS;
	}
	
	/***
	 * 题库题目使用总次数
	 */
	public String reportQuesUsingCount(){
		if(UtilString.isNullAndEmpty(zykId)) 
			return null;
		resultList = UtilCharts.generatePieChartData(reportApplicationService.reportQuesUsingCount(zykId, beginDate, endDate));
		return SUCCESS;
	}
	
	/***
	 * 累计学习天数统计
	 * @return
	 */
	public String reportAccumulatorUserDateCount(){
		if(UtilString.isNullAndEmpty(zykId)) 
			return null;
		resultList = UtilCharts.generateBarChartData(reportApplicationService.reportAccumulatorUserDateCount(zykId, beginDate, endDate),"1");
		return SUCCESS;
	} 
}
