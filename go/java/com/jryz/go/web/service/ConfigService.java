package com.jryz.go.web.service;

import com.jryz.go.web.bean.ConfigBean;
import com.jryz.dao.service.BasicServiceInterface;

/**
* 数据源配置信息
* @author
*/
public interface ConfigService extends BasicServiceInterface <ConfigBean> {

    /**
     * 替换方法
     * 不存在 则添加 存在则更新
     * 并返回新的 bean
     * @param bean
     * @return
    */
    ConfigBean replace(ConfigBean bean);
}
