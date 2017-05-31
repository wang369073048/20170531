package com.asdc.lrm.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.asdc.lrm.dao.ZykCourseDao;
import com.asdc.lrm.dao.ZykDao;
import com.asdc.lrm.entity.ZykCourseEntity;
import com.asdc.lrm.entity.ZykEntity;
import com.asdc.lrm.jersey.ServiceResponse;
import com.asdc.lrm.service.ZykCourseService;
import com.asdc.lrm.util.UtilString;

public class ZykCourseServiceImpl extends BaseServiceImpl implements ZykCourseService {

	private static final Log log = LogFactory.getLog(ZykCourseServiceImpl.class);
	
	private ZykDao zykDao;
	public void setZykDao(ZykDao zykDao) {
		this.zykDao = zykDao;
	}
	
	private ZykCourseDao zykCourseDao;
	public void setZykCourseDao(ZykCourseDao zykCourseDao) {
		this.zykCourseDao = zykCourseDao;
	}
	
	public ServiceResponse saveZykCourseData(String source, String data, String fullName, String instituteInCharge) {
		log.info(" upload data of ZYK_COURSE ");
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
		
			List<ZykCourseEntity> courseList = new ArrayList<ZykCourseEntity>();
			JSONArray jsonArray = JSONArray.fromObject(data);
			for(int i = 0 ; i < jsonArray.size();i++){
				JSONObject json = jsonArray.getJSONObject(i);
				if(json.get("courseid") == null){
					return new ServiceResponse(400,"failed","courseid有空值,上传数据失败!");
				}
				
				ZykCourseEntity courseEntity = new ZykCourseEntity();
				courseEntity.setZykId(builder.toString());
				courseEntity.setCourseId(UtilString.getStr(json.get("courseid")));
				courseEntity.setFullname(UtilString.getStr(json.get("fullname")));
				courseEntity.setSpecialty(UtilString.getStr(json.get("specialty")));
				courseEntity.setAuthor(UtilString.getStr(json.get("author")));
				courseEntity.setCourseType(UtilString.getStr(json.get("coursetype")));
				courseEntity.setCourseLevel(UtilString.getStr(json.get("courselevel")));
				
				if(json.get("description") != null){
					String description = json.get("description").toString();
					if(description.length() > 1000){
						description = description.substring(0, 999);
					}
					courseEntity.setDescription(description);
				}
				
				if(json.get("modifiedtime") != null){
					try {
						courseEntity.setModifiedTime(new SimpleDateFormat("yyyy-MM-dd").parse(json.get("modifiedtime").toString()));
					} catch (ParseException e) {
						return new ServiceResponse(400,"failed","modifiedtime时间格式有误!");
					}
				}
				
				courseList.add(courseEntity);
			}
			ZykCourseEntity existCourse = null;
			for(ZykCourseEntity course : courseList){
				existCourse = zykCourseDao.findCourseByCourseIdAndZykId(course.getCourseId(), course.getZykId());
				if(existCourse != null){
					String errStr = " courseId:"+course.getCourseId()+" fullname:"+course.getFullname();
					return new ServiceResponse(400,"failed", "课程数据保存失败! " + errStr);
				}
			}
			
			for(ZykCourseEntity course : courseList){
				zykCourseDao.save(course);
			}
			log.info(" upload data of ZYK_COURSE success ! insert data : " + courseList.size());				
			return new ServiceResponse(200,"success","课程数据保存成功! 插入数据:"+courseList.size()+"条.");
		}catch (RuntimeException e) {
			log.info(" Exception when upload data of ZYK_USER : " + e);
			e.printStackTrace();
			return new ServiceResponse(400,"failed","课程数据保存失败!");
		}
	}
}
