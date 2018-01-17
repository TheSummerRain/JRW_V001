package com.jryz.go.web.controller;


import com.jryz.core.free.Pagination;
import com.jryz.go.web.bean.TableBean;
import com.jryz.go.web.service.TableService;
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
import java.util.List;

/**
* 表信息
* @author
*/
@RequestMapping("/o/table")
@Controller
public class TableController {


    @Autowired
    private TableService tableService;
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 保存
     * @param tableBean
     * @return
     */
    @RequestMapping("insert")
    @ResponseBody
    public ApiResult insert(TableBean tableBean){
        ApiResult re = new ApiResult();
        try {
            Assert.state(tableBean.getTableName() != null, "表名不能为空");
            Assert.state(tableBean.getName() != null, "加工后的 名称 如 userBean不能为空");
            Assert.state(tableBean.getPackageName() != null, "包名不能为空");
            Assert.state(tableBean.getContent() != null, "描述不能为空");
            Assert.state(tableBean.getConfigId() != null, "配置id不能为空");
            TableBean tableBeancheck;
            tableBeancheck = new TableBean();
            tableBeancheck.setTableName(tableBean.getTableName());
            tableBeancheck.setConfigId(tableBean.getConfigId());
            List<TableBean> check;
            Assert.state(
            (check = tableService.list(tableBeancheck)) == null
                || check.size() == 0
            , "tableName configId  重复"
            );
            tableBean.setId(RandomUtil.getUUID());
            tableBean.setCreateDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            tableService.insert(tableBean);
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
     * @param tableBean
     * @return
     */
    @RequestMapping("update/{id}")
    @ResponseBody
    public ApiResult update(@PathVariable String id, TableBean tableBean){
        ApiResult re = new ApiResult();
        try {
            Assert.hasText(id, "id不能为空");
            tableBean.setId(id);
            TableBean tableBeancheck;
            tableBeancheck = new TableBean();
            tableBeancheck.setTableName(tableBean.getTableName());
            tableBeancheck.setConfigId(tableBean.getConfigId());
            List<TableBean> check;
            Assert.state(
            (
                check = tableService.list(tableBeancheck)) == null
                || check.size() == 0
                || (check.size() == 1 &&id.equals(check.get(0).getId()))
            , "table_name config_id  重复"
            );
            tableService.update(tableBean);
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
            tableService.delete(id);
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
    * @param tableBean
    * @return
    */
    @RequestMapping("list")
    @ResponseBody
    public ApiResult list(TableBean tableBean){
        ApiResult re = new ApiResult();
        try {
            re.setData(tableService.list(tableBean));
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
            tableService.listByPage(pag);
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
