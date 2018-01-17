package com.core.login.controller;


import com.core.login.bean.AccountBean;
import com.core.login.service.AccountService;
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

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
* 账号
* @author
*/
@RequestMapping("/o/account")
@Controller
public class AccountController {


    @Autowired
    private AccountService accountService;
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private final static String LOGIN_ACCOUNT = "login";

    /**
     * 登录
     * @param accountBean
     * @return
     */
    @RequestMapping("login")
    @ResponseBody
    public ApiResult login(AccountBean accountBean, HttpServletRequest request){
        ApiResult re = new ApiResult();
        try {
            Assert.hasText(accountBean.getLoginName(), "登录账号不能为空");
            Assert.hasText(accountBean.getPassword(), "密码不能为空");
            AccountBean accountBeancheck = new AccountBean();
            accountBeancheck.setLoginName(accountBean.getLoginName());
            List<AccountBean> check;
            Assert.state(
                    (check = accountService.list(accountBeancheck)) != null
                            && check.size() != 0
                    , "账号不存在"
            );
            accountBeancheck = check.get(0);
            Assert.state(accountBeancheck.getPassword().equals(accountBean.getPassword()), "密码错误");
            re.setData(accountBeancheck);
            request.getSession().setAttribute(LOGIN_ACCOUNT, accountBeancheck); // 放入session
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
     * 退出登录
     * @return
     */
    @RequestMapping("out")
    @ResponseBody
    public ApiResult out(HttpServletRequest request){
        ApiResult re = new ApiResult();
        try {
            request.getSession().removeAttribute(LOGIN_ACCOUNT);
            re.setSuccess(true);
        } catch (IllegalArgumentException e) { // 捕获运行期异常
            re.setMsg(e.getMessage());
        } catch (Exception e) {
            logger.error("", e);
        }
        return re;
    }

    /**
     * 查看登录信息
     * @return
     */
    @RequestMapping("info")
    @ResponseBody
    public ApiResult info(HttpServletRequest request){
        ApiResult re = new ApiResult();
        try {
            re.setData(request.getSession().getAttribute(LOGIN_ACCOUNT));
            re.setSuccess(true);
        } catch (IllegalArgumentException e) { // 捕获运行期异常
            re.setMsg(e.getMessage());
        } catch (Exception e) {
            logger.error("", e);
        }
        return re;
    }

    /**
     * 保存
     * @param accountBean
     * @return
     */
    @RequestMapping("insert")
    @ResponseBody
    public ApiResult insert(AccountBean accountBean, String oPassword){
        ApiResult re = new ApiResult();
        try {
            Assert.state(accountBean.getPassword().equals(oPassword), "两次输入密码不一致");
            Assert.hasText(accountBean.getLoginName(), "登录账号不能为空");
            Assert.hasText(accountBean.getPassword(), "密码不能为空");
            AccountBean accountBeancheck = new AccountBean();
            accountBeancheck.setLoginName(accountBean.getLoginName());
            List<AccountBean> check;
            Assert.state(
            (check = accountService.list(accountBeancheck)) == null
                || check.size() == 0
            , "账号重复  重复"
            );
            accountBean.setId(RandomUtil.getUUID());
            accountBean.setCreateDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            accountService.insert(accountBean);
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
     * @param accountBean
     * @return
     */
    @RequestMapping("update/{upId}")
    @ResponseBody
    public ApiResult update(@PathVariable String upId, AccountBean accountBean){
        ApiResult re = new ApiResult();
        try {
            Assert.hasText(upId, "id不能为空");
            accountBean.setId(upId);
            AccountBean accountBeancheck = new AccountBean();
            accountBeancheck.setPassword(accountBean.getPassword());
            accountBeancheck.setLoginName(accountBean.getLoginName());
            List<AccountBean> check;
            Assert.state(
            (
                check = accountService.list(accountBeancheck)) == null
                || check.size() == 0
                || (check.size() == 1 &&upId.equals(check.get(0).getId()))
            , "password login_name  重复"
            );
            accountService.update(accountBean);
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
            accountService.delete(id);
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
    * @param accountBean
    * @return
    */
    @RequestMapping("list")
    @ResponseBody
    public ApiResult list(AccountBean accountBean){
        ApiResult re = new ApiResult();
        try {
            re.setData(accountService.list(accountBean));
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
            accountService.listByPage(pag);
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
