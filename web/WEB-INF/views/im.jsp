<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html>
<head>
    <title>IM</title>
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
        a {
            font-size: 30px;
            font-weight: bold;
            margin-right: 30px;
            /*background: #C0C0C0;*/
            /*width: 100px;*/
            /*height: 30px;*/
        }
    </style>
    <script type="text/javascript" src="../js/highcharts/jquery-1.8.2.min.js"></script>
    <script type="text/javascript" src="../js/highcharts/highcharts.js"></script>
    <script type="text/javascript" src="../js/highcharts/exporting.js"></script>

</head>
<body>

    <div style="margin: 20px auto 0px auto; width: 300px;">
        <h1 style="font-size: 30px; font-weight: bold">即时通信统计</h1>
    </div>





    <div style="margin: 0px auto 50px auto; width: 500px;">
        <jsp:include page="search_box.jsp"/>
        <strong>发送失败统计：</strong>
        <c:forEach items="${proNum}" var="p">
            <a href="im/fail?p=${p.product}" target="_blank">${p.product}</a>
        </c:forEach>

        <hr>

        <strong>丢包统计：　　</strong>
        <c:forEach items="${proNum}" var="p">
            <a href="im/lost?p=${p.product}" target="_blank">${p.product}</a>
        </c:forEach>

        <hr>

        <strong>延迟统计：</strong>
        <c:forEach items="${proNum}" var="p">
            <a href="im/delay?p=${p.product}" target="_blank">${p.product}</a>
        </c:forEach>
    </div>

    <!-- 消息数量 -->
    <div id="zongShuLiang" class="continer"></div>

    <!-- 失败率 -->
    <div id="chengGongLv" class="continer"></div>

    <!-- 丢包率 -->
    <div id="diuBaoLv" class="continer"></div>

    <!-- 延迟 -->
    <div id="yanChi" class="continer"></div>
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

<!-- 失败率 -->
<script type="text/javascript">
    var products = [];
    var data = [];

    <c:forEach items="${products}" var="p">
        products.push('${p}');
    </c:forEach>

    <c:forEach items="${data}" var="d">
        var obj = {};
        obj.name = '${d.name}';
        obj.data = [];
        <c:forEach items="${d.data}" var="dd">
            obj.data.push(${dd});
        </c:forEach>
        data.push(obj);
    </c:forEach>


    $(function () {
        $('#chengGongLv').highcharts({
            chart: {
                type: 'column'
            },
            title: {
                text: '失败率(发送数量/发送成功数量)'
            },
            colors: ['#f7a35c', '#8085e9', '#f15c80', '#e4d354', '#8085e8', '#8d4653', '#91e8e1'],
            xAxis: {
                categories: products
            },
            yAxis: {
                min: 0,
                title: {
                    text: '失败率(发送数量/发送成功数量)'
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
            series: data
        });
    });
</script>

<!-- 丢包率 -->
<script type="text/javascript">
    var data2 = [];

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
        $('#diuBaoLv').highcharts({
            chart: {
                type: 'column'
            },
            colors: ['#e4d354', '#8085e8', '#8d4653', '#91e8e1'],
            title: {
                text: '丢包率(接收/丢失)'
            },
            xAxis: {
                categories: products
            },
            yAxis: {
                min: 0,
                title: {
                    text: '丢包率(接收/丢失)'
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

<!-- 延迟 -->
<script type="text/javascript">
    var data3 = [];

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
        $('#yanChi').highcharts({
            chart: { type: 'bar'},
            title: {
                text: '延迟'
            },
            subtitle: {
                text: '单位:ms'
            },
            xAxis: {
                categories: products,
                title: {
                    text: '延迟统计,单位(ms)'
                }
            },
            yAxis: {
                min: 0,
                title: {
                    text: 'Population (ms)',
                    align: 'high'
                },
                labels: { overflow: 'justify'}
            },
            tooltip: { valueSuffix: ' '},
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