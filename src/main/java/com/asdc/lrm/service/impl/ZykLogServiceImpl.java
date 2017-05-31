package com.asdc.lrm.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.asdc.lrm.dao.ZykDao;
import com.asdc.lrm.dao.ZykLogDao;
import com.asdc.lrm.entity.ZykEntity;
import com.asdc.lrm.entity.ZykLogEntity;
import com.asdc.lrm.jersey.ServiceResponse;
import com.asdc.lrm.service.ZykLogService;
import com.asdc.lrm.util.UtilDate;
import com.asdc.lrm.util.UtilString;

public class ZykLogServiceImpl extends BaseServiceImpl implements ZykLogService {

	private static final Log log = LogFactory.getLog(ZykLogServiceImpl.class);
	
	private ZykDao zykDao;
	public void setZykDao(ZykDao zykDao) {
		this.zykDao = zykDao;
	}
	
	private ZykLogDao zykLogDao;
	public void setZykLogDao(ZykLogDao zykLogDao) {
		this.zykLogDao = zykLogDao;
	}
	
	public ServiceResponse saveZykLogData(String source,String data,String fullName, String instituteInCharge) {
		log.info(" upload data of ZYK_LOG");
		try{
			/** 根据专业名称和主持单位查询资源库信息，可能是多个 **/
			List<ZykEntity> zykEntityList = zykDao.getZykEntityList(fullName,instituteInCharge);
			
			StringBuilder builder = new StringBuilder();
			
			/** 如果来源是资源库平台，资源库不存在则不再继续，直接返回错误信息 **/
			/** 如果来源是申报平台，资源库不存在则先创建资源库信息，再保存用户数据 **/
			if(zykEntityList.size() == 0){
				return new ServiceResponse(400,"failed","资源库不存在!");
				
			}else{
				ZykEntity entity = zykEntityList.get(0);
				builder.append(entity.getZykId());
			}
			
			List<ZykLogEntity> logList = new ArrayList<ZykLogEntity>();
			JSONArray jsonArray = JSONArray.fromObject(data);
			for(int i = 0 ; i < jsonArray.size();i++){
				JSONObject json = jsonArray.getJSONObject(i);
				if(json.get("logid") == null){
					return new ServiceResponse(400,"failed","logid有空值,上传数据失败!");
				}
				
				ZykLogEntity logEntity = new ZykLogEntity();
				logEntity.setZykId(builder.toString());
				logEntity.setLogId(UtilString.getStr(json.get("logid")));
				logEntity.setUserId(UtilString.getStr(json.get("userid")));
				logEntity.setIp(UtilString.getStr(json.get("ip")));
				logEntity.setEquipment(UtilString.getStr(json.get("equipment")));
				logEntity.setCourseId(UtilString.getStr(json.get("courseid")));
				logEntity.setObjectId(UtilString.getStr(json.get("objectid")));
				logEntity.setObjectType(UtilString.getStr(json.get("objecttype")));
				logEntity.setAction(UtilString.getStr(json.get("action")));
				
				if(json.get("time") != null){
					try {
						logEntity.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(json.get("time").toString()));
					} catch (ParseException e) {
						return new ServiceResponse(400,"failed","time时间格式有误!");
					}
				}
				
				logList.add(logEntity);
			}
			
			ZykLogEntity existLog = null;
			for(ZykLogEntity log : logList){
				existLog = zykLogDao.findLogByLogIdAndZykId(log.getLogId(), log.getZykId());
				if(existLog != null){
					String errStr = " logid:"+log.getLogId()+" userid:"+log.getUserId()+" courseid:"+log.getCourseId()+" objectid:"+log.getObjectId();
					return new ServiceResponse(400,"failed", "用户行为日志数据保存失败!" + errStr);
				}
			}
			
			for(ZykLogEntity log : logList){
				zykLogDao.save(log);
			}
			log.info(" upload data of ZYK_LOG success! insert data : "+logList.size());
			return new ServiceResponse(200,"success","用户行为日志数据保存成功! 插入数据:"+logList.size()+"条.");
		}catch (RuntimeException e) {
			log.error(" Exception when upload data of ZYK_LOG : " + e);
			e.printStackTrace();
			return new ServiceResponse(400,"failed","用户行为日志数据保存失败!");
		}
	}
	
	public Object findFirstLogData(String zykId) {
		return zykLogDao.getFirstLogData(zykId);
	}
	
	public String getFirstAndLastDate(String zykId) {
		List<Object[]> firstDate = zykLogDao.getFirstDate(zykId);
		List<Object[]> lastDate = zykLogDao.getLastDate(zykId);
		Date date = new Date();
		SimpleDateFormat dateFormate = new SimpleDateFormat("yyyy-MM-dd");
		String cutTime = dateFormate.format(date.getTime());
		
		if(firstDate.size() == 0 || firstDate == null){
			return ";";
		}
		String startTime = firstDate.get(0)+"";
		String endTime = lastDate.get(0)+"";
		if(!UtilString.isNullAndEmpty(startTime)){
			startTime = startTime.substring(0,10);
			endTime = endTime.substring(0,10);
			if(!UtilDate.Date1BeforeDate2(endTime, cutTime)){
				endTime = cutTime;
			}
			if(!UtilDate.Date1BeforeDate2(startTime, cutTime)){
				startTime = cutTime;
			}
		}else{
			startTime = "";
			endTime = "";
		}
		return startTime +";"+endTime;
	}
}
