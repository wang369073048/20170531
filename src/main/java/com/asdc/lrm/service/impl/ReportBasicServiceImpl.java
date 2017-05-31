package com.asdc.lrm.service.impl;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.asdc.lrm.dao.ZykCourseDao;
import com.asdc.lrm.dao.ZykCourseRresourceRelationDao;
import com.asdc.lrm.dao.ZykDao;
import com.asdc.lrm.dao.ZykDatachangeLogDao;
import com.asdc.lrm.dao.ZykLogDao;
import com.asdc.lrm.dao.ZykQuestionbankDao;
import com.asdc.lrm.dao.ZykResourceDao;
import com.asdc.lrm.dao.ZykUserDao;
import com.asdc.lrm.entity.ZykCourseEntity;
import com.asdc.lrm.entity.ZykCourseRresourceRelationEntity;
import com.asdc.lrm.entity.ZykDatachangeLogEntity;
import com.asdc.lrm.entity.ZykEntity;
import com.asdc.lrm.entity.ZykLogEntity;
import com.asdc.lrm.entity.ZykQuestionbankEntity;
import com.asdc.lrm.entity.ZykResourceEntity;
import com.asdc.lrm.entity.ZykUserEntity;
import com.asdc.lrm.service.ReportBasicService;
import com.asdc.lrm.util.CacheUtil;
import com.asdc.lrm.util.UtilDate;
import com.asdc.lrm.util.UtilProperties;
import com.asdc.lrm.util.UtilString;

@SuppressWarnings("unchecked")
public class ReportBasicServiceImpl implements ReportBasicService {

	private ZykUserDao zykUserDao;
	private ZykCourseDao zykCourseDao;
	private ZykResourceDao zykResourceDao;
	private ZykCourseRresourceRelationDao zykCourseRresourceRelationDao;
	private ZykQuestionbankDao zykQuestionbankDao;
	private ZykLogDao zykLogDao;
	private ZykDao zykDao;
	private ZykDatachangeLogDao zykDatachangeLogDao;

	private CacheUtil cacheUtil;

	private long allReportsExpireSeconds;

	public void setZykLogDao(ZykLogDao zykLogDao) {
		this.zykLogDao = zykLogDao;
	}

	public void setZykDao(ZykDao zykDao) {
		this.zykDao = zykDao;
	}

	public void setZykQuestionbankDao(ZykQuestionbankDao zykQuestionbankDao) {
		this.zykQuestionbankDao = zykQuestionbankDao;
	}

	public void setZykCourseRresourceRelationDao(
			ZykCourseRresourceRelationDao zykCourseRresourceRelationDao) {
		this.zykCourseRresourceRelationDao = zykCourseRresourceRelationDao;
	}

	public void setZykUserDao(ZykUserDao zykUserDao) {
		this.zykUserDao = zykUserDao;
	}

	public void setZykCourseDao(ZykCourseDao zykCourseDao) {
		this.zykCourseDao = zykCourseDao;
	}

	public void setZykResourceDao(ZykResourceDao zykResourceDao) {
		this.zykResourceDao = zykResourceDao;
	}

	public void setCacheUtil(CacheUtil cacheUtil) {
		this.cacheUtil = cacheUtil;
	}

	public void setZykDatachangeLogDao(ZykDatachangeLogDao zykDatachangeLogDao) {
		this.zykDatachangeLogDao = zykDatachangeLogDao;
	}
	
	public void setAllReportsExpireSeconds(long allReportsExpireSeconds) {
		this.allReportsExpireSeconds = allReportsExpireSeconds;
	}

	private String getAllReportsKey(String zykId) {
		return new StringBuilder("allreports_").append(zykId).toString();
	}

	public void showAllReportsForCache(){
		List<ZykEntity> zykList = zykDao.findAllZykEntity();
		for(ZykEntity zyk : zykList){
			String zykId = zyk.getZykId();
			showAllReports(zykId);
		}
	}
	
	/**
	 * 统计所有报表 Report_Basic
	 */
	public Map showAllReports(String zykId) {
		HashMap resultMap = null;
		String cacheKey = this.getAllReportsKey(zykId);
		resultMap = (HashMap) this.cacheUtil.get(cacheKey);
		if (resultMap != null) {
			return resultMap;
		}
		// zyk_user表 全部数据
		List<ZykUserEntity> totalUserList = zykUserDao.findAllUserByZykId(zykId);

		// zyk_resource表全部数据
		List<ZykResourceEntity> totalResourceList = zykResourceDao.getZykResourceByZykId(zykId);

		// zyk_cource表全部数据
		List<ZykCourseEntity> totalCourseList = zykCourseDao.findAllCourseByZykId(zykId);

		// zyk_course_resource_relation表全部数据
		List<ZykCourseRresourceRelationEntity> totalRelationList = zykCourseRresourceRelationDao.findAllRelation(zykId);

		// zyk_questionbank表全部数据
		List<ZykQuestionbankEntity> totalQuestionList = zykQuestionbankDao.getZykQuestionbankByZykId(zykId);

		// zyk_log表全部数据
		List<ZykLogEntity> totalLogList = zykLogDao.getZykLogByZykId(zykId);
		
		// zyk_log表全部数据
		List<ZykDatachangeLogEntity> totalDatachangeLogList = zykDatachangeLogDao.getZykDatachangeLogByZykId(zykId);

		/**
		 * 建设模块
		 */
		Map constructionMap = constructionService(zykId, totalLogList, totalUserList, totalRelationList, totalResourceList,	totalCourseList, totalQuestionList);

		/**
		 * 应用模块
		 */
		Map applicationMap = applicationService(zykId, totalLogList, totalUserList, totalResourceList, totalCourseList,	totalQuestionList);

		/**
		 * 更新模块
		 */
		Map updateModuleMap = updateModuleService(zykId, totalLogList, totalUserList, totalResourceList, totalCourseList, totalQuestionList, totalDatachangeLogList);

		resultMap = new HashMap();
		resultMap.putAll(constructionMap);
		resultMap.putAll(applicationMap);
		resultMap.putAll(updateModuleMap);
		this.cacheUtil.set(cacheKey, resultMap, this.allReportsExpireSeconds);
		return resultMap;
	}

	/**
	 * 建设模块基本报表统计
	 * 
	 * @param totalUserList
	 * @return
	 */
	private Map constructionService(String zykId,
			List<ZykLogEntity> totalLogList, List<ZykUserEntity> totalUserList,
			List<ZykCourseRresourceRelationEntity> totalRelationList,
			List<ZykResourceEntity> totalResourceList,
			List<ZykCourseEntity> totalCourseList,
			List<ZykQuestionbankEntity> totalQuestionList) {
		
		Map resultMap = new HashMap();
		// 用户总数
		List userCountList = userCountList(totalUserList);

		// 用户来自的院校数
		List userInstituteList = userInstituteList(totalUserList);

		// 用户来自的省级行政区数
		List userProvinceList = userProvinceList(totalUserList);

		// 资源素材总数
		List resourceCountList = resourceCountList(totalResourceList);

		// 不同教学应用的资源素材分布
		// List instructionCountList = instructionCountList(totalResourceList);

		// 涉及本专业知识点数
		List knowledgeCountList = knowledgeCountList(totalResourceList);

		// 课程数
		Map courseCount = courseCount(totalCourseList);

		// 不同类型课程分布
		List courseTypeCountList = courseTypeCountList(totalCourseList);

		// 微课和培训项目
		List courseLevelCountList = courseLevelCountList(totalCourseList);

		// 模块数
		List courseModuleCountList = courseModuleCountList(totalRelationList, totalCourseList);

		// 课程引用的资源素材占所有资源素材比
		List courseResourceCountList = courseResourceCountList(totalCourseList, totalRelationList);

		// 题库题目总数
		Map totalQuestionCount = totalQuestionCount(totalQuestionList);

		resultMap.put("userCountList", userCountList);
		resultMap.put("userInstituteList", userInstituteList);
		resultMap.put("userProvinceList", userProvinceList);
		resultMap.put("resourceCountList", resourceCountList);
		// resultMap.put("instructionCountList", instructionCountList);
		resultMap.put("knowledgeCountList", knowledgeCountList);
		resultMap.put("courseLevelCountList", courseLevelCountList);
		resultMap.put("courseCount", courseCount);
		resultMap.put("courseTypeCountList", courseTypeCountList);
		resultMap.put("courseModuleCountList", courseModuleCountList);
		resultMap.put("courseResourceCountList", courseResourceCountList);
		resultMap.put("totalQuestionCount", totalQuestionCount);

		return resultMap;
	}

	/**
	 * 更新模块基本报表统计
	 * 
	 * @param totalUserList
	 * @return
	 */
	private Map updateModuleService(String zykId, List<ZykLogEntity> totalLogList,
			List<ZykUserEntity> totalUserList,
			List<ZykResourceEntity> totalResourceList,
			List<ZykCourseEntity> totalCourseList,
			List<ZykQuestionbankEntity> totalQuestionList,
			List<ZykDatachangeLogEntity> totalDatachangeLogList) {
		
		Map resultMap = new HashMap();

		// 同比上一年用户注册增长率
		List userIncreaseRateList = userIncreaseRateList(totalUserList);

		// 同比上一年用户更新率
		List userModifiedRateList = userModifiedRateList(totalUserList);
		
		// 历年新增用户数
		List userAddCountList = userAddCountList(totalUserList);
		
		// 历年修改用户数
		List userUpdateCountList = userUpdateCountList(totalUserList);
		
		// 历年删除用户数
		List userDeleteCountList = userDeleteCountList(totalDatachangeLogList);

		// 同比上一年资源更新率
		List resourceUpdateRateList = resourceUpdateRateList(totalResourceList);
		
		// 历年新增资源数
		List resourceAddCountList = resourceAddCountList(totalResourceList);
		
		// 历年修改资源数
		List resourceUpdateCountList = resourceUpdateCountList(totalResourceList);
		
		// 历年删除资源数
		List resourceDeleteCountList = resourceDeleteCountList(totalDatachangeLogList);

		// 当年更新数与资源总数的比值
		// List resourceCurYearUpdateRateList =
		// resourceCurYearUpdateRateList(totalResourceList);

		// 同比上一年课程更新率
		List courseUpdateRateList = courseUpdateRateList(totalCourseList);
		
		// 历年新增课程数
		List courseAddCountList = courseAddCountList(totalCourseList);
		
		// 历年修改课程数
		List courseUpdateCountList = courseUpdateCountList(totalCourseList);
		
		// 历年删除课程数
		List courseDeleteCountList = courseDeleteCountList(totalDatachangeLogList);
		
		resultMap.put("userIncreaseRateList", userIncreaseRateList);
		resultMap.put("userModifiedRateList", userModifiedRateList);
		resultMap.put("userAddCountList", userAddCountList);
		resultMap.put("userUpdateCountList", userUpdateCountList);
		resultMap.put("userDeleteCountList", userDeleteCountList);
		resultMap.put("resourceUpdateRateList", resourceUpdateRateList);
		resultMap.put("resourceAddCountList", resourceAddCountList);
		resultMap.put("resourceUpdateCountList", resourceUpdateCountList);
		resultMap.put("resourceDeleteCountList", resourceDeleteCountList);
		// resultMap.put("resourceCurYearUpdateRateList",
		// resourceCurYearUpdateRateList);
		resultMap.put("courseUpdateRateList", courseUpdateRateList);
		resultMap.put("courseAddCountList", courseAddCountList);
		resultMap.put("courseUpdateCountList", courseUpdateCountList);
		resultMap.put("courseDeleteCountList", courseDeleteCountList);

		return resultMap;

	}

