package com.qiyan.job;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.qiyan.bean.Dzyz;
import com.qiyan.dao.Dao;
import com.qiyan.dao.WfDao;
import com.qiyan.service.PdfJpg;
import com.qiyan.util.SystemUtil;

public class ProduceEOT implements Job {
    private static Logger  logger = Logger.getLogger(ProduceEOT.class);
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		Map<String, String> map = WfDao.isCreateEOT();
		Set<String> set = map.keySet();
		String urlStr = null;
		SimpleDateFormat sdf =new SimpleDateFormat("yyMMdd");
		for (String jdsbh : set) {
				if(jdsbh!=null&&map.get(jdsbh)!=null) {
					String value = sdf.format(new Date());
					urlStr =SystemUtil.getConfigByStringKey("IMG.URL")+"pdf"+File.separator+value+File.separator+jdsbh+".jpg";	
					File file = new File(urlStr);
					logger.debug("urlStr"+urlStr+" 是否存在:"+file.exists());
					if(!file.exists()) {
						Map<String, String> map1=WfDao.queryWfxxByJdsbh(jdsbh);
						if(map1!=null) {
							String dzyz = map1.get("FXJG");
							Map<String,String> mapparentdept=new Dzyz().MapParentDept;
							dzyz=mapparentdept.get(dzyz);
							map1.put("DZYZ", dzyz);
							PdfJpg pj = new PdfJpg();
							//创建pdf文件
							String pdfurl =pj.createPDF(map1);
							//pdf文件转成jpg图片
							boolean flag =pj.pdf2Image(pdfurl,value,400);
							//删除pdf文件和图片
							new PdfJpg().deleteFile(jdsbh);
							if(flag) {
								logger.debug(jdsbh+".jpg生成电子处罚书成功！");
								String sql = "update TB_VIO_VIOLATION  set zxbh=? where jdsbh=?";
								String[] params = new String[]{value,jdsbh};
								Dao.update(sql, params);
								logger.debug("修改表TB_VIO_VIOLATION"+"|zxbh="+value+"|jdsbh="+jdsbh);
							}else{
								logger.debug(jdsbh+".jpg生成电子处罚书失败！");
							}
						}
					}
				}
			}
   }
}
