package com.asdc.lrm.service;

import com.asdc.lrm.jersey.ServiceResponse;

public interface ZykUserService{

	/**
	 * 上传用户信息
	 * @param source
	 * @param data
	 * @param fullName
	 * @param instituteInCharge
	 * @return
	 */
	ServiceResponse saveZykUser(String source, String data, String fullName, String instituteInCharge);

}
