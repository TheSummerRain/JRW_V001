package com.jryz.go.web.service.imp;

import com.db.dao.DbConfig;
import com.db.dao.jdbc.BasicJdbcDao;
import com.jryz.StringUtil;
import com.jryz.freemarker.FreemarkerUtil;
import com.jryz.go.InputType;
import com.jryz.go.web.bean.ConfigBean;
import com.jryz.go.web.bean.FieldBean;
import com.jryz.go.web.bean.TableBean;
import com.jryz.go.web.service.TableCoreService;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.*;

/**
 * 导入表信息 service
 */
@Service
public class TableCoreServiceImpl implements TableCoreService {

    @Override
    public List<Map<String, String>> getTables(DbConfig dbConfig, String tableName) {
        BasicJdbcDao db = new BasicJdbcDao(dbConfig);
        List<String> params = new ArrayList<>();
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT                                 ");
        sql.append(" 	TABLE_NAME,                         ");
        sql.append(" 	TABLE_COMMENT                       ");
        sql.append(" FROM                                   ");
        sql.append(" 	information_schema.`TABLES`         ");
        sql.append(" WHERE                                  ");
        sql.append(" 	TABLE_SCHEMA = ?                    ");
        params.add(dbConfig.getDbName());
        if (tableName != null) {
            params.add("%" + tableName + "%");
            sql.append(" AND TABLE_NAME LIKE ? ");
        }
        return db.query(sql.toString(), params);
    }

    @Override
    public TableBean getTable(DbConfig dbConfig, TableBean table) {
        BasicJdbcDao db = new BasicJdbcDao(dbConfig);
        List<String> params = new ArrayList<>();
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT                                 ");
        sql.append(" 	TABLE_NAME,                         ");
        sql.append(" 	TABLE_COMMENT                       ");
        sql.append(" FROM                                   ");
        sql.append(" 	information_schema.`TABLES`         ");
        sql.append(" WHERE                                  ");
        sql.append(" 	TABLE_SCHEMA = ?                    ");
        sql.append(" AND TABLE_NAME = ?                     ");
        params.add(dbConfig.getDbName());
        params.add(table.getTableName());
        Map<String, String> tableMap =  db.queryOneRow(sql.toString(), params.toArray(new String[]{}));
        Assert.state(tableMap != null && !tableMap.isEmpty(), "表不存在");

        table.setName(StringUtil.getBeanNameByTableName(tableMap.get("TABLE_NAME")));
        table.setContent(tableMap.get("TABLE_COMMENT"));
        return table;
    }

    @Override
    public List<FieldBean> getFidlds(DbConfig dbConfig, TableBean table) {
        BasicJdbcDao db = new BasicJdbcDao(dbConfig);
        List<String> params = new ArrayList<>();
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT                                             ");
        sql.append(" 	k.CONSTRAINT_NAME,                              ");
        sql.append(" 	c.*                                             ");
        sql.append(" FROM                                               ");
        sql.append(" 	information_schema.`COLUMNS` c                  ");
        sql.append(" LEFT JOIN information_schema.KEY_COLUMN_USAGE k    ");
        sql.append(" ON k.COLUMN_NAME = c.COLUMN_NAME                   ");
        sql.append(" AND k.TABLE_SCHEMA = c.TABLE_SCHEMA                ");
        sql.append(" AND k.constraint_name != 'PRIMARY'                 ");
        sql.append(" WHERE                                              ");
        sql.append(" 	c.TABLE_SCHEMA = ?                              ");
        sql.append(" AND c.TABLE_NAME = ?                               ");

        params.add(dbConfig.getDbName());
        params.add(table.getTableName());
        List<FieldBean> fidlds = new ArrayList<>();
        List<Map<String, String>> data =  db.query(sql.toString(), params.toArray(new String[]{}));

        Assert.state(data != null && !data.isEmpty(), "字段不存在 " + fidlds);
        List<FieldBean> fields = new ArrayList<>(data.size());
        data.forEach((v) -> {
            FieldBean field = new FieldBean();
            String fieldType = null;
            switch (v.get("DATA_TYPE").toLowerCase()) {
                case "int":
                case "tinyint":
                    fieldType = "Integer";
                    break;
                case "decimal":
                case "double":
                case "float":
                    fieldType = "Float";
                    break;
                default:
                    fieldType =  "String";
                    break;
            }
            field.setFieldType(fieldType);
            field.setTableId(table.getId());
            field.setName(v.get("COLUMN_NAME"));
            field.setInput("input");
            field.setUnName(v.get("CONSTRAINT_NAME"));
            field.setSeelectType(1);
            field.setJavaName(StringUtil.getBeanNameByFiedlName(v.get("COLUMN_NAME")));
            field.setContent(v.get("COLUMN_COMMENT") == null ? "" : v.get("COLUMN_COMMENT"));
            field.setLength(v.get("CHARACTER_MAXIMUM_LENGTH") == null ? 0 : Integer.parseInt(v.get("CHARACTER_MAXIMUM_LENGTH")));
            field.setNotNull("NO".equalsIgnoreCase(v.get("IS_NULLABLE")) ? "1" : "0");
            fields.add(field);
        });
        return fields;
    }

