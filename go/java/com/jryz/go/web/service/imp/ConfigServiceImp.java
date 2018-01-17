package com.jryz.go.web.service.imp;

import com.jryz.dao.service.BasicService;
import com.jryz.go.web.bean.ConfigBean;
import com.jryz.go.web.maper.ConfigMaper;
import com.jryz.go.web.service.ConfigService;
import com.jryz.random.RandomUtil;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* 数据源配置信息
* @author
*/
@Service
public class ConfigServiceImp extends BasicService<ConfigBean> implements ConfigService {

    /**
     * 继承需传入 maper
    */
    public ConfigServiceImp() {
        super(ConfigMaper.class);
    }


    /**
     * 获取到 数据集并返回结果前的 通知
     * @param obj type = list<T> || T
    */
    @Override
    protected void postpositionNotification(Object obj) {
        if (obj instanceof List) {
            machiningList((List<ConfigBean>) obj);
        } else if (obj instanceof ConfigBean) {
            machiningBean((ConfigBean) obj);
        }
    }

    /**
     * 为实体的 关联关系赋值
     * @param list
     * @return
    */
    public void machiningList(List<ConfigBean> list){
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
    public void machiningBean(ConfigBean bean){
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
    public ConfigBean replace(ConfigBean bean) {
        List<ConfigBean> oldBeanList;
        ConfigBean oldBean;

        bean.setId(RandomUtil.getUUID());
        super.insert(bean);
        return bean;
    }
}
