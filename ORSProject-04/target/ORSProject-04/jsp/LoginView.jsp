<%@page import="in.co.rays.proj4.controller.LoginCtl"%>
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

	<%@include file="Header.jsp"%>
	<jsp:useBean id="bean" class="in.co.rays.proj4.bean.UserBean"
		scope="request"></jsp:useBean>

	<form action="<%=ORSView.LOGIN_CTL%>" method="post">

		<div align="center">

			<h1 align="center" style="margin-bottom: -15; color: navy">Login</h1>

			<div style="height: 15px; margin-bottom: 12px">
				<h3 align="center">
					<font color="green"><%=ServletUtility.getSuccessMessage(request)%></font>
				</h3>
				<h3 align="center">
					<font color="red"><%=ServletUtility.getErrorMessage(request)%></font>
				</h3>
			</div>

			<input type="hidden" name="id" value="<%=bean.getId()%>"> <input
				type="hidden" name="createdBy" value="<%=bean.getCreatedby()%>">
			<input type="hidden" name="modifiedBy"
				value="<%=bean.getModifiedby()%>"> <input type="hidden"
				name="createdDatetime"
				value="<%=DataUtility.getTimestamp(bean.getCreateddatetime())%>">
			<input type="hidden" name="modifiedDatetime"
				value="<%=DataUtility.getTimestamp(bean.getModifieddatetime())%>">
		</div>

		<table align="center">
			<tr>
				<th align="left">Login Id<span style="color: red">*</span></th>
				<td><input type="text" name="login" placeholder="Enter Email Id"
					value="<%=DataUtility.getStringData(bean.getLogin())%>"></td>

				<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("login", request)%></font></td>
			</tr>
			<tr>
				<th align="left">Password<span  style="color: red">*</span></th>
				<td><input type="text" name="password"
					placeholder="Enter password"
					value="<%=DataUtility.getStringData(bean.getPassword())%>"></td>

				<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("password", request)%></font></td>
			</tr>
			
			<tr>
			<th></th>
			<td><input type="submit" name="operation" value="<%=LoginCtl.OP_SIGN_IN%>">
			<input type="submit" name="operation" value="<%=LoginCtl.OP_SIGN_UP%>">&nbsp;
			</td>
			</tr>
			
			<tr>
					<th></th>
					<td></td>
				</tr>
				<tr>
					<th></th>
					<td><a href="<%=ORSView.FORGET_PASSWORD_CTL%>"><b>Forget my password?</b></a>&nbsp;</td>
				</tr>
		</table>
	</form>
	<%@include file="Footer.jsp" %>
</body>
</html>