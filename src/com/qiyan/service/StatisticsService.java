package com.qiyan.service;

import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.qiyan.bean.Statistics;
import com.qiyan.dao.StatisticsDao;

public class StatisticsService {

	public String queryStatisticsBeforeDay(int i) {
		List<Statistics> list = StatisticsDao.queryStatisticsBeforeDay(i);
		return JSONArray.toJSONString(list);	
	}
}
