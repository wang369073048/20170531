package com.asdc.lrm.service.impl;

import java.math.BigInteger;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import net.sf.json.JSONArray;

import com.asdc.lrm.dao.ZykCourseDao;
import com.asdc.lrm.dao.ZykDatachangeLogDao;
import com.asdc.lrm.dao.ZykResourceDao;
import com.asdc.lrm.dao.ZykUserDao;
import com.asdc.lrm.entity.ZykCourseEntity;
import com.asdc.lrm.entity.ZykDatachangeLogEntity;
import com.asdc.lrm.entity.ZykResourceEntity;
import com.asdc.lrm.entity.ZykUserEntity;
import com.asdc.lrm.service.ReportUpdateService;
import com.asdc.lrm.util.UtilDate;

public class ReportUpdateServiceImpl implements ReportUpdateService {

	private ZykUserDao zykUserDao;
	private ZykDatachangeLogDao zykDatachangeLogDao;

	public void setZykDatachangeLogDao(ZykDatachangeLogDao zykDatachangeLogDao) {
		this.zykDatachangeLogDao = zykDatachangeLogDao;
	}

	public void setZykUserDao(ZykUserDao zykUserDao) {
		this.zykUserDao = zykUserDao;
	}

	private ZykCourseDao zykCourseDao;

	public void setZykCourseDao(ZykCourseDao zykCourseDao) {
		this.zykCourseDao = zykCourseDao;
	}

	private ZykResourceDao zykResourceDao;

	public void setZykResourceDao(ZykResourceDao zykResourceDao) {
		this.zykResourceDao = zykResourceDao;
	}

	/**
	 * 同比上一年用户注册增长率
	 */
	@Override
	public List<String> reportUserUpdate(String zykId) {
		int last_Year = UtilDate.getYear() - 1;
		List<String> userCount = new ArrayList<String>();
		List<Object[]> userList = zykUserDao.findUserCountGroupByYear(zykId,
				last_Year);
		String titleArray = "['']";
		String dataArray = "['']";
		String rateArray = "''";
		if (userList.size() == 0 || userList == null) {
			userCount.add(titleArray);
			userCount.add(dataArray);
			userCount.add(rateArray);
			return userCount;
		}

		double rate = 0;
		double lastCount = 0;
		double curCount = 0;
		if (userList.size() == 2) {
			lastCount = Integer.parseInt(userList.get(0)[1] + "");
			curCount = Double.parseDouble(userList.get(1)[1] + "");
		} else if (userList.size() == 1) {
			if (Integer.parseInt(userList.get(0)[0] + "") == UtilDate.getYear()) {
				curCount = Integer.parseInt(userList.get(0)[1] + "");
			} else {
				lastCount = Integer.parseInt(userList.get(0)[1] + "");
			}
		}

		rate = curCount / lastCount;
		DecimalFormat df = new DecimalFormat("#.#");
		String rateFormat = df.format(rate * 100);
		rateArray = rateFormat + "%";

		String lastYear = UtilDate.getYear() - 1 + "";
		String currentYear = UtilDate.getYear() + "";

		titleArray = "['" + lastYear + "年','" + currentYear + "年']";
		dataArray = "[" + lastCount + "," + curCount + "]";

		userCount.add(titleArray);
		userCount.add(dataArray);
		userCount.add("'" + rateArray + "'");
		return userCount;
	}
	
	/**
	 * 历年用户数据变化
	 */
	@Override
	public List<String> reportUserChange(String zykId) {
		List<String> resultList = new ArrayList<String>();
		List<ZykUserEntity> totalUserList = zykUserDao.findAllUserByZykId(zykId);
		List<ZykDatachangeLogEntity> totalDatachangeLogList = zykDatachangeLogDao.findDatachangeLogByZykId(zykId);
		int curYear = UtilDate.getYear();
		int beginYear = curYear - 4;
		int endYear = curYear;
		
		//initList存放初始化数据
		//偶数位year 奇数位count
		List<Object> initAddList = new ArrayList<Object>();
		List<Object> initUpdateList = new ArrayList<Object>();
		List<Object> initDeleteList = new ArrayList<Object>();
		
		Calendar createdCal = Calendar.getInstance();
		Calendar modifiedCal = Calendar.getInstance();
		Calendar deleteCal = Calendar.getInstance();
		for(int year = beginYear; year <= endYear ; year++){
			int addCount = 0;
			int updataCount = 0;
			int deleteCount = 0;
			for(ZykUserEntity user : totalUserList){
				if(user.getCreatedTime() != null){
					createdCal.setTime(user.getCreatedTime());
					int createdYear = createdCal.get(Calendar.YEAR);
					if(createdYear == 2020){
						createdYear = curYear;
					}
					if(year == createdYear){
						addCount ++;
					}
				}
				if(user.getModifiedTime() != null){
					modifiedCal.setTime(user.getModifiedTime());
					int modifiedYear = createdCal.get(Calendar.YEAR);
					if(modifiedYear == 2020){
						modifiedYear = curYear;
					}
					if(year == modifiedYear){
						updataCount ++;
					}
				}
			}
			for(ZykDatachangeLogEntity changeLog : totalDatachangeLogList){
				if("用户".equals(changeLog.getObjectType()) && "删除".equals(changeLog.getAction())){
					if(changeLog.getTime() != null){
						deleteCal.setTime(changeLog.getTime());
						int time = deleteCal.get(Calendar.YEAR);
						if(time == 2020){
							time = curYear;
						}
						if(year == time){
							deleteCount ++;
						}
					}
				}
			}
			
			initAddList.add(year);
			initAddList.add(addCount);
			initUpdateList.add(year);
			initUpdateList.add(updataCount);
			initDeleteList.add(year);
			initDeleteList.add(0-deleteCount);
		}
		resultList.addAll(ListtoStringFormat(initAddList));
		resultList.addAll(ListtoStringFormat(initUpdateList));
		resultList.addAll(ListtoStringFormat(initDeleteList));
		return resultList;
	}
	
