package com.qiyan.dao;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import com.qiyan.bean.Pay;
import com.qiyan.db.DBConnectionManager;

public class PayDao {
	static DBConnectionManager db=new DBConnectionManager();
	/**
	 * 通过订单编号来查询支付结果
	 * @param ddbh
	 * @return
	 */
	public static Pay queryPayByDdbh(String ddbh) {
		String sql="select * from sys_pay where ddbh=?";
		Connection conn = null;
		Pay payRet =null;
		try {
			conn=db.getConnection();
			QueryRunner qr = new QueryRunner();
			payRet = qr.query(conn, sql,ddbh,new BeanHandler<>(Pay.class));
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				db.freeConnection(conn);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return payRet;
	}

}
