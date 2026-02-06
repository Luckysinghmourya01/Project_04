package in.co.rays.proj4.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public final class JDBCDataSource {

	private static JDBCDataSource jds = null;
	private ComboPooledDataSource cpds = null;

	private static ResourceBundle rb = ResourceBundle.getBundle("in.co.rays.proj4.bundle.system");

	private JDBCDataSource() {
		try {
			cpds = new ComboPooledDataSource();
			cpds.setDriverClass(rb.getString("driver"));
			cpds.setJdbcUrl(rb.getString("url"));
			cpds.setUser(rb.getString("username"));
			cpds.setPassword(rb.getString("password"));
			cpds.setInitialPoolSize(Integer.parseInt(rb.getString("initialpoolsize")));
			cpds.setMaxPoolSize(Integer.parseInt(rb.getString("maxpoolsize")));
			cpds.setAcquireIncrement(Integer.parseInt(rb.getString("acquireincrement")));

		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	public static JDBCDataSource getInstance() {
		if (jds == null) {
			jds = new JDBCDataSource();
		}
		return jds;
	}
	
	
	public static Connection getConnection() {
		try {
			return getInstance().cpds.getConnection();
		}
		catch(Exception e) {
			return null;
		}
	}
	
	public static void closeConnection(Connection conn , Statement stm , ResultSet rs) {
		
		try {
			if(rs != null) {
				rs.close();
			}
			if(stm != null) {
				stm.close();
			}
			if(conn != null) {
				conn.close();	
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void closeConnection(Connection conn, Statement stm) {
		closeConnection(conn, stm, null);
	}
	
	public static void closeconnection(Connection conn) {
		closeConnection( conn , null);
	}
}


