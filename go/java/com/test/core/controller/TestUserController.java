package com.test.core.controller;


import com.test.core.bean.TestUserBean;
import com.test.core.service.TestUserService;
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
* t
* @author
*/
@RequestMapping("/o/testUser")
@Controller
public class TestUserController {


    @Autowired
    private TestUserService testUserService;
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 保存
     * @param testUserBean
     * @return
     */
    @RequestMapping("insert")
    @ResponseBody
    public ApiResult insert(TestUserBean testUserBean){
        ApiResult re = new ApiResult();
        try {
            Assert.state(testUserBean.getName() != null, "姓名不能为空");

            TestUserBean testUserBeancheck;
            List<TestUserBean> check;
            testUserBeancheck = new TestUserBean();
            testUserBeancheck.setName(testUserBean.getName());
            Assert.state(
            (
                check = testUserService.list(testUserBeancheck)) == null
                || check.size() == 0
                , "姓名  重复"
            );
            testUserBean.setId(RandomUtil.getUUID());
            testUserBean.setCreateDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            testUserService.insert(testUserBean);
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
     * @param testUserBean
     * @return
     */
    @RequestMapping("update/{id}")
    @ResponseBody
    public ApiResult update(@PathVariable String id, TestUserBean testUserBean){
        ApiResult re = new ApiResult();
        try {
            Assert.hasText(id, "id不能为空");
            testUserBean.setId(id);

            TestUserBean testUserBeancheck;
            List<TestUserBean> check;
            testUserBeancheck = new TestUserBean();
            testUserBeancheck.setName(testUserBean.getName());
            Assert.state(
                (
                    check = testUserService.list(testUserBeancheck)) == null
                    || check.size() == 0
                    || (check.size() == 1 && id.equals(check.get(0).getId())
                )
                , "姓名  重复"
            );
            testUserService.update(testUserBean);
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
            testUserService.delete(id);
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
    * @param testUserBean
    * @return
    */
    @RequestMapping("list")
    @ResponseBody
    public ApiResult list(TestUserBean testUserBean){
        ApiResult re = new ApiResult();
        try {
            re.setData(testUserService.list(testUserBean));
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
            testUserService.listByPage(pag);
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
