package com.asdc.lrm.service;

import com.asdc.lrm.jersey.ServiceResponse;

public interface ZykLogService{

	/**
	 * 上传日志类
	 * @param source
	 * @param data
	 * @param fullName
	 * @param instituteInCharge
	 * @return
	 */
	ServiceResponse saveZykLogData(String source, String data, String fullName, String instituteInCharge);

	/**
	 * 根据zykId获得表中数据的起始时间
	 * @param zykId
	 * @return
	 */
	Object findFirstLogData(String zykId);
	
	String getFirstAndLastDate(String zykId);
}
