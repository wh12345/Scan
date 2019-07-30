package com.qiyan.db;

import java.sql.*;
import javax.naming.*;
import javax.sql.DataSource;

import com.qiyan.util.SystemUtil;

public class DBConnectionManager {

	private Connection conn;
    private static Connection sconn = null;
    private DataSource ds;
    private String dsNameKey = "DB.DATASOURCE";

    public DBConnectionManager()
    {
    }
    
    public void setDsNameKey(String value){
    	dsNameKey = value;
    }
    public String getDsNameKey(){
    	return this.dsNameKey;
    }

    public DataSource getDataSource(String dsName)
    {
        Context ctx = null;
        java.util.Hashtable ht = null;
        try
        {
            ctx = new InitialContext();
            ds = (DataSource)ctx.lookup(dsName);
        }
        catch(NamingException ex)
        {
            ex.printStackTrace();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return ds;
    }

    public Connection getConnection()
        throws SQLException
    {
        try
        {
            DBConnectionManager dbcm = new DBConnectionManager();
            dbcm.setDsNameKey(this.dsNameKey);
            sconn = dbcm._getConnection();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return sconn;
    }

    private Connection _getConnection()
        throws SQLException
    {
        try
        {
            String dsName = SystemUtil.getConfigByStringKey(dsNameKey);//("DB.DATASOURCE");//Config.getInstance().getValue("DB.DATASOURCE");
            //System.out.println(dsName);
            //System.out.println("datasource:"+dsName);
            if(dsName == null)
                conn = getConnectionFromConfig();
            else
            if(!dsName.equalsIgnoreCase(""))
            {
                DataSource tempDs = getDataSource(dsName);
                if(tempDs != null)
                    conn = tempDs.getConnection();
                else
                    conn = getConnectionFromConfig();
            } else
            {
                conn = getConnectionFromConfig();
            }
        }
        catch(SQLException ex)
        {
            throw ex;
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return conn;
    }

    private Connection getConnectionFromConfig()
    {
        Connection connection = null;
        try
        {
            String driver = SystemUtil.getConfigByStringKey("DB.DRIVER");//Config.getInstance().getValue("DB.DRIVER");
            String url = SystemUtil.getConfigByStringKey("DB.URL");//Config.getInstance().getValue("DB.URL");
            String user = SystemUtil.getConfigByStringKey("DB.USER");//Config.getInstance().getValue("DB.USER");
            String password = SystemUtil.getConfigByStringKey("DB.PASSWORD");//Config.getInstance().getValue("DB.PASSWORD");
            Class.forName(driver);
            connection = DriverManager.getConnection(url, user, password);
        }
        catch(Exception e)
        {
            System.out.println(" ");
            e.printStackTrace();
        }
        return connection;
    }

    public void freeConnection(Connection con)
        throws SQLException
    {
        if(conn != null)
            try
            {
                conn.close();
                conn = null;
            }
            catch(SQLException ex)
            {
                throw ex;
            }
        if(con != null)
            try
            {
                con.close();
            }
            catch(SQLException ex)
            {
                throw ex;
            }
    }

    public static void main(String args[])
    {
        DBConnectionManager db = new DBConnectionManager();
        try
        {
            db.getConnection();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }
}
