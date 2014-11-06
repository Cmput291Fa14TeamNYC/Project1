package project1yunita;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Main {

	public Main() {
		DataSource ds = new DataSource("commande", "Alexsq2014");
		menu(ds);

	}

	public void menu(DataSource ds) {
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
			/*
			 * Prescribing new prescription ----------------------------- This
			 * component allows a user to enter the detailed information about
			 * the prescription: the employee_no of the doctor who prescribes
			 * the test, the test name, the name and/or the health_care_no of
			 * the patient.
			 */
			System.out.println("1.Prescribing new prescription");
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
			// terminate program
			System.out.println("The program is terminated.");
			System.exit(0);
			break;
		}

		ds.closeConnection();
	}

	// later implementation
	public void searchEngine(DataSource ds) {
		// testing purpose
		ds.listRecordByPrescribedDate("1/JAN/2013", "31/DEC/2013", 707070);
	}

	public void prescription(DataSource ds) {
		int employee_no = 0;
		int patient_no = 0;
		int type_id = 0;

		// enter and get the employee no
		employee_no = this.enterEmployeeNo(ds);
		System.out.println(employee_no);

		// enter and get the patient no
		patient_no = this.enterPatientNo(ds);
		System.out.println(patient_no);

		// enter test name and get the test/type id
		type_id = this.enterTestName(ds);
		System.out.println(type_id);

		// if patient is allowed to take this test, then enter the new
		// prescription
		if (ds.isAllowed(patient_no, type_id)) {
			ds.enterPrescription(employee_no, patient_no, type_id);
		}
	}

	// later implementation: method to go back

	public int enterEmployeeNo(DataSource ds) {
		int doctor_id = 0;
		while (true) {
			try {
				System.out.print("Enter employee no: ");
				Scanner input1 = new Scanner(System.in);
				int employee_no = input1.nextInt();

				if (ds.checkDoctorByEmpNo(employee_no) == 0) {
					System.out.println("Doctor wityh employee no: "
							+ employee_no + " does not exist.");
					continue;
				} else {
					doctor_id = employee_no;
				}
				return doctor_id;
			} catch (Exception e) {
				System.out.println("Wrong input. Please enter number.");
				continue;
			}
		}
	}

	public int enterPatientNo(DataSource ds) {
		int patient_no = 0;
		while (true) {
			try {
				System.out.print("Enter patient no or patient name: ");
				Scanner input2 = new Scanner(System.in);
				String patient_info = input2.nextLine();

				if (ds.checkPatient(patient_info) == 0) {
					System.out.println("Patient: " + patient_info
							+ " does not exist.");
					continue;
				} else {
					patient_no = ds.checkPatient(patient_info);
					// what if there is duplicate name with different health
					// care no?
					// later implementation here...
				}
				return patient_no;
			} catch (Exception e) {
				continue;
			}
		}
	}

	public int enterTestName(DataSource ds) {
		int type_id = 0;
		while (true) {
			try {
				System.out.print("Enter test name: ");
				Scanner input3 = new Scanner(System.in);
				String test_name = input3.nextLine();
				int test_id = ds.checkTestByName(test_name);
				if (test_id == 0) {
					System.out.println(test_name + " does not exist.");
					continue;
				} else {
					type_id = test_id;
				}
				return type_id;
			} catch (Exception e) {
				System.out.println("Wrong input. Please enter number.");
				continue;
			}
		}
	}

	public static void main(String[] args) {
		System.out.println("Hello Yunita");
		Main m = new Main();
	}
}
