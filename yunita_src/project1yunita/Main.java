package project1yunita;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Main {

	public Main(){
		DataSource ds = new DataSource("commande", "Alexsq2014");
		menu(ds);

	}
	
	public void menu(DataSource ds){
		System.out.println("Main Menu");

		System.out.println("1.Prescription");
		System.out.println("2.Medical Test");
		System.out.println("3.Patient Information Update");
		System.out.println("4.Search Engine");
		System.out.println("5.Exit");
		
		System.out.print("Option: ");
		Scanner input = new Scanner(System.in);
		int userInput = input.nextInt();
		
		switch (userInput) {
		
		case 1:
			System.out.println("1.Prescription");
			prescription(ds);
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
			System.out.println("5.Exit");
			break;
		}
	}
	
	public void prescription(DataSource ds){
		// enter employee no
		System.out.print("Enter employee no: ");
		Scanner input1 = new Scanner(System.in);
		int employee_no = input1.nextInt();
		
		// enter health care no or patient name
		System.out.print("Enter health care no or patient name: ");
		Scanner input2 = new Scanner(System.in);
		String patient_info = input2.nextLine();
		
		// enter test name
		System.out.print("Enter test name: ");
		Scanner input3 = new Scanner(System.in);
		String test_name = input3.nextLine();
		System.out.println(test_name);
		
		ds.enterPrescription(employee_no, test_name, patient_info);
		
	}
	
	public static void main(String[] args) {
		System.out.println("Hello Yunita");
		Main m = new Main();
	}
}
