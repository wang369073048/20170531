package com.asdc.lrm.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.asdc.lrm.dao.ZykLogDao;
import com.asdc.lrm.dao.ZykQuestionbankDao;
import com.asdc.lrm.dao.ZykResourceDao;
import com.asdc.lrm.dao.ZykUserDao;
import com.asdc.lrm.service.ReportApplicationService;
import com.asdc.lrm.util.UtilCharts;
import com.asdc.lrm.util.UtilProperties;
import com.asdc.lrm.util.UtilString;

public class ReportApplicationServiceImpl implements ReportApplicationService {

	private ZykLogDao zykLogDao;
	private ZykResourceDao zykResourceDao;
	private ZykUserDao zykUserDao;
	private ZykQuestionbankDao zykQuestionbankDao;
	public void setZykLogDao(ZykLogDao zykLogDao) {
		this.zykLogDao = zykLogDao;
	}
	public void setZykResourceDao(ZykResourceDao zykResourceDao) {
		this.zykResourceDao = zykResourceDao;
	}
	public void setZykUserDao(ZykUserDao zykUserDao) {
		this.zykUserDao = zykUserDao;
	}
	public void setZykQuestionbankDao(ZykQuestionbankDao zykQuestionbankDao) {
		this.zykQuestionbankDao = zykQuestionbankDao;
	}
	
	
	@Override
	public Map<String, Object> reportUserData(String zykId, String beginDate, String endDate) {
		Map<String, Object> initMap = zykLogDao.getObjectTypeUserCountMap(zykId,beginDate,endDate);
		Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
		String behaviourStr = UtilProperties.getPropertyZykLog("user.behaviour");
		String[] behaviourSet = behaviourStr.split(";");
		for (String behaviour : behaviourSet) {
			int count = 0;
			if(initMap.containsKey(behaviour)){
				count = Integer.parseInt(initMap.get(behaviour).toString());
			}
			resultMap.put(behaviour, count);
		}
		return resultMap;
	}
	
	@Override
	public Map<String,Object> reportCourseCount(String zykId, String beginDate,
			String endDate) {
		return zykLogDao.getLogCourseCountMap(zykId, beginDate, endDate, "课程模块",null);
	}


	
	public Map<String,Object> reportTopThreeCourseCount(String zykId, String beginDate,
			String endDate) {
		Map<String, Object> courseCount = zykLogDao.getLogCourseCountMap(zykId, beginDate, endDate, "课程模块","4");
		int count = 4;
		while(UtilCharts.getTopThreeCount(courseCount)==null){
			count += 3;
			courseCount = zykLogDao.getLogCourseCountMap(zykId, beginDate, endDate, "课程模块",count+"");
		}
		return courseCount;
	}

	@Override
	public List<String> reportTopThreeResourceCount(String zykId, String beginDate,	String endDate) {
		List<String> resultList = new ArrayList<String>();
		List<Object[]> initList = zykLogDao.getLogCourseCountList(zykId, beginDate, endDate);
		int totalCount = UtilString.TotalCount_ForObject(initList);
//		List<Object[]> top3_List = UtilString.Top_N_List_ForObject(initList, 3);
//		initList = UtilString.SortList_ForObject(initList);
		resultList = UtilString.generateTitleAndValue(initList);
		resultList.add(totalCount+"");
		return resultList;
	}
	
	@Override
	public Map<String,Object> reportResourceCount(String zykId, String beginDate,
			String endDate) {
		String resource = "资源素材模块";
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("浏览资源", zykLogDao.getLogCount(zykId, beginDate, endDate, resource, "浏览资源"));
		map.put("评价资源", zykLogDao.getLogCount(zykId, beginDate, endDate, resource, "评价资源"));
		map.put("分享资源", zykLogDao.getLogCount(zykId, beginDate, endDate, resource, "分享资源"));
		map.put("下载资源", zykLogDao.getLogCount(zykId, beginDate, endDate, resource, "下载资源"));
		return map;
	}

	@Override
	public Map<String,Object> reportActivedUserCount(String zykId, String beginDate,
			String endDate) {
		return zykLogDao.getActivedUserCountMap(zykId, beginDate, endDate, null, null, null);
	}

	@Override
	public List<Map<String,Object>> reportUsedResourceRatio(String zykId,
			String beginDate, String endDate) {
		int resourceTotal = zykResourceDao.getZykResourceCount(zykId,null,null);
		Map<String, Object> actionCountMap = zykLogDao.getActionCountMap(zykId, beginDate, endDate, "资源素材模块",null);
		List<String> data = UtilCharts.generatePieChartData(actionCountMap);
		Map<String, Object> noUsedMap = new HashMap<String, Object>();
		noUsedMap.put("未被使用的资源素材", (resourceTotal-Integer.valueOf(data.get(3))));
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		list.add(actionCountMap);
		list.add(noUsedMap);
		return list;
	}
	
