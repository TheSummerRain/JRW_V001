<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${maperPackagePath}.${maperName}">

    <select id="list" parameterType="${beanName}" resultType="${beanName}">
        <include refid="select${beanName}"></include>
        <where>
            <include refid="select${beanName}Where"></include>
        </where>
    </select>

    <select id="listByPage" parameterType="map" resultType="${beanName}">
        <include refid="select${beanName}"></include>
        <where>
            <include refid="select${beanName}WherePage"></include>
        </where>
        <trim prefix="order by" suffixOverrides=",">
            <#list fields as field>
            <if test="orderBy.${field.javaName} != null and orderBy.${field.javaName}!=''">
                ${field.name}
                <if test="orderBy.${field.javaName} == 'desc'">
                    desc
                </if>,
            </if>
            </#list>
        </trim>
    </select>

    <select id="get" parameterType="string" resultType="${beanName}">
        <include refid="select${beanName}"></include>
        WHERE id=${r"#{id}"}
    </select>


    <insert id="insert" parameterType="${beanName}">
        INSERT INTO ${table.tableName}
        (
    <#list fields as field>
            ${field.name}<#if field_has_next>,  </#if>
    </#list>
        )
        VALUES
        (
    <#list fields as field>
            ${r"#{"}${field.javaName}}<#if field_has_next>, </#if>
    </#list>
         );
    </insert>

    <update id="update" parameterType="${beanName}">
        UPDATE ${table.tableName}
        <set>
        <#list fields as field>
            <#if
            field.javaName != 'id' &&
            field.javaName != 'createBy' &&
            field.javaName != 'updateBy'
            >
                ${field.name} = ${r"#{"}${field.javaName}},
            </#if>
        </#list>
        </set>
        WHERE
        id = ${r"#{id}"}
    </update>

    <delete id="delete" parameterType="string">
        DELETE FROM ${table.tableName} WHERE id = ${r"#{id}"}
    </delete>

    <!-- sql片段 -->
    <sql id="select${beanName}">
        SELECT * FROM ${table.tableName}
    </sql>

    <sql id="select${beanName}Where">
    <#list fields as field>
        <if test="${field.javaName} != null and ${field.javaName}!=''">
            AND ${field.name} like ${r"#{"}${field.javaName}}
        </if>
    </#list>
    </sql>

    <sql id="select${beanName}WherePage">
    <#list fields as field>
        <if test="param.${field.javaName} != null and param.${field.javaName}!=''">
            AND ${field.name} like ${r"#{"}param.${field.javaName}}
        </if>
    </#list>
    <#list fields as field>
        <if test="param.${field.javaName}Begin != null and param.${field.javaName}Begin!=''">
            AND ${field.name} &gt;= ${r"#{"}param.${field.javaName}Begin}
        </if>
        <if test="param.${field.javaName}End != null and param.${field.javaName}End!=''">
            AND ${field.name}  &lt;= ${r"#{"}param.${field.javaName}End}
        </if>
    </#list>
    </sql>
</mapper>