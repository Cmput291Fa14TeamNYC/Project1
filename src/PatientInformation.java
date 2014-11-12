

import java.util.Scanner;

public class PatientInformation {
	
	@SuppressWarnings("resource")
	public PatientInformation(DataSource ds) {
		while (true) {
			
			// Ask for health care number from user.
			System.out.println("Patient Information Update");
			System.out.println("Enter 0 to go back.");
			System.out.println("Please enter the patient's Health Care No.:");
			Scanner input = new Scanner(System.in);
			int health_care_no = 0;
			try {
				health_care_no = input.nextInt();
			} catch (Exception e) {
				System.out.println("Please enter a valid Health Care No.");
				continue;
			}
			// Exit program if user chooses to do so.
			if (health_care_no == 0) 
				return;
			
			promptPatientFieldSelection(ds, health_care_no);
			
			continue;
			
		}
		 
	}
	
	@SuppressWarnings("resource")
	public void promptPatientFieldSelection(DataSource ds, int health_care_no) {
		
		while (true) {
			
			Patient patient = ds.getPatient(health_care_no);
			if (patient == null) {
				System.out.println("Could not find a patient with Health Care No. " + health_care_no);
				return;
			}
		
			System.out.println("Patient with Health Care No. " + health_care_no +" found:");
			System.out.println("1.Name: " + patient.getName());
			System.out.println("2.Address: " + patient.getAddress());
			System.out.println("3.Phone: " + patient.getPhone());
			System.out.println("4.Birthday: " + patient.getBirth_day());
			System.out.println("0.Go back");
			System.out.println("Select a field to edit: ");
			
			Scanner input = new Scanner(System.in);
			int selection = input.nextInt();
			
			switch(selection) {
				case 1:
					promptPatientFieldUpdate(ds, health_care_no, "name");
					break;
				case 2:
					promptPatientFieldUpdate(ds, health_care_no, "address");
					break;
				case 3:
					promptPatientFieldUpdate(ds, health_care_no, "phone");
					break;
				case 4:
					promptPatientFieldUpdate(ds, health_care_no, "birth_day");
					break;
				case 0:
					return;
				default:
					System.out.println("Please select a number from the given options.");
					continue;
			}
		}
	}
	
	@SuppressWarnings("resource")
	public void promptPatientFieldUpdate(DataSource ds, int health_care_no, String field) {
		System.out.println("Enter new " + field + ":");
		
		Scanner input = new Scanner(System.in);
		String userInput = input.nextLine();
		
		ds.updatePatient(health_care_no, field , userInput);
	}
}
