<%@include file="/views/file.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8"/>
    <title>控制台 - Bootstrap后台管理系统模版Ace下载</title>
    <meta name="keywords" content="Bootstrap模版,Bootstrap模版下载,Bootstrap教程,Bootstrap中文"/>
    <meta name="description" content="站长素材提供Bootstrap模版,Bootstrap教程,Bootstrap中文翻译等相关Bootstrap插件下载"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <!-- basic styles -->

    <!-- 引入样式 -->
    <script src="plug-in\vue\vue.js"></script>
    <script src="plug-in\vue\vue-resourec.js"></script>

    <link rel="stylesheet" href="plug-in/elm/css/index.css">
    <script src="plug-in/elm/js/index.js"></script>
    <link href="views/ace/assets/css/bootstrap.min.css" rel="stylesheet"/>

    <link rel="stylesheet" href="views/ace/assets/css/font-awesome.min.css"/>

    <!--[if IE 7]>
    <link rel="stylesheet" href="views/ace/assets/css/font-awesome-ie7.min.css"/>
    <![endif]-->

    <!-- page specific plugin styles -->

    <!-- fonts -->

    <link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Open+Sans:400,300"/>

    <!-- ace styles -->

    <link rel="stylesheet" href="views/ace/assets/css/ace.min.css"/>
    <link rel="stylesheet" href="views/ace/assets/css/ace-rtl.min.css"/>
    <link rel="stylesheet" href="views/ace/assets/css/ace-skins.min.css"/>

    <!--[if lte IE 8]>
    <link rel="stylesheet" href="views/ace/assets/css/ace-ie.min.css"/>
    <![endif]-->

    <!-- inline styles related to this page -->

    <!-- ace settings handler -->

    <script src="views/ace/assets/js/ace-extra.min.js"></script>

    <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->

    <!--[if lt IE 9]>
    <script src="views/ace/assets/js/html5shiv.js"></script>
    <script src="views/ace/assets/js/respond.min.js"></script>
    <![endif]-->

    <script src="plug-in\jquery\jquery.js" type="text/javascript"></script>
</head>
<style>
    body {
        overflow-x: hidden !important;
        overflow-y: auto !important;
    }

    body::-webkit-scrollbar-track {
        -webkit-box-shadow: inset 0 0 6px rgba(187, 189, 174, 0.3);
        background-color: #F5F5F5;
    }

    body::-webkit-scrollbar {
        width: 6px;
        background-color: #fafafa;
    }

    body::-webkit-scrollbar-thumb {
        background-color: #5faeff;
    }

    div::-webkit-scrollbar-track {
        -webkit-box-shadow: inset 0 0 6px rgba(0, 0, 0, 0.3);
        background-color: #F5F5F5;
    }

    div::-webkit-scrollbar {
        width: 6px;
        background-color: #fafafa;
    }

    div::-webkit-scrollbar-thumb {
        background-color: #5faeff;
    }
