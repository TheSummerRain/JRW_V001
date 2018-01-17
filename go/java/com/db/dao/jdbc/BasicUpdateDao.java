package com.db.dao.jdbc;

import com.db.dao.DbConfig;
import com.db.dao.UpdateDao;

import javax.sql.DataSource;
import java.sql.*;
import java.util.*;

public class BasicUpdateDao extends BasicDaoWrapper implements UpdateDao {
	
	
	/**
	 * 通过jdniName创建连接
	 * @param jndiName jndi的名称
	 */
	public BasicUpdateDao(String jndiName) {
		this.dbConnector = new DbConnector(jndiName);
	}
	
	public BasicUpdateDao(DataSource source) {
		this.dbConnector = new DbConnector(source);
	}
	
	
	public BasicUpdateDao(DbConfig dbConfig) {
		this.dbConnector = new DbConnector(dbConfig);
		
	}
	
	public BasicUpdateDao(DbCode dbCode) {
		this.dbConnector = new DbConnector(dbCode);
	}
	
	public BasicUpdateDao(DbCodeFinder dbCodeFinder) {
		this.dbConnector = new DbConnector(dbCodeFinder);
	}
	
	
	@SuppressWarnings("rawtypes")
	public void excecuteProcedure(String procedureName, Map params) {
		CallableStatement proc = null;
		String procedure = "{ call " + procedureName + "(";
		int paramsSize = params.size();
		String paramStr = "";
		if (paramsSize > 0) {
			for (int i = 0; i < paramsSize; i++) {
				paramStr = paramStr + "?,";
			}
			paramStr = paramStr.substring(0, paramStr.length() - 1);
		}
		procedure = procedure + paramStr + ") }";
		try {
			proc = getConn().prepareCall(procedure);
			Iterator iterator = params.keySet().iterator();
			int i = 0;
			while (iterator.hasNext()) {
				i++;
				String key = (String) iterator.next();
				if ("int".equalsIgnoreCase(key)) {
					proc.setInt(i, Integer.parseInt((String) params.get(key)));
				} else if ("String".equalsIgnoreCase(key)) {
					proc.setString(i, (String) params.get(key));
				} else if ("float".equalsIgnoreCase(key)) {
					proc.setFloat(i, Float.parseFloat((String) params.get(key)));
				} else if ("double".equalsIgnoreCase(key)) {
					proc.setDouble(i, Double.parseDouble((String) params.get(key)));
				} else {
					throw new SQLException("error param data type in the " + i + " param!");
				}
			}
			proc.execute();
			proc.close();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			appendMessage("执行失败：" + e.getMessage());
		} finally {
			close();
		}
	}
	
	/**
	 * 基本更新方法
	 * @param sqlStr
	 * @return
	 */
	@Override
	public int updateByList(String sqlStr) {
		int rset = 0;
		Statement stmt = null;
		try {
			stmt = getConn().createStatement();
			rset = stmt.executeUpdate(sqlStr);
		} catch (SQLException e) {
			rset = -1;
			log.error("error on execute :" + sqlStr);
			log.error(e.getMessage(), e);
			appendMessage("更新失败：" + e.getMessage());
		} finally {
			close();
		}
		return rset;
	}

	/**
	 * 
	 * @param sqlStr
	 * @param longString
	 * @return
	 */
	/*
	public int updateByList(String sqlStr, String longString) {
		int rset = 0;
		PreparedStatement pstmt;
		StringReader strR;
		Connection _conn = null;
		try {
			_conn = getConn();
			pstmt = _conn.prepareStatement(sqlStr);
			strR = new StringReader(longString);
			pstmt.setCharacterStream(1, strR, longString.length());
			rset = pstmt.executeUpdate();
			pstmt.close();
			_conn.close();
		} catch (Exception e) {
			rset = -1;
			log.error("error on execute :" + sqlStr);
			log.error(e.getMessage(), e);
			appendMessage("更新失败：" + e.getMessage());
			try {
				_conn.close();
			} catch (SQLException e2) {
				log.error("connection closed failed. on execute :" + sqlStr);
			}
		}
		return rset;
	}*/
	
	/**
	 * 
	 * @param sqlStr
	 * @param longString1
	 * @param longString2
	 * @return
	 */
	/*
	public int updateByList(String sqlStr, String longString1, String longString2) {
		int rset = 0;
		PreparedStatement pstmt;
		StringReader strR1, strR2;
		Connection _conn = null;
		try {
			_conn = getConn();
			pstmt = _conn.prepareStatement(sqlStr);
			strR1 = new StringReader(longString1);
			strR2 = new StringReader(longString2);
			pstmt.setCharacterStream(1, strR1, longString1.length());
			pstmt.setCharacterStream(1, strR2, longString2.length());
			rset = pstmt.executeUpdate();
			pstmt.close();
			_conn.close();
		} catch (SQLException e) {
			rset = -1;
			appendMessage("更新失败：" + e.getMessage());
			try {
				_conn.close();
			} catch (SQLException e2) {
				log.error("connection closed failed. on execute :" + sqlStr);
			}
		}
		return rset;
	}*/
	
