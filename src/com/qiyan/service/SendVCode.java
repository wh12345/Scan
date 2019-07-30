package com.qiyan.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Random;

import com.qiyan.bean.VCode;
import com.qiyan.dao.Dao;
import com.qiyan.dao.WfclDao;
import com.qiyan.util.SendSmsUtil;
import com.qiyan.util.SystemUtil;

/**
 * 
 * @author wanghui
 *
 */
public class SendVCode {
	
	/**
	 * 查询车主手机号
	 * @param hphm
	 * @param hpzl
	 * @return
	 */
   public Map<String, String> queryPhone(String hphm,String hpzl) {
	   return WfclDao.queryPhone(hphm, hpzl);
   }
   
   /**
    * 查询该手机号今天是否达到发送最大上限
    * @param phone
    * @return
    */
   public boolean querySendCount(String hphm,String phone) {
	   VCode vc = WfclDao.querySendCount(phone);
	   if(vc!=null) {
		   int sendvcode_maxcount = Integer.parseInt(SystemUtil.getConfigByStringKey("SENDVCODE_MAXCOUNT"));
		   if(Integer.parseInt(vc.getSend_count())<=sendvcode_maxcount) {
			  String sql = "update sys_vcode  set send_count=send_count+1,send_date=sysdate where phone=? and send_date>=trunc(sysdate)";
		      String[] params = new String[]{phone};
			  Dao.update(sql, params);
			  return true; 
		   }
	   }else{
		    String sql = "insert into sys_vcode values(?,?,?,sysdate)";
			String[] params = new String[]{hphm,phone,"0"};
			Dao.update(sql, params);
			return true;
	   }
	   return false;
   }
   
   /**
    * 发送验证码并返回值
    * @param phone
    * @return
    */
   public String sendVcode(String phone,String jszh,String hphm,String fkje,String wfjfs) {
	   String vcode = getValidationCode();
	   String msg ="韶关交警公众号验证码:"+vcode+",提示:"+hphm+"的违法行为将对驾驶人"+jszh+"处罚款"+fkje+"元,计"+wfjfs+"分，如不是本人操作,请忽略此短信。";
	   String result = null;
	   try {
		  result = SendSmsUtil.LoginGo(phone, msg);		  
		} catch (Exception e) {
			e.printStackTrace();
		}
	   if(result.equals("")) {
		  System.out.println(msg+"结果:失败");
		  return  "-1";
	   }
	   System.out.println(msg+"结果"+result);
	   return vcode;
   }
   
   /**
    * 
    * 获取4位随机验证码
    * @return
    * 
    */
   public  String getValidationCode() {
   	return String.valueOf(new Random().nextInt(8999)+1000);
   }
}
