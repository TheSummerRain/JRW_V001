package com.test.core.service.imp;

 
import com.test.core.bean.UserSonBean;
import com.test.core.maper.UserSonMaper;
import com.test.core.service.UserSonService;
import com.jryz.core.free.Pagination;
import com.jryz.dao.service.BasicService;
import com.jryz.random.RandomUtil;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
* test
* @author
*/
@Service
public class UserSonServiceImp extends BasicService<UserSonBean> implements UserSonService {

    /**
     * 继承需传入 maper
    */
    public UserSonServiceImp() {
        super(UserSonMaper.class);
    }

 


    /**
     * 获取到 数据集并返回结果前的 通知
     * @param obj type = list<T> || T
    */
    @Override
    protected void postpositionNotification(Object obj) {
        if (obj instanceof List) {
            machiningList((List<UserSonBean>) obj);
        } else if (obj instanceof UserSonBean) {
            machiningBean((UserSonBean) obj);
        }
    }

    /**
     * 为实体的 关联关系赋值
     * @param list
     * @return
    */
    public void machiningList(List<UserSonBean> list){
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
    public void machiningBean(UserSonBean bean){
        if (bean == null)
            return ;
 
        // 填充实体
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
    public UserSonBean replace(UserSonBean bean) {
        List<UserSonBean> oldBeanList;
        UserSonBean oldBean;

        oldBean = new UserSonBean();
        oldBean.setName(bean.getName());
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