</style>
<body>
<div id="app" style="display: none">
    <div class="navbar navbar-default">
        <script type="text/javascript">
            try {
                ace.settings.check('navbar', 'fixed')
            } catch (e) {
            }
        </script>

        <div class="navbar-container" id="navbar-container">
            <div class="navbar-header pull-left">
                <a href="#" class="navbar-brand">
                    <small>
                        <i class="icon-leaf"></i>
                        ACE后台管理系统
                    </small>
                </a><!-- /.brand -->
            </div><!-- /.navbar-header -->

            <div class="navbar-header pull-right" role="navigation">
                <ul class="nav ace-nav" style="height: 45px">
                    <li class="light-blue">
                        <a data-toggle="dropdown" href="#" class="dropdown-toggle">
								<span class="user-info">
									<small>欢迎光临,</small>
									Jason
								</span>

                            <i class="icon-caret-down"></i>
                        </a>

                        <ul class="user-menu pull-right dropdown-menu dropdown-yellow dropdown-caret dropdown-close">
                            <li>
                                <a href="#" @click="out">
                                    <i class="icon-off"></i>
                                    退出
                                </a>
                            </li>
                        </ul>
                    </li>
                </ul><!-- /.ace-nav -->
            </div><!-- /.navbar-header -->
        </div><!-- /.container -->
    </div>

    <div class="main-container" id="main-container">
        <script type="text/javascript">
            try {
                ace.settings.check('main-container', 'fixed')
            } catch (e) {
            }
        </script>

        <div class="main-container-inner">
            <a class="menu-toggler" id="menu-toggler" href="#">
                <span class="menu-text"></span>
            </a>

            <div class="sidebar" id="sidebar">
                <script type="text/javascript">
                    try {
                        ace.settings.check('sidebar', 'fixed')
                    } catch (e) {
                    }
                </script>

                <div class="sidebar-shortcuts" id="sidebar-shortcuts">
                    <div class="sidebar-shortcuts-large" id="sidebar-shortcuts-large">
                        <button class="btn btn-success">
                            <i class="icon-signal"></i>
                        </button>

                        <button class="btn btn-info">
                            <i class="icon-pencil"></i>
                        </button>

                        <button class="btn btn-warning">
                            <i class="icon-group"></i>
                        </button>

                        <button class="btn btn-danger">
                            <i class="icon-cogs"></i>
                        </button>
                    </div>

                    <div class="sidebar-shortcuts-mini" id="sidebar-shortcuts-mini">
                        <span class="btn btn-success"></span>

                        <span class="btn btn-info"></span>

                        <span class="btn btn-warning"></span>

                        <span class="btn btn-danger"></span>
                    </div>
                </div><!-- #sidebar-shortcuts -->

                <ul class="nav nav-list">
                    <li class="active">
                        <a href="#">
                            <i class="icon-dashboard"></i>
                            <span class="menu-text"> 控制台 </span>
                        </a>
                    </li>

                    <li v-for="m in menu">
                        <a href="#" class="dropdown-toggle">
                            <i class="icon-desktop"></i>
                            <span class="menu-text"> {{m.text}}</span>

                            <b class="arrow icon-angle-down"></b>
                        </a>

                        <ul class="submenu">
                            <li  v-for="i in m.menu">
                                <a href="#" @click="addTab(i)">
                                    <i :class="i.ioc"></i>
                                    {{i.text}}
                                </a>
                            </li>
                        </ul><!-- /.nav-list -->
                    </li>
                </ul>

                <div class="sidebar-collapse" id="sidebar-collapse">
                    <i class="icon-double-angle-left" data-icon1="icon-double-angle-left"
                       data-icon2="icon-double-angle-right"></i>
                </div>

                <script type="text/javascript">
                    try {
                        ace.settings.check('sidebar', 'collapsed')
                    } catch (e) {
                    }
                </script>

            </div>

            <div class="main-content" style="" >
                <el-tabs type="border-card" v-model="editableTabsValue2" type="card" closable @tab-remove="removeTab">
                    <el-tab-pane
                            v-for="(item, index) in editableTabs2"
                            :key="item.name"
                            :label="item.text"
                            :name="item.name"
                    >
                        <div style="width: 99%;margin-left: auto;margin-right: auto;">

                            <iframe name="iframepage" v-bind:src="item.href" width="100%" height="600" frameborder="no"
                                    border="0" marginwidth="0"
                                    marginheight="0" scrolling="no" allowtransparency="yes"></iframe>
                        </div>
                    </el-tab-pane>
                </el-tabs>
                <%--



                --%>
            </div><!-- /.main-content -->
            <script>
                var app = new Vue({
                    el: '#app',
                    data: {
                        menu: [
                            {
                                text: "JR",
                                ioc: "",
                                href: "#",
                                menu: [
                                    {
                                        text: "数据源配置",
                                        ioc: "",
                                        name: '2',
                                        href: "go&web&config"
                                    }, {
                                        text: "表管理",
                                        ioc: "",
                                        name: '4',
                                        href: "go&web&Table"
                                    }, {
                                        text: "账号管理",
                                        ioc: "",
                                        name: '5',
                                        href: "login&Account"
                                    }, {
                                        text: "账号类型",
                                        ioc: "",
                                        name: '6',
                                        href: "login&AccountType"
                                    },
                                ]
                            }
                        ],
                        editableTabsValue2: '0',
                        editableTabs2: [{
                            text: '首页',
                            name: '0',
                            href: 'hello'
                        }],
                        tabIndex: 1
                    },
                    mounted: function () {
                        this.$nextTick(function () { // 加载成功的回调
                            try {
                                checkSession();
                            } catch (e) {
                                console.error(e);
                            } finally {
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
                        out: function () {
                          this.$http.post("o/account/out", {})
                              .then(function (res) {
                                  console.log(res);
                                  try {
                                      if (res.body.success) {
                                          this.msgs().open2("提交成功");
                                          window.location.href = window.location.href.split("&")[0] + '&login';
                                      } else {
                                          this.msgs().open6("提交失败 " + res.body.msg)
                                      }
                                  } catch (e) {
                                      console.error(e);
                                      this.msgs().open6("提交失败 遇到错误");
                                  }
                              })
                        },
                        addTab: function (i) {
                            checkSession();
                            var tabs = this.editableTabs2;
                            var isExist = false;
                            var iName = i.name;
                            tabs.forEach(function (tab, index) {
                                if (tab.name == iName) {
                                    isExist = true;
                                }
                            });
                            if (!isExist) {
                                this.editableTabs2.push(i);
                            }
                            this.editableTabsValue2 = iName;
                        },
                        removeTab: function (targetName) {
                            var tabs = this.editableTabs2;
                            if (targetName == '0')
                                return;
                            var activeName = this.editableTabsValue2;
                            var i;
                            if (activeName == targetName) {
                                tabs.forEach(function (tab, index) {
                                    if (tab.name == targetName) {
                                        i = index;
                                        var nextTab = tabs[index + 1] || tabs[index - 1];
                                        if (nextTab) {
                                            activeName = nextTab.name;
                                        }
                                    }
                                });
                            }
                            this.editableTabsValue2 = activeName;
                            var n = tabs.filter(
                                function (tab) {
                                    return tab.name != targetName
                                }
                            );
                            this.editableTabs2 = n;
                        }
                    }
                })
            </script>
            <div class="ace-settings-container" id="ace-settings-container">
                <div class="btn btn-app btn-xs btn-warning ace-settings-btn" id="ace-settings-btn">
                    <i class="icon-cog bigger-150"></i>
                </div>

                <div class="ace-settings-box" id="ace-settings-box">
                    <div>
                        <div class="pull-left">
                            <select id="skin-colorpicker" class="hide">
                                <option data-skin="default" value="#438EB9">#438EB9</option>
                                <option data-skin="skin-1" value="#222A2D">#222A2D</option>
                                <option data-skin="skin-2" value="#C6487E">#C6487E</option>
                                <option data-skin="skin-3" value="#D0D0D0">#D0D0D0</option>
                            </select>
                        </div>
                        <span>&nbsp; 选择皮肤</span>
                    </div>

                    <div>
                        <input type="checkbox" class="ace ace-checkbox-2" id="ace-settings-navbar"/>
                        <label class="lbl" for="ace-settings-navbar"> 固定导航条</label>
                    </div>

                    <div>
                        <input type="checkbox" class="ace ace-checkbox-2" id="ace-settings-sidebar"/>
                        <label class="lbl" for="ace-settings-sidebar"> 固定滑动条</label>
                    </div>

                    <div>
                        <input type="checkbox" class="ace ace-checkbox-2" id="ace-settings-breadcrumbs"/>
                        <label class="lbl" for="ace-settings-breadcrumbs">固定面包屑</label>
                    </div>

                    <div>
                        <input type="checkbox" class="ace ace-checkbox-2" id="ace-settings-rtl"/>
                        <label class="lbl" for="ace-settings-rtl">切换到左边</label>
                    </div>

                    <div>
                        <input type="checkbox" class="ace ace-checkbox-2" id="ace-settings-add-container"/>
                        <label class="lbl" for="ace-settings-add-container">
                            切换窄屏
                            <b></b>
                        </label>
                    </div>
                </div>
            </div><!-- /#ace-settings-container -->
        </div><!-- /.main-container-inner -->

        <a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse">
            <i class="icon-double-angle-up icon-only bigger-110"></i>
        </a>
    </div><!-- /.main-container -->
</div>
<!-- basic scripts -->


<!-- <![endif]-->

<![endif]-->

<script type="text/javascript">
    if ("ontouchend" in document) document.write("<script src='views/ace/assets/js/jquery.mobile.custom.min.js'>" + "<" + "script>");
</script>
<script src="plug-in\bootstarp\js\bootstrap.js"></script>
<script src="views/ace/assets/js/typeahead-bs2.min.js"></script>

<!-- page specific plugin scripts -->

<!--[if lte IE 8]>
<script src="views/ace/assets/js/excanvas.min.js"></script>
<![endif]-->

<script src="views/ace/assets/js/jquery-ui-1.10.3.custom.min.js"></script>
<script src="views/ace/assets/js/jquery.ui.touch-punch.min.js"></script>
<script src="views/ace/assets/js/jquery.slimscroll.min.js"></script>
<script src="views/ace/assets/js/jquery.easy-pie-chart.min.js"></script>
<script src="views/ace/assets/js/jquery.sparkline.min.js"></script>
<script src="views/ace/assets/js/flot/jquery.flot.min.js"></script>
<script src="views/ace/assets/js/flot/jquery.flot.pie.min.js"></script>
<script src="views/ace/assets/js/flot/jquery.flot.resize.min.js"></script>

<!-- ace scripts -->

<script src="views/ace/assets/js/ace-elements.min.js"></script>
<script src="views/ace/assets/js/ace.min.js"></script>

<!-- inline scripts related to this page -->

<script type="text/javascript">
    jQuery(function ($) {
        $('.easy-pie-chart.percentage').each(function () {
            var $box = $(this).closest('.infobox');
            var barColor = $(this).data('color') || (!$box.hasClass('infobox-dark') ? $box.css('color') : 'rgba(255,255,255,0.95)');
            var trackColor = barColor == 'rgba(255,255,255,0.95)' ? 'rgba(255,255,255,0.25)' : '#E2E2E2';
            var size = parseInt($(this).data('size')) || 50;
            $(this).easyPieChart({
                barColor: barColor,
                trackColor: trackColor,
                scaleColor: false,
                lineCap: 'butt',
                lineWidth: parseInt(size / 10),
                animate: /msie\s*(8|7|6)/.test(navigator.userAgent.toLowerCase()) ? false : 1000,
                size: size
            });
        })

        $('.sparkline').each(function () {
            var $box = $(this).closest('.infobox');
            var barColor = !$box.hasClass('infobox-dark') ? $box.css('color') : '#FFF';
            $(this).sparkline('html', {
                tagValuesAttribute: 'data-values',
                type: 'bar',
                barColor: barColor,
                chartRangeMin: $(this).data('min') || 0
            });
        });


        var placeholder = $('#piechart-placeholder').css({'width': '90%', 'min-height': '150px'});
        var data = [
            {label: "social networks", data: 38.7, color: "#68BC31"},
            {label: "search engines", data: 24.5, color: "#2091CF"},
            {label: "ad campaigns", data: 8.2, color: "#AF4E96"},
            {label: "direct traffic", data: 18.6, color: "#DA5430"},
            {label: "other", data: 10, color: "#FEE074"}
        ]

        function drawPieChart(placeholder, data, position) {
            $.plot(placeholder, data, {
                series: {
                    pie: {
                        show: true,
                        tilt: 0.8,
                        highlight: {
                            opacity: 0.25
                        },
                        stroke: {
                            color: '#fff',
                            width: 2
                        },
                        startAngle: 2
                    }
                },
                legend: {
                    show: true,
                    position: position || "ne",
                    labelBoxBorderColor: null,
                    margin: [-30, 15]
                }
                ,
                grid: {
                    hoverable: true,
                    clickable: true
                }
            })
        }

        drawPieChart(placeholder, data);

        /**
         we saved the drawing function and the data to redraw with different position later when switching to RTL mode dynamically
         so that's not needed actually.
         */
        placeholder.data('chart', data);
        placeholder.data('draw', drawPieChart);


        var $tooltip = $("<div class='tooltip top in'><div class='tooltip-inner'></div></div>").hide().appendTo('body');
        var previousPoint = null;

        placeholder.on('plothover', function (event, pos, item) {
            if (item) {
                if (previousPoint != item.seriesIndex) {
                    previousPoint = item.seriesIndex;
                    var tip = item.series['label'] + " : " + item.series['percent'] + '%';
                    $tooltip.show().children(0).text(tip);
                }
                $tooltip.css({top: pos.pageY + 10, left: pos.pageX + 10});
            } else {
                $tooltip.hide();
                previousPoint = null;
            }

        });


        var d1 = [];
        for (var i = 0; i < Math.PI * 2; i += 0.5) {
            d1.push([i, Math.sin(i)]);
        }

        var d2 = [];
        for (var i = 0; i < Math.PI * 2; i += 0.5) {
            d2.push([i, Math.cos(i)]);
        }

        var d3 = [];
        for (var i = 0; i < Math.PI * 2; i += 0.2) {
            d3.push([i, Math.tan(i)]);
        }


        var sales_charts = $('#sales-charts').css({'width': '100%', 'height': '220px'});
        $.plot("#sales-charts", [
            {label: "Domains", data: d1},
            {label: "Hosting", data: d2},
            {label: "Services", data: d3}
        ], {
            hoverable: true,
            shadowSize: 0,
            series: {
                lines: {show: true},
                points: {show: true}
            },
            xaxis: {
                tickLength: 0
            },
            yaxis: {
                ticks: 10,
                min: -2,
                max: 2,
                tickDecimals: 3
            },
            grid: {
                backgroundColor: {colors: ["#fff", "#fff"]},
                borderWidth: 1,
                borderColor: '#555'
            }
        });


        $('#recent-box [data-rel="tooltip"]').tooltip({placement: tooltip_placement});

        function tooltip_placement(context, source) {
            var $source = $(source);
            var $parent = $source.closest('.tab-content')
            var off1 = $parent.offset();
            var w1 = $parent.width();

            var off2 = $source.offset();
            var w2 = $source.width();

            if (parseInt(off2.left) < parseInt(off1.left) + parseInt(w1 / 2)) return 'right';
            return 'left';
        }


        $('.dialogs,.comments').slimScroll({
            height: '300px'
        });


        //Android's default browser somehow is confused when tapping on label which will lead to dragging the task
        //so disable dragging when clicking on label
        var agent = navigator.userAgent.toLowerCase();
        if ("ontouchstart" in document && /applewebkit/.test(agent) && /android/.test(agent))
            $('#tasks').on('touchstart', function (e) {
                var li = $(e.target).closest('#tasks li');
                if (li.length == 0) return;
                var label = li.find('label.inline').get(0);
                if (label == e.target || $.contains(label, e.target)) e.stopImmediatePropagation();
            });

        $('#tasks').sortable({
                opacity: 0.8,
                revert: true,
                forceHelperSize: true,
                placeholder: 'draggable-placeholder',
                forcePlaceholderSize: true,
                tolerance: 'pointer',
                stop: function (event, ui) {//just for Chrome!!!! so that dropdowns on items don't appear below other items after being moved
                    $(ui.item).css('z-index', 'auto');
                }
            }
        );
        $('#tasks').disableSelection();
        $('#tasks input:checkbox').removeAttr('checked').on('click', function () {
            if (this.checked) $(this).closest('li').addClass('selected');
            else $(this).closest('li').removeClass('selected');
        });


    })
</script>

<script>
    go('hello');
    loadOk();
</script>
</body>
</html>

