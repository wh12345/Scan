package com.qiyan.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.qiyan.service.WfclxxService;

public class WfclxxServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
			this.doPost(request, response);
	}


	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		response.setContentType("text/html");
		String  xh = request.getParameter("xh");
		String hphm = request.getParameter("hphm");
		String hpzl = request.getParameter("hpzl");
		WfclxxService wcs =new WfclxxService();
		String ret = wcs.queryWfclInfo(xh, hphm, hpzl);
		PrintWriter pw = response.getWriter();
		pw.write(ret);
	}

}
