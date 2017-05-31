package com.asdc.lrm.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.asdc.lrm.dao.ZykCityDao;
import com.asdc.lrm.dao.ZykDao;
import com.asdc.lrm.entity.ZykCityEntity;
import com.asdc.lrm.entity.ZykEntity;
import com.asdc.lrm.jersey.ServiceResponse;
import com.asdc.lrm.service.ZykCourseRresourceRelationService;
import com.asdc.lrm.service.ZykCourseService;
import com.asdc.lrm.service.ZykDatachangeLogService;
import com.asdc.lrm.service.ZykLogService;
import com.asdc.lrm.service.ZykQuestionbankService;
import com.asdc.lrm.service.ZykResourceService;
import com.asdc.lrm.service.ZykService;
import com.asdc.lrm.service.ZykUserService;
import com.asdc.lrm.util.UtilString;

@SuppressWarnings("unchecked")
public class ZykServiceImpl extends BaseServiceImpl implements ZykService {
	
	private ZykDao zykDao;
	public void setZykDao(ZykDao zykDao) {
		this.zykDao = zykDao;
	}
	
	private ZykCityDao zykCityDao;
	public void setZykCityDao(ZykCityDao zykCityDao) {
		this.zykCityDao = zykCityDao;
	}
	
	private ZykUserService zykUserService;
	public void setZykUserService(ZykUserService zykUserService) {
		this.zykUserService = zykUserService;
	}

	private ZykCourseService zykCourseService;
	public void setZykCourseService(ZykCourseService zykCourseService) {
		this.zykCourseService = zykCourseService;
	}

	private ZykQuestionbankService zykQuestionbankService;
	public void setZykQuestionbankService(ZykQuestionbankService zykQuestionbankService) {
		this.zykQuestionbankService = zykQuestionbankService;
	}

	private ZykResourceService zykResourceService;
	public void setZykResourceService(ZykResourceService zykResourceService) {
		this.zykResourceService = zykResourceService;
	}

	private ZykLogService zykLogService;
	public void setZykLogService(ZykLogService zykLogService) {
		this.zykLogService = zykLogService;
	}

	private ZykCourseRresourceRelationService zykCourseRresourceRelationService;
	public void setZykCourseRresourceRelationService(ZykCourseRresourceRelationService zykCourseRresourceRelationService) {
		this.zykCourseRresourceRelationService = zykCourseRresourceRelationService;
	}
	
	private ZykDatachangeLogService zykDatachangeLogService;
	public void setZykDatachangeLogService(ZykDatachangeLogService zykDatachangeLogService) {
		this.zykDatachangeLogService = zykDatachangeLogService;
	}

	@Override
	public ServiceResponse upZykData(String source, String fullName, String instituteInCharge,String type, String status, String data){
		
		if(source == null || source.isEmpty()) 
			return new ServiceResponse(400,"failed","来源不能为空");
		if(fullName == null || fullName.isEmpty())
			return new ServiceResponse(400,"failed","专业名称不能为空");
		if(instituteInCharge == null || instituteInCharge.isEmpty())
			return new ServiceResponse(400,"failed","主持单位不能为空");
		if(data == null || data.isEmpty())
			return new ServiceResponse(400,"failed","数据为空");
		if(status == null || status.isEmpty())
			return new ServiceResponse(400,"failed","状态为空");

		//判断类型
		if("1".equals(type)){    //资源类型
			return saveZykData(status, data, fullName, instituteInCharge);
		}else if("2".equals(type)){  //用户基本信息
			return zykUserService.saveZykUser(source, data, fullName, instituteInCharge);
		}else if("3".equals(type)){   //课程信息
			return zykCourseService.saveZykCourseData(source, data, fullName, instituteInCharge);
		}else if("4".equals(type)){  //题库信息
			return zykQuestionbankService.saveZykQuestionbank(source, data, fullName, instituteInCharge);
		}else if("5".equals(type)){   //资源素材信息
			return zykResourceService.saveZykResourceData(source, data, fullName, instituteInCharge);
		}else if("6".equals(type)){	  //课程模块应用素材关系
			return zykCourseRresourceRelationService.saveCourseResourceRelation(source, data, fullName, instituteInCharge);
		}else if("7".equals(type)){   //用户行为日志log
			return zykLogService.saveZykLogData(source, data, fullName, instituteInCharge);
		}
		
		return new ServiceResponse(200,"success","资源库上传成功!");
	}

	/** 
	 * 保存资源库信息
	 * @param data
	 * @param zykEntity
	 */
	private ServiceResponse saveZykData(String status, String data, String fullName, String instituteInCharge) {
		ServiceResponse response = null;
		try{
			JSONObject jsonObj = JSONObject.fromObject(data);
			String[] insArray = instituteInCharge.split(",");
			String uuid = UUID.randomUUID().toString().replace("-", "");
			
			for (int i = 0; i < insArray.length; i++) {
				ZykEntity zyk = new ZykEntity();
				zyk.setFullname(fullName);
				zyk.setInstituteInCharge(insArray[i]);
				ZykEntity entity = zykDao.getZykEntityByFullNameAndIns(zyk);
				
				JSONArray jsonArray = JSONArray.fromObject(jsonObj.get(insArray[i]));
				JSONObject json = jsonArray.getJSONObject(0);
				
				if(entity == null){
					response = saveZykEntity(status, fullName, insArray[i], json, uuid);
				}else{
					response = updateZykEntity(status, entity, json);
				}
			}
			return response;
		}catch (RuntimeException e) {
			e.printStackTrace();
			return new ServiceResponse(400,"failed","资源库数据保存失败!");
		}
	}
	
