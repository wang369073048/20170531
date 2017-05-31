package com.asdc.lrm.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.asdc.lrm.dao.ZykCourseDao;
import com.asdc.lrm.dao.ZykDao;
import com.asdc.lrm.dao.ZykDatachangeLogDao;
import com.asdc.lrm.dao.ZykResourceDao;
import com.asdc.lrm.dao.ZykUserDao;
import com.asdc.lrm.entity.ZykCourseEntity;
import com.asdc.lrm.entity.ZykDatachangeLogEntity;
import com.asdc.lrm.entity.ZykEntity;
import com.asdc.lrm.entity.ZykResourceEntity;
import com.asdc.lrm.entity.ZykUserEntity;
import com.asdc.lrm.jersey.ServiceResponse;
import com.asdc.lrm.service.ZykDatachangeLogService;
import com.asdc.lrm.util.UtilString;

public class ZykDatachangeLogServiceImpl extends BaseServiceImpl implements ZykDatachangeLogService {

	private static final Log log = LogFactory.getLog(ZykDatachangeLogServiceImpl.class);
	
	private ZykDatachangeLogDao zykDatachangeLogDao;
	private ZykDao zykDao;
	private ZykUserDao zykUserDao;
	private ZykCourseDao zykCourseDao;
	private ZykResourceDao zykResourceDao;
	
	public void setZykUserDao(ZykUserDao zykUserDao) {
		this.zykUserDao = zykUserDao;
	}

	public void setZykCourseDao(ZykCourseDao zykCourseDao) {
		this.zykCourseDao = zykCourseDao;
	}

	public void setZykResourceDao(ZykResourceDao zykResourceDao) {
		this.zykResourceDao = zykResourceDao;
	}

	public void setZykDao(ZykDao zykDao) {
		this.zykDao = zykDao;
	}

	public void setZykDatachangeLogDao(ZykDatachangeLogDao zykDatachangeLogDao) {
		this.zykDatachangeLogDao = zykDatachangeLogDao;
	}