	/**
	 * 同比上一年课程更新率
	 * 
	 * @param totalCourseList
	 * @return
	 */
	private List<Object> courseUpdateRateList(List<ZykCourseEntity> totalCourseList) {

		int curYear = UtilDate.getYear();
		int beginYear = curYear - 5;
		int endYear = curYear;
		
		//initList存放初始化数据
		//偶数位year 奇数位count
		List<Integer> initList = new ArrayList<Integer>();
		
		Calendar cal = Calendar.getInstance();
		for(int year = beginYear; year <= endYear ; year++){
			int count = 0;
			for(ZykCourseEntity course : totalCourseList){
				if(course.getModifiedTime() != null){
					cal.setTime(course.getModifiedTime());
					int resourceYear = cal.get(Calendar.YEAR);
					if(resourceYear == 2020){
						resourceYear = curYear;
					}
					if(year >= resourceYear){
						count ++;
					}
				}
			}
			initList.add(year);
			initList.add(count);
		}
		List<Object> resultList  = UpdateModules(initList, "更新率");
		return resultList;
	}
	
	/**
	 * 历年新增课程数
	 * @param totalUserList
	 * @return
	 */
	private List<Object> courseAddCountList(List<ZykCourseEntity> totalCourseList) {
		int curYear = UtilDate.getYear();
		int beginYear = curYear - 4;
		int endYear = curYear;
		
		//initList存放初始化数据
		//偶数位year 奇数位count
		List<Integer> initList = new ArrayList<Integer>();
		
		Calendar createdCal = Calendar.getInstance();
		for(int year = beginYear; year <= endYear ; year++){
			int count = 0;
			for(ZykCourseEntity course : totalCourseList){
				if(course.getCreatedTime() != null){
					createdCal.setTime(course.getCreatedTime());
					int createdYear = createdCal.get(Calendar.YEAR);
					if(createdYear == 2020){
						createdYear = curYear;
					}
					if(year == createdYear){
						count ++;
					}
				}
			}
			initList.add(year);
			initList.add(count);
		}
		List<Object> resultList  = UpdateOrAddCount(initList, "年新增课程");
		return resultList;
	}
	
	/**
	 * 历年修改课程数
	 * @param totalUserList
	 * @return
	 */
	private List<Object> courseUpdateCountList(List<ZykCourseEntity> totalResourceList) {
		int curYear = UtilDate.getYear();
		int beginYear = curYear - 4;
		int endYear = curYear;
		
		//initList存放初始化数据
		//偶数位year 奇数位count
		List<Integer> initList = new ArrayList<Integer>();
		
		Calendar modifiedCal = Calendar.getInstance();
		for(int year = beginYear; year <= endYear ; year++){
			int count = 0;
			for(ZykCourseEntity course : totalResourceList){
				if(course.getModifiedTime() != null){
					modifiedCal.setTime(course.getModifiedTime());
					int modifiedYear = modifiedCal.get(Calendar.YEAR);
					if(modifiedYear == 2020){
						modifiedYear = curYear;
					}
					if(year == modifiedYear){
						count ++;
					}
				}
			}
			initList.add(year);
			initList.add(count);
		}
		List<Object> resultList  = UpdateOrAddCount(initList, "年修改课程");
		return resultList;
	}
	
	/**
	 * 历年删除课程数
	 * @param totalUserList
	 * @return
	 */
	private List<Object> courseDeleteCountList(List<ZykDatachangeLogEntity> totalDatachangeLogList) {
		int curYear = UtilDate.getYear();
		int beginYear = curYear - 4;
		int endYear = curYear;
		
		//initList存放初始化数据
		//偶数位year 奇数位count
		List<Integer> initList = new ArrayList<Integer>();
		
		Calendar cal = Calendar.getInstance();
		for(int year = beginYear; year <= endYear ; year++){
			int count = 0;
			for(ZykDatachangeLogEntity changeLog : totalDatachangeLogList){
				if("课程".equals(changeLog.getObjectType()) && "删除".equals(changeLog.getAction())){
					if(changeLog.getTime() != null){
						cal.setTime(changeLog.getTime());
						int time = cal.get(Calendar.YEAR);
						if(time == 2020){
							time = curYear;
						}
						if(year == time){
							count ++;
						}
					}
				}
			}
			initList.add(year);
			initList.add(0 - count);
		}
		List<Object> resultList  = UpdateOrAddCount(initList, "年删除课程");
		return resultList;
	}
	
	/**
	 * 当年更新数与资源总数的比值
	 * 
	 * @param totalResourceList
	 * @return
	 */
	private List<Object> resourceCurYearUpdateRateList(
			List<ZykResourceEntity> totalResourceList) {

		List<Object> resultList = new ArrayList<Object>();
		// 当前年更新
		int currentYear = UtilDate.getYear();
		int curYearCount = 0;
		Map curYearCountMap = new HashMap();
		for (ZykResourceEntity resource : totalResourceList) {
			String[] strs = resource.getModifiedTime().toString().split("-");
			if (strs[0].equals(currentYear + "")) {
				curYearCount++;
			}
		}
		curYearCountMap.put("year", currentYear + "年素材更新数量");
		curYearCountMap.put("count", curYearCount);
		resultList.add(curYearCountMap);

		int totalCount = totalResourceList.size();
		resultList = addRateForMap(totalCount, resultList);

		// 总量
		Map totalCountMap = new HashMap();
		totalCountMap.put("totalCount", totalCount);
		resultList.add(totalCountMap);

		double rate = 0;
		double curCount = Double.parseDouble(curYearCount + "");
		double total = Double.parseDouble(totalCount + "");
		String rateArray = "0";
		if (total != 0) {
			rate = curCount / total;
			DecimalFormat df = new DecimalFormat("0.0");
			String rateFormat = df.format(rate * 100);
			rateArray = rateFormat + "%";
		}

		Map rateMap = new HashMap();
		rateMap.put("year", currentYear + "年更新率");
		rateMap.put("count", rateArray);
		resultList.add(rateMap);

		return resultList;
	}

	/**
	 * 同比上一年资源更新率
	 * 
	 * @param totalResourceList
	 * @return
	 */
	private List<Object> resourceUpdateRateList(List<ZykResourceEntity> totalResourceList) {

		int curYear = UtilDate.getYear();
		int beginYear = curYear - 5;
		int endYear = curYear;
		
		//initList存放初始化数据
		//偶数位year 奇数位count
		List<Integer> initList = new ArrayList<Integer>();
		
		Calendar cal = Calendar.getInstance();
		for(int year = beginYear; year <= endYear ; year++){
			int count = 0;
			for(ZykResourceEntity resource : totalResourceList){
				cal.setTime(resource.getModifiedTime());
				int resourceYear = cal.get(Calendar.YEAR);
				if(resourceYear == 2020){
					resourceYear = curYear;
				}
				if(year >= resourceYear){
					count ++;
				}
			}
			initList.add(year);
			initList.add(count);
		}
		List<Object> resultList  = UpdateModules(initList, "更新率");
		return resultList;
	}
	
	/**
	 * 历年新增资源数
	 * @param totalUserList
	 * @return
	 */
	private List<Object> resourceAddCountList(List<ZykResourceEntity> totalResourceList) {
		int curYear = UtilDate.getYear();
		int beginYear = curYear - 4;
		int endYear = curYear;
		
		//initList存放初始化数据
		//偶数位year 奇数位count
		List<Integer> initList = new ArrayList<Integer>();
		
		Calendar createdCal = Calendar.getInstance();
		for(int year = beginYear; year <= endYear ; year++){
			int count = 0;
			for(ZykResourceEntity resource : totalResourceList){
				if(resource.getCreatedTime() != null){
					createdCal.setTime(resource.getCreatedTime());
					int createdYear = createdCal.get(Calendar.YEAR);
					if(createdYear == 2020){
						createdYear = curYear;
					}
					if(year == createdYear){
						count ++;
					}
				}
			}
			initList.add(year);
			initList.add(count);
		}
		List<Object> resultList  = UpdateOrAddCount(initList, "年新增资源");
		return resultList;
	}
	
	/**
	 * 历年修改资源数
	 * @param totalUserList
	 * @return
	 */
	private List<Object> resourceUpdateCountList(List<ZykResourceEntity> totalResourceList) {
		int curYear = UtilDate.getYear();
		int beginYear = curYear - 4;
		int endYear = curYear;
		
		//initList存放初始化数据
		//偶数位year 奇数位count
		List<Integer> initList = new ArrayList<Integer>();
		
		Calendar modifiedCal = Calendar.getInstance();
		for(int year = beginYear; year <= endYear ; year++){
			int count = 0;
			for(ZykResourceEntity resource : totalResourceList){
				if(resource.getModifiedTime() != null){
					modifiedCal.setTime(resource.getModifiedTime());
					int modifiedYear = modifiedCal.get(Calendar.YEAR);
					if(modifiedYear == 2020){
						modifiedYear = curYear;
					}
					if(year == modifiedYear){
						count ++;
					}
				}
			}
			initList.add(year);
			initList.add(count);
		}
		List<Object> resultList  = UpdateOrAddCount(initList, "年修改资源");
		return resultList;
	}
	
