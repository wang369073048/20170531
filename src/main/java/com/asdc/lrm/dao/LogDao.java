package com.asdc.lrm.dao;

import java.util.List;

import com.asdc.lrm.dao.common.Dao;
import com.asdc.lrm.entity.LogEntity;

public interface LogDao extends Dao<LogEntity, Long>{
	
	List<LogEntity> findLogs(String keyword, String beginDate, String endDate, int page, int rows);
	
	int findLogsTotal(String keyword, String beginDate, String endDate);
}
