package com.jryz.go.core.service.impl;

import com.db.dao.DbConfig;
import com.db.dao.jdbc.BasicJdbcDao;
import com.jryz.StringUtil;
import com.jryz.file.PropertiesUtil;
import com.jryz.freemarker.FreemarkerUtil;
import com.jryz.go.core.bean.Field;
import com.jryz.go.core.bean.FieldType;
import com.jryz.go.core.bean.Table;
import com.jryz.go.core.service.JrGoService;
import org.springframework.util.Assert;

import java.util.*;

/**
 * 代码生成器 逻辑接口
 */
public class JrGoServiceImpl implements JrGoService{


    /**
     * 获取 表结构
     * @param tableName
     * @return
     */
    @Override
    public Table getTable(String tableName, DbConfig dbConfig) {
        Assert.notNull(tableName);
        Assert.notNull(dbConfig);
        Table table = new Table();
        table.setTableName(tableName);
        table.setName(StringUtil.getBeanNameByTableName(tableName));
        table.setUnique(getUnKey(tableName, dbConfig));
        table.setFields(getField(tableName, dbConfig));
        return table;
    }

    /**
     * 获取 字段集合
     * @param tableName
     * @return
     */
    private List<Field> getField(String tableName, DbConfig dbConfig) {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT                                      ");
        sql.append(" 	*                                        ");
        sql.append(" FROM                                        ");
        sql.append(" 	information_schema.COLUMNS               ");
        sql.append(" WHERE                                       ");
        sql.append(" 	table_name = ? AND TABLE_SCHEMA = ?      ");
        BasicJdbcDao db = new BasicJdbcDao(dbConfig);
        List<Map<String, String>> data = db.query(sql.toString(), tableName, dbConfig.getDbName());
        List<Field> fields = new ArrayList<>(data.size());
        data.forEach((v) -> {
            Field field = new Field();
            field.setType(FieldType.getFieldType(v.get("DATA_TYPE")));
            field.setName(v.get("COLUMN_NAME"));
            field.setJavaName(StringUtil.getBeanNameByFiedlName(v.get("COLUMN_NAME")));
            field.setContent(v.get("COLUMN_COMMENT") == null ? "" : v.get("COLUMN_COMMENT"));
            field.setLength(v.get("CHARACTER_MAXIMUM_LENGTH") == null ? 0 : Integer.parseInt(v.get("CHARACTER_MAXIMUM_LENGTH")));
            field.setNotNull("NO".equalsIgnoreCase(v.get("IS_NULLABLE")));
            fields.add(field);
        });
        return fields;
    }

    /**
     * 获取 索引
     * @param tableName
     * @return
     */
    private List<Field[]> getUnKey(String tableName, DbConfig dbConfig) {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT                                     ");
        sql.append(" 	GROUP_CONCAT(COLUMN_NAME) COLUMN_NAMES  ");
        sql.append(" FROM                                       ");
        sql.append(" 	information_schema.KEY_COLUMN_USAGE     ");
        sql.append(" WHERE                                      ");
        sql.append(" 	table_name = ? AND TABLE_SCHEMA = ?     ");
        sql.append(" AND constraint_name != 'PRIMARY'           ");
        sql.append(" GROUP BY                                   ");
        sql.append(" 	constraint_name                         ");
        BasicJdbcDao db = new BasicJdbcDao(dbConfig);
        List<Map<String, String>> data = db.query(sql.toString(), tableName, dbConfig.getDbName());
        List<Field[]> fields = new ArrayList<>(data.size());
        data.forEach((v) -> {
            String[] columns = v.get("COLUMN_NAMES").split(",");
            Field[] fields1 = new Field[columns.length];
            for (int i = 0; i < columns.length; i++) {
                Field field = new Field();
                field.setName(columns[i]);
                field.setJavaName(StringUtil.getBeanNameByTableName(columns[i]));
                fields1[i] = field;
            }
            fields.add(fields1);
        });
        return fields;
    }

    /**
     * 生成代码
     *
     * @param table
     */
    @Override
    public void inputTable(Table table) {
        Assert.notNull(table.getPackageName(), "报名为空");
        Assert.notNull(table.getName());
        Assert.notNull(table.getFields());

        Map<String, Object> map = new HashMap<>();
        map.put("table", table);
        PropertiesUtil propertiesUtil = new PropertiesUtil("jrgo/go.properties");
        Properties properties = propertiesUtil.getProperties();

        // 初始化参数
        String beanName =  table.getName().substring(0, 1).toUpperCase() + table.getName().substring(1);
        String filePath = properties.getProperty("BASIC_PATCH");
        String srcPath = properties.getProperty("SRC_PATCH");
        String resourcesPath = properties.getProperty("RESOURCES_PATCH");
        String htmlPah = properties.getProperty("HTML_PATCH");
        String packagePath = table.getPackageName().replace(".", "/");
        srcPath =  filePath + "/" + srcPath + "/" + packagePath;
        resourcesPath = filePath + "/" + resourcesPath + "/" + packagePath;

        String[] strs = packagePath.split("/");
        htmlPah = filePath + "/" + htmlPah + "/" + packagePath.replace(strs[0] + "/" + strs[1], ""); // html 取二级目录 省略 com.xx (域名后缀 加业务标记)

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
        FreemarkerUtil.print("java/maper/maperXml.ftl", map, resourcesPath + "/maper/" + maperName + ".xml");
        FreemarkerUtil.print("java/maper/maper.ftl", map, srcPath + "/maper/" + maperName + ".java");
        FreemarkerUtil.print("java/service/imp/serviceImpl.ftl", map, srcPath + "/service/imp/" + serviceImpName + ".java");
        FreemarkerUtil.print("java/bean/bean.ftl", map, srcPath + "/bean/" + className + ".java");
        FreemarkerUtil.print("java/controller/controller.ftl", map, srcPath + "/controller/" + controllerName + ".java");
        FreemarkerUtil.print("java/service/service.ftl", map, srcPath + "/service/" + serviceName + ".java");
        FreemarkerUtil.print("java/views/views.ftl", map, htmlPah + "/" + beanName + ".jsp");
       /*

       */
    }
}
