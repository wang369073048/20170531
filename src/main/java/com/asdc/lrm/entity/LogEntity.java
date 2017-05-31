package com.asdc.lrm.entity;

import java.util.Date;

public class LogEntity {
	
	public LogEntity() {
		super();
	}
	
	public LogEntity(String userName, String ip, Date operateTime, String operateModule, String operateAction, String operateObject) {
		super();
		this.userName = userName;
		this.ip = ip;
		this.operateTime = operateTime;
		this.operateModule = operateModule;
		this.operateAction = operateAction;
		this.operateObject = operateObject;
	}
	
	private long id;					// ID
	private String userName;			// 操作人
	private String ip;					// ip地址
	private Date operateTime;			// 操作时间
	private String operateModule;		// 操作模块
	private String operateAction;		// 做操动作
	private String operateObject;		// 操作对象
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public Date getOperateTime() {
		return operateTime;
	}
	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}
	public String getOperateModule() {
		return operateModule;
	}
	public void setOperateModule(String operateModule) {
		this.operateModule = operateModule;
	}
	public String getOperateAction() {
		return operateAction;
	}
	public void setOperateAction(String operateAction) {
		this.operateAction = operateAction;
	}
	public String getOperateObject() {
		return operateObject;
	}
	public void setOperateObject(String operateObject) {
		this.operateObject = operateObject;
	}
}