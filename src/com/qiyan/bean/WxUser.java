package com.qiyan.bean;

public class WxUser {
	private String openid = null;
	private String err_count = null;
    private String w_date = null;
    private String kind = null;
    private String sum_count = null;
    public WxUser(){}
	public WxUser(String openid, String err_count, String w_date, String kind,
			String sum_count) {
		super();
		this.openid = openid;
		this.err_count = err_count;
		this.w_date = w_date;
		this.kind = kind;
		this.sum_count = sum_count;
	}
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public String getErr_count() {
		return err_count;
	}
	public void setErr_count(String err_count) {
		this.err_count = err_count;
	}
	public String getW_date() {
		return w_date;
	}
	public void setW_date(String w_date) {
		this.w_date = w_date;
	}
	public String getKind() {
		return kind;
	}
	public void setKind(String kind) {
		this.kind = kind;
	}
	public String getSum_count() {
		return sum_count;
	}
	public void setSum_count(String sum_count) {
		this.sum_count = sum_count;
	}
	@Override
	public String toString() {
		return "WxUser [openid=" + openid + ", err_count=" + err_count
				+ ", w_date=" + w_date + ", kind=" + kind + ", sum_count="
				+ sum_count + "]";
	}	
}
