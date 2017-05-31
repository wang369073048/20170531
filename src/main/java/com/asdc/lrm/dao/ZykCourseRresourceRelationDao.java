package com.asdc.lrm.dao;

import java.util.List;

import com.asdc.lrm.dao.common.Dao;
import com.asdc.lrm.entity.ZykCourseRresourceRelationEntity;

public interface ZykCourseRresourceRelationDao extends Dao<ZykCourseRresourceRelationEntity, String>{

	/**
	 * 根据资源库ID查询
	 * @param zykId
	 * @return
	 */
	ZykCourseRresourceRelationEntity getCourseRresourceByZykId(String zykId);
	
	/**
	 * 引用的资源素材的课程
	 * @param zykId
	 * @return
	 */
	List<ZykCourseRresourceRelationEntity> getCourseRresourceList(String zykId);
	
	/**
	 * 查询zyk_course_recourse_relation表中全部数据
	 */
	List<ZykCourseRresourceRelationEntity> findAllRelation(String zykId);
	
	/**
	 * 根据relationId, zykId 查询zyk_cource_resource_relation中全部数据
	 * @param relationId, zykId
	 * @return
	 */
	ZykCourseRresourceRelationEntity findRelaByRelationIdAndZykId(String relationId, String zykId);
}
