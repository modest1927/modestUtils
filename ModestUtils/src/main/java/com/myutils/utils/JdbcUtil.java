package com.myutils.utils;

import org.apache.commons.collections4.map.ListOrderedMap;
import org.springframework.jdbc.core.ColumnMapRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * jdbc工具类。在bean初始化中不要使用，直接使用bean jdbcTemplate即可
 */
public class JdbcUtil {

	private static JdbcTemplate jt;

	/**
	 * 获取JdbcTemplate对象.
	 * @return
	 */
	public static JdbcTemplate getJdbcTemplate() {
		return jt ;
	}

	public static DataSource getDataSource() {
		return jt.getDataSource() ;
	}

	public static Connection getConnection() {
		try {
			return jt.getDataSource().getConnection() ;
		} catch (SQLException e) {
			throw new RuntimeException(e) ;
		}
	}

/**
 * 使用进行JdbcTemplate进行sql查询
 * @param sql 指定的sql语句
 * @return 结果List,List中的每一条记录是一个Map对象，对应数据库中某一行；该Map中的每一项对应数据库行中的某一列值。
 */
	public static List query(String sql) {
		return getJdbcTemplate().queryForList(sql);
	}
/**
 * 使用进行JdbcTemplate进行sql查询,返回的list中存入的ListOrderedMap类型的Map<br>
 * map中的key值需大写
 * @param sql 指定的sql语句
 * @return 结果List,List中的每一条记录是一个Map对象，对应数据库中某一行；该Map中的每一项对应数据库行中的某一列值。
 */
	public static List<ListOrderedMap> query2(String sql) {
		List list = jt.query(sql, new ColumnMapRowMapper(){
			 protected Map createColumnMap(int columnCount) {
			    return new ListOrderedMap();
			 }
		});
		return list;
	}
/**
 * 使用进行JdbcTemplate进行sql更新和插入操作
 * @param sql 指定的sql语句
 * @return
 */
	public static int update(String sql) {
		return jt.update(sql);
	}
/**
 * 使用进行JdbcTemplate进行其他非查询及更新的sql操作
 * @param sql 指定的sql语句
 */
	public static void execute(String sql) {
		jt.execute(sql);
	}

	/**
	 * JdbcTemplate批处理
	 * @param sql
	 * @return
	 */
	public static int[] batchUpdate(String[] sql) {
		return jt.batchUpdate(sql) ;
	}

	/**
	 * 根据sql的第一列和第二列生成map
	 * @param sql
	 * @return
	 */
	public static Map<String, Object> getMapData(String sql) {
		final Map<String, Object> datas = new HashMap<String, Object>();
		jt.query(sql, new RowCallbackHandler() {
			public void processRow(ResultSet rs) throws SQLException {
				datas.put(rs.getString(1), rs.getObject(2));
			}
		});
		return datas;
	}

	public static Map<String, Object> getMapDataByOrder(String sql) {
		final Map<String, Object> datas = new LinkedHashMap<String, Object>();
		jt.query(sql, new RowCallbackHandler() {
			public void processRow(ResultSet rs) throws SQLException {
				datas.put(rs.getString(1), rs.getObject(2));
			}
		});
		return datas;
	}


	private JdbcTemplate jdbcTemplate ;
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
		JdbcUtil.jt = jdbcTemplate ;
	}
}

