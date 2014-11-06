package project1nancy;

import java.sql.*;
import java.util.Scanner;

import project1main.Patient;

public class Main {
	
	static String username = "commande";
	static String password = "Alexsq2014";
	static String name;
	static String testResult;
	static String medLabName;
	static String date;
	static int health_care_no;
	static int employee_no;
	static int testId;
	static int doctorNum;
	static DataSource data = new DataSource(username, password);


	public static void main(String[] args) {
		menu();
	}
	
	public static void menu(){
/*connect to data source once in main application*/
		
		System.out.println("Main Menu");

		System.out.println("1.Prescription");
		System.out.println("2.Medical Test");
		System.out.println("3.Patient Information Update");
		System.out.println("4.Search Engine");
		System.out.println("0.Exit");

		Scanner input = new Scanner(System.in);
		int userInput = input.nextInt();
		
		switch (userInput) {
		
		case 1:
			System.out.println("1.Prescription");
			break;
			
		case 2:
			System.out.println("2.Medical Test");
			enterMedicalInfo();
		
			break;
		
		case 3:
			System.out.println("3.Patient");
			break;
			
		case 4:
			System.out.println("4.Search Engine");
			searchEngine();
			break;
			
		case 0:
			System.out.println("0.Exit");
			break;
			
			
		}
	
	}
	
	public static void enterMedicalInfo(){
		
		
		while(data.counter == 0){
		System.out.println("Enter Name or health care number of patient: ");
		Scanner s1 = new Scanner (System.in);
		name = s1.nextLine();
		
		
		System.out.println("List of Patients: ");
		
		data.testRecordInfo(name);
		
		}
		
		choosePatientList(name);
		
		
		
	}
	
	public static void choosePatientList(String name){
		
		int healthNum;
		System.out.println("Please enter a number to choose from the list");
		Scanner input = new Scanner (System.in);
		int in = input.nextInt();
		
		
		healthNum = data.healthNum.get(in);
		testId = data.testId.get(in);
		doctorNum = data.doctorNum.get(in);
		
		System.out.println("Test Record retrieved for: " + name);
		System.out.println("Health Care: " + healthNum);
		System.out.println("Test Id: " + testId + "\n");
		
		enterTestResult(healthNum, testId, doctorNum );
	}
	
	
	public static void enterTestResult(int healthNum, int testId, int doctorNum){
		System.out.println("Enter Name of medical lab: ");
		Scanner s1 = new Scanner (System.in);
		medLabName = s1.nextLine();
		
		System.out.println("Enter date of test: ");
		Scanner s2 = new Scanner (System.in);
		date = s2.nextLine();
		
		System.out.println("Enter test result: ");
		Scanner s3 = new Scanner (System.in);
		testResult = s3.nextLine();
		
		data.updateTestResult(name, healthNum, doctorNum, testResult, medLabName, date, testId);
	}
	
	public static void searchEngine(){
		
		
		while(data.counter == 0 || true){
		System.out.println("\n" + "Enter Name or health care number of patient: ");
		Scanner s1 = new Scanner (System.in);
		name = s1.nextLine();
		
		
			System.out.println("List of Patients: ");
			
			data.searchEngineInfo(name);
			
			System.out.println("Enter '0' to go back to the main menu");
			
			if(name.compareTo("0") == 0)
				menu();
			else
			continue;
			
	break;	
	}
			
		
	}
	
}
