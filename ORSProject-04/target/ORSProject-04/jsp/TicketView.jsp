<%@page import="in.co.rays.proj4.controller.TicketCtl"%>
<%@page import="in.co.rays.proj4.util.HtmlUtility"%>
<%@page import="java.util.HashMap"%>
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
	<form action="<%=ORSView.TICKET_CTL%>" method="post">

		<%@ include file="Header.jsp"%>
		<jsp:useBean id="bean" class="in.co.rays.proj4.bean.TicketBean"
			scope="request"></jsp:useBean>


		<div align="center">

			<h1 align="center" style="margin-bottom: -15; color: navy">

				<%
					if (bean != null && bean.getId() > 0) {
				%>Update<%
					} else {
				%>
				Add<%
					}
				%>
				Ticket
			</h1>

			<div style="height: 15px; margin-bottom: 12px">
				<h3 align="center">
					<font color="red"><%=ServletUtility.getErrorMessage(request)%></font>
				</h3>

				<H3 align="center">
					<font color="green"> <%=ServletUtility.getSuccessMessage(request)%>
					</font>
				</H3>
			</div>


			<table>
				<tr>
					<th align="left">Ticket Code<span style="color: red">*</span></th>
					<td><input type="text" name="ticketCode"
						placeholder="Enter code "
						value="<%=DataUtility.getStringData(bean.getTicketCode())%>"></td>

					<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("ticketCode", request)%></font>
					</td>
				</tr>


				<tr>
					<th align="left">Title <span style="color: red">*</span></th>
					<td><input type="text" name="tittle"
						placeholder="Enter Tittle name"
						value="<%=DataUtility.getStringData(bean.getTittle())%>"></td>

					<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("tittle", request)%></font>
					</td>
				</tr>
				<tr>
					<th>Agent Name<span style="color: red">*</span></th>
					<td><input type="text" name="assigendAgent"
						placeholder="Enter Agent name"
						value="<%=DataUtility.getStringData(bean.getAssigendAgent())%>"></td>

					<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("assigendAgent", request)%></font>
					</td>
				</tr>

				</tr>


				<tr>
					<th align="left">Status<span style="color: red">*</span></th>
					<td>
						<%
							HashMap<String, String> map = new HashMap<String, String>();

							map.put("Active", "Active");
							map.put("InActive", "InActive");

							String htmlList = HtmlUtility.getList("status", bean.getTicketStatus(), map);
						%> <%=htmlList%>

					</td>

					<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("status", request)%></font>
					</td>
				</tr>


				<tr>
					<th>
						<%
							if (bean != null && bean.getId() > 0) {
						%>
					
					<td><input type="submit" name="operation"
						value="<%=TicketCtl.OP_UPDATE%>"> <input type="submit"
						name="operation" value="<%=TicketCtl.OP_CANCEL%>"></td>
					<%
						} else {
					%>
					<td><input type="submit" name="operation"
						value="<%=TicketCtl.OP_SAVE%>"> <input type="submit"
						name="operation" value="<%=TicketCtl.OP_RESET%>"></td>
					<%
						}
					%>
				</tr>

			</table>
		</div>
	</form>
</body>
</html>