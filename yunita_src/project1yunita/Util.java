package project1yunita;

import java.util.Vector;

import project1main.Patient;

public class Util {

	public Util(){}
	
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
	
	
}
