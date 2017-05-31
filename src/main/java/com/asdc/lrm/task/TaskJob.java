package com.asdc.lrm.task;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.asdc.lrm.entity.ZykEntity;
import com.asdc.lrm.service.ZykService;
import com.asdc.lrm.util.UtilPDF;
import com.asdc.lrm.util.UtilString;

/***
 * 任务类
 * @author sher
 *
 */
public class TaskJob {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private ZykService zykService;
	public void setZykService(ZykService zykService) {
		this.zykService = zykService;
	}

	/**
	 * 生成PDF工作类
	 * @throws IOException 
	 */
	public void createPDF() throws IOException{
		
		List<ZykEntity> zykEntityList = zykService.findAllOrderByFullname();
		
		if(zykEntityList == null || zykEntityList.size() == 0){
			logger.error("资源库数据为空");
			return ;
		}
		
//		System.out.println("=========开始生成PDF======");
//		logger.info("=========开始生成PDF======"+new Date().toLocaleString());
//		long startTime = System.currentTimeMillis();
		
		for(int i = 0 ; i < zykEntityList.size(); i++){
			ZykEntity zyk = zykEntityList.get(i);
			String zykId = zyk.getZykId();
			String status = zyk.getStatus();
			//生成pdf文件流
			String[] urlArray = getServerUrl();
			String url = "http://"+urlArray[0]+":"+urlArray[1]+"/"+urlArray[1]+"/report_basic/showAllReports.action?zykId="+zykId;
			
			if("3".equals(status)){
				status = "已验收";
			}else if("2".equals(status)){
				status = "已立项";
			}else if("1".equals(status)){
				status = "申报中";
			}
			
			String pdfFile = getPDFFilePath(zyk, status);
			UtilPDF.createLocalPDF(url, new File(pdfFile));
		}
		
//		System.out.println("============结束===========所用时间："+(System.currentTimeMillis()-startTime));
//		logger.info("============结束===========所用时间："+(System.currentTimeMillis()-startTime));
	}


	private String getPDFFilePath(ZykEntity zyk, String status)
			throws IOException {
		WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
		ServletContext context = webApplicationContext.getServletContext();
		InputStream in = context.getResourceAsStream("/WEB-INF/classes/config.properties");
		
		context.getServletContextName();
		Properties properties = new Properties();
		properties.load(in);
		String property = properties.getProperty("pdf.localPath");
		if(UtilString.isNullAndEmpty(property)){
			property = "/pdf";
		}
		String path = context.getRealPath(property);
		File file = new File(path);
		if(!file.exists()){
			file.mkdir();
		}
		String pdfFile = path +"/"+zyk.getFullname()+"_"+zyk.getInstituteInCharge()+"_"+status+".pdf";
		return pdfFile;
	}
	
	public String[] getServerUrl(){
		WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
		ServletContext context = webApplicationContext.getServletContext();
		InputStream in = context.getResourceAsStream("/WEB-INF/classes/config.properties");
		
		context.getServletContextName();
		Properties properties = new Properties();
		try {
			properties.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		String[] url = new String[3];
		url[0] = properties.getProperty("server.ip");
		url[1] = properties.getProperty("server.port");
		url[2] = properties.getProperty("server.path");
		
		return url;
	}
	
}
