package com.qiyan.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.qiyan.bean.Pay;
import com.qiyan.dao.PayDao;

public class GetPayResultServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	       request.setCharacterEncoding("utf-8");
	       response.setContentType("text/html;charset=utf-8");
	       String ddbh = request.getParameter("ddbh");
	       Pay pay = PayDao.queryPayByDdbh(ddbh);
           SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
           Date date = new Date();
		   try {
				date = sdf.parse(pay.getP_time());
			} catch (ParseException e) {
				e.printStackTrace();
			}
           SimpleDateFormat sdf2=new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
           pay.setP_time(sdf2.format(date));
	       JSONObject json = (JSONObject) JSONObject.toJSON(pay);
	       String payResult=json.toString();
	       PrintWriter out =response.getWriter();
	       out.print(payResult);

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
         	this.doGet(request, response);
	}

}
