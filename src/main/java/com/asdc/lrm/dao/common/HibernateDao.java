package com.asdc.lrm.dao.common;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

@SuppressWarnings("unchecked")
public abstract class HibernateDao<T , PK extends Serializable> extends HibernateDaoSupport implements Dao<T , PK> {

	public T get(PK id){
		return (T)this.getHibernateTemplate().get(getGenericType(0), id);
	}
	
	public void save(T entity) {
		this.getHibernateTemplate().save(entity);
	}

	public void update(T entity) {
		this.getHibernateTemplate().update(entity);
	}
	public void merge(T entity) {
		this.getHibernateTemplate().merge(entity);
	}

	public void delete(T entity) {
		this.getHibernateTemplate().delete(entity);
	}
	
	protected Class getGenericType(int index) {
		Type genType = getClass().getGenericSuperclass();
		if (!(genType instanceof ParameterizedType)) {
			return Object.class;
		}
		Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
		if (index >= params.length || index < 0) {
			throw new RuntimeException("Index outof bounds");
		}
		if (!(params[index] instanceof Class)) {
			return Object.class;
		}
		return (Class) params[index];
	}
}