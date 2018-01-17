<%--
  Created by IntelliJ IDEA.
  User: jr
  Date: 2017/12/11
  Time: 13:51
  To change this template use File | Settings | File Templates.
  egpp 后端
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>HELLO</title>
        <link type="text/css" href="views/egpp/bootstrap/css/bootstrap.min.css" rel="stylesheet">
        <link type="text/css" href="views/egpp/bootstrap/css/bootstrap-responsive.min.css" rel="stylesheet">
        <link type="text/css" href="views/egpp/css/theme.css" rel="stylesheet">
        <link type="text/css" href="views/egpp/images/icons/css/font-awesome.css" rel="stylesheet">
        <%--<link type="text/css" href='http://fonts.googleapis.com/css?family=Open+Sans:400italic,600italic,400,600'
              rel='stylesheet'>--%>
    </head>
<body style="height: 100%">
<div class="navbar navbar-fixed-top">
    <div class="navbar-inner">
        <div class="container">
            <a class="btn btn-navbar" data-toggle="collapse" data-target=".navbar-inverse-collapse">
                <i class="icon-reorder shaded"></i></a><a class="brand" href="#">Edmin </a>
            <div class="nav-collapse collapse navbar-inverse-collapse">

                <ul class="nav pull-right">
                    <li class="nav-user dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown">
                        <img src="views/egpp/images/user.png" class="nav-avatar"/>
                        <b class="caret"></b></a>
                        <ul class="dropdown-menu">
                            <li><a href="#">个人资料</a></li>
                            <li class="divider"></li>
                            <li><a href="#">退出登录</a></li>
                        </ul>
                    </li>
                </ul>
            </div>
            <!-- /.nav-collapse -->
        </div>
    </div>
    <!-- /navbar-inner -->
</div>
<!-- /navbar -->
<div class="wrapper">
    <div class="container">
        <div class="row">
            <div class="span3">
                <div class="sidebar">

                    <ul class="widget widget-menu unstyled">
                        <li><a class="collapsed" data-toggle="collapse" href="#togglePages"><i
                                class="menu-icon icon-cog">
                        </i><i class="icon-chevron-down pull-right"></i><i class="icon-chevron-up pull-right">
                        </i>JRGO 代码生成器</a>
                            <ul id="togglePages" class="collapse unstyled">
                                <li><a href="#"><i class="menu-icon"></i>GO</a></li>
                            </ul>
                        </li>
                        <li><a href="#"><i class="menu-icon icon-signout"></i>退出登录 </a></li>
                    </ul>

                </div>
                <!--/.sidebar-->
            </div>
            <!--/.span3-->
            <div class="span9">
                <div class="content">
                    <iframe id="iframepage" src="http://127.0.0.1:8080/jr/index#" width="100%" height="" frameborder="no" border="0"                                        marginwidth="0"
                            marginheight="0" scrolling="no" allowtransparency="yes"></iframe>
                </div>
                <!--/.content-->
            </div>
            <!--/.span9-->
        </div>
    </div>
    <!--/.container-->
</div>
<!--/.wrapper-->

<script src="views/egpp/scripts/jquery-1.9.1.min.js" type="text/javascript"></script>
<script src="views/egpp/scripts/jquery-ui-1.10.1.custom.min.js" type="text/javascript"></script>
<script src="views/egpp/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
<script src="views/egpp/scripts/flot/jquery.flot.js" type="text/javascript"></script>
<script src="views/egpp/scripts/flot/jquery.flot.resize.js" type="text/javascript"></script>
<script src="views/egpp/scripts/datatables/jquery.dataTables.js" type="text/javascript"></script>
<script src="views/egpp/scripts/common.js" type="text/javascript"></script>
<script src="plug-in/index/index/index.js" type="text/javascript"></script>

</body>
