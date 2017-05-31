package com.asdc.lrm.service.impl;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.asdc.lrm.dao.ReportSummaryDao;
import com.asdc.lrm.dao.ZykCourseDao;
import com.asdc.lrm.dao.ZykDao;
import com.asdc.lrm.dao.ZykResourceDao;
import com.asdc.lrm.dao.ZykUserDao;
import com.asdc.lrm.service.ReportSummaryService;

public class ReportSummaryServiceImpl extends BaseServiceImpl implements ReportSummaryService {
	
	private ZykDao zykDao;
	private ZykResourceDao zykResourceDao;
	private ZykCourseDao zykCourseDao;
	private ZykUserDao zykUserDao;
	private ReportSummaryDao reportSummaryDao;
	public void setReportSummaryDao(ReportSummaryDao reportSummaryDao) {
		this.reportSummaryDao = reportSummaryDao;
	}

	public void setZykDao(ZykDao zykDao) {
		this.zykDao = zykDao;
	}

	public void setZykResourceDao(ZykResourceDao zykResourceDao) {
		this.zykResourceDao = zykResourceDao;
	}

	public void setZykCourseDao(ZykCourseDao zykCourseDao) {
		this.zykCourseDao = zykCourseDao;
	}

	public void setZykUserDao(ZykUserDao zykUserDao) {
		this.zykUserDao = zykUserDao;
	}

	
	@Override
	public List<Object> totality() {
		List<Object> resultList = new LinkedList<Object>();
		//申报专业资源库总数
		int zykTotalCount = 0; 
		zykTotalCount = zykDao.findZykTotalCount();
		resultList.add(0, "申报专业资源库总数");
		resultList.add(1, zykTotalCount);
		
		//建设资源总数
		int resourceTotalCount = 0;
		resourceTotalCount = zykResourceDao.findResourceTotalCount();
		resultList.add(2, "建设资源总数");
		resultList.add(3, resourceTotalCount);
		
		//申报单位数
		int departmentTotalCount = 0;
		departmentTotalCount = zykDao.findZykDepartmentTotalCount();
		resultList.add(4, "申报单位数");
		resultList.add(5, departmentTotalCount);
		
		//开设课程数	
		int courceTotalCount = 0;
		courceTotalCount = zykCourseDao.findCourseTotalCount();
		resultList.add(6, "开设课程数");
		resultList.add(7, courceTotalCount);
		
		//注册用户数
		int userTotalCount = 0;
		userTotalCount = zykUserDao.findUserTotalCount();
		resultList.add(8, "注册用户数");
		resultList.add(9, userTotalCount);
		
		return resultList;
	}

	@Override
	public List<Map<String,Object>> reportSummary(int pageIndex, int count, int status) {
		List<Map<String,Object>> summary = reportSummaryDao.reportSummary(pageIndex, count, status);
		return summary;
	}

	/**
	 * 资源库申报情况分布
	 */
	@Override
	public List<String> distribution() {
		List<String> resultList = new ArrayList<String>();
		List<Object[]> zykList = zykDao.getZykGroupByProvince();
		
		int i = 0;
		String maxCount = zykList.get(0)[1]+"";
		StringBuffer data = new StringBuffer("[");
		for(Object[] obj : zykList){
			i++;
			String provinceStr = (String) obj[0];
			String province = provinceStr.substring(0, 2);
			int count = Integer.parseInt(obj[1]+"");
			
			if("黑龙".equals(province)){
				province = "黑龙江";
			}
			if("内蒙".equals(province)){
				province = "内蒙古";
			}
			
			data.append("{name:'");
			data.append(province);
			data.append("',value:");
			data.append(count);
			
			if(i < zykList.size()){
				data.append("},");
			}else{
				data.append("}");
			}
		}
		data.append("]");
		
		resultList.add(data.toString());
		resultList.add(maxCount);
		return resultList;
	}

	/**
	 * 资源库总体情况
	 * 页面右边展示table
	 * @return
	 */
	@Override
	public List<String> totalityForTable() {
		List<String> resultList = new ArrayList<String>();
		List<Object[]> zykList = zykDao.getZykGroupByProvince();
		
		Double totalCount = 0.0;
		for(Object[] obj : zykList){
			int count = Integer.parseInt(obj[1]+"");
			totalCount += count;
		}
		
		DecimalFormat df = new DecimalFormat("#.#");
		for(int i = 0; i < zykList.size(); i++){
			Object[] obj = zykList.get(i);
			String provinceStr = (String) obj[0];
			String province = provinceStr.substring(0, 2);
			double count = Double.parseDouble(obj[1]+"");
			Double rateDuoble = count/totalCount *100;
			String rate = df.format(rateDuoble)+"%";
			if("黑龙".equals(province)){
				province = "黑龙江";
			}
			if("内蒙".equals(province)){
				province = "内蒙古";
			}
			String cssStr = "<td width='120px;'><div style='height:10px; background-color: #006EDD; width: "+5*rateDuoble+"px;'></div></td>";
			resultList.add(i+1+"");
			resultList.add(province+cssStr);
			resultList.add(rate);
		}
		
		return resultList;
	}
}
