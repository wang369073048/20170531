package com.asdc.lrm.util;

import java.io.File;
import java.util.Calendar;

import org.apache.struts2.ServletActionContext;

public class UtilFile {

    public static String separator = "/";//windows、linux都可识别
	
	public static String createSysMenuImgDir(){
		Calendar c =  Calendar.getInstance() ;
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH)+1;
		int day = c.get(Calendar.DAY_OF_MONTH);
		String rootPath = ServletActionContext.getServletContext().getRealPath("")+UtilFile.separator;
		String calendarPath = "upload"+UtilFile.separator+"sysMenuImg" +UtilFile.separator+year+UtilFile.separator+month+UtilFile.separator+day+UtilFile.separator;
		
		File file = new File(rootPath+calendarPath);
		if(!file.exists()){
			file.mkdirs();
		}
		return calendarPath;
	}
	
	public static String createUserImgDir(){
		Calendar c =  Calendar.getInstance() ;
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH)+1;
		int day = c.get(Calendar.DAY_OF_MONTH);
		String rootPath = ServletActionContext.getServletContext().getRealPath("")+UtilFile.separator;
		String calendarPath = "upload"+UtilFile.separator+"userImg" +UtilFile.separator+year+UtilFile.separator+month+UtilFile.separator+day+UtilFile.separator;
		
		File file = new File(rootPath+calendarPath);
		if(!file.exists()){
			file.mkdirs();
		}
		return calendarPath;
	}
	
	public static String createExcelDir(){
		String rootPath = ServletActionContext.getServletContext().getRealPath("")+UtilFile.separator;
		String calendarPath = "upload"+UtilFile.separator+"excel" +UtilFile.separator;
		File file = new File(rootPath+calendarPath);
		if(!file.exists()){
			file.mkdirs();
		}
		return calendarPath;
	}
	
	public static void deleteFile(String filePath){
		try{
			File file = new File(filePath);
			if(file.exists()){
				file.delete();
			}
		} catch(RuntimeException e){
			e.printStackTrace();
		}
	}
}