package project1yunita;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import java.util.Vector;

import project1main.Patient;

public class Main {

	public Main() {
		DataSource ds = new DataSource("commande", "Alexsq2014");
		menu(ds);

	}

	public void menu(DataSource ds) {
		while (true) {
			System.out.println("Main Menu");

			System.out.println("1.Prescribing new medication");
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
				 * Prescribing new medication 
				 * -----------------------------
				 * This component allows a user to enter the detailed
				 * information about the prescription: the employee_no of the
				 * doctor who prescribes the test, the test name, the name
				 * and/or the health_care_no of the patient.
				 */
				System.out.println("1.Prescribing new medication");
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
	}

	// later implementation
	public void searchEngine(DataSource ds) {
		// testing purpose
		ds.listRecordByPrescribedDate("1/JAN/2013", "31/DEC/2013", 707070);
	}

	public void prescription(DataSource ds) {
		int nums[] = new int[3];
		this.prescriptionMenu(ds, nums);
	}

	// prescription menu
	@SuppressWarnings("resource")
	public void prescriptionMenu(DataSource ds, int nums[]) {
		while (true) {
			System.out
					.println("Enter Doctor = 1, Patient = 2, Test name = 3, Prescribe = 4, Menu = 0");
			System.out.print("Enter option: ");
			Scanner s = new Scanner(System.in);
			int option = s.nextInt();
			switch (option) {
			case 1:
				nums[0] = this.getEmployeeNo(ds);
				break;
			case 2:
				nums[1] = this.getPatientNo(ds);
				break;
			case 3:
				nums[2] = this.getTestName(ds);
				break;
			case 4:
				System.out.println("Employee no: " + nums[0] + ", Health care no: " + nums[1] + ", Test no: " + nums[2]);
				if(nums[0] != 0 && nums[1] != 0 && nums[2] != 0){
					if (ds.isAllowed(nums[1], nums[2])) {
						ds.enterPrescription(nums[0], nums[1], nums[2]);
					}
					nums[0] = 0;
					nums[1] = 0;
					nums[2] = 0;
					return;
				} else {
					System.out.println("Please complete the form.");
				}
				break;
			default:
				return;
			}
		}
	}

	// Method: getEmployeeNo
	// >> check whether the doctor exists and if exist, then return its employee no
	@SuppressWarnings("resource")
	public int getEmployeeNo(DataSource ds) {
		int doctor_id = 0;
		while (true) {
			try {
				System.out.print("Back = 0. Enter employee no: ");
				Scanner s = new Scanner(System.in);
				int employee_no = s.nextInt();
				if (employee_no == 0) {
					return 0;
				}
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

	// Method: getPatientNo
	// >> obtain the health care no by entering patient name
	@SuppressWarnings("resource")
	public int getPatientNo(DataSource ds) {
		while (true) {
			System.out
					.print("Back = 0. Enter health care no or patient name: ");
			Scanner s = new Scanner(System.in);
			String patient_info = s.nextLine();
			try {
				int option = Integer.parseInt(patient_info);
				if (option == 0) {
					return 0;
				}
			} catch (Exception e) {
			}
			ResultSet rs = ds.checkPatient(patient_info);
			Vector<Patient> patients = ds.getPatientList(rs);
			int isExist = patients.size();
			if (isExist == 0) {
				System.out.println("Patient: " + patient_info
						+ " does not exist.");
				continue;
			} else {
				Util.printPatients(patients);
				if (isExist > 1) {
					return getPatientNo(ds);
				} else {
					return patients.listIterator(0).next().getHealth_care_no();
				}
			}
		}
	}

	// Method: getTestName
	// >> obtain the test id by entering test name
	@SuppressWarnings("resource")
	public int getTestName(DataSource ds) {
		int type_id = 0;
		while (true) {
			try {
				System.out.print("Back = 0. Enter test name: ");
				Scanner s = new Scanner(System.in);
				String test_name = s.nextLine();
				if (test_name.equals("0")) {
					return 0;
				}
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
