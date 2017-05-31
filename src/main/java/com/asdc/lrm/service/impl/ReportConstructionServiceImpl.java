package com.asdc.lrm.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.asdc.lrm.dao.ZykCourseDao;
import com.asdc.lrm.dao.ZykCourseRresourceRelationDao;
import com.asdc.lrm.dao.ZykQuestionbankDao;
import com.asdc.lrm.dao.ZykResourceDao;
import com.asdc.lrm.dao.ZykUserDao;
import com.asdc.lrm.entity.ZykCourseRresourceRelationEntity;
import com.asdc.lrm.entity.ZykResourceEntity;
import com.asdc.lrm.service.ReportConstructionService;

public class ReportConstructionServiceImpl implements ReportConstructionService {

	private ZykUserDao zykUserDao;
	private ZykResourceDao zykResourceDao;
	private ZykCourseDao zykCourseDao;
	private ZykCourseRresourceRelationDao zykCourseRresourceRelationDao;
	private ZykQuestionbankDao zykQuestionbankDao;
	
	public void setZykQuestionbankDao(ZykQuestionbankDao zykQuestionbankDao) {
		this.zykQuestionbankDao = zykQuestionbankDao;
	}

	public void setZykCourseRresourceRelationDao(ZykCourseRresourceRelationDao zykCourseRresourceRelationDao) {
		this.zykCourseRresourceRelationDao = zykCourseRresourceRelationDao;
	}

	public void setZykCourseDao(ZykCourseDao zykCourseDao) {
		this.zykCourseDao = zykCourseDao;
	}
	
	public void setZykResourceDao(ZykResourceDao zykResourceDao) {
		this.zykResourceDao = zykResourceDao;
	}

	public void setZykUserDao(ZykUserDao zykUserDao) {
		this.zykUserDao = zykUserDao;
	}

	/**
	 * 用户总数
	 */
	public List<String> reportUserCount(String zykId, String beginDate, String endDate) {
		List<Object[]> userList = zykUserDao.findUsers(zykId, beginDate, endDate);
		List<String> userCount = new ArrayList<String>();
		
		StringBuffer dataStr = new StringBuffer("[");
		StringBuffer valueStr = new StringBuffer("[");
		int maxCount = 0;
		int totalCount = 0;
		for(int i = 0; i < userList.size(); i++){
			dataStr.append("'"+userList.get(i)[0]+"'");
			valueStr.append("{value:"+userList.get(i)[1]+", name:'"+userList.get(i)[0]+"'}");
			
			int count = Integer.parseInt(userList.get(i)[1]+"");
			totalCount += count;
			if(count > maxCount){
				maxCount = count;
			}
			
			if((i+1) < userList.size()){
				dataStr.append(",");
				valueStr.append(",");
			}
		}
		dataStr.append("]");
		valueStr.append("]");
		
		userCount.add(dataStr.toString());
		userCount.add(valueStr.toString());
		userCount.add(maxCount+"");
		userCount.add(totalCount+"");
		
		return userCount;
	}

	/**
	 * 用户来自的院校数
	 */
	public List<String> reportInstituteCount(String zykId, String beginDate, String endDate) {
		
		List<String> instituteList = new ArrayList<String>();
		List<Object[]> list = zykUserDao.findUserByInstitute(zykId, beginDate, endDate);
		
		int totalCount = list.size();
		
		StringBuffer dataStr = new StringBuffer("[");
		StringBuffer valueStr = new StringBuffer("[");
		
		for(int i = 0; i < list.size(); i++){
			dataStr.append("'");
			dataStr.append(list.get(i)[0]);
			dataStr.append("'");
			
//			int count = Integer.parseInt(list.get(i)[1]+"");
//			totalCount += count;
			
			valueStr.append("'");
			valueStr.append(list.get(i)[1]);
			valueStr.append("'");
			
			if((i+1) < list.size()){
				dataStr.append(",");
				valueStr.append(",");
			}
			
		}
		dataStr.append("]");
		valueStr.append("]");
		
		instituteList.add(dataStr.toString());
		instituteList.add(valueStr.toString());
		instituteList.add(totalCount+"");
		return instituteList;
	}

