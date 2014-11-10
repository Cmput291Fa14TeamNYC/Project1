package project1yunita;

import java.sql.*;
import java.util.*;

public class MedicalTestUpdate {
	
	public MedicalTestUpdate(){
		
	}
	
	
	public void choosePatientList(DataSource ds){
		while(true){
			System.out.println("Enter Name or health care number of patient: ");
			Scanner s1 = new Scanner (System.in);
			String patient_info = s1.nextLine();
		try{
			ResultSet rs = ds.testRecordInfo(patient_info);
			if (rs.next()) {
				System.out
						.println("HEALTH CARE NO \t PATIENT NAME \t EMPLOYEE NO \t TEST ID");
				while (rs.next()) {
					System.out.println(rs.getInt("health_care_no") + "\t\t" + rs.getString("name")
							+ "\t\t" + rs.getInt("employee_no") + "\t\t"
							+ rs.getInt("test_id"));
					}
			} else {
				System.out.println("Nothing.");
			}
		}catch(SQLException e){
			System.out.println();
			}
		}		
	}

	/*If there exists such a patient and test record and prescription
	 * then allow the user to enter in the information of the test
	 * such as lab name, test date, the test result**/
	
	public void updateTestResult(String patientName, int healthNum, int doctorNum, String testResult, 
			String medLabName, String date, int testId){
		try {
				
				PreparedStatement updateRecord = null;
			
				
				String updateTestQuery = "UPDATE test_record SET medical_lab = ?, result = ?, test_date = ? "
					+ " WHERE patient_no = " + healthNum + " AND test_id = " + testId;  
				
				updateRecord = con.prepareStatement(updateTestQuery);
				
				updateRecord.setString(1, medLabName );
				updateRecord.setString(2, testResult );
				updateRecord.setString(3, date );
				
				
				updateRecord.executeUpdate();
				
				con.commit();
				System.out.println("Test Results have been added for " + patientName);
			
			
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Your request cannot be completed. Please check your entries");
			e.printStackTrace();
		}
	}
	
}
