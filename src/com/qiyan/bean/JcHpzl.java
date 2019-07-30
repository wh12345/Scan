package com.qiyan.bean;

public class JcHpzl{
	private String kind = null;
	private String code = null;
	private String detail = null;
	public JcHpzl(){}
	public JcHpzl(String kind, String code, String detail) {
		super();
		this.kind = kind;
		this.code = code;
		this.detail = detail;
	}
	public String getKind() {
		return kind;
	}
	public void setKind(String kind) {
		this.kind = kind;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	@Override
	public String toString() {
		return "Wfcl [kind=" + kind + ", code=" + code + ", detail=" + detail
				+ "]";
	}
	
}