	/**
	 * 批量更新方法
	 * @param sqlgroup
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public int updateBatch(Hashtable sqlgroup) {
		List sqllist = new ArrayList();
		for (int i = 0; i < sqlgroup.size(); i++) {
			sqllist.add(sqlgroup.get(i + 1));
		}
		return updateBatch(sqllist);
	}
	
	/**
	 * 批量更新方法
	 * @param sqlgroup
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public int updateBatch(List sqlgroup) {
		int rset = 0;
		Statement stmt = null;
		int sqlgroupsize = 0;
		sqlgroupsize = sqlgroup.size();
		try {
			getConn().setAutoCommit(false);
			stmt = getConn().createStatement();
			for (int i = 0; i < sqlgroupsize; i++) {
				String sql = (String) sqlgroup.get(i);
				stmt.addBatch(sql);
			}

			stmt.executeBatch();
			rset = stmt.getUpdateCount();
			getConn().commit();
			
		} catch (SQLException e) {
			rset = -1;
			log.error(e.getMessage(), e);
			appendMessage("更新失败：" + e.getMessage());
			try {
				getConn().rollback();
			} catch (SQLException ex) {
				log.error(ex.getMessage(), ex);
			}
		} finally {
			close();
		}
		return rset;
	}
	
	
	/**
	 * 批量更新方法
	 * @return
	 */
	@Override
	public int updateBatch(String sql, List<Object[]> params) {
		int rset = 0;
		int onceSaveNumber  = 50;
		int cacheNumber = 0;
		PreparedStatement pstmt = null;
        Object [] rowData = null;
		try {
			getConn().setAutoCommit(false);
			pstmt = getConn().prepareStatement(sql);
			for (int i = 0; i < params.size(); i++) {
				rowData = params.get(i);
				for (int j = 1; j <= rowData.length; j++) {
					pstmt.setObject(j, rowData[j-1]);
				}
				pstmt.addBatch();
				cacheNumber ++;
				if (cacheNumber % onceSaveNumber == 0 || cacheNumber == params.size()) {
					pstmt.executeBatch();
				}
			}
			
			rset = pstmt.getUpdateCount();
			getConn().commit();
		} catch (SQLException e) {
			rset = -1;
			log.error(e.getMessage(), e);
			appendMessage("更新失败：" + e.getMessage());
			try {
				getConn().rollback();
			} catch (SQLException ex) {
				log.error(ex.getMessage(), ex);
			}
		} finally {
			close();
		}
		return rset;
	}
	
	@Override
	public int updateBatchNoRollback(String sql, List<Object[]> params) {
		int rset = 0;
		int onceSaveNumber  = 50;
		int cacheNumber = 0;
		PreparedStatement pstmt = null;
        Object [] paramList = null;
		int sqlgroupsize = 0;
		sqlgroupsize = params.size();
		try {
			// _conn.setAutoCommit(false);
			pstmt = getConn().prepareStatement(sql);
			for (int i = 0; i < sqlgroupsize; i++) {
				paramList = params.get(i);
				for (int j = 1; j <= paramList.length; j++) {
					pstmt.setObject(j, paramList[j-1]);
				}
				pstmt.addBatch();
				cacheNumber ++;
				if (cacheNumber % onceSaveNumber == 0 || cacheNumber == params.size()) {
					try {
						pstmt.executeBatch();
					} catch (SQLException e) {
						appendMessage(e.getMessage());
						log.debug(e.getMessage(), e);
					}
				}
			}
			
			rset = pstmt.getUpdateCount();
			getConn().commit();
		} catch (SQLException e) {
			log.error(e.getMessage(), e);
			appendMessage("更新失败：" + e.getMessage());
		} finally {
			close();
		}
		return rset;
	}
	
	/**
	 * 更新方法
	 * @param sql 允许带?占位符的sql
	 * @param paramList ?占位符对应的值列表
	 * @return
	 */
	@Override
	public int update(String sql, Object... paramList) {
		int rset = 0;
		PreparedStatement pstmt = null;
		try {
			pstmt = getConn().prepareStatement(sql);

            Object v;
            for (int i = 1; i <= paramList.length; i++) {
                if ((v = paramList[i-1]) != null && v.getClass().isEnum())
                    v = v.toString();
                pstmt.setObject(i, v);
            }
			
			rset = pstmt.executeUpdate();
		} catch (SQLException e) {
			rset = -1;
			log.error("error on execute :" + sql);
			log.error(e.getMessage(), e);
			appendMessage("更新失败：" + e.getMessage());
		} finally {
			close();

		}
		return rset;
	}
	
	@Override
	public int updateByList(String sql, List<Object> paramList) {
		return update(sql, paramList.toArray());
	}
	

	/**
	 * 更新方法
	 * @param sql 允许带?占位符的sql
	 * @param paramList ?占位符对应的值列表
	 * @return 主键id
	 */
	public long updateAndReturnKey(String sql, Object... paramList) {
		long id = -1L;
		PreparedStatement pstmt = null;
		try {
			pstmt = getConn().prepareStatement(sql);
			
			for (int i = 1; i <= paramList.length; i++) {
				pstmt.setObject(i, paramList[i-1]);
			}
			
			pstmt.executeUpdate();
			 // 检索由于执行此 Statement 对象而创建的所有自动生成的键   
            ResultSet rs = pstmt.getGeneratedKeys();   
            if (rs.next()) {  
                id = rs.getLong(1);   
            }
		} catch (SQLException e) {
			log.error("error on execute :" + sql);
			log.error(e.getMessage(), e);
			appendMessage("更新失败：" + e.getMessage());
		} finally {
			close();
			
		}
		return id;
	}
}
