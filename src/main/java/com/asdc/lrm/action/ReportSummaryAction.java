package com.asdc.lrm.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;

import org.apache.struts2.ServletActionContext;

import com.asdc.lrm.service.ReportSummaryService;
import com.asdc.lrm.util.UtilHTTP;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 汇总报告Action
 */
public class ReportSummaryAction extends ActionSupport {

	private static final long serialVersionUID = 1L;
	private ReportSummaryService reportSummaryService;
	private List<Map<String,Object>> list;
	private int status;
	private int pageIndex;
	private int count;
	private List<Object> totalityList;
	private List<String> resultList;
	private List<String> dataList;
	
	public List<String> getDataList() {
		return dataList;
	}
	public void setDataList(List<String> dataList) {
		this.dataList = dataList;
	}
	public List<String> getResultList() {
		return resultList;
	}
	public void setResultList(List<String> resultList) {
		this.resultList = resultList;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getPageIndex() {
		return pageIndex;
	}
	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public List<Map<String, Object>> getList() {
		return list;
	}
	public void setList(List<Map<String, Object>> list) {
		this.list = list;
	}
	public ReportSummaryService getReportSummaryService() {
		return reportSummaryService;
	}
	public void setReportSummaryService(ReportSummaryService reportSummaryService) {
		this.reportSummaryService = reportSummaryService;
	}
	public List<Object> getTotalityList() {
		return totalityList;
	}
	public void setTotalityList(List<Object> totalityList) {
		this.totalityList = totalityList;
	}

	/**
	 * 资源库总体情况
	 * @return
	 */
	public String totality(){
		totalityList = reportSummaryService.totality();
		return SUCCESS;
	}
	
	/**
	 * 资源库申报建设总体情况
	 * @return
	 */
	public String declareConstruction(){
		if(pageIndex <= 0){
			pageIndex = 0;
		}else {
			pageIndex = pageIndex * count; 
		}
		if(count <= 0)
			count = 10;
		
		list = reportSummaryService.reportSummary(pageIndex, count, status);
		if(pageIndex > 1){
			try {
				PrintWriter writer = UtilHTTP.getHttpServletResponse().getWriter();
				writer.print(JSONArray.fromObject(list));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else{
			return SUCCESS;
		}
		return null;
	}
	
	/**
	 * 导出资源库申报建设总体情况
	 * @return
	 */
	public String exprot_declareConstruction(){
		HttpServletRequest request = ServletActionContext.getRequest();
		request.setAttribute("fileName", "资源库申报建设总体情况");
		
		pageIndex = 0;
		count = 1000;
		
		list = reportSummaryService.reportSummary(pageIndex, count, status);
		return SUCCESS;
	}
	
	/**
	 * 资源库申报分布情况
	 * @return
	 */
	public String distribution(){
		resultList = reportSummaryService.distribution();
		dataList = reportSummaryService.totalityForTable();
		return SUCCESS;
	}

}
