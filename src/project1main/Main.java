package project1main;

import java.sql.*;
import java.util.*;

import project1main.Doctor;
import project1main.Patient;

public class Main {

	private final static String DB_URL = "jdbc:oracle:thin:@localhost:1525:CRS";
	// private final static String DB_USERNAME; = "commande";
	// private final static String DB_PASSWORD; = "Alexsq2014";

	private static DataSource ds;

	public Main() {
		this.login();
		menu(ds);
		
	}

	public void login() {
		while (true) {
			System.out.print("Enter username: ");
			String DB_USERNAME = new Scanner(System.in).nextLine();
			System.out.print("Enter password: ");
			String DB_PASSWORD = new Scanner(System.in).nextLine();
			ds = new DataSource(DB_URL, DB_USERNAME, DB_PASSWORD);
			if (ds.isConnected()) {
				return;
			} else {
				System.out.println("Invalid username or password.");
			}
		}
	}

	public void menu(DataSource ds) {
		while (true) {
			System.out.println("Main Menu");
			System.out.println("1.Prescribing New Medication");
			System.out.println("2.Medical Test Update");
			System.out.println("3.Patient Information Update");
			System.out.println("4.Search Engine");
			System.out.println("5.Exit");
			System.out.print("Option: ");
			Scanner input = new Scanner(System.in);
			int userInput = input.nextInt();

			switch (userInput) {

			case 1:
				/*
				 * Prescribing new medication ----------------------------- This
				 * component allows a user to enter the detailed information
				 * about the prescription: the employee_no of the doctor who
				 * prescribes the test, the test name, the name and/or the
				 * health_care_no of the patient.
				 */
				System.out.println("1.Prescribing New Medication");
				Prescription prescription = new Prescription(ds);
				break;

			case 2:
				System.out.println("2.Medical Test Update");
				MedicalTestUpdate medicalTestUpdate = new MedicalTestUpdate(ds);
				break;

			case 3:
				/*
				 * Patient Information Update -------------------------- This
				 * component is used to enter the information of a new patient
				 * or to update the information of an existing patient. All the
				 * information about a patient, except the health_care_no for an
				 * existing patient, may be updated.
				 */
				System.out.println("3.Patient Information Update");
				PatientInformation patientInformation = new PatientInformation(
						ds);
				break;

			case 4:
				System.out.println("4.Search Engine");
				SearchEngine searchEngine = new SearchEngine(ds);
				break;

			case 5:
				System.out.println("5.Exit");
				// terminate program
				ds.closeConnection();
				System.out.println("The program is terminated.");
				System.exit(0);
				break;
			}
		}
	}

	public static void main(String[] args) {
		System.out.println("Hello Yunita");
		Main m = new Main();
	}
}
