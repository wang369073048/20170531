package com.asdc.lrm.action;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.asdc.lrm.service.ZykService;
import com.opensymphony.xwork2.ActionSupport;

public class ZykAction extends ActionSupport{

	private static final long serialVersionUID = 1L;
	
	private ZykService zykService;
	public void setZykService(ZykService zykService) {
		this.zykService = zykService;
	}

	private String status;
	private String beginDate;
	private String endDate;
	private String jsonData;
	private String zykId;
	
	public String getZykId() {
		return zykId;
	}
	public void setZykId(String zykId) {
		this.zykId = zykId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
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
	public String getJsonData() {
		return jsonData;
	}
	public void setJsonData(String jsonData) {
		this.jsonData = jsonData;
	}

	/**
	 * 按专业名称获取资源库信息
	 * 返回json数据，共页面combo使用
	 */
	public void findZykFroComboFullname() {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("UTF-8");
		try {
			jsonData = zykService.findZykFroComboFullname(status);
			response.getWriter().print(jsonData);
		}catch (RuntimeException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 按专业名称获取资源库信息
	 * 返回json数据，共页面combo使用
	 */
	public void findZykFroComboInstituteInCharge() {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("UTF-8");
		try {
			jsonData = zykService.findZykFroComboInstituteInCharge(status);
			response.getWriter().print(jsonData);
		}catch (RuntimeException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 按城市获取资源库信息
	 * 返回json数据，共页面combo使用
	 */
	public void findZykFroComboCity() {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("UTF-8");
		try {
			jsonData = zykService.findZykFroComboCity(status);
			response.getWriter().print(jsonData);
		}catch (RuntimeException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
}
