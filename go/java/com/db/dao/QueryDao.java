package com.db.dao;

import java.util.List;
import java.util.Map;

public interface QueryDao extends JdbcDao{

	public String getUnique(String sql);
	
	public String getUnique(String sql, List<String> paramList);
	
	public String getUnique(String sql, String... paramList);

	public List<Map<String, String>> query(String sql);
	
	public List<Map<String, String>> query(String sql, String... args);
	
	public List<Map<String, String>> query(String sql, List<String> args);

	public Pagination query(String sql, Pagination page);

	public Pagination query(String sql, Pagination page, String... paramList);

	public List<String[]> queryForArray(String sql);

	public List<String> queryOneColumn(String sql);
	
	public List<String> queryOneColumn(String sql, String... args);
	
	public Map<String, String> queryOneRow(String sql);

	public Map<String, String> queryOneRow(String sql, String... params);

	public int countselect(String sqlStr);
	
	public int countselect(String sqlStr, String... params);

	/**
	 * 获取表结构
	 * @param tblName
	 * @return
	 */
	public List<Map<String, String>> getTblStruct(String tblName);
}
