package com.asdc.lrm.action;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.asdc.lrm.entity.ZykEntity;
import com.asdc.lrm.entity.common.Constants;
import com.asdc.lrm.service.ReportBasicService;
import com.asdc.lrm.service.ZykLogService;
import com.asdc.lrm.service.ZykService;
import com.asdc.lrm.util.UtilHTTP;
import com.asdc.lrm.util.UtilPDF;
import com.asdc.lrm.util.UtilProperties;
import com.asdc.lrm.util.UtilString;
import com.opensymphony.xwork2.ActionSupport;

/**
 * Basic统计报表Action
 */
public class ReportBasicAction extends ActionSupport{

	private static final long serialVersionUID = 1L;
	
	@Autowired
	private ReportBasicService reportBasicService;
	public void setReportBasicService(ReportBasicService reportBasicService) {
		this.reportBasicService = reportBasicService;
	}
	
	private ZykService zykService;
	public void setZykService(ZykService zykService) {
		this.zykService = zykService;
	}
	
	private ZykLogService zykLogService;
	public void setZykLogService(ZykLogService zykLogService) {
		this.zykLogService = zykLogService;
	}

	private String zykId;
	private String beginDate;
	private String endDate;
	private Map resultMap;
	private List resultList = new ArrayList();
	private String fullName;
	private String instituteInCharge;
	private String personInCharge;
	private String zykNum;
	private String status;
	private String order;
	
