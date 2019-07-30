<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>" >   
    <!--<base href="http://whweb.j2eeall.com/Scan/"> -->       
    <title>详细违法信息</title>   
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
	<script src="js/jquery-1.11.0.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <script src="js/myjs.js"></script>
	<link href="css/bootstrap.min.css" rel="stylesheet" media="screen">
	<style type="text/css">
			.container{
        	  width: 375px;
        	  margin:0 auto;
        	}
         	 img{
        	  width: 100%;
        	  height: 100%; 
        	  background: url(img/loading2.gif)  no-repeat;
        	  background-size:100% 100%;
  		      position:absolute;
        	} 
		</style>
	</style>
  </head> 
  <body>
  	 <nav id="dhl" class="navbar navbar-default navbar-fixed-top" style="min-height:34px;background-color:#5BC0DE;" >
        <span id="back" class="glyphicon glyphicon-chevron-left" style="color:white;font-size:25px;" aria-hidden="true">返回</span>
      </nav>
    <div class="container">
     <br/> <br/><br/>
        <c:choose>
        <c:when test="${jdsbh=='000000000000000' }">
  	      <div class="text-center" style="font-size:12px;color:red;">
  	      <span>该电子处罚书正在生成中,请稍后重试！</span>
  	      <img  src="img/loading3.gif">
  	      </div>
  	    </c:when>
  	    <c:otherwise>
  	      <div class="text-center" style="font-size:12px;color:red;">
          <span class="glyphicon glyphicon-hand-right"></span>  
  	      <span>提示:长按可保存电子处罚单！</span>
  	       </div>
           <img  src="GetImgServlet?jdsbh=${jdsbh}&value=${value}">  
        </c:otherwise>
        </c:choose>
    </div>
    <script type="text/javascript">			
		  $("#back").click(function() {
               window.location.href=$("base").attr("href")+"Scan";
           });
	</script>
  </body>
</html>