package com.asdc.lrm.util;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import javax.servlet.ServletContext;

import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.xhtmlrenderer.extend.FontResolver;
import org.xhtmlrenderer.layout.SharedContext;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

/***
 * 采用flying saucer生成PDF文档
 * 
 * @author sher
 * 
 */
public class UtilPDF {

	private static Logger logger = LoggerFactory.getLogger(UtilPDF.class);
	
	/***
	 * pdf本地路径
	 * @return
	 */
	public static String pdfLocalPath(){
		String path = UtilProperties.getPropertyConfig("pdf.localPath");
		if(UtilString.isNullAndEmpty(path)){
			path = "/pdf";
		}
		ServletContext context = ServletActionContext.getServletContext();
		String localPath = context.getRealPath(path);
		File file = new File(localPath);
		if(!file.exists()){
			file.mkdir();
		}
		return localPath;
	}
	
	/***
	 * html本地路径
	 * @return
	 */
	public static String htmlLocalPath(){
		String path = UtilProperties.getPropertyConfig("html.localPath");
		if(UtilString.isNullAndEmpty(path)){
			path = "/html";
		}
		ServletContext context = ServletActionContext.getServletContext();
		String localPath = context.getRealPath(path);
		File file = new File(localPath);
		if(!file.exists()){
			file.mkdir();
		}
		return localPath;
	}
	
	/***
	 * 根据专业名称，主持单位，状态确认本地是否有pdf
	 * @param fullName
	 * @param instituteInCharge
	 * @param status
	 * @return
	 */
	public static boolean isHasPDF(String fullName,String instituteInCharge,String status){
		if(UtilString.isNullAndEmpty(fullName) || UtilString.isNullAndEmpty(instituteInCharge) || UtilString.isNullAndEmpty(status))
			return false;
		String pdfName = fullName+"_"+instituteInCharge+"_"+status+".pdf";
		String localPath = pdfLocalPath()+"/"+pdfName;
		File file = new File(localPath);
		return file.exists();
	}
	
	/***
	 * pdf文件路径
	 * @param fullName
	 * @param instituteInCharge
	 * @param status
	 * @return
	 */
	public static File pdfFilePath(String fullName,String instituteInCharge,String status){
		if(UtilString.isNullAndEmpty(fullName) || UtilString.isNullAndEmpty(instituteInCharge) || UtilString.isNullAndEmpty(status))
			return null;
		return new File(pdfLocalPath()+"/"+fullName+"_"+instituteInCharge+"_"+status+".pdf");
	}
	
	/***
	 * html文件路径
	 * @param fullName
	 * @param instituteInCharge
	 * @param status
	 * @return
	 */
	public static File htmlFilePath(String fullName,String instituteInCharge,String status){
		if(UtilString.isNullAndEmpty(fullName) || UtilString.isNullAndEmpty(instituteInCharge) || UtilString.isNullAndEmpty(status))
			return null;
		return new File(htmlLocalPath()+"/"+fullName+"_"+instituteInCharge+"_"+status+".html");
	}
	
