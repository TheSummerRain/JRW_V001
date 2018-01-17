package com.jryz.go.web.controller;


import com.jryz.go.web.bean.FieldBean;
import com.jryz.go.web.service.FieldService;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Date;
import java.text.SimpleDateFormat;

/**
* 字段信息
* @author
*/
@RequestMapping("/o/field")
@Controller
public class FieldController {


    @Autowired
    private FieldService fieldService;
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 保存
     * @param fieldBean
     * @return
     */
    @RequestMapping("insert")
    @ResponseBody
    public ApiResult insert(FieldBean fieldBean, @RequestParam(value = "dbTableShowFields[]", required = false) String dbShowFileIds){
        ApiResult re = new ApiResult();
        try {
            fieldBean.setDbTableShowField(dbShowFileIds);
            Assert.state(fieldBean.getName() != null, "字段名不能为空");
            Assert.state(fieldBean.getJavaName() != null, "java类名不能为空");
            Assert.state(fieldBean.getContent() != null, "描述不能为空");
            Assert.state(fieldBean.getLength() != null, "长度不能为空");
            Assert.state(fieldBean.getNotNull() != null, "是否为空不能为空");
            Assert.state(fieldBean.getTableId() != null, "表id不能为空");
            Assert.state(fieldBean.getInput() != null, "input/radio/checkbox/select不能为空");
            Assert.state(fieldBean.getSeelectType() != null, "1 模糊查询， 2 范围查询不能为空");
            FieldBean fieldBeancheck;
            fieldBeancheck = new FieldBean();
            fieldBeancheck.setName(fieldBean.getName());
            fieldBeancheck.setTableId(fieldBean.getTableId());
            List<FieldBean> check;
            Assert.state(
            (check = fieldService.list(fieldBeancheck)) == null
                || check.size() == 0
            , "name tableId  重复"
            );
            fieldBean.setId(RandomUtil.getUUID());
            fieldBean.setCreateDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            fieldService.insert(fieldBean);
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
     * @param fieldBean
     * @return
     */
    @RequestMapping("update/{id}")
    @ResponseBody
    public ApiResult update(@PathVariable String id, FieldBean fieldBean, @RequestParam(value = "dbTableShowFields[]", required = false) String dbShowFileIds){
        ApiResult re = new ApiResult();
        try {
            fieldBean.setDbTableShowField(dbShowFileIds);
            Assert.hasText(id, "id不能为空");
            fieldBean.setId(id);
            FieldBean fieldBeancheck;
            fieldBeancheck = new FieldBean();
            fieldBeancheck.setName(fieldBean.getName());
            fieldBeancheck.setTableId(fieldBean.getTableId());
            List<FieldBean> check;
            Assert.state(
            (
                check = fieldService.list(fieldBeancheck)) == null
                || check.size() == 0
                || (check.size() == 1 &&id.equals(check.get(0).getId()))
            , "name table_id  重复"
            );
            fieldService.update(fieldBean);
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
            fieldService.delete(id);
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
    * @param fieldBean
    * @return
    */
    @RequestMapping("list")
    @ResponseBody
    public ApiResult list(FieldBean fieldBean){
        ApiResult re = new ApiResult();
        try {
            re.setData(fieldService.list(fieldBean));
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
            fieldService.listByPage(pag);
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
