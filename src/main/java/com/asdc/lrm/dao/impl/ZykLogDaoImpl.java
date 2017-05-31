package com.asdc.lrm.dao.impl;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.asdc.lrm.dao.ZykLogDao;
import com.asdc.lrm.dao.common.HibernateDao;
import com.asdc.lrm.entity.ZykLogEntity;
import com.asdc.lrm.util.UtilString;

@SuppressWarnings("unchecked")
public class ZykLogDaoImpl extends HibernateDao<ZykLogEntity, String> implements ZykLogDao {

	public List<ZykLogEntity> getZykLogByZykId(final String zykId) {
		StringBuilder builder = new StringBuilder("from ZykLogEntity t where zykId=?");
		
		final String hql = builder.toString();
		return (List<ZykLogEntity>)getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(hql).setParameter(0, zykId);
			/*	query.setFirstResult(0);
				query.setMaxResults(100);*/
				return query.list();
			}
		});
		/*return getHibernateTemplate().execute(new HibernateCallback<List<ZykLogEntity>>() {
			public List<ZykLogEntity> doInHibernate(Session session) throws HibernateException, SQLException {
				return session.createQuery("from ZykLogEntity where zykId=? ").setParameter(0, zykId).list();
			}
		});*/
	}

	public int getLogCount(final String zykId, String beginDate, String endDate,String objectType,String action) {
		final StringBuilder hql = new StringBuilder("select count(*) from ZykLogEntity where zykId=? ");
		if(!UtilString.isNullAndEmpty(objectType)){
			hql.append(" and objectType = '").append(objectType).append("' ");
		}
		if(!UtilString.isNullAndEmpty(action)){
			hql.append(" and action = '").append(action).append("' ");
		}
		if(!UtilString.isNullAndEmpty(beginDate)){
			hql.append(" and time >= '").append(beginDate).append("' ");
		}
		if(!UtilString.isNullAndEmpty(endDate)){
			hql.append(" and time <= '").append(endDate).append(" 23:59:59' ");
		}
		return getHibernateTemplate().execute(new HibernateCallback<Integer>() {

			public Integer doInHibernate(Session session)
					throws HibernateException, SQLException {
				List list = session.createQuery(hql.toString()).setParameter(0, zykId).list();
				return list == null || list.size() == 0 ? 0 : Integer.valueOf(list.get(0).toString());
			}
		});
	}
	
	/**
	 * 用户行为活动总量
	 */
	public Map<String, Object> getObjectTypeUserCountMap(final String zykId, String beginDate, String endDate) {
		final StringBuilder sql = new StringBuilder("SELECT objectType, COUNT(*) as count FROM zyk_log WHERE zykId='").append(zykId).append("' ");
		if(!UtilString.isNullAndEmpty(beginDate)){
			sql.append(" AND time >= '").append(beginDate).append("' ");
		}
		if(!UtilString.isNullAndEmpty(endDate)){
			sql.append(" AND time <= '").append(endDate).append(" 23:59:59'");
		}
		sql.append(" GROUP BY objectType");
		return getHibernateTemplate().execute(new HibernateCallback<Map<String, Object>>() {

			public Map<String, Object> doInHibernate(Session session) throws HibernateException,
					SQLException {
				List list = session.createSQLQuery(sql.toString()).list();
				Map<String,Object> map = new LinkedHashMap<String, Object>();
				if(list!=null && list.size() > 0){
					for(int i = 0 ; i < list.size();i++){
						Object[] obj = (Object[]) list.get(i);
						if(obj[0] == null) continue;
						map.put(obj[0].toString(), obj[1]==null?0:obj[1]);
					}
				}
				return map;
			}
		});
	}


	public Map<String, Object> getLogObjectTypeCountMap(final String zykId, String beginDate, String endDate) {
		final StringBuilder sql = new StringBuilder("select objectType,count(*) as count from zyk_log where zykId='").append(zykId).append("' ");
		if(!UtilString.isNullAndEmpty(beginDate)){
			sql.append(" and time >= '").append(beginDate).append("' ");
		}
		if(!UtilString.isNullAndEmpty(endDate)){
			sql.append(" and time <= '").append(endDate).append(" 23:59:59'");
		}
		sql.append(" group by objectType order by count desc ");
		return hibernateTemplateExe(sql.toString());
	}
	
	public Map<String, Object> getLogCourseCountMap(String zykId, String beginDate, String endDate, String objectType,String limitNum) {
		StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder.append("SELECT t1.Fullname,count(*) as visitNum FROM zyk_course t1 , zyk_log t2 " +
				" WHERE t1.zykId='"+zykId+"' " +
				" AND t2.zykId='"+zykId+"' " +
				" AND t2.objectType LIKE '%课程%' AND t1.courseId=t2.objectId " );
		if(!UtilString.isNullAndEmpty(beginDate)){
			sqlBuilder.append(" and t2.time >= '").append(beginDate).append("' ");
		}
		if(!UtilString.isNullAndEmpty(endDate)){
			sqlBuilder.append(" and t2.time <= '").append(endDate).append(" 23:59:59'");
		}
		sqlBuilder.append(" GROUP BY t1.Fullname ORDER BY visitNum desc ");
		if(!UtilString.isNullAndEmpty(limitNum)){
			sqlBuilder.append(" LIMIT 3");
		}
		return hibernateTemplateExe(sqlBuilder.toString());
	}
	
	@Override
	public List<Object[]> getLogCourseCountList(String zykId, String beginDate, String endDate) {
		
		StringBuffer sqlBuilder = new StringBuffer("SELECT t1.mediaType, COUNT(1) FROM zyk_resource t1, zyk_log t2 ");
		sqlBuilder.append(" WHERE  t1.zykId ='" + zykId + "' ");
		sqlBuilder.append(" AND  t2.zykId ='" + zykId + "' ");
		sqlBuilder.append(" AND t2.ObjectType = '资源素材模块' ");
		sqlBuilder.append(" AND (t2.action='下载资源' or t2.action='浏览资源') ");
		sqlBuilder.append(" AND t1.resourceId = t2.ObjectId ");
		
		if(beginDate != null && endDate != null){
			sqlBuilder.append(" AND t2.time > '" + beginDate+" 00:00:00' " );
			sqlBuilder.append(" AND t2.time < '" + endDate+" 23:59:59' " );
		}
		sqlBuilder.append(" GROUP BY  t1.mediaType ");
		final String sql = sqlBuilder.toString();
		return (List) getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				return session.createSQLQuery(sql).list();
			}
		});
	}

	public Map<String, Object> getLogResourceCountMap(String zykId,
			String beginDate, String endDate, String objectType, String limitNum,String... actions) {
		StringBuilder sql = new StringBuilder("select r.mediaType,count(*) as count from zyk_resource r, zyk_log l ");
		sql.append(" where r.zykId='").append(zykId).append("' and l.zykId='").append(zykId).append("' ");
		sql.append(" and r.resourceId=l.objectId ");
		if(!UtilString.isNullAndEmpty(objectType)){
			sql.append(" and l.objectType='").append(objectType).append("' ");
		}
		if(actions!=null && actions.length > 0){
			sql.append(" and (");
			for(int i = 0 ; i < actions.length; i++){
				sql.append(" l.action='").append(actions[i]).append("' ");
				if(i < actions.length - 1){
					sql.append(" or ");
				}
			}
			sql.append(" ) ");
		}
		if(!UtilString.isNullAndEmpty(beginDate)){
			sql.append(" and l.time >= '").append(beginDate).append("' ");
		}
		if(!UtilString.isNullAndEmpty(endDate)){
			sql.append(" and l.time <= '").append(endDate).append(" 23:59:59' ");
		}
		sql.append(" group by r.mediaType order by count desc ");
		if(!UtilString.isNullAndEmpty(limitNum)){
			sql.append(" limit ").append(limitNum);
		}
		return hibernateTemplateExe(sql.toString());
	}

	
	public Map<String, Object> hibernateTemplateExe(final String sql){
		return getHibernateTemplate().execute(new HibernateCallback<Map<String, Object>>() {

			public Map<String, Object> doInHibernate(Session session) throws HibernateException,
					SQLException {
				List list = session.createSQLQuery(sql.toString()).list();
				Map<String,Object> map = new LinkedHashMap<String, Object>();
				if(list!=null && list.size() > 0){
					for(int i = 0 ; i < list.size();i++){
						Object[] obj = (Object[]) list.get(i);
						if(obj[0] == null) continue;
						map.put(obj[0].toString(), obj[1]==null?0:obj[1]);
					}
				}
				return map;
			}
		});
	}

	public int getUsedResourceCount(String zykId, String beginDate, String endDate, String objectType) {
		final StringBuilder sql = new StringBuilder("select count(*) from ( select distinct objectId from zyk_log where zykId='"+zykId+"' and" +
				" objectType='"+objectType+"' ");
		if(!UtilString.isNullAndEmpty(beginDate)){
			sql.append(" and time >= '").append(beginDate).append("' ");
		}
		if(!UtilString.isNullAndEmpty(endDate)){
			sql.append(" and time <= '").append(endDate).append(" 23:59:59' ");
		}
		sql.append(" ) t");
		return getHibernateTemplate().execute(new HibernateCallback<Integer>() {

			@Override
			public Integer doInHibernate(Session session)
					throws HibernateException, SQLException {
				List list = session.createSQLQuery(sql.toString()).list();
				return list == null || list.size() == 0 ? 0 : Integer.valueOf(list.get(0).toString());
			}
		});
	}

	public Map<String, Object> getActionCountMap(String zykId, String beginDate,
			String endDate, String objectType, String... actions) {
		StringBuilder sql = new StringBuilder("select action,count(*) as count from zyk_log where ");
		sql.append(" zykId='").append(zykId).append("' and objectType='").append(objectType).append("' ");
		if(actions!=null && actions.length > 0){
			sql.append(" and (");
			for(int i = 0 ; i < actions.length; i++){
				sql.append(" action='").append(actions[i]).append("' ");
				if(i < actions.length - 1){
					sql.append(" or ");
				}
			}
			sql.append(" ) ");
		}
		if(!UtilString.isNullAndEmpty(beginDate)){
			sql.append(" and time >= '").append(beginDate).append("' ");
		}
		if(!UtilString.isNullAndEmpty(endDate)){
			sql.append(" and time <= '").append(endDate).append(" 23:59:59' ");
		}
		sql.append(" group by action order by count desc ");
		return hibernateTemplateExe(sql.toString());
	}

	public int getActivedUserCount(String zykId, String beginDate,
			String endDate,String objectType,String type) {
		final StringBuilder sql = new StringBuilder("select count(*) from ( select distinct userId from zyk_log where ");
		sql.append(" zykId='").append(zykId).append("' ");
		if(!UtilString.isNullAndEmpty(beginDate)){
			sql.append(" and time >= '").append(beginDate).append("' ");
		}
		if(!UtilString.isNullAndEmpty(endDate)){
			sql.append(" and time <= '").append(endDate).append(" 23:59:59' ");
		}
		if(!UtilString.isNullAndEmpty(objectType)){
			if("1".equals(type))
				sql.append(" and objectType='").append(objectType).append("' ");
			else if("2".equals(type)){
				sql.append(" and objectType<>'").append(objectType).append("' ");
			}
		}
		sql.append(" ) t");
		
		return getHibernateTemplate().execute(new HibernateCallback<Integer>() {
			public Integer doInHibernate(Session session) throws HibernateException, SQLException {
				List list = session.createSQLQuery(sql.toString()).list();
				return list == null || list.size() == 0 ? 0 : Integer.valueOf(list.get(0).toString());
			}
		});
	}

	public int getActivedUserDateCount(String zykId, String beginDate, String endDate) {
		final StringBuilder sql = new StringBuilder("select count(*) from ( select distinct userId,date_format(time,'%Y-%m-%d') as day ");
		sql.append(" from zyk_log where zykId='").append(zykId).append("' ");
		if(!UtilString.isNullAndEmpty(beginDate)){
			sql.append(" and time >= '").append(beginDate).append("' ");
		}
		if(!UtilString.isNullAndEmpty(endDate)){
			sql.append(" and time <= '").append(endDate).append(" 23:59:59' ");
		}
		sql.append(") t");
		
		return getHibernateTemplate().execute(new HibernateCallback<Integer>() {
			public Integer doInHibernate(Session session) throws HibernateException, SQLException {
				List list = session.createSQLQuery(sql.toString()).list();
				return list == null || list.size() == 0 ? 0 : Integer.valueOf(list.get(0).toString());
			}
		});
		
	}

	public int getActivedUserCountByRole(String zykId, String beginDate, String endDate, String role) {
		final StringBuilder sql = new StringBuilder(" select count(*) from zyk_log l,zyk_user u ");
		sql.append(" where l.zykId='").append(zykId).append("' and u.zykId='").append(zykId).append("'");
		sql.append(" and l.userId = u.userId and u.role='").append(role).append("' ");
		if(!UtilString.isNullAndEmpty(beginDate)){
			sql.append(" and l.time >= '").append(beginDate).append("' ");
		}
		if(!UtilString.isNullAndEmpty(endDate)){
			sql.append(" and l.time <= '").append(endDate).append(" 23:59:59' ");
		}
		
		return getHibernateTemplate().execute(new HibernateCallback<Integer>() {
			public Integer doInHibernate(Session session) throws HibernateException, SQLException {
				List list = session.createSQLQuery(sql.toString()).list();
				return list == null || list.size() == 0 ? 0 : Integer.valueOf(list.get(0).toString());
			}
		});
	}

	public int getActivedUserDateCountByRole(String zykId, String beginDate, String endDate, String role) {
		final StringBuilder sql = new StringBuilder(" select count(*) from ( select distinct userId,date_format(time,'%Y-%m-%d') as day ");
		sql.append(" from zyk_log where zykId='").append(zykId).append("' and userId in (");
		sql.append(" select userId from zyk_user where zykId='").append(zykId).append("' and role='").append(role).append("' )");
		if(!UtilString.isNullAndEmpty(beginDate)){
			sql.append(" and time >= '").append(beginDate).append("' ");
		}
		if(!UtilString.isNullAndEmpty(endDate)){
			sql.append(" and time <= '").append(endDate).append(" 23:59:59' ");
		}
		sql.append(" ) t");
		
		return getHibernateTemplate().execute(new HibernateCallback<Integer>() {
			public Integer doInHibernate(Session session)
					throws HibernateException, SQLException {
				List list = session.createSQLQuery(sql.toString()).list();
				return list == null || list.size() == 0 ? 0 : Integer.valueOf(list.get(0).toString());
			}
		});
	}

	public Map<String, Object> getActivedUserCountMap(String zykId,
			String beginDate, String endDate, String role,String objectType,String limitNum,String...actions) {
		StringBuilder sql = new StringBuilder("select u.role,count(*) as count from zyk_log l,zyk_user u ");
		sql.append(" where l.zykId='").append(zykId).append("' and u.zykId='").append(zykId).append("'");
		sql.append(" and l.userId = u.userId ");
		if(!UtilString.isNullAndEmpty(role)){
			sql.append(" and u.role='").append(role).append("' ");
		}
		if(!UtilString.isNullAndEmpty(objectType)){
			sql.append(" and l.objectType='").append(objectType).append("' ");
		}
		if(actions!=null && actions.length > 0){
			sql.append(" and (");
			for(int i = 0 ; i < actions.length; i++){
				sql.append(" l.action='").append(actions[i]).append("' ");
				if(i < actions.length - 1){
					sql.append(" or ");
				}
			}
			sql.append(" ) ");
		}
		if(!UtilString.isNullAndEmpty(beginDate)){
			sql.append(" and l.time >= '").append(beginDate).append("' ");
		}
		if(!UtilString.isNullAndEmpty(endDate)){
			sql.append(" and l.time <= '").append(endDate).append(" 23:59:59' ");
		}
		sql.append(" group by u.role order by count desc ");
		if(!UtilString.isNullAndEmpty(limitNum)){
			sql.append(" limit ").append(limitNum);
		}
		return hibernateTemplateExe(sql.toString());
	}

	public Map<String, Object> getActivedUserDateCountMap(String zykId,
			String beginDate, String endDate, String role, String objectType,
			String limitNum, String... actions) {
		StringBuilder sql = new StringBuilder("select u.username,count(date_format(l.time,'%Y-%m-%d')) as count from zyk_log l,zyk_user u ");
		sql.append(" where l.zykId='").append(zykId).append("' and u.zykId='").append(zykId).append("'");
		sql.append(" and l.userId = u.userId ");
		if(!UtilString.isNullAndEmpty(role)){
			sql.append(" and u.role='").append(role).append("' ");
		}
		if(!UtilString.isNullAndEmpty(objectType)){
			sql.append(" and l.objectType='").append(objectType).append("' ");
		}
		if(actions!=null && actions.length > 0){
			sql.append(" and (");
			for(int i = 0 ; i < actions.length; i++){
				sql.append(" l.action='").append(actions[i]).append("' ");
				if(i < actions.length - 1){
					sql.append(" or ");
				}
			}
			sql.append(" ) ");
		}
		if(!UtilString.isNullAndEmpty(beginDate)){
			sql.append(" and l.time >= '").append(beginDate).append("' ");
		}
		if(!UtilString.isNullAndEmpty(endDate)){
			sql.append(" and l.time <= '").append(endDate).append(" 23:59:59' ");
		}
		sql.append(" group by l.userid order by count desc ");
		return hibernateTemplateExe(sql.toString());
	}

	@Override
	public Object getFirstLogData(final String zykId) {
		StringBuilder builder = new StringBuilder("SELECT MIN(time) FROM zyk_log WHERE zykId = '"+zykId+"' ; ");
		final String hql = builder.toString();
		return (Object)getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				return session.createSQLQuery(hql).list();
//				if(query.list().size() > 0){
//					return query.list().get(0);
//				}else{
//					return null;
//				}
			}
		});
	}
	
	@Override
	public List<Object[]> getFirstDate(final String zykId) {
		StringBuilder builder = new StringBuilder("SELECT  time FROM zyk_log WHERE zykId = '"+zykId+"' ORDER BY time LIMIT 1 ; ");
		final String hql = builder.toString();
		return (List)getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				return session.createSQLQuery(hql).list();
			}
		});
	}
	
	@Override
	public List<Object[]> getLastDate(final String zykId) {
		StringBuilder builder = new StringBuilder("SELECT  time FROM zyk_log WHERE zykId = '"+zykId+"' ORDER BY time DESC LIMIT 1 ; ");
		final String hql = builder.toString();
		return (List)getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				return session.createSQLQuery(hql).list();
			}
		});
	}

	/**
	 * 查询资源素材应用情况
	 * 浏览资源;评价资源;分享资源;下载资源
	 */
	@Override
	public List<Object[]> findResourceActionCount(String zykId,	String beginDate, String endDate) {
		StringBuffer sqlBuilder = new StringBuffer(); 
		
		//“Action=浏览资源” 数量
		sqlBuilder.append(" SELECT  count(1) FROM zyk_log ");
		sqlBuilder.append(" WHERE zykId='" + zykId + "' ");
		sqlBuilder.append(" AND ObjectType= '资源素材模块' ");
		sqlBuilder.append(" AND Action= '浏览资源' ");
		if(beginDate != null && endDate != null){
			sqlBuilder.append(" AND Time > '" + beginDate +" 00:00:00' ");
			sqlBuilder.append(" AND Time < '"+endDate+" 23:59:59' ");
		}
		sqlBuilder.append(" UNION ALL ");
		//“Action=评价资源” 数量
		sqlBuilder.append(" SELECT  count(1) FROM zyk_log ");
		sqlBuilder.append(" WHERE zykId='" + zykId + "' ");
		sqlBuilder.append(" AND ObjectType= '资源素材模块' ");
		sqlBuilder.append(" AND Action= '评价资源' ");
		if(beginDate != null && endDate != null){
			sqlBuilder.append(" AND Time > '" + beginDate +" 00:00:00' ");
			sqlBuilder.append(" AND Time < '"+endDate+" 23:59:59' ");
		}
		sqlBuilder.append(" UNION ALL ");
		//“Action=分享资源” 数量
		sqlBuilder.append(" SELECT  count(1) FROM zyk_log ");
		sqlBuilder.append(" WHERE zykId='" + zykId + "' ");
		sqlBuilder.append(" AND ObjectType= '资源素材模块' ");
		sqlBuilder.append(" AND Action= '分享资源' ");
		if(beginDate != null && endDate != null){
			sqlBuilder.append(" AND Time > '" + beginDate +" 00:00:00' ");
			sqlBuilder.append(" AND Time < '"+endDate+" 23:59:59' ");
		}
		sqlBuilder.append(" UNION ALL ");
		//“Action=下载资源” 数量
		sqlBuilder.append(" SELECT  count(1) FROM zyk_log ");
		sqlBuilder.append(" WHERE zykId='" + zykId + "' ");
		sqlBuilder.append(" AND ObjectType= '资源素材模块' ");
		sqlBuilder.append(" AND Action= '下载资源' ");
		if(beginDate != null && endDate != null){
			sqlBuilder.append(" AND Time > '" + beginDate +" 00:00:00' ");
			sqlBuilder.append(" AND Time < '"+endDate+" 23:59:59' ");
		}
		
		final String querySql = sqlBuilder.toString();
		return (List)getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				return session.createSQLQuery(querySql).list();
			}
		});
	}

	/**
	 * 注册用户活动情况
	 */
	@Override
	public List<Object[]> findUserActiveCount(String zykId) {

		StringBuffer sqlBuilder = new StringBuffer(); 
		//有活动记录的注册用户数
		sqlBuilder.append(" SELECT COUNT(userId) FROM (SELECT DISTINCT zl.userId FROM zyk_log zl, zyk_user zu WHERE ");
		sqlBuilder.append(" zl.userId = zu.userId ");
		sqlBuilder.append(" AND zl.zykId='" + zykId + "' ");
		sqlBuilder.append(" ) t");
		sqlBuilder.append(" UNION ALL ");
		
		//有活动记录的注册用户占全部注册用户的比例
		sqlBuilder.append(" SELECT COUNT(userId)/(SELECT COUNT(userId) FROM zyk_user WHERE ");
		sqlBuilder.append(" zykId='" + zykId + "' ");
		sqlBuilder.append(" )FROM (SELECT DISTINCT zl.userId FROM zyk_log zl, zyk_user zu WHERE zl.userId = zu.userId  ");
		sqlBuilder.append(" AND zl.zykId='" + zykId + "' ");
		sqlBuilder.append(" ) t");
		sqlBuilder.append(" UNION ALL ");
		
		//有资源浏览和下载活动记录的注册用户占全部注册用户的比例
		sqlBuilder.append(" SELECT COUNT(userId)/(SELECT COUNT(userId) FROM zyk_user WHERE ");
		sqlBuilder.append(" zykId='" + zykId + "' ");
		sqlBuilder.append(" )FROM (SELECT DISTINCT zl.userId FROM zyk_log zl , zyk_user zu WHERE zl.userId  = zu.userId ");
		sqlBuilder.append(" AND zl.zykId='" + zykId + "' ");
		sqlBuilder.append(" AND zl.ObjectType = '资源素材模块' ");
		sqlBuilder.append(" ) t");
		sqlBuilder.append(" UNION ALL ");
		
		//有课程浏览及学习活动记录的注册用户占全部注册用户的比例
		sqlBuilder.append(" SELECT COUNT(userId)/(SELECT COUNT(userId) FROM zyk_user WHERE ");
		sqlBuilder.append(" zykId='" + zykId + "' ");
		sqlBuilder.append(" )FROM (SELECT DISTINCT zl.userId FROM zyk_log zl, zyk_user zu WHERE  zl.userId = zu.userId ");
		sqlBuilder.append(" AND zl.zykId='" + zykId + "' ");
		sqlBuilder.append(" AND zl.ObjectType <> '资源素材模块' ");
		sqlBuilder.append(" ) t");
		sqlBuilder.append(" UNION ALL ");
		
//		//人均活动天数（有活动记录的用户为分母）
//		sqlBuilder.append(" SELECT COUNT(userId)/(SELECT COUNT(userId) FROM zyk_user WHERE 1=1 ");
//		sqlBuilder.append(" AND zykId='" + zykId + "' ");
//		sqlBuilder.append(" )FROM (SELECT DISTINCT userId FROM zyk_log WHERE  1=1 ");
//		sqlBuilder.append(" AND zykId='" + zykId + "' ");
//		sqlBuilder.append(" ) t");
//		sqlBuilder.append(" UNION ALL ");
//		
		//企业用户活动总次数
		sqlBuilder.append(" SELECT  COUNT(*) FROM zyk_user t1 , zyk_log t2 WHERE ");
		sqlBuilder.append(" t1.zykId='" + zykId + "' ");
		sqlBuilder.append(" AND t2.zykId='" + zykId + "' ");
		sqlBuilder.append(" AND t1.userId=t2.userId ");
		sqlBuilder.append(" AND t1.role='企业用户' ");
		sqlBuilder.append(" UNION ALL ");

		//社会学习者活动总次数
		sqlBuilder.append(" SELECT  COUNT(*) FROM zyk_user t1 , zyk_log t2 WHERE  ");
		sqlBuilder.append(" t1.zykId='" + zykId + "' ");
		sqlBuilder.append(" AND t2.zykId='" + zykId + "' ");
		sqlBuilder.append(" AND t1.userId=t2.userId ");
		sqlBuilder.append(" AND t1.role='社会学习者' ");
//		sqlBuilder.append(" UNION ALL ");
//		
//		//企业用户累计学习天数
//		sqlBuilder.append(" SELECT COUNT(userId) FROM ( ");
//		sqlBuilder.append(" SELECT DISTINCT userId FROM zyk_log WHERE  1=1 AND userid IN ");
//		sqlBuilder.append(" (SELECT userId FROM zyk_user WHERE 1=1 AND role='企业用户' ");
//		sqlBuilder.append(" AND zykId='" + zykId + "'  ");
//		sqlBuilder.append(" ) ");
//		sqlBuilder.append(" ) t");
//		sqlBuilder.append(" UNION ALL ");
//
//		//社会学习者累计学习天数
//		sqlBuilder.append(" SELECT COUNT(userId) FROM ( ");
//		sqlBuilder.append(" SELECT DISTINCT userId FROM zyk_log WHERE  1=1 AND userid IN ");
//		sqlBuilder.append(" (SELECT userId FROM zyk_user WHERE 1=1 AND role='社会学习者' ");
//		sqlBuilder.append(" AND zykId='" + zykId + "'  ");
//		sqlBuilder.append(" ) ");
//		sqlBuilder.append(" ) t");
		
		final String querySql = sqlBuilder.toString();
		return (List)getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				return session.createSQLQuery(querySql).list();
			}
		});
	}

	/**
	 * 不同文件类型资源素材浏览和下载频次
	 */
	@Override
	public List<Object[]> resourceUsingAndDownloadCountList(String zykId) {
		StringBuffer sqlBuilder = new StringBuffer(); 
		
		sqlBuilder.append(" SELECT mediaType,COUNT(*) FROM zyk_resource t1,zyk_log t2 ");
		sqlBuilder.append(" WHERE t1.zykId='" + zykId + "' ");
		sqlBuilder.append(" AND t2.zykId='" + zykId + "' ");
		sqlBuilder.append(" AND t2.zykId='" + zykId + "' ");
		sqlBuilder.append(" AND t1.resourceId=t2.objectid ");
		sqlBuilder.append(" AND t2.zykId='" + zykId + "' ");
		sqlBuilder.append(" AND t2.ObjectType='资源素材模块' ");
		sqlBuilder.append(" AND (t2.action='下载资源' OR t2.action='浏览资源') ");
		sqlBuilder.append(" GROUP BY mediaType ");
		
		final String querySql = sqlBuilder.toString();
		return (List)getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				return session.createSQLQuery(querySql).list();
			}
		});
	}

	/**
	 * 根据logId, zykId 查询zyk_log中全部数据
	 * @param logId, zykId
	 * @return
	 */
	@Override
	public ZykLogEntity findLogByLogIdAndZykId(final String logId, final String zykId){
		return getHibernateTemplate().execute(new HibernateCallback<ZykLogEntity>() {
			public ZykLogEntity doInHibernate(Session session) throws HibernateException, SQLException {
				List<ZykLogEntity> list = session.createQuery("from ZykLogEntity where logId=? and zykId =? ")
						.setParameter(0, logId).setParameter(1, zykId).list();
				return (ZykLogEntity) (list == null || list.size() == 0 ? null : list.get(0));
			}
		});
	}
}
