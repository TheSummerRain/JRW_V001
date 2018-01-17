package com.jryz.go.web.bean;

import com.jryz.bean.BasicEntity;

import java.util.List;

/**
* 表信息
* @author
*/
public class TableBean extends BasicEntity {

    private String id; // 
    private String tableName; // 表名
    private String name; // 加工后的 名称 如 userBean
    private String unName; // 
    private String packageName; // 包名
    private String content; // 描述
    private String configId; // 配置id
    private Integer isInput; // 是否启动导入模板
    private String prTableId; //
    private String prTableFieId; //
    private String createBy; //
    private String updateBy; //
    private String createDate; //
    private String updateDate; //

    private ConfigBean configBean; // 配置
    private List<FieldBean> fieldList;
    private List<FieldBean> onefieldList;
    private List<FieldBean> manyfieldList;
    private List<List<FieldBean>> unFieldList;
    private List<TableBean> sonTableBean;
    private FieldBean prFieldBean;

    public List<FieldBean> getOnefieldList() {
        return onefieldList;
    }

    public void setOnefieldList(List<FieldBean> onefieldList) {
        this.onefieldList = onefieldList;
    }

    public List<List<FieldBean>> getUnFieldList() {
        return unFieldList;
    }

    public void setUnFieldList(List<List<FieldBean>> unFieldList) {
        this.unFieldList = unFieldList;
    }

    public List<FieldBean> getManyfieldList() {
        return manyfieldList;
    }

    public void setManyfieldList(List<FieldBean> manyfieldList) {
        this.manyfieldList = manyfieldList;
    }

    public List<TableBean> getSonTableBean() {
        return sonTableBean;
    }

    public void setSonTableBean(List<TableBean> sonTableBean) {
        this.sonTableBean = sonTableBean;
    }

    public FieldBean getPrFieldBean() {
        return prFieldBean;
    }

    public void setPrFieldBean(FieldBean prFieldBean) {
        this.prFieldBean = prFieldBean;
    }

    public List<FieldBean> getFieldList() {
        return fieldList;
    }

    public void setFieldList(List<FieldBean> fieldList) {
        this.fieldList = fieldList;
    }

    public ConfigBean getConfigBean() {
        return configBean;
    }

    public void setConfigBean(ConfigBean configBean) {
        this.configBean = configBean;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
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

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getConfigId() {
        return configId;
    }

    public void setConfigId(String configId) {
        this.configId = configId;
    }

    public Integer getIsInput() {
        return isInput;
    }

    public void setIsInput(Integer isInput) {
        this.isInput = isInput;
    }

    public String getPrTableId() {
        return prTableId;
    }

    public void setPrTableId(String prTableId) {
        this.prTableId = prTableId;
    }

    public String getPrTableFieId() {
        return prTableFieId;
    }

    public void setPrTableFieId(String prTableFieId) {
        this.prTableFieId = prTableFieId;
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
