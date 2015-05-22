<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<html>
<head>
    <title>${product} 延迟统计</title>
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

    <!-- 延迟-机型 -->
    <div id="delay_phone" class="continer"></div>

    <!-- 延迟-平台 -->
    <div id="delay_channel" class="continer"></div>

    <!-- 延迟-消息类型 -->
    <div id="delay_msgType" class="continer"></div>


</body>


<!-- 延迟-机型 -->
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
        $('#delay_phone').highcharts({
            chart: { type: 'bar'},
            title: {
                text: '${product} 延时统计-按机型分类'
            },
            subtitle: { text: ' '},
            xAxis: {
                categories: phones,
                title: {
                    text: null
                }
            },
            yAxis: {
                min: 0,
                title: {
                    text: 'Population (millions)',
                    align: 'high'
                },
                labels: {
                    overflow: 'justify'
                }
            },
            tooltip: { valueSuffix: ' millions'},
            plotOptions: {
                bar: {
                    dataLabels: {
                        enabled: true
                    }
                }
            },
            legend: {
                layout: 'vertical',
                align: 'right',
                verticalAlign: 'top',
                x: -40,
                y: 100,
                floating: true,
                borderWidth: 1,
                backgroundColor: '#FFFFFF',
                shadow: true
            },
            credits: { enabled: false},
            series: data1
        });
    });
</script>


<!-- 延迟-渠道 -->
<script type="text/javascript">
    var data2 = [];
    var channels = [];

    <c:forEach items="${channels}" var="p">
        channels.push('${p}');
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
        $('#delay_channel').highcharts({
            chart: { type: 'bar'},
            title: {
                text: '${product} 延时统计-按渠道分类'
            },
            subtitle: { text: ' '},
            xAxis: {
                categories: channels,
                title: {
                    text: null
                }
            },
            yAxis: {
                min: 0,
                title: {
                    text: 'Population (millions)',
                    align: 'high'
                },
                labels: {
                    overflow: 'justify'
                }
            },
            tooltip: { valueSuffix: ' millions'},
            plotOptions: {
                bar: {
                    dataLabels: {
                        enabled: true
                    }
                }
            },
            legend: {
                layout: 'vertical',
                align: 'right',
                verticalAlign: 'top',
                x: -40,
                y: 100,
                floating: true,
                borderWidth: 1,
                backgroundColor: '#FFFFFF',
                shadow: true
            },
            credits: { enabled: false},
            series: data2
        });
    });
</script>



<!-- 延迟-消息类型 -->
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
        $('#delay_msgType').highcharts({
            chart: { type: 'bar'},
            title: {
                text: '${product} 延时统计-按消息类型分类'
            },
            subtitle: { text: ' '},
            xAxis: {
                categories: msgTypes,
                title: {
                    text: null
                }
            },
            yAxis: {
                min: 0,
                title: {
                    text: 'Population (millions)',
                    align: 'high'
                },
                labels: {
                    overflow: 'justify'
                }
            },
            tooltip: { valueSuffix: ' millions'},
            plotOptions: {
                bar: {
                    dataLabels: {
                        enabled: true
                    }
                }
            },
            legend: {
                layout: 'vertical',
                align: 'right',
                verticalAlign: 'top',
                x: -40,
                y: 100,
                floating: true,
                borderWidth: 1,
                backgroundColor: '#FFFFFF',
                shadow: true
            },
            credits: { enabled: false},
            series: data3
        });
    });
</script>
</html>
