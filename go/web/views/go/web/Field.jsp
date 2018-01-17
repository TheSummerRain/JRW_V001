<%--
  Created by IntelliJ IDEA.
  User: yangchao
  Date: 2017/12/14
  Time: 16:54
  To change this template use File | Settings | File Templates.
--%>
<%@include file="/views/file.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!-- 一个 单页的 单对单 字段信息 -->
<html>
<head>
    <title>字段信息</title>

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
        <el-dialog title="字段信息"
                   :visible.sync="dialogFormVisible"
                   center="true"
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
                tableId: [
                    {required: true, message: '请输入表id', trigger: 'change'}
                ]
                ,
                input: [
                    {required: true, message: '请输入input/radio/checkbox/select', trigger: 'change'}
                ]
                ,
                seelectType: [
                    {required: true, message: '请输入1 模糊查询， 2 范围查询', trigger: 'change'}
                ]
                ,
            }" ref="ruleForm"
                         label-width="120px" label-position="left"
                         class="demo-ruleForm">

                    <el-form-item label="字段名" prop="name">
                        <el-input v-model="ruleForm.name"></el-input>
                    </el-form-item>
                    <el-form-item label="" prop="unName">
                        <el-input v-model="ruleForm.unName"></el-input>
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
                    <el-form-item label="表id" prop="tableId">
                        <el-input v-model="ruleForm.tableId"></el-input>
                    </el-form-item>
                    <el-form-item label="字段类型" prop="fieldType">
                        <el-input v-model="ruleForm.fieldType"></el-input>
                    </el-form-item>
                    <el-form-item label="1 单对单,2 单对多" prop="type">
                        <el-input v-model="ruleForm.type"></el-input>
                    </el-form-item>
                    <el-form-item label="单对多操作时（多对多模型） 不可为空。并确保关联字段存在" prop="prTableId">
                        <el-input v-model="ruleForm.prTableId"></el-input>
                    </el-form-item>
                    <el-form-item label="数据源表" prop="dbTableId">
                        <el-input v-model="ruleForm.dbTableId"></el-input>
                    </el-form-item>
                    <el-form-item label="数据源表展示字段 字段逗号分隔" prop="dbTableShowField">
                        <el-input v-model="ruleForm.dbTableShowField"></el-input>
                    </el-form-item>
                    <el-form-item label="分隔符 缺省 -" prop="dbTableShowFieldDelimiter">
                        <el-input v-model="ruleForm.dbTableShowFieldDelimiter"></el-input>
                    </el-form-item>
                    <el-form-item label="input/radio/checkbox/select" prop="input">
                        <el-input v-model="ruleForm.input"></el-input>
                    </el-form-item>
                    <el-form-item label="1 模糊查询， 2 范围查询" prop="seelectType">
                        <el-input v-model="ruleForm.seelectType"></el-input>
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
                    <el-form :inline="true" :model="formInline" class="el-form--inline"  label-width="100px" label-position="right">

                        <el-col :span="12">
                            <el-form-item label="字段名">
                                <el-input v-model="formInline.name" placeholder="字段名"></el-input>
                            </el-form-item>
                        </el-col>
                        <el-col :span="12">
                            <el-form-item label="">
                                <el-input v-model="formInline.unName" placeholder=""></el-input>
                            </el-form-item>
                        </el-col>
                        <el-col :span="12">
                            <el-form-item label="java类名">
                                <el-input v-model="formInline.javaName" placeholder="java类名"></el-input>
                            </el-form-item>
                        </el-col>
                        <el-col :span="12">
                            <el-form-item label="描述">
                                <el-input v-model="formInline.content" placeholder="描述"></el-input>
                            </el-form-item>
                        </el-col>
                        <el-col :span="12">
                            <el-form-item label="长度">
                                <el-input v-model="formInline.length" placeholder="长度"></el-input>
                            </el-form-item>
                        </el-col>
                        <el-col :span="12">
                            <el-form-item label="是否为空">
                                <el-input v-model="formInline.notNull" placeholder="是否为空"></el-input>
                            </el-form-item>
                        </el-col>
                        <el-col :span="12">
                            <el-form-item label="表id">
                                <el-input v-model="formInline.tableId" placeholder="表id"></el-input>
                            </el-form-item>
                        </el-col>
                        <el-col :span="12">
                            <el-form-item label="字段类型">
                                <el-input v-model="formInline.fieldType" placeholder="字段类型"></el-input>
                            </el-form-item>
                        </el-col>
                        <el-col :span="12">
                            <el-form-item label="1 单对单,2 单对多">
                                <el-input v-model="formInline.type" placeholder="1 单对单,2 单对多"></el-input>
                            </el-form-item>
                        </el-col>
                        <el-col :span="12">
                            <el-form-item label="单对多操作时（多对多模型） 不可为空。并确保关联字段存在">
                                <el-input v-model="formInline.prTableId" placeholder="单对多操作时（多对多模型） 不可为空。并确保关联字段存在"></el-input>
                            </el-form-item>
                        </el-col>
                        <el-col :span="12">
                            <el-form-item label="数据源表">
                                <el-input v-model="formInline.dbTableId" placeholder="数据源表"></el-input>
                            </el-form-item>
                        </el-col>
                        <el-col :span="12">
                            <el-form-item label="数据源表展示字段 字段逗号分隔">
                                <el-input v-model="formInline.dbTableShowField" placeholder="数据源表展示字段 字段逗号分隔"></el-input>
                            </el-form-item>
                        </el-col>
                        <el-col :span="12">
                            <el-form-item label="分隔符 缺省 -">
                                <el-input v-model="formInline.dbTableShowFieldDelimiter" placeholder="分隔符 缺省 -"></el-input>
                            </el-form-item>
                        </el-col>
                        <el-col :span="12">
                            <el-form-item label="input/radio/checkbox/select">
                                <el-input v-model="formInline.input" placeholder="input/radio/checkbox/select"></el-input>
                            </el-form-item>
                        </el-col>
                        <el-col :span="12">
                            <el-form-item label="1 模糊查询， 2 范围查询">
                                <el-input v-model="formInline.seelectType" placeholder="1 模糊查询， 2 范围查询"></el-input>
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
                                <el-form-item label="表id">
                                    <span>{{ props.row.tableId }}</span>
                                </el-form-item>
                                <el-form-item label="字段类型">
                                    <span>{{ props.row.fieldType }}</span>
                                </el-form-item>
                                <el-form-item label="1 单对单,2 单对多">
                                    <span>{{ props.row.type }}</span>
                                </el-form-item>
                                <el-form-item label="单对多操作时（多对多模型） 不可为空。并确保关联字段存在">
                                    <span>{{ props.row.prTableId }}</span>
                                </el-form-item>
                                <el-form-item label="数据源表">
                                    <span>{{ props.row.dbTableId }}</span>
                                </el-form-item>
                                <el-form-item label="数据源表展示字段 字段逗号分隔">
                                    <span>{{ props.row.dbTableShowField }}</span>
                                </el-form-item>
                                <el-form-item label="分隔符 缺省 -">
                                    <span>{{ props.row.dbTableShowFieldDelimiter }}</span>
                                </el-form-item>
                                <el-form-item label="input/radio/checkbox/select">
                                    <span>{{ props.row.input }}</span>
                                </el-form-item>
                                <el-form-item label="1 模糊查询， 2 范围查询">
                                    <span>{{ props.row.seelectType }}</span>
                                </el-form-item>
                                <el-form-item label="">
                                    <span>{{ props.row.createDate }}</span>
                                </el-form-item>
                                <el-form-item label="">
                                    <span>{{ props.row.updateDate }}</span>
                                </el-form-item>

                        </el-form>
                    </template>
                </el-table-column> <!-- END 展开部分 -->

                    <el-table-column
                            prop="name"
                            label="字段名">
                    </el-table-column>
                    <el-table-column
                            prop="unName"
                            label="">
                    </el-table-column>
                    <el-table-column
                            prop="javaName"
                            label="java类名">
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
                            prop="notNull"
                            label="是否为空">
                    </el-table-column>
                    <el-table-column
                            prop="tableId"
                            label="表id">
                    </el-table-column>
                    <el-table-column
                            prop="fieldType"
                            label="字段类型">
                    </el-table-column>
                    <el-table-column
                            prop="type"
                            label="1 单对单,2 单对多">
                    </el-table-column>
                    <el-table-column
                            prop="prTableId"
                            label="单对多操作时（多对多模型） 不可为空。并确保关联字段存在">
                    </el-table-column>
                    <el-table-column
                            prop="dbTableId"
                            label="数据源表">
                    </el-table-column>
                    <el-table-column
                            prop="dbTableShowField"
                            label="数据源表展示字段 字段逗号分隔">
                    </el-table-column>
                    <el-table-column
                            prop="dbTableShowFieldDelimiter"
                            label="分隔符 缺省 -">
                    </el-table-column>
                    <el-table-column
                            prop="input"
                            label="input/radio/checkbox/select">
                    </el-table-column>
                    <el-table-column
                            prop="seelectType"
                            label="1 模糊查询， 2 范围查询">
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

                    <el-button @click="setCurrent([])" size="small" plain>取消选择</el-button>
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
    var app = new Vue({
        el: '#app',
        data: {
            visible2: false,
            ruleForm: {
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
            query: function() {
                var params = JSON.parse(JSON.stringify(this.tableData));
                delete params.orderBy;
                delete params.items;
                params.param = this.formInline;
                // 开始查询
                this.$http.post('o/field/listByPage', params).then(function (res) {
                    this.tableData = res.body.data;
                    console.log(res);
                })
            }
            ,
            msgs: function(msg) {
                var tdl = this;
                return {
                    open1: function(msg) {
                        tdl.$message({
                            showClose: true,
                            message: msg||'这是一条消息提示'
                        });
                    },
                    open2: function(msg) {
                        tdl.$message({
                            showClose: true,
                            message: msg||'恭喜你，这是一条成功消息',
                            type: 'success'
                        });
                    },
                    open3: function(msg) {
                        tdl.$message({
                            showClose: true,
                            message: msg||'警告哦，这是一条警告消息',
                            type: 'warning'
                        });
                    },
                    open6: function(msg) {
                        tdl.$message({
                            showClose: true,
                            message: msg||'错了哦，这是一条错误消息',
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
                if (this.currentRow == null || this.currentRow.id == null){
                    this.msgs().open3("未选中");
                    return;
                }
                this.ruleForm = JSON.parse(JSON.stringify(this.currentRow));; // 赋值
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
