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
    <title>缴费结果</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
	<script src="js/jquery-1.11.0.min.js"></script>
	<script src="js/bootstrap.min.js"></script>
	<link rel="stylesheet" href="css/bootstrap.min.css" />
	<style type="text/css">
			.container{
        	  width: 360px;
        	  margin:0 auto;  
        	}
	</style>
  </head>
  <body>
    <nav id="dhl" class="navbar navbar-default navbar-fixed-top" style="min-height:34px;background-color:#5BC0DE;" >
        <span id="back" class="glyphicon glyphicon-chevron-left" style="color:white;font-size:25px;" aria-hidden="true">返回</span>
      </nav>
     <div class="container">
            <br/><br/>
			<div class="text-center"><h4>
				<span class="glyphicon glyphicon-search"></span>
				<span id = "msg">正在查询缴费结果，请稍等！如不想等待结果，可点击主页返回违法信息界面！</span>
			</h4></div>
			<ul class="list-unstyled" class="text-left" > 
		       <li>
		       	 <ul class="list-inline">
		       	 	<li>订单编号:</li>
		       	 	<li><a id="ddbh"></a></li>
		       	 </ul>
		       </li>
		       <br/>
		       <li>
		       	 <ul class="list-inline">
		       	 	<li>订单时间:</li>
		       	 	<li><a id="date"></a></li>
		       	 </ul>
		       </li>
		        <br/>
		       <li>
		       	 <ul class="list-inline">
		       	 	<li>缴费状态:</li>
		       	 	<li><span id="search"><a>查询中</a><img src="img/wait.gif" style="width: 60px;height: 45px;"/></span></li>
		       	 </ul>
		       </li>
			</ul>
		    <div class="text-right">
		    	<button class="btn btn-info" type="button" id="ret">主页 <span class="glyphicon glyphicon-chevron-right"></span></button>
		    </div>
		</div>
  </body>
  <script type="text/javascript">
    	var ddbh="<%=request.getParameter("ddbh")%>";
  		var id;
		$(function(){
		 //给按钮添加触发事件
		  $("#ret").on("click",function() {
		    window.location.href=$("base").attr("href")+"WxCodeServlet";
		  });
		  id = window.setInterval(
		   function() {
		      $.ajax({
                 type: "GET",
                 url: "GetPayResultServlet",
                 data: "ddbh="+ddbh,
                 success: function(msg) {
                   var obj=JSON.parse(msg);
                   $("#ddbh").text(obj.ddbh);
                   $("#date").text(obj.p_time);
                   if(obj.jfbj=="3") {
                      $("#msg").text("查询结果如下:");
                      $("#search").html("<a>缴费成功</a><span class='glyphicon glyphicon-ok-circle'></span>");
                       window.clearInterval(id);
                   }else if(obj.jfbj=="1"){
                    $("#msg").text("查询结果如下:");
                     $("#search").html("<a>缴费失败</a><span class='glyphicon glyphicon-remove-circle'></span>");
                      window.clearInterval(id);           
                   }
                   }
               });
		   },3000);
		   });
		   $("#back").click(function() {
               window.history.back();
           });
	</script>
</html>
