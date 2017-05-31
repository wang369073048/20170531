package com.asdc.lrm.util;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/***
 * 报表工具类
 * @author sher
 *
 */
public class UtilCharts {

	
	
	/***
	 * 根据map的key value 生成饼图表数据
	 * @param map
	 * @return
	 */
	public static List<String> generatePieChartData(Map<String,Object> map){
		List<String> list = new ArrayList<String>();
		if(map == null || map.size() == 0){
			list.add("''");list.add("''");list.add("0");list.add("0");
			return list;
		}
		StringBuilder keys = new StringBuilder("[");
		StringBuilder values = new StringBuilder("[");
		int len = 0;
		int maxCount = 0;
		int sum = 0;
		for(Map.Entry<String, Object> entry : map.entrySet()){
			keys.append("'"+entry.getKey()+"'");
			values.append("{value:").append(entry.getValue()).append(",name:'").append(entry.getKey()).append("'}");
			if(Integer.valueOf(entry.getValue()+"") > maxCount){
				maxCount = Integer.valueOf(entry.getValue().toString());
			}
			if(++len < map.size()){
				keys.append(",");
				values.append(",");
			}
			sum += Integer.valueOf(entry.getValue().toString());
		}
		keys.append("]");
		values.append("]");
		list.add(keys.toString());
		list.add(values.toString());
		list.add(maxCount+"");
		list.add(sum+"");
		return list;
	}
	
	/**
	 * 生成柱状图数据
	 * @param map
	 * @param order 1 ： map的正常次序   2或null： map的倒序输出
	 * @return
	 */
	public static List<String> generateBarChartData(Map<String,Object> map,String order){
		List<String> list = new ArrayList<String>();
		if(map == null || map.size() == 0){
			list.add("''");list.add("''");list.add("''");list.add("''");
			return list;
		}
		StringBuilder keys = new StringBuilder("[");
		StringBuilder values = new StringBuilder("[");
		int len = 0;
		int sum = 0;
		if(!UtilString.isNullAndEmpty(order) && "1".equals(order)){
			for(Map.Entry<String, Object> entry : map.entrySet()){
				keys.append("'"+entry.getKey()+"'");
				values.append("'").append(entry.getValue()).append("'");
				if(++len < map.size()){
					keys.append(",");
					values.append(",");
				}
				sum += Integer.valueOf(entry.getValue().toString()); 
				
			}
		}
		else {
			return generateBarChartData(reverseMap(map),"1");
		}
		keys.append("]");
		values.append("]");
		list.add(keys.toString());
		list.add(values.toString());
		list.add(sum+"");
		return list;
	}
	
	/***
	 * map倒序
	 * @param map
	 * @return
	 */
	public static Map<String,Object> reverseMap(Map<String,Object> map){
		if(map == null || map.size() == 0)
			return null;
		Map<String,Object> resultMap = new LinkedHashMap<String, Object>();
		Set<String> keySet = map.keySet();
		String[] array = keySet.toArray(new String[0]);
		if(array.length > 0){
			for(int i = array.length -1 ; i >= 0; i--){
				resultMap.put(array[i], map.get(array[i]));
			}
		}
		return resultMap;
	}
	
	/**
	 * 查询4个 返回前3个结果
	 * @param map (key,value)
	 * @return
	 */
	public static Map<String, Object> getTopThreeCount(Map<String, Object> map){
		int i = 0;
		int rank = 0;
		int count = 0;
		String keys = "";
		if(map.size() <= 3){
			return map;
		}
		for(Map.Entry<String, Object> entry : map.entrySet()){
			if(i == 0 && Integer.valueOf(entry.getValue()+"") == 0){
				return null;
			}
			if(count != Integer.valueOf(entry.getValue()+"")){
				count = Integer.valueOf(entry.getValue()+"");
				rank++;
				if(rank >= 4){
					keys += entry.getKey()+",";
				}
			}
			i++;
		}
		if(rank >= 4){
			String[] keyArr = keys.split(",");
			for(int k = 0 ; k < keyArr.length;k++){
				map.remove(keyArr[k]);
			}
			return map;
		}
		return null;
	}
	
