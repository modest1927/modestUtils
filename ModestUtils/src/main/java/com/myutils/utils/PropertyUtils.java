//
//import com.myutils.utils.SpringContextUtil;
//import org.springframework.beans.BeansException;
//import org.springframework.beans.factory.BeanFactory;
//import org.springframework.beans.factory.BeanFactoryAware;
//
//import java.io.*;
//import java.util.*;
//
///**
// * 配置加载工具类，加载业务配置
// * @author lizp
// * @date May 31, 2016
// */
//public class PropertyUtils implements BeanFactoryAware {
//	/**
//	 * 获得属性配置
//	 * @param key
//	 * @return
//	 */
//	public static Object getValue(String key) {
//		Properties p = SpringContextUtil.getBean("properties");
//		return p.get(key);
//	}
//
//	public List<String> getList(String prefix) {
//		if (properties == null || prefix == null) {
//			return Collections.emptyList();
//		}
//		List<String> list = new ArrayList<String>();
//		Enumeration<?> en = properties.propertyNames();
//		String key;
//		while (en.hasMoreElements()) {
//			key = (String) en.nextElement();
//			if (key.startsWith(prefix)) {
//				list.add(properties.getProperty(key));
//			}
//		}
//		return list;
//	}
//
//	public Set<String> getSet(String prefix) {
//		if (properties == null || prefix == null) {
//			return Collections.emptySet();
//		}
//		Set<String>set=new TreeSet<String>();
//		Enumeration<?> en = properties.propertyNames();
//		String key;
//		while (en.hasMoreElements()) {
//			key = (String) en.nextElement();
//			if (key.startsWith(prefix)) {
//				set.add(properties.getProperty(key));
//			}
//		}
//		return set;
//	}
//
//
//	public Map<String, String> getMap(String prefix) {
//		if (properties == null || prefix == null) {
//			return Collections.emptyMap();
//		}
//		Map<String, String> map = new HashMap<String, String>();
//		Enumeration<?> en = properties.propertyNames();
//		String key;
//		int len = prefix.length();
//		while (en.hasMoreElements()) {
//			key = (String) en.nextElement();
//			if (key.startsWith(prefix)) {
//				map.put(key.substring(len), properties.getProperty(key));
//			}
//		}
//		return map;
//	}
//
//	public String getPropertiesValue(String key) {
//		return properties.getProperty(key) ;
//	}
//
//	public Properties getProperties(String prefix) {
//		Properties props = new Properties();
//		if (properties == null || prefix == null) {
//			return props;
//		}
//		Enumeration<?> en = properties.propertyNames();
//		String key;
//		int len = prefix.length();
//		while (en.hasMoreElements()) {
//			key = (String) en.nextElement();
//			if (key.startsWith(prefix)) {
//				props.put(key.substring(len), properties.getProperty(key));
//			}
//		}
//		return props;
//	}
//
//	public String getPropertiesString(String prefix) {
//		String property = "";
//		if (properties == null || prefix == null) {
//			return property;
//		}
//		Enumeration<?> en = properties.propertyNames();
//		String key;
//		while (en.hasMoreElements()) {
//			key = (String) en.nextElement();
//			if (key.equals(prefix)) {
//				return properties.getProperty(key);
//			}
//		}
//		return property;
//	}
//
//	public Map<String, Object> getBeanMap(String prefix) {
//		Map<String, String> keyMap = getMap(prefix);
//		if (keyMap.isEmpty()) {
//			return Collections.emptyMap();
//		}
//		Map<String, Object> resultMap = new HashMap<String, Object>(keyMap.size());
//		String key, value;
//		for (Map.Entry<String, String> entry : keyMap.entrySet()) {
//			key = entry.getKey();
//			value = entry.getValue();
//			resultMap.put(key, beanFactory.getBean(value, Object.class));
//		}
//		return resultMap;
//	}
//
//	public static Properties getProperties(File file) {
//		Properties props = new Properties();
//		InputStream in;
//		try {
//			in = new FileInputStream(file);
//			props.load(in);
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		return props;
//	}
//
//	public static String getPropertyValue(File file,String key) {
//		Properties props=getProperties(file);
//		return (String) props.get(key);
//	}
//
//	private BeanFactory beanFactory;
//	private Properties properties;
//
//	public void setProperties(Properties properties) {
//		this.properties = properties;
//	}
//
//	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
//		this.beanFactory = beanFactory;
//	}
//
//	 /**
//     * 获得所有自定义标签bean。继承BaseTemplateDirectiveModel及在属性配置中配置名称为directive.开头的bean名称。
//     * 继承BaseTemplateDirectiveModel的标签名为注解或者bean的bean名称，配置文件中的名称directive.后的部分。
//     * @return
//     */
//    public Map getTemplateDirectivesMap() {
//		Map map = SpringContextUtil.getApplicationContext().getBeansOfType(BaseTemplateDirectiveModel.class) ;
//		if(map==null) {
//			map = new HashMap();
//		}
//		Map<String, Object> resultMap = getBeanMap("directive.");
//		map.putAll(resultMap) ;
//		return map ;
//	}
//
//}
