package com.asdc.lrm.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.asdc.lrm.dao.ReportSummaryDao;
import com.asdc.lrm.util.UtilString;

public class ReportSummaryDaoImpl extends HibernateDaoSupport implements ReportSummaryDao{

	public List<Map<String,Object>> reportSummary(int pageIndex, int count, int status) {
		final StringBuilder sql = new StringBuilder();
		sql.append("select z.zykid,z.fullname,z.SpecialtyCategory,z.SpecialtyName,z.instituteInCharge,r.countresource,c.countcourse, ");
		sql.append(" t.countuser, l.countlog,crr.countmodules,q.countquestion, q.countsubquestion, q.countobjquestion, q.countcitiedquestion, ");
		sql.append(" q.countusingquestion, z.modifiedDate,z.Status from ");
		
		sql.append(" (select zykid,fullname,SpecialtyCategory,SpecialtyName,group_concat(instituteInCharge) as instituteInCharge,modifiedDate,Status from zyk_zyk ");	
		if(status != 0){
			sql.append(" where Status="+status+" ");
		}
		sql.append("group by zykid order by CONVERT(fullname USING gbk) COLLATE gbk_chinese_ci ASC limit ");
		
		sql.append(pageIndex).append(",").append(count);
		sql.append(" )  z left join ");
		sql.append(" (select z.zykid,count(u.userid) as countuser from (SELECT DISTINCT zykId , Fullname from zyk_zyk " );
		if(status != 0){
			sql.append(" where Status="+status+" ");
		}
		sql.append(" ) z left join zyk_user u on z.zykid = u.zykid group by z.zykid order by CONVERT(z.fullname USING gbk) COLLATE gbk_chinese_ci ASC limit ");
		sql.append(pageIndex).append(",").append(count);
		sql.append(" ) t on z.zykid = t.zykid left join ");
		sql.append(" (select z.zykid,count(r.resourceid) as countresource from (SELECT DISTINCT zykId , Fullname from zyk_zyk" );
		if(status != 0){
			sql.append(" where Status="+status+" ");
		}
		sql.append(" ) z left join zyk_resource r on z.zykid = r.zykid group by z.zykid order by CONVERT(z.fullname USING gbk) COLLATE gbk_chinese_ci ASC limit ");
		sql.append(pageIndex).append(",").append(count);
		sql.append(" ) r on t.zykid = r.zykid left join ");
		sql.append(" (select z.zykid,count(c.courseid) as countcourse from (SELECT DISTINCT zykId , Fullname from zyk_zyk" );
		if(status != 0){
			sql.append(" where Status="+status+" ");
		}
		sql.append(" ) z left join zyk_course c on z.zykid = c.zykid group by z.zykid  order by CONVERT(z.fullname USING gbk) COLLATE gbk_chinese_ci ASC limit ");
		sql.append(pageIndex).append(",").append(count);
		sql.append(" ) c on r.zykid = c.zykid ");
		
		//日志总数
		sql.append(" left join ");
		sql.append(" (select z.zykid,count(l.logId) as countlog from (SELECT DISTINCT zykId , Fullname from zyk_zyk " );
		if(status != 0){
			sql.append(" where Status="+status+" ");
		}
		sql.append(" ) z left join zyk_log l on z.zykid = l.zykid group by z.zykid order by CONVERT(z.fullname USING gbk) COLLATE gbk_chinese_ci ASC limit ");
		sql.append(pageIndex).append(",").append(count);
		sql.append(" ) l on c.zykid = l.zykid ");
		
		//应用（模块）总数
		sql.append(" left join ");
		sql.append(" (select z.zykid,count(cr.CourseModuleId) as countmodules from (SELECT DISTINCT zykId , Fullname from zyk_zyk " );
		if(status != 0){
			sql.append(" where Status="+status+" ");
		}
		sql.append(" ) z left join (SELECT c.zykId, r.CourseModuleId from zyk_course c , zyk_course_resource_relation r ");
		sql.append(" where  c.courseLevel = '课程' and r.Courseid = c.courseId and c.zykId = r.zykId ) cr  on z.zykid = cr.zykid ");
		sql.append(" group by z.zykid order by CONVERT(z.fullname USING gbk) COLLATE gbk_chinese_ci ASC limit ");
		sql.append(pageIndex).append(",").append(count);
		sql.append(" ) crr on crr.zykid = l.zykid ");
		
		sql.append(" left join ");
		sql.append(" (select z.zykid, SUM(q.QuestionNum) as countquestion , SUM(q.SubQuesNum) as countsubquestion, ");
		sql.append(" SUM(q.ObjQuesNum) as countobjquestion, SUM(q.CitedQuesNum) as countcitiedquestion, ");
		sql.append(" SUM(q.QuesUsingNum) as countusingquestion from ");
		sql.append(" (SELECT DISTINCT zykId , Fullname from zyk_zyk" );
		if(status != 0){
			sql.append(" where Status="+status+" ");
		}
		sql.append(" ) z left join zyk_questionbank q on z.zykid = q.zykid group by z.zykid  order by CONVERT(z.fullname USING gbk) COLLATE gbk_chinese_ci ASC limit ");
		sql.append(pageIndex).append(",").append(count);
		sql.append(" ) q on t.zykid = q.zykid  ");

		return getHibernateTemplate().execute(new HibernateCallback<List<Map<String,Object>>>() {

			public List<Map<String,Object>> doInHibernate(Session session) throws HibernateException, SQLException {
				List list = session.createSQLQuery(sql.toString()).list();
				List<Map<String,Object>> resultList = new ArrayList<Map<String,Object>>();
				if(list!=null && list.size() > 0){
					Map<String,Object> map;
					for(int i = 0; i < list.size(); i++){
						map = new HashMap<String,Object>();
						Object[] obj= (Object[]) list.get(i);
						map.put("zykId", obj[0]);
						map.put("fullName", obj[1]);
						map.put("specialtyCategory", UtilString.isNullAndEmpty(obj[2]+"") ? "" : obj[2]);
						map.put("specialtyName", UtilString.isNullAndEmpty(obj[3]+"") ? "" : obj[3]);
						map.put("instituteInCharge",obj[4]);
						map.put("resourceCount",obj[5] == null ? "0" : obj[5]);
						map.put("courseCount",obj[6] == null ? "0" : obj[6]);
						map.put("userCount",obj[7] == null ? "0" : obj[7]);
						map.put("logCount",obj[8] == null ? "0" : obj[8]);
						map.put("modulesCount",obj[9] == null ? "0" : obj[9]);
						map.put("questionCount",obj[10] == null ? "0" : obj[10]);
						map.put("subquestionCount",obj[11] == null ? "0" : obj[11]);
						map.put("objquestionCount",obj[12] == null ? "0" : obj[12]);
						map.put("citiedquestionCount",obj[13] == null ? "0" : obj[13]);
						map.put("usingquestionCount",obj[14] == null ? "0" : obj[14]);
						map.put("modifiedDate",obj[15]+"");
						resultList.add(map);
					}
				}
				return resultList;
			}
		});
	}

}
