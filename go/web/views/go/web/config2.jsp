<%--
  Created by IntelliJ IDEA.
  User: yangchao
  Date: 2017/12/14
  Time: 16:54
  To change this template use File | Settings | File Templates.
--%>
<%@include file="../../file.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!-- 一个 单页的 单对单 模型 -->
<html>
<head>
    <title>配置</title>

    <%-- <link type="text/css" href="plug-in\bootstarp\css\bootstrap.css" rel="stylesheet">
     <script src="plug-in\bootstarp\js\bootstrap.js"></script>--%>

    <!-- 引入样式 -->
    <script src="plug-in\vue\vue.js"></script>
    <script src="plug-in\vue\vue-resourec.js"></script>
    <script src="plug-in\jquery\jquery.js"></script>

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
        <el-dialog title="收货地址"
                   :visible.sync="dialogFormVisible"
                   center="true"
                   width="600">
            <div style="text-align: left; margin: 0">
                <el-form :model="ruleForm" :rules="
                {
                basicPath: [
                    {required: true, message: '请输入', trigger: 'change'}
                ],
                srcPath: [
                    {required: true, message: '请输入', trigger: 'change'}
                ],
                htmlPath: [
                    {required: true, message: '请输入', trigger: 'change'}
                ],
                dbUrl: [
                    {required: true, message: '请输入', trigger: 'change'}
                ],
                dbUserName: [
                    {required: true, message: '请输数据库账号名称', trigger: 'blur'},
                    {min: 3, max: 5, message: '长度在 3 到 5 个字符', trigger: 'blur'}
                ],
                dbUserPassword: [
                    {required: true, message: '请输入数据库密码', trigger: 'change'}
                ]
            }" ref="ruleForm"
                         label-width="120px" label-position="left"
                         class="demo-ruleForm">
                    <el-form-item label="项目基础路径" prop="basicPath">
                        <el-input v-model="ruleForm.basicPath"></el-input>
                    </el-form-item>
                    <el-form-item label="后端代码路径" prop="srcPath">
                        <el-input v-model="ruleForm.srcPath"></el-input>
                    </el-form-item>
                    <el-form-item label="html路径" prop="htmlPath">
                        <el-input v-model="ruleForm.htmlPath"></el-input>
                    </el-form-item>
                    <el-form-item label="jdbc url" prop="dbUrl">
                        <el-input v-model="ruleForm.dbUrl"></el-input>
                    </el-form-item>
                    <el-form-item label="用户名" prop="dbUserName">
                        <el-input v-model="ruleForm.dbUserName"></el-input>
                    </el-form-item>
                    <el-form-item label="用户密码" prop="dbUserPassword">
                        <el-input v-model="ruleForm.dbUserPassword"></el-input>
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
        <el-col :span="22" offset="1">
            <el-collapse v-model="activeName" @change="handleChange">
                <el-collapse-item name="1">
                    <template slot="title">
                        <i class="el-icon-search"></i> 搜索项
                    </template>
                    <el-form :inline="true" :model="formInline" class="el-form--inline">
                        <el-col :span="8">
                            <el-form-item label="项目路径">
                                <el-input v-model="formInline.basicPath" placeholder="项目路径"></el-input>
                            </el-form-item>
                        </el-col>
                        <el-col :span="8">
                            <el-form-item label="项目路径">
                                <el-input v-model="formInline.basicPath" placeholder="项目路径"></el-input>
                            </el-form-item>
                        </el-col>
                        <el-col :span="8">
                            <el-form-item label="项目路径">
                                <el-input v-model="formInline.basicPath" placeholder="项目路径"></el-input>
                            </el-form-item>
                        </el-col>
                        <el-col :span="8">
                            <el-form-item label="项目路径">
                                <el-input v-model="formInline.basicPath" placeholder="项目路径"></el-input>
                            </el-form-item>
                        </el-col>
                        <el-col :span="8">
                            <el-form-item label="项目路径">
                                <el-input v-model="formInline.basicPath" placeholder="项目路径"></el-input>
                            </el-form-item>
                        </el-col>
                        <el-col :span="22">
                            <div class="block" style="float: left">
                                <p></p>
                            </div>
                        </el-col>
                        <el-col :span="2">
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
        <el-col :span="22" offset="1">
            <el-table
                    :data="tableData.items"
                    stripe="true"
                    ref="singleTable"
                    highlight-current-row
                    height="550"
                    @current-change="clink"
                    style="width: 100%">
                <el-table-column
                        fixed
                        type="index"
                        width="50">
                </el-table-column>
                <el-table-column
                        prop="basicPath"
                        label="项目根目录">
                </el-table-column>
                <el-table-column
                        prop="srcPath"
                        label="后端路径"
                >
                </el-table-column>
                <el-table-column
                        prop="htmlPath"
                        label="html路径"
                        width="120">
                </el-table-column>
                <el-table-column
                        prop="address"
                        label="地 址">
                </el-table-column>
                <el-table-column
                        prop="createDate"
                        label="邮十多分dfdsa 编">
                </el-table-column>
                <el-table-column
                        prop="updateDate"
                        label="邮编">
                </el-table-column>
                <%-- <el-table-column
                         fixed="right"
                         label="操作"
                         width="150">
                     <template slot-scope="scope">
                         <el-button @click="handleClick(scope.row)" type="danger" size="mini">编辑</el-button>
                         <el-button type="danger" size="mini">删除</el-button>
                     </template>
                 </el-table-column>--%>
            </el-table>
        </el-col>
    </el-row>
    <!-- END table -->

    <!-- BEGIN pagination 部分 -->
    <el-row :gutter="20">
        <el-col :span="22" offset="1">

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
                basicPath: '',
                srcPath: '',
                htmlPath: '',
                dbUrl: '',
                dbUserPassword: '',
                dbUserName: ''
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
                this.$http.post('o/config/listByPage', params).then(function (res) {
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
                        var url = 'o/config/';
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
                                            t.currentRow = params; // 更新表单
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
                this.$http.post("o/config/del/" + this.currentRow.id)
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
