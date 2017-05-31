package com.asdc.lrm.service;

import java.util.List;
import java.util.Map;

public interface ReportSummaryService {
		
	/**
	 * 资源库总体情况
	 * @param zykId
	 * @param beginDate
	 * @param endDate
	 * @return 
	 */
	public List<Object> totality();
	
	/**
	 * 资源库报建设库总体情况
	 * @param pageIndex
	 * @param count
	 * @param status
	 * @return
	 */
	List<Map<String,Object>> reportSummary(int pageIndex,int count, int status);
	
	/**
	 * 资源库申报情况分布
	 * @return
	 */
	List<String> distribution();
	
	/**
	 * 资源库总体情况
	 * 页面右边展示table
	 * @return
	 */
	List<String> totalityForTable();

}
