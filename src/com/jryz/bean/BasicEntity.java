package com.jryz.bean;

import java.io.Serializable;

/**
 * 所有缓存实体需继承该方法
 * Created by jryc on 2016/12/13.
 */
public class BasicEntity implements Serializable {

    private static final long serialVersionUID = 8672765984400992961L;
    private String id; // 作为  唯一标识

    private String createDate;
    private String updateDate;
    private String updateBy;
    private String createBy;

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

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    protected BasicEntity(String id){
        this.id = id;
    }

    protected BasicEntity(){
    }

    public String getId() {
        return id;
    }

    public void setId(String redisId) {
        this.id = redisId;
    }
}
