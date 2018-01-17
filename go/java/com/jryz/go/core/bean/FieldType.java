package com.jryz.go.core.bean;

/**
 * 字段类型
 */
public enum FieldType {

    INT(Integer.class, "Integer"),
    STRING(String.class, "String"),
    FLOAT(Float.class, "Float");


    FieldType(Class<?> classes, String str) {
        this.classes = classes;
        this.str = str;
    }

    FieldType(Class<?> classes) {
        this.classes = classes;
    }

    private Class<?> classes;
    private String str;

    public Class<?> getClasses() {
        return classes;
    }

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }

    public static FieldType getFieldType(String mysqlType){
        switch (mysqlType.toLowerCase()) {
            case "int":
            case "tinyint":
                return INT;
            case "decimal":
            case "double":
            case "float":
                return FLOAT;
            default:
                return STRING;
        }
    }
}
