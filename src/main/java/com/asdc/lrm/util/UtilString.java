package com.asdc.lrm.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.util.Collections;


/**
 *  处理字符串工具
 * @author sher
 *
 */
public class UtilString {

	/**
	 * 判断字符串是否为空
	 * @param s
	 * @return
	 */
	public static boolean isNullAndEmpty(String s){
		return s == null || s.trim().length() == 0 || "null".equals(s);
	}
	
	
	
	public static Map<String,Object> parseUrlStr(String url){
		if(isNullAndEmpty(url)) return null;
		Map<String,Object> map = new HashMap<String, Object>();
		if(url.contains("?")) url = url.substring(1);
		if(url.contains("&")){
			String[] params = url.split("&");
			if(params.length > 0){
				for(int i = 0 ; i<params.length; i++){
					String param = params[i];
					String[] maps = param.split("=");
					map.put(maps[0], maps[1]);
				}
			}
		}else{
			map.put(url.split("=")[0], url.split("=")[1]);
		}
		return map;
	} 
	
	/***
	 * 解析key:value:value形式数据生成数组
	 * @param param  
	 * @return
	 */
	public static List<String[]> parseKeyValueData(String param){
		if(isNullAndEmpty(param)) return null;
		List<String[]> list = new ArrayList<String[]>();
		if(param.contains(",")){
			String[] values = param.split(",");
			for(int i = 0 ; i < values.length; i++){
				if(values[i].contains(":")){
					String[] vals = values[i].split(":");
					list.add(vals);
				}
				else{
					continue;
				}
				
			}
		}
		return list;
	}
	
	
	public static boolean isOSWindow(){
		String property = System.getProperty("os.name");
		if(property != null && property.contains("Windows"))
			return true;
		else
			return false;
	}
	
	public static String getStr(Object o){
		if(o == null) return null;
		else{
			String str = o+"";
			if("null".equals(str.toLowerCase())){
				return null;
			}
			return str;
		}
		
	}
	
	/**
	 * 按照map中的count排序 
	 * 取前N位
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static List<Object> Top_N_List_ForMap(List<Object> initList, int N){
		
		//初始化list 按照map中的count 排序
		for(int i = 0; i < initList.size(); i++){
			for (int j = i; j < initList.size(); j++) {
				Map mapTemp_i = ((Map) initList.get(i));
				Map mapTemp_j= ((Map) initList.get(j));
				int count_i = (Integer) mapTemp_i.get("count");
				int count_j = (Integer) mapTemp_j.get("count");
				if(count_i < count_j){
					Map map_temp = new HashMap();
					map_temp.putAll((Map) initList.get(j));
					((Map)initList.get(j)).putAll((Map) initList.get(i));
					((Map)initList.get(i)).putAll(map_temp);
				}
			}
		}
		
		List resultList = new ArrayList();
		for(int i = 0; i < initList.size(); i++){
			if(i < N){
				resultList.add(initList.get(i));
			}else{
				Map mapTemp_i = ((Map) initList.get(i));
				Map mapTemp_j= ((Map) initList.get(i-1));
				int count_i = (Integer) mapTemp_i.get("count");
				int count_j = (Integer) mapTemp_j.get("count");
				if(count_i == count_j){
					resultList.add(initList.get(i));
				}else{
					break;
				}
			}
		}
		return resultList;
	}
	
	/**
	 * 为list排序
	 * object[0] 为String
	 * object[1] 为int
	 */
	public static List<Object[]> SortList_ForObject(List<Object[]> initList){
		for(int i = 0; i < initList.size(); i++){
			for(int j = i; j < initList.size(); j++){
				int count_i= Integer.parseInt(initList.get(i)[1]+"");
				int count_j= Integer.parseInt(initList.get(j)[1]+"");
				if(count_i < count_j){
					int temp = count_i;
					initList.get(i)[1] = count_j;
					initList.get(j)[1] = temp;
				}
			}
		}
		return initList;
	}
	
	/**
	 * 取List中的前N位
	 * List中存Object数组
	 * object[0] 为String
	 * object[1] 为int
	 */
	public static List<Object[]> Top_N_List_ForObject(List<Object[]> initList, int N){
		List<Object[]> resultList = new ArrayList<Object[]>();
		for(int i = 0; i < initList.size(); i++){
			for(int j = i; j < initList.size(); j++){
				int count_i= Integer.parseInt(initList.get(i)[1]+"");
				int count_j= Integer.parseInt(initList.get(j)[1]+"");
				if(count_i < count_j){
					int temp = count_i;
					initList.get(i)[1] = count_j;
					initList.get(j)[1] = temp;
				}
			}
		}
		for(int i = 0; i < initList.size(); i++){
			if(i < N){
				resultList.add(initList.get(i));
			}else{
				if(initList.get(i)[1].equals(initList.get(i-1)[1])){
					resultList.add(initList.get(i));
				}else{
					break;
				}
			}
		}
		Collections.reverse(resultList);
		return resultList;
	}
	
	/**
	 * 计算List中存Object数组中 分组排序后各个数值的和
	 * object[1] 为int
	 */
	public static int TotalCount_ForObject(List<Object[]> initList){
		int totalCount = 0;
		for(int i = 0; i < initList.size(); i++){
			int count = Integer.parseInt(initList.get(i)[1]+"");
			totalCount += count;
		}
		return totalCount;
	}
	
	
	/**
	 * 生成echarts页面需要的title数据及对应的value值
	 * @return
	 */
	public static List<String> generateTitleAndValue(List<Object[]> initList){
		
		List<String> resultList = new ArrayList<String>();
		StringBuffer titleStr = new StringBuffer("[");
		StringBuffer valueStr = new StringBuffer("[");
		
		for(int i = 0; i < initList.size(); i++){
			titleStr.append("'");
			titleStr.append(initList.get(i)[0]);
			titleStr.append("'");
			
			valueStr.append("'");
			valueStr.append(initList.get(i)[1]);
			valueStr.append("'");
			
			if((i+1) < initList.size()){
				titleStr.append(",");
				valueStr.append(",");
			}
			
		}
		titleStr.append("]");
		valueStr.append("]");
		
		resultList.add(titleStr.toString());
		resultList.add(valueStr.toString());
		return resultList;
	}
}