	public String getOrder() {
		return order;
	}
	public void setOrder(String order) {
		this.order = order;
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
	public String getPersonInCharge() {
		return personInCharge;
	}
	public void setPersonInCharge(String personInCharge) {
		this.personInCharge = personInCharge;
	}
	public String getZykNum() {
		return zykNum;
	}
	public void setZykNum(String zykNum) {
		this.zykNum = zykNum;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Map getResultMap() {
		return resultMap;
	}
	public void setResultMap(Map resultMap) {
		this.resultMap = resultMap;
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
	public List getResultList() {
		return resultList;
	}
	public void setResultList(List resultList) {
		this.resultList = resultList;
	}
	
	public String reportBasic(){
		
		if(status == null || status.equals("")){
			status = "1";
		}
		
		ZykEntity zyk = new ZykEntity();
		if(fullName != null && !fullName.equals("")){
			zyk.setFullname(fullName);
		}
		if(personInCharge != null && !personInCharge.equals("")){
			zyk.setPersonInCharge(personInCharge);
		}
		if(instituteInCharge != null && !instituteInCharge.equals("")){
			zyk.setInstituteInCharge(instituteInCharge);
		}
		if(zykNum != null && !zykNum.equals("")){
			zyk.setZykNum(zykNum);
		}
		
		if(order != null && "fullname".equalsIgnoreCase(order)){
			resultList = zykService.findByStatusOrderByFullname(status, zyk);
		}else{
			resultList = zykService.findAllOrderByZyknum(status, zyk);
		}
		
		for (int i = 0; i < resultList.size(); i++) {
			ZykEntity entity = (ZykEntity) resultList.get(i);
			for (int j = i+1; j < resultList.size(); j++) {
				ZykEntity zykEntity = (ZykEntity) resultList.get(j);
				if(entity.getZykId().equals(zykEntity.getZykId())){
					entity.setSendColor("red");
					zykEntity.setSendColor("red");
					
					if(zykEntity.getSendFlag() != 2){
						zykEntity.setSendFlag(2);
					}
				}
			}
		}
		
		if(status.equals("1")){
			return "basic_1";
		}else if(status.equals("2")){
			return "basic_2";
		}else if(status.equals("3")){
			return "basic_3";
		}else{
			return INPUT;
		}
	}
	
	public void showAllReportsForCache() throws IOException{
		reportBasicService.showAllReportsForCache();
	}
	
	public String showAllReports() throws IOException{
		resultMap = reportBasicService.showAllReports(zykId);
		HttpServletRequest request = ServletActionContext.getRequest();
		
		List<ZykEntity> zykList = zykService.findZykByZykId(zykId);
		request.setAttribute("zykList", zykList);
		
		String[] timeStrs = zykLogService.getFirstAndLastDate(zykId).split(";");
		String startTime = "";
		String endTime = "";
		if(timeStrs.length != 0){
			startTime = timeStrs[0];
			endTime = timeStrs[1];
		}
		request.setAttribute("zykLogStartTime", startTime);
		request.setAttribute("zykLogEndTime", endTime);
		return SUCCESS;
	}
	
	//导出excel报告
	public String exportExcelBasicReport() throws IOException{
		resultMap = reportBasicService.showAllReports(zykId);
		HttpServletRequest request = ServletActionContext.getRequest();
		
		List<ZykEntity> zykList = zykService.findZykByZykId(zykId);
		request.setAttribute("zykList", zykList);
		
		ZykEntity zykEntity = zykList.get(0);
		StringBuilder status = new StringBuilder("");
		if("3".equals(zykEntity.getStatus())){
			status.append("已验收");
		}else if("2".equals(zykEntity.getStatus())){
			status.append("已立项");
		}else if("1".equals(zykEntity.getStatus())){
			status.append("申报中");
		}
		String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		request.setAttribute("fileName", zykEntity.getFullname()+"_"+zykEntity.getInstituteInCharge()+"_"+status+"_"+date);
		
		String[] timeStrs = zykLogService.getFirstAndLastDate(zykId).split(";");
		String startTime = "";
		String endTime = "";
		if(timeStrs.length != 0){
			startTime = timeStrs[0];
			endTime = timeStrs[1];
		}
		request.setAttribute("zykLogStartTime", startTime);
		request.setAttribute("zykLogEndTime", endTime);
		
		return SUCCESS;
	}
	
	//下发报表 返回字符串
	public void sendReports(){
		PrintWriter writer = null;
		String now = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date());
		
		try {
			writer = UtilHTTP.getHttpServletResponse().getWriter();
			if(UtilString.isNullAndEmpty(fullName)|| UtilString.isNullAndEmpty(instituteInCharge)|| UtilString.isNullAndEmpty(status)){
				writer.print("{'state':'failed','msg':'参数不能为空'}");
			}
			
			//httpclient 提交数据
			CloseableHttpClient client = HttpClients.createDefault();
			String server = UtilProperties.getPropertyConfig("sendReport.server");
			HttpPost post = new HttpPost(server);

			String serverName = UtilHTTP.getHttpServletRequest().getServerName();
			int port = UtilHTTP.getHttpServletRequest().getServerPort();
			String contextPath = UtilHTTP.getHttpServletRequest().getContextPath();
			String url = "http://"+serverName+":"+port+contextPath+"/report_basic/showAllReports.action?zykId="+zykId;
			
			StringBody fn = new StringBody(fullName, ContentType.create("text/html", "UTF-8"));
			StringBody incharge = new StringBody(instituteInCharge, ContentType.create("text/html", "UTF-8"));
			StringBody stus = new StringBody(status, ContentType.create("text/html", "UTF-8"));
			
			if("3".equals(status)){
				status = "已验收";
			}else if("2".equals(status)){
				status = "已立项";
			}else if("1".equals(status)){
				status = "申报中";
			}
			
			File file = UtilPDF.htmlFilePath(fullName, instituteInCharge, status);
			if(!file.exists()){
				try {
					UtilPDF.createLocalHtml(url, file);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			FileBody bin = new FileBody(file);
			
			//多文件提交
			String pdfName = UtilPDF.pdfFilePath(serverName, contextPath, url).getName();
			HttpEntity entity = MultipartEntityBuilder.create()
						.addPart("fullname", fn)
						.addPart("instituteincharge", incharge)
						.addPart("status", stus)
						.addPart(pdfName,bin)
						.build();
			
			post.setEntity(entity);
			
			CloseableHttpResponse response = client.execute(post);
			StatusLine statusLine = response.getStatusLine();
			int statusCode = statusLine.getStatusCode();
			
			if(statusCode == 404){
				reportBasicService.updateZykSendStatus(zykId,2,new Timestamp(System.currentTimeMillis()));
				writer.print("{'state':'failed','sendstatus':'下发失败','sendLastTime':'"+now+"'}");
			}else{
				//返回的结果实例
				HttpEntity result = response.getEntity();
				String resultValue = UtilHTTP.parseInputStream(result.getContent());
				
				if(Constants.SEND_REPORT_FAILED_1.equals(resultValue)){
					reportBasicService.updateZykSendStatus(zykId,2,new Timestamp(System.currentTimeMillis()));
					writer.print("{'state':'failed','sendstatus':'下发失败','sendLastTime':'"+now+"'}");
				}else if(Constants.SEND_REPORT_FAILED_2.equals(resultValue)){
					reportBasicService.updateZykSendStatus(zykId,2,new Timestamp(System.currentTimeMillis()));
					writer.print("{'state':'failed','sendstatus':'下发失败','sendLastTime':'"+now+"'}");
				}else if(Constants.SEND_REPORT_SUCCESS.equals(resultValue)){
					reportBasicService.updateZykSendStatus(zykId,1,new Timestamp(System.currentTimeMillis()));
					writer.print("{'state':'success','sendstatus':'下发成功','sendLastTime':'"+now+"'}");
				}
				EntityUtils.consume(result);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
			reportBasicService.updateZykSendStatus(zykId,2,new Timestamp(System.currentTimeMillis()));
			writer.print("{state:'failed','sendstatus':'下发文件失败','sendLastTime':'"+now+"'}");
		} 
	}
	
}
