package com.jryz.go.core.bean;

import java.util.List;

/**
 * 表的基类
 * @author jr
 */
public class Table {

    private String id;
    private String tableName;       // 表名
    private String name;            // 加工后的 名称 如 userBean
    private String content;         // 加工后的 名称 如 userBean
    private String title;           // 表标题
    private String packageName;     // 包名
    private String className;
    private List<Field> fields;
    private List<Field[]> unique;   // 唯一约束

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public List<Field> getFields() {
        return fields;
    }

    public void setFields(List<Field> fields) {
        this.fields = fields;
    }

    public List<Field[]> getUnique() {
        return unique;
    }

    public void setUnique(List<Field[]> unique) {
        this.unique = unique;
    }
}
