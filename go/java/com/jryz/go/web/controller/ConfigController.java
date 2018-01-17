package com.jryz.go.web.controller;


import com.jryz.go.web.bean.ConfigBean;
import com.jryz.go.web.service.ConfigService;
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
* 数据源配置信息
* @author
*/
@RequestMapping("/o/config")
@Controller
public class ConfigController {


    @Autowired
    private ConfigService configService;
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 保存
     * @param configBean
     * @return
     */
    @RequestMapping("insert")
    @ResponseBody
    public ApiResult insert(ConfigBean configBean){
        ApiResult re = new ApiResult();
        try {
            Assert.state(configBean.getTitle() != null, "配置名称不能为空");
            Assert.state(configBean.getBasicPath() != null, "项目完整路径（根目录）不能为空");
            Assert.state(configBean.getSrcPath() != null, "代码相对路径不能为空");
            Assert.state(configBean.getHtmlPath() != null, "html相对路径不能为空");
            Assert.state(configBean.getResourcesPath() != null, "配置文件相对路径不能为空");
            Assert.state(configBean.getDbUrl() != null, "数据库地址不能为空");
            Assert.state(configBean.getDbUserPassword() != null, "数据库密码不能为空");
            Assert.state(configBean.getDbUserName() != null, "数据库账号不能为空");
            Assert.state(configBean.getDbName() != null, "数据库名称不能为空");
            ConfigBean configBeancheck;
            configBean.setId(RandomUtil.getUUID());
            configBean.setCreateDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            configService.insert(configBean);
            re.setSuccess(true);
        } catch (IllegalArgumentException e) { // 捕获运行期异常
            re.setMsg(e.getMessage());
        } catch (Exception e) {
            logger.error("", e);
        }
        return re;
    }

    /**
     * 修改
     * @param configBean
     * @return
     */
    @RequestMapping("update/{id}")
    @ResponseBody
    public ApiResult update(@PathVariable String id, ConfigBean configBean){
        ApiResult re = new ApiResult();
        try {
            Assert.hasText(id, "id不能为空");
            configBean.setId(id);
            ConfigBean configBeancheck;
            configService.update(configBean);
            re.setSuccess(true);
        } catch (IllegalArgumentException e) {
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
            configService.delete(id);
            re.setSuccess(true);
        } catch (IllegalArgumentException e) {
            re.setMsg(e.getMessage());
        } catch (Exception e) {
            logger.error("", e);
        }
        return re;
    }

    /**
    * 查询
    * @param configBean
    * @return
    */
    @RequestMapping("list")
    @ResponseBody
    public ApiResult list(ConfigBean configBean){
        ApiResult re = new ApiResult();
        try {
            re.setData(configService.list(configBean));
            re.setSuccess(true);
        } catch (IllegalArgumentException e) {
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
            configService.listByPage(pag);
            pag.setParam(null); // 从返回结果中 剔除查询参数
            re.setData(pag);
            re.setSuccess(true);
        } catch (IllegalArgumentException e) {
            re.setMsg(e.getMessage());
        } catch (Exception e) {
            logger.error("", e);
        }
        return re;
    }
}
