package com.asdc.lrm.dao;

import java.util.List;

import com.asdc.lrm.dao.common.Dao;
import com.asdc.lrm.entity.ZykCourseEntity;

public interface ZykCourseDao extends Dao<ZykCourseEntity, String>{

	/**
	 * 根据资源库ID查询
	 * @param zykId
	 * @return
	 */
	ZykCourseEntity getZykCourseByZykId(String zykId);

	/**
	 * 根据更新年份分组，查询课程数量
	 * @param zykId
	 * @param yearDate
	 * @return
	 */
	List<Object[]> findCourseCountGroupByYear(String zykId, int lastYear);

	/**
	 * 查询课程数量
	 * @param zykId
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	List<Object[]> getZykCourseCountByModifiedtime(String zykId, String beginDate, String endDate);
	
	/**
	 * 不同类型课程分布 
	 * @param zykId
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	List<Object[]> getZykCourseType(String zykId, String beginDate, String endDate);
	
	/**
	 * 不同层级课程分布 
	 * @param zykId
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	List<Object[]> getZykCourseLevel(String zykId, String beginDate, String endDate);
	
	/**
	 * 查询各门课程的模块分布数量
	 * @param zykId
	 * @param yearDate
	 * @return
	 */
	List<Object[]> findCourseModuleZykCourseLevel(String zykId, String beginDate, String endDate);
	
	/**
	 * 根据zykId查询所有课程
	 * @param zykId
	 * @return
	 */
	List<ZykCourseEntity> findAllCourseByZykId(String zykId);
	
	/**
	 * 查询全部课程数量
	 * @return
	 */
	int findCourseTotalCount();
	
	/**
	 * 根据courseId,zykId 查询zyk_course中数据
	 * @param courseId,zykId 
	 * @return
	 */
	ZykCourseEntity findCourseByCourseIdAndZykId(String courseId, String zykId);

}
