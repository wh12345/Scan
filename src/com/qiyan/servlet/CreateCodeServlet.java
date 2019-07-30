package com.qiyan.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.qiyan.util.VerifyCodeUtils;




public class CreateCodeServlet extends HttpServlet {
    
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setHeader("Pragma", "No-cache");  
        resp.setHeader("Cache-Control", "no-cache");  
        resp.setDateHeader("Expires", 0);  
        resp.setContentType("image/jpeg");  
          
        //生成随机字串  
        String verifyCode = VerifyCodeUtils.generateVerifyCode(4);  
        //存入会话session  
        req.getSession().setAttribute("code", verifyCode.toLowerCase());  
        
        //生成图片  
        int width = 200;//宽
        int height = 80;//高  
        VerifyCodeUtils.outputImage(width, height, resp.getOutputStream(), verifyCode);  
        
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}