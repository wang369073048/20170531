package com.asdc.lrm.dao;

import java.util.List;

import com.asdc.lrm.dao.common.Dao;
import com.asdc.lrm.entity.ZykResourceEntity;
public interface ZykResourceDao extends Dao<ZykResourceEntity, String>{

	/**
	 * 根据zykId获取实体类
	 * @param zykId
	 * @return
	 */
	List<ZykResourceEntity> getZykResourceByZykId(String zykId);
	
	/**
	 * 获取资源库数量
	 * @param zykId
	 * @return
	 */
	int getZykResourceCount(String zykId,String beginDate,String endDate);
	
	/**
	 * 查询资源素材，按照媒体类型统计
	 * @param zykId
	 * @return
	 */
	List<Object[]> findResourceMediaType(String zykId, String beginDate, String endDate);
	
	/**
	 * 查询不同教学应用的资源素材分布
	 * @param zykId
	 * @return
	 */
	List<Object[]> findResourceInstruction(String zykId, String beginDate, String endDate);
	
	/**
	 * 根据年份分组，查询资源数量
	 * @param zykId
	 * @param yearDate
	 * @return
	 */
	List<Object[]> findResourceCountGroupByYear(String zykId, int lastYear);
	
	/**
	 * 查询资源总数
	 * @param zykId
	 * @return
	 */
	List<ZykResourceEntity> findResourceCount(String zykId);
	
	/**
	 * 查询涉及本专业知识点数
	 * @param zykId
	 * @return
	 */
	List<String> findResourceKnowledge(String zykId, String beginDate, String endDate);
	
	/**
	 * 根据年份查询资源素材数量
	 * 若year为null 则查询全部
	 */
	List<Object[]> findResourceCountByYear(String zykId, int year);
	
	/**
	 * 查询资源总数
	 */
	int findResourceTotalCount();
	
	/**
	 * 根据resourceId,zykId 查询zyk_resource中数据
	 * @param resourceId,zykId 
	 * @return
	 */
	ZykResourceEntity findResourceByResourceIdAndZykId(String resourceId, String zykId);
	
}
