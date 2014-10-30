package project1main;

public class Doctor {

	private int employee_no;
	private String clinic_address;
	private String office_phone;
	private String emergency_phone;
	private int health_care_no;

	public Doctor() {
		//constructor
	}
	
	public int getEmployee_no() {
		return employee_no;
	}

	public void setEmployee_no(int employee_no) {
		this.employee_no = employee_no;
	}

	public String getClinic_address() {
		return clinic_address;
	}

	public void setClinic_address(String clinic_address) {
		this.clinic_address = clinic_address;
	}

	public String getOffice_phone() {
		return office_phone;
	}

	public void setOffice_phone(String office_phone) {
		this.office_phone = office_phone;
	}

	public String getEmergency_phone() {
		return emergency_phone;
	}

	public void setEmergency_phone(String emergency_phone) {
		this.emergency_phone = emergency_phone;
	}

	public int getHealth_care_no() {
		return health_care_no;
	}

	public void setHealth_care_no(int health_care_no) {
		this.health_care_no = health_care_no;
	}

}
