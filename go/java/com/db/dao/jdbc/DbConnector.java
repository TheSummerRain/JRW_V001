package com.db.dao.jdbc;

import com.db.dao.DbConfig;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

class DbConnector {
	
	private static Logger log = LoggerFactory.getLogger(DbConnector.class);

	private String jndiName;
	private DataSource source;
	private DbConfig dbConfig;
	private DbCode dbCode;
	private DbCodeFinder dbCodeFinder;
	
	private int connectBy;
	
	public DbConnector(String jndiName) {
		this.jndiName = jndiName;
		this.connectBy = 1;
	}
	
	public DbConnector(DataSource source) {
		this.source = source;
		this.connectBy = 2;
	}
	
	public DbConnector(DbConfig dbConfig) {
		this.dbConfig = dbConfig;
		this.connectBy = 3;
	}
	
	public DbConnector(DbCode dbCode) {
		this.dbCode = dbCode;
		this.connectBy = 4;
	}
	
	public DbConnector(DbCodeFinder dbCodeFinder) {
		this.dbCodeFinder = dbCodeFinder;
		this.connectBy = 5;
	}
	
	
	public ConnectionWrapper getConn() {
		Connection conn = null;
		switch (connectBy) {
		case 1:
			conn = getConnByJndiName();
			break;
		case 2:
			conn = getConnByDatasource();
			break;
		case 3:
			conn = getConnByDbConfig();
			break;
		case 4:
			conn = getConnByDbCode();
			break;
		case 5:
			conn = getConnByDbCodeFinder();
			break;
		default:
			break;
		}
		return new ConnectionWrapper(conn);
	}
	

	private Connection getConnByJndiName() {
		try {
			Context ctx = (Context) new InitialContext().lookup("java:comp/env");
			DataSource source = (DataSource) ctx.lookup(this.jndiName);
			return source.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private Connection getConnByDatasource() {
		if (this.source != null) {
			try {
				return this.source.getConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	private Connection getConnByDbConfig() {
		try {
            Class.forName("com.mysql.jdbc.Driver");
            if (StringUtils.isNotBlank(dbConfig.getUrl()))
                return DriverManager.getConnection(dbConfig.getUrl(), dbConfig.getLoginName(), dbConfig.getPassword());

            return DriverManager.getConnection("jdbc:mysql://" + dbConfig.getIp() + ":" + dbConfig.getPort() + "/" + dbConfig.getDbName() + "?characterEncoding=utf8&amp;zeroDateTimeBehavior=convertToNull", dbConfig.getLoginName(), dbConfig.getPassword());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}

	private Connection getConnByDbCode() {
		/*try {
			DataSource source = (DataSource) ApplicationContextUtil.getContext().getBean(this.dbCode.getCode());
			return source.getConnection();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}*/
		return null;
	}
	
	private Connection getConnByDbCodeFinder() {
		/*try {
			DbCode dbCode = this.dbCodeFinder.getDbCode();
			DataSource source = (DataSource) ApplicationContextUtil.getContext().getBean(dbCode.getCode());
			return source.getConnection();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}*/
		return null;
	}

}
