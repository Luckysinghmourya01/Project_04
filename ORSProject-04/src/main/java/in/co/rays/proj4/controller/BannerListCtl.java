package in.co.rays.proj4.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.bean.UserBean;
import in.co.rays.proj4.bean.bannerBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.model.BannerModel;
import in.co.rays.proj4.model.UserModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;
@WebServlet("/BannerListCtl")
public class BannerListCtl extends BaseCtl{

	
	@Override
	protected BaseBean populateBean(HttpServletRequest request) {
		
		bannerBean bean = new bannerBean();
		
		bean.setBannerCode(DataUtility.getString(request.getParameter("bannerCode")));
		bean.setBannerTitle(DataUtility.getString(request.getParameter("bannerTitle")));
		
		return bean;
	}
	
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int pageNo = 1;
		int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));

	bannerBean bean = (bannerBean)	populateBean(request);
	BannerModel model = new BannerModel();
	
	try {
	List<bannerBean> list = 	model.search(bean, pageNo, pageSize);
	 List<bannerBean> next =    model.search(bean, pageNo+1, pageSize);
	 
	 if (list == null || list.isEmpty()) {
			ServletUtility.setErrorMessage("Record not found", request);

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
		ServletUtility.forword(getView(), request, response);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		List list = null;
		List next = null;

		int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
		int pageSize = DataUtility.getInt(request.getParameter("pageSize"));

		pageNo = (pageNo == 0) ? 1 : pageNo;
		pageSize = (pageSize == 0) ? DataUtility.getInt(PropertyReader.getValue("page.size")) : pageSize;

		bannerBean bean = (bannerBean) populateBean(request);
		BannerModel model = new BannerModel();
		String op = DataUtility.getString(request.getParameter("operation"));
		String[] ids = request.getParameterValues("ids");

		try {

			if (OP_SEARCH.equalsIgnoreCase(op) || "Next".equalsIgnoreCase(op) || "Previous".equalsIgnoreCase(op)) {

				if (OP_SEARCH.equalsIgnoreCase(op)) {
					pageNo = 1;
				} else if (OP_NEXT.equalsIgnoreCase(op)) {
					pageNo++;
				} else if (OP_PREVIOUS
						.equalsIgnoreCase(op) && pageNo > 1) {
					pageNo--;
				}
			} else if (OP_NEW.equalsIgnoreCase(op)) {

				ServletUtility.redirect(ORSView.BANNER_CTL, request, response);
				return;
			} else if (OP_DELETE.equalsIgnoreCase(op)) {
				pageNo = 1;
				if (ids != null && ids.length > 0) {
					bannerBean deleteBean = new bannerBean();
					for (String id : ids) {
						deleteBean.setId(DataUtility.getInt(id));
						model.delete(deleteBean);
						ServletUtility.setSuccessMessage("User deleted sucessfully", request);
					}
				} else {
					ServletUtility.setErrorMessage("Selected at least one record", request);
				}
			} else if (OP_RESET.equalsIgnoreCase(op)) {

				ServletUtility.redirect(ORSView.BANNER_LIST_CTL, request, response);
				return;
			} else if (OP_BACK.equalsIgnoreCase(op)) {

				ServletUtility.redirect(ORSView.BANNER_LIST_CTL, request, response);
				return;
			}

			list = model.search(bean, pageNo, pageSize);
			next = model.search(bean, pageNo + 1, pageSize);

			if (list == null || list.isEmpty()) {
				ServletUtility.setErrorMessage("Record not found", request);

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
	protected String getView() {
		return ORSView.BANNER_LIST_VIEW;
	}

}
