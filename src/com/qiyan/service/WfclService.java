package com.qiyan.service;

import java.util.Map;
import java.util.UUID;
import com.qiyan.dao.Dao;
import com.qiyan.dao.WfclDao;

public class WfclService {
	/**
	 * 违法处理
	 * @param xh
	 * @param dsr
	 * @param jszh
	 * @param dabh
	 * @return
	 */
	public String wfcl(String xh,String dsr,String jszh,String dabh,String openid) {
		Map<String,String> map = WfclDao.wfcl(getId(), xh, dsr, jszh, dabh,openid);
		String ret = map.get("ret");
		String temp ="{\"ret\":\""+ret+"\",\"info\":\""+map.get("cwxx_out")+"\"}";
		if("1".equals(ret)) {
			temp ="{\"code\":\""+ret+"\",\"jdsbh\":\""+map.get("jdsbh")+"\"}";
		}
		return temp;		
	}
	
	public void updateIsCl(String jdsbh,String xh) {
       String sql = "update TB_VIO_SURVEIL_WFCL set jdsbh=?,clbj='1' where xh=?";
       String[] params = new String[]{jdsbh,xh};
       Dao.update(sql, params);
	}
	/**
	 * 生成唯一id
	 * @return
	 */
	private static String getId() {
		return UUID.randomUUID().toString().replace("-", "");
	}
}
