package com.db.dao.jdbc;

public enum DbCode {
    ROOGO("roogo", ""),
	TEST("test",""),
	CORE("core","核心数据库"),
	BMS("bms", "本地测试库"),
	REDIS_SYS("REDIS", "REDIS 数据库 1", 1),
    REDIS_HOST("REDIS", "REDIS 数据库 0", 0);

	private String code;
	private String name;
    private int index;
	
	private DbCode(String code, String name) {
		this.code = code;
		this.name = name;
	}

    private DbCode(String code, String name, int index) {
        this.code = code;
        this.name = name;
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public String getCode() {
		return code;
	}

	public String getName() {
		return name;
	}
	
}



