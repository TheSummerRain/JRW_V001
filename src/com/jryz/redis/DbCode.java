package com.jryz.redis;

/**
 * redis 数据标记 为了防止不相干数据 key 冲突 添加前缀 并以此区分使用数据库
 * Created by jryc on 2017/5/27.
 */
public enum DbCode {

    LOGIN_KEY(com.jryz.dao.DbCode.REDIS_SYS),
    LOGINFO(com.jryz.dao.DbCode.REDIS_SYS),
    SIGN(com.jryz.dao.DbCode.REDIS_SYS),
    USER_LOGGIN_KEY(com.jryz.dao.DbCode.REDIS_SYS),
    UUID_DATA(com.jryz.dao.DbCode.REDIS_SYS), // 通过uuid 标记的临时数据
    ;

    DbCode(com.jryz.dao.DbCode dbCode) {
        this.dbCode = dbCode;
    }

    // 对应数据库
    private com.jryz.dao.DbCode dbCode;

    public com.jryz.dao.DbCode getDbCode() {
        return dbCode;
    }
}