	/***
	 * 遍历Map求取总和
	 * @param map
	 * @return
	 */
	public static int getTotalCountByMap(Map<String, Object> map){
		if(map == null || map.size() == 0) return 0;
		int total = 0;
		for(Map.Entry<String, Object> entry : map.entrySet()){
			total += Integer.valueOf(entry.getValue().toString());
		}
		return total;
	}
	
	/**
	 * 排序map中的值
	 * @param map
	 * @param order   1:增序  2：倒序
	 * @return
	 */
	public static Map<String,Object> getSortedValueMap(Map<String,Object> map,String order){
		if(map==null || map.size()==0) return null;
		Map<String,Object> resultMap = new LinkedHashMap<String, Object>();
		int size = map.size();
		int[] arr = new int[size];
		String[] values = new String[size];
		int len = 0;
		for(Map.Entry<String, Object> entry : map.entrySet()){
			Integer value = Integer.valueOf(entry.getValue().toString());
			arr[len] = value;
			values[len] = entry.getKey();
			len++;
		}
		
		for(int i = 0 ; i < arr.length - 1; i++){
			int lowIndex = i;
			for(int j = i + 1; j < arr.length; j++){
				if(arr[lowIndex] > arr[j]){
					lowIndex = j;
				}
			}
			int temp = arr[i];
			arr[i] = arr[lowIndex];
			arr[lowIndex] = temp;
			String str = values[i];
			values[i] = values[lowIndex];
			values[lowIndex] = str;
		}
		if("1".equals(order)){
			for(int i = 0 ; i < arr.length;i++){
				resultMap.put(values[i], arr[i]);
			}
		}else if("2".equals(order)){
			for(int i = arr.length-1 ; i >= 0;i--){
				resultMap.put(values[i], arr[i]);
			}
		}
		return resultMap;
	}
	
	
	/***
	 * 生成含有内部饼状比例图
	 * @param usedList
	 * @param names  内部饼状名称
	 * @return
	 */
	public static List<String> generatePieChartRatio(List<Map<String, Object>> usedList,String... names){
		List<String> list = new ArrayList<String>();
		if(usedList==null || usedList.size() ==0) {
			list.add("''");list.add("''");list.add("''");list.add("''");
			return list;
		}
		if(names==null || names.length ==0) {
			list.add("''");list.add("''");list.add("''");list.add("''");
			return list;
		};
		if(usedList.size()!= names.length) {
			list.add("''");list.add("''");list.add("''");list.add("''");
			return list;
		};
		StringBuilder keys = new StringBuilder("[");
		StringBuilder values = new StringBuilder("[");
		StringBuilder inPieData = new StringBuilder("[");
		int total = 0;
		for(int i = 0; i < usedList.size(); i++){
			Map<String, Object> map = usedList.get(i);
			if(map!=null && map.size() > 0){
				int sum = 0;
				int len = 0;
				for(Map.Entry<String, Object> entry : map.entrySet()){
					keys.append("'"+entry.getKey()+"'");
					values.append("{value:").append(entry.getValue()).append(",name:'").append(entry.getKey()).append("'}");
					if(++len < map.size() || i < usedList.size()-1){
						keys.append(",");
						values.append(",");
					}
					sum += Integer.valueOf(entry.getValue().toString());
					total += Integer.valueOf(entry.getValue().toString());
				}
				inPieData.append("{value:").append(sum).append(",name:'").append(names[i]).append("'}");
				if(i < usedList.size()-1){
					inPieData.append(",");
				}
			}
		}
		keys.append("]");
		values.append("]");
		inPieData.append("]");
		list.add(keys.toString());
		list.add(values.toString());
		list.add(inPieData.toString());
		list.add(total+"");
		return list;
	}
	
