<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html>
<head>
    <title>IM</title>
    <style type="text/css">
        .continer{
            width: 800px; height: 600px;
            margin: 0px auto 100px auto;
            background: #E0E0E0;
        }
    </style>
    <script type="text/javascript" src="../js/highcharts/jquery-1.8.2.min.js"></script>
    <script type="text/javascript" src="../js/highcharts/highcharts.js"></script>
    <script type="text/javascript" src="../js/highcharts/exporting.js"></script>

</head>
<body>

    <!-- 消息数量 -->
    <div id="zongShuLiang" class="continer"></div>

    <!-- 成功率 -->
    <div id="chengGongLv_JiXing" class="continer"></div>
    <div id="chengGongLv_QuDao" class="continer"></div>
    <div id="chengGongLv_LeiXing" class="continer"></div>
</body>


<!-- 消息总数量 -->
<script type="text/javascript">
    var colors = Highcharts.getOptions().colors;
    var pros = [];
    var prosNums = [];
    <c:forEach items="${proNum}" var="pro">
        pros[pros.length] = '${pro.product}';
        prosNums[prosNums.length] = parseInt('${pro.totalNum}');
    </c:forEach>
    var dataPros = [];
    for(var i=0; i<prosNums.length; ++i){
        dataPros[i] = {};
        dataPros[i].y = prosNums[i];
        dataPros[i].color = colors[i];
    }
    $(function () {
        var colors = Highcharts.getOptions().colors;
        var categories = pros;
        var name = '消息总数量';
        var data = dataPros;

        function setChart(name, categories, data, color) {
            chart.xAxis[0].setCategories(categories, false);
            chart.series[0].remove(false);
            chart.addSeries({
                name: name,
                data: data,
                color: color || 'white'
            }, false);
            chart.redraw();
        }

        var chart = $('#zongShuLiang').highcharts({
            chart: { type: 'column'},
            title: { text: '即时通信数据统计'},
            subtitle: { text: '消息总数量'},
            xAxis: { categories: categories},
            yAxis: {
                title: { text: ''}
            },
            plotOptions: {
                column: {
                    cursor: 'pointer',
                    dataLabels: {
                        enabled: true,
                        color: colors[0],
                        style: {
                            fontWeight: 'bold'
                        },
                        formatter: function () {
                            return this.y + ' 条';
                        }
                    }
                }
            },
            tooltip: {
                formatter: function () {
                    return this.x + ':<b>' + this.y + '</b><br>';;
                }
            },
            series: [{
                name: name,
                data: data,
                color: 'white'
            }],
            exporting: {
                enabled: false
            }
        }).highcharts(); // return chart
    });
</script>


<!-- 失败率 -->
<script type="text/javascript">

    var prodPhoneData = [];
    var products = [];
    var phones = [];

    <c:forEach items="${phones}" var="p" varStatus="status">
        var ind = ${status.index*2};
        var obj1 = {};
        obj1.name = '成功';
        obj1.stack = '${p}';
        obj1.data = [];

        var obj2 = {};
        obj2.name = '失败';
        obj2.stack = '${p}';
        obj2.data = [];

        prodPhoneData[ind]= obj1;
        prodPhoneData[ind+1] = obj2;
    </c:forEach>
    <%--<c:forEach items="${names}" var="n">--%>
        <%--products[products.length] = '${n.key}';--%>

        <%--<%--alert('${n.key}, ${n.value}');--%>
    <%--</c:forEach>--%>

    <c:forEach items="${prodPhoneNum}" var="pp" varStatus="status">
        <%--prodPhoneData[${status.index}].data[${status.index}] = parseInt('${pp.success}');--%>
    </c:forEach>

    $(function () {
        $('#chengGongLv_JiXing').highcharts({
            chart: { type: 'column'},
            title: { text: '根据机型统计发送成功率'},
            xAxis: { categories: ['Apples', 'Oranges', 'Pears', 'Grapes', 'Bananas']},
            yAxis: {
                allowDecimals: false,
                min: 0,
                title: {
                    text: ' '
                }
            },

            tooltip: {
                formatter: function() {
                    return '<b>'+ this.x +'</b><br>'+
                            this.series.name +': '+ this.y +'<br>'+
                            '共: '+ this.point.stackTotal;
                }
            },
            plotOptions: {
                column: {
                    stacking: 'normal'
                }
            },
            colors: ['#4572A7', '#AA4643', '#89A54E', '#80699B', '#3D96AE', '#DB843D', '#92A8CD', '#A47D7C', '#B5CA92'],
            series: [{
                name: '成功',
                data: [5, 3, 4, 7, 2],
                stack: 'Huawei'
            }, {
                name: '失败',
                data: [3, 4, 4, 2, 5],
                stack: 'Huawei'
            }]
        });
    });
</script>

</html>