	/**
	 * 上传zyk_datachange_log
	 * @param source
	 * @param data
	 * @param fullName
	 * @param instituteInCharge
	 * @return
	 */
	@Override
	public ServiceResponse saveZykDatachangeLogData(String data, String fullName, String instituteInCharge) {

		log.info(" upload data of ZYK_DATACHANGE_LOG");
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
			
			List<ZykDatachangeLogEntity> logList = new ArrayList<ZykDatachangeLogEntity>();
			JSONArray jsonArray = JSONArray.fromObject(data);
			for(int i = 0 ; i < jsonArray.size();i++){
				JSONObject json = jsonArray.getJSONObject(i);
				if(json.get("logid") == null){
					return new ServiceResponse(400,"failed","logid有空值,上传数据失败!");
				}
				
				ZykDatachangeLogEntity logEntity = new ZykDatachangeLogEntity();
				logEntity.setZykId(builder.toString());
				logEntity.setLogId(UtilString.getStr(json.get("logid")));
				logEntity.setUserId(UtilString.getStr(json.get("userid")));
				logEntity.setObjectType(UtilString.getStr(json.get("objecttype")));
				logEntity.setObjectId(UtilString.getStr(json.get("objectid")));
				logEntity.setAction(UtilString.getStr(json.get("action")));
				
				if(json.get("time") != null){
					try {
						logEntity.setTime(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(json.get("time").toString()));
					} catch (ParseException e) {
						return new ServiceResponse(400,"failed","time时间格式有误!");
					}
				}
				
				logList.add(logEntity);
			}
			
			ZykDatachangeLogEntity existLog = null;
			for(ZykDatachangeLogEntity log : logList){
				existLog = zykDatachangeLogDao.findDataLogByLogIdAndZykId(log.getLogId(), log.getZykId());
				if(existLog != null){
					String errStr = " logid:"+log.getLogId()+" zykid:"+log.getZykId();
					return new ServiceResponse(400,"failed", "ZYK_DATACHANGE_LOG数据保存失败!" + errStr);
				}
			}
			
			for(ZykDatachangeLogEntity log : logList){
				zykDatachangeLogDao.save(log);
			}
			log.info(" upload data of ZYK_DATACHANGE_LOG success! insert data : "+logList.size());
			return new ServiceResponse(200,"success","ZYK_DATACHANGE_LOG数据保存成功! 插入数据:"+logList.size()+"条.");
		}catch (RuntimeException e) {
			log.error(" Exception when upload data of ZYK_DATACHANGE_LOG : " + e);
			e.printStackTrace();
			return new ServiceResponse(400,"failed","ZYK_DATACHANGE_LOG数据保存失败!");
		}
	
	}
	
	/**
	 * 上传zyk_datachange_log
	 * 更新资源、课程、用户数据
	 * @param source
	 * @param data
	 * @param fullName
	 * @param instituteInCharge
	 * @return
	 */
	@Override
	public ServiceResponse updateZykDatachangeLogData(String data, String fullName, String instituteInCharge) {
		log.info(" upload data of ZYK_DATACHANGE_LOG for update ");
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
			
			List<ZykUserEntity> userList = new ArrayList<ZykUserEntity>();
			List<ZykCourseEntity> courseList = new ArrayList<ZykCourseEntity>();
			List<ZykResourceEntity> resourceList = new ArrayList<ZykResourceEntity>();
			List<ZykDatachangeLogEntity> logList = new ArrayList<ZykDatachangeLogEntity>();
			JSONArray jsonArray = JSONArray.fromObject(data);
			for(int i = 0 ; i < jsonArray.size();i++){
				JSONObject dataJson = jsonArray.getJSONObject(i);
				if(dataJson.get("logid") == null){
					return new ServiceResponse(400,"failed","logid有空值,上传数据失败!");
				}
				
				//zyk_datachange_log格式化
				ZykDatachangeLogEntity logEntity = new ZykDatachangeLogEntity();
				logEntity.setZykId(builder.toString());
				logEntity.setLogId(UtilString.getStr(dataJson.get("logid")));
				logEntity.setUserId(UtilString.getStr(dataJson.get("userid")));
				String objectType = UtilString.getStr(dataJson.get("objecttype"));
				logEntity.setObjectType(objectType);
				String objectId = UtilString.getStr(dataJson.get("objectid"));
				logEntity.setObjectId(objectId);
				logEntity.setAction(UtilString.getStr(dataJson.get("action")));
				if(dataJson.get("time") != null){
					try {
						logEntity.setTime(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(dataJson.get("time").toString()));
					} catch (ParseException e) {
						return new ServiceResponse(400,"failed","time时间格式有误!");
					}
				}
				
				logList.add(logEntity);
				
				JSONArray dataArray = JSONArray.fromObject(UtilString.getStr(dataJson.get("updatedata")));
				
				//zyk_user数据格式化
				if("用户".equals(objectType)){
					for(int j = 0; j < dataArray.size(); j++){
						JSONObject userJson = dataArray.getJSONObject(j);
						/*if(userJson.get("userid") == null){
							return new ServiceResponse(400,"failed","userId有空值!");
						}*/
						
						ZykUserEntity userEntity = new ZykUserEntity();
						userEntity.setZykId(builder.toString());
						userEntity.setUserId(objectId);
						userEntity.setUsername(UtilString.getStr(userJson.get("username")));
						userEntity.setRole(UtilString.getStr(userJson.get("role")));
						userEntity.setEmail(UtilString.getStr(userJson.get("email")));
						userEntity.setTelephone(UtilString.getStr(userJson.get("telephone")));
						userEntity.setGender(UtilString.getStr(userJson.get("gender")));
						userEntity.setInstitute(UtilString.getStr(userJson.get("institute")));
						userEntity.setProvince(UtilString.getStr(userJson.get("province")));
						userEntity.setCity(UtilString.getStr(userJson.get("city")));
						userEntity.setSpecialty(UtilString.getStr(userJson.get("specialty")));
						
						if(userJson.get("birthday") != null){
							try {
								userEntity.setBirthday(new SimpleDateFormat("yyyy-MM-dd").parse(userJson.get("birthday").toString()));
							} catch (ParseException e) {
								return new ServiceResponse(400,"failed","birthday时间格式有误!"+" UserID:"+userEntity.getUserId());
							}
						}
						
						if(userJson.get("createdtime") != null){
							try {
								Date date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(userJson.get("createdtime").toString());
								userEntity.setCreatedTime(date);
							} catch (ParseException e) {
								return new ServiceResponse(400,"failed","createdtime时间格式有误!"+" UserID:"+userEntity.getUserId());
							}
						}
						if(userJson.get("modifiedtime") != null){
							try {
								Date date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(userJson.get("modifiedtime").toString());
								userEntity.setModifiedTime(date);
							} catch (ParseException e) {
								return new ServiceResponse(400,"failed","modifiedtime时间格式有误!"+" UserID:"+userEntity.getUserId());
							}
						}
						userList.add(userEntity);
					}
				}
				
				//zyk_course数据格式化
				if("课程".equals(objectType)){
					for(int j = 0; j < dataArray.size(); j++){
						JSONObject courseJson = dataArray.getJSONObject(j);
						/*if(courseJson.get("courseid") == null){
							return new ServiceResponse(400,"failed","courseid有空值,上传数据失败!");
						}*/
						
						ZykCourseEntity courseEntity = new ZykCourseEntity();
						courseEntity.setZykId(builder.toString());
						courseEntity.setCourseId(objectId);
						courseEntity.setFullname(UtilString.getStr(courseJson.get("fullname")));
						courseEntity.setSpecialty(UtilString.getStr(courseJson.get("specialty")));
						courseEntity.setAuthor(UtilString.getStr(courseJson.get("author")));
						courseEntity.setCourseType(UtilString.getStr(courseJson.get("coursetype")));
						courseEntity.setCourseLevel(UtilString.getStr(courseJson.get("courselevel")));
						
						if(courseJson.get("description") != null){
							String description = courseJson.get("description").toString();
							if(description.length() > 1000){
								description = description.substring(0, 999);
							}
							courseEntity.setDescription(description);
						}
						
						if(courseJson.get("modifiedtime") != null){
							try {
								courseEntity.setModifiedTime(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(courseJson.get("modifiedtime").toString()));
							} catch (ParseException e) {
								return new ServiceResponse(400,"failed","modifiedtime时间格式有误!");
							}
						}
						
						courseList.add(courseEntity);
					}
				}
				
				//zyk_resource数据格式化
				if("资源".equals(objectType)){
					for(int j = 0; j < dataArray.size(); j++){
						JSONObject resourceJson = dataArray.getJSONObject(j);
						/*if(resourceJson.get("resourceid") == null){
							return new ServiceResponse(400,"failed","resourceid有空值,上传数据失败!");
						}*/
						
						ZykResourceEntity resourceEntity = new ZykResourceEntity();
						resourceEntity.setZykId(builder.toString());
						resourceEntity.setResourceId(objectId);
						resourceEntity.setTitle(UtilString.getStr(resourceJson.get("title")));
						resourceEntity.setSource(UtilString.getStr(resourceJson.get("source")));
						resourceEntity.setSpecialty(UtilString.getStr(resourceJson.get("specialty")));
						resourceEntity.setCourse(UtilString.getStr(resourceJson.get("course")));
						resourceEntity.setKnowledge(UtilString.getStr(resourceJson.get("knowledge")));
						resourceEntity.setKeywords(UtilString.getStr(resourceJson.get("keywords")));
						resourceEntity.setSubject(UtilString.getStr(resourceJson.get("subject")));
						resourceEntity.setInstruction(UtilString.getStr(resourceJson.get("instruction")));
						resourceEntity.setMediaType(UtilString.getStr(resourceJson.get("mediatype")));
						resourceEntity.setLanguage(UtilString.getStr(resourceJson.get("language")));
						resourceEntity.setFileFormat(UtilString.getStr(resourceJson.get("fileformat")));
						
						if(resourceJson.get("filesize") != null || resourceJson.get("filesize") != ""){
							String filesize = resourceJson.get("filesize").toString();
							resourceEntity.setFilesize(Integer.parseInt(filesize));
						}
						
						if(resourceJson.get("modifiedtime") != null){
							try {
								resourceEntity.setModifiedTime(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(resourceJson.get("modifiedtime").toString()));
							} catch (ParseException e) {
								return new ServiceResponse(400,"failed","modifiedtime时间格式有误!");
							}
						}
						
						if(resourceJson.get("createdtime") != null){
							try {
								resourceEntity.setCreatedTime(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(resourceJson.get("createdtime").toString()));
							} catch (ParseException e) {
								return new ServiceResponse(400,"failed","createdtime时间格式有误!");
							}
						}
						
						resourceList.add(resourceEntity);
					}
				}
			}
			
			//校验zyk_datachange_log表中是否存在该条数据
			ZykDatachangeLogEntity existLog = null;
			for(ZykDatachangeLogEntity log : logList){
				existLog = zykDatachangeLogDao.findDataLogByLogIdAndZykId(log.getLogId(), log.getZykId());
				if(existLog != null){
					String errStr = " logid:"+log.getLogId()+" zykid:"+log.getZykId();
					return new ServiceResponse(400,"failed", "ZYK_DATACHANGE_LOG 数据插入失败! 数据重复: " + errStr);
				}
			}
			
			//校验zyk_use表r中是否存在该条数据
			ZykUserEntity existUser = null;
			for(ZykUserEntity user : userList){
				existUser = zykUserDao.findUserByUserIdAndZykId(user.getUserId(), user.getZykId());
				if(existUser == null){
					String errStr = " objectid:"+user.getUserId()+" zykid:"+user.getZykId();
					return new ServiceResponse(400,"failed", "用户数据修改失败! 未找到该条用户数据 " + errStr);
				}
			}
			
			//校验zyk_course表中是否存在该条数据
			ZykCourseEntity existCourse = null;
			for(ZykCourseEntity course : courseList){
				existCourse = zykCourseDao.findCourseByCourseIdAndZykId(course.getCourseId(), course.getZykId());
				if(existCourse == null){
					String errStr = " objectid:"+course.getCourseId()+" zykid:"+course.getZykId();
					return new ServiceResponse(400,"failed", "课程数据修改失败! 未找到该条课程数据 " + errStr);
				}
			}
			
			//校验zyk_resource表中是否存在该条数据
			ZykResourceEntity existResource = null;
			for(ZykResourceEntity resource : resourceList){
				existResource = zykResourceDao.findResourceByResourceIdAndZykId(resource.getResourceId(), resource.getZykId());
				if(existResource == null){
					String errStr = " objectid:"+resource.getResourceId()+" zykid:"+resource.getZykId();
					return new ServiceResponse(400,"failed", "资源数据修改失败! 未找到该条资源数据 " + errStr);
				}
			}
			
			//修改zyk_user表中数据
			if(userList != null && userList.size() != 0){
				for(ZykUserEntity user : userList){
					zykUserDao.merge(user);
				}
				log.info(" update data of ZYK_USER success ! amount : "+ userList.size());
			}
			
			//修改zyk_course表中数据
			if(courseList != null && courseList.size() != 0){
				for(ZykCourseEntity course : courseList){
					zykCourseDao.merge(course);
				}
				log.info(" update data of ZYK_COURSE success ! amount : "+ courseList.size());
			}
			
			//修改zyk_resource表中数据
			if(resourceList != null && resourceList.size() != 0){
				for(ZykResourceEntity resource : resourceList){
					zykResourceDao.merge(resource);
				}
				log.info(" update data of ZYK_RESOURCE success ! amount : "+ resourceList.size());
			}
			
			//向zyk_datachange_log表中插入数据
			for(ZykDatachangeLogEntity log : logList){
				zykDatachangeLogDao.save(log);
			}
			log.info(" upload data of ZYK_DATACHANGE_LOG for update success! insert data : "+logList.size());
			return new ServiceResponse(200,"success","ZYK_DATACHANGE_LOG数据保存成功! 插入数据:"+logList.size()+"条.");
		}catch (RuntimeException e) {
			log.error(" Exception when upload data of ZYK_DATACHANGE_LOG for update : " + e);
			e.printStackTrace();
			return new ServiceResponse(400,"failed","ZYK_DATACHANGE_LOG数据保存失败!");
		}
	}

	/**
	 * 上传zyk_datachange_log
	 * 删除资源、课程、用户表数据
	 * @param source
	 * @param data
	 * @param fullName
	 * @param instituteInCharge
	 * @return
	 */
	@Override
	public ServiceResponse deleteZykDatachangeLogData(String data, String fullName, String instituteInCharge) {
		log.info(" upload data of ZYK_DATACHANGE_LOG for delete ");
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
			
			List<ZykDatachangeLogEntity> logList = new ArrayList<ZykDatachangeLogEntity>();
			JSONArray jsonArray = JSONArray.fromObject(data);
			for(int i = 0 ; i < jsonArray.size();i++){
				JSONObject json = jsonArray.getJSONObject(i);
				if(json.get("logid") == null){
					return new ServiceResponse(400,"failed","logid有空值,上传数据失败!");
				}
				
				ZykDatachangeLogEntity logEntity = new ZykDatachangeLogEntity();
				logEntity.setZykId(builder.toString());
				logEntity.setLogId(UtilString.getStr(json.get("logid")));
				logEntity.setUserId(UtilString.getStr(json.get("userid")));
				logEntity.setObjectType(UtilString.getStr(json.get("objecttype")));
				logEntity.setObjectId(UtilString.getStr(json.get("objectid")));
				logEntity.setAction(UtilString.getStr(json.get("action")));
				
				if(json.get("time") != null){
					try {
						logEntity.setTime(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(json.get("time").toString()));
					} catch (ParseException e) {
						return new ServiceResponse(400,"failed","time时间格式有误!");
					}
				}
				
				logList.add(logEntity);
			}
			
			List<ZykUserEntity> existUserList = new ArrayList<ZykUserEntity>();
			List<ZykCourseEntity> existCourseList = new ArrayList<ZykCourseEntity>();
			List<ZykResourceEntity> existResourceList = new ArrayList<ZykResourceEntity>();
			ZykDatachangeLogEntity existLog = null;
			for(ZykDatachangeLogEntity log : logList){
				//检查zyk_datachange_log表中是否有该条数据
				existLog = zykDatachangeLogDao.findDataLogByLogIdAndZykId(log.getLogId(), log.getZykId());
				if(existLog != null){
					String errStr = " logid:"+log.getLogId()+" zykid:"+log.getZykId();
					return new ServiceResponse(400,"failed", "ZYK_DATACHANGE_LOG数据保存失败! " + errStr);
				}
				
				//检查三个表中是否有要被删除的数据，如是有存入list
				ZykUserEntity existUser = null;
				ZykCourseEntity existCourse = null;
				ZykResourceEntity existResource = null;
				if("用户".equals(log.getObjectType())){
					existUser = zykUserDao.findUserByUserIdAndZykId(log.getObjectId(), log.getZykId());
					if(existUser == null){
						String errStr = " objectid:"+log.getObjectId()+" zykid:"+log.getZykId();
						return new ServiceResponse(400,"failed", "用户数据删除失败! 未找到该条用户数据 " + errStr);
					}
					existUserList.add(existUser);
				}else if("资源".equals(log.getObjectType())){
					existResource = zykResourceDao.findResourceByResourceIdAndZykId(log.getObjectId(), log.getZykId());
					if(existResource == null){
						String errStr = " objectid:"+log.getObjectId()+" zykid:"+log.getZykId();
						return new ServiceResponse(400,"failed", "资源数据删除失败! 未找到该条资源数据 " + errStr);
					}
					existResourceList.add(existResource);
				}else if("课程".equals(log.getObjectType())){
					existCourse = zykCourseDao.findCourseByCourseIdAndZykId(log.getObjectId(), log.getZykId());
					if(existCourse == null){
						String errStr = " objectid:"+log.getObjectId()+" zykid:"+log.getZykId();
						return new ServiceResponse(400,"failed", "课程数据删除失败! 未找到该条课程数据 " + errStr);
					}
					existCourseList.add(existCourse);
				}
			}

			//插入zyk_datachange_log表
			for(ZykDatachangeLogEntity log : logList){
				zykDatachangeLogDao.save(log);
			}
			
			//删除zyk_user表中数据
			if(existUserList != null){
				for(ZykUserEntity user : existUserList){
					zykUserDao.delete(user);
				}
				log.info(" delete data of ZYK_USER success ! amount : "+ existUserList.size());
			}
			
			//删除zyk_resource表中数据
			if(existResourceList != null){
				for(ZykResourceEntity resource : existResourceList){
					zykResourceDao.delete(resource);
				}
				log.info(" delete data of ZYK_RESROUCE success ! amount : "+ existResourceList.size());
			}
			
			//删除zyk_course表中数据
			if(existCourseList != null){
				for(ZykCourseEntity course : existCourseList){
					zykCourseDao.delete(course);
				}
				log.info(" delete data of ZYK_COURSE success ! amount : "+ existCourseList.size());
			}
			
			log.info(" upload data of ZYK_DATACHANGE_LOG for delete success! insert data : "+logList.size());
			return new ServiceResponse(200,"success","ZYK_DATACHANGE_LOG数据保存成功! 插入数据:"+logList.size()+"条.");
		}catch (RuntimeException e) {
			log.error(" Exception when upload data of ZYK_DATACHANGE_LOG for delete : " + e);
			e.printStackTrace();
			return new ServiceResponse(400,"failed","ZYK_DATACHANGE_LOG 数据保存失败!");
		}
		
	}

}
