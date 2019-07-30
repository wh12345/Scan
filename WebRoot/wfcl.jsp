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
    <title>违法处理</title>   
    <meta content="text/html; charset=utf-8" http-equiv="Content-Type"/>
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
     <!--  <form action="WfclServlet" method="post" >  -->
		<label for="dsr">当事人姓名:</label> 
		<input type="text" id="dsr" name="dsr"  readonly="readonly" />
		<label for="jszh">驾驶证号:</label> 
		<input type="text" id="jszh" name="jszh"  readonly="readonly"/>
		<!-- <div class="text-left" style="font-size:16px;color:red;"><span class="glyphicon glyphicon-hand-right"></span><span style="color:red;">驾驶证号须与机动车所有人身份证号一致</span></div><br/> -->
		 <label for="dabh">驾驶证档案号:</label> 
		<input type="text" id="dabh" name="dabh"   readonly="readonly"/> 
		<label for="vcode">手机短信验证码:</label> 
		<div class="form-inline">
		<input type="text" id="vcode" name="vcode" style="width: 150px;" required maxlength="4"/>
		<button type="button" id="send" class="btn btn-info" style="width:140px;">
		<span class="glyphicon glyphicon-comment"></span>&emsp;获取验证码</button>
		</div>
		<div id="tips"></div>
		<button id="cl" type="submit" class="btn btn-primary" style="width:300px ;"><span class="glyphicon glyphicon-ok-circle"></span>确定</button>
		<div class="text-left" style="font-size:16px;color:red;"><span class="glyphicon glyphicon-exclamation-sign"></span><span style="color:red;">提示:处理成功后,请在15日内缴纳罚款，逾期不缴纳的，每日按罚款数额的3%加处罚款。</span></div>
		<button id="back" type="button" class="btn btn-primary" style="width:300px ;"><span class="glyphicon glyphicon-home"></span>返回</button>
		
	<!-- </form>  -->
	</div>
	<script type="text/javascript" >
	           //定义要用的变量
	           var xh,hphm,dsr,jszh,dabh,fkje,wfjfs,phone;
	          //间隔执行id
	           var id=null;
	           var num=120;
	           var phone = "";
               $(function() {
               	    var str=decodeURI(location.href); //取得解码后的地址栏
					var num=str.indexOf("="); 
					var jsonstr=str.substr(num+1);
					var json =JSON.parse(jsonstr);
					xh=json.xh,hphm=json.hphm;dsr =json.dsr;jszh =json.jszh;dabh =json.dabh;fkje =json.fkje;wfjfs =json.wfjfs;phone =json.phone;					
					$("#dsr").val(dsr);
               		$("#jszh").val(jszh);
               		$("#dabh").val(dabh);              		 
               });

		       $("#send").click(function() {		            
		            swal({title: "请稍后..",showConfirmButton: false,imageUrl: "img/loading1.gif",showCancelButton: false});
					var jszh1 = jszh.substring(0,4)+"*****"+jszh.substring(14,18);  
			        $.ajax({
                           type: "POST",
                           url: "SendVCodeServlet",
                           data: "hphm="+hphm+"&jszh="+jszh1+"&phone="+phone+"&fkje="+fkje+"&wfjfs="+wfjfs,
                           success: function(msg) {	
                              $(".sweet-overlay").css("display","none");
				              $(".sweet-alert").css("display","none");
				              $("#send").attr("disabled","ture"); 
                              var tips = $("#tips");
                              var html ="";
                              if(msg==0) {
                                html="<marquee><span class='glyphicon glyphicon-bullhorn'></span><font>今天发送验证码次数已达到上限！</font></marquee>";                               
                              }else if(msg==2){
                                html="<marquee><span class='glyphicon glyphicon-bullhorn'></span><font>对不起,你未绑定车辆联系方式！</font></marquee>";                               
                              }else if(msg==3){
                              	html="<marquee><font><span class='glyphicon glyphicon-bullhorn'></span>你绑定的手机号码不正确,请到车管所业务窗口办理车辆联系方式变更业务!</font></marquee>";                              
                              }else if(msg==4){
                                myInterval();
                                html="<marquee><span class='glyphicon glyphicon-bullhorn'></span><font>发送验证码失败,请稍后重试！</font></marquee>";
                              }else{
                                var temp = phone.substring(0,3)+"*****"+phone.substring(8,11);               
                                myInterval();
                                html="<marquee><span class='glyphicon glyphicon-bullhorn'></span><font>已经将验证码发送到"
                                html+=temp;
                                html+=",请查收后填入验证码。如手机号码有误,请到车管所业务窗口办理车辆联系方式变更业务!</font></marquee>";                              
                              }  
                              tips.append(html);                                                           
                              tips.css({"display":"inline","color":"red","height":"25px"});                                                                                                   
                           }                      
                           });       	       
		       });
		       function myInterval() {		          
		          id=window.setInterval(function(){
		            if(num>0) {
		             var html="<font>剩余("+num+"秒)</font>";
		             $("#send").empty();
		             $("#send").append(html);
		             num--;
		            }else{
		              $("#send").empty();
                      var html="<span class='glyphicon glyphicon-comment'></span>&emsp;获取验证码</span>";
		              $("#send").append(html);
		              $("#send").attr("disabled",false);
		              window.clearInterval(id);
		              num=120;
		              $("#tips").empty(); 
		              $("#tips").css("display","none");
		            }	             
		          },1000);		       
		       }	
		       
		       $("#cl").click(function(){
		          /* var dsr=$("#dsr").val();
		          var jszh=$("#jszh").val();
		          var dabh=$("#dabh").val(); */
		          var vcode=$("#vcode").val();
		          if(phone=="") {
		             swal("","你还未发送短信验证码!","warning");		             
		          }else if(vcode=="") {
		             swal("","短信验证码不能为空!","warning");	
		          }else{
		             $("#cl").empty();
		             $("#cl").append("<font>处理中...</font>");
		             $("#cl").attr("disabled","disabled");
		             $.ajax({
                           type: "POST",
                           url: "WfclServlet",
                           data: "xh="+xh+"&dsr="+dsr+"&jszh="+jszh+"&dabh="+dabh+"&vcode="+vcode,
                           success: function(msg) {
                              $("#cl").empty();
                              $("#cl").append("<span class='glyphicon glyphicon-ok-circle'></span>确定"); 
                              $("#cl").attr("disabled",false);
                              var json =JSON.parse(msg); 
                              var ret=json.ret;                 	
                              if(ret=="0") {
                                  swal("","你发送的验证码已过期,请重新发送！","warning");
                                  $("#vcode").val("");
                                  phone="";
                              }else if(ret=="2") {
                                var info = json.info;
                                swal("",info,"error");
                              }else if(ret=="3") {
                                swal("","你填写的验证码不正确!","error");
                                $("#vcode").val("");
                              }else if(ret=="4") {
                                  swal("","你已经连续输错验证码5次，请重新发送短信验证码！","warning");
                                  $("#send").empty();
			                      var html="<span class='glyphicon glyphicon-comment'></span>&emsp;获取验证码</span>";
					              $("#send").append(html);
					              $("#send").attr("disabled",false);
					              window.clearInterval(id);
					              num=120;
					              $("#tips").empty();
					              $("#tips").css("display","none");
                              }else{
                                var jdsbh=json.jdsbh;
                                wait();                           
                                window.location.href=$("base").attr("href")+"WfclServlet?xh="+xh+"&hphm="+encodeURI(encodeURI($("#hphm").val()))+"&jdsbh="+jdsbh;
                              }
                           }
                           });
		          }
		       });
		     $("#back").click(function() {
               window.location.href=$("base").attr("href")+"WxCodeServlet?flag=1";
             }); 
	</script>
  </body>
</html>
