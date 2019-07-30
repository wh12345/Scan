package com.qiyan.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.dbutils.QueryRunner;
import com.qiyan.bean.Violation;
import com.qiyan.db.DBConnectionManager;
import com.qiyan.util.SystemUtil;

public class Dao {
	static DBConnectionManager db=new DBConnectionManager();
	
	/**
	 * 通过sql语句和相应的参数来更新
	 * @param sql
	 * @param params
	 */
	public static void update(String sql,String[] params) {
		Connection conn =null;
		try {
			conn =db.getConnection();
			conn.setAutoCommit(false);
			QueryRunner qr = new QueryRunner(db.getDataSource(SystemUtil.getConfigByStringKey("DB.DATASOURCE")));
			//QueryRunner qr = new QueryRunner();
			qr.update(sql,params);
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
	 * 把result转化成map对象
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	public static Map<String, String> getResultMap(ResultSet rs)  
            throws SQLException {  
        Map<String, String> hm = new HashMap<String, String>();  
        ResultSetMetaData rsmd = rs.getMetaData();  
        int count = rsmd.getColumnCount(); 
        rs.next();
        for (int i = 1; i <= count; i++) {  
            String key = rsmd.getColumnName(i);  
            String value = rs.getString(i);  
            hm.put(key, value);  
        }  
        return hm;  
    }  	
}
