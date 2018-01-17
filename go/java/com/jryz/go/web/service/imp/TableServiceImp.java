package com.jryz.go.web.service.imp;

import com.jryz.dao.service.BasicService;
import com.jryz.go.web.bean.FieldBean;
import com.jryz.go.web.bean.TableBean;
import com.jryz.go.web.maper.TableMaper;
import com.jryz.go.web.service.ConfigService;
import com.jryz.go.web.service.FieldService;
import com.jryz.go.web.service.TableService;
import com.jryz.random.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* 表信息
* @author
*/
@Service
public class TableServiceImp extends BasicService<TableBean> implements TableService {

    /**
     * 继承需传入 maper
    */
    public TableServiceImp() {
        super(TableMaper.class);
    }

    @Autowired
    private ConfigService configService;
    @Autowired
    private FieldService fieldService;

    /**
     * 获取到 数据集并返回结果前的 通知
     * @param obj type = list<T> || T
    */
    @Override
    protected void postpositionNotification(Object obj) {
        if (obj instanceof List) {
            machiningList((List<TableBean>) obj);
        } else if (obj instanceof TableBean) {
            machiningBean((TableBean) obj);
        }
    }

    /**
     * 为实体的 关联关系赋值
     * @param list
     * @return
    */
    public void machiningList(List<TableBean> list){
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
    public void machiningBean(TableBean bean){
        if (bean == null)
            return ;
        bean.setConfigBean(configService.get(bean.getConfigId()));

        FieldBean fieldBean = new FieldBean();
        fieldBean.setTableId(bean.getId());
        bean.setFieldList(fieldService.list(fieldBean));

        // 得到唯一索引
        List<List<FieldBean>> unFieldList = new ArrayList<>();
        Map<String, List<FieldBean>> unFieldData = new HashMap<>();
        List<FieldBean> onefieldList = new ArrayList<>();
        List<FieldBean> manyfieldList = new ArrayList<>();
        bean.getFieldList().forEach(i -> {
            String unName = i.getUnName();
            if (unName != null) {
                List fields = unFieldData.get(unName);
                if (fields == null) {
                    fields = new ArrayList();
                    unFieldData.put(unName, fields);
                }
                fields.add(i);
            }
            if (i.getDbTableId() != null) {
                if ((i.getType() == 2) && i.getPrTableBean() != null) {
                    manyfieldList.add(i);
                } else {
                    onefieldList.add(i);
                }
            }
        });
        unFieldData.forEach((k, v) -> unFieldList.add(v));
        bean.setUnFieldList(unFieldList);
        bean.setOnefieldList(onefieldList);
        bean.setManyfieldList(manyfieldList);

        TableBean sonTable = new TableBean();
        sonTable.setPrTableId(bean.getId());
        bean.setSonTableBean(super.list(sonTable));
        if (bean.getPrTableFieId() != null) {
            bean.setPrFieldBean(fieldService.get(bean.getPrTableFieId(), false));
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
    public TableBean replace(TableBean bean) {
        List<TableBean> oldBeanList;
        TableBean oldBean;

        oldBean = new TableBean();
        oldBean.setTableName(bean.getTableName());
        oldBean.setConfigId(bean.getConfigId());
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
