package com.test.core.service;

import com.test.core.bean.UserSonBean;
import com.jryz.dao.service.BasicServiceInterface;

/**
* test
* @author
*/
public interface UserSonService extends BasicServiceInterface <UserSonBean> {

    /**
     * 替换方法
     * 不存在 则添加 存在则更新
     * 并返回新的 bean
     * @param bean
     * @return
    */
    UserSonBean replace(UserSonBean bean);
}
