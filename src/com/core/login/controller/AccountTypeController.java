package com.core.login.controller;


import com.core.login.bean.AccountTypeBean;
import com.core.login.service.AccountTypeService;
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

import java.text.SimpleDateFormat;
import java.util.Date;

/**
* 账号类型
* @author
*/
@RequestMapping("/o/accountType")
@Controller
public class AccountTypeController {


    @Autowired
    private AccountTypeService accountTypeService;
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 保存
     * @param accountTypeBean
     * @return
     */
    @RequestMapping("insert")
    @ResponseBody
    public ApiResult insert(AccountTypeBean accountTypeBean){
        ApiResult re = new ApiResult();
        try {
            Assert.hasText(accountTypeBean.getName(), "类型名称不能为空");
            accountTypeBean.setId(RandomUtil.getUUID());
            accountTypeBean.setCreateDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            accountTypeService.insert(accountTypeBean);
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
     * @param accountTypeBean
     * @return
     */
    @RequestMapping("update/{id}")
    @ResponseBody
    public ApiResult update(@PathVariable String id, AccountTypeBean accountTypeBean){
        ApiResult re = new ApiResult();
        try {
            Assert.hasText(id, "id不能为空");
            accountTypeBean.setId(id);
            accountTypeService.update(accountTypeBean);
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
            accountTypeService.delete(id);
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
    * @param accountTypeBean
    * @return
    */
    @RequestMapping("list")
    @ResponseBody
    public ApiResult list(AccountTypeBean accountTypeBean){
        ApiResult re = new ApiResult();
        try {
            re.setData(accountTypeService.list(accountTypeBean));
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
            accountTypeService.listByPage(pag);
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
