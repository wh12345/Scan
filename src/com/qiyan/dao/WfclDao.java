package com.qiyan.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import oracle.jdbc.OracleTypes;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.qiyan.bean.JcHpzl;
import com.qiyan.bean.VCode;
import com.qiyan.bean.Wfcl;
import com.qiyan.db.DBConnectionManager;
 
public class WfclDao {
	static DBConnectionManager db=new DBConnectionManager();
	/**
	 * 查询车牌号简称和号牌种类
	 * @return
	 */
	public static List<JcHpzl> queryJcHpzl() {
		String sql ="SELECT kind,code,detail from dictionary";
		Connection conn =null;
		List<JcHpzl> list=null;
		try {
			conn=db.getConnection();
			QueryRunner qr=new QueryRunner();
			list=qr.query(conn, sql,new BeanListHandler<JcHpzl>(JcHpzl.class));
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				db.freeConnection(conn);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
	}
	
	/**
	 * 判断违法行为是否要处理
	 * @param hphm
	 * @param hpzl
	 * @param zjh
	 * @return
	 */
	public static Map<String, String> isWfcl(String hphm,String hpzl,String cjh) {
		String sql = "{? = call Mypackage.func_queryWfcl(?,?,?,?)}";
		Connection conn = null;
		CallableStatement call = null;
		Map<String, String> retMap = null;
		try {
			conn = db.getConnection();
			call=conn.prepareCall(sql);
			call.registerOutParameter(1, OracleTypes.VARCHAR);
			call.setString(2, hphm);
			call.setString(3, hpzl);
			call.setString(4, cjh);
			call.registerOutParameter(5, OracleTypes.VARCHAR);
			call.execute();
		    String retCode = call.getString(1);
			String outStr  = call.getString(5);
			retMap = new HashMap<String,String>();
			retMap.put("retCode", retCode);
			retMap.put("outStr", outStr);
			System.out.println("ret:"+retCode+" outStr:"+outStr);	
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				db.freeConnection(conn);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return retMap;
	}
	
	/**
	 * 查询该车牌号码所有的违法记录
	 * @param hphm
	 * @return
	 */
	public static List<Wfcl> queryWfcl(String hphm) {
		String sql ="select * from TB_VIO_SURVEIL_WFCL t where t.hphm=?";
		Connection conn =null;
		List<Wfcl> list=null;
		try {
			conn=db.getConnection();
			QueryRunner qr=new QueryRunner();
			list=qr.query(conn, sql,hphm,new BeanListHandler<Wfcl>(Wfcl.class));
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				db.freeConnection(conn);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
	}
	/**
	 * 查询该车牌号码所有的违法记录
	 * @param hphm
	 * @return
	 */
	public static List<Wfcl> queryWfclByXh(String xh) {
		String sql ="select * from TB_VIO_SURVEIL_WFCL t where t.xh=?";
		Connection conn =null;
		List<Wfcl> list=null;
		try {
			conn=db.getConnection();
			QueryRunner qr=new QueryRunner();
			list=qr.query(conn, sql,xh,new BeanListHandler<Wfcl>(Wfcl.class));
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				db.freeConnection(conn);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
	}
	
	/**
	 * 查询车主手机号码
	 * @param hphm
	 * @param hpzl
	 * @return
	 */
	public static Map<String, String> queryPhone(String hphm,String hpzl) {
		String sql = "{? = call Mypackage.func_queryPhone(?,?,?)}";
		Connection conn = null;
		CallableStatement call = null;
		Map<String,String> retMap = null;
		try {
			conn = db.getConnection();
			call=conn.prepareCall(sql);
			call.registerOutParameter(1, OracleTypes.VARCHAR);
			call.setString(2, hphm);
			call.setString(3, hpzl);
			call.registerOutParameter(4, OracleTypes.VARCHAR);
			call.execute();
		    String ret = call.getString(1);
			String outStr  = call.getString(4);
			retMap =new HashMap<String, String>();
			retMap.put("ret",ret);
			retMap.put("outStr", outStr);
			System.out.println("ret:"+ret+" outStr:"+outStr);	
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				db.freeConnection(conn);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return retMap;
	}
	
	/**
	 * 查询该手机发送参数
	 * @param phone
	 * @return
	 */
	public static VCode querySendCount(String phone) {
		String sql="select * from SYS_VCODE where phone =? and send_date>=trunc(sysdate)";
		Connection conn = null;
		VCode ret =null;
		try {
			conn=db.getConnection();
			QueryRunner qr = new QueryRunner();
			ret = qr.query(conn, sql,phone,new BeanHandler<>(VCode.class));
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				db.freeConnection(conn);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return ret;
	}
	
	/**
	 * 进行违法处理
	 * @return
	 */
	public static Map<String, String> wfcl(String id,String xh,String dsr,String jszh,String dabh,String openid) {
		String sql = "{? = call Mypackage.func_wfcl(?,?,?,?,?,?,?,?)}";
		Connection conn = null;
		CallableStatement call = null;
		Map<String,String> retMap = null;
		try {
			conn = db.getConnection();
			call=conn.prepareCall(sql);
			call.registerOutParameter(1, OracleTypes.VARCHAR);
			call.setString(2, id);
			call.setString(3, xh);
			call.setString(4, jszh);
			call.setString(5, dabh);
			call.setString(6, dsr);
			call.setString(7, openid);
			call.registerOutParameter(8, OracleTypes.VARCHAR);
			call.registerOutParameter(9, OracleTypes.VARCHAR);
			call.execute();
		    String ret = call.getString(1);
		    String cwxx_out   = call.getString(9);
			retMap =new HashMap<String, String>();
			retMap.put("ret",ret);
			retMap.put("cwxx_out", cwxx_out);
		    System.out.println("处理违章ret:"+ret+" cwxx_out:"+cwxx_out);
		    if(ret.equals("1")) {
				String jdsbh  = call.getString(8);
				retMap.put("jdsbh", jdsbh);
		    }
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				db.freeConnection(conn);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return retMap;
	}
	
	/**
	 * 查询执行单位编号
	 * @param jdsbh
	 * @return
	 */
	public static Map<String, String> queryZxdwbh(String jdsbh) {
		String sql = "{? = call Mypackage.func_queryZxdwbh(?,?)}";
		Connection conn = null;
		CallableStatement call = null;
		Map<String,String> retMap = null;
		try {
			conn = db.getConnection();
			call=conn.prepareCall(sql);
			call.registerOutParameter(1, OracleTypes.VARCHAR);
			call.setString(2, jdsbh);
			call.registerOutParameter(3, OracleTypes.VARCHAR);
			call.execute();
		    String ret = call.getString(1);
			String zxdwbh  = call.getString(3);
			retMap =new HashMap<String, String>();
			retMap.put("ret",ret);
			retMap.put("zxdwbh", zxdwbh);
			System.out.println("ret:"+ret+" outStr:"+zxdwbh);	
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				db.freeConnection(conn);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return retMap;
	}
	
	/**
	 * 查询车主违法处理信息
	 * @param xh
	 * @param hphm
	 * @param hpzl
	 * @return
	 */
	public static Map<String,String> queryWfclInfo(String xh,String hphm,String hpzl) {
		String sql = "{? = call Mypackage.func_queryWfclInfo(?,?,?,?,?,?,?,?,?,?)}";
		Connection conn = null;
		CallableStatement call = null;
		Map<String,String> retMap = null;
		try {
			conn = db.getConnection();
			call=conn.prepareCall(sql);
			call.registerOutParameter(1, OracleTypes.VARCHAR);
			call.setString(2, xh);
			call.setString(3, hphm);
			call.setString(4, hpzl);
			call.registerOutParameter(5, OracleTypes.VARCHAR);
			call.registerOutParameter(6, OracleTypes.VARCHAR);
			call.registerOutParameter(7, OracleTypes.VARCHAR);
			call.registerOutParameter(8, OracleTypes.VARCHAR);
			call.registerOutParameter(9, OracleTypes.VARCHAR);
			call.registerOutParameter(10, OracleTypes.VARCHAR);
			call.registerOutParameter(11, OracleTypes.VARCHAR);
			call.execute();
		    String retCode = call.getString(1);
			String dsr  = call.getString(5);
			String jszh  = call.getString(6);
			String dabh  = call.getString(7);
			String fkje  = call.getString(8);
			String wfjfs  = call.getString(9);
			String phone = call.getString(10);
			String cwxx_out  = call.getString(11);
			retMap =new HashMap<String, String>();
			retMap.put("retCode",retCode);
			retMap.put("dsr",dsr);
			retMap.put("jszh", jszh);
			retMap.put("dabh",dabh);	
			retMap.put("fkje",fkje);	
			retMap.put("wfjfs",wfjfs);
			retMap.put("phone", phone);
			retMap.put("cwxx_out", cwxx_out);
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				db.freeConnection(conn);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return retMap;
	}
}
