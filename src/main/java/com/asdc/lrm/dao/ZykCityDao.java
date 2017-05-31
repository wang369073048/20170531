package com.asdc.lrm.dao;

import java.util.List;

import com.asdc.lrm.dao.common.Dao;
import com.asdc.lrm.entity.ZykCityEntity;

public interface ZykCityDao extends Dao<ZykCityEntity, String>{

	/**
	 * 根据类型查询
	 * @param type
	 * @return
	 */
	List<ZykCityEntity> getByType(int type);
	
	/**
	 * 根据城市名称查询
	 * @param cityName
	 * @return
	 */
	ZykCityEntity getCityByName(String cityName);
	
	/**
	 * 创建名为“其他”的省份
	 */
	void saveOther();

}
