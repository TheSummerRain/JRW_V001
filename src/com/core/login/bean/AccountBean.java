package com.core.login.bean;

import com.jryz.bean.BasicEntity;

/**
* 账号
* @author
*/
public class AccountBean extends BasicEntity {

    private String id; // 
    private String loginName; // 登录账号
    private String type; // 类型
    private AccountTypeBean typeBean;
    private String password; // 密码
    private String createBy; // 创建人
    private String updateBy; // 修改人
    private String createDate; // 创建时间
    private String updateDate; // 修改时间

    public AccountTypeBean getTypeBean() {
        return typeBean;
    }

    public void setTypeBean(AccountTypeBean typeBean) {
        this.typeBean = typeBean;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
