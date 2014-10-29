package ca.ualberta.cmput291TeamNYC.project1;

import java.sql.*;

public class Main {

	public static void main(String[] args) {
		System.out.println("Testing ojdbc.jar and connection");

		Connection con = null;
		Statement stmt= null;
		ResultSet rs = null;
		
		String drivername = "oracle.jdbc.driver.OracleDriver";
		String url = "jdbc:oracle:thin:@gwynne.cs.ualberta.ca:1521:CRS";

//		String username = "yunita";
//		String password = "yenchi12.";
		
		String username = "commande";
		String password = "Alexsq2014";

		try{
			Class.forName(drivername);
			con = DriverManager.getConnection(url, username, password);
			stmt = con.createStatement();
			rs = stmt.executeQuery("SELECT * FROM PATIENT");
			while(rs.next()){
				System.out.println(rs.getString("name"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try{
				rs.close();
				stmt.close();
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
}
