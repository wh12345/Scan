package com.qiyan.service;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.qiyan.util.HttpRequestUtil;
import com.qiyan.util.SystemUtil;

public class WxService {
	
	/**
	 * 获取openid
	 * @param code
	 * @return
	 */
	public String getOpenId(String code) {
		Map params = new HashMap();
		params.put("secret",SystemUtil.getConfigByStringKey("WX.SECRET"));
		params.put("appid", SystemUtil.getConfigByStringKey("WX.APPID"));
		//暂时用的是测试账号
	   /* params.put("secret", "ee458ee7d2ac28f5a9546aca8ea6d4db");
		params.put("appid", "wx64a1a07343c30b3a");*/
		params.put("grant_type", "authorization_code");
		params.put("code", code);
		String result = HttpRequestUtil.httpRequestToString(
				"https://api.weixin.qq.com/sns/oauth2/access_token", params);
		JSONObject jsonObject = JSONObject.parseObject(result);
		String openid = jsonObject.get("openid").toString();
		return openid;
	}

}
