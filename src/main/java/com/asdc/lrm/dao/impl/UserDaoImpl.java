package com.asdc.lrm.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.asdc.lrm.dao.UserDao;
import com.asdc.lrm.dao.common.HibernateDao;
import com.asdc.lrm.entity.UserEntity;

@SuppressWarnings("unchecked")
public class UserDaoImpl extends HibernateDao<UserEntity, Long> implements UserDao {

	public UserEntity findUsersByLoginNameAndPassword(String loginName, final String password){
		StringBuilder builder = new StringBuilder("from UserEntity t where t.deleteMark = 0 ");
		if(loginName != null && !loginName.equals(""))
			builder.append("and t.loginName = '"+loginName+"' ");
		if(password != null && !password.equals(""))
			builder.append("and t.password = '"+password+"' ");
		final String hql = builder.toString();
		return (UserEntity)getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(hql);
				if(query.list().size() > 0){
					return query.list().get(0);
				}else{
					return null;
				}
			}
		});
	}
	
	public List<UserEntity> findUsers(long groupId, UserEntity entity, final int page, final int rows){
		StringBuilder builder = new StringBuilder("SELECT T.ID,T.USER_TYPE,T.USER_NAME,T.LOGIN_NAME " +
				"FROM lrm_userinfo T ");
		
		if(groupId != -1){
			builder.append("INNER JOIN lrm_usergroup UG ON T.ID = UG.USER_ID WHERE UG.GROUP_ID = "+groupId+" ");
		}else{
			builder.append("WHERE 1=1 ");
		}
		
		if(entity != null){
			if(entity.getUserType() != 0)
				builder.append("AND T.USER_TYPE = "+entity.getUserType()+" ");
			
			if(entity.getLoginName() != null && !entity.getLoginName().equals("")){
				builder.append("AND (T.LOGIN_NAME LIKE '%"+entity.getLoginName()+"%' " +
						"OR T.USER_NAME LIKE '%"+entity.getLoginName()+"%') ");
			}
		}
		builder.append("AND T.DELETE_MARK = 0 ORDER BY T.CREATE_TIME DESC");
		
		final String sql = builder.toString();
		return (List<UserEntity>)getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createSQLQuery(sql);
				if(page != -1 && rows != -1){
					query.setFirstResult(page * rows);
					query.setMaxResults(rows);
				}
				List list = query.list();
				List<UserEntity> userList = new ArrayList<UserEntity>();
				if(list != null && list.size() > 0){
					for(int i=0; i<list.size(); i++){
						Object[] obj = (Object[])list.get(i);
						UserEntity entity = new UserEntity();
						entity.setId(Long.valueOf(obj[0].toString()));
						entity.setUserType(Integer.parseInt(obj[1].toString()));
						entity.setUserName(obj[2].toString());
						entity.setLoginName(obj[3].toString());
						userList.add(entity);
					}
				}
				return userList;
			}
		});
	}
	
	public int findUsersTotal(long groupId, UserEntity entity){
		StringBuilder builder = new StringBuilder("SELECT COUNT(T.ID) FROM lrm_userinfo T ");
		
		if(groupId != -1){
			builder.append("INNER JOIN lrm_usergroup UG ON T.ID = UG.USER_ID WHERE UG.GROUP_ID = "+groupId+" ");
		}else{
			builder.append("WHERE 1=1 ");
		}
		
		if(entity != null){
			if(entity.getUserType() != 0)
				builder.append("AND T.USER_TYPE = "+entity.getUserType()+" ");
			
			if(entity.getLoginName() != null && !entity.getLoginName().equals("")){
				builder.append("AND (T.LOGIN_NAME LIKE '%"+entity.getLoginName()+"%' " +
						"OR T.USER_NAME LIKE '%"+entity.getLoginName()+"%') ");
			}
		}
		builder.append("AND T.DELETE_MARK = 0");
		
		final String sql = builder.toString();
		return (Integer)getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createSQLQuery(sql);
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