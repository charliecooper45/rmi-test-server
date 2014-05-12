package database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.c3p0.DataSources;

public class DatabaseConnectionPool {
	private static DataSource dataSource;
	private static final String URL;
	private static final String USERNAME;
	private static final String PASSWORD;

	static {
		Properties p = new Properties(System.getProperties());
		p.put("com.mchange.v2.log.MLog", "com.mchange.v2.log.FallbackMLog");
		p.put("com.mchange.v2.log.FallbackMLog.DEFAULT_CUTOFF_LEVEL", "OFF"); 
		System.setProperties(p);
		
		URL = "jdbc:mysql://localhost:3306/test";
		USERNAME = "root";
		PASSWORD = "letmein";

		dataSource = init();
	}

	public static Connection getConnection() throws SQLException {
		return dataSource.getConnection();
	}

	private static DataSource init() {
		ComboPooledDataSource cpds = new ComboPooledDataSource();
		cpds.setJdbcUrl(URL);
		cpds.setUser(USERNAME);
		cpds.setPassword(PASSWORD);
		cpds.setMinPoolSize(10);
		cpds.setAcquireIncrement(5);
		cpds.setMaxPoolSize(40);
		return cpds;
	}
	
	public static void destroy() {
		try {
			DataSources.destroy(dataSource);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		Connection conn = null;

		try {
			conn = DatabaseConnectionPool.getConnection();
			Statement statement = conn.createStatement();

			// execute select SQL stetement
			ResultSet rs = statement.executeQuery("SELECT * FROM employee");

			while (rs.next()) {
				String name = rs.getString("name");
				System.out.println("Name: " + name);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
