package com.qiyan.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.qiyan.bean.JcHpzl;
import com.qiyan.bean.Wfcl;
import com.qiyan.dao.WfclDao;
import com.qiyan.service.WfclcxService;

public class WfclcxServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		String jc = URLDecoder.decode(request.getParameter("jc"), "utf-8");	
		String hphm = jc+URLDecoder.decode(request.getParameter("hphm"),"utf-8");		
		String hpzl = request.getParameter("hpzl");
		String cjh = request.getParameter("cjh");
		String flag = request.getParameter("flag");
		String vcode = request.getParameter("code");
		System.out.println("hphm:"+hphm+" hpzl:"+hpzl+" cjh:"+cjh);
		WfclcxService ws = new WfclcxService();
		PrintWriter pw = response.getWriter();
		/*try {
			Thread.sleep(6000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}*/
		if(flag==null) {
			HttpSession session = request.getSession();
			//获取验证码
			String code = (String) session.getAttribute("code");
			System.out.println("服务器验证码:"+code+"上传验证码:"+vcode);
			if(code.toLowerCase().equals(vcode.toLowerCase())) {
				String openid = (String) session.getAttribute("openid");
				Map<String, String>  retMap= ws.isWfcl(hphm, hpzl, cjh,openid);
				String ret =retMap.get("ret");
				System.out.println("查询违法记录ret:"+ret);				
				if(ret!=null) {
					if("2".equals(ret)) {
						pw.print(retMap.get("outStr"));				   
					}else{
						pw.print(ret);
					}
				}else{
					pw.print("-1");
				}
			}else{
				pw.print("2");
			}
		}else{ 
			List<Wfcl> wfcls = ws.queryWfcls(hphm);
			request.setAttribute("wfcls", wfcls);
			request.getRequestDispatcher("wfclxx.jsp").forward(request, response);
		}		
	}

}
