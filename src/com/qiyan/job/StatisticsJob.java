package com.qiyan.job;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.qiyan.bean.Statistics;
import com.qiyan.dao.StatisticsDao;
import com.qiyan.util.SendSmsUtil;
import com.qiyan.util.SystemUtil;



 

public class StatisticsJob implements Job{
	private Logger logger  = Logger.getLogger(StatisticsJob.class);
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		    Calendar c = Calendar.getInstance();
		    Date date1 = new Date();
	        c.setTime(date1);
	        int day = c.get(Calendar.DATE);
	        c.set(Calendar.DATE, day-1);
		    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			String date = sdf.format(date1);
			Map<String, String>  map = StatisticsDao.statisticsInfo(date);	
			logger.info("定时统计结果:"+map.get("ret")+" "+map.get("cwxx_out"));
			Statistics statistics = StatisticsDao.queryStatisticsBydate(date);
			String msg = "韶关交警微信公众号("+date+")统计信息:"+"违法查询总次数:"+statistics.getSearch_sum()
					+";违法查询失败总次数:"+statistics.getSearch_error_count_sum()
					+";违法处理总次数:"+statistics.getWfcl_sum()
					+";违法缴费成功总次数:"+statistics.getPay_success_sum()
					+";违法缴费失败总次数:"+statistics.getPay_fail_sum();
			String ss = SystemUtil.getConfigByStringKey("SS.PHONE");
			String[] phones =ss.split(",");
			System.out.println(msg);
			for (String phone : phones) {
				String result = null;
				System.out.println(msg);
				try {
					result = SendSmsUtil.LoginGo(phone, msg);		  
				} catch (Exception e) {
					e.printStackTrace();
				}
				if(result.equals("")) {
					System.out.println("发送给"+phone+" 信息："+msg+"结果:失败");
					logger.info("发送给"+phone+" 信息："+msg+"结果:失败");
				}	
			}
	}

}