	/**
	 * 历年课程数据变化
	 */
	@Override
	public List<String> reportCourseChange(String zykId) {
		List<String> resultList = new ArrayList<String>();
		List<ZykCourseEntity> totalCourseList = zykCourseDao.findAllCourseByZykId(zykId);
		List<ZykDatachangeLogEntity> totalDatachangeLogList = zykDatachangeLogDao.findDatachangeLogByZykId(zykId);
		int curYear = UtilDate.getYear();
		int beginYear = curYear - 4;
		int endYear = curYear;
		
		//initList存放初始化数据
		//偶数位year 奇数位count
		List<Object> initAddList = new ArrayList<Object>();
		List<Object> initUpdateList = new ArrayList<Object>();
		List<Object> initDeleteList = new ArrayList<Object>();
		
		Calendar createdCal = Calendar.getInstance();
		Calendar modifiedCal = Calendar.getInstance();
		Calendar deleteCal = Calendar.getInstance();
		for(int year = beginYear; year <= endYear ; year++){
			int addCount = 0;
			int updataCount = 0;
			int deleteCount = 0;
			for(ZykCourseEntity course : totalCourseList){
				if(course.getCreatedTime() != null){
					createdCal.setTime(course.getCreatedTime());
					int createdYear = createdCal.get(Calendar.YEAR);
					if(createdYear == 2020){
						createdYear = curYear;
					}
					if(year == createdYear){
						addCount ++;
					}
				}
				if(course.getModifiedTime() != null){
					modifiedCal.setTime(course.getModifiedTime());
					int modifiedYear = createdCal.get(Calendar.YEAR);
					if(modifiedYear == 2020){
						modifiedYear = curYear;
					}
					if(year == modifiedYear){
						updataCount ++;
					}
				}
			}
			for(ZykDatachangeLogEntity changeLog : totalDatachangeLogList){
				if("课程".equals(changeLog.getObjectType()) && "删除".equals(changeLog.getAction())){
					if(changeLog.getTime() != null){
						deleteCal.setTime(changeLog.getTime());
						int time = deleteCal.get(Calendar.YEAR);
						if(time == 2020){
							time = curYear;
						}
						if(year == time){
							deleteCount ++;
						}
					}
				}
			}
			
			initAddList.add(year);
			initAddList.add(addCount);
			initUpdateList.add(year);
			initUpdateList.add(updataCount);
			initDeleteList.add(year);
			initDeleteList.add(0-deleteCount);
		}
		resultList.addAll(ListtoStringFormat(initAddList));
		resultList.addAll(ListtoStringFormat(initUpdateList));
		resultList.addAll(ListtoStringFormat(initDeleteList));
		return resultList;
	}
	
