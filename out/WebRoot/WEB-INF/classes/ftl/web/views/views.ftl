<%--
  Created by IntelliJ IDEA.
  User: yangchao
  Date: 2017/12/14
  Time: 16:54
  To change this template use File | Settings | File Templates.
--%>
<%@include file="/views/file.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!-- 一个 单页的 单对单 ${table.content} -->
<html>
<head>
    <title>${table.content}</title>

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

        <!-- 编辑 添加部分 -->
        <el-dialog title="${table.content}"
                   :visible.sync="dialogFormVisible"
                   center="true"
                   width="600">
            <div style="text-align: left; margin: 0">
                <el-form :model="ruleForm" :rules="
                {
                <#-- 验证非空 -->
                <#if fields??>
                    <#list fields as field>
                        <#if field.notNull == '1'>
                            <#if field.javaName != 'id'>
                ${field.javaName}: [
                    {required: true, message: '请输入${field.content}', trigger: 'change'}
                ]
                ,
                            </#if>
                        </#if>
                    </#list>
                </#if>
            }" ref="ruleForm"
                         label-width="120px" label-position="left"
                         class="demo-ruleForm">
                <#-- 编辑部分的 input 们 -->
                <#list fields as field>
                    <#if
                    field.javaName != 'id'
                    && field.javaName != 'createDate'
                    && field.javaName != 'createBy'
                    && field.javaName != 'updateBy'
                    && field.javaName != 'updateDate'
                    >
                        <#if field.type?? && field.dbTableBean??>
                        <#--如果为 单对单 ： 暂时只处理 单对单-->
                            <#assign titlem = ""/><#--标题-->
                            <#assign titlefgf = "-"/><#--分隔符-->
                            <#if field.dbTableShowFieldDelimiter??>
                                <#assign titlefgf = field.dbTableShowFieldDelimiter/>
                            </#if>
                            <#if field.showTableFieldList??>
                                <#list field.showTableFieldList as fieldm>
                                    <#assign titlem = titlem + "item." + fieldm.javaName/>
                                    <#if fieldm_has_next>
                                        <#assign titlem = titlem + "+'" + titlefgf + "'+"/>
                                    </#if>
                                </#list>
                            <#else >
                                <#assign titlem = "item.name"/>
                            </#if>
                            <el-form-item
                                    label="${field.content}"
                                    prop="${field.javaName}">
                                <el-select
                                        v-model="ruleForm.${field.javaName}"
                                        placeholder="请选择"
                                        @change="${field.javaName}Clink">
                                    <el-option
                                            v-for="item in selectData.${field.dbTableBean.name}"
                                            :key="item.id"
                                            :label="${titlem}"
                                            :value="item.id">
                                    </el-option>
                                </el-select>
                            </el-form-item>
                        <#else >
                            <el-form-item
                                    label="${field.content}"
                                    prop="${field.javaName}">
                                <el-input
                                        v-model="ruleForm.${field.javaName}">
                                </el-input>
                            </el-form-item>
                        </#if>
                    </#if>
                </#list>
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
                    <el-form :inline="true" :model="formInline" class="el-form--inline"  label-width="100px" label-position="right">

                    <#list fields as field>
                        <#if
                        field.javaName != 'id' &&
                        field.javaName != 'createDate' &&
                        field.javaName != 'createBy' &&
                        field.javaName != 'updateBy' &&
                        field.javaName != 'updateDate'>
                            <#if field.type?? && field.dbTableBean??>
                            <#--暂时只处理 单对单-->
                                <#assign titlem = ""/><#--标题-->
                                <#assign titlefgf = "-"/><#--分隔符-->
                                <#if field.dbTableShowFieldDelimiter??>
                                    <#assign titlefgf = field.dbTableShowFieldDelimiter/>
                                </#if>
                                <#if field.showTableFieldList??>
                                    <#list field.showTableFieldList as fieldm>
                                        <#assign titlem = titlem + "item." + fieldm.javaName/>
                                        <#if fieldm_has_next>
                                            <#assign titlem = titlem + "+'" + titlefgf + "'+"/>
                                        </#if>
                                    </#list>
                                <#else >
                                    <#assign titlem = "item.name"/>
                                </#if>
                                <el-col :span="12">
                                    <el-form-item
                                            label="${field.content}"
                                            prop="${field.javaName}">
                                        <el-select
                                                v-model="formInline.${field.javaName}"
                                                clearable
                                                placeholder="请选择"
                                        >
                                            <el-option
                                                    v-for="item in selectData.${field.dbTableBean.name}"
                                                    :key="item.id"
                                                    :label="${titlem}"
                                                    :value="item.id">
                                            </el-option>
                                        </el-select>
                                    </el-form-item>
                                </el-col>
                            <#else >
                                <el-col :span="12">
                                    <el-form-item label="${field.content}">
                                        <el-input
                                                v-model="formInline.${field.javaName}"
                                                placeholder="${field.content}"></el-input>
                                    </el-form-item>
                                </el-col>
                            </#if>
                        </#if>
                    </#list>

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

                <!-- BEGIN 展开部分 -->
                <el-table-column type="expand">
                    <template slot-scope="props">
                        <el-form label-position="left" inline class="demo-table-expand">
                        <#list fields as field>
                            <#if
                            field.javaName != 'id' &&
                            field.javaName != 'createBy' &&
                            field.javaName != 'updateBy'
                            >
                                <#if field.type?? && field.dbTableBean??>
                                <#-- 单对单-->
                                    <#assign titlem = ""/><#--标题-->
                                    <#assign titlefgf = "-"/><#--分隔符-->
                                    <#if field.dbTableShowFieldDelimiter??>
                                        <#assign titlefgf = field.dbTableShowFieldDelimiter/>
                                    </#if>
                                    <#if field.showTableFieldList??>
                                        <#list field.showTableFieldList as fieldm>
                                            <#assign titlem = titlem + "props.row." + field.javaName?replace("Id", "") + 'Bean.' + fieldm.javaName/>
                                            <#if fieldm_has_next>
                                                <#assign titlem = titlem + "+'" + titlefgf + "'+"/>
                                            </#if>
                                        </#list>
                                    <#else >
                                        <#assign titlem = "props.row.title"/>
                                    </#if>
                                    <el-form-item label="${field.content}">
                                        <span>{{ ${titlem} }}</span>
                                    </el-form-item>
                                <#else >
                                    <el-form-item label="${field.content}">
                                        <span>{{ props.row.${field.javaName} }}</span>
                                    </el-form-item>
                                </#if>

                            </#if>
                        </#list>

                        </el-form>
                    </template>
                </el-table-column>
                <!-- END 展开部分 -->

            <#list fields as field>
                <#if
                field.javaName != 'id'
                && field.javaName != 'createDate'
                && field.javaName != 'createBy'
                && field.javaName != 'updateBy'
                && field.javaName != 'updateDate'
                >
                    <#if field.type?? && field.dbTableBean??>
                    <#-- 单对单-->
                        <#assign titlem = ""/><#--标题-->
                        <#assign titlefgf = "-"/><#--分隔符-->
                        <#if field.dbTableShowFieldDelimiter??>
                            <#assign titlefgf = field.dbTableShowFieldDelimiter/>
                        </#if>
                        <#if field.showTableFieldList??>
                            <#list field.showTableFieldList as fieldm>
                                <#assign titlem = titlem + "" + field.javaName?replace("Id", "") + 'Bean.' + fieldm.javaName/>
                                <#if fieldm_has_next>
                                    <#assign titlem = titlem + "+'" + titlefgf + "'+"/>
                                </#if>
                            </#list>
                        <#else >
                            <#assign titlem = "title"/>
                        </#if>
                        <el-table-column
                                prop="${titlem}"
                                label="${field.content}">
                        </el-table-column>
                    <#else > <#-- 普通 td -->
                        <el-table-column
                            <#if field.javaName == 'createDate' || field.javaName == 'updateDate'>
                                    width="195"
                            </#if>
                                    prop="${field.javaName}"
                                    label="${field.content}">
                        </el-table-column>
                    </#if>
                </#if>
            </#list>

            <#if sonTable?? && sonTable?size != 0>
                <el-table-column
                        fixed="right"
                        label="操作"
                        width="100">
                    <template slot-scope="scope">
                        <#list sonTable as stable>
                            <el-button @click="open${stable.name}App(scope.row)" type="text" size="small">查看${stable.content}</el-button>
                        </#list>
                    </template>
                </el-table-column>
            </#if>

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
                    <el-button icon="el-icon-circle-plus" size="small" plain @click="insert">添加</el-button>
                    <el-button icon="el-icon-edit" size="small" plain @click="update">修改</el-button>
                    <el-button type="success" icon="el-icon-delete" size="small" plain v-popover:popover5>删除</el-button>
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
<script type="text/javascript">
    Vue.http.options.emulateJSON = true;
    var url = "o/${table.name}/";
    var app = new Vue({
        el: '#app',
        data: {
            visible2: false,
            inputLoad: false,
            selectData: {
    <#assign tests = "|"/>
    <#list oneFields as field>
        <#if !tests?contains("|"+field.dbTableBean.name+"|")>
            <#assign tests = tests + field.dbTableBean.name + "|">
                ${field.dbTableBean.name}:[],
                ${field.dbTableBean.name}Index:{},
        </#if>
    </#list>
                text:[]
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
        },
        methods: {
        <#list sonTable as tablem>
            open${tablem.name}App: function (obj) {
                this.currentRow = obj;
                if (this.currentRow == null || !this.currentRow.id == null) {
                    this.msgs().open3("未选中")
                    return;
                } else {
                    ${tablem.name}App.tableName = this.currentRow.name || '${tablem.content}'; // 将name作为 子窗体的标题
                    ${tablem.name}App.formInline.${tablem.prFieldBean.javaName} = obj.id;
                    ${tablem.name}App.prBean = obj;
                    ${tablem.name}App.query();
                    ${tablem.name}App.app = true;
                }
            },
        </#list>
            /***
             * select 相关方法
             * */
            select: function () {
                var t = this;
                return {
                    // 初始化下拉框数据
                    initSelect: function () {
                    <#assign tests = "|"/>
                    <#list oneFields as field>
                        <#if !tests?contains("|"+field.dbTableBean.name+"|")>
                            t.$http.post("o/${field.dbTableBean.name}/list").then(function (res) {
                                <#assign tests = tests + field.dbTableBean.name + "|">
                                t.selectData.${field.dbTableBean.name} = res.body.data;
                                t.selectData.${field.dbTableBean.name}.forEach(function (v) {
                                    t.selectData.${field.dbTableBean.name}Index[v.id] = v;
                                })
                            });
                        </#if>
                    </#list>
                    }
                }
            },
        <#list oneFields as field>
            ${field.javaName}Clink: function (id) {
                console.log("点击事件" + id);
            },
        </#list>
            /**
             * 查询方法 会更改 tableData
             */
            query: function () {
                var params = JSON.parse(JSON.stringify(this.tableData));
                delete params.orderBy;
                delete params.items;
                params.param = this.formInline;
                // 开始查询
                this.$http.post(url + 'listByPage', params).then(function (res) {
                    this.tableData = res.body.data;
                    console.log(res);
                })
            },
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
            },
            handleCurrentChange: function (val) { // 跳页时间
                console.log('当前页: ' + val);
                this.tableData.curPage = val;
                this.query(); // 刷新
            },
            handleClick: function (row) {
                console.log(row);
            },
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
                        var params = t.ruleForm;
                        var isUpdate;
                        var myUrl;
                        if (isUpdate = (params.id != null)) {
                            myUrl = url + "update/" + params.id; // 修改
                        } else {
                            myUrl = url + "insert/"; // 添加
                        }
                    <#list oneFields as field>
                        delete params.${field.javaName?replace("Id", "")}Bean;
                    </#list>

                        t.$http.post(myUrl, params)
                                .then(function (res) {
                                    console.log(res);
                                    try {
                                        if (res.body.success) {
                                            if (isUpdate) {
                                                // 更新表单
                                                for (var key in params) {
                                                    t.currentRow[key] = params[key];
                                                }
                                            <#list oneFields as field>
                                                t.currentRow.${field.javaName?replace("Id", "")}Bean = this.selectData.${field.dbTableBean.name}Index[t.currentRow.${field.javaName}];
                                            </#list>
                                            } else
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
                this.$http.post(url + "del/" + this.currentRow.id)
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

<#list sonTable as stable>
    <#assign table = stable/>
<div id="${table.name}App" style="">

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
            <el-dialog
                    append-to-body
                    title="${table.content}"
                    :visible.sync="dialogFormVisible"
                    center="true"
                    width="600">
                <div style="text-align: left; margin: 0">
                    <el-form :model="ruleForm" :rules="
                {
                <#-- 验证非空 -->
                <#if fields??>
                    <#list fields as field>
                        <#if field.notNull == '1'>
                            <#if field.javaName != 'id'>
                ${field.javaName}: [
                    {required: true, message: '请输入${field.content}', trigger: 'change'}
                ],
                            </#if>
                        </#if>
                    </#list>
                </#if>
            }" ref="ruleForm"
                             label-width="120px" label-position="left"
                             class="demo-ruleForm">
                    <#-- 编辑部分的 input 们 -->
                        <#list fields as field>
                            <#if
                            field.javaName != 'id'
                            && field.javaName != 'createDate'
                            && field.javaName != 'createBy'
                            && field.javaName != 'updateBy'
                            && field.javaName != 'updateDate'
                            >
                                <#if field.type?? && field.dbTableBean??>
                                <#--如果为 单对单 ： 暂时只处理 单对单-->
                                    <#assign titlem = ""/><#--标题-->
                                    <#assign titlefgf = "-"/><#--分隔符-->
                                    <#if field.dbTableShowFieldDelimiter??>
                                        <#assign titlefgf = field.dbTableShowFieldDelimiter/>
                                    </#if>
                                    <#if field.showTableFieldList??>
                                        <#list field.showTableFieldList as fieldm>
                                            <#assign titlem = titlem + "item." + fieldm.javaName/>
                                            <#if fieldm_has_next>
                                                <#assign titlem = titlem + "+'" + titlefgf + "'+"/>
                                            </#if>
                                        </#list>
                                    <#else >
                                        <#assign titlem = "item.name"/>
                                    </#if>
                                    <el-form-item
                                            label="${field.content}"
                                            prop="${field.javaName}">
                                        <el-select
                                                v-model="ruleForm.${field.javaName}"
                                                placeholder="请选择"
                                                @change="${field.javaName}Clink">
                                            <el-option
                                                    v-for="item in selectData.${field.dbTableBean.name}"
                                                    :key="item.id"
                                                    :label="${titlem}"
                                                    :value="item.id">
                                            </el-option>
                                        </el-select>
                                    </el-form-item>
                                <#else >
                                    <el-form-item
                                            label="${field.content}"
                                            prop="${field.javaName}">
                                        <el-input
                                                v-model="ruleForm.${field.javaName}">
                                        </el-input>
                                    </el-form-item>
                                </#if>
                            </#if>
                        </#list>
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
                        <el-form :inline="true" :model="formInline" class="el-form--inline"  label-width="100px" label-position="right">

                            <#list fields as field>
                                <#if
                                field.javaName != 'id' &&
                                field.javaName != 'createDate' &&
                                field.javaName != 'createBy' &&
                                field.javaName != 'updateBy' &&
                                field.javaName != 'updateDate'>
                                    <#if field.type?? && field.dbTableBean??>
                                    <#--暂时只处理 单对单-->
                                        <#assign titlem = ""/><#--标题-->
                                        <#assign titlefgf = "-"/><#--分隔符-->
                                        <#if field.dbTableShowFieldDelimiter??>
                                            <#assign titlefgf = field.dbTableShowFieldDelimiter/>
                                        </#if>
                                        <#if field.showTableFieldList??>
                                            <#list field.showTableFieldList as fieldm>
                                                <#assign titlem = titlem + "item." + fieldm.javaName/>
                                                <#if fieldm_has_next>
                                                    <#assign titlem = titlem + "+'" + titlefgf + "'+"/>
                                                </#if>
                                            </#list>
                                        <#else >
                                            <#assign titlem = "item.name"/>
                                        </#if>
                                        <el-col :span="12">
                                            <el-form-item
                                                    label="${field.content}"
                                                    prop="${field.javaName}">
                                                <el-select
                                                        v-model="formInline.${field.javaName}"
                                                        clearable
                                                        placeholder="请选择"
                                                >
                                                    <el-option
                                                            v-for="item in selectData.${field.dbTableBean.name}"
                                                            :key="item.id"
                                                            :label="${titlem}"
                                                            :value="item.id">
                                                    </el-option>
                                                </el-select>
                                            </el-form-item>
                                        </el-col>
                                    <#else >
                                        <el-col :span="12">
                                            <el-form-item label="${field.content}">
                                                <el-input
                                                        v-model="formInline.${field.javaName}"
                                                        placeholder="${field.content}"></el-input>
                                            </el-form-item>
                                        </el-col>
                                    </#if>
                                </#if>
                            </#list>

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

                    <!-- BEGIN 展开部分 -->
                    <el-table-column type="expand">
                        <template slot-scope="props">
                            <el-form label-position="left" inline class="demo-table-expand">
                                <#list fields as field>
                                    <#if
                                    field.javaName != 'id' &&
                                    field.javaName != 'createBy' &&
                                    field.javaName != 'updateBy'
                                    >
                                        <#if field.type?? && field.dbTableBean??>
                                        <#-- 单对单-->
                                            <#assign titlem = ""/><#--标题-->
                                            <#assign titlefgf = "-"/><#--分隔符-->
                                            <#if field.dbTableShowFieldDelimiter??>
                                                <#assign titlefgf = field.dbTableShowFieldDelimiter/>
                                            </#if>
                                            <#if field.showTableFieldList??>
                                                <#list field.showTableFieldList as fieldm>
                                                    <#assign titlem = titlem + "props.row." + field.javaName?replace("Id", "") + 'Bean.' + fieldm.javaName/>
                                                    <#if fieldm_has_next>
                                                        <#assign titlem = titlem + "+'" + titlefgf + "'+"/>
                                                    </#if>
                                                </#list>
                                            <#else >
                                                <#assign titlem = "props.row.title"/>
                                            </#if>
                                            <el-form-item label="${field.content}">
                                                <span>{{ ${titlem} }}</span>
                                            </el-form-item>
                                        <#else >
                                            <el-form-item label="${field.content}">
                                                <span>{{ props.row.${field.javaName} }}</span>
                                            </el-form-item>
                                        </#if>

                                    </#if>
                                </#list>

                            </el-form>
                        </template>
                    </el-table-column>
                    <!-- END 展开部分 -->

                    <#list fields as field>
                        <#if
                        field.javaName != 'id'
                        && field.javaName != 'createDate'
                        && field.javaName != 'createBy'
                        && field.javaName != 'updateBy'
                        && field.javaName != 'updateDate'
                        >
                            <#if field.type?? && field.dbTableBean??>
                            <#-- 单对单-->
                                <#assign titlem = ""/><#--标题-->
                                <#assign titlefgf = "-"/><#--分隔符-->
                                <#if field.dbTableShowFieldDelimiter??>
                                    <#assign titlefgf = field.dbTableShowFieldDelimiter/>
                                </#if>
                                <#if field.showTableFieldList??>
                                    <#list field.showTableFieldList as fieldm>
                                        <#assign titlem = titlem + "" + field.javaName?replace("Id", "") + 'Bean.' + fieldm.javaName/>
                                        <#if fieldm_has_next>
                                            <#assign titlem = titlem + "+'" + titlefgf + "'+"/>
                                        </#if>
                                    </#list>
                                <#else >
                                    <#assign titlem = "title"/>
                                </#if>
                                <el-table-column
                                        prop="${titlem}"
                                        label="${field.content}">
                                </el-table-column>
                            <#else > <#-- 普通 td -->
                                <el-table-column
                                    <#if field.javaName == 'createDate' || field.javaName == 'updateDate'>
                                            width="195"
                                    </#if>
                                            prop="${field.javaName}"
                                            label="${field.content}">
                                </el-table-column>
                            </#if>
                        </#if>
                    </#list>

                    <#if sonTable?? && sonTable?size != 0>
                        <el-table-column
                                fixed="right"
                                label="操作"
                                width="100">
                            <template slot-scope="scope">
                                <#list sonTable as stable>
                                    <el-button @click="open${stable.name}App(scope.row)" type="text" size="small">查看${stable.content}</el-button>
                                </#list>
                            </template>
                        </el-table-column>
                    </#if>

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
                        <el-button icon="el-icon-circle-plus" size="small" plain @click="insert">添加</el-button>
                        <el-button icon="el-icon-edit" size="small" plain @click="update">修改</el-button>
                        <el-button type="success" icon="el-icon-delete" size="small" plain v-popover:popover5>删除</el-button>
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

<script type="text/javascript">
    var ${table.name}App = new Vue({
        el: '#${table.name}App',
        data: {
            app: false,
            visible2: false,
            tableName: '',
            inputLoad: false,
            selectData: {
        <#assign tests = "|"/>
        <#list oneFields as field>
            <#if !tests?contains("|"+field.dbTableBean.name+"|")>
                <#assign tests = tests + field.dbTableBean.name + "|">
            ${field.dbTableBean.name}:[],
                    ${field.dbTableBean.name}Index:{},
            </#if>
        </#list>
                text:[]
            },
            inputTableData: {
            },
            ruleForm: {
            },
            dialogFormVisible: false, // 编辑表单 是否打开
            currentRow:'',
            activeName:{}, // 选中的数据
            formInline: { // 搜索框数据源
            },
            tableData: {
                items: [],// 分页数据集
                curPage:1,
                pageSize: 10,
                totalPage :1
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
        },
        methods: {
            /***
             * select 相关方法
             * */
            select: function () {
                var t = this;
                return {
                    // 初始化下拉框数据
                    initSelect: function () {
                        <#assign tests = "|"/>
                        <#list oneFields as field>
                            <#if !tests?contains("|"+field.dbTableBean.name+"|")>
                                t.$http.post("o/${field.dbTableBean.name}/list").then(function (res) {
                                    <#assign tests = tests + field.dbTableBean.name + "|">
                                    t.selectData.${field.dbTableBean.name} = res.body.data;
                                    t.selectData.${field.dbTableBean.name}.forEach(function (v) {
                                        t.selectData.${field.dbTableBean.name}Index[v.id] = v;
                                    })
                                });
                            </#if>
                        </#list>
                    }
                }
            },
        <#list oneFields as field>
            ${field.javaName}Clink: function (id) {
                console.log("点击事件" + id);
            },
        </#list>
            /**
             * 查询方法 会更改 tableData
             */
            query: function () {
                var params = JSON.parse(JSON.stringify(this.tableData));
                delete params.orderBy;
                delete params.items;
                params.param = this.formInline;
                // 开始查询
                this.$http.post('o/${table.name}/listByPage', params).then(function (res) {
                    this.tableData = res.body.data;
                    console.log(res);
                })
            },
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
            },
            handleCurrentChange: function (val) { // 跳页时间
                console.log('当前页: ' + val);
                this.tableData.curPage = val;
                this.query(); // 刷新
            },
            handleClick: function (row) {
                console.log(row);
            },
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
                        var mayUrl = 'o/${table.name}/';
                        var params = t.ruleForm;
                        var isUpdate;
                        if (isUpdate = (params.id != null)) {
                            mayUrl += "/update/" + params.id; // 修改
                        } else {
                            mayUrl += "/insert/"; // 添加
                        }
                        <#list oneFields as field>
                            delete params.${field.javaName?replace("Id", "")}Bean;
                        </#list>
                        params.${table.prFieldBean.javaName} = t.prBean.id;
                        t.$http.post(mayUrl, params)
                                .then(function (res) {
                                    console.log(res);
                                    try {
                                        if (res.body.success) {
                                            if (isUpdate) {
                                                // 更新表单
                                                for (var key in params) {
                                                    t.currentRow[key] = params[key];
                                                }
                                                <#list oneFields as field>
                                                    t.currentRow.${field.javaName?replace("Id", "")}Bean = this.selectData.${field.dbTableBean.name}Index[t.currentRow.${field.javaName}];
                                                </#list>
                                            } else
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
                                }
                        )
            },
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
                                            this.inputLoad = false;
                                        } else {
                                            this.msgs().open6("提交失败" + res.body.msg)
                                        }
                                    } catch (e) {
                                        console.error(e);
                                        this.msgs().open6("提交失败 遇到错误");
                                    }
                                }
                        );
            }
        }
    })
</script>


</#list>
</body>
</html>
