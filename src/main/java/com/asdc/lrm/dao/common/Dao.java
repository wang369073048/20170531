package com.asdc.lrm.dao.common;

import java.io.Serializable;

public interface Dao<T , PK extends Serializable> {

	public T get(PK id);
	
	public void save(T entity);
	
	public void update(T entity);
	
	public void delete(T entity);
	
	public void merge(T entity);
}
