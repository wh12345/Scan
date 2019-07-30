<%@ page language="java" import="java.util.*,com.qiyan.bean.Violation" pageEncoding="UTF-8"%>
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
    <title></title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <link href="css/sweetalert.css" rel="stylesheet" media="screen" />
	<link href="css/bootstrap.min.css" rel="stylesheet" media="screen">
	<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
	<script src="js/jquery-1.11.0.min.js"></script>
	<script src="js/bootstrap.min.js"></script>
    <script src="js/sweetalert.min.js"></script>
    <script src="js/myjs.js"></script>
	<style type="text/css">
         	table ul{
         		width:auto;
         		height: auto;
         		text-align: left;
         	}
         	/* .container{
        	  width: 370px;
        	  margin:0 auto;
        	} */
        	p{
       		  overflow : hidden;
			  text-overflow: ellipsis;
			  display: -webkit-box;
			  -webkit-line-clamp: 2;
			  -webkit-box-orient: vertical;       	
        	}
     </style>
	 <script type="text/javascript">
        $(function() {
            wx.config({
                //debug : true, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
                appId : '<%=(String)request.getAttribute("appId")%>', // 必填，公众号的唯一标识
                timestamp :'<%=(String)request.getAttribute("timestamp")%>', // 必填，生成签名的时间戳
                nonceStr :'<%=(String)request.getAttribute("nonceStr")%>', // 必填，生成签名的随机串
                signature : '<%=(String)request.getAttribute("signature")%>',// 必填，签名，见附录1
                jsApiList : ['checkJsApi','scanQRCode']
            // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
            });
        });
       
    </script> 
 </head>
