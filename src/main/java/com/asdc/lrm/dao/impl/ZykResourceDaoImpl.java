package com.asdc.lrm.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.asdc.lrm.dao.ZykResourceDao;
import com.asdc.lrm.dao.common.HibernateDao;
import com.asdc.lrm.entity.ZykResourceEntity;
import com.asdc.lrm.util.UtilString;

@SuppressWarnings("unchecked")
public class ZykResourceDaoImpl extends HibernateDao<ZykResourceEntity, String> implements ZykResourceDao {

	public List<ZykResourceEntity> getZykResourceByZykId(final String zykId) {
		return getHibernateTemplate().execute(new HibernateCallback<List<ZykResourceEntity>>() {
			public List<ZykResourceEntity> doInHibernate(Session session) throws HibernateException, SQLException {
				return session.createQuery("from ZykResourceEntity where zykId=?").setParameter(0, zykId).list();
			}
		});
	}

	public List<Object[]> findResourceCountGroupByYear(final String zykId, int curYear) {

		StringBuilder sqlBuilder = new StringBuilder("SELECT YEAR(modifiedTime), COUNT(1) FROM zyk_resource ");
		sqlBuilder.append(" WHERE zykId = '"+zykId+"'");
		sqlBuilder.append(" AND YEAR(modifiedTime) <= '"+curYear+"'");
		sqlBuilder.append(" GROUP BY YEAR(modifiedTime) ORDER BY YEAR(modifiedTime); ");
		
		final String sql = sqlBuilder.toString();
		return (List) getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				return session.createSQLQuery(sql).list();
			}
		});
	}

	public List<ZykResourceEntity> findResourceCount(final String zykId) {
		StringBuilder builder = new StringBuilder("from ZykResourceEntity where zykId=?  ");
		final String hql = builder.toString();
		return (List)getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				return session.createQuery(hql).setParameter(0, zykId).list();
			}
		});
	}
	
	public int getZykResourceCount(final String zykId,String beginDate,String endDate) {
		final StringBuilder sql = new StringBuilder("select count(*) from ZykResourceEntity where zykId=? ");
		if(!UtilString.isNullAndEmpty(beginDate)){
			sql.append(" and modifiedTime >= '").append(beginDate).append("' ");
		}
		if(!UtilString.isNullAndEmpty(endDate)){
			sql.append(" and modifiedTime <= '").append(endDate).append(" 23:59:59' ");
		}
		return getHibernateTemplate().execute(new HibernateCallback<Integer>() {
			
			public Integer doInHibernate(Session session)
					throws HibernateException, SQLException {
				
				List list = session.createQuery(sql.toString()).setParameter(0, zykId).list();
				return list == null || list.size() == 0 ? 0 : Integer.valueOf(list.get(0).toString());
			}
		});
	}
	
	public List<Object[]> findResourceMediaType(String zykId, String beginDate, String endDate) {
		StringBuffer sqlBuilder = new StringBuffer("SELECT mediaType, count(1) FROM zyk_resource WHERE zykId='"+zykId+"' ");
		if(beginDate != null && endDate != null){
			sqlBuilder.append(" AND modifiedTime > '" + beginDate+" 00:00:00' " );
			sqlBuilder.append(" AND modifiedTime < '" + endDate+" 23:59:59' " );
		}
		sqlBuilder.append(" GROUP BY mediaType; ");
		
		final String sql = sqlBuilder.toString();
		return (List)getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException,
					SQLException {
				return session.createSQLQuery(sql).list();
			}
		});
	}
	
	public List<Object[]> findResourceInstruction(String zykId,	String beginDate, String endDate) {
		StringBuffer sqlBuilder = new StringBuffer("SELECT instruction, count(1) FROM zyk_resource WHERE zykId='"+zykId+"' ");
		if(beginDate != null && endDate != null){
			sqlBuilder.append(" AND modifiedTime > '" + beginDate+" 00:00:00' " );
			sqlBuilder.append(" AND modifiedTime < '" + endDate+" 23:59:59' " );
		}
		sqlBuilder.append(" GROUP BY instruction ORDER BY count(1) DESC ; ");
		
		final String sql = sqlBuilder.toString();
		return (List)getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException,
					SQLException {
				return session.createSQLQuery(sql).list();
			}
		});
	}
	
	public List<String> findResourceKnowledge(String zykId, String beginDate, String endDate) {
		StringBuffer sqlBuilder = new StringBuffer("SELECT distinct knowledge FROM zyk_resource WHERE zykId='"+zykId+"' ");
		if(beginDate != null && endDate != null){
			sqlBuilder.append(" AND modifiedTime > '" + beginDate+" 00:00:00' " );
			sqlBuilder.append(" AND modifiedTime < '" + endDate+" 23:59:59' " );
		}
		
		final String sql = sqlBuilder.toString();
		return (List)getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException,	SQLException {
				return session.createSQLQuery(sql).list();
			}
		});
	}
	
	public List<Object[]> findResourceCountByYear(String zykId, int year) {
		StringBuilder sqlBuilder = new StringBuilder("SELECT COUNT(1) FROM zyk_resource ");
		sqlBuilder.append(" WHERE zykId = '"+zykId+"'");
		if(year != 0){
			sqlBuilder.append(" AND YEAR(modifiedTime) = '"+year+"'");
		}
		
		final String sql = sqlBuilder.toString();
		
		return (List) getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				return session.createSQLQuery(sql).list();
			}
		});
	}

	public int findResourceTotalCount() {
		final String hql = "select count(t.resourceId) from ZykResourceEntity t";
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
	
	/**
	 * 根据resourceId,zykId 查询zyk_resource中数据
	 * @param resourceId,zykId 
	 * @return
	 */
	@Override
	public ZykResourceEntity findResourceByResourceIdAndZykId(final String resourceId, final String zykId){
		return getHibernateTemplate().execute(new HibernateCallback<ZykResourceEntity>() {
			public ZykResourceEntity doInHibernate(Session session) throws HibernateException, SQLException {
				List<ZykResourceEntity> list = session.createQuery("from ZykResourceEntity where resourceId=? and zykId =? ")
						.setParameter(0, resourceId).setParameter(1, zykId).list();
				return (ZykResourceEntity) (list == null || list.size() == 0 ? null : list.get(0));
			}
		});
	}
}