	@Override
	public Map<String, Object> reportUsedResourceAVG(String zykId,
			String beginDate, String endDate) {
		Map<String, Object> countMap = zykLogDao.getLogResourceCountMap(zykId, beginDate, endDate, "资源素材模块", null);
		int total = zykResourceDao.getZykResourceCount(zykId, null,null);
		return UtilCharts.getDataAVG(countMap, total,"总资源素材数");
	}
	
	
	@Override
	public Map<String, Object> reportActionMapByObjectType(String zykId,
			String beginDate, String endDate, String objectType,String... actions) {
		return zykLogDao.getActionCountMap(zykId, beginDate, endDate, objectType, actions);
	}
	@Override
	public Map<String, Object> reportActivedUserLogCount(String zykId,
			String beginDate, String endDate) {
		Map<String,Object> map = new HashMap<String, Object>();
		int userCount = zykUserDao.getUsersCount(zykId, null, null);
		int activedUserCount = zykLogDao.getActivedUserCount(zykId, beginDate, endDate, null, null);
		int resourceUserCount = zykLogDao.getActivedUserCount(zykId, beginDate, endDate, "资源素材模块", "1");
		int courseUserCount = zykLogDao.getActivedUserCount(zykId, beginDate, endDate, "资源素材模块", "2");
		map.put("有活动记录", activedUserCount);
		map.put("有资源活动记录", resourceUserCount);
		map.put("有课程浏览及学习活动记录", courseUserCount);
		map.put("total", userCount);
		return map;
	}
	@Override
	public Map<String,Object> reportActivedDateUserAVG(String zykId,
			String beginDate, String endDate) {
		Map<String,Object> map = new LinkedHashMap<String, Object>();
		Map<String, Object> countMap = zykLogDao.getActivedUserDateCountMap(zykId, beginDate, endDate, null, null, null);
		int days = UtilCharts.getTotalCountByMap(countMap);
		int count = zykLogDao.getActivedUserCount(zykId, beginDate, endDate, null, null);
		map.put("人均活动天数", Math.floor((double)days/count*10)/10);
		map.put("活动记录的用户数",count );
		map.put("总的活动天数", days);
		return map;
	}
	@Override
	public Map<String, Object> reportCitedquesCount(String zykId,
			String beginDate, String endDate) {
		return zykQuestionbankDao.getCitedQuesCountMap(zykId);
	}
	@Override
	public Map<String, Object> reportQuesUsingCount(String zykId,
			String beginDate, String endDate) {
		return zykQuestionbankDao.getQuesUsingCountMap(zykId);
	}
	
	@Override
	public Map<String, Object> reportAccumulatorUserDateCount(String zykId,
			String beginDate, String endDate) {
		Map<String,Object> map = new LinkedHashMap<String, Object>();
		map.put("社会学习者", zykLogDao.getActivedUserDateCountByRole(zykId, beginDate, endDate, "社会学习者"));
		map.put("教师", zykLogDao.getActivedUserDateCountByRole(zykId, beginDate, endDate, "教师"));
		map.put("学生", zykLogDao.getActivedUserDateCountByRole(zykId, beginDate, endDate, "学生"));
		map.put("企业用户", zykLogDao.getActivedUserDateCountByRole(zykId, beginDate, endDate, "企业用户"));
		map.put("其他", zykLogDao.getActivedUserDateCountByRole(zykId, beginDate, endDate, "其他"));
		return map;
	}

	@Override
	public Map<String, Object> reportExamActivedCount(String zykId,
			String beginDate, String endDate) {
		Map<String,Object> map = new LinkedHashMap<String, Object>();
		map.put("发布考试",zykLogDao.getLogCount(zykId, beginDate, endDate, "考试模块", "发布考试"));
		map.put("参加考试",zykLogDao.getLogCount(zykId, beginDate, endDate, "考试模块", "参加考试"));
		return UtilCharts.getSortedValueMap(map, "2");
	}
	@Override
	public Map<String, Object> reportForumActivedCount(String zykId,
			String beginDate, String endDate) {
		Map<String,Object> map = new LinkedHashMap<String, Object>();
		map.put("发帖数",zykLogDao.getLogCount(zykId, beginDate, endDate, "论坛模块", "发帖"));
		map.put("读贴数",zykLogDao.getLogCount(zykId, beginDate, endDate, "论坛模块", "读贴"));
		return UtilCharts.getSortedValueMap(map, "2");
	}
	@Override
	public Map<String, Object> reportWorkActivedCount(String zykId,
			String beginDate, String endDate) {
		Map<String,Object> map = new LinkedHashMap<String, Object>();
		map.put("发布作业",zykLogDao.getLogCount(zykId, beginDate, endDate, "作业模块", "发布作业"));
		map.put("提交作业",zykLogDao.getLogCount(zykId, beginDate, endDate, "作业模块", "提交作业"));
		map.put("批改作业",zykLogDao.getLogCount(zykId, beginDate, endDate, "作业模块", "批改作业"));
		return UtilCharts.getSortedValueMap(map, "2");
	}
	
	/**
	 * 资源素材应用情况
	 */
	@Override
	public List<String> reportResourceActionCount(String zykId, String beginDate, String endDate) {
		List<Object[]> resourceActionList = zykLogDao.findResourceActionCount(zykId, beginDate, endDate);
		List<String> resourceActionCountList = new ArrayList<String>();

		String titleStr = "['浏览资源','评价资源','分享资源','下载资源']";
		int totalCount = 0;
		StringBuffer valueStr = new StringBuffer("[");
		for(int i = 0; i < resourceActionList.size(); i++){
			int count = Integer.parseInt(resourceActionList.get(i)+"");
			totalCount += count;
			valueStr.append("'"+ resourceActionList.get(i) +"'");
			
			if((i+1) < resourceActionList.size()){
				valueStr.append(",");
			}
		}
		valueStr.append("]");
		
		resourceActionCountList.add(titleStr);
		resourceActionCountList.add(valueStr.toString());
		resourceActionCountList.add(totalCount+"");
		return resourceActionCountList;
	}
	
}
