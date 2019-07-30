package com.qiyan.servlet;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.qiyan.bean.Dzyz;
import com.qiyan.dao.WfDao;
import com.qiyan.service.PdfJpg;
import com.qiyan.util.SystemUtil;


public class WfInfoServlet extends HttpServlet {


	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
         this.doPost(request, response);
	}


	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		String jdsbh = request.getParameter("jdsbh");
		String value = WfDao.getImgDic(jdsbh);
		String urlStr =SystemUtil.getConfigByStringKey("IMG.URL")+"pdf"+File.separator+value+File.separator+jdsbh+".jpg";
		File file = new File(urlStr);
	    System.out.println("jdsbh"+jdsbh+"value"+value);
		if(file.exists()) {
			request.setAttribute("value", value);
			request.setAttribute("jdsbh", jdsbh);			
		}else{
			request.setAttribute("jdsbh", "000000000000000");	
		}
		request.getRequestDispatcher("/wfxx.jsp").forward(request, response);
	}

}
