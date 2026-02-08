<%@page import="in.co.rays.proj4.util.ServletUtility"%>
<%@page import="in.co.rays.proj4.controller.CollegeCtl"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
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
	<%@ include file="Header.jsp"%>
	<form action="<%=ORSView.COLLEGECTL%>" method="post">

		<jsp:useBean id="bean" class="in.co.rays.proj4.bean.CollageBean"
			scope="request"></jsp:useBean>

		<div align="center">
			<h1 align="center" style="margin-bottom: -15px; color: navy">Add College</h1>
			
			<div style="height: 15px; margin-bottom: 12px">
			<h3 align="center">
			<font color="red"><%=ServletUtility.getErrorMessage(request) %></font>
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
					<th align="left">Name<span style="color: red">*</span></th>
					<td><input type="text" name="name"
						placeholder="Enter College Name"
						value="<%=DataUtility.getStringData(bean.getName())%>"></td>

					<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("name", request)%></font>
					</td>
				</tr>

				<tr>
					<th align="left">Address<span style="color: red">*</span></th>
					<td><input type="text" name="address"
						placeholder="Enter Address"
						value="<%=DataUtility.getStringData(bean.getAddress())%>"></td>

					<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("address", request)%></font>
					</td>
				</tr>

				<tr>
					<th align="left">State<span style="color: red">*</span></th>
					<td><input type="text" name="state" placeholder="Enter State"
						value="<%=DataUtility.getStringData(bean.getState())%>"></td>

					<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("state", request)%></font>
					</td>
				</tr>
				<tr>
					<th align="left">City<span style="color: red">*</span></th>
					<td><input type="text" name="city" placeholder="Enter City"
						value="<%=DataUtility.getStringData(bean.getCity())%>"></td>
						
						<td style="position: fixed;">
						<font color="red"><%=ServletUtility.getErrorMessage("city", request)%></font>
						</td>
				</tr>

				<tr>
					<th>Mobile No<span style="color: red">*</span></th>
					<td><input type="text" name="phoneNo" maxlength="10"
						placeholder="Enter Mobile No"
						value="<%=DataUtility.getStringData(bean.getPhoneNo())%>"></td>
						
						<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("phoneNo", request)%></font>
					</td>
				</tr>

				<tr>
					<th></th>
					<td align="left" colspan="2"><input type="submit"
						name="operation" value="<%=CollegeCtl.OP_SAVE%>"> <input
						type="submit" name="operation" value="<%=CollegeCtl.OP_RESET%>"></td>
				</tr>
			</table>
		</div>
	</form>
</body>
</html>
