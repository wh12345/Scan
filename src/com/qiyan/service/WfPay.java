package com.qiyan.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.apache.log4j.Logger;
import com.alibaba.fastjson.JSONObject;
import com.qiyan.bean.Auth;
import com.qiyan.bean.GetTokenParam;
import com.qiyan.bean.PayParam;
import com.qiyan.bean.Violation;
import com.qiyan.dao.Dao;
import com.qiyan.dao.WfDao;
import com.qiyan.util.Base64Utils;
import com.qiyan.util.HttpRequestUtil;
import com.qiyan.util.MD5;
import com.qiyan.util.RSA;
import com.qiyan.util.SystemUtil;

/**
 *  
 * @author wanghui
 *
 */
public class WfPay{
	private final Logger logger = Logger.getLogger(WfPay.class);
    /**
     * 获取token
     * @param jdsbh
     * @return
     */
    public  Map<String, String> getPayToken(String jdsbh,String zxdwbh,String openid) {
    	Map<String, String> map =  new HashMap<String, String>();
    	 Violation v =WfDao.queryViolationByJdsbh(jdsbh);	
 		//新建获取token的对象
 		GetTokenParam gtp =new GetTokenParam();

 		gtp.setJylx("03023");
 		gtp.setTzsbh(jdsbh);
 		gtp.setZxdwbh("440200143014");
 		if(zxdwbh!=null) {
 			gtp.setZxdwbh(zxdwbh);
 		} 		
 		gtp.setXmbh("440200");
 		gtp.setSqlybm("100031");
 		//保留字段
 		/*gtp.setReseverd1("20");
 		gtp.setReseverd2("0");
 		gtp.setReseverd3("sgjjce");*/
 		
 		//生成订单编号(32位)
 		gtp.setDdbh(getDdbh());
 		//设置时间戳
 		gtp.setTime(getDate());

 		//设置个人信息实体类
 		if(v==null) {
 			v = new Violation();
 			v.setDsr("无");
 			v.setJszh("000000000000000000");
 			String[] param =new String[43];
 			param[0] = jdsbh;
 			param[42] =openid;
 			System.out.println("插入数据:jdsbh:"+jdsbh+" openid:"+openid);
 			WfDao.insertViolation(param);
 		}
 		if(v.getDsr()==null&&v.getJszh()==null) {
 			v.setDsr("无");
 			v.setJszh("000000000000000000");
 			System.out.println("插入数据:jdsbh:"+jdsbh+" openid:"+openid);
 		}
 		if(v.getBz()==null) {
 			v.setBz("00000000000");
 		}
 		Auth a = new Auth();
 		a.setId(v.getJszh());
 		a.setName(v.getDsr());
 		a.setPhonenuber(v.getBz());
 		System.out.println("实名信息:"+a.toString());
 		String jsonStr = JSONObject.toJSONString(a);
 		//获取RSA公钥
        String publicKey = SystemUtil.getConfigByStringKey("RSA.PUBLICKEY");
 		byte[] bytes =null;
 		String auth= null;
 		try {
 			bytes = RSA.encryptByPublicKey(jsonStr.getBytes(), publicKey);
 			auth = Base64Utils.encode(bytes);
 		} catch (Exception e) {
 			e.printStackTrace();
 		}
 		gtp.setAuth(auth); 		
 		//获取私钥
 		String privateKey=SystemUtil.getConfigByStringKey("PRIVATEKEY");
 		
 		//生成获取token的签名
 		String tempSign=getSignStr(gtp,privateKey);
 		String sign = MD5.getMD5(tempSign.getBytes());
 		gtp.setSign(sign);
 		
 		//发送请求获取token，获取返回值
 		String jsonStr1 = JSONObject.toJSONString(gtp);
 		String getTokenUrl = SystemUtil.getConfigByStringKey("PAY.GETTOKENURL");
 		System.out.println("获取token"+getTokenUrl+"获取token请求参数:"+jsonStr1);
 		String retData = HttpRequestUtil.PostRequest(getTokenUrl, jsonStr1);
 		if(retData!=null) {
	 		JSONObject jsonObject = JSONObject.parseObject(retData);
	 		System.out.println("获取token相关信息:"+jsonObject.toString());
	 		String status = (String) jsonObject.get("status");
	 		//插入支付记录
	 		if(status.equals("0000")) {
		 		String ddbh = (String) jsonObject.get("ddbh");
		 		String token = (String) jsonObject.get("token");
	 	 		String[] params=new String[]{gtp.getDdbh(),jdsbh,a.getName(),a.getId(),a.getPhonenuber(),gtp.getTime(),"2"};
	 	 		String sql = "insert into sys_pay values(?,?,?,?,?,to_date(?,'yyyyMMddHH24miss'),?,'')";
	 	 		Dao.update(sql, params);
	 	 		map.put("ddbh", ddbh);
	 	 		map.put("token", token);
	 	 		map.put("status", status);
	 		}else{
	 			map.put("status", status);
	 		}
 		}else{
 			map.put("status", "9999");
 		}
    	return map;
    }
    
    /**
     * 获取缴费请求的参数
     * @param ddbh
     * @param token
     * @return
     */
    public  PayParam getPayParam(String ddbh,String token) {
		PayParam pp = new PayParam();
		pp.setDdbh(ddbh);
		pp.setToken(token);
		String privateKey=SystemUtil.getConfigByStringKey("PRIVATEKEY");
		String tempPaySign=getPaySign(pp,privateKey);
		String paysign = MD5.getMD5(tempPaySign.getBytes());
		pp.setSign(paysign);
		return pp;
    }
    
    private static String getSignStr(GetTokenParam param,String key) {
		return param.getJylx()+param.getTzsbh()+param.getZxdwbh()
				+param.getXmbh()+param.getSqlybm()+param.getDdbh()
				+param.getTime()+param.getAuth()+key;
	}

	private static String getPaySign(PayParam pp,String key) {
		return pp.getToken()+pp.getDdbh()+key;
	}
	
	private static String getDdbh() {
		return UUID.randomUUID().toString().replace("-", "");
	}
	
	private static String getDate() {
 		SimpleDateFormat sdf =new SimpleDateFormat("YYYYMMddHHmmss");
 		return sdf.format(new Date());
	}
    
}
