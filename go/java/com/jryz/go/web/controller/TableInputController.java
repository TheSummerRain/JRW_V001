package com.jryz.go.web.controller;


import com.db.dao.DbConfig;
import com.jryz.go.InputType;
import com.jryz.go.web.bean.ConfigBean;
import com.jryz.go.web.bean.FieldBean;
import com.jryz.go.web.bean.TableBean;
import com.jryz.go.web.service.ConfigService;
import com.jryz.go.web.service.FieldService;
import com.jryz.go.web.service.TableCoreService;
import com.jryz.go.web.service.TableService;
import com.jryz.model.ApiResult;
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
import java.util.Set;

/**
 * 表导入相关接口
 * 读取表信息部分 使用jdbc 链接。 与架构脱离
 * @author
*/
@RequestMapping("/o/tableInput")
@Controller
public class TableInputController {


    @Autowired
    private FieldService fieldService;
    @Autowired
    private TableService tableService;
    @Autowired
    private TableCoreService tableInputService;
    @Autowired
    private ConfigService configService;
    @Autowired
    private TableCoreService tableCoreService;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 获取table列表
     * @param configId
     * @return
     */
    @RequestMapping("selectTable/{configId}")
    @ResponseBody
    public ApiResult selectTable(@PathVariable String configId, TableBean table){
        ApiResult re = new ApiResult();
        try {
            ConfigBean configBean = configService.get(configId);
            Assert.notNull(configBean, "配置信息不存在");

            DbConfig dbConfig = new DbConfig();
            dbConfig.setUrl(configBean.getDbUrl());
            dbConfig.setLoginName(configBean.getDbUserName());
            dbConfig.setPassword(configBean.getDbUserPassword());
            dbConfig.setDbName(configBean.getDbName());

            re.setData(
                    tableInputService.getTables(dbConfig, table.getTableName())
            );
            re.setSuccess(true);
        } catch (IllegalArgumentException e) { // 捕获运行期异常
            re.setMsg(e.getMessage());
        } catch (Exception e) {
            logger.error("", e);
        }
        return re;
    }

    /**
     * 导入 table
     * @param
     * @return
     */
    @RequestMapping("/doInputs/{id}")
    @ResponseBody
    public ApiResult inputTables(@PathVariable("id") String configId, @RequestParam("tableNames[]") List<String> tableNames, String packageName){
        ApiResult re = new ApiResult();
        Assert.notNull(tableNames);
        for (String tableName : tableNames) {
            TableBean tableBean = new TableBean();
            tableBean.setTableName(tableName);
            tableBean.setPackageName(packageName);
            ApiResult r = inputTable(configId, tableBean);
            if (!r.isSuccess()) {
                throw new RuntimeException(tableName + "导入异常 请查看后端控制台日志输出");
            }
        }
        re.setSuccess(true);
        return re;
    }

    /**
     * 导入 table
     * @param
     * @return
     */
    @RequestMapping("/doInput/{id}")
    @ResponseBody
    public ApiResult inputTable(@PathVariable("id") String configId, TableBean table){
        ApiResult re = new ApiResult();
        try {
            Assert.hasText(table.getTableName(), "表名不能为空");
            Assert.hasText(configId, "配置id 不能为空");
            ConfigBean config = configService.get(configId);
            Assert.notNull(config, "配置不存在");

            DbConfig dbConfig = new DbConfig();
            dbConfig.setUrl(config.getDbUrl());
            dbConfig.setLoginName(config.getDbUserName());
            dbConfig.setPassword(config.getDbUserPassword());
            dbConfig.setDbName(config.getDbName());

            // 保存表信息
            table = tableInputService.getTable(dbConfig, table);
            table.setConfigId(configId);
            table = tableService.replace(table);

            // 保存字段信息
            List<FieldBean> fields = tableInputService.getFidlds(dbConfig, table);
            Assert.notNull(fields, "获取字段信息失败");
            fields.forEach((i) -> fieldService.replace(i));

            re.setSuccess(true);
        } catch (IllegalArgumentException e) { // 捕获运行期异常
            re.setMsg(e.getMessage());
        } catch (Exception e) {
            logger.error("", e);
        }
        return re;
    }

    /**
     * 生成代码
     * @param tableId
     * @param inputTypes
     * @return
     */
    @RequestMapping("/createFile/{id}")
    @ResponseBody
    public ApiResult createFile(@PathVariable("id") String tableId, @RequestParam(value = "inputTypes[]", required = false) Set<InputType> inputTypes){
        ApiResult re = new ApiResult();
        try {
            TableBean tableBean = tableService.get(tableId);
            tableCoreService.createCode(tableBean, inputTypes);
            re.setSuccess(true);
        } catch (IllegalArgumentException e) { // 捕获运行期异常
            re.setMsg(e.getMessage());
        } catch (Exception e) {
            logger.error("", e);
        }
        return re;
    }
}
