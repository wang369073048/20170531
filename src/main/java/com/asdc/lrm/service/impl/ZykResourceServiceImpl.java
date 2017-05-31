package com.asdc.lrm.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.asdc.lrm.dao.ZykDao;
import com.asdc.lrm.dao.ZykResourceDao;
import com.asdc.lrm.entity.ZykEntity;
import com.asdc.lrm.entity.ZykResourceEntity;
import com.asdc.lrm.jersey.ServiceResponse;
import com.asdc.lrm.service.ZykResourceService;
import com.asdc.lrm.util.UtilString;

public class ZykResourceServiceImpl extends BaseServiceImpl implements ZykResourceService {

	private static final Log log = LogFactory.getLog(ZykResourceServiceImpl.class);
	
	private ZykDao zykDao;
	private ZykResourceDao zykResourceDao;
	
	public void setZykDao(ZykDao zykDao) {
		this.zykDao = zykDao;
	}

	public void setZykResourceDao(ZykResourceDao zykResourceDao) {
		this.zykResourceDao = zykResourceDao;
	}
	
	public ServiceResponse saveZykResourceData(String source,String data,String fullName, String instituteInCharge) {
		log.info(" upload data of ZYK_RESOURCE ");
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
			
			List<ZykResourceEntity> resourceList = new ArrayList<ZykResourceEntity>();
			JSONArray jsonArray = JSONArray.fromObject(data);
			for(int i = 0 ; i < jsonArray.size();i++){
				JSONObject json = jsonArray.getJSONObject(i);
				if(json.get("resourceid") == null){
					return new ServiceResponse(400,"failed","resourceid有空值,上传数据失败!");
				}
				
				ZykResourceEntity resourceEntity = new ZykResourceEntity();
				resourceEntity.setZykId(builder.toString());
				resourceEntity.setResourceId(UtilString.getStr(json.get("resourceid")));
				resourceEntity.setTitle(UtilString.getStr(json.get("title")));
				resourceEntity.setSource(UtilString.getStr(json.get("source")));
				resourceEntity.setSpecialty(UtilString.getStr(json.get("specialty")));
				resourceEntity.setCourse(UtilString.getStr(json.get("course")));
				resourceEntity.setKnowledge(UtilString.getStr(json.get("knowledge")));
				resourceEntity.setKeywords(UtilString.getStr(json.get("keywords")));
				resourceEntity.setSubject(UtilString.getStr(json.get("subject")));
				resourceEntity.setInstruction(UtilString.getStr(json.get("instruction")));
				resourceEntity.setMediaType(UtilString.getStr(json.get("mediatype")));
				resourceEntity.setLanguage(UtilString.getStr(json.get("language")));
				resourceEntity.setFileFormat(UtilString.getStr(json.get("fileformat")));
				
				if(json.get("filesize") != null || json.get("filesize") != ""){
					String filesize = json.get("filesize").toString();
					resourceEntity.setFilesize(Integer.parseInt(filesize));
				}
				
				if(json.get("modifiedtime") != null){
					try {
						resourceEntity.setModifiedTime(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(json.get("modifiedtime").toString()));
					} catch (ParseException e) {
						return new ServiceResponse(400,"failed","modifiedtime时间格式有误!");
					}
				}
				
				if(json.get("createdtime") != null){
					try {
						resourceEntity.setCreatedTime(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(json.get("createdtime").toString()));
					} catch (ParseException e) {
						return new ServiceResponse(400,"failed","createdtime时间格式有误!");
					}
				}
				
				resourceList.add(resourceEntity);
			}
			ZykResourceEntity existResource = null;
			for(ZykResourceEntity resource : resourceList){
				existResource = zykResourceDao.findResourceByResourceIdAndZykId(resource.getResourceId(), resource.getZykId());
				if(existResource != null){
					String errStr = " reourceid:"+resource.getResourceId()+" title:"+resource.getTitle();
					return new ServiceResponse(400,"failed", "资源素材数据保存失败! "+errStr);
				}
			}
			
			for(ZykResourceEntity resource : resourceList){
				zykResourceDao.save(resource);
			}
			log.info(" upload data of ZYK_RESOURCE success ! insert data :　" + resourceList.size());
			return new ServiceResponse(200,"success","资源素材数据保存成功! 插入数据:"+resourceList.size()+"条.");
		}catch (RuntimeException e) {
			log.info(" Exception when upload data of ZYK_RESOURCE : " + e);
			e.printStackTrace();
			return new ServiceResponse(400,"failed","资源素材数据保存失败!");
		}
	}

}
