package project1yunita;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import java.util.Vector;

import project1main.Doctor;
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
				 * Prescribing new medication ----------------------------- This
				 * component allows a user to enter the detailed information
				 * about the prescription: the employee_no of the doctor who
				 * prescribes the test, the test name, the name and/or the
				 * health_care_no of the patient.
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
				this.searchEngine(ds);
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
		while (true) {
			System.out
					.println("Search record by: Patient info = 1, Prescribed date = 2, Alarming age = 3, Main menu = 0");
			System.out.print("Enter option: ");
			Scanner s = new Scanner(System.in);
			int option = s.nextInt();
			switch (option) {
			case 1:
				System.out.println("By health care no or patient name");
				// code
				break;
			case 2:
				System.out.println("By prescribed date");
				this.searchEngineMenu2(ds);
				break;
			case 3:
				System.out.println("By alarming age");
				// code
				break;
			default:
				return;
			}
		}
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
				System.out.println("Employee no: " + nums[0]
						+ ", Health care no: " + nums[1] + ", Test no: "
						+ nums[2]);
				if (nums[0] != 0 && nums[1] != 0 && nums[2] != 0) {
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
	// >> obtain the employee no by entering doctor name
	@SuppressWarnings("resource")
	public int getEmployeeNo(DataSource ds) {
		while (true) {
			System.out.print("Back = 0. Enter employee no or doctor name: ");
			Scanner s = new Scanner(System.in);
			String doctor_info = s.nextLine();
			try {
				int option = Integer.parseInt(doctor_info);
				if (option == 0) {
					return 0;
				}
			} catch (Exception e) {
			}
			ResultSet rs = ds.checkDoctor(doctor_info);
			Vector<Doctor> doctors = ds.getDoctorList(rs);
			int isExist = doctors.size();
			System.out.println("size: " + isExist);
			if (isExist == 0) {
				System.out.println("Doctor: " + doctor_info
						+ " does not exist.");
				continue;
			} else {
				Util.printDoctors(doctors);
				if (isExist > 1) {
					return getEmployeeNo(ds);
				} else {
					return doctors.listIterator(0).next().getEmployee_no();
				}
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

	// Method: searchEngineMenu2
	// >> search for all prescribed tests during a specified time period
	public void searchEngineMenu2(DataSource ds) {
		System.out
				.println("Enter doctor info (employee no or name), start date (DD/MM/YYYY), end date (DD/MM/YYYY): ");
		System.out
				.println("eg. 1, 01/01/2013, 31/12/2014 or eg. yuri, 01/01/2013, 31/12/2014");
		Scanner s = new Scanner(System.in);
		String input[] = null;
		try {
			input = s.nextLine().split(", ");
		} catch (Exception e) {
			System.out.println("Format input is wrong.");
		}
		if (input[1].matches("\\d{2}/\\d{2}/\\d{4}")
				&& input[2].matches("\\d{2}/\\d{2}/\\d{4}")) {
			try {
				ResultSet rs = ds.checkDoctor(input[0]);
				if (rs.next()) {
					ds.listRecordByPrescribedDate(input[1], input[2], rs.getInt("employee_no"));
				} else {
					System.out.println("Nothing.");
				}
			} catch (SQLException e) {
			}
		} else {
			System.out.println("Wrong date format.");
		}
	}

	public static void main(String[] args) {
		System.out.println("Hello Yunita");
		Main m = new Main();
	}
}
