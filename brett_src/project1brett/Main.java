package project1brett;

import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
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
			System.out.println("Patient Information Update");
			System.out.println("Please enter the patient's Health Care No.:");
			
			Scanner input = new Scanner(System.in);
			int userInput = input.nextInt();
			
			System.out.println(userInput);
			
			return;
		}
	}

}
