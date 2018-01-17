package com.jryz.go.web.controller;


import com.jryz.go.web.bean.TableUnBean;
import com.jryz.go.web.service.TableUnService;
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
* 唯一约束
* @author
*/
@RequestMapping("/o/tableUn")
@Controller
public class TableUnController {


    @Autowired
    private TableUnService tableUnService;
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 保存
     * @param tableUnBean
     * @return
     */
    @RequestMapping("insert")
    @ResponseBody
    public ApiResult insert(TableUnBean tableUnBean){
        ApiResult re = new ApiResult();
        try {
            Assert.state(tableUnBean.getTableId() != null, "不能为空");
            Assert.state(tableUnBean.getFieldId() != null, "不能为空");
            Assert.state(tableUnBean.getUnName() != null, "唯一约束的昵称不能为空");
            TableUnBean tableUnBeancheck;
            tableUnBean.setId(RandomUtil.getUUID());
            tableUnBean.setCreateDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            tableUnService.insert(tableUnBean);
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
     * @param tableUnBean
     * @return
     */
    @RequestMapping("update/{id}")
    @ResponseBody
    public ApiResult update(@PathVariable String id, TableUnBean tableUnBean){
        ApiResult re = new ApiResult();
        try {
            Assert.hasText(id, "id不能为空");
            tableUnBean.setId(id);
            TableUnBean tableUnBeancheck;
            tableUnService.update(tableUnBean);
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
            tableUnService.delete(id);
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
    * @param tableUnBean
    * @return
    */
    @RequestMapping("list")
    @ResponseBody
    public ApiResult list(TableUnBean tableUnBean){
        ApiResult re = new ApiResult();
        try {
            re.setData(tableUnService.list(tableUnBean));
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
            tableUnService.listByPage(pag);
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
