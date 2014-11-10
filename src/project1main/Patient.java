package project1main;

import java.util.Date;

public class Patient {

	private int health_care_no;
	private String name;
	private String address;
	private Date birth_day; // optional: casting to String
	private String phone;
	

	public Patient() {
		//constructor
	}
	
	public int getHealth_care_no() {
		return health_care_no;
	}


	public void setHealth_care_no(int health_care_no) {
		this.health_care_no = health_care_no;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}


	public Date getBirth_day() {
		return birth_day;
	}


	public void setBirth_day(Date birth_day) {
		this.birth_day = birth_day;
	}


	public String getPhone() {
		return phone;
	}


	public void setPhone(String phone) {
		this.phone = phone;
	}	
	
	public String toString(){
		String line = this.getHealth_care_no() + "\t\t" + this.getName() + "\t\t" + this.getAddress() + "\t\t" +
		this.getBirth_day() + "\t\t" + this.getPhone();
		return line;
	}
}
