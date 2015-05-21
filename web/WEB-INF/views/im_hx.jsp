<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<html>
<head>
    <title>${product}</title>
    <style type="text/css">
      .continer {
        width:1000px;
        height: 500px;
        margin: 0px auto 100px auto;
        background: #E0E0E0;
      }
    </style>
    <script type="text/javascript" src="../js/highcharts/jquery-1.8.2.min.js"></script>
    <script type="text/javascript" src="../js/highcharts/highcharts.js"></script>
    <script type="text/javascript" src="../js/highcharts/exporting.js"></script>
</head>
<body>
    <!-- 失败率-机型 -->
    <div id="shiBai_jiXing" class="continer"></div>

    <!-- 失败率-平台 -->
    <div id="shiBai_pingTai" class="continer"></div>

    <!-- 失败率-消息类型 -->
    <div id="shiBai_leiXing" class="continer"></div>

</body>





<!-- 失败率-机型 -->
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
      $('#shiBai_jiXing').highcharts({
        chart: {
          type: 'column'
        },
        title: {
          text: '${product} 成功/失败数--按机型划分'
        },
        xAxis: {
          categories: phones
        },
        yAxis: {
          min: 0,
          title: {
            text: '${product} 成功/失败数, 按机型划分'
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


<!-- 失败率-平台 -->
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
        $('#shiBai_pingTai').highcharts({
            chart: {
                type: 'column'
            },
            title: {
                text: '${product} 成功/失败数--按渠道划分'
            },
            xAxis: {
                categories: channles
            },
            yAxis: {
                min: 0,
                title: {
                    text: '${product} 成功/失败数--按渠道划分'
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





<!-- 失败率-消息类型 -->
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
        $('#shiBai_leiXing').highcharts({
            chart: {
                type: 'column'
            },
            title: {
                text: '${product} 成功/失败数--按消息类型划分'
            },
            xAxis: {
                categories: msgTypes
            },
            yAxis: {
                min: 0,
                title: {
                    text: '${product} 成功/失败数--按消息类型划分'
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
