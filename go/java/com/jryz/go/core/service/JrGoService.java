package com.jryz.go.core.service;

import com.db.dao.DbConfig;
import com.jryz.go.core.bean.Table;

/**
 * 代码生成器 逻辑接口
 */
public interface JrGoService {

    /**
     * 获取 表结构
     * @param tableName
     * @return
     */
    Table getTable(String tableName, DbConfig dbConfig);

    /**
     * 生成代码
     *
     * @param table
     */
    void inputTable(Table table);
}