<body>
  	<nav id="dhl" class="navbar navbar-default navbar-fixed-top" style="min-height:36px;background-color:#5BC0DE;" > 
  	    <div class="text-center" style="font-size:30px;color:white;"><span>电子处罚单列表</span></div>
      </nav>
        <div class="container">
     <div class="navbar navbar-inverse navbar-fixed-bottom" style="min-height:40px;">
            <div class="navbar-inner">
                <!--fluid 是偏移一部分-->
                <div class="container-fluid"  style="text-align:center; padding-top:5px;"> 
                  	<button  id="scanQRCode" type="button" class="btn btn-default btn-lg,btn btn-primary">
                		<span class="glyphicon glyphicon-qrcode" aria-hidden="true"></span>
                		扫一扫</button>
                	&emsp;&emsp; 
                	<button id="inputPay" type="button" class="btn btn-default btn-lg,btn btn-primary">
                    	<span class="glyphicon glyphicon-edit" aria-hidden="true"></span>
                    	输入缴费
                    </button>
                </div>
            </div>
        </div>
        <br/><br/><br/>
        <div class="text-center" style="font-size:12px;color:red;">  
          <span class="glyphicon glyphicon-hand-right"></span> 
  	      <span>提示:1.无信息可扫一扫添加 &emsp;2.扫一扫可直接缴费&emsp;3.输入决定书编号进行缴费</span>
  	    </div>
        <c:if test="${retList.size()>0 }">
        <table class="table table-bordered" id="tb">
            <c:forEach items="${retList}" var="v" varStatus="varSta">           
         	<tr>
     		<td width="10px" style="display:table-cell; vertical-align:middle"><a>${varSta.count }</a></td>
         	<td>		
	         	<ul class="list-group">
	         	<li class="list-group-item">决定书编号:<a>${v.jdsbh}</a></li>
			    <li class="list-group-item">车牌号码:<a>${v.hphm }</a></li>
			    <li class="list-group-item">违法时间:<a>${v.wfsj}</a></li>
			    <li class="list-group-item"><p>违法地点:<a>${v.wfdz}</a></p></li>
			    <li class="list-group-item"><p>违法行为:<a>${v.wfxw}</a></p></li>
			    <li class="list-group-item">违法记分:<a>${v.bz}</a></li>			    
			    <li class="list-group-item">罚款金额:<a>${v.fkje}</a></li>
			    <c:choose>
			    <c:when test="${v.jkbj==null }">
			      <li class="list-group-item">缴费标记:<a>暂无记录</a></li>
			     </c:when>
			     <c:when test="${v.jkbj=='9' }">
			    <li class="list-group-item">缴费标记:<a>不需要缴费</a></li>
			     </c:when>
			     <c:when test="${v.jkbj=='1' }">
			    <li class="list-group-item">缴费标记:<a>已缴费</a></li>
			    </c:when>
			    <c:otherwise>
			    <li class="list-group-item">缴费标记:<a>未缴费</a></li>
			    </c:otherwise>
			    </c:choose>
		        <li class="list-group-item" style="text-align: -webkit-center;">
		            <c:if test="${v.jkbj!=null&&v.jkbj=='0' }">
		        	<button type="button"  class="btn btn-primary" name="jf" value="${v.jdsbh }"><span class="glyphicon glyphicon-credit-card"></span> 缴费</button>
		        	</c:if>
		        	<button type="button"  class="btn btn-primary" name="xx" value="${v.jdsbh }"><span class="glyphicon glyphicon-list-alt"></span> 详细信息</button>
		        </li>
	         	</ul>
         	</td>
         	</tr>
         	</c:forEach>
         </table>
         </c:if>
         <c:if test="${retList.size()==0 }">
         	  <h5>暂无电子处罚单信息，扫一扫可添加！</h5>
         </c:if> 
    <script type="text/javascript">
    	  $("#inputPay").click(function() {
    	    swal({
			      title: "<small>请输入相关信息!</small>",
				  text: "<input type='text' name='myinput' id='jdsbh' maxlength='15' >"
			      +"<input type='text' name='myinput' id='jszh' maxlength='6' placeholder='驾驶证后六位'>"
			      +"<input type='text' id='code' class='txtVerification' maxlength='4' placeholder='验证码' />"
			      +"<img id='codeimg' height='26px' src='${pageContext.request.contextPath }/createCode' onclick='changeImg()'>&emsp;<a href='javascript:void(0);' onclick='changeImg()'>看不清，换一张</a>",
			      html: true,
			      type: "prompt",
			      showCancelButton: true,
			      cancelButtonText: '取消',
			      confirmButtonText: '确认',
			      closeOnConfirm: false
			 }, function(){			     
                 var jdsbh = $("#jdsbh").val();
                 var jszh = $("#jszh").val();
                 if(jdsbh.length!=15) {
                  alert("决定书编号格式不正确！"); 
                 }else {
                  if(jszh.length!=6) {
                 	alert("驾驶证后六位格式不正确！");
                  }else{
                    var code = $("#code").val();
					if(code=="") {
					  alert("验证码不能为空！");
					}else{
					      sweetAlert.disableButtons();					      
					  	  $.ajax({
                           type: "POST",
                           url: "InputPayServlet",
                           data: "jdsbh="+jdsbh+"&jszh="+jszh+"&code="+code,
                           success: function(msg) {
                                changeImg();
                                if(msg=="0") {
                                  alert("用户今天输错次数超过限制!");
                                  $(".sweet-overlay").css("display","none");
				                  $(".sweet-alert").css("display","none");
                                }else if(msg=="2"){
                                  alert("决定书编号或驾驶证号有误!");
                                  sweetAlert.enableButtons();
                                }else if(msg=="3"){
                                  alert("该处罚单已缴费或不存在！");
                                  sweetAlert.enableButtons();
                                }else if(msg=="4"){
                                  alert("用户给该车牌缴费超过限制!");
                                  sweetAlert.enableButtons();
                                }else if(msg=="5"){
                                  alert("验证码不正确!");
                                  sweetAlert.enableButtons();
                                }else if(msg=="E"){
                                  alert("系统异常,稍后重试!");
                                  sweetAlert.enableButtons();
                                }else{
                                   sweetAlert.enableButtons();
                                   wait();
                                   var zxdwbh = msg.substring(0,12);
                                   var jdsbh = msg.substring(12,28);
                                   window.location.href=$("base").attr("href")+"Pay?jdsbh="+jdsbh+"&zxdwbh="+zxdwbh; 
                                }                
                           }
                           });
					}
                  }
                 } 
             });
    	  });
          $("#scanQRCode").click(function() {
            wx.scanQRCode({
                // 默认为0，扫描结果由微信处理，1则直接返回扫描结果
                needResult : 1,
                scanType:["qrCode"],
                desc : 'scanQRCode desc',
                success : function(res) {
                    //扫码后获取结果参数赋值给Input
                    var resultStr = res.resultStr;
                    var msg =res.errMsg;
                    var flag =msg.substring(msg.indexOf(':')+1);
                    //商品条形码，取","后面的
                    if(flag == "ok") {
                      $.ajax({
                           type: "POST",
                           url: "BackInfoServlet",
                           data: "resultStr="+resultStr,
                           success: function(msg) {
                             if(msg == '-1') {
                               swal({title:"不是电子处罚单二维码！",timer:2000,showConfirmButton:false});
                             }else if(msg == '0') {
                                swal({title:"已添加，请勿重复扫码！",timer:2000,showConfirmButton:false});
                             }else if(msg.substring(0,4)=="4402") {
                                var zxdwbh = msg.substring(0,12);
                                var jdsbh = msg.substring(12,28);
                             	window.location.href=$("base").attr("href")+"Pay?jdsbh="+jdsbh+"&zxdwbh="+zxdwbh; 
                             }else {
                               var len = $("table tr").length;         
                               var obj=JSON.parse(msg);
                               var context="<tr><td width='120px' style='display:table-cell; vertical-align:middle'><a>"+(len+1)+"</a></td>";
                               context+="<td><ul class='list-group'>";
			                   context+="<li class='list-group-item'>决定书编号:<a>"+obj.jdsbh+"</a></li>";
			                   context+="<li class='list-group-item'>违法时间:<a>"+obj.wfsj+"</a></li>";
			                   context+="<li class='list-group-item'>违法地点:<a>"+obj.wfdz+"</a></li>";
			                   context+="<li class='list-group-item'>执勤民警:<a>"+obj.zqmj+"</a></li>";
			                   context+="<li class='list-group-item'>缴费标记:<a>"+obj.jkbj+"</a></li>";
			                   context+="<li class='list-group-item' style='text-align: -webkit-center;'>";
			                   var jdsbh = obj.jdsbh;
			                   if(jdsbh == 0) {
			                     context+="<button type='button'  class='btn btn-primary' id='jf' name='jf' value='"+jdsbh+"'><span class='glyphicon glyphicon-credit-card'> 缴费</button>";
			                   }
			                   context+="<button type='button'  class='btn btn-primary' id='xx' name='xx' value='"+jdsbh+"'><span class='glyphicon glyphicon-list-alt'> 详细信息</button>";
			                   context+="</li></ul></td></tr>";
                               $("table").append(context);
                               $("#jf").on("click",function(){
                                  wait();     
                                  var jdsbh=$(this).attr("value");
				                  window.location.href=$("base").attr("href")+"Pay?jdsbh="+jdsbh; 
                               });
                               $("#xx").on("click",function() { 
                                  wait();
                                  var jdsbh=$(this).attr("value");
				                  window.location.href=$("base").attr("href")+"WfInfo?jdsbh="+jdsbh; 
                               });
                             }
                           }
                        });
                    }else {
                       alert("扫码失败！");
                    }                    
                },
                error: function(){
				  alert("调用扫码失败！");
				}
            });
            });
           $("button[name=jf]").click(function() { 
                  wait(); 
                  var jdsbh=$(this).attr("value");
                  window.location.href=$("base").attr("href")+"Pay?jdsbh="+jdsbh;
           });
           $("button[name=xx]").click(function() {
                  wait();  
                  var jdsbh=$(this).attr("value");
                  window.location.href=$("base").attr("href")+"WfInfo?jdsbh="+jdsbh;
           });
		 //改变验证码事件
		function changeImg(){
		    var src = "${pageContext.request.contextPath }/createCode?x=" + Math.floor(Math.random()*100);
		    $("#codeimg").attr("src",src);
		}             
    </script>
</body>
</html>
