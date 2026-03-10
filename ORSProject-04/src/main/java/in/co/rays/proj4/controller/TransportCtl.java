package in.co.rays.proj4.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.bean.TransportBean;
import in.co.rays.proj4.bean.UserBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DublicateRecordException;
import in.co.rays.proj4.model.TransportModel;
import in.co.rays.proj4.model.UserModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.DataValidator;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

@WebServlet("/TransportCtl")
public class TransportCtl extends BaseCtl {

	@Override
	protected boolean validate(HttpServletRequest request) {
		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("transportId"))) {
			request.setAttribute("transportId", PropertyReader.getValue("error.require", "Id"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("vehicleNo"))) {
			request.setAttribute("vehicleNo", PropertyReader.getValue("error.require", "description"));

			pass = false;
		} else if (!DataValidator.isInteger(request.getParameter("vehicleNo"))) {
			request.setAttribute("vehicleNo", "Invalid No");
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("driverName"))) {
			request.setAttribute("driverName", PropertyReader.getValue("error.require", "name"));
			pass = false;
		} else if (!DataValidator.isName(request.getParameter("driverName"))) {
			request.setAttribute("driverName", "Invalid Name");
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("vehicleType"))) {
			request.setAttribute("vehicleType", PropertyReader.getValue("error.require", "Vehicle Type"));
			pass = false;
		} else if (!DataValidator.isName(request.getParameter("vehicleType"))) {
			request.setAttribute("vehicleType", "Invalid Vehicle Type");
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("status"))) {
			request.setAttribute("status", PropertyReader.getValue("error.require", "Status"));
			pass = false;
		}

		return pass;
	}

	@Override
	protected TransportBean populateBean(HttpServletRequest request) {

		TransportBean bean = new TransportBean();

		bean.setId(DataUtility.getInt(request.getParameter("id")));
		bean.setTransportId(DataUtility.getLong(request.getParameter("transportId")));
		bean.setVehicleNo(DataUtility.getString(request.getParameter("vehicleNo")));
		bean.setDriverName(DataUtility.getString(request.getParameter("driverName")));
		bean.setVehicleType(DataUtility.getString(request.getParameter("vehicleType")));
		bean.setTransportStatus(DataUtility.getString(request.getParameter("status")));

		return bean;

	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		long id = DataUtility.getLong(request.getParameter("id"));

		TransportModel model = new TransportModel();

		if (id > 0) {

			try {
				TransportBean bean = model.findByPk(id);
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

		TransportModel model = new TransportModel();

		

		if (OP_SAVE.equalsIgnoreCase(op)) {
			TransportBean bean = (TransportBean) populateBean(request);

			try {
				long pk = model.add(bean);
				ServletUtility.setSuccessMessage("Transport added sucessfully", request);

			} catch (ApplicationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return;
			}
		} else if (OP_UPDATE.equalsIgnoreCase(op)) {
			TransportBean bean = (TransportBean) populateBean(request);
			
			try {
				if (id > 0) {
					model.update(bean);
				}
				ServletUtility.setBean(bean, request);
				ServletUtility.setSuccessMessage("Transport updated successfully", request);
			} catch (ApplicationException e) {
				e.printStackTrace();
				ServletUtility.handleException(e, request, response);
				return;
			}
		} else if (OP_CANCEL.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.USER_LIST_CTL, request, response);
			return;
		} 
		
		else if (OP_RESET.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.TRANSPORT_CTL, request, response);
			return;
		}

		ServletUtility.forword(getView(), request, response);

	}

	@Override
	protected String getView() {

		return ORSView.TRANSPORT_VIEW;
	}

}