	/**
	 * 历年删除资源数
	 * @param totalUserList
	 * @return
	 */
	private List<Object> resourceDeleteCountList(List<ZykDatachangeLogEntity> totalDatachangeLogList) {
		int curYear = UtilDate.getYear();
		int beginYear = curYear - 4;
		int endYear = curYear;
		
		//initList存放初始化数据
		//偶数位year 奇数位count
		List<Integer> initList = new ArrayList<Integer>();
		
		Calendar cal = Calendar.getInstance();
		for(int year = beginYear; year <= endYear ; year++){
			int count = 0;
			for(ZykDatachangeLogEntity changeLog : totalDatachangeLogList){
				if("资源".equals(changeLog.getObjectType()) && "删除".equals(changeLog.getAction())){
					if(changeLog.getTime() != null){
						cal.setTime(changeLog.getTime());
						int time = cal.get(Calendar.YEAR);
						if(time == 2020){
							time = curYear;
						}
						if(year == time){
							count ++;
						}
					}
				}
			}
			initList.add(year);
			initList.add(0 - count);
		}
		List<Object> resultList  = UpdateOrAddCount(initList, "年删除用户");
		return resultList;
	}
	
	/**
	 * 更新模块
	 * 用户、资源、课程三部分内容报表
	 * @param initList
	 * @param contentStr
	 * @return
	 */
	private List<Object> UpdateModules(List<Integer> initList, String contentStr){
		List<Object> resultList = new ArrayList<Object>();
		DecimalFormat df = new DecimalFormat("#.#");
		for(int i = 0; i < 9; i = i+2){
			Map<Object, Object> resultMap = new HashMap<Object, Object>();
			StringBuffer content = new StringBuffer();
			String updateRate = "";
			
			//截止去年数据与截止今年数据相等，更新率：0
			if(initList.get(i+1).equals(initList.get(i+3))){
				updateRate = "0";
			}else if(initList.get(i+1).equals(0)){
			//截止去年数据为0，截止今年数据不为0 更新率：100%
				updateRate = "100%";
			}else{
			//截止去年数据不为0，截止今年数据不为0，且二者不相等，更新率：(今年数据 - 去年数据)/去年数据
				double rate = 0.0;
				double lastCount = initList.get(i+1);
				double curCount = initList.get(i+3);
				rate = (curCount - lastCount)/lastCount;
				updateRate = df.format(rate*100)+"%";
			}
			content.append(initList.get(i+2) +"年比"+initList.get(i)+"年"+contentStr);
			resultMap.put("year", content);
			resultMap.put("count", updateRate);
			resultList.add(resultMap);

		}
		return resultList;
	}
	
	/**
	 * 更新模块
	 * 用户、资源、课程三部分内容报表
	 * @param initList
	 * @param contentStr
	 * @return
	 */
	private List<Object> UpdateOrAddCount(List<Integer> initList, String contentStr){
		List<Object> resultList = new ArrayList<Object>();
//		DecimalFormat df = new DecimalFormat("#.#");
		for(int i = 0; i < 9; i = i+2){
			Map<Object, Object> resultMap = new HashMap<Object, Object>();
			StringBuffer content = new StringBuffer();
			String updateRate = "";
//			
//			//截止去年数据与截止今年数据相等，更新率：0
//			if(initList.get(i+1).equals(initList.get(i+3))){
//				updateRate = "0";
//			}else if(initList.get(i+1).equals(0)){
//				//截止去年数据为0，截止今年数据不为0 更新率：100%
//				updateRate = "100%";
//			}else{
//				//截止去年数据不为0，截止今年数据不为0，且二者不相等，更新率：(今年数据 - 去年数据)/去年数据
//				double rate = 0.0;
//				double lastCount = initList.get(i+1);
//				double curCount = initList.get(i+3);
//				rate = (curCount - lastCount)/lastCount;
//				updateRate = df.format(rate*100)+"%";
//			}
			if(initList.get(i+1) == null){
				updateRate = "0";
			}
			content.append(initList.get(i) +contentStr);
			resultMap.put("year", content);
			resultMap.put("count", initList.get(i+1));
			resultList.add(resultMap);
			
		}
		return resultList;
	}

	/**
	 * 同比上一年用户注册增长率
	 * 
	 * @param totalUserList
	 * @return
	 */
	private List<Object> userIncreaseRateList(List<ZykUserEntity> totalUserList) {
		int curYear = UtilDate.getYear();
		int beginYear = curYear - 5;
		int endYear = curYear;
		
		//initList存放初始化数据
		//偶数位year 奇数位count
		List<Integer> initList = new ArrayList<Integer>();
		
		Calendar cal = Calendar.getInstance();
		for(int year = beginYear; year <= endYear ; year++){
			int count = 0;
			for(ZykUserEntity user : totalUserList){
				if(user.getCreatedTime() != null){
					cal.setTime(user.getCreatedTime());
					int resourceYear = cal.get(Calendar.YEAR);
					if(resourceYear == 2020){
						resourceYear = curYear;
					}
					if(year >= resourceYear){
						count ++;
					}
				}
			}
			initList.add(year);
			initList.add(count);
		}
		List<Object> resultList  = UpdateModules(initList, "增长率");
		return resultList;
	}
	
	/**
	 * 同比上一年用户更新率
	 * 
	 * @param totalUserList
	 * @return
	 */
	private List<Object> userModifiedRateList(List<ZykUserEntity> totalUserList) {
		int curYear = UtilDate.getYear();
		int beginYear = curYear - 5;
		int endYear = curYear;
		
		//initList存放初始化数据
		//偶数位year 奇数位count
		List<Integer> initList = new ArrayList<Integer>();
		
		Calendar createdCal = Calendar.getInstance();
		Calendar modifiedCal = Calendar.getInstance();
		for(int year = beginYear; year <= endYear ; year++){
			int count = 0;
			for(ZykUserEntity user : totalUserList){
				if(user.getCreatedTime() != null && user.getModifiedTime() != null){
					createdCal.setTime(user.getCreatedTime());
					modifiedCal.setTime(user.getModifiedTime());
					int createdYear = createdCal.get(Calendar.YEAR);
					int modifiedYear = createdCal.get(Calendar.YEAR);
					if(createdYear == 2020){
						createdYear = curYear;
					}
					if(year >= createdYear || year > modifiedYear){
						count ++;
					}
				}
			}
			initList.add(year);
			initList.add(count);
		}
		List<Object> resultList  = UpdateModules(initList, "更新率");
		return resultList;
	}
	
	/**
	 * 历年新增用户数
	 * @param totalUserList
	 * @return
	 */
	private List<Object> userAddCountList(List<ZykUserEntity> totalUserList) {
		int curYear = UtilDate.getYear();
		int beginYear = curYear - 4;
		int endYear = curYear;
		
		//initList存放初始化数据
		//偶数位year 奇数位count
		List<Integer> initList = new ArrayList<Integer>();
		
		Calendar createdCal = Calendar.getInstance();
		for(int year = beginYear; year <= endYear ; year++){
			int count = 0;
			for(ZykUserEntity user : totalUserList){
				if(user.getCreatedTime() != null){
					createdCal.setTime(user.getCreatedTime());
					int createdYear = createdCal.get(Calendar.YEAR);
					if(createdYear == 2020){
						createdYear = curYear;
					}
					if(year == createdYear){
						count ++;
					}
				}
			}
			initList.add(year);
			initList.add(count);
		}
		List<Object> resultList  = UpdateOrAddCount(initList, "年新增用户");
		return resultList;
	}
	
	/**
	 * 历年修改用户数
	 * @param totalUserList
	 * @return
	 */
	private List<Object> userUpdateCountList(List<ZykUserEntity> totalUserList) {
		int curYear = UtilDate.getYear();
		int beginYear = curYear - 4;
		int endYear = curYear;
		
		//initList存放初始化数据
		//偶数位year 奇数位count
		List<Integer> initList = new ArrayList<Integer>();
		
		Calendar modifiedCal = Calendar.getInstance();
		for(int year = beginYear; year <= endYear ; year++){
			int count = 0;
			for(ZykUserEntity user : totalUserList){
				if(user.getModifiedTime() != null){
					modifiedCal.setTime(user.getCreatedTime());
					int modifiedYear = modifiedCal.get(Calendar.YEAR);
					if(modifiedYear == 2020){
						modifiedYear = curYear;
					}
					if(year == modifiedYear){
						count ++;
					}
				}
			}
			initList.add(year);
			initList.add(count);
		}
		List<Object> resultList  = UpdateOrAddCount(initList, "年修改用户");
		return resultList;
	}
	
	/**
	 * 历年删除用户数
	 * @param totalUserList
	 * @return
	 */
	private List<Object> userDeleteCountList(List<ZykDatachangeLogEntity> totalDatachangeLogList) {
		int curYear = UtilDate.getYear();
		int beginYear = curYear - 4;
		int endYear = curYear;
		
		//initList存放初始化数据
		//偶数位year 奇数位count
		List<Integer> initList = new ArrayList<Integer>();
		
		Calendar cal = Calendar.getInstance();
		for(int year = beginYear; year <= endYear ; year++){
			int count = 0;
			for(ZykDatachangeLogEntity changeLog : totalDatachangeLogList){
				if("用户".equals(changeLog.getObjectType()) && "删除".equals(changeLog.getAction())){
					if(changeLog.getTime() != null){
						cal.setTime(changeLog.getTime());
						int time = cal.get(Calendar.YEAR);
						if(time == 2020){
							time = curYear;
						}
						if(year == time){
							count ++;
						}
					}
				}
			}
			initList.add(year);
			initList.add(0 - count);
		}
		List<Object> resultList  = UpdateOrAddCount(initList, "年删除用户");
		return resultList;
	}

