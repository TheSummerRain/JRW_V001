package com.db.dao;

public class DbConfig {
	private String ip; // 数据库连接ip
	private String port; // 数据库连接
	private String loginName; // 登录账号名称
	private String url;
	private String password;
	private String dbName; // 数据库名称
	private String alias; // 数据库别名

    public DbConfig() {
		super();
	}

    protected DbConfig(String ip, String port, String loginName, String password, String dbName) {
		super();
		this.ip = ip;
		this.port = port;
		this.loginName = loginName;
		this.password = password;
		this.dbName = dbName;
	}

    protected DbConfig(String ip, String port, String loginName, String password, String dbName, String alias) {
		super();
		this.ip = ip;
		this.port = port;
		this.loginName = loginName;
		this.password = password;
		this.dbName = dbName;
		this.alias = alias;
	}

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDbName() {
		return dbName;
	}

	public void setDbName(String dbName) {
		this.dbName = dbName;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}
}