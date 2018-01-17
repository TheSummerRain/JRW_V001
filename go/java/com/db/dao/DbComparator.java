package com.db.dao;

import com.db.dao.jdbc.BasicJdbcDao;
import com.google.gson.Gson;
import org.springframework.util.Assert;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 数据库表结构对比工具
 *
 * @author yangchao
 */
public class DbComparator extends DbConfig {

    private static final String KEY_TABLE_NAME = "TABLE_NAME";
    private static final String KEY_COLUMN_NAME = "COLUMN_NAME";
    private static final String KEY_COLUMN_TYPE = "COLUMN_TYPE";
    private static final String KEY_IS_NULLABLE = "IS_NULLABLE";
    private static final String KEY_TABLE_A = "新增";
    private static final String KEY_TABLE_U = "";
    private Map<String, Map<String, Map<String, String>>> tableMap = null;

    public DbComparator() {
        super();
    }

    public DbComparator(String ip, String port, String loginName, String password, String dbName) {
        super(ip, port, loginName, password, dbName);
    }

    public DbComparator(String ip, String port, String loginName, String password, String dbName, String alias) {
        super(ip, port, loginName, password, dbName, alias);
    }

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        DbComparator dbConfig1 = new DbComparator("api.syschain.cn", "3306", "qinyi", "qinyi365", "purchasing", "47");
        DbComparator dbConfig2 = new DbComparator("test.syschain.cn", "3306", "qinyi", "qinyi365", "purchasing", "test");
        String result = dbConfig1.compare(dbConfig2);
        System.out.println(result);
    }

    private String getMyAddKey() {
        return this.getAlias() + "_" + KEY_TABLE_A;
    }

/*	@SuppressWarnings({ "rawtypes", "unchecked" })
    private Map<String, List<String>> getIncreasedAndReducedKey(Map m1, Map m2){
		Map<String, List<String>> map = new HashMap<>();
		map.put(KEY_TABLE_A, compareSetString(m1.keySet(), m2.keySet()));	// 增加的字段
		map.put(KEY_TABLE_D, compareSetString(m2.keySet(), m1.keySet()));	// 减少的字段
		return map;
	}*/

    public String compare(DbComparator dbConfig) throws ClassNotFoundException, SQLException {
        Map<String, Object> data = new HashMap<>();
        // 获取所有表的信息
        Map<String, Map<String, Map<String, String>>> thisMap = this.getTableMap();
        Map<String, Map<String, Map<String, String>>> otherMap = dbConfig.getTableMap();

        // 对比双方增加的字段
        Map<String, List<String>> map = new HashMap<>();
        map.put(this.getMyAddKey(), compareSetString(thisMap.keySet(), otherMap.keySet()));
        map.put(dbConfig.getMyAddKey(), compareSetString(otherMap.keySet(), thisMap.keySet()));
        data.put("TABLE", map);

        // 移除有差异的表 然后进行字段的对比
        map.get(this.getMyAddKey()).forEach(t -> thisMap.remove(t));
        map.get(dbConfig.getMyAddKey()).forEach(t -> thisMap.remove(t));

        Map<String, Object> tableCompareInfo = new HashMap<>();

        // 循环对比表结构
        for (String tableName : thisMap.keySet()) {
            Map<String, Map<String, String>> thisTableInfo = thisMap.get(tableName);
            Map<String, Map<String, String>> otherTableInfo = otherMap.get(tableName);
            Map<String, List<String>> m = new HashMap<>();
            m.put(this.getMyAddKey(), compareSetString(thisTableInfo.keySet(), otherTableInfo.keySet()));
            m.put(dbConfig.getMyAddKey(), compareSetString(otherTableInfo.keySet(), thisTableInfo.keySet()));
            // 表字段 添加/删除  对比
            if (!m.get(this.getMyAddKey()).isEmpty() || !m.get(dbConfig.getMyAddKey()).isEmpty()) {
                tableCompareInfo.put(tableName, m);
            }
            // 进行字段类型的对比
            thisTableInfo.keySet().stream().filter(t -> !m.get(this.getMyAddKey()).contains(t))
                    .filter(t -> !m.get(dbConfig.getMyAddKey()).contains(t))
                    .filter(t -> !thisTableInfo.get(t).get(KEY_COLUMN_TYPE).equals(otherTableInfo.get(t).get(KEY_COLUMN_TYPE)))
                    .forEach(t -> {
                        Map<String, Object> columnUpMap = new HashMap<>();
                        columnUpMap.put(this.getAlias() + " - " + t, thisTableInfo.get(t).get(KEY_COLUMN_TYPE));
                        columnUpMap.put(dbConfig.getAlias() + " - " + t, otherTableInfo.get(t).get(KEY_COLUMN_TYPE));
                        tableCompareInfo.put(KEY_TABLE_U + tableName, columnUpMap);
                    });
        }
        data.put("COLUMN", tableCompareInfo);
        return new Gson().toJson(data);
    }

    /**
     * 对比获取 set1中存在 但 set2 中不存在的 值
     *
     * @param set1
     * @param set2
     * @return
     */
    private List<String> compareSetString(Set<String> set1, Set<String> set2) {
        return set1.stream().filter(t -> !set2.contains(t)).collect(Collectors.toList());
    }

    /**
     * 获取数据库所有表的表结构
     *
     * @return
     */
    private List<Map<String, String>> getTableInfo() {
        List<Map<String, String>> list;
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT 			");
        sql.append(" 	lower(COLUMN_NAME) " + KEY_COLUMN_NAME + ", 	"); // 字段名
        sql.append(" 	lower(COLUMN_TYPE) " + KEY_COLUMN_TYPE + ", 	"); // 字段类型 + 长度
        sql.append(" 	IS_NULLABLE " + KEY_IS_NULLABLE + ",	"); // 是否为空
        sql.append(" 	lower(TABLE_NAME) " + KEY_TABLE_NAME + "		"); // 表明
        sql.append(" FROM 										");
        sql.append(" 	information_schema.`COLUMNS` ");
        sql.append(" WHERE 							 ");
        sql.append(" 	TABLE_SCHEMA LIKE '" + this.getDbName() + "' ");
        sql.append(" ORDER BY 						 			");
        sql.append(" 	TABLE_NAME 					 			");

        BasicJdbcDao dao = new BasicJdbcDao(this);
        list = dao.query(sql.toString());
        return list;
    }

    private Map<String, Map<String, Map<String, String>>> getTableMap() {
        if (tableMap == null) {
            List<Map<String, String>> list = this.getTableInfo();
            tableMap = getGroupbyMap(list, KEY_TABLE_NAME, KEY_COLUMN_NAME);
        }
        return tableMap;
    }

    /**
     * @param list
     * @param groupbyName
     * @return Map<String(表名), Map<String(字段名), Map<String, String>>>
     */
    private Map<String, Map<String, Map<String, String>>> getGroupbyMap(List<Map<String, String>> list, String groupbyName, String groupbyName1) {
        Map<String, Map<String, Map<String, String>>> maps = new HashMap<>();
        list.forEach(t -> {
            String tableName = t.get(groupbyName);
            String columnName = t.get(groupbyName1);
            Assert.notNull(tableName, "分组字段值不能为空");
            // 获取该表的表结构map
            Map<String, Map<String, String>> tableMap = maps.getOrDefault(tableName, new HashMap<>());
            tableMap.put(columnName, t);
            maps.put(tableName, tableMap);
        });
        return maps;
    }
}
