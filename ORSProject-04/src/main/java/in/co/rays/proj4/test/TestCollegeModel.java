package in.co.rays.proj4.test;

import java.sql.Timestamp;
import java.util.Date;

import in.co.rays.proj4.bean.CollageBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DublicateRecordException;
import in.co.rays.proj4.model.CollageModel;

public class TestCollegeModel {

	public static void main(String[] args) throws ApplicationException, DublicateRecordException {
      testAdd();
	}

	public static void testAdd() throws ApplicationException, DublicateRecordException {
		CollageModel model = new CollageModel();
		CollageBean bean = new CollageBean();

		bean.setName("IPS");
		bean.setAddress("rau");
		bean.setState("m.p");
		bean.setCity("Indore");
		bean.setPhoneNo("7804931017");
		bean.setCreatedby("admin");
		bean.setModifiedby("admin");
		bean.setCreateddatetime(new Timestamp(new Date().getTime()));
		bean.setModifieddatetime(new Timestamp(new Date().getTime()));
		
		long i = model.add(bean);
		
		System.out.println("add sucess" + 1);
	}
}
