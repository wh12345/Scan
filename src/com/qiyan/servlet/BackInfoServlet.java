package com.qiyan.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.alibaba.fastjson.JSONObject;
import com.qiyan.bean.Violation;
import com.qiyan.dao.WfDao;
import com.qiyan.util.AES;
import com.qiyan.util.SystemUtil;
/**
 * @author wanghui
 * 
 * 
 */
public class BackInfoServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		String resultStr = request.getParameter("resultStr");
		String flag = resultStr.substring(0, 4);
		PrintWriter out =response.getWriter();
		if(flag.equals("4402")) {
			String[] info = resultStr.split("\\|");
			String zxdwbh = info[1];
			String jdsbh = info[2];
			System.out.println("扫码获取信息: zxdwbh:"+zxdwbh+" jdsbh:"+jdsbh);
			out.print(zxdwbh+jdsbh);
		}else {
			try {
				resultStr=AES.Decrypt(resultStr);
			} catch (Exception e) {
				e.printStackTrace();
			}
			String[] info = resultStr.split("\\|");
			if(info.length==43) {
				//加入openId
				HttpSession session =request.getSession();
				String openid = (String) session.getAttribute("openid");
				info[info.length-1] =openid;
				Violation tempViolation=WfDao.queryViolationByJdsbh(info[0]);
				if(tempViolation == null) {
					WfDao.insertViolation(info);
					String jsonStr = null;
					try {
						Violation violation=(Violation) SystemUtil.invokeMethod(info);
						System.out.println("扫码插入数据:"+violation.toString());
						if(violation.getJkbj().equals("9")) {
							violation.setJkbj("不需要缴费");
						}else if(violation.getJkbj().equals("1")) {
							violation.setJkbj("已缴费");
						}else{
							violation.setJkbj("未缴费");
						}
					    jsonStr= JSONObject.toJSONString(violation);
					} catch (Exception e) {
						e.printStackTrace();
					}
					out.print(jsonStr);
				}else {
					out.print(0);		
				}
			}else{
				out.print(-1);	
			}	
		 }		
	}
}
