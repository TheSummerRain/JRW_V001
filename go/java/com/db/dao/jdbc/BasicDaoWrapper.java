package com.db.dao.jdbc;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


abstract class BasicDaoWrapper {

	protected Logger log = LoggerFactory.getLogger(this.getClass());

	DbConnector dbConnector;
    private String message = "";
    private ConnectionWrapper connWrapper;

    protected String getValue(ResultSetMetaData mate, MyResultSet rs, int i) throws Exception{
        String colVal;
        switch (mate.getColumnType(i)) {
            case Types.LONGVARBINARY:
            case Types.BLOB:
                byte[] bytes;
                if ((bytes = rs.getBytes(i)) != null) {
                    colVal = new String(bytes, "utf-8");
                } else {
                    colVal = null;
                }
                break;
            default:
                colVal = rs.getString(i);
        }
        return getValue(colVal);
    }

    private String getValue(String colVal){
        if (colVal != null && colVal.matches("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}\\.\\d{1}")) {
            colVal = colVal.substring(0, colVal.length() - 2);
        }
        return colVal;
    }
	/**
	 * 获取数据库名
	 * 
	 * @return 数据库名
	 */
	String getDBProductName(Connection conn) {
		String dbProductName = null;
		try {
			dbProductName = conn.getMetaData().getDatabaseProductName();
		} catch (SQLException e) {
			log.error(e.getMessage(), e);
			appendMessage("查询失败：" + e.getMessage());
		}
		return dbProductName;
	}

	/**
	 * 从数据源获取数据库链接
	 * 
	 * @return 数据库连接
	 */
	protected Connection getConn() {
		if (connWrapper == null) {
			connWrapper = dbConnector.getConn();
		}
		if (connWrapper.getConn() == null) {
			log.error("getOne connection failed.");
			appendMessage("获取连接失败");
		}
		return connWrapper.getConn();
	}

	public void close(boolean isCommit) {
		try {
			log.debug("关闭一个连接.");
			connWrapper.close(isCommit);
		} finally {
			connWrapper = null;
		}
	}

	public void close() {
		close(false);
	}

	void appendMessage(String msg) {
        if (this.message != null && !"".equals(this.message)) {
            this.message += " | " + msg;
        } else {
            this.message = msg;
        }
    }

	public void resetMessage() {
		this.message = "";
	}

	public String getMessage() {
		return this.message;
	}

	private ConnectionWrapper getConnWrapper() {
		return connWrapper;
	}

}
