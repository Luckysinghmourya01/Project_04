
package in.co.rays.proj4.test;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import in.co.rays.proj4.bean.UserBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DublicateRecordException;
import in.co.rays.proj4.model.UserModel;

public class TestUserModel {

	public static void main(String[] args)
			throws ParseException, ApplicationException, DublicateRecordException, DatabaseException {

		//testAdd();
		// testnext();
		// testUpdate();
		 //testDelete();
		//testFindbyPk();
		//testFindbyLogin();
		//testAuthenticate();
		testSearch();
	}

	private static void testnext() throws DatabaseException {

		UserModel m = new UserModel();
		int i = m.nextPk();
		System.out.println(i);

	}

	public static void testAdd() throws ParseException, ApplicationException, DublicateRecordException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		UserBean bean = new UserBean();
		UserModel model = new UserModel();

		bean.setFirstName("aman");
		bean.setLastName("panwar");
		bean.setLogin("aman@gmail.com");
		bean.setPassword("aman1212");
		bean.setDob(sdf.parse("2003-1-23"));
		bean.setMobileNo("7247084759");
		bean.setRoleId(2L);
		bean.setGender("male");
		bean.setCreatedby("admin");
		bean.setModifiedby("admin");
		bean.setCreateddatetime(new Timestamp(new Date().getTime()));

		model.add(bean);
	}

	public static void testUpdate() throws ApplicationException, DublicateRecordException, ParseException {
		UserBean bean = new UserBean();
		UserModel model = new UserModel();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		bean.setId(2L);
		bean.setFirstName("kamal");
		bean.setLastName("mehra");
		bean.setLogin("kamal@gmail.com");
		bean.setPassword("kamal1212");
		bean.setDob(sdf.parse("2003-1-23"));
		bean.setMobileNo("12347084759");
		bean.setRoleId(2L);
		bean.setGender("male");
		bean.setCreatedby("student");
		bean.setModifiedby("admin");
		bean.setCreateddatetime(new Timestamp(new Date().getTime()));
		bean.setModifieddatetime(new Timestamp(new Date().getTime()));

		model.update(bean);
	}

	public static void testDelete() throws ApplicationException {

		UserModel model = new UserModel();
		UserBean bean = new UserBean();

		bean.setId(2L);
		model.delete(bean);
	}

	public static void testFindbyPk() throws ApplicationException {

		UserBean bean = new UserBean();
		UserModel model = new UserModel();

		bean = model.findBypk(1L);
		if (bean == null) {
			System.out.println("fid by pk fail");
		} else {
			System.out.println(bean.getId());
			System.out.println(bean.getFirstName());
			System.out.println(bean.getLastName());
			System.out.println(bean.getCreatedby());
		}
	}

	public static void testFindbyLogin() throws ApplicationException {
		UserBean bean = new UserBean();
		UserModel model = new UserModel();

		bean = model.findByLogin("kamal@gmail.com");
		if (bean == null) {
			System.out.println("login failed");
		} else {
             System.out.println(bean.getId());
             System.out.println(bean.getFirstName());
             System.out.println(bean.getLastName());
             System.out.println(bean.getLogin());
		}
	}
	
	public static void testAuthenticate() throws ApplicationException {
		UserBean bean = new UserBean();
		UserModel model = new UserModel();

		bean = model.authenticate("kamal@gmail.com", "kamal1212");
		if (bean == null) {
			System.out.println("login failed");
		} else {
             System.out.println(bean.getId());
             System.out.println(bean.getFirstName());
             System.out.println(bean.getLastName());
             System.out.println(bean.getLogin());
             System.out.println(bean.getDob());
		}
	}
	
	public static void testSearch() throws ApplicationException {
		UserBean bean = new UserBean();
		UserModel model = new UserModel();
		
	List list = 	model.search(bean, 0, 0);
	
	     Iterator<UserBean> it =    list.iterator();
	     while(it.hasNext()) {
	    	bean =  it.next();
	    	
	    	System.out.println(bean.getId());
            System.out.println(bean.getFirstName());
            System.out.println(bean.getLastName());
            System.out.println(bean.getLogin());
            System.out.println(bean.getPassword());
            System.out.println(bean.getDob());
            System.out.println(bean.getMobileNo());
            System.out.println(bean.getRoleId());
            System.out.println(bean.getGender());
            System.out.println(bean.getCreatedby());
            System.out.println(bean.getModifiedby());
            System.out.println(bean.getCreateddatetime());
            System.out.println(bean.getModifieddatetime());
	    	
	     }
	}
}
