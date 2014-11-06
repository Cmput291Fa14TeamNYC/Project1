package project1nancy;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import project1main.Patient;
import project1main.TestRecord;

public class DataSource {

		private Connection con = null;
		private Statement stmt = null;
		private ResultSet rs = null;
		private final String DRIVERNAME = "oracle.jdbc.driver.OracleDriver";
		private final String URL = "jdbc:oracle:thin:@localhost:1525:CRS";
		int counter = 0;
		int health_care_no;
		int typeId;
		Vector<Integer> healthNum = new Vector<Integer>();
		Vector<Integer> testId = new Vector<Integer>();
		Vector<Integer> doctorNum = new Vector<Integer>();
		
		public DataSource(String username, String password){			
			try {
				Class.forName(DRIVERNAME);
				con = DriverManager.getConnection(URL, username, password);
				stmt = con.createStatement();
			} catch (SQLException e) {
				System.out.println("Cannot connect to database.");
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				System.out.println("Class is not found.");
				e.printStackTrace();
			}
		}
				
	
	
	public ResultSet testRecordInfo(int employee_no, String patientInfo){
		int i = 0;
		
		try {
			if(isInteger(patientInfo)){
				String testRecordQuery = "SELECT p.name, p.health_care_no, t.patient_no, t.employee_no, t.test_id FROM patient p, test_record t WHERE " +
				" p.health_care_no = " + Integer.parseInt(patientInfo) + " AND t.patient_no = p.health_care_no";
				rs = stmt.executeQuery(testRecordQuery);
				while(rs.next()){
					
					patientInfo = rs.getString("name");
					employee_no = rs.getInt("employee_no");
					health_care_no = rs.getInt("health_care_no");
					typeId = rs.getInt("test_id");
					
					
					healthNum.add(health_care_no);
					doctorNum.add(employee_no);
					testId.add(typeId);
								
								
					System.out.println(counter + ". " + "Patient Name : " + patientInfo);
					System.out.println("Health Care Number: " + healthNum.get(i));
					System.out.println("Doctor Employee No. : " + doctorNum.get(i));
					System.out.println("Test ID: " + testId.get(i) + "\n");
					counter++;
					i++;
				
				}
				
			}
			else{
				String testRecordQuery = "SELECT p.name, p.health_care_no, t.patient_no, t.employee_no, t.test_id FROM patient p, test_record t WHERE " +
				" t.patient_no = p.health_care_no AND p.name = '" + patientInfo + "'";
				rs = stmt.executeQuery(testRecordQuery);
				while(rs.next()){
				
				patientInfo = rs.getString("name");
				employee_no = rs.getInt("employee_no");
				health_care_no = rs.getInt("health_care_no");
				typeId = rs.getInt("test_id");
				
				healthNum.add(health_care_no);
				doctorNum.add(employee_no);
				testId.add(typeId);
							
							
				System.out.println(counter + ". " + "Patient Name : " + patientInfo);
				System.out.println("Health Care Number: " + healthNum.get(i));
				System.out.println("Doctor Employee No. : " + doctorNum.get(i));
				System.out.println("Test ID: " + testId.get(i) + "\n");
				counter++;
				i++;
				}
			}
							
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return rs;
		
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
	
		public static boolean isInteger(String s) {
			try {
				Integer.parseInt(s);
				return true;
			} catch (Exception e) {
				return false;
			}
		}
		
}
