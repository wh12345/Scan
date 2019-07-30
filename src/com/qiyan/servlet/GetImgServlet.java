package com.qiyan.servlet;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.qiyan.service.PdfJpg;
import com.qiyan.util.SystemUtil;

public class GetImgServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	       request.setCharacterEncoding("utf-8");
	       response.setContentType("text/html;charset=utf-8");
	       String  jdsbh=request.getParameter("jdsbh");
	       String  value=request.getParameter("value");
	       byte[] btImg = null;
      	   String imgurl = SystemUtil.getConfigByStringKey("IMG.URL");
    	   String url=imgurl+"pdf"+File.separator+value+File.separator+jdsbh+".jpg";
    	   System.out.println("图片: "+url);
    	   btImg=SystemUtil.getImg(url);
	       OutputStream os = response.getOutputStream();
	       os.write(btImg);
	       os.flush();
	       os.close();

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
         	this.doGet(request, response);
	}

}
