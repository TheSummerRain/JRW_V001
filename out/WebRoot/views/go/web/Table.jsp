<%--
  Created by IntelliJ IDEA.
  User: yangchao
  Date: 2017/12/14
  Time: 16:54
  To change this template use File | Settings | File Templates.
--%>
<%@include file="/views/file.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!-- 一个 单页的 单对单 表信息 -->
<html>
<head>
    <title>表信息</title>

    <%-- <link type="text/css" href="plug-in\bootstarp\css\bootstrap.css" rel="stylesheet">
     <script src="plug-in\bootstarp\js\bootstrap.js"></script>--%>

    <!-- 引入样式 -->
    <script src="plug-in\vue\vue.js"></script>
    <script src="plug-in\vue\vue-resourec.js"></script>

    <!-- 引入组件库 -->
    <link rel="stylesheet" href="plug-in/elm/css/index.css">
    <script src="plug-in/elm/js/index.js"></script>
    <style>
        .el-row {
            margin-bottom: 20px;

        &
        :last-child {
            margin-bottom: 0;
        }

        }
        .el-col {
            border-radius: 4px;
        }

        .bg-purple-dark {
            background: #99a9bf;
        }

        .bg-purple {
            background: #d3dce6;
        }

        .bg-purple-light {
            background: #e5e9f2;
        }

        .grid-content {
            border-radius: 4px;
            min-height: 36px;
        }

        .row-bg {
            padding: 10px 0;
            background-color: #f9fafc;
        }

        body {
            overflow-x: hidden !important;
            overflow-y: auto !important;
        }

        .demo-table-expand {
            font-size: 0;
        }

        .demo-table-expand label {
            width: 120px;
            color: #99a9bf;
        }

        .demo-table-expand .el-form-item {
            margin-right: 0;
            margin-bottom: 0;
            width: 50%;
        }
    </style>
</head>
<body style="width: 100%">

