package com.jryz.go.web.bean;

import com.jryz.bean.BasicEntity;

import java.util.List;

/**
* 字段信息
* @author
*/
public class FieldBean extends BasicEntity {

    private String id; // 
    private String name; // 字段名
    private String unName; // 
    private String javaName; // java类名
    private String content; // 描述
    private Integer length; // 长度
    private String notNull; // 是否为空
    private String tableId; // 表id
    private String fieldType; // 字段类型
    private Integer type; // 1 单对单,2 单对多
    private String prTableId; // 单对多操作时（多对多模型） 不可为空。并确保关联字段存在
    private String dbTableId; // 数据源表
    private String dbTableShowField; // 数据源表展示字段 字段逗号分隔
    private String dbTableShowFieldDelimiter; // 分隔符 缺省 -
    private String input; // input/radio/checkbox/select
    private Integer seelectType; // 1 模糊查询， 2 范围查询
    private String createBy; //
    private String updateBy; //
    private String createDate; //
    private String updateDate; //

    private TableBean tableBean;
    private TableBean dbTableBean;
    private TableBean prTableBean;
    private List<FieldBean> showTableFieldList;


    public List<FieldBean> getShowTableFieldList() {
        return showTableFieldList;
    }

    public void setShowTableFieldList(List<FieldBean> showTableFieldList) {
        this.showTableFieldList = showTableFieldList;
    }

    public TableBean getTableBean() {
        return tableBean;
    }

    public void setTableBean(TableBean tableBean) {
        this.tableBean = tableBean;
    }

    public TableBean getDbTableBean() {
        return dbTableBean;
    }

    public void setDbTableBean(TableBean dbTableBean) {
        this.dbTableBean = dbTableBean;
    }

    public TableBean getPrTableBean() {
        return prTableBean;
    }

    public void setPrTableBean(TableBean prTableBean) {
        this.prTableBean = prTableBean;
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

    public String getUnName() {
        return unName;
    }

    public void setUnName(String unName) {
        this.unName = unName;
    }

    public String getJavaName() {
        return javaName;
    }

    public void setJavaName(String javaName) {
        this.javaName = javaName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public String getNotNull() {
        return notNull;
    }

    public void setNotNull(String notNull) {
        this.notNull = notNull;
    }

    public String getTableId() {
        return tableId;
    }

    public void setTableId(String tableId) {
        this.tableId = tableId;
    }

    public String getFieldType() {
        return fieldType;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getPrTableId() {
        return prTableId;
    }

    public void setPrTableId(String prTableId) {
        this.prTableId = prTableId;
    }

    public String getDbTableId() {
        return dbTableId;
    }

    public void setDbTableId(String dbTableId) {
        this.dbTableId = dbTableId;
    }

    public String getDbTableShowField() {
        return dbTableShowField;
    }

    public void setDbTableShowField(String dbTableShowField) {
        this.dbTableShowField = dbTableShowField;
    }

    public String getDbTableShowFieldDelimiter() {
        return dbTableShowFieldDelimiter;
    }

    public void setDbTableShowFieldDelimiter(String dbTableShowFieldDelimiter) {
        this.dbTableShowFieldDelimiter = dbTableShowFieldDelimiter;
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public Integer getSeelectType() {
        return seelectType;
    }

    public void setSeelectType(Integer seelectType) {
        this.seelectType = seelectType;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

}
