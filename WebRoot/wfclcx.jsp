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
    <title>违法查询</title>    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
	<script src="js/jquery-1.11.0.min.js"></script>
	<script src="js/bootstrap.min.js"></script>
	<script src="js/sweetalert.min.js"></script>
	<script src="js/myjs.js"></script>
	<link href="css/sweetalert.css" rel="stylesheet" media="screen" />
	<link href="css/bootstrap.min.css" rel="stylesheet" media="screen">
    <link href="css/mycss.css"  rel="stylesheet" media="screen">
</head>
  <body>
    <div id="form">
		<label for="hphm" >号牌号码:</label> 
		<div class="form-inline">
		<select id="jc" name="jc" style="width:115px;" > 
		   <option value="粤">粤</option>
		</select>
		<input type="text" id="hphm" name="hphm"  required placeholder="请输入号牌号码" style="width: 180px;" onkeyup="toUpperCase(this)"/>
		</div>	
		<label for="hpzl">号牌种类:</label> 
		<select id="hpzl" name="hpzl">
			<option value="02">小型汽车</option>
		</select>
		<label for="cjh">车架号后六位:</label> 
		<input type="text" id="cjh" name="cjh" required placeholder="请输入车架号后六位" maxlength="6"  />
	   <div class="form-inline">
		<input type='text' id="code" required class='txtVerification' maxlength='4' placeholder='验证码' style="width:115px;"/>
		 &emsp;<img id="codeimg" height="29px" width="80px" src="${pageContext.request.contextPath }/createCode" onclick="changeImg()">&emsp;<a href="javascript:void(0);" onclick="changeImg()">看不清，换一张</a>
		</div>
		<button id="search"  type="submit" class="btn btn-primary" style="width:300px;" >
		 <span class="glyphicon glyphicon-search"></span>查询</button>
	 </div>
  </body>
  <script type="text/javascript">
        $(function() {
        	//加载号牌号码简称和号牌种类
        	 $.ajax({
        	 		  async:false,
                      type: "GET",
                      url: "GetJcHpzlServlet",
                      success: function(msg) {
                        var jsonArray = JSON.parse(msg);
                        for(var i in jsonArray) {
                        if(jsonArray[i].kind=="HPZL") {
                          var tmp ="<option value='"+jsonArray[i].code+"'>"+jsonArray[i].detail+"</option>";
                          $("#hpzl").append(tmp);
                        }else{
                          var tmp ="<option value='"+jsonArray[i].code+"'>"+jsonArray[i].detail+"</option>";
                          $("#jc").append(tmp);                      
                        }
                        }
                      }
                 });
             });
           
          //检验输入的信息   
        $("button").click(function(){
  	       var jc = $("#jc").val();
           var hphm = $("#hphm").val().trim().toUpperCase(); 
           var temp = jc+hphm;      
           var reg1 = /(^[\u4E00-\u9FA5]{1}[A-Z0-9]{6}$)|(^[A-Z]{2}[A-Z0-9]{2}[A-Z0-9\u4E00-\u9FA5]{1}[A-Z0-9]{4}$)|(^[\u4E00-\u9FA5]{1}[A-Z0-9]{5}[挂学警军港澳]{1}$)|(^[A-Z]{2}[0-9]{5}$)|(^(08|38){1}[A-Z0-9]{4}[A-Z0-9挂学警军港澳]{1}$)/;
           if(hphm=="") {
             swal("","请输入车牌号码！","warning");
           }else if(!reg1.test(temp)) {
             swal("","你输入的车牌号码格式不正确,请重新输入！","warning");
           }else{
           	var hpzl = $("#hpzl").val();
       		var cjh = $("#cjh").val().trim();
       	    if(cjh=="") {
       	        swal("","请输入车架号!","warning");
       		}else if(cjh.length!=6) {
       			swal("","您输入的车架号长度不够!","warning");
       		}else{
       		      var code = $("#code").val();
       		      if(code=="") {
       		        swal("","请输入验证码!","warning");
       		      }else{
	              $("#search").empty();
	              $("#search").append("<font>查询中...</font>");
	              $("#search").attr("disabled","disabled");
               	   $.ajax({
                       type: "POST",
                       url: "WfclcxServlet",
                       data: "jc="+jc+"&hphm="+hphm+"&hpzl="+hpzl+"&cjh="+cjh+"&code="+code,
                       success: function(msg) { 
                           $("#search").empty();
                           $("#search").append("<span class='glyphicon glyphicon-search'></span>查询");  
                           changeImg();                    
                           if(msg=="0") {
                             swal("","你今天输错次数超过限制！","warning");                     
                           }else if(msg=="1"){                             
                             $("#search").attr("disabled",false);
                             wait();
                             window.location.href=$("base").attr("href")+"WfclcxServlet?jc="+encodeURI(encodeURI(jc))+"&hphm="+encodeURI(encodeURI(hphm))+"&hpzl="+hpzl+"&cjh="+cjh+"&flag=flag";
                           }else if(msg=="2"){
                             swal("","验证码不正确!","warning");
                             $("#search").attr("disabled",false);                            
                           }else if(msg=="-1"){
                             swal("","系统异常,请稍后重试!","error");
                             $("#search").attr("disabled",false);	  
                           }else{		                              
                             swal("",msg,"error");
                             $("#search").attr("disabled",false);	
                           }                          
                       },
                       error: function(){
                           swal("","发送请求失败,请稍后重试!","error");
                           $("#search").attr("disabled",false);	
                           $("#search").empty();
                           $("#search").append("<span class='glyphicon glyphicon-search'></span>查询");                                                        
                           changeImg(); 
                        }                          
	              });	
        		}
        		}
           	 }                    
        }); 
        //把小写字母转成大写
        function toUpperCase(obj){
		   obj.value = obj.value.toUpperCase();
		 }	
		 //改变验证码事件
		function changeImg(){
		    var src = "${pageContext.request.contextPath }/createCode?x=" + Math.floor(Math.random()*100);
		    $("#codeimg").attr("src",src);
		}
  </script>
</html>
