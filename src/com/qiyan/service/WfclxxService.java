package com.qiyan.service;

import java.util.Map;

import com.qiyan.dao.WfclDao;

public class WfclxxService {
   
	public String queryWfclInfo(String xh,String hphm,String hpzl) {
		Map<String,String> map = WfclDao.queryWfclInfo(xh, hphm, hpzl);	
		String retCode = map.get("retCode");
		String msg = "";
		if("1".equals(retCode)) {
			String  dsr = map.get("dsr");
			String  jszh = map.get("jszh"); 
			String  dabh = map.get("dabh"); 
			String  fkje = map.get("fkje"); 
			String  wfjfs = map.get("wfjfs");
			String  phone = map.get("phone");
			msg = "{\"retCode\":\""+retCode+"\",\"dsr\":\""+dsr+"\",\"jszh\":\""+jszh+"\",\"dabh\":\""+
					dabh+"\",\"fkje\":\""+fkje+"\",\"wfjfs\":\""+wfjfs+"\",\"phone\":\""+phone+"\"}";
		}else{
			String  info = map.get("cwxx_out");
			msg ="{\"retCode\":\""+retCode+"\",\"info\":\""+info+"\"}";
		}
		return msg;
	}
}
