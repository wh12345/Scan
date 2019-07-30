package com.qiyan.bean;

/**
 * 
 * @author wanghui
 *
 */
public class Auth {
	private String  name = null;
	private String  id = null;
	private String phonenuber = null;
	public Auth(){}
	public Auth(String name, String id, String phonenuber) {
		super();
		this.name = name;
		this.id = id;
		this.phonenuber = phonenuber;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPhonenuber() {
		return phonenuber;
	}
	public void setPhonenuber(String phonenuber) {
		this.phonenuber = phonenuber;
	}
	@Override
	public String toString() {
		return "Auth [name=" + name + ", id=" + id + ", phonenuber="
				+ phonenuber + "]";
	}
	
}
