package com.qiyan.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.qiyan.bean.Violation;
import com.qiyan.bean.WfnrAndWfjfs;
import com.qiyan.dao.WfDao;

public class Scan {
   public List<Violation> queryViolationByOpenid(String openid) {
	   List<Violation> retList = WfDao.queryViolationByOpenid(openid);
	   if(retList.size()>0) {
           SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
           Date date=null;
           SimpleDateFormat sdf2=new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
           for(int i=0;i<retList.size();i++) {
             Violation violation=retList.get(i);
             //违法时间
             String wfsj = violation.getWfsj();
             if(wfsj!=null) {
				 try {
					date = sdf.parse(wfsj);
				 } catch (ParseException e) {
					e.printStackTrace();
				 }
                 violation.setWfsj(sdf2.format(date));
             }else{
                wfsj="";
             }
             /*String jdsbh=(violation.getJdsbh()==null)?"":violation.getJdsbh();
             String wfdz=(violation.getWfdz()==null)?"":violation.getWfdz();*/
             //违法行为
             String wfxw = violation.getWfxw();
             //System.out.println(wfxw);
             if(wfxw!=null) {
            	 String wfjfs = WfnrAndWfjfs.wfjfsMap.get(wfxw);
            	 wfxw =WfnrAndWfjfs.wfnrMap.get(wfxw);
            	 violation.setWfxw(wfxw);
            	 violation.setBz(wfjfs);
             }
           }
	   }
	   return retList;
   }
}
