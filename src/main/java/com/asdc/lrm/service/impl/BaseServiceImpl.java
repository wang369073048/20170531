package com.asdc.lrm.service.impl;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.UUID;

import org.apache.struts2.ServletActionContext;

import com.asdc.lrm.dao.LogDao;
import com.asdc.lrm.entity.LogEntity;
import com.asdc.lrm.util.UtilFile;
import com.asdc.lrm.util.UtilHTTP;
import com.asdc.lrm.util.UtilSession;

public class BaseServiceImpl {

	private LogDao logDao;
	public void setLogDao(LogDao logDao) {
		this.logDao = logDao;
	}
	
	/**
	 * 记录日志
	 * @param operateModule
	 * @param operateAction
	 * @param operateObject
	 */
	protected void log(String operateModule, String operateAction, String operateObject){
        logDao.save(new LogEntity(UtilSession.getUserName(), 
        		UtilHTTP.getHttpServletRequest().getRemoteAddr(), new Date(),
        		operateModule, operateAction, operateObject));
	}
	
	/**
	 * 上传文件
	 * @param file	文件
	 * @param fileName	文件名
	 * @param path	服务器地址
	 * @return
	 */
	protected String fileUploadImg(File file, String fileName, String path){
		UUID uuid = UUID.randomUUID();
		String fileType = getFileExt(fileName);
		String uploadName = uuid+"."+fileType;
		String uploadPath = ServletActionContext.getServletContext().getRealPath("")+UtilFile.separator+path+uploadName;
		writeFile(uploadPath, file);
		return path+uploadName;
	}
	
	/**
	 * 获取文件后缀名
	 * @param filename
	 * @return
	 */
	protected String getFileExt(String filename){
		return filename.substring(filename.lastIndexOf(".")+1);
	}
	
	/**
	 * 文件写入服务器
	 * @param src
	 * @param file
	 */
	protected static void writeFile(String src,File file) {
		try{
			InputStream in = null ;
			OutputStream out = null ;
			try{
				in = new BufferedInputStream(new FileInputStream(file));
				out = new BufferedOutputStream( new FileOutputStream(src));
               
				byte [] buffer = new byte [1024];
				int len = 0; 
				while ((len = in.read(buffer)) > 0) {
					out.write(buffer, 0, len); 
				}
			}finally{
				if ( null != in)  {
					in.close();
				} 
				if ( null != out)  {
					out.close();
				} 
			} 
		} catch (Exception e)  {
			e.printStackTrace();
		} 
	}
}