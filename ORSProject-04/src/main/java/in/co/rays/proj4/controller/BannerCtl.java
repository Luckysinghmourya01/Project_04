package in.co.rays.proj4.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.bean.bannerBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DublicateRecordException;
import in.co.rays.proj4.model.BannerModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.DataValidator;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

@WebServlet("/BannerCtl")
public class BannerCtl extends BaseCtl {

	@Override
	protected boolean validate(HttpServletRequest request) {
		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("bannerCode"))) {
			request.setAttribute("bannerCode", PropertyReader.getValue("error.require", "Banner Code"));
			pass = false;
		} else if (!DataValidator.isPasswordLength(request.getParameter("bannerCode"))) {
			request.setAttribute("bannerCode", "Banner code contain Lowercase and Uppercase");
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("bannerTitle"))) {
			request.setAttribute("bannerTitle", PropertyReader.getValue("error.require", "Banner Title"));
			pass = false;
		} else if (!DataValidator.isName(request.getParameter("bannerTitle"))) {
			request.setAttribute("bannerTitle", "Invalid banner title");
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("imagePath"))) {
			request.setAttribute("imagePath", PropertyReader.getValue("error.require", "Image Path "));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("status"))) {
			request.setAttribute("status", PropertyReader.getValue("error.require", "banner status "));
			pass = false;
		}
		return pass;
	}

	@Override
	protected BaseBean populateBean(HttpServletRequest request) {

		bannerBean bean = new bannerBean();

		bean.setId(DataUtility.getInt(request.getParameter("id")));
		bean.setBannerCode(DataUtility.getString(request.getParameter("bannerCode")));
		bean.setBannerTitle(DataUtility.getString(request.getParameter("bannerTitle")));
		bean.setImagePath(DataUtility.getString(request.getParameter("imagePath")));
		bean.setBannerStatus(DataUtility.getString(request.getParameter("status")));
		return bean;

	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		ServletUtility.forword(getView(), request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String op = DataUtility.getString(request.getParameter("operation"));

		BannerModel model = new BannerModel();

		if (OP_SAVE.equalsIgnoreCase(op)) {

			bannerBean bean = (bannerBean) populateBean(request);
			try {
				long pk = model.add(bean);
				ServletUtility.setBean(bean, request);
				ServletUtility.setSuccessMessage("Banner add sucessfull", request);
			} catch (DublicateRecordException e) {
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("Banner Code already exist", request);
				e.printStackTrace();
			} catch (ApplicationException e) {

				e.printStackTrace();
				ServletUtility.handleException(e, request, response);
				return;
			}
		} else if (OP_RESET.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.BANNER_CTL, request, response);
			return;
		}

		ServletUtility.forword(getView(), request, response);
	}

	@Override
	protected String getView() {
		return ORSView.BANNER_VIEW;
	}
}
