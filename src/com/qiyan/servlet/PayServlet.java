package com.qiyan.servlet;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.qiyan.bean.PayParam;
import com.qiyan.dao.WfDao;
import com.qiyan.dao.WfclDao;
import com.qiyan.service.WfPay;
import com.qiyan.util.SystemUtil;

public class PayServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
        String jdsbh = request.getParameter("jdsbh");
        String zxdwbh = request.getParameter("zxdwbh");
        WfclDao wcd = new WfclDao();
        if(zxdwbh==null) {
        	Map<String, String> map = wcd.queryZxdwbh(jdsbh);
        	zxdwbh = map.get("zxdwbh");
        }
        
        HttpSession session = request.getSession();
        String openid =(String) session.getAttribute("openid");
        
        WfPay wp = new WfPay();
        Map<String, String> retMap = wp.getPayToken(jdsbh,zxdwbh,openid);
        String status = retMap.get("status");
        if(status.equals("0000")) {
            String ddbh = retMap.get("ddbh");
            String token = retMap.get("token");
     		if(status.equals("0000")) {
     			PayParam pp = wp.getPayParam(ddbh, token);
     			//重定向发送请求
     			String payUrl = SystemUtil.getConfigByStringKey("PAY.URL")+"?&ddbh="
     					        +pp.getDdbh()+"&token="+pp.getToken()+"&sign="+pp.getSign();
     			response.sendRedirect(payUrl);	
     		}
        }else {
        	String url = "er.jsp?status="+status;
        	response.sendRedirect(url);	
        }		
	}

}