	/**
	 * 计算平均值
	 * @param map  倒序排列
	 * @param total  总数
	 * @param totalName  总数名称
	 * @return
	 */
	public static List<String> generateBarChartDataHasTotal(Map<String, Object> map,String totalName){
		List<String> list = new ArrayList<String>();
		if(map == null || map.size() == 0){
			list.add("''");list.add("''");list.add("''");list.add("''");
			return list;
		}
		Map<String, Object> resultMap = reverseMap(map);
		int total = 0;
		int sum = 0;
		for(Map.Entry<String, Object> entry : resultMap.entrySet()){
			if("total".equals(entry.getKey())){
				total = Integer.valueOf(entry.getValue()+"");
				continue;
			}
			sum += Integer.valueOf(entry.getValue()+"");
		}
		resultMap.remove("total");
		resultMap.put("平均值", sum/resultMap.size());
		resultMap.put("总数", sum);
		if(total != 0 && !UtilString.isNullAndEmpty(totalName)){
			resultMap.put(totalName, total);
		}
		return generateChartData(resultMap);
	}
	
	/***
	 * 返回平均值数据map
	 * @param map
	 * @param total
	 * @param totalName
	 * @return
	 */
	public static Map<String,Object> getDataAVG(Map<String, Object> map,int total,String totalName){
		if(map == null || map.size() ==0) return null;
		if(total == 0) return null;
		Map<String, Object> resultMap = reverseMap(map);
		int sum = 0;
		for(Map.Entry<String, Object> entry : resultMap.entrySet()){
			sum += Integer.valueOf(entry.getValue()+"");
		}
		resultMap.put("平均值", sum/resultMap.size());
		resultMap.put("总数", sum);
//		resultMap.put(totalName, total);
		return resultMap;
	}
	
	/***
	 * 只遍历map输出
	 * @param map
	 * @return
	 */
	public static List<String> generateChartData(Map<String, Object> map){
		List<String> list = new ArrayList<String>();
		if(map == null || map.size() == 0){
			list.add("''");list.add("''");list.add("''");list.add("''");
			return list;
		}
		StringBuilder keys = new StringBuilder("[");
		StringBuilder values = new StringBuilder("[");
		int len = 0;
		for(Map.Entry<String, Object> entry : map.entrySet()){
			keys.append("'"+entry.getKey()+"'");
			values.append("'").append(entry.getValue()).append("'");
			if(++len < map.size()){
				keys.append(",");
				values.append(",");
			}
		}
		keys.append("]");
		values.append("]");
		list.add(keys.toString());
		list.add(values.toString());
		return list;
	}
	
	/***
	 * 饼状环形图比例
	 * @param map 中包含有total
	 * @return
	 */
	public static List<String> generatePieLoopChartDataRatio(Map<String, Object> map){
		List<String> list = new ArrayList<String>();
		if(map == null || map.size() == 0){
			list.add("''");list.add("''");list.add("''");list.add("''");
			return list;
		}
		StringBuilder keys = new StringBuilder("[");
		int total = 0;
		int len = 0;
		for(Map.Entry<String, Object> entry : map.entrySet()){
			if("total".equals(entry.getKey())){
				total = Integer.valueOf(entry.getValue().toString());
				continue;
			}
			StringBuilder values = new StringBuilder("[");
			keys.append("'").append(entry.getKey()).append("'");
			values.append("{name:'other',value:").append(total-Integer.valueOf(entry.getValue().toString())).append(",itemStyle:labelBottom},");
			values.append("{name:'").append(entry.getKey()).append("',value:").append(Integer.valueOf(entry.getValue().toString())).append(",itemStyle : labelTop}");
			if(++len < map.size() - 1){
				keys.append(",");
			}
			values.append("]");
			list.add(values.toString());
		}
		keys.append("]");
		list.add(keys.toString());
		list.add(total+"");
		return list;
	}
}
