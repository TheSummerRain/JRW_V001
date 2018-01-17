package com.jryz.go.web.service;

import com.jryz.go.web.bean.TableUnBean;
import com.jryz.dao.service.BasicServiceInterface;

/**
* 唯一约束
* @author
*/
public interface TableUnService extends BasicServiceInterface <TableUnBean> {

    /**
     * 替换方法
     * 不存在 则添加 存在则更新
     * 并返回新的 bean
     * @param bean
     * @return
    */
    TableUnBean replace(TableUnBean bean);
}
