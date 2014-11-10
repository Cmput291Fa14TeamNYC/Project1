package project1yunita;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import project1main.Doctor;
import project1main.Patient;

public class DataSource {

	private Connection con = null;
	private Statement stmt = null;
	private ResultSet rs = null;
	private final String DRIVERNAME = "oracle.jdbc.driver.OracleDriver";

	public DataSource(String URL, String username, String password) {
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
	// >> insert new prescription into record table if patient is allowed to
	// take the specified test
	public void enterPrescription(int employee_no, int patient_no, int type_id) {
		try {
			// check whether the patient is allowed to take the specified test
			// type
			if (this.isAllowed(patient_no, type_id) == false) {
				System.out
						.println("Sorry this patient is not allowed to take this test!");
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

	// Method: checkDoctor
	// >> check whether the doctor with the specified doctor information
	// (name or employee) exists in doctor table
	public ResultSet checkDoctor(String doctor_info) {
		try {
			// checking whether the user enters employee no or doctor name
			if (Util.isInteger(doctor_info)) {
				String selectDoctorByNoQuery = "SELECT * FROM DOCTOR WHERE employee_no = "
						+ Integer.parseInt(doctor_info);
				rs = stmt.executeQuery(selectDoctorByNoQuery);
			} else {
				String selectDoctorByNameQuery = "SELECT * FROM PATIENT p, DOCTOR d "
						+ "WHERE p.health_care_no = d.health_care_no AND UPPER(name) = UPPER('"
						+ doctor_info + "')";
				rs = stmt.executeQuery(selectDoctorByNameQuery);
			}

		} catch (SQLException e) {
			System.out.println("Could not find the doctor.");
		}
		return rs;
	}

	// Method: getDoctorList
	// >> return the list of doctors
	public Vector<Doctor> getDoctorList(ResultSet rs) {
		Vector<Doctor> doctors = new Vector<Doctor>();
		try {
			while (rs.next()) {
				System.out.println("here");
				Doctor doctor = new Doctor();
				doctor.setHealth_care_no(rs.getInt("health_care_no"));
				doctor.setClinic_address(rs.getString("clinic_address"));
				doctor.setEmergency_phone(rs.getString("emergence_phone"));
				doctor.setEmployee_no(rs.getInt("employee_no"));
				doctor.setOffice_phone(rs.getString("office_phone"));
				doctors.add(doctor);
			}
		} catch (SQLException e) {
		}
		return doctors;
	}

	// Method: checkPatient
	// >> check whether the patient with the specified patient information
	// (name or health care no) exists in patient table
	public ResultSet checkPatient(String patient_info) {

		try {
			// checking whether the user enters health care no or patient name
			if (Util.isInteger(patient_info)) {
				String selectPatientByNoQuery = "SELECT * FROM PATIENT WHERE health_care_no = "
						+ Integer.parseInt(patient_info);
				rs = stmt.executeQuery(selectPatientByNoQuery);
			} else {
				String selectPatientByNameQuery = "SELECT * FROM PATIENT WHERE UPPER(name) = UPPER('"
						+ patient_info + "')";
				rs = stmt.executeQuery(selectPatientByNameQuery);
			}

		} catch (SQLException e) {
			System.out.println("Could not find the patient.");
		}
		return rs;
	}

	// Method: getPatientList
	// >> return the list of patients
	public Vector<Patient> getPatientList(ResultSet rs) {
		Vector<Patient> patients = new Vector<Patient>();
		try {
			while (rs.next()) {
				Patient patient = new Patient();
				patient.setHealth_care_no(rs.getInt("health_care_no"));
				patient.setName(rs.getString("name"));
				patient.setBirth_day(rs.getDate("birth_day"));
				patient.setAddress(rs.getString("address"));
				patient.setPhone(rs.getString("phone"));
				patients.add(patient);
			}
		} catch (SQLException e) {
		}
		return patients;
	}

	// Method: checkTestByName
	// >> check whether the test name exists in test type table,
	// if yes, then return its test type id
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
	public boolean isAllowed(int patient_no, int test_id) {
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

	// Method: listRecordByPrescribedDate
	// >> list the health care no, patient name, test type name, prescribing
	// date
	// of all tests prescribed by a given doctor during a specified period.
	public void listRecordByPrescribedDate(String start_date, String end_date,
			int employee_no) {
		String selectQuery = "SELECT r.PATIENT_NO, t.TEST_NAME, p.NAME, r.PRESCRIBE_DATE "
				+ "FROM TEST_RECORD r, PATIENT p, TEST_TYPE t "
				+ "WHERE r.PATIENT_NO = p.HEALTH_CARE_NO "
				+ "AND r.TYPE_ID = t.TYPE_ID "
				+ "AND r.PRESCRIBE_DATE BETWEEN TO_DATE('"
				+ start_date
				+ "','DD/MM/YYYY') "
				+ "AND TO_DATE('"
				+ end_date
				+ "','DD/MM/YYYY') " + "AND r.EMPLOYEE_NO =" + employee_no;

		try {
			rs = stmt.executeQuery(selectQuery);
			System.out
					.println("PATIENT NO \t TEST NAME \t\t\t PATIENT NAME \t\t PRESCRIBE DATE");
			while (rs.next()) {
				System.out
						.println(rs.getInt(1) + "\t\t" + rs.getString(2)
								+ "\t\t\t" + rs.getString(3) + "\t\t\t"
								+ rs.getDate(4));
			}
		} catch (SQLException e) {
			System.out.println("Could not execute the query.");
		}
	}

	public void closeConnection() {
		try {
			con.close();
		} catch (SQLException e) {
			System.out.println("Could not close the connection.");
		}
	}

	/***********************************
	 * BRETT****************************
	 ***********************************
	 */

	public ResultSet selectPatientByHealthCareNo(int health_care_no) {
		String patientQuery = "SELECT * FROM patient p WHERE p.health_care_no = "
				+ health_care_no;

		rs = null;
		try {
			rs = stmt.executeQuery(patientQuery);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;

	}

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

	public void updatePatient(int health_care_no, String field, String value) {
		// UPDATE table_name
		// SET column1=value1,column2=value2,...
		// WHERE some_column=some_value;

		String patientUpdate = "";

		if (field.equals("name") || field.equals("address")
				|| field.equals("phone")) {
			patientUpdate = "UPDATE patient SET " + field + "='" + value
					+ "' WHERE health_care_no=" + health_care_no;
		} else if (field.equals("birth_day")) {
			patientUpdate = "UPDATE patient SET " + field + "=to_date('"
					+ value + "', 'YYYY-MM-DD') WHERE health_care_no="
					+ health_care_no;
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
