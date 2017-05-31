package com.asdc.lrm.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.asdc.lrm.dao.ZykCityDao;
import com.asdc.lrm.dao.common.HibernateDao;
import com.asdc.lrm.entity.ZykCityEntity;

@SuppressWarnings("unchecked")
public class ZykCityDaoImpl extends HibernateDao<ZykCityEntity, String> implements ZykCityDao {

	public List<ZykCityEntity> getByType(int type){
		StringBuilder builder = new StringBuilder("from ZykCityEntity where 1=1 ");
		if(type != 0){
			builder.append("and type="+type+" ");
		}
		builder.append("ORDER BY CONVERT(name USING gbk) COLLATE gbk_chinese_ci ASC");
		final String hql = builder.toString();
		
		return (List<ZykCityEntity>)getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(hql);
				return query.list();
			}
		});
	}
	
	public ZykCityEntity getCityByName(final String cityName){
		 return  getHibernateTemplate().execute(new HibernateCallback<ZykCityEntity>() {
			 public ZykCityEntity doInHibernate(Session session) throws HibernateException, SQLException {
				 List list = session.createQuery("from ZykCityEntity where name=?").setParameter(0, cityName).list();
				 return (ZykCityEntity) (list == null || list.size() == 0 ? null : list.get(0));
			 }
		 });
	}
	
	public void saveOther(){
		try{
			ZykCityEntity entity = new ZykCityEntity();
			entity.setName("其他");
			entity.setParentId("0");
			entity.setType(1);
			this.getHibernateTemplate().save(entity);
			
		}catch (RuntimeException e) {
			e.printStackTrace();
		}
	}
}
