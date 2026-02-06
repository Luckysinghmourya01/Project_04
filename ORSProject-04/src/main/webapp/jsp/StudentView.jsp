<%@page import="in.co.rays.proj4.util.ServletUtility"%>
<%@page import="in.co.rays.proj4.controller.StudentCtl"%>
<%@page import="in.co.rays.proj4.util.HtmlUtility"%>
<%@page import="java.util.HashMap"%>

<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.bean.StudentBean"%>
<%@page import="java.util.List"%>
<%@page import="in.co.rays.proj4.controller.ORSView"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

	<form action="<%=ORSView.STUDENT_CTL%>" method="post">

		<%@ include file="Header.jsp"%>
		
		<jsp:useBean id="bean" class="in.co.rays.proj4.bean.StudentBean"
			scope="request"></jsp:useBean>
		<%
			List<StudentBean> roleList = (List<StudentBean>) request.getAttribute("collegeList");
		%>
		<div align="center">

			<h1 style="margin-bottom: -15; color: navy">Add Student</h1>


			<div style="height: 15px; margin-bottom: 12px">
				<h3 align="center">
					<font color="green"><%=ServletUtility.getSuccessMessage(request)%></font>
				</h3>

				<h3 align="center">
					<font color="red"><%=ServletUtility.getErrorMessage(request)%></font>
				</h3>
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
					<th align="left">First Name<span style="color: red">*</span></th>
					<td><input type="text" name="firstName"
						placeholder="Enter First Name"
						value="<%=DataUtility.getStringData(bean.getFirstName())%>"></td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("firstName", request)%></font></td>
				</tr>
				<tr>
					<th align="left">Last Name<span style="color: red">*</span></th>
					<td><input type="text" name="lastName"
						placeholder="Enter Last Name"
						value="<%=DataUtility.getStringData(bean.getLastName())%>"></td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("lastName", request)%></font></td>
				</tr>

				<tr>
					<th align="left">Email Id<span style="color: red">*</span></th>
					<td><input type="text" name="email"
						placeholder="Enter Email Id"
						value="<%=DataUtility.getStringData(bean.getEmail())%>">
					<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("email", request)%></font>
					</td>
				</tr>

				<tr>
					<th align="left">Date of Birth<span style="color: red">*</span></th>
					<td><input type="text" id="udate" name="dob"
						placeholder="Select Date of Birth"
						value="<%=DataUtility.getDateString(bean.getDob())%>"></td>

					<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("dob", request)%></font>
					</td>

				</tr>

				<tr>
					<th align="left">Gender<span style="color: red">*</span></th>
					<td>
						<%
							HashMap<String, String> map = new HashMap<String, String>();

							map.put("male", "male");
							map.put("female", "female");

							String htmlList = HtmlUtility.getList("gender", bean.getGender(), map);
						%> <%=htmlList%>
					<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("gender", request)%></font>
					</td>

					</td>

				</tr>
				<tr>
					<th align="left">College<span style="color: red">*</span></th>
					<td><%=HtmlUtility.getList("collegeId", String.valueOf(bean.getCollageId()), roleList)%>
					</td>

					<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("collegeId", request)%></font>
					</td>
				</tr>

				<tr>
					<th align="left">Mobile No<span style="color: red">*</span></th>
					<td><input type="text" name="mobileNo" maxlength="10"
						placeholder="Enter Mobile No."
						value="<%=DataUtility.getStringData(bean.getMobileNo())%>"></td>

					<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("mobileNo", request)%></font>
					</td>

				</tr>

				<tr>
					<th></th>
					<td align="left" colspan="2"><input type="submit"
						name="operation" value="<%=StudentCtl.OP_SAVE%>"> <input
						type="submit" name="operation" value="<%=StudentCtl.OP_RESET%>">
					</td>
				</tr>
			</table>
		</div>
	</form>
</body>
</html>