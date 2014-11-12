

import java.sql.*;
import java.util.*;


public class DataSource {

	private Connection con = null;
	private Statement stmt = null;
	private ResultSet rs = null;
	private final String DRIVERNAME = "oracle.jdbc.driver.OracleDriver";
	private boolean isConnected = true;
	
	public DataSource(String URL, String username, String password) {
		try {
			Class.forName(DRIVERNAME);
			con = DriverManager.getConnection(URL, username, password);
			stmt = con.createStatement();
		} catch (SQLException e) {
			System.out.println("Cannot connect to database.");
			isConnected = false;
		} catch (ClassNotFoundException e) {
			System.out.println("Class is not found.");
			isConnected = false;
		}
	}

	public boolean isConnected(){
		return isConnected;
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
			if (Helper.isInteger(doctor_info)) {
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

			if (Helper.isInteger(patient_info)) {
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
	
	public List<Patient> getAlarmingAgePatients(String testName) {
		
		List<Patient> lst = new ArrayList<Patient>();
		int typeId;
		try {
			rs = stmt.executeQuery("SELECT type_id FROM test_type t WHERE UPPER(t.test_name) = " + testName.toUpperCase());
			if (rs.next()) {
				typeId = rs.getInt("type_id");
			} else {
				return null;
			}
			
			// create medical risk view
			stmt.executeUpdate("DROP VIEW medical_risk");
			stmt.executeUpdate("" 
					+ "CREATE VIEW medical_risk(medical_type,alarming_age,abnormal_rate) AS"
					+ "SELECT c1.type_id,min(c1.age),ab_rate"
					+ "FROM" 
					+      "(SELECT   t1.type_id, count(distinct t1.patient_no)/count(distinct t2.patient_no) ab_rate"
					+       "FROM     test_record t1, test_record t2"
					+       "WHERE    t1.result <> 'normal' AND t1.type_id = t2.type_id"
					+       "GROUP BY t1.type_id"
					+       ") r,"
					+      "(SELECT   t1.type_id,age,COUNT(distinct p1.health_care_no) AS ab_cnt"
					+       "FROM     patient p1,test_record t1,"
					+                "(SELECT DISTINCT trunc(months_between(sysdate,p1.birth_day)/12) AS age FROM patient p1)"
					+       "WHERE    trunc(months_between(sysdate,p1.birth_day)/12)>=age"
					+                "AND p1.health_care_no=t1.patient_no"
					+                "AND t1.result<>'normal'"
					+       "GROUP BY age,t1.type_id"
					+       ") c1," 
					+       "(SELECT  t1.type_id,age,COUNT(distinct p1.health_care_no) AS cnt"
					+        "FROM    patient p1, test_record t1,"
					+                "(SELECT DISTINCT trunc(months_between(sysdate,p1.birth_day)/12) AS age FROM patient p1)"
					+        "WHERE trunc(months_between(sysdate,p1.birth_day)/12)>=age"
					+              "AND p1.health_care_no=t1.patient_no"
					+        "GROUP BY age,t1.type_id"
					+       ") c2"
					+ "WHERE  c1.age = c2.age AND c1.type_id = c2.type_id AND c1.type_id = r.type_id"
					+        "AND c1.ab_cnt/c2.cnt>=2*r.ab_rate"
					+ "GROUP BY c1.type_id,ab_rate;");
			
			rs = stmt.executeQuery(""
					+ "SELECT DISTINCT name, address, phone"
					+ "FROM   patient p, medical_risk m"
					+ "WHERE  trunc(months_between(sysdate,birth_day)/12) >= m.alarming_age"
					+ "AND m.medical_type = " + typeId
					+ "AND"
					+        "p.health_care_no NOT IN (SELECT patient_no"
					+                                 "FROM   test_record t"
					+                                 "WHERE  m.medical_type = t.type_id"
					+                                ");");
			
			while (rs.next()) {
				Patient patient = new Patient();
				patient.setName(rs.getString("name"));
				patient.setAddress(rs.getString("address"));
				patient.setPhone(rs.getString("phone"));
				lst.add(patient);
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Could not get the alarming age patients.");
			return null;
		}
		
		return lst;
	}

	public boolean checkTestName(String testName) {
		try {
			rs = stmt.executeQuery("SELECT type_id FROM test_type t WHERE UPPER(t.test_name) = " + testName.toUpperCase());
			if (rs.next()) {
				return true;
			} 
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Could not get type_id for test named " + testName);
		}
		return false;

	}
	
	/***********************************
	 * NANCY****************************
	 ***********************************
	 */
	
	public ResultSet searchEngineInfo(String patient_info){
		try{
			if(Helper.isInteger(patient_info)){
				String testRecordQuery = 
				"SELECT p.name, p.health_care_no, t.patient_no, t.employee_no, " +
				"t.test_id, t.type_id, tt.type_id, tt.test_name, t.test_date, t.result " +
				"FROM patient p, test_record t, test_type tt " +
				"WHERE " + " p.health_care_no = " + Integer.parseInt(patient_info) + 
				" AND t.patient_no = p.health_care_no " +
				"AND t.type_id = tt.type_id";
				rs = stmt.executeQuery(testRecordQuery);
			}else {
				String testRecordQuery = 
				"SELECT p.name, p.health_care_no, t.patient_no, t.employee_no, t.test_id, "+
				"t.type_id, tt.type_id, tt.test_name, t.result, t.test_date " +
				"FROM patient p, test_record t, test_type tt " +
				"WHERE t.patient_no = p.health_care_no " +
				"AND UPPER(p.name) = UPPER('" + patient_info + "')" +
				" AND t.type_id = tt.type_id";
				rs = stmt.executeQuery(testRecordQuery);
			}
		} catch(SQLException e){
			
		}
		return rs;
	}

	public ResultSet checkRecord(String patientInfo){
		try {
			if(Helper.isInteger(patientInfo)){
				String testRecordQuery = 
					"SELECT * " +
					"FROM patient p, test_record t " +
					"WHERE " + " p.health_care_no = " + Integer.parseInt(patientInfo) + 
					" AND t.patient_no = p.health_care_no " +
					"AND t.result IS NULL" ;
				rs = stmt.executeQuery(testRecordQuery);
			}
			else{
				String testRecordQuery = 
				"SELECT * "+
				"FROM patient p, test_record t " +
				"WHERE t.patient_no = p.health_care_no " +
				"AND t.result IS NULL " +
				"AND UPPER(p.name) = UPPER ('" + patientInfo + "')";		
				rs = stmt.executeQuery(testRecordQuery);
			}
		} catch (SQLException e) {
		}
		return rs;
	}
	

	
	public Vector<TestRecord> getRecordList(ResultSet rs) {
		Vector<TestRecord> records = new Vector<TestRecord>();
		try {
			while (rs.next()) {
				TestRecord record = new TestRecord();
				record.setTest_id(rs.getInt("test_id"));
				record.setType_id(rs.getInt("type_id"));
				record.setPatient_no(rs.getInt("patient_no"));
				record.setEmployee_no(rs.getInt("employee_no"));
				record.setMedical_lab(rs.getString("medical_lab"));
				record.setResult(rs.getString("result"));
				record.setPrescribe_date(rs.getDate("prescribe_date"));
				record.setTest_date(rs.getDate("test_date"));
				records.add(record);
			}
		} catch (SQLException e) {
		}
		return records;
	}
	
	/*If there exists such a patient and test record and prescription
	 * then allow the user to enter in the information of the test
	 * such as lab name, test date, the test result**/
	public void updateTestResult(int healthNum, int doctorNum, String testResult, 
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
				System.out.println("Test Results have been added for record#: " + testId);
		} catch (SQLException e) {
			System.out.println("Your request cannot be completed. Please check your entries");
		}
	}
	
}
