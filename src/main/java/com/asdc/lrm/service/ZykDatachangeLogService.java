package com.asdc.lrm.service;

import com.asdc.lrm.jersey.ServiceResponse;

public interface ZykDatachangeLogService{

	/**
	 * 上传zyk_datachange_log
	 * @param source
	 * @param data
	 * @param fullName
	 * @param instituteInCharge
	 * @return
	 */
	ServiceResponse saveZykDatachangeLogData(String data, String fullName, String instituteInCharge);
	
	/**
	 * 上传zyk_datachange_log
	 * @param source
	 * @param data
	 * @param fullName
	 * @param instituteInCharge
	 * @return
	 */
	ServiceResponse updateZykDatachangeLogData(String data, String fullName, String instituteInCharge);

	/**
	 * 上传zyk_datachange_log
	 * @param source
	 * @param data
	 * @param fullName
	 * @param instituteInCharge
	 * @return
	 */
	ServiceResponse deleteZykDatachangeLogData(String data, String fullName, String instituteInCharge);
}
