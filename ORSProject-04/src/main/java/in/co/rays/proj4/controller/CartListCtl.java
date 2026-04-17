package in.co.rays.proj4.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.bean.CartBean;
import in.co.rays.proj4.bean.CollageBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.model.CartModel;
import in.co.rays.proj4.model.CollageModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

@WebServlet("/CartListCtl")
public class CartListCtl extends BaseCtl {

	@Override
	protected void preload(HttpServletRequest request) {

		CartModel cartModel = new CartModel();
		try {
			List cartList = cartModel.list();

			request.setAttribute("cartList", cartList);
		} catch (ApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	protected BaseBean populateBean(HttpServletRequest request) {

		CartBean bean = new CartBean();

		bean.setUserName(DataUtility.getString(request.getParameter("userName")));
		bean.setCartCode(DataUtility.getString(request.getParameter("cartCode")));
		bean.setStatus(DataUtility.getString(request.getParameter("cartStatus")));

		return bean;
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		int pageNo = 1;
		int pageSize = DataUtility.getInt(PropertyReader.getValue("pageSize"));

		CartBean bean = (CartBean) populateBean(request);

		CartModel model = new CartModel();

		try {

			List<CartBean> list = model.search(bean, pageNo, pageSize);
			List<CartBean> next = model.search(bean, pageNo + 1, pageSize);

			if (list != null) {

				ServletUtility.setBean(bean, request);
				ServletUtility.setList(list, request);
				ServletUtility.setPageNo(pageNo, request);
				ServletUtility.setPageSize(pageSize, request);
				request.setAttribute("nextList", next);

				ServletUtility.forword(getView(), request, response);

			}

		} catch (ApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			ServletUtility.handleException(e, request, response);

			return;
		}

	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		List list = null;
		List next = null;

		int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
		int pageSize = DataUtility.getInt(request.getParameter("pageSize"));

		pageNo = (pageNo == 0) ? 1 : pageNo;
		pageSize = (pageSize == 0) ? DataUtility.getInt(PropertyReader.getValue("page.size")) : pageSize;

		CartBean bean = (CartBean) populateBean(request);
		CartModel model = new CartModel();

		String op = DataUtility.getString(request.getParameter("operation"));
		String[] ids = request.getParameterValues("ids");

		try {

			if (OP_SEARCH.equalsIgnoreCase(op) || "Next".equalsIgnoreCase(op) || "Previous".equalsIgnoreCase(op)) {
				if (OP_SEARCH.equalsIgnoreCase(op)) {
					pageNo = 1;
				} else if (OP_NEXT.equalsIgnoreCase(op)) {
					pageNo++;
				} else if (OP_PREVIOUS.equalsIgnoreCase(op) && pageNo > 1) {
					pageNo--;
				}
			} else if (OP_NEW.equalsIgnoreCase(op)) {
				ServletUtility.redirect(ORSView.CART_CTL, request, response);
				return;
			}
			if (OP_DELETE.equalsIgnoreCase(op)) {
				pageNo = 1;
				if (ids != null && ids.length > 0) {
					CartBean deleteBean = new CartBean();
					for (String id : ids) {
						deleteBean.setId(DataUtility.getInt(id));
						model.delete(deleteBean);
						ServletUtility.setSuccessMessage("Cart deleted sucessfully", request);
					}
				} else {
					ServletUtility.setErrorMessage("Selected at least one record", request);
				}
			} else if (OP_RESET.equalsIgnoreCase(op)) {
				ServletUtility.redirect(ORSView.CART_LIST_CTL, request, response);
				return;
			} else if (OP_BACK.equalsIgnoreCase(op)) {
				ServletUtility.redirect(ORSView.CART_LIST_CTL, request, response);
				return;
			}
			list = model.search(bean, pageNo, pageSize);

			next = model.search(bean, pageNo + 1, pageSize);

			if (list == null || list.isEmpty()) {
				ServletUtility.setErrorMessage("No record found", request);
			}

			ServletUtility.setList(list, request);
			ServletUtility.setPageNo(pageNo, request);
			ServletUtility.setPageSize(pageSize, request);
			ServletUtility.setBean(bean, request);
			request.setAttribute("nextList", next.size());
			ServletUtility.forword(getView(), request, response);
		} catch (ApplicationException e) {
			e.printStackTrace();
			ServletUtility.handleException(e, request, response);
			return;
		}
	}

	@Override
	protected String getView() {

		return ORSView.CART_LIST_VIEW;
	}

}
