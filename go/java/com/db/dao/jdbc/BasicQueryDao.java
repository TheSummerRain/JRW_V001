package com.db.dao.jdbc;

import com.db.dao.DbConfig;
import com.db.dao.Pagination;
import com.db.dao.QueryDao;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BasicQueryDao extends BasicDaoWrapper implements QueryDao {
	
	/**
	 * 通过jdniName创建连接
	 * @param jndiName jndi的名称
	 */
	public BasicQueryDao(String jndiName) {
		this.dbConnector = new DbConnector(jndiName);
	}
	
	public BasicQueryDao(DataSource source) {
		this.dbConnector = new DbConnector(source);
	}
	
	
	public BasicQueryDao(DbConfig dbConfig) {
		this.dbConnector = new DbConnector(dbConfig);
		
	}
	
	public BasicQueryDao(DbCode dbCode) {
		this.dbConnector = new DbConnector(dbCode);
	}
	
	public BasicQueryDao(DbCodeFinder dbCodeFinder) {
		this.dbConnector = new DbConnector(dbCodeFinder);
	}
	
	
	@Override
	public List<String[]> queryForArray(String sql) {
		List<String[]> result = new ArrayList<>();
		
		MyResultSet rs = executeQuery(sql);
		ResultSetMetaData meta = null;
		int colCount = 0;
		
		try {
			if (rs != null) {
				meta = rs.rset.getMetaData();
				colCount = meta.getColumnCount();
                while (rs.next()) {
                    String[] rows = new String[colCount];
                    for (int i = 0; i < colCount; i++) {
                        rows[i] = rs.getString(meta.getColumnLabel(i + 1));
                    }
                    result.add(rows);
                }
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			appendMessage("查询失败：" + e.getMessage());
		} finally {
			close();
		}
		return result;
	}
	
	
	@Override
	public String getUnique(String sql, List<String> paramList) {
		String[] args = paramList.toArray(new String[0]);
		return getUnique(sql, args);
	}
	
	/**
	 * 获取唯一返回值
	 * @param sql 可以带?号占位符的sql
	 * @param paramList	sql语句中占位符对应的值列表
	 * @return
	 */
	@Override
	public String getUnique(String sql, String... paramList) {
		ResultSet rset = null;
		PreparedStatement pstmt = null;
		Connection _conn = null;
		String result = null;
		try {
			_conn = getConn();
			pstmt = _conn.prepareStatement(sql);
			
			if (paramList != null && paramList.length > 0) {
				for (int i = 1; i <= paramList.length; i++) {
					pstmt.setObject(i, paramList[i-1]);
				}
			}
			
			rset = pstmt.executeQuery();
			if (rset.next()) {
				result = rset.getString(1);
			}
		} catch (SQLException e) {
			log.error("error on execute: " + sql);
			log.error(e.getMessage(), e);
			appendMessage("查询失败：" + e.getMessage());
		} finally {
			close();
		}
		return result;
	}
	
	/**
	 * 获取唯一的返回值
	 * @param sql
	 * @return
	 */
	@Override
	public String getUnique(String sql) {
		return getUnique(sql, new String[0]);
	}
	
	/**
	 * 基本sql查询
	 * @param sqlStr
	 * @return
	 */
	protected MyResultSet executeQuery(String sqlStr) {
		Connection conn = getConn();
		MyResultSet rs = null;
		try {
			rs = executeQuery(conn, sqlStr, new String[]{});
		} finally {
			close();
		}
		return rs;
	}
	
	/**
	 * 使用prepareStatement的查询
	 * @param sqlStr sql语句，可以带有?占位符
	 * @param paramList 对应sql语句中带?号位置的值列表，如果sql中无占位符，可以为null
	 * @return
	 */
	protected MyResultSet executeQuery(Connection conn, String sqlStr, String... paramList) {
		ResultSet rset = null;
		PreparedStatement pstmt = null;
		//Connection conn = null;
		MyResultSet myrset = new MyResultSet();
		try {
			//conn = getConn();
			pstmt = conn.prepareStatement(sqlStr);
			
			if (paramList != null && paramList.length > 0) {
				for (int i = 1; i <= paramList.length; i++) {
					pstmt.setObject(i, paramList[i-1]);
				}
			}
			
			rset = pstmt.executeQuery();
			if (rset == null) {
			    if (conn != null)
				    conn.close();
				return null;
			}
			myrset.conn = conn;
			myrset.setMyResuleSet(rset);
		} catch (SQLException e) {
			close();
			log.error("error on execute: " + sqlStr);
			log.error(e.getMessage(), e);
			appendMessage("查询失败：" + e.getMessage());
		}
		return myrset;
	}
	
	@Override
	public List<Map<String, String>> query(String sql) {
		return query(sql, new String[] {});
	}
	
	
	@Override
	public List<Map<String, String>> query(String sql, List<String> args) {
		String[] paramArr = args.toArray(new String[0]);
		return query(sql, paramArr);
	}

    @Override
    public Pagination query(String sql, Pagination page) {
        return null;
    }

    @Override
    public Pagination query(String sql, Pagination page, String... paramList) {
        return null;
    }

    /**
	 * 
	 * @param sql
	 * @param paramList	参数列表
	 * @return
	 * @throws SQLException
	 */
	@Override
	public List<Map<String, String>> query(String sql, String... paramList) {
		List<Map<String, String>> result = new ArrayList<Map<String, String>>();
		MyResultSet rs = null;
		Connection conn = getConn();
		try {
			rs = executeQuery(conn, sql, paramList);
			ResultSetMetaData meta = null;
			int colCount = 0;
		
			if (rs != null && rs.rset != null) {
				meta = rs.rset.getMetaData();
				colCount = meta.getColumnCount();
				
				while (rs.next()) {
					Map<String, String> rows = new HashMap<String, String>();
					for (int i = 0; i < colCount; i++) {
						String colName = meta.getColumnLabel(i + 1);
						String colVal = getValue(meta, rs, i + 1);
						rows.put(colName, colVal);
					}
					result.add(rows);
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			appendMessage("查询失败：" + e.getMessage());
			return null;
		} finally {
			close();
		}
		return result;
	}
	

	@Override
	public List<String> queryOneColumn(String sql, String... params) {
		List<String> result = new ArrayList<>();
		MyResultSet rs = null;
		Connection conn = getConn();
		try {
			rs = executeQuery(conn, sql, params);
			if (rs != null && rs.rset != null) {
				while (rs.next()) {
					String colVal = getValue(rs.rset.getMetaData(), rs, 1);
					result.add(colVal);
				}
			} else
			    return null;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			appendMessage("查询失败：" + e.getMessage());
		} finally {
			close();
		}
		return result;
	}

    @Override
	public List<String> queryOneColumn(String sql) {
        return queryOneColumn(sql, new String[]{});
    }

	/**
	 * 查询一条记录
	 * 
	 * @param sql
	 * @return
	 * @throws SQLException
	 */
	@Override
	public Map<String, String> queryOneRow(String sql) {
		return queryOneRow(sql, new String[] {});
	}

	/**
	 * 查询一条记录
	 * 
	 * @param sql
	 * @param paramList
	 * @return
	 */
	@Override
	public Map<String, String> queryOneRow(String sql, String... paramList) {
		MyResultSet rs = null;
		Map<String, String> rows = new HashMap<String, String>();
		Connection conn = getConn();
		try {
			rs = executeQuery(conn, sql, paramList);
			ResultSetMetaData meta = null;
			int colCount = 0;

			if (rs != null && rs.rset != null) {
				meta = rs.rset.getMetaData();
				colCount = meta.getColumnCount();

				if (rs.next()) {
					for (int i = 0; i < colCount; i++) {
						String colName = meta.getColumnLabel(i + 1);
                        String colVal = getValue(meta, rs, i + 1);
						rows.put(colName, colVal);
					}
				}
			}

		} catch (Exception e) {
			log.error(e.getMessage(), e);
			appendMessage("查询失败：" + e.getMessage());
		} finally {
			close();
		}
		return rows;
	}

	@Override
	public int countselect(String sqlStr) {
		return countselect(sqlStr, new String[] {});
	}
	
	@Override
	public int countselect(String sqlStr, String... params) {
		int icount = -1;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		Connection _conn = null;
		String csql = "select count(*) from (" + sqlStr + ") temp";
		log.debug(csql);
		try {
			_conn = getConn();
			pstmt = _conn.prepareStatement(csql);
			
			if (params != null && params.length > 0) {
				for (int i = 1; i <= params.length; i++) {
					pstmt.setObject(i, params[(i-1)]);
				}
			}
			
			rset = pstmt.executeQuery();
			rset.next();
			icount = rset.getInt(1);
			rset.close();
		} catch (SQLException e) {
			log.error(e.getMessage(), e);
			icount = 0;
			appendMessage("查询失败：" + e.getMessage());
		} finally {
			close();
		}
		return icount;
	}
	
	private int countselect(Connection conn, String sqlStr, String... params) {
		int icount = -1;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		Connection _conn = null;
		String csql = "select count(*) from (" + sqlStr + ") temp";
		log.debug(csql);
		try {
			_conn = getConn();
			pstmt = _conn.prepareStatement(csql);
			
			if (params != null && params.length > 0) {
				for (int i = 1; i <= params.length; i++) {
					pstmt.setObject(i, params[(i-1)]);
				}
			}
			
			rset = pstmt.executeQuery();
			rset.next();
			icount = rset.getInt(1);
			rset.close();
		} catch (SQLException e) {
			log.error(e.getMessage(), e);
			icount = 0;
			appendMessage("查询失败：" + e.getMessage());
		}
		return icount;
	}
	
	/**
	 * 组装分页sql
	 * @param sql
	 * @param pageInt
	 * @param pageSize
	 * @return
	 */
	private String installPageSql(Connection conn, String sql, int pageInt, int pageSize) {
		String dbProductName = getDBProductName(conn);
		
		if ("mysql".equalsIgnoreCase(dbProductName)) {
			sql = "select * from (" + sql + ") row_ limit " + (pageInt - 1) * pageSize + "," + pageSize;
			
		} else if ("oracle".equalsIgnoreCase(dbProductName)) {
			sql = "select * from ( "
				+ "		select row_.*, rownum rownum_ from (" + sql + ")  row_ where rownum<=" + pageInt * pageSize
				+ " ) where rownum_>" + (pageInt - 1) * pageSize;
		}
		return sql;
	}
	
	
	/**
	 * 获取表结构
	 * list中的map结构：{Field:xxx, Type:xxx, Null:xxx, Key: xxx, Default: xxx, Extra: xxx}
	 */
	@Override
	public List<Map<String, String>> getTblStruct(String tblName) {
		String sqlCols = "show columns from " + tblName;
		List<Map<String, String>> tblCols;
		tblCols = query(sqlCols);
		return tblCols;
	}
}
