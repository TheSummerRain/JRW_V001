package com.jryz.go.web.service.imp;

import com.jryz.dao.service.BasicService;
import com.jryz.go.web.bean.FieldBean;
import com.jryz.go.web.maper.FieldMaper;
import com.jryz.go.web.service.FieldService;
import com.jryz.go.web.service.TableService;
import com.jryz.random.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
* 字段信息
* @author
*/
@Service
public class FieldServiceImp extends BasicService<FieldBean> implements FieldService {

    @Autowired
    private TableService tableService;

    /**
     * 继承需传入 maper
    */
    public FieldServiceImp() {
        super(FieldMaper.class);
    }


    /**
     * 获取到 数据集并返回结果前的 通知
     * @param obj type = list<T> || T
    */
    @Override
    protected void postpositionNotification(Object obj) {
        if (obj instanceof List) {
            machiningList((List<FieldBean>) obj);
        } else if (obj instanceof FieldBean) {
            machiningBean((FieldBean) obj);
        }
    }

    /**
     * 为实体的 关联关系赋值
     * @param list
     * @return
    */
    public void machiningList(List<FieldBean> list){
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
    public void machiningBean(FieldBean bean){
        if (bean == null)
            return ;
        if (bean.getTableId() != null)
            bean.setTableBean(tableService.get(bean.getTableId(), false));
        if (bean.getPrTableId() != null)
            bean.setPrTableBean(tableService.get(bean.getPrTableId(), false));
        if (bean.getDbTableId() != null)
            bean.setDbTableBean(tableService.get(bean.getDbTableId(), false));
        if (bean.getDbTableShowField() != null) {
            String[] showFideIds = bean.getDbTableShowField().split(",");
            List<FieldBean> fields = new ArrayList<>();
            for (String showFideId : showFideIds) {
                fields.add(super.get(showFideId, false));
            }
            bean.setShowTableFieldList(fields);
        }
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
    public FieldBean replace(FieldBean bean) {
        List<FieldBean> oldBeanList;
        FieldBean oldBean;

        oldBean = new FieldBean();
        oldBean.setName(bean.getName());
        oldBean.setTableId(bean.getTableId());
        oldBeanList = super.list(oldBean);
        if (oldBeanList != null && oldBeanList.size() > 0) {
            String id = oldBeanList.get(0).getId();
            bean.setId(id);
            super.update(bean);
            return super.get(id);
        }

        bean.setId(RandomUtil.getUUID());
        super.insert(bean);
        return bean;
    }
}