	/**
	 * 应用模块基本报表统计
	 * 
	 * @param totalUserList
	 * @return
	 */
	private Map applicationService(String zykId,
			List<ZykLogEntity> totalLogList, List<ZykUserEntity> totalUserList,
			List<ZykResourceEntity> totalResourceList,
			List<ZykCourseEntity> totalCourseList,
			List<ZykQuestionbankEntity> totalQuestionList) {
		
		Map resultMap = new HashMap();

		// 用户行为活动总量
		List userBehaviourCountList = userBehaviourCountList(totalLogList);
		
		//注册用户活动情况
		List userActiveLogCountList = userActiveLogCountList(zykId);

		// 用户累计学习天数
		// List userStudyDateCountList = userStudyDateCountList(totalLogList, totalUserList);

		// 用户活动总次数
		// List userActiveCountList = userActiveCountList(totalLogList, totalUserList);

		// 人均活动天数
		// List userActiveDateAvgCountList = userActiveDateAvgCountList(totalLogList);

		// 资源素材使用总次数
		List resourceUsingCountList = resourceUsingCountList(totalLogList);

		// 资源素材应用情况
		List resourceUsingActionCountList = resourceUsingActionCountList(totalLogList);

		// 不同文件类型资源素材浏览和下载频次）
		List resourceUsingAndDownloadCountList = resourceUsingAndDownloadCountList(zykId);

		// 使用的资源在全部资源的占比
		List resourceUsingRateCountList = resourceUsingRateCountList(totalLogList, totalResourceList);

		// 平均每个资源使用次数
		List resourceUsingAvgCountList = resourceUsingAvgCountList(totalLogList, totalResourceList);

		// 课程访问次数
		// List courseVisitCountList = courseVisitCountList(totalLogList,
		// totalCourseList);

		// 各个课程访问情况（前三）
		List courseVisitTop3CountList = courseVisitTop3CountList(totalLogList,
				totalCourseList);

		// 题库题目引用数
		List questionQuesCountList = questionQuesCountList(totalQuestionList);

		// 题库题目使用总次数
		List questionUsingCountList = questionUsingCountList(totalQuestionList);

		// 论坛活动模块统计
		List forumActiveCountList = forumActiveCountList(totalLogList);

		// 作业活动模块统计
		List workActiveCountList = workActiveCountList(totalLogList);

		// 考试活动模块统计
		List examActiveCountList = examActiveCountList(totalLogList);

		resultMap.put("userBehaviourCountList", userBehaviourCountList);
		resultMap.put("userActiveLogCountList", userActiveLogCountList);
		// resultMap.put("userStudyDateCountList", userStudyDateCountList);
		// resultMap.put("userActiveCountList", userActiveCountList);
		// resultMap.put("userActiveDateAvgCountList", userActiveDateAvgCountList);
		resultMap.put("resourceUsingCountList", resourceUsingCountList);
		resultMap.put("resourceUsingActionCountList", resourceUsingActionCountList);
		resultMap.put("resourceUsingAndDownloadCountList", resourceUsingAndDownloadCountList);
		resultMap.put("resourceUsingRateCountList", resourceUsingRateCountList);
		resultMap.put("resourceUsingAvgCountList", resourceUsingAvgCountList);
		// resultMap.put("courseVisitCountList", courseVisitCountList);
		resultMap.put("courseVisitTop3CountList", courseVisitTop3CountList);
		resultMap.put("questionQuesCountList", questionQuesCountList);
		resultMap.put("questionUsingCountList", questionUsingCountList);
		resultMap.put("forumActiveCountList", forumActiveCountList);
		resultMap.put("workActiveCountList", workActiveCountList);
		resultMap.put("examActiveCountList", examActiveCountList);
		return resultMap;
	}

	/**
	 * 考试活动模块统计
	 * 
	 * @param totalLogList
	 * @return
	 */
	private List<Object> examActiveCountList(List<ZykLogEntity> totalLogList) {
		List<Object> resultList = new ArrayList<Object>();

		// 发布考试
		Map postCountMap = new HashMap();
		int postCount = 0;
		for (ZykLogEntity userLog : totalLogList) {
			if ("考试模块".equals(userLog.getObjectType())
					&& "发布考试".equals(userLog.getAction())) {
				postCount++;
			}
			postCountMap.put("exam", "发布考试");
			postCountMap.put("count", postCount);
		}

		resultList.add(postCountMap);

		// 参加考试
		Map takeCountMap = new HashMap();
		int takeCount = 0;
		for (ZykLogEntity userLog : totalLogList) {
			if ("考试模块".equals(userLog.getObjectType())
					&& "参加考试".equals(userLog.getAction())) {
				takeCount++;
			}
			takeCountMap.put("exam", "参加考试");
			takeCountMap.put("count", takeCount);
		}
		resultList.add(takeCountMap);

		// 添加百分比统计项
		int totalCount = postCount + takeCount;
		resultList = addRateForMap(totalCount, resultList);

		// 平均值
		DecimalFormat df = new DecimalFormat("0.0");
		double avgCount = (Double.parseDouble(totalCount + "")) / 2;
		String avgCountStr = "0";
		if (avgCount != 0) {
			avgCountStr = df.format(avgCount);
		}
		Map avgCountMap = new HashMap();
		avgCountMap.put("exam", "平均值");
		avgCountMap.put("avgCount", avgCountStr);
		resultList.add(avgCountMap);

		// 总量
		Map totalCountMap = new HashMap();
		totalCountMap.put("totalCount", totalCount);
		resultList.add(totalCountMap);

		return resultList;
	}

	/**
	 * 作业活动模块统计
	 * 
	 * @param totalLogList
	 * @return
	 */
	private List<Object> workActiveCountList(List<ZykLogEntity> totalLogList) {
		List<Object> resultList = new ArrayList<Object>();

		// 发布作业
		Map postCountMap = new HashMap();
		int postCount = 0;
		for (ZykLogEntity userLog : totalLogList) {
			if ("作业模块".equals(userLog.getObjectType())
					&& "发布作业".equals(userLog.getAction())) {
				postCount++;
			}
			postCountMap.put("work", "发布作业");
			postCountMap.put("count", postCount);
		}

		resultList.add(postCountMap);

		// 提交作业
		Map submitCountMap = new HashMap();
		int submitCount = 0;
		for (ZykLogEntity userLog : totalLogList) {
			if ("作业模块".equals(userLog.getObjectType())
					&& "提交作业".equals(userLog.getAction())) {
				submitCount++;
			}
			submitCountMap.put("work", "提交作业");
			submitCountMap.put("count", submitCount);
		}
		resultList.add(submitCountMap);

		// 批改作业
		Map correctCountMap = new HashMap();
		int correctCount = 0;
		for (ZykLogEntity userLog : totalLogList) {
			if ("作业模块".equals(userLog.getObjectType())
					&& "批改作业".equals(userLog.getAction())) {
				correctCount++;
			}
			correctCountMap.put("work", "批改作业");
			correctCountMap.put("count", correctCount);
		}
		resultList.add(correctCountMap);

		// 添加百分比统计项
		int totalCount = postCount + submitCount + correctCount;
		resultList = addRateForMap(totalCount, resultList);

		// 平均值
		DecimalFormat df = new DecimalFormat("0.0");
		double avgCount = (Double.parseDouble(totalCount + "")) / 3;
		String avgCountStr = "0";
		if (avgCount != 0) {
			avgCountStr = df.format(avgCount);
		}
		Map avgCountMap = new HashMap();
		avgCountMap.put("work", "平均值");
		avgCountMap.put("avgCount", avgCountStr);
		resultList.add(avgCountMap);

		// 总量
		Map totalCountMap = new HashMap();
		totalCountMap.put("totalCount", totalCount);
		resultList.add(totalCountMap);

		return resultList;
	}

	/**
	 * 论坛活动模块统计
	 * 
	 * @param totalLogList
	 * @return
	 */
	private List<Object> forumActiveCountList(List<ZykLogEntity> totalLogList) {
		List<Object> resultList = new ArrayList<Object>();
		// 发帖
		Map postCountMap = new HashMap();
		int postCount = 0;
		for (ZykLogEntity userLog : totalLogList) {
			if ("论坛模块".equals(userLog.getObjectType())
					&& "发帖".equals(userLog.getAction())) {
				postCount++;
			}
			postCountMap.put("action", "发帖");
			postCountMap.put("count", postCount);
		}

		resultList.add(postCountMap);

		// 读贴
		Map readCountMap = new HashMap();
		int readCount = 0;
		for (ZykLogEntity userLog : totalLogList) {
			if ("论坛模块".equals(userLog.getObjectType())
					&& "读贴".equals(userLog.getAction())) {
				readCount++;
			}
			readCountMap.put("action", "读贴");
			readCountMap.put("count", readCount);
		}
		resultList.add(readCountMap);

		// 添加百分比统计项
		int totalCount = postCount + readCount;
		resultList = addRateForMap(totalCount, resultList);

		// 平均值
		DecimalFormat df = new DecimalFormat("0.0");
		double avgCount = (Double.parseDouble(totalCount + "")) / 2;
		String avgCountStr = "0";
		if (avgCount != 0) {
			avgCountStr = df.format(avgCount);
		}
		Map avgCountMap = new HashMap();
		avgCountMap.put("action", "平均值");
		avgCountMap.put("avgCount", avgCountStr);
		resultList.add(avgCountMap);

		// 总量
		Map totalCountMap = new HashMap();
		totalCountMap.put("totalCount", totalCount);
		resultList.add(totalCountMap);

		return resultList;
	}

	/**
	 * 题库题目使用总次数
	 * 
	 * @param totalQuestionList
	 * @return
	 */
	private List<Object> questionUsingCountList(
			List<ZykQuestionbankEntity> totalQuestionList) {
		List<Object> resultList = new ArrayList<Object>();
		int totalCount = 0;
		for (ZykQuestionbankEntity question : totalQuestionList) {
			Map questionQuesCountMap = new HashMap();
			questionQuesCountMap.put("title", question.getTitle());
			questionQuesCountMap.put("count", question.getQuesUsingNum());

			totalCount += question.getQuesUsingNum();

			resultList.add(questionQuesCountMap);
		}

		// 添加百分比统计项
		resultList = addRateForMap(totalCount, resultList);

		Map totalCountMap = new HashMap();
		totalCountMap.put("totalCount", totalCount);
		resultList.add(totalCountMap);

		return resultList;
	}

	/**
	 * 题库题目引用数
	 * 
	 * @param totalQuestionList
	 * @return
	 */
	private List<Object> questionQuesCountList(
			List<ZykQuestionbankEntity> totalQuestionList) {
		List<Object> resultList = new ArrayList<Object>();
		int totalCount = 0;
		for (ZykQuestionbankEntity question : totalQuestionList) {
			Map questionQuesCountMap = new HashMap();
			questionQuesCountMap.put("title", question.getTitle());
			questionQuesCountMap.put("count", question.getCitedQuesNum());

			totalCount += question.getCitedQuesNum();

			resultList.add(questionQuesCountMap);
		}

		// 添加百分比统计项
		resultList = addRateForMap(totalCount, resultList);

		Map totalCountMap = new HashMap();
		totalCountMap.put("totalCount", totalCount);
		resultList.add(totalCountMap);

		return resultList;
	}