	/**
	 * 用户来自的省级行政区数
	 */
	public List<String> reportProvinceCount(String zykId, String beginDate,
			String endDate) {
		List<String> proviceltList = new ArrayList<String>();
		List<Object[]> list = zykUserDao.findUserByProvince(zykId, beginDate, endDate);
		int totalCount = list.size();
		
		StringBuffer dataStr = new StringBuffer("[");
		StringBuffer valueStr = new StringBuffer("[");
		
		for(int i = 0; i < list.size(); i++){
			dataStr.append("'");
			dataStr.append(list.get(i)[0]);
			dataStr.append("'");
			
//			int count = Integer.parseInt(list.get(i)[1]+"");
//			totalCount += count;
			
			valueStr.append("'");
			valueStr.append(list.get(i)[1]);
			valueStr.append("'");
			
			if((i+1) < list.size()){
				dataStr.append(",");
				valueStr.append(",");
			}
			
		}
		dataStr.append("]");
		valueStr.append("]");
		
		proviceltList.add(dataStr.toString());
		proviceltList.add(valueStr.toString());
		proviceltList.add(totalCount+"");
		return proviceltList;
	}

	/**
	 * 资源素材总数
	 */
	public List<String> reportResourceCount(String zykId, String beginDate,
			String endDate) {
		List<Object[]> resourceList = zykResourceDao.findResourceMediaType(zykId, beginDate, endDate);
		List<String> resourceCount = new ArrayList<String>();
		
		StringBuffer dataStr = new StringBuffer("[");
		StringBuffer valueStr = new StringBuffer("[");
		int maxCount = 0;
		int totalCount = 0;
		for(int i = 0; i < resourceList.size(); i++){
			dataStr.append("'"+resourceList.get(i)[0]+"'");
			valueStr.append("{value:"+resourceList.get(i)[1]+", name:'"+resourceList.get(i)[0]+"'}");
			
			int count = Integer.parseInt(resourceList.get(i)[1]+"");
			totalCount += count;
			if(count > maxCount){
				maxCount = count;
			}
			
			if((i+1) < resourceList.size()){
				dataStr.append(",");
				valueStr.append(",");
			}
		}
		dataStr.append("]");
		valueStr.append("]");
		
		resourceCount.add(dataStr.toString());
		resourceCount.add(valueStr.toString());
		resourceCount.add(maxCount+"");
		resourceCount.add(totalCount+"");
		
		return resourceCount;
	}

	/**
	 * 不同教学应用的资源素材分布
	 */
	public List<String> reportInstructionCount(String zykId, String beginDate, String endDate) {
		
		List<String> instructionList = new ArrayList<String>();
		List<Object[]> initList = zykResourceDao.findResourceInstruction(zykId, beginDate, endDate);
		List<Object[]> top3_List = new ArrayList<Object[]>();
		
		//initList中的数据已经根据count排序,前三位直接加入top3_List 后面的跟第三位比较
		for(int i = 0; i < initList.size(); i++){
			if(i < 3){
				top3_List.add(initList.get(i));
			}else{
				if(initList.get(i)[1].equals(initList.get(i-1)[1])){
					top3_List.add(initList.get(i));
				}else{
					break;
				}
			}
		}
		Collections.reverse(top3_List);
		
		StringBuffer dataStr = new StringBuffer("[");
		StringBuffer valueStr = new StringBuffer("[");
		
		for(int i = 0; i < top3_List.size(); i++){
			dataStr.append("'");
			dataStr.append(top3_List.get(i)[0]);
			dataStr.append("'");
			
			valueStr.append("'");
			valueStr.append(top3_List.get(i)[1]);
			valueStr.append("'");
			
			if((i+1) < top3_List.size()){
				dataStr.append(",");
				valueStr.append(",");
			}
			
		}
		dataStr.append("]");
		valueStr.append("]");
		
		instructionList.add(dataStr.toString());
		instructionList.add(valueStr.toString());
		return instructionList;
	}

	/**
	 * 涉及本专业的知识点数量
	 */
	public List<String> reportKnowledgeCount(String zykId, String beginDate, String endDate) {
		List<String> knowledgeList = zykResourceDao.findResourceKnowledge(zykId, beginDate, endDate);
		List<String> knowledgeCountList = new ArrayList<String>();
		int knowledgeListSize = 0;
		
		StringBuffer dataStr = new StringBuffer("[");
		StringBuffer valueStr = new StringBuffer("[");
		if(knowledgeList != null){
			knowledgeListSize = knowledgeList.size();
			for(int i = 0; i < knowledgeList.size(); i++){
				dataStr.append("'"+knowledgeList.get(i)+"'");
				valueStr.append("{value:"+ 1 +", name:'"+knowledgeList.get(i)+"'}");
				
				if((i+1) < knowledgeList.size()){
					dataStr.append(",");
					valueStr.append(",");
				}
			}
		}
		dataStr.append("]");
		valueStr.append("]");
		
		knowledgeCountList.add(dataStr.toString());
		knowledgeCountList.add(valueStr.toString());
		knowledgeCountList.add(knowledgeListSize+"");
		
		return knowledgeCountList;
	}
	
