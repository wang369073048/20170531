package com.asdc.lrm.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.asdc.lrm.dao.LogDao;
import com.asdc.lrm.dao.common.HibernateDao;
import com.asdc.lrm.entity.LogEntity;

@SuppressWarnings("unchecked")
public class LogDaoImpl extends HibernateDao<LogEntity, Long> implements LogDao {
	
	public List<LogEntity> findLogs(String keyword, String beginDate, String endDate, final int page, final int rows){
		StringBuilder builder = new StringBuilder("from LogEntity t where 1=1 ");
		if(keyword != null && keyword.trim().length() > 0){
			builder.append("and (t.userName = '"+keyword+"' or t.ip like '%"+keyword+"%' or t.operateModule = '"+keyword+"' " +
					"or t.operateAction = '"+keyword+"' or t.operateObject like '%"+keyword+"%') ");
		}
		if(beginDate != null && beginDate.trim().length() > 0)
			builder.append("and t.operateTime >= '" + beginDate+" 00:00:00" + "' ");
		if(endDate != null && endDate.trim().length() > 0)
			builder.append("and t.operateTime <= '" + endDate +" 23:59:59" + "' ");
		builder.append("order by t.operateTime desc");
		
		final String hql = builder.toString();
		return (List<LogEntity>)getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(hql);
				if(page != -1 && rows != -1){
					query.setFirstResult(page * rows);
					query.setMaxResults(rows);
				}
				return query.list();
			}
		});
	}
	
	public int findLogsTotal(String keyword, String beginDate, String endDate){
		StringBuilder builder = new StringBuilder("select count(t.id) from LogEntity t where 1=1 ");
		if(keyword != null && keyword.trim().length() > 0){
			builder.append("and (t.userName = '"+keyword+"' or t.ip like '%"+keyword+"%' or t.operateModule = '"+keyword+"' " +
					"or t.operateAction = '"+keyword+"' or t.operateObject like '%"+keyword+"%') ");
		}
		if(beginDate != null && beginDate.trim().length() > 0)
			builder.append("and t.operateTime >= '" + beginDate+" 00:00:00" + "' ");
		if(endDate != null && endDate.trim().length() > 0)
			builder.append("and t.operateTime <= '" + endDate +" 23:59:59" + "' ");
		builder.append("order by t.operateTime desc");
		
		final String hql = builder.toString();
		return (Integer)getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
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
}
