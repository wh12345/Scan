package com.qiyan.bean;

public class VCode {
	private String hphm = null;
	private String phone = null;
	private String send_count = null;
	private String send_date = null;
	public VCode(){}
	public VCode(String hphm, String phone, String send_count, String send_date) {
		super();
		this.hphm = hphm;
		this.phone = phone;
		this.send_count = send_count;
		this.send_date = send_date;
	}
	public String getHphm() {
		return hphm;
	}
	public void setHphm(String hphm) {
		this.hphm = hphm;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getSend_count() {
		return send_count;
	}
	public void setSend_count(String send_count) {
		this.send_count = send_count;
	}
	public String getSend_date() {
		return send_date;
	}
	public void setSend_date(String send_date) {
		this.send_date = send_date;
	}
	

}
