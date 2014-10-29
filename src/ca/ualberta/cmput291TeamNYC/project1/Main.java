package ca.ualberta.cmput291TeamNYC.project1;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import java.sql.*;


public class Main {

	public static void main(String[] args) {
	/*	System.out.println("Testing ojdbc.jar and connection");

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
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}*/
	
	
	
	System.out.println("Main Menu");
	System.out.println("1.Prescription");
	System.out.println("2.Medical Test");
	System.out.println("3.Patient Information Update");
	System.out.println("4.Search Engine");
	System.out.println("5.Exit");
	
	Scanner input = new Scanner(System.in);
	int userInput = input.nextInt();
	
	switch (userInput) {
	
	case 1:
		System.out.println("1.Prescription");
		break;
		
	case 2:
		System.out.println("2.Medical Test");
		break;
	
	case 3:
		System.out.println("3.Patient");
		break;
		
	case 4:
		System.out.println("4.Search Engine");
		break;
		
	case 5:
		System.out.println("1.Exit");
		break;
	
	
	}
	
	}
}
