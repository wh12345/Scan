<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
   <base href="<%=basePath%>" >    
   <!--<base href="http://whweb.j2eeall.com/Scan/"> --> 
    
    <title>统计信息</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	 <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link href="css/bootstrap.min.css" rel="stylesheet" media="screen">
	<script src="js/jquery-1.11.0.min.js"></script>
	<script src="js/bootstrap.min.js"></script>
	<script src="js/echarts.js"></script>
	<style type="text/css">
        	.container{
        	  width: 380px;
        	  margin:0 auto;
        	}	   
	</style>
  </head> 
  <body>
    <div class="container">
		<div class="text-center"><b>韶关交警微信公众号便民服务数据统计</b></div><br/>
		<div class="text-center">统计信息表格(前一周)</div>
		<table class="table table-striped">
            <thead>
            	<th></th>
            	<th>违法查询</th>
            	<th>违法处理</th>
            	<th>违法缴费</th>
            </thead>           
        </table>
        <div class="text-center">统计信息折线图(前一周)</div>
	    <div id="main" style="width: auto; height: 300px;margin:0 12px;"></div>
	    </div>
	    <script type="text/javascript">
	    	var myChart = echarts.init(document.getElementById('main'));
			option = {
			    title: {
			        text: ''
			    },
			    tooltip : {
			        trigger: 'axis',
			        axisPointer: {
			            type: 'cross',
			            label: {
			                backgroundColor: '#6a7985'
			            }
			        }
			    },
			    legend: {
			        data:['违法查询','违法处理','违法缴费']
			    },
			    grid: {
			        left: '3%',
			        right: '4%',
			        bottom: '3%',
			        containLabel: true
			    },
			    xAxis : [
			        {
			            type : 'category',
			            boundaryGap : false,
			            data : []
			        }
			    ],
			    yAxis : [
			        {
			            type : 'value'
			        }
			    ],
			    series : [
			        {
			            name:'违法查询',
			            type:'line',
			            //stack: '总量',
			            areaStyle: {},
			            data:[]
			        },
			        {
			            name:'违法处理',
			            type:'line',
			            //stack: '总量',
			            areaStyle: {},
			            data:[]
			        },
			        {
			            name:'违法缴费',
			            type:'line',
			            //stack: '总量',
			            areaStyle: {},
			            data:[]
			        },			        
			    ]
			};
			myChart.hideLoading();
			myChart.setOption(option);
	       $(function() {
	            $.ajax({
                           type: "POST",
                           url: "StatisticsInfoServlet",
                           success: function(msg) {
                              var jsonArray = JSON.parse(msg);
                              var x =new Array(); 
                              var y1 =new Array();  
                              var y2 =new Array(); 
                              var y3 =new Array();                          
                              for(var i = 0;i<jsonArray.length;i++){
                                 var json = jsonArray[i];
                                 var html ="<tr><td>"+json.week_day+"</td>";
                                 html+="<td>"+json.search_sum+"</td>";
                                 html+="<td>"+json.wfcl_sum+"</td>";
                                 html+="<td>"+json.pay_success_sum+"</td></tr>";
                                 $("table").append(html);
                                 x[i]=json.week_day;
                                 y1[i]=json.search_sum;
                                 y2[i]=json.wfcl_sum;
                                 y3[i]=json.pay_success_sum;
                              }
                               myChart.setOption({
							        xAxis: {
							            data: x
							        },
							        series: [
							       // 根据名字对应到相应的系列
							        {
							            name: '违法查询',
							            data: y1
							        },
							        {
							            name: '违法处理',
							            data: y2
							        },
							        {
							            name: '违法缴费',
							            data: y3
							        }
							        ]
							    });
                           }
                           });
	       });

		</script>    
  </body>
</html>
