package project1yunita;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import project1main.Helpers;

public class DataSource {

	private Connection con = null;
	private Statement stmt = null;
	private ResultSet rs = null;
	private final String DRIVERNAME = "oracle.jdbc.driver.OracleDriver";
	private final String URL = "jdbc:oracle:thin:@localhost:1525:CRS";

	public DataSource(String username, String password) {
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

	
	/***********************************
	 * YUNITA***************************
	 ***********************************
	 */

	// Method: enterPrescription
	// >> insert new prescription into record table if patient is allowed to take the specified test
	public void enterPrescription(int employee_no, int patient_no, int type_id) {
		try {
			// check whether the patient is allowed to take the specified test type
			if(this.isAllowed(patient_no, type_id) == false){
				System.out.println("Sorry this patient is not allowed to take this test!");
			} else {
				// inserting into record
				String insertRecordQuery = "INSERT INTO test_record VALUES ("
						+ getLastId("test_id", "test_record") + "," + type_id
						+ "," + patient_no + "," + employee_no + "," + "null"
						+ "," + "null" + "," + "sysdate" + "," + "null" + ")";
				stmt.execute(insertRecordQuery);
				con.commit();
				System.out.println("New record is inserted.");
			}
		} catch (SQLException e) {
			System.out.println("Cannot insert values into Record table.");
		}
	}

	// Method: checkDoctorByEmpNo
	// >> check whether the doctor with the specified employee no exists in doctor table,
	//    if yes, then return its employee no
	public int checkDoctorByEmpNo(int employee_no) {
		String selectDoctorQuery = "SELECT * FROM DOCTOR WHERE employee_no = "
				+ employee_no;
		int doctor_id = 0;
		try {
			rs = stmt.executeQuery(selectDoctorQuery);
			if (rs.next()) {
				doctor_id = rs.getInt("employee_no");
			}
		} catch (SQLException e) {
			System.out.println("Could not find the doctor.");
		}
		return doctor_id;
	}

	// Method: checkPatient
	// >> check whether the patient with the specified patient information
	//    (name or health care no) exists in patient table
	//    if yes, then return its test patient no
	public int checkPatient(String patient_info) {
		int patient_id = 0;
		try {
			// checking whether the user enters health care no or patient name
			if (Helpers.isInteger(patient_info)) {
				String selectPatientByNoQuery = "SELECT * FROM PATIENT WHERE health_care_no = "
						+ Integer.parseInt(patient_info);
				rs = stmt.executeQuery(selectPatientByNoQuery);
			} else {
				String selectPatientByNameQuery = "SELECT * FROM PATIENT WHERE UPPER(name) = UPPER('"
						+ patient_info + "')";
				rs = stmt.executeQuery(selectPatientByNameQuery);
			}

			if (rs.next()) {
				patient_id = rs.getInt("health_care_no");
			}

		} catch (SQLException e) {
			System.out.println("Could not find the patient.");
		}
		return patient_id;
	}

	// Method: checkTestByName
	// >> check whether the test name exists in test type table,
	//    if yes, then return its test type id
	public int checkTestByName(String test_name) {
		String checkTestQuery = "SELECT * FROM TEST_TYPE WHERE UPPER(test_name) = UPPER('"
				+ test_name + "')";
		int type_id = 0;
		try {
			rs = stmt.executeQuery(checkTestQuery);
			if (rs.next()) {
				type_id = rs.getInt("type_id");
			}
		} catch (SQLException e) {
			System.out.println("Cannot execute select test type statement.");
		}
		return type_id;
	}

	// Method: isAllowed
	// >> check whether the patient is allowed to take the specified test
	public boolean isAllowed(int patient_no, int test_id){
		String selectNotAllowedQuery = "SELECT * FROM NOT_ALLOWED WHERE health_care_no = "
				+ patient_no + " AND test_id = " + test_id;
		boolean isAllowed = true;
		try {
			rs = stmt.executeQuery(selectNotAllowedQuery);
			if (rs.next()) {
				isAllowed = false;
			}
		} catch (SQLException e) {
		}
		return isAllowed;
	}
	
	// Method: getLastId
	// >> return the last id + 1 from the specified table
	public int getLastId(String col, String table) {
		String lastIdQuery = "SELECT MAX(" + col + ") AS ID FROM " + table;
		int lastId = 0;
		try {
			rs = stmt.executeQuery(lastIdQuery);
			if (rs.next()) {
				lastId = rs.getInt("id");
			}
		} catch (SQLException e) {
			System.out.println("Cannot get the last id from " + table);
		}
		return lastId + 1;
	}

	
	/*
	 * List the health_care_no, patient name, test type name, 
	 * prescribing date of all tests prescribed by a given doctor 
	 * during a specified time period. The user needs to enter the name 
	 * or employee_no of the doctor, and the starting and ending dates
	 *  between which tests are prescribed.
	 */
	public void listRecordByPrescribedDate(String start_date, String end_date, int employee_no){
		String selectQuery = 
		"SELECT r.PATIENT_NO, t.TEST_NAME, p.NAME, r.PRESCRIBE_DATE "
		+ "FROM TEST_RECORD r, PATIENT p, TEST_TYPE t "
		+ "WHERE r.PATIENT_NO = p.HEALTH_CARE_NO "
		+ "AND r.TYPE_ID = t.TYPE_ID "
		+ "AND r.PRESCRIBE_DATE BETWEEN TO_DATE('"+ start_date +"','DD/MON/YYYY') "
		+ "AND TO_DATE('"+ end_date +"','DD/MON/YYYY') "
		+ "AND r.EMPLOYEE_NO =" + employee_no;
		
		try{
			rs = stmt.executeQuery(selectQuery);
			System.out.println("PATIENT NO \t TEST NAME \t\t\t PATIENT NAME \t\t PRESCRIBE DATE");
			while(rs.next()){
				System.out.println(rs.getInt(1) + "\t\t" + rs.getString(2) + "\t\t\t" + rs.getString(3) + "\t\t\t" + rs.getDate(4));
			}
		}catch(SQLException e){
			System.out.println("Could not execute the query.");
		}
	}
	
	
	public void closeConnection() {
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
