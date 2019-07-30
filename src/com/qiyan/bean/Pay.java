package com.qiyan.bean;

public class Pay {
	private String ddbh = null;
	private String tzsbh = null;
	private String p_name = null;
	private String p_id = null;
	private String p_phone = null;
	private String p_time = null;
	private String jfbj = null;
	private String status = null;
	public Pay(){}
	public Pay(String ddbh, String tzsbh, String p_name, String p_id,
			String p_phone, String p_time, String jfbj, String status) {
		super();
		this.ddbh = ddbh;
		this.tzsbh = tzsbh;
		this.p_name = p_name;
		this.p_id = p_id;
		this.p_phone = p_phone;
		this.p_time = p_time;
		this.jfbj = jfbj;
		this.status = status;
	}
	public String getDdbh() {
		return ddbh;
	}
	public void setDdbh(String ddbh) {
		this.ddbh = ddbh;
	}
	public String getTzsbh() {
		return tzsbh;
	}
	public void setTzsbh(String tzsbh) {
		this.tzsbh = tzsbh;
	}
	public String getP_name() {
		return p_name;
	}
	public void setP_name(String p_name) {
		this.p_name = p_name;
	}
	public String getP_id() {
		return p_id;
	}
	public void setP_id(String p_id) {
		this.p_id = p_id;
	}
	public String getP_phone() {
		return p_phone;
	}
	public void setP_phone(String p_phone) {
		this.p_phone = p_phone;
	}
	public String getP_time() {
		return p_time;
	}
	public void setP_time(String p_time) {
		this.p_time = p_time;
	}
	public String getJfbj() {
		return jfbj;
	}
	public void setJfbj(String jfbj) {
		this.jfbj = jfbj;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "Pay [ddbh=" + ddbh + ", tzsbh=" + tzsbh + ", p_name=" + p_name
				+ ", p_id=" + p_id + ", p_phone=" + p_phone + ", p_time="
				+ p_time + ", jfbj=" + jfbj + ", status=" + status + "]";
	}	
}
