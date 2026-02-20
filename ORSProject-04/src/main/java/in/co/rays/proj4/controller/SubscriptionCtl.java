package in.co.rays.proj4.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.bean.SubscriptionBean;
import in.co.rays.proj4.bean.UserBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DublicateRecordException;
import in.co.rays.proj4.model.SubscriptionModel;
import in.co.rays.proj4.model.UserModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.DataValidator;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

@WebServlet("/SubscriptionCtl")
public class SubscriptionCtl extends BaseCtl {

	@Override
	protected boolean validate(HttpServletRequest request) {
		boolean pass = true;
		if (DataValidator.isNull(request.getParameter("subscriptionCode"))) {
			request.setAttribute("subscriptionCode", PropertyReader.getValue("error.require", "Code"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("planName"))) {
			request.setAttribute("planName", PropertyReader.getValue("error.require", "Plan Name"));

			pass = false;
		} else if (!DataValidator.isName(request.getParameter("planName"))) {

			request.setAttribute("planName", "Invalid PlanName");
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("startDate"))) {
			request.setAttribute("startDate", PropertyReader.getValue("error.require", "start Date"));
			pass = false;
		} else if (!DataValidator.isDate(request.getParameter("startDate"))) {
			request.setAttribute("startDate", PropertyReader.getValue("error.date", "Start Date"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("endDate"))) {
			request.setAttribute("endDate", PropertyReader.getValue("error.require", "End Date"));
			pass = false;
		} else if (!DataValidator.isDate(request.getParameter("endDate"))) {
			request.setAttribute("endDate", PropertyReader.getValue("error.date", "End Date"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("status"))) {
			request.setAttribute("status", PropertyReader.getValue("error.require", "Status"));
			pass = false;
		}

		return pass;
	}

	@Override
	protected BaseBean populateBean(HttpServletRequest request) {

		SubscriptionBean bean = new SubscriptionBean();

		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setSubscriptionCode(DataUtility.getString(request.getParameter("subscriptionCode")));
		bean.setPlanName(DataUtility.getString(request.getParameter("planName")));
		bean.setStartDate(DataUtility.getDate(request.getParameter("startDate")));
		bean.setEndDate(DataUtility.getDate(request.getParameter("endDate")));
		bean.setSubscriptionStatus(DataUtility.getString(request.getParameter("status")));

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

		long id = DataUtility.getLong(request.getParameter("id"));

		SubscriptionModel model = new SubscriptionModel();

		if (OP_SAVE.equalsIgnoreCase(op)) {
			SubscriptionBean bean = (SubscriptionBean) populateBean(request);

			try {
				long pk = model.add(bean);
				ServletUtility.setSuccessMessage("Subscription added sucessfully", request);

			} catch (DublicateRecordException e) {
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("Record Already exist", request);
				e.printStackTrace();
			} catch (ApplicationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return;
			}
		} else if (OP_UPDATE.equalsIgnoreCase(op)) {
			SubscriptionBean bean = (SubscriptionBean) populateBean(request);

			try {
				if (id > 0) {
					model.update(bean);
				}
				ServletUtility.setBean(bean, request);
				ServletUtility.setSuccessMessage("User updated successfully", request);
			} catch (ApplicationException e) {
				e.printStackTrace();
				ServletUtility.handleException(e, request, response);
				return;
			}
		} /*
			 * else if (OP_CANCEL.equalsIgnoreCase(op)) {
			 * ServletUtility.redirect(ORSView.USER_LIST_CTL, request, response); return; }
			 */

		else if (OP_RESET.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.SUBSCRIPTION_CTL, request, response);
			return;
		}

		ServletUtility.forword(getView(), request, response);
	}

	@Override
	protected String getView() {
		return ORSView.SUBSCRIPTION_VIEW;

	}

}
