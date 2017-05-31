package com.asdc.lrm.service;

import com.asdc.lrm.jersey.ServiceResponse;

public interface ZykQuestionbankService{

	/**
	 * 上传题库信息
	 * @param source
	 * @param data
	 * @param fullName
	 * @param instituteInCharge
	 * @return
	 */
	ServiceResponse saveZykQuestionbank(String source, String data, String fullName, String instituteInCharge);

}
