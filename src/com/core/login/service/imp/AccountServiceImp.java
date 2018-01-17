package com.core.login.service.imp;

import com.core.login.bean.AccountBean;
import com.core.login.bean.AccountTypeBean;
import com.core.login.maper.AccountMaper;
import com.core.login.service.AccountService;
import com.core.login.service.AccountTypeService;
import com.jryz.dao.service.BasicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* 账号
* @author
*/
@Service
public class AccountServiceImp extends BasicService<AccountBean> implements AccountService {

    @Autowired
    private AccountTypeService accountTypeService;

    /**
     * 继承需传入 maper
     */
    public AccountServiceImp() {
        super(AccountMaper.class);
    }

    /**
     * 获取到 数据集并返回结果前的 通知
     * @param obj type = list<T> || T
     */
    @Override
    protected void postpositionNotification(Object obj) {
        if (obj instanceof List) {
            machiningList((List<AccountBean>) obj);
        } else if (obj instanceof AccountBean) {
            machiningBean((AccountBean) obj);
        }
    }

    /**
    * 为实体的 关联关系赋值
    * @param list
    * @return
    */
    public void machiningList(List<AccountBean> list){
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
    public void machiningBean(AccountBean bean){
        if (bean == null)
            return ;
        String typeId = bean.getType();
        AccountTypeBean typeBean = accountTypeService.get(typeId);
        bean.setTypeBean(typeBean);
    }
}
