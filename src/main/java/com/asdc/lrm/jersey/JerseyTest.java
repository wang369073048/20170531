package com.asdc.lrm.jersey;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.stereotype.Controller;

import com.sun.jersey.spi.resource.Singleton;

@Path("/JerseyTest")  
@Singleton  
@Controller 
public class JerseyTest {
	
	/**
	 * 测试
	 * @param parameters1	参数1
	 * @param parameters2	参数2
	 * @return
	 */
	@Path("/testing/{parameters1}/{parameters2}")
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String testing(@PathParam("parameters1") String parameters1, @PathParam("parameters2") String parameters2){
		StringBuilder builder = new StringBuilder("{");
		try{
			builder.append("\"resultStatus\":0,\"data\":{\"parameters1\":\""+parameters1+"\",\"parameters2\":\""+parameters2+"\"}");
		}catch (RuntimeException e) {
			e.printStackTrace();
			builder.append("\"resultStatus\":1,\"message\":\"系统出错!\"");
		}
		builder.append("}");
		return builder.toString();
	}
	
	
}