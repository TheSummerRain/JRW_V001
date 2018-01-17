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

</head>
<body style="overflow-x: hidden;width: 100%">

<div id="app">
    <el-form ref="form" :model="form" label-width="80px">
        <el-form-item label="活动名称">
            <el-input v-model="form.name"></el-input>
        </el-form-item>
        <el-form-item label="活动区域">
            <el-select v-model="form.region" placeholder="请选择活动区域">
                <el-option label="区域一" value="shanghai"></el-option>
                <el-option label="区域二" value="beijing"></el-option>
            </el-select>
        </el-form-item>
        <el-form-item label="活动时间">
            <el-col :span="11">
                <el-date-picker type="date" placeholder="选择日期" v-model="form.date1" style="width: 100%;"></el-date-picker>
            </el-col>
            <el-col class="line" :span="2">-</el-col>
            <el-col :span="11">
                <el-time-picker type="fixed-time" placeholder="选择时间" v-model="form.date2" style="width: 100%;"></el-time-picker>
            </el-col>
        </el-form-item>
        <el-form-item label="即时配送">
            <el-switch v-model="form.delivery"></el-switch>
        </el-form-item>
        <el-form-item label="活动性质">
            <el-checkbox-group v-model="form.type">
                <el-checkbox label="美食/餐厅线上活动" name="type"></el-checkbox>
                <el-checkbox label="地推活动" name="type"></el-checkbox>
                <el-checkbox label="线下主题活动" name="type"></el-checkbox>
                <el-checkbox label="单纯品牌曝光" name="type"></el-checkbox>
            </el-checkbox-group>
        </el-form-item>
        <el-form-item label="特殊资源">
            <el-radio-group v-model="form.resource">
                <el-radio label="线上品牌商赞助"></el-radio>
                <el-radio label="线下场地免费"></el-radio>
            </el-radio-group>
        </el-form-item>
        <el-form-item label="活动形式">
            <el-input type="textarea" v-model="form.desc"></el-input>
        </el-form-item>
        <el-form-item>
            <el-button type="primary" @click="onSubmit">立即创建</el-button>
            <el-button>取消</el-button>
        </el-form-item>
    </el-form>
</div>
<script type="text/javascript">
    Vue.http.options.emulateJSON = true;
    var app = new Vue({
        el: '#app',
        data: {
            form: {
                name: '',
                region: '',
                date1: '',
                date2: '',
                delivery: true,
                type: [],
                resource: '',
                desc: ''
            },
            rules: {
                name: [
                    { required: true, message: '请输入活动名称', trigger: 'blur' },
                    { min: 3, max: 5, message: '长度在 3 到 5 个字符', trigger: 'blur' }
                ],
                region: [
                    { required: true, message: '请选择活动区域', trigger: 'change' }
                ],
                date1: [
                    { type: 'date', required: true, message: '请选择日期', trigger: 'change' }
                ],
                date2: [
                    { type: 'date', required: true, message: '请选择时间', trigger: 'change' }
                ],
                type: [
                    { type: 'array', required: true, message: '请至少选择一个活动性质', trigger: 'change' }
                ],
                resource: [
                    { required: true, message: '请选择活动资源', trigger: 'change' }
                ],
                desc: [
                    { required: true, message: '请填写活动形式', trigger: 'blur' }
                ]
            }
        },
        mounted: function () {
            loadOk();
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
                // 开始查询
                this.$http.post('o/config/listByPage', params).then(function (res) {
                    this.tableData = res.body.data;
                    console.log(res);
                })
            }
            ,
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
            handleCurrentChange: function(val) {
                this.currentRow = val;
            },
            /**
             * 取消选中
             * @param row
             */
            setCurrent: function(row) {
                this.$refs.singleTable.setCurrentRow(row);
            },
        }

    })
</script>
</body>
</html>
