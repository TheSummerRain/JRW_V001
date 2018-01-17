package com.jryz.go.web.service;

import com.jryz.go.web.bean.FieldBean;
import com.jryz.dao.service.BasicServiceInterface;

/**
* 字段信息
* @author
*/
public interface FieldService extends BasicServiceInterface <FieldBean> {

    /**
     * 替换方法
     * 不存在 则添加 存在则更新
     * 并返回新的 bean
     * @param bean
     * @return
    */
    FieldBean replace(FieldBean bean);
}
