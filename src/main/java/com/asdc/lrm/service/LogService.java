package com.asdc.lrm.service;


public interface LogService {

	/**
	 * 根据查询条件，分页获取日志记录
	 * 返回json数据
	 * @param keyword
	 * @param beginDate
	 * @param endDateint
	 * @param page
	 * @param rows
	 * @return
	 */
	String findLogGrid(String keyword, String beginDate, String endDate, int page, int rows);
}
