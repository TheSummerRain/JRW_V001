package com.db.dao.jdbc;

public class DbCodeFinder {

	private DbCode dbCode = null;

	public DbCodeFinder(String dbId) {

		DbCode[] dbCodes = DbCode.values();
		for (DbCode dbCode : dbCodes) {
			if (dbCode.getCode().equals(dbId)) {
				this.dbCode = dbCode;
				break;
			}
		}
	}

	public DbCode getDbCode() {
		return dbCode;
	}

}
