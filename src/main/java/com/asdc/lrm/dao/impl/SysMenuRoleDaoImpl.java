package com.asdc.lrm.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.asdc.lrm.dao.SysMenuRoleDao;
import com.asdc.lrm.dao.common.HibernateDao;
import com.asdc.lrm.entity.SysMenuRoleEntity;

@SuppressWarnings("unchecked")
public class SysMenuRoleDaoImpl extends HibernateDao<SysMenuRoleEntity, Long> implements SysMenuRoleDao {
	
	public void delete(SysMenuRoleEntity entity){
		this.getHibernateTemplate().delete(entity);
	}

	public List<SysMenuRoleEntity> findByMenuId(long menuId) {
		final String hql = "from SysMenuRoleEntity t where t.menuId = "+menuId;
		return (List<SysMenuRoleEntity>)getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(hql);
				return query.list();
			}
		});
	}

	public List<SysMenuRoleEntity> findByRoleId(long roleId) {
		final String hql = "from SysMenuRoleEntity t where t.roleId = "+roleId;
		return (List<SysMenuRoleEntity>)getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(hql);
				return query.list();
			}
		});
	}
}
