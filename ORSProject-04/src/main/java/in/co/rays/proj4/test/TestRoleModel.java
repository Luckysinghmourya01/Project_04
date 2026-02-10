package in.co.rays.proj4.test;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import in.co.rays.proj4.bean.RoleBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.model.RoleModel;

public class TestRoleModel {

	public static void main(String[] args) throws Exception {
		//testAdd();
		// testUpdate();
		 testDelete();
		// testfindByPk();
		// testfindByName();
		//TestSearch();
	}

	public static void testAdd() throws Exception {
		RoleBean bean = new RoleBean();
		RoleModel model = new RoleModel();

		bean.setName("admin");
		bean.setDescription("admin");
		bean.setCreatedby("student");
		bean.setModifiedby("student");
		bean.setCreateddatetime(new Timestamp(new Date().getTime()));
		bean.setModifieddatetime(new Timestamp(new Date().getTime()));

		Long pk = model.add(bean);
		RoleBean addbean = model.findByPk(pk);
		if (addbean == null) {
			System.out.println("test add fail");
		}

	}

	public static void testUpdate() throws Exception {
		RoleBean bean = new RoleBean();
		RoleModel model = new RoleModel();
		bean.setId(1);
		bean.setName("admin");
		bean.setDescription("admin");
		bean.setCreatedby("student");
		model.update(bean);

		RoleBean updatedbean = model.findByPk(1L);

		if (!"Admin".equals(updatedbean.getName())) {
			System.out.println("Test Update Success");
		}
	}

	public static void testDelete() throws ApplicationException {
		RoleBean bean = new RoleBean();
		RoleModel model = new RoleModel();

		bean.setId(2l);
		  model.delete(bean);
	}

	public static void testfindByPk() throws ApplicationException {
		RoleBean bean = new RoleBean();
		RoleModel model = new RoleModel();

		bean = model.findByPk(1L);
		if (bean == null) {
			System.out.println("find by pk fail");
		} else {
			System.out.println(bean.getId());
			System.out.println(bean.getName());
			System.out.println(bean.getDescription());
		}
	}

	public static void testfindByName() {
		RoleBean bean = new RoleBean();
		RoleModel model = new RoleModel();

		try {
			bean = model.findByName("admin");
			if (bean == null) {
				System.out.println("find by name fail");
			} else {

				System.out.println(bean.getId());
				System.out.println(bean.getName());
				System.out.println(bean.getDescription());
				System.out.println(bean.getCreateddatetime());
			}
		} catch (ApplicationException e) {

			e.printStackTrace();
		}
	}

	public static void TestSearch() throws ApplicationException {
	
		RoleModel model = new RoleModel();
		RoleBean bean = new RoleBean();
		
		
		List list = model.search(bean, 0, 0);
		
		    
	Iterator<RoleBean> it = list.iterator();
	while(it.hasNext()) {
		bean = it.next();
		System.out.println(bean.getId());
		System.out.println(bean.getName());
		System.out.println(bean.getDescription());
		System.out.println(bean.getCreatedby());
		System.out.println(bean.getModifiedby());
		System.out.println(bean.getCreateddatetime());
	}
	}
}
