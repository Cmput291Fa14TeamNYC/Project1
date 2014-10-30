package project1yunita;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {

	public static void main(String[] args) {
		System.out.println("Hello Yunita");
	
		DataSource ds = new DataSource("commande", "Alexsq2014");
		ds.select("*", "PATIENT");
		
	}
}
