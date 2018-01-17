package com.jryz.go.web.service;

import com.db.dao.DbConfig;
import com.jryz.go.InputType;
import com.jryz.go.web.bean.FieldBean;
import com.jryz.go.web.bean.TableBean;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 导入表信息 service
 */
public interface TableCoreService {

    public List<Map<String, String>> getTables(DbConfig dbConfig, String tableName);

    public TableBean getTable(DbConfig dbConfig, TableBean table);

    public List<FieldBean> getFidlds(DbConfig dbConfig, TableBean table);

    /**
     * 生成代码
     * @param tableBean
     */
    public void createCode(TableBean tableBean, Set<InputType> inputTypes);
}
