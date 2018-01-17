package ${beanPackagePath};

import com.jryz.bean.BasicEntity;
<#if oneFields??><#--单对单操作-->
<#list oneFields as field>
import ${field.dbTableBean.packageName}.bean.${field.dbTableBean.name ?cap_first}Bean;
</#list >
</#if>
<#if sonTable??><#--子表-->
<#list sonTable as table>
import ${table.packageName}.bean.${table.name ?cap_first}Bean;
</#list >
import java.util.List;

</#if>
/**
* ${table.content}
* @author
*/
public class ${beanName} extends BasicEntity {

<#--BEGIN 成员变量部分-->
<#list fields as field>
    private ${field.fieldType} ${field.javaName}; // ${field.content}
</#list>

<#if oneFields??><#--单对单操作-->
<#list oneFields as field>
    private ${field.dbTableBean.name ?cap_first}Bean ${field.javaName?replace("Id", "")}Bean; // ${field.tableBean.content}
</#list >
</#if>
<#if sonTable??><#--子表-->
<#list sonTable as table>
    private List<${table.name ?cap_first}Bean> ${table.name}List; // ${table.content}
</#list >
</#if>
<#--END 成员变量部分-->

<#--BEGIN get and set 方法 -->
<#list fields as field>

    public ${field.fieldType} get${field.javaName ?cap_first}() {
        return ${field.javaName};
    }

    public void set${field.javaName?cap_first}(${field.fieldType} ${field.javaName}) {
        this.${field.javaName} = ${field.javaName};
    }
</#list>
<#if oneFields??><#--单对单操作-->
<#list oneFields as field>
    <#assign fieldName = field.javaName ?replace("Id", "") + 'Bean'/>
    <#assign fieldBean = field.dbTableBean.name ?cap_first + 'Bean'/>

    public ${fieldBean} get${fieldName ?cap_first}() {
        return ${fieldName};
    }

    public void set${fieldName ?cap_first}(${fieldBean} ${fieldName}) {
        this.${fieldName} = ${fieldName};
    }
</#list >
</#if>
<#if sonTable??><#--子表-->
<#list sonTable as table>
    <#assign fieldName = table.name + 'List'/>

    public List<${table.name ?cap_first}Bean> get${fieldName ?cap_first}() {
        return ${fieldName};
    }

    public void set${fieldName?cap_first}(List<${table.name ?cap_first}Bean> ${fieldName}) {
        this.${fieldName} = ${fieldName};
    }
</#list >
</#if>
<#--END get and set 方法 -->
}