    /**
     * 生成代码
     *
     * @param table
     */
    @Override
    public void createCode(TableBean table, Set<InputType> inputTypes) {
        ConfigBean configBean;
        Assert.notNull(table.getPackageName(), "包名为空");
        Assert.notNull(table.getName());
        Assert.notNull(table.getFieldList());
        Assert.notNull(configBean = table.getConfigBean());

        Map<String, Object> map = new HashMap<>();
        map.put("table", table);

        // 初始化参数
        String beanName =  table.getName().substring(0, 1).toUpperCase() + table.getName().substring(1);
        String filePath = configBean.getBasicPath();
        String srcPath = configBean.getSrcPath();
        String resourcesPath = configBean.getResourcesPath();
        String htmlPah = configBean.getHtmlPath();
        String packagePath = table.getPackageName().replace(".", "/");
        srcPath =  filePath + "/" + srcPath + "/" + packagePath;
        resourcesPath = filePath + "/" + resourcesPath + "/" + packagePath;

        String[] strs = packagePath.split("/");
        htmlPah = filePath + "/" + htmlPah + "/" + packagePath.replace(strs[0] + "/", "/"); // html 取二级目录 省略 com (域名)

        /**
         * 包装参数
         */
        String className = beanName + "Bean";
        String controllerName = beanName + "Controller";
        String maperName = beanName + "Maper";
        String serviceName = beanName + "Service";
        String serviceImpName = beanName + "ServiceImp";

        map.put("beanName", className);
        map.put("controllerName", controllerName);
        map.put("maperName", maperName);
        map.put("serviceName", serviceName);
        map.put("serviceImpName", serviceImpName);

        map.put("beanPackagePath", table.getPackageName() + ".bean");
        map.put("controllerPackagePath", table.getPackageName() + ".controller");
        map.put("maperPackagePath", table.getPackageName() + ".maper");
        map.put("servicePackagePath", table.getPackageName() + ".service");
        map.put("serviceImpPackagePath", table.getPackageName() + ".service.imp");

        map.put("fields", table.getFieldList());        // 字段
        map.put("unFields", table.getUnFieldList());    // 唯一索引
        map.put("sonTable", table.getSonTableBean());   // 子表
        map.put("oneFields", table.getOnefieldList());   // 单对单操作
        map.put("manyFields", table.getManyfieldList());   // 子表

        if (table.getSonTableBean() != null) {
            table.getSonTableBean().forEach(b -> createCode(b, inputTypes)); // 先去创建 子表
        }

        if (inputTypes.contains(InputType.MAPPER)) {
            FreemarkerUtil.print("web/maper/maperXml.ftl", map, resourcesPath + "/maper/" + maperName + ".xml");
            FreemarkerUtil.print("web/maper/maper.ftl", map, srcPath + "/maper/" + maperName + ".java");
        }
        if (inputTypes.contains(InputType.SERVICE)) {
            FreemarkerUtil.print("web/service/imp/serviceImpl.ftl", map, srcPath + "/service/imp/" + serviceImpName + ".java");
            FreemarkerUtil.print("web/service/service.ftl", map, srcPath + "/service/" + serviceName + ".java");
        }
        if (inputTypes.contains(InputType.BEAN)) {
            FreemarkerUtil.print("web/bean/bean.ftl", map, srcPath + "/bean/" + className + ".java");
        }
        if (inputTypes.contains(InputType.CONTROLLER)) {
            FreemarkerUtil.print("web/controller/controller.ftl", map, srcPath + "/controller/" + controllerName + ".java");
        }
        if (inputTypes.contains(InputType.HTML)) {
            FreemarkerUtil.print("web/views/views.ftl", map, htmlPah + "/" + beanName.toLowerCase() + ".jsp");
        }
    }
}
