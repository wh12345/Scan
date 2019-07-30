package com.qiyan.servlet;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.qiyan.bean.Violation;
import com.qiyan.dao.WfDao;
import com.qiyan.service.Scan;
import com.qiyan.service.WxJs;
import com.qiyan.service.WxService;
import com.qiyan.util.SystemUtil;
/**
 * 
 * @author wanghui
 *
 */
public class ScanController extends HttpServlet {
	private final Logger logger = Logger.getLogger(ScanController.class);
    private static final long serialVersionUID = 1L;
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
       request.setCharacterEncoding("utf-8");
       response.setContentType("text/html;charset=utf-8");
		//获取调用扫码条件的信息
        HttpSession session = request.getSession();
        String openid = (String) session.getAttribute("openid");
        Scan sc = new Scan();
        List<Violation> retList=sc.queryViolationByOpenid(openid);
        request.setAttribute("retList", retList);
        String url = SystemUtil.getConfigByStringKey("WX.URL");
        WxJs wj = new WxJs();
	    Map<String, String> ret = wj.sign(url);
	    for (Map.Entry entry : ret.entrySet()) {
	               logger.info(entry.getKey() + "=" + entry.getValue()+"|");
	               request.setAttribute(entry.getKey().toString(), entry.getValue());
	    }
	    request.getRequestDispatcher("/index.jsp").forward(request, response);
		} 
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
