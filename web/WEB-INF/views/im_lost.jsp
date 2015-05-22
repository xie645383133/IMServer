<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<html>
<head>
    <title>${product} 丢包统计</title>
    <style type="text/css">
      .continer {
        width:1000px;
        height: 500px;
        margin: 0px auto 100px auto;
        background: #E0E0E0;
      }
      button {
        width: 100px;
        height: 30px;
      }
      hr{
        margin-bottom: 20px; margin-top: 20px;
      }


    </style>
    <script type="text/javascript" src="../js/highcharts/jquery-1.8.2.min.js"></script>
    <script type="text/javascript" src="../js/highcharts/highcharts.js"></script>
    <script type="text/javascript" src="../js/highcharts/exporting.js"></script>
</head>
<body>
    <jsp:include page="search_box.jsp"/>

    <!-- 丢包率-机型 -->
    <div id="lost_phone" class="continer"></div>

    <!-- 丢包率-平台 -->
    <div id="lost_channel" class="continer"></div>

    <!-- 丢包率-消息类型 -->
    <div id="lost_msgType" class="continer"></div>
</body>



<!-- 丢包率-机型 -->
<script type="text/javascript">
    var data1 = [];
    var phones = [];

    <c:forEach items="${phones}" var="p">
    phones.push('${p}');
    </c:forEach>

    <c:forEach items="${data1}" var="d">
        var obj = {};
        obj.name = '${d.name}';
        obj.data = [];
        <c:forEach items="${d.data}" var="dd">
            obj.data.push(${dd});
        </c:forEach>
        data1.push(obj);
    </c:forEach>

    $(function () {
        $('#lost_phone').highcharts({
            chart: {
                type: 'column'
            },
            title: {
                text: '${product}丢包率统计-按机型分类'
            },
            xAxis: {
                categories: phones
            },
            yAxis: {
                min: 0,
                title: {
                    text: '${product}丢包率统计-按机型分类'
                },
                stackLabels: {
                    enabled: true,
                    style: {
                        fontWeight: 'bold',
                        color: (Highcharts.theme && Highcharts.theme.textColor) || 'gray'
                    }
                }
            },
            legend: {
                align: 'right',
                x: -70,
                verticalAlign: 'top',
                y: 20,
                floating: true,
                backgroundColor: (Highcharts.theme && Highcharts.theme.legendBackgroundColorSolid) || 'white',
                borderColor: '#CCC',
                borderWidth: 1,
                shadow: false
            },
            tooltip: {
                formatter: function() {
                    return '<b>'+ this.x +'</b><br/>'+
                            this.series.name +': '+ this.y +'<br/>'+
                            'Total: '+ this.point.stackTotal;
                }
            },
            plotOptions: {
                column: {
                    stacking: 'normal',
                    dataLabels: {
                        enabled: true,
                        color: (Highcharts.theme && Highcharts.theme.dataLabelsColor) || 'white'
                    }
                }
            },
            series: data1
        });
    });
</script>


<!-- 丢包率-平台 -->
<script type="text/javascript">
    var data2 = [];
    var channles = [];

    <c:forEach items="${channles}" var="p">
        channles.push('${p}');
    </c:forEach>

    <c:forEach items="${data2}" var="d">
        var obj = {};
        obj.name = '${d.name}';
        obj.data = [];
        <c:forEach items="${d.data}" var="dd">
            obj.data.push(${dd});
        </c:forEach>
        data2.push(obj);
    </c:forEach>

    $(function () {
        $('#lost_channel').highcharts({
            chart: {
                type: 'column'
            },
            title: {
                text: '${product}丢包率统计-按平台分类'
            },
            xAxis: {
                categories: channles
            },
            yAxis: {
                min: 0,
                title: {
                    text: '${product}丢包率统计-按平台分类'
                },
                stackLabels: {
                    enabled: true,
                    style: {
                        fontWeight: 'bold',
                        color: (Highcharts.theme && Highcharts.theme.textColor) || 'gray'
                    }
                }
            },
            legend: {
                align: 'right',
                x: -70,
                verticalAlign: 'top',
                y: 20,
                floating: true,
                backgroundColor: (Highcharts.theme && Highcharts.theme.legendBackgroundColorSolid) || 'white',
                borderColor: '#CCC',
                borderWidth: 1,
                shadow: false
            },
            tooltip: {
                formatter: function() {
                    return '<b>'+ this.x +'</b><br/>'+
                            this.series.name +': '+ this.y +'<br/>'+
                            'Total: '+ this.point.stackTotal;
                }
            },
            plotOptions: {
                column: {
                    stacking: 'normal',
                    dataLabels: {
                        enabled: true,
                        color: (Highcharts.theme && Highcharts.theme.dataLabelsColor) || 'white'
                    }
                }
            },
            series: data2
        });
    });
</script>


<!-- 丢包率-消息类型 -->
<script type="text/javascript">
    var data3 = [];
    var msgTypes = [];

    <c:forEach items="${msgTypes}" var="p">
        msgTypes.push('${p}');
    </c:forEach>

    <c:forEach items="${data3}" var="d">
        var obj = {};
        obj.name = '${d.name}';
        obj.data = [];
        <c:forEach items="${d.data}" var="dd">
            obj.data.push(${dd});
        </c:forEach>
        data3.push(obj);
    </c:forEach>

    $(function () {
        $('#lost_msgType').highcharts({
            chart: {
                type: 'column'
            },
            title: {
                text: '${product}丢包率统计-按消息类型分类'
            },
            xAxis: {
                categories: channles
            },
            yAxis: {
                min: 0,
                title: {
                    text: '${product}丢包率统计-按消息类型分类'
                },
                stackLabels: {
                    enabled: true,
                    style: {
                        fontWeight: 'bold',
                        color: (Highcharts.theme && Highcharts.theme.textColor) || 'gray'
                    }
                }
            },
            legend: {
                align: 'right',
                x: -70,
                verticalAlign: 'top',
                y: 20,
                floating: true,
                backgroundColor: (Highcharts.theme && Highcharts.theme.legendBackgroundColorSolid) || 'white',
                borderColor: '#CCC',
                borderWidth: 1,
                shadow: false
            },
            tooltip: {
                formatter: function() {
                    return '<b>'+ this.x +'</b><br/>'+
                            this.series.name +': '+ this.y +'<br/>'+
                            'Total: '+ this.point.stackTotal;
                }
            },
            plotOptions: {
                column: {
                    stacking: 'normal',
                    dataLabels: {
                        enabled: true,
                        color: (Highcharts.theme && Highcharts.theme.dataLabelsColor) || 'white'
                    }
                }
            },
            series: data3
        });
    });
</script>
</html>
