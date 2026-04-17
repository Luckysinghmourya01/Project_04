<%@page import="in.co.rays.proj4.util.HtmlUtility"%>
<%@page import="in.co.rays.proj4.controller.CartListCtl"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="in.co.rays.proj4.bean.CartBean"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

	<%@ include file="Header.jsp"%>
	<jsp:useBean id="bean" class="in.co.rays.proj4.bean.CartBean"
		scope="request"></jsp:useBean>

	<div>

		<h1 align="center" style="margin-bottom: -15; color: navy">Cart
			List</h1>

		<div align="center"height: 15px; margin-bottom: 12px">
			<h3 align="center">
				<font color="red"><%=ServletUtility.getErrorMessage(request)%></font>
			</h3>
			<h3 align="center">
				<font color="green"><%=ServletUtility.getSuccessMessage(request)%></font>
			</h3>
		</div>

		<form action="<%=ORSView.CART_LIST_CTL%>" method="post">

			<%
				int pageNo = ServletUtility.getpageNo(request);
				int pageSize = ServletUtility.getPageSize(request);
				int index = ((pageNo - 1) * pageSize) + 1;
				int nextPageSize = DataUtility.getInt(request.getAttribute("nextList").toString());
				List<CartBean> cartList = (List<CartBean>) request.getAttribute("cartList");
				List<CartBean> list = (List<CartBean>) ServletUtility.getList(request);

				Iterator<CartBean> it = list.iterator();

				if (list.size() != 0) {
			%>

			<table style="width: 100%">
				<tr>
					<td align="center"><label><b> Name : </b></label> <input
						type="text" name="userName" placeholder="Enter your name"
						value="<%=ServletUtility.getParameter("userName", request)%>">
						<label><b>Code :</b></label> <input type="text" name="cartCode"
						placeholder="Enter College City"
						value="<%=ServletUtility.getParameter("cartCode", request)%>">&emsp;
						<input type="submit" name="operation"
						value="<%=CartListCtl.OP_SEARCH%>">&nbsp; <input
						type="submit" name="operation" value="<%=CartListCtl.OP_RESET%>"></td>
				</tr>
			</table>

			<table border="1" style="width: 100%; border: groove;">
				<tr style="background-color: #e1e6f1e3;">
					<th width="5%"><input type="checkbox" id="selectall" /></th>
					<th width="5%">S.No</th>
					<th width="25%">Cart Code</th>
					<th width="25%">Name</th>
					<th width="15%">Total Item</th>
					<th width="10%">Status</th>
					<th width="15%">Edit</th>

				</tr>

				<%
					while (it.hasNext()) {

							bean = it.next();
				%>
				<tr>
					<td style="text-align: center;"><input type="checkbox"
						class="case" name="ids" value="<%=bean.getId()%>"></td>
					<td style="text-align: center;"><%=index++%></td>
					<td style="text-align: center; text-transform: capitalize;"><%=bean.getCartCode()%></td>
					<td style="text-align: center; text-transform: capitalize;"><%=bean.getUserName()%></td>
					<td style="text-align: center; text-transform: capitalize;"><%=bean.getTotalItem()%></td>
					<td style="text-align: center; text-transform: capitalize;"><%=bean.getStatus()%></td>

					<td style="text-align: center;"><a
						href="CartCtl?id=<%=bean.getId()%>">Edit</a></td>
				</tr>
				<%
					}
				%>
			</table>

			<table style="width: 100%">
				<tr>
					<td style="width: 25%"><input type="submit" name="operation"
						value="<%=CartListCtl.OP_PREVIOUS%>"
						<%=pageNo > 1 ? "" : "disabled"%>></td>
					<td align="center" style="width: 25%"><input type="submit"
						name="operation" value="<%=CartListCtl.OP_NEW%>"></td>
					<td align="center" style="width: 25%"><input type="submit"
						name="operation" value="<%=CartListCtl.OP_DELETE%>"></td>
					<td style="width: 25%" align="right"><input type="submit"
						name="operation" value="<%=CartListCtl.OP_NEXT%>"
						<%=(nextPageSize != 0) ? "" : "disabled"%>></td>
				</tr>
			</table>

			<%
				}
				if (list.size() == 0) {
			%>
			<table align="center">
				<tr>
					<td align="center"><input type="submit" name="operation"
						value="<%=CartListCtl.OP_BACK%>"></td>
				</tr>
			</table>
			<%
				}
			%>
		</form>
	</div>

</body>
</html>