	/**
	 * 各个课程访问情况（前三）
	 * 
	 * @param totalLogList
	 * @param totalCourseList
	 * @return
	 */
	private List courseVisitTop3CountList(List<ZykLogEntity> totalLogList,
			List<ZykCourseEntity> totalCourseList) {

		// 初始化LogList objectType="课程模块"
		List<ZykLogEntity> initLogList = new ArrayList<ZykLogEntity>();

		// logList集合中的数据与courseList中的数据通过courseID相关联
		Set<String> courseIdAndFullnameSet = new HashSet<String>();
		for (ZykLogEntity userLog : totalLogList) {
			if (userLog.getObjectType().equals("课程模块")) {
				initLogList.add(userLog);
				for (ZykCourseEntity course : totalCourseList) {
					if (userLog.getCourseId().equals(course.getCourseId())) {
						courseIdAndFullnameSet.add(course.getCourseId() + "::"
								+ course.getFullname());
					}
				}
			}
		}

		List<Object> initList = new ArrayList<Object>();
		int totalCount = 0;
		for (String str : courseIdAndFullnameSet) {
			int count = 0;
			Map fullnameMap = new HashMap();
			String[] strs = str.split("::");
			for (ZykLogEntity zykLog : initLogList) {
				if (strs[0].equals(zykLog.getCourseId())) {
					count++;
				}
			}
			totalCount += count;

			fullnameMap.put("fullname", strs[1]);
			fullnameMap.put("count", count);
			initList.add(fullnameMap);
		}

		// 将initList 按照count大小排序
		for (int i = 0; i < initList.size(); i++) {
			for (int j = i; j < initList.size(); j++) {
				Map mapTemp_i = ((Map) initList.get(i));
				Map mapTemp_j = ((Map) initList.get(j));
				int count_i = (Integer) mapTemp_i.get("count");
				int count_j = (Integer) mapTemp_j.get("count");
				if (count_i < count_j) {
					Map map_temp = new HashMap();
					map_temp.putAll((Map) initList.get(j));
					((Map) initList.get(j)).putAll((Map) initList.get(i));
					((Map) initList.get(i)).putAll(map_temp);
				}
			}
		}
		List top3List = new ArrayList();
		/*
		 * for(int i = 0; i < initList.size(); i++){ if(i < 3){
		 * top3List.add(initList.get(i)); }else{ Map mapTemp_i = ((Map)
		 * initList.get(i)); Map mapTemp_j= ((Map) initList.get(i-1)); int
		 * count_i = (Integer) mapTemp_i.get("count"); int count_j = (Integer)
		 * mapTemp_j.get("count"); if(count_i == count_j){
		 * top3List.add(initList.get(i)); }else{ break; } } }
		 */
		for (int i = 0; i < initList.size(); i++) {
			if (i < 3) {
				top3List.add(initList.get(i));
			}
		}

		// 添加百分比统计项
		top3List = addRateForMap(totalCount, top3List);

		Map totalCountMap = new HashMap();
		totalCountMap.put("totalCount", totalCount);
		top3List.add(totalCountMap);

		return top3List;
	}

	/**
	 * 课程访问次数
	 * 
	 * @param totalLogList
	 * @param totalCourseList
	 * @return
	 */
	private List courseVisitCountList(List<ZykLogEntity> totalLogList,
			List<ZykCourseEntity> totalCourseList) {

		// logList集合中的数据与courseList中的数据通过courseID相关联
		Set<String> courseIdAndFullnameSet = new HashSet<String>();
		for (ZykLogEntity userLog : totalLogList) {
			if (userLog.getObjectType().equals("课程模块")) {
				for (ZykCourseEntity course : totalCourseList) {
					if (userLog.getCourseId().equalsIgnoreCase(
							course.getCourseId())) {
						courseIdAndFullnameSet.add(userLog.getCourseId() + "::"
								+ course.getFullname());
					}
				}
			}
		}

		// 得到fullname
		Set<String> fullnameSet = new HashSet<String>();
		for (String str : courseIdAndFullnameSet) {
			String[] strs = str.split("::");
			fullnameSet.add(strs[1]);
		}

		List<Object> resultList = new ArrayList<Object>();
		int totalCount = 0;
		// 根据fullname将courseIdAndFullnameSet集合中的数据分组
		for (String fullname : fullnameSet) {
			Map fullnameMap = new HashMap();
			int count = 0;
			for (String str : courseIdAndFullnameSet) {
				String[] strs = str.split("::");
				if (fullname.equals(strs[1])) {
					count++;
				}
			}
			totalCount += count;

			fullnameMap.put("fullname", fullname);
			fullnameMap.put("count", count);
			resultList.add(fullnameMap);
		}

		// 添加百分比统计项
		resultList = addRateForMap(totalCount, resultList);

		Map totalCountMap = new HashMap();
		totalCountMap.put("totalCount", totalCount);
		resultList.add(totalCountMap);

		return resultList;
	}

	/**
	 * 平均每个资源使用次数
	 * 
	 * @param totalLogList
	 * @param totalResourceList
	 * @return
	 */
	private List resourceUsingAvgCountList(List<ZykLogEntity> totalLogList,
			List<ZykResourceEntity> totalResourceList) {

		List resultList = new ArrayList();
		// 遍历resourceList 获取media类型
		Set<String> mediaTypeSet = new HashSet<String>();
		for (ZykResourceEntity resource : totalResourceList) {
			String resourceId = resource.getResourceId();
			String mediaType = resource.getMediaType();
			mediaTypeSet.add(resourceId + "::" + mediaType);
		}

		List<ZykResourceEntity> resourceList = new ArrayList<ZykResourceEntity>();
		List<ZykLogEntity> logList = new ArrayList<ZykLogEntity>();
		// resouceList中的resourceId与logList中的objectId关联
		Set<String> mediaCountSet = new HashSet<String>();
		for (ZykLogEntity userLog : totalLogList) {
			if ("资源素材模块".equals(userLog.getObjectType())) {
				logList.add(userLog);
				/*
				 * for(String str : mediaTypeSet){ String[] strs =
				 * str.split("::"); String resourceId = userLog.getObjectId();
				 * if(resourceId.equalsIgnoreCase(strs[0])){
				 * mediaCountSet.add(str); } }
				 */
			}
		}
		double logCount = logList.size();
		double resourceCount = totalResourceList.size();
		double rate = 0;
		String rateStr = "0";
		if (resourceCount != 0) {
			rate = logCount / resourceCount;
			DecimalFormat df = new DecimalFormat("0.00");
			rateStr = df.format(rate);
		}

		// 资源素材浏览和下载频次 List
		/*
		 * Set<String> mediaSet = new HashSet<String>(); for(ZykResourceEntity
		 * resource : totalResourceList){ String mediaType =
		 * resource.getMediaType(); mediaSet.add(mediaType); }
		 * 
		 * //从初始化list中得到前三位数据 List resultList = new ArrayList(); int totalCount
		 * = 0; for(String media : mediaSet){ Map mediaCountMap = new HashMap();
		 * int count = 0; for(String str : mediaCountSet){ String[] strs =
		 * str.split("::"); if(media.equals(strs[1])){ count++; } } totalCount
		 * += count; mediaCountMap.put("media", media);
		 * mediaCountMap.put("count", count); resultList.add(mediaCountMap); }
		 * double total = Double.parseDouble(totalCount+""); double mediaCount =
		 * Double.parseDouble(resultList.size()+""); String avgStr = "0";
		 * if(mediaCount != 0){ double avgCount = total/mediaCount;
		 * DecimalFormat df = new DecimalFormat("0.0"); avgStr =
		 * df.format(avgCount); }
		 * 
		 * //添加百分比统计项 resultList = addRateForMap(totalCount, resultList);
		 * 
		 * Map totalCountMap = new HashMap(); totalCountMap.put("totalCount",
		 * totalCount); resultList.add(totalCountMap);
		 */

		Map avgCountMap = new HashMap();
		avgCountMap.put("media", "平均值");
		avgCountMap.put("avgCount", rateStr);
		resultList.add(avgCountMap);

		return resultList;
	}

	/**
	 * 使用的资源在全部资源的占比
	 * 
	 * @param totalLogList
	 * @param totalResourceList
	 * @return
	 */
	private List resourceUsingRateCountList(List<ZykLogEntity> totalLogList,
			List<ZykResourceEntity> totalResourceList) {
		List resultList = new ArrayList();
		Set<String> usingResourceSet = new HashSet<String>();

		Set<String> actionSet = new HashSet<String>();
		// Set<ZykLogEntity> usingResourceSet = new HashSet<ZykLogEntity>();
		for (ZykLogEntity userLog : totalLogList) {
			if ("资源素材模块".equals(userLog.getObjectType())) {
				usingResourceSet.add(userLog.getObjectId());
				// actionSet.add(userLog.getAction());
				// usingResourceSet.add(userLog);
			}
		}
		double totalCount = totalResourceList.size();
		double usingCount = usingResourceSet.size();
		double rate = 0;
		String rateStr = "0";
		if (totalCount != 0) {
			rate = usingCount / totalCount;
			DecimalFormat df = new DecimalFormat("0.0");
			rateStr = df.format(rate * 100) + "%";
		}

		// int totalCount = 0;
		// for(String action : actionSet){
		// Map actionCountMap = new HashMap();
		// int count = 0;
		// for(ZykLogEntity userLog : usingResourceSet){
		// if(action.equals(userLog.getAction())){
		// count++;
		// }
		// }
		// totalCount += count;
		//
		// actionCountMap.put("action", action);
		// actionCountMap.put("count", count);
		// resultList.add(actionCountMap);
		// }

		// int recourceTotalCount = totalResourceList.size();
		// int noUsingCount = recourceTotalCount-totalCount;

		// double rate = usingCount/totalCount;
		Map noUsingActionMap = new HashMap();
		noUsingActionMap.put("action", "比值");
		noUsingActionMap.put("count", rateStr);
		resultList.add(noUsingActionMap);

		// 添加百分比统计项
		// resultList = addRateForMap(recourceTotalCount, resultList);

		// Map totalCountMap = new HashMap();
		// totalCountMap.put("totalCount", recourceTotalCount);
		// resultList.add(totalCountMap);

		return resultList;
	}

