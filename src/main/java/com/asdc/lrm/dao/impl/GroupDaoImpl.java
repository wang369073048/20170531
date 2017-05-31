package com.asdc.lrm.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.asdc.lrm.dao.GroupDao;
import com.asdc.lrm.dao.common.HibernateDao;
import com.asdc.lrm.entity.GroupEntity;

@SuppressWarnings("unchecked")
public class GroupDaoImpl extends HibernateDao<GroupEntity, Long> implements GroupDao {

	public List<GroupEntity> findGroups(long parentId, String keyword, final int page, final int rows) {
		StringBuilder builder = new StringBuilder("from GroupEntity t where t.deleteMark=0 ");
		if (parentId != -1)
			builder.append("and t.parentId = "+parentId+" ");
		if (keyword != null && !keyword.equals(""))
			builder.append("and t.groupName like '%"+keyword+"%' ");
		builder.append("order by t.sortNumber asc");
		
		final String hql = builder.toString();
		return (List<GroupEntity>)getHibernateTemplate().execute(new HibernateCallback(){
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

	public int findGroupsTotal(long parentId, String keyword) {
		StringBuilder builder = new StringBuilder("select count(t.id) from GroupEntity t where t.deleteMark=0 ");
		if (parentId != -1)
			builder.append("and t.parentId = "+parentId+" ");
		if (keyword != null && !keyword.equals(""))
			builder.append("and t.groupName like '%"+keyword+"%' ");
		
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
