package com.asdc.lrm.dao;

import java.util.List;

import com.asdc.lrm.dao.common.Dao;
import com.asdc.lrm.entity.ZykDatachangeLogEntity;

public interface ZykDatachangeLogDao extends Dao<ZykDatachangeLogEntity, String>{
	
	/**
	 * 根据logId, zykId 查询zyk_datachange_log中全部数据
	 * @param logId, zykId
	 * @return
	 */
	ZykDatachangeLogEntity findDataLogByLogIdAndZykId(String logId, String zykId);
	
	/***
	 * 根据资源Id获取实体类
	 * @param zykId
	 * @return
	 */
	List<ZykDatachangeLogEntity> getZykDatachangeLogByZykId(String zykId);
	
	/**
	 * 根据zykId 查询zyk_datachange_log中全部数据
	 * @param zykId
	 * @return
	 */
	List<ZykDatachangeLogEntity> findDatachangeLogByZykId(String zykId);
}
