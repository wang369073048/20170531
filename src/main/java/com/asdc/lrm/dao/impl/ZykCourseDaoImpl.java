package com.asdc.lrm.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.asdc.lrm.dao.ZykCourseDao;
import com.asdc.lrm.dao.common.HibernateDao;
import com.asdc.lrm.entity.ZykCourseEntity;

@SuppressWarnings("unchecked")
public class ZykCourseDaoImpl extends HibernateDao<ZykCourseEntity, String> implements ZykCourseDao {
	
	@SuppressWarnings("rawtypes")
	public ZykCourseEntity getZykCourseByZykId(String zykId) {
		List list = getHibernateTemplate().find("from ZykCourseEntity where zykId= ?",zykId);
		return (ZykCourseEntity) (list == null || list.size() == 0 ? null : list.get(0));
	}

	@SuppressWarnings("rawtypes")
	public List<Object[]> findCourseCountGroupByYear(final String zykId,	int lastYear) {
		
		StringBuilder sqlBuilder = new StringBuilder("SELECT YEAR(modifiedTime), COUNT(1) FROM zyk_course ");
		sqlBuilder.append(" WHERE zykId = '"+zykId+"'");
		sqlBuilder.append(" AND YEAR(modifiedTime) >= '"+lastYear+"'");
		sqlBuilder.append(" GROUP BY YEAR(modifiedTime); ");
		
		final String sql = sqlBuilder.toString();
		
		return (List) getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				return session.createSQLQuery(sql).list();
			}
		});
	}

	@SuppressWarnings("rawtypes")
	public List<Object[]> getZykCourseType(String zykId, String beginDate,	String endDate) {
		StringBuffer sqlBuilder = new StringBuffer(); 
		
		//“couseType=专业核心课” 数量
		sqlBuilder.append(" SELECT  count(1) FROM zyk_course ");
		sqlBuilder.append(" WHERE zykId='" + zykId + "' ");
		sqlBuilder.append(" AND courseType= '专业核心课' ");
		if(beginDate != null && endDate != null){
			sqlBuilder.append(" AND modifiedTime > '" + beginDate +" 00:00:00' ");
			sqlBuilder.append(" AND modifiedTime < '"+endDate+" 23:59:59' ");
		}
		sqlBuilder.append(" UNION ALL ");
		//“couseType=专业基础课” 数量
		sqlBuilder.append(" SELECT  count(1) FROM zyk_course ");
		sqlBuilder.append(" WHERE zykId='" + zykId + "' ");
		sqlBuilder.append(" AND courseType= '专业基础课' ");
		if(beginDate != null && endDate != null){
			sqlBuilder.append(" AND modifiedTime > '" + beginDate +" 00:00:00' ");
			sqlBuilder.append(" AND modifiedTime < '"+endDate+" 23:59:59' ");
		}
		sqlBuilder.append(" UNION ALL ");
		//“couseType=专业拓展课 ” 数量
		sqlBuilder.append(" SELECT count(1) FROM zyk_course ");
		sqlBuilder.append(" WHERE zykId='" + zykId + "' ");
		sqlBuilder.append(" AND courseType= '专业拓展课' ");
		if(beginDate != null && endDate != null){
			sqlBuilder.append(" AND modifiedTime > '" + beginDate +" 00:00:00' ");
			sqlBuilder.append(" AND modifiedTime < '"+endDate+" 23:59:59' ");
		}
		sqlBuilder.append(" UNION ALL ");
		//“couseType=公共基础课 ” 数量
		sqlBuilder.append(" SELECT count(1) FROM zyk_course ");
		sqlBuilder.append(" WHERE zykId='" + zykId + "' ");
		sqlBuilder.append(" AND courseType= '公共基础课' ");
		if(beginDate != null && endDate != null){
			sqlBuilder.append(" AND modifiedTime > '" + beginDate +" 00:00:00' ");
			sqlBuilder.append(" AND modifiedTime < '"+endDate+" 23:59:59' ");
		}
		sqlBuilder.append(" UNION ALL ");
		//“couseType=专业实训课” 数量
		sqlBuilder.append(" SELECT count(1) FROM zyk_course ");
		sqlBuilder.append(" WHERE zykId='" + zykId + "' ");
		sqlBuilder.append(" AND courseType= '专业实训课' ");
		if(beginDate != null && endDate != null){
			sqlBuilder.append(" AND modifiedTime > '" + beginDate +" 00:00:00' ");
			sqlBuilder.append(" AND modifiedTime < '"+endDate+" 23:59:59' ");
		}
		
		final String querySql = sqlBuilder.toString();
		return (List)getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				return session.createSQLQuery(querySql).list();
			}
		});
	}
	
	@SuppressWarnings("rawtypes")
	public List<Object[]> getZykCourseLevel(String zykId, String beginDate,	String endDate) {
		StringBuffer sqlBuilder = new StringBuffer(); 
		
		//“couseLevel=课程” 数量
		sqlBuilder.append(" SELECT  count(1) FROM zyk_course ");
		sqlBuilder.append(" WHERE zykId='" + zykId + "' ");
		sqlBuilder.append(" AND courseLevel= '课程' ");
		if(beginDate != null && endDate != null){
			sqlBuilder.append(" AND modifiedTime > '" + beginDate +" 00:00:00' ");
			sqlBuilder.append(" AND modifiedTime < '"+endDate+" 23:59:59' ");
		}
		sqlBuilder.append(" UNION ALL ");
		//“couseLevel=微课” 数量
		sqlBuilder.append(" SELECT  count(1) FROM zyk_course ");
		sqlBuilder.append(" WHERE zykId='" + zykId + "' ");
		sqlBuilder.append(" AND courseLevel= '微课' ");
		if(beginDate != null && endDate != null){
			sqlBuilder.append(" AND modifiedTime > '" + beginDate +" 00:00:00' ");
			sqlBuilder.append(" AND modifiedTime < '"+endDate+" 23:59:59' ");
		}
		sqlBuilder.append(" UNION ALL ");
		//“couseLevel=培训项目 ” 数量
		sqlBuilder.append(" SELECT count(1) FROM zyk_course ");
		sqlBuilder.append(" WHERE zykId='" + zykId + "' ");
		sqlBuilder.append(" AND courseType= '培训项目' ");
		if(beginDate != null && endDate != null){
			sqlBuilder.append(" AND modifiedTime > '" + beginDate +" 00:00:00' ");
			sqlBuilder.append(" AND modifiedTime < '"+endDate+" 23:59:59' ");
		}
		
		final String querySql = sqlBuilder.toString();
		return (List)getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				return session.createSQLQuery(querySql).list();
			}
		});
	}

	@SuppressWarnings("rawtypes")
	public List<Object[]> findCourseModuleZykCourseLevel(String zykId, String beginDate, String endDate) {
		StringBuffer sqlBuilder = new StringBuffer(
			"SELECT c.Fullname,COUNT(1) FROM zyk_course c , zyk_course_resource_relation r " +
				" WHERE c.courseId = r.Courseid " +
				" AND courseLevel ='课程' AND c.zykId='" + zykId + "' ");
		if(beginDate != null && endDate != null){
			sqlBuilder.append(" AND c.modifiedTime > '" + beginDate +" 00:00:00' ");
			sqlBuilder.append(" AND c.modifiedTime < '" + endDate +" 23:59:59' ");
		}
		sqlBuilder.append("GROUP BY c.Fullname ;");
		final String querySql = sqlBuilder.toString();
		return (List)getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException,	SQLException {
				return session.createSQLQuery(querySql).list();
			}
		});
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public List<ZykCourseEntity> findAllCourseByZykId(final String zykId) {
		return getHibernateTemplate().execute(new HibernateCallback<List>() {
			public List doInHibernate(Session session) throws HibernateException, SQLException {
				List list = session.createQuery("from ZykCourseEntity where zykId=?").setParameter(0, zykId).list();
				return list;
			}
		});
	}

	public int findCourseTotalCount() {
		final String hql = "select count(t.courseId) from ZykCourseEntity t";
		return getHibernateTemplate().execute(new HibernateCallback<Integer>(){
			public Integer doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(hql);
				if(query.list().size() > 0){
					String total = query.list().get(0).toString();
					return Integer.parseInt(total);
				}else{
					return 0;
				}
			}
		});
	}
	
	public List<Object[]> getZykCourseCountByModifiedtime(String zykId, String beginDate, String endDate) {
		StringBuffer sqlBuilder = new StringBuffer("SELECT COUNT(1) FROM zyk_course WHERE ZYKID='"+zykId+"'");
		if(beginDate != null && endDate != null){
			sqlBuilder.append(" AND modifiedTime > '" + beginDate+" 00:00:00' " );
			sqlBuilder.append(" AND modifiedTime < '" + endDate+" 23:59:59' " );
		}
		final String sql = sqlBuilder.toString();
		return getHibernateTemplate().execute(new HibernateCallback<List<Object[]>>() {
			public List<Object[]> doInHibernate(Session session) throws HibernateException, SQLException {
				return session.createSQLQuery(sql).list();
			}
		});
	}

	/**
	 * 根据courseId,zykId 查询zyk_course中全部数据
	 * @param courseId, zykId
	 * @return
	 */
	@Override
	public ZykCourseEntity findCourseByCourseIdAndZykId(final String courseId, final String zykId) {
		return getHibernateTemplate().execute(new HibernateCallback<ZykCourseEntity>() {
			public ZykCourseEntity doInHibernate(Session session) throws HibernateException, SQLException {
				List<ZykCourseEntity> list = session.createQuery("from ZykCourseEntity where courseId=? and zykId =? ")
						.setParameter(0, courseId).setParameter(1, zykId).list();
				return (ZykCourseEntity) (list == null || list.size() == 0 ? null : list.get(0));
			}
		});
	}
}
