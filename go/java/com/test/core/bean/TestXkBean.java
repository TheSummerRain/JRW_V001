package com.test.core.bean;

import com.jryz.bean.BasicEntity;
import com.test.core.bean.TestUserBean;
import com.test.core.bean.TestUserBean;
import java.util.List;

/**
* test
* @author
*/
public class TestXkBean extends BasicEntity {

    private String prUserId; // 老师
    private String name; // 学科名称
    private String id; // 
    private String userId; // 学生

    private TestUserBean prUserBean; // test
    private TestUserBean userBean; // test

    public String getPrUserId() {
        return prUserId;
    }

    public void setPrUserId(String prUserId) {
        this.prUserId = prUserId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public TestUserBean getPrUserBean() {
        return prUserBean;
    }

    public void setPrUserBean(TestUserBean prUserBean) {
        this.prUserBean = prUserBean;
    }

    public TestUserBean getUserBean() {
        return userBean;
    }

    public void setUserBean(TestUserBean userBean) {
        this.userBean = userBean;
    }
}
