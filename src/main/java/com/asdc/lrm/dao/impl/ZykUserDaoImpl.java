package com.asdc.lrm.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.asdc.lrm.dao.ZykUserDao;
import com.asdc.lrm.dao.common.HibernateDao;
import com.asdc.lrm.entity.ZykUserEntity;
import com.asdc.lrm.util.UtilString;

@SuppressWarnings("unchecked")
public class ZykUserDaoImpl extends HibernateDao<ZykUserEntity, String> implements ZykUserDao {
	
	public ZykUserEntity getUserByZykId(final String zykId) {
		return getHibernateTemplate().execute(new HibernateCallback<ZykUserEntity>() {
			public ZykUserEntity doInHibernate(Session session) throws HibernateException, SQLException {
				List<ZykUserEntity> list = session.createQuery("from ZykUserEntity where zykId=?").setParameter(0, zykId).list();
				return (ZykUserEntity) (list==null || list.size()==0 ? null : list.get(0));
			}
		});
	}
	
	public List<Object[]> findUsers(String zykId, String beginDate, String endDate ) {
		StringBuffer builder = new StringBuffer("SELECT role, COUNT(1) FROM zyk_user WHERE zykId = '"+zykId+"' "); 
		if(beginDate != null && endDate != null){
			builder.append(" AND createdTime > '" + beginDate+" 00:00:00' " );
			builder.append(" AND createdTime < '" + endDate+" 23:59:59' " );
		}
		builder.append(" GROUP BY role;");
		final String hql = builder.toString();
		return (List) getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				return session.createSQLQuery(hql).list();
			}
		});
	}
	
	public int getUsersCount(final String zykId, String beginDate, String endDate) {
		final StringBuilder sql = new StringBuilder("select count(*) from ZykUserEntity where zykId=?");
		if(!UtilString.isNullAndEmpty(beginDate)){
			sql.append(" and createdTime >= '").append(beginDate).append("' ");
		}
		if(!UtilString.isNullAndEmpty(endDate)){
			sql.append(" and createdTime <= '").append(endDate).append(" 23:59:59' ");
		}
		return getHibernateTemplate().execute(new HibernateCallback<Integer>() {

			public Integer doInHibernate(Session session)
					throws HibernateException, SQLException {
				List list = session.createQuery(sql.toString()).setParameter(0, zykId).list();
				return list == null || list.size() == 0 ? 0 : Integer.valueOf(list.get(0).toString());
			}
		});
	}
	
	public List<Object[]> findUserByInstitute(String zykId, String beginDate, String endDate) {
		StringBuffer hqlBuilder = new StringBuffer("SELECT institute, COUNT(1) FROM zyk_user WHERE zykId = '"+zykId+"' "); 
		if(beginDate != null && endDate != null){
			hqlBuilder.append(" AND createdTime > '" + beginDate+" 00:00:00' " );
			hqlBuilder.append(" AND createdTime < '" + endDate+" 23:59:59' " );
		}
		hqlBuilder.append(" GROUP BY institute;");
		final String hql = hqlBuilder.toString();
		return (List) getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				return session.createSQLQuery(hql).list();
			}
		});
	}
	
	public List<Object[]> findUserByProvince(String zykId, String beginDate, String endDate) {
		StringBuffer hqlBuilder = new StringBuffer("SELECT province, COUNT(1) FROM zyk_user WHERE zykId = '"+zykId+"' "); 
		if(beginDate != null && endDate != null){
			hqlBuilder.append(" AND createdTime > '" + beginDate+" 00:00:00' " );
			hqlBuilder.append(" AND createdTime < '" + endDate+" 23:59:59' " );
		}
		hqlBuilder.append(" GROUP BY province;");
		final String hql = hqlBuilder.toString();
		return (List) getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				return session.createSQLQuery(hql).list();
			}
		});
	}
	
	public List<Object[]> findUserCountGroupByYear(String zykId, int lastYear) {
		StringBuffer sqlBuilder = new StringBuffer("SELECT YEAR(CREATEDTIME), COUNT(1) FROM zyk_user WHERE zykId = '"+zykId+"'");
		sqlBuilder.append(" AND YEAR(CREATEDTIME) >= '"+lastYear+"' ");
		sqlBuilder.append(" GROUP BY YEAR(CREATEDTIME) ;");
		
		final String sql = sqlBuilder.toString();
		return (List) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				return session.createSQLQuery(sql).list();
			}
		});
	}
	
	public List<ZykUserEntity> findAllUserByZykId(final String zykId) {
		return getHibernateTemplate().execute(new HibernateCallback<List>() {
			public List doInHibernate(Session session) throws HibernateException, SQLException {
				List list = session.createQuery("from ZykUserEntity where zykId=?").setParameter(0, zykId).list();
				return list;
			}
		});
	}
	
	public int findUserTotalCount() {
		final String hql = "select count(t.userId) from ZykUserEntity t";
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
	 * 根据userId,zykId 查询zyk_user中全部数据
	 * @param userId
	 * @return
	 */
	@Override
	public ZykUserEntity findUserByUserIdAndZykId(final String userId, final String zykId) {
		return getHibernateTemplate().execute(new HibernateCallback<ZykUserEntity>() {
			public ZykUserEntity doInHibernate(Session session) throws HibernateException, SQLException {
				List<ZykUserEntity> list = session.createQuery(" from ZykUserEntity where userId=? and zykId =? ")
						.setParameter(0, userId).setParameter(1, zykId).list();
				return (ZykUserEntity) (list == null || list.size() == 0 ? null : list.get(0));
			}
		});
	}
}
