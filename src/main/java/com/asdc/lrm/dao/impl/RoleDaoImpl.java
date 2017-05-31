package com.asdc.lrm.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.asdc.lrm.dao.RoleDao;
import com.asdc.lrm.dao.common.HibernateDao;
import com.asdc.lrm.entity.RoleEntity;

@SuppressWarnings("unchecked")
public class RoleDaoImpl extends HibernateDao<RoleEntity, Long> implements RoleDao{

	public List<RoleEntity> findRoles(final String keyword, final int page, final int rows){
		StringBuilder builder = new StringBuilder("from RoleEntity t where t.deleteMark=0 ");
		if (keyword != null && !keyword.equals(""))
			builder.append("and t.roleName like '%"+keyword+"%' ");
		builder.append("order by t.createTime desc");
		final String hql = builder.toString();
		
		return (List<RoleEntity>)getHibernateTemplate().execute(new HibernateCallback(){
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

	public int findRolesTotal(final String keyword){
		StringBuilder builder = new StringBuilder("select count(t.id) from RoleEntity t where t.deleteMark=0 ");
		if (keyword != null && !keyword.equals(""))
			builder.append("and t.roleName=:roleName ");
		
		final String hql = builder.toString();
		return (Integer)getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(hql);
				if (keyword != null && !keyword.equals(""))
					query.setParameter("roleName", keyword);
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
