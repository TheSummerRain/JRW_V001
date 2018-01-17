package com.jryz.go.web.service;

import com.jryz.go.web.bean.TableBean;
import com.jryz.dao.service.BasicServiceInterface;

/**
* 表信息
* @author
*/
public interface TableService extends BasicServiceInterface <TableBean> {

    /**
     * 替换方法
     * 不存在 则添加 存在则更新
     * 并返回新的 bean
     * @param bean
     * @return
    */
    TableBean replace(TableBean bean);
}
