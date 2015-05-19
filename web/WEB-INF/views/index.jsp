<%--
  Created by IntelliJ IDEA.
  User: zhangyong
  Date: 2015/5/15
  Time: 11:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>即时通信/推送统计</title>
    <style type="text/css">
        .center {
            MARGIN-RIGHT: auto;
            MARGIN-LEFT: auto;
            margin-top: 5%;
            height: 600px;
            width: 600px;
            vertical-align: middle;
            line-height: 200px;
        }

        button {
            width: 200px;
            height: 60px;
        }
    </style>

    <script type="text/javascript">

    </script>
</head>
<body>
<div class="center">
    <button style="margin-right:50px" onclick="javascript:location.href='/im';">即时通信</button>
    <button onclick="javascript:location.href='/push';">推送</button>
</div>
</body>
</html>
