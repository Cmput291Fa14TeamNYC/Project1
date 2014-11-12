

import java.sql.*;
import java.util.*;

public class MedicalTestUpdate {
	
	public MedicalTestUpdate(DataSource ds){
		TestRecord tr = getMedicalTest(ds);
		this.enterTestResult(ds, tr.getPatient_no(), tr.getTest_id(), tr.getEmployee_no());
	}
	
	@SuppressWarnings("resource")
	public TestRecord getMedicalTest(DataSource ds) {
		while (true) {
			System.out.print("Back = 0. Enter name or health care number of patient:  ");
			Scanner s = new Scanner(System.in);
			String patient_info = s.nextLine();
			try {
				int option = Integer.parseInt(patient_info);
				if (option == 0) {
					return null;
				}
			} catch (Exception e) {
			}
			ResultSet rs = ds.checkRecord(patient_info);
			Vector<TestRecord> records = ds.getRecordList(rs);
			int isExist = records.size();
			System.out.println("size: " + isExist);
			if (isExist == 0) {
				System.out.println("Nothing.");
				continue;
			} else {
				Helper.printRecords(records);
				if (isExist > 1) {
					while(true){
						System.out.print("Enter test id:  ");
						int id = new Scanner(System.in).nextInt();
						for(TestRecord t : records){
							if(t.getTest_id() == id){
								return t;
							}
						}
					}
				} else {
					return records.listIterator(0).next();
				}
			}
		}
	}

	@SuppressWarnings("resource")
	public void enterTestResult(DataSource data, int healthNum, int testId, int doctorNum){
		System.out.println("Enter Name of medical lab: ");
		Scanner s1 = new Scanner (System.in);
		String medLabName = s1.nextLine();
		
		System.out.println("Enter date of test: ");
		Scanner s2 = new Scanner (System.in);
		String date = s2.nextLine();
		
		System.out.println("Enter test result: ");
		Scanner s3 = new Scanner (System.in);
		String testResult = s3.nextLine();
		
		data.updateTestResult(healthNum, doctorNum, testResult, medLabName, date, testId);
	}
	
}
