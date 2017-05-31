package com.asdc.lrm.entity;


public class ZykCourseRresourceRelationEntity{

	private String relationId;								//关系id
	private String zykId;									//资源库id
	private String courseId;								//课程id
	private String courseModuleId;							//课程模块id
	private String courseModule;							//课程模块名称
	private String resourceId;								//资源id
	
	public String getRelationId() {
		return relationId;
	}
	public void setRelationId(String relationId) {
		this.relationId = relationId;
	}
	public String getZykId() {
		return zykId;
	}
	public void setZykId(String zykId) {
		this.zykId = zykId;
	}
	public String getCourseId() {
		return courseId;
	}
	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}
	public String getCourseModuleId() {
		return courseModuleId;
	}
	public void setCourseModuleId(String courseModuleId) {
		this.courseModuleId = courseModuleId;
	}
	public String getCourseModule() {
		return courseModule;
	}
	public void setCourseModule(String courseModule) {
		this.courseModule = courseModule;
	}
	public String getResourceId() {
		return resourceId;
	}
	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}
	
}