package project1nancy;

import java.sql.*;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
/*connect to data source once in main application*/
		
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
			//MedicalLab m = new MedicalLab();
			//m.enterMedicalInfo();
			enterMedicalInfo();
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
	
	
	public static void enterMedicalInfo(){
		String username = "commande";
		String password = "Alexsq2014";
		String patientName, testResult, medLabName, date;
		int healthNum, doctorNum;
		DataSource data = new DataSource(username, password);


	System.out.println("Please enter the name of the patient: ");
	Scanner s1 = new Scanner(System.in);
	patientName = s1.nextLine();
	
	System.out.println("Please enter health care number of patient " + patientName + ":");
	Scanner s2 = new Scanner(System.in);
	healthNum = s2.nextInt();
	
	System.out.println("Employee Number of Doctor: ");
	Scanner s3 = new Scanner(System.in);
	doctorNum = s3.nextInt();
	
	System.out.println("Please enter test results: ");
	Scanner s4 = new Scanner(System.in);
	testResult = s4.nextLine();

	System.out.println("Enter name of medical lab: ");
	Scanner s5 = new Scanner(System.in);
	medLabName = s5.nextLine();
	
	System.out.println("Enter date of test DD-MM-YY: ");
	Scanner s6 = new Scanner(System.in);
	date = s6.nextLine();
	
	
	data.updateTestResult(patientName, healthNum, doctorNum, testResult, medLabName, date);
	
	
	}
}
