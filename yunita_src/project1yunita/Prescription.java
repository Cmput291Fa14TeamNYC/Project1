package project1yunita;

import java.sql.ResultSet;
import java.util.Scanner;
import java.util.Vector;

import project1main.Doctor;
import project1main.Patient;

public class Prescription {

	// prescription menu
	@SuppressWarnings("resource")
	public Prescription(DataSource ds) {
		int nums[] = new int[3];
		while (true) {
			try {
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
			} catch (Exception e) {
				System.out.println("Wrong input.");
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
			if (isExist == 0) {
				System.out.println("Doctor: " + doctor_info
						+ " does not exist.");
				continue;
			} else {
				Helper.printDoctors(doctors);
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
				Helper.printPatients(patients);
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
}
