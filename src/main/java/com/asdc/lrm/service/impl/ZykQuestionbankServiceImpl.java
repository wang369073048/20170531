package com.asdc.lrm.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.asdc.lrm.dao.ZykDao;
import com.asdc.lrm.dao.ZykQuestionbankDao;
import com.asdc.lrm.entity.ZykEntity;
import com.asdc.lrm.entity.ZykQuestionbankEntity;
import com.asdc.lrm.jersey.ServiceResponse;
import com.asdc.lrm.service.ZykQuestionbankService;
import com.asdc.lrm.util.UtilString;

public class ZykQuestionbankServiceImpl extends BaseServiceImpl implements ZykQuestionbankService {

	private static final Log log = LogFactory.getLog(ZykQuestionbankServiceImpl.class);
	
	private ZykDao zykDao;
	public void setZykDao(ZykDao zykDao) {
		this.zykDao = zykDao;
	}

	private ZykQuestionbankDao zykQuestionbankDao;
	public void setZykQuestionbankDao(ZykQuestionbankDao zykQuestionbankDao) {
		this.zykQuestionbankDao = zykQuestionbankDao;
	}
	
	public ServiceResponse saveZykQuestionbank(String source,String data, String fullName, String instituteInCharge) {
		log.info(" upload date of ZYK_QUESTIONBANK");
		try{
			/** 根据专业名称和主持单位查询资源库信息，可能是多个 **/
			List<ZykEntity> zykEntityList = zykDao.getZykEntityList(fullName,instituteInCharge);
			
			StringBuilder builder = new StringBuilder();
			
			/** 如果来源是资源库平台，资源库不存在则不再继续，直接返回错误信息 **/
			/** 如果来源是申报平台，资源库不存在则先创建资源库信息，再保存用户数据 **/
			if(zykEntityList.size() == 0){
				return new ServiceResponse(400,"failed","资源库不存在!");
				
			}else{
				ZykEntity entity = zykEntityList.get(0);
				builder.append(entity.getZykId());
			}
			
			List<ZykQuestionbankEntity> questionList = new ArrayList<ZykQuestionbankEntity>();
			JSONArray jsonArray = JSONArray.fromObject(data);
			for(int i = 0 ; i < jsonArray.size();i++){
				JSONObject json = jsonArray.getJSONObject(i);
				if(json.get("qbid") == null){
					return new ServiceResponse(400,"failed","qbid有空值,上传数据失败!");
				}
				
				ZykQuestionbankEntity questionEntity = new ZykQuestionbankEntity();
				questionEntity.setZykId(builder.toString());
				questionEntity.setQbId(UtilString.getStr(json.get("qbid")));
				questionEntity.setTitle(UtilString.getStr(json.get("title")));
				
				if(json.get("questionnum") != null && json.get("questionnum") != ""){
					String questionNum = json.get("questionnum").toString();
					questionEntity.setQuestionNum(Integer.parseInt(questionNum));
				}
				
				if(json.get("objquesnum") != null && json.get("objquesnum") != ""){
					String objquesNum = json.get("objquesnum").toString();
					questionEntity.setObjQuesNum(Integer.parseInt(objquesNum));
				}
				
				if(json.get("subquesnum") != null && json.get("subquesnum") != ""){
					String subquesNum = json.get("subquesnum").toString();
					questionEntity.setSubQuesNum(Integer.parseInt(subquesNum));
				}
				
				if(json.get("citedquesnum") != null && json.get("citedquesnum") != ""){
					String citedquesNum = json.get("citedquesnum").toString();
					questionEntity.setCitedQuesNum(Integer.parseInt(citedquesNum));
				}
				
				if(json.get("quesusingnum") != null && json.get("quesusingnum") != ""){
					String quesUsingNum = json.get("quesusingnum").toString();
					questionEntity.setQuesUsingNum(Integer.parseInt(quesUsingNum));
				}
				
				questionList.add(questionEntity);
			}
			
			ZykQuestionbankEntity existQuestion = null;
			for(ZykQuestionbankEntity question : questionList){
				existQuestion = zykQuestionbankDao.findQuestionByQbIdAndZykId(question.getQbId(), question.getZykId());
				if(existQuestion != null){
					zykQuestionbankDao.merge(existQuestion);
				}
				zykQuestionbankDao.save(question);
			}
			log.info(" upload data of ZYK_QUESTIONBANK success! save data : " + questionList.size());
			return new ServiceResponse(200,"success","题库数据保存成功! 保存数据:"+questionList.size()+"条.");
		}catch (RuntimeException e) {
			log.error(" Exception when upload data of ZYK_QUESTIONBANK : " +e);
			e.printStackTrace();
			return new ServiceResponse(400,"failed","题库数据保存失败!");
		}
	}

}
