package com.db.dao.jdbc;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class ConnectionWrapper {
	protected LocalDateTime createTime = LocalDateTime.now();
	private Connection conn;
	private Logger log = LoggerFactory.getLogger(this.getClass());

	public ConnectionWrapper(Connection conn) {
		this.conn = conn;
		log.debug("创建一个数据库连接，创建时间：" + createTime.toString());
	}

	public LocalDateTime getCreateTime() {
		return createTime;
	}

	public Connection getConn() {
		return conn;
	}

	public void close(boolean isCommit) {
		try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
			log.error(e.getMessage(), e);
		}
		conn = null;
	}

}