<div id="app" style="display: none">

    <!-- BEGIN 弹窗 -->
    <div>


        <!-- 确认删除弹框 -->
        <el-popover
                ref="popover5"
                placement="top"
                width="200"
                v-model="visible2">
            <p>这是一段内容这是一段内容确定删除吗？</p>
            <div style="text-align: right; margin: 0">
                <el-button size="mini" type="mini" @click="visible2 = false">
                    取消
                </el-button>
                <el-button type="danger" size="mini" @click="visible2 = false;del()">
                    确定
                </el-button>
            </div>
        </el-popover>


        <!-- 导入表信息 -->
        <el-dialog title="导入表信息"
                   :visible.sync="inputLoad"
                   center="true"
                   width="600">
            <div style="text-align: left; margin: 0">
                <el-form :model="inputTableData" :rules="
                {
                configId: [
                    {required: true, message: '请输入配置id', trigger: 'change'}
                ],
                 tableNames: [
                    {required: true, message: '请选择表名', trigger: 'change'}
                ],
                packageName: [
                    {required: true, message: '输入包名', trigger: 'change'}
                ]
            }" ref="inputTableData"
                         label-width="120px" label-position="left"
                         class="demo-ruleForm">
                    <el-form-item label="配置" prop="configId">
                        <el-select v-model="inputTableData.configId"
                                   clearable
                                   placeholder="请选择"
                                   @change="configClink"
                        >
                            <el-option
                                    v-for="item in selectData.config"
                                    :key="item.id"
                                    :label="item.title"
                                    :value="item.id">
                            </el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item label="表名" prop="tableNames">
                        <%-- <el-input v-model="inputTableData.tableNames"></el-input>--%>
                        <el-select v-model="inputTableData.tableNames"
                                   multiple
                                   filterable
                                   placeholder="请选择">
                            <el-option
                                    v-for="item in selectData.table"
                                    :key="item.TABLE_NAME"
                                    :label="item.TABLE_COMMENT + '(' + item.TABLE_NAME + ')'"
                                    :value="item.TABLE_NAME">
                            </el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item label="包名" prop="packageName">
                        <el-input v-model="inputTableData.packageName"></el-input>
                    </el-form-item>
                    <el-form-item>
                        <el-button type="primary" @click="inputTable('ruleForm')">
                            导入
                        </el-button>
                        <el-button @click="inputLoad=false">
                            关闭
                        </el-button>
                    </el-form-item>
                </el-form>
            </div>
        </el-dialog>

        <!-- 编辑 添加部分 -->
        <el-dialog title="表信息"
                   :visible.sync="dialogFormVisible"
                   center="true"
                   width="600">
            <div style="text-align: left; margin: 0">
                <el-form :model="ruleForm" :rules="
                {
                tableName: [
                    {required: true, message: '请输入表名', trigger: 'change'}
                ]
                ,
                name: [
                    {required: true, message: '请输入加工后的 名称 如 userBean', trigger: 'change'}
                ]
                ,
                packageName: [
                    {required: true, message: '请输入包名', trigger: 'change'}
                ]
                ,
                content: [
                    {required: true, message: '请输入描述', trigger: 'change'}
                ]
                ,
                configId: [
                    {required: true, message: '请输入配置id', trigger: 'change'}
                ]
                ,
            }" ref="ruleForm"
                         label-width="120px" label-position="left"
                         class="demo-ruleForm">

                    <el-form-item label="表名" prop="">
                        <el-input :disabled="true" v-model="ruleForm.tableName"></el-input>
                    </el-form-item>
                    <el-form-item label="bean name" prop="">
                        <el-input :disabled="true" v-model="ruleForm.name"></el-input>
                    </el-form-item>
                    <el-form-item label="包名" prop="packageName">
                        <el-input v-model="ruleForm.packageName"></el-input>
                    </el-form-item>
                    <el-form-item label="描述" prop="content">
                        <el-input v-model="ruleForm.content"></el-input>
                    </el-form-item>
                    <el-form-item label="启动导入导出?" prop="isInput">
                        <template>
                            <el-radio v-model="ruleForm.isInput" label="1">是</el-radio>
                            <el-radio v-model="ruleForm.isInput" label="0">否</el-radio>
                        </template>
                    </el-form-item>
                    <el-form-item label="父表" prop="prTableId">
                        <el-select v-model="ruleForm.prTableId"
                                   placeholder="请选择"
                                   @change="tableClink"
                        >
                            <el-option
                                    v-for="item in selectData.sonTable"
                                    :key="item.id"
                                    :label="item.name + ' ' + item.content"
                                    :value="item.id">
                            </el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item label="与父表关联字段" prop="prTableFieId">
                        <el-select v-model="ruleForm.prTableFieId"
                                   placeholder="请选择"
                        >
                            <el-option
                                    v-for="item in selectData.sonField"
                                    :key="item.id"
                                    :label="item.name  + ' ' + item.content"
                                    :value="item.id">
                            </el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item>
                        <el-button type="primary" @click="submitForm('ruleForm')">
                            提交
                        </el-button>
                        <el-button @click="resetForm('ruleForm')">
                            关闭
                        </el-button>
                    </el-form-item>
                </el-form>
            </div>
        </el-dialog>
    </div>

    <!-- END 弹窗 -->

    <!-- BEGIN 搜索框 部分 -->
    <el-row :gutter="20">
        <el-col :span="24" offset="0">
            <el-collapse v-model="activeName" @change="handleChange">
                <el-collapse-item name="1">
                    <template slot="title">
                        <i class="el-icon-search"></i> 搜索项
                    </template>
                    <el-form :inline="true" :model="formInline" class="el-form--inline" label-width="100px"
                             label-position="right">

                        <el-col :span="12">
                            <el-form-item label="表名">
                                <el-input v-model="formInline.tableName" placeholder="表名"></el-input>
                            </el-form-item>
                        </el-col>
                        <el-col :span="12">
                            <el-form-item label="名称">
                                <el-input v-model="formInline.name" placeholder="名称"></el-input>
                            </el-form-item>
                        </el-col>
                        <el-col :span="12">
                            <el-form-item label="包名">
                                <el-input v-model="formInline.packageName" placeholder="包名"></el-input>
                            </el-form-item>
                        </el-col>
                        <el-col :span="12">
                            <el-form-item label="描述">
                                <el-input v-model="formInline.content" placeholder="描述"></el-input>
                            </el-form-item>
                        </el-col>
                        <el-col :span="12">
                            <el-form-item label="配置">
                                <el-select v-model="formInline.configId"
                                           clearable
                                           placeholder="请选择"
                                >
                                    <el-option
                                            v-for="item in selectData.config"
                                            :key="item.id"
                                            :label="item.title"
                                            :value="item.id">
                                    </el-option>
                                </el-select>
                            </el-form-item>
                        </el-col>
                        <el-col :span="23">
                            <el-form-item style="float: right">
                                <div class="block" style="float: right">
                                    <el-button type="primary" @click="onSubmit" size="small" icon="el-icon-search"
                                               plain>搜索
                                    </el-button>
                                </div>
                            </el-form-item>
                        </el-col>
                    </el-form>
                </el-collapse-item>
            </el-collapse>
        </el-col>
    </el-row>
    <!-- END 搜索框 -->

    <!-- BEGIN table 部分 -->
    <el-row :gutter="20">
        <el-col :span="24" offset="0">
            <el-table
                    :data="tableData.items"
                    stripe="true"
                    ref="singleTable"
                    highlight-current-row
                    height="500"
                    @current-change="clink"
                    style="width: 100%">

                <el-table-column type="expand">
                    <template slot-scope="props">
                        <el-form label-position="left" inline class="demo-table-expand">
                            <el-form-item label="表名">
                                <span>{{ props.row.tableName }}</span>
                            </el-form-item>
                            <el-form-item label="bean 名">
                                <span>{{ props.row.name }}</span>
                            </el-form-item>
                            <el-form-item label="包名">
                                <span>{{ props.row.packageName }}</span>
                            </el-form-item>
                            <el-form-item label="描述">
                                <span>{{ props.row.content }}</span>
                            </el-form-item>
                            <el-form-item label="配置">
                                <span>{{ props.row.configBean.title }}</span>
                            </el-form-item>
                            <el-form-item label="导入时间">
                                <span>{{ props.row.createDate }}</span>
                            </el-form-item>
                            <el-form-item label="修改时间">
                                <span>{{ props.row.updateDate }}</span>
                            </el-form-item>

                        </el-form>
                    </template>
                </el-table-column> <!-- END 展开部分 -->

                <el-table-column
                        prop="tableName"
                        label="表名">
                </el-table-column>
                <el-table-column
                        prop="configBean.title"
                        label="配置">
                </el-table-column>
                <el-table-column
                        prop="content"
                        label="描述">
                </el-table-column>
                <el-table-column
                        fixed="right"
                        label="操作"
                        width="200">
                    <template slot-scope="scope">
                        <el-button @click="openFieldApp(scope.row)" type="text" size="small">查看字段</el-button>
                        <el-button @click="go(scope.row)" type="text" size="small" >生成代码</el-button>
                    </template>
                </el-table-column>

            </el-table>
        </el-col>
    </el-row>
    <!-- END table -->

    <!-- BEGIN pagination 部分 -->
    <el-row :gutter="20">
        <el-col :span="24" offset="0">

            <!-- BEGIN 按钮区 -->
            <el-col :span="10">
                <div class="block" style="">
                    <el-button icon="el-icon-circle-plus" size="small" plain @click="inputLoad = true">导入</el-button>
                    <el-button icon="el-icon-edit" size="small" plain @click="update">加工</el-button>
                    <el-button type="success" icon="el-icon-delete" size="small" plain v-popover:popover5>删除</el-button>
                    &nbsp;
                    <el-select v-model="checkedInputTypes" size="small" collapse-tags multiple placeholder="请选择模块">
                        <el-option
                                v-for="item in inputTypes"
                                :key="item"
                                :label="item"
                                :value="item">
                        </el-option>
                    </el-select>
                </div>
            </el-col>
            <!-- END 按钮区 -->

            <el-col :span="14">
                <div class="block" style="float: right">
                    <el-pagination
                            @size-change="handleSizeChange"
                            @current-change="handleCurrentChange"
                            :current-page="tableData.curPage"
                            :page-sizes="[5, 10, 50, 100, 500]"
                            :page-size="tableData.pageSize"
                            layout="total, sizes, prev, pager, next, jumper"
                            :total="tableData.totalCount">
                    </el-pagination>
                </div>
            </el-col> <!-- end 分页 -->
        </el-col>
    </el-row>
    <!-- END pagination -->
