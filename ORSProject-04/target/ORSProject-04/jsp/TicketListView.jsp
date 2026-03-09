<%@page import="in.co.rays.proj4.controller.TicketListCtl"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="in.co.rays.proj4.bean.TicketBean"%>
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
	<jsp:useBean id="bean" class="in.co.rays.proj4.bean.TicketBean"
		scope="request"></jsp:useBean>

	<div align="center">
		<h1 align="center" style="margin-bottom: -15px; color: navy;">Ticket
			List</h1>

		<div style="height: 15px; margin-bottom: 12px">
			<h3>
				<font color="red"><%=ServletUtility.getErrorMessage(request)%></font>
			</h3>
			<h3>
				<font color="green"><%=ServletUtility.getSuccessMessage(request)%></font>
			</h3>
		</div>
		<form action="<%=ORSView.TICKET_LIST_CTL%>" method="post">

			<%
				int pageNo = ServletUtility.getpageNo(request);
				int pageSize = ServletUtility.getPageSize(request);
				int index = ((pageNo - 1) * pageSize) + 1;
				int nextListSize = DataUtility.getInt(request.getAttribute("nextListSize").toString());

				List<TicketBean> list = (List<TicketBean>) ServletUtility.getList(request);

				Iterator<TicketBean> it = list.iterator();
				if (list.size() != 0) {
			%>

			<input type="hidden" name="pageNo" value="<%=pageNo%>"> <input
				type="hidden" name="pageSize" value="<%=pageSize%>">

			<table style="width: 100%">
				<tr>
					<td align="center"><label><b>Ticket Code :</b></label> <input
						type="text" name="ticketCode" placeholder="Enter First Name"
						value="<%=ServletUtility.getParameter("ticketCode", request)%>">&emsp;

						<label align="center"><b>Assigend Name :</b></label><input
						type="text" name="assigendName" placeholder="Enter assigend Name"
						value="<%=ServletUtility.getParameter("assigendName", request)%>">





						<input type="submit" name="operation"
						value="<%=TicketListCtl.OP_SEARCH%>"> &nbsp; <input
						type="submit" name="operation" value="<%=TicketListCtl.OP_RESET%>">
					</td>
				</tr>
			</table>
			<br>

			<table border="1" style="width: 100%; border: groove;">
				<tr style="background-color: #e1e6f1e3;">
					<th width="5%"><input type="checkbox" id="selectall" /></th>
					<th width="5%">S.No</th>
					<th width="13%">Ticket Code</th>
					<th width="13%">Tittle</th>
					<th width="23%">Assigend Name</th>
					<th width="10%">Status</th>
					<th width="5%">Edit</th>
				</tr>

				<%
					while (it.hasNext()) {
							bean = (TicketBean) it.next();
				%>

				<tr>
					<td style="text-align: center;"><input type="checkbox"
						class="case" name="ids" value="<%=bean.getId()%>"></td>
					<td style="text-align: center;"><%=index++%></td>
					<td style="text-align: center; text-transform: capitalize;"><%=bean.getTicketCode()%></td>
					<td style="text-align: center; text-transform: capitalize;"><%=bean.getTittle()%></td>
					<td style="text-align: center; text-transform: lowercase;"><%=bean.getAssigendAgent()%></td>
					<td style="text-align: center;"><%=bean.getTicketStatus()%></td>
					<td style="text-align: center;"><a
						href="TicketCtl?id=<%=bean.getId()%>">Edit</a></td>
				</tr>

				<%
					}
				%>

				<table style="width: 100%">
					<tr>
						<td style="width: 25%"><input type="submit" name="operation"
							value="<%=TicketListCtl.OP_PREVIOUS%>"
							<%=pageNo > 1 ? "" : "disabled"%>></td>

						<td align="center" style="width: 25%"><input type="submit"
							name="operation" value="<%=TicketListCtl.OP_NEW%>"></td>

						<td align="center" style="width: 25%"><input type="submit"
							name="operation" value="<%=TicketListCtl.OP_DELETE%>"></td>

						<td align="right" sStyle="width: 25%"><input type="submit"
							name="operation" value="<%=TicketListCtl.OP_NEXT%>"
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
							value="<%=TicketListCtl.OP_BACK%>"></td>
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