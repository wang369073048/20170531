package com.asdc.lrm.dao;

import java.util.List;
import java.util.Map;

public interface ReportSummaryDao{
	
	/**
	 * 汇总报告
	 * 采用4张表关联产生结果, 数据量有限，如果数据量太大，不建议使用sql执行
	 * @param pageIndex
	 * @param count
	 * @param status
	 * @return
	 */
	List<Map<String,Object>> reportSummary(int pageIndex, int count, int status);

}
