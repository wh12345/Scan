package com.qiyan.service;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import com.qiyan.bean.WxParam;
import com.qiyan.dao.Dao;
import com.qiyan.dao.WxDao;
import com.qiyan.util.SystemUtil;
import com.qiyan.util.WxUtil;

public class WxJs {

	/*
	    * 获取jsapi_ticket、noncestr、timestamp、url
	    * 
	    */
	    public Map<String, String> sign(String url) {
	        Map<String, String> ret = new HashMap<String, String>();
	        //这里的jsapi_ticket是获取的jsapi_ticket。
	        String jsapi_ticket = null; 
			WxParam wParam=WxDao.queryWx();
	        if(wParam==null||SystemUtil.getInterval(wParam.getUpdate_time())>7200L) {
	        	String access_token=WxUtil.getJSSDKAccessToken();
	        	jsapi_ticket = WxUtil.getJSSDKTicket(access_token);
	        	Date updateTime=new Date();
	        	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
	        	String ut=sdf.format(updateTime);
	        	String sql = null;
	        	String[] params =null;
	        	if(wParam==null) {
	        		sql ="insert into sys_wx values(?,?,?)";
	        		params = new String[]{access_token,jsapi_ticket,ut};
	        	}else{
	        		sql = "update sys_wx set access_token=?,jsapi_ticket=?,update_time=? where access_token=?";
	        		params = new String[]{access_token,jsapi_ticket,ut,wParam.getAccess_token()};
	        	}        	
	    		Dao.update(sql, params);
	        }else{
	        	jsapi_ticket =wParam.getJsapi_ticket();
	        }
	        String nonce_str = create_nonce_str();
	        String timestamp = create_timestamp();
	        String string1;
	        String signature = "";

	        //注意这里参数名必须全部小写，且必须有序
	        string1 = "jsapi_ticket=" + jsapi_ticket +
	                  "&noncestr=" + nonce_str +
	                  "&timestamp=" + timestamp +
	                  "&url=" + url;
	       // System.out.println(string1);
	        try
	        {
	            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
	            crypt.reset();
	            crypt.update(string1.getBytes("UTF-8"));
	            signature = byteToHex(crypt.digest());
	        }
	        catch (NoSuchAlgorithmException e)
	        {
	            e.printStackTrace();
	        }
	        catch (UnsupportedEncodingException e)
	        {
	            e.printStackTrace();
	        }

	        ret.put("url", url);
	        ret.put("jsapi_ticket", jsapi_ticket);
	        ret.put("nonceStr", nonce_str);
	        ret.put("timestamp", timestamp);
	        ret.put("signature", signature);
	        ret.put("appId", SystemUtil.getConfigByStringKey("WX.APPID"));
	        return ret;
	    }

	    private String byteToHex(final byte[] hash) {
	        Formatter formatter = new Formatter();
	        for (byte b : hash)
	        {
	            formatter.format("%02x", b);
	        }
	        String result = formatter.toString();
	        formatter.close();
	        return result;
	    }

	    private static String create_nonce_str() {
	        return UUID.randomUUID().toString();
	    }

	    private static String create_timestamp() {
	        return Long.toString(System.currentTimeMillis() / 1000);
	    }
	
}
