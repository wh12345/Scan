package com.qiyan.bean;

import java.util.Map;

import com.qiyan.dao.WfDao;

public class WfnrAndWfjfs {
    public static final Map<String, String> wfnrMap =WfDao.queryWfnrAndWfjfs().get(0);
    public static final Map<String, String> wfjfsMap =WfDao.queryWfnrAndWfjfs().get(1);
    
}
