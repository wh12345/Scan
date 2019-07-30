package com.qiyan.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.qiyan.bean.WxUser;
import com.qiyan.dao.Dao;
import com.qiyan.dao.WfDao;
import com.qiyan.dao.WxDao;
import com.qiyan.util.SystemUtil;

public class InputPay {
     
	/**
	 * 输入缴费
	 * @param openid
	 * @param jdsbh
	 * @param jszh
	 * @return
	 */
	public Map<String, String> inputPay(String openid,String jdsbh,String jszh) {
		Map<String, String> tMap = new HashMap<String, String>();
		WxUser wu = WxDao.queryWxUserByOpenId(openid,"1");
		if(wu!=null) {
			int inpayinerr_count = Integer.parseInt(SystemUtil.getConfigByStringKey("INPAYINERR_COUNT"));
			if(Integer.parseInt(wu.getErr_count())>inpayinerr_count) {
				System.out.println(openid+" 用户今天输错次数超过限制！");
				tMap.put("flag", "0");
				return tMap;
			}
		}else{
			String iSql = "insert into sys_wxuser values(?,?,sysdate,?,?)";
			String[] params = new String[]{openid,"0","1","0"};
			Dao.update(iSql, params);
		} 
		
		Map<String, String> map = WfDao.queryInPay(jdsbh, jszh);
		if(map!=null) {	
			String ret = map.get("ret");
			String uSql="update sys_wxuser set sum_count=sum_count+1 where kind=? and openid=? and W_DATE>=trunc(sysdate)";
			if("2".equals(ret)) {
				System.out.println(openid+"决定书编号或驾驶证号有误!");
			    uSql="update sys_wxuser set err_count=err_count+1,sum_count=sum_count+1 where  kind=? and openid=?  and W_DATE>=trunc(sysdate)";
				tMap.put("flag", ret);
			}else if("3".equals(ret)) {
				System.out.println(openid+"该处罚单已经缴费！");
				tMap.put("flag", ret);
			}else if("E".equals(ret)) {
				System.out.println("输入缴费：系统异常！");
				tMap.put("flag", "E");
			}else{
				String hphm = map.get("HPHM");
				int count =WxDao.queryForHphmCount(openid, hphm);
				if(count>3) {
					System.out.println(openid+" 用户给该车牌缴费超过限制!");
					tMap.put("flag", "4");
				}else{
					String iSql ="insert into sys_inpay values(?,?,sysdate,?)";
					String[] params = new String[]{jdsbh,hphm,openid};
					Dao.update(iSql, params);
					String zxdwbh = map.get("zxdwbh");
					tMap.put("flag", ret);
					tMap.put("zxdwbh", zxdwbh);						
				}
			}
			//统计输入次数和输错次数
			String[] params = new String[]{"1",openid};
			Dao.update(uSql, params);
		}else{
			tMap.put("flag", "-1");
		}
		return tMap;
	}
}
