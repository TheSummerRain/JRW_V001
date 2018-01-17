package com.db.dao;

import java.util.List;

public interface UpdateDao extends JdbcDao{


    /**
     * @param sql sql
     * @return 成功/失败
     */
    int updateByList(String sql);
	
	/**
	 * @param sql sql
	 * @param paramList 参数列表
	 * @return int
	 */
	int update(String sql, Object... paramList);
	
	int updateByList(String sql, List<Object> paramList);

	int updateBatch(String sql, List<Object[]> params);

	int updateBatchNoRollback(String sql, List<Object[]> params);
}
