package com.asdc.lrm.service;

import com.asdc.lrm.jersey.ServiceResponse;

public interface ZykCourseRresourceRelationService{

	/**
	 * 上传课程模块应用素材关系数据
	 * @param source
	 * @param data
	 * @param fullName
	 * @param instituteInCharge
	 * @return
	 */
	ServiceResponse saveCourseResourceRelation(String source, String data, String fullName, String instituteInCharge);
}
