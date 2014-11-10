package project1main;

public class Helpers {
	
	// http://stackoverflow.com/questions/5439529/determine-if-a-string-is-an-integer-in-java, On Nov 6, 2014
	// User: corsiKa
	public static boolean isInteger(String s) {
	    try { 
	        Integer.parseInt(s); 
	    } catch(NumberFormatException e) { 
	        return false; 
	    }
	    // only got here if we didn't return false
	    return true;
	}
}
