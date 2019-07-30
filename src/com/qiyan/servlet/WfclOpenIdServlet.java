package com.qiyan.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.qiyan.service.WxService;

public class WfclOpenIdServlet extends HttpServlet {

	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
         this.doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	    request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		String code = request.getParameter("code");//获取code
	    WxService ws = new WxService();
	    String openid = ws.getOpenId(code);
	    System.out.println(" WfclOpenIdServlet openid" +openid);
		request.setAttribute("openid", openid);
		HttpSession session = request.getSession();
		session.setAttribute("openid", openid);
		response.sendRedirect("/Scan/wfclcx.jsp");
	}

}
