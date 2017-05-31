package com.asdc.lrm.dao;

import java.util.List;
import java.util.Map;

import com.asdc.lrm.dao.common.Dao;
import com.asdc.lrm.entity.ZykQuestionbankEntity;

public interface ZykQuestionbankDao extends Dao<ZykQuestionbankEntity, String>{

	/**
	 * 根据资源库ID查询
	 * @param zykId
	 * @return
	 */
	List<ZykQuestionbankEntity> getZykQuestionbankByZykId(String zykId);
	
	/**
	 * 查询题库题目数量
	 * @param zykId
	 * @return
	 */
	List<Object[]> getZykQuestionbankCount(String zykId);

	/***
	 * 题库题目引用数
	 * @param zykId
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	Map<String,Object> getCitedQuesCountMap(String zykId);
	
	/***
	 * 题库题目使用总次数
	 * @param zykId
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	Map<String,Object> getQuesUsingCountMap(String zykId);
	
	/**
	 * 根据qbId,zykId 查询zyk_questionbank中数据
	 * @param qbId,zykId 
	 * @return
	 */
	ZykQuestionbankEntity findQuestionByQbIdAndZykId(String qbId, String zykId);
}
