package com.qiyan.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

/**
 * 
 * @author wanghui
 *
 */
public class HttpRequestUtil {
	 
	public static String httpRequestToString(String url, Map<String,String> params) {
		String result = null;
		try {
			InputStream is = httpRequestToStream(url,  params);
			BufferedReader in = new BufferedReader(new InputStreamReader(is,
					"UTF-8"));
			StringBuffer buffer = new StringBuffer();
			String line = "";
			while ((line = in.readLine()) != null) {
				buffer.append(line);
			}
			result = buffer.toString();
		} catch (Exception e) {
			return null;
		}
		return result;
	}
 
	private static InputStream httpRequestToStream(String url,
			Map<String, String> params) {
		 InputStream is = null;
	        try {
	            String parameters = "";
	            boolean hasParams = false;
	            for(String key : params.keySet()){
	                String value = URLEncoder.encode(params.get(key), "UTF-8");
	                parameters += key +"="+ value +"&";
	                hasParams = true;
	            }
	            if(hasParams){
	                parameters = parameters.substring(0, parameters.length()-1);
	            }
	            url += "?"+ parameters;
	            URL u = new URL(url);
	            HttpURLConnection conn = (HttpURLConnection) u.openConnection();
				conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
				conn.setRequestProperty("Accept-Charset", "UTF-8");
				conn.setRequestProperty("contentType", "utf-8");
	            conn.setConnectTimeout(50000);  
	            conn.setReadTimeout(50000);
	            conn.setDoInput(true);
	            //设置请求方式，默认为GET
	            conn.setRequestMethod("GET");
 
 
	            is = conn.getInputStream();
	        } catch (UnsupportedEncodingException e) {
	            e.printStackTrace();
	        } catch (MalformedURLException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        return is;
	}
	public static String PostRequest(String url,String params) {
		CloseableHttpClient client =HttpClients.createDefault();
		HttpPost post = new HttpPost(url);
		post.setHeader("Content-Type", "application/json;charset=UTF-8");
		StringEntity se=null;
		String resData=null;
		try {
			se = new StringEntity(params);
			se.setContentType("UTF-8");
			post.setEntity(se);
			CloseableHttpResponse response = client.execute(post);
			resData=EntityUtils.toString(response.getEntity(),"utf-8");
			client.close();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return resData;
	}
	
}

