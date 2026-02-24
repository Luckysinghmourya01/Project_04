package in.co.rays.proj4.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import in.co.rays.proj4.bean.AtmBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.model.AtmModel;

public class Atmtest {

	public static void main(String[] args) throws ParseException, ApplicationException {
		
		testUpdate();
		
	}
	
	public static void testUpdate() throws ParseException, ApplicationException {
		
		SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd");
		
		AtmBean bean = new AtmBean();
		AtmModel model = new AtmModel();
		bean.setId(1);
		bean.setLocation("bhopal");
		bean.setCashAailable(1200);
		bean.setDob(sdf.parse("2009-11-23"));
		bean.setRemark("unsucess");
		
		 model.update(bean);
	}
}