</div>

<!-- BEGIN 字段 app -->
<div id="appFiled" style="">

    <el-dialog :title="tableName"
               :visible.sync="app"
               center="true"
               width="95%">

        <!-- BEGIN 弹窗 -->
        <div>
            <!-- 确认删除弹框 -->
            <el-popover
                    append-to-body
                    ref="popover5"
                    placement="top"
                    width="200"
                    v-model="visible2">
                <p>这是一段内容这是一段内容确定删除吗？</p>
                <div style="text-align: right; margin: 0">
                    <el-button size="mini" type="mini" @click="visible2 = false">
                        取消
                    </el-button>
                    <el-button type="danger" size="mini" @click="visible2 = false;del()">
                        确定
                    </el-button>
                </div>
            </el-popover>

            <!-- 编辑 添加部分 -->
            <el-dialog title="字段信息"
                       :visible.sync="dialogFormVisible"
                       center="true"
                       append-to-body
                       width="600">
                <div style="text-align: left; margin: 0">
                    <el-form :model="ruleForm" :rules="
                    {
                    name: [
                        {required: true, message: '请输入字段名', trigger: 'change'}
                    ]
                    ,
                    javaName: [
                        {required: true, message: '请输入java类名', trigger: 'change'}
                    ]
                    ,
                    content: [
                        {required: true, message: '请输入描述', trigger: 'change'}
                    ]
                    ,
                    length: [
                        {required: true, message: '请输入长度', trigger: 'change'}
                    ]
                    ,
                    notNull: [
                        {required: true, message: '请输入是否为空', trigger: 'change'}
                    ]
                    ,
                    input: [
                        {required: true, message: '请输入input/radio/checkbox/select', trigger: 'change'}
                    ]
                    ,
                    seelectType: [
                        {required: true, message: '请输入1 模糊查询， 2 范围查询', trigger: 'change'}
                    ],
                    fieldType:[
                           {required: true, message: '请输入1 模糊查询， 2 范围查询', trigger: 'change'}
                    ]

                }" ref="ruleForm"
                             label-width="120px" label-position="left"
                             class="demo-ruleForm">

                        <el-form-item label="字段名" prop="name">
                            <el-input v-model="ruleForm.name"></el-input>
                        </el-form-item>
                        <el-form-item label="java类名" prop="javaName">
                            <el-input v-model="ruleForm.javaName"></el-input>
                        </el-form-item>
                        <el-form-item label="描述" prop="content">
                            <el-input v-model="ruleForm.content"></el-input>
                        </el-form-item>
                        <el-form-item label="长度" prop="length">
                            <el-input v-model="ruleForm.length"></el-input>
                        </el-form-item>
                        <el-form-item label="是否为空" prop="notNull">
                            <el-input v-model="ruleForm.notNull"></el-input>
                        </el-form-item>
                        <el-form-item label="字段类型" prop="fieldType">
                            <el-select v-model="ruleForm.fieldType"
                                       clearable
                                       placeholder="请选择"
                            >
                                <el-option
                                        v-for="item in selectData.fieldType"
                                        :key="item.id"
                                        :label="item.content"
                                        :value="item.id">
                                </el-option>
                            </el-select>
                        </el-form-item>
                        <el-form-item label="查询方式" prop="seelectType">
                            <el-select v-model="ruleForm.seelectType"
                                       clearable
                                       placeholder="请选择"
                            >
                                <el-option
                                        v-for="item in selectData.selectType"
                                        :key="item.id"
                                        :label="item.content"
                                        :value="item.id">
                                </el-option>
                            </el-select>
                        </el-form-item>
                        <el-form-item label="关联关系" prop="type">
                            <el-select v-model="ruleForm.type"
                                       clearable
                                       placeholder="请选择"
                            >
                                <el-option
                                        v-for="item in selectData.joinType"
                                        :key="item.id"
                                        :label="item.content"
                                        :value="item.id">
                                </el-option>
                            </el-select>
                        </el-form-item>
                        <div v-show="ruleForm.type == '1' || ruleForm.type == '2'">
                            <div v-show="ruleForm.type == '2'">
                                <el-form-item label="单对多时的关联表" prop="prTableId">
                                <el-select v-model="ruleForm.prTableId"
                                           clearable
                                           placeholder="请选择"
                                >
                                    <el-option
                                            v-for="item in selectData.tableDate"
                                            :key="item.id"
                                            :label="item.content+'('+item.tableName+')'"
                                            :value="item.id">
                                    </el-option>
                                </el-select>
                            </el-form-item>
                            </div>
                            <el-form-item label="数据源表" prop="dbTableId">
                                <el-select v-model="ruleForm.dbTableId"
                                           clearable
                                           placeholder="请选择"
                                           @change="dbTableClink"
                                >
                                    <el-option
                                            v-for="item in selectData.tableDate"
                                            :key="item.id"
                                            :label="item.content+'('+item.tableName+')'"
                                            :value="item.id">
                                    </el-option>
                                </el-select>
                            </el-form-item>
                            <el-form-item label="展示字段" prop="dbTableShowField">
                                <el-select v-model="ruleForm.dbTableShowFields"
                                           clearable
                                           multiple
                                           placeholder="请选择"
                                >
                                    <el-option
                                            v-for="item in selectData.fieldData"
                                            :key="item.id"
                                            :label="item.content+'('+item.name+')'"
                                            :value="item.id">
                                    </el-option>
                                </el-select>
                            </el-form-item>
                            <el-form-item label="分隔符" prop="dbTableShowFieldDelimiter">
                                <el-input v-model="ruleForm.dbTableShowFieldDelimiter"></el-input>
                            </el-form-item>
                        </div>

                        <el-form-item>
                            <el-button type="primary" @click="submitForm('ruleForm')">
                                提交
                            </el-button>
                            <el-button @click="resetForm('ruleForm')">
                                关闭
                            </el-button>
                        </el-form-item>
                    </el-form>
                </div>
            </el-dialog>
        </div>
        <!-- END 弹窗 -->

        <!-- BEGIN 搜索框 部分 -->
        <el-row :gutter="20">
            <el-col :span="24" offset="0">
                <el-collapse v-model="activeName" @change="handleChange">
                    <el-collapse-item name="1">
                        <template slot="title">
                            <i class="el-icon-search"></i> 搜索项
                        </template>
                        <el-form :inline="true" :model="formInline" class="el-form--inline" label-width="100px"
                                 label-position="right">

                            <el-col :span="12">
                                <el-form-item label="字段名">
                                    <el-input v-model="formInline.name" placeholder="字段名"></el-input>
                                </el-form-item>
                            </el-col>
                            <el-col :span="12">
                                <el-form-item label="">
                                    <el-input v-model="formInline.unName" placeholder="索引名（唯一索引）"></el-input>
                                </el-form-item>
                            </el-col>
                            <el-col :span="23">
                                <el-form-item style="float: right">
                                    <div class="block" style="float: right">
                                        <el-button type="primary" @click="onSubmit" size="small" icon="el-icon-search"
                                                   plain>搜索
                                        </el-button>
                                    </div>
                                </el-form-item>
                            </el-col>
                        </el-form>
                    </el-collapse-item>
                </el-collapse>
            </el-col>
        </el-row>
        <!-- END 搜索框 -->

        <!-- BEGIN table 部分 -->
        <el-row :gutter="20">
            <el-col :span="24" offset="0">
                <el-table
                        :data="tableData.items"
                        stripe="true"
                        ref="singleTable"
                        highlight-current-row
                        height="380"
                        @current-change="clink"
                        style="width: 100%">

                    <el-table-column type="expand">
                        <template slot-scope="props">
                            <el-form label-position="left" inline class="demo-table-expand">
                                <el-form-item label="字段名">
                                    <span>{{ props.row.name }}</span>
                                </el-form-item>
                                <el-form-item label="">
                                    <span>{{ props.row.unName }}</span>
                                </el-form-item>
                                <el-form-item label="java类名">
                                    <span>{{ props.row.javaName }}</span>
                                </el-form-item>
                                <el-form-item label="描述">
                                    <span>{{ props.row.content }}</span>
                                </el-form-item>
                                <el-form-item label="长度">
                                    <span>{{ props.row.length }}</span>
                                </el-form-item>
                                <el-form-item label="是否为空">
                                    <span>{{ props.row.notNull }}</span>
                                </el-form-item>
                                <el-form-item label="字段类型">
                                    <span>{{ props.row.fieldType }}</span>
                                </el-form-item>
                                <el-form-item label="1 单对单,2 单对多">
                                    <span>{{ props.row.type }}</span>
                                </el-form-item>
                                <el-form-item label="类型">
                                    <span>{{ props.row.input }}</span>
                                </el-form-item>
                                <%--<el-form-item label="单对多操作时（多对多模型） 不可为空。并确保关联字段存在">
                                    <span>{{ props.row.prTableId }}</span>
                                </el-form-item>--%>
                                <el-form-item label="数据源表">
                                    <span>{{ props.row.dbTableId }}</span>
                                </el-form-item>
                                <el-form-item label="text">
                                    <span>{{ props.row.dbTableShowField }}</span>
                                </el-form-item>
                                <el-form-item label="分隔符">
                                    <span>{{ props.row.dbTableShowFieldDelimiter }}</span>
                                </el-form-item>
                                <el-form-item label="1 模糊查询， 2 范围查询">
                                    <span>{{ props.row.seelectType }}</span>
                                </el-form-item>

                            </el-form>
                        </template>
                    </el-table-column> <!-- END 展开部分 -->

                    <el-table-column
                            prop="name"
                            label="字段名">
                    </el-table-column>
                    <el-table-column
                            prop="content"
                            label="描述">
                    </el-table-column>
                    <el-table-column
                            prop="length"
                            label="长度">
                    </el-table-column>
                    <el-table-column
                            prop="fieldType"
                            label="字段类型">
                    </el-table-column>
                    <el-table-column
                            prop="input"
                            label="表单类型">
                    </el-table-column>
                </el-table>
            </el-col>
        </el-row>
        <!-- END table -->

        <!-- BEGIN pagination 部分 -->
        <el-row :gutter="20">
            <el-col :span="24" offset="0">

                <!-- BEGIN 按钮区 -->
                <el-col :span="10">
                    <div class="block" style="">
                        <el-button icon="el-icon-edit" size="small" plain @click="update">编辑</el-button>
                        </el-button>
                    </div>
                </el-col>
                <!-- END 按钮区 -->

                <el-col :span="14">
                    <div class="block" style="float: right">
                        <el-pagination
                                @size-change="handleSizeChange"
                                @current-change="handleCurrentChange"
                                :current-page="tableData.curPage"
                                :page-sizes="[5, 10, 50, 100, 500]"
                                :page-size="tableData.pageSize"
                                layout="total, sizes, prev, pager, next, jumper"
                                :total="tableData.totalCount">
                        </el-pagination>
                    </div>
                </el-col> <!-- end 分页 -->
            </el-col>
        </el-row>
        <!-- END pagination -->
    </el-dialog>
