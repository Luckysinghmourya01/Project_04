package in.co.rays.proj4.test;

import java.util.Iterator;



import in.co.rays.proj4.bean.bannerBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DublicateRecordException;
import in.co.rays.proj4.model.BannerModel;

public class TestBannerModel {

	public static void main(String[] args) throws DatabaseException, ApplicationException {
		
		//testAdd();
		//testnextPk();
		testSearch();
		
	}
	
	public static void testnextPk() throws DatabaseException {
		
		BannerModel model = new BannerModel();
		
	int i = 	model.nextPk();
	  System.out.println("next pk=" + i);
	
	}
	
	
	public static void testAdd() {
		
		BannerModel model = new BannerModel();
		bannerBean bean = new bannerBean();
		
		bean.setBannerCode("233CAA046");
		bean.setBannerTitle("Home page banner");
		bean.setImagePath("banner1.jpg");
		bean.setBannerStatus("Active");
		
		try {
			try {
				model.add(bean);
			} catch (DublicateRecordException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (ApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void testSearch() throws ApplicationException {

        bannerBean bean = new bannerBean();
        BannerModel model = new BannerModel();

        // Optional filter
        // bean.setBannerStatus("Active");

        java.util.List<bannerBean> list = model.search(bean, 0, 0);

        Iterator<bannerBean> it = list.iterator();
        while (it.hasNext()) {

            bean = it.next();

            System.out.println(bean.getId());
            System.out.println(bean.getBannerCode());
            System.out.println(bean.getBannerTitle());
            System.out.println(bean.getImagePath());
            System.out.println(bean.getBannerStatus());
            System.out.println("------------------------");
        }
    }
	
}
