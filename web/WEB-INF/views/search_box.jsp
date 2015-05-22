<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script type="text/javascript" src="../my97/WdatePicker.js"></script>



    <div style="margin: 10px auto 100px auto; width: 500px;">
        <input class="Wdate" type="text" onClick="WdatePicker()" style="margin-right: 50px;" id="begin">
        <input class="Wdate" type="text" onClick="WdatePicker()" style="margin-bottom: 50px;" id="end">
        <button onclick="search()">查询</button>
        <hr>
    </div>


    <form action="" id="form" method="post" style="visibility: hidden">
        <input name="begin" type="hidden" />
        <input name="end" type="hidden" />

    </form>


    <script type="text/javascript">
        $(function(){
            var action = location.href;
            $("#form").attr("action", action);
            $("#begin").val('${fn:substring(begin,0,10)}');
            $("#end").val('${fn:substring(end,0,10)}');
        });
        function search(){
            var begin = $("#begin").val();
            var end = $("#end").val();
            $("[name='begin']").val(begin);
            $("[name='end']").val(end);
            $("#form").submit();
        }
    </script>