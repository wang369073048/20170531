package com.asdc.lrm.action;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import com.asdc.lrm.entity.common.BaseEntity;
import com.asdc.lrm.util.UtilHTTP;
import com.opensymphony.xwork2.ActionSupport;

public abstract class BaseAction<T extends BaseEntity> extends ActionSupport{

	private static final long serialVersionUID = 6476863755246634640L;

	protected T entity;
	protected long id;
	protected int page;
	protected int rows;
	protected long parentId;
	protected String keyword;
	protected String jsonData;
	protected String beginDate;
	protected String endDate;
	protected File imgFile;
	protected String imgFileFileName;
	
	public T getEntity() {
		return entity;
	}
	public void setEntity(T entity) {
		this.entity = entity;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getRows() {
		return rows;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}
	public long getParentId() {
		return parentId;
	}
	public void setParentId(long parentId) {
		this.parentId = parentId;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public String getJsonData() {
		return jsonData;
	}
	public void setJsonData(String jsonData) {
		this.jsonData = jsonData;
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
	public File getImgFile() {
		return imgFile;
	}
	public void setImgFile(File imgFile) {
		this.imgFile = imgFile;
	}
	public String getImgFileFileName() {
		return imgFileFileName;
	}
	public void setImgFileFileName(String imgFileFileName) {
		this.imgFileFileName = imgFileFileName;
	}
	
	public void outPrint(){
		HttpServletResponse response = UtilHTTP.getHttpServletResponse();
		response.setContentType("text/html;charset=GBK");
		PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		out.print(jsonData);
		out.flush();
		out.close();
	}
}