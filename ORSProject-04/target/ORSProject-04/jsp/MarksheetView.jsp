<%@page import="in.co.rays.proj4.util.ServletUtility"%>
<%@page import="in.co.rays.proj4.controller.MarksheetCtl"%>
<%@page import="in.co.rays.proj4.bean.StudentBean"%>
<%@page import="java.util.List"%>
<%@page import="in.co.rays.proj4.util.HtmlUtility"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
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
	<jsp:useBean id="bean" class="in.co.rays.proj4.bean.MarksheetBean"
		scope="request"></jsp:useBean>
	<div align="center">
		<form action="<%=ORSView.MARKSHEET_CTL%>" method="post">

			<h1 style="margin-bottom: -15; color: navy">
				<%
					if (bean != null && bean.getId() > 0) {
				%>Update
				<%
					} else {
				%>
				Add
				<%
					}
				%>
				Marksheet

			</h1>

			<%
				List<StudentBean> studentList = (List<StudentBean>) request.getAttribute("studentList");
			%>

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
					<th align="left"">Roll NO<span style="color: red">*</span></th>
					<td><input type="text" name="rollNo"
						placeholder="Enter Roll NO"
						value="<%=DataUtility.getStringData(bean.getRollNo())%>"
						<%=(bean.getId() > 0 ? "redonly" : "")%>></td>

					<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("rollNo", request)%></font>
					</td>
				<tr>
				</tr>


				<th align="left">Name<span style="color: red">*</span></th>
				<td><%=HtmlUtility.getList("studentId", String.valueOf(bean.getStudentId()), studentList)%>
				</td>

				<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("studentId", request)%></font>
				</td>
				</tr>

				<tr>
					<th>Physics<span style="color: red">*</span></th>
					<td align="left"><input type="text" maxlength="3"
						name="physics" placeholder="Enter Physics Marks"
						value="<%=DataUtility.getStringData(bean.getPhysics())%>"></td>

					<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("physics", request)%></font>
				</tr>



				<tr>
					<th align="left">Chemistry<span style="color: red">*</span></th>
					<td align="left"><input type="text" maxlength="3"
						name="chemistry" placeholder="Enter Chemistry Marks"
						value="<%=DataUtility.getStringData(bean.getChemistry())%>"></td>

					<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("chemistry", request)%></font>
				</tr>


				<tr>
					<th align="left">Maths<span style="color: red">*</span></th>
					<td align="left"><input type="text" maxlength="3" name="maths"
						placeholder="Enter Maths Marks"
						value="<%=DataUtility.getStringData(bean.getMaths())%>"></td>

					<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("maths", request)%></font>
				</tr>

				<tr>
					<th></th>
					<%
						if (bean != null && bean.getId() > 0) {
					%>
					<td><input type="submit" name="operation"
						value="<%=MarksheetCtl.OP_UPDATE%>"> <input type="submit"
						name="operation" value="<%=MarksheetCtl.OP_CANCEL%>"></td>
					<%
						} else {
					%>

					<td align="left" colspan="2"><input type="submit"
						name="operation" value="<%=MarksheetCtl.OP_SAVE%>"> <input
						type="submit" name="operation" value="<%=MarksheetCtl.OP_RESET%>"></td>
					<%
						}
					%>
				</tr>
			</table>

		</form>
	</div>
</body>
</html>