package com.qiyan.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.qiyan.service.InputPay;

public class InputPayServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
        this.doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		String  jdsbh = request.getParameter("jdsbh");
		String  jszh = request.getParameter("jszh");
		String  vcode = request.getParameter("code");
		HttpSession session =request.getSession();
		PrintWriter pw = response.getWriter();
		String code = (String) session.getAttribute("code");
		if(code.toLowerCase().equals(vcode.toLowerCase())) {
			String openid = (String) session.getAttribute("openid");
			InputPay ip = new InputPay();
			Map<String, String> map = ip.inputPay(openid, jdsbh, jszh);
			String flag =  map.get("flag");
			if(flag!=null) {			
					if("1".equals(flag)) {
						String zxdwbh = (String) map.get("zxdwbh");
						System.out.println("zxdwbh+jdsbh"+zxdwbh+jdsbh);
						pw.print(zxdwbh+jdsbh);
					}else{
						pw.print(flag);
					}
			}else{
				response.sendRedirect("/500jsp");
			}
		}else{
			pw.print("5");
		}
	}

}
