package com.qiyan.util;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.regex.Pattern;

import com.qiyan.bean.Violation;

/**
 * @author wanghui
 * 
 */
public class SystemUtil {
	static Properties configProperties =null;	
	/**
	 * 获取配置文件中的值
	 * @param key
	 * @return
	 */
	public static String getConfigByStringKey(String key) {
		String configValue = null;
		try {
			if (null == configProperties) {
				System.out.println("第一次加载配置文件");
				InputStream in = com.qiyan.util.SystemUtil.class
						.getResourceAsStream("/config/config.properties");
				configProperties = new Properties();
				configProperties.load(in);
			}
			configValue = configProperties.getProperty(key);
		} catch (Exception ioe) {
			ioe.printStackTrace();
		}
		return configValue;
	}
	
	/**
	 * 获取车主签名图片数组
	 * @param url
	 * @return
	 */
	public static byte[] getCzqmImg(String url)
	{
		DataInputStream  input = null;
		ByteArrayOutputStream out = null;
		try {
			URL urlImage = new URL(url);
			input = new DataInputStream(urlImage.openStream());
			out = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024];
			int length;
			while((length = input.read(buffer)) > 0){
				out.write(buffer,0,length);
			}
			out.flush();
			return out.toByteArray();
		} catch (Exception e) {
			// TODO: handle exception
			//e.printStackTrace();
			System.out.println(e.toString());
			return null;
		}finally{
			if(input != null )
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			if(out != null)
				try {
					out.close();
					out = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
	}
	
	public static byte[] getImg(String url) {
		 File file = new File(url);
	     FileInputStream fis = null;
	     ByteArrayOutputStream baos = null;
		try {
			fis = new FileInputStream(file);
			baos = new ByteArrayOutputStream();
			int data = -1;
			while((data = fis.read())!=-1) {
				baos.write(data);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return baos.toByteArray();
	}
	
	/**
	 * 反射实现集合赋值给对象
	 * @param array
	 * @return
	 * @throws IntrospectionException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	public static Object invokeMethod(String[] array) throws IntrospectionException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Object object =Violation.class.newInstance();
		Field[] fields =Violation.class.getDeclaredFields();
		for (int i = 0; i < array.length; i++) {
			Field f = fields[i];
			PropertyDescriptor pd = new PropertyDescriptor(f.getName(), Violation.class);
			Method m = pd.getWriteMethod();
			m.invoke(object, array[i]);
 		}
		return object;
	}
	
    public static Long getInterval(String date) {
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		Date start = null;
		try {
			start = sdf.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Date end =new Date();
		Long flag=(end.getTime()-start.getTime())/1000;
		return flag;
    }
    public static boolean checkCellphone(String cellphone) {
    	String regex = "^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(18[0,5-9]))\\d{8}$"; 
    	return Pattern.matches(regex, cellphone);
   }
}
