package com.qiyan.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONArray;
import com.qiyan.bean.JcHpzl;
import com.qiyan.dao.WfclDao;

public class GetJcHpzlServlet extends HttpServlet {

	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		 request.setCharacterEncoding("utf-8");
	       response.setContentType("text/html;charset=utf-8");
	       List<JcHpzl> list =WfclDao.queryJcHpzl();
	       if(list!=null) {
	    	   JSONArray jsonArray =(JSONArray) JSONArray.toJSON(list);
	    	   String json = jsonArray.toString();
	    	   PrintWriter writer = response.getWriter();
	    	   writer.print(json);
	       }
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	      this.doGet(request, response);  
	}

}
