package com.asdc.lrm.dao;

import java.util.List;
import java.util.Map;

import com.asdc.lrm.dao.common.Dao;
import com.asdc.lrm.entity.ZykEntity;

public interface ZykDao extends Dao<ZykEntity, String>{
	
	/**
	 * 根据资源库ID查询数据
	 * @param zykId
	 * @return
	 */
	List<ZykEntity> getZykEntityByZykId(String zykId);

	/**
	 * 根据专业名称和主持单位查询
	 * @param entity
	 * @return
	 */
	ZykEntity getZykEntityByFullNameAndIns(ZykEntity entity);

	/**
	 * 根据专业名称和主持单位查询，主持单位可以为多个，以“，”分割
	 * @param fullName
	 * @param instituteInCharge
	 * @return
	 */
	List<ZykEntity> getZykEntityList(String fullName, String instituteInCharge);
	
	/**
	 * 查询全部资源库
	 * 按照专业名称排序
	 * @return
	 */
	List<ZykEntity> findAllOrderByFullname();
	
	/**
	 * 根据状态和条件查询
	 * 按照专业名称排序
	 * @param status
	 * @param entity
	 * @return
	 */
	List<ZykEntity> findByStatusOrderByFullname(String status, ZykEntity entity);
	
	/**
	 * 根据状态查询
	 * 按照主持单位排序
	 * @param status
	 * @return
	 */
	List<ZykEntity> findByStatusOrderByInstituteInCharge(String status);
	
	/**
	 * 根据状态查询
	 * 按城市名称位排序
	 * @param status
	 * @return
	 */
	List<ZykEntity> findByStatusOrderByCity(String status);
	
	/**
	 * 查询zyk表的数据数量
	 */
	int findZykTotalCount();
	
	/**
	 * 查询单位数量
	 * zyk表instituteInCharge字段
	 */
	int findZykDepartmentTotalCount();
	
	/**
	 * 查询资源库数据
	 * 根据cityId分组
	 */
	List<Object[]> getZykGroupByProvince();
	
	/**
	 * 去除重复zykId
	 * 查询所有资源库数据
	 * 供缓存使用
	 */
	List<ZykEntity> findAllZykEntity();
	
	/**
	 * 返回资源库的统计情况
	 * @param zykId
	 * @return
	 */
	List<Map<String, Object>> getZykSummary(String zykId);
	
	/**
	 * 根据状态查询
	 * 按照zykNum排序
	 * @param status
	 * @param entity
	 * @return
	 */
	List<ZykEntity> findByStatusOrderByZyknum(String status, ZykEntity entity);
}
