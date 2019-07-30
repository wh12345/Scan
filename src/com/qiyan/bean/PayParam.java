package com.qiyan.bean;

public class PayParam {
	private String ddbh = null;
	private String token = null;
	private String sign = null;
	public PayParam(){}
	public PayParam(String ddbh, String token, String sign) {
		super();
		this.ddbh = ddbh;
		this.token = token;
		this.sign = sign;
	}
	public String getDdbh() {
		return ddbh;
	}
	public void setDdbh(String ddbh) {
		this.ddbh = ddbh;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}

}
