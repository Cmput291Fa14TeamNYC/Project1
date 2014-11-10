package project1brett;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import project1main.Patient;

public class DataSource {

	private Connection con = null;
	private Statement stmt = null;
	private ResultSet rs = null;
	private final String DRIVERNAME = "oracle.jdbc.driver.OracleDriver";
//	private final String URL = "jdbc:oracle:thin:@localhost:1525:CRS";

	public DataSource(String url, String username, String password) {
		try {
			Class.forName(DRIVERNAME);
			con = DriverManager.getConnection(url, username, password);
			stmt = con.createStatement();
		} catch (SQLException e) {
			System.out.println("Cannot connect to database.");
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			System.out.println("Class is not found.");
			e.printStackTrace();
		}
	}

	/*
	 * The program shall allow a user to enter the detailed information about
	 * the prescription: >the employee_no or name of the doctor who prescribes
	 * the test >the test name >the name and/or the health_care_no of the
	 * patient
	 */

	public void enterPrescription(int employee_no, String test_name,
			String patient_no) {
		
		// check test name, return test id
		// check patient id, return name

		int doctor_id = 0;
		int patient_id = 0;
		int type_id = 0;
		int not_allowed = 0;

		try {
			// check whether the doctor exists in doctor table
			if (selectDoctorByEmpNo(employee_no).next()) { // return a single row
				System.out.println("Doctor name: " + rs.getString("name"));
				doctor_id = rs.getInt("employee_no");
			}
			// check whether the patient exists in patient table
			if (selectPatient(patient_no).next()) {
				System.out.println("Patient name: " + rs.getString("name"));
				patient_id = rs.getInt("health_care_no");
			}
			// check whether the test type exists in test type table
			if (selectTestByName(test_name).next()) {
				System.out.println("Test name: " + rs.getString("type_id"));
				type_id = rs.getInt("type_id");
			}
			if (selectNotAllowedByPatient(patient_id).next()) {
				System.out.println("Not allowed: " + rs.getString("test_id"));
				not_allowed = rs.getInt("test_id");
			}

			// check whether the patient is allowed to take the test type
			if (type_id == not_allowed) {
				System.out
						.println("Sorry this patient is not allowed to take this test!");
			} else {
				// inserting into record
				String insertRecordQuery = "INSERT INTO test_record VALUES ("
						+ getLastId("test_id", "test_record") + "," + type_id
						+ "," + patient_id + "," + doctor_id + "," + "null"
						+ "," + "null" + "," + "sysdate" + "," + "null" + ")";
				stmt.execute(insertRecordQuery);
				con.commit();
				System.out.println("New record is inserted.");
			}
		} catch (SQLException e) {
			System.out.println("Cannot insert values into Record table.");
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

	public ResultSet selectPatient(String patient_info) {
		try {
			// checking whether the user enters health care no or patient name
			if (isInteger(patient_info)) {
				String checkPatientByNoQuery = "SELECT * FROM PATIENT WHERE health_care_no = "
						+ Integer.parseInt(patient_info);
				rs = stmt.executeQuery(checkPatientByNoQuery);
			} else {
				String checkPatientByNameQuery = "SELECT * FROM PATIENT WHERE name = '"
						+ patient_info + "'";
				rs = stmt.executeQuery(checkPatientByNameQuery);
			}
		} catch (SQLException e) {
			System.out.println("Cannot execute select patient statement.");
		}
		return rs;
	}

	public ResultSet selectDoctorByEmpNo(int employee_no) {
		String checkDoctorQuery = "SELECT * FROM PATIENT p, DOCTOR d "
				+ "WHERE d.health_care_no = p.health_care_no AND d.employee_no = "
				+ employee_no;
		try {
			rs = stmt.executeQuery(checkDoctorQuery);
		} catch (SQLException e) {
			System.out.println("Cannot execute select doctor statement.");
		}
		return rs;
	}

	public ResultSet selectTestByName(String test_name) {
		String checkTestQuery = "SELECT * FROM TEST_TYPE WHERE test_name = '"
				+ test_name + "'";
		try {
			rs = stmt.executeQuery(checkTestQuery);
		} catch (SQLException e) {
			System.out.println("Cannot execute select test type statement.");
		}
		return rs;
	}

	public ResultSet selectNotAllowedByPatient(int patient_no) {
		String checkNotAllowedQuery = "SELECT * FROM NOT_ALLOWED WHERE health_care_no = "
				+ patient_no;
		try {
			rs = stmt.executeQuery(checkNotAllowedQuery);
		} catch (SQLException e) {
			System.out.println("Cannot execute select not allowed statement.");
		}
		return rs;
	}

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
		return lastId+1;
	}
	
	
	// BRETT
	// =====
	
	
	/**
	 * Submits a query to the database retrieving one-row result set of the
	 * patient matching the provided health care number.
	 * 
	 * @param health_care_no
	 * @return
	 */
	public ResultSet selectPatientByHealthCareNo(int health_care_no) {
		String patientQuery = "SELECT * FROM patient p WHERE p.health_care_no = " + health_care_no;
		
		rs = null;
		try {
			rs = stmt.executeQuery(patientQuery);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
		
	}
	
	
	/**
	 * Delegates the retrieval of a patient row result set to another 
	 * method, then converts the patient row from the result set to a 
	 * patient java object and returns it.
	 * 
	 * @param health_care_no
	 * @return
	 */
	public Patient getPatient(int health_care_no) {
		Patient patient = new Patient();
		
		try {
			if (selectPatientByHealthCareNo(health_care_no).next()) {
				patient.setHealth_care_no(rs.getInt("health_care_no"));
				patient.setName(rs.getString("name"));
				patient.setBirth_day(rs.getDate("birth_day"));
				patient.setAddress(rs.getString("address"));
				patient.setPhone(rs.getString("phone"));
				return patient;
			} else {
				return null;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}
	
	/**
	 * Submits an update statement to the database for the patient with the 
	 * given health care number. It updates the given field with the given value.
	 * 
	 * @param health_care_no
	 * @param field
	 * @param value
	 */
	
	public void updatePatient(int health_care_no, String field, String value) {
		
		String patientUpdate = "";
		
		if (field.equals("name") || field.equals("address") || field.equals("phone")) {
			patientUpdate = "UPDATE patient SET " + field + "='" + value + 
					"' WHERE health_care_no=" + health_care_no;
		} else if (field.equals("birth_day")) {
			patientUpdate = "UPDATE patient SET " + field + "=to_date('" + value + 
					"', 'YYYY-MM-DD') WHERE health_care_no=" + health_care_no;
		}
		
		try {
			stmt.executeUpdate(patientUpdate);
			con.commit();
			System.out.println("Patient updated.");
		} catch (SQLException e) {
			System.out.println("Sorry, could not update " + field);
		}
	}

}