	/***
	 * 根据URL生成PDF在本地路径下
	 * @param url
	 * @param localPath  本地路径
	 */
	public static void createPDF(String url,OutputStream fos) {
		try {
			ITextRenderer renderer = new ITextRenderer();
			renderer.setDocument(url);
			new UtilPDF().new ITextFontWapper(renderer.getSharedContext(), renderer.getFontResolver()).addFont();
			renderer.layout();
			renderer.createPDF(fos);
		} catch (Exception e) {
			e.printStackTrace();
//			String htmlPath = localPath.substring(0, localPath.lastIndexOf("."))+".html";
//			Document document = htmlChecker(url, htmlPath);
//			createPDF(url, document, localPath,fos);
		}finally{
			if(fos!=null){
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static void createHTML(String url,OutputStream fos) {
		try {
//			ITextRenderer renderer = new ITextRenderer();
//			renderer.setDocument(url);
			URL u = new URL(url);
			HttpURLConnection httpConn = (HttpURLConnection) u.openConnection();
			InputStreamReader input = new InputStreamReader(httpConn.getInputStream(), "utf-8");  
		    BufferedReader bufReader = new BufferedReader(input);
			
		    String line = "";  
		    StringBuilder contentBuf = new StringBuilder();  
		    while ((line = bufReader.readLine()) != null) {  
		        contentBuf.append(line);  
		    }  
		    String buf = contentBuf.toString();
		    fos.write(buf.getBytes());  
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(fos!=null){
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	
	/***
	 * 根据document来生成pdf
	 * @param url
	 * @param document
	 * @param localPath
	 */
	public static void createPDF(String url,Document document,String localPath,OutputStream fos){
		try {
			ITextRenderer renderer = new ITextRenderer();
			renderer.setDocument(document,url);
			new UtilPDF().new ITextFontWapper(renderer.getSharedContext(), renderer.getFontResolver()).addFont();
			renderer.layout();
			renderer.createPDF(fos);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(fos!=null){
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/***
	 * 获取PDF本地文件流
	 * @param url
	 * @param fullName
	 * @param instituteInCharge
	 * @param status
	 * @return
	 */
	public static InputStream getLocalPDFInputStream(String url,String fullName,String instituteInCharge,String status){
		if(UtilString.isNullAndEmpty(fullName) || UtilString.isNullAndEmpty(instituteInCharge) || UtilString.isNullAndEmpty(status))
			return null;
		InputStream in = null;
		try {
			String path = pdfFilePath(fullName, instituteInCharge, status).getPath();
			File file = new File(path);
			if(!file.exists()){
				FileOutputStream fos = new FileOutputStream(path);
				createPDF(url,fos);
			}
			if(file.exists()){
				in = new FileInputStream(file);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return in;
	}
	
	
	public static void createLocalPDF(String url,File file){
		FileOutputStream fos;
		try {
			fos = new FileOutputStream(file);
			createPDF(url,fos);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			logger.error("保存到本地文件出错",e);
		}
	}
	
	public static void createLocalHtml(String url,File file) throws Exception {
		FileOutputStream fos;
		try {
			fos = new FileOutputStream(file);
			createHTML(url,fos);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			logger.error("保存到本地文件出错",e);
		}
	}
	
	/**
	 * 内存中生成PDF流
	 * @param url
	 * @param fullName
	 * @param instituteInCharge
	 * @param status
	 * @return
	 */
	public static InputStream createPDFInputStream(String url,String fullName,String instituteInCharge,String status){
		if(UtilString.isNullAndEmpty(fullName) || UtilString.isNullAndEmpty(instituteInCharge) || UtilString.isNullAndEmpty(status))
			return null;
		String path = pdfFilePath(fullName, instituteInCharge, status).getPath();
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		createPDF(url, bos);
		return new ByteArrayInputStream(bos.toByteArray());
	}
	
	/***
	 * 对不合格的html进行检测并修正
	 * @param url
	 * @param htmlPath
	 * @return
	 */
//	public static Document htmlChecker(String url,String htmlPath){
//		Tidy tidy = new Tidy();
//		tidy.setIndentContent(true);
//		tidy.setXHTML(true);
//		 //是否隐藏注释  
//        tidy.setHideComments(true);   
//          
//        //是否br在一行中显示  
//        tidy.setBreakBeforeBR(true);   
//          
//        //不知道是啥  
//        //tidy.setBurstSlides(false);   
//          
//        //是否删除空的<p></p>  
//        tidy.setDropEmptyParas(false);  
//          
//        //是否用p标签包括文字,如测试html的: plz save me  
//        tidy.setEncloseBlockText(false);  
//        
//        tidy.setOutputEncoding("utf-8"); 
//        tidy.setPrintBodyOnly(false);
//        tidy.setTrimEmptyElements(true); 
//        tidy.setSmartIndent(true);
//        tidy.setInputEncoding("utf-8");
//        Document document = null;
//        try {
//        	URL u = new URL(url);
//        	URLConnection connection = u.openConnection();
//        	FileOutputStream fos = new FileOutputStream(htmlPath);
//			document = tidy.parseDOM(connection.getInputStream(), fos);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		return document;
//	}
//	
	/**
	 * 实现CJKFont字体类
	 * @author sher
	 *
	 */
	public class ITextFontWapper extends ITextFontResolver{
		private ITextFontResolver fontResolver;
		public ITextFontWapper(SharedContext sharedContext,FontResolver font) {
			super(sharedContext);
			this.fontResolver = (ITextFontResolver) font;
		}
		
		public void addFont(){
			try {
				Field field = ITextFontResolver.class.getDeclaredField("_fontFamilies");
				field.setAccessible(true);
				Map map = (Map) field.get(fontResolver);
				Method method = ITextFontResolver.class.getDeclaredMethod("addCJKFonts",Map.class);
				method.setAccessible(true);
				method.invoke(fontResolver, map);
			} catch (Exception e) {
				e.printStackTrace();
			} 
		}
	}

}
