package com.qiyan.dao;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.qiyan.bean.WxParam;
import com.qiyan.bean.WxUser;
import com.qiyan.db.DBConnectionManager;

public class WxDao {
	static DBConnectionManager db=new DBConnectionManager();
	
	/**
	 * 查询微信公众号信息
	 * @return
	 */
	public static WxParam queryWx() {
		String sql="select * from sys_wx";
		Connection conn = null;
		WxParam wp =null;
		try {
			conn=db.getConnection();
			QueryRunner qr = new QueryRunner();
			wp = qr.query(conn, sql,new BeanHandler<>(WxParam.class));
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				db.freeConnection(conn);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return wp;
	}
	
	/**
	 * 查询微信用户记录信息
	 * @param openid
	 * @return
	 */
	public static WxUser queryWxUserByOpenId(String openid,String kind) {
		String sql="select * from sys_wxuser where kind=? and openid=? and W_DATE>=trunc(sysdate)";
		Connection conn = null;
		WxUser wu =null;
		try {
			conn=db.getConnection();
			QueryRunner qr = new QueryRunner();
			wu = qr.query(conn, sql, new BeanHandler<>(WxUser.class),kind,openid);
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				db.freeConnection(conn);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return wu;
	}
	

	/**
	 * 查询该用户为该号牌缴费次数
	 * @param jdsbh
	 * @return
	 */
	
	public static int queryForHphmCount(String hphm,String openid) {
		String sql="select count(*) from SYS_INPAY where  hphm =? and openid=? and i_date>=trunc(sysdate)";
		Connection conn = null;
		int flag = 0;
		try {
			conn=db.getConnection();
			QueryRunner qr = new QueryRunner();
			Object object = qr.query(conn,sql, new ScalarHandler<Long>(),hphm,openid);
			flag=Integer.parseInt(object.toString());
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				db.freeConnection(conn);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return flag;
	}

}
