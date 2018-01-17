package com.core.login.bean;

import com.jryz.bean.BasicEntity;

/**
* 账号类型
* @author
*/
public class AccountTypeBean extends BasicEntity {

    private String id; // 
    private String name; // 类型名称
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
