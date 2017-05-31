package com.asdc.lrm.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.asdc.lrm.dao.ZykDao;
import com.asdc.lrm.dao.common.HibernateDao;
import com.asdc.lrm.entity.ZykEntity;

@SuppressWarnings("unchecked")
public class ZykDaoImpl extends HibernateDao<ZykEntity, String> implements ZykDao {

	public List<ZykEntity> getZykEntityByZykId(final String zykId){
		StringBuilder builder = new StringBuilder("from ZykEntity where zykId = ? " +
				"ORDER BY CONVERT(fullname USING gbk) COLLATE gbk_chinese_ci ASC");
		final String hql = builder.toString();
		
		return getHibernateTemplate().execute(new HibernateCallback<List>() {
			public List<ZykEntity> doInHibernate(Session session) throws HibernateException, SQLException {
				return session.createQuery(hql).setParameter(0, zykId).list();
			}
		});
	}
	
	public ZykEntity getZykEntityByFullNameAndIns(final ZykEntity entity) {
		if(entity == null) 
			return null;
		
		if(entity.getFullname()==null || entity.getFullname().isEmpty()) 
			return null;
		
		if(entity.getInstituteInCharge()==null || entity.getInstituteInCharge().isEmpty()) 
			return null;
		
		StringBuilder builder = new StringBuilder("from ZykEntity where fullname = ? and instituteInCharge=? ");
		final String hql = builder.toString();
		
		return getHibernateTemplate().execute(new HibernateCallback<ZykEntity>() {
			public ZykEntity doInHibernate(Session session) throws HibernateException, SQLException {
				List list = session.createQuery(hql).setParameter(0, entity.getFullname()).
				setParameter(1, entity.getInstituteInCharge()).list();
				return (ZykEntity) (list==null || list.size()==0 ? null : list.get(0));
			}
		});
	}

	public List<ZykEntity> getZykEntityList(final String fullName, String ins) {
		StringBuilder builder = new StringBuilder("from ZykEntity where fullname=? and instituteInCharge in (");
		String[] insArr = ins.split(",");
		
		for(int i = 0; i<insArr.length;i++){
			builder.append("'").append(insArr[i]).append("'");
			if((i+1)<insArr.length){
				builder.append(",");
			}
		}
		builder.append(")");
		final String hql = builder.toString();
		
		return getHibernateTemplate().execute(new HibernateCallback<List>() {
			public List doInHibernate(Session session) throws HibernateException, SQLException {
				return session.createQuery(hql).setParameter(0, fullName).list();
			}
		});
	}
	
	public List<ZykEntity> findAllOrderByFullname(){
		StringBuilder builder = new StringBuilder("from ZykEntity " +
				"ORDER BY CONVERT(fullname USING gbk) COLLATE gbk_chinese_ci ASC");
		final String hql = builder.toString();
		
		return getHibernateTemplate().execute(new HibernateCallback<List>() {
			public List<ZykEntity> doInHibernate(Session session) throws HibernateException, SQLException {
				return session.createQuery(hql).list();
			}
		});
	}
	
	public List<ZykEntity> findByStatusOrderByFullname(String status, ZykEntity entity){
		StringBuilder builder = new StringBuilder("from ZykEntity where status='"+status+"' ");
		if(entity != null){
			if(entity.getFullname() != null){
				builder.append(" and fullname='"+entity.getFullname()+"' ");
			}
			if(entity.getPersonInCharge() != null){
				builder.append(" and personInCharge='"+entity.getPersonInCharge()+"' ");
			}
			if(entity.getInstituteInCharge() != null){
				builder.append(" and instituteInCharge='"+entity.getInstituteInCharge()+"' ");
			}
			if(entity.getZykNum() != null){
				builder.append(" and zykNum='"+entity.getZykNum()+"' ");
			}
		}
		builder.append("ORDER BY CONVERT(fullname USING gbk) COLLATE gbk_chinese_ci ASC");
		final String hql = builder.toString();
		
		return getHibernateTemplate().execute(new HibernateCallback<List>() {
			public List<ZykEntity> doInHibernate(Session session) throws HibernateException, SQLException {
				return session.createQuery(hql).list();
			}
		});
	}
	
	public List<ZykEntity> findByStatusOrderByInstituteInCharge(final String status){
		StringBuilder builder = new StringBuilder("from ZykEntity where status=? " +
				"ORDER BY CONVERT(instituteInCharge USING gbk) COLLATE gbk_chinese_ci ASC");
		final String hql = builder.toString();
		
		return getHibernateTemplate().execute(new HibernateCallback<List>() {
			public List<ZykEntity> doInHibernate(Session session) throws HibernateException, SQLException {
				return session.createQuery(hql).setParameter(0, status).list();
			}
		});
	}
	