	/**
	 * 历年资源数据变化
	 */
	@Override
	public List<String> reportResourceChange(String zykId) {
		List<String> resultList = new ArrayList<String>();
		List<ZykResourceEntity> totalResourceList = zykResourceDao.getZykResourceByZykId(zykId);
		List<ZykDatachangeLogEntity> totalDatachangeLogList = zykDatachangeLogDao.findDatachangeLogByZykId(zykId);
		int curYear = UtilDate.getYear();
		int beginYear = curYear - 4;
		int endYear = curYear;
		
		//initList存放初始化数据
		//偶数位year 奇数位count
		List<Object> initAddList = new ArrayList<Object>();
		List<Object> initUpdateList = new ArrayList<Object>();
		List<Object> initDeleteList = new ArrayList<Object>();
		
		Calendar createdCal = Calendar.getInstance();
		Calendar modifiedCal = Calendar.getInstance();
		Calendar deleteCal = Calendar.getInstance();
		for(int year = beginYear; year <= endYear ; year++){
			int addCount = 0;
			int updataCount = 0;
			int deleteCount = 0;
			for(ZykResourceEntity resource : totalResourceList){
				if(resource.getCreatedTime() != null){
					createdCal.setTime(resource.getCreatedTime());
					int createdYear = createdCal.get(Calendar.YEAR);
					if(createdYear == 2020){
						createdYear = curYear;
					}
					if(year == createdYear){
						addCount ++;
					}
				}
				if(resource.getModifiedTime() != null){
					modifiedCal.setTime(resource.getModifiedTime());
					int modifiedYear = createdCal.get(Calendar.YEAR);
					if(modifiedYear == 2020){
						modifiedYear = curYear;
					}
					if(year == modifiedYear){
						updataCount ++;
					}
				}
			}
			for(ZykDatachangeLogEntity changeLog : totalDatachangeLogList){
				if("资源".equals(changeLog.getObjectType()) && "删除".equals(changeLog.getAction())){
					if(changeLog.getTime() != null){
						deleteCal.setTime(changeLog.getTime());
						int time = deleteCal.get(Calendar.YEAR);
						if(time == 2020){
							time = curYear;
						}
						if(year == time){
							deleteCount ++;
						}
					}
				}
			}
			
			initAddList.add(year);
			initAddList.add(addCount);
			initUpdateList.add(year);
			initUpdateList.add(updataCount);
			initDeleteList.add(year);
			initDeleteList.add(0-deleteCount);
		}
		resultList.addAll(ListtoStringFormat(initAddList));
		resultList.addAll(ListtoStringFormat(initUpdateList));
		resultList.addAll(ListtoStringFormat(initDeleteList));
		return resultList;
	}

	/**
	 * 根据传入的list将偶数位的数值与奇数位的年份转换格式
	 * @param initList
	 * @return
	 */
	private List<String> ListtoStringFormat(List<Object> initList){
		List<String> resultList = new ArrayList<String>();
		StringBuilder titleStr = new StringBuilder("['");
		StringBuilder dataStr = new StringBuilder("['");
		for (int i = 0; i < initList.size(); i = i+2) {
			titleStr.append(initList.get(i)+"年");
			dataStr.append(initList.get(i+1));
			if(i+2 < initList.size()){
				titleStr.append("','");
				dataStr.append("','");
			}else{
				titleStr.append("'");
				dataStr.append("'");
			}
			
		}
		titleStr.append("]");
		dataStr.append("]");
		resultList.add(titleStr.toString());
		resultList.add(dataStr.toString());
		return resultList;
	}
	
	/**
	 * 同比上一年课程更新率
	 */
	public List<String> reportCourseUpdate(String zykId) {

		int last_Year = UtilDate.getYear() - 1;
		List<String> courseCountRate = new ArrayList<String>();
		List<Object[]> courseList = zykCourseDao.findCourseCountGroupByYear(
				zykId, last_Year);

		String titleArray = "['']";
		String dataArray = "['']";
		String rateArray = "''";
		if (courseList.size() == 0 || courseList == null) {
			courseCountRate.add(titleArray);
			courseCountRate.add(dataArray);
			courseCountRate.add(rateArray);
			return courseCountRate;
		}

		double rate = 0;
		double lastCount = 0;
		double curCount = 0;
		if (courseList.size() == 2) {
			lastCount = Integer.parseInt(courseList.get(0)[1] + "");
			curCount = Double.parseDouble(courseList.get(1)[1] + "");
		} else if (courseList.size() == 1) {
			if (Integer.parseInt(courseList.get(0)[0] + "") == UtilDate
					.getYear()) {
				curCount = Integer.parseInt(courseList.get(0)[1] + "");
			} else {
				lastCount = Integer.parseInt(courseList.get(0)[1] + "");
			}
		}

		rate = curCount / lastCount;
		DecimalFormat df = new DecimalFormat("#.#");
		String rateFormat = df.format(rate * 100);
		rateArray = rateFormat + "%";

		String lastYear = UtilDate.getYear() - 1 + "";
		String currentYear = UtilDate.getYear() + "";

		titleArray = "['" + lastYear + "年','" + currentYear + "年']";
		dataArray = "[" + lastCount + "," + curCount + "]";

		courseCountRate.add(titleArray);
		courseCountRate.add(dataArray);
		courseCountRate.add("'" + rateArray + "'");
		return courseCountRate;
	}

	private String getUpdateRate(long currentCount, long lastCount) {
		double rate = ((double) currentCount - (double) lastCount)
				/ (double) lastCount;
		DecimalFormat df = new DecimalFormat("#.#");
		String rateFormat = df.format(rate * 100);
		return rateFormat + "%";
	}

