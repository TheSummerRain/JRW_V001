package com.test.core.service;

import com.test.core.bean.TestXkBean;
import com.jryz.dao.service.BasicServiceInterface;

/**
* test
* @author
*/
public interface TestXkService extends BasicServiceInterface <TestXkBean> {

    /**
     * 替换方法
     * 不存在 则添加 存在则更新
     * 并返回新的 bean
     * @param bean
     * @return
    */
    TestXkBean replace(TestXkBean bean);
}
