package ${beanPackagePath};

import com.jryz.bean.BasicEntity;

/**
* ${table.content}
* @author
*/
public class ${beanName} extends BasicEntity {

<#assign fields = table.fields />
<#--成员变量部分-->
<#list fields as field>
    <#assign type = field.type />
    private ${type.str} ${field.javaName}; // ${field.content}
</#list>

<#-- getOne set 方法 -->
<#list fields as field>
    <#assign type = field.type />
    public ${type.str} get${field.javaName ?cap_first}() {
        return ${field.javaName};
    }

    public void set${field.javaName?cap_first}(${type.str} ${field.javaName}) {
        this.${field.javaName} = ${field.javaName};
    }

</#list>
}
