package in.co.rays.proj4.controller;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.bean.CollageBean;
import in.co.rays.proj4.controller.BaseCtl;
import in.co.rays.proj4.controller.ORSView;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.model.CollageModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;
@WebServlet("/CollegeListCtl")
public class CollegeListCtl extends BaseCtl {

	
	@Override
	protected void preload(HttpServletRequest request) {

		CollageModel collegeModel = new CollageModel();
		try {
		List collegeList = 	collegeModel.list();
		request.setAttribute("collegeList", collegeList);
		} catch (ApplicationException e) {
			
			e.printStackTrace();
		}
	}
	
	@Override
	protected BaseBean populateBean(HttpServletRequest request) {
		  CollageBean bean = new CollageBean();
		  
		  bean.setName(DataUtility.getString(request.getParameter("name")));
		  bean.setCity(DataUtility.getString(request.getParameter("city")));
		  bean.setId(DataUtility.getLong(request.getParameter("courseId")));
		  
		  return bean;
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int pageNo = 1;
		int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));
		
		 CollageBean bean = (CollageBean)populateBean(request);
		 CollageModel model = new CollageModel();
		 
		 try {
		List<CollageBean> list = 	model.search(bean, pageNo, pageSize);
		  List<CollageBean> next = model.search(bean, pageNo + 1, pageSize);
		  
		  if(list == null || list.isEmpty()) {
			  ServletUtility.setErrorMessage("No record found", request);
		  }
		  
		  ServletUtility.setList(list, request);
		  ServletUtility.setPageNo(pageNo, request);
		  ServletUtility.setPageSize(pageSize, request);
		  ServletUtility.setBean(bean, request);
		  request.setAttribute("nextListSize", next);
		  ServletUtility.forword(getView(), request, response);
		  
		} catch (ApplicationException e) {
			
			e.printStackTrace();
			ServletUtility.handleException(e, request, response);
			return;
		}
		
		
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		ServletUtility.forword(getView(), request, response);
	}
	
	@Override
	protected String getView() {
		return ORSView.COLLEGE_LIST_VIEW;
	}
}
