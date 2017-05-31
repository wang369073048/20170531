package com.asdc.lrm.service;

import java.util.List;

import com.asdc.lrm.entity.ZykEntity;
import com.asdc.lrm.jersey.ServiceResponse;

public interface ZykService{

	/***
	 * 上传资源库信息
	 * @param source
	 * @param fullName
	 * @param instituteInCharge
	 * @param type
	 * @param status
	 * @param data
	 * @return
	 */
	ServiceResponse upZykData(String source, String fullName, String instituteInCharge, String type, String status, String data);
	
	/**
	 * 获取全部资源库
	 * 按照专业名称排序
	 * @return
	 */
	List<ZykEntity> findAllOrderByFullname();
	
	/**
	 * 根据状态查询资源库
	 * 按照专业名称排序
	 * @param status
	 * @param entity
	 * @return
	 */
	List<ZykEntity> findByStatusOrderByFullname(String status, ZykEntity entity);

	/**
	 * 根据资源库ID查询数据
	 * @param zykId
	 * @return
	 */
	List<ZykEntity> findZykByZykId(String zykId);
	
	/**
	 * 按专业名称获取资源库信息
	 * 返回json数据，共页面combo使用
	 * @param status
	 * @return
	 */
	String findZykFroComboFullname(String status);
	
	/**
	 * 按专主持单位获取资源库信息
	 * 返回json数据，共页面combo使用
	 * @param status
	 * @return
	 */
	String findZykFroComboInstituteInCharge(String status);
	
	/**
	 * 按专城市获取资源库信息
	 * 返回json数据，共页面combo使用
	 * @param status
	 * @return
	 */
	String findZykFroComboCity(String status);
	
	/***
	 * 获取资源库信息
	 * @param source
	 * @param fullName
	 * @return
	 */
	ServiceResponse getZykData(String fullName, String instituteInCharge);
	
	/***
	 * 上传zyk_datachange_log信息,并更改对应库表
	 * @param fullName
	 * @param instituteInCharge
	 * @param type
	 * @param data
	 * @return
	 */
	ServiceResponse updateZykData(String fullName, String instituteInCharge, String type, String data);
	
	/**
	 * 获取全部资源库
	 * 按照zykNum排序
	 * @return
	 */
	List<ZykEntity> findAllOrderByZyknum(String status, ZykEntity entity);
}
