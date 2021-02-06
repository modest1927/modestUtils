package com.myutils.utils;

import com.beust.jcommander.internal.Maps;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class LambdaUtil {

	/**
	 * 过滤
	 * 
	 * @param list
	 * @return
	 */
	public static List<Employee> filter(List<Employee> list) {
		List<Employee> newList = list.stream().filter(item -> {
			return item.getSalary().compareTo(new BigDecimal(10000)) > 0 && !item.getWorkType().equals("项目经理");
		}).collect(Collectors.toList());
		return newList;
	}

	/**
	 * 取集合中对象，对象某个字段
	 * @param list
	 * @return
	 */
	public static List<String> toListStr(List<Employee> list) {
		// 写法一
//        List<String> newList = list.stream().map(Employee::getEmpName).collect(Collectors.toList());
		// 写法二
		List<String> newList = list.stream().map(item -> item.getEmpName()).collect(Collectors.toList());
		return newList;
	}

	/**
	 * 对每个对象重新赋值
	 * @param list
	 * @return
	 */
	public static List<Employee> toListObject(List<Employee> list) {
		List<Employee> newList = list.stream().map(item -> {
			item.setWorkType("重新设置职位");
			return item;
		}).collect(Collectors.toList());
		return newList;
	}

	/**
	 * list转map
	 * 
	 * @param list
	 * @return
	 */
	public static Map<String, String> toMapStr(List<Employee> list) {
		// 写法一
		// Map<String, String> map =
		// list.stream().collect(Collectors.toMap(com.myutils.utils.Employee::getEmpName,
		// com.myutils.utils.Employee::getWorkType));
		// 写法二 (k1,k2)->k2 避免键重复 k1-取第一个数据；k2-取最后一条数据
		Map<String, String> map = list.stream()
				.collect(Collectors.toMap(i -> i.getEmpName() + i.getUnitName(), j -> j.getWorkType(), (k1, k2) -> k1));
		return map;
	}

	/**
	 * list转map
	 * 
	 * @param list
	 * @return
	 */
	public static Map<String, Object> toMapObject(List<Employee> list) {
		// (k1,k2)->k2 避免键重复 k1-取第一个数据；k2-取最后一条数据
		Map<String, Object> map = list.stream()
				.collect(Collectors.toMap(i -> i.getEmpName() + i.getUnitName(), j -> j, (k1, k2) -> k1));
		return map;
	}
	
	/**
	 * ,拼接
	 * @param list
	 * @return
	 */
	public static String join(List<Employee> list){
		String empNames = list.stream().map(Employee::getEmpName).collect(Collectors.joining(","));
		return empNames;
	}
	
	/**
	 * 排序
	 * @param list
	 * @return
	 */
	public static List<Employee> sort(List<Employee> list){
		// s1-s2 升序   s2-s1降序
		Collections.sort(list,(s1,s2) -> s1.getSalary().compareTo(s2.getSalary()));
		return list;
	}
	
	/**
	 * 多条件排序
	 * @param list
	 * @return
	 */
	public static List<Employee> multiSort(List<Employee> list){
		// s1-s2 升序   s2-s1降序
		list.sort(Comparator.comparing(Employee::getSalary).reversed().thenComparing(Employee::getId).reversed());
		return list;
	}
	
	/**
	 * BigDecimal求和
	 * @param list
	 * @return
	 */
	public static BigDecimal bigDecimalSum(List<Employee> list){
		BigDecimal sum = list.stream().map(Employee::getSalary).reduce(BigDecimal.ZERO,BigDecimal::add);
		return sum;
	}
	
	/**
	 * 基本数据类型求和
	 * @param list
	 * @return
	 */
	public static Integer intSum(List<Employee> list){
		Integer sum = list.stream().mapToInt(Employee::getId).sum();
		return sum;
	}
	
	/**
	 * BigDecimal求最大值
	 * @param list
	 * @return
	 */
	public static BigDecimal bigDecimalMax(List<Employee> list){
		BigDecimal max = list.stream().map(Employee::getSalary).reduce(BigDecimal.ZERO,BigDecimal::max);
		return max;
	}
	
	/**
	 * 基本数据类型求最大值
	 * @param list
	 * @return
	 */
	public static Integer intMax(List<Employee> list){
		OptionalInt optionalMax = list.stream().mapToInt(Employee::getId).max();
		return optionalMax.getAsInt();
	}
	
	/**
	 * 求最大值的对象
	 * @param list
	 * @return
	 */
	public static Employee maxIntObject(List<Employee> list){
		Optional<Employee> max = list.stream().max(Comparator.comparingInt(Employee::getId));
		return max.get();
	}
	
	/**
	 * 求最大值的对象
	 * @param list
	 * @return
	 */
	public static Employee maxBigDeciamlObject(List<Employee> list){
		Optional<Employee> max = list.stream().max(Comparator.comparing(Employee::getSalary));
//		return max.get();
		//new com.myutils.utils.Employee()  防止空指针
		return max.orElse(new Employee());
	}
	
	/**
	 * 求最大值的对象
	 * @param list
	 * @return
	 */
	public static Employee maxBigDecimaObject2(List<Employee> list){
		Optional<Employee> optional = list.stream().collect(Collectors.maxBy(Comparator.comparing(Employee::getId)));
//		if (optional.isPresent()) { // 判断是否有值
//			com.myutils.utils.Employee user = optional.get();
//		}
		return optional.orElse(new Employee());
	}
	
	
	/**
	 * BigDecimal求最小值
	 * @param list
	 * @return
	 */
	public static BigDecimal bigDecimalMin(List<Employee> list){
		BigDecimal min = list.stream().map(Employee::getSalary).reduce(BigDecimal.ZERO,BigDecimal::min);
		return min;
	}
	
	/**
	 * 基本数据类型求最小值
	 * @param list
	 * @return
	 */
	public static Integer intMin(List<Employee> list){
		OptionalInt optionalMin = list.stream().mapToInt(Employee::getId).min();
		return optionalMin.getAsInt();
	}
	
	/**
	 * 求最小值的对象
	 * @param list
	 * @return
	 */
	public static Employee minIntObject(List<Employee> list){
		Optional<Employee> min = list.stream().min(Comparator.comparingInt(Employee::getId));
		return min.get();
	}
	
	/**
	 * 求最小值的对象
	 * @param list
	 * @return
	 */
	public static Employee minBigDeciamlObject(List<Employee> list){
		Optional<Employee> min = list.stream().min(Comparator.comparing(Employee::getSalary));
//		return max.get();
		//new com.myutils.utils.Employee()  防止空指针
		return min.orElse(new Employee());
	}
	
	/**
	 * 求最小值的对象
	 * @param list
	 * @return
	 */
	public static Employee minBigDeciamlObject2(List<Employee> list){
		Optional<Employee> optional = list.stream().collect(Collectors.minBy(Comparator.comparing(Employee::getSalary)));
//		if (optional.isPresent()) { // 判断是否有值
//			com.myutils.utils.Employee user = optional.get();
//		}
		return optional.orElse(new Employee());
	}
	
	/**
	 * 平均值
	 * @param list
	 * @return
	 */
	public static Double average(List<Employee> list){
		OptionalDouble average = list.stream().mapToInt(Employee::getId).average();
		return average.getAsDouble();
	}
	
	
	/**
	 * 统计：和、数量、最大值、最小值、平均值
	 * @param list
	 */
	public static void statistics(List<Employee> list){
		IntSummaryStatistics collect = list.stream().collect(Collectors.summarizingInt(Employee::getId));
		System.out.println("和：" + collect.getSum());
		System.out.println("数量：" + collect.getCount());
		System.out.println("最大值：" + collect.getMax());
		System.out.println("最小值：" + collect.getMin());
		System.out.println("平均值：" + collect.getAverage());
	}
	
	/**
	 * map转list
	 * @param map
	 * @return
	 */
	public static List<Employee> mapToList(Map<Integer,String> map){
		List<Employee> collect = map.entrySet().stream().map(item -> {
			Employee employee = new Employee();
			employee.setId(item.getKey());
			employee.setEmpName(item.getValue());
			return employee;
		}).collect(Collectors.toList());
		return collect;
	}
	
	/**
	 * 分组
	 * @param list
	 * @return
	 */
	public static Map<String,List<Employee>> toMapGroup(List<Employee> list){
		Map<String, List<Employee>> collect = list.stream().collect(Collectors.groupingBy(i -> i.getUnitName()));
		return collect;
	}
	
	/**
	 * 分组
	 * @param list
	 * @return
	 */
	public static Map<String, Map<String,List<Employee>>> toMapMultGroup(List<Employee> list){
		Map<String, Map<String,List<Employee>>> collect = list.stream().collect(Collectors.groupingBy(i -> i.getUnitName(),Collectors.groupingBy(i -> i.getWorkType())));
		return collect;
	}
	
	/**
	 * 某个值的数量
	 * @param list
	 * @return
	 */
	public static Map<BigDecimal,Long> groupingCount(List<Employee> list){
		Map<BigDecimal, Long> collect = list.stream().collect(Collectors.groupingBy(i -> i.getSalary(),Collectors.counting()));
		return collect;
	}
	
	/**
	 * 某个值的和
	 * @param list
	 * @return
	 */
//	public static Map<String,BigDecimal> groupingSum(List<com.myutils.utils.Employee> list){
//		list.stream().collect(Collectors.groupingBy(i -> i.getUnitName(),Collectors.reducing(BigDecimal.ZERO,BigDecimal::max)));
//		return null;
//	}
	
	/**
	 * 对象分组数量
	 * @param list
	 * @return
	 */
	public static Map<Employee,Long> groupingObjectCount(List<Employee> list){
		Map<Employee, Long> collect = list.stream().collect(Collectors.groupingBy(Function.identity(),Collectors.counting()));
		return collect;
	}
	
	/**
	 * 分区
	 * @param list
	 * @return
	 */
	public static Map<Boolean,List<Employee>> partitioningBy(List<Employee> list){
		Map<Boolean, List<Employee>> collect = list.stream().collect(Collectors.partitioningBy(i -> i.getId() == 1));
		return collect;
	}
	
	/**
	 * 多层分区
	 * @param list
	 * @return
	 */
	public static Map<Boolean,Map<Boolean,List<Employee>>> partitioningMultBy(List<Employee> list){
		Map<Boolean, Map<Boolean,List<Employee>>> collect = list.stream().collect(Collectors.partitioningBy(i -> i.getId() == 1,Collectors.partitioningBy(i -> i.getSalary().compareTo(new BigDecimal(20000)) == 0)));
		return collect;
	}

    /**
     * 根据map的key排序
     *
     * @param map 待排序的map
     * @param isDesc 是否降序，true：降序，false：升序
     * @return 排序好的map
     * @author zero 2019/04/08
     */
    public static <K extends Comparable<? super K>, V> Map<K, V> sortByKey(Map<K, V> map, boolean isDesc) {
        Map<K, V> result = Maps.newLinkedHashMap();
        if (isDesc) {
            map.entrySet().stream().sorted(Map.Entry.<K, V>comparingByKey().reversed())
                    .forEachOrdered(e -> result.put(e.getKey(), e.getValue()));
        } else {
            map.entrySet().stream().sorted(Map.Entry.<K, V>comparingByKey())
                    .forEachOrdered(e -> result.put(e.getKey(), e.getValue()));
        }
        return result;
    }

    /**
     * 根据map的value排序
     *
     * @param map 待排序的map
     * @param isDesc 是否降序，true：降序，false：升序
     * @return 排序好的map
     * @author zero 2019/04/08
     */
    public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map, boolean isDesc) {
        Map<K, V> result = Maps.newLinkedHashMap();
        if (isDesc) {
            map.entrySet().stream().sorted(Map.Entry.<K, V>comparingByValue().reversed())
                    .forEach(e -> result.put(e.getKey(), e.getValue()));
        } else {
            map.entrySet().stream().sorted(Map.Entry.<K, V>comparingByValue())
                    .forEachOrdered(e -> result.put(e.getKey(), e.getValue()));
        }
        return result;
    }
}
