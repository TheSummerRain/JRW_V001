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
                            window.location.href = window.location.href.split("&")[0] + '&index';
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