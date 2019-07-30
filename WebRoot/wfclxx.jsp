<%@ page language="java" import="java.util.*,com.qiyan.bean.Wfcl" pageEncoding="utf-8"%>
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
    <title>违法信息</title>   
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
	<link href="css/bootstrap.min.css" rel="stylesheet" media="screen">
	<script src="js/jquery-1.11.0.min.js"></script>
	<script src="js/bootstrap.min.js"></script>
    <script src="js/sweetalert.min.js"></script>
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
	<link href="css/sweetalert.css" rel="stylesheet" media="screen" />
  </head>
  <body>
      <nav id="dhl" class="navbar navbar-default navbar-fixed-top" style="min-height:36px;background-color:#5BC0DE;" > 
  	    <div class="text-left" style="font-size:30px;color:white;"><font style="font-size:25px;"><span id="back" class="glyphicon glyphicon-chevron-left"  aria-hidden="true">返回</span></font>&emsp;&emsp;&emsp;<span>违法记录</span></div>
      </nav>
 	    <div class="container">
 	    <br/><br/><br/>
 	    <c:if test="${wfcls.size()>0 }">
 	    <table class="table table-bordered">
 	    <c:forEach items="${wfcls}" var="wfcl" varStatus="varSta">
 	    <tr>
     		<td width="10px" style="display:table-cell; vertical-align:middle" id="${varSta.count}">${varSta.count}</td>
         	<td  >		
	         	<ul class="list-group">
			    <li class="list-group-item">车牌号码:<a>${wfcl.hphm } &emsp;${wfcl.ylzz1}</a></li>
			    <li class="list-group-item">违法时间:<a>${wfcl.wfsj }</a></li>
			    <li class="list-group-item"><p>违法地点:<a>${wfcl.wfdz }</a></p></li>
			    <li class="list-group-item"><p>违法行为:<a>${wfcl.wfxw}</a></p></li>
			    <li class="list-group-item">违法记分:<a>${wfcl.wfjfs }</a></li>
			    <li class="list-group-item">罚款金额:<a>${wfcl.fkje }</a></li>
			    <c:if test="${wfcl.clbj=='0' }">
			    <li class="list-group-item">处理状态:<a>未处理</a></li>
			    	<li class="list-group-item" style="text-align: -webkit-center;">		            
		        	<button type="button"  class="btn btn-primary" name="cl" value="${wfcl.xh},${wfcl.hphm},${wfcl.hpzl}"><span class="glyphicon glyphicon-tags"></span> 处理(本人)</button>		        
		        </li>
			    </c:if>			    
			    <c:if test="${wfcl.clbj=='1'&&wfcl.jkbj=='0' }">
			    <c:set var="flag" value="flag" scope="page"></c:set>
			    <li class="list-group-item">决定书编号:<a>${wfcl.jdsbh }</a></li>
			    <li class="list-group-item">处理状态:<a>未缴费</a></li>
			    <li class="list-group-item" style="text-align: -webkit-center;">		            
		        	<button type="button"  class="btn btn-primary" name="jf" value="${wfcl.jdsbh }"><span class="glyphicon glyphicon-credit-card"></span> 缴费</button>		        
		        </li>
			    </c:if>
			    <c:if test="${wfcl.clbj=='1'&&wfcl.jkbj=='1' }">
			       <li class="list-group-item">决定书编号:<a>${wfcl.jdsbh }</a></li>
				    <li class="list-group-item">处理状态:<a>已缴费</a></li>
			    </c:if>
	         	</ul>
         	</td>
         	</tr>	
         	</c:forEach>
         </table>
         <c:if test="${flag.length()>0 }">
           <div class="text-center" style="font-size:16px;color:red;"><span class="glyphicon glyphicon-exclamation-sign"></span><span style="color:red;">提示:请在15日内缴纳罚款，逾期不缴纳的，每日按罚款数额的3%加处罚款。</span></div>
         </c:if>
         </c:if>	
         	<c:if test="${wfcls.size()==0}">
         	  <div class="text-center" style="font-size:12px;color:red;">  
		          <span class="glyphicon glyphicon-hand-right"></span> 
		  	      <span>暂未查询到违章信息！</span>
  	          </div>
         	</c:if>
         </div>
      <c:if test="${wfcls!=null }">
         <script type="text/javascript">
             $("button[name=cl]").click(function() {
                wait();
                var val =$(this).attr("value");
                var str =val.split(",");
		        $.ajax({
                       type: "POST",
                       url: "WfclxxServlet",
                       data: "xh="+str[0]+"&hphm="+str[1]+"&hpzl="+str[2],
                       success: function(msg) {
                          var ret =JSON.parse(msg);                    
                          var retCode =ret.retCode;                         
                          if(retCode=="1"){
                            var dsr=ret.dsr,jszh=ret.jszh,dabh=ret.dabh,fkje=ret.fkje,wfjfs=ret.wfjfs,phone=ret.phone;
                            var obj ={xh:str[0],hphm:str[1],hpzl:str[2],dsr:dsr,jszh:jszh,dabh:dabh,fkje:fkje,wfjfs:wfjfs,phone:phone};
			                var jsonStr=JSON.stringify(obj);
			                window.location.href=$("base").attr("href")+"wfcl.jsp?jsonStr="+ encodeURI(jsonStr);
                          }else if(retCode=="2"){ 
                            swal("",ret.info,"warning");
                          }else{
                            swal("","系统异常！","error");
                          }
                      }
                   });                                                
             });
              $("button[name=jf]").click(function() {
                wait();
                var val =$(this).attr("value");
                window.location.href=$("base").attr("href")+"Pay?jdsbh="+ val;
             });
             $("#back").click(function() {
               window.location.href=$("base").attr("href")+"WxCodeServlet?flag=1";
             });
             function wait() {swal({title: "请稍后..",showConfirmButton: false,imageUrl: "img/loading.gif",showCancelButton: false});window.setTimeout(function() {$(".sweet-overlay").css("display","none");$(".sweet-alert").css("display","none");},30000);}
         </script>
         </c:if>
  </body>
</html>
