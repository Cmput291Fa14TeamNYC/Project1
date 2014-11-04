package project1brett;

import java.util.Scanner;

import project1main.Patient;

public class Main {
	
	private final static String DB_URL = "jdbc:oracle:thin:@localhost:1525:CRS";
	private final static String DB_USERNAME = "commande";
	private final static String DB_PASSWORD = "Alexsq2014";
	
	private static DataSource ds;

	public static void main(String[] args) {
		
		// Connect to the database;
		ds = new DataSource(DB_URL, DB_USERNAME, DB_PASSWORD);
		
		// Display Main Menu
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
				/* Patient Information Update
				 * --------------------------
				 * This component is used to enter the information of a 
				 * new patient or to update the information of an existing 
				 * patient. All the information about a patient, except 
				 * the health_care_no for an existing patient, may be updated.
				 */
				runPatientInformationUpdate();
				break;
				
			case 4:
				System.out.println("4.Search Engine");
				break;
				
			case 5:
				System.out.println("5.Exit");
				break;
		}

	}

	private static void runPatientInformationUpdate() {
		while (true) {
			
			// Ask for health care number from user.
			System.out.println("Patient Information Update");
			System.out.println("Enter 0 to go back.");
			System.out.println("Please enter the patient's Health Care No.:");
			Scanner input = new Scanner(System.in);
			int health_care_no = input.nextInt();
		
			// Exit program if user chooses to do so.
			if (health_care_no == 0) 
				return;
			
			promptPatientFieldSelection(health_care_no);
			
			continue;
			
		}
		 
	}
	
	public static void promptPatientFieldSelection(int health_care_no) {
		
		while (true) {
			// Get patient associated with provided number
			Patient patient = ds.getPatient(health_care_no);
			if (patient != null) {
				System.out.println("Patient with Health Care No. " + health_care_no +" found:");
				System.out.println("1.Name: " + patient.getName());
				System.out.println("2.Address: " + patient.getAddress());
				System.out.println("3.Phone: " + patient.getPhone());
				System.out.println("4.Birthday: " + patient.getBirth_day());
				System.out.println("0.Go back");
			} else {
				System.out.println("Could not find a patient with Health Care No. " + health_care_no);
				continue;
			}
			
			System.out.println("Select a field to edit: ");
			
			Scanner input = new Scanner(System.in);
			int selection = input.nextInt();
			
			switch(selection) {
				case 1:
					promptPatientFieldUpdate(health_care_no, "name");
					break;
				case 2:
					promptPatientFieldUpdate(health_care_no, "address");
					break;
				case 3:
					promptPatientFieldUpdate(health_care_no, "phone");
					break;
				case 4:
					promptPatientFieldUpdate(health_care_no, "birth_day");
					break;
				case 0:
					return;
				default:
					System.out.println("Please select from the given options.");
					continue;
			}
		}
	}
	
	public static void promptPatientFieldUpdate(int health_care_no, String field) {
		System.out.println("Enter new " + field + ":");
		
		Scanner input = new Scanner(System.in);
		String userInput = input.nextLine();
		
		ds.updatePatient(health_care_no, field , userInput);
	}

}
