package com.asdc.lrm.action;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.asdc.lrm.util.UtilExcel;
import com.asdc.lrm.util.UtilHTTP;
import com.asdc.lrm.util.UtilPDF;
import com.asdc.lrm.util.UtilString;
import com.opensymphony.xwork2.ActionSupport;

/***
 * 下载资源action
 */
public class DownLoadAction  extends ActionSupport {

	private static final long serialVersionUID = 1L;
	
	private String fileName;					//文件名
	private InputStream inputStream;			//文件流
	private String title;
	private String total;
	private String value;
	private String zykId;
	private String fullName;
	private String instituteInCharge;
	private String status;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public void setTotal(String total) {
		this.total = total;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public InputStream getInputStream() {
		return inputStream;
	}
	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getInstituteInCharge() {
		return instituteInCharge;
	}
	public void setInstituteInCharge(String instituteInCharge) {
		this.instituteInCharge = instituteInCharge;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getZykId() {
		return zykId;
	}
	public void setZykId(String zykId) {
		this.zykId = zykId;
	}
	
	/**
	 * 导出excel
	 * @return
	 */
	public String exportExcel(){
		if(UtilString.isNullAndEmpty(value)) return null;
		try {
			List<String[]> data = UtilString.parseKeyValueData(value);
			if(!UtilString.isNullAndEmpty(total)){
				String[] totals = new String[]{"总数",total};
				data.add(totals);
			}
			fileName = new String(title.getBytes("GBK"),"ISO8859-1") + "_"+ new SimpleDateFormat("yyyy-MM-dd").format(new Date())+".xls";
			inputStream = UtilExcel.generateExcelDataInputStream(data, title);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	/**
	 * 导出PDF
	 * @return
	 */
	public String exportPDF(){
		if(UtilString.isNullAndEmpty(fullName) || UtilString.isNullAndEmpty(zykId) ||
				UtilString.isNullAndEmpty(instituteInCharge) || UtilString.isNullAndEmpty(status))
			return null;
		try {
			String serverName = UtilHTTP.getHttpServletRequest().getServerName();
			int port = UtilHTTP.getHttpServletRequest().getServerPort();
			String contextPath = UtilHTTP.getHttpServletRequest().getContextPath();
			String url = "http://"+serverName+":"+port+contextPath+"/report_basic/showAllReports.action?zykId="+zykId;
			if("3".equals(status)){
				status = "已验收";
			}else if("2".equals(status)){
				status = "已立项";
			}else if("1".equals(status)){
				status = "申报中";
			}else{
				return null;
			}
			String pdfName = fullName+"_"+instituteInCharge+"_"+status;
			fileName = new String(pdfName.getBytes("GBK"),"ISO8859-1") + "_"+ new SimpleDateFormat("yyyy-MM-dd").format(new Date())+".pdf";
			inputStream = UtilPDF.getLocalPDFInputStream(url, fullName, instituteInCharge, status);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
}
