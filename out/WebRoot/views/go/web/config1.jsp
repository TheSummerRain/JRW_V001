<%--
  Created by IntelliJ IDEA.
  User: yangchao
  Date: 2017/12/14
  Time: 16:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!-- 一个 单页的 单对单 模型 -->
<html>
<head>
    <title>配置</title>

    <link type="text/css" href="views\egpp\bootstrap\css\bootstrap.min.css" rel="stylesheet">
    <link type="text/css" href="plug-in\bootstarp\css\bootstrap.css" rel="stylesheet">

    <script src="plug-in\vue\vue.js"></script>
    <script src="plug-in\jquery\jquery.js"></script>
    <script src="plug-in\bootstarp\js\bootstrap.js"></script>

    <!-- 分页插件 -->
    <link rel="stylesheet" href="plug-in\vue-easytable\css\table.css">
    <script src="plug-in\vue-easytable\js\table.js"></script>

</head>
<body>

<div id="app">
    <input type="button" value="重新请求" @click="request()"/>
    <v-table
            is-horizontal-resize
            style="width:100%"
            multiple-sort
            :columns="columns"
            :table-data="tableData"
            @sort-change="sortChange"
            row-hoverColor="#eee"
            row-click-color="#edf7ff"
    ></v-table>
    <div class="mt30 mb20 bold">
        <v-pagination :total="600" :layout="['total', 'sizer', 'prev', 'pager', 'next', 'jumper']"
                      @page-change="pageChange1" @page-size-change="pageSizeChange1">

        </v-pagination>
    </div>
</div>

<script>
    new Vue({
        el: '#app',
        data: function () {
            return {
                tableData: [
                    {"name": "赵伟", "tel": "156*****1987", "hobby": "钢琴、书法、唱歌", "address": "上海市黄浦区金陵东路569号17楼"},
                    {"name": "李伟", "tel": "182*****1538", "hobby": "钢琴、书法、唱歌", "address": "上海市奉贤区南桥镇立新路12号2楼"},
                    {"name": "孙伟", "tel": "161*****0097", "hobby": "钢琴、书法、唱歌", "address": "上海市崇明县城桥镇八一路739号"},
                    {"name": "周伟", "tel": "197*****1123", "hobby": "钢琴、书法、唱歌", "address": "上海市青浦区青浦镇章浜路24号"},
                    {"name": "吴伟", "tel": "183*****6678", "hobby": "钢琴、书法、唱歌", "address": "上海市松江区乐都西路867-871号"}
                ],
                columns: [
                    {
                        field: 'name',
                        title: '姓名',
                        width: 80,
                        titleAlign: 'center',
                        columnAlign: 'center',
                        orderBy: 'asc',
                        isResize: true
                    },
                    {
                        field: 'tel',
                        title: '手机号码',
                        width: 150,
                        titleAlign: 'center',
                        columnAlign: 'center',
                        orderBy: 'desc',
                        isResize: true
                    },
                    {
                        field: 'hobby',
                        title: '爱好',
                        width: 150,
                        titleAlign: 'center',
                        columnAlign: 'center',
                        isResize: true
                    }, {
                        field: 'address',
                        title: '地址',
                        width: 230,
                        titleAlign: 'center',
                        columnAlign: 'left',
                        isResize: true
                    },
                    {
                        field: 'custome-adv',
                        title: '操作',
                        width: 200,
                        titleAlign: 'center',
                        columnAlign: 'center',
                        componentName: 'table-operation',
                        isResize: true
                    }

                ]
            }
        },
        methods: {
            pageChange1: function (pageIndex) {
                console.log(pageIndex)
            },
            pageSizeChange1: function (pageSize) {
                console.log(pageSize)
            },
            // 获取 table 组件每次操作后的参数（重新去请求数据）
            sortChange: function (params) {
                console.log(params)
            },
            request: function () {
                this.isLoading = true;
                var r = Math.random();
                if (r > 0.5) {
                    this.tableData = [];
                } else {
                    this.tableData = [
                        {"name": "赵伟", "tel": "156*****1987", "hobby": "钢琴、书法、唱歌", "address": "上海市黄浦区金陵东路569号17楼"},
                        {"name": "李伟", "tel": "182*****1538", "hobby": "钢琴、书法、唱歌", "address": "上海市奉贤区南桥镇立新路12号2楼"},
                        {"name": "孙伟", "tel": "161*****0097", "hobby": "钢琴、书法、唱歌", "address": "上海市崇明县城桥镇八一路739号"},
                        {"name": "周伟", "tel": "197*****1123", "hobby": "钢琴、书法、唱歌", "address": "上海市青浦区青浦镇章浜路24号"},
                        {"name": "吴伟", "tel": "183*****6678", "hobby": "钢琴、书法、唱歌", "address": "上海市松江区乐都西路867-871号"}
                    ];
                }
            },
            customCompFunc: function (params) {

                console.log(params);

                if (params.type === 'delete') { // do delete operation

                    this.$delete(this.tableData, params.index);

                } else if (params.type === 'edit') { // do edit operation
                    alert("行号${params.index} 姓名：${params.rowData['name']}");
                }

            }
        }
    })

    // 自定义列组件
    Vue.component('table-operation', {
        template: '<span> ' +
        '<a href="" @click.stop.prevent="update(rowData,index)">编辑</a>&nbsp;' +
        ' <a href="" @click.stop.prevent="deleteRow(rowData,index)">删除</a>' +
        '</span>',
        props: {
            rowData: {
                type: Object
            },
            field: {
                type: String
            },
            index: {
                type: Number
            }
        },
        methods: {
            update: function () {
                // 参数根据业务场景随意构造
                let params = {type: 'edit', index: this.index, rowData: this.rowData};
                this.$emit('on-custom-comp', params);
            },

            deleteRow: function () {
                // 参数根据业务场景随意构造
                let params = {type: 'delete', index: this.index};
                this.$emit('on-custom-comp', params);

            }
        }
    })
</script>
</body>
</html>
