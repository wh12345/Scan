package com.qiyan.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.qiyan.bean.JcHpzl;
import com.qiyan.bean.Wfcl;
import com.qiyan.bean.WfnrAndWfjfs;
import com.qiyan.bean.WxUser;
import com.qiyan.dao.Dao;
import com.qiyan.dao.WfclDao;
import com.qiyan.dao.WxDao;
import com.qiyan.util.SystemUtil;

public class WfclcxService {
    private static Logger logger = Logger.getLogger(WfclcxService.class);
	public  Map<String, String> isWfcl(String hphm,String hpzl,String cjh,String openid) {		    
		    Map<String, String> retMap = new HashMap<String, String>();
			WxUser wu = WxDao.queryWxUserByOpenId(openid, "2");
			if(wu!=null) {
				System.out.println("查询："+wu.toString());
				int searcherr_count =Integer.parseInt(SystemUtil.getConfigByStringKey("SEARCHERR_COUNT"));
				if(Integer.parseInt(wu.getErr_count())>searcherr_count) {
					System.out.println(openid+" 用户今天查询输错次数超过限制！");
					logger.info(openid+":用户今天查询输错次数超过限制！");
					retMap.put("ret", "0");
					return retMap;
				}
			}else{
				String iSql = "insert into sys_wxuser values(?,?,sysdate,?,?)";
				String[] params = new String[]{openid,"0","2","0"};
				Dao.update(iSql, params);
			}
			Map<String, String> map= WfclDao.isWfcl(hphm, hpzl, cjh);
			String retCode = map.get("retCode");
			String uSql="update sys_wxuser set w_date=sysdate,sum_count=sum_count+1 where openid=? and kind=? and W_DATE>=trunc(sysdate)";
			if("2".equals(retCode)) {
				uSql="update sys_wxuser set err_count=err_count+1,w_date=sysdate,sum_count=sum_count+1 where openid=? and kind=? and W_DATE>=trunc(sysdate)";
			}
			String[] params = new String[]{openid,"2"};
			Dao.update(uSql, params);				
			retMap.put("ret", retCode);
			retMap.put("outStr", map.get("outStr"));
		    return retMap; 	
	}
	
	public List<Wfcl> queryWfcls(String hphm) {
		List<Wfcl> retList =  WfclDao.queryWfcl(hphm);
		if(retList.size()>0) {
			   SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			   Date date=null;
               SimpleDateFormat sdf2=new SimpleDateFormat("yyyy年MM月dd日 HH:mm");             
	   			List<JcHpzl> list =WfclDao.queryJcHpzl();
	   			String hpzlStr = null;
	   			
	           for(int i=0;i<retList.size();i++) {
	             Wfcl wfcl=retList.get(i);
	             String wfsj = wfcl.getWfsj();
	             if(wfsj!=null) {
					 try {
						date = sdf.parse(wfsj);
					 } catch (ParseException e) {
						e.printStackTrace();
					 }
	                 wfcl.setWfsj(sdf2.format(date));
	             }else{
	                wfsj="";
	             }
	             String wfxw = wfcl.getWfxw();
	             //System.out.println(wfxw);
	             if(wfxw!=null) {
	            	 wfxw =WfnrAndWfjfs.wfnrMap.get(wfxw);
	            	 wfcl.setWfxw(wfxw);
	             }
	             
	             for (JcHpzl jcHpzl : list) {
		   				if(jcHpzl.getKind().equals("HPZL")&&jcHpzl.getCode().equals(wfcl.getHpzl())) {
		   					hpzlStr=jcHpzl.getDetail();
		   				}
		   			}
				wfcl.setYlzz1(hpzlStr);
	           }
	    }
		return retList;
	}
	public List<Wfcl> queryWfclsByXh(String xh) {
		List<Wfcl> retList =  WfclDao.queryWfclByXh(xh);
		if(retList.size()>0) {
			   SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			   Date date=null;
               SimpleDateFormat sdf2=new SimpleDateFormat("yyyy年MM月dd日 HH:mm");             
	   			List<JcHpzl> list =WfclDao.queryJcHpzl();
	   			String hpzlStr = null;
	   			
	           for(int i=0;i<retList.size();i++) {
	             Wfcl wfcl=retList.get(i);
	             String wfsj = wfcl.getWfsj();
	             if(wfsj!=null) {
					 try {
						date = sdf.parse(wfsj);
					 } catch (ParseException e) {
						e.printStackTrace();
					 }
	                 wfcl.setWfsj(sdf2.format(date));
	             }else{
	                wfsj="";
	             }
	             String wfxw = wfcl.getWfxw();
	             //System.out.println(wfxw);
	             if(wfxw!=null) {
	            	 wfxw =WfnrAndWfjfs.wfnrMap.get(wfxw);
	            	 wfcl.setWfxw(wfxw);
	             }
	             
	             for (JcHpzl jcHpzl : list) {
		   				if(jcHpzl.getKind().equals("HPZL")&&jcHpzl.getCode().equals(wfcl.getHpzl())) {
		   					hpzlStr=jcHpzl.getDetail();
		   				}
		   			}
				wfcl.setYlzz1(hpzlStr);
	           }
	    }
		return retList;
	}
}
