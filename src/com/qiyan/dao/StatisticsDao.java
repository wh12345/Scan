package com.qiyan.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import oracle.jdbc.internal.OracleTypes;
import com.qiyan.bean.Statistics;
import com.qiyan.db.DBConnectionManager;

public class StatisticsDao {
	
	static DBConnectionManager db=new DBConnectionManager();
	
	public static Map<String, String> statisticsInfo(String date) {
		String sql = "{?=call Mypackage.func_queryInfo(?,?)}";
		Connection conn =null;
		CallableStatement call = null;
		Map<String, String> retMap = null;
		try {
			conn = db.getConnection();
			call = conn.prepareCall(sql);
			call.registerOutParameter(1, OracleTypes.VARCHAR);
			call.setString(2, date);
			call.registerOutParameter(3, OracleTypes.VARCHAR);
			call.execute();
			String ret = call.getString(1);
			String cwxx_out = call.getString(3);
			retMap = new HashMap<String, String>();
			retMap.put("ret", ret);
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
	/**
	 * 通过日期查询统计信息
	 * @param date
	 * @return
	 */
	public static Statistics queryStatisticsBydate(String date) {
		String sql ="select * from SYS_STATISTICS where to_char(update_date,'YYYYMMDD')=?";
		Connection conn = null;
		Statistics statistics = null;
		try {
			conn =db.getConnection();
			QueryRunner qr = new QueryRunner();
			statistics = qr.query(conn, sql, date,new BeanHandler<Statistics>(Statistics.class));
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			try {
				db.freeConnection(conn);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return statistics;
	}
	
	/**
	 * 查询前面多少天的数据
	 * @param i
	 * @return
	 */
	public  static List<Statistics> queryStatisticsBeforeDay(int i) {
		String sql = "select search_sum,wfcl_sum,pay_success_sum,update_date,to_char(update_date,'day') week_day  from SYS_STATISTICS where update_date>=trunc(sysdate-?) and update_date<>trunc(sysdate) order by update_date";
		List<Statistics> retList = null;
		Connection conn = null;
		try {
			conn = db.getConnection();
			QueryRunner qr = new QueryRunner();
			retList = qr.query(conn, sql, i, new BeanListHandler(Statistics.class));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return retList;
	}
	public static void main(String[] args) {
		/*Map<String, String> map = statisticsInfo();
		Set set =map.keySet();
		for (Object object : set) {
			System.out.println(object+" "+map.get(object));
		}*/
		/*List<Statistics> list = queryStatisticsBeforeDay(7);
		String json =JSONArray.toJSONString(list);
		System.out.println(json);*/
	}
}
