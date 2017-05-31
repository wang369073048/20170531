package com.asdc.lrm.service;

import com.asdc.lrm.jersey.ServiceResponse;

public interface ZykCourseService{

	/**
	 * 上传课程信息
	 * @param source
	 * @param data
	 * @param fullName
	 * @param instituteInCharge
	 * @return
	 */
	ServiceResponse saveZykCourseData(String source, String data, String fullName, String instituteInCharge);

}
