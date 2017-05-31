package com.asdc.lrm.action;

import com.asdc.lrm.service.LogService;

@SuppressWarnings("unchecked")
public class LogAction extends BaseAction{
	
	private static final long serialVersionUID = 1L;

	private LogService logService;
	public void setLogService(LogService logService) {
		this.logService = logService;
	}
	
	/**
	 * 系统日志列表数据
	 */
	public void logGrid(){
		if(page == 0)
			page = 1;
		if(rows == 0)
			rows = 20;
		try {
			jsonData = logService.findLogGrid(keyword, beginDate, endDate, page-1, rows);
		}catch (RuntimeException e) {
			e.printStackTrace();
		}finally{
			outPrint();
		}
	}
}