package com.qiyan.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.qiyan.dao.WfclDao;
public class WfclPayServlet extends HttpServlet {

	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		String  jdsbh = request.getParameter("jdsbh");
		Map<String, String> map = WfclDao.queryZxdwbh(jdsbh);
		if(map!=null&&map.get("ret").equals("1")) {
			String  zxdwbh =map.get("zxdwbh");
			String  url = "Pay?jdsbh="+jdsbh+"&zxdwbh"+zxdwbh;
			response.sendRedirect(url);
		}else{
			System.out.println(jdsbh+"查询不到执行单位编号");
		}
		
	}

	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		this.doGet(request, response);
	}

}
