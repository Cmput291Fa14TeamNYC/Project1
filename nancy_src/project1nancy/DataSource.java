package project1nancy;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DataSource {

		private Connection con = null;
		private Statement stmt = null;
		private ResultSet rs = null;
		private final String DRIVERNAME = "oracle.jdbc.driver.OracleDriver";
		private final String URL = "jdbc:oracle:thin:@localhost:1525:CRS";
		
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
		
		public void select(String column, String tablename){
			try {
				rs = stmt.executeQuery("SELECT " + column + " FROM " + tablename);
				while (rs.next()) {
					System.out.println(rs.getString("name"));
				}
			} catch (SQLException e) {
				System.out.println("Patient table does not exist.");
				e.printStackTrace();
			}
		}
		
		
		//SQL STATEMETS 
		public ResultSet checkForPatientName(String patientName){
			String patientQuery = "SELECT name FROM patient WHERE name = '" + patientName + "'";
			try {
				rs = stmt.executeQuery(patientQuery);
			} catch (SQLException e) {
				System.out.println("Patient table does not exist");
				e.printStackTrace();
			}
			return rs;
			}
			
		public ResultSet checkForPatientNum(int healthNum){
			String patientQuery = "SELECT health_care_no FROM patient WHERE health_care_no = '" + healthNum+"'";
			try {
				rs = stmt.executeQuery(patientQuery);
			} catch (SQLException e) {
				System.out.println("Patient table does not exist");
				e.printStackTrace();
			}
			return rs;
		}
		
		public ResultSet checkForDoctorNum(int doctorNum){
			String doctorQuery = "SELECT employee_no FROM doctor WHERE employee_no = '" + doctorNum+"'";
			try {
				rs = stmt.executeQuery(doctorQuery);
			} catch (SQLException e) {
				System.out.println("Doctor table does not exist");
				e.printStackTrace();
			}
			return rs;
		}
		
		/*If there exists such a patient and test record and prescription
		 * then allow the user to enter in the information of the test
		 * such as lab name, test date, the test result**/
		
		public void updateTestResult(String patientName, int healthNum, int doctorNum, String testResult, 
				String medLabName, String date){
			try {
				//Check if the patient name exists
				if(checkForPatientName(patientName).next() && checkForPatientNum(healthNum).next()
						&& checkForDoctorNum(doctorNum).next() ){
					System.out.println(patientName + " " + healthNum + " " + doctorNum);
					
					
					PreparedStatement updateRecord = null;
				
					
					String updateTestQuery = "UPDATE test_record SET result = ?, test_date = ? "
						+ " WHERE patient_no = " + healthNum;  
					
					updateRecord = con.prepareStatement(updateTestQuery);
					
					
					updateRecord.setString(1, testResult );
					updateRecord.setString(2, date );
					
					
					updateRecord.executeUpdate();
					
					con.commit();
					System.out.println("Test Results have been added for " + patientName);
				}
				else{
					System.out.println("The prescription for " + patientName + " does not exist. Please check" +
							" your entries");
				}
				/*else{
					System.out.println("THERE IS NO SUCH PATIENT");
				}*/
				
				//check if the patient's health care number exists
				/*if(checkForPatientNum(healthNum).next()){
					System.out.println(healthNum);
				}
				else{
					System.out.println("THERE IS NO SUCH PATIENT");
				}*/
				
				//check if the doctor's employee number exists
				/*if(checkForDoctorNum(doctorNum).next()){
					System.out.println(doctorNum);
				}
				else{
					System.out.println("THERE IS NO SUCH DOCTOR");
				}*/
				
			
				
				
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.println("Your request cannot be completed. Please check your entries");
				e.printStackTrace();
			}
		}
		
}
