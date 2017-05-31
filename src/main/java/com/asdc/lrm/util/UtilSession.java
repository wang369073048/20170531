package com.asdc.lrm.util;

import java.util.Map;

import com.asdc.lrm.entity.UserEntity;
import com.opensymphony.xwork2.ActionContext;

@SuppressWarnings("unchecked")
public class UtilSession {

	public static long getUserId(){
		ActionContext context = ActionContext.getContext();
		Map session = context.getSession();
		UserEntity user = (UserEntity)session.get("userEntity");
		if (user != null) 
			return user.getId();
		else
			return 0;
	}
	
	public static String getUserName(){
		ActionContext context = ActionContext.getContext();
		Map session = context.getSession();
		UserEntity user = (UserEntity)session.get("userEntity");
		if (user != null) 
			return user.getUserName();
		else
			return null;
	}
	
	public static String getUserLoginName(){
		ActionContext context = ActionContext.getContext();
		Map session = context.getSession();
		UserEntity user = (UserEntity)session.get("userEntity");
		if (user != null) 
			return user.getLoginName();
		else
			return null;
	}
	
	public static UserEntity getUser(){
		ActionContext context = ActionContext.getContext();
		Map session = context.getSession();
		UserEntity user = (UserEntity)session.get("userEntity");
		return user;
	}
}
