package project1yunita;

import java.util.Scanner;

public class Login {

	// user login
	@SuppressWarnings({ "unused", "resource" })
	public Login(){
		while(true){
			System.out.print("Enter username: ");
			String username = new Scanner(System.in).nextLine();
			System.out.print("Enter password: ");
			String password = new Scanner(System.in).nextLine();
			
		}
	}
	
	public void createPassword(){
		// simple hash: md5 method
	}
	
	public void checkUser(DataSource ds, String username, String password){
		try{
			
		} catch(SQLException e) {
			System.out.println("Invalid username or password.");
		}
	}
	
}