	/**
	 * 不同文件类型资源素材浏览和下载频次
	 * 
	 * @param totalLogList
	 * @param totalResourceList
	 * @return
	 */
	private List resourceUsingAndDownloadCountList(String zykId) {
		List resultList = new ArrayList();
		/*
		 * if(totalLogList.size() == 0 || totalLogList == null){ Map map = new
		 * HashMap(); map.put("NULL", "null"); resultList.add(map); return
		 * resultList; }
		 */

		// 遍历resourceList 获取media类型
		/*
		 * Set<String> mediaTypeSet = new HashSet<String>();
		 * for(ZykResourceEntity resource : totalResourceList){ String
		 * resourceId = resource.getResourceId(); String mediaType =
		 * resource.getMediaType(); mediaTypeSet.add(resourceId+"::"+mediaType);
		 * }
		 * 
		 * //resouceList中的resourceId与logList中的objectId关联，并且过滤出下载资源与浏览资源的数据
		 * Set<String> mediaCountSet = new HashSet<String>(); for(ZykLogEntity
		 * userLog : totalLogList){ if("下载资源".equals(userLog.getAction()) ||
		 * "浏览资源".equals(userLog.getAction())){ for(String str : mediaTypeSet){
		 * String[] strs = str.split("::"); String resourceId =
		 * userLog.getObjectId(); if(resourceId.equalsIgnoreCase(strs[0])){
		 * mediaCountSet.add(str); } } } }
		 */

		List<Object[]> initList = zykLogDao
				.resourceUsingAndDownloadCountList(zykId);

		// 资源素材浏览和下载频次 List
		String mediaTypeStrs = UtilProperties
				.getPropertyZykLog("resource.download.type");
		String[] mediaSet = mediaTypeStrs.split(";");

		int totalCount = 0;
		for (String media : mediaSet) {
			Map mediaCountMap = new HashMap();
			int count = 0;
			for (int i = 0; i < initList.size(); i++) {
				if (media.equals(initList.get(i)[0].toString())) {
					count = Integer.parseInt(initList.get(i)[1] + "");
				}
			}

			/*
			 * for(String str : initList){ String[] strs = str.split("::");
			 * if(media.equals(strs[1])){ count++; } }
			 */
			totalCount += count;

			mediaCountMap.put("media", media);
			mediaCountMap.put("count", count);
			resultList.add(mediaCountMap);
		}

		// resultList = UtilString.Top_N_List(resultList, 3);

		// 添加百分比统计项
		resultList = addRateForMap(totalCount, resultList);

		Map totalCountMap = new HashMap();
		totalCountMap.put("totalCount", totalCount);
		resultList.add(totalCountMap);

		return resultList;
	}

	/**
	 * 资源素材应用情况
	 * 
	 * @param totalLogList
	 * @return
	 */
	private List resourceUsingActionCountList(List<ZykLogEntity> totalLogList) {

		String actionStrs = UtilProperties.getPropertyZykLog("resource.action");
		String[] actionTypeSet = actionStrs.split(";");

		int totalCount = 0;
		List resultList = new ArrayList();
		for (String action : actionTypeSet) {
			Map actionMap = new HashMap();
			int count = 0;
			for (ZykLogEntity userLog : totalLogList) {
				if (userLog.getObjectType().equals("资源素材模块")) {
					if (action.equals(userLog.getAction())) {
						count++;
					}
				}
			}
			totalCount += count;

			actionMap.put("action", action);
			actionMap.put("count", count);
			resultList.add(actionMap);
		}

		// 添加百分比统计项
		resultList = addRateForMap(totalCount, resultList);

		Map totalCountMap = new HashMap();
		totalCountMap.put("totalCount", totalCount);
		resultList.add(totalCountMap);

		return resultList;
	}

	/**
	 * 资源素材使用总次数
	 * 
	 * @param totalLogList
	 * @return
	 */
	private List resourceUsingCountList(List<ZykLogEntity> totalLogList) {

		Set<String> actionTypeSet = new HashSet<String>();
		List<ZykLogEntity> resourceLogList = new ArrayList<ZykLogEntity>();
		for (ZykLogEntity userLog : totalLogList) {
			if ("资源素材模块".equals(userLog.getObjectType())) {
				if (userLog.getAction().length() > 4) {
					actionTypeSet.add(userLog.getAction().substring(0, 4));
				} else {
					actionTypeSet.add(userLog.getAction());
				}
				// actionTypeSet.add(userLog.getAction());
				resourceLogList.add(userLog);
			}
		}

		int totalCount = 0;
		List resultList = new ArrayList();
		for (String action : actionTypeSet) {
			Map actionMap = new HashMap();
			int count = 0;
			for (ZykLogEntity userLog : resourceLogList) {
				String logAction = userLog.getAction();
				if (logAction.length() > 4) {
					logAction = logAction.substring(0, 4);
				}
				if (action.equals(logAction)) {
					count++;
				}
			}
			totalCount += count;

			actionMap.put("action", action);
			actionMap.put("count", count);
			resultList.add(actionMap);
		}

		// 添加百分比统计项
		resultList = addRateForMap(totalCount, resultList);

		Map totalCountMap = new HashMap();
		totalCountMap.put("totalCount", totalCount);
		resultList.add(totalCountMap);

		return resultList;
	}

	/**
	 * 人均活动天数
	 * 
	 * @param totalLogList
	 * @return
	 */
	private List userActiveDateAvgCountList(List<ZykLogEntity> totalLogList) {

		List resultList = new ArrayList();
		// 将logList集合中的userId去重
		Set<ZykLogEntity> activeUserSet = new HashSet<ZykLogEntity>();
		for (ZykLogEntity userLog : totalLogList) {
			activeUserSet.add(userLog);
		}
		// 有活动记录的用户数
		int activeUserCount = activeUserSet.size();
		Map activeUserMap = new HashMap();
		activeUserMap.put("active", "有活动记录的用户数");
		activeUserMap.put("count", activeUserCount);

		// 根据userId与time去除 logList中的重复数据
		// userLogSet中数据格式为 "userId::uesrTime"
		Set<String> userLogSet = new HashSet<String>();
		for (ZykLogEntity userLog : totalLogList) {
			String userIdAndTimeStr = userLog.getUserId() + "::"
					+ userLog.getTime();
			userLogSet.add(userIdAndTimeStr);
		}
		int totalActiveCount = userLogSet.size();
		Map totalDateCountMap = new HashMap();
		totalDateCountMap.put("active", "总的活动天数");
		totalDateCountMap.put("count", totalActiveCount);

		// 人均活动天数
		double totalCount = (Double.parseDouble(totalActiveCount + ""));
		double avtiveCount = (Double.parseDouble(activeUserCount + ""));
		double avg = 0;
		String avgFormat = "0";
		if (activeUserCount != 0) {
			avgFormat = "0";
			DecimalFormat df = new DecimalFormat("0.0");
			avgFormat = df.format(avg);
		}

		Map avgCountMap = new HashMap();
		avgCountMap.put("active", "人均活动天数");
		avgCountMap.put("count", avgFormat);

		resultList.add(activeUserMap);
		resultList.add(totalDateCountMap);
		resultList.add(avgCountMap);
		return resultList;
	}

	/**
	 * 用户活动总次数
	 * 
	 * @param totalLogList
	 * @param totalUserList
	 * @return
	 */
	private List userActiveCountList(List<ZykLogEntity> totalLogList,
			List<ZykUserEntity> totalUserList) {

		Set<String> roleSet = new HashSet<String>();
		for (ZykUserEntity user : totalUserList) {
			roleSet.add(user.getRole());
		}

		// 遍历set集合，根据集合中的userId 查找 userList 添加上userRole
		// userLogCountSet中的数据格式为 "userId::uesrTime::userRole"
		List<String> userLogCountList = new ArrayList<String>();
		for (ZykUserEntity user : totalUserList) {
			for (ZykLogEntity userLog : totalLogList) {
				if (user.getUserId().equalsIgnoreCase(userLog.getUserId())) {
					String str = user.getUserId() + "::" + user.getRole();
					userLogCountList.add(str);
				}
			}
		}

		List resultList = new ArrayList();
		int totalCount = 0;
		for (String role : roleSet) {
			Map roleCountMap = new HashMap();
			int count = 0;
			for (String str : userLogCountList) {
				String[] strs = str.split("::");
				if (role.equals(strs[1])) {
					count++;
				}
			}
			totalCount += count;

			roleCountMap.put("role", role);
			roleCountMap.put("count", count);
			resultList.add(roleCountMap);
		}

		// 添加百分比统计项
		resultList = addRateForMap(totalCount, resultList);

		Map totalCountMap = new HashMap();
		totalCountMap.put("totalCount", totalCount);
		resultList.add(totalCountMap);

		return resultList;

	}

	/**
	 * 用户累计学习天数
	 * 
	 * @param totalLogList
	 * @param totalUserList
	 * @return
	 */
	private List userStudyDateCountList(List<ZykLogEntity> totalLogList,
			List<ZykUserEntity> totalUserList) {

		Set<String> roleSet = new HashSet<String>();
		for (ZykUserEntity user : totalUserList) {
			roleSet.add(user.getRole());
		}

		// 根据userId与time去除 logList中的重复数据
		// userLogSet中数据格式为 "userId::uesrTime"
		Set<String> userLogSet = new HashSet<String>();
		for (ZykLogEntity userLog : totalLogList) {
			String userIdAndTimeStr = userLog.getUserId() + "::"
					+ userLog.getTime();
			userLogSet.add(userIdAndTimeStr);
		}

		// 遍历set集合，根据集合中的userId 查找 userList 添加上userRole
		// userLogCountSet中的数据格式为 "userId::uesrTime::userRole"
		Set<String> userLogCountSet = new HashSet<String>();
		for (ZykUserEntity user : totalUserList) {
			for (String str : userLogSet) {
				String[] strs = str.split("::");
				String userId = strs[0];
				if (userId.equalsIgnoreCase(user.getUserId())) {
					str += "::" + user.getRole();
					userLogCountSet.add(str);
				}
			}
		}

		List resultList = new ArrayList();
		int totalCount = 0;
		for (String role : roleSet) {
			Map roleCountMap = new HashMap();
			int count = 0;
			for (String str : userLogCountSet) {
				String[] strs = str.split("::");
				if (role.equals(strs[2])) {
					count++;
				}
			}
			totalCount += count;

			roleCountMap.put("role", role);
			roleCountMap.put("count", count);
			resultList.add(roleCountMap);
		}

		// 添加百分比统计项
		resultList = addRateForMap(totalCount, resultList);

		Map totalCountMap = new HashMap();
		totalCountMap.put("totalCount", totalCount);
		resultList.add(totalCountMap);

		return resultList;
	}

