package com.asdc.lrm.util;

import java.lang.reflect.Field;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@SuppressWarnings("unchecked")
public class UtilJSON {

	/**
	 * json对象转化为相应对象
	 * @param json
	 * @param t
	 * @return
	 */
	public static Object json2Object(JSONObject json,Class beanClass){
		if(!(beanClass instanceof Class)){
			return null;
		}
		Object instance = null;
		try {
			JSONArray names = json.names();
			instance = beanClass.newInstance();
			Field[] fields = beanClass.getDeclaredFields();
			if(fields.length > 0){
				for(int i = 0 ; i < fields.length;i++){
					Field field = fields[i];
					String fieldName = field.getName().toLowerCase();
					field.setAccessible(true);
					if(names != null && names.size() >0)
						for(int j = 0 ; j < names.size(); j++){
							if(names.get(j).toString().equals(fieldName)){
								field.set(instance, json.get(fieldName));
								break;
							}
						}
				}
			}
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return instance;
	}

	
	/**
	 * 对象转成json字符串
	 * @param o
	 * @return
	 */
	public static String toJsonString(Object o){
		return JSONObject.fromObject(o).toString();
	}
	
}

