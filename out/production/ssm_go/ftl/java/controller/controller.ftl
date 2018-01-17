package ${controllerPackagePath};

<#assign serviceName = serviceName />
<#assign beanName = beanName />
<#assign service = table.name + "Service" />
<#assign bean = table.name + "Bean" />

import ${beanPackagePath}.${beanName};
import ${servicePackagePath}.${serviceName};
import com.jryz.core.free.Pagination;
import com.jryz.model.ApiResult;
import com.jryz.random.RandomUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Date;
import java.text.SimpleDateFormat;

/**
* ${table.content}
* @author
*/
@RequestMapping("/o/${table.name}")
@Controller
public class ${controllerName} {


    @Autowired
    private ${serviceName} ${service};
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    <#assign fields = table.fields />
    <#assign unique = table.unique />

    /**
     * 保存
     * @param ${bean}
     * @return
     */
    @RequestMapping("insert")
    @ResponseBody
    public ApiResult insert(${beanName} ${bean}){
        ApiResult re = new ApiResult();
        try {
            <#-- 验证非空 -->
            <#if fields??>
                <#list fields as field>
                    <#if field.notNull == true>
                        <#if field.javaName != 'id'>
            Assert.state(${bean}.get${field.javaName?cap_first}() != null, "${field.content}不能为空");
                        </#if>
                    </#if>
                </#list>
            </#if>
            <#if unique??>
            ${beanName} ${bean}check;
                <#list unique as un1>
                    <#assign str = ""/>
            ${bean}check = new ${beanName}();
                    <#list un1 as un>
                        <#assign str = str + un.javaName + " "/>
            ${bean}check.set${un.javaName?cap_first}(${bean}.get${un.javaName?cap_first}());
                    </#list>
            List<${beanName}> check;
            Assert.state(
            (check = ${service}.list(${bean}check)) == null
                || check.size() == 0
            , "${str} 重复"
            );
                </#list>
            </#if>
            ${bean}.setId(RandomUtil.getUUID());
            ${bean}.setCreateDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            ${service}.insert(${bean});
            re.setSuccess(true);
        } catch (IllegalArgumentException e) { // 捕获运行期异常
            re.setMsg(e.getMessage());
        } catch (IllegalStateException e) { // 捕获运行期异常
            re.setMsg(e.getMessage());
        } catch (Exception e) {
            logger.error("", e);
        }
        return re;
    }

    /**
     * 修改
     * @param ${bean}
     * @return
     */
    @RequestMapping("update/{id}")
    @ResponseBody
    public ApiResult update(@PathVariable String id, ${beanName} ${bean}){
        ApiResult re = new ApiResult();
        try {
            Assert.hasText(id, "id不能为空");
            ${bean}.setId(id);
            <#if unique??>
            ${beanName} ${bean}check;
                <#list unique as un1>
                    <#assign str = ""/>
            ${bean}check = new ${beanName}();
                    <#list un1 as un>
                        <#assign str = str + un.name + " "/>
            ${bean}check.set${un.javaName?cap_first}(${bean}.get${un.javaName?cap_first}());
                    </#list>
            List<${beanName}> check;
            Assert.state(
            (
                check = ${service}.list(${bean}check)) == null
                || check.size() == 0
                || (check.size() == 1 &&id.equals(check.get(0).getId()))
            , "${str} 重复"
            );
                </#list>
            </#if>
            ${service}.update(${bean});
            re.setSuccess(true);
        } catch (IllegalArgumentException e) { // 捕获运行期异常
            re.setMsg(e.getMessage());
        } catch (IllegalStateException e) { // 捕获运行期异常
            re.setMsg(e.getMessage());
        } catch (Exception e) {
            logger.error("", e);
        }
        return re;
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @RequestMapping("del/{id}")
    @ResponseBody
    public ApiResult del(@PathVariable String id){
        ApiResult re = new ApiResult();
        try {
            Assert.hasText(id, "id不能为空");
            ${service}.delete(id);
            re.setSuccess(true);
        } catch (IllegalArgumentException e) { // 捕获运行期异常
            re.setMsg(e.getMessage());
        } catch (IllegalStateException e) { // 捕获运行期异常
            re.setMsg(e.getMessage());
        } catch (Exception e) {
            logger.error("", e);
        }
        return re;
    }

    /**
    * 查询
    * @param ${bean}
    * @return
    */
    @RequestMapping("list")
    @ResponseBody
    public ApiResult list(${beanName} ${bean}){
        ApiResult re = new ApiResult();
        try {
            re.setData(${service}.list(${bean}));
            re.setSuccess(true);
        } catch (IllegalArgumentException e) { // 捕获运行期异常
            re.setMsg(e.getMessage());
        } catch (IllegalStateException e) { // 捕获运行期异常
            re.setMsg(e.getMessage());
        } catch (Exception e) {
            logger.error("", e);
        }
        return re;
    }

    /**
     * 分页查询
     * @param pag
     * @return
     */
    @RequestMapping("listByPage")
    @ResponseBody
    public ApiResult listByPage(Pagination pag){
        ApiResult re = new ApiResult();
        try {
            ${service}.listByPage(pag);
            pag.setParam(null); // 从返回结果中 剔除查询参数
            re.setData(pag);
            re.setSuccess(true);
        } catch (IllegalArgumentException e) { // 捕获运行期异常
            re.setMsg(e.getMessage());
        } catch (IllegalStateException e) { // 捕获运行期异常
            re.setMsg(e.getMessage());
        } catch (Exception e) {
            logger.error("", e);
        }
        return re;
    }
}
