function go(str) {
    var obj;
    //鉴于上面出现的问题，可以采取下面的方法解决，用if判断是否为IE6浏览器
    if(window.XMLHttpRequest)//如果有XMLHttpRequest，那就是非IE6浏览器。()里面加window的原因下面会有描述。
    {
        obj = new XMLHttpRequest();//创建ajax对象
    }
    else//如果没有XMLHttpRequest，那就是IE6浏览器
    {
        obj = new ActiveXObject("Microsoft.XMLHTTP");//IE6浏览器创建ajax对象
    }
    obj.open('GET', 'o/account/info', false);
    obj.onreadystatechange = function() {
        if (obj.readyState == 4 && obj.status == 200 || obj.status == 304) { // readyState == 4说明请求已完成
            var re = JSON.parse(obj.responseText);
            if (re.data == null || re.data.length == 0) {
                var url = window.top.window.location.href;
                if (url.indexOf("$index")) {
                    url = url.split("&index")[0] + "&login"
                } else {
                    url = 'egpp&login';
                }
                //window.top.window.location.href = url;
            }
        }
    };
    obj.send();
    $("#iframepage").attr("src", str);
    console.info($(this).text());
}

function checkSession() {
    var obj;
    //鉴于上面出现的问题，可以采取下面的方法解决，用if判断是否为IE6浏览器
    if(window.XMLHttpRequest)//如果有XMLHttpRequest，那就是非IE6浏览器。()里面加window的原因下面会有描述。
    {
        obj = new XMLHttpRequest();//创建ajax对象
    }
    else//如果没有XMLHttpRequest，那就是IE6浏览器
    {
        obj = new ActiveXObject("Microsoft.XMLHTTP");//IE6浏览器创建ajax对象
    }
    obj.open('GET', 'o/account/info', false);
    obj.onreadystatechange = function() {
        if (obj.readyState == 4 && obj.status == 200 || obj.status == 304) { // readyState == 4说明请求已完成
            var re = JSON.parse(obj.responseText);
            if (re.data == null || re.data.length == 0) {
                var url = window.top.window.location.href;
                url = url.split("&index")[0] + "&login"
                window.top.window.location.href = url;
            }
        }
    };
    obj.send();
}
// 定义一个函数，定时调用并刷新iframe高度
function reinitIframe() {
    var iframes = document.getElementsByName("iframepage");
    if (iframes == null) {
        return
    }
    iframes.forEach(function (t) {
        try {
            var bHeight = t.contentWindow.document.body.scrollHeight;
            var dHeight = t.contentWindow.document.documentElement.scrollHeight;
            t.height = bHeight;
        } catch (ex) {
            console.error(ex);
        }
    })

}

var timer1 = window.setInterval("reinitIframe()", 100); //定时调用开始 无限循环调整 iframe 高度

//完毕后干掉定时器  
function IframeLoadEND() {
    var iframe = document.getElementById("iframepage");
    try {
        window.clearInterval(timer1);
        var bHeight = iframe.contentWindow.document.body.scrollHeight;
        var dHeight = iframe.contentWindow.document.documentElement.scrollHeight;
        var height = Math.max(bHeight, dHeight);
        iframe.height = height;
    } catch (ex) {
    }
    // 停止定时  
    window.clearInterval(timer1);
}

// 原生js ajax 请求
var Ajax = {
    get: function(url, fn) {
        var obj = new XMLHttpRequest();  // XMLHttpRequest对象用于在后台与服务器交换数据
        obj.open('GET', url, true);
        obj.onreadystatechange = function() {
            if (obj.readyState == 4 && obj.status == 200 || obj.status == 304) { // readyState == 4说明请求已完成
                fn.call(this, obj.responseText);  //从服务器获得数据
            }
        };
        obj.send();
    },
    post: function (url, data, fn) {         // datat应为'a=a1&b=b1'这种字符串格式，在jq里如果data为对象会自动将对象转成这种字符串格式
        var obj = new XMLHttpRequest();
        obj.open("POST", url, true);
        obj.setRequestHeader("Content-type", "application/x-www-form-urlencoded");  // 添加http头，发送信息至服务器时内容编码类型
        obj.onreadystatechange = function() {
            if (obj.readyState == 4 && (obj.status == 200 || obj.status == 304)) {  // 304未修改
                fn.call(this, obj.responseText);
            }
        };
        obj.send(data);
    }
}