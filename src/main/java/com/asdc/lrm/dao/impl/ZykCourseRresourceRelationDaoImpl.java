package com.asdc.lrm.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.asdc.lrm.dao.ZykCourseRresourceRelationDao;
import com.asdc.lrm.dao.common.HibernateDao;
import com.asdc.lrm.entity.ZykCourseRresourceRelationEntity;

@SuppressWarnings("unchecked")
public class ZykCourseRresourceRelationDaoImpl extends HibernateDao<ZykCourseRresourceRelationEntity, String> implements ZykCourseRresourceRelationDao {

	public ZykCourseRresourceRelationEntity getCourseRresourceByZykId(final String zykId) {
		return getHibernateTemplate().execute(new HibernateCallback<ZykCourseRresourceRelationEntity>() {
			public ZykCourseRresourceRelationEntity doInHibernate(Session session) throws HibernateException, SQLException {
				List list = session.createQuery("from ZykCourseRresourceRelationEntity where zykId=?").setParameter(0, zykId).list();
				return (ZykCourseRresourceRelationEntity) (list == null || list.size() == 0 ? null : list.get(0));
			}
		});
	}

	public List<ZykCourseRresourceRelationEntity> getCourseRresourceList(String zykId) {
		StringBuffer sqkBuilder = new StringBuffer("SELECT DISTINCT resourceid FROM zyk_course_resource_relation WHERE zykId='"+zykId+"' ;");
		
		final String sql = sqkBuilder.toString();
		return (List) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				return session.createSQLQuery(sql).list();
			}
		});
	}

	public List<ZykCourseRresourceRelationEntity> findAllRelation(final String zykId) {
		return getHibernateTemplate().execute(new HibernateCallback<List>() {
			public List doInHibernate(Session session) throws HibernateException, SQLException {
				List list = session.createQuery("from ZykCourseRresourceRelationEntity where zykId=?").setParameter(0, zykId).list();
				return list;
			}
		});	
	}
	
	/**
	 * 根据relationId, zykId 查询zyk_cource_resource_relation中全部数据
	 * @param relationId, zykId
	 * @return
	 */
	@Override
	public ZykCourseRresourceRelationEntity findRelaByRelationIdAndZykId(final String relationId, final String zykId){
		return getHibernateTemplate().execute(new HibernateCallback<ZykCourseRresourceRelationEntity>() {
			public ZykCourseRresourceRelationEntity doInHibernate(Session session) throws HibernateException, SQLException {
				List<ZykCourseRresourceRelationEntity> list = session.createQuery("from ZykCourseRresourceRelationEntity where relationId=? and zykId =? ")
						.setParameter(0, relationId).setParameter(1, zykId).list();
				return (ZykCourseRresourceRelationEntity) (list == null || list.size() == 0 ? null : list.get(0));
			}
		});
	}
}