	/**
	 * 课程数
	 */
	public List<String> reportCourseCount(String zykId, String beginDate, String endDate) {
		List<Object[]> questionCountList = zykCourseDao.getZykCourseCountByModifiedtime(zykId, beginDate, endDate);
		List<String> resultList = new ArrayList<String>();
		resultList.add(questionCountList.get(0)+"");
		return resultList;
	}
	
	/**
	 * 不同类型课程分布
	 */
	public List<String> reportCourseTypeCount(String zykId, String beginDate, String endDate) {
		List<Object[]> courseLevelList = zykCourseDao.getZykCourseType(zykId, beginDate, endDate);
		List<String> courseLevelCountList = new ArrayList<String>();
		
		String titleStr = "['专业核心课','专业基础课','专业拓展课','公共基础课','专业实训课']";
		StringBuffer valueStr = new StringBuffer("[");
		for(int i = 0; i < courseLevelList.size(); i++){
			valueStr.append("'"+ courseLevelList.get(i) +"'");
			
			if((i+1) < courseLevelList.size()){
				valueStr.append(",");
			}
		}
		valueStr.append("]");
		
		courseLevelCountList.add(titleStr);
		courseLevelCountList.add(valueStr.toString());
		return courseLevelCountList;
	}
	
	/**
	 * 不同层级课程分布
	 */
	public List<String> reportCourseLevelCount(String zykId, String beginDate, String endDate) {
		List<Object[]> courseLevelList = zykCourseDao.getZykCourseLevel(zykId, beginDate, endDate);
		List<String> courseLevelCountList = new ArrayList<String>();
		
//		String titleStr = "['课程','微课','培训项目']";
		String titleStr = "['微课','培训项目']";
		StringBuffer valueStr = new StringBuffer("[");
		for(int i = 0; i < courseLevelList.size(); i++){
			valueStr.append("'"+ courseLevelList.get(i) +"'");
			
			if((i+1) < courseLevelList.size()){
				valueStr.append(",");
			}
		}
		valueStr.append("]");
		
		courseLevelCountList.add(titleStr);
		courseLevelCountList.add(valueStr.toString());
		return courseLevelCountList;
	}

	/**
	 * 各门课程的模块数
	 */
	public List<String> reportCourseModuleCount(String zykId, String beginDate,	String endDate) {
		List<Object[]> courseModuleList = zykCourseDao.findCourseModuleZykCourseLevel(zykId, beginDate, endDate);
		List<String> courseModuleCountList = new ArrayList<String>();
		int courseModuleListSize = courseModuleList.size();
		
		StringBuffer dataStr = new StringBuffer("[");
		StringBuffer valueStr = new StringBuffer("[");
		if(courseModuleList != null){
			
			for(int i = 0; i < courseModuleList.size(); i++){
				dataStr.append("'"+courseModuleList.get(i)[0]+"'");
				valueStr.append("'"+ courseModuleList.get(i)[1] +"'");
				
//				int count = Integer.parseInt(courseModuleList.get(i)[1]+"");
//				courseModuleListSize += count;
				
				if((i+1) < courseModuleList.size()){
					dataStr.append(",");
					valueStr.append(",");
				}
			}
		}
		dataStr.append("]");
		valueStr.append("]");
		
		courseModuleCountList.add(dataStr.toString());
		courseModuleCountList.add(valueStr.toString());
		courseModuleCountList.add(courseModuleListSize+"");
		
		return courseModuleCountList;
	}

	/**
	 * 课程引用的资源素材占所有资源素材比
	 */
	public List<String> reportCourseQuoteResource(String zykId,	String beginDate, String endDate) {
		List<ZykResourceEntity> zykResourceList = zykResourceDao.getZykResourceByZykId(zykId);
		List<ZykCourseRresourceRelationEntity> zykCRRlist = zykCourseRresourceRelationDao.getCourseRresourceList(zykId);
		List<String> resultList = new ArrayList<String>();
		
		int resourceTotal = zykResourceList.size();
		int courseQuoteTotal = zykCRRlist.size();
		int D_value = resourceTotal-courseQuoteTotal;
		String data = "['课程引用的资源素材','未引用的资源素材']";
		String value = "[{value:"+courseQuoteTotal+", name:'课程引用的资源素材'}, {value:"+D_value+", name:'未引用的资源素材'}]";
		
		resultList.add(data);
		resultList.add(value);
		resultList.add(resourceTotal+"");
		
		return resultList;
	}
	
	/**
	 * 题库题目总数
	 */
	public List<String> reportQuestionCount(String zykId) {
		List<Object[]> questionCountList = zykQuestionbankDao.getZykQuestionbankCount(zykId);
		List<String> resultList = new ArrayList<String>();
		resultList.add(questionCountList.get(0)+"");
		return resultList;
	}
}
