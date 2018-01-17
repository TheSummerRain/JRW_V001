package com.test.core.controller;


import com.test.core.bean.UserSonBean;
import com.test.core.service.UserSonService;
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
* test
* @author
*/
@RequestMapping("/o/userSon")
@Controller
public class UserSonController {


    @Autowired
    private UserSonService userSonService;
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 保存
     * @param userSonBean
     * @return
     */
    @RequestMapping("insert")
    @ResponseBody
    public ApiResult insert(UserSonBean userSonBean){
        ApiResult re = new ApiResult();
        try {

            UserSonBean userSonBeancheck;
            List<UserSonBean> check;
            userSonBeancheck = new UserSonBean();
            userSonBeancheck.setName(userSonBean.getName());
            Assert.state(
            (
                check = userSonService.list(userSonBeancheck)) == null
                || check.size() == 0
                , "儿子姓名  重复"
            );
            userSonBean.setId(RandomUtil.getUUID());
            userSonBean.setCreateDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            userSonService.insert(userSonBean);
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
     * @param userSonBean
     * @return
     */
    @RequestMapping("update/{id}")
    @ResponseBody
    public ApiResult update(@PathVariable String id, UserSonBean userSonBean){
        ApiResult re = new ApiResult();
        try {
            Assert.hasText(id, "id不能为空");
            userSonBean.setId(id);

            UserSonBean userSonBeancheck;
            List<UserSonBean> check;
            userSonBeancheck = new UserSonBean();
            userSonBeancheck.setName(userSonBean.getName());
            Assert.state(
                (
                    check = userSonService.list(userSonBeancheck)) == null
                    || check.size() == 0
                    || (check.size() == 1 && id.equals(check.get(0).getId())
                )
                , "儿子姓名  重复"
            );
            userSonService.update(userSonBean);
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
            userSonService.delete(id);
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
    * @param userSonBean
    * @return
    */
    @RequestMapping("list")
    @ResponseBody
    public ApiResult list(UserSonBean userSonBean){
        ApiResult re = new ApiResult();
        try {
            re.setData(userSonService.list(userSonBean));
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
            userSonService.listByPage(pag);
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
