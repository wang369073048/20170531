package com.asdc.lrm.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.asdc.lrm.service.ReportUpdateService;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 更新统计报表Action
 */
public class ReportUpdateAction extends ActionSupport{

	private static final long serialVersionUID = 1L;
	
	private String zykId;
	private String beginDate;
	private String endDate;
	private String types;
	private List<String> resultList = new ArrayList<String>();
	
	public String getTypes() {
		return types;
	}
	public void setTypes(String types) {
		this.types = types;
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
	
	private ReportUpdateService reportUpdateService;
	public void setReportUpdateService(ReportUpdateService reportUpdateService) {
		this.reportUpdateService = reportUpdateService;
	}
	
	/**
	 * 统计用户注册增长率
	 * @return
	 * @throws IOException 
	 */
	public String reportUserUpdate(){
		resultList = reportUpdateService.reportUserUpdate(zykId);
		return SUCCESS;
	}
	
	/**
	 * 统计历年用户数据变化
	 * @return
	 * @throws IOException 
	 */
	public String reportUserChange(){
		resultList = reportUpdateService.reportUserChange(zykId);
		return SUCCESS;
	}
	
	/**
	 * 同比上一年课程更新率
	 * @return
	 * @throws IOException 
	 */
	public String reportCourseUpdate(){
		resultList = reportUpdateService.reportCourseUpdate(zykId);
		return SUCCESS;
	}
	
	/**
	 * 统计历年课程数据变化
	 * @return
	 * @throws IOException 
	 */
	public String reportCourseChange(){
		resultList = reportUpdateService.reportCourseChange(zykId);
		return SUCCESS;
	}
	
	/**
	 * 统计资源更新年增长率
	 * @return
	 * @throws IOException 
	 */
	public String reportResourceLastYearUpdate(){
		resultList = reportUpdateService.reportResourceLastYearUpdate(zykId);
		return SUCCESS;
	}
	
	/**
	 * 当年资源素材更新数与资源素材总数的比值
	 * @return
	 * @throws IOException 
	 */
	public String reportResourceTotalCountUpdate(){
		resultList = reportUpdateService.reportResourceTotalCountUpdate(zykId);
		return SUCCESS;
	}
	
	/**
	 * 统计历年资源数据变化
	 * @return
	 * @throws IOException 
	 */
	public String reportResourceChange(){
		resultList = reportUpdateService.reportResourceChange(zykId);
		return SUCCESS;
	}
}
