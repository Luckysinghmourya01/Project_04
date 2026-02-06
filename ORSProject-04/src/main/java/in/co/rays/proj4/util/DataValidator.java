package in.co.rays.proj4.util;

import java.util.Calendar;
import java.util.Date;

public class DataValidator {

	public static boolean isNull(String val) {
		if (val == null || val.trim().length() == 0) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean isNotNull(String val) {
		return !isNull(val);
	}

	public static boolean isInteger(String val) {

		if (isNotNull(val)) {
			try {
				Integer.parseInt(val);
				return true;
			} catch (NumberFormatException e) {
				return false;
			}
		} else {
			return false;
		}
	}

	public static boolean isLong(String val) {

		if (isNotNull(val)) {
			try {
				Long.parseLong(val);
				return true;
			} catch (NumberFormatException e) {
				return false;
			}
		} else {
			return false;
		}
	}

	public static boolean isEmail(String val) {

		String emailreg = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
		if (isNotNull(val)) {
			try {
				return val.matches(emailreg);
			} catch (NumberFormatException e) {
				return false;
			}
		} else {
			return false;
		}
	}

	public static boolean isName(String val) {

		String namereg = "^[^-\\s][\\p{L} .'-]+$";
		if (isNotNull(val)) {
			try {
				return val.matches(namereg);
			} catch (NumberFormatException e) {
				return false;
			}
		} else {
			return false;
		}
	}

	public static boolean isRollNo(String val) {

		String rollreg = "[a-zA-Z]{2}[0-9]{3}";

		if (isNotNull(val)) {
			try {
				return val.matches(rollreg);
			} catch (NumberFormatException e) {
				return false;
			}
		} else {
			return false;
		}
	}

	public static boolean isPassword(String val) {

		String passreg = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,12}";

		if (isNotNull(val)) {
			try {
				return val.matches(passreg);
			} catch (NumberFormatException e) {
				return false;
			}
		}
		return false;
	}

	public static boolean isPasswordLength(String val) {

		if (isNotNull(val) && val.length() >= 8 && val.length() <= 12) {
			return true;
		}

		else {
			return false;
		}
	}

	public static boolean isPhoneNo(String val) {

		String phonereg = "^[6-9][0-9]{9}$";
		if (isNotNull(val)) {
			try {
				return val.matches(phonereg);
			} catch (NumberFormatException e) {
				return false;
			}
		}
		return false;
	}

	public static boolean isPhoneLength(String val) {
		if (isNotNull(val) && val.length() == 10) {
			return true;
		}

		else {
			return false;
		}
	}

	
	  public static boolean isDate(String val) {
	  
	  Date d = null;
	  
	  if (isNotNull(val)) {
		  d = DataUtility.getDate(val);
		  } 
	  return d != null;
	  }
	  
	  
	  
	  
	  public static boolean isSunday(String val) {
	  
	  Calendar call = Calendar.getInstance();
	  call.setTime(DataUtility.getDate(val)); 
	  int i =  call.get(Calendar.DAY_OF_WEEK);
	  
	  
	  if(i == Calendar.SUNDAY) { 
		  return true; 
		  } else
		  { 
			  return false;
		  } 
	  }
	 

	public static void main(String[] args) {

		// test is null or not null

		System.out.println("is null test");
		System.out.println("empty String: " + isNull(""));
		System.out.println("null string: " + isNull(null));
		System.out.println("not null String: " + isNotNull(""));

		// test isInteger
		System.out.println();
		System.out.println("is integer test");
		System.out.println("valid integer string : '123' ->" + isInteger("123"));
		System.out.println("invalid integer string : 'abc' ->" + isInteger("abc"));
		System.out.println("null string: " + isInteger(null));

		// Test isLong
		System.out.println("\nisLong Test:");
		System.out.println("Valid Long String: '1234567890' -> " + isLong("1234567890"));
		System.out.println("Invalid Long String: 'abc' -> " + isLong("abc"));

		// Test isEmail
		System.out.println("\nisEmail Test:");
		System.out.println("Valid Email: 'test@example.com' -> " + isEmail("test@example.com"));
		System.out.println("Invalid Email: 'test@.com' -> " + isEmail("test@.com"));

		// Test isName
		System.out.println("\nisName Test:");
		System.out.println("Valid Name: 'John Doe' -> " + isName("lucky"));
		System.out.println("Invalid Name: '123John' -> " + isName("123John"));

		// Test isRollNo
		System.out.println("\nisRollNo Test:");
		System.out.println("Valid RollNo: 'AB123' -> " + isRollNo("AB123"));
		System.out.println("Invalid RollNo: 'A1234' -> " + isRollNo("1A1234"));

		// Test isPassword
		System.out.println("\nisPassword Test:");
		System.out.println("Valid Password: 'Passw0rd@123' -> " + isPassword("Password@123"));
		System.out.println("Invalid Password: 'pass123' -> " + isPassword("pass@123"));

	}

}
