<%@page import="java.text.SimpleDateFormat"%>
<%@page import="in.co.rays.proj4.model.AtmModel"%>
<%@page import="in.co.rays.proj4.controller.AtmListCtl"%>
<%@page import="java.util.Iterator"%>
<%@page import="in.co.rays.proj4.bean.AtmBean"%>
<%@page import="java.util.List"%>
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
	<jsp:useBean id="bean" class="in.co.rays.proj4.bean.AtmBean"
		scope="request"></jsp:useBean>

	<div align="center">
		<h1 align="center" style="margin-bottom: -15px; color: navy;">Atm
			List</h1>

		<div style="height: 15px; margin-bottom: 12px">
			<h3>
				<font color="red"><%=ServletUtility.getErrorMessage(request)%></font>
			</h3>
			<h3>
				<font color="green"><%=ServletUtility.getSuccessMessage(request)%></font>
			</h3>
		</div>
		<form action="<%=ORSView.ATM_LIST_CTL%>" method="post">

			<%
				int pageNo = ServletUtility.getpageNo(request);
				int pageSize = ServletUtility.getPageSize(request);
				int index = ((pageNo - 1) * pageSize) + 1;
				int nextListSize = DataUtility.getInt(request.getAttribute("nextListSize").toString());

				List<AtmBean> list = (List<AtmBean>) ServletUtility.getList(request);

				Iterator<AtmBean> it = list.iterator();
				if (list.size() != 0) {
			%>

			<input type="hidden" name="pageNo" value="<%=pageNo%>"> <input
				type="hidden" name="pageSize" value="<%=pageSize%>">

			<table style="width: 100%">
				<tr>
					<td align="center"><label><b>Location :</b></label> <input
						type="text" name="location" placeholder="Enter Location "
						value="<%=ServletUtility.getParameter("location", request)%>">&emsp;



						<input type="submit" name="operation"
						value="<%=AtmListCtl.OP_SEARCH%>"> &nbsp; <input
						type="submit" name="operation" value="<%=AtmListCtl.OP_RESET%>">
					</td>
				</tr>
			</table>
			<br>

			<table border="1" style="width: 100%; border: groove;">
				<tr style="background-color: #e1e6f1e3;">
					<th width="5%"><input type="checkbox" id="selectall" /></th>
					<th width="5%">S.No</th>
					<th width="13%">Location</th>
					<th width="13%">Cash</th>
					<th width="23%">Date of Birth</th>
					<th width="10%">Remark</th>
					<th width="5%">Edit</th>
				</tr>

				<%
					while (it.hasNext()) {
							bean = (AtmBean) it.next();
							AtmModel model = new AtmModel();

							SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
							String date = sdf.format(bean.getDob());
				%>

				<tr>
					<td style="text-align: center;"><input type="checkbox"
						class="case" name="ids" value="<%=bean.getId()%>"></td>
					<td style="text-align: center;"><%=index++%></td>
					<td style="text-align: center; text-transform: capitalize;"><%=bean.getLocation()%></td>
					<td style="text-align: center; text-transform: capitalize;"><%=bean.getCashAailable()%></td>
					<td style="text-align: center; text-transform: lowercase;"><%=date%></td>
					<td style="text-align: center;"><%=bean.getRemark()%></td>
					<td style="text-align: center;"><a
						href="AtmCtl?id=<%=bean.getId()%>">Edit</a></td>
				</tr>

				<%
					}
				%>

				<table style="width: 100%">
					<tr>
						<td style="width: 25%"><input type="submit" name="operation"
							value="<%=AtmListCtl.OP_PREVIOUS%>"
							<%=pageNo > 1 ? "" : "disabled"%>></td>

						<td align="center" style="width: 25%"><input type="submit"
							name="operation" value="<%=AtmListCtl.OP_NEW%>"></td>

						<td align="center" style="width: 25%"><input type="submit"
							name="operation" value="<%=AtmListCtl.OP_DELETE%>"></td>

						<td align="right" sStyle="width: 25%"><input type="submit"
							name="operation" value="<%=AtmListCtl.OP_NEXT%>"
							<%=nextListSize != 0 ? "" : "disabled"%>></td>
					</tr>

				</table>

				<%
					}
					if (list.size() == 0) {
				%>
				<table>
					<tr>
						<td align="right"><input type="submit" name="operation"
							value="<%=AtmListCtl.OP_BACK%>"></td>
					</tr>
				</table>

				<%
					}
				%>
			</table>
		</form>

	</div>

</body>
</html>