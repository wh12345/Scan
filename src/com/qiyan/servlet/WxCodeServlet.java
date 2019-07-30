package com.qiyan.servlet;

import java.io.IOException;
import java.net.URLEncoder;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.qiyan.util.SystemUtil;

/**
 * 
 * @author wanghui
 *
 */
public class WxCodeServlet extends HttpServlet {
		 
		public void doGet(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {	 
			doPost(request, response);
		}
	 
		public void doPost(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {	 
	        request.setCharacterEncoding("utf-8");
			response.setContentType("text/html;charset=utf-8");
			String flag = request.getParameter("flag");
			String redirect_uri= null;			
			if(flag==null) {
		        //授权回调地址处理一下，否则微信识别不了
				redirect_uri = URLEncoder.encode(SystemUtil.getConfigByStringKey("WX.REDIRECT_URI"), "UTF-8");			
			}else{
				//违法处理的回调地址
				redirect_uri = URLEncoder.encode(SystemUtil.getConfigByStringKey("WX.REDIRECT_URI1"), "UTF-8");	
			}
			//简单获取openid的话参数response_type与scope与state参数固定写死即可
			/*StringBuffer url= new StringBuffer("https://open.weixin.qq.com/connect/oauth2/authorize?redirect_uri="+redirect_uri+
					"&appid="+"wx64a1a07343c30b3a"+"&response_type=code&scope=snsapi_base&state=1#wechat_redirect");*/
			StringBuffer url=new StringBuffer("https://open.weixin.qq.com/connect/oauth2/authorize?redirect_uri="+redirect_uri+
					"&appid="+SystemUtil.getConfigByStringKey("WX.APPID")+"&response_type=code&scope=snsapi_base&state=1#wechat_redirect");
			response.sendRedirect(url.toString());
			
	}

}
