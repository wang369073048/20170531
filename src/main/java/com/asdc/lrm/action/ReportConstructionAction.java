package com.asdc.lrm.action;

import java.util.ArrayList;
import java.util.List;

import com.asdc.lrm.service.ReportConstructionService;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 建设统计报表Action
 */
public class ReportConstructionAction extends ActionSupport{

	private static final long serialVersionUID = 1L;
	
	private String zykId;
	private String beginDate;
	private String endDate;
	private List<String> resultList = new ArrayList<String>();
	
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
	public String getZykId() {
		return zykId;
	}
	public void setZykId(String zykId) {
		this.zykId = zykId;
	}
	public List<String> getResultList() {
		return resultList;
	}
	public void setResultList(List<String> resultList) {
		this.resultList = resultList;
	}

	
	private ReportConstructionService reportConstructionService;
	public void setReportConstructionService(ReportConstructionService reportConstructionService) {
		this.reportConstructionService = reportConstructionService;
	}
	
	/**
	 * 用户总数
	 * @return
	 */
	public String reportUserCount(){
		resultList = reportConstructionService.reportUserCount(zykId, beginDate, endDate);
		return SUCCESS;
	}
	
	/**
	 * 用户来自的院校数
	 * @return
	 */
	public String reportInstituteCount(){
		resultList = reportConstructionService.reportInstituteCount(zykId, beginDate, endDate);
		return SUCCESS;
	}
	
	/**
	 * 用户来自的省级行政区数
	 * @return
	 */
	public String reportProvinceCount(){
		resultList = reportConstructionService.reportProvinceCount(zykId, beginDate, endDate);
		return SUCCESS;
	}
	
	/**
	 * 资源素材总数
	 * @return
	 */
	public String reportResourceCount(){
		resultList = reportConstructionService.reportResourceCount(zykId, beginDate, endDate);
		return SUCCESS;
	}
	
	/**
	 * 不同教学应用的资源素材分布
	 * @return
	 */
	public String reportInstructionCount(){
		resultList = reportConstructionService.reportInstructionCount(zykId, beginDate, endDate);
		return SUCCESS;
	}
	
	/**
	 * 专业知识点数量
	 * @return
	 */
	public String reportKnowledgeCount(){
		resultList = reportConstructionService.reportKnowledgeCount(zykId, beginDate, endDate);
		return SUCCESS;
	}
	
	/**
	 * 课程数
	 * @return
	 */
	public String reportCourseCount(){
		resultList = reportConstructionService.reportCourseCount(zykId, beginDate, endDate);
		return SUCCESS;
	}
	
	/**
	 * 不同类型课程分布 
	 * @return
	 */
	public String reportCourseTypeCount(){
		resultList = reportConstructionService.reportCourseTypeCount(zykId, beginDate, endDate);
		return SUCCESS;
	}
	
	/**
	 * 不同层级课程分布 
	 * @return
	 */
	public String reportCourseLevelCount(){
		resultList = reportConstructionService.reportCourseLevelCount(zykId, beginDate, endDate);
		return SUCCESS;
	}
	
	/**
	 * 各门课程模块分布
	 * @return
	 */
	public String reportCourseModuleCount(){
		resultList = reportConstructionService.reportCourseModuleCount(zykId, beginDate, endDate);
		return SUCCESS;
	}
	
	/**
	 * 课程引用的资源素材占所有资源素材比
	 * @return
	 */
	public String reportCourseQuoteResource(){
		resultList = reportConstructionService.reportCourseQuoteResource(zykId, beginDate, endDate);
		return SUCCESS;
	}
	
	/**
	 * 题库题目总数
	 * @return
	 */
	public String reportQuestionCount(){
		resultList = reportConstructionService.reportQuestionCount(zykId);
		return SUCCESS;
	}
	
}
