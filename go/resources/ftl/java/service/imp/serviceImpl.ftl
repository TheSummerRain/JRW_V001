<#assign name=table.name?cap_first />
package ${serviceImpPackagePath};

import ${beanPackagePath}.${beanName};
import ${maperPackagePath}.${maperName};
import ${servicePackagePath}.${serviceName};
import com.jryz.core.free.Pagination;
import com.jryz.dao.service.BasicService;
import com.jryz.random.RandomUtil;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* ${table.content}
* @author
*/
@Service
public class ${serviceImpName} extends BasicService<${beanName}> implements ${name}Service {

    /**
     * 继承需传入 maper
    */
    public ${serviceImpName}() {
        super(${maperName}.class);
    }


    /**
     * 获取到 数据集并返回结果前的 通知
     * @param obj type = list<T> || T
    */
    @Override
    protected void postpositionNotification(Object obj) {
        if (obj instanceof List) {
            machiningList((List<${beanName}>) obj);
        } else if (obj instanceof ${beanName}) {
            machiningBean((${beanName}) obj);
        }
    }

    /**
     * 为实体的 关联关系赋值
     * @param list
     * @return
    */
    public void machiningList(List<${beanName}> list){
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
    public void machiningBean(${beanName} bean){
        if (bean == null)
            return ;
    }

<#assign unique = table.unique />
    /**
     * 替换方法
     * 不存在 则添加 存在则更新
     * 并返回新的 bean
     *
     * @param bean
     * @return
    */
    @Override
    public ${beanName} replace(${beanName} bean) {
        List<${beanName}> oldBeanList;
        ${beanName} oldBean;
<#if unique??>
    <#list unique as un1>
        <#assign str = ""/>

        oldBean = new ${beanName}();
        <#list un1 as un>
            <#assign str = str + un.javaName + " "/>
        oldBean.set${un.javaName?cap_first}(bean.get${un.javaName?cap_first}());
        </#list>
        oldBeanList = super.list(oldBean);
        if (oldBeanList != null && oldBeanList.size() > 0) {
            String id = oldBeanList.get(0).getId();
            bean.setId(id);
            super.update(bean);
            return super.get(id);
        }
    </#list>
</#if>

        bean.setId(RandomUtil.getUUID());
        super.insert(bean);
        return bean;
    }
}
