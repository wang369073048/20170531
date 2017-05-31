package com.asdc.lrm.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.asdc.lrm.dao.ZykDatachangeLogDao;
import com.asdc.lrm.dao.common.HibernateDao;
import com.asdc.lrm.entity.ZykDatachangeLogEntity;

public class ZykDatachangeLogDaoImpl extends HibernateDao<ZykDatachangeLogEntity, String> implements ZykDatachangeLogDao {

	/**
	 * 根据logId, zykId 查询zyk_datachange_log中全部数据
	 * @param logId, zykId
	 * @return
	 */
	@Override
	public ZykDatachangeLogEntity findDataLogByLogIdAndZykId(final String logId, final String zykId) {
		return getHibernateTemplate().execute(new HibernateCallback<ZykDatachangeLogEntity>() {
			public ZykDatachangeLogEntity doInHibernate(Session session) throws HibernateException, SQLException {
				@SuppressWarnings("unchecked")
				List<ZykDatachangeLogEntity> list = session.createQuery("from ZykDatachangeLogEntity where logId=? and zykId =? ")
						.setParameter(0, logId).setParameter(1, zykId).list();
				return (ZykDatachangeLogEntity) (list == null || list.size() == 0 ? null : list.get(0));
			}
		});
	}
	
	@Override
	public List<ZykDatachangeLogEntity> getZykDatachangeLogByZykId(final String zykId) {
		return (List<ZykDatachangeLogEntity>) getHibernateTemplate().execute(new HibernateCallback<List<ZykDatachangeLogEntity>>() {
			@SuppressWarnings("unchecked")
			public List<ZykDatachangeLogEntity> doInHibernate(Session session) throws HibernateException, SQLException {
				List<ZykDatachangeLogEntity> list = session.createQuery(" from ZykDatachangeLogEntity where zykId=? ")
						.setParameter(0, zykId).list();
				return list;
			}
		});
	}
	
	/**
	 * 根据zykId 查询zyk_datachange_log中全部数据
	 * @param zykId
	 * @return
	 */
	@Override
	public List<ZykDatachangeLogEntity> findDatachangeLogByZykId(final String zykId) {
		return getHibernateTemplate().execute(new HibernateCallback<List<ZykDatachangeLogEntity>>() {
			@SuppressWarnings("unchecked")
			public List<ZykDatachangeLogEntity> doInHibernate(Session session) throws HibernateException, SQLException {
				List<ZykDatachangeLogEntity> list = session.createQuery("from ZykDatachangeLogEntity where zykId=?")
						.setParameter(0, zykId).list();
				return list;
			}
		});
	}
}