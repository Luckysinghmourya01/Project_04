package in.co.rays.proj4.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import in.co.rays.proj4.bean.SubscriptionBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DublicateRecordException;
import in.co.rays.proj4.model.SubscriptionModel;

public class testSubscription {

	public static void main(String[] args) throws DatabaseException, ParseException, ApplicationException, DublicateRecordException {
		
		testNextPk();
		testAdd();
	}
	
	public static void testNextPk() throws DatabaseException {
		
		SubscriptionModel model = new SubscriptionModel();
		
		Long i = model.nextPk();
		
		System.out.println("next pk=" + i);
	}
	
	public static void testAdd() throws ParseException, ApplicationException, DublicateRecordException {
		
		SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd");
		
		SubscriptionModel model = new SubscriptionModel();
		SubscriptionBean bean = new SubscriptionBean();
		
		bean.setSubscriptionCode("233CAA#23");
		bean.setPlanName("netflix");
		bean.setStartDate(sdf.parse("2026-2-20"));
		bean.setEndDate(sdf.parse("2026-3-19"));
		bean.setSubscriptionStatus("active");
		
		Long i = model.add(bean);
		
		System.out.println("sucess" + i);
	}
}
