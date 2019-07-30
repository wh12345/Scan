package com.qiyan.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import oracle.jdbc.OracleTypes;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.qiyan.bean.Violation;
import com.qiyan.db.DBConnectionManager;
import com.qiyan.util.SystemUtil;
 
/**
 * 
 * @author wanghui
 *
 */
public class WfDao{
	static DBConnectionManager db=new DBConnectionManager();
	
	/**
	 * 插入一条违法记录
	 * @param v
	 * @return
	 */
	public static void insertViolation(String[] v) {
		String sql ="insert into tb_vio_violation values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,to_date(?,'yyyy-MM-dd HH24:mi:ss'),?,?,?,?,?,?,?,?,?,?,?,?,?,to_date(?,'yyyy-MM-dd HH24:mi:ss'),?,to_date(?,'yyyy-MM-dd HH24:mi:ss'),?,?,?,?,?,?,to_date(?,'yyyy-MM-dd HH24:mi:ss'),?,?)";
		Connection conn =null;
		try {
			conn =db.getConnection();
			conn.setAutoCommit(false);
			QueryRunner qr = new QueryRunner(db.getDataSource(SystemUtil.getConfigByStringKey("DB.DATASOURCE")));
			qr.update(sql, v);
			conn.commit();
			conn.setAutoCommit(true);
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				db.freeConnection(conn);
			} catch (SQLException e) {
				e.printStackTrace();
			} 
		}
	}
	
	/**
	 * 通过openID查询该用户所有的违法记录
	 * @param openid
	 * @return
	 */
	public static List<Violation>  queryViolationByOpenid(String openid) {
		String sql ="select * from tb_vio_violation where openid=?";
		Connection conn =null;
		List<Violation> list=null;
		try {
			conn=db.getConnection();
			QueryRunner qr=new QueryRunner();
			list=qr.query(conn, sql,openid,new BeanListHandler<Violation>(Violation.class));
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
	 * 通过决定书编号来查询违法记录
	 * @param jdsbh
	 * @return
	 */
	public static Violation queryViolationByJdsbh(String jdsbh) {
		String sql="select * from tb_vio_violation where jdsbh=?";
		Connection conn = null;
		Violation ret =null;
		try {
			conn=db.getConnection();
			QueryRunner qr = new QueryRunner();
			ret = qr.query(conn, sql,jdsbh,new BeanHandler<>(Violation.class));
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
	 * 通过决定书编号来查询详细违法信息
	 * @param jdsbh
	 * @return
	 */
	public static Map<String, String> queryWfxxByJdsbh(String jdsbh) {
		String sql = "{? = call Mypackage.func_queryPrint(?,?,?)}";
		Connection conn = null;
		CallableStatement call = null;
		ResultSet rs = null;
		Map<String, String> map =null;
		try {
			conn = db.getConnection();
			call=conn.prepareCall(sql);
			call.registerOutParameter(1, OracleTypes.VARCHAR);
			call.setString(2, jdsbh);
			call.registerOutParameter(3, OracleTypes.CURSOR);
			call.registerOutParameter(4, OracleTypes.VARCHAR);
			call.execute();
			rs =(ResultSet) call.getObject(3);
			String ret=call.getString(1);
			String cwxx_out=call.getString(4);
			//System.out.println("ret:"+ret+" cwxx_out:"+cwxx_out);
			map=Dao.getResultMap(rs);			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				db.freeConnection(conn);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return map;
	}
	
	/**
	 * 对手工输入缴费的数据进行判断并查询执行单位编号
	 * @return
	 */
	public static Map<String, String> queryInPay(String jdsbh,String jszh) {
		String sql = "{? = call Mypackage.func_queryInPay(?,?,?,?)}";
		Connection conn = null;
		CallableStatement call = null;
		Map<String, String> map = null;
		try {
			conn = db.getConnection();
			call=conn.prepareCall(sql);
			call.registerOutParameter(1, OracleTypes.VARCHAR);
			call.setString(2, jdsbh);
			call.setString(3, jszh);
			call.registerOutParameter(4, OracleTypes.VARCHAR);
			call.registerOutParameter(5, OracleTypes.VARCHAR);
			call.execute();
			String ret=call.getString(1);
			String zxdwbh  = call.getString(4);
			String hphm  = call.getString(5);
			System.out.println("ret:"+ret+" zxdwbh:"+zxdwbh+"hphm:"+hphm);	
			map =new HashMap<String, String>();
			map.put("ret",ret);
			map.put("zxdwbh", zxdwbh);
			map.put("hphm", hphm);
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				db.freeConnection(conn);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return map;
	}
	
	/**
	 * 查询违法行为
	 * @return
	 */
	public static List<Map<String, String>> queryWfnrAndWfjfs() {
		String sql="select wfxw,wfnr,wfjfs from VIO_CODEWFDM";
		Connection conn = null;
		ResultSet rs = null;
		List<Map<String, String>> retList = null;
		Map<String, String> retMap1 = null;
		Map<String, String> retMap2 = null;
		try {
			conn=db.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.execute();
			rs = ps.getResultSet();
			retMap1 = new HashMap<>();
			retMap2 = new HashMap<>();
			while (rs.next()) {
				 retMap1.put(rs.getString(1),rs.getString(2));
				 retMap2.put(rs.getString(1),rs.getString(3));
			}
			retList = new ArrayList();
			retList.add(retMap1);
			retList.add(retMap2);
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try { 
				db.freeConnection(conn);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return retList;
	}	
	
	/**
	 * 查询所有决定书编号的时间目录
	 * @return
	 */
	public static Map<String, String> isCreateEOT() {
		String sql = "select jdsbh,wfsj from TB_VIO_VIOLATION where zxbh is null";
		Map<String, String> retMap = new HashMap<>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn=db.getConnection();
			ps = conn.prepareStatement(sql);
			ps.execute();
			rs = ps.getResultSet();
			retMap = new HashMap<>();
			while (rs.next()) {
				retMap.put(rs.getString(1),rs.getString(2));		
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
	
	public static String getImgDic(String jdsbh) {
		String sql = "select zxbh  from tb_vio_violation where jdsbh=?";
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = db.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, jdsbh);
			ps.execute();
			rs = ps.getResultSet();
			while (rs.next()) {
				return rs.getString(1);				
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
		return null;
	}
	/*public static void main(String[] args) {
		Map<String,String> map = getImgUrl();
		Set<Entry<String, String>> entrySet = map.entrySet();
		Iterator<Entry<String, String>> iterator =entrySet.iterator();
		while(iterator.hasNext()) {
			Entry<String, String> entry = iterator.next();
			System.out.println(entry.getKey()+" "+entry.getValue());
		}
	}*/

}
