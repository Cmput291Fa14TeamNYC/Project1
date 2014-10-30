package ca.ualberta.cmput291TeamNYC.project1;

import java.util.Date;

public class TestRecord {

	private int test_id;
	private int type_id;
	private int patient_no;
	private int employee_no;
	private String medical_lab;
	private String result;
	private Date prescribe_date; // optional: casting to String
	private Date test_date; // optional: casting to String

	public TestRecord() {
	}

	public int getTest_id() {
		return test_id;
	}

	public void setTest_id(int test_id) {
		this.test_id = test_id;
	}

	public int getType_id() {
		return type_id;
	}

	public void setType_id(int type_id) {
		this.type_id = type_id;
	}

	public int getPatient_no() {
		return patient_no;
	}

	public void setPatient_no(int patient_no) {
		this.patient_no = patient_no;
	}

	public int getEmployee_no() {
		return employee_no;
	}

	public void setEmployee_no(int employee_no) {
		this.employee_no = employee_no;
	}

	public String getMedical_lab() {
		return medical_lab;
	}

	public void setMedical_lab(String medical_lab) {
		this.medical_lab = medical_lab;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public Date getPrescribe_date() {
		return prescribe_date;
	}

	public void setPrescribe_date(Date prescribe_date) {
		this.prescribe_date = prescribe_date;
	}

	public Date getTest_date() {
		return test_date;
	}

	public void setTest_date(Date test_date) {
		this.test_date = test_date;
	}

}
