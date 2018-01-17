package com.test;


import com.db.dao.DbConfig;
import com.jryz.file.PropertiesUtil;
import com.jryz.freemarker.FreemarkerUtil;
import com.jryz.go.core.bean.Field;
import com.jryz.go.core.bean.FieldType;
import com.jryz.go.core.bean.Table;
import com.jryz.go.core.service.JrGoService;
import com.jryz.go.core.service.impl.JrGoServiceImpl;
import org.junit.jupiter.api.Test;

import java.util.*;

public class Jrgo {

    // 输入表名 生成代码
    @Test
    public void testJrGo(){
        JrGoService jrGoService = new JrGoServiceImpl();
        PropertiesUtil propertiesUtil = new PropertiesUtil("jrgo/go.properties");
        Properties properties = propertiesUtil.getProperties();

        DbConfig dbConfig = new DbConfig();
        dbConfig.setDbName(properties.getProperty("go.dbname"));
        dbConfig.setUrl(properties.getProperty("go.url"));
        dbConfig.setLoginName(properties.getProperty("go.username"));
        dbConfig.setPassword(properties.getProperty("go.password"));

        Table table = jrGoService.getTable("go_config", dbConfig);
        table.setContent("数据源配置信息");
        table.setPackageName("com.jryz.go.web");
        jrGoService.inputTable(table);

        table = jrGoService.getTable("go_field", dbConfig);
        table.setContent("字段信息");
        table.setPackageName("com.jryz.go.web");
        jrGoService.inputTable(table);

        table = jrGoService.getTable("go_table", dbConfig);
        table.setContent("表信息");
        table.setPackageName("com.jryz.go.web");
        jrGoService.inputTable(table);

        table = jrGoService.getTable("go_table_un", dbConfig);
        table.setContent("唯一约束");
        table.setPackageName("com.jryz.go.web");
        jrGoService.inputTable(table);

        /*table = jrGoService.getTable("tab_account", dbConfig);
        table.setContent("账号");
        table.setPackageName("com.core.login");
        jrGoService.inputTable(table);

        table = jrGoService.getTable("tab_account_type", dbConfig);
        table.setContent("账号类型");
        table.setPackageName("com.core.login");
        jrGoService.inputTable(table);*/
    }

    @Test
    public void test(){
        Map<String, Object> map = new HashMap<>();
        Table table = new Table();
        table.setName("test");
        table.setPackageName("test");
        List<Field> fields = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Field field = new Field();
            field.setName("test" + i);
            field.setType(FieldType.STRING);
            fields.add(field);
        }
        table.setFields(fields);
        map.put("table", table);
        FreemarkerUtil.print("java/bean/bean.ftl", map, "D:\\many\\ssm\\go\\java\\com\\test\\bean\\TestBean.java");
    }
}
