package com.test.core.bean;

import com.jryz.bean.BasicEntity;
import com.test.core.bean.UserSonBean;
import java.util.List;

/**
* t
* @author
*/
public class TestUserBean extends BasicEntity {

    private String id; // 
    private String name; // 姓名

    private List<UserSonBean> userSonList; // test

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

    public List<UserSonBean> getUserSonList() {
        return userSonList;
    }

    public void setUserSonList(List<UserSonBean> userSonList) {
        this.userSonList = userSonList;
    }
}
