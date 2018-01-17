package com.jryz.go.web.service.imp;

import com.jryz.go.web.bean.TableUnBean;
import com.jryz.go.web.maper.TableUnMaper;
import com.jryz.go.web.service.TableUnService;
import com.jryz.core.free.Pagination;
import com.jryz.dao.service.BasicService;
import com.jryz.random.RandomUtil;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* 唯一约束
* @author
*/
@Service
public class TableUnServiceImp extends BasicService<TableUnBean> implements TableUnService {

    /**
     * 继承需传入 maper
    */
    public TableUnServiceImp() {
        super(TableUnMaper.class);
    }


    /**
     * 获取到 数据集并返回结果前的 通知
     * @param obj type = list<T> || T
    */
    @Override
    protected void postpositionNotification(Object obj) {
        if (obj instanceof List) {
            machiningList((List<TableUnBean>) obj);
        } else if (obj instanceof TableUnBean) {
            machiningBean((TableUnBean) obj);
        }
    }

    /**
     * 为实体的 关联关系赋值
     * @param list
     * @return
    */
    public void machiningList(List<TableUnBean> list){
        if (list == null || list.isEmpty()) {
            return ;
        }
        list.forEach((bean) -> machiningBean(bean));
    }

    /**
     * 为实体的 关联关系赋值
     * @param bean
     * @return
    */
    public void machiningBean(TableUnBean bean){
        if (bean == null)
            return ;
    }

    /**
     * 替换方法
     * 不存在 则添加 存在则更新
     * 并返回新的 bean
     *
     * @param bean
     * @return
    */
    @Override
    public TableUnBean replace(TableUnBean bean) {
        List<TableUnBean> oldBeanList;
        TableUnBean oldBean;

        bean.setId(RandomUtil.getUUID());
        super.insert(bean);
        return bean;
    }
}
