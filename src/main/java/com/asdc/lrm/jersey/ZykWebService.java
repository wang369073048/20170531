package com.asdc.lrm.jersey;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;

import com.asdc.lrm.service.ZykService;
import com.asdc.lrm.util.UtilHTTP;
import com.asdc.lrm.util.UtilJSON;
import com.sun.jersey.spi.resource.Singleton;

@Path("/zyk")
@Singleton
@Controller
public class ZykWebService {
	
	@Autowired  
	@Qualifier("zykService")  
	private ZykService zykService;
	public void setZykService(ZykService zykService) {
		this.zykService = zykService;
	}
	
	public ZykService getZykService() {
		return zykService;
	}

	/**
	 * 上报的资源库信息
	 * @return
	 */
	@Path("/up")
	@POST
	@Produces(MediaType.TEXT_PLAIN)
	public String upZykData(){
		HttpServletRequest request = UtilHTTP.getHttpServletRequest();
		
		try {
			String param = UtilHTTP.parseInputStream(request.getInputStream());
			param = param.replaceAll("\t", " ");
			param = param.replaceAll("\r", " ");
			param = param.replaceAll("\n", " ");
			
			JSONObject json = JSONObject.fromObject(param);
			String source = json.get("source") + "";
			String fullName = json.get("fullname") + "";
			String instituteInCharge = json.get("instituteincharge") + "";
			String type = json.get("type") + "";
			String status = json.get("status") + "";
			String data = json.get("data") + "";
			
			String result = UtilJSON.toJsonString(zykService.upZykData(source, fullName, instituteInCharge, type, status, data));
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "资源库数据保存失败";
		}
	}
	
	/**
	 * 上报的资源库信息
	 * @return
	 */
	@Path("/update")
	@POST
	@Produces(MediaType.TEXT_PLAIN)
	public String updateZykData(){
		HttpServletRequest request = UtilHTTP.getHttpServletRequest();
		
		try {
			String param = UtilHTTP.parseInputStream(request.getInputStream());
			param = param.replaceAll("\t", " ");
			param = param.replaceAll("\r", " ");
			param = param.replaceAll("\n", " ");
			
			JSONObject json = JSONObject.fromObject(param);
			String fullName = json.get("fullname") + "";
			String instituteInCharge = json.get("instituteincharge") + "";
			String type = json.get("type") + "";
			String data = json.get("data") + "";
			
			String result = UtilJSON.toJsonString(zykService.updateZykData(fullName, instituteInCharge, type, data));
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "资源库数据保存失败";
		}
	}
	
	/**
	 * 上报的资源库信息
	 * @return
	 */
	@Path("/get")
	@POST
	@Produces(MediaType.TEXT_PLAIN)
	public String getZykData(){
		HttpServletRequest request = UtilHTTP.getHttpServletRequest();
		
		try {
			String param = UtilHTTP.parseInputStream(request.getInputStream());
			param = param.replaceAll("\t", " ");
			param = param.replaceAll("\r", " ");
			param = param.replaceAll("\n", " ");
			
			JSONObject json = JSONObject.fromObject(param);
			String fullName = json.get("fullname") + "";
			String instituteInCharge = json.get("instituteincharge") + "";
			
			String result = UtilJSON.toJsonString(zykService.getZykData(fullName, instituteInCharge));
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "资源库数据获取失败";
		}
	}
	
}
