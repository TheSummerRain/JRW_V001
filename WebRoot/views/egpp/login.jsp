<%--
  Created by IntelliJ IDEA.
  User: jr
  Date: 2017/12/11
  Time: 13:51
  To change this template use File | Settings | File Templates.
  egpp 后端
--%>
<%@include file="/views/file.jsp"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>登录</title>
    <link type="text/css" href="views/egpp/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link type="text/css" href="views/egpp/bootstrap/css/bootstrap-responsive.min.css" rel="stylesheet">
    <link type="text/css" href="views/egpp/css/theme.css" rel="stylesheet">
    <link type="text/css" href="views/egpp/images/icons/css/font-awesome.css" rel="stylesheet">
    <%--<link type="text/css" href='http://fonts.googleapis.com/css?family=Open+Sans:400italic,600italic,400,600'
          rel='stylesheet'>--%>
</head>
<body>

<div class="navbar navbar-fixed-top">
    <div class="navbar-inner">
        <div class="container">
            <a class="btn btn-navbar" data-toggle="collapse" data-target=".navbar-inverse-collapse">
                <i class="icon-reorder shaded"></i>
            </a>

            <a class="brand" href="index.html">
                JR系统
            </a>

            <div class="nav-collapse collapse navbar-inverse-collapse">

                <ul class="nav pull-right">

                    <li><a href="#">
                        注册
                    </a></li>



                   <%-- <li><a href="#">
                        忘记密码
                    </a></li>--%>
                </ul>
            </div><!-- /.nav-collapse -->
        </div>
    </div><!-- /navbar-inner -->
</div><!-- /navbar -->



<div class="wrapper" id="app">
    <div class="container">
        <div class="row">
            <div class="module module-login span4 offset4">
                <form class="form-vertical">
                    <div class="module-head">
                        <h3>登录</h3>
                    </div>
                    <div class="module-body">
                        <div class="control-group">
                            <div class="controls row-fluid">
                                <input class="span12" type="text" v-model="fromData.loginName" id="inputEmail" placeholder="Username">
                            </div>
                        </div>
                        <div class="control-group">
                            <div class="controls row-fluid">
                                <input class="span12" type="password" v-model="fromData.password" id="inputPassword" placeholder="Password">
                            </div>
                        </div>
                    </div>
                    <div class="module-foot">
                        <div class="control-group">
                            <div class="controls clearfix">
                                <button type="button" class="btn btn-primary pull-right" @click="submitForm">登录</button>
                                <label class="checkbox">
                                    <input type="checkbox"> 记住密码
                                </label>
                            </div>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div><!--/.wrapper-->

<div class="footer">
    <div class="container">
    </div>
</div>
<script src="views/egpp/scripts/jquery-ui-1.10.1.custom.min.js" type="text/javascript"></script>
<script src="views/egpp/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
<script src="views/egpp/scripts/flot/jquery.flot.js" type="text/javascript"></script>
<script src="views/egpp/scripts/flot/jquery.flot.resize.js" type="text/javascript"></script>
<script src="views/egpp/scripts/datatables/jquery.dataTables.js" type="text/javascript"></script>
<script src="views/egpp/scripts/common.js" type="text/javascript"></script>

<script src="plug-in\vue\vue.js"></script>
<script src="plug-in\vue\vue-resourec.js"></script>
<!-- 引入组件库 -->
<link rel="stylesheet" href="plug-in/elm/css/index.css">
<script src="plug-in/elm/js/index.js"></script>
<script>
    Vue.http.options.emulateJSON = true;
    var app =  new Vue({
        el: '#app',
        data: {
            fromData:{}
        },
        mounted: function () {
            this.$nextTick(function () { // 加载成功的回调
                try {
                } catch (e) {
                    console.error(e);
                } finally {
                    loadOk();
                }
            });
        }
        ,
        methods: {
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
            /**
             * 提交
             */
            submitForm: function () {
                var t = this;
                var url = 'o/account/login';
                var params = t.fromData;
                t.$http.post(url, params)
                    .then(function (res) {
                        console.log(res);
                        try {
                            if (res.body.success) {
                                t.msgs().open2("提交成功");
                                window.location.href = 'egpp&index';
                            } else {
                                t.msgs().open6("提交失败 " + res.body.msg)
                            }
                        } catch (e) {
                            console.error(e);
                            t.msgs().open6("提交失败 遇到错误");
                        }
                    })
            }
        }
    })
</script>
</body>
