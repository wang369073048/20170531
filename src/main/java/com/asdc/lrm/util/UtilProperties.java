package com.asdc.lrm.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.servlet.ServletContext;

import org.apache.struts2.ServletActionContext;

public class UtilProperties {
	
	private static Properties properties_config;
	private static Properties properties_log;
	private static Properties properties_struts;
	private static Properties properties_zykLog;
	
	private static void initPropertiesConfig(){
		properties_config = new Properties();
		try {
			ServletContext context = ServletActionContext.getServletContext();
			InputStream in = context.getResourceAsStream("/WEB-INF/classes/config.properties");
			properties_config.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String getPropertyConfig(String key){
		if(null == properties_config)
			initPropertiesConfig();
		return properties_config.getProperty(key);
	}
	
	private static void initPropertiesLog(){
		properties_log = new Properties();
		try {
			ServletContext context = ServletActionContext.getServletContext();
			InputStream in = context.getResourceAsStream("/WEB-INF/classes/properties/log.properties");
			properties_log.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	public static String getPropertyLog(String key){
		if(null == properties_log)
			initPropertiesLog();
		return properties_log.getProperty(key);
	}
	
	private static void initPropertiesStruts(){
		properties_struts = new Properties();
		try {
			ServletContext context = ServletActionContext.getServletContext();
			InputStream in = context.getResourceAsStream("/WEB-INF/classes/struts.properties");
			properties_struts.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	public static String getPropertyStruts(String key){
		if(null == properties_struts)
			initPropertiesStruts();
		return properties_struts.getProperty(key);
	}
	
	private static void initPropertiesZykLog(){
		properties_zykLog = new Properties();
		try {
			
			//ServletContext context = ServletActionContext.getServletContext();
			InputStream in = UtilProperties.class.getClassLoader().getResourceAsStream("zykLog.properties");
			properties_zykLog.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static String getPropertyZykLog(String key){
		if(null == properties_zykLog)
			initPropertiesZykLog();
		return properties_zykLog.getProperty(key);
	}
}