</div>
<!-- END 字段 app -->

<script type="text/javascript">
    Vue.http.options.emulateJSON = true;
    var app = new Vue({
        el: '#app',
        data: {
            checkAll : false,
            isIndeterminate : false,
            visible6: false,
            visible2: false,
            inputLoad: false,
            checkedInputTypes:[],
            inputTypes: [
                'BEAN',
                'SERVICE',
                'CONTROLLER',
                'MAPPER',
                'HTML'
            ],
            selectData: {
                config: [],
                is: [{"content": "是", id: 1}, {content: "否", id: 0}],
                table: [],
                sonTable: [],
                sonField: []
            },
            inputTableData: {},
            ruleForm: {},
            dialogFormVisible: false, // 编辑表单 是否打开
            activeName: '',
            currentRow: {}, // 选中的数据
            formInline: { // 搜索框数据源
            },
            tableData: {
                items: [],// 分页数据集
                curPage: 1,
                pageSize: 10,
                totalPage: 1
            }
        },
        mounted: function () {
            this.$nextTick(function () { // 加载成功的回调
                try {
                    this.select().initSelect();
                    this.query();
                } catch (e) {
                    console.error(e);
                } finally {
                    loadOk();
                }
            });
        }
        ,
        methods: {
            go: function (obj) {
                if (this.checkedInputTypes == null || this.checkedInputTypes.length == 0) {
                    this.msgs().open3("先选择模块 （位于左下角，删除按钮右侧）")
                    return;
                }
                if (obj == null || !obj.id == null) {
                    this.msgs().open3("未选中")
                    return;
                }

                this.$http.post('o/tableInput/createFile/' + obj.id,
                    {
                        inputTypes:this.checkedInputTypes
                    }
                    ).then(function(res){
                    if (res.data.success){
                        this.msgs().open2("生成成功");
                    } else {
                        this.msgs().open6("遇到错误：" + res.msg);
                    }
                });
            },
            openFieldApp: function (obj) {
                this.currentRow = obj;
                if (this.currentRow == null || !this.currentRow.id == null) {
                    this.msgs().open3("未选中")
                    return;
                } else {
                    app2.tableName = this.currentRow.tableName;
                    app2.query();
                    app2.app = true;
                }
            },
            /***
             * select 相关方法
             * */
            select: function () {
                var t = this;
                return {
                    // 初始化下拉框数据
                    initSelect: function () {
                        t.$http.post('o/config/list').then(function (res) {
                            this.selectData.config = res.body.data;
                            console.log(res);
                        })
                        t.$http.post('o/table/list').then(function (res) {
                            this.selectData.sonTable = res.body.data;
                            app2.selectData.tableDate = res.body.data;
                            console.log(res);
                        })
                    },
                    initTableSelect: function () {
                        // 加载  表单
                        var configId = t.inputTableData.configId;
                        t.$http.post('o/tableInput/selectTable/' + configId).then(function (res) {
                            t.inputTableData.tableNames = [];
                            t.selectData.table = res.body.data;
                            console.log(res);
                        });
                    }
                }
            },
            tableClink: function () {
                this.selectData.sonField = this.ruleForm.fieldList;
            },
            configClink: function () {
                this.select().initTableSelect();
            },
            /**
             * 查询方法 会更改 tableData
             */
            query: function () {
                var params = JSON.parse(JSON.stringify(this.tableData));
                delete params.orderBy;
                delete params.items;
                params.param = this.formInline;
                // 开始查询
                this.$http.post('o/table/listByPage', params).then(function (res) {
                    this.tableData = res.body.data;
                    console.log(res);
                })
            }
            ,
            msgs: function (msg) {
                var tdl = this;
                return {
                    open1: function (msg) {
                        tdl.$message({
                            showClose: true,
                            message: msg || '这是一条消息提示'
                        });
                    },
                    open2: function (msg) {
                        tdl.$message({
                            showClose: true,
                            message: msg || '恭喜你，这是一条成功消息',
                            type: 'success'
                        });
                    },
                    open3: function (msg) {
                        tdl.$message({
                            showClose: true,
                            message: msg || '警告哦，这是一条警告消息',
                            type: 'warning'
                        });
                    },
                    open6: function (msg) {
                        tdl.$message({
                            showClose: true,
                            message: msg || '错了哦，这是一条错误消息',
                            type: 'error'
                        });
                    }
                }
            },
            handleSizeChange: function (val) { // 每页条数 变更时间
                this.tableData.pageSize = val;
                console.log('每页' + val);
                this.query(); // 刷新
            }
            ,
            handleCurrentChange: function (val) { // 跳页时间
                console.log('当前页: ' + val);
                this.tableData.curPage = val;
                this.query(); // 刷新
            }
            ,
            handleClick: function (row) {
                console.log(row);
            }
            ,
            onSubmit: function () {
                this.query(); // 刷新
                console.log('submit!');
            },
            /**
             * 单击事件
             * @param val
             */
            clink: function (val) {
                this.currentRow = val;
            },
            /**
             * 取消选中
             * @param row
             */
            setCurrent: function (row) {
                this.$refs.singleTable.setCurrentRow(row);
            },
            /**
             * 重置 表单 // 有bug 改为关闭 表单
             */
            resetForm: function (formName) {
                this.dialogFormVisible = false; // 打开窗口
            },
            /**
             * 提交
             */
            submitForm: function () {
                var t = this;
                this.$refs['ruleForm'].validate(function (valid) {
                    if (valid) {
                        var url = 'o/table/';
                        var params = t.ruleForm;
                        var isUpdate;
                        if (isUpdate = (params.id != null)) {
                            url += "/update/" + params.id; // 修改
                        } else {
                            url += "/insert/"; // 添加
                        }
                        delete params.configBean;
                        delete params.fieldList;
                        delete params.unFieldList;
                        delete params.prFieldBean;
                        delete params.onefieldList;
                        delete params.onefieldList;
                        t.$http.post(url, params)
                            .then(function (res) {
                                console.log(res);
                                try {
                                    if (res.body.success) {
                                        if (isUpdate)
                                        // 更新表单
                                            for (var key in params) {
                                                t.currentRow[key] = params[key];
                                            }
                                        else
                                            t.query(); // 刷新表单
                                        t.msgs().open2("提交成功");
                                        t.dialogFormVisible = false;
                                    } else {
                                        t.msgs().open6("提交失败" + res.body.msg)
                                    }
                                } catch (e) {
                                    console.error(e);
                                    t.msgs().open6("提交失败 遇到错误");
                                }
                            })
                    } else {
                        t.msgs().open6("验证不通过");
                        return false;
                    }
                });
            },
            update: function () {
                if (this.currentRow == null || this.currentRow.id == null) {
                    this.msgs().open3("未选中");
                    return;
                }
                this.ruleForm = JSON.parse(JSON.stringify(this.currentRow));
                ; // 赋值
                this.tableClink();
                this.dialogFormVisible = true; // 打开窗口
            },
            insert: function () {
                this.ruleForm = {}; // 清空
                this.dialogFormVisible = true; // 打开窗口
            },
            del: function () {
                this.currentRow;
                if (this.currentRow == null || this.currentRow.id == null) {
                    this.msgs().open3("未选中");
                    return;
                }
                this.$http.post("o/table/del/" + this.currentRow.id)
                    .then(function (res) {
                        console.log(res);
                        try {
                            if (res.body.success) {
                                this.query(); // 刷新表单
                                this.msgs().open2("提交成功");
                            } else {
                                this.msgs().open6("提交失败" + res.body.msg)
                            }
                        } catch (e) {
                            console.error(e);
                            this.msgs().open6("提交失败 遇到错误");
                        }
                    })
            }
            ,
            /**
             *
             */
            inputTable: function () {
                var configId = this.inputTableData.configId;
                var params = JSON.parse(JSON.stringify(this.inputTableData)); // 赋值
                delete params.configId;
                this.$http.post("o/tableInput/doInputs/" + configId, params)
                    .then(function (res) {
                        console.log(res);
                        try {
                            if (res.body.success) {
                                this.query(); // 刷新表单
                                this.msgs().open2("提交成功");
                                this.select().initSelect();
                                this.inputLoad = false;
                            } else {
                                this.msgs().open6("提交失败" + res.body.msg)
                            }
                        } catch (e) {
                            console.error(e);
                            this.msgs().open6("提交失败 遇到错误");
                        }
                    });
            }
        },
    })

    var app2 = new Vue({
        el: '#appFiled',
        data: {
            app: false,
            visible2: false,
            ruleForm: {},
            selectData: {
                fieldType: [
                    {id: "Integer", content: "Integer"},
                    {id: "String", content: "String"},
                    {id: "Date", content: "Date"},
                    {id: "Float", content: "Float"}
                ],
                tableDate: [],
                fieldData: [],
                selectType: [
                    {id: "1", content: "模糊查询"},
                    {id: "2", content: "范围查询"}
                ],
                joinType: [
                    {id: "1", content: "单对单"},
                    {id: "2", content: "单对多"}
                ]
            },
            dialogFormVisible: false, // 编辑表单 是否打开
            activeName: '',
            currentRow: {}, // 选中的数据
            formInline: { // 搜索框数据源
            },
            tableData: {
                items: [],// 分页数据集
                curPage: 1,
                pageSize: 10,
                totalPage: 1
            }
        },
        mounted: function () {
            this.$nextTick(function () { // 加载成功的回调
                try {
                    this.query();
                } catch (e) {
                    console.error(e);
                } finally {
                    loadOk();
                }
            });
        }
        ,
        methods: {
            /**
             * 查询方法 会更改 tableData
             */
            query: function () {
                var params = JSON.parse(JSON.stringify(this.tableData));
                delete params.orderBy;
                delete params.items;
                params.param = this.formInline;
                params.param.tableId = app.currentRow.id;
                // 开始查询
                this.$http.post('o/field/listByPage', params).then(function (res) {
                    this.tableData = res.body.data;
                    this.tableData.items.forEach(function (t) {
                        app2.machiningBean(t);
                    })
                })
            },
            dbTableClink: function () {
                this.$http.post('o/field/list', {tableId : this.ruleForm.dbTableId}).then(
                    function(res){
                        this.selectData.fieldData = res.body.data;
                    }
                )
            },
            machiningBean: function (bean) {
                // 进行数据加工处理
                bean.dbTableShowFields = [];
                var dbTableShowField = bean.dbTableShowField;
                if (dbTableShowField != null) {
                    bean.dbTableShowFields = dbTableShowField.split(",");
                }
            }
            ,
            msgs: function (msg) {
                var tdl = this;
                return {
                    open1: function (msg) {
                        tdl.$message({
                            showClose: true,
                            message: msg || '这是一条消息提示'
                        });
                    },
                    open2: function (msg) {
                        tdl.$message({
                            showClose: true,
                            message: msg || '恭喜你，这是一条成功消息',
                            type: 'success'
                        });
                    },
                    open3: function (msg) {
                        tdl.$message({
                            showClose: true,
                            message: msg || '警告哦，这是一条警告消息',
                            type: 'warning'
                        });
                    },
                    open6: function (msg) {
                        tdl.$message({
                            showClose: true,
                            message: msg || '错了哦，这是一条错误消息',
                            type: 'error'
                        });
                    }
                }
            },
            handleSizeChange: function (val) { // 每页条数 变更时间
                this.tableData.pageSize = val;
                console.log('每页' + val);
                this.query(); // 刷新
            }
            ,
            handleCurrentChange: function (val) { // 跳页时间
                console.log('当前页: ' + val);
                this.tableData.curPage = val;
                this.query(); // 刷新
            }
            ,
            handleClick: function (row) {
                console.log(row);
            }
            ,
            onSubmit: function () {
                this.query(); // 刷新
                console.log('submit!');
            },
            /**
             * 单击事件
             * @param val
             */
            clink: function (val) {
                this.currentRow = val;
            },
            /**
             * 取消选中
             * @param row
             */
            setCurrent: function (row) {
                this.$refs.singleTable.setCurrentRow(row);
            },
            /**
             * 重置 表单 // 有bug 改为关闭 表单
             */
            resetForm: function (formName) {
                this.dialogFormVisible = false; // 打开窗口
            },
            /**
             * 提交
             */
            submitForm: function () {
                var t = this;
                this.$refs['ruleForm'].validate(function (valid) {
                    if (valid) {
                        var url = 'o/field/';
                        var params = t.ruleForm;
                        delete params.dbTableShowField;
                        delete params.tableBean;
                        delete params.dbTableBean;
                        delete params.prTableBean;
                        delete params.showTableFieldList;
                        var isUpdate;
                        if (isUpdate = (params.id != null)) {
                            url += "/update/" + params.id; // 修改
                        } else {
                            url += "/insert/"; // 添加
                        }
                        t.$http.post(url, params)
                            .then(function (res) {
                                console.log(res);
                                try {
                                    if (res.body.success) {
                                        if (isUpdate)
                                        // 更新表单
                                            for (var key in params) {
                                                t.currentRow[key] = params[key];
                                            }
                                        else
                                            t.query(); // 刷新表单
                                        t.msgs().open2("提交成功");
                                        t.dialogFormVisible = false;
                                    } else {
                                        t.msgs().open6("提交失败" + res.body.msg)
                                    }
                                } catch (e) {
                                    console.error(e);
                                    t.msgs().open6("提交失败 遇到错误");
                                }
                            })
                    } else {
                        t.msgs().open6("验证不通过");
                        return false;
                    }
                });
            },
            update: function () {
                if (this.currentRow == null || this.currentRow.id == null) {
                    this.msgs().open3("未选中");
                    return;
                }
                this.ruleForm = JSON.parse(JSON.stringify(this.currentRow));
                ; // 赋值
                this.dbTableClink();
                this.dialogFormVisible = true; // 打开窗口
            },
            insert: function () {
                this.ruleForm = {}; // 清空
                this.dialogFormVisible = true; // 打开窗口
            },
            del: function () {
                this.currentRow;
                if (this.currentRow == null || this.currentRow.id == null) {
                    this.msgs().open3("未选中");
                    return;
                }
                this.$http.post("o/field/del/" + this.currentRow.id)
                    .then(function (res) {
                        console.log(res);
                        try {
                            if (res.body.success) {
                                this.query(); // 刷新表单
                                this.msgs().open2("提交成功");
                            } else {
                                this.msgs().open6("提交失败" + res.body.msg)
                            }
                        } catch (e) {
                            console.error(e);
                            this.msgs().open6("提交失败 遇到错误");
                        }
                    })
            }
        },
    })

</script>
</body>
</html>