	/**
	 * 同比上一年资源更新率
	 */
	public List<String> reportResourceLastYearUpdate(String zykId) {
		List<String> resourceCountRate = new ArrayList<String>();
		int curYear = UtilDate.getYear();
		List<Object[]> resourceList = zykResourceDao.findResourceCountGroupByYear(zykId, curYear);
		String titleArray = "['']";
		String dataArray = "['']";
		String rateArray = "''";
		if(resourceList == null || resourceList.size() == 0){
			resourceCountRate.add(titleArray);
			resourceCountRate.add(dataArray);
			resourceCountRate.add(rateArray);
			return resourceCountRate;
		}
		double rate = 0;
		double lastCount = 0;
		double curCount = 0;
		if (resourceList.size() >= 2) {
			List<Integer> years = new ArrayList<Integer>();
			List<String> titles = new ArrayList<String>();
			List<String> countsString = new ArrayList<String>();
			List<Long> counts = new ArrayList<Long>();
			StringBuilder rates = new StringBuilder("");
			for (Object[] rets : resourceList) {
				years.add((Integer.parseInt(rets[0]+"")));
				if (rets[1] instanceof BigInteger) {
					BigInteger bi = (BigInteger) rets[1];
					counts.add(bi.longValue());
				} else {
					counts.add((Long) rets[1]);
				}

			}

			for (int i = 0; i < years.size(); i++) {
				int year = years.get(i);
				long count = counts.get(i);
				if (i > 0) {
					StringBuilder rateString = new StringBuilder("")
							.append(years.get(i - 1))
							.append("年到")
							.append(year)
							.append("年更新率：")
							.append(this.getUpdateRate(count, counts.get(i - 1)));
					if (rates.length() > 0) {
						rates.append(", ").append(rateString.toString());
					} else {
						rates.append(rateString.toString());
					}
				}
				titles.add(year + "年");
				countsString.add(Long.toString(count));

			}
			resourceCountRate.add(JSONArray.fromObject(titles).toString());
			resourceCountRate
					.add(JSONArray.fromObject(countsString).toString());

			resourceCountRate.add("'" + rates.toString() + "'");
		} else if (resourceList.size() == 1) {
			if (Integer.parseInt(resourceList.get(0)[0] + "") == UtilDate
					.getYear()) {
				curCount = Integer.parseInt(resourceList.get(0)[1] + "");
			} else {
				lastCount = Integer.parseInt(resourceList.get(0)[1] + "");
			}
			rate = (curCount - lastCount) / lastCount;
			DecimalFormat df = new DecimalFormat("#.#");
			String rateFormat = df.format(rate * 100);
			rateArray = rateFormat + "%";

			String lastYear = UtilDate.getYear() - 1 + "";
			String currentYear = UtilDate.getYear() + "";

			titleArray = "['" + lastYear + "年','" + currentYear + "年']";
			dataArray = "[" + lastCount + "," + curCount + "]";

			resourceCountRate.add(titleArray);
			resourceCountRate.add(dataArray);
			resourceCountRate.add("'" + lastYear + "年到" + currentYear + "年更新率："
					+ rateArray + "'");
		}

		return resourceCountRate;
	}

	/**
	 * 当年资源素材更新数与资源素材总数的比值
	 */
	public List<String> reportResourceTotalCountUpdate(String zykId) {

		List<String> recourseRateList = new ArrayList<String>();

		int currentYear = UtilDate.getYear();
		List<Object[]> currtentYearList = zykResourceDao
				.findResourceCountByYear(zykId, currentYear);
		List<Object[]> totalResourceList = zykResourceDao
				.findResourceCountByYear(zykId, 0);

		String titleArray = "['']";
		String dataArray = "['']";
		String rateArray = "''";

		double rate = 0;
		double currentCount = Integer.parseInt(currtentYearList.get(0) + "");
		double totalCount = Double.parseDouble(totalResourceList.get(0) + "");

		if (totalCount == 0) {
			recourseRateList.add(titleArray);
			recourseRateList.add(dataArray);
			recourseRateList.add(rateArray);
			recourseRateList.add(rateArray);
			return recourseRateList;
		}

		rate = currentCount / totalCount;
		DecimalFormat df = new DecimalFormat("#.#");
		String rateFormat = df.format(rate * 100);
		rateArray = rateFormat + "%";

		titleArray = "['" + currentYear + "年数量','资源总数']";
		dataArray = "[" + currentCount + "," + totalCount + "]";

		recourseRateList.add(titleArray);
		recourseRateList.add(dataArray);
		recourseRateList.add("'" + rateArray + "'");
		recourseRateList.add("'" + currentYear + "'");
		return recourseRateList;
	}
}