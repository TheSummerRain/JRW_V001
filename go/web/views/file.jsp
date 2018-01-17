<!-- 首页公共引用-->
<style>
    body {
        overflow-x: hidden !important;
        overflow-y: auto !important;
    }
    body::-webkit-scrollbar-track
    {
        -webkit-box-shadow: inset 0 0 6px rgba(187, 189, 174, 0.3);
        background-color: #F5F5F5;
    }

    body::-webkit-scrollbar
    {
        width: 6px;
        background-color: #fafafa;
    }

    body::-webkit-scrollbar-thumb
    {
        background-color: #5faeff;
    }


    div::-webkit-scrollbar-track
    {
        -webkit-box-shadow: inset 0 0 6px rgba(0,0,0,0.3);
        background-color: #F5F5F5;
    }

    div::-webkit-scrollbar
    {
        width: 6px;
        background-color: #fafafa;
    }

    div::-webkit-scrollbar-thumb
    {
        background-color: #5faeff;
    }
</style>

<script>
    function loadOk(id) {
        try {
            var v = document.getElementById(id || "app");
            v.style.display = "block";
        } finally {
            setTimeout('document.getElementById("loadDiv").style.display="none"', 100);
        }
    }
    setTimeout("loadOk()", 1000);
</script>
<script src="plug-in/index/index/index.js" type="text/javascript"></script>
<div id="loadDiv"
     style="
     position: fixed;
     z-index: 10001111111;
     width: 100%;
     height: 100%;
     background-color: #ffffff;
     text-align: center;
     border-width: 0px">
    <img src="views/niao.gif" width="300" style="
                                            margin-top: 50px;
                                            margin-right: auto;
                                            margin-left: auto;
                                            "/>
</div>