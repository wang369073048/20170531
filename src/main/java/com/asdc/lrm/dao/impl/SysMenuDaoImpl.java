package com.asdc.lrm.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.asdc.lrm.dao.SysMenuDao;
import com.asdc.lrm.dao.common.HibernateDao;
import com.asdc.lrm.entity.SysMenuEntity;

@SuppressWarnings("unchecked")
public class SysMenuDaoImpl extends HibernateDao<SysMenuEntity, Long> implements SysMenuDao {
	
	public List<SysMenuEntity> findSysMenus(final long parentId, final int page, final int rows){
		StringBuilder builder = new StringBuilder("from SysMenuEntity t where t.deleteMark=0 ");
		if(parentId != -1)
			builder.append("and t.parentId=:parentId ");
		builder.append("order by t.sortNumber asc");
		
		final String hql = builder.toString();
		return (List<SysMenuEntity>)getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(hql);
				if(parentId != -1)
					query.setParameter("parentId", parentId);
				if(page != -1 && rows != -1){
					query.setFirstResult(page * rows);
					query.setMaxResults(rows);
				}
				return query.list();
			}
		});
	}

	public int findSysMenusTotal(final long parentId){
		StringBuilder builder = new StringBuilder("select count(t.id) from SysMenuEntity t where t.deleteMark=0 ");
		if(parentId != -1)
			builder.append("and t.parentId=:parentId ");
		builder.append("order by t.sortNumber asc");
		final String hql = builder.toString();
		
		return (Integer)getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(hql);
				if(parentId != -1)
					query.setParameter("parentId", parentId);
				if(query.list().size() > 0){
					String total = query.list().get(0).toString();
					return Integer.parseInt(total);
				}else{
					return 0;
				}
			}
		});
	}
	
	public List<SysMenuEntity> findSysMenusByRole(List<Long>roleIds, long parentId, int menuType){
		StringBuilder builder = new StringBuilder("SELECT DISTINCT M.ID,M.PARENT_ID,M.MENU_NAME,M.MENU_ENNAME,M.MENU_URL,M.MENU_IMG,M.SORT_NUMBER " +
				"FROM lrm_menuinfo M JOIN lrm_menurole MR ON M.ID = MR.MENU_ID WHERE (");
		
		for(int i=0; i<roleIds.size(); i++){
			builder.append("MR.ROLE_ID = "+roleIds.get(i));
			if(i+1 < roleIds.size()){
				builder.append(" OR ");
			}
		}
		builder.append(") ");
		
		if(parentId != -1)
			builder.append("AND M.PARENT_ID = "+parentId+" ");
		
		builder.append("AND M.DELETE_MARK = 0 ORDER BY M.SORT_NUMBER ASC");
		final String sql = builder.toString();
		
		return (List<SysMenuEntity>)getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createSQLQuery(sql);
				List list = query.list();
				List<SysMenuEntity> menuList = new ArrayList<SysMenuEntity>();
				
				if(list != null && list.size() > 0){
					for(int i=0; i<list.size(); i++){
						Object[] obj = (Object[])list.get(i);
						SysMenuEntity entity = new SysMenuEntity();
						entity.setId(Long.valueOf(obj[0].toString()));
						entity.setParentId(Long.valueOf(obj[1].toString()));
						entity.setMenuName(obj[2].toString());
						//entity.setMenuEnName(obj[3].toString());
						entity.setMenuUrl(obj[4].toString());
						entity.setMenuImg(obj[5] != null ? obj[4].toString() : null);
						entity.setSortNumber(Integer.parseInt(obj[6].toString()));
						menuList.add(entity);
					}
				}
				return menuList;
			}
		});
	}
}