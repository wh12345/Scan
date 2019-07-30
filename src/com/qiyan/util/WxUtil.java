package com.qiyan.util;

import java.io.InputStreamReader;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

/**
 * 
 * @author wanghui
 *
 */
public class WxUtil {
	//微信JSSDK的AccessToken请求URL地址
	public final static String weixin_jssdk_acceToken_url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential";
	// 微信JSSDK的ticket请求URL地址 
	public final static String weixin_jssdk_ticket_url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi"; 

	/**
     * 获取微信JSSDK的access_token 
     * 
     */ 
    public static String getJSSDKAccessToken() {  
        String returnString="";
        String requestUrl = weixin_jssdk_acceToken_url+"&appid="
                +SystemUtil.getConfigByStringKey("WX.APPID")
                +"&secret="+SystemUtil.getConfigByStringKey("WX.SECRET");
        JSONObject jsonObject = httpRequest(requestUrl);  //Http GET请求
        System.out.println("获取token:"+jsonObject.toString());
        // 如果请求成功   
        if (null != jsonObject) {  
            try {  
                returnString=jsonObject.getString("access_token");  
            } catch (JSONException e) {  
                returnString = null;  
            }  
        }  
        return returnString;  
    } 
     
    private static JSONObject httpRequest(String requestUrl) {
    	 HttpClient client = new DefaultHttpClient();  
         HttpGet get = new HttpGet(requestUrl);  
         JSONObject json = null;  
         try {  
             HttpResponse res = client.execute(get);  
             if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {  
                 HttpEntity entity =  res.getEntity();  
                 json = new JSONObject(new JSONTokener(new InputStreamReader(entity.getContent(), HTTP.UTF_8)));  
           }  
         } catch (Exception e) {  
             throw new RuntimeException(e);          
         } finally{  
             //关闭连接 ,释放资源  
             client.getConnectionManager().shutdown();  
         }  
         return json;  
    
	}

	/**
     * 获取微信JSSDK的ticket 
     * @author Benson
     */
    public static String getJSSDKTicket(String access_token) {  
        String returnString="";
        String requestUrl = weixin_jssdk_ticket_url.replace("ACCESS_TOKEN", access_token);  
        JSONObject jsonObject = httpRequest(requestUrl);  
        // 如果请求成功   
                if (null != jsonObject) {  
                    try {  
                        returnString=jsonObject.getString("ticket");  
                    } catch (JSONException e) {  
                        returnString = null;  
                    }  
                }  
        return returnString;  
    } 
}