	public ServiceResponse saveZykEntity(String status, String fullName, String instituteInCharge, JSONObject json, String uuid){
		try{
			int statusInt = Integer.parseInt(status);
			if(statusInt > 3 || statusInt < 1){
				return new ServiceResponse(400,"failed","资源库状态错误!");
			}
			
			ZykEntity entity = new ZykEntity();
			entity.setZykId(uuid);
			entity.setStatus(status);
			entity.setFullname(fullName);
			entity.setInstituteInCharge(instituteInCharge);
			entity.setSpecialtyCategory(UtilString.getStr(json.get("specialtycategory")));
			entity.setSpecialtyName(UtilString.getStr(json.get("specialtyname")));
			entity.setCooperationInstitute(UtilString.getStr(json.get("cooperationinstitute")));
			entity.setMoreSpecialty(UtilString.getStr(json.get("morespecialty")));
			entity.setPersonInCharge(UtilString.getStr(json.get("personincharge")));
			entity.setWebsite(UtilString.getStr(json.get("website")));
			entity.setZykNum(UtilString.getStr(json.get("zyknum")));
			
			if(json.get("modifieddate") != null){
				entity.setModifiedDate(new SimpleDateFormat("yyyy-MM-dd").parse(json.get("modifieddate").toString()));
			}
			
			String province = UtilString.getStr(json.get("province"));
			String city = UtilString.getStr(json.get("city"));
			if(province != null && city != null){
				ZykCityEntity cityEntity = zykCityDao.getCityByName(city);
				if(cityEntity == null){
					ZykCityEntity provinceEntity = zykCityDao.getCityByName(province);
					if(provinceEntity == null){
						ZykCityEntity newProvince = new ZykCityEntity();
						newProvince.setName(province);
						newProvince.setParentId("0");
						newProvince.setType(1);
						zykCityDao.save(newProvince);
						
						ZykCityEntity newCity = new ZykCityEntity();
						newCity.setName(city);
						newCity.setParentId(newProvince.getCityId());
						newCity.setType(2);
						zykCityDao.save(newCity);
						
						entity.setCityId(newCity.getCityId());
					}else{
						ZykCityEntity newCity = new ZykCityEntity();
						newCity.setName(city);
						newCity.setParentId(provinceEntity.getCityId());
						newCity.setType(2);
						zykCityDao.save(newCity);
						
						entity.setCityId(newCity.getCityId());
					}
				}else{
					entity.setCityId(cityEntity.getCityId());
				}
			}else{
				ZykCityEntity cityEntity = zykCityDao.getCityByName("其他");
				if(cityEntity == null){
					zykCityDao.saveOther();
					entity.setCityId(zykCityDao.getCityByName("其他").getCityId());
				}else{
					entity.setCityId(cityEntity.getCityId());
				}
			}
			
			if("1".equals(status)){
				entity.setDeclarationDate(new Date());
			}else if("2".equals(status)){
				entity.setProjectDate(new Date());
			}else if("3".equals(status)){
				entity.setAccepDate(new Date());
			}
			
			zykDao.save(entity);
			
			return new ServiceResponse(200,"success","资源库数据保存成功!");
		}catch (RuntimeException e) {
			return new ServiceResponse(400,"failed", "资源库数据保存失败!"+" 名称："+fullName);
		}catch (ParseException e) {
			return new ServiceResponse(400,"failed","modifieddate时间格式有误!");
		}
	}
	
	public ServiceResponse updateZykEntity(String status, ZykEntity entity, JSONObject json){
		try{
			if(!entity.getStatus().equals(status)){
				int statusInt = Integer.parseInt(status);
				int statusIntOld = Integer.parseInt(entity.getStatus());
				
				if(statusIntOld == 1){
					if(statusInt == 3){
						return new ServiceResponse(400,"failed","资源库状态不能由申报中修改为已验收，只能修改为已立项!");
					}
				}else if(statusIntOld == 2){
					if(statusInt == 1){
						return new ServiceResponse(400,"failed","资源库状态不能由已立项修改为申报中，只能修改为已验收!");
					}
				}else if(statusIntOld == 3){
					return new ServiceResponse(400,"failed","资源库状态不能由已已验收改为申报中或已立项!");
				}
				
				entity.setStatus(status);
				
				if("1".equals(status)){
					entity.setDeclarationDate(new Date());
				}else if("2".equals(status)){
					entity.setProjectDate(new Date());
				}else if("3".equals(status)){
					entity.setAccepDate(new Date());
				}
				
				zykDao.update(entity);
				
				return new ServiceResponse(200,"success","资源库数据保存成功!");
			}else{
				return new ServiceResponse(400,"failed","资源库数据已经存在!");
			}
		}catch (RuntimeException e) {
			return new ServiceResponse(400,"failed","资源库数据保存失败!");
		}
	}
	
