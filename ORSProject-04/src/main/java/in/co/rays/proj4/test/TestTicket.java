package in.co.rays.proj4.test;

import in.co.rays.proj4.bean.PTicketbean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.model.PTicektModel;

public class TestTicket {

	public static void main(String[] args) throws DatabaseException, ApplicationException {
		
		testNextPk();
		testAdd();
		
		
	}
	
	public static void testNextPk() throws DatabaseException {
		
		PTicektModel model = new PTicektModel();
		
	  int i = 	model.nextPk();
	  
	  System.out.println("sucess=" + i);
	}
	
	public static void  testAdd() throws DatabaseException, ApplicationException {
		
		PTicketbean bean = new PTicketbean();
		PTicektModel model = new PTicektModel();
		
		bean.setTicketId(11123l);
		bean.setPassengerName("lucky");
		bean.setFare(2000);
		bean.setSeatNumber("233CAA");
		
	    	model.add(bean);
		
		
		
	}
}
