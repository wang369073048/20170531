package com.asdc.lrm.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import com.asdc.lrm.dao.LogDao;
import com.asdc.lrm.entity.LogEntity;
import com.asdc.lrm.service.LogService;

@SuppressWarnings("unchecked")
public class LogServiceImpl implements LogService {
	
	private LogDao logDao;
	public void setLogDao(LogDao logDao) {
		this.logDao = logDao;
	}

	public String findLogGrid(String keyword, String beginDate, String endDate, int page, int rows){
		try{
			List<LogEntity> logList = logDao.findLogs(keyword, beginDate, endDate, page, rows);
			int total = logDao.findLogsTotal(keyword, beginDate, endDate);
			
	        Map pageMap = new HashMap();
	        pageMap.put("total", total);
	        
	        List dataList = new ArrayList();
	        for(int i=0; i<logList.size(); i++){
	            Map dataMap = new HashMap();
	            dataMap.put("id", logList.get(i).getId());
	            dataMap.put("userName", logList.get(i).getUserName());
	            dataMap.put("ip", logList.get(i).getIp());
	            dataMap.put("operateTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(logList.get(i).getOperateTime()));
	            dataMap.put("operateModule", logList.get(i).getOperateModule());
	            dataMap.put("operateAction", logList.get(i).getOperateAction());
	            dataMap.put("operateObject", logList.get(i).getOperateObject());
	            dataList.add(dataMap);
	        }   
	        pageMap.put("rows", dataList);
	        JSONObject object = JSONObject.fromObject(pageMap);
	        return object.toString();
		}catch (RuntimeException e) {
			e.printStackTrace();
			return null;
		}
	}
}