	public List<ZykEntity> findAllOrderByFullname(){
		try {
	        return zykDao.findAllOrderByFullname();
		} catch (RuntimeException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<ZykEntity> findByStatusOrderByFullname(String status, ZykEntity entity){
		try {
	        return zykDao.findByStatusOrderByFullname(status, entity);
		} catch (RuntimeException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<ZykEntity> findZykByZykId(String zykId){
		try {
	        return zykDao.getZykEntityByZykId(zykId);
		} catch (RuntimeException e) {
			e.printStackTrace();
			return null;
		}
	}

	public String findZykFroComboFullname(String status){
		try {
			List<ZykEntity> zykList = zykDao.findByStatusOrderByFullname(status, null);
			int total = zykList.size();
			
	        Map pageMap = new HashMap();
	        pageMap.put("total", total);
	        
	        List dataList = new ArrayList();
	        for (ZykEntity entity : zykList) {
	            Map dataMap = new HashMap();
	            dataMap.put("id", entity.getId());
	            dataMap.put("zykId", entity.getZykId());
	            dataMap.put("fullname", entity.getFullname());
	            dataMap.put("instituteInCharge", entity.getInstituteInCharge());
	            dataList.add(dataMap);
	        }
	        pageMap.put("rows", dataList);
	        JSONObject object = JSONObject.fromObject(pageMap);
	        return object.toString();
		} catch (RuntimeException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public String findZykFroComboInstituteInCharge(String status){
		try {
			List<ZykEntity> zykList = zykDao.findByStatusOrderByInstituteInCharge(status);
			int total = zykList.size();
			
	        Map pageMap = new HashMap();
	        pageMap.put("total", total);
	        
	        List dataList = new ArrayList();
	        for (ZykEntity entity : zykList) {
	            Map dataMap = new HashMap();
	            dataMap.put("id", entity.getId());
	            dataMap.put("zykId", entity.getZykId());
	            dataMap.put("instituteInCharge", entity.getInstituteInCharge());
	            dataMap.put("fullname", entity.getFullname());
	            dataList.add(dataMap);
	        }
	        pageMap.put("rows", dataList);
	        JSONObject object = JSONObject.fromObject(pageMap);
	        return object.toString();
		} catch (RuntimeException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public String findZykFroComboCity(String status){
		try {
			List<ZykEntity> zykList = zykDao.findByStatusOrderByCity(status);
			int total = zykList.size();
			
	        Map pageMap = new HashMap();
	        pageMap.put("total", total);
	        
	        List dataList = new ArrayList();
	        for (ZykEntity entity : zykList) {
	            Map dataMap = new HashMap();
	            dataMap.put("id", entity.getId());
	            dataMap.put("zykId", entity.getZykId());
	            dataMap.put("city", entity.getCity());
	            dataMap.put("instituteInCharge", entity.getInstituteInCharge());
	            dataMap.put("fullname", entity.getFullname());
	            dataList.add(dataMap);
	        }
	        pageMap.put("rows", dataList);
	        JSONObject object = JSONObject.fromObject(pageMap);
	        return object.toString();
		} catch (RuntimeException e) {
			e.printStackTrace();
			return null;
		}
	}

	/***
	 * 获取资源库信息
	 * @param fullName
	 * @param instituteInCharge
	 * @return
	 */
	@Override
	public ServiceResponse getZykData(String fullName, String instituteInCharge) {
		ZykEntity zykEntity = new ZykEntity();
		zykEntity.setFullname(fullName);
		zykEntity.setInstituteInCharge(instituteInCharge);
		zykEntity = zykDao.getZykEntityByFullNameAndIns(zykEntity);
		if(zykEntity == null){
			return new ServiceResponse(400,"failed","未找到对应的资源库");
		}
		List<Map<String, Object>> list = zykDao.getZykSummary(zykEntity.getZykId());
		return new ServiceResponse(200,"success",list.toString());
	}

	/***
	 * 上传zyk_datachange_log信息,并更改对应库表
	 * @param fullName
	 * @param instituteInCharge
	 * @param type
	 * @param data
	 * @return
	 */
	@Override
	public ServiceResponse updateZykData(String fullName, String instituteInCharge, String type, String data) {
		if("1".equals(type)){//update
			return zykDatachangeLogService.updateZykDatachangeLogData(data, fullName, instituteInCharge);
		}else if("2".equals(type)){//delete
			return zykDatachangeLogService.deleteZykDatachangeLogData(data, fullName, instituteInCharge);
		}
		return new ServiceResponse(400,"failed","修改数据的接口类型type错误！");
	}
	
	/**
	 * 获取全部资源库
	 * 按照zykNum排序
	 * @return
	 */
	@Override
	public List<ZykEntity> findAllOrderByZyknum(String status, ZykEntity entity) {
		return zykDao.findByStatusOrderByZyknum(status, entity);
	}
	
}