	public List<ZykEntity> findByStatusOrderByCity(final String status){
		StringBuilder builder = new StringBuilder("select z.id,z.zykId,z.Fullname,z.instituteInCharge,c.name from zyk_zyk z " +
				"INNER JOIN zyk_city c ON c.cityId = z.cityId " +
				"where status=? " +
				"ORDER BY CONVERT(c.name USING gbk) COLLATE gbk_chinese_ci ASC");
		final String sql = builder.toString();
		
		return getHibernateTemplate().execute(new HibernateCallback<List>() {
			public List<ZykEntity> doInHibernate(Session session) throws HibernateException, SQLException {
				List list = session.createSQLQuery(sql).setParameter(0, status).list();
				List<ZykEntity> zykList = new ArrayList<ZykEntity>();
				
				for(int i=0; i<list.size(); i++){
					Object[] obj = (Object[])list.get(i);
					ZykEntity entity = new ZykEntity();
					entity.setId(obj[0].toString());
					entity.setZykId(obj[1].toString());
					entity.setFullname(obj[2].toString());
					entity.setInstituteInCharge(obj[3].toString());
					entity.setCity(obj[4].toString());
					zykList.add(entity);
				}
				
				return zykList;
			}
		});
	}

	public int findZykTotalCount() {
		final String hql = "select count(t.id) from ZykEntity t ";
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
	
	public int findZykDepartmentTotalCount() {
		final String hql = "select count(distinct t.instituteInCharge) from ZykEntity t ";
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
	 * 查询资源库数据
	 * 根据cityId分组
	 */
	@Override
	public List<Object[]> getZykGroupByProvince() {
		final String sql =
			"SELECT provinceName, COUNT(1) FROM zyk_zyk z ," +
				" ( SELECT c1.cityId,  IF(c2.`name` is null, c1.`name`, c2.`name`) AS provinceName " +
				" FROM zyk_city c1 LEFT JOIN zyk_city c2 ON c1.parentId = c2.cityId ) t " +
			" WHERE z.cityId =  t.cityId " +
			" GROUP BY provinceName " +
			" ORDER BY COUNT(1) DESC ";
		return getHibernateTemplate().execute(new HibernateCallback<List<Object[]>>() {
			public List<Object[]> doInHibernate(Session session) throws HibernateException, SQLException {
				return session.createSQLQuery(sql).list();
			}
		});
	}
	
	@Override
	public List<ZykEntity> findAllZykEntity(){
		StringBuilder builder = new StringBuilder("from ZykEntity GROUP BY zykId");
		final String hql = builder.toString();
		
		return getHibernateTemplate().execute(new HibernateCallback<List>() {
			public List<ZykEntity> doInHibernate(Session session) throws HibernateException, SQLException {
				return session.createQuery(hql).list();
			}
		});
	}

	@Override
	public List<Map<String, Object>> getZykSummary(String zykId) {

		final StringBuilder sql = new StringBuilder();
		sql.append(" select z.fullname,z.instituteInCharge,r.countresource,c.countcourse, ");
		sql.append(" t.countuser, l.countlog,crr.countmodules,q.countquestion, q.countsubquestion, q.countobjquestion, q.countcitiedquestion, ");
		sql.append(" q.countusingquestion from ");
		sql.append(" (select zykid,fullname,instituteInCharge  ");	
		sql.append(" from zyk_zyk   ");	
		
		sql.append(" where zykId = '"+zykId+"'");
		sql.append(" ) z ");
		
		//用户数量
		sql.append("  left join ");
		sql.append(" (select u.zykId, count(u.userid) as countuser from zyk_user u  " );
		sql.append(" where u.zykid = '"+zykId+"' ");
		sql.append(" GROUP BY u.zykId ");
		sql.append("  ) t ");
		sql.append(" on z.zykid = t.zykid  ");  
		
		//资源素材数量
		sql.append("  left join ");
		sql.append(" (select r.zykid,count(r.resourceid) as countresource from zyk_resource r " );
		sql.append(" where r.zykid = '"+zykId+"' ");
		sql.append(" GROUP BY r.zykId ");
		sql.append("  ) r ");
		sql.append(" on z.zykid = r.zykid  ");  
		
		//课程数量
		sql.append("  left join ");
		sql.append(" (select c.zykid,count(c.courseid) as countcourse from  zyk_course c " );
		sql.append(" where c.zykid = '"+zykId+"' ");
		sql.append(" GROUP BY c.zykId ");
		sql.append("  ) c ");
		sql.append(" on z.zykid = c.zykid  ");  
		
		//日志数量
		sql.append("  left join ");
		sql.append(" (select l.zykid,count(l.logId) as countlog from zyk_log l " );
		sql.append(" where l.zykid = '"+zykId+"' ");
		sql.append(" GROUP BY l.zykId ");
		sql.append("  ) l ");
		sql.append(" on z.zykid = l.zykid  ");  
		
		//应用模块数量
		sql.append("  left join ");
		sql.append(" (select r.zykid,count(r.CourseModuleId) as countmodules from  zyk_course c , zyk_course_resource_relation r  " );
		sql.append(" where  c.courseLevel = '课程' and r.Courseid = c.courseId and c.zykId = r.zykId and c.zykId = '"+zykId+"' ");
		sql.append(" GROUP BY c.zykId ");
		sql.append("  ) crr ");
		sql.append(" on z.zykid = crr.zykid  ");  
		
		
		sql.append("  left join ");
		sql.append(" (select q.zykid, SUM(q.QuestionNum) as countquestion , SUM(q.SubQuesNum) as countsubquestion, ");
		sql.append(" SUM(q.ObjQuesNum) as countobjquestion, SUM(q.CitedQuesNum) as countcitiedquestion,");
		sql.append(" SUM(q.QuesUsingNum) as countusingquestion  from  zyk_questionbank q ");
		sql.append(" where q.zykid = '"+zykId+"' ");
		sql.append(" GROUP BY q.zykId ");
		sql.append("  ) q ");
		sql.append(" on z.zykid = q.zykid  ");  
		
		return getHibernateTemplate().execute(new HibernateCallback<List<Map<String,Object>>>() {

			public List<Map<String,Object>> doInHibernate(Session session) throws HibernateException, SQLException {
				List list = session.createSQLQuery(sql.toString()).list();
				List<Map<String,Object>> resultList = new ArrayList<Map<String,Object>>();
				if(list!=null && list.size() > 0){
					Map<String,Object> map;
					for(int i = 0; i < list.size(); i++){
						map = new HashMap<String,Object>();
						Object[] obj= (Object[]) list.get(i);
						map.put("名称", obj[0]);
						map.put("主持单位",obj[1]);
						map.put("资源素材数量",obj[2] == null ? "0" : obj[2]);
						map.put("课程数量",obj[3] == null ? "0" : obj[3]);
						map.put("用户数量",obj[4] == null ? "0" : obj[4]);
						map.put("日志数量",obj[5] == null ? "0" : obj[5]);
						map.put("应用模块数量",obj[6] == null ? "0" : obj[6]);
						map.put("题库数量",obj[7] == null ? "0" : obj[7]);
						map.put("主观题目数量",obj[8] == null ? "0" : obj[8]);
						map.put("客观题目数量",obj[9] == null ? "0" : obj[9]);
						map.put("题目引用数",obj[10] == null ? "0" : obj[10]);
						map.put("题目使用数",obj[11] == null ? "0" : obj[11]);
						resultList.add(map);
					}
				}
				return resultList;
			}
		});
	}
	
	@Override
	public List<ZykEntity> findByStatusOrderByZyknum(String status, ZykEntity entity){
		StringBuilder builder = new StringBuilder("from ZykEntity where status='"+status+"' ");
		if(entity != null){
			if(entity.getFullname() != null){
				builder.append(" and fullname='"+entity.getFullname()+"' ");
			}
			if(entity.getPersonInCharge() != null){
				builder.append(" and personInCharge='"+entity.getPersonInCharge()+"' ");
			}
			if(entity.getInstituteInCharge() != null){
				builder.append(" and instituteInCharge='"+entity.getInstituteInCharge()+"' ");
			}
			if(entity.getZykNum() != null){
				builder.append(" and zykNum='"+entity.getZykNum()+"' ");
			}
		}
		builder.append("ORDER BY zykNum ASC");
		final String hql = builder.toString();
		
		return getHibernateTemplate().execute(new HibernateCallback<List>() {
			public List<ZykEntity> doInHibernate(Session session) throws HibernateException, SQLException {
				return session.createQuery(hql).list();
			}
		});
	}
	
}