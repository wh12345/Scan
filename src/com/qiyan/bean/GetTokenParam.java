package com.qiyan.bean;

/**
 * 
 * @author wanghui
 *
 */
public class GetTokenParam {
	private String jylx = null;
	private String tzsbh = null;
	private String zxdwbh = null;
	private String xmbh = null;
	private String sqlybm =null;
	private String ddbh = null;
	private String time = null;
	private String auth = null;
	private String sign = null;
	public GetTokenParam(){}
	public GetTokenParam(String jylx, String tzsbh, String zxdwbh, String xmbh,
			String sqlybm, String ddbh, String time, String auth, String sign) {
		super();
		this.jylx = jylx;
		this.tzsbh = tzsbh;
		this.zxdwbh = zxdwbh;
		this.xmbh = xmbh;
		this.sqlybm = sqlybm;
		this.ddbh = ddbh;
		this.time = time;
		this.auth = auth;
		this.sign = sign;
	}

	public String getJylx() {
		return jylx;
	}
	public void setJylx(String jylx) {
		this.jylx = jylx;
	}
	public String getTzsbh() {
		return tzsbh;
	}
	public void setTzsbh(String tzsbh) {
		this.tzsbh = tzsbh;
	}
	public String getZxdwbh() {
		return zxdwbh;
	}
	public void setZxdwbh(String zxdwbh) {
		this.zxdwbh = zxdwbh;
	}
	public String getXmbh() {
		return xmbh;
	}
	public void setXmbh(String xmbh) {
		this.xmbh = xmbh;
	}
	public String getSqlybm() {
		return sqlybm;
	}
	public void setSqlybm(String sqlybm) {
		this.sqlybm = sqlybm;
	}
	public String getDdbh() {
		return ddbh;
	}
	public void setDdbh(String ddbh) {
		this.ddbh = ddbh;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getAuth() {
		return auth;
	}
	public void setAuth(String auth) {
		this.auth = auth;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	
}
