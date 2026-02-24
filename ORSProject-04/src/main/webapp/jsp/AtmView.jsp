<%@page import="in.co.rays.proj4.controller.AtmCtl"%>
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
	<form action="<%=ORSView.ATM_CTL%>" , method="post">

		<%@ include file="Header.jsp"%>
		<jsp:useBean id="bean" class="in.co.rays.proj4.bean.AtmBean"
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
				Atm
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


			<input type="hidden" name="id" value="<%=bean.getId()%>"> <input
				type="hidden" name="createdby" value="<%=bean.getCreatedby()%>">
			<input type="hidden" name="modifiedby"
				value="<%=bean.getModifiedby()%>"> <input type="hidden"
				name="createddatetime"
				value="<%=DataUtility.getTimestamp(bean.getCreateddatetime())%>">
			<input type="hidden" name="modifieddatetime"
				value="<%=DataUtility.getTimestamp(bean.getModifieddatetime())%>">

			<table>
				<tr>
					<th align="left">Location<span style="color: red">*</span></th>
					<td><input type="text" name="location"
						placeholder="Enter first name"
						value="<%=DataUtility.getStringData(bean.getLocation())%>"></td>

					<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("location", request)%></font>
					</td>
				</tr>


				<tr>
					<th align="left">Cash<span style="color: red">*</span></th>
					<td><input type="text" name="cashAailable"
						placeholder="Enter cash "
						value="<%=DataUtility.getStringData(bean.getCashAailable())%>"></td>

					<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("cashAailable", request)%></font>
					</td>
				</tr>
				<tr>
					<th>Dob<span style="color: red">*</span></th>
					<td><input type="text" name="dob"
						placeholder="Enter date of birth"
						value="<%=DataUtility.getStringData(bean.getDob())%>"></td>

					<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("dob", request)%></font>
					</td>
				</tr>

				</tr>



				<tr>
					<th align="left">Remark<span style="color: red">*</span></th>
					<td>
						<%
							HashMap<String, String> map = new HashMap<String, String>();

							map.put("Sucess", "Sucess");
							map.put("Unsucess", "Unsucess");

							String htmlList = HtmlUtility.getList("remark", bean.getRemark(), map);
						%> <%=htmlList%>

					</td>

					<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("remark", request)%></font>
					</td>
				</tr>


				<tr>
					<th>
						<%
							if (bean != null && bean.getId() > 0) {
						%>
					
					<td><input type="submit" name="operation"
						value="<%=AtmCtl.OP_UPDATE%>"> <input type="submit"
						name="operation" value="<%=AtmCtl.OP_CANCEL%>"></td>
					<%
						} else {
					%>
					<td><input type="submit" name="operation"
						value="<%=AtmCtl.OP_SAVE%>"> <input type="submit"
						name="operation" value="<%=AtmCtl.OP_RESET%>"></td>
					<%
						}
					%>
				</tr>

			</table>
		</div>
	</form>
</body>
</html>