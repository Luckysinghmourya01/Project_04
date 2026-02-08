package in.co.rays.proj4.test;

import java.sql.Timestamp;
import java.util.Date;

import in.co.rays.proj4.bean.CourseBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DublicateRecordException;
import in.co.rays.proj4.model.CourseModel;

public class TestCourseModel {

	
	public static void main(String[] args) throws ApplicationException, DublicateRecordException, DatabaseException {
		testAdd();
		testnextPk();
	}
	
	
	public static void testnextPk() throws DatabaseException {
		
		CourseBean bean = new CourseBean();
		CourseModel model = new CourseModel();
		
	int i = 	model.nextPk();
	System.out.println(i);
	}
	
	
	public static void testAdd() throws ApplicationException, DublicateRecordException {
		CourseBean bean = new CourseBean();
		CourseModel model = new CourseModel();
		
		bean.setName("bca");
		bean.setDuration("3");
		bean.setDescription("bca is the");
		bean.setCreatedby("root");
		bean.setModifiedby("root");
		bean.setCreateddatetime(new Timestamp(new Date().getTime()));
		bean.setModifieddatetime(new Timestamp(new Date().getTime()));
		
		long i = model.add(bean);
		
		System.out.println(i + "added sucess");
	}
}
