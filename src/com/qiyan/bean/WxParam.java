package com.qiyan.bean;

public class WxParam {
	private String access_token = null;
	private String jsapi_ticket = null;
	private String update_time =null;
	public WxParam(){}
	public WxParam(String access_token, String jsapi_ticket, String update_time) {
		super();
		this.access_token = access_token;
		this.jsapi_ticket = jsapi_ticket;
		this.update_time = update_time;
	}
	public String getAccess_token() {
		return access_token;
	}
	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}
	public String getJsapi_ticket() {
		return jsapi_ticket;
	}
	public void setJsapi_ticket(String jsapi_ticket) {
		this.jsapi_ticket = jsapi_ticket;
	}
	public String getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(String update_time) {
		this.update_time = update_time;
	}
	@Override
	public String toString() {
		return "WxParam [access_token=" + access_token + ", jsapi_ticket="
				+ jsapi_ticket + ", update_time=" + update_time + "]";
	}
	
}
