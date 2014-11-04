package project1nancy;

import java.sql.SQLException;
import java.util.*;

public class MedicalLab {

	//public static void main(String[] args){
		
		public void enterMedicalInfo(){
		String username = "commande";
		String password = "Alexsq2014";
		String patientName,  doctorName, testResult;
		int healthNum;
		
		DataSource data = new DataSource(username, password);


		/*Enter in information in order to search for the patient to see if there
		 * is a proper prescription/ proper test record. If there is no such test 
		 * test record then reject request**/ 
	System.out.println("Please enter the name of the patient: ");
	Scanner s1 = new Scanner(System.in);
	patientName = s1.nextLine();
	
	
	System.out.println("Please enter health care number of patient " + patientName + ":");
	Scanner s2 = new Scanner(System.in);
	healthNum = s2.nextInt();
	
	System.out.println("Name of Doctor: ");
	Scanner s3 = new Scanner(System.in);
	doctorName = s3.nextLine();
	
	System.out.println("Please enter test results: ");
	Scanner s4 = new Scanner(System.in);
	testResult = s4.nextLine();

	
//	System.out.println(patientName + " " + healthNum);
	enterTestResult(patientName, healthNum, doctorName, testResult);
	
	
	}
		
		/*If there exists such a patient and test record and prescription
		 * then allow the user to enter in the information of the test
		 * such as lab name, test date, the test result**/
		
		public void enterTestResult(String patientName, int healthNum, String doctorName, String testResult){
			String username = "commande";
			String password = "Alexsq2014";
			DataSource data = new DataSource(username, password);
			try {
				//Check if the patient name exists
				if(data.checkForPatientName(patientName).next()){
					System.out.println(patientName);
				}
				else{
					System.out.println("THERE IS NO SUCH PATIENT");
				}
				
				//check if the patient's health care number exists
				if(data.checkForPatientNum(healthNum).next()){
					System.out.println(healthNum);
				}
				else{
					System.out.println("THERE IS NO SUCH PATIENT");
				}
				
				
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
}
