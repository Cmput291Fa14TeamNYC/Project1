package project1nancy;

import java.sql.*;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
/*connect to data source once in main application*/
		
		System.out.println("Main Menu");

		System.out.println("1.Prescription");
		System.out.println("2.Medical Test");
		System.out.println("3.Patient Information Update");
		System.out.println("4.Search Engine");
		System.out.println("5.Exit");

		Scanner input = new Scanner(System.in);
		int userInput = input.nextInt();
		
		switch (userInput) {
		
		case 1:
			System.out.println("1.Prescription");
			break;
			
		case 2:
			System.out.println("2.Medical Test");
			break;
		
		case 3:
			System.out.println("3.Patient");
			break;
			
		case 4:
			System.out.println("4.Search Engine");
			break;
			
		case 5:
			System.out.println("1.Exit");
			break;
		}
	
	}
}
