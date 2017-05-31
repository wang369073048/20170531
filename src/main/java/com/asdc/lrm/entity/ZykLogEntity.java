package com.asdc.lrm.entity;

import java.util.Date;

public class ZykLogEntity{

	private String logId;								//日志Id
	private String zykId;								//资源库Id
	private Date time;									//创建时间
	private String userId;								//用户ID
	private String ip;									//ip地址
	private String equipment;							//访问设备
	private String courseId;							//课程ID
	private String objectType;							//操作对象所属模块
	private String objectId;							//操作对象路径
	private String action;								//动作

	public String getLogId() {
		return logId;
	}
	public void setLogId(String logId) {
		this.logId = logId;
	}
	public String getZykId() {
		return zykId;
	}
	public void setZykId(String zykId) {
		this.zykId = zykId;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getEquipment() {
		return equipment;
	}
	public void setEquipment(String equipment) {
		this.equipment = equipment;
	}
	public String getCourseId() {
		return courseId;
	}
	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}
	public String getObjectType() {
		return objectType;
	}
	public void setObjectType(String objectType) {
		this.objectType = objectType;
	}
	public String getObjectId() {
		return objectId;
	}
	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}

}