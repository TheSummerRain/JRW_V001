package com.db.dao.jdbc;

import com.db.dao.DbConfig;
import com.db.dao.Pagination;
import com.db.dao.QueryDao;
import com.db.dao.UpdateDao;

import java.util.List;
import java.util.Map;

public class BasicJdbcDao extends BasicDaoWrapper implements QueryDao, UpdateDao{

	
	private QueryDao queryDao;
	private UpdateDao updateDao;
	
	public BasicJdbcDao(DbConfig dbConfig) {
		queryDao = new BasicQueryDao(dbConfig);
		updateDao = new BasicUpdateDao(dbConfig);
		
	}

	@Override
	public int updateByList(String sql) {
		return updateDao.updateByList(sql);
	}

	@Override
	public int update(String sql, Object... paramList) {
		return updateDao.update(sql, paramList);
	}

	@Override
	public int updateByList(String sql, List<Object> paramList) {
		return updateDao.update(sql, paramList.toArray());
	}

	@Override
	public int updateBatch(String sql, List<Object[]> params) {
		return updateDao.updateBatch(sql, params);
	}

	@Override
	public int updateBatchNoRollback(String sql, List<Object[]> params) {
		return updateDao.updateBatchNoRollback(sql, params);
	} 
	
	@Override
	public List<String[]> queryForArray(String sql) {
		return queryDao.queryForArray(sql);
	}

	@Override
	public String getUnique(String sql) {
		return queryDao.getUnique(sql);
	}

	@Override
	public String getUnique(String sql, List<String> paramList) {
		return queryDao.getUnique(sql, paramList);
	}

	@Override
	public String getUnique(String sql, String... paramList) {
		return queryDao.getUnique(sql, paramList);
	}

	@Override
	public List<Map<String, String>> query(String sql) {
		return queryDao.query(sql);
	}

	@Override
	public List<Map<String, String>> query(String sql, String... args) {
		return queryDao.query(sql, args);
	}

	@Override
	public List<Map<String, String>> query(String sql, List<String> args) {
		return queryDao.query(sql, args);
	}

    @Override
    public Pagination query(String sql, Pagination page) {
        return null;
    }

    @Override
    public Pagination query(String sql, Pagination page, String... paramList) {
        return null;
    }

    @Override
	public List<String> queryOneColumn(String sql) {
		return queryDao.queryOneColumn(sql);
	}

	@Override
	public List<String> queryOneColumn(String sql, String... args) {
		return queryDao.queryOneColumn(sql, args);
	}

	@Override
	public Map<String, String> queryOneRow(String sql) {
		return queryDao.queryOneRow(sql);
	}

	@Override
	public Map<String, String> queryOneRow(String sql, String... params) {
		return queryDao.queryOneRow(sql, params);
	}

	@Override
	public List<Map<String, String>> getTblStruct(String tblName) {
		return queryDao.getTblStruct(tblName);
	}

	@Override
	public int countselect(String sqlStr) {
		return queryDao.countselect(sqlStr);
	}

	@Override
	public int countselect(String sqlStr, String... params) {
		return queryDao.countselect(sqlStr, params);
	}
}
