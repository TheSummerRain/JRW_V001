package com.core.login.service.imp;

import com.core.login.bean.AccountTypeBean;
import com.core.login.maper.AccountTypeMaper;
import com.core.login.service.AccountTypeService;
import com.jryz.dao.service.BasicService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* 账号类型
* @author
*/
@Service
public class AccountTypeServiceImp extends BasicService<AccountTypeBean> implements AccountTypeService {

    /**
     * 继承需传入 maper
     */
    public AccountTypeServiceImp() {
        super(AccountTypeMaper.class);
    }

    /**
     * 获取到 数据集并返回结果前的 通知
     * @param obj type = list<T> || T
     */
    @Override
    protected void postpositionNotification(Object obj) {
        if (obj instanceof List) {
            machiningList((List<AccountTypeBean>) obj);
        } else if (obj instanceof AccountTypeBean) {
            machiningBean((AccountTypeBean) obj);
        }
    }

    /**
    * 为实体的 关联关系赋值
    * @param list
    * @return
    */
    public void machiningList(List<AccountTypeBean> list){
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
    public void machiningBean(AccountTypeBean bean){
        if (bean == null)
            return ;
    }
}
