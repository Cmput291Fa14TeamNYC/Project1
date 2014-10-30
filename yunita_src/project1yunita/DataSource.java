package project1yunita;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DataSource {

	private Connection con = null;
	private Statement stmt = null;
	private ResultSet rs = null;
	private final String DRIVERNAME = "oracle.jdbc.driver.OracleDriver";
	private final String URL = "jdbc:oracle:thin:@localhost:1525:CRS";
	
	public DataSource(String username, String password){			
		try {
			Class.forName(DRIVERNAME);
			con = DriverManager.getConnection(URL, username, password);
			stmt = con.createStatement();
		} catch (SQLException e) {
			System.out.println("Cannot connect to database.");
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			System.out.println("Class is not found.");
			e.printStackTrace();
		}
	}
	
	public void select(String column, String tablename){
		try {
			rs = stmt.executeQuery("SELECT " + column + " FROM " + tablename);
			while (rs.next()) {
				System.out.println(rs.getString("name"));
			}
		} catch (SQLException e) {
			System.out.println("Patient table does not exist.");
			e.printStackTrace();
		}
	}
}
