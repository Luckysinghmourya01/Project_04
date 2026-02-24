package in.co.rays.proj4.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.bean.TicketBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DublicateRecordException;
import in.co.rays.proj4.model.TicketModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.DataValidator;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

@WebServlet("/TicketCtl")
public class TicketCtl extends BaseCtl {

	@Override
	protected boolean validate(HttpServletRequest request) {
		boolean pass = true;
		if (DataValidator.isNull(request.getParameter("ticketCode"))) {
			request.setAttribute("ticketCode", PropertyReader.getValue("error.require", "Code"));
			pass = false;
		} 
		if (DataValidator.isNull(request.getParameter("tittle"))) {
			request.setAttribute("tittle", PropertyReader.getValue("error.require", "Tittle"));
			pass = false;
		} else if (!DataValidator.isName(request.getParameter("tittle"))) {
			request.setAttribute("tittle", "Invalid Tittle");
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("assigendAgent"))) {
			request.setAttribute("assigendAgent", PropertyReader.getValue("error.require", "Assigend Agent"));
			pass = false;
		} else if (!DataValidator.isName(request.getParameter("assigendAgent"))) {
			request.setAttribute("assigendAgent", "Invalid Name");
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

		TicketBean bean = new TicketBean();

		bean.setId(DataUtility.getInt(request.getParameter("id")));
		bean.setTicketCode(DataUtility.getString(request.getParameter("ticketCode")));
		bean.setTittle(DataUtility.getString(request.getParameter("tittle")));
		bean.setAssigendAgent(DataUtility.getString(request.getParameter("assigendAgent")));
		bean.setTicketStatus(DataUtility.getString(request.getParameter("ticketStatus")));

		return bean;
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		long id = DataUtility.getLong(request.getParameter("id"));

		TicketModel model = new TicketModel();
		if (id > 0) {

			try {
				TicketBean bean = model.findByPk(id);
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

		TicketModel model = new TicketModel();
		if (OP_SAVE.equalsIgnoreCase(op)) {

			TicketBean bean = (TicketBean) populateBean(request);

			try {
				long pk = model.add(bean);
				ServletUtility.setBean(bean, request);
				ServletUtility.setSuccessMessage("Ticket is Sucessfully saved", request);
			} catch (DublicateRecordException e) {
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("Ticket Already exist", request);

			} catch (ApplicationException e) {
				e.printStackTrace();
				ServletUtility.handleException(e, request, response);
				return;
			}
		} else if (OP_UPDATE.equalsIgnoreCase(op)) {

			TicketBean bean = (TicketBean) populateBean(request);

			try {
				if (id > 0) {
					model.update(bean);
				}
				ServletUtility.setBean(bean, request);
				ServletUtility.setSuccessMessage("Ticket is successfully updated", request);
			} catch (DublicateRecordException e) {
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("ticket already exists", request);
			} catch (ApplicationException e) {
				e.printStackTrace();
				ServletUtility.handleException(e, request, response);
				return;
			}

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.TICKET_LIST_CTL, request, response);
			return;
		} else if (OP_RESET.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.TICKET_CTL, request, response);
			return;
		}

		ServletUtility.forword(getView(), request, response);

	}

	@Override
	protected String getView() {
		return ORSView.TICKET_VIEW;
	}

}
