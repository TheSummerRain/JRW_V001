package com.test.core.controller;


import com.test.core.bean.TestXkBean;
import com.test.core.service.TestXkService;
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
@RequestMapping("/o/testXk")
@Controller
public class TestXkController {


    @Autowired
    private TestXkService testXkService;
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 保存
     * @param testXkBean
     * @return
     */
    @RequestMapping("insert")
    @ResponseBody
    public ApiResult insert(TestXkBean testXkBean){
        ApiResult re = new ApiResult();
        try {

            TestXkBean testXkBeancheck;
            List<TestXkBean> check;
            testXkBeancheck = new TestXkBean();
            testXkBeancheck.setName(testXkBean.getName());
            Assert.state(
            (
                check = testXkService.list(testXkBeancheck)) == null
                || check.size() == 0
                , "学科名称  重复"
            );
            testXkBean.setId(RandomUtil.getUUID());
            testXkBean.setCreateDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            testXkService.insert(testXkBean);
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
     * @param testXkBean
     * @return
     */
    @RequestMapping("update/{id}")
    @ResponseBody
    public ApiResult update(@PathVariable String id, TestXkBean testXkBean){
        ApiResult re = new ApiResult();
        try {
            Assert.hasText(id, "id不能为空");
            testXkBean.setId(id);

            TestXkBean testXkBeancheck;
            List<TestXkBean> check;
            testXkBeancheck = new TestXkBean();
            testXkBeancheck.setName(testXkBean.getName());
            Assert.state(
                (
                    check = testXkService.list(testXkBeancheck)) == null
                    || check.size() == 0
                    || (check.size() == 1 && id.equals(check.get(0).getId())
                )
                , "学科名称  重复"
            );
            testXkService.update(testXkBean);
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
            testXkService.delete(id);
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
    * @param testXkBean
    * @return
    */
    @RequestMapping("list")
    @ResponseBody
    public ApiResult list(TestXkBean testXkBean){
        ApiResult re = new ApiResult();
        try {
            re.setData(testXkService.list(testXkBean));
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
            testXkService.listByPage(pag);
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
