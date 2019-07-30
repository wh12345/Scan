package com.qiyan.bean;

public class Statistics {
	  private String search_sum = null;
	  private String search_error_count_sum = null;
	  private String  wfcl_sum = null;
	  private String pay_success_sum = null;
	  private String pay_fail_sum = null;
	  private String update_date = null;
	  private String week_day = null;
	  public Statistics(){}
	  public Statistics(String search_sum, String search_error_count_sum,
			String wfcl_sum, String pay_success_sum, String pay_fail_sum,
			String update_date, String week_day) {
		super();
		this.search_sum = search_sum;
		this.search_error_count_sum = search_error_count_sum;
		this.wfcl_sum = wfcl_sum;
		this.pay_success_sum = pay_success_sum;
		this.pay_fail_sum = pay_fail_sum;
		this.update_date = update_date;
		this.week_day = week_day;
	}
	public String getSearch_sum() {
		return search_sum;
	}
	public void setSearch_sum(String search_sum) {
		this.search_sum = search_sum;
	}
	public String getSearch_error_count_sum() {
		return search_error_count_sum;
	}
	public void setSearch_error_count_sum(String search_error_count_sum) {
		this.search_error_count_sum = search_error_count_sum;
	}
	public String getWfcl_sum() {
		return wfcl_sum;
	}
	public void setWfcl_sum(String wfcl_sum) {
		this.wfcl_sum = wfcl_sum;
	}
	public String getPay_success_sum() {
		return pay_success_sum;
	}
	public void setPay_success_sum(String pay_success_sum) {
		this.pay_success_sum = pay_success_sum;
	}
	public String getPay_fail_sum() {
		return pay_fail_sum;
	}
	public void setPay_fail_sum(String pay_fail_sum) {
		this.pay_fail_sum = pay_fail_sum;
	}
	public String getUpdate_date() {
		return update_date;
	}
	public void setUpdate_date(String update_date) {
		this.update_date = update_date;
	}
	public String getWeek_day() {
		return week_day;
	}
	public void setWeek_day(String week_day) {
		this.week_day = week_day;
	}
	@Override
	public String toString() {
		return "Statistics [search_sum=" + search_sum
				+ ", search_error_count_sum=" + search_error_count_sum
				+ ", wfcl_sum=" + wfcl_sum + ", pay_success_sum="
				+ pay_success_sum + ", pay_fail_sum=" + pay_fail_sum
				+ ", update_date=" + update_date + ", week_day=" + week_day
				+ "]";
	}	  
}
