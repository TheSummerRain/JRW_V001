package ${servicePackagePath};

import ${beanPackagePath}.${beanName};
import com.jryz.dao.service.BasicServiceInterface;

/**
* ${table.content}
* @author
*/
public interface ${serviceName} extends BasicServiceInterface <${beanName}> {

    /**
     * 替换方法
     * 不存在 则添加 存在则更新
     * 并返回新的 bean
     * @param bean
     * @return
    */
    ${beanName} replace(${beanName} bean);
}
