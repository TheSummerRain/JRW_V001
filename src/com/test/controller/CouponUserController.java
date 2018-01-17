package com.test.controller;


import com.test.bean.CouponUserBean;
import com.test.service.CouponUserService;
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

@RequestMapping("/o/couponUser")
@Controller
public class CouponUserController {


    @Autowired
    private CouponUserService couponUserService;
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    /**
     * 保存
     * @param couponUserBean
     * @return
     */
    @RequestMapping("insert")
    @ResponseBody
    public ApiResult insert(CouponUserBean couponUserBean){
        ApiResult re = new ApiResult();
        try {
            // 验证非空
            Assert.hasText(couponUserBean.getId(), "不能为空");
            Assert.hasText(couponUserBean.getProjectId(), "不能为空");
            Assert.hasText(couponUserBean.getCouponId(), "不能为空");
            // 验证唯一
            CouponUserBean couponUserBeancheck = new CouponUserBean();
            couponUserBeancheck.setUserId(couponUserBean.getUserId());
            couponUserBeancheck.setUnId(couponUserBean.getUnId());
            couponUserBeancheck.setProjectId(couponUserBean.getProjectId());
            List<CouponUserBean> check;
            Assert.state(
            (check = couponUserService.list(couponUserBeancheck)) == null || check.size() == 0
            , "userId unId projectId  重复"
            );

            couponUserBean.setId(RandomUtil.getUUID());
            couponUserService.insert(couponUserBean);
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
     * @param couponUserBean
     * @return
     */
    @RequestMapping("update/{id}")
    @ResponseBody
    public ApiResult update(@PathVariable String id, CouponUserBean couponUserBean){
        ApiResult re = new ApiResult();
        try {
            Assert.hasText(id, "id不能为空");
            couponUserBean.setId(id);
            // 验证唯一
            CouponUserBean couponUserBeancheck = new CouponUserBean();
            couponUserBeancheck.setUserId(couponUserBean.getUserId());
            couponUserBeancheck.setUnId(couponUserBean.getUnId());
            couponUserBeancheck.setProjectId(couponUserBean.getProjectId());
            List<CouponUserBean> check;
            Assert.state(
            (check = couponUserService.list(couponUserBeancheck)) == null
                || check.size() == 0
                || id.equals(check.get(0).getId())
            , "userId unId projectId  重复"
            );

            couponUserService.update(couponUserBean);
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
            couponUserService.delete(id);
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
    @RequestMapping("list")
    @ResponseBody
    public ApiResult list(Pagination pag){
        ApiResult re = new ApiResult();
        try {
            couponUserService.listByPage(pag);
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
