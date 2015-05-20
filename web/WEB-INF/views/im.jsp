<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html>
<head>
    <title>IM</title>
    <style type="text/css">
        .continer {
            width: 800px;
            height: 600px;
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
<div id="chengGongLv_JiXing_HX" class="continer"></div>
<div id="chengGongLv_JiXing_RY" class="continer"></div>
<div id="chengGongLv_JiXing_YTX" class="continer"></div>

<div id="chengGongLv_QuDao" class="continer"></div>
<div id="chengGongLv_LeiXing" class="continer"></div>
</body>


<!-- 消息总数量 -->
<script type="text/javascript">
    var colors = Highcharts.getOptions().colors;
    var pros = [];          // 产品
    var prosNums = [];      // 产品总数量
    <c:forEach items="${proNum}" var="pro">
    pros[pros.length] = '${pro.product}';
    prosNums[prosNums.length] = parseInt('${pro.totalNum}');
    </c:forEach>


    var dataPros = [];
    for (var i = 0; i < prosNums.length; ++i) {
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
            chart: {type: 'column'},
            title: {text: '即时通信数据统计'},
            subtitle: {text: '消息总数量'},
            xAxis: {categories: categories},
            yAxis: {
                title: {text: ''}
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
                    return this.x + ':<b>' + this.y + '</b><br>';
                    ;
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


<!-- 机型失败率-环信 -->
<script type="text/javascript">
//    var products = [];
    var hxData = [];
    var phones = [];


    <%--<c:forEach items="${names}" var="n">--%>
        <%--products.push('${n.key}')--%>
    <%--</c:forEach>--%>

    <c:forEach items="${data}" var="d">
        <c:if test="${d.key eq 'HX'}">
            <c:forEach items="${d.value}" var="v">
                var obj = {};
                obj.name = '${v.name}';
                obj.data = [];
                <c:forEach items="${v.data}" var="dd">
                    obj.data.push(${dd});
                </c:forEach>
                hxData.push(obj);
            </c:forEach>
        </c:if>
    </c:forEach>


    <c:forEach items="${phones}" var="p">
        <c:if test="${p.key eq 'HX'}">
            <c:forEach items="${p.value}" var="v">
                phones.push('${v}');
            </c:forEach>
        </c:if>
    </c:forEach>



$(function () {
    $('#chengGongLv_JiXing_HX').highcharts({
        chart: {
            type: 'column'
        },
        title: {
            text: 'Stacked column chart'
        },
        xAxis: {
            categories: phones
        },
        yAxis: {
            min: 0,
            title: {
                text: 'Total fruit consumption'
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
        series: hxData
    });
});
</script>

</html>