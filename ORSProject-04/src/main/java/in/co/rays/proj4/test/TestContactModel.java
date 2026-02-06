package in.co.rays.proj4.test;

import java.sql.SQLException;

import in.co.rays.proj4.bean.ContactBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.model.ContactModel;

public class TestContactModel {
    
	public static void main(String[] args) throws SQLException, ApplicationException, DatabaseException {
		testNextPk();
		testAdd();
	}
	
	
	public static void testNextPk() throws SQLException, ApplicationException, DatabaseException {
		ContactModel model = new ContactModel();
		ContactBean bean  = new ContactBean();
		
	int	i = model.nextPk();
	
	System.out.println("max id = " + i);
	}
	
	public static void testAdd() throws ApplicationException, SQLException, DatabaseException {
		
		ContactModel model = new ContactModel();
		ContactBean bean  = new ContactBean();
		
		bean.setName("aman");
		bean.setEmail("aman12@gmail.com");
		bean.setMobileNo("9911564321");
		bean.setMessage("how are you");
		
		  long i =  model.add(bean);
		  
		  System.out.println("add sucessfull" + i);
		
	}
}