	/**
	 * 注册用户活动情况
	 * 
	 * @param totalLogList
	 * @return
	 */
	private List userActiveLogCountList(String zykId) {

		List resultList = new ArrayList();
		List<Object[]> initList = zykLogDao.findUserActiveCount(zykId);

		String userActiveStrs = UtilProperties.getPropertyZykLog("user.active");
		String[] userActiveSet = userActiveStrs.split(";");

		DecimalFormat df = new DecimalFormat("0.0");
		for (int i = 0; i < userActiveSet.length; i++) {
			Map activeUserCountMap = new HashMap();
			String active = userActiveSet[i];
			String countStr = initList.get(i) + "";

			double count = 0;
			if (!UtilString.isNullAndEmpty(countStr)) {
				count = Double.parseDouble(countStr);
			}
			String result = "";
			if (i == 1 || i == 2 || i == 3) {
				result = df.format(count * 100) + "%";
			}

			// else if(i == 4){
			// DecimalFormat df = new DecimalFormat("0.0");
			// result = df.format(count) +"天";
			// }

			else {
				String[] strs = countStr.split("\\.");
				result = strs[0];
			}

			activeUserCountMap.put("active", active);
			activeUserCountMap.put("count", result);
			resultList.add(activeUserCountMap);
		}

		// resultList.add(activeUserCountMap);

		/*
		 * int totalCount = 0; //有活动记录 Map activeUserCountMap = new HashMap();
		 * Set<String> activeUserSet = new HashSet<String>(); for(ZykLogEntity
		 * userLog : totalLogList){ activeUserSet.add(userLog.getUserId()); }
		 * totalCount = activeUserSet.size();
		 * 
		 * activeUserCountMap.put("active", "有活动记录");
		 * activeUserCountMap.put("count", activeUserSet.size());
		 * resultList.add(activeUserCountMap);
		 * 
		 * //有资源活动记录 Map resourceUserCountMap = new HashMap(); Set<String>
		 * resourceUserSet = new HashSet<String>(); for(ZykLogEntity userLog :
		 * totalLogList){ if("资源素材模块".equals(userLog.getObjectType())){
		 * resourceUserSet.add(userLog.getUserId()); } }
		 * resourceUserCountMap.put("active", "有资源活动记录");
		 * resourceUserCountMap.put("count", resourceUserSet.size());
		 * resultList.add(resourceUserCountMap);
		 * 
		 * //有课程浏览及学习活动记录 Map courceAndStudyUserCountMap = new HashMap();
		 * Set<String> courceAndStudyUserSet = new HashSet<String>();
		 * for(ZykLogEntity userLog : totalLogList){
		 * if(!("资源素材模块".equals(userLog.getObjectType()))){
		 * courceAndStudyUserSet.add(userLog.getUserId()); } }
		 * courceAndStudyUserCountMap.put("active", "有课程浏览及学习活动记录");
		 * courceAndStudyUserCountMap.put("count",
		 * courceAndStudyUserSet.size());
		 * resultList.add(courceAndStudyUserCountMap);
		 * 
		 * //添加百分比统计项 resultList = addRateForMap(totalCount, resultList);
		 * 
		 * Map totalCountMap = new HashMap(); totalCountMap.put("totalCount",
		 * totalCount); resultList.add(totalCountMap);
		 */

		return resultList;
	}

	/**
	 * 用户行为活动总量
	 * 
	 * @param totalLogList
	 * @return
	 */
	private List userBehaviourCountList(List<ZykLogEntity> totalLogList) {
		List resultList = new ArrayList();
		/*
		 * if(totalLogList.size() == 0 || totalLogList == null){ Map map = new
		 * HashMap(); map.put("NULL", "null"); resultList.add(map); return
		 * resultList; }
		 */

		String behaviourStrs = UtilProperties
				.getPropertyZykLog("user.behaviour");
		String[] behaviourSet = behaviourStrs.split(";");

		int totalCount = 0;
		for (String behaviour : behaviourSet) {
			int count = 0;
			Map behaviourMap = new HashMap();
			for (ZykLogEntity userLog : totalLogList) {
				if (userLog.getObjectType().equals(behaviour)) {
					count++;
				}
			}
			totalCount += count;

			behaviourMap.put("behaviour", behaviour);
			behaviourMap.put("count", count);
			resultList.add(behaviourMap);
		}

		resultList = addRateForMap(totalCount, resultList);
		Map totalCountMap = new HashMap();
		totalCountMap.put("totalCount", totalCount);
		resultList.add(totalCountMap);

		return resultList;

	}

	/**
	 * 用户数量统计
	 * 
	 * @param totalUserList
	 * @return
	 */
	private List userCountList(List<ZykUserEntity> totalUserList) {
		List resultList = new ArrayList();
		// if(totalUserList.size() == 0 || totalUserList == null){
		// Map map = new HashMap();
		// map.put("NULL", "null");
		// resultList.add(map);
		// return resultList;
		// }

		String roleStrs = UtilProperties.getPropertyZykLog("user.role");
		String[] roleSet = roleStrs.split(";");

		int totalCount = 0;
		for (String role : roleSet) {
			int count = 0;
			Map userMap = new HashMap();

			for (ZykUserEntity user : totalUserList) {
				if (role.endsWith(user.getRole())) {
					count++;
				}
			}
			totalCount += count;

			userMap.put("role", role);
			userMap.put("count", count);
			resultList.add(userMap);
		}
		Map totalCountMap = new HashMap();
		totalCountMap.put("totalCount", totalCount);
		resultList.add(totalCountMap);

		// 添加百分比统计项
		resultList = addRateForMap(totalCount, resultList);

		return resultList;
	}

	/**
	 * 用户院校分布统计
	 * 
	 * @param totalUserList
	 * @return
	 */
	private List userInstituteList(List<ZykUserEntity> totalUserList) {

		Set<String> instituteSet = new HashSet<String>();
		for (ZykUserEntity user : totalUserList) {
			if (!UtilString.isNullAndEmpty(user.getInstitute())) {
				instituteSet.add(user.getInstitute());
			}
		}

		List resultList = new ArrayList();
		int totalCount = instituteSet.size();
		// for(String institute : instituteSet){
		// int count = 0;
		// Map instituteMap = new HashMap();
		//
		// for(ZykUserEntity user : totalUserList){
		// if(institute.equals(user.getInstitute())){
		// count++;
		// }
		// }
		// // totalCount += count;
		//
		// instituteMap.put("institute", institute);
		// instituteMap.put("count", count);
		// resultList.add(instituteMap);
		// }
		Map totalCountMap = new HashMap();
		totalCountMap.put("totalCount", totalCount);
		resultList.add(totalCountMap);

		// 添加百分比统计项
		resultList = addRateForMap(totalCount, resultList);

		return resultList;
	}

	/**
	 * 用户地域分布
	 * 
	 * @param totalUserList
	 * @return
	 */
	private List userProvinceList(List<ZykUserEntity> totalUserList) {

		Set<String> provinceSet = new HashSet<String>();
		for (ZykUserEntity user : totalUserList) {
			provinceSet.add(user.getProvince());
		}

		List resultList = new ArrayList();
		int totalCount = provinceSet.size();
		for (String province : provinceSet) {
			int count = 0;
			Map provinceMap = new HashMap();

			for (ZykUserEntity user : totalUserList) {
				if (province.equals(user.getProvince())) {
					count++;
				}
			}
			// totalCount += count;

			provinceMap.put("province", province);
			provinceMap.put("count", count);
			resultList.add(provinceMap);
		}
		Map totalCountMap = new HashMap();
		totalCountMap.put("totalCount", totalCount);
		resultList.add(totalCountMap);

		// 添加百分比统计项
		resultList = addRateForMap(totalCount, resultList);

		return resultList;
	}

	/**
	 * 资源素材总数
	 * 
	 * @param totalResourceList
	 * @return
	 */
	private List resourceCountList(List<ZykResourceEntity> totalResourceList) {
		List resultList = new ArrayList();
		// if(totalResourceList.size() == 0 || totalResourceList == null){
		// Map map = new HashMap();
		// map.put("NULL", "null");
		// resultList.add(map);
		// return resultList;
		// }

		String mediaStrs = UtilProperties.getPropertyZykLog("resource.media");
		String[] mediaSet = mediaStrs.split(";");

		int totalCount = 0;
		for (String media : mediaSet) {
			int count = 0;
			Map resourceCountMap = new HashMap();
			for (ZykResourceEntity resource : totalResourceList) {
				if (media.equals(resource.getMediaType())) {
					count++;
				}
			}
			totalCount += count;

			resourceCountMap.put("media", media);
			resourceCountMap.put("count", count);
			resultList.add(resourceCountMap);
		}

		Map totalCountMap = new HashMap();
		totalCountMap.put("totalCount", totalCount);
		resultList.add(totalCountMap);

		// 为resultList中的map 添加 百分比
		resultList = addRateForMap(totalCount, resultList);

		return resultList;
	}

	/**
	 * 不同教学应用的资源素材分布
	 * 
	 * @param totalResourceList
	 * @return
	 */
	private List instructionCountList(List<ZykResourceEntity> totalResourceList) {

		List resultList = new ArrayList();
		/*
		 * if(totalResourceList.size() == 0 || totalResourceList == null){ Map
		 * map = new HashMap(); map.put("NULL", "null"); resultList.add(map);
		 * return resultList; }
		 */

		String instructionStrs = UtilProperties
				.getPropertyZykLog("resource.instruction");
		String[] instructionSet = instructionStrs.split(";");

		// 统计不同教学应用资源的数量
		List initList = new ArrayList();
		int totalCount = 0;
		for (String instruction : instructionSet) {
			int count = 0;
			Map resourceCountMap = new HashMap();
			for (ZykResourceEntity resource : totalResourceList) {
				if (instruction.equals(resource.getInstruction())) {
					count++;
				}
			}
			totalCount += count;

			resourceCountMap.put("instruction", instruction);
			resourceCountMap.put("count", count);
			initList.add(resourceCountMap);
		}

		initList = addRateForMap(totalCount, initList);
		Map totalCountMap = new HashMap();
		totalCountMap.put("totalCount", totalCount);
		initList.add(totalCountMap);

		return initList;
	}

