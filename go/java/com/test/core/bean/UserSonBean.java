package com.test.core.bean;

import com.jryz.bean.BasicEntity;
import java.util.List;

/**
* test
* @author
*/
public class UserSonBean extends BasicEntity {

    private String code; // 儿子编号
    private String name; // 儿子姓名
    private String userId; // 爸爸
    private String id; // 


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
