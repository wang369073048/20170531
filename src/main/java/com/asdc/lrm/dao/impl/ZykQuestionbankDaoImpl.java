package com.asdc.lrm.dao.impl;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.asdc.lrm.dao.ZykQuestionbankDao;
import com.asdc.lrm.dao.common.HibernateDao;
import com.asdc.lrm.entity.ZykQuestionbankEntity;

@SuppressWarnings("unchecked")
public class ZykQuestionbankDaoImpl extends HibernateDao<ZykQuestionbankEntity, String> implements ZykQuestionbankDao {

	public List<ZykQuestionbankEntity> getZykQuestionbankByZykId(final String zykId) {
		return getHibernateTemplate().execute(new HibernateCallback<List<ZykQuestionbankEntity>>() {
			public List<ZykQuestionbankEntity> doInHibernate(Session session) throws HibernateException, SQLException {
				@SuppressWarnings("rawtypes")
				List list = session.createQuery("from ZykQuestionbankEntity where zykId = ?").setParameter(0, zykId).list();
				return list;
			}
		});
	}

	public Map<String, Object> getCitedQuesCountMap(String zykId) {
		StringBuilder sql = new StringBuilder("select title,CitedQuesNum from zyk_questionbank where zykId='");
		sql.append(zykId).append("'");
		return hibernateTemplateExe(sql.toString());
	}

	public Map<String, Object> getQuesUsingCountMap(String zykId) {
		StringBuilder sql = new StringBuilder("select title,QuesUsingNum from zyk_questionbank where zykId='");
		sql.append(zykId).append("'");
		return hibernateTemplateExe(sql.toString());
	}
	
	public Map<String, Object> hibernateTemplateExe(final String sql){
		return getHibernateTemplate().execute(new HibernateCallback<Map<String, Object>>() {

			public Map<String, Object> doInHibernate(Session session) throws HibernateException,
					SQLException {
				@SuppressWarnings("rawtypes")
				List list = session.createSQLQuery(sql.toString()).list();
				Map<String,Object> map = new LinkedHashMap<String, Object>();
				if(list!=null && list.size() > 0){
					for(int i = 0 ; i < list.size();i++){
						Object[] obj = (Object[]) list.get(i);
						if(obj[0] == null || obj[1] == null) continue;
						map.put(obj[0].toString(), obj[1]);
					}
				}
				return map;
			}
		});
	}

	public List<Object[]> getZykQuestionbankCount(String zykId) {
		StringBuffer sqlBuilder = new StringBuffer("SELECT COUNT(1) FROM zyk_questionbank WHERE ZYKID='"+zykId+"'");
		final String sql = sqlBuilder.toString();
		return getHibernateTemplate().execute(new HibernateCallback<List<Object[]>>() {
			public List<Object[]> doInHibernate(Session session) throws HibernateException, SQLException {
				return session.createSQLQuery(sql).list();
			}
		});
	}

	/**
	 * 根据qbId,zykId 查询zyk_questionbank中数据
	 * @param qbId,zykId 
	 * @return
	 */
	@Override
	public 	ZykQuestionbankEntity findQuestionByQbIdAndZykId(final String qbId, final String zykId){
		return getHibernateTemplate().execute(new HibernateCallback<ZykQuestionbankEntity>() {
			public ZykQuestionbankEntity doInHibernate(Session session) throws HibernateException, SQLException {
				List<ZykQuestionbankEntity> list = session.createQuery("from ZykQuestionbankEntity where qbId=? and zykId =? ")
						.setParameter(0, qbId).setParameter(1, zykId).list();
				return (ZykQuestionbankEntity) (list == null || list.size() == 0 ? null : list.get(0));
			}
		});
	}
}
