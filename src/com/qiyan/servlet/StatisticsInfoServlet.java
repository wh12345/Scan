package com.qiyan.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.qiyan.service.StatisticsService;

public class StatisticsInfoServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		StatisticsService ss = new StatisticsService();
		String ret = ss.queryStatisticsBeforeDay(7);
		PrintWriter pw = response.getWriter();
		if(ret!=null) {
			pw.write(ret);			
		}
	}

}