	/**
	 * 涉及本专业知识点数
	 * 
	 * @param totalResourceList
	 * @return
	 */
	private List knowledgeCountList(List<ZykResourceEntity> totalResourceList) {
		Set<String> knowledgeSet = new HashSet<String>();
		for (ZykResourceEntity resource : totalResourceList) {
			knowledgeSet.add(resource.getKnowledge());
		}
		List<Object> resultList = new ArrayList<Object>();
		for (String knowledge : knowledgeSet) {
			Map knowledgeMap = new HashMap();
			knowledgeMap.put("knowledge", knowledge);

			resultList.add(knowledgeMap);
		}

		Map totalCountMap = new HashMap();
		totalCountMap.put("totalCount", knowledgeSet.size());
		resultList.add(totalCountMap);

		return resultList;
	}

	/**
	 * 课程、微课统计
	 * 
	 * @param totalCourseList
	 * @return
	 */
	private List courseLevelCountList1(List<ZykCourseEntity> totalCourseList) {

		// courseLevelSet存放不同的courseLevel（课程、微课）以及courseType（核心课程）
		Set<String> courseLevelSet = new HashSet<String>();
		for (ZykCourseEntity course : totalCourseList) {
			if (course.getCourseLevel() != null) {
				courseLevelSet.add(course.getCourseLevel());
			}
			if (course.getCourseType() != null) {
				courseLevelSet.add(course.getCourseType());
			}
		}

		List resultList = new ArrayList();
		for (String courseLevel : courseLevelSet) {
			int count = 0;
			Map courseLevelMap = new HashMap();

			// 遍历课程、微课数量
			for (ZykCourseEntity course : totalCourseList) {
				if (courseLevel.equals(course.getCourseLevel())) {
					count++;
				}
			}
			courseLevelMap.put("courseLevel", courseLevel);
			courseLevelMap.put("count", count);

			// 遍历核心课程数量
			for (ZykCourseEntity course : totalCourseList) {
				if (courseLevel.equals(course.getCourseType())) {
					count++;
				}
			}
			courseLevelMap.put("courseLevel", courseLevel);
			courseLevelMap.put("count", count);
			resultList.add(courseLevelMap);
		}
		return resultList;
	}

	/**
	 * 课程数
	 */
	private Map courseCount(List<ZykCourseEntity> totalCourseList) {
		Map resultMap = new HashMap();
		/*
		 * if(totalCourseList == null || totalCourseList.size() == 0){ return
		 * null; }
		 */
		List<ZykCourseEntity> resultList = new ArrayList<ZykCourseEntity>();
		for(ZykCourseEntity course : totalCourseList){
			if(course.getCourseLevel() != null && "课程".equals(course.getCourseLevel())){
				resultList.add(course);
			}
		}
		resultMap.put("totalCount", resultList.size());
		return resultMap;
	}

	/**
	 * 微课和培训项目
	 * 
	 * @param totalCourseList
	 * @return
	 */
	private List courseLevelCountList(List<ZykCourseEntity> totalCourseList) {

		List resultList = new ArrayList();
		/*
		 * if(totalCourseList.size() == 0 || totalCourseList == null){ Map map =
		 * new HashMap(); map.put("NULL", "null"); resultList.add(map); return
		 * resultList; }
		 */

		String courseLevelStrs = UtilProperties
				.getPropertyZykLog("course.level");
		String[] courseLevelSet = courseLevelStrs.split(";");

		int totalCount = 0;
		for (String courseLevel : courseLevelSet) {
			int count = 0;
			Map courseLevelMap = new HashMap();
			for (ZykCourseEntity course : totalCourseList) {
				if (courseLevel.equals(course.getCourseLevel())) {
					count++;
				}
			}
			totalCount += count;
			courseLevelMap.put("courseLevel", courseLevel);
			courseLevelMap.put("count", count);
			resultList.add(courseLevelMap);
		}

		// 为resultList中的map 添加 百分比
		resultList = addRateForMap(totalCount, resultList);

		Map totalCountMap = new HashMap();
		totalCountMap.put("totalCount", totalCount);
		resultList.add(totalCountMap);
		return resultList;
	}

	/**
	 * 不同类型课程分布
	 */
	private List courseTypeCountList(List<ZykCourseEntity> totalCourseList) {
		List resultList = new ArrayList();
		/*
		 * if(totalCourseList.size() == 0 || totalCourseList == null){ Map map =
		 * new HashMap(); map.put("NULL", "null"); resultList.add(map); return
		 * resultList; }
		 */

		String courseTypeStrs = UtilProperties.getPropertyZykLog("course.type");
		String[] courseTypeSet = courseTypeStrs.split(";");

		int totalCount = totalCourseList.size();
		for (String courseType : courseTypeSet) {
			int count = 0;
			Map courseTypeMap = new HashMap();
			for (ZykCourseEntity course : totalCourseList) {
				if (courseType.equals(course.getCourseType())) {
					count++;
				}
			}
			// totalCount += count;
			courseTypeMap.put("courseType", courseType);
			courseTypeMap.put("count", count);
			resultList.add(courseTypeMap);
		}

		// 为resultList中的map 添加 百分比
		resultList = addRateForMap(totalCount, resultList);

		Map totalCountMap = new HashMap();
		totalCountMap.put("totalCount", totalCount);
		resultList.add(totalCountMap);
		return resultList;
	}

	/**
	 * 模块数
	 * 
	 * @param totalCourseList
	 * @param totalRelationList
	 * @return
	 */
	private List courseModuleCountList(
			List<ZykCourseRresourceRelationEntity> totalRelationList,
			List<ZykCourseEntity> totalCourseList) {

		List resultList = new ArrayList();
		// 存放relation表中的courseId
		Set<String> moduleIdSet = new HashSet<String>();
		// 根据relation表中coureseId 对应course表中的fullname
		List<String> courseFullnameList = new ArrayList<String>();

		// 遍历relation表，根据courseId 查询出course表中的fullname
		for (ZykCourseRresourceRelationEntity relation : totalRelationList) {
			for (ZykCourseEntity course : totalCourseList) {
				if ("课程".equals(course.getCourseLevel())
						&& relation.getCourseId().equalsIgnoreCase(
								course.getCourseId())) {
					moduleIdSet.add(relation.getCourseModuleId());
				}
			}
		}

		// 遍历courseId集合，计算出relation表中的数量
		/*
		 * int totalCount = 0;//courseFullnameList.size(); for (int i = 0; i <
		 * moduleIdSet.size(); i++) { Map courseFullnameMap = new HashMap();
		 * String courseFullname = courseFullnameList.get(i); int count = 0;
		 * for(ZykCourseRresourceRelationEntity relation : totalRelationList){
		 * if(moduleIdSet.get(i).equalsIgnoreCase(relation.getCourseId())){
		 * count++; } } totalCount += count;
		 * 
		 * courseFullnameMap.put("courseFullname", courseFullname);
		 * courseFullnameMap.put("count", count);
		 * resultList.add(courseFullnameMap); }
		 */

		Map totalCountMap = new HashMap();
		totalCountMap.put("totalCount", moduleIdSet.size());
		resultList.add(totalCountMap);

		return resultList;
	}

	/**
	 * 课程引用的资源素材占所有资源素材比
	 * 
	 * @param totalCourseList
	 * @param totalRelationList
	 * @return
	 */
	private List courseResourceCountList(List<ZykCourseEntity> totalCourseList,
			List<ZykCourseRresourceRelationEntity> totalRelationList) {
		List resultList = new ArrayList();
		Set<String> sourceIdset = new HashSet<String>();
		for (ZykCourseRresourceRelationEntity relation : totalRelationList) {
			sourceIdset.add(relation.getCourseId());
		}
		double resourceCount = sourceIdset.size();
		double totalCount = totalCourseList.size();

		/*
		 * resourceCount == 0 引用资源素材为0 前台页面不统计
		 */
		double rate = 0;
		String rateArray = "0";
		if (totalCount != 0) {
			rate = resourceCount / totalCount;
			DecimalFormat df = new DecimalFormat("0.0");
			String rateFormat = df.format(rate * 100);
			rateArray = rateFormat + "%";
		}

		Map resourceCountMap = new HashMap();
		resourceCountMap.put("resource", "比值");
		resourceCountMap.put("count", rateArray);
		resultList.add(resourceCountMap);

		/*
		 * resultList = addRateForMap(totalCount, resultList);
		 * 
		 * Map totalMap = new HashMap(); totalMap.put("totalCount", totalCount);
		 * resultList.add(totalMap);
		 */

		return resultList;
	}

	/**
	 * 题库题目总数
	 * 
	 * @param totalQuestionList
	 * @return
	 */
	private Map totalQuestionCount(List<ZykQuestionbankEntity> totalQuestionList) {

		int totalCount = 0;

		for (ZykQuestionbankEntity question : totalQuestionList) {
			totalCount += question.getQuestionNum();
		}

		// if(totalCount == 0){
		// return null;
		// }

		Map resultMap = new HashMap();
		resultMap.put("count", totalCount);

		return resultMap;
	}

	/**
	 * 为resultList中的map 添加 百分比
	 * 
	 * @param totalCount
	 * @param resultList
	 * @return
	 */
	private List addRateForMap(int totalCount, List resultList) {
		double totalCountDouble = Double.parseDouble(totalCount + "");
		String rateStr = null;
		DecimalFormat df = new DecimalFormat("0.0");
		if (resultList.size() != 1) {
			for (int i = 0; i < resultList.size(); i++) {
				Map map = (Map) resultList.get(i);
				String count = (Integer) map.get("count") + "";
				if (!UtilString.isNullAndEmpty(count)) {
					if (totalCount == 0) {
						rateStr = "0";
					} else {
						double countDouble = Double.parseDouble(count);
						double rate = countDouble / totalCountDouble * 100;
						rateStr = df.format(rate) + "%";
					}
					map.put("rate", rateStr);
				}
			}
		}
		return resultList;
	}

	@Override
	public void updateZykSendStatus(String zykId, int status, Timestamp time) {
		List<ZykEntity> zykEntityList = zykDao.getZykEntityByZykId(zykId);
		if (zykEntityList != null && zykEntityList.size() > 0) {
			for (ZykEntity zykEntity : zykEntityList) {
				zykEntity.setSendStatus(status);
				zykEntity.setSendLastTime(time);
				zykDao.update(zykEntity);
			}
		}
	}
}