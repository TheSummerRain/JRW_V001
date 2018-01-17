// 定义一个函数，定时调用并刷新iframe高度  
function reinitIframe(){
    var iframe = document.getElementById("iframepage");
    try{
        var bHeight = iframe.contentWindow.document.body.scrollHeight;
      /*  var dHeight = iframe.contentWindow.document.documentElement.scrollHeight;
        var height = Math.max(bHeight, dHeight);*/
        iframe.height = bHeight;
    }catch (ex){}
}

var timer1 = window.setInterval("reinitIframe()", 500); //定时调用开始  

//完毕后干掉定时器  
function IframeLoadEND(){
    var iframe = document.getElementById("iframepage");
    try{
        window.clearInterval(timer1);
        var bHeight = iframe.contentWindow.document.body.scrollHeight;
        var dHeight = iframe.contentWindow.document.documentElement.scrollHeight;
        var height = Math.max(bHeight, dHeight);
        iframe.height = height;
    }catch (ex){}
    // 停止定时  
    window.clearInterval(timer1);

}  