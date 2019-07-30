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
    <title>缴费错误页面</title>   
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
  </head>
  <body>
       <% String status = (String)request.getParameter("status"); %> 
      <font style="color:red;"><h4>状态:<%=status %></h4>
      <h4>信息:
      <%if(status.equals("0001")) {%>
        <span>签名不一致</span>
      <%}else if(status.equals("0006")) { %>
         <span>又不为空的参数为空</span>
      <%}else if(status.equals("0011")) { %>
       <span>实名验证失败</span>
      <%}else if(status.equals("9999")) { %>
       <span>其他原因失败</span>
      <%}else if(status.equals("0015")){ %>
       <span>数据格式不正确</span>
      <%}else{ %>
         <span>未知错误！</span>
      <%} %>
      </h4>
      </font>
  </body>
</html>
