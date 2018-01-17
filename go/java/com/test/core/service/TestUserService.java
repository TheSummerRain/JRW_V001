package com.test.core.service;

import com.test.core.bean.TestUserBean;
import com.jryz.dao.service.BasicServiceInterface;

/**
* t
* @author
*/
public interface TestUserService extends BasicServiceInterface <TestUserBean> {

    /**
     * 替换方法
     * 不存在 则添加 存在则更新
     * 并返回新的 bean
     * @param bean
     * @return
    */
    TestUserBean replace(TestUserBean bean);
}
