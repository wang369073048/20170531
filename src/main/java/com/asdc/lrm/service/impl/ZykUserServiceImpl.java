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
import com.asdc.lrm.dao.ZykUserDao;
import com.asdc.lrm.entity.ZykEntity;
import com.asdc.lrm.entity.ZykUserEntity;
import com.asdc.lrm.jersey.ServiceResponse;
import com.asdc.lrm.service.ZykUserService;
import com.asdc.lrm.util.UtilString;

public class ZykUserServiceImpl extends BaseServiceImpl implements ZykUserService {

	private static final Log log = LogFactory.getLog(ZykUserServiceImpl.class);
	
	private ZykDao zykDao;
	public void setZykDao(ZykDao zykDao) {
		this.zykDao = zykDao;
	}

	private ZykUserDao zykUserDao;
	public void setZykUserDao(ZykUserDao zykUserDao) {
		this.zykUserDao = zykUserDao;
	}
	
	public ServiceResponse saveZykUser(String source, String data, String fullName, String instituteInCharge) {
		log.info(" upload data of ZYK_USER ");
		try{
			/** 根据专业名称和主持单位查询资源库信息，可能是多个 **/
			List<ZykEntity> zykEntityList = zykDao.getZykEntityList(fullName,instituteInCharge);
			
			StringBuilder builder = new StringBuilder();
			
			/** 如果来源是资源库平台，资源库不存在则不再继续，直接返回错误信息 **/
			/** 如果来源是申报平台，资源库不存在则先创建资源库信息，再保存用户数据 **/
			if(zykEntityList.size() == 0){
				return new ServiceResponse(400,"failed","资源库不存在!");
				
//				if(source.equals("2")){
//					return new ServiceResponse(400,"failed","资源库不存在");
//				}else if(source.equals("1")){
//					builder.append(UUID.randomUUID().toString().replace("-", ""));
//					
//					ZykEntity entity = new ZykEntity();
//					entity.setZykId(builder.toString());
//					entity.setFullname(fullName);
//					entity.setInstituteInCharge(instituteInCharge);
//					
//					ZykCityEntity cityEntity = zykCityDao.getCityByName("其他");
//					if(cityEntity == null){
//						zykCityDao.saveOther();
//						entity.setCityId(zykCityDao.getCityByName("其他").getCityId());
//					}else{
//						entity.setCityId(cityEntity.getCityId());
//					}
//					
//					zykDao.save(entity);
//				}
			}else{
				ZykEntity entity = zykEntityList.get(0);
				builder.append(entity.getZykId());
			}
			
			List<ZykUserEntity> userList = new ArrayList<ZykUserEntity>();
			JSONArray jsonArray = JSONArray.fromObject(data);
			for(int i = 0 ; i < jsonArray.size();i++){
				JSONObject json = jsonArray.getJSONObject(i);
				if(json.get("userid") == null){
					return new ServiceResponse(400,"failed","userId有空值,上传数据失败!");
				}
				
				ZykUserEntity userEntity = new ZykUserEntity();
				userEntity.setZykId(builder.toString());
				userEntity.setUserId(UtilString.getStr(json.get("userid")));
				userEntity.setUsername(UtilString.getStr(json.get("username")));
				userEntity.setRole(UtilString.getStr(json.get("role")));
				userEntity.setEmail(UtilString.getStr(json.get("email")));
				userEntity.setTelephone(UtilString.getStr(json.get("telephone")));
				userEntity.setGender(UtilString.getStr(json.get("gender")));
				userEntity.setInstitute(UtilString.getStr(json.get("institute")));
				userEntity.setProvince(UtilString.getStr(json.get("province")));
				userEntity.setCity(UtilString.getStr(json.get("city")));
				userEntity.setSpecialty(UtilString.getStr(json.get("specialty")));
				
				if(json.get("birthday") != null){
					try {
						userEntity.setBirthday(new SimpleDateFormat("yyyy-MM-dd").parse(json.get("birthday").toString()));
					} catch (ParseException e) {
						return new ServiceResponse(400,"failed","birthday时间格式有误!"+" UserID:"+userEntity.getUserId());
					}
				}
				
				if(json.get("createdtime") != null){
					try {
						Date date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(json.get("createdtime").toString());
						userEntity.setCreatedTime(date);
					} catch (ParseException e) {
						return new ServiceResponse(400,"failed","createdtime时间格式有误!"+" UserID:"+userEntity.getUserId());
					}
				}
				
				if(json.get("modifiedtime") != null){
					try {
						Date date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(json.get("modifiedtime").toString());
						userEntity.setModifiedTime(date);
					} catch (ParseException e) {
						return new ServiceResponse(400,"failed","modifiedtime时间格式有误!"+" UserID:"+userEntity.getUserId());
					}
				}
				
				userList.add(userEntity);
			}
			
			ZykUserEntity existUser = null;
			for(ZykUserEntity zykUser : userList){
				existUser = zykUserDao.findUserByUserIdAndZykId(zykUser.getUserId(), zykUser.getZykId());
				if(existUser != null){
					String errStr = " userid:"+zykUser.getUserId()+" username:"+zykUser.getUsername();
					return new ServiceResponse(400,"failed", "用户数据保存失败!" + errStr);
				}
			}
			
			for(ZykUserEntity zykUser : userList){
				zykUserDao.save(zykUser);
			}
			log.info(" upload data of ZYK_USER success ! insert data : "+userList.size());
			return new ServiceResponse(200,"success","用户数据保存成功! 插入数据:"+userList.size()+"条.");
		}catch (RuntimeException e) {
			log.error("Exception when upload data of ZYK_USER ：" + e );
			e.printStackTrace();
			return new ServiceResponse(400,"failed","用户数据保存失败!");
		}
	}

}
