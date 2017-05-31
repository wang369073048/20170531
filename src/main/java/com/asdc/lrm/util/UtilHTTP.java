package com.asdc.lrm.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;


public class UtilHTTP {

	public static HttpServletRequest getHttpServletRequest(){
		return ServletActionContext.getRequest();
	}
	
	public static HttpServletResponse getHttpServletResponse(){
		return ServletActionContext.getResponse();
	}
	
	public static HttpSession getSession(){
		return ServletActionContext.getRequest().getSession();
	}
	
	public static ServletContext getServletContext(){
		return ServletActionContext.getServletContext();
	}
	
	public static String parseInputStream(InputStream in){
		if(in == null) 
			return null;
		ByteArrayOutputStream bos = null;
		String result = null;
		try {
			bos = new ByteArrayOutputStream();
			int len;
			byte[] buffer= new byte[1024];
			while((len = in.read(buffer)) != -1){
				bos.write(buffer, 0, len);
			}
			result = new String(bos.toByteArray(),"UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(in != null){
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(bos != null){
				try {
					bos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return result.trim();
	}
}
