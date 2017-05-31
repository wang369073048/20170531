package com.asdc.lrm.service;

import com.asdc.lrm.jersey.ServiceResponse;

public interface ZykResourceService{

	/**
	 * 上传素材
	 * @param source
	 * @param data
	 * @param fullName
	 * @param instituteInCharge
	 * @return
	 */
	ServiceResponse saveZykResourceData(String source, String data, String fullName, String instituteInCharge);

}
