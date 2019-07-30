package com.qiyan.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.qiyan.dao.Dao;


public class PayRetServlet extends HttpServlet {


	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		String jylx = request.getParameter("jylx");
		String status = request.getParameter("status");
		String time = request.getParameter("time");
		String ddbh = request.getParameter("ddbh");
		String sign = request.getParameter("sign");
		String[] params =null;
		if("0000".equals(status)) {
			System.out.println("缴费成功:jylx:"+jylx+" status:"+status+" time"+time+" ddbh:"+ddbh+" sign:"+sign);
			params= new String[]{"3",time,ddbh,status};
			String sql1="update tb_vio_violation set jkbj='1' where jdsbh=(select tzsbh from sys_pay where ddbh=?)";
			String sql2="update tb_vio_surveil_wfcl set jkbj='1' where jdsbh=(select tzsbh from sys_pay where ddbh=?)";
			String[] jfbjparam=new String[]{ddbh};
			Dao.update(sql1, jfbjparam);
			Dao.update(sql2, params);

		}else {
			System.out.println("缴费失败:jylx:"+jylx+" status:"+status+" time"+time+" ddbh:"+ddbh+" sign:"+sign);
			params= new String[]{"1",time,ddbh,status};
		}
		String sql ="update sys_pay set jfbj=?,p_time=to_date(?,'yyyyMMddHH24miss'),status=? where ddbh=?";
		Dao.update(sql, params);
	} 

}
