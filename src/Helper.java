

import java.util.Vector;

public class Helper {
	
	public Helper(){}
	
	public static boolean isInteger(String s) {
		try {
			Integer.parseInt(s);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public static void printPatients(Vector<Patient> patients){
		System.out.println("Health Care no \t Patient name \t Address \t\t Birth day \t\t Phone no");
		for(Patient p : patients){		
			System.out.println(p.toString());
		}
	}
	
	public static void printDoctors(Vector<Doctor> doctors){
		System.out.println("Employee no \t Clinic address \t\t Office phone \t\t Emergency phone");
		for(Doctor d : doctors){		
			System.out.println(d.toString());
		}
	}
	
	public static void printRecords(Vector<TestRecord> records){
		System.out.println("Test id \t Health care no \t\t Employee no \t\t Prescribe date");
		for(TestRecord r : records){		
			System.out.println(r.toString());
		}
	}
	
}
