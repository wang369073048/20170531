package com.asdc.lrm.service;

import java.sql.Timestamp;
import java.util.Map;

@SuppressWarnings("unchecked")
public interface ReportBasicService {
	
	/**
	 * 统计所有的报表
	 */
	Map showAllReports(String zykId);

	/**
	 * 更改资源库上传状态
	 * @param zykId
	 * @param status
	 * @param time
	 */
	void updateZykSendStatus(String zykId, int status, Timestamp time);
	
	void showAllReportsForCache();

}
