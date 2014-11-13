

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;
import java.util.Vector;

public class SearchEngine {

	// search engine menu
	@SuppressWarnings("resource")
	public SearchEngine(DataSource ds) {
		while (true) {
			try {
				System.out
						.println("Search record by: Patient info = 1, Prescribed date = 2, Alarming age = 3, Main menu = 0");
				System.out.print("Enter option: ");
				Scanner s = new Scanner(System.in);
				int option = s.nextInt();
				switch (option) {
				case 1:
					System.out.println("By health care no or patient name");
					this.searchEngineMenu1(ds);
					break;
				case 2:
					System.out.println("By prescribed date");
					this.searchEngineMenu2(ds);
					break;
				case 3:
					System.out.println("By alarming age");
					this.searchEngineMenu3(ds);
					break;
				default:
					return;
				}
			} catch (Exception e) {
				System.out.println("Wrong input.");
			}
		}
	}

	public void searchEngineMenu1(DataSource ds) {
		System.out.println("\n"
				+ "Enter Name or health care number of patient: ");
		Scanner s1 = new Scanner(System.in);
		String patient_info = s1.nextLine();
		try {
			ResultSet rs = ds.searchEngineInfo(patient_info);
			int counter = 0;
			while (rs.next()) {
				if (counter == 0) {
					System.out
						.println("HEALTH CARE NO \t PATIENT NAME \t TEST NAME \t TEST DATE \t RESULT");
				}
				System.out.println(rs.getInt(2) + "\t\t" + rs.getString(1)
						+ "\t\t" + rs.getString(8) + "\t\t"
						+ rs.getDate("test_date") + "\t"
						+ rs.getString("result"));
				counter ++;
			}
			if (counter == 0) {
				System.out.println("Nothing.");
			}
		} catch (SQLException e) {
		}
	}

	// Method: searchEngineMenu2
	// >> search for all prescribed tests during a specified time period
	@SuppressWarnings("resource")
	public void searchEngineMenu2(DataSource ds) {
		System.out
				.println("Enter doctor info (employee no or name), start date (DD/MM/YYYY), end date (DD/MM/YYYY): ");
		System.out
				.println("eg. 1, 01/01/2013, 31/12/2014 or eg. yuri, 01/01/2013, 31/12/2014");
		Scanner s = new Scanner(System.in);
		String input[] = null;
		try {
			input = s.nextLine().split(", ");
			if (input[1].matches("\\d{2}/\\d{2}/\\d{4}")
					&& input[2].matches("\\d{2}/\\d{2}/\\d{4}")) {
				try {
					ResultSet rs = ds.checkDoctor(input[0]);
					Vector<Doctor> doctors = ds.getDoctorList(rs);
					int emp_no = 0;
					if(doctors.size() > 1){
						Helper.printDoctors(doctors);
						try{
							System.out.print("Enter employee no from the list: ");
							emp_no = new Scanner(System.in).nextInt();
						}catch(Exception e){System.out.println("Does not exist.");}
					} else {
						emp_no = doctors.get(0).getEmployee_no();
					}
					System.out.println(">>" + emp_no);
						ds.listRecordByPrescribedDate(input[1], input[2],
								emp_no);
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				System.out.println("Wrong date format.");
			}
		} catch (Exception e) {
			System.out.println("Format input is wrong.");
		}
	}
	
	@SuppressWarnings("resource")
	public void searchEngineMenu3(DataSource ds) {
		// TODO: finish searchEngine3
		
		while (true) {
			System.out.println("Alarming Age Search Engine");
			System.out.println("Back = 0. Please enter a test name:");
			Scanner scanner = new Scanner(System.in);
			String testName = scanner.nextLine();
			
			if (testName.equals("0")) {
				return;
			}
			
			if (!ds.checkTestName(testName)) {
				System.out.println("Please enter a valid test name");
				continue;
			}
			
			System.out.println("Patients who have reached alarming age for " + testName + " but have not taken that test:");
			List<Patient> patientLst = ds.getAlarmingAgePatients(testName);
			if (patientLst.size() == 0) {
				System.out.println("None");
			}
			for (Patient patient : patientLst) {
				System.out.println(patient.getName());
				System.out.println(patient.getAddress());
				System.out.println(patient.getPhone());
				System.out.println("--------------------");
			}
		}
	}

}
