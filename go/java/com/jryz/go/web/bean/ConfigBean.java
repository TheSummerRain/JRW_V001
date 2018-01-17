package com.jryz.go.web.bean;

import com.jryz.bean.BasicEntity;

/**
* 数据源配置信息
* @author
*/
public class ConfigBean extends BasicEntity {

    private String id; // 
    private String title; // 配置名称
    private String basicPath; // 项目完整路径（根目录）
    private String srcPath; // 代码相对路径
    private String htmlPath; // html相对路径
    private String resourcesPath; // 配置文件相对路径
    private String dbUrl; // 数据库地址
    private String dbUserPassword; // 数据库密码
    private String dbUserName; // 数据库账号
    private String dbName; // 数据库名称
    private String createBy; // 创建人
    private String updateBy; // 修改人
    private String createDate; // 创建时间
    private String updateDate; // 修改时间

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBasicPath() {
        return basicPath;
    }

    public void setBasicPath(String basicPath) {
        this.basicPath = basicPath;
    }

    public String getSrcPath() {
        return srcPath;
    }

    public void setSrcPath(String srcPath) {
        this.srcPath = srcPath;
    }

    public String getHtmlPath() {
        return htmlPath;
    }

    public void setHtmlPath(String htmlPath) {
        this.htmlPath = htmlPath;
    }

    public String getResourcesPath() {
        return resourcesPath;
    }

    public void setResourcesPath(String resourcesPath) {
        this.resourcesPath = resourcesPath;
    }

    public String getDbUrl() {
        return dbUrl;
    }

    public void setDbUrl(String dbUrl) {
        this.dbUrl = dbUrl;
    }

    public String getDbUserPassword() {
        return dbUserPassword;
    }

    public void setDbUserPassword(String dbUserPassword) {
        this.dbUserPassword = dbUserPassword;
    }

    public String getDbUserName() {
        return dbUserName;
    }

    public void setDbUserName(String dbUserName) {
        this.dbUserName = dbUserName;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
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
