package com.qiyan.bean;

import java.util.Date;

public class SVCode {
    private String vcode = null;
    private int count = 0;
    public  Date sendTime = null;
    public SVCode(){}
	public SVCode(String vcode, int count, Date sendTime) {
		super();
		this.vcode = vcode;
		this.count = count;
		this.sendTime = sendTime;
	}
	public String getVcode() {
		return vcode;
	}
	public void setVcode(String vcode) {
		this.vcode = vcode;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public Date getSendTime() {
		return sendTime;
	}
	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}
	@Override
	public String toString() {
		return "SVCode [vcode=" + vcode + ", count=" + count + ", sendTime="
				+ sendTime + "]";
	}   
	
}
