package com.myutils.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 输出结果工具类
 * @author may
 * @since 2015-1-29
 */
public class ResultUtil {

	/**
	 * 将结果集转化成json数据格式
	 * @param list List<Map>或者List<bean>或者单纯List
	 * @param cols要转换的列字段，null表示转换全部属性
	 * 属性后可带配置参数，如："name,sex,createTime(yyyy-MM-dd)",其中括号部分表示日期格式
	 * @return
	 */
	public static String getResult(List<?> list, String cols){
		if(list == null || list.size() == 0)
			return "";
		if (list.get(0) instanceof String) {
			return JSONArray.toJSONString(list);
		}else{
			JSONArray arr = new JSONArray();
			for (int i = 0; i < list.size(); i++) {
				arr.add(getResultFromObject(list.get(i), cols));
			}
			return arr.toString();
		}
	}

	/**
	 * 将单个对象转换为json对象
	 * @param object（map/bean）对象
	 * @param cols要转换的列字段，null表示转换全部属性
	 * 属性后可带配置参数，如："name,sex,createTime(yyyy-MM-dd)",其中括号部分表示日期格式
	 * @return
	 */
	public static JSONObject getResultFromObject(Object object, String cols){
		JSONObject obj = new JSONObject();
		if(object == null){
			return obj;
		}
		Map<String, Object> tmp = null;
		try {
			tmp = (HashMap<String, Object>) object;
		} catch (Exception e) {
			tmp = transBean2Map(object);
		}
		if(cols != null){
			String[] keys = cols.split(",");
			for (String k : keys) {
				String key = getKey(k);
				Object val = tmp.get(key);
				if(val == null || val.equals("null")){
//					obj.accumulate(key, "");
					obj.put(key, "");
					continue;
				}
				if (val instanceof Date) {
					String format = getConfig(k).equals("")?"yyyy-MM-dd HH:mm:ss":getConfig(k);
//					obj.accumulate(key, new SimpleDateFormat(format).format(val));
					obj.put(key, new SimpleDateFormat(format).format(val));
				}else if(val instanceof Boolean || val instanceof Integer){
//					obj.accumulate(key, val);
					obj.put(key, val);
				}else{
//					obj.accumulate(key, val.toString());
					obj.put(key, val.toString());
				}
			}
		}else{
			Iterator<String> it = tmp.keySet().iterator();
			while(it.hasNext()){
				String key = it.next();
				Object val = tmp.get(key);
				if(val == null || val.equals("null")){
//					obj.accumulate(key, "");
					obj.put(key, "");
					continue;
				}
				if (val instanceof Date) {
//					obj.accumulate(key, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(val));
					obj.put(key, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(val));
				}else if(val instanceof Boolean || val instanceof Integer){
//					obj.accumulate(key, val);
					obj.put(key, val);
				}else{
//					obj.accumulate(key, val.toString());
					obj.put(key, val.toString());
				}
			}
		}
		return obj;
	}

	/**
	 * 获取列名
	 * @param str
	 * @return
	 */
	private static String getKey(String str){
		int ind = str.indexOf("(");
		if(ind < 0)
			return str;
		else{
			return str.substring(0, ind);
		}
	}

	/**
	 * 获取列配置信息
	 * @param str
	 * @return
	 */
	private static String getConfig(String str){
		int ind = str.indexOf("(");
		if(ind < 0)
			return "";
		else{
			str = str.substring(ind+1);
			ind = str.indexOf(")");
			return str.substring(0, ind);
		}
	}

	// Bean --> Map 1: 利用Introspector和PropertyDescriptor 将Bean --> Map
	public static Map<String, Object> transBean2Map(Object obj) {
		if (obj == null) {
			return null;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
			PropertyDescriptor[] propertyDescriptors = beanInfo
					.getPropertyDescriptors();
			for (PropertyDescriptor property : propertyDescriptors) {
				String key = property.getName();
				// 过滤class属性
				if (!key.equals("class")) {
					// 得到property对应的getter方法
					Method getter = property.getReadMethod();
					Object value = getter.invoke(obj);
					map.put(key, value);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	// Map --> Bean 1: 利用Introspector,PropertyDescriptor实现 Map --> Bean
	public static void transMap2Bean(Map<String, Object> map, Object obj) {
		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
			PropertyDescriptor[] propertyDescriptors = beanInfo
					.getPropertyDescriptors();
			for (PropertyDescriptor property : propertyDescriptors) {
				String key = property.getName();
				if (map.containsKey(key)) {
					Object value = map.get(key);
					// 得到property对应的setter方法
					Method setter = property.getWriteMethod();
					setter.invoke(obj, value);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return;
	}

	public static void main(String[] args) {
		List li = new ArrayList();
		li.add("zhangsan");
		li.add("li");
		li.add("wangwu");
		li.add("zhaoliu");
		System.out.println(getResult(li, null));

		li.clear();
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("name", "张三");
		m.put("sex", "男");
		m.put("birthday", new Date());
		m.put("realname", null);
		m.put("years", 6);
		li.add(m);
		System.out.println(getResultFromObject(m, "name,birthday,realname,years"));

//		li.add(new MsgPojo(false, "操作错误"));
//		li.add(new MsgPojo(true, "成功保存"));
		System.out.println(getResult(li, null));
	}
}
