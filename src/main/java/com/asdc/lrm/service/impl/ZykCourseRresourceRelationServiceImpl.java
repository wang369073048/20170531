package com.asdc.lrm.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.asdc.lrm.dao.ZykCourseRresourceRelationDao;
import com.asdc.lrm.dao.ZykDao;
import com.asdc.lrm.entity.ZykCourseRresourceRelationEntity;
import com.asdc.lrm.entity.ZykEntity;
import com.asdc.lrm.jersey.ServiceResponse;
import com.asdc.lrm.service.ZykCourseRresourceRelationService;
import com.asdc.lrm.util.UtilString;

public class ZykCourseRresourceRelationServiceImpl extends BaseServiceImpl implements ZykCourseRresourceRelationService {

	private static final Log log = LogFactory.getLog(ZykCourseRresourceRelationServiceImpl.class);
	
	private ZykDao zykDao;
	public void setZykDao(ZykDao zykDao) {
		this.zykDao = zykDao;
	}
	
	private ZykCourseRresourceRelationDao zykCourseRresourceRelationDao;
	public void setZykCourseRresourceRelationDao(ZykCourseRresourceRelationDao zykCourseRresourceRelationDao) {
		this.zykCourseRresourceRelationDao = zykCourseRresourceRelationDao;
	}
	
	public ServiceResponse saveCourseResourceRelation(String source,String data,String fullName, String instituteInCharge) {
		log.info(" upload data of ZYK_COURSE_RESOURCE_RELATION ");
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
			
			List<ZykCourseRresourceRelationEntity> list = new ArrayList<ZykCourseRresourceRelationEntity>();
			JSONArray jsonArray = JSONArray.fromObject(data);
			for(int i = 0 ; i < jsonArray.size();i++){
				JSONObject json = jsonArray.getJSONObject(i);
				
				ZykCourseRresourceRelationEntity relationEntity = new ZykCourseRresourceRelationEntity();
				relationEntity.setZykId(builder.toString());
				relationEntity.setRelationId(UtilString.getStr(json.get("relationid")));
				relationEntity.setCourseId(UtilString.getStr(json.get("courseid")));
				relationEntity.setCourseModule(UtilString.getStr(json.get("coursemodule")));
				relationEntity.setCourseModuleId(UtilString.getStr(json.get("coursemoduleid")));
				relationEntity.setResourceId(UtilString.getStr(json.get("resourceid")));
				list.add(relationEntity);
			}
			
			ZykCourseRresourceRelationEntity existRela = null;
			for(ZykCourseRresourceRelationEntity relation : list){
				existRela = zykCourseRresourceRelationDao.findRelaByRelationIdAndZykId(relation.getRelationId(), relation.getZykId());
				if(existRela != null){
					String errStr = " relationid:"+relation.getRelationId()+" courseid:"+relation.getCourseId()+" resourceid:"+relation.getResourceId();
					return new ServiceResponse(400,"failed", "课程模块应用素材关系数据保存失败! " + errStr);
				}
			}
			
			for(ZykCourseRresourceRelationEntity relation : list){
				zykCourseRresourceRelationDao.save(relation);
			}
			log.info(" upload data of ZYK_COURSE_RESOURCE_RELATION success ! insert data : "+list.size());
			return new ServiceResponse(200,"success","课程模块应用素材关系数据保存成功! 插入数据:"+list.size()+"条.");
		}catch (RuntimeException e) {
			log.info(" Exception when upload data of ZYK_COURSE_RESOURCE_RELATION : "+e);
			e.printStackTrace();
			return new ServiceResponse(400,"failed","课程模块应用素材关系数据保存失败!");
		}
	}
}
