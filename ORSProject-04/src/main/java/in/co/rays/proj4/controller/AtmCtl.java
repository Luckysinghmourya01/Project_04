package in.co.rays.proj4.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.proj4.bean.AtmBean;
import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.model.AtmModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.DataValidator;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

@WebServlet("/AtmCtl")
public class AtmCtl extends BaseCtl {

	@Override
	protected boolean validate(HttpServletRequest request) {
		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("location"))) {
			request.setAttribute("location", PropertyReader.getValue("error.require", "Location"));
			pass = false;
		} else if (!DataValidator.isName(request.getParameter("location"))) {
			request.setAttribute("location", "Invalid Loccation");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("cashAailable"))) {
			request.setAttribute("cashAailable", PropertyReader.getValue("error.require", "Cash "));

			pass = false;
		} else if (!DataValidator.isInteger(request.getParameter("cashAailable"))) {

			request.setAttribute("cashAailable", "Invalid LastName");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("dob"))) {
			request.setAttribute("dob", PropertyReader.getValue("error.require", "Date of Birth"));
			pass = false;
		} else if (!DataValidator.isDate(request.getParameter("dob"))) {
			request.setAttribute("dob", PropertyReader.getValue("error.date", "Date of Birth"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("remark"))) {
			request.setAttribute("remark", PropertyReader.getValue("error.require", "Remark"));
			pass = false;
		}

		return pass;
	}

	@Override
	protected BaseBean populateBean(HttpServletRequest request) {

		AtmBean bean = new AtmBean();

		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setLocation(DataUtility.getString(request.getParameter("location")));
		bean.setCashAailable(DataUtility.getInt(request.getParameter("cashAailable")));
		bean.setDob(DataUtility.getDate(request.getParameter("dob")));
		bean.setRemark(DataUtility.getString(request.getParameter("remark")));

		return bean;

	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		long id = DataUtility.getLong(request.getParameter("id"));

		AtmModel model = new AtmModel();
		if (id > 0) {

			try {
				AtmBean bean = model.findByPk(id);
				ServletUtility.setBean(bean, request);
			} catch (ApplicationException e) {

				e.printStackTrace();
				ServletUtility.handleException(e, request, response);
				return;
			}
		}

		ServletUtility.forword(getView(), request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String op = DataUtility.getString(request.getParameter("operation"));

		long id = DataUtility.getLong(request.getParameter("id"));

		AtmModel model = new AtmModel();

		if (OP_SAVE.equalsIgnoreCase(op)) {
			AtmBean bean = (AtmBean) populateBean(request);

			try {
				long pk = model.add(bean);
				ServletUtility.setSuccessMessage("Cash added sucessfully", request);

			} catch (ApplicationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return;
			}
		} else if (OP_UPDATE.equalsIgnoreCase(op)) {

		    AtmBean bean = (AtmBean) populateBean(request);
		    bean.setId(id);   

		    try {
		        if (id > 0) {
		            model.update(bean);
		            System.out.println(bean);
			        ServletUtility.setBean(bean, request);
			        ServletUtility.setSuccessMessage("Atm updated successfully", request);
		        }
		       

		    } catch (ApplicationException e) {
		        e.printStackTrace();
		        ServletUtility.handleException(e, request, response);
		        return;
		    }
		
		} else if (OP_CANCEL.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.ATM_LIST_CTL, request, response);
			return;
		}

		else if (OP_RESET.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.ATM_CTL, request, response);
			return;
		}

		ServletUtility.forword(getView(), request, response);
	}

	@Override
	protected String getView() {

		return ORSView.ATM_VIEW;
	}
}
