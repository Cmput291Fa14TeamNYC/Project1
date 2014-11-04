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
			MedicalLab m = new MedicalLab();
			m.enterMedicalInfo();
			//enterMedicalInfo();
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
		String patientName,  doctorName, testResult;
		int healthNum;
		DataSource data = new DataSource(username, password);


	System.out.println("Please enter the name of the patient: ");
	Scanner s1 = new Scanner(System.in);
	patientName = s1.nextLine();
	
	System.out.println("Please enter health care number of patient " + patientName + ":");
	Scanner s2 = new Scanner(System.in);
	healthNum = s2.nextInt();
	
	System.out.println("Name of Doctor: ");
	Scanner s3 = new Scanner(System.in);
	doctorName = s3.nextLine();
	
	System.out.println("Please enter test results: ");
	Scanner s4 = new Scanner(System.in);
	testResult = s4.nextLine();

	
	
	
	//data.enterTestResult(patientName, healthNum, doctorName, testResult);

	System.out.println(patientName + " " + healthNum);
	
	
	}
}
