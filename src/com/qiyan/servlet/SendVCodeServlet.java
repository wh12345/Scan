package com.qiyan.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.qiyan.bean.SVCode;
import com.qiyan.dao.Dao;
import com.qiyan.service.SendVCode;
import com.qiyan.util.SystemUtil;

public class SendVCodeServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		String hphm = request.getParameter("hphm");
		String jszh = request.getParameter("jszh");
		String phone =request.getParameter("phone");
		String fkje =request.getParameter("fkje");
		String wfjfs = request.getParameter("wfjfs");
		SendVCode sv = new SendVCode();
		//查询手机号码
		/*Map<String, String> map = sv.queryPhone(hphm, hpzl);
		String phone = map.get("outStr");
		String ret = map.get("ret");*/
		PrintWriter pw =response.getWriter();
		HttpSession session = request.getSession();	    
	    boolean flag = sv.querySendCount(hphm,phone);
	    System.err.println(phone);
	    if(phone!=""||phone!=null) {
	    	if(SystemUtil.checkCellphone(phone)) {
		     if(flag) {
		    	String vcode =  sv.sendVcode(phone, jszh, hphm, fkje, wfjfs);
		    	if(vcode.equals("-1")) {
		    		pw.print(4);
		    	}else{			    		
		    		pw.print(phone);
				    String sql = "update sys_vcode set send_count=send_count+1 where phone=?";
					String[] params = new String[]{phone};
					System.out.println(phone+" "+params);
					Dao.update(sql, params);
					SVCode svc = new SVCode(vcode,0,new Date());
					session.setAttribute("svc", svc);
		    	}
		    }else{
		    	pw.print(0);
		    }
	    	}else{
	    		pw.print(3);
	    	}
	}else{
		pw.print(2);
	}
	}
}
