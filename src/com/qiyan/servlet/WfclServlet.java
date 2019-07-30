package com.qiyan.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.qiyan.bean.SVCode;
import com.qiyan.bean.Wfcl;
import com.qiyan.service.WfclService;
import com.qiyan.service.WfclcxService;
import com.qiyan.util.SystemUtil;

public class WfclServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		String jdsbh = request.getParameter("jdsbh");
		PrintWriter pw = response.getWriter();
		WfclService ws = new WfclService();
		//模拟网速慢
		/*try {
			Thread.sleep(6000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}*/
		if(jdsbh==null) {
			String vcode = request.getParameter("vcode");
			HttpSession session = request.getSession();		
			SVCode svc = (SVCode) session.getAttribute("svc");
			System.out.println("svc1"+svc);
			Date sendDate = svc.getSendTime();
            SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
            String date =sdf.format(sendDate);
            Long interval = SystemUtil.getInterval(date);
            String ret ="";
			if(interval<300L) {
			 int v_vcodeerr_count = Integer.parseInt(SystemUtil.getConfigByStringKey("V_VCODEERR_COUNT"));
			  if(svc.getCount()<v_vcodeerr_count) {
				  if(vcode.equals(svc.getVcode())) {
					  String xh = request.getParameter("xh");
					  String dsr =request.getParameter("dsr");
					  String jszh = request.getParameter("jszh");
					  String dabh = request.getParameter("dabh");
					  String openid = (String) session.getAttribute("openid");
					  ret = ws.wfcl(xh, dsr, jszh, dabh,openid);
					  System.out.println("ret"+ret);
				  }else{
					  svc.setCount(svc.getCount()+1);
					  session.setAttribute("svc", svc);
					  System.out.println("svc2"+svc);
					  ret ="{\"ret\":\"3\"}";
				  }
			  }else{
				  ret ="{\"ret\":\"4\"}";
			  }
			}else{
				ret ="{\"ret\":\"0\"}";
			}
			pw.write(ret);
		}else{
			String xh = request.getParameter("xh");
			System.err.println("xh:"+xh);
			ws.updateIsCl(jdsbh,xh);
			String hphm = URLDecoder.decode(request.getParameter("hphm"), "utf-8");
			WfclcxService wcs = new WfclcxService();
			List<Wfcl> wfcls = wcs.queryWfclsByXh(xh);
			request.setAttribute("wfcls", wfcls);
			request.getRequestDispatcher("wfclxx.jsp").forward(request, response);
		}
	}